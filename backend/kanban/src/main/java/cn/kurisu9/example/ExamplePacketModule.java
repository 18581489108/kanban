package cn.kurisu9.example;

import cn.kurisu9.loop.reflect.annotation.PacketHandler;
import cn.kurisu9.loop.reflect.annotation.PacketModule;

/**
 * @author kurisu9
 * @description 消息处理模块的模拟
 * @date 2018/9/17 20:31
 **/
@PacketModule
public class ExamplePacketModule {

    @PacketHandler
    public void handleLoginRequest(ClientObject client, ExampleMessage.LoginRequest packet) {
        long uuid = packet.getUuid();

        if (uuid != 9) {
            return;
        }

        client.setLoginVerifyOk(true);

        ExampleMessage.LoginResponse.Builder builder = ExampleMessage.LoginResponse.newBuilder();
        builder.setResult(true);
        client.senClientPacket(builder.build());

    }


}
