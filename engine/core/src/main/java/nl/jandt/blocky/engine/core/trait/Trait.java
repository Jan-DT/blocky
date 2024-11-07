package nl.jandt.blocky.engine.core.trait;

import nl.jandt.blocky.engine.core.Container;
import nl.jandt.blocky.engine.core.Object;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

public abstract class Trait extends Object implements Traitlike<Container> {
    protected @Nullable Container container;

    public Trait() {}

    @Override
    public void _setup(Container container) {
        this.container = container;

        onSetup();
    }

    @ApiStatus.OverrideOnly
    protected void onSetup() {
        // nothing
    }

    @Override
    public void _destroy() {
        onDestroy();
    }

    @ApiStatus.OverrideOnly
    public void onDestroy() {
        // nothing
    }

    @Override
    public @NotNull Container getContainer() {
        if (this.container == null) {
            throw new IllegalStateException("Use of container before setup");
        }

        return this.container;
    }

    //region Traitable Implementations
    @Override
    public final <T extends Trait> @Nullable T getTrait(Class<T> trait) {
        return container != null ? container.getTrait(trait) : null;
    }

    @Override
    public final @NotNull <T extends Trait> Optional<T> tryGetTrait(Class<T> trait) {
        return container != null ? container.tryGetTrait(trait) : Optional.empty();
    }

    @Override
    public final <T extends Trait> @Nullable Collection<T> getTraits(Class<T> trait) {
        return container != null ? container.getTraits(trait) : null;
    }

    @Override
    public final @NotNull <T extends Trait> Optional<Collection<T>> tryGetTraits(Class<T> trait) {
        return container != null ? container.tryGetTraits(trait) : Optional.empty();
    }

    @Override
    public final <T extends Trait> boolean hasTrait(Class<T> trait) {
        return container != null && container.hasTrait(trait);
    }
    //endregion
}
