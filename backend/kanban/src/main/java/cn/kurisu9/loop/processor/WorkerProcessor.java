package cn.kurisu9.loop.processor;

import cn.kurisu9.loop.event.AbstractEvent;
import cn.kurisu9.loop.event.EventEngine;
import cn.kurisu9.loop.logic.AbstractContainerLogic;
import cn.kurisu9.loop.util.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author kurisu9
 * @description 工作逻辑线程
 * @date 2018/9/2 18:17
 **/
public class WorkerProcessor extends AbstractProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerProcessor.class);

    /**
     * 是否空闲
     * */
    private AtomicBoolean idle = new AtomicBoolean(true);

    /**
     * 异步事件引擎
     * */
    private EventEngine eventEngine;

    /**
     * 应用层使用的容器逻辑对象
     * */
    private AbstractContainerLogic containerLogic;

    public WorkerProcessor() {
        setProcessorType(ProcessorTypeEnum.WORKER);
    }

    /**
     * 由子类来具体实现tick内容
     *
     * @param intervalTime 间隔时间，单位毫秒
     */
    @Override
    public void tick(int intervalTime) {
        eventEngine.tick(intervalTime);

        idle.set(eventEngine.idle());
    }

    /**
     * 由子类来实现初始化函数
     */
    @Override
    public boolean init() {

        containerLogic = ConfigUtils.createContainerLogic();
        containerLogic.setWorkerProcessor(this);


        eventEngine = new EventEngine(ConfigUtils.EVENT_THREAD_COUNT);

        return true;
    }

    /**
     * 添加事件
     * */
    public void addEvent(AbstractEvent event) {
        eventEngine.addEvent(event);
    }

    /**
     * 是否空闲
     * */
    public AtomicBoolean isIdle() {
        eventEngine.log();
        return idle;
    }

    /**
     * 关闭工作线程
     * */
    public void shutdown() {
        eventEngine.shutdown();
    }
}





















