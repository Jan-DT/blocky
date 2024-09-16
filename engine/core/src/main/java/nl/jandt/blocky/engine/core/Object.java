package nl.jandt.blocky.engine.core;

import java.util.UUID;


public class Object implements Objectlike {
    private final UUID objectId;

    public Object(UUID objectId) {
        this.objectId = objectId;
    }

    public Object() {
        this(UUID.randomUUID());
    }

    public UUID getObjectId() {
        return objectId;
    }
}
