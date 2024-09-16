package nl.jandt.blocky.engine.impl.module;

import java.util.List;

@FunctionalInterface
public interface ModuleLoader {
    List<LoadedModule> loadModules();
}
