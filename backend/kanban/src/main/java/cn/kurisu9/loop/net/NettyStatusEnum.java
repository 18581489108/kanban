package cn.kurisu9.loop.net;

/**
 * Created by kurisu9 on 2018/9/1.
 */
public enum NettyStatusEnum {
    /**
     * 初始化中
     * */
    INIT,

    /**
     * 运行中
     * */
    RUNNING,

    /**
     * 关闭中
     * */
    SHUTTING_DOWN,

    /**
     * 关闭
     * */
    STOPED,

    ;
}
