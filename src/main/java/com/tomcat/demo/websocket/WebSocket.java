package com.tomcat.demo.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;
/**
 * @author Cherry
 * 2020年5月25日
 */
@ServerEndpoint("/websocket/{username}")
public class WebSocket {
   
    /*
     * 发送消息只需要使用websocket.send(“发送消息”)，就可以触发服务端的onMessage()方法，
     * 当连接时，触发服务器端onOpen()方法，此时也可以调用发送消息的方法去发送消息。
     * 关闭websocket时，触发服务器端onclose()方法，此时也可以发送消息，但是不能发送给自己，
     * 因为自己的已经关闭了连接，但是可以发送给其他人。
     */
    private static int onlineCount = 0;

    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();

    private Session session;

    private String username;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session)
            throws IOException {
        this.username = username;
        this.session = session;
        addOnlineCount();
        clients.put(username, this);
        System.out.println("已连接");
    }

    @OnClose
    public void onClose() throws IOException {
        clients.remove(username);
        subOnlineCount();
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        JSONObject jsonTo = JSONObject.fromObject(message);
        if (!jsonTo.get("To").equals("All")) {
            sendMessageTo("给一个人", jsonTo.get("To").toString());
        } else {
            sendMessageAll("给所有人");
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessageTo(String message, String To) throws IOException {
        // session.getBasicRemote().sendText(message);
        // session.getAsyncRemote().sendText(message);
        for (WebSocket item : clients.values()) {
            if (item.username.equals(To))
                item.session.getAsyncRemote().sendText(message);
        }
    }

    public void sendMessageAll(String message) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

    public static synchronized Map<String, WebSocket> getClients() {
        return clients;
    }
}
