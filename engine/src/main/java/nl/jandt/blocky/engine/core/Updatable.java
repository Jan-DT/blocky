package nl.jandt.blocky.engine.core;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface Updatable extends Toggleable {
    /**
     * This method is called on every update on an enabled object.
     * <p>
     * In most cases, you should not call this!
     * This method is meant to be called by the engine internally.
     * In case of {@link Behaviour}, implement {@link Behaviour#onUpdate()} instead.
     *
     * @see Behaviour#onUpdate()
     */
    @ApiStatus.Internal
    default void _update() {}
}
