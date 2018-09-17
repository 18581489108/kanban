package cn.kurisu9.loop.util;

import cn.kurisu9.loop.entity.NetConfig;
import cn.kurisu9.loop.logic.AbstractContainerLogic;
import cn.kurisu9.loop.logic.AbstractObject;
import cn.kurisu9.loop.logic.ObjectTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * @author kurisu9
 * @description 配置相关的工具类
 * @date 2018/9/2 17:22
 **/
public class ConfigUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);

    /**
     * 网络包的基础长度
     * */
    public static final int NET_PACKET_BASE_LEN = 14;

    /**
     * 主逻辑线程的tick毫秒数为100
     * */
    public static final int MAIN_PROCESSOR_TICK_MS = 100;

    /**
     * 工作逻辑线程的tick毫秒数为10
     * */
    public static final int WORKER_PROCESSOR_TICK_MS = 10;

    /**
     * 事件线程数
     * */
    public static final int EVENT_THREAD_COUNT = 2;

    //region session对象使用的配置
    /**
     * 登录耗时的时间限制，单位毫秒
     *
     * 5分钟
     * */
    public static final int LOGIN_ELAPSED_LIMIT_TIME = 300000;

    /**
     * 每次tick客户端输入的处理的个数
     * */
    public static final int CLIENT_INPUT_HANDLE_COUNT = 20;

    /**
     * 每次tick向客户端发送消息的个数
     * */
    public static final int CLIENT_OUTPUT_HANDLE_COUNT = 10;
    //endregion

    //region SessionManager使用的配置
    /**
     * 每次tick处理的新连接的客户端session的处理个数
     * */
    public static final int NEW_CLIENT_SESSION_HANDLE_COUNT = 100;

    /**
     * 每次tick处理客户端session登录请求的处理个数
     * */
    public static final int LOGGING_IN_CLIENT_SESSION_HANDLE_COUNT = 100;
    //endregion

    //region Dispatcher使用的配置

    /**
     * 最大的消息id 不包含这个值
     * */
    public static final int MAX_MESSAGE_ID = 9999;

    /**
     * 最大的网络消息包id 不包含这个值
     * */
    public static final int MAX_PACKET_ID = 9999;

    //endregion


    /**
     * 环境变量名称（配置文件目录）
     */
    private static final String CONFIG_PATH = "config.path";

    /**
     * 网络配置
     * */
    private static NetConfig netConfig;

    /**
     * 通过文件加载netty的配置
     * */
    public static boolean loadNettyConfigFromFile() {
        Properties properties = new Properties();
        InputStream in = load("netty.properties");
        if (in == null) {
            return false;
        }

        try {
            properties.load(in);
            NetConfig config = new NetConfig();

            String host = properties.getProperty("netty.host");
            if (host == null) {
                LOGGER.error("not key: netty.host");
                return false;
            }
            config.setHost(host);

            String port = properties.getProperty("netty.port");
            if (port == null) {
                LOGGER.error("not key: netty.port");
                return false;
            }
            config.setPort(Integer.valueOf(port));

            netConfig = config;
            return true;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
            return false;
        }
    }

    public static NetConfig getNetConfig() {
        return netConfig;
    }

    /**
     * 判断配置文件是否存在
     *
     * @param fileName 文件名
     * @return 是否存在
     */
    private static InputStream load(String fileName) {
        // 存放读取结果变量
        InputStream result;

        // 配置文件目录（环境变量）
        String filePath = System.getProperty(CONFIG_PATH);

        // 读取配置
        if (filePath == null) {
            // 未配置环境变量情况下，从class path 读取文件
            ClassLoader classLoader = ConfigUtils.class.getClassLoader();
            result = classLoader.getResourceAsStream(fileName);
        } else {
            // 配置环境变量情况下，从指定目录下读取
            File file;
            if (filePath.matches("^.*[/\\\\]$")) {
                file = new File(filePath + fileName);
            } else {
                file = new File(filePath + "/" + fileName);
            }

            if (file.isFile() && file.exists()) {
                try {
                    result = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    result = null;
                }
            } else {
                result = null;
            }
        }

        // 返回结果
        return result;

    }

    //region 使用的对象信息

    /**
     * 容器对象的实际实现类
     * */
    private static Class<? extends AbstractContainerLogic> containerLogicClass = null;

    public static void setContainerLogicClass(Class<? extends AbstractContainerLogic> containerLogicClass) {
        ConfigUtils.containerLogicClass = containerLogicClass;
    }

    /**
     * 创建容器对象
     * */
    public static AbstractContainerLogic createContainerLogic() {
        try {
            return ((AbstractContainerLogic) containerLogicClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Class<? extends AbstractObject> abstractObjectClass = null;

    public static void setAbstractObjectClass(Class<? extends AbstractObject> abstractObjectClass) {
        ConfigUtils.abstractObjectClass = abstractObjectClass;
    }

    /**
     * 创建session挂载的对象
     * */
    public static AbstractObject createAbstractObject(ObjectTypeEnum type) {
        try {
            return ((AbstractObject) abstractObjectClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
    //endregion
}
