package cn.kurisu9.loop.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author kurisu9
 * @description spring相关的工具类
 * @date 2018/9/15 16:58
 **/
@Component
public class SpringUtils implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringUtils.class);

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            LOGGER.info("SpringUtils get applicationContext success");
            SpringUtils.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    
}
