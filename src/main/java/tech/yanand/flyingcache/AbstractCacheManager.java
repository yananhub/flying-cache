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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

abstract class AbstractCacheManager implements CacheManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final ThreadLocal<ConcurrentMap<String, Cache>> threadCacheHolder = new NamedThreadLocal<>("Thread caches");

    /**
     * Clear the cache from thread local.
     */
    public static void clearThreadCache() {
        threadCacheHolder.remove();
    }

    @Override
    public Cache getCache(String name) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            ConcurrentMap<String, Cache> caches = getCacheFromRequest(requestAttributes);
            if (caches == null) {
                caches = new ConcurrentHashMap<>();
                requestAttributes.setAttribute(getCacheKey(), caches, getScope());
            }

            return caches.computeIfAbsent(name, ConcurrentMapCache::new);
        } else {
            if (logger.isWarnEnabled() && !isCallingInWrapper()) {
                logger.warn("The current thread is not in a request scope, " +
                        "so it will utilize ThreadLocal cache data. " +
                        "It is important to note that at the end of the thread, " +
                        "clearCache() should be called to prevent memory leaks. " +
                        "Or using RunnableWrapper or CallableWrapper to wrap the task of the thread.");
            }

            ConcurrentMap<String, Cache> caches = threadCacheHolder.get();
            if (caches == null) {
                caches = new ConcurrentHashMap<>();
                threadCacheHolder.set(caches);
            }

            return caches.computeIfAbsent(name, ConcurrentMapCache::new);
        }
    }

    @Override
    public Collection<String> getCacheNames() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ConcurrentMap<String, Cache> caches = requestAttributes != null
                ? getCacheFromRequest(requestAttributes)
                : threadCacheHolder.get();
        return caches != null ? caches.keySet() : List.of();
    }

    /**
     * Get the cache key for storing the request cache.
     *
     * @return The cache key.
     */
    protected abstract String getCacheKey();

    /**
     * Get the type of scope for storing the cache.
     *
     * @return The scope that must be
     * {@link RequestAttributes#SCOPE_REQUEST} or {@link RequestAttributes#SCOPE_SESSION}.
     */
    protected abstract int getScope();

    @SuppressWarnings("unchecked")
    private ConcurrentMap<String, Cache> getCacheFromRequest(RequestAttributes requestAttributes) {
        return (ConcurrentMap<String, Cache>) requestAttributes.getAttribute(getCacheKey(), getScope());
    }

    private boolean isCallingInWrapper() {
        return Arrays.stream(Thread.currentThread().getStackTrace())
                .anyMatch(stack -> stack.getClassName().equals(RunnableWrapper.class.getName())
                        || stack.getClassName().equals(CallableWrapper.class.getName()));
    }
}
