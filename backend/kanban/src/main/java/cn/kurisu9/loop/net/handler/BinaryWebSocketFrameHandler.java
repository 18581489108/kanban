package cn.kurisu9.loop.net.handler;

import cn.kurisu9.loop.entity.Session;
import cn.kurisu9.loop.manager.SessionExceptionEnum;
import cn.kurisu9.loop.message.AddNewSessionMessage;
import cn.kurisu9.loop.net.codec.NetPacket;
import cn.kurisu9.loop.processor.AbstractProcessor;
import cn.kurisu9.loop.processor.ProcessorPool;
import cn.kurisu9.loop.processor.ProcessorTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kurisu9 on 2018/9/1.
 */
public class BinaryWebSocketFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinaryWebSocketFrameHandler.class);

    private Session session = null;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        session = new Session();
        session.setChannel(ctx.channel());

        AbstractProcessor workerProcessor = ProcessorPool.getInstance().getFirstProcessorByType(ProcessorTypeEnum.WORKER);
        if (workerProcessor == null) {
            LOGGER.error("no worker processor, close channel");
            ctx.close();
            return;
        }

        AddNewSessionMessage message = new AddNewSessionMessage();
        message.setSession(session);
        message.setClient(true);
        ProcessorPool.getInstance().sendProcessorMessage(workerProcessor, message);

        LOGGER.info("new channel ctx {}, remote address:{}", ctx.name(), ctx.channel().remoteAddress().toString());
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("channel ctx {} is inactive, remote address:{}", ctx.name(), ctx.channel().remoteAddress().toString());
        exception();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        exception();
    }

    private void exception() {
        if (session == null) {
            return;
        }

        if (SessionExceptionEnum.NONE.equals(session.getSessionException())) {
            session.setValid(false);
            session.setSessionException(SessionExceptionEnum.NET_EXCEPTION);
        }

        session = null;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            LOGGER.info("channel ctx {} is tcp timeout, remote address:{}", ctx.name(), ctx.channel().remoteAddress().toString());
            ctx.close();
            return;
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
        if (session == null) {
            LOGGER.warn("session is null, ctx {}, remote address:{}", ctx.name(), ctx.channel().remoteAddress().toString());
            return;
        }

        /*
        ByteBuf byteBuf = msg.content();
        int bufLen = byteBuf.readableBytes();

        byteBuf.markReaderIndex();

        byte[] body = new byte[bufLen];
        byteBuf.readBytes(body);

        System.out.println(new String(body));
        */
        NetPacket netPacket = new NetPacket();
        // TODO 构建消息包
        session.pushReceivedNetPacket(netPacket);
    }
}
