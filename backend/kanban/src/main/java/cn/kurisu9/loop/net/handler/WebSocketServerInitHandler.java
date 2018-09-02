package cn.kurisu9.loop.net.handler;

import cn.kurisu9.loop.net.codec.NetPacketDecode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by kurisu9 on 2018/9/1.
 */
public class WebSocketServerInitHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // websocket协议本身是基于http协议的，所以这边也要使用http解编码器
        pipeline.addLast(new HttpServerCodec());
        // 以块的方式来写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        // netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
        pipeline.addLast(new HttpObjectAggregator(8192));

        // ws://server:port/context_path
        // ws://localhost:9999/ws
        // 参数指的是contex_path
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //pipeline.addLast(new NetPacketDecode());
        // websocket定义了传递数据的6种frame类型
        //pipeline.addLast(new TextWebSocketFrameHandler());
        pipeline.addLast(new BinaryWebSocketFrameHandler());

    }
}
