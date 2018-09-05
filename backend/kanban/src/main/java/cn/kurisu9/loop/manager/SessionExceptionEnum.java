package cn.kurisu9.loop.manager;

/**
 * @author kurisu9
 * @description session异常的原因
 * @date 2018/9/1 17:10
 **/
public enum SessionExceptionEnum {
    /**
     * 没有异常
     * */
    NONE,

    /**
     * 网络异常
     * */
    NET_EXCEPTION,

    /**
     * 网络包异常
     * */
    PACKET_EXCEPTION,

    /**
     * 登录超时
     * */
    LOGIN_TIMEOUT,

    /**
     * 重复登录
     * */
    REPEAT_LOGIN,

    /**
     * 主动与客户端断开连接
     * */
    ACTIVE_DISCONNECT,

    ;
}
