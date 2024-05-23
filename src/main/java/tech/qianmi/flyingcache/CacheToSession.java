package tech.qianmi.flyingcache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static tech.qianmi.flyingcache.SessionCacheManager.SESSION_CACHE_MANAGER_NAME;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Cacheable(cacheManager = SESSION_CACHE_MANAGER_NAME)
public @interface CacheToSession {

    @AliasFor(annotation = Cacheable.class)
    String[] value() default {};

    @AliasFor(annotation = Cacheable.class)
    String[] cacheNames() default {};

    @AliasFor(annotation = Cacheable.class)
    String key() default "";

    @AliasFor(annotation = Cacheable.class)
    String keyGenerator() default "";
}