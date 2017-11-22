package io.github.xyzc1988.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangcheng on 2017/11/18 15:17.
 *
 */
@RestController
public class ErrorController {

    private final static String URL_500 = "500";
    private final static String URL_404 = "404";

    @RequestMapping(value="/error_500")
    public ModelAndView error_500(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(URL_500);
        return modelAndView;
    }

    @RequestMapping(value="/error_404")
    public ModelAndView error_404(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(URL_404);
        return modelAndView;
    }
}