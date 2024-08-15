package nl.jandt.blocky.engine.core;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Container implements Traitable, Updatable {
    private static final Logger log = LoggerFactory.getLogger(Container.class);

    private final AtomicBoolean enabled = new AtomicBoolean(true);
    private final UUID objectId = UUID.randomUUID();

    private final EventNode<Event> eventNode = EventNode.all(objectId.toString());

    private final Map<Class<? extends Trait>, List<Trait>> traitMap = new ConcurrentHashMap<>();
    private final Collection<Trait> traitSet = new CopyOnWriteArrayList<>();

    /**
     * Adds a new trait object of type {@code trait} to this container.
     * <p>
     * This will instantiate a new object of the specified type,
     * thus requiring a constructor in the form:
     * <pre>
     *     public SomeComponent(Container container) {
     *         super(container);
     *     }
     * </pre>
     *
     * After initialization of an enabled behaviour object,
     * the {@link Behaviour#onEnable()} method will be called.
     *
     * @param trait Type of trait to add.
     */
    public final <T extends Trait> void addTrait(final @NotNull Class<T> trait) {
        try {
            // gets a constructor in shape Behaviour(Container)
            // in the future, dependency injection handled through this method
            final var traitObject = trait.getDeclaredConstructor(Container.class).newInstance(this);

            // we use size 1, as there is usually only one instance of a trait on an object,
            // though multiple are possible (at the cost of a capacity increase)
            traitMap.computeIfAbsent(trait, k -> new ArrayList<>(1))
                    .add(traitObject);
            // also add it to a set for bulk updating
            traitSet.add(traitObject);

            if (traitObject instanceof Toggleable toggledTrait) {
                if (toggledTrait.isEnabled()) {
                    toggledTrait._enable();
                }
            }
        } catch (NoSuchMethodException e) {
            log.error("Invalid constructor for trait '{}'. Trait must have a constructor in the form this(Container).", trait, e);
        } catch (Exception e) {
            log.error("Failed to add trait '{}' to container '{}'", trait, this, e);
        }
    }

    /** @hidden */
    @Override
    @ApiStatus.Internal
    public void _update() {
        if (!this.isEnabled()) return;

        getTraitCollection().stream()
                .filter(t -> (t instanceof Behaviour b) && b.isEnabled())
                .forEach(b -> ((Behaviour) b)._update());
    }

    @Override
    public void setEnabled(boolean state) {
        this.enabled.set(state);
    }

    @Override
    public boolean isEnabled() {
        return enabled.get();
    }

    public @NotNull EventNode<Event> getEventNode() {
        return eventNode;
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
    @ApiStatus.Internal
    protected final Collection<Trait> getTraitCollection() {
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
