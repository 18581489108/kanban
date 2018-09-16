package cn.kurisu9.loop.processor;

import cn.kurisu9.loop.bootstrap.ServerStatusEnum;
import cn.kurisu9.loop.entity.NetConfig;
import cn.kurisu9.loop.net.NettyServer;
import cn.kurisu9.loop.net.NettyStatusEnum;
import cn.kurisu9.loop.net.handler.WebSocketServerInitializer;
import cn.kurisu9.loop.util.ConfigUtils;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kurisu9
 * @description 主逻辑线程 整个逻辑的服务线程
 * @date 2018/9/2 17:07
 **/
public class MainProcessor extends AbstractProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainProcessor.class);

    /**
     * 服务状态
     * */
    private volatile ServerStatusEnum serverStatus = ServerStatusEnum.INIT;

    /**
     * 网络通信服务
     * */
    private NettyServer nettyServer;

    /**
     * 工作线程
     * */
    private WorkerProcessor workerProcessor;

    public MainProcessor() {
        setProcessorType(ProcessorTypeEnum.MAIN);
    }


    /**
     * @param intervalTime 间隔时间，单位毫秒
     */
    @Override
    public void tick(int intervalTime) {
        tickNettyServer();

        tickServerStatus();
    }

    /**
     * 由子类来实现初始化函数
     */
    @Override
    public boolean init() {
        // 启动网络接收层的netty
        NetConfig netConfig = ConfigUtils.getNetConfig();
        nettyServer = new NettyServer(netConfig.getHost(), netConfig.getPort(), new WebSocketServerInitializer());
        nettyServer.boot();


        workerProcessor = new WorkerProcessor();
        ProcessorPool.getInstance().run(workerProcessor, ConfigUtils.WORKER_PROCESSOR_TICK_MS);

        //serverStatus = ServerStatusEnum.RUNNING;
        return true;
    }

    /**
     * tick服务状态
     * */
    private void tickServerStatus() {
        if (ServerStatusEnum.STOPPED.equals(serverStatus)) {
            //ServerBoot.mutex.countDown();
            return;
        }

        if (ServerStatusEnum.SHUTTING_DOWN.equals(serverStatus)) {
            // 关闭nettyServer
            stopNettyServer();

            serverStatus = ServerStatusEnum.STOPPED;
        }
    }

    private void tickNettyServer() {
        ChannelFuture channelFuture = nettyServer.getChannelFuture();
        NettyStatusEnum nettyStatus = nettyServer.getNettyStatus();

        switch (nettyStatus) {
            case INIT:
                if (channelFuture.channel().isWritable()) {
                    nettyServer.setNettyStatus(NettyStatusEnum.RUNNING);
                    LOGGER.info("----------netty server listening, ip:{}, port:{}----------", nettyServer.getHost(), nettyServer.getPort());
                }
                break;
            case SHUTTING_DOWN:
                stopNettyServer();
                break;
            default:
                break;
        }
    }

    /**
     * 关闭nettyServer
     * */
    private void stopNettyServer() {
        LOGGER.info("----------shutdown netty server, ip:{}, port:{}----------", nettyServer.getHost(), nettyServer.getPort());
        nettyServer.stop();
        nettyServer.setNettyStatus(NettyStatusEnum.STOPPED);
    }

    public ServerStatusEnum getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(ServerStatusEnum serverStatus) {
        this.serverStatus = serverStatus;
    }
}















