package cn.kurisu9.loop.bootstrap;

import cn.kurisu9.loop.processor.*;
import cn.kurisu9.loop.util.ConfigUtils;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author kurisu9
 * @description 服务器启动
 * @date 2018/9/2 18:23
 **/
public class ServerBoot {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerBoot.class);

    /**
     * 闭锁
     * */
    public final static CountDownLatch mutex = new CountDownLatch(1);

    /**
     * 启动服务器
     * */
    public static void boot() {
        {
            boolean result = ConfigUtils.loadNettyConfigFromFile();
            if (!result) {
                LOGGER.error("loadNettyConfigFromFile failed");
                return;
            }

            MainProcessor mainProcessor = new MainProcessor();
            ProcessorPool.getInstance().run(mainProcessor, ConfigUtils.MAIN_PROCESSOR_TICK_MS);

        }

        while (true) {
            try {
                mutex.await();

                LOGGER.info("开始关闭服务器");
                // 关闭Netty线程
                ProcessorPool.getInstance().getProcessorsByType(ProcessorTypeEnum.MAIN).forEach(processor -> {
                    MainProcessor mainProcessor = (MainProcessor) processor;
                    mainProcessor.setServerStatus(ServerStatusEnum.SHUTTING_DOWN);
                });

                // 处理所有未完成的任务
                ProcessorPool.getInstance().getProcessorsByType(ProcessorTypeEnum.WORKER).forEach(processor -> {
                    WorkerProcessor workerProcessor = (WorkerProcessor) processor;
                    do {
                        // 暂停等待2秒钟,触发断线处理，并等待所有任务处理完成
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            LOGGER.error("InterruptedException", e);
                        }
                    } while ((!workerProcessor.isIdle().get()));
                });

                // 关闭所有开启的线程
                Arrays.stream(ProcessorTypeEnum.values()).forEach(type ->
                        ProcessorPool.getInstance().getProcessorsByType(type).forEach(processor -> {
                                    if (processor instanceof WorkerProcessor) {
                                        ((WorkerProcessor) processor).shutdown();
                                    }
                                    processor.setStatus(ProcessorStatusEnum.STOPPED);
                                    LOGGER.info("processor finish close,name = {}", processor.getProcessorName());
                                }
                        ));



                LOGGER.info("关闭服务器结束");
                LogManager.shutdown();

                break;
            }
            catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭服务器
     * */
    public static void shutdown() {
        mutex.countDown();
    }
}
























