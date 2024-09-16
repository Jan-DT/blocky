package nl.jandt.blocky.engine.core;

import nl.jandt.blocky.engine.core.trait.Behaviour;
import org.jetbrains.annotations.ApiStatus;

@SuppressWarnings("unused")
public interface Toggleable {
    /**
     * Toggles updates for this object. Disabled objects are not updated.
     * Whenever the state changes to enabled or disabled, {@link Toggleable#_enable()} or {@link Toggleable#_disable()}
     * are called respectively.
     *
     * @param state Whether to enable or disable updates on this object.
     * @see Toggleable#isEnabled()
     */
    void setEnabled(boolean state);

    /**
     * @return Whether this object is enabled (thus receiving updates).
     */
    boolean isEnabled();

    /**
     * This method is called whenever an object is enabled.
     * Thus, this is also called on initialization.
     * <p>
     * You probably should not call this!
     * This method is meant to be called by the engine internally.
     * In case of {@link Behaviour}, implement {@link Behaviour#onEnable()} instead.
     *
     * @see Behaviour#onEnable()
     */
    @ApiStatus.Internal
    default void _enable() {}

    /**
     * This method is called whenever an object is disabled from being enabled.
     * This means this method is not called on initialization of a disabled trait!
     * <p>
     * In most cases, you should not call this!
     * This method is meant to be called by the engine internally.
     * In case of {@link Behaviour}, implement {@link Behaviour#onDisable()} instead,
     * and use {@link Toggleable#setEnabled(boolean)} to disable or enable objects.
     *
     * @see Behaviour#onDisable()
     */
    @ApiStatus.Internal
    default void _disable() {}
}
