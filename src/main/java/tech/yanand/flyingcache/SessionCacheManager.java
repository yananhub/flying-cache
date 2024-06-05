package tech.yanand.flyingcache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.request.RequestContextHolder;

import static java.util.Optional.ofNullable;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

@Qualifier(SessionCacheManager.SESSION_CACHE_MANAGER_NAME)
public class SessionCacheManager extends AbstractCacheManager {

    public static final String SESSION_CACHE_MANAGER_NAME = "sessionCacheManager";

    private static final String SESSION_CACHE_KEY = SessionCacheManager.class.getName() + ".SESSION_CACHES";

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
