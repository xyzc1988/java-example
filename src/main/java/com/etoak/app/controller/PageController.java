package com.etoak.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etoak.app.common.bean.PaginationModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.System.in;

/**
 * Created by zhangcheng on 2017/10/24.
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/getPage")
    @ResponseBody
    public PaginationModel getPage(int pageSize,int pageIndex) {
        URL resource = this.getClass().getResource("/data.json");

        PaginationModel paginationModel = new PaginationModel();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(resource.getPath()), Charset.forName("utf-8")));
            String s = br.readLine();
            List<Map> arrayLists = JSON.parseArray(s, Map.class);
            paginationModel.setTotal(arrayLists.size());
            paginationModel.setData(Arrays.asList(arrayLists.get(pageIndex - 1)));
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
        return paginationModel;
    }
}
