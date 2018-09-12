package cn.kurisu9.loop.logic;

import java.util.HashSet;
import java.util.Set;

/**
 * @author kurisu9
 * @description 消息包过滤器
 * @date 2018/9/12 21:20
 **/
public class PacketFilter {
    private static final PacketFilter INSTANCE = new PacketFilter();

    private PacketFilter() {}

    public static PacketFilter getInstance() {
        return INSTANCE;
    }

    /**
     * 登录消息包ID
     * */
    private Set<Integer> loginPackets = new HashSet<>();

    public void registerLoginPacketId(int id) {
        loginPackets.add(id);
    }

    public boolean isLoginPcket(int id) {
        return loginPackets.contains(id);
    }
}
