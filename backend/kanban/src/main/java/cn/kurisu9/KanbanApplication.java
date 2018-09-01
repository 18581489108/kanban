package cn.kurisu9;

import cn.kurisu9.loop.net.NettyServer;
import cn.kurisu9.loop.net.handler.WebSocketServerInitHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KanbanApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanApplication.class, args);

        NettyServer nettyServer = new NettyServer("localhost", 8090, new WebSocketServerInitHandler());
        nettyServer.boot();
	}
}
