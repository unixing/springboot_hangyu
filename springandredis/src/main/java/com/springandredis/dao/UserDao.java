package com.springandredis.dao;


import com.springandredis.model.User;

public interface UserDao {
	public int register(User user);
	public String login(String username);
}
