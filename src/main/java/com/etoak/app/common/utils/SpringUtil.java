package com.etoak.app.common.utils;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * 获取spring管理的bean对象工具类
 * Created by zhangcheng on 2017/8/30.
 */
public class SpringUtil extends SpringBeanAutowiringSupport {

    @Autowired
    private BeanFactory beanFactory;

    //静态方法初始化类
    private static SpringUtil instance;

    static {
        instance = new SpringUtil();
    }

    //根据bean的id，获取对应类对象
    //根据bean的id获取bean对象要比根据class获取bean对象效率高，但容易出现人为错误
    public Object getBeanById(String beanId) {
        return beanFactory.getBean(beanId);
    }

    //根据bean的类型，获取对应类对象，
    //不容易出现人为错误，但效率不如根据id获取bean对象，因为spring内部是把class转换为name，然后再进行查找
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object getBeanByClass(Class class1) {
        return beanFactory.getBean(class1);
    }

    public static SpringUtil getInstance() {
        return instance;
    }

}