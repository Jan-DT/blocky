package nl.jandt.blocky.engine.core.world;

import java.util.Collection;

public interface WorldManager {
    <T extends World> void registerWorld(T world);
    <T extends World> void unregisterWorld(T world);

    <T extends World> T createWorld();

    <T extends World> Collection<T> getWorlds();
}
