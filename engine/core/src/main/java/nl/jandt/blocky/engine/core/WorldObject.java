package nl.jandt.blocky.engine.core;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import nl.jandt.blocky.engine.core.trait.Behaviour;
import nl.jandt.blocky.engine.core.trait.Trait;
import nl.jandt.blocky.engine.core.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class WorldObject extends Container implements Updatable {
    private static final Logger log = LoggerFactory.getLogger(WorldObject.class);
    private final World world;
    private final EventNode<Event> eventNode = EventNode.all(getObjectId().toString());

    protected final AtomicBoolean enabled = new AtomicBoolean(true);

    public WorldObject(@Nullable World world) {
        this.world = world;
    }

    /** @hidden */
    @Override
    @ApiStatus.Internal
    public void _update() {
        if (!this.isEnabled()) return;

        getTraitCollection().stream()
                .filter(t -> (t instanceof Behaviour b) && b.isEnabled())
                .forEach(b -> ((Behaviour) b)._update());
    }

    @Override
    protected void setupTrait(Trait trait) {
        super.setupTrait(trait);

        if (trait instanceof Toggleable toggled && toggled.isEnabled())
            // TODO: some other way of scheduling (own scheduler, also for ticks?)
            MinecraftServer.getSchedulerManager()
                    .scheduleEndOfTick(() -> ((Toggleable) trait)._enable());
    }

    @Override
    public void setEnabled(boolean state) {
        this.enabled.set(state);
    }

    @Override
    public boolean isEnabled() {
        return enabled.get();
    }

    public World getWorld() {
        return world;
    }

    public @NotNull EventNode<Event> eventNode() {
        return eventNode;
    }
}
