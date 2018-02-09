package org.ldd.ssm.hangyu.web;

import java.util.Map;

import org.ldd.ssm.hangyu.service.PublicOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicOpinionAction {
	@Autowired
	private PublicOpinionService publicOpinionService;
	@RequestMapping("/getPublicOpinionList")
	@ResponseBody
	public Map<String,Object> getPublicOpinionList(String code,Integer type,Integer codeType,Integer page){
		return publicOpinionService.getPublicOpinionList(code, type,codeType,page,10);
	}
}
