package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.InformationIssued;
import org.ldd.ssm.crm.domain.PageListInformationIssued;
import org.ldd.ssm.crm.service.InformationIssuedService;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/versioninfo")
public class InformationIssuedAction {
	@Autowired
	private InformationIssuedService informationIssuedService;
	
	@RequestMapping("/getPageList")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> getPageList(int page){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			List<InformationIssued> list = informationIssuedService.getPageList(page);
			map.put("list", list);
			map.put("opResult", "0");
		} catch (Exception e) {
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/getFirstPageData")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> getFirstPageData(){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			PageListInformationIssued list = informationIssuedService.getFirstPageData();
			map.put("list", list);
			map.put("opResult", "0");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/isHasNewInfromation")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> IsHasNewestInformation(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = informationIssuedService.IsHasNewestInformation();
			map.put("opResult", "0");
			map.put("result", result);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/updateInformationType")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> updateInformationType(String lclDptDay){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = informationIssuedService.updateInformationType(lclDptDay);
			if(result){
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
}
