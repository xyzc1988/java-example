package com.etoak.jsonTest;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.etoak.bean.Person;

import java.io.*;
import java.util.Date;

public class FastJsonTest {

	public static void main(String[] args) throws IOException {
		Person p = new Person();
		p.setName("xiaozhang");
		p.setAge(new Date());
		JSON.toJSONStringWithDateFormat(p, JSON.DEFFAULT_DATE_FORMAT);
		String pathString = FastJsonTest.class.getClassLoader().getResource("test.json").getPath();
		System.out.println(pathString );
		  String encoding="GBK";
          File file=new File("target/classes/test.json");
          String resString = "";
          if(file.isFile() && file.exists()){ //判断文件是否存在
              InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
              BufferedReader bufferedReader = new BufferedReader(read);
              String lineTxt = null;
              while((lineTxt = bufferedReader.readLine()) != null){
                  System.out.println(lineTxt);
                  resString += lineTxt;
              }
              read.close();
          }
		Teacher teacher = JSON.parseObject(resString,Teacher.class);
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Teacher.class, "tid");
		System.out.println(JSON.toJSONString(teacher,filter));
		PropertyFilter propertyFilter = new PropertyFilter() {
			@Override
			public boolean apply(Object object, String name, Object value) {
				if("tid".equals(name)){
					return false;
				}
				return true;
			}
		};
		System.out.println(JSON.toJSONString(teacher,propertyFilter));
		System.out.println(teacher.getName());


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model_type","abc");
        JSONObject info = new JSONObject();
        info.put("time_inf",jsonObject);
        System.out.println(JSON.toJSON(info));


	}
}
