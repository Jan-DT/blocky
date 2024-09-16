package nl.jandt.blocky.engine.core.module;

public interface ModuleManager {
    <T extends Module> void registerModule(T module);
    <T extends Module> void unregisterModule(T module);

    <T extends Module> boolean hasModule(T module);
}
