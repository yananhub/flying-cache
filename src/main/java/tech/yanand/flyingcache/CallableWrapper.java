package tech.yanand.flyingcache;

import java.util.concurrent.Callable;

/**
 * Wrap a callable task to perform cleanup cache data in the thread.
 *
 * @param <V> The result type of method {@link Callable#call()}.
 *
 * @author Richard Zhang
 */
public final class CallableWrapper<V> extends AbstractWrapper<Callable<V>> implements Callable<V> {

    /**
     * Constructor with a callable task.
     *
     * @param task The task to wrap.
     */
    public CallableWrapper(Callable<V> task) {
        super(task);
    }

    @Override
    public V call() throws Exception {
        beforeExecute();
        try {
            return task.call();
        } finally {
            afterExecute();
        }
    }
}
