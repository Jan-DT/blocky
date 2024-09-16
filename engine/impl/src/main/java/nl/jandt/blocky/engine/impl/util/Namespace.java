package nl.jandt.blocky.engine.impl.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class Namespace implements Identifier {
    public static final String VALID_PATTERN_STRING = "[0123456789abcdefghijklmnopqrstuvwxyz_-]+";

    private final String namespace;

    private Namespace(String namespace) {
        this.namespace = namespace;
    }

    public boolean isValid() {
        return isValid(namespace);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Namespace of(String namespace) throws IllegalArgumentException {
        if (!isValid(namespace)) throw new IllegalArgumentException("Illegal namespace: " + namespace);
        return new Namespace(namespace);
    }

    @Contract(pure = true)
    public static boolean isValid(final @NotNull String name) {
        return name.matches(VALID_PATTERN_STRING);
    }

    public String toString() {
        return namespace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Namespace namespace1 = (Namespace) o;
        return Objects.equals(namespace, namespace1.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(namespace);
    }

    public static class SerializeString implements Serializer<Namespace, String> {
        @Override
        public String serialize(@NotNull Namespace from) {
            return from.toString();
        }
    }

    public static class DeserializeString implements Serializer<String, Namespace> {
        @Override
        public Namespace serialize(String from) {
            return Namespace.of(from);
        }
    }
}
