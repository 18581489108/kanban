package cn.kurisu9.loop.entity;

/**
 * @author kurisu9
 * @description 网络通信的配置
 * @date 2018/9/2 17:18
 **/
public class NetConfig {
    /**
     * 监听的主机
     * */
    private String host;

    /**
     * 对应的端口号
     * */
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
