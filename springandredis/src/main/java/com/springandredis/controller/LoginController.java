package com.springandredis.controller;

import com.springandredis.model.User;
import com.springandredis.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(User user){
        return loginService.login(user);
    }
    @RequestMapping("/register")
    @ResponseBody
    public Map<String,Object> register(User user){
        return  loginService.register(user);
    }
}
