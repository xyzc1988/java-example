package io.github.xyzc1988.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
/**
 * 监听器随着项目的启动而启动
 *
 */
public class CustomServletContextListener implements ServletContextListener{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try{
            // 获取项目跟路径
            String basePath = servletContextEvent.getServletContext().getRealPath("/");
            if (!(basePath.endsWith(File.separator))){
                basePath = basePath + File.separator;
            }
            logger.info("执行CustomServletContextListener.contextInitialized...:[{}]",servletContextEvent.getServletContext().getServletContextName());

        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    // 销毁监听器
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // logger.info("执行CustomServletContextListener.contextDestroyed...:[{}]", servletContextEvent.getServletContext().getServletContextName());
        // logger.info("关闭logManager....");
        // LogManager.shutdown();

    }
}