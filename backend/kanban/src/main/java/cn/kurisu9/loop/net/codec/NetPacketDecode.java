package cn.kurisu9.loop.net.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zhaoxin_m
 * @description TODO
 * @date 2018/9/1 20:49
 **/
public class NetPacketDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int bufLen = byteBuf.readableBytes();

        byteBuf.markReaderIndex();

        byte[] body = new byte[bufLen];
        byteBuf.readBytes(body);

        System.out.println(new String(body));
    }
}
