package nl.jandt.blocky.engine.core.trait;

import nl.jandt.blocky.engine.core.Container;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;


public interface Traitlike<C extends Container> extends Accessor {
    /** @hidden */
    @ApiStatus.Internal
    default void _setup(C container) {}

    /** @hidden */
    @ApiStatus.Internal
    default void _destroy() {}

    @Nullable C getContainer();
}
