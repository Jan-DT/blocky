package nl.jandt.blocky.engine.core.trait;

import nl.jandt.blocky.engine.core.Container;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

@ApiStatus.Internal
public interface Accessor {
    // probably have to make up a better name for this

    /**
     * Get a reference to the first trait object connected to the {@link Container}, which has the specified type.
     * <p>
     * This should primarily be used when you are sure a trait exists on a {@link Container}.
     * If you are not sure, it is recommended to use {@link Accessor#tryGetTrait} over this method.
     *
     * @param trait Type of trait to return.
     * @return      The first trait of specified type found in the container, or {@code null} if none.
     * @see Accessor#tryGetTrait(Class)
     */
    <T extends Trait> @Nullable T getTrait(Class<T> trait);

    /**
     * Get a reference to the first trait object connected to the {@link Container}, which has the specified type.
     * <p>
     * Use this whenever you are not sure if a Trait is connected to this container,
     * and you want to explicitly check this. If you are sure the required trait is connected, you can use {@link Accessor#getTrait}.
     *
     * @param trait Type of trait to return.
     * @return      The first trait of specified type found in the container, as Optional value.
     * @see Accessor#getTrait(Class)
     */
    <T extends Trait> @NotNull Optional<T> tryGetTrait(Class<T> trait);

    /**
     * Get an array of trait objects connected to the {@link Container}, which have the specified type.
     * <p>
     * This should primarily be used when you are sure a trait exists on a {@link Container}.
     * If you are not sure, it is recommended to use {@link Accessor#tryGetTraits} over this method.
     *
     * @param trait Type of trait to return.
     * @return      A collection of trait objects of specified type found in the container, or {@code null} if none.
     * @see Accessor#tryGetTraits(Class)
     */
    <T extends Trait> @Nullable Collection<T> getTraits(Class<T> trait);

    /**
     * Get an array of trait objects connected to the {@link Container}, which have the specified type.
     * <p>
     * Use this whenever you are not sure if a Trait is connected to this container,
     * and you want to explicitly check this. If you are sure the required trait is connected, you can use {@link Accessor#getTrait}.
     *
     * @param trait Type of trait to return.
     * @return      A collection of trait objects of specified type found in the container, as Optional value.
     * @see Accessor#getTrait(Class)
     */
    <T extends Trait> @NotNull Optional<Collection<T>> tryGetTraits(Class<T> trait);

    /**
     * Returns whether the passed in {@code trait} is connected to the {@link Container}.
     *
     * @param trait Type of trait to return.
     * @return      Whether a trait of the specified type is connected.
     */
    <T extends Trait> boolean hasTrait(Class<T> trait);
}
