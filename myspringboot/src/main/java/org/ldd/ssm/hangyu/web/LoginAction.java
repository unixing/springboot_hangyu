package org.ldd.ssm.hangyu.web;

import java.util.HashMap;
import java.util.Map;

import org.ldd.ssm.hangyu.domain.AirCompenyInfoThree;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.service.AirCompenyInfoThreeService;
import org.ldd.ssm.hangyu.service.ILoginService;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginAction {
	
	@Autowired
	private ILoginService loginService;
	@Autowired
	private AirCompenyInfoThreeService objService;
//	@RequestMapping("/login")
//	@ResponseBody
//	public AjaxResult login(String username,String password){
//		if(!TextUtil.isEmpty(username)&&!TextUtil.isEmpty(password)){
//			boolean is = loginService.checkLogin(username,password);
//			if(is){
//				return new AjaxResult("登录成功");
//			}else{
//				return new AjaxResult("登录失败",-99);
//			}
//		}else{
//			return new AjaxResult("登录失败",-99);
//		}
//	}
	
	@RequestMapping("/login")
	@ResponseBody
	public Map<String,Object> login(String username,String password){
		Map<String,Object> map = new HashMap<String,Object>();
		if(!TextUtil.isEmpty(username)&&!TextUtil.isEmpty(password)){
			Employee emp = null;
			try {
				emp = loginService.login(username,password);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("opResult", "2");
				return map;
			}
			if(emp!=null){
				//判断用户类型
				if("0".equals(emp.getRole())){
					emp.setRoleStr("航司");
				}else if("1".equals(emp.getRole())){
					emp.setRoleStr("机场");
				}else if("2".equals(emp.getRole())){
					emp.setRoleStr("太美");
				}
				//判断签约用户
				if("0".equals(emp.getWhethersign())){
					emp.setWhethersignStr("是");
				}else{
					emp.setWhethersignStr("不是");
				}
				map.put("obj", emp);
				//获取用户绑定航司对象
				if("0".equals(emp.getRole())){
					AirCompenyInfoThree compeny = null;
					if(!StringUtils.isEmpty(emp.getAirlineretrievalcondition()))
						compeny = objService.load(emp.getAirlineretrievalcondition());
					map.put("compeny", compeny);
				}
				return map;
			}else{
				map.put("opResult", "1");
				return map;
			}
		}else{
			map.put("opResult", "3");
			return map;
		}
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String,Object> logout(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			UserContext.getRequest().getSession().invalidate();//清空session
			map.put("opResult", "0");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opResult", "1");
			return map;
		}
		return map;
	}
}
