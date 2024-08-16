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
        implements Comparable<SemVer>
{
    public static final Pattern VALID_PATTERN = Pattern.compile("(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?$");

    public SemVer(int major, int minor, int patch) {
        this(major, minor, patch, null, null);
    }

    public SemVer(int major, int minor, int patch, String prerelease) {
        this(major, minor, patch, prerelease, null);
    }

    public boolean isGreaterThan(SemVer version) {
        return greaterThan(this, version);
    }

    public boolean isLessThan(SemVer version) {
        return lessThan(this, version);
    }

    public boolean isEqualTo(SemVer version) {
        return equals(this, version);
    }

    public boolean isPreRelease() {
        return prerelease != null;
    }

    @Contract(pure = true)
    public @NotNull String toString() {
        if (prerelease == null && build == null) return "%s.%s.%s".formatted(major, minor, patch);
        if (prerelease == null) return "%s.%s.%s+%s".formatted(major, minor, patch, build);
        if (build == null) return "%s.%s.%s-%s".formatted(major, minor, patch, prerelease);
        return "%s.%s.%s-%s+%s".formatted(major, minor, patch, prerelease, build);
    }

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

    public static boolean greaterThan(@NotNull SemVer version1, @NotNull SemVer version2) {
        return (version1.major() > version2.major())
                || (version1.minor() > version2.minor()
                && version1.major() == version2.major())
                || (version1.patch() > version2.patch()
                && version1.minor() == version2.minor()
                && version1.major() == version2.major());
    }

    public static boolean lessThan(@NotNull SemVer version1, @NotNull SemVer version2) {
        return (version1.major() < version2.major())
                || (version1.minor() < version2.minor()
                && version1.major() == version2.major())
                || (version1.patch() < version2.patch()
                && version1.minor() == version2.minor()
                && version1.major() == version2.major());
    }

    public static boolean equals(@NotNull SemVer version1, @NotNull SemVer version2) {
        return version1.equals(version2);
    }

    public static boolean isValid(String versionString) {
        return VALID_PATTERN.matcher(versionString).matches();
    }

    @Override
    public int compareTo(@NotNull SemVer version) {
        if (isLessThan(version)) return -1;
        if (isGreaterThan(version)) return 1;
        return 0;
    }
}
