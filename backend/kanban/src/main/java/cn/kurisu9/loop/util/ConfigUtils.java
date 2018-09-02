package cn.kurisu9.loop.util;

import cn.kurisu9.loop.entity.NetConfig;
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
     * 主逻辑线程的tick毫秒数为100
     * */
    public static final int MAIN_PROCESSOR_TICK_MS = 100;

    /**
     * 工作逻辑线程的tick毫秒数为10
     * */
    public static final int WORKER_PROCESSOR_TICK_MS = 10;

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
}
