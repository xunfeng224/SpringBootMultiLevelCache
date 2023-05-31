package com.example.cachedemo.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: cacheDemo
 * @description:
 * @author: xiongfeng
 * @create: 2023-05-31 20:50
 **/
public class MultiCacheManager extends AbstractCacheManager {

    private final CacheManager localCacheManger;
    private final CacheManager remoteCacheManager;

    public MultiCacheManager(CacheManager localCacheManager, CacheManager remoteManager) {
        this.localCacheManger = localCacheManager;
        this.remoteCacheManager = remoteManager;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        Set<String> localCacheNames = new HashSet<>(localCacheManger.getCacheNames());
        Set<String> remoteCacheNames = new HashSet<>(remoteCacheManager.getCacheNames());
        Collection<Cache> caches = new ArrayList<>();
        localCacheNames.forEach(name -> {
            if (remoteCacheNames.contains(name)) {
                caches.add(new MultiCache(name, localCacheManger.getCache(name), remoteCacheManager.getCache(name)));
            } else {
                caches.add(localCacheManger.getCache(name));
            }
        });

        remoteCacheNames.forEach(name -> {
            if (!localCacheNames.contains(name)) {
                caches.add(remoteCacheManager.getCache(name));
            }
        });
        return caches;
    }
}
