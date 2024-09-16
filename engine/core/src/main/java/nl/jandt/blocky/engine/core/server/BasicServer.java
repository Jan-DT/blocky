package nl.jandt.blocky.engine.core.server;

import nl.jandt.blocky.engine.core.module.SimpleModuleManager;
import org.jetbrains.annotations.NotNull;

public abstract class BasicServer implements Server {
    private final SimpleModuleManager moduleRegistry = new SimpleModuleManager(this);
    private final SimpleServiceManager serviceRegistry = new SimpleServiceManager(this);

    @Override
    public SimpleModuleManager getModuleManager() {
        return moduleRegistry;
    }

    @Override
    public SimpleServiceManager getServiceManager() {
        return serviceRegistry;
    }
}
