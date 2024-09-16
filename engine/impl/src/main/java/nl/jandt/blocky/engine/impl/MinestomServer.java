package nl.jandt.blocky.engine.impl;

import net.minestom.server.MinecraftServer;
import nl.jandt.blocky.engine.core.module.Module;
import nl.jandt.blocky.engine.core.server.BasicServer;
import nl.jandt.blocky.engine.core.world.WorldManager;
import org.jetbrains.annotations.NotNull;

public class MinestomServer extends BasicServer {
    private final MinecraftServer minecraftServer;

    protected MinestomServer(MinecraftServer minecraftServer) {
        super();

        this.minecraftServer = minecraftServer;
    }

    public static @NotNull MinestomServer init(Module defaultModule) {
        final var minecraftServer = MinecraftServer.init();
        final var srv = new MinestomServer(minecraftServer);

        srv.getModuleManager().registerModule(defaultModule);

        return srv;
    }

    public MinecraftServer getMinecraftServer() {
        return this.minecraftServer;
    }

    @Override
    public void run(@NotNull String address, int port) {
        this.minecraftServer.start(address, port);
    }

    @Override
    public WorldManager getWorldManager() {
        return null;
    }
}
