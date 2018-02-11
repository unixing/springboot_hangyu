package com.springandredis.service.impl;

import com.springandredis.dao.UserDao;
import com.springandredis.model.User;
import com.springandredis.service.LoginService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDao userDao;
    private Logger logger = Logger.getLogger(LoginServiceImpl.class);
    public Map<String, Object> login(User user) {
        Map<String,Object> map = new HashMap<String,Object>();
        if(user==null||StringUtils.isEmpty(user.getPassword())||StringUtils.isEmpty(user.getUsername())){
            logger.debug("The user is an invalid parameter");
            map.put("opResult","3");
            return map;
        }
        try {
            String dataPassword = userDao.login(user.getUsername());
            if(user.getPassword().equals(dataPassword)){
                map.put("opResult", "0");
            }else{
                map.put("opResult", "1");
            }
        } catch (Exception e) {
            logger.error("There are errors in code");
            e.printStackTrace();
            map.put("opResult", "2");
            return map;
        }
        return map;
    }

    public Map<String, Object> register(User user) {
        Map<String,Object> map = new HashMap<String,Object>();
        if(user==null||StringUtils.isEmpty(user.getPassword())||StringUtils.isEmpty(user.getUsername())){
            logger.debug("The user is an invalid parameter");
            map.put("opResult","3");
            return map;
        }
        try {
            int activeLines = userDao.register(user);
            if(activeLines>0){
                map.put("opResult", "0");
            }else{
                map.put("opResult", "1");
            }
        } catch (Exception e) {
            logger.error("There are errors in code");
            e.printStackTrace();
            map.put("opResult", "2");
            return map;
        }
        return map;
    }
}
