package org.ldd.ssm.crm.web;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.FltDyDetial;
import org.ldd.ssm.crm.domain.OtherSalesReport;
import org.ldd.ssm.crm.domain.SalesReport;
import org.ldd.ssm.crm.domain.SalesReportDayInfo;
import org.ldd.ssm.crm.domain.YearSalesReport;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.query.FlyNum;
import org.ldd.ssm.crm.query.FormulaUtilQuery;
import org.ldd.ssm.crm.query.SalesReportQuery;
import org.ldd.ssm.crm.service.SalesReportService;
import org.ldd.ssm.crm.utils.FormulaUtil;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

/**
 * 销售报表控制类
 * @Title:SalesReportAction 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-20 上午10:17:49
 */
@Controller
public class SalesReportAction {
	@Autowired
	private SalesReportService salesReportService;
	@Autowired
	private FormulaUtil formulaUtil;
	@Autowired
	private OutPortMapper outPortMapper;
	/**
	 * 销售报表主页面
	 * @Title: salesReport 
	 * @Description:  
	 * @param @return    
	 * @return ModelAndView     
	 * @throws
	 */
	@RequestMapping("/salesReport")
	@MyMethodNote(comment="销售报表日报主页:2")
	@MethodNote(comment="销售报表:16")
	public ModelAndView  salesReportDay() {
		 ModelAndView modelAndView = new ModelAndView("newHtml/salesReport/salesReport-day");  
		//最新有数据的一天
	     return modelAndView; 
	}
	/**
	 * 销售报表主页面
	 * @Title: salesReport 
	 * @Description:  
	 * @param @return    
	 * @return ModelAndView     
	 * @throws
	 */
	@RequestMapping("/salesReport_weeks")
	@MyMethodNote(comment="销售报表周报主页:2")
	@MethodNote(comment="销售报表:16")
	public ModelAndView  salesReportWeek() {
		 ModelAndView modelAndView = new ModelAndView("newHtml/salesReport/salesReport-weeks");  
		//最新有数据的一天
	     return modelAndView; 
	}
	/**
	 * 销售报表主页面
	 * @Title: salesReport 
	 * @Description:  
	 * @param @return    
	 * @return ModelAndView     
	 * @throws
	 */
	@RequestMapping("/salesReport_month")
	@MyMethodNote(comment="销售报表月报主页:2")
	@MethodNote(comment="销售报表:16")
	public ModelAndView  salesReportMonth() {
		 ModelAndView modelAndView = new ModelAndView("newHtml/salesReport/salesReport-month");  
		//最新有数据的一天
	     return modelAndView; 
	}
	/**
	 * 销售报表主页面
	 * @Title: salesReport 
	 * @Description:  
	 * @param @return    
	 * @return ModelAndView     
	 * @throws
	 */
	@RequestMapping("/salesReport_season")
	@MyMethodNote(comment="销售报表航季报主页:2")
	@MethodNote(comment="销售报表:16")
	public ModelAndView  salesReportSeason() {
		 ModelAndView modelAndView = new ModelAndView("newHtml/salesReport/salesReport-season");  
		//最新有数据的一天
	     return modelAndView; 
	}
	/**
	 * 销售报表主页面
	 * @Title: salesReport 
	 * @Description:  
	 * @param @return    
	 * @return ModelAndView     
	 * @throws
	 */
	@RequestMapping("/salesReport_years")
	@MyMethodNote(comment="销售报表年报主页:2")
	@MethodNote(comment="销售报表:16")
	public ModelAndView  salesReportYear() {
		 ModelAndView modelAndView = new ModelAndView("newHtml/salesReport/salesReport-years");  
		//最新有数据的一天
	     return modelAndView; 
	}
	
	/**
	 * 日报接口
	 * @Title: getAirportOnLineData 
	 * @Description:  
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/restful/getDailyReportDataNew",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="销售报表日报查询:2")
	@MethodNote(comment="销售报表:16")
	@ResponseBody
	public String getDailyReportDataNew(SalesReportQuery salesReportQuery){
		Map<String,Object> retMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String callback = request.getParameter("callback");
		String str = salesReportQuery.getFlt_nbr_Count(); 
		if(!TextUtil.isEmpty(str)){
			String [] nums = str.split("/");
			
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum(nums[1]);
			}else{
				salesReportQuery.setGoNum(nums[1]);
				salesReportQuery.setHuiNum(nums[0]);
			}
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		if(TextUtil.isEmpty(salesReportQuery.getDay())){
			//最新有数据的一天
			salesReportQuery.setDay(salesReportService.getNewDate(salesReportQuery));
		}
		salesReportQuery = salesReportService.getSalesReportQueryChangeDay(salesReportQuery);
//		Map<String,Object> salesReportMap = salesReportService.getDailyReportIncomeInfo(salesReportQuery);
		//销售报表分出航段改版
		Map<String ,Object>  newmap = salesReportService.getDailyReportIncomeInfo_New(salesReportQuery);
		//收入信息
//		retMap.put("map", salesReportMap);
		retMap.put("newmap", newmap);
		retMap.put("goNum", salesReportQuery.getGoNum());
		retMap.put("backNum", salesReportQuery.getHuiNum());
		
		retMap.put("Dpt_AirPt_Cd_code", salesReportQuery.getDpt_AirPt_Cd());
		retMap.put("Pas_stp_code", salesReportQuery.getPas_stp());
		retMap.put("Arrv_Airpt_Cd_code", salesReportQuery.getArrv_Airpt_Cd());
		
		retMap.put("Dpt_AirPt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getDpt_AirPt_Cd()));
		retMap.put("Pas_stp", outPortMapper.getairportNameByCode(salesReportQuery.getPas_stp()));
		retMap.put("Arrv_Airpt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getArrv_Airpt_Cd()));
		String day = salesReportQuery.getDay();
		if(TextUtil.isEmpty(day)){
			day = sdf.format(new Date());	
			salesReportQuery.setDay(day);
		}
		String [] datee = salesReportQuery.getDay().split("-");
		retMap.put("datee",datee[0]+"-"+Integer.parseInt(datee[1])+"-"+Integer.parseInt(datee[2]));
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	
	@RequestMapping(value="/restful/getWeekReportDataNew",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="销售报表周报查询:2")
	@MethodNote(comment="销售报表:16")
	@ResponseBody
	public String getWeekReportDataNew(SalesReportQuery salesReportQuery){
		Map<String,Object> retMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		String str = salesReportQuery.getFlt_nbr_Count(); 
		String [] nums = str.split("/");
		if(nums.length<2){
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum("");
			}else{
				salesReportQuery.setGoNum("");
				salesReportQuery.setHuiNum(nums[0]);
			}
		}else{
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum(nums[1]);
			}else{
				salesReportQuery.setGoNum(nums[1]);
				salesReportQuery.setHuiNum(nums[0]);
			}
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		salesReportQuery = salesReportService.getSalesReportQueryChange(salesReportQuery);
		//销售周报分航段改版
		Map<String ,Object>  newmap = salesReportService.getWeekReportIncomeInfo_New(salesReportQuery);
		retMap.put("newmap", newmap);
		//周报
		retMap.put("Dpt_AirPt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getDpt_AirPt_Cd()));
		retMap.put("Pas_stp", outPortMapper.getairportNameByCode(salesReportQuery.getPas_stp()));
		retMap.put("Arrv_Airpt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getArrv_Airpt_Cd()));
//		retMap.put("map", salesReportMap);
		retMap.put("goNum", salesReportQuery.getGoNum());
		retMap.put("backNum", salesReportQuery.getHuiNum());
		retMap.put("Dpt_AirPt_Cd_code", salesReportQuery.getDpt_AirPt_Cd());
		retMap.put("Pas_stp_code", salesReportQuery.getPas_stp());
		retMap.put("Arrv_Airpt_Cd_code", salesReportQuery.getArrv_Airpt_Cd());
		//计划班次和实际执行班次
		String pclass = "0.0";
//		String pclass = salesReportService.getPlanClass(salesReportQuery);
//		String eclass = salesReportService.getExecutiveClass(salesReportQuery);
		Double eclass = 0.0;
		List<SalesReportDayInfo> SalesReportDayInfoList = (List<SalesReportDayInfo>)newmap.get("everyList");
		if(SalesReportDayInfoList!=null&&SalesReportDayInfoList.size()>0){
			eclass = (Double) SalesReportDayInfoList.get(0).getDataMap().get("allBc");
		}
		if(Double.parseDouble(pclass)<eclass){
			pclass = eclass+"";
		}
		retMap.put("planClass",pclass);
		retMap.put("executiveClass",eclass);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	@RequestMapping(value="/restful/getMonthlyReportDataNew",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="销售报表月报查询:2")
	@MethodNote(comment="销售报表:16")
	@ResponseBody
	public String getMonthlyReportDataNew(SalesReportQuery salesReportQuery){
		Map<String,Object> retMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		String str = salesReportQuery.getFlt_nbr_Count();   
		String [] nums = str.split("/");
		if(nums.length<2){
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum("");
			}else{
				salesReportQuery.setGoNum("");
				salesReportQuery.setHuiNum(nums[0]);
			}
		}else{
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum(nums[1]);
			}else{
				salesReportQuery.setGoNum(nums[1]);
				salesReportQuery.setHuiNum(nums[0]);
			}
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		salesReportQuery = salesReportService.getSalesReportQueryChange(salesReportQuery);
		
		
		Map<String ,Object>  newmap = salesReportService.getMonthReportIncomeInfo_New(salesReportQuery);
		retMap.put("newmap", newmap);
		//月报
//		retMap.put("map", salesReportMap);
		retMap.put("goNum", salesReportQuery.getGoNum());
		retMap.put("backNum", salesReportQuery.getHuiNum());
		retMap.put("Dpt_AirPt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getDpt_AirPt_Cd()));
		retMap.put("Pas_stp", outPortMapper.getairportNameByCode(salesReportQuery.getPas_stp()));
		retMap.put("Arrv_Airpt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getArrv_Airpt_Cd()));
		retMap.put("Dpt_AirPt_Cd_code", salesReportQuery.getDpt_AirPt_Cd());
		retMap.put("Pas_stp_code", salesReportQuery.getPas_stp());
		retMap.put("Arrv_Airpt_Cd_code", salesReportQuery.getArrv_Airpt_Cd());
//		String pclass = salesReportService.getPlanClass(salesReportQuery);
//		String eclass = salesReportService.getExecutiveClass(salesReportQuery);
		String pclass = "0.0";
		Double eclass = 0.0;
		List<SalesReportDayInfo> SalesReportDayInfoList = (List<SalesReportDayInfo>)newmap.get("everyList");
		if(SalesReportDayInfoList!=null&&SalesReportDayInfoList.size()>0){
			eclass = (Double) SalesReportDayInfoList.get(0).getDataMap().get("allBc");
		}
		if(Double.parseDouble(pclass)<eclass){
			pclass = eclass+"";
		}
		retMap.put("planClass",pclass);
		retMap.put("executiveClass",eclass);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	/**
	 * 新的航季报
	 * @Title: getHalfYearReportDataNew 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/restful/getHalfYearReportDataNew",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="销售报表航季报查询:2")
	@MethodNote(comment="销售报表:16")
	@ResponseBody
	public String getHalfYearReportDataNew(SalesReportQuery salesReportQuery){
		Map<String,Object> retMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		String str = salesReportQuery.getFlt_nbr_Count();   
		String [] nums = str.split("/");
		if(nums.length<2){
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum("");
			}else{
				salesReportQuery.setGoNum("");
				salesReportQuery.setHuiNum(nums[0]);
			}
		}else{
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum(nums[1]);
			}else{
				salesReportQuery.setGoNum(nums[1]);
				salesReportQuery.setHuiNum(nums[0]);
			}
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		salesReportQuery = salesReportService.getSalesReportQueryChange(salesReportQuery);
		Map<String ,Object>  newmap = null;
		try {
			newmap = salesReportService.getHalfYearReportIncomeInfo_New(salesReportQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		retMap.put("newmap", newmap);
		//季报
		retMap.put("goNum", salesReportQuery.getGoNum());
		retMap.put("backNum", salesReportQuery.getHuiNum());
		retMap.put("Dpt_AirPt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getDpt_AirPt_Cd()));
		retMap.put("Pas_stp", outPortMapper.getairportNameByCode(salesReportQuery.getPas_stp()));
		retMap.put("Arrv_Airpt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getArrv_Airpt_Cd()));
		retMap.put("Dpt_AirPt_Cd_code", salesReportQuery.getDpt_AirPt_Cd());
		retMap.put("Pas_stp_code", salesReportQuery.getPas_stp());
		retMap.put("Arrv_Airpt_Cd_code", salesReportQuery.getArrv_Airpt_Cd());
//		String pclass = salesReportService.getPlanClass(salesReportQuery);
//		String eclass = salesReportService.getExecutiveClass(salesReportQuery);
		String eclass = "0.0";
		String pclass = "0.0";
		List<SalesReportDayInfo> SalesReportDayInfoList = (List<SalesReportDayInfo>)newmap.get("everyList");
		if(SalesReportDayInfoList!=null&&SalesReportDayInfoList.size()>0){
			eclass = (String) SalesReportDayInfoList.get(0).getDataMap().get("zbc");
		}
		if(Double.parseDouble(pclass)<Double.parseDouble(eclass)){
			pclass = eclass;
		}
		retMap.put("planClass",pclass);
		retMap.put("executiveClass",eclass);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	@RequestMapping(value="/restful/getYearlyReportDataNew",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="销售报表年报查询:2")
	@MethodNote(comment="销售报表:16")
	@ResponseBody
	public String getYearlyReportDataNew(SalesReportQuery salesReportQuery){
		Map<String,Object> retMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		String str = salesReportQuery.getFlt_nbr_Count();   
		String [] nums = str.split("/");
		if(nums.length<2){
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum("");
			}else{
				salesReportQuery.setGoNum("");
				salesReportQuery.setHuiNum(nums[0]);
			}
		}else{
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum(nums[1]);
			}else{
				salesReportQuery.setGoNum(nums[1]);
				salesReportQuery.setHuiNum(nums[0]);
			}
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		salesReportQuery = salesReportService.getSalesReportQueryChange(salesReportQuery);
//		Map<String,Object> salesReportMap = salesReportService.getYearReportIncomeInfo(salesReportQuery);
		Map<String ,Object>  newmap = salesReportService.getYearReportIncomeInfo_New(salesReportQuery);
		retMap.put("newmap", newmap);
//		retMap.put("map", salesReportMap);
		retMap.put("goNum", salesReportQuery.getGoNum());
		retMap.put("backNum", salesReportQuery.getHuiNum());
		retMap.put("Dpt_AirPt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getDpt_AirPt_Cd()));
		retMap.put("Pas_stp", outPortMapper.getairportNameByCode(salesReportQuery.getPas_stp()));
		retMap.put("Arrv_Airpt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getArrv_Airpt_Cd()));
		retMap.put("Dpt_AirPt_Cd_code", salesReportQuery.getDpt_AirPt_Cd());
		retMap.put("Pas_stp_code", salesReportQuery.getPas_stp());
		retMap.put("Arrv_Airpt_Cd_code", salesReportQuery.getArrv_Airpt_Cd());
		String pclass = "0.0";
//		String pclass = salesReportService.getPlanClass(salesReportQuery);
//		String eclass = salesReportService.getExecutiveClass(salesReportQuery);
		String eclass = "0.0";
		List<SalesReportDayInfo> SalesReportDayInfoList = (List<SalesReportDayInfo>)newmap.get("everyList");
		if(SalesReportDayInfoList!=null&&SalesReportDayInfoList.size()>0){
			eclass = (String) SalesReportDayInfoList.get(0).getDataMap().get("zbc");
		}
		if(Double.parseDouble(pclass)<Double.parseDouble(eclass)){
			pclass = eclass;
		}
		retMap.put("planClass",pclass);
		retMap.put("executiveClass",eclass);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	/**
	 * 获得总飞行时间
	 * @Title: getTalTime 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/getTalTime")
	@MethodNote(comment="销售报表:16")
	@ResponseBody
	public Map<String,Object> getTalTime(SalesReportQuery salesReportQuery) {
		Map<String,Object> retMap = new HashMap<String,Object>();
		if("汇总".equals(salesReportQuery.getFlt_nbr_Count())||salesReportQuery.getFlt_nbr_Count()==null){
			salesReportQuery.setFlt_nbr_Count(null);
		}else{
			String str = salesReportQuery.getFlt_nbr_Count();
			String [] nums = str.split("/");
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum(nums[1]);
			}else{
				salesReportQuery.setGoNum(nums[1]);
				salesReportQuery.setHuiNum(nums[0]);
			}
		}
		FormulaUtilQuery formulaUtilQuery = new FormulaUtilQuery();
		formulaUtilQuery.setArrv_Airpt_Cd(salesReportQuery.getArrv_Airpt_Cd());
		formulaUtilQuery.setDpt_AirPt_Cd(salesReportQuery.getDpt_AirPt_Cd());
		formulaUtilQuery.setLcl_Dpt_Day(salesReportQuery.getDay());
		formulaUtilQuery.setGoNum(salesReportQuery.getGoNum());
		formulaUtilQuery.setHuiNum(salesReportQuery.getHuiNum());
		formulaUtilQuery.setPas_stp(salesReportQuery.getPas_stp());
		formulaUtilQuery.setIsIncludeExceptionData(salesReportQuery.getIsIncludeExceptionData());
		formulaUtilQuery.setIsIncludePasDpt(salesReportQuery.getIsIncludePasDpt());
		retMap.put("allTime", formulaUtil.getTotalTime(formulaUtilQuery));
		return retMap;
	} 
	/**
	 * 航线调换为正确的航线
	 * @Title: getExchangereprot 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/restful/getExchangereprot",produces="text/plain;charset=UTF-8")
	@MethodNote(comment="销售报表:16")
	@ResponseBody
	public String getExchangereprot(SalesReportQuery salesReportQuery){
		Map<String,Object> retMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		String str = salesReportQuery.getFlt_nbr_Count(); 
		if(!TextUtil.isEmpty(str)){
			String [] nums = str.split("/");
			if(nums.length<2){
				if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
					salesReportQuery.setGoNum(nums[0]);
					salesReportQuery.setHuiNum("");
				}else{
					salesReportQuery.setGoNum("");
					salesReportQuery.setHuiNum(nums[0]);
				}
			}else{
				if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
					salesReportQuery.setGoNum(nums[0]);
					salesReportQuery.setHuiNum(nums[1]);
				}else{
					salesReportQuery.setGoNum(nums[1]);
					salesReportQuery.setHuiNum(nums[0]);
				}
			}
			salesReportQuery = salesReportService.getSalesReportQueryChange(salesReportQuery);
			if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
				salesReportQuery.setPas_stp(null);
			}
		}
		retMap.put("goNum", salesReportQuery.getGoNum());
		retMap.put("backNum", salesReportQuery.getHuiNum());
		retMap.put("Dpt_AirPt_Cd_code", salesReportQuery.getDpt_AirPt_Cd());
		retMap.put("Pas_stp_code", salesReportQuery.getPas_stp());
		retMap.put("Arrv_Airpt_Cd_code", salesReportQuery.getArrv_Airpt_Cd());
		retMap.put("Dpt_AirPt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getDpt_AirPt_Cd()));
		retMap.put("Pas_stp", outPortMapper.getairportNameByCode(salesReportQuery.getPas_stp()));
		retMap.put("Arrv_Airpt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getArrv_Airpt_Cd()));
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	/**
	 * 日报
	 * @Title: getDailyReportData 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/getDailyReportData")
	@ResponseBody
	@MethodNote(comment="销售报表:16")
	@MyMethodNote(comment="日报:2")
	public Map<String,Object> getDailyReportData(SalesReportQuery salesReportQuery) {
		if("汇总".equals(salesReportQuery.getFlt_nbr_Count())||salesReportQuery.getFlt_nbr_Count()==null){
			salesReportQuery.setFlt_nbr_Count(null);
		}else{
			String str = salesReportQuery.getFlt_nbr_Count();
			String [] nums = str.split("/");
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum(nums[1]);
			}else{
				salesReportQuery.setGoNum(nums[1]);
				salesReportQuery.setHuiNum(nums[0]);
			}
			
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		Map<String,Object> retMap = new HashMap<String,Object>();
		salesReportQuery = salesReportService.getSalesReportQueryChange(salesReportQuery);
		List<SalesReport> salesReportList = salesReportService.getDailyReportList(salesReportQuery);
		retMap.put("list", salesReportList);
		//计算小时成本
		if(salesReportList!=null&&salesReportList.size()>0){
			FormulaUtilQuery formulaUtilQuery = new FormulaUtilQuery();
			formulaUtilQuery.setLcl_Dpt_Day(salesReportQuery.getDay());
			formulaUtilQuery.setSite_num(salesReportList.get(0).getTal_Nbr_Set());
			formulaUtilQuery.setRoleId(UserContext.getCompanyId());
			retMap.put("hourPrice", formulaUtil.getHourPrice(formulaUtilQuery));
		}else{
			retMap.put("hourPrice", "0");
		}
		
		return retMap;
	} 
	@RequestMapping("/getDayAndMonth")
	@MethodNote(comment="销售报表:16")
	@ResponseBody
	public Map<String,String> getDayAndMonth(SalesReportQuery salesReportQuery){
		if("汇总".equals(salesReportQuery.getFlt_nbr_Count())||salesReportQuery.getFlt_nbr_Count()==null){
			salesReportQuery.setFlt_nbr_Count(null);
		}else{
			String str = salesReportQuery.getFlt_nbr_Count();
			String [] nums = str.split("/");
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum(nums[1]);
			}else{
				salesReportQuery.setGoNum(nums[1]);
				salesReportQuery.setHuiNum(nums[0]);
			}
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		String newDateTemp = salesReportService.getNewDate(salesReportQuery);
		String [] str = newDateTemp.split("-");
		Map<String,String> retMap = new HashMap<String,String>();
		retMap.put("defaultDate", newDateTemp);
		retMap.put("defaultMonth", str[0]+"-"+str[1]);
		retMap.put("defaultYear", str[0]);
		return retMap;
	}
	/**
	 * 周报 
	 * @Title: getWeeklyReportData 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,List<OtherSalesReport>>     
	 * @throws
	 */
	@RequestMapping("/getWeeklyReportData")
	@ResponseBody
	@MethodNote(comment="销售报表:16")
	@MyMethodNote(comment="周报:2")
	public Map<String,List<OtherSalesReport>> getWeeklyReportData(SalesReportQuery salesReportQuery) {
		if("汇总".equals(salesReportQuery.getFlt_nbr_Count())||salesReportQuery.getFlt_nbr_Count()==null){
			salesReportQuery.setFlt_nbr_Count(null);
		}else{
			String str = salesReportQuery.getFlt_nbr_Count();
			String [] nums = str.split("/");
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum(nums[1]);
			}else{
				salesReportQuery.setGoNum(nums[1]);
				salesReportQuery.setHuiNum(nums[0]);
			}
		}
		Map<String,List<OtherSalesReport>> retMap = new HashMap<String,List<OtherSalesReport>>();
		Map<String,List<OtherSalesReport>> otherSalesReportMap = salesReportService.getWeeklyReportData(salesReportQuery);
		retMap = otherSalesReportMap;
		return retMap;
	} 
	/**
	 * 月报
	 * @Title: getMonthlyReportData 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,List<OtherSalesReport>>     
	 * @throws
	 */
	@RequestMapping("/getMonthlyReportData")
	@ResponseBody
	@MethodNote(comment="销售报表:16")
	@MyMethodNote(comment="月报:2")
	public Map<String,List<OtherSalesReport>> getMonthlyReportData(SalesReportQuery salesReportQuery) {
		if("汇总".equals(salesReportQuery.getFlt_nbr_Count())||salesReportQuery.getFlt_nbr_Count()==null){
			salesReportQuery.setFlt_nbr_Count(null);
		}else{
			String str = salesReportQuery.getFlt_nbr_Count();
			String [] nums = str.split("/");
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum(nums[1]);
			}else{
				salesReportQuery.setGoNum(nums[1]);
				salesReportQuery.setHuiNum(nums[0]);
			}
		}
		Map<String,List<OtherSalesReport>> retMap = new HashMap<String,List<OtherSalesReport>>();
		List<OtherSalesReport> otherSalesReportList = salesReportService.getMonthlyReportData(salesReportQuery);
		retMap.put("list", otherSalesReportList);
		return retMap;
	} 
	/**
	 * 航季报
	 * @Title: getHalfYearReportData 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,List<YearSalesReport>>     
	 * @throws
	 */
	@RequestMapping("/getHalfYearReportData")
	@ResponseBody
	@MethodNote(comment="销售报表:16")
	@MyMethodNote(comment="季报:2")
	public Map<String,List<List<YearSalesReport>>> getHalfYearReportData(SalesReportQuery salesReportQuery) {
		if("汇总".equals(salesReportQuery.getFlt_nbr_Count())||salesReportQuery.getFlt_nbr_Count()==null){
			salesReportQuery.setFlt_nbr_Count(null);
		}else{
			String str = salesReportQuery.getFlt_nbr_Count();
			String [] nums = str.split("/");
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum(nums[1]);
			}else{
				salesReportQuery.setGoNum(nums[1]);
				salesReportQuery.setHuiNum(nums[0]);
			}
		}
		Map<String,List<List<YearSalesReport>>> retMap = new HashMap<String,List<List<YearSalesReport>>>();
		List<List<YearSalesReport>> yearSalesReportListList = salesReportService.getHalfYearReportData(salesReportQuery);
		retMap.put("list", yearSalesReportListList);
		return retMap;
	} 
	/**
	 * 年报
	 * @Title: getYearlyReportData 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,List<List<YearSalesReport>>>     
	 * @throws
	 */
	@RequestMapping("/getYearlyReportData")
	@ResponseBody
	@MethodNote(comment="销售报表:16")
	@MyMethodNote(comment="年报:2")
	public Map<String,List<List<YearSalesReport>>> getYearlyReportData(SalesReportQuery salesReportQuery) {
		if("汇总".equals(salesReportQuery.getFlt_nbr_Count())||salesReportQuery.getFlt_nbr_Count()==null){
			salesReportQuery.setFlt_nbr_Count(null);
		}else{
			String str = salesReportQuery.getFlt_nbr_Count();
			String [] nums = str.split("/");
			if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
				salesReportQuery.setGoNum(nums[0]);
				salesReportQuery.setHuiNum(nums[1]);
			}else{
				salesReportQuery.setGoNum(nums[1]);
				salesReportQuery.setHuiNum(nums[0]);
			}
		}
		Map<String,List<List<YearSalesReport>>> retMap = new HashMap<String,List<List<YearSalesReport>>>();
		List<List<YearSalesReport>> yearSalesReportListList = salesReportService.getYearlyReportData(salesReportQuery);
		retMap.put("list", yearSalesReportListList);
		return retMap;
	} 
	
	/**
	 * 得到航班号
	 * @Title: getHbh 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/getHbh")
	@MethodNote(comment="销售报表:13;航线历史收益统计:1")
	@ResponseBody
	public Map<String,Object> getHbh(SalesReportQuery salesReportQuery) {
		List<FlyNum> hbhList = salesReportService.getHbh(salesReportQuery);
		Map<String,Object> retMap = new HashMap<String,Object>();
		List<String> hblist = new ArrayList<String>();
		String tempFlyNum = "";
		String tempFlyNum2 = "";
		for (FlyNum flyNum2 : hbhList) {
			String str = flyNum2.getFlyNum();
			if(str.length()==6&&TextUtil.isNum(str.substring(5, 6))){
				String firststr = str.substring(0, 2);
				int lasttwostr = Integer.parseInt(str.substring(2, 5));
				int laststr = Integer.parseInt(str.substring(5, 6));
				String pdStr = "";
				if(laststr%2==0){
					if(laststr==0){
						pdStr =firststr + (lasttwostr-1) + "9";
					}else{
						pdStr = firststr +lasttwostr+  (laststr-1);
					}
				}else{
					if(laststr==9){
						pdStr =firststr + (lasttwostr+1) + "0";
					}else{
						pdStr = firststr +lasttwostr+  (laststr+1);
					}
				}
				tempFlyNum = str + "/" + pdStr;
				tempFlyNum2 = pdStr + "/" + str;
				if(!hblist.contains(tempFlyNum)&&!hblist.contains(tempFlyNum2)){
					int a = Integer.parseInt(tempFlyNum.substring(tempFlyNum.length()-1, tempFlyNum.length()));
					if(a%2==0){
						hblist.add(tempFlyNum);
					}else{
						hblist.add(tempFlyNum2);
					}
				}
			}
		}
		retMap.put("list", hblist);
		return retMap;
	} 
	@RequestMapping("/getDateList")
	@MethodNote(comment="销售报表:16;航班动态:6")
	@ResponseBody
	public Map<String,Object> getDateList(SalesReportQuery salesReportQuery) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> retMap = new HashMap<String,Object>();
		if(TextUtil.isEmpty(salesReportQuery.getDay())){
			salesReportQuery.setDay(sdf.format(new Date()));
		}
		String str = salesReportQuery.getFlt_nbr_Count();  
		if(!TextUtil.isEmpty(str)){
			String [] nums = str.split("/");
			if(nums.length<2){
				if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
					salesReportQuery.setGoNum(nums[0]);
					salesReportQuery.setHuiNum("");
				}else{
					salesReportQuery.setGoNum("");
					salesReportQuery.setHuiNum(nums[0]);
				}
			}else{
				if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
					salesReportQuery.setGoNum(nums[0]);
					salesReportQuery.setHuiNum(nums[1]);
				}else{
					salesReportQuery.setGoNum(nums[1]);
					salesReportQuery.setHuiNum(nums[0]);
				}
			}
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		String day = salesReportQuery.getDay();
		String startMonth = TextUtil.addDateMonth(day,0);
		String endMonth = TextUtil.addDateMonth(day,0);
		String startDay = startMonth + "-01";
		String endDay = endMonth + "-31";
		//得到最近三个月有数据的日期
		salesReportQuery.setStartTime(startDay);
		salesReportQuery.setEndTime(endDay);
		salesReportQuery = salesReportService.getSalesReportQueryChangeDay(salesReportQuery);
		List<String> datesList = salesReportService.getHavingDataDayList(salesReportQuery);
		List<FltDyDetial> fltDyDetialList = salesReportService.getFltDyDetialList(salesReportQuery,datesList);
		Map<String,Object> mapp = new HashMap<String,Object>();
		mapp.put("dateList", datesList);
		mapp.put("fltDyDetialList", fltDyDetialList);
		mapp.put("Dpt_AirPt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getDpt_AirPt_Cd()));
		mapp.put("Pas_stp", outPortMapper.getairportNameByCode(salesReportQuery.getPas_stp()));
		mapp.put("Arrv_Airpt_Cd", outPortMapper.getairportNameByCode(salesReportQuery.getArrv_Airpt_Cd()));
		retMap.put("success", mapp);
		return retMap;
	}
	@RequestMapping("/getDates")
	@MethodNote(comment="销售报表:16;航班动态:6")
	@ResponseBody
	public Map<String,Object> getDates(SalesReportQuery salesReportQuery) {
		String str = salesReportQuery.getFlt_nbr_Count();  
		if(!TextUtil.isEmpty(str)){
			String [] nums = str.split("/");
			if(nums.length<2){
				if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
					salesReportQuery.setGoNum(nums[0]);
					salesReportQuery.setHuiNum("");
				}else{
					salesReportQuery.setGoNum("");
					salesReportQuery.setHuiNum(nums[0]);
				}
			}else{
				if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
					salesReportQuery.setGoNum(nums[0]);
					salesReportQuery.setHuiNum(nums[1]);
				}else{
					salesReportQuery.setGoNum(nums[1]);
					salesReportQuery.setHuiNum(nums[0]);
				}
			}
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		Map<String,Object> retMap = salesReportService.getDates(salesReportQuery);
		String day = salesReportQuery.getMonth();
		String startDay = day + "-01";
		String endDay = day + "-31";
		salesReportQuery.setStartTime(startDay);
		salesReportQuery.setEndTime(endDay);
		List<FltDyDetial> fltDyDetialList = salesReportService.getFltDyDetialList(salesReportQuery,null);
		retMap.put("fltDyDetialList", fltDyDetialList);
		return retMap;
	}
	
	@RequestMapping("/getMonths")
	@MethodNote(comment="销售报表:16")
	@ResponseBody
	public Map<String,Object> getMonths(SalesReportQuery salesReportQuery) {
		String str = salesReportQuery.getFlt_nbr_Count();  
		if(!TextUtil.isEmpty(str)){
			String [] nums = str.split("/");
			if(nums.length<2){
				if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
					salesReportQuery.setGoNum(nums[0]);
					salesReportQuery.setHuiNum("");
				}else{
					salesReportQuery.setGoNum("");
					salesReportQuery.setHuiNum(nums[0]);
				}
			}else{
				if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
					salesReportQuery.setGoNum(nums[0]);
					salesReportQuery.setHuiNum(nums[1]);
				}else{
					salesReportQuery.setGoNum(nums[1]);
					salesReportQuery.setHuiNum(nums[0]);
				}
			}
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		Map<String,Object> retMap = salesReportService.getMonths(salesReportQuery);
		return retMap;
	}
	@RequestMapping("/getSeasons")
	@ResponseBody
	public Map<String,Object> getSeasons(SalesReportQuery salesReportQuery) {
		String str = salesReportQuery.getFlt_nbr_Count();  
		if(!TextUtil.isEmpty(str)){
			String [] nums = str.split("/");
			if(nums.length<2){
				if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
					salesReportQuery.setGoNum(nums[0]);
					salesReportQuery.setHuiNum("");
				}else{
					salesReportQuery.setGoNum("");
					salesReportQuery.setHuiNum(nums[0]);
				}
			}else{
				if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
					salesReportQuery.setGoNum(nums[0]);
					salesReportQuery.setHuiNum(nums[1]);
				}else{
					salesReportQuery.setGoNum(nums[1]);
					salesReportQuery.setHuiNum(nums[0]);
				}
			}
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		Map<String,Object> retMap = salesReportService.getSeasons(salesReportQuery);
		return retMap;
	}
	
	@RequestMapping("/getYears")
	@ResponseBody
	public Map<String,Object> getYears(SalesReportQuery salesReportQuery) {
		String str = salesReportQuery.getFlt_nbr_Count();  
		if(!TextUtil.isEmpty(str)){
			String [] nums = str.split("/");
			if(nums.length<2){
				if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
					salesReportQuery.setGoNum(nums[0]);
					salesReportQuery.setHuiNum("");
				}else{
					salesReportQuery.setGoNum("");
					salesReportQuery.setHuiNum(nums[0]);
				}
			}else{
				if(HbhCharater(nums[0].substring(nums[0].length()-1, nums[0].length()))%2==1){
					salesReportQuery.setGoNum(nums[0]);
					salesReportQuery.setHuiNum(nums[1]);
				}else{
					salesReportQuery.setGoNum(nums[1]);
					salesReportQuery.setHuiNum(nums[0]);
				}
			}
		}
		if(TextUtil.isEmpty(salesReportQuery.getPas_stp())){
			salesReportQuery.setPas_stp(null);
		}
		Map<String,Object> retMap = salesReportService.getYears(salesReportQuery);
		return retMap;
	}
	
	
	/**
	 * 根据公式得到航班号最后一位字母对应的数字
	 * @Title: HbhCharater 
	 * @Description:  
	 * @param @param numnew
	 * @param @return    
	 * @return int     
	 * @throws
	 */
	public int HbhCharater(String numnew){
		int lastH = 0;
		if("Z".equals(numnew)){
			lastH = 0;
		}else if("Y".equals(numnew)){
			lastH = 1;
		}else if("X".equals(numnew)){
			lastH = 2;
		}else if("W".equals(numnew)){
			lastH = 3;
		}else if("V".equals(numnew)){
			lastH = 4;
		}else if("U".equals(numnew)){
			lastH = 5;
		}else if("T".equals(numnew)){
			lastH = 6;
		}else if("S".equals(numnew)){
			lastH = 7;
		}else if("R".equals(numnew)){
			lastH = 8;
		}else if("Q".equals(numnew)){
			lastH = 9;
		}else  
		lastH = Integer.parseInt(numnew);
		return lastH;
	}
	
	
}
