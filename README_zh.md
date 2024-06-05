# 请求和线程范围的 Spring 缓存扩展

**其他语言版本: [English](README.md), [中文](README_zh.md).**

当我们开发应用时经常会查询一些热点数据，这些数据是业务性的并且多变，它并不适合永久缓存，我们只希望将它存储在请求范围内。通常我们
会想到建立一个请求范围的 bean，像下面的代码一样：

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

这时候在map中的`TestMode`缓存会随着请求线程的结束而一起销毁。

然而这种方式是一种繁琐而重复的工作。现在好了，有了 flying-cache，可以使这项工作变得轻松。我们只需要简单的两步来完成：

在你的`@Configuration`类中添加`@EnableCaching`注解并声明`ThreadCacheManager`为 bean。

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

使用`@CacheToThread`来声明方法为可缓存。这时，service 方法的调用结果将存储在请求线程缓存中，以便在后续调用时(使用相同的参数)，
无需实际调用该方法即可返回缓存中的值，直到该次请求结束。

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