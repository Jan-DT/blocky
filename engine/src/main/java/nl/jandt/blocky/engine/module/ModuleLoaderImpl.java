package nl.jandt.blocky.engine.module;

import nl.jandt.blocky.engine.annotation.BlockyModule;
import nl.jandt.blocky.engine.util.Namespace;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ModuleLoaderImpl implements ModuleLoader {
    private static final Logger log = LoggerFactory.getLogger(ModuleLoaderImpl.class);

    public ModuleLoaderImpl() {}

    public List<LoadedModule> loadModules() {
        final var moduleSet = scanAvailableModules();

        final Map<Namespace, LoadedModule> modules = new HashMap<>();
        final Map<Namespace, List<Namespace>> dependencyGraph = new HashMap<>();
        final Map<Namespace, @NotNull Integer> degrees = new HashMap<>();

        for (LoadedModule module : moduleSet) {
            modules.put(module.getIdentifier(), module);
            dependencyGraph.put(module.getIdentifier(), new ArrayList<>());
            degrees.put(module.getIdentifier(), 0);
        }

        buildGraph(modules, dependencyGraph, degrees);
        return calculateLoadOrder(modules, dependencyGraph, degrees)
                .stream().map(modules::get).toList();
    }

    public static @NotNull Set<LoadedModule> scanAvailableModules() {
        // retrieve all annotated classes in the entire classpath
        final var reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(Scanners.TypesAnnotated));

        // get classes annotated as BlockyModule
        final var moduleClasses = reflections.getTypesAnnotatedWith(BlockyModule.class);
        final var modules = new HashSet<LoadedModule>();

        log.info("Scanning for modules...");
        for (Class<?> moduleClass : moduleClasses) {
            // checks if annotated modules implement Entrypoint
            if (!Entrypoint.class.isAssignableFrom(moduleClass)) {
                log.error("Skipping module {}. Classes annotated with @BlockyModule must implement Entrypoint.",
                        moduleClass);
                continue;
            }

            try {
                // construct a new instance of the entrypoint class
                Entrypoint moduleInstance = (Entrypoint) moduleClass.getDeclaredConstructor().newInstance();
                BlockyModule moduleAnnotation = moduleClass.getAnnotation(BlockyModule.class);

                // construct new module
                modules.add(new LoadedModule(moduleAnnotation, moduleInstance));

                log.debug("Loaded module {}.", moduleClass);
            } catch (Exception e) {
                log.error("Failed to load module {}. Skipping...", moduleClass, e);
            }
        }

        return modules;
    }

    private void buildGraph(@NotNull Map<Namespace, LoadedModule> modules,
                            @NotNull Map<Namespace, List<Namespace>> dependencyGraph,
                            @NotNull Map<Namespace, @NotNull Integer> degrees) throws RuntimeException {
        for (LoadedModule module : modules.values()) {
            final var moduleId = module.getIdentifier();
            for (LoadedModule.Dependency dependency : module.getDependencies()) {
                final var dependencyId = dependency.identifier();

                if (!modules.containsKey(dependencyId)) {
                    throw new RuntimeException("Module %s requires non-installed dependency %s.".formatted(moduleId, dependencyId));
                }
                if (!dependency.isCompatible(modules.get(dependencyId).getVersion())) {
                    throw new RuntimeException("Module %s is incompatible with dependency version %s.".formatted(moduleId, dependencyId));
                }

                dependencyGraph.get(dependencyId).add(moduleId);
                degrees.compute(moduleId, (m, v) -> Objects.requireNonNull(v) + 1);
            }
        }
    }

    private @NotNull List<Namespace> calculateLoadOrder(@NotNull Map<Namespace, LoadedModule> modules,
                                                        @NotNull Map<Namespace, List<Namespace>> dependencyGraph,
                                                        @NotNull Map<Namespace, @NotNull Integer> degrees) {
        final List<Namespace> loadOrder = new ArrayList<>();
        final Queue<Namespace> queue = new LinkedList<>();

        for (final var entry : degrees.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            final var currentId = queue.poll();
            loadOrder.add(currentId);

            for (final var dependentId : dependencyGraph.get(currentId)) {
                degrees.compute(dependentId, (n, v) -> Objects.requireNonNull(v) - 1);
                if (degrees.get(dependentId) == 0) {
                    queue.offer(dependentId);
                }
            }
        }

        if (loadOrder.size() != modules.size()) {
            final List<Namespace> incompatibleMods = new ArrayList<>();

            for (final var entry : degrees.entrySet()) {
                if (entry.getValue() != 0) incompatibleMods.add(entry.getKey());
            }

            throw new RuntimeException("The following modules contain circular dependencies: %s".formatted(incompatibleMods));
        }

        return loadOrder;
    }
}
