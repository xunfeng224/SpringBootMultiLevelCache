package com.example.cachedemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.cachedemo.cache.MultiCache;
import com.example.cachedemo.entity.Scene;
import com.example.cachedemo.service.SceneService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xiongfeng
 * @date 2023/5/16
 */
@RestController
@RequiredArgsConstructor
public class TestController {

    private final CacheManager multiCacheManager;
    private final SceneService sceneService;

    @GetMapping("/test")
    public String test(HttpServletRequest request) throws InterruptedException {
        String str = "ip:" + request.getRemoteAddr() + "线程:" + Thread.currentThread().getName();
        Thread.sleep(500);
        return "000";
    }

    @GetMapping("/testMultiCache")
    public List<Scene> testMultiCache() {
        MultiCache multiCache = (MultiCache) multiCacheManager.getCache("dict");
        List<Scene> scenes = sceneService.listScene("");
        String jsonString = JSONObject.toJSONString(scenes);
        multiCache.put("aa", jsonString);
        String valueString = multiCache.get("aa", String.class);
        List<Scene> sceneList = JSONObject.parseArray(valueString, Scene.class);

        multiCache.put("bb", scenes);
        List bb = multiCache.getList("bb");

        return sceneList;
    }

}