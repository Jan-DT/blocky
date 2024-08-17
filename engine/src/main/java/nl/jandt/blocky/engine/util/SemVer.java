package nl.jandt.blocky.engine.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * This record represents a Semantic Version.
 * @param major
 * @param minor
 * @param patch
 * @param prerelease
 * @param build
 */
public record SemVer(int major, int minor, int patch, @Nullable String prerelease, @Nullable String build)
        implements Comparable<SemVer>, Identifier
{
    /**
     * The Regex pattern representing the official Semantic Version format. Use {@link SemVer#VALID_PATTERN} for a precompiled {@link Pattern}.
     */
    public static final String VALID_PATTERN_STRING = "(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?$";
    public static final Pattern VALID_PATTERN = Pattern.compile(VALID_PATTERN_STRING);

    public SemVer(int major, int minor, int patch) {
        this(major, minor, patch, null, null);
    }

    public SemVer(int major, int minor, int patch, String prerelease) {
        this(major, minor, patch, prerelease, null);
    }

    /**
     * Checks whether this version is greater than the specified version.
     */
    public boolean isGreaterThan(SemVer version) {
        return greaterThan(this, version);
    }

    /**
     * Checks whether this version is less than the specified version.
     */
    public boolean isLessThan(SemVer version) {
        return lessThan(this, version);
    }

    /**
     * Checks whether this exactly equals the specified version.
     */
    public boolean isEqualTo(SemVer version) {
        return equals(this, version);
    }

    /**
     * Checks whether any pre-release part is specified.
     * <p>
     * The pre-release is the part after the '{@code -}' but before the '{@code +}', so in case of {@code 1.0.1-beta+build.2},
     * the pre-release is 'beta'.
     */
    public boolean isPreRelease() {
        return prerelease != null;
    }

    /**
     * Always returns {@code true}, as any instance of SemVer is guaranteed to be valid.
     */
    public boolean isValid() {
        return true;
    }

    /**
     * Returns the String representation of this version.
     * Should exactly equal the version passed to {@link SemVer#from(String)}.
     *
     * @return The string value of the version.
     */
    @Contract(pure = true)
    public @NotNull String toString() {
        if (prerelease == null && build == null) return "%s.%s.%s".formatted(major, minor, patch);
        if (prerelease == null) return "%s.%s.%s+%s".formatted(major, minor, patch, build);
        if (build == null) return "%s.%s.%s-%s".formatted(major, minor, patch, prerelease);
        return "%s.%s.%s-%s+%s".formatted(major, minor, patch, prerelease, build);
    }

    /**
     * Parses a Semantic Version string into a SemVer object.
     *
     * @param versionString A (valid) version string, like {@code 1.2.3-alpha+some.build.4}.
     *                      Note that this version should not be prefixed by 'v',
     *                      as that is technically an invalid Semantic Version!
     * @throws IllegalArgumentException If the specified string is not a valid Semantic Version.
     */
    @Contract("_ -> new")
    public static @NotNull SemVer from(String versionString) throws IllegalArgumentException {
        final var matcher = VALID_PATTERN.matcher(versionString);
        if (!matcher.matches())
            throw new IllegalArgumentException("Invalid SemVer-version specified: " + versionString);

        final var major = Integer.decode(matcher.group(1));
        final var minor = Integer.decode(matcher.group(2));
        final var patch = Integer.decode(matcher.group(3));
        final var prerelease = matcher.group(4);
        final var build = matcher.group(5);

        return new SemVer(major, minor, patch, prerelease, build);
    }

    /**
     * @return Whether {@code version1 > version2}.
     */
    public static boolean greaterThan(@NotNull SemVer version1, @NotNull SemVer version2) {
        return (version1.major() > version2.major())
                || (version1.minor() > version2.minor()
                && version1.major() == version2.major())
                || (version1.patch() > version2.patch()
                && version1.minor() == version2.minor()
                && version1.major() == version2.major());
    }

    /**
     * @return Whether {@code version1 < version2}.
     */
    public static boolean lessThan(@NotNull SemVer version1, @NotNull SemVer version2) {
        return (version1.major() < version2.major())
                || (version1.minor() < version2.minor()
                && version1.major() == version2.major())
                || (version1.patch() < version2.patch()
                && version1.minor() == version2.minor()
                && version1.major() == version2.major());
    }

    /**
     * @return Whether {@code version1 == version2}.
     */
    public static boolean equals(@NotNull SemVer version1, @NotNull SemVer version2) {
        return version1.equals(version2);
    }

    /**
     * @param versionString A version string, like {@code 1.2.3-alpha+some.build.4}
     * @return Whether the specified version string is a valid SemVer.
     */
    @Contract(pure = true)
    public static boolean isValid(@NotNull String versionString) {
        return versionString.matches(VALID_PATTERN_STRING);
    }

    @Override
    public int compareTo(@NotNull SemVer version) {
        if (isLessThan(version)) return -1;
        if (isGreaterThan(version)) return 1;
        return 0;
    }

    public static class SerializeString implements Serializer<SemVer, String> {
        @Override
        public String serialize(@NotNull SemVer fromVersion) {
            return fromVersion.toString();
        }
    }

    public static class DeserializeString implements Serializer<String, SemVer> {
        @Override
        public SemVer serialize(String versionString) {
            return SemVer.from(versionString);
        }
    }
}
