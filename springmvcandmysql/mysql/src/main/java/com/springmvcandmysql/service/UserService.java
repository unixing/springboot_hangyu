package com.springmvcandmysql.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springmvcandmysql.common.EasyUIResult;
import com.springmvcandmysql.mapper.UserMapper;
import com.springmvcandmysql.model.City;
import com.springmvcandmysql.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    
    /**
     * 
     * @Title: queryUser   
     * @Description: 查询所有用户
     * @param: @return      
     * @return: List<User>      
     * @throws
     */
    public EasyUIResult queryUser() {
        // TODO Auto-generated method stub
        //PageHelper.startPage(1, 5);
        List<User> ulist = this.userMapper.queryAll();
        PageInfo<User> pageInfo = new PageInfo<User>(ulist);
        return new EasyUIResult(pageInfo.getTotal(), ulist);
    }
    
    /**
     * @param endtime 
     * @param starttime 
     * @param username 
     * @param rows 
     * @param page 
     * 
     * @Title: queryUser   
     * @Description: 查询所有用户带分页
     * @param: @return      
     * @return: EasyUIResult      
     * @throws
     */
    public EasyUIResult queryUserByPage(Integer page, Integer rows, String username, String starttime, String endtime) {
        // TODO Auto-generated method stub
        PageHelper.startPage(page, rows);
        List<User> ulist = this.userMapper.queryUserByPage(username,starttime,endtime);
        PageInfo<User> pageInfo = new PageInfo<User>(ulist);
        return new EasyUIResult(pageInfo.getTotal(), ulist);
    }
    
    
    public City getCityName(Integer id){
        Map<Integer,City> map = new HashMap<Integer, City>();
        map.put(1, new City(1,"北京"));
        map.put(2, new City(2,"上海"));
        map.put(3, new City(3,"天津"));
        map.put(4, new City(4,"重庆"));
        
        return map.get(id);
    }
    
    public String getCityName2(Integer id){
        Map<Integer,String> map = new HashMap<Integer, String>();
        map.put(1, "北京");
        map.put(2, "上海");
        map.put(3, "天津");
        map.put(4, "重庆");
        
        return map.get(id);
    }

    
    public List<City> getCity() {
        List<City> clist = new ArrayList<City>();
        clist.add(new City(1,"北京"));
        clist.add(new City(2,"上海"));
        clist.add(new City(3,"天津"));
        clist.add(new City(4,"重庆"));
        return clist;
    }

    /**
     * 
     * @Title: saveUser   
     * @Description: 新增用户 
     * @param: @param user
     * @param: @return      
     * @return: Integer      
     * @throws
     */
    public Integer saveUser(User user) {
        return this.userMapper.insert(user);
    }

    /**
     * 
     * @Title: updateUser   
     * @Description: 修改用户信息
     * @param: @param user
     * @param: @return      
     * @return: Integer      
     * @throws
     */
    public Integer updateUser(User user) {
        // TODO Auto-generated method stub
        return this.userMapper.updateByPrimaryKey(user);
    }

    /**
     * 
     * @Title: delete   
     * @Description: 删除用户
     * @param: @param ids      
     * @return: void      
     * @throws
     */
    public Integer delete(List<Integer> ids) {
        int count = 0;
        for (Integer id : ids) {
            count = this.userMapper.deleteByPrimaryKey(id);
            count += count;
        }
        return count;
    }

}
