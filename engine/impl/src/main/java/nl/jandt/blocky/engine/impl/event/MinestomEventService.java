package nl.jandt.blocky.engine.impl.event;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record MinestomEventService(EventNode<Event> eventNode) implements EventService {

    @Contract(" -> new")
    public static @NotNull MinestomEventService ofDefault() {
        return new MinestomEventService(MinecraftServer.getGlobalEventHandler());
    }
}
