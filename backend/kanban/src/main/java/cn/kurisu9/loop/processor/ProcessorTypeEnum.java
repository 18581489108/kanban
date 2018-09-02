package cn.kurisu9.loop.processor;

/**
 * @author kurisu9
 * @description 线程类型描述枚举类
 * @date 2018/9/2 14:43
 **/
public enum ProcessorTypeEnum {
    MAIN("MainProcessor"),
    WORKER("WorkerProcessor"),

    ;

    private String name;

    ProcessorTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
