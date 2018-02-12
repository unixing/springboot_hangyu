package com.springmvcandmysql.mapper;

import com.springmvcandmysql.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(int id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    public List<User> queryAll() ;

    List<User> queryUserByPage(@Param("username")String username, @Param("starttime") String starttime, @Param("endtime")String endtime) ;
}
