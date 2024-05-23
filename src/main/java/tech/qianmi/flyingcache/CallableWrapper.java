package tech.qianmi.flyingcache;

import java.util.concurrent.Callable;

public final class CallableWrapper<V> extends AbstractWrapper<Callable<V>> implements Callable<V> {

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
