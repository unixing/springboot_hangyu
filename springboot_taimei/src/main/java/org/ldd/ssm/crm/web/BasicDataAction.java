package org.ldd.ssm.crm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.DailyParameters;
import org.ldd.ssm.crm.domain.Digitt;
import org.ldd.ssm.crm.domain.FlyDetalisInfo;
import org.ldd.ssm.crm.query.DailyParametersQuery;
import org.ldd.ssm.crm.query.FlyBasicDataQuery;
import org.ldd.ssm.crm.query.ProcessTaskObject;
import org.ldd.ssm.crm.service.BasicDataService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *航线的前台控制器层
 */
@Controller
public class BasicDataAction {
	@Autowired
	private BasicDataService basicDataService;
	//springMVC请求的映射地址
	@RequestMapping("/basicData")
	@MyMethodNote(comment="航线主页:2")
	public ModelAndView basicData() {
		ModelAndView modelAndView = new ModelAndView("basicData/basicData");
		Digitt	digit = basicDataService.get();
		modelAndView.addObject("digit", digit.getDigit());  
		modelAndView.addObject("id", digit.getId());  
		return modelAndView;
	}
	@RequestMapping("/flyBasicData")
	public ModelAndView flyBasicData() {
		ModelAndView modelAndView = new ModelAndView("basicData/flyBasicData");
		return modelAndView;
	}
	@MyMethodNote(comment="销售报表设置主页:2")
	@RequestMapping("/dailyParameters")
	public ModelAndView dailyParameters() {
		ModelAndView modelAndView = new ModelAndView("basicData/dailyParameters");
		return modelAndView;
	}
	/**
	 *更新小数位数设置
	 * @Title: saveBasicData 
	 * @Description:  
	 * @param @param digitt
	 * @param @return    
	 * @return Map<String,String>     
	 * @throws
	 */
	@RequestMapping("/saveBasicData")
	@ResponseBody
//	@MethodNote(comment="报表参数设置:8")
	@MyMethodNote(comment="更新小数位数设置:3")
	public Map<String, String> saveBasicData (Digitt digitt) {
		Map<String, String> rootMap = new HashMap<String, String>();
		int i = digitt.getDigit();
		String str = "0.";
		if(i==0){
			str = "0";
		}else{
			for (int j = 0; j < i; j++) {
				str = str +"0";
			}
		}
		digitt.setExpression(str);
		String info = basicDataService.update(digitt);
		rootMap.put("info", info);
		return rootMap;
	}
	@RequestMapping("/flyInfoList")
	@ResponseBody
	public ProcessTaskObject<FlyDetalisInfo> flyInfoList(FlyBasicDataQuery sQuery) {
		
		return null;
	}
	@RequestMapping("/dailyParametersList")
	@ResponseBody
//	@MethodNote(comment="报表参数设置:8")
	@MyMethodNote(comment="日报参数设置:2")
	public List<DailyParameters> dailyParametersList(DailyParametersQuery dailyParametersQuery) {
		List<DailyParameters>  dailyParametersList = new ArrayList<DailyParameters>();
		dailyParametersList = basicDataService.getDailyParametersList(dailyParametersQuery);
		for (DailyParameters dailyParameters : dailyParametersList) {
			dailyParameters.setSitee(dailyParameters.getFly_site_min()+"到"+dailyParameters.getFly_site());
		}
		return dailyParametersList;
	}
	@RequestMapping("/saveOrUpdatedailyParameters")
	@ResponseBody
//	@MethodNote(comment="报表参数设置:8")
	@MyMethodNote(comment="保存修改日报参数:3")
	public Map<String,String> saveOrUpdatedailyParameters(DailyParameters dailyParameters) {
		String info  = "保存失败";
		Map<String,String>  retMap = new HashMap<String, String>();
		dailyParameters.setRole_id(UserContext.getCompanyId());
		if(TextUtil.isEmpty(dailyParameters.getId()+"")){
			//新增
			info = basicDataService.saveDailyParameters(dailyParameters);
		}else{
			//修改
			info = basicDataService.updateDailyParameters(dailyParameters);
		}
		retMap.put("info", info);
		return retMap;
	}
	@RequestMapping("/deletedailyParameters")
	@ResponseBody
//	@MethodNote(comment="报表参数设置:8")
	@MyMethodNote(comment="删除日报参数:3")
	public Map<String,String> deletedailyParameters(String ids) {
		String info  = "保存失败";
		Map<String,String>  retMap = new HashMap<String, String>();
		info = basicDataService.deleteDailyParameters(ids);
		retMap.put("info", info);
		return retMap;
	}

}
