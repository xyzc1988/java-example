package io.github.xyzc1988.controller;

import com.alibaba.fastjson.JSON;
import io.github.xyzc1988.common.bean.PaginationModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public PaginationModel getPage(@RequestBody Map<String, Object> params) throws URISyntaxException {
        int pageIndex = (int) params.get("pageIndex");
        URL resource = this.getClass().getResource("/data.json");
        Path path = Paths.get(resource.toURI());

        PaginationModel paginationModel = new PaginationModel();
        try {
         /*   BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(resource.getPath()), Charset.forName("utf-8")));
            Files.readAllLines();
            String s = br.readLine();*/
            byte[] bytes = Files.readAllBytes(path);
            List<Map> arrayLists = JSON.parseArray(new String(bytes, "utf-8"), Map.class);
            paginationModel.setTotalCount(arrayLists.size());
            paginationModel.setData(Arrays.asList(arrayLists.get(pageIndex)));
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

    @RequestMapping("/getImage")
    public void getImage(HttpServletRequest request, HttpServletResponse response) {

        String fileName = request.getServletContext().getRealPath("/")
                + "images\\1.png";
        String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
        System.out.println(System.getProperty("webapp.root"));
        System.out.println(System.getProperty("myWebapp.root"));
        byte[] newInputStream = null;
        try {
            newInputStream = Files.readAllBytes(Paths.get(fileName));
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(newInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
