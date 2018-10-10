package cn.kurisu9.example;

import java.util.Map;
import java.util.HashMap;

import cn.kurisu9.example.Test2;
import cn.kurisu9.example.Test1;

/**
 * 此类由工具自动生成，不可更改!!!
 *
 * This class created by ProtobufHelper on 2018-10-09 16:39:05.
 * Don't modify!!!
 */
public class PackId {
    //region Test1 消息id
    public static final short LoginRequest = 1001;
    public static final short LoginResponse = 1002;
    public static final short Message = 1003;
    //endregion

    //region Test2 消息id
    public static final short TestMessage = 2001;
    public static final short Hello = 2002;
    //endregion


    /**
    * 消息包类和id的映射关系
    */
    private static final Map<Class<?>, Short> packetIdMap = new HashMap<>();

    static {
        //region Test1 消息id
        packetIdMap.put(Test1.LoginRequest.class, PackId.LoginRequest);
        packetIdMap.put(Test1.LoginResponse.class, PackId.LoginResponse);
        packetIdMap.put(Test1.Message.class, PackId.Message);
        //endregion

        //region Test2 消息id
        packetIdMap.put(Test2.TestMessage.class, PackId.TestMessage);
        packetIdMap.put(Test2.Hello.class, PackId.Hello);
        //endregion

    }

    /**
    * 通过message类型获取message的id
    */
    public static short getPacketId(Class<?> clazz) {
        return packetIdMap.get(clazz);
    }
}