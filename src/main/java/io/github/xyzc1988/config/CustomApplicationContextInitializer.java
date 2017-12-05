package io.github.xyzc1988.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by zhangcheng on 2017/12/4.
 *
 */
public class CustomApplicationContextInitializer implements ApplicationContextInitializer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        logger.info("CustomApplicationContextInitializer.initialize.....");
    }
}
