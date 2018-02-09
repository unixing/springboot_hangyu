package org.ldd.ssm.crm.web;

import java.util.List;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.Instructions;
import org.ldd.ssm.crm.service.IEtermConfigService;
import org.ldd.ssm.crm.utils.AjaxResult;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EtermConfigAction {
	@Autowired
	private IEtermConfigService etermConfigServicce;
	@MyMethodNote(comment="ETERM主页:2")
	@MethodNote(comment="采集号配置信息:12")
	@RequestMapping("/configuration")
	public String configuration(){
		return "configuration/configuration";
	}
	@MyMethodNote(comment="ETERM列表:2")
	@RequestMapping("/configuration_list")
	@ResponseBody
	public List<Instructions> configuration_list(){
		Employee user = UserContext.getUser();
		
		
		return etermConfigServicce.getConfiguration_list(user.getId());
	}
	@MyMethodNote(comment="ETERM保存:3")
	@MethodNote(comment="采集号配置信息:12")
	@RequestMapping("/configuration_save")
	@ResponseBody
	public AjaxResult configuration_save(Instructions instructions){
		List<Instructions> list = etermConfigServicce.getEtermsByAccName(instructions.getEtm_Usr());
		if(instructions.getId()!=0){
			if(list==null||list.size()==0){
				etermConfigServicce.updateConfig(instructions);
				return new AjaxResult("保存完成");
			}else if(list.size()==1){
				if(instructions.getId()==list.get(0).getId()){
					etermConfigServicce.updateConfig(instructions);
					return new AjaxResult("保存完成");
				}else{
					return new AjaxResult("用户名已经存在");
				}
			}else{
				return new AjaxResult("用户名已经存在");
			}
		}else{
			if(list==null||list.size()==0){
				etermConfigServicce.saveConfig(instructions);
				return new AjaxResult("保存完成");
			}else{
				return new AjaxResult("用户名已经存在");
			}
		}
	}
	@RequestMapping("/configuration_delete")
	@MethodNote(comment="采集号配置信息:12")
	@ResponseBody
	@MyMethodNote(comment="ETERM删除:3")
	public AjaxResult configuration_delete(long id){
		etermConfigServicce.deleteConfig(id);
		return new AjaxResult("删除完成");
	}
}
