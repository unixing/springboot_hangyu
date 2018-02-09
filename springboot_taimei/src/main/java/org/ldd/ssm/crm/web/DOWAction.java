package org.ldd.ssm.crm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.AcquisitionTime;
import org.ldd.ssm.crm.domain.DOW;
import org.ldd.ssm.crm.domain.MonthData;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.DOWObject;
import org.ldd.ssm.crm.query.DOWQuery;
import org.ldd.ssm.crm.query.MonthObject;
import org.ldd.ssm.crm.query.SystemLogQuery;
import org.ldd.ssm.crm.service.IDOWServicce;
import org.ldd.ssm.crm.utils.DataUtils;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *DOW的前台控制器层
 */
@Controller
public class DOWAction {
	//spring注解注入对象
	@Autowired
	private IDOWServicce iDOWservice;
	//springMVC请求的映射地址
	@MyMethodNote(comment="DOW分析主页:2")
	@RequestMapping("/DOWindex")
	public String getDOWindex() {
			return "/charts/DOW/DOW";
	}
	@RequestMapping("/DOWChilder")
	public String getChilderPage(String index) {
		UserContext.setData("index", index);
		return "DOWChilder";
	}
	
	@RequestMapping("/DOW_list")
	@ResponseBody
//	@MethodNote(comment="查询数据:73")
	@MyMethodNote(comment="查询数据DOW:2")
	public DOWObject<DOW> getDOW_list(DOWQuery dta_Sce) {
		if(dta_Sce.getDta_Sce()==null||"请选择数据源".equals(dta_Sce.getDta_Sce())){
			return null;
		}
		if(dta_Sce.getLcl_Dpt_Day()==null||"请选择年份".equals(dta_Sce.getLcl_Dpt_Day())){
			return null;
		}
		if("".equals(dta_Sce.getFlt_nbr())){
			dta_Sce.setFlt_nbr(null);
		}
//		新增航班号查询条件，（“始发到达”或“航班号”可任选其一或两者同时指定查询），当指定航班号时统计分析该航班DOW情况，若仅指定“始发到达”则统计分析该航线所有航班DOW情况。
		dta_Sce.setLcl_Dpt_Day(dta_Sce.getLcl_Dpt_Day().substring(0, 4));
		DOWObject<DOW> method = iDOWservice.getMethod(dta_Sce);
		return method;
	}
	@RequestMapping("/getMinMonth")
	@ResponseBody
	public String getMinMonth(DOWQuery dta_Sce) {
		if(dta_Sce.getDta_Sce()==null||"请选择数据源".equals(dta_Sce.getDta_Sce())){
			return null;
		}
		if(dta_Sce.getLcl_Dpt_Day()==null||"请选择年份".equals(dta_Sce.getLcl_Dpt_Day())){
			return null;
		}
		if("".equals(dta_Sce.getFlt_nbr())){
			dta_Sce.setFlt_nbr(null);
		}
//		新增航班号查询条件，（“始发到达”或“航班号”可任选其一或两者同时指定查询），当指定航班号时统计分析该航班DOW情况，若仅指定“始发到达”则统计分析该航线所有航班DOW情况。
		dta_Sce.setLcl_Dpt_Day(dta_Sce.getLcl_Dpt_Day().substring(0, 4));
		DOWObject<DOW> method = iDOWservice.getMethod(dta_Sce);
		String minMonth = "01";
		if(method!=null&&method.getRows()!=null&&method.getRows().size()>0){
			minMonth = method.getRows().get(0).getMethod();
		}
		return minMonth;
	}
	/***
	 * 这里是返回月数据的方法
	 */
	@RequestMapping("/DOW_detailList")
	@ResponseBody
//	@MethodNote(comment="查询月数据:73")
	public MonthObject<MonthData> DOW_detailList() {
		String indexData = UserContext.getIndexData("index");
		String dowData = UserContext.getIndexData("year");
		DOWQuery dowData2 = UserContext.getDOWData("lcl");
		dowData2.setMonth(indexData);
		dowData2.setLcl_Dpt_Day(dowData);
		MonthObject<MonthData> monthData = iDOWservice.getMonthData(dowData2);
		return monthData;
	}
	/**
	 * 生成数据源下拉列表
	 * @return
	 */
	@RequestMapping("/DOW_company")
	@ResponseBody
	public List<Z_Airdata> getDOW_company() {
		return iDOWservice.getCompany();
	}
	/**
	 * 生成年份时间下拉列表
	 * @return
	 */
	@RequestMapping("/DOW_Date")
	@ResponseBody
	public List<AcquisitionTime> getDOW_Date() {
		return iDOWservice.getDOW_Date();
	}
	//--------------------------------以下为图形界面----------------------------------------------
	/**
	 * 去程超过均班人数的班次统计
	 * @param searchKey
	 * @return
	 */
	@RequestMapping("/DOW_Clz_Graph")
	@ResponseBody
//	@MethodNote(comment="DOW班次展示:73")
	@MyMethodNote(comment="DOW班次展示:2")
	public Map<String, Object> DailyStatement_graph(SystemLogQuery searchKey){
		DOWQuery dowData2 = UserContext.getDOWData("lcl");
		dowData2.setMonth(searchKey.getSearchKey());
		dowData2.setLcl_Dpt_Day(dowData2.getLcl_Dpt_Day());
		List<DOW> rows = iDOWservice.getViewSum(dowData2);
		if(rows.size()==0){
			return DataUtils.getNullMap(searchKey,dowData2);
		}
		return DataUtils.getMap(rows,searchKey,dowData2);
		
	}
	/**
	 * 此方法在点击当月数据之后,联动显示图像界面使用
	 * @param searchKey
	 * @return
	 */
	@RequestMapping("/DOW_Clz_Graph2")
	@ResponseBody
//	@MethodNote(comment="DOW人数展示:73")
	@MyMethodNote(comment="DOW人数展示:2")
	public Map<String, Object> DailyStatement_graph2(SystemLogQuery searchKey){
		DOWQuery dowData2 = UserContext.getDOWData("lcl");
		List<String> arrMethod = DataUtils.getArrayMethod(searchKey);
		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<List<String[]>> lists2 = new ArrayList<List<String[]>>();
		List<String> method = new ArrayList<String>(); 
		List<DOW> rows = new ArrayList<DOW>();
		for (String string : arrMethod) {
			dowData2.setMonth(string);
			List<String[]> list = new ArrayList<String[]>();
			rows = iDOWservice.getViewSumPer(dowData2);
			if(rows.isEmpty()){
				continue;
			}
			for (DOW dt : rows) {
					if(dt!=null){
					String[] str1 = {"星期一",dt.getMonTotal()+""};
					String[] str2 = {"星期二",dt.getTueTotal()+""};
					String[] str3 = {"星期三",dt.getWedTotal()+""};
					String[] str4 = {"星期四",dt.getThurTotal()+""};
					String[] str5 = {"星期五",dt.getFriTotal()+""};
					String[] str6 = {"星期六",dt.getSatTotal()+""};
					String[] str7 = {"星期日",dt.getSunTotal()+""};
					list.add(str1);
					list.add(str2);
					list.add(str3);
					list.add(str4);
					list.add(str5);
					list.add(str6);
					list.add(str7);
					method.add(dt.getMethod()+"月");
				}
			}
			lists2.add(list);
		}
		//将数据组装成需要的数据,
		List<Map<String, String>> needData;
		List<Map<String, String>> echart2 ;
		Map<String, String> echart;
		if(lists2.size()!=0){
			needData = DataUtils.getNeedData(lists2);
			rootMap.put("list", needData);
			echart2 = new ArrayList<Map<String, String>>();
			echart = new HashMap<String, String>();
			echart.put("MonTotal",rows.get(rows.size()-1).getInformation()+" DOW客量变化对比");
			echart2.add(echart);
		}else{
			needData = DataUtils.getNullData();
			rootMap.put("list", needData);
			echart2 = new ArrayList<Map<String, String>>();
			echart = new HashMap<String, String>();
			String indexData = UserContext.getIndexData("year");
			String[] split = indexData.split("-");
			echart.put("MonTotal",dowData2.getLcl_Dpt_Day()+"年"+searchKey.getSearchKey()+"月"+split[0]+"到"+split[1]+" DOW客量变化对比");
			echart2.add(echart);
		}
		rootMap.put("method",method);
		rootMap.put("echart2", echart2);
		return rootMap;
	}
}
