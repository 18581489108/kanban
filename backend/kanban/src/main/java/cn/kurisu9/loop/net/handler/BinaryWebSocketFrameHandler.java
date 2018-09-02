package cn.kurisu9.loop.net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kurisu9 on 2018/9/1.
 */
public class BinaryWebSocketFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinaryWebSocketFrameHandler.class);

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {

        ByteBuf byteBuf = msg.content();
        int bufLen = byteBuf.readableBytes();

        byteBuf.markReaderIndex();

        byte[] body = new byte[bufLen];
        byteBuf.readBytes(body);

        System.out.println(new String(body));
    }
}
