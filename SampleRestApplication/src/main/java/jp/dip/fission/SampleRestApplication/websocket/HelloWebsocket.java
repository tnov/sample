package jp.dip.fission.SampleRestApplication.websocket;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/websocket")
public class HelloWebsocket {
    @OnMessage
    public String echo(String message) {
        System.out.println(message);
        return message;
    }
}
