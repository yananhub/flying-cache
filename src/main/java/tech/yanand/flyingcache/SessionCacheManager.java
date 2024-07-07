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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.web.context.request.RequestContextHolder;

import static java.util.Optional.ofNullable;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

/**
 * Implements Spring's {@link CacheManager} to manage the cache of the session scope.
 * If the current thread is not a requesting thread, it will create the cache in {@link ThreadLocal}.
 *
 * @see CacheToSession
 * @author Richard Zhang
 */
@Qualifier(SessionCacheManager.SESSION_CACHE_MANAGER_NAME)
public class SessionCacheManager extends AbstractCacheManager {

    static final String SESSION_CACHE_MANAGER_NAME = "sessionCacheManager";

    private static final String SESSION_CACHE_KEY = SessionCacheManager.class.getName() + ".SESSION_CACHES";

    /**
     * Clear the cache from session scope.
     */
    public static void clearSessionCache() {
        ofNullable(RequestContextHolder.getRequestAttributes())
                .ifPresent(attr -> attr.removeAttribute(SESSION_CACHE_KEY, SCOPE_SESSION));
    }

    @Override
    protected String getCacheKey() {
        return SESSION_CACHE_KEY;
    }

    @Override
    protected int getScope() {
        return SCOPE_SESSION;
    }
}
