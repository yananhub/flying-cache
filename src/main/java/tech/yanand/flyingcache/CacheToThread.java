package tech.yanand.flyingcache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indicating that the result of invoking a method (or all methods
 * in a class) can be cached in the thread scope.
 *
 * @author Richard Zhang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Cacheable(cacheManager = ThreadCacheManager.THREAD_CACHE_MANAGER_NAME)
public @interface CacheToThread {

    /**
     * Alias for {@link Cacheable#value}.
     *
     * @return The cache names.
     */
    @AliasFor(annotation = Cacheable.class)
    String[] value() default {};

    /**
     * Alias for {@link Cacheable#cacheNames}.
     *
     * @return The cache names.
     */
    @AliasFor(annotation = Cacheable.class)
    String[] cacheNames() default {};

    /**
     * Alias for {@link Cacheable#key}.
     *
     * @return The cache key.
     */
    @AliasFor(annotation = Cacheable.class)
    String key() default "";

    /**
     * Alias for {@link Cacheable#keyGenerator}.
     *
     * @return The key generator.
     */
    @AliasFor(annotation = Cacheable.class)
    String keyGenerator() default "";
}