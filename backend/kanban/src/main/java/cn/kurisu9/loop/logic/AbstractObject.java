package cn.kurisu9.loop.logic;

import cn.kurisu9.loop.base.TickObject;
import cn.kurisu9.loop.entity.Session;
import cn.kurisu9.loop.manager.SessionExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxin_m
 * @description 抽象对象，抽象了用于挂载session的对象
 * @date 2018/9/4 10:23
 **/
public abstract class AbstractObject implements TickObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractObject.class);

    /**
     * 连接产生的会话对象
     * */
    private Session session;

    /**
     * 是否已经进入过容器逻辑对象
     * */
    private boolean entered = false;

    /**
     * 寄宿在的容器逻辑对象
     * */
    private AbstractContainerLogic containerLogic = null;


    /**
     * 初始化
     * */
    public abstract boolean init();


    /**
     * 处理异常
     * */
    protected abstract void handleException(SessionExceptionEnum exception);

    /**
     * 处理异常
     * */
    public void exception(SessionExceptionEnum exception) {
        handleException(exception);
    }

    /**
     * 登录
     * */
    public abstract void login();

    /**
     * 是否登录验证成功
     * */
    public abstract boolean isLoginVerifyOk();

    /**
     * 是否可以被销毁
     * */
    public abstract boolean canBeDestroyed();

    /**
     * 由子类实现具体销毁
     * */
    protected abstract void doDestroy();

    /**
     * 销毁
     * */
    public void destroy() {
        doDestroy();
        session = null;
    }

    /**
     * 交由子类来实现重复登录
     * */
    protected abstract boolean doRepeatLogin();

    /**
     * 重复登录
     * */
    public boolean repeatLogin(Session newSession) {
        boolean result = doRepeatLogin();
        if (result) {
            setNewSession(newSession);
        }

        return result;
    }

    /**
     * 交由子类来实现断线重连
     * */
    protected abstract boolean doResume();

    /**
     * 断线重连
     * */
    public boolean resume(Session newSession) {
        boolean result = doResume();
        if (result) {
            setNewSession(newSession);
        }

        return result;
    }

    private void setNewSession(Session newSession) {
        session.setAbstractObject(null);
        session = newSession;
        session.setAbstractObject(this);
    }

    /**
     * 离开容器
     * */
    public void leaveContainer() {
        session = null;
    }

    /**
     * 获取uuid
     * */
    public long getUuid() {
        return session.getUuid();
    }

    //region getter/setter
    public boolean isEntered() {
        return entered;
    }

    public void setEntered(boolean entered) {
        this.entered = entered;
    }

    public AbstractContainerLogic getContainerLogic() {
        return containerLogic;
    }

    public void setContainerLogic(AbstractContainerLogic containerLogic) {
        this.containerLogic = containerLogic;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    //endregion

}



























