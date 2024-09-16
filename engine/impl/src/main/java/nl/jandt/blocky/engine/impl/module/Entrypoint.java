package nl.jandt.blocky.engine.impl.module;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import nl.jandt.blocky.engine.impl.command.CommandService;
import org.jetbrains.annotations.ApiStatus;

public interface Entrypoint {
    /**
     * This method is called during module initialization.
     * Modules are guaranteed to be initialized after their dependencies.
     */
    @ApiStatus.OverrideOnly
    void init();

    /**
     * This method is called after {@link Entrypoint#init},
     * and should be used to register any events used in this module.
     */
    @ApiStatus.OverrideOnly
    default void registerEvents(EventNode<Event> eventNode) {}

    /**
     * This method is called after {@link Entrypoint#registerEvents},
     * and should be used to register any commands used in this module.
     */
    @ApiStatus.OverrideOnly
    default void registerCommands(CommandService commandService) {}
}
