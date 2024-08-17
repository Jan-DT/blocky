package nl.jandt.blocky.engine.module;

import nl.jandt.blocky.engine.annotation.BlockyModule;
import nl.jandt.blocky.engine.util.Namespace;
import nl.jandt.blocky.engine.util.SemVer;
import nl.jandt.blocky.engine.util.SemVerBounds;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;

public class LoadedModule {
    private static final Logger log = LoggerFactory.getLogger(LoadedModule.class);

    private final Namespace identifier;
    private final String name;
    private final SemVer version;
    private final Collection<Dependency> dependencies;

    private final Entrypoint entrypoint;

    public LoadedModule(@NotNull BlockyModule module, Entrypoint entrypoint) {
        try {
            this.identifier = Namespace.of(module.identifier());
            this.name = module.name().isBlank() ? identifier.toString() : module.name();
            this.version = SemVer.from(module.version());
            this.dependencies = parseDependencies(module.dependencies());

            this.entrypoint = entrypoint;
        } catch (IllegalArgumentException e ) {
            log.error("Failed to parse parameters for module {}.", module.identifier(), e);
            throw new IllegalArgumentException(e);
        }
    }

    public Namespace getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public SemVer getVersion() {
        return version;
    }

    public Entrypoint getEntrypoint() {
        return entrypoint;
    }

    public Collection<Dependency> getDependencies() {
        return dependencies;
    }

    @Contract(pure = true)
    public static @NotNull Collection<Dependency> parseDependencies(String @NotNull [] dependencies)
            throws IllegalArgumentException {
        final var parsed = new HashSet<Dependency>();

        for (String dependency : dependencies) {
            if (dependency.isBlank()) continue;

            final var split = dependency.split(":");
            if (split.length != 2)
                throw new IllegalArgumentException("Invalid format specified for dependency: " + dependency);
            final Namespace identifier = Namespace.of(split[0].strip());
            final SemVerBounds bounds = SemVerBounds.from(split[1].strip());

            parsed.add(new Dependency(identifier, bounds, false));
        }

        return parsed;
    }

    public record Dependency(Namespace identifier, SemVerBounds bounds, boolean optional) {
        boolean isCompatible(SemVer version) {
            return bounds.isCompatible(version);
        }
    }
}
