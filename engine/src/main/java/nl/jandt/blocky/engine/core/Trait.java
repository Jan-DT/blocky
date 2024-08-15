package nl.jandt.blocky.engine.core;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

public abstract class Trait extends GameObject implements Traitable {
    public static final Class<Container> CONTAINER_TYPE = Container.class;

    protected final Container container;

    public Trait(Container container) {
        this.container = container;
    }

    @SuppressWarnings("unused")
    public Container getContainer() {
        return container;
    }

    //region Traitable Implementations
    @Override
    public <T extends Trait> @Nullable T getTrait(Class<T> trait) {
        return container.getTrait(trait);
    }

    @Override
    public @NotNull <T extends Trait> Optional<T> tryGetTrait(Class<T> trait) {
        return container.tryGetTrait(trait);
    }

    @Override
    public <T extends Trait> @Nullable Collection<T> getTraits(Class<T> trait) {
        return container.getTraits(trait);
    }

    @Override
    public @NotNull <T extends Trait> Optional<Collection<T>> tryGetTraits(Class<T> trait) {
        return container.tryGetTraits(trait);
    }

    @Override
    public <T extends Trait> boolean hasTrait(Class<T> trait) {
        return container.hasTrait(trait);
    }
    //endregion
}
