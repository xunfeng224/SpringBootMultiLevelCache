package com.example.cachedemo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @program: cacheDemo
 * @description:
 * @author: xiongfeng
 * @create: 2023-05-18 20:34
 **/
@Component
@Slf4j
public class KeyDeletedListener extends KeyDeletedEventMessageListener {
    public KeyDeletedListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * @param message 消息
     * @param pattern 主题
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("收到key删除事件，消息主题是：{},消息内容是：{}", message, pattern);
    }
}
