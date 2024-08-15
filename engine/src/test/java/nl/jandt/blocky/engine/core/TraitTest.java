package nl.jandt.blocky.engine.core;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TraitTest {
    static class SomeTrait extends Trait {
        SomeTrait(Container container) {
            super(container);
        }
    }

    static class SomeBehaviour extends Behaviour {
        int updates = 0;

        SomeBehaviour(Container object) {
            super(object);

            setEnabled(false);
        }

        @Override
        protected void onUpdate() {
            updates++;
        }
    }

    @Test
    void testContainer() {
        final var container = new Container();
        assertFalse(container.hasTrait(SomeTrait.class));

        container.addTrait(SomeTrait.class);
        assertNotNull(container.getTrait(SomeTrait.class));

        container.addTrait(SomeTrait.class);
        assertEquals(2, Objects.requireNonNull(container.getTraits(SomeTrait.class)).size());
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void testUpdate() {
        final var object = new WorldObject(null);

        object._update();
        object.addTrait(SomeBehaviour.class);
        assertEquals(0, object.getTrait(SomeBehaviour.class).updates);

        object._update();
        assertEquals(0, object.getTrait(SomeBehaviour.class).updates);

        object.getTrait(SomeBehaviour.class).setEnabled(true);

        object._update();
        assertEquals(1, object.getTrait(SomeBehaviour.class).updates);
    }

    @Test
    void testInvalidConstructor() {
        final var container = new Container();
        final var worldObject = new WorldObject(null);

        // a Behaviour should only accept WorldObjects
        assertThrows(IllegalArgumentException.class, () -> {
            new SomeBehaviour(container);
        });

        // thus attempting to add a Behaviour to a regular Container should throw
        assertThrows(InvocationTargetException.class, () -> {
            container._initializeTrait(SomeBehaviour.class);
        });

        class InvalidArgumentBehaviour extends Behaviour {
            public InvalidArgumentBehaviour(Container container, int someNumber) {
                super(container);
            }
        }

        // if the constructor of the Behaviour is invalid,
        // an error should also be thrown
        assertThrows(NoSuchMethodException.class, () -> worldObject._initializeTrait(InvalidArgumentBehaviour.class));

        // as currently expected from the API,
        // exceptions should be logged but caught
        assertDoesNotThrow(() -> container.addTrait(InvalidArgumentBehaviour.class));
    }
}