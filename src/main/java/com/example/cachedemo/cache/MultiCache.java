package com.example.cachedemo.cache;

import org.springframework.cache.Cache;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author xfeng
 */
public class MultiCache implements Cache {
    private String name;
    private Cache localCache;
    private Cache remoteCache;

    public MultiCache(String name, Cache localCache, Cache remoteCache) {
        this.name = name;
        this.localCache = localCache;
        this.remoteCache = remoteCache;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper valueWrapper = localCache.get(key);
        if (valueWrapper == null) {
            valueWrapper = remoteCache.get(key);
            if (valueWrapper != null) {
                localCache.put(key, valueWrapper.get());
            }
        }
        return valueWrapper;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        T value = localCache.get(key, type);
        if (value == null) {
            value = remoteCache.get(key, type);
            if (value != null) {
                localCache.put(key, value);
            }
        }
        return value;
    }

    public List getList(Object key) {
        return this.get(key, List.class);
//        List list = localCache.get(key, List.class);
//        if (list == null) {
//            list = remoteCache.get(key, List.class);
//            if (list != null) {
//                localCache.put(key, list);
//            }
//        }
//         list;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        ValueWrapper valueWrapper = localCache.get(key);
        if (valueWrapper == null) {
            T value = remoteCache.get(key, valueLoader);
            if (value != null) {
                localCache.put(key, value);
            }
            return value;
        } else {
            return (T) valueWrapper.get();
        }
    }

    @Override
    public void put(Object key, Object value) {
        localCache.put(key, value);
        remoteCache.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        localCache.putIfAbsent(key, value);
        return remoteCache.putIfAbsent(key, value);
    }

    @Override
    public void evict(Object key) {
        localCache.evict(key);
        remoteCache.evict(key);
    }

    @Override
    public void clear() {
        localCache.clear();
        remoteCache.clear();
    }
}
