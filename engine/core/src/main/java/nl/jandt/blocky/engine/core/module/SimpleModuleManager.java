package nl.jandt.blocky.engine.core.module;

import nl.jandt.blocky.engine.core.server.Server;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleModuleManager implements ModuleManager {
    private final Collection<Object> modules = new ArrayList<>();
    private final Server server;

    public SimpleModuleManager(Server server) {
        this.server = server;
    }

    @Override
    public <T extends Module> void registerModule(T module) {
        modules.add(module);
        module.initialize(server);
    }

    @Override
    public <T extends Module> void unregisterModule(T module) {
        modules.remove(module);
    }

    @Override
    public <T extends Module> boolean hasModule(T module) {
        return modules.contains(module);
    }
}
