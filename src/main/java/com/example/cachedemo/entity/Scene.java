package com.example.cachedemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: cacheDemo
 * @description:
 * @author: xiongfeng
 * @create: 2023-05-17 20:50
 **/
@Data
@TableName("t_scene")
@AllArgsConstructor
public class Scene implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(
            value = "id",
            type = IdType.AUTO
    )
    private Long id;
    /**
     * 场景名称
     */
    private String sceneName;
    /**
     * 场景类型： 1：默认场景  2：扩展应用场景
     */
    private Integer type;
    /**
     * 场景状态 1：可用 2：不可用
     */
    private Integer status;
    /**
     * 任务ID
     */
    private Long taskId;
    /**
     * 技能
     */
    private Long skillId;
    /**
     * 算法code 唯一识别一个算法
     */
    private String algCode;
    /**
     * 告警内容
     */
    private String warnContent;


}
