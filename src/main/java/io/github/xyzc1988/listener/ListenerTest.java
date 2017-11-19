package io.github.xyzc1988.listener;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * 监听器随着项目的启动而启动
 *
 */
public class ListenerTest implements ServletContextListener{
    // 销毁监听器
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("date20161020095500 :" + servletContextEvent.getServletContext());
    }
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try{
            // 获取项目跟路径
            String basePath = servletContextEvent.getServletContext().getRealPath("/");
            // D:\apache-tomcat-6.0.41\webapps\i2money\ 绝对路径
            System.out.println("basePath20161020094700 :" + basePath);
            if (!(basePath.endsWith(File.separator))){
                basePath = basePath + File.separator;
            }
            basePath = basePath + "WEB-INF" + File.separator + "classes" + File.separator;
            // D:\apache-tomcat-6.0.41\webapps\i2money\WEB-INF\classes\
            System.out.println("basePath20161020094701 :" + basePath);
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}