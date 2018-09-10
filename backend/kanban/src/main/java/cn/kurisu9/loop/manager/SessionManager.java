package cn.kurisu9.loop.manager;

import cn.kurisu9.loop.base.TickObject;
import cn.kurisu9.loop.entity.Session;
import cn.kurisu9.loop.entity.SessionStatusEnum;
import cn.kurisu9.loop.logic.AbstractContainerLogic;
import cn.kurisu9.loop.logic.ObjectTypeEnum;
import cn.kurisu9.loop.util.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author kurisu9
 * @description session管理
 * @date 2018/9/9 18:52
 **/
public class SessionManager implements TickObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionManager.class);

    /**
     * Session所在的容器逻辑
     * */
    private AbstractContainerLogic containerLogic;

    /**
     * 新连接上来的客户端session
     * */
    private LinkedList<Session> newClientSessionList = new LinkedList<>();

    /**
     * 正在登录的session
     * */
    private LinkedList<Session> loggingInClientSessionList = new LinkedList<>();

    /**
     * 正常运行的session
     * */
    private Map<Long, Session> clientSessionMap = new HashMap<>();

    /**
     * 发生异常的session
     * */
    private Map<Long, Session> exceptionClientSessionMap = new HashMap<>();

    /**
     * 等待销毁的session
     * */
    private Map<Long, Session> destroyClientSessionMap = new HashMap<>();

    /**
     * tick
     *
     * @param intervalTime tick的间隔时间
     */
    @Override
    public void tick(int intervalTime) {
        tickClient(intervalTime);
    }

    private void tickClient(int intervalTime) {
        tickNewClientSession(intervalTime);

        tickLoggingInClientSession(intervalTime);

        tickClientSession(intervalTime);
    }

    private void tickNewClientSession(int intervalTime) {
        if (newClientSessionList.isEmpty()) {
            return;
        }

        Iterator<Session> iterator = newClientSessionList.iterator();
        int count = 0;
        while (iterator.hasNext() && count < ConfigUtils.NEW_CLIENT_SESSION_HANDLE_COUNT) {
            Session session = iterator.next();
            count++;

            iterator.remove();

            LOGGER.info("new client session add to logging in list, remote address:{}", session.getChannel().remoteAddress());

            session.initObject(ObjectTypeEnum.CLIENT);
            loggingInClientSessionList.add(session);
            session.login();
            session.getAbstractObject().setContainerLogic(containerLogic);
        }
    }

    private void tickLoggingInClientSession(int intervalTime) {
        if (loggingInClientSessionList.isEmpty()) {
            return;
        }

        Iterator<Session> iterator = loggingInClientSessionList.iterator();
        int count = 0;
        while (iterator.hasNext() && count < ConfigUtils.LOGGING_IN_CLIENT_SESSION_HANDLE_COUNT) {
            Session session = iterator.next();
            count++;

            // 只处理登录消息
            session.tickLogin(intervalTime);

            if (!SessionExceptionEnum.NONE.equals(session.getSessionException())) {
                iterator.remove();
                LOGGER.info("logging in client session exception, exception type:{}", session.getSessionException());
                exceptionClientSessionMap.put(session.getUuid(), session);
                continue;
            }

            if (session.isLoginVerifyOk()) {
                session.setSessionStatus(SessionStatusEnum.ONLINE);
            }

            if (SessionStatusEnum.ONLINE.equals(session.getSessionStatus())) {
                iterator.remove();
                handleOnlineSession(session);
            }
        }
    }

    private void handleOnlineSession(Session newSession) {
        long uuid = newSession.getUuid();

        Session oldSession;


        // 说明该用户重复登录了
        if ((oldSession = getSessionFromClientSessions(uuid)) != null) {
            boolean handleResult = oldSession.repeatLogin(newSession);
            if (handleResult) {
                clientSessionMap.remove(uuid);
                enterNewSession(newSession);
                return;
            }

            closeAndDestroySession(newSession);
            return;
        }

        // 断线重连
        if ((oldSession = getSessionFromClientExceptionSessions(uuid)) != null) {
            boolean handleResult = oldSession.resume(newSession);
            if (handleResult) {
                exceptionClientSessionMap.remove(uuid);
                enterNewSession(newSession);
                return;
            }

            closeAndDestroySession(newSession);
            return;
        }

        // 断线重连
        if ((oldSession = getSessionFromClientDestroySessions(uuid)) != null) {
            boolean handleResult = oldSession.resume(newSession);
            if (handleResult) {
                destroyClientSessionMap.remove(uuid);
                enterNewSession(newSession);
                return;
            }

            closeAndDestroySession(newSession);
            return;
        }

        enterNewSession(newSession);
    }

    /**
     * 将新session进行enter
     * */
    private void enterNewSession(Session newSession) {
        containerLogic.enter(newSession.getAbstractObject());
        clientSessionMap.put(newSession.getUuid(), newSession);
    }

    private void closeAndDestroySession(Session session) {
        session.closeChannel();
        session.clear();
    }

    private void tickClientSession(int intervalTime) {
        if (clientSessionMap.isEmpty()) {
            return;
        }

        Iterator<Map.Entry<Long, Session>> iterator = clientSessionMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Session> entry = iterator.next();
            Session session = entry.getValue();

            // 如果session发生了异常
            if (!SessionExceptionEnum.NONE.equals(session.getSessionException())) {
                iterator.remove();
                LOGGER.info("session exception, exception type:{}", session.getSessionException());
                exceptionClientSessionMap.put(session.getUuid(), session);
                continue;
            }

            session.tickClientInput(intervalTime);
            session.tickLogic(intervalTime);
            session.tickClientOutput(intervalTime);
        }
    }

    private Session getSessionFromClientSessions(long uuid) {
        return clientSessionMap.get(uuid);
    }

    private Session getSessionFromClientExceptionSessions(long uuid) {
        return exceptionClientSessionMap.get(uuid);
    }

    private Session getSessionFromClientDestroySessions(long uuid) {
        return destroyClientSessionMap.get(uuid);
    }

    //region getter/setter

    public AbstractContainerLogic getContainerLogic() {
        return containerLogic;
    }

    public void setContainerLogic(AbstractContainerLogic containerLogic) {
        this.containerLogic = containerLogic;
    }

    //endregion
}







































