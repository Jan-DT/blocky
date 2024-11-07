package nl.jandt.blocky.engine.core.trait;

import nl.jandt.blocky.engine.core.Container;
import nl.jandt.blocky.engine.core.WorldObject;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TraitTest {
    public static class SomeTrait extends Trait {}

    public static class SomeBehaviour extends Behaviour {
        int updates = 0;

        public SomeBehaviour() {
            super(false);
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

        container.tryAddTrait(SomeTrait.class);
        assertNotNull(container.getTrait(SomeTrait.class));

        container.tryAddTrait(SomeTrait.class);
        assertEquals(2, Objects.requireNonNull(container.getTraits(SomeTrait.class)).size());
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void testUpdate() {
        final var object = new WorldObject(null);

        object._update();
        object.tryAddTrait(SomeBehaviour.class);
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
        assertThrows(IllegalArgumentException.class, () -> new SomeBehaviour()._setup(container));

        class InvalidArgumentBehaviour extends Behaviour {
            @SuppressWarnings("unused")
            public InvalidArgumentBehaviour(int someNumber) {
                super();
            }
        }

        // if the constructor of the Behaviour is invalid,
        // an error should also be thrown
        assertThrows(NoSuchMethodException.class, () -> worldObject._initializeTrait(InvalidArgumentBehaviour.class));

        // as currently expected from the API,
        // exceptions should be logged but caught
        assertDoesNotThrow(() -> container.tryAddTrait(InvalidArgumentBehaviour.class));
    }
}
