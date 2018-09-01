package cn.kurisu9.loop.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhaoxin_m on 2018/9/1.
 */
public class NettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    /**
     * 监听的主机
     * */
    private String host;

    /**
     * 监听的端口号
     * */
    private int port;

    /**
     * netty的状态
     * */
    private NettyStatusEnum nettyStatus = NettyStatusEnum.INIT;

    private ChannelFuture channelFuture;

    /**
     * netty 的accept线程组
     * */
    private EventLoopGroup bossGroup;

    /**
     * netty 的io线程组
     **/
    private EventLoopGroup workerGroup;

    private ChannelInitializer<SocketChannel> initializer;

    public NettyServer(String host, int port, ChannelInitializer<SocketChannel> initializer) {
        this.host = host;
        this.port = port;
        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup(1);
        this.initializer = initializer;
    }


    /**
     * 启动netty
     * */
    public void boot() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(initializer);

        try {
            channelFuture = bootstrap.bind(host, port).sync();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
            nettyStatus = NettyStatusEnum.SHUTTING_DOWN;
        }
    }

    /**
     * 停止
     * */
    public void stop() {
        if (channelFuture != null && channelFuture.channel() != null) {
            LOGGER.info("Netty TCP Server Stop, host:{}, port:{}", host, port);
            channelFuture.channel().close();
        }

        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public NettyStatusEnum getNettyStatus() {
        return nettyStatus;
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }
}







































