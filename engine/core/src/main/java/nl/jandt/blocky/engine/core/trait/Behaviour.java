package nl.jandt.blocky.engine.core.trait;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import nl.jandt.blocky.engine.core.Container;
import nl.jandt.blocky.engine.core.Updatable;
import nl.jandt.blocky.engine.core.WorldObject;
import nl.jandt.blocky.engine.core.server.Server;
import nl.jandt.blocky.engine.core.world.World;
import org.jetbrains.annotations.ApiStatus;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Behaviour extends Trait implements Updatable {
    private final AtomicBoolean enabled = new AtomicBoolean(true);

    public Behaviour(Container container) {
        super(container);

        if (!(container instanceof WorldObject))
            throw new IllegalArgumentException("Behaviour traits can only be added to WorldObjects");
    }

    public Behaviour(Container container, boolean enabled) {
        this(container);

        this.enabled.set(enabled);
    }

    /**
     * This method is called every tick. Updating only happens when the object is enabled.
     *
     * @see Behaviour#setEnabled(boolean)
     */
    @ApiStatus.OverrideOnly
    protected void onUpdate() {
        // nothing
    }

    /** @hidden */
    @Override
    @ApiStatus.Internal
    public final void _update() {
        onUpdate();
    }

    /**
     * This method is called whenever this object is enabled.
     * This happens once at initialization, if spawned as enabled,
     * and whenever {@link Behaviour#setEnabled} is used to enable this object.
     *
     * @see Behaviour#onDisable()
     * @see Behaviour#setEnabled(boolean)
     * @see Behaviour#isEnabled()
     */
    @ApiStatus.OverrideOnly
    protected void onEnable() {
        // nothing
    }

    /** @hidden */
    @Override
    @ApiStatus.Internal
    public final void _enable() {
        onEnable();
    }

    /**
     * This method is called whenever this object is disabled.
     * This happens once at initialization, if spawned as disabled,
     * and whenever {@link Behaviour#setEnabled} is used to disable this object.
     *
     * @see Behaviour#onEnable()
     * @see Behaviour#setEnabled(boolean)
     * @see Behaviour#isEnabled()
     */
    @ApiStatus.OverrideOnly
    protected void onDisable() {
        // nothing
    }

    /** @hidden */
    @Override
    @ApiStatus.Internal
    public final void _disable() {
        onDisable();
    }

    @Override
    public final void setEnabled(boolean state) {
        this.enabled.set(state);
    }

    @Override
    public final boolean isEnabled() {
        return this.enabled.get();
    }

    @Override
    public WorldObject getContainer() {
        return (WorldObject) container;
    }

    public World getWorld() {
        return getContainer().getWorld();
    }

    public Server getServer() {
        return getWorld().getServer();
    }

    public EventNode<Event> parentEventNode() {
        return getContainer().eventNode();
    }
}
