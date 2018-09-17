package cn.kurisu9.example;

import cn.kurisu9.loop.logic.AbstractContainerLogic;
import cn.kurisu9.loop.logic.AbstractObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kurisu9
 * @description 逻辑容器的模拟
 * @date 2018/9/17 20:46
 **/
public class ExampleContainerLogic extends AbstractContainerLogic {
    public Map<Long, AbstractObject> objectMap = new HashMap<>();
    /**
     * tick
     *
     * @param intervalTime tick的间隔时间
     */
    @Override
    public void tick(int intervalTime) {

    }

    /**
     * 初始化
     */
    @Override
    public boolean init() {
        return true;
    }

    /**
     * 新的object进入
     *
     * @param object
     */
    @Override
    protected void enterObject(AbstractObject object) {
        objectMap.put(object.getUuid(), object);
    }

    /**
     * object对象离开
     *
     * @param object
     */
    @Override
    protected void leaveObject(AbstractObject object) {
        objectMap.remove(object.getUuid());
    }
}
