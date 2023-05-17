package com.example.cachedemo.controller;

import com.example.cachedemo.entity.Scene;
import com.example.cachedemo.service.SceneService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: cacheDemo
 * @description:
 * @author: xiongfeng
 * @create: 2023-05-17 20:55
 **/
@RestController
@RequestMapping("/scene")
@AllArgsConstructor
public class SceneController {
    private SceneService sceneService;

    @GetMapping("/list")
    public List<Scene> listScene(String str) {
        return sceneService.listScene(str);
    }

    @GetMapping("{sceneId}")
    public Scene getSceneById(@PathVariable(value = "sceneId") Long sceneId) {
        return sceneService.getSceneById(sceneId);
    }
}
