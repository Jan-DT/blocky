package nl.jandt.blocky.engine.core.trait;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import nl.jandt.blocky.engine.core.Container;
import nl.jandt.blocky.engine.core.Updatable;
import nl.jandt.blocky.engine.core.WorldObject;
import nl.jandt.blocky.engine.core.server.Server;
import nl.jandt.blocky.engine.core.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Behaviour extends Trait implements Updatable {
    private final AtomicBoolean enabled;
    private final boolean disableOnDestroy;

    public Behaviour() {
        this(true, true);
    }

    public Behaviour(boolean enabled) {
        this(enabled, true);
    }

    public Behaviour(boolean enabled, boolean disableOnDestroy) {
        this.enabled = new AtomicBoolean(enabled);
        this.disableOnDestroy = disableOnDestroy;
    }

    /** @hidden */
    @Override
    @ApiStatus.Internal
    public void _setup(Container container) {
        if (!(container instanceof WorldObject)) {
            throw new IllegalArgumentException("Container for Behaviour must be a WorldObject");
        }

        super._setup(container);
    }

    /** @hidden */
    @Override
    @ApiStatus.Internal
    public void _destroy() {
        if (disableOnDestroy) this._disable();

        super._destroy();
    }

    @Override
    public @NotNull WorldObject getContainer() {
        return (WorldObject) super.getContainer();
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

    public @NotNull World getWorld() {
        return getContainer().getWorld();
    }

    public @NotNull Server getServer() {
        return getWorld().getServer();
    }

    public @NotNull EventNode<Event> parentEventNode() {
        return getContainer().eventNode();
    }
}
