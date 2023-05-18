package com.example.cachedemo.controller;


import com.example.cachedemo.entity.Scene;
import com.example.cachedemo.service.SceneService;
import com.example.cachedemo.websocket.SocketResult;
import com.example.cachedemo.websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiongfeng
 * @date 2023/5/18
 */
@RestController
@RequestMapping("/websocket")
public class WebSocketController {
    @Autowired
    private SceneService sceneService;
    @Autowired
    private WebSocket webSocket;

    @GetMapping("/sendMessage")
    public Boolean sendMessage(String topic) {
        SocketResult socketResult = new SocketResult();
        SocketResult.Header header = new SocketResult.Header();
        header.setMessageType("test");
        header.setTopic(topic);
        socketResult.setHeader(header);
        Scene scene = sceneService.getById(1L);
        socketResult.setBody(scene);
        webSocket.sendMessageByTopic(topic, socketResult);
        return Boolean.TRUE;
    }
}