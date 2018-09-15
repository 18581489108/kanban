package cn.kurisu9.loop.reflect;

import java.lang.reflect.Method;

/**
 * @author kurisu9
 * @description 封装了模块处理器的数据
 * @date 2018/9/15 17:47
 **/
public class ModuleHandlerData {
    /**
     * 对应的module类
     * */
    private Object module;

    /**
     * 处理方法
     * */
    private Method handler;

    public ModuleHandlerData(Object module, Method handler) {
        this.module = module;
        this.handler = handler;
    }

    public Object getModule() {
        return module;
    }

    public void setModule(Object module) {
        this.module = module;
    }

    public Method getHandler() {
        return handler;
    }

    public void setHandler(Method handler) {
        this.handler = handler;
    }
}
