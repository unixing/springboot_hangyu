package org.ldd.ssm.crm.web;

import java.io.IOException;

import org.ldd.ssm.crm.service.IResourceNewService;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResourceNewAction {
	//spring注解注入对象
	@Autowired
	private IResourceNewService resourceNewService;
	
	@RequestMapping("/resourcenew_scanResource")
	@MethodNote(comment="默认:16")
	public String scanResource(){
		try {
			resourceNewService.scanResource();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/index/index";
	}
}
