package tech.qianmi.flyingcache;

abstract class AbstractWrapper<T> {

    protected final T task;

    protected AbstractWrapper(T task) {
        this.task = task;
    }

    protected void beforeExecute() {
        ThreadCacheManager.clearRequestCache();
        SessionCacheManager.clearSessionCache();
        AbstractCacheManager.clearThreadCache();
    }

    protected void afterExecute() {
        beforeExecute();
    }
}
