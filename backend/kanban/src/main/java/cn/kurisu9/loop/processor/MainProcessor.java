package cn.kurisu9.loop.processor;

import cn.kurisu9.loop.bootstrap.ServerBoot;
import cn.kurisu9.loop.bootstrap.ServerStatusEnum;
import cn.kurisu9.loop.entity.NetConfig;
import cn.kurisu9.loop.net.NettyServer;
import cn.kurisu9.loop.net.NettyStatusEnum;
import cn.kurisu9.loop.net.handler.WebSocketServerInitHandler;
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
        nettyServer = new NettyServer(netConfig.getHost(), netConfig.getPort(), new WebSocketServerInitHandler());
        nettyServer.boot();


        WorkerProcessor workerProcessor = new WorkerProcessor();
        ProcessorPool.getInstance().run(workerProcessor, ConfigUtils.WORKER_PROCESSOR_TICK_MS);

        serverStatus = ServerStatusEnum.RUNNING;
        return true;
    }

    /**
     * tick服务状态
     * */
    private void tickServerStatus() {
        if (ServerStatusEnum.STOPPED.equals(serverStatus)) {
            ServerBoot.mutex.countDown();
            return;
        }

        if (ServerStatusEnum.SHUTTING_DOWN.equals(serverStatus)) {
            // 准备关闭nettyServer
            nettyServer.setNettyStatus(NettyStatusEnum.SHUTTING_DOWN);

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
                    LOGGER.info("----------server listening, ip:{}, port:{}----------", nettyServer.getHost(), nettyServer.getPort());
                }
                break;
            case SHUTTING_DOWN:
                LOGGER.info("----------shutdown server, ip:{}, port:{}----------", nettyServer.getHost(), nettyServer.getPort());
                nettyServer.stop();
                nettyServer.setNettyStatus(NettyStatusEnum.STOPED);
                break;
            default:
                break;
        }
    }

    public ServerStatusEnum getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(ServerStatusEnum serverStatus) {
        this.serverStatus = serverStatus;
    }
}















