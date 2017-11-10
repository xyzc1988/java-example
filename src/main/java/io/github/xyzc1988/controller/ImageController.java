package io.github.xyzc1988.controller;

import com.alibaba.fastjson.JSON;
import io.github.xyzc1988.common.bean.PaginationModel;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangcheng on 2017/10/24.
 */
@Controller
@RequestMapping("/image")
public class ImageController {

    @RequestMapping("/getImage")
    public void getImage(HttpServletRequest request, HttpServletResponse response) {

        String fileName = request.getServletContext().getRealPath("/") + "images\\1.png";

        try {
            byte[] bytes = Files.readAllBytes(Paths.get(fileName));
            response.setHeader("Content-Type", "image/png");
            response.setHeader("Content-Disposition", "attachment;filename=aaa.png");

            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/getBase64Image")
    @ResponseBody
    public String getBase64Image(HttpServletRequest request, HttpServletResponse response) {

        String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(path + "/images/1.png"));
            String base64String = Base64.encodeBase64String(bytes);
            return "data:image/png;base64," + base64String;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/getImageBody")
    @ResponseBody
    public byte[] getImageBody(HttpServletRequest request, HttpServletResponse response) {
        ServletContextResource servletContextResource = new ServletContextResource(ContextLoader.getCurrentWebApplicationContext().getServletContext(), "/images/1.png");
        try {
            InputStream inputStream = servletContextResource.getInputStream();
            byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
            response.setHeader("Content-Type", "image/png");
            response.setHeader("Content-Disposition", "attachment;filename=bbb.png");
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
