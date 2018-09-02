package cn.kurisu9.loop.net.codec;

/**
 * @author kurisu9
 * @description 网络通信解析成的数据包
 * @date 2018/9/2 13:15
 **/
public class NetPacket {

    /**
     * 数据包id
     * */
    private short packetId;

    /**
     * 数据包长度
     * */
    private int packetLen;

    /**
     * 目标id
     * -1 表示是客户端
     * */
    private int dstId = -1;

    /**
     * 发送源id
     * 在目标id为客户端时也使用-1表示
     * */
    private int srcId = -1;

    /**
     * 包体
     * */
    private byte[] body = null;


    public NetPacket() {
    }

    public NetPacket(short packetId, int packetLen, int dstId, int srcId, byte[] body) {
        this.packetId = packetId;
        this.packetLen = packetLen;
        this.dstId = dstId;
        this.srcId = srcId;
        this.body = body;
    }

    public short getPacketId() {
        return packetId;
    }

    public void setPacketId(short packetId) {
        this.packetId = packetId;
    }

    public int getPacketLen() {
        return packetLen;
    }

    public void setPacketLen(int packetLen) {
        this.packetLen = packetLen;
    }

    public int getDstId() {
        return dstId;
    }

    public void setDstId(int dstId) {
        this.dstId = dstId;
    }

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}






















