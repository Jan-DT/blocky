package nl.jandt.blocky.engine.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents version bounds required for a {@link SemVer} version to be compatible.
 * @param lowerBound The version checked should be greater than or equal to this version.
 * @param upperBound The version should be less than this version.
 */
public record SemVerBounds(SemVer lowerBound, SemVer upperBound) {
    public static Pattern VALID_PATTERN = Pattern.compile("^(\\^|~|=|)(0|[1-9]\\d*)(?:\\.(0|[1-9]\\d*))?(?:\\.(0|[1-9]\\d*))?$");

    /**
     * Checks whether the specified {@link SemVer} version is compatible with these bounds.
     * @param version Version to check for compatibility.
     */
    public boolean isCompatible(@NotNull SemVer version) {
        return isCompatible(version, lowerBound, upperBound);
    }

    /**
     * Checks whether the specified {@link SemVer} version is compatible with the specified bounds.
     * @param check Version to check for compatibility.
     * @param lowerBound Lower version bound, which the check must be greater than or equal to.
     * @param upperBound Upper version bound, which the check must be less than.
     */
    public static boolean isCompatible(@NotNull SemVer check, @NotNull SemVer lowerBound, @NotNull SemVer upperBound) {
        return (check.isGreaterThan(lowerBound) || check.isEqualTo(lowerBound)) && check.isLessThan(upperBound);
    }

    /**
     * Parses a semantic version bounds string to a SemVerBounds object.
     * Bounds strings can use the following patterns:
     * <ul>
     * <li>EXACT: {@code "=*.*.*"} requires any version matching the bounds exactly, which means:
     *  <ul>
     *      <li>{@code "=1"} is {@code "1.0.0" - "1.0.1"}</li>
     *      <li>{@code "=1.1"} is {@code "1.1.0" - "1.1.1"}</li>
     *      <li>{@code "=1.1.1"} is {@code "1.1.1" - "1.1.2"}</li>
     *  </ul>
     * </li>
     * <li>MINIMAL: {@code "~*.*.*"} requires any version that guarantees compatibility,
     * based on the scope used, similar to Cargo's tilde requirements, which means:
     *  <ul>
     *      <li>{@code "~1"} is {@code "1.0.0" - "2.0.0"}</li>
     *      <li>{@code "~1.1"} is {@code "1.1.0" - "1.2.0"}</li>
     *      <li>{@code "~1.1.1"} is {@code "1.1.1" - "1.2.0"}</li>
     *  </ul>
     * </li>
     * <li>SEMVER: {@code "^1.1.1"} requires anything up to the next major version,
     * which means:
     *  <ul>
     *      <li>{@code "^1"} is {@code "1.0.0" - "2.0.0"}</li>
     *      <li>{@code "^1.1"} is {@code "1.1.0" - "2.0.0"}</li>
     *      <li>{@code "^1.1.1"} is {@code "1.1.1" - "2.0.0"}</li>
     *  </ul>
     * </li>
     * <li>When no scope is specified, one will be determined according to the following rules:
     *  <ul>
     *      <li>{@code "0.0.1"} will use the same rules as "EXACT", so versions below 0.1 can break at every patch</li>
     *      <li>{@code "0.1.0"} will use the same rules as "MINIMAL", so versions below 1.0 can break at minor updates</li>
     *      <li>{@code "1.0.0">} will use the same rules as "SEMVER", so versions above 1.0 can only break at major updates</li>
     *  </ul>
     * </li>
     * </ul>
     *
     * @param boundsPattern A bounds pattern, following one of the specified formats.
     * @throws IllegalArgumentException If the bounds pattern is invalid.
     */
    @Contract("_ -> new")
    public static @NotNull SemVerBounds from(String boundsPattern) throws IllegalArgumentException {
        final var matcher = VALID_PATTERN.matcher(boundsPattern);
        if (!matcher.matches()) throw new IllegalArgumentException("Invalid compatability string given: " + boundsPattern);

        final int major = Integer.parseInt(matcher.group(2));
        final @Nullable Integer minor = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : null;
        final @Nullable Integer patch = matcher.group(4) != null ? Integer.parseInt(matcher.group(4)) : null;

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

        // just trust the tests on this one...
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

    /**
     * Checks whether the specified bounds pattern is syntactically correct.
     */
    public static boolean isValid(String boundsPattern) {
        return VALID_PATTERN.matcher(boundsPattern).matches();
    }

    private enum Compatability {
        EXACT,
        MINIMAL,
        SEMVER,
    }
}
