package cn.kurisu9;

import cn.kurisu9.loop.bootstrap.ServerBoot;
import cn.kurisu9.loop.net.NettyServer;
import cn.kurisu9.loop.net.handler.WebSocketServerInitHandler;
import cn.kurisu9.loop.util.ConfigUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KanbanApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanApplication.class, args);

        ServerBoot.boot();
    }
}
