package nl.jandt.blocky.engine.impl.util;

@FunctionalInterface
public interface Serializer<From, To> {
    /**
     * Serialize the specified From class to the To class.
     */
    To serialize(From from);
}
