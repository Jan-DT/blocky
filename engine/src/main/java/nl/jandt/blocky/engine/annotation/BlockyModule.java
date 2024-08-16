package nl.jandt.blocky.engine.annotation;

import nl.jandt.blocky.engine.util.Namespace;
import nl.jandt.blocky.engine.util.SemVer;

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
}
