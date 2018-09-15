package cn.kurisu9.loop.message;

import cn.kurisu9.loop.entity.Session;
import cn.kurisu9.loop.processor.WorkerProcessor;
import cn.kurisu9.loop.reflect.annotation.MessageHandler;
import cn.kurisu9.loop.reflect.annotation.MessageModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kurisu9
 * @description 消息处理模块
 * @date 2018/9/15 20:58
 **/
@MessageModule
public class CoreMessageModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreMessageModule.class);

    @MessageHandler(CoreMessageId.ADD_NEW_SESSION)
    public void handleAddNewSession(WorkerProcessor workerProcessor, AddNewSessionMessage message) {
        Session session = message.getSession();
        if (message.isClient()) {
            workerProcessor.getSessionManager().addNewClientSession(session);
            return;
        }

        LOGGER.error("do not handle new session, message id:{}", message.getMessageId());
    }
}
