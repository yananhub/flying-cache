/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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