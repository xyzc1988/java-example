package com.etoak.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangcheng on 2016/12/2.
 */
public class Log4j2Test {

    private static Logger logger = LoggerFactory.getLogger(Log4j2Test.class);

    public static void main(String[] args) {
        while (true) {
            logger.error("This second example shows a rollover strategy that will keep up to 20 files before removing them.");
        }
    }
}
