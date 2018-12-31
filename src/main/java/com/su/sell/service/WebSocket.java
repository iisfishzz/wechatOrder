package com.su.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
* @Description:    监听websocket并处理事件
* @Author:         scott
* @CreateDate:     2018/12/29 11:05
* @UpdateUser:     scott
* @UpdateDate:     2018/12/29 11:05
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {
    private Session session;

    private static CopyOnWriteArrayList<WebSocket> webSocketSet = new CopyOnWriteArrayList<>();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
        log.info("【webSocket消息】有新的连接,总数:{}",webSocketSet.size());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("【websocket消息】断开连接,总数:{}",webSocketSet.size());
    }
    @OnMessage
    public void onMessage(String message){
        log.info("【webSocket消息】收到客户端发来的消息:{}",message);
    }

    /**
     * 发送消息到客户端
     */
    public void sendMessage(String message){
        for (WebSocket webSocket : webSocketSet) {
            log.info("【webSocket消息】广播消息,message={}",message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("【webSocket消息】广播消息发生异常:{}",e);
            }
        }
    }
}
