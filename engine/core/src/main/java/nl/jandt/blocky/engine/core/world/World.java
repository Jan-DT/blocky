package nl.jandt.blocky.engine.core.world;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import nl.jandt.blocky.engine.core.Object;
import nl.jandt.blocky.engine.core.Toggleable;
import nl.jandt.blocky.engine.core.Updatable;
import nl.jandt.blocky.engine.core.WorldObject;
import nl.jandt.blocky.engine.core.server.Server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public class World extends Object implements Updatable {
    private final Server server;
    private final Collection<Object> objects = new ArrayList<>();
    private final EventNode<Event> eventNode = EventNode.all(getObjectId().toString());
    private final AtomicBoolean enabled = new AtomicBoolean(true);

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

    @Override
    public void _update() {
        objects.forEach(o -> {
            if (o instanceof Updatable) ((Updatable) o)._update();
        });
    }

    @Override
    public void setEnabled(boolean state) {
        enabled.set(state);
    }

    @Override
    public boolean isEnabled() {
        return enabled.get();
    }

    @Override
    public void _enable() {
        objects.forEach(o -> {
            if (o instanceof Toggleable) ((Toggleable) o)._enable();
        });
    }

    @Override
    public void _disable() {
        objects.forEach(o -> {
            if (o instanceof Toggleable) ((Toggleable) o)._disable();
        });
    }
}
