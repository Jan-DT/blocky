package nl.jandt.blocky.engine.module;

import java.util.List;

@FunctionalInterface
public interface ModuleLoader {
    List<LoadedModule> loadModules();
}
