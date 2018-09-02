package cn.kurisu9.loop.bootstrap;

/**
 * @author kurisu9
 * @description 服务器状态描述类
 * @date 2018/9/2 17:10
 **/
public enum ServerStatusEnum {
    /**
     * 初始化
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
     * 停止
     * */
    STOPPED,

    ;
}
