package org.ldd.ssm.crm.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.AcquisitionTime;
import org.ldd.ssm.crm.domain.Eachflightinfo;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.query.ProcessTaskObject;
import org.ldd.ssm.crm.query.ProcessTaskQuery;
import org.ldd.ssm.crm.service.IExtractService;
import org.ldd.ssm.crm.utils.AjaxResult;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 时刻表的控制层
 */
@Controller
public class ProcessTaskAction {
	@Autowired
	private IExtractService extractService;

	// 定位到页面
	@RequestMapping("/processTask")
	@MethodNote(comment="时刻查询:7")
	@MyMethodNote(comment="航班时刻主页:2")
	public String index() {
		return "newHtml/time-query";
//		return "/timetable/timetable_j";
	} 
	@RequestMapping("/processTask_date")
	@MethodNote(comment="时刻查询:7")
	@ResponseBody
	public List<AcquisitionTime> processTask_date() {
		return extractService.getAll();
	}
	@RequestMapping("/processTask_list")
	@ResponseBody
	@MethodNote(comment="时刻查询:7")
	@MyMethodNote(comment="航班时刻查询:2")
	public AjaxResult gather(String name1, String name2) {
		List<String> list = extractService.getName();
		Employee user = UserContext.getUser();
		try {
			if (user.getUsrSts() == 99) {
				/**
				 * 第一次调用的时间格式化后作为查询日期
				 */
				Date format = new Date();
				for (String name : list) {
					if(name.equals("西昌")){
					 String str = extractService.get(name);
					 extractService.testname(str, name,format);
					}
				}
				return new AjaxResult("采集完成");
			} else {
				return new AjaxResult("权限不足");
			}
		} catch (Exception e) {
			return new AjaxResult("采集失败,原因未明,请和软件管理员联系");
		}
	}

	@RequestMapping("/processTask_list2")
	@ResponseBody
	@MethodNote(comment="时刻查询:7")
	@MyMethodNote(comment="航班时刻查询:2")
	public ProcessTaskObject<Eachflightinfo> list2(ProcessTaskQuery sQuery) {
		if (StringUtils.isEmpty(sQuery.getDpt_AirPt_Cd())
				&& StringUtils.isEmpty(sQuery.getArrv_Airpt_Cd())) {
			return null;
		}
		if (StringUtils.isEmpty(sQuery.getDpt_AirPt_Cd())) {
			sQuery.setDpt_AirPt_Cd(null);
		}
		if (StringUtils.isEmpty(sQuery.getArrv_Airpt_Cd())) {
			sQuery.setArrv_Airpt_Cd(null);
		}
		if("return".equals(sQuery.getGoOrReturn())){
			if(sQuery.getArrv_Airpt_Cd()!=null&&sQuery.getDpt_AirPt_Cd()!=null){
				ProcessTaskObject<Eachflightinfo> query = extractService.queryToRoRreturn(sQuery);
				return query;
			}
			ProcessTaskObject<Eachflightinfo> query = extractService.queryAll(sQuery);
			return query;
		}else{
			ProcessTaskObject<Eachflightinfo> query = extractService.query(sQuery);
			return query;
		}
	}

	@RequestMapping("/processTask_list3")
	@ResponseBody
	@MethodNote(comment="时刻查询:7")
	@MyMethodNote(comment="航班时刻查询:2")
	public ProcessTaskObject<Eachflightinfo> list(ProcessTaskQuery sQuery) {
		if (StringUtils.isEmpty(sQuery.getDpt_AirPt_Cd_itia())
				&& StringUtils.isEmpty(sQuery.getArrv_Airpt_Cd_itia())) {
			return null;
		}
		if (StringUtils.isEmpty(sQuery.getDpt_AirPt_Cd_itia())) {
			sQuery.setDpt_AirPt_Cd_itia(null);
		}
		if (StringUtils.isEmpty(sQuery.getArrv_Airpt_Cd_itia())) {
			sQuery.setArrv_Airpt_Cd_itia(null);
		}
		if("return".equals(sQuery.getGoOrReturn())){
			if(sQuery.getArrv_Airpt_Cd_itia()!=null&&sQuery.getDpt_AirPt_Cd_itia()!=null){
				ProcessTaskObject<Eachflightinfo> query = extractService.queryToRoRreturnByIata(sQuery);
				return query;
			}
			ProcessTaskObject<Eachflightinfo> query = extractService.queryAllByIata(sQuery);
			return query;
		}else{
			ProcessTaskObject<Eachflightinfo> query = extractService.queryByIata(sQuery);
			return query;
		}
	}
	
	@RequestMapping("/jxlExcel")
	@MethodNote(comment="时刻查询:7")
	public ModelAndView viewJxlExcel(String dpt_AirPt_Cd,String Arrv_Airpt_Cd,String goOrReturn,String get_tim,HttpServletRequest request,
			HttpServletResponse response) {		
		if(StringUtils.isEmpty(dpt_AirPt_Cd)&&StringUtils.isEmpty(dpt_AirPt_Cd)&&StringUtils.isEmpty(goOrReturn)&&StringUtils.isEmpty(get_tim)){
			return new ModelAndView("newHtml/time-query", null);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(dpt_AirPt_Cd)) {
			dpt_AirPt_Cd=null;
		}else{
			map.put("titles", dpt_AirPt_Cd);
		}
		if (StringUtils.isEmpty(Arrv_Airpt_Cd)) {
			Arrv_Airpt_Cd=null;
		}else{
			map.put("titles", Arrv_Airpt_Cd);
		}
		if(!StringUtils.isEmpty(dpt_AirPt_Cd)&&!StringUtils.isEmpty(Arrv_Airpt_Cd)){
			map.put("titles", dpt_AirPt_Cd+"-"+Arrv_Airpt_Cd);
		}
		if(StringUtils.isEmpty(goOrReturn)){
			goOrReturn=null;
		}
		if(StringUtils.isEmpty(get_tim)){
			get_tim=null;
		}
		ProcessTaskQuery sQuery = new ProcessTaskQuery();
		sQuery.setDpt_AirPt_Cd_itia(dpt_AirPt_Cd);
		sQuery.setArrv_Airpt_Cd_itia(Arrv_Airpt_Cd);
		sQuery.setGet_tim(get_tim);
		ProcessTaskObject<Eachflightinfo> query = null;
		if("return".equals(goOrReturn)){
			query = extractService.queryPortReturn(sQuery);
		}else{
			query = extractService.queryPort(sQuery);
		}
		Map<String,List<Eachflightinfo>> model = new HashMap<String,List<Eachflightinfo>>();
		// 构造数据
		List<Eachflightinfo> Eachflightinfo = new ArrayList<Eachflightinfo>();
		List<Eachflightinfo> rows = query.getRows();
		for (Eachflightinfo eachflightinfo : rows) {
			Eachflightinfo.add(eachflightinfo);
		}
		model.put("list", Eachflightinfo);		
		return new ModelAndView(new JXLExcelView(map), model);
	}
	@RequestMapping("/NullExcel")
	@MethodNote(comment="时刻查询:7")
	public ModelAndView NullExcel(String titles,String colums,HttpServletRequest request,
			HttpServletResponse response) {		
		return new ModelAndView(new TitleExcelView(), null);
	}

	@RequestMapping("/getNewEstCollectDate")
	@MethodNote(comment="时刻查询:7")
	@ResponseBody
	public List<String> getNewEstCollectDate(ProcessTaskQuery sQuery){
		List<String> list = new ArrayList<String>();
		list = extractService.getNewEstCollectDate(sQuery);
		return list;
	}
}
