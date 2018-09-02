package cn.kurisu9.loop.entity;

import cn.kurisu9.loop.manager.SessionExceptionEnum;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
