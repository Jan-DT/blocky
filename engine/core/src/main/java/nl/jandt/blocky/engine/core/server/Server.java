package nl.jandt.blocky.engine.core.server;

import nl.jandt.blocky.engine.core.module.ModuleManager;
import nl.jandt.blocky.engine.core.service.ServiceManager;
import nl.jandt.blocky.engine.core.world.WorldManager;
import org.jetbrains.annotations.NotNull;

public interface Server {
    void run(@NotNull String address, int port);

    WorldManager getWorldManager();

    ModuleManager getModuleManager();

    ServiceManager getServiceManager();
}
