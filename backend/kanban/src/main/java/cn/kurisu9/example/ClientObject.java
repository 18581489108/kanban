package cn.kurisu9.example;

import cn.kurisu9.loop.logic.AbstractObject;
import cn.kurisu9.loop.manager.SessionExceptionEnum;
import com.google.protobuf.Message;

/**
 * @author kurisu9
 * @description 模拟客户端对象
 * @date 2018/9/17 20:27
 **/
public class ClientObject extends AbstractObject {
    private boolean isLoginVerifyOk = false;

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
     * 处理异常
     *
     * @param exception
     */
    @Override
    protected void handleException(SessionExceptionEnum exception) {

    }

    /**
     * 登录
     */
    @Override
    public void login() {

    }

    /**
     * 是否登录验证成功
     */
    @Override
    public boolean isLoginVerifyOk() {
        return isLoginVerifyOk;
    }

    public void setLoginVerifyOk(boolean loginVerifyOk) {
        isLoginVerifyOk = loginVerifyOk;
    }

    /**
     * 是否可以被销毁
     */
    @Override
    public boolean canBeDestroyed() {
        return false;
    }

    /**
     * 由子类实现具体销毁
     */
    @Override
    protected void doDestroy() {

    }

    /**
     * 交由子类来实现重复登录
     */
    @Override
    protected boolean doRepeatLogin() {
        return false;
    }

    /**
     * 交由子类来实现断线重连
     */
    @Override
    protected boolean doResume() {
        return false;
    }

    public void senClientPacket(Message packet) {
        short packetId = PacketId.getPacketId(packet.getClass());
        if (packetId > 0 && session != null) {
            session.sendNetPacket(packetId, -1, -1, packet);
        }
    }
}
