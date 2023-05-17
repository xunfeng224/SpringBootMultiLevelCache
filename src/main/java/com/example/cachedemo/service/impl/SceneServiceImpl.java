package com.example.cachedemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachedemo.entity.Scene;
import com.example.cachedemo.mapper.SceneMapper;
import com.example.cachedemo.service.SceneService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: cacheDemo
 * @description:
 * @author: xiongfeng
 * @create: 2023-05-17 20:53
 **/
@Service
@CacheConfig(cacheNames = "scene")
public class SceneServiceImpl extends ServiceImpl<SceneMapper, Scene> implements SceneService {
    @Override
    @CachePut(key = "#str")
    public List<Scene> listScene(String str) {
        return this.list();
    }

    @Override
    @Cacheable(key = "#sceneId")
    public Scene getSceneById(Long sceneId) {
        return this.getById(sceneId);
    }
}
