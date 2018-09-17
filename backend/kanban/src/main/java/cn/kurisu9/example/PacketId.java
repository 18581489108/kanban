package cn.kurisu9.example;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kurisu9
 * @description 消息包Id
 * @date 2018/9/17 20:15
 **/
public class PacketId {
    public static final short LoginRequest = 1001;

    public static final short LoginResponse = 1002;

    public static final short Message = 1003;

    private static final Map<Class<?>, Short> packetMap;

    static {
        packetMap = new HashMap<>();

        packetMap.put(ExampleMessage.LoginRequest.class, LoginRequest);
        packetMap.put(ExampleMessage.LoginResponse.class, LoginResponse);
        packetMap.put(ExampleMessage.Message.class, Message);
    }

    public static short getPacketId(Class<?> clazz) {
        Short id = packetMap.get(clazz);
        return id == null ? -1 : id;
    }
}
