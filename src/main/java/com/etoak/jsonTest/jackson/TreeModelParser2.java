package com.etoak.jsonTest.jackson;

/**
 * Created by zhangcheng on 2016/9/8.
 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TreeModelParser2 {
    public static void main(String[] args) throws MalformedURLException, IOException {
        // Get a list of albums from free music archive. limit the results to 5
        String url = "http://www.weather.com.cn/data/sk/101010100.html";
        // Get the contents of json as a string using commons IO IOUTils class.
        String genreJson = IOUtils.toString(new URL(url), "utf-8");

        // create an ObjectMapper instance.
        ObjectMapper mapper = new ObjectMapper();
        // use the ObjectMapper to read the json string and create a tree
        JsonNode node = mapper.readTree(genreJson);

        // not the use of path. this does not cause the code to break if the
        // code does not exist
        JsonNode weatherinfo = node.path("weatherinfo");
        JsonNode city = weatherinfo.path("city");
        Iterator<String> fieldNames = weatherinfo.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            String value = weatherinfo.path(fieldName).asText();
            System.out.println(fieldName + "::::" + value);
        }
        //System.out.println(albums.next().path("album_title"));
    }
}