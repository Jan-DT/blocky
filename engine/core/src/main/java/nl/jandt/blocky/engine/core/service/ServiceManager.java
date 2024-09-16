package nl.jandt.blocky.engine.core.service;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface ServiceManager {
    <T extends Service> void registerService(Class<T> clazz, T service);
    <T extends Service> void unregisterService(Class<T> clazz);

    <T extends Service> boolean hasService(Class<T> clazz);

    <T extends Service> @Nullable T getService(Class<T> clazz);

    default <T extends Service> Optional<T> tryGetService(Class<T> clazz) {
        return Optional.ofNullable(getService(clazz));
    }
}
