package cn.kurisu9.loop.logic;

import cn.kurisu9.loop.base.TickObject;
import cn.kurisu9.loop.event.AbstractEvent;
import cn.kurisu9.loop.processor.WorkerProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kurisu9
 * @description 封装了在workerProcessor中的顶层逻辑抽象
 * @date 2018/9/4 10:35
 **/
public abstract class AbstractContainerLogic implements TickObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractContainerLogic.class);

    /**
     * 寄宿的工作线程
     * */
    private WorkerProcessor workerProcessor;

    /**
     * 初始化
     * */
    public abstract boolean init();

    /**
     * 新的object进入
     * */
    protected abstract void enterObject(AbstractObject object);

    /**
     * 新的object进入
     * */
    public void enter(AbstractObject object) {
        // 防止在极短的时间内，断线重连处理逻辑引起的AbstractContainerLogic没有进行enter回调
        if (object.isEntered()) {
            return;
        }

        object.setContainerLogic(this);
        enterObject(object);

        object.setEntered(true);
    }

    /**
     * object对象离开
     * */
    protected abstract void leaveObject(AbstractObject object);

    /**
     * object对象离开
     * */
    public void leave(AbstractObject object) {
        leaveObject(object);
        object.setContainerLogic(null);
    }

    /**
     * 添加事件
     * */
    public void addEvent(AbstractEvent event) {
        if (workerProcessor != null) {
            workerProcessor.addEvent(event);
        }
    }

    //region getter/setter
    public WorkerProcessor getWorkerProcessor() {
        return workerProcessor;
    }

    public void setWorkerProcessor(WorkerProcessor workerProcessor) {
        this.workerProcessor = workerProcessor;
    }
    //endregion

}






























