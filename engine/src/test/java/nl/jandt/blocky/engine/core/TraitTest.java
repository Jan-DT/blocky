package nl.jandt.blocky.engine.core;

import org.junit.jupiter.api.Test;

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

        SomeBehaviour(Container container) {
            super(container);

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
        final var container = new Container();

        container._update();
        container.addTrait(SomeBehaviour.class);
        assertEquals(0, container.getTrait(SomeBehaviour.class).updates);

        container._update();
        assertEquals(0, container.getTrait(SomeBehaviour.class).updates);

        container.getTrait(SomeBehaviour.class).setEnabled(true);

        container._update();
        assertEquals(1, container.getTrait(SomeBehaviour.class).updates);
    }
}