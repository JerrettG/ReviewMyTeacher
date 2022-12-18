package com.kenzie.appserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    // TODO create a cache for searching by teacherName

    @Bean
    public ReviewCache myCache() {
        return new ReviewCache(120, TimeUnit.SECONDS, 1000);
    }
}
