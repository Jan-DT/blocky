package nl.jandt.blocky.engine.core;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameObject {
    private final UUID objectId = UUID.randomUUID();

    public UUID getObjectId() {
        return objectId;
    }
}
