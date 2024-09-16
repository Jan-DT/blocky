package nl.jandt.blocky.engine.core.world;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import nl.jandt.blocky.engine.core.Object;
import nl.jandt.blocky.engine.core.WorldObject;
import nl.jandt.blocky.engine.core.server.Server;

import java.util.ArrayList;
import java.util.Collection;

public class World extends Object {
    private final Server server;
    private final Collection<Object> objects = new ArrayList<>();
    private final EventNode<Event> eventNode = EventNode.all(getObjectId().toString());

    public World(Server server) {
        this.server = server;
    }

    private void registerObject(Object object) {
        this.objects.add(object);

        if (object instanceof WorldObject worldObject) {
            this.eventNode().addChild(worldObject.eventNode());
        }
    }

    public WorldObject createObject() {
        final var object = new WorldObject(this);
        registerObject(object);
        return object;
    }

    public Server getServer() {
        return server;
    }

    public final EventNode<Event> eventNode() {
        return eventNode;
    }
}
