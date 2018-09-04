package cn.kurisu9.loop.reflect;

import cn.kurisu9.loop.logic.AbstractObject;
import cn.kurisu9.loop.net.codec.NetPacket;
import cn.kurisu9.loop.processor.AbstractProcessor;
import cn.kurisu9.loop.processor.AbstractProcessorMessage;

/**
 * @author kurisu9
 * @description 消息/网络包分发器
 * @date 2018/9/2 14:58
 **/
public class Dispatcher {


    /**
     * 分发线程间的消息
     * */
    public static void dispatchMessage(AbstractProcessor processor, AbstractProcessorMessage message, int messageId) {
        // TODO 分发消息
    }

    /**
     * 分发客户端消息
     * */
    public static void dispatchClientPacket(AbstractObject object, NetPacket packet) {

    }
}
