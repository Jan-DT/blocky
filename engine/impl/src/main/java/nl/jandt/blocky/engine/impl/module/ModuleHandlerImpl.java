package nl.jandt.blocky.engine.impl.module;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import nl.jandt.blocky.engine.impl.command.CommandService;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModuleHandlerImpl implements ModuleHandler {
    private final List<LoadedModule> modules;

    public ModuleHandlerImpl(List<LoadedModule> modules) {
        this.modules = modules;
    }

    public ModuleHandlerImpl(@NotNull ModuleLoader moduleLoader) {
        this.modules = moduleLoader.loadModules();
    }

    @Override
    public void initialize(EventNode<Event> eventNode, CommandService commandService) {
        for (final var module : modules) {
            module.getEntrypoint().init();
            module.getEntrypoint().registerEvents(eventNode);
            module.getEntrypoint().registerCommands(commandService);
        }
    }

    @Override
    public List<LoadedModule> getModules() {
        return modules;
    }
}
