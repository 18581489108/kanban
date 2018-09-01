package cn.kurisu9.loop.entity;

/**
 * @author zhaoxin_m
 * @description session的状态
 * @date 2018/9/1 17:12
 **/
public enum  SessionStatusEnum {
    /**
     * 初始化中
     * */
    INIT,

    /**
     * 登录中
     * */
    LOGIN,

    /**
     * 在线
     * */
    ONLINE,

    /**
     * 断线
     * */
    OFFLINE,

    /**
     * 登出
     * */
    LOGOUT,

    ;
}
