package cn.kurisu9.loop.reflect;

import cn.kurisu9.loop.reflect.annotation.MessageHandler;
import cn.kurisu9.loop.reflect.annotation.MessageModule;
import cn.kurisu9.loop.reflect.annotation.PacketHandler;
import cn.kurisu9.loop.reflect.annotation.PacketModule;
import cn.kurisu9.loop.util.SpringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author kurisu9
 * @description 加载注解配置的处理模块
 * @date 2018/9/15 17:12
 **/
public class ModuleLoader {
    private static final ModuleLoader INSTANCE = new ModuleLoader();

    public static ModuleLoader getInstance() {
        return INSTANCE;
    }

    /**
     * 注册网络包处理
     * */
    public void registerPacket() {
        registerModule(PacketModule.class, PacketHandler.class,
                (Object module, Method method) -> {
                    PacketHandler handler = method.getAnnotation(PacketHandler.class);
                    short id = handler.value();
                    Dispatcher.registerPacket(id, module, method);
        });
    }

    /**
     * 注册线程间消息处理
     * */
    public void registerMessage() {
        registerModule(MessageModule.class, MessageHandler.class,
                (Object module, Method method) -> {
                    MessageHandler handler = method.getAnnotation(MessageHandler.class);
                    short id = handler.value();
                    Dispatcher.registerMessage(id, module, method);
                });
    }

    /**
     * 注册模块
     * @param moduleClass   扫描的模块注解
     * @param handlerClass  扫描的处理器注解
     * @param register      注册方式
     */
    private void registerModule(Class<? extends Annotation> moduleClass, Class<? extends Annotation> handlerClass, HandlerRegister register) {
        Map<String, Object> modules = SpringUtils.getApplicationContext().getBeansWithAnnotation(moduleClass);
        for (Object obj : modules.values()) {
            Method[] methods = obj.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(handlerClass)) {
                    register.register(obj, method);
                }
            }
        }
    }

    @FunctionalInterface
    private interface HandlerRegister {
        /**
         * 注册
         * @param module    模块
         * @param method    模块下的处理方法
         */
        void register(Object module, Method method);
    }
}
























