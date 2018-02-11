package com.springandredis.service;

import com.springandredis.model.User;

import java.util.Map;

public interface LoginService {
    public Map<String,Object> login(User user);
    public Map<String,Object> register(User user);
}
