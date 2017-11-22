package com.etoak.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by zhangcheng on 2016/11/9.
 */
public class DropTable {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-config.xml");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) ac.getBean("jdbcTemplate");
        String tables = "PRT_INDDB_COM_DATA_H,PRT_INDDB_COM_DATA_D,PRT_INDDB_COM_DATA_M,PRT_INDDB_MKACT_DATA_H," +
                "PRT_INDDB_MKACT_DATA_D,PRT_INDDB_MKACT_DATA_M,PRT_INDDB_USR_CDC_M";
        for(String tableName : tables.split(",")){
            try {
                jdbcTemplate.update("DROP TABLE " + tableName );
                System.out.println("删除表:" + tableName);
            } catch (DataAccessException e) {
                System.out.println("删除" + tableName + "失败!" + e.getMessage());
            }

        }
    }


}
