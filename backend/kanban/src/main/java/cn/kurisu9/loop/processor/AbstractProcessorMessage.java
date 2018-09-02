package cn.kurisu9.loop.processor;

/**
 * @author kurisu9
 * @description 线程间的消息
 * @date 2018/9/2 14:33
 **/
public abstract class AbstractProcessorMessage {
    private int messageId = -1;

    public AbstractProcessorMessage(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
