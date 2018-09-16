package cn.kurisu9.loop.net.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author kurisu9
 * @description WebSocket 的协议解析
 * @date 2018/9/16 18:07
 **/
@ChannelHandler.Sharable
public class WebSocketProtoCodec extends MessageToMessageCodec<WebSocketFrame, NetPacket> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketProtoCodec.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, NetPacket msg, List<Object> out) throws Exception {
        if (msg == null) {
            LOGGER.warn("NetPacket is Null.");
            throw new IllegalStateException("NetPacket is Null.");
        }

        short packetId = msg.getPacketId();

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeShort(packetId);
        byteBuf.writeInt(msg.getPacketLen());
        byteBuf.writeBytes(msg.getBody());

        out.add(new BinaryWebSocketFrame(byteBuf));
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = msg.content();
        int bufLen = byteBuf.readableBytes();

        if (bufLen < 6) {
            return;
        }

        short packetID = byteBuf.readShort();
        int packetLength = byteBuf.readInt();

        if (packetID < 0 || packetLength <= 0) {
            LOGGER.warn("packet id {} or packet length {} invalid", packetID, packetLength);
            throw new IllegalStateException("Packet format invalid.");
        }

        NetPacket netPacket = new NetPacket();
        netPacket.setPacketId(packetID);
        netPacket.setPacketLen(packetLength);

        byte[] body = new byte[packetLength];
        byteBuf.readBytes(body);
        netPacket.setBody(body);
    }
}
















