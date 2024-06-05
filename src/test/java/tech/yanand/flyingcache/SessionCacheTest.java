package tech.yanand.flyingcache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SessionCacheTest {

    @Autowired
    private SampleCache sampleCache;

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    @BeforeEach
    void init() {
        RequestContextHolder.resetRequestAttributes();
        AbstractCacheManager.clearThreadCache();
    }

    @Test
    void testSessionScope() {
        mockRequestScope();

        assertTrue(sameReturnForTwiceCall());
    }

    @Test
    void testRunnableWrapper() throws InterruptedException, ExecutionException {
        RunnableWrapper wrapper = new RunnableWrapper(this::sameReturnForTwiceCall);

        Future<?> future = taskExecutor.submit(wrapper);

        future.get();
    }

    @Test
    void testCallableWrapper() throws InterruptedException, ExecutionException {
        CallableWrapper<Boolean> wrapper = new CallableWrapper<>(this::sameReturnForTwiceCall);

        Future<Boolean> future = taskExecutor.submit(wrapper);

        assertTrue(future.get());
    }

    private boolean sameReturnForTwiceCall() {
        TestModel data = sampleCache.getSessionData();
        TestModel dataFromCache = sampleCache.getSessionData();

        assertSame(dataFromCache, data);

        return data == dataFromCache;
    }

    private void mockRequestScope() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }
}
