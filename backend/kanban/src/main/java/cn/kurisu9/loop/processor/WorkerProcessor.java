package cn.kurisu9.loop.processor;

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

    }

    /**
     * 由子类来实现初始化函数
     */
    @Override
    public boolean init() {
        return true;
    }

    /**
     * 是否空闲
     * */
    public AtomicBoolean isIdle() {
        // TODO
        return idle;
    }

    /**
     * 关闭工作线程
     * */
    public void shutdown() {

    }
}





















