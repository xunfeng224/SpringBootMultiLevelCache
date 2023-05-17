package com.example.cachedemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.example.cachedemo")
@EnableCaching
@MapperScan(value = "com.example.cachedemo.mapper")
public class CacheDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheDemoApplication.class, args);
    }

}
