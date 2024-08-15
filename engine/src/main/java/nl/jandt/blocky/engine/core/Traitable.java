package nl.jandt.blocky.engine.core;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

@ApiStatus.Internal
public interface Traitable {
    /**
     * Get a reference to the first trait object connected to the {@link Container}, which has the specified type.
     * <p>
     * This should primarily be used when you are sure a trait exists on a {@link Container}.
     * If you are not sure, it is recommended to use {@link Traitable#tryGetTrait} over this method.
     *
     * @param trait Type of trait to return.
     * @return      The first trait of specified type found in the container, or {@code null} if none.
     * @see Traitable#tryGetTrait(Class)
     */
    <T extends Trait> @Nullable T getTrait(Class<T> trait);

    /**
     * Get a reference to the first trait object connected to the {@link Container}, which has the specified type.
     * <p>
     * Use this whenever you are not sure if a Trait is connected to this container,
     * and you want to explicitly check this. If you are sure the required trait is connected, you can use {@link Traitable#getTrait}.
     *
     * @param trait Type of trait to return.
     * @return      The first trait of specified type found in the container, as Optional value.
     * @see Traitable#getTrait(Class)
     */
    <T extends Trait> @NotNull Optional<T> tryGetTrait(Class<T> trait);

    /**
     * Get an array of trait objects connected to the {@link Container}, which have the specified type.
     * <p>
     * This should primarily be used when you are sure a trait exists on a {@link Container}.
     * If you are not sure, it is recommended to use {@link Traitable#tryGetTraits} over this method.
     *
     * @param trait Type of trait to return.
     * @return      A collection of trait objects of specified type found in the container, or {@code null} if none.
     * @see Traitable#tryGetTraits(Class)
     */
    <T extends Trait> @Nullable Collection<T> getTraits(Class<T> trait);

    /**
     * Get an array of trait objects connected to the {@link Container}, which have the specified type.
     * <p>
     * Use this whenever you are not sure if a Trait is connected to this container,
     * and you want to explicitly check this. If you are sure the required trait is connected, you can use {@link Traitable#getTrait}.
     *
     * @param trait Type of trait to return.
     * @return      A collection of trait objects of specified type found in the container, as Optional value.
     * @see Traitable#getTrait(Class)
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
