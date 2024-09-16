package nl.jandt.blocky.engine.impl;

import net.minestom.server.MinecraftServer;
import nl.jandt.blocky.engine.impl.command.CommandService;
import nl.jandt.blocky.engine.impl.command.MinestomCommandService;
import nl.jandt.blocky.engine.core.module.BasicModule;
import nl.jandt.blocky.engine.impl.event.EventService;
import nl.jandt.blocky.engine.impl.event.MinestomEventService;

public class MinestomModule extends BasicModule {
    @Override
    protected void setup() {
        registerService(PlayerService.class, new PlayerService());
        registerService(CommandService.class, new MinestomCommandService(MinecraftServer.getCommandManager()));
        registerService(EventService.class, new MinestomEventService(MinecraftServer.getGlobalEventHandler()));
    }
}
