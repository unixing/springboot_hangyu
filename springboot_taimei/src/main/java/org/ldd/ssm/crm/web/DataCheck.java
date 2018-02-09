package org.ldd.ssm.crm.web;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.DataCheckObject;
import org.ldd.ssm.crm.query.DateCheckQuery;
import org.ldd.ssm.crm.service.IDataCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataCheck {
	
	@Autowired
	private IDataCheckService dataCheckService;
	
	@RequestMapping("/dataCheck")
	public String execut(){
		return "dataCheck/dataCheck";
	}
	@RequestMapping("/dataCheck_list")
	@ResponseBody
	@MyMethodNote(comment="数据验证:2")
	public DataCheckObject<Z_Airdata> list(DateCheckQuery query){
		 return dataCheckService.getDataCheck(query);
	}
	
}			
				
