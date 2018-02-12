package com.springandmongo.controller;

import com.springandmongo.dao.UserDao;
import com.springandmongo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/register")
    @ResponseBody
    public Map<String,Object> register(User user){
        Map<String,Object> map = new HashMap<String,Object>();
        if(user==null||StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())){
            map.put("opResult","3");
            return map;
        }
        try {
            userDao.store(user);
            map.put("opResult","0");
        }catch (Exception e){
            e.printStackTrace();
            map.put("opResult","2");
            return map;
        }
        return map;
    }
}
