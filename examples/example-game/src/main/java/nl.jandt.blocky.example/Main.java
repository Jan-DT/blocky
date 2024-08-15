package nl.jandt.blocky.example;

import nl.jandt.blocky.engine.core.Container;

public class Main {
    public static void main(String[] args) {
        final var object = new Container();

        object.addTrait(PlayerFlight.class);
    }
}
