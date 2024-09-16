package nl.jandt.blocky.engine.impl.annotation;

import nl.jandt.blocky.engine.impl.util.Namespace;
import nl.jandt.blocky.engine.impl.util.SemVer;
import nl.jandt.blocky.engine.impl.util.SemVerBounds;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface BlockyModule {
    /**
     * Unique identifier used by this module.
     * Identifiers must follow the {@link Namespace#VALID_PATTERN_STRING} regex pattern.
     * <p>
     * This will also be used as default namespace for any objects,
     * scripts and assets defined through this module.
     * <p>
     * Try to use an identifier that does not exist yet,
     * to prevent possible namespace collisions.
     *
     * @see Namespace
     * @see Namespace#of(String)
     */
    String identifier();

    /**
     * Display name for this module.
     * This is primarily used in logs and the editor,
     * and not be used for any identification.
     * <p>
     * By default, the {@link BlockyModule#identifier} will be used instead.
     */
    String name() default "";

    /**
     * Semantic Version string defining the version of this module.
     * Versions must follow the {@link SemVer#VALID_PATTERN_STRING} regex pattern,
     * which itself follows the official SemVer standard.
     *
     * @see SemVer
     * @see SemVer#from(String) 
     */
    String version() default "0.1.0";

    /**
     * A list of dependencies for this module,
     * where each dependency is specified in the format identifier:versionBound ({@code "identifier:~*.*.*"}).
     * <p>
     * Here, the identifier must be a valid {@link Namespace}, parsed with {@link Namespace#of(String)},
     * and each versionBound should be a valid {@link SemVerBounds}, parsed with {@link SemVerBounds#from(String)}.
     * These should be separated using a {@code ':'}.
     * All space on both sides of the {@code ':'} is stripped.
     * 
     * @see SemVerBounds#from(String)
     */
    String[] dependencies() default "";
}
