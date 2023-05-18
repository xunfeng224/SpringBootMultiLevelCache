package com.example.cachedemo.websocket;

import lombok.Data;

/**
 * 请求响应体
 *
 * @author Lee
 * @return
 * @date 2022-3-29 16:46
 */
@Data
public class SocketResult {

    /**
     * 请求头
     */
    private Header header;

    @Data
    public static class Header {
        private String version;
        private Long timestamp;
        private String messageType;
        private String topic;
        private String callId;
    }

    /**
     * 请求体 body
     */
    private Object body;

    /**
     * 创建请求头
     */
    public static Header createHeader(String messageType, String topic) {
        Header header = new Header();
        header.setVersion("V1.0");
        header.setTimestamp(System.currentTimeMillis());
        header.setMessageType(messageType);
        header.setTopic(topic);
        header.setCallId(header.getTimestamp().toString());
        return header;
    }

    /**
     * 创建推送结果
     *
     * @param topic
     * @param body
     * @return com.hfitech.unicorn.eye.core.biz.websocket.bean.SocketData
     * @author Lee
     * @date 2022-3-29 17:10
     */
    public static SocketResult createSocketResult(String messageType, String topic, Object body) {
        SocketResult socketResult = new SocketResult();
        socketResult.setHeader(createHeader(messageType, topic));
        socketResult.setBody(body);
        return socketResult;
    }
}
