package nl.jandt.blocky.engine.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class SemVerBoundsTest {
    @Test
    void fromExact() {
        {
            final var bounds = SemVerBounds.from("=1.1.1");
            assertEquals(SemVer.from("1.1.1"), bounds.lowerBound());
            assertEquals(SemVer.from("1.1.2"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("=1.1");
            assertEquals(SemVer.from("1.1.0"), bounds.lowerBound());
            assertEquals(SemVer.from("1.1.1"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("=1");
            assertEquals(SemVer.from("1.0.0"), bounds.lowerBound());
            assertEquals(SemVer.from("1.0.1"), bounds.upperBound());
        }
    }

    @Test
    void fromMinimal() {
        {
            final var bounds = SemVerBounds.from("~1.1.1");
            assertEquals(SemVer.from("1.1.1"), bounds.lowerBound());
            assertEquals(SemVer.from("1.2.0"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("~1.1");
            assertEquals(SemVer.from("1.1.0"), bounds.lowerBound());
            assertEquals(SemVer.from("1.2.0"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("~1");
            assertEquals(SemVer.from("1.0.0"), bounds.lowerBound());
            assertEquals(SemVer.from("2.0.0"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("~0.0.1");
            assertEquals(SemVer.from("0.0.1"), bounds.lowerBound());
            assertEquals(SemVer.from("0.1.0"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("~0.1");
            assertEquals(SemVer.from("0.1.0"), bounds.lowerBound());
            assertEquals(SemVer.from("0.2.0"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("~0");
            assertEquals(SemVer.from("0.0.0"), bounds.lowerBound());
            assertEquals(SemVer.from("1.0.0"), bounds.upperBound());
        }
    }

    @Test
    void fromSemver() {
        {
            final var bounds = SemVerBounds.from("^1.1.1");
            assertEquals(SemVer.from("1.1.1"), bounds.lowerBound());
            assertEquals(SemVer.from("2.0.0"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("^1.1");
            assertEquals(SemVer.from("1.1.0"), bounds.lowerBound());
            assertEquals(SemVer.from("2.0.0"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("^1");
            assertEquals(SemVer.from("1.0.0"), bounds.lowerBound());
            assertEquals(SemVer.from("2.0.0"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("^0.0.1");
            assertEquals(SemVer.from("0.0.1"), bounds.lowerBound());
            assertEquals(SemVer.from("1.0.0"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("^0.1");
            assertEquals(SemVer.from("0.1.0"), bounds.lowerBound());
            assertEquals(SemVer.from("1.0.0"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("^0");
            assertEquals(SemVer.from("0.0.0"), bounds.lowerBound());
            assertEquals(SemVer.from("1.0.0"), bounds.upperBound());
        }
    }

    @Test
    void fromDefault() {
        {
            final var bounds = SemVerBounds.from("1.2.3");
            assertEquals(SemVer.from("2.0.0"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("0.1.2");
            assertEquals(SemVer.from("0.2.0"), bounds.upperBound());
        }
        {
            final var bounds = SemVerBounds.from("0.0.1");
            assertEquals(SemVer.from("0.0.2"), bounds.upperBound());
        }
    }

    @Test
    void isCompatible() {
        // exact comparisons
        {
            final var bounds = SemVerBounds.from("=1.2.3");
            assertTrue(bounds.isCompatible(SemVer.from("1.2.3")));
        }
        {
            final var bounds = SemVerBounds.from("=1.2");
            assertTrue(bounds.isCompatible(SemVer.from("1.2.0")));
            assertFalse(bounds.isCompatible(SemVer.from("1.3.0")));
            assertFalse(bounds.isCompatible(SemVer.from("1.2.1")));
        }
        {
            final var bounds = SemVerBounds.from("=1");
            assertTrue(bounds.isCompatible(SemVer.from("1.0.0")));
            assertFalse(bounds.isCompatible(SemVer.from("1.1.0")));
            assertFalse(bounds.isCompatible(SemVer.from("1.0.1")));
        }

        // minimal comparisons
        {
            final var bounds = SemVerBounds.from("~1.2.3");
            assertTrue(bounds.isCompatible(SemVer.from("1.2.3")));
            assertTrue(bounds.isCompatible(SemVer.from("1.2.34")));
            assertFalse(bounds.isCompatible(SemVer.from("1.2.2")));
            assertFalse(bounds.isCompatible(SemVer.from("1.3.0")));
            assertFalse(bounds.isCompatible(SemVer.from("1.3.3")));
            assertFalse(bounds.isCompatible(SemVer.from("2.0.0")));
            assertFalse(bounds.isCompatible(SemVer.from("2.2.3")));
        }
    }
}