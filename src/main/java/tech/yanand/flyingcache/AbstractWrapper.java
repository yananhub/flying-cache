package tech.yanand.flyingcache;

abstract class AbstractWrapper<T> {

    /**
     * The task to wrap.
     */
    protected final T task;

    protected AbstractWrapper(T task) {
        this.task = task;
    }

    /**
     * Things to do before executing the task.
     */
    protected void beforeExecute() {
        ThreadCacheManager.clearRequestCache();
        SessionCacheManager.clearSessionCache();
        AbstractCacheManager.clearThreadCache();
    }

    /**
     * Things to do after executing the task.
     */
    protected void afterExecute() {
        beforeExecute();
    }
}
