package nl.jandt.blocky.engine.core;

import nl.jandt.blocky.engine.core.trait.Behaviour;
import nl.jandt.blocky.engine.core.trait.Trait;
import nl.jandt.blocky.engine.core.trait.Accessor;
import nl.jandt.blocky.engine.core.trait.Traitlike;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


public class Container extends Object implements Accessor {
    private static final Logger log = LoggerFactory.getLogger(Container.class);

    protected final Map<Class<? extends Trait>, List<Trait>> traitMap = new ConcurrentHashMap<>();
    protected final Collection<Trait> traitSet = new CopyOnWriteArraySet<>();

    /**
     * Add a new trait object of type {@code trait} to this container.
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
     * @see Container#addTrait(Class)
     */
    public final <T extends Trait> Optional<T> tryAddTrait(final @NotNull Class<T> trait) {
        return Optional.ofNullable(addTrait(trait));
    }

    /**
     * Add a new trait object of type {@code trait} to this container.
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
     * @return      The trait object, which might be null if the creation failed.
     * @see Container#tryAddTrait(Class)
     */
    public final <T extends Trait> @Nullable T addTrait(final @NotNull Class<T> trait) {
        try {
            final var object = _initializeTrait(trait);

            // we use size 1, as there is usually only one instance of a trait on an object,
            // though multiple are possible (at the cost of a capacity increase)
            traitMap.computeIfAbsent(trait, k -> new ArrayList<>(1))
                    .add(object);
            // also add it to a set for bulk updating
            traitSet.add(object);

            // calls the setupTrait method for any subclasses of Container
            setupTrait(object);

            return object;

        } catch (NoSuchMethodException e) {
            log.error("Invalid constructor for trait '{}'. Trait must have a constructor in the form this(Container).", trait, e);
        } catch (InvocationTargetException e) {
            log.error("Failed to construct trait '{}' for container '{}'", trait, this, e);
        } catch (Exception e) {
            log.error("Failed to add trait '{}' to container '{}'", trait, this, e);
        }

        return null;
    }

    /**
     * Removes the specified trait from this container.
     * @param trait The trait object to remove.
     * @return Whether the specified trait was removed from this object.
     * If {@code false}, the trait probably was not present on the object in the first case.
     */
    public final <T extends Trait> boolean removeTrait(final @NotNull T trait) {
        if (!traitSet.contains(trait)) return false;

        destroyTrait(trait);

        // iterate over map values and remove any that match the specified trait
        // is there a way to do this more efficiently?
        traitMap.values().forEach(s -> s.remove(trait));
        // remove empty keys
        traitMap.entrySet().removeIf(e -> e.getValue().isEmpty());

        traitSet.remove(trait);
        return true;
    }

    @ApiStatus.OverrideOnly
    protected void setupTrait(Trait trait) {
        // nothing
    }

    @ApiStatus.OverrideOnly
    protected void destroyTrait(Trait trait) {
        // nothing
    }

    /** @hidden */
    @ApiStatus.Internal
    public final <T extends Traitlike<?>> @NotNull T _initializeTrait(@NotNull Class<T> trait) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // gets a constructor in shape Behaviour(Container)
        // in the future, dependency injection might be handled through this method
        return trait.getDeclaredConstructor().newInstance();
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

    //#region Accessor Implementations
    @SuppressWarnings("unchecked")
    @Override
    public final <T extends Trait> @Nullable T getTrait(Class<T> trait) {
        return (T) Optional.ofNullable(traitMap.get(trait))
                .map(t -> t.isEmpty() ? null : t.getFirst())
                .orElse(null);
    }

    @Override
    public final @NotNull <T extends Trait> Optional<T> tryGetTrait(Class<T> trait) {
        return Optional.ofNullable(getTrait(trait));
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <T extends Trait> @Nullable Collection<T> getTraits(Class<T> trait) {
        // this method returns a Collection, not a List,
        // as I don't want to give any promises about ordering at this point
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
