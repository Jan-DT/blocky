package nl.jandt.block.example;

import nl.jandt.blocky.engine.core.Container;
import nl.jandt.blocky.engine.core.WorldObject;

public class Main {
    public static void main(String[] args) {
        final var container = new Container();

        container.addTrait(TestTrait.class);

        final var worldObject = new WorldObject(null);

        worldObject.addTrait(TestTrait.class);
    }
}
