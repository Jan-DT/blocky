package nl.jandt.blocky.engine.impl.module;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import nl.jandt.blocky.engine.impl.command.CommandService;

import java.util.List;

public interface ModuleHandler {
    /**
     * @return A list of modules, sorted by load order.
     */
    List<LoadedModule> getModules();

    /**
     * Initializes every module in order.
     */
    void initialize(EventNode<Event> eventNode, CommandService commandService);
}
