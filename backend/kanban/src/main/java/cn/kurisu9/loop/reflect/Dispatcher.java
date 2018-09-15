package cn.kurisu9.loop.reflect;

import cn.kurisu9.loop.logic.AbstractObject;
import cn.kurisu9.loop.net.codec.NetPacket;
import cn.kurisu9.loop.processor.AbstractProcessor;
import cn.kurisu9.loop.processor.AbstractProcessorMessage;
import cn.kurisu9.loop.util.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author kurisu9
 * @description 消息/网络包分发器
 * @date 2018/9/2 14:58
 **/
public class Dispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);


    /**
     * 网络消息包解码方法
     * */
    private static Method[] packetDecodeMethods = new Method[ConfigUtils.MAX_PACKET_ID];

    /**
     * 网络消息包的处理方法
     * */
    private static ModuleHandlerData[] packetHandlers = new ModuleHandlerData[ConfigUtils.MAX_PACKET_ID];

    /**
     * 线程间消息的处理方法
     * */
    private static ModuleHandlerData[] messageHandlers = new ModuleHandlerData[ConfigUtils.MAX_MESSAGE_ID];

    /**
     * 注册网络包处理
     * @param id            网络包id
     * @param moduleClass   网络包处理模块
     * @param method        网络包处理方法
     */
    public static void registerPacket(short id, Object moduleClass, Method method) throws NoSuchMethodException {
        // 获取protoBuf的解析方法
        packetDecodeMethods[id] = method.getParameterTypes()[1].getDeclaredMethod("parseFrom", byte[].class);

        packetHandlers[id] = new ModuleHandlerData(moduleClass, method);
    }

    /**
     * 注册消息处理
     * @param id            消息id
     * @param moduleClass   消息处理模块
     * @param method        消息处理方法
     */
    public static void registerMessage(short id, Object moduleClass, Method method) {
        messageHandlers[id] = new ModuleHandlerData(moduleClass, method);
    }

    /**
     * 分发线程间的消息
     * */
    public static void dispatchMessage(AbstractProcessor processor, AbstractProcessorMessage message, int messageId) {
        if (messageId < 0 || messageId >= ConfigUtils.MAX_MESSAGE_ID) {
            LOGGER.error("dispatch message id:{} < 0 or >= {}", messageId, ConfigUtils.MAX_MESSAGE_ID);
            return;
        }

        ModuleHandlerData data = messageHandlers[messageId];

        Object module = data.getModule();
        try {
            data.getHandler().invoke(module, processor, message);
        } catch (Exception e) {
            LOGGER.error("dispatch message {} error, error:{}", messageId, e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 分发客户端消息
     * */
    public static void dispatchClientPacket(AbstractObject object, NetPacket netPacket) {
        int packetId = netPacket.getPacketId();

        if (packetId < 0 || packetId >= ConfigUtils.MAX_PACKET_ID) {
            LOGGER.error("dispatch packet id:{} < 0 or >= {}", packetId, ConfigUtils.MAX_PACKET_ID);
            return;
        }

        ModuleHandlerData data = packetHandlers[packetId];
        Method decodeMethod = packetDecodeMethods[packetId];

        try {
            Object packet = decodeMethod.invoke(null, netPacket.getBody());
            LOGGER.debug("handle client packet:{}, content:\n{}", packetId, packet.toString());
            data.getHandler().invoke(data.getModule(), object, packet);
        } catch (Exception e) {
            LOGGER.error("dispatch client packet {} error, error:{}", packetId, e.getMessage());
            e.printStackTrace();
        }

    }
}






















