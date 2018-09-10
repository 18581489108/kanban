package cn.kurisu9.loop.event;

import java.util.concurrent.Callable;

/**
 * @author kurisu9
 * @description 事件的抽象类
 * @date 2018/9/3 18:16
 **/
public abstract class AbstractEvent implements Callable<AbstractEvent> {
    /**
     * 执行事件
     * */
    protected abstract void execute();

    /**
     * 回调，需要的数据需要在execute里进行保存
     * */
    protected abstract void callback();


    @Override
    public AbstractEvent call() throws Exception {
        execute();
        return this;
    }
}
