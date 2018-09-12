package cn.kurisu9.loop.message;

import cn.kurisu9.loop.entity.Session;
import cn.kurisu9.loop.processor.AbstractProcessorMessage;

/**
 * @author kurisu9
 * @description 添加新的session的消息
 * @date 2018/9/12 21:58
 **/
public class AddNewSessionMessage extends AbstractProcessorMessage {

    public AddNewSessionMessage() {
        super(CoreMessageId.ADD_NEW_SESSION);
    }

    private Session session;

    /**
     * 是否是客户端
     * */
    private boolean isClient;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public boolean isClient() {
        return isClient;
    }

    public void setClient(boolean client) {
        isClient = client;
    }
}
