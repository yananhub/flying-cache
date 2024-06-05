# Spring cache extension for request and thread scoped

**Read this in other languages: [English](README.md), [中文](README_zh.md).**

When we develop an application, we often query some hot data, this data is business and changeable, it is not suitable
for permanent caching, we only want to store it within the scope of the request. Usually we would think of creating a 
request-scoped bean, like the following code:

```java
@RequestScope
@Component
public class TestModelCache {

    private ConcurrentMap<Long, TestModel> cache = new ConcurrentHashMap<>();

    @Autowired
    private TestModelService service;

    public TestModel getTestModelById(Long id) {
        return cache.computeIfAbsent(id, service::getById);
    }
}
```

In this case, the `TestMode` cache in the map is destroyed at the end of the request thread.

However, this approach is tedious and repetitive work. Now, with flying-cache, this task is made easier. We only need 
two simple steps to do this:

Add the `@EnableCaching` annotation to your `@Configuration` class and declare `ThreadCacheManager` as a bean.

```java
@EnableCaching
@Configuration
class TestConfig {
    
    @Bean
    ThreadCacheManager threadCacheManager() {
        return new ThreadCacheManager();
    }
}
```

Use `@CacheToThread` to declare a method cacheable. At this point, the result of the call to the service method is 
stored in the requesting thread cache so that on subsequent calls (with the same parameters), the cached value can be 
returned without actually calling the method until the request is complete.

```java
@Service
public class SampleCache {

    @Autowired
    private TestModelService service;
    
    @CacheToThread("SampleCache.getTestModelById")
    public TestModel getTestModelById(Long id) {
        return service.getById(id);
    }
}
```