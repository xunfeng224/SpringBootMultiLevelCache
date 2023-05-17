package com.example.cachedemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiongfeng
 * @date 2023/5/16
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String test(HttpServletRequest request) throws InterruptedException {
        String str = "ip:" + request.getRemoteAddr() + "线程:" + Thread.currentThread().getName();
        Thread.sleep(500);
        return "000";
    }

}