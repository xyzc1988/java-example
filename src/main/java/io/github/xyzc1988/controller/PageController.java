package io.github.xyzc1988.controller;

import com.alibaba.fastjson.JSON;
import io.github.xyzc1988.annotation.Auth;
import io.github.xyzc1988.common.bean.PageModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
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
@RequestMapping("/page")
public class PageController {

    @Auth
    @RequestMapping()
    public String pageIndex(HttpServletRequest request, HttpServletResponse response) {
        return "page";
    }

    @Auth
    @RequestMapping("/getPage")
    @ResponseBody
    public PageModel getPage(@RequestBody Map<String, Object> params, HttpServletRequest request) throws URISyntaxException {
        int pageIndex = (int) params.get("pageIndex");
        String webappRoot = "";
        // 获取项目根路径的方法
        webappRoot = System.getProperty("webapp.root");
        webappRoot = request.getServletContext().getRealPath("/");
        webappRoot = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");

        // 获得类加载器根路径 ../WEB-INF/classes
        this.getClass().getResource("/");
        // 获得类加载全包名路径 ../WEB-INF/classes/../..
        this.getClass().getResource("");

        //spring获得类加载器根目录资源文件 类似的还有FileSystemResource,ServletContextResource等
        Resource classPathResource = new ClassPathResource("/mail/IGXE验证消息_.eml");

        PageModel paginationModel = new PageModel();
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(webappRoot + "/data/data.json"));
            List<Map> arrayLists = JSON.parseArray(new String(bytes, "utf-8"), Map.class);
            paginationModel.setTotalCount(arrayLists.size());
            paginationModel.setData(Collections.singletonList(arrayLists.get(pageIndex)));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return paginationModel;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity responseEntityTest(@PathVariable long id) {
       /* URI location = URI.create("aaaa");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        responseHeaders.set("MyResponseHeader", "MyValue");
        return new ResponseEntity("Hello World", responseHeaders, HttpStatus.CREATED);*/

        return ResponseEntity.ok().header("MyResponseHeader", "MyValue").body("Hello World");
    }
}
