package nl.jandt.blocky.engine.core.trait;

import nl.jandt.blocky.engine.core.Container;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;


public interface Traitlike<C extends Container> extends Accessor {
    @ApiStatus.Internal
    default void _setup(C container) {}

    @Nullable C getContainer();
}
