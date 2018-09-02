package cn.kurisu9.loop.net.handler;

import cn.kurisu9.loop.entity.Session;
import cn.kurisu9.loop.manager.SessionExceptionEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kurisu9
 * @description 处理文字类的websocket消息
 * @date 2018/9/1 16:56
 **/
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);

    private Session session;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        session = new Session();
        session.setChannel(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("channel is inactive, remoteAddress:{}", ctx.channel().remoteAddress().toString());
        exception();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) throws Exception {
        System.out.println(msg.text());
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

    /**
     * 处理超时
     * */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            LOGGER.info("TCP timeout");
            ctx.channel().close();
            return;
        }
        super.userEventTriggered(ctx, evt);
    }
}
