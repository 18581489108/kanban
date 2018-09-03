package cn.kurisu9.loop.event;

import cn.kurisu9.loop.base.TickObject;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * @author zhaoxin_m
 * @description 事件引擎
 * @date 2018/9/3 18:26
 **/
public class EventEngine implements TickObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventEngine.class);

    /**
     * 执行事件的线程池
     * */
    private ExecutorService executorService;

    /**
     * 线程执行结果的列表
     * */
    private LinkedList<Future<AbstractEvent>> futures = new LinkedList<>();

    /**
     * 待执行的事件列表
     * */
    private LinkedList<AbstractEvent> events = new LinkedList<>();

    /**
     * 剩余可用的线程数
     * */
    private int threadRemainCount = 0;

    public EventEngine(int n) {
        ThreadFactory threadFactory = new DefaultThreadFactory("EventEngine Thread");
        executorService = Executors.newFixedThreadPool(n, threadFactory);
        threadRemainCount = n;
        LOGGER.info("Create EventEngine, thread count: {}", n);
    }


    /**
     * tick
     *
     * @param intervalTime tick的间隔时间
     */
    @Override
    public void tick(int intervalTime) {
        tickEvent(intervalTime);

        tickFuture(intervalTime);
    }

    private void tickEvent(int intervalTime) {
        if (threadRemainCount <= 0) {
            return;
        }

        if (events.size() <= 0) {
            return;
        }

        Iterator<AbstractEvent> iterator = events.iterator();
        while (iterator.hasNext()) {
            if (threadRemainCount <= 0) {
                break;
            }

            threadRemainCount--;
            AbstractEvent event = iterator.next();
            iterator.remove();
            Future<AbstractEvent> future = executorService.submit(event);
            futures.add(future);
        }
    }

    private void tickFuture(int intervalTime) {
        if (futures.size() <= 0) {
            return;
        }

        Iterator<Future<AbstractEvent>> iterator = futures.iterator();
        while (iterator.hasNext()) {
            Future<AbstractEvent> future = iterator.next();
            if (future.isDone()) {
                try {
                    AbstractEvent event = future.get();
                    event.callback();
                } catch (InterruptedException e) {
                    LOGGER.error("event future is interrupted");
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    LOGGER.error("event future is Execution");
                    e.printStackTrace();
                }

                iterator.remove();
            }
        }
    }

    /**
     * 添加事件
     * */
    public void addEvent(AbstractEvent event) {
        if (event != null) {
            events.add(event);
        }
    }

    /**
     * 是否空闲
     * */
    public boolean idle() {
        return futures.isEmpty() && events.isEmpty();
    }

    /**
     * 输出当前未完成的event或者正在执行/等待回调的event
     * */
    public void log() {
        LOGGER.debug("未完成的Event:{} | 正在执行/等待回调的Event:{}", events.size(), futures.size());
    }

    /**
     * 关闭事件引擎
     * */
    public void shutdown() {
        if (executorService != null) {
            this.executorService.shutdownNow();
        }
    }
}





























