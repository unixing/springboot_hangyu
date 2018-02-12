package com.springmvcandmysql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    /**
     * 
     * @Title: toIndex   
     * @Description: 通用页面跳转
     * @param: @param index
     * @param: @return      
     * @return: String      
     * @throws
     */
    @RequestMapping("/{index}")
    public String toIndex(@PathVariable("index")String index){
        return index;
    }
}
