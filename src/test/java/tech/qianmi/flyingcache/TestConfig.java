package tech.qianmi.flyingcache;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@SpringBootConfiguration
@EnableCaching
@ComponentScan
class TestConfig {

    @Bean
    @Primary
    ThreadCacheManager threadCacheManager() {
        return new ThreadCacheManager();
    }

    @Bean
    SessionCacheManager sessionCacheManager() {
        return new SessionCacheManager();
    }

    @Bean
    SimpleAsyncTaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
