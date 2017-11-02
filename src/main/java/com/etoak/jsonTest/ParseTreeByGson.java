package com.etoak.jsonTest;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by zhangcheng on 2016/9/8.
 */

public class ParseTreeByGson {
    public static void main(String[] args) throws MalformedURLException, IOException {
        String url = "http://www.weather.com.cn/data/sk/101010100.html";
        String json = IOUtils.toString(new URL(url), Charset.forName("utf-8"));
        JsonParser parser = new JsonParser();
        // The JsonElement is the root node. It can be an object, array, null or
        // java primitive.
        JsonElement element = parser.parse(json);
        // use the isxxx methods to find out the type of jsonelement. In our
        // example we know that the root object is the Albums object and
        // contains an array of dataset objects
        if (element.isJsonObject()) {
            JsonObject albums = element.getAsJsonObject();
            JsonObject weatherinfo = albums.getAsJsonObject("weatherinfo");
            System.out.println(weatherinfo.get("city").getAsString());
            System.out.println(weatherinfo.get("cityid").getAsLong());
            /*JsonArray datasets = albums.getAsJsonArray("dataset");
            for (int i = 0; i < datasets.size(); i++) {
                JsonObject dataset = datasets.get(i).getAsJsonObject();
                System.out.println(dataset.get("album_title").getAsString());
            }*/
        }

    }
}