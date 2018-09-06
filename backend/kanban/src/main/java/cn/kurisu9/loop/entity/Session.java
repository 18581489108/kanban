package cn.kurisu9.loop.entity;

import cn.kurisu9.loop.logic.AbstractObject;
import cn.kurisu9.loop.logic.ObjectTypeEnum;
import cn.kurisu9.loop.manager.SessionExceptionEnum;
import cn.kurisu9.loop.net.codec.NetPacket;
import cn.kurisu9.loop.reflect.Dispatcher;
import cn.kurisu9.loop.util.ConfigUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author kurisu9
 * @description 连接的相关会话
 * @date 2018/9/1 17:00
 **/
public class Session {
    private static final Logger LOGGER = LoggerFactory.getLogger(Session.class);

    /**
     * 挂载到Session上的channel对象
     * */
    private Channel channel;

    /**
     * 是否合法
     * */
    private volatile boolean valid = true;

    /**
     * session状态
     * */
    private SessionStatusEnum sessionStatus = SessionStatusEnum.INIT;

    /**
     * session异常的原因
     * */
    private SessionExceptionEnum sessionException = SessionExceptionEnum.NONE;

    /**
     * session挂载的对象
     * */
    private AbstractObject abstractObject;

    /**
     * 每个session对应的uuid
     * */
    private long uuid;

    /**
     * 登陆花费的时间
     * */
    private int loginElapsedTime;

    /**
     * 收到的网络包队列
     * */
    private ConcurrentLinkedQueue<NetPacket> receivedNetPacketQueue = new ConcurrentLinkedQueue<>();

    /**
     * 待发送的网络包队列
     * */
    private LinkedList<NetPacket> pendingSendNetPacketQueue = new LinkedList<>();

    /**
     * 将异常通知给挂载的object
     * */
    public void exception() {
        if (abstractObject != null) {
            abstractObject.exception(sessionException);
        }
    }

    /**
     * 添加收到的网络包
     * */
    public void pushReceivedNetPacket(NetPacket packet) {
        if (packet == null) {
            return;
        }

        receivedNetPacketQueue.add(packet);
    }

    /**
     * 弹出收到的网络包
     * */
    public NetPacket popReceivedNetPacket() {
        return receivedNetPacketQueue.poll();
    }

    /**
     * 将packet添加到待发送列表
     * */
    public void sendNetPacket(NetPacket packet) {
        if (packet != null) {
            pendingSendNetPacketQueue.add(packet);
        }
    }


    /**
     * 弹出待发送列表中的第一个消息包
     * */
    public NetPacket popPeningSendNetPacket() {
        return pendingSendNetPacketQueue.poll();
    }

    /**
     * 获取待发送列表中的第一个消息包
     * */
    public NetPacket peekPendingSendNetPacket() {
        return pendingSendNetPacketQueue.peek();
    }

    /**
     * 销毁session对象
     * */
    public void destroy() {
        LOGGER.info("session destroyed, uuid:{}", uuid);
        if (abstractObject != null) {
            abstractObject.destroy();
        }

        clear();
    }

    /**
     * 清理session对象
     * */
    public void clear() {
        channel = null;
        receivedNetPacketQueue.clear();
        pendingSendNetPacketQueue.clear();
        abstractObject = null;
    }

    /**
     * 关闭底层的channel
     * */
    public void closeChannel() {
        if (channel != null) {
            if (channel.isActive() || channel.isOpen() || channel.isRegistered() || channel.isWritable()) {
                channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    /**
     * 初始化挂载的对象
     * */
    public boolean initObject(ObjectTypeEnum type) {
        abstractObject = ConfigUtils.createAbstractObject(type);

        if (abstractObject == null) {
            LOGGER.warn("create AbstractObject failed");
            return false;
        }

        abstractObject.setSession(this);
        abstractObject.init();
        return true;
    }

    /**
     * tick登录消息
     * */
    public void tickLogin(int intervalTime) {
        handleLoginInput(intervalTime);

        handleOutput(intervalTime);
    }



    /**
     * 处理登陆消息
     * */
    private void handleLoginInput(int intervalTime) {
        loginElapsedTime += intervalTime;

        // 登录超时
        if (loginElapsedTime >= ConfigUtils.LOGIN_ELAPSED_LIMIT_TIME) {
            sessionException = SessionExceptionEnum.LOGIN_TIMEOUT;
            return;
        }

        NetPacket netPacket;
        if (abstractObject == null
            || (netPacket = popReceivedNetPacket()) == null) {
            return;
        }

        int packetId = netPacket.getPacketId();

        // TODO 检测是否是登录消息

        LOGGER.debug("Message id:{} form {}, message is not login message", packetId, netPacket.getSrcId());
    }

    /**
     * 处理输出消息
     * */
    private void handleOutput(int handleCount) {
        if (abstractObject == null || channel == null) {
            return;
        }

        boolean isFlush = false;
        for (int i = 0; i < handleCount; i++) {
            NetPacket netPacket = peekPendingSendNetPacket();
            if (netPacket == null) {
                break;
            }

            isFlush = true;
            channel.write(netPacket);
            popPeningSendNetPacket();
        }

        if (isFlush) {
            channel.flush();
        }
    }

    /**
     * tick客户端发送的消息包
     * */
    public void tickClientInput(int intervalTime) {
        handleClientInput(ConfigUtils.CLIENT_INPUT_HANDLE_COUNT);
    }

    /**
     * 处理客户端发来的消息
     * */
    private void handleClientInput(int handleCount) {
        if (abstractObject == null) {
            return;
        }

        for (int i = 0; i < handleCount; i++) {
            NetPacket netPacket = popReceivedNetPacket();
            if (netPacket == null) {
                break;
            }

            Dispatcher.dispatchClientPacket(abstractObject, netPacket);
        }
    }

    /**
     * tick逻辑
     * */
    public void tickLogic(int intervalTime) {
        if (abstractObject == null) {
            return;
        }

        abstractObject.tick(intervalTime);
    }

    /**
     * tick向客户端发送消息
     * */
    public void tickClientOutput(int intervalTime) {
        handleOutput(ConfigUtils.CLIENT_OUTPUT_HANDLE_COUNT);
    }

    //region getter/setter
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public SessionStatusEnum getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(SessionStatusEnum sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public SessionExceptionEnum getSessionException() {
        return sessionException;
    }

    public void setSessionException(SessionExceptionEnum sessionException) {
        this.sessionException = sessionException;
    }

    public AbstractObject getAbstractObject() {
        return abstractObject;
    }

    public void setAbstractObject(AbstractObject abstractObject) {
        this.abstractObject = abstractObject;
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    //endregion

}



























