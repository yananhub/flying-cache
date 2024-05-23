package tech.qianmi.flyingcache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.request.RequestContextHolder;

import static java.util.Optional.ofNullable;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

@Qualifier(ThreadCacheManager.THREAD_CACHE_MANAGER_NAME)
public class ThreadCacheManager extends AbstractCacheManager {

    public static final String THREAD_CACHE_MANAGER_NAME = "threadCacheManager";

    private static final String THREAD_CACHE_KEY = ThreadCacheManager.class.getName() + ".THREAD_CACHES";

    public static void clearRequestCache() {
        ofNullable(RequestContextHolder.getRequestAttributes())
                .ifPresent(attr -> attr.removeAttribute(THREAD_CACHE_KEY, SCOPE_REQUEST));
    }

    @Override
    protected String getCacheKey() {
        return THREAD_CACHE_KEY;
    }

    @Override
    protected int getScope() {
        return SCOPE_REQUEST;
    }
}
