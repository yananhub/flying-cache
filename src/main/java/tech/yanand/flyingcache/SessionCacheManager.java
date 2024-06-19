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
