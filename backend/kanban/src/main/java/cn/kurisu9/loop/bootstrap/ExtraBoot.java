package cn.kurisu9.loop.bootstrap;

/**
 * @author kurisu9
 * @description 额外的启动，主要是由外部逻辑进行
 * @date 2018/9/17 20:52
 **/
@FunctionalInterface
public interface ExtraBoot {
    void  boot();
}
