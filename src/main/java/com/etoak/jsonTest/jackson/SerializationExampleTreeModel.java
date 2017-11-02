package com.etoak.jsonTest.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileWriter;

/**
 * Created by zhangcheng on 2016/9/8.
 */
public class SerializationExampleTreeModel {

    public static void main(String[] args) throws Exception {
        //����һ���ڵ㹤��,Ϊ�����ṩ���нڵ�
        JsonNodeFactory factory = new JsonNodeFactory(false);
        //����һ��json factory��дtree modleΪjson
        JsonFactory jsonFactory = new JsonFactory();
        //����һ��json������
        JsonGenerator generator = jsonFactory.createGenerator(new FileWriter(new File("d:/temp/country2.json")));
        //ע�⣬Ĭ������¶���ӳ��������ָ�����ڵ㣬��������ڵ�Ϊcountry
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode country = factory.objectNode();

        country.put("country_id", "China");
        country.put("birthDate", "1949-10-01");

        //��Java�У�List��Arrayת��Ϊjson���Ӧ�ĸ�ʽ���Ŷ���"obj:[]"
        ArrayNode nation = factory.arrayNode();
        nation.add("Han").add("Meng").add("Hui").add("WeiWuEr").add("Zang");
        country.set("nation", nation);

        ArrayNode lakes = factory.arrayNode();
        lakes.add("QingHai Lake").add("Poyang Lake").add("Dongting Lake").add("Taihu Lake");
        country.set("lakes", lakes);

        ArrayNode provinces = factory.arrayNode();
        ObjectNode province = factory.objectNode();
        ObjectNode province2 = factory.objectNode();
        province.put("name","Shanxi");
        province.put("population", 37751200);
        province2.put("name","ZheJiang");
        province2.put("population", 55080000);
        provinces.add(province).add(province2);
        country.set("provinces", provinces);

        ObjectNode traffic = factory.objectNode();
        traffic.put("HighWay(KM)", 4240000);
        traffic.put("Train(KM)", 112000);
        country.set("traffic", traffic);

        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.writeTree(generator, country);
    }

}








