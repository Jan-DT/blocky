package nl.jandt.blocky.engine.core.module;

import nl.jandt.blocky.engine.core.server.Server;
import nl.jandt.blocky.engine.core.service.Service;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;

public abstract class BasicModule implements Module {
    private Server server;

    @MustBeInvokedByOverriders
    @Override
    public void initialize(@NotNull Server server) {
        this.server = server;

        setup();
    }

    @ApiStatus.OverrideOnly
    protected void setup() { }

    @Override
    public Server getServer() {
        return server;
    }

    @SuppressWarnings("UnusedReturnValue")
    public <T extends Service> BasicModule registerService(Class<T> clazz, T service) {
        server.getServiceManager().registerService(clazz, service);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public <T extends Service> BasicModule unregisterService(Class<T> clazz) {
        server.getServiceManager().unregisterService(clazz);
        return this;
    }

    public <T extends Service> boolean hasService(Class<T> clazz) {
        return server.getServiceManager().hasService(clazz);
    }

    public <T extends Service> T getService(Class<T> clazz) {
        return server.getServiceManager().getService(clazz);
    }

    @SuppressWarnings("UnusedReturnValue")
    public <T extends Module> BasicModule registerModule(T module) {
        server.getModuleManager().registerModule(module);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public <T extends Module> BasicModule unregisterModule(T module) {
        server.getModuleManager().unregisterModule(module);
        return this;
    }

    public <T extends Module> boolean hasModule(T module) {
        return server.getModuleManager().hasModule(module);
    }

}
