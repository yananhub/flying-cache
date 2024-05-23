package tech.qianmi.flyingcache;

public final class RunnableWrapper extends AbstractWrapper<Runnable> implements Runnable {

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
