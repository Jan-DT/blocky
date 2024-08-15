package nl.jandt.blocky.engine.core;

import org.jetbrains.annotations.ApiStatus;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Behaviour extends Trait implements Updatable {
    private final AtomicBoolean enabled = new AtomicBoolean(true);

    public Behaviour(Container container) {
        super(container);
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
}
