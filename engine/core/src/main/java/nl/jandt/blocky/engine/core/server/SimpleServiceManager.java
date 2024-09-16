package nl.jandt.blocky.engine.core.server;

import nl.jandt.blocky.engine.core.service.Service;
import nl.jandt.blocky.engine.core.service.ServiceManager;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class SimpleServiceManager implements ServiceManager {
    private final HashMap<Class<?>, Object> services = new HashMap<>();
    private final Server server;

    public SimpleServiceManager(Server server) {
        this.server = server;
    }

    @Override
    public <T extends Service> void registerService(Class<T> clazz, T service) {
        services.put(clazz, service);
    }

    @Override
    public <T extends Service> void unregisterService(Class<T> clazz) {
        services.remove(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Service> @Nullable T getService(Class<T> clazz) {
        return (T) services.get(clazz);
    }

    @Override
    public <T extends Service> boolean hasService(Class<T> clazz) {
        return services.containsKey(clazz);
    }
}
