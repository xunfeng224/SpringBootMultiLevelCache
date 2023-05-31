package com.example.cachedemo.cache;


import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.List;

/**
 * @program: cacheDemo
 * @description: 两级缓存配置
 * @author: xiongfeng
 * @create: 2023-05-31 20:51
 **/
@Configuration
@EnableConfigurationProperties(MultiCacheConfig.class)
@ConfigurationProperties(prefix = "multi")
@Setter
public class MultiCacheConfig {
    private MultiCache local;
    private MultiCache remote;

    @Setter
    static class MultiCache {
        private String spec;
        private List<Config> settings;
    }

    @Setter
    static class Config {
        private String name;
        private String spec;
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 使用Caffeine做本地缓存
        // 支持配置最大容量,超时时间等,可参考com.github.benmanes.caffeine.cache.CaffeineSpec
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        // 设置公共配置
        String localCommonSpec = local.spec;
        if (localCommonSpec != null && !localCommonSpec.isEmpty()) {
            caffeineCacheManager.setCaffeineSpec(CaffeineSpec.parse(localCommonSpec));
        }
        for (Config item : local.settings) {
            String spec = item.spec;
            if (spec != null && !spec.isEmpty()) {
                // 单独配置cache
                com.github.benmanes.caffeine.cache.Cache<Object, Object> coffeeCache = Caffeine.from(item.spec).build();
                caffeineCacheManager.registerCustomCache(item.name, coffeeCache);
            } else {
                // 使用公共配置创建Cache
                caffeineCacheManager.getCache(item.name);
            }
        }


        // 使用redis做远程缓存
        // 只可以设置过期时间(单位S), 且不支持单独设置cache,可参考org.springframework.data.redis.cache.RedisCacheConfiguration
        String remoteSpec = remote.spec;
        long ttl = computeExpiration(remoteSpec);
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer())).entryTtl(Duration.ofSeconds(ttl));
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfiguration).build();
        for (Config item : remote.settings) {
            // 创建Cache
            redisCacheManager.getCache(item.name);
        }
        return new MultiCacheManager(caffeineCacheManager, redisCacheManager);
    }

    // 读取ttl
    private long computeExpiration(String spec) {
        String[] ttl = spec.split("=", -1);
        if (ttl.length == 2) {
            if ("ttl".equals(ttl[0])) {
                return Long.parseLong(ttl[1]);
            }
        }
        throw new IllegalArgumentException("error ttl param, please input like 'ttl=30'");
    }
}

