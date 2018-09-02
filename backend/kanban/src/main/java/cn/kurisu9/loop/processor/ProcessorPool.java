package cn.kurisu9.loop.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kurisu9
 * @description 线程池
 * @date 2018/9/2 15:18
 **/
public class ProcessorPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorPool.class);

    private static final ProcessorPool INSTANCE = new ProcessorPool();

    /**
     * 线程容器
     * key: 线程名
     * value: 线程
     * */
    private Map<String, Thread> threadMap = new ConcurrentHashMap<>();

    /**
     * 逻辑处理容器
     * key: 线程id
     * value: 逻辑线程
     * */
    private Map<Integer, AbstractProcessor> processorMap = new ConcurrentHashMap<>();


    /**
     * 当前拥有的最大线程id
     * */
    private AtomicInteger maxProcessorId = new AtomicInteger();

    public static ProcessorPool getInstance() {
        return INSTANCE;
    }

    /**
     * 运行线程
     * @param processor 逻辑线程
     * @param milliseconds tick的时间间隔，单位毫秒
     * @return 逻辑线程的id
     */
    public int run(AbstractProcessor processor, int milliseconds) {
        int processorId = maxProcessorId.getAndAdd(1);

        String name = processor.getProcessorType().getName() + "-" + processorId;

        processor.setTickSleepTimeMilli(milliseconds);
        processor.setProcessorName(name);

        Thread thread = new Thread(processor, name);
        thread.start();

        threadMap.put(name, thread);
        processorMap.put(processorId, processor);

        return processorId;
    }

    /**
     * 根据类型来获取指定的逻辑线程
     * @param type 逻辑线程的类型
     * @return
     */
    public List<AbstractProcessor> getProcessorsByType(ProcessorTypeEnum type) {
        List<AbstractProcessor> list = new ArrayList<>();

        Iterator<Map.Entry<Integer, AbstractProcessor>> iterator = processorMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, AbstractProcessor> entry = iterator.next();
            AbstractProcessor processor = entry.getValue();
            if (type.equals(processor.getProcessorType())) {
                list.add(processor);
            }
        }

        return list;
    }

    /**
     * 获取目标类型中第一个逻辑线程
     * @param type
     * @return
     */
    public AbstractProcessor getFirstProcessorByType(ProcessorTypeEnum type) {
        Iterator<Map.Entry<Integer, AbstractProcessor>> iterator = processorMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, AbstractProcessor> entry = iterator.next();
            AbstractProcessor processor = entry.getValue();
            if (type.equals(processor.getProcessorType())) {
                return processor;
            }
        }

        return null;
    }

    /**
     * 通过id获取指定的逻辑线程
     * */
    public AbstractProcessor getProcesserById(int processorId) {
        return processorMap.get(processorId);
    }

    /**
     * 发送线程间的消息
     * @param processorId
     * @param message
     * @return
     */
    public boolean sendProcessorMessage(int processorId, AbstractProcessorMessage message) {
        AbstractProcessor processor = getProcesserById(processorId);
        if (processor == null) {
            LOGGER.warn("processorId: {} nod found, thread message class: {}", processorId, message.getClass().getName());
            return false;
        }

        processor.pushProcessorMessage(message);
        return true;
    }

    /**
     * 发送线程间的消息
     * */
    public boolean sendProcessorMessage(AbstractProcessor processor, AbstractProcessorMessage message) {
        processor.pushProcessorMessage(message);
        return true;
    }
}

















