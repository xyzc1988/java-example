package com.etoak.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etoak.bean.Birthday;
import com.etoak.bean.McpChangeRequest;
import com.etoak.bean.Student;
import com.thoughtworks.xstream.XStream;

/**
 * <b>function:</b>Java对象和XML字符串的相互转换
 * jar-lib-version: xstream-1.3.1
 * @author hoojo
 * @createDate Nov 27, 2010 12:15:15 PM
 * @file XStreamTest.java
 * @package com.hoo.test
 * @project WebHttpUtils
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */

public class XStreamTest {
    
	private Logger logger = LoggerFactory.getLogger(getClass());

    private XStream xstream = null;

    private ObjectOutputStream  out = null;

    private ObjectInputStream in = null;

    

    private Student bean = null;

    

    /**
     * <b>function:</b>初始化资源准备
     * @author hoojo
     * @createDate Nov 27, 2010 12:16:28 PM
     */
    @Before
    public void init() {

        try {

            xstream = new XStream();

            //xstream = new XStream(new DomDriver()); // 需要xpp3 jar
        } catch (Exception e) {

            e.printStackTrace();

        }

        bean = new Student();

        bean.setAddress("china");

        bean.setEmail("jack@email.com");

        bean.setId("1");

        bean.setName("jack");

        Birthday day = new Birthday();

        day.setBirthday("2010-11-22");

        bean.setBirthday(day);

    }

    

    /**
     * <b>function:</b>释放对象资源
     * @author hoojo
     * @createDate Nov 27, 2010 12:16:38 PM
     */
    @After

    public void destory() {

        xstream = null;

        bean = null;

        try {

            if (out != null) {

                out.flush();

                out.close();

            }

            if (in != null) {

                in.close();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        System.gc();

    }

    
   
    public final void fail(String string) {

        System.out.println(string);

    }

    

    public final void failRed(String string) {

        System.err.println(string);

    }
    @Test
    public void test(){
    	xstream.alias("student", Student.class);
    	//System.out.println(xstream.toXML(bean));
    	 //对指定的类使用Annotation
        xstream.processAnnotations(Student.class);
        //启用Annotation
       // xstream.autodetectAnnotations(true);
        
    	logger.info("报文为:\n{}",xstream.toXML(bean));
    	McpChangeRequest mcpChangeRequest = new McpChangeRequest();
    	mcpChangeRequest.setContentCode("aaa");
    	logger.info(xstream.toXML(mcpChangeRequest));
    }
}

