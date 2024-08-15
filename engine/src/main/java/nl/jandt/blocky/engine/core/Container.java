package nl.jandt.blocky.engine.core;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class Container extends GameObject implements Traitable {
    private static final Logger log = LoggerFactory.getLogger(Container.class);

    protected final Map<Class<? extends Trait>, List<Trait>> traitMap = new ConcurrentHashMap<>();
    protected final Collection<Trait> traitSet = new CopyOnWriteArrayList<>();

    /**
     * Try to add a new trait object of type {@code trait} to this container.
     * <p>
     * This will instantiate a new object of the specified type,
     * thus requiring a constructor in the form:
     * <pre>
     *     public SomeComponent(Container container) {
     *         super(container);
     *     }
     * </pre>
     *
     * @param trait Type of trait to add.
     * @return      The optional trait object, which is {@link Optional#empty()} if the creation failed.
     */
    public final <T extends Trait> Optional<T> addTrait(final @NotNull Class<T> trait) {
        try {
            final var object = _initializeTrait(trait);

            // we use size 1, as there is usually only one instance of a trait on an object,
            // though multiple are possible (at the cost of a capacity increase)
            traitMap.computeIfAbsent(trait, k -> new ArrayList<>(1))
                    .add(object);
            // also add it to a set for bulk updating
            traitSet.add(object);

            setupTrait(object);

            return Optional.of(object);
        } catch (NoSuchMethodException e) {
            log.error("Invalid constructor for trait '{}'. Trait must have a constructor in the form this(Container).", trait, e);
        } catch (InvocationTargetException e) {
            log.error("Failed to construct trait '{}' for container '{}'", trait, this, e);
        } catch (Exception e) {
            log.error("Failed to add trait '{}' to container '{}'", trait, this, e);
        }

        return Optional.empty();
    }

    @ApiStatus.OverrideOnly
    protected void setupTrait(Trait trait) {
        // nothing
    }

    /** @hidden */
    @ApiStatus.Internal
    final <T extends Trait> @NotNull T _initializeTrait(@NotNull Class<T> trait) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // gets a constructor in shape Behaviour(Container)
        // in the future, dependency injection handled through this method
        return trait.getDeclaredConstructor(Container.class).newInstance(this);
    }

    /**
     * @return The amount of trait objects connected to this container.
     */
    @SuppressWarnings("unused")
    public final int getTraitCount() {
        return traitSet.size();
    }

    /**
     * @return A collection of all trait objects connected to this container.
     */
    @ApiStatus.Experimental
    public final Collection<Trait> getTraitCollection() {
        return traitSet;
    }

    //#region Traitable Implementations
    @SuppressWarnings("unchecked")
    @Override
    public final <T extends Trait> @Nullable T getTrait(Class<T> trait) {
        return (T) traitMap.get(trait).getFirst();
    }

    @Override
    public final @NotNull <T extends Trait> Optional<T> tryGetTrait(Class<T> trait) {
        return Optional.ofNullable(getTrait(trait));
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <T extends Trait> @Nullable Collection<T> getTraits(Class<T> trait) {
        // this method returns a Collection, not a List,
        // as I don't want to give any promises about ordering without stuff being stable
        final var result = traitMap.get(trait);
        return (result != null && !result.isEmpty()) ? (Collection<T>) result : null;
    }

    @Override
    public final @NotNull <T extends Trait> Optional<Collection<T>> tryGetTraits(Class<T> trait) {
        return Optional.ofNullable(getTraits(trait));
    }

    @Override
    public final <T extends Trait> boolean hasTrait(Class<T> trait) {
        return traitMap.containsKey(trait);
    }
    //#endregion
}
