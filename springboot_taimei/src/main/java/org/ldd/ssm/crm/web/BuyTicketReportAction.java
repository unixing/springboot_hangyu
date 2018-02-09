package org.ldd.ssm.crm.web;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.LinkedMap;
import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.BuyTicketReport;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.query.BuyTicketReportQuery;
import org.ldd.ssm.crm.service.BuyTicketReportService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * 上客进度表控制类
 * @Title:BuyTicketReportAction 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-19 下午12:01:12
 */
@Controller
public class BuyTicketReportAction {
	@Autowired
	private BuyTicketReportService buyTicketReportService;
	@Autowired
	private OutPortMapper outPortMapper;
	/**
	 * 上客进度表主页
	 * @Title: buyTicketReport 
	 * @Description:  
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping("/buyTicketReport")
	@MyMethodNote(comment="销售动态主页:2")
	@MethodNote(comment="销售动态:5")
	public String  buyTicketReport() {
		return "newHtml/salesDynamic/salesDynamic";
	}
	/**
	 * 得到上客进度表数据
	 * @Title: getBuyTicketReportData 
	 * @Description:  
	 * @param @param buyTicketReportQuery
	 * @param @return    
	 * @return Map<String,List<BuyTicketReport>>     
	 * @throws
	 */
	@RequestMapping("/getBuyTicketReportData")
	@ResponseBody
	@MethodNote(comment="销售动态:5")
	@MyMethodNote(comment="查询上客记录:2")
	public Map<String, List<BuyTicketReport>> getBuyTicketReportData(BuyTicketReportQuery buyTicketReportQuery) {
		return  buyTicketReportService.getBuyTicketReportData(buyTicketReportQuery);
	} 
	
	/**
	 * 得到航段数据
	 * @Title: getBuyTicketReportData 
	 * @Description:  
	 * @param @param buyTicketReportQuery
	 * @param @return    
	 * @return Map<String,List<BuyTicketReport>>     
	 * @throws
	 */
	@RequestMapping("/getBuyTicketReportLine")
	@ResponseBody
	@MethodNote(comment="销售动态:5")
	@MyMethodNote(comment="查询上客航线:2")
	public List<String> getBuyTicketReportLine(BuyTicketReportQuery buyTicketReportQuery) {
		return  buyTicketReportService.getBuyTicketReportLine(buyTicketReportQuery);
	} 
	
	@RequestMapping("/getBuyTicketReportLineData")
	@ResponseBody
	@MethodNote(comment="销售动态:5")
	@MyMethodNote(comment="查询上客航线数据:2")
	public Map<String,Object> getBuyTicketReportLineData(BuyTicketReportQuery buyTicketReportQuery) {
		return  buyTicketReportService.getBuyTicketReportLineData(buyTicketReportQuery,null);
	} 
	
	/**
	 * 得到销售动态数据
	 * @Title: getBuyTicketReportLineDataNew 
	 * @Description:  
	 * @param @param buyTicketReportQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/restful/getBuyTicketReportLineDataNew",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="销售动态查询:2")
	@MethodNote(comment="销售动态:5")
	@ResponseBody
	public String getBuyTicketReportLineDataNew(BuyTicketReportQuery buyTicketReportQuery){
		Map<String,Object> retMap = new LinkedMap();
		Map<String,Object> retMapNew = new HashMap<String,Object> ();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		Map<String,Object> flyListMap = buyTicketReportService.getBuyTicketReportLineNew(buyTicketReportQuery);
		List<String> flyList = (List<String>) flyListMap.get("hangxianList");
		//算单段的
//		for (String flt : flyList) {
//			String [] arr = flt.split(",");
//			String [] fltStr = arr[1].split("-");
//			Map<String,Object> mapp = new HashMap<String,Object>();
//			String fltt = "";
//			if(fltStr.length<=2){
//				buyTicketReportQuery.setDpt_AirPt_Cd(fltStr[0]);
//    			buyTicketReportQuery.setArrv_Airpt_Cd(fltStr[1]);
//    			buyTicketReportQuery.setPst_cd(null);
//    			fltt = fltStr[0]+"-"+ fltStr[1];
//    			buyTicketReportQuery.setFlt_nbr_Count(arr[0]);
//    			mapp = buyTicketReportService.getBuyTicketReportLineData(buyTicketReportQuery,flyList);
//    			retMap.put(fltt, mapp);
//    		}
//		}
		retMap = buyTicketReportService.getBuyTicketReportLineData(buyTicketReportQuery,flyListMap);
//		for (Map.Entry<String,Object> entry : retMap.entrySet()) {  
//		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
//		} 
		//算单程的 经停
		if(flyList.size()>=2){
			retMap = buyTicketReportService.getBuyTicketReportSingleLineData(flyList,retMap);
		}
		if(retMap.size()>0){
			retMapNew.put("dataMap", retMap);
		}else{
			retMapNew.put("dataMap", "");
		}
		retMapNew.put("fltNumq", flyListMap.get("fltNumq")) ;
		retMapNew.put("fltNumh", flyListMap.get("fltNumh")) ;
		retMapNew.put("dptName", flyListMap.get("dptName")) ;
		retMapNew.put("pstName", flyListMap.get("pstName")) ;
		retMapNew.put("arrName", flyListMap.get("arrName")) ;
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMapNew);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	@RequestMapping(value="/restful/getAirLine",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="销售动态查询:2")
	@MethodNote(comment="销售动态:5")
	@ResponseBody
	public String getAirLine(BuyTicketReportQuery buyTicketReportQuery){
		Map<String,Object> retMapNew = new HashMap<String,Object> ();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		List<String> airLineList = new ArrayList<String>();
		if(TextUtil.isEmpty(buyTicketReportQuery.getFlt_nbr_Count())){
			retMapNew.put("list", new ArrayList<String>());
		}else{
			airLineList = buyTicketReportService.getAirLine(buyTicketReportQuery);
			retMapNew.put("list", airLineList);
		}
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMapNew);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	@RequestMapping(value="/restful/getBuyTicketReportLineDataNewDate",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="销售动态查询:2")
	@MethodNote(comment="销售动态:5")
	@ResponseBody
	public String getBuyTicketReportLineDataNewDate(BuyTicketReportQuery buyTicketReportQuery){
		Map<String,Object> retMapNew = new HashMap<String,Object> ();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		if(!TextUtil.isEmpty(buyTicketReportQuery.getFlt_nbr_Count())){
			String [] str = buyTicketReportQuery.getFlt_nbr_Count().split("/");
			if(str.length==2){
				buyTicketReportQuery.setFlt_nbr_Count(str[0]);
				buyTicketReportQuery.setFlt_nbr_Counth(str[1]);
			}
		}
		String newDate = buyTicketReportService.getBuyTicketReportLineDataNewDate(buyTicketReportQuery);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(TextUtil.isEmpty(newDate)){
			newDate =sdf.format(new Date());
		}
		retMapNew.put("newDate", newDate);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMapNew);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	

}
