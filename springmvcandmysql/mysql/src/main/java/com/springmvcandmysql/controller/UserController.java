package com.springmvcandmysql.controller;

import java.util.List;

import com.springmvcandmysql.common.EasyUIResult;
import com.springmvcandmysql.common.ResponseEntity;
import com.springmvcandmysql.model.City;
import com.springmvcandmysql.model.User;
import com.springmvcandmysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value="queryUser",method=RequestMethod.POST)
    @ResponseBody
    public EasyUIResult queryUser(){
        EasyUIResult result = this.userService.queryUser();
        return result;
    }

    @RequestMapping(value="queryUserByPage",method=RequestMethod.POST)
    @ResponseBody
    public EasyUIResult queryUserByPage(@RequestParam(value="page",defaultValue="1")Integer page,
            @RequestParam(value="rows",defaultValue="5")Integer rows,
            @RequestParam(value="username",defaultValue="")String username,
            @RequestParam(value="starttime",defaultValue="")String starttime,
            @RequestParam(value="endtime",defaultValue="")String endtime){
        
        EasyUIResult result = this.userService.queryUserByPage(page,rows,username,starttime,endtime);
        return result;
    }
    
    /**
     * 
     * @Title: getCityName   
     * @Description: 根据id名称获取城市名称
     * @param: @param id
     * @param: @return      
     * @return: City      
     * @throws
     */
    @RequestMapping(value="getCityName",method=RequestMethod.POST)
    @ResponseBody
    public City getCityName(@RequestParam(value="id")Integer id){
        City city = this.userService.getCityName(id);
        return city;
    }
    
    //produces={"text/html;charset=UTF-8;","application/json;"  可以返回json或text，如果返回text不设置会出现乱码,如果返回json可以不设置.如上方法
    @RequestMapping(value="getCityName2",method=RequestMethod.POST,produces={"text/html;charset=UTF-8;","application/text;"})
    @ResponseBody
    public String getCityName2(@RequestParam(value="id")Integer id){
        /*City city = this.userService.getCityName(id);*/
        String cityName2 = this.userService.getCityName2(id);
        return cityName2;
    }
    
    /**
     * 
     * @Title: getCity   
     * @Description: 获取所有城市 
     * @param: @return      
     * @return: List<City>      
     * @throws
     */
    @RequestMapping(value="getCity",method=RequestMethod.POST)
    @ResponseBody
    public List<City> getCity(){
        List<City> clist = this.userService.getCity();
        return clist;
    } 
    
    /**
     * 
     * @Title: save   
     * @Description: 新增用户信息
     * @param: @param user
     * @param: @return      
     * @return: ResponseEntity      
     * @throws
     */
    @RequestMapping(value="save",method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity save(User user){
        Integer count = this.userService.saveUser(user);
        if(count == 1){
            return new ResponseEntity("ok", "新增用户成功！");
        }
        return new ResponseEntity("ok", "新增用户失败！");
    }
    
    
    /**
     * 
     * @Title: save   
     * @Description: 修改用户信息 
     * @param: @param user
     * @param: @return      
     * @return: ResponseEntity      
     * @throws
     */
    @RequestMapping(value="update",method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity update(User user){
        Integer count = this.userService.updateUser(user);
        if(count == 1){
            return new ResponseEntity("ok", "修改用户成功！");
        }
        return new ResponseEntity("ok", "修改用户失败！");
    }
    
    
    
    /**
     * 
     * @Title: save   
     * @Description: 修改用户信息 
     * @param: @param user
     * @param: @return      
     * @return: ResponseEntity      
     * @throws
     */
    @RequestMapping(value="delete",method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity delete(@RequestParam("ids")List<Integer> ids){
        Integer count = this.userService.delete(ids);
        if(count > 0){
            return new ResponseEntity("ok", "删除用户成功！");
        }
        return new ResponseEntity("ok", "删除用户失败！");
    }
}
