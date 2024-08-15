package nl.jandt.blocky.engine.core;

public class WorldObject extends Container {
    private final World world;

    public WorldObject(World world) {
        this.world = world;
    }

    public WorldObject() {
        this.world = null;
    }

    public World getWorld() {
        return world;
    }
}
