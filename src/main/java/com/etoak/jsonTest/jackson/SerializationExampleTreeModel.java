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
        //创建一个节点工厂,为我们提供所有节点
        JsonNodeFactory factory = new JsonNodeFactory(false);
        //创建一个json factory来写tree modle为json
        JsonFactory jsonFactory = new JsonFactory();
        //创建一个json生成器
        JsonGenerator generator = jsonFactory.createGenerator(new FileWriter(new File("d:/temp/country2.json")));
        //注意，默认情况下对象映射器不会指定根节点，下面设根节点为country
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode country = factory.objectNode();

        country.put("country_id", "China");
        country.put("birthDate", "1949-10-01");

        //在Java中，List和Array转化为json后对应的格式符号都是"obj:[]"
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








