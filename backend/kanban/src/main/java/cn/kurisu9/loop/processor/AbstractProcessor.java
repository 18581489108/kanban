package cn.kurisu9.loop.processor;

import cn.kurisu9.loop.reflect.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author kurisu9
 * @description 抽象的逻辑线程对象
 * @date 2018/9/2 14:28
 **/
public abstract class AbstractProcessor implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProcessor.class);

    /**
     * 线程名字
     * */
    private String processorName;

    /**
     * 存放线程间消息
     * */
    protected ConcurrentLinkedQueue<AbstractProcessorMessage> processorMessages = new ConcurrentLinkedQueue<>();

    /**
     * processor状态
     * */
    private volatile ProcessorStatusEnum status = ProcessorStatusEnum.INIT;

    /**
     * tick的时间间隔，单位毫秒
     * */
    private int tickSleepTimeMilli;

    /**
     * 线程类型
     * */
    private ProcessorTypeEnum processorType;

    /**
     * 上次执行run的毫秒级时间戳
     * */
    private long lastRunMT = 0;

    public AbstractProcessor() {
    }

    /**
     * 由子类来具体实现tick内容
     *
     * @param intervalTime 间隔时间，单位毫秒
     * */
    protected abstract void tick(int intervalTime);

    /**
     * 由子类来实现初始化函数
     * */
    protected abstract boolean init();

    /**
     * 处理线程间的消息
     * */
    private void processMessage() {
        for (int i = 0; i < 100; i++) {
            AbstractProcessorMessage message = popProcessorMessage();
            if (message == null) {
                break;
            }

            Dispatcher.dispatchMessage(this, message, message.getMessageId());
        }
    }

    @Override
    public void run() {
        lastRunMT = System.currentTimeMillis();

        int busyTick = 0;

        loop: while (true) {
            switch (status) {
                case INIT:
                    // 如果初始化失败，则结束
                    if (!init()) {
                        LOGGER.error("processor {} init failure", processorName);
                        break loop;
                    }
                    status = ProcessorStatusEnum.RUNNING;
                    break;
                case RUNNING:
                    long currentMT = System.currentTimeMillis();
                    long delta = currentMT - lastRunMT;

                    if (delta <= tickSleepTimeMilli) {
                        // 如果上次运行到这次运行的时间差小于了每次tick的时间
                        // 则将线程睡眠，补上时间差
                        try {
                            Thread.sleep(tickSleepTimeMilli - delta);
                        } catch (InterruptedException e) {
                            LOGGER.error(e.getMessage(), e);
                            e.printStackTrace();
                        }
                    } else {
                        // 每次tick的时间超过了目标时间，此时进入了忙状态
                        busyTick++;
                        if (busyTick > 10) {
                            busyTick = 0;
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                LOGGER.error(e.getMessage(), e);
                                e.printStackTrace();
                            }
                        }
                    }

                    currentMT = System.currentTimeMillis();
                    try {
                        // 处理线程间消息
                        processMessage();

                        tick((int) (currentMT - lastRunMT));
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                        e.printStackTrace();
                    } finally {
                        lastRunMT = currentMT;
                    }

                    break;
                case STOPPED:
                    LOGGER.info("processor {} status was stopped", processorName);
                    break loop;
                default:
                    LOGGER.error("processor {} error status: {}", processorName, status);
                    break loop;
            }
        }
    }

    /**
     * 存放线程间的消息
     * */
    public void pushProcessorMessage(AbstractProcessorMessage message) {
        processorMessages.add(message);
    }

    /**
     * 获取线程间的消息
     * */
    private AbstractProcessorMessage popProcessorMessage() {
        return processorMessages.poll();
    }

    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public int getTickSleepTimeMilli() {
        return tickSleepTimeMilli;
    }

    public void setTickSleepTimeMilli(int tickSleepTimeMilli) {
        this.tickSleepTimeMilli = tickSleepTimeMilli;
    }

    public ProcessorTypeEnum getProcessorType() {
        return processorType;
    }

    public void setProcessorType(ProcessorTypeEnum processorType) {
        this.processorType = processorType;
    }

    public ProcessorStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProcessorStatusEnum status) {
        this.status = status;
    }
}






























