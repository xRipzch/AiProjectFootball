package org.example.chatgptspring08.config;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {   // Here we create our cache.
        return new ConcurrentMapCacheManager("oddsCache");   // like a regular map, but thread safe. Allows for concurrent read and write operations.

    }
}
