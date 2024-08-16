package nl.jandt.blocky.engine.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.regex.Pattern;

public record SemVerBounds(SemVer lowerBound, SemVer upperBound) {
    private static final Logger log = LoggerFactory.getLogger(SemVerBounds.class);
    public static Pattern VALID_PATTERN = Pattern.compile("^(\\^|~|=|)(0|[1-9]\\d*)(?:\\.(0|[1-9]\\d*))?(?:\\.(0|[1-9]\\d*))?$");

    public boolean isCompatible(@NotNull SemVer version) {
        return isCompatible(version, lowerBound, upperBound);
    }

    public static boolean isCompatible(@NotNull SemVer check, @NotNull SemVer lowerBound, @NotNull SemVer upperBound) {
        return (check.isGreaterThan(lowerBound) || check.isEqualTo(lowerBound)) && check.isLessThan(upperBound);
    }

    @Contract("_ -> new")
    public static @NotNull SemVerBounds from(String boundsPattern) throws IllegalArgumentException {
        final var matcher = VALID_PATTERN.matcher(boundsPattern);
        if (!matcher.matches()) throw new IllegalArgumentException("Invalid compatability string given: " + boundsPattern);

        final var major = Integer.parseInt(matcher.group(2));
        final var minor = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : null;
        final var patch = matcher.group(4) != null ? Integer.parseInt(matcher.group(4)) : null;

        // if no equality operator is specified, just like the Rust versioning system,
        // we choose an equality operator depending on the major or minor version:
        // in case of 1.*.*, we use SEMVER,
        // in case of 0.1.*, we use MINIMAL,
        // in case of 0.0.1, we use EXACT
        final Compatability equality = switch (matcher.group(1)) {
            case "=" -> Compatability.EXACT;
            case "~" -> Compatability.MINIMAL;
            case "^" -> Compatability.SEMVER;
            default -> {
                if (major == 1) yield Compatability.SEMVER;
                if (Objects.requireNonNullElse(minor, 0) >= 1)
                    yield Compatability.MINIMAL;
                yield Compatability.EXACT;
            }
        };

        // unspecified values are replaced with 0, as this means 1 becomes 1.0.0, and 1.1 becomes 1.1.0
        // major is never null, so it never has to be replaced
        final SemVer lowerBound = new SemVer(major,
                Objects.requireNonNullElse(minor, 0),
                Objects.requireNonNullElse(patch, 0));

        return switch (equality) {
            case EXACT -> new SemVerBounds(
                    lowerBound,
                    new SemVer(major,
                            Objects.requireNonNullElse(minor, 0),
                            Objects.requireNonNullElse(patch, 0) + 1)
            );
            case MINIMAL -> new SemVerBounds(
                    lowerBound,
                    new SemVer(minor == null ? major + 1 : major,
                            minor == null ? 0 : minor + 1,
                            0)
            );
            case SEMVER -> new SemVerBounds(
                    lowerBound,
                    new SemVer(major + 1, 0, 0)
            );
        };
    }

    public static boolean isValid(String boundsPattern) {
        return VALID_PATTERN.matcher(boundsPattern).matches();
    }

    private enum Compatability {
        EXACT,
        MINIMAL,
        SEMVER,
    }
}
