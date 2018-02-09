package org.ldd.ssm.crm.web;

import java.util.List;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Instructions;
import org.ldd.ssm.crm.query.ETermQuery;
import org.ldd.ssm.crm.query.EtermInfoObject;
import org.ldd.ssm.crm.service.IETermService;
import org.ldd.ssm.crm.utils.AjaxResult;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SystemAction {
	@Autowired
	private IETermService addeTermService;
	@RequestMapping("/addeTerm")
	@MyMethodNote(comment="增加eterm配置:3")
	@MethodNote(comment="采集号配置信息:12")
	@ResponseBody
	public AjaxResult addeTerm(ETermQuery query){
		List<Instructions> list = addeTermService.getEtermByName(query.geteTermName(),UserContext.getCompanyName());
		if(list==null||list.size()==0){
			addeTermService.addeTerm(query);
			return new AjaxResult();
		}else if(list.size()==1){
			if(TextUtil.isEmpty(query.getId())){
				return new AjaxResult("用户名已存在");
			}else{
				if(list.get(0).getId()==Long.valueOf(query.getId()).longValue()){
					addeTermService.addeTerm(query);
					return new AjaxResult();
				}else{
					return new AjaxResult("用户名已存在");
				}
			}
		}else{
			return new AjaxResult("用户名已存在");
		}
	}
	@RequestMapping("/getEtermAirportOnLineData")
	@MyMethodNote(comment="查询eterm配置:2")
	@MethodNote(comment="采集号配置信息:12")
	@ResponseBody
	public List<EtermInfoObject> getAirportOnLineData(){
		return addeTermService.getAirportOnLineData();
	}
	@RequestMapping("/eterm_delete")
	@MyMethodNote(comment="删除eterm配置:3")
	@MethodNote(comment="采集号配置信息:12")
	@ResponseBody
	public AjaxResult eterm_delete(String id){
		return addeTermService.eterm_delete(id);
	}
	@RequestMapping("/eterm_test")
	@MyMethodNote(comment="eterm配置测试:2")
	@MethodNote(comment="采集号配置信息:12")
	@ResponseBody
	public AjaxResult eterm_test(String id){
		try {
			AjaxResult eterm_test = addeTermService.eterm_test(id);
			return eterm_test;
		} catch (Exception e) {
			AjaxResult eterm_test = new AjaxResult();
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return eterm_test;
		}
	}
}
