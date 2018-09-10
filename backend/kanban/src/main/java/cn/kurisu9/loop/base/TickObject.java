package cn.kurisu9.loop.base;

/**
 * @author kurisu9
 * @description 可被tick的对象
 * @date 2018/9/3 19:08
 **/
public interface TickObject {
    /**
     * tick
     * @param intervalTime tick的间隔时间
     * */
    void tick(int intervalTime);
}
