package tech.yanand.flyingcache;

/**
 * Wrap a runnable task to perform cleanup cache data in the thread.
 *
 * @author Richard Zhang
 */
public final class RunnableWrapper extends AbstractWrapper<Runnable> implements Runnable {

    /**
     * Constructor with a runnable task.
     *
     * @param task The task to wrap.
     */
    public RunnableWrapper(Runnable task) {
        super(task);
    }

    @Override
    public void run() {
        beforeExecute();
        try {
            task.run();
        } finally {
            afterExecute();
        }
    }
}
