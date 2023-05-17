package com.example.cachedemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cachedemo.entity.Scene;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: cacheDemo
 * @description:
 * @author: xiongfeng
 * @create: 2023-05-17 20:52
 **/
@Mapper
public interface SceneMapper extends BaseMapper<Scene> {
}
