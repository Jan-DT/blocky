package nl.jandt.blocky.engine.impl.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SemVerTest {

    @Test
    void from() {
        final var test1 = SemVer.from("0.1.2-beta.1+build.123");
        assertEquals(test1.major(), 0);
        assertEquals(test1.minor(), 1);
        assertEquals(test1.patch(), 2);
        assertEquals(test1.prerelease(), "beta.1");
        assertEquals(test1.build(), "build.123");
        assertEquals(test1.toString(), "0.1.2-beta.1+build.123");

        assertThrows(IllegalArgumentException.class, () -> {
            SemVer.from("shouldnt match");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            SemVer.from("0.1.2.3");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            SemVer.from("0.1-no-patch");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            SemVer.from("0.1+no-patch");
        });
    }
}