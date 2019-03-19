package net.tnktoys.ZipAddress.websocket;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/websocket")
public class HelloWebsocket {


    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
    	System.out.println("onOpen");
    }

    @OnMessage
    public String echo(String message, Session session, EndpointConfig config) {
        System.out.println(message);
        return message;
    }


    @OnClose
    public void onClose(Session session, CloseReason reason) {
    	System.out.println("onClose");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
    	System.out.println("onError");
        throwable.printStackTrace();
    }
}
