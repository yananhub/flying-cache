package tech.qianmi.flyingcache;

import org.springframework.stereotype.Component;

@Component
public class SampleCache {

    @CacheToThread("SampleCache.getThreadData")
    public TestModel getThreadData() {
        return new TestModel();
    }

    @CacheToSession("SampleCache.getSessionData")
    public TestModel getSessionData() {
        return new TestModel();
    }
}
