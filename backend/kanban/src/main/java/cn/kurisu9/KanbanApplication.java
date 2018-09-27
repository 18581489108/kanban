package cn.kurisu9;

import cn.kurisu9.example.ClientObject;
import cn.kurisu9.example.ExampleContainerLogic;
import cn.kurisu9.example.PacketId;
import cn.kurisu9.loop.bootstrap.ServerBoot;
import cn.kurisu9.loop.logic.PacketFilter;
import cn.kurisu9.loop.util.ConfigUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KanbanApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(KanbanApplication.class, args);

        ServerBoot.boot(() -> {
			PacketFilter.getInstance().registerLoginPacketId(PacketId.LoginRequest);
            ConfigUtils.setAbstractObjectClass(ClientObject.class);
            ConfigUtils.setContainerLogicClass(ExampleContainerLogic.class);
		});
    }
}
