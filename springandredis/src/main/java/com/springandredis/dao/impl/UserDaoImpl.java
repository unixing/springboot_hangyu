package com.springandredis.dao.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import com.springandredis.dao.UserDao;
import com.springandredis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
	private RedisTemplate<Serializable, Serializable> redisTemplate;

    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public int register(final User user) {
			int activeLines = redisTemplate.execute(new RedisCallback<Integer>() {
				public Integer doInRedis(RedisConnection conn)
						throws DataAccessException {
					conn.set(redisTemplate.getStringSerializer().serialize(user.getUsername()),
							redisTemplate.getStringSerializer().serialize(user.getPassword()));
					return 1;
				}
			});
		return activeLines;
	}

	public String login(final String username) {
		String password = redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection conn)
					throws DataAccessException {
				byte[] password = conn.get(redisTemplate.getStringSerializer().serialize(username));
				try {
					if(password==null)
						return null;
					return new String(password, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return null;
				}
			}
		});
		return password;
	}

}
