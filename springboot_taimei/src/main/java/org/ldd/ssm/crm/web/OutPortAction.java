package org.ldd.ssm.crm.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.ClassRanking;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.OutPort;
import org.ldd.ssm.crm.query.AirportOperatingHistoryQuery;
import org.ldd.ssm.crm.query.DOWObject;
import org.ldd.ssm.crm.query.DOWQuery;
import org.ldd.ssm.crm.service.IOutPortService;
import org.ldd.ssm.crm.utils.DataUtils;
import org.ldd.ssm.crm.utils.TextUtil;
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
public class OutPortAction {
	@Autowired
	private IOutPortService iOutPortService;
	//springMVC请求的映射地址
	@RequestMapping("/outPort")
	@MethodNote(comment="机场历史运营:2")
	@MyMethodNote(comment="机场历史运营主页:2")
	public String getOutPort() {
		return "newHtml/airport-operating-history";
	}
	
	@RequestMapping("/airport_fltNbr_flydata")
	@MethodNote(comment="机场航班运营数据表:2")
	@MyMethodNote(comment="机场航班运营数据表:2")
	@ResponseBody
	public Map<String,Object> airport_fltNbr_flydata(AirportOperatingHistoryQuery query){
		Map<String, List<Map<String, Map<String, String>>>>  tempMap = null;
		Map<String,Object> retMap = new HashMap<String,Object>();
		try {
			tempMap = iOutPortService.getFltNbrInfoData(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String [] keys = new String[tempMap.entrySet().size()-1];
		String exceptionFlag = "";
		int i=0;
		for (Entry<String,List<Map<String, Map<String, String>>>> entry : tempMap.entrySet()) {
			String key = entry.getKey();
			String [] t = key.split(",");
			if("exceptionFlag".equals(key)){
				exceptionFlag= entry.getValue().get(0).get("exceptionFlag").get("exceptionFlag");
			}else{
				keys[i] = t[1]+","+t[0];
				i++;
			}
		}
		Arrays.sort(keys);
		List<Map<String, List<Map<String, Map<String, String>>>>> list = new ArrayList<Map<String, List<Map<String, Map<String, String>>>>>();
		for (String key : keys) {
			String keyt = key.split(",")[1]+","+key.split(",")[0];
			Map<String, List<Map<String, Map<String, String>>>>  remap = new HashMap<String, List<Map<String, Map<String, String>>>>();
			remap.put(keyt, tempMap.get(keyt));
			list.add(remap);
		}
		retMap.put("exceptionFlag", exceptionFlag);
		retMap.put("list", list);
		return retMap;
	}
	
	//----------------新页面需求的数据--不含过站,只是取包含机场的数据
	@RequestMapping("/airport_Operating_History")
	@MethodNote(comment="机场历史运营:2")
	@MyMethodNote(comment="机场历史运营查询:2")
	@ResponseBody
	public Map<String, Object> airport_Operating_History(AirportOperatingHistoryQuery query){
		//------------------出港数据
		String dpt_AirPt_Cd = query.getDpt_AirPt_Cd();
		String cpy_Nm = query.getCpy_Nm();
		Map<String, Object> map = new HashMap<String, Object>();
		//进港新
		DecimalFormat df = new DecimalFormat("0.00");
		DOWQuery dowData = new DOWQuery();
		dowData.setArrv_Airpt_Cd(dpt_AirPt_Cd);
		dowData.setDpt_AirPt_Cd(null);
		dowData.setIsIncludeExceptionData(query.getIsIncludeExceptionData());
		dowData.setIsIncludeExceptionHuangduan(query.getIsIncludeExceptionHuangduan());
		dowData.setMonthOrDay(query.getMonthOrDay());
		dowData.setStartTime(query.getStartTime());
		dowData.setEndTime(query.getEndTime());
		dowData.setCpy_Nm(cpy_Nm);
		Map<String, Map<String, Map<String, String>>>  tempMap = iOutPortService.airportHistroy(dowData);
		String exceptionFlagJg = tempMap.get("exceptionFlag").get("exceptionFlag").get("exceptionFlag");
		Map<String, Map<String, String>> monthll = tempMap.get("monthll");
		Map<String, Object> rootMap1 = new HashMap<String, Object>();
		List<Map<String, Object>> data1 = new ArrayList<Map<String, Object>>();
		String flag = "";
		if(monthll.isEmpty()){
			flag = "0";
		}else{
			for(String key : monthll.keySet()){ 
			    Map<String,String> dataMap = (Map<String, String>) monthll.get(key); 
			    Map<String, Object> map1 = new HashMap<String, Object>();
			    map1.put("label",dataMap.get("label"));
				map1.put("idd_moh",dataMap.get("idd_moh"));
				map1.put("Cla_Nbr",dataMap.get("Cla_Nbr"));
				map1.put("Tal_Nbr_Set",dataMap.get("Tal_Nbr_Set"));
				map1.put("Grp_moh",dataMap.get("Grp_moh"));
				map1.put("Grp_Ine",dataMap.get("Grp_Ine"));
				map1.put("Tol_Ine",dataMap.get("Tol_Ine"));
				map1.put("key",key);
				data1.add(map1);
			}
			flag = "1";
		}
		try {
			rootMap1.put("list",TextUtil.listSort(data1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		rootMap1.put("flag", flag);
		map.put("outPort1",rootMap1);//月客量走势
		
		Map<String, Map<String, String>> monthjbll = tempMap.get("monthjbll");
		Map<String, Object> rootMap2 = new HashMap<String, Object>();
		List<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>();
		for(String key : monthjbll.keySet()){ 
		    Map<String,String> dataMap = (Map<String, String>) monthjbll.get(key); 
		    Map<String, Object> map1 = new HashMap<String, Object>();
		    map1.put("label",dataMap.get("label"));
			map1.put("cla_Set",dataMap.get("cla_Set"));
			map1.put("cla_Per",dataMap.get("cla_Per"));
			map1.put("cla_Grp",dataMap.get("cla_Grp"));
			map1.put("flt_Ser_Ine",dataMap.get("flt_Ser_Ine"));
			map1.put("lod_For",dataMap.get("lod_For"));
			map1.put("idd_Dct",dataMap.get("idd_Dct"));
			map1.put("key",key);
			data2.add(map1);
		}
		try {
			rootMap2.put("list", TextUtil.listSort(data2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("evenPort1",rootMap2);//均班客量
		
		Map<String, Map<String, String>> codebcMap = tempMap.get("codebcMap");
		Map<String, Object> rootMap3 = new HashMap<String, Object>();
		List<ClassRanking> rankings = new ArrayList<ClassRanking>();
		for(String key : codebcMap.keySet()){ 
			 Map<String,String> dataMap = (Map<String, String>) codebcMap.get(key);
			ClassRanking classRanking = new ClassRanking();
			classRanking.setCount(dataMap.get("count"));
			classRanking.setDpt_AirPt_Cd(dataMap.get("dpt_AirPt_Cd"));
			classRanking.setArrv_Airpt_Cd(dataMap.get("arrv_Airpt_Cd"));
			rankings.add(classRanking);
		}
		rootMap3.put("list",TextUtil.sortClassRanking(rankings,"count","DESC"));
		map.put("class1",rootMap3);//班次
		
		Map<String, Map<String, String>> codezsMap = tempMap.get("codezsMap");
		Map<String, Object> rootMap4 = new HashMap<String, Object>();
		List<ClassRanking> rankings4 = new ArrayList<>();
		for(String key : codezsMap.keySet()){ 
			Map<String,String> dataMap = (Map<String, String>) codezsMap.get(key);
			ClassRanking classRanking = new ClassRanking();
			classRanking.setCount(dataMap.get("count"));//座公里收入
			classRanking.setSet_Ktr_Ine(Double.parseDouble(df.format((Float.parseFloat(dataMap.get("set_Ktr_Ine"))))));//总收入
			classRanking.setDpt_AirPt_Cd(dataMap.get("dpt_AirPt_Cd"));
			classRanking.setArrv_Airpt_Cd(dataMap.get("arrv_Airpt_Cd"));
			rankings4.add(classRanking);
		}
		rootMap4.put("list", TextUtil.sortClassRanking(rankings4,"count","DESC"));
		map.put("set1",rootMap4);//座公里排行
		
		Map<String, Map<String, String>> codejblkMap = tempMap.get("codejblkMap");
		Map<String, Object> rootMap5 = new HashMap<String, Object>();
		List<ClassRanking> rankings5 = new ArrayList<>();
		for(String key : codejblkMap.keySet()){ 
			Map<String,String> dataMap = (Map<String, String>) codejblkMap.get(key);
			ClassRanking classRanking = new ClassRanking();
			classRanking.setCount(dataMap.get("count"));//每班座位
			classRanking.setDpt_AirPt_Cd(dataMap.get("dpt_AirPt_Cd"));
			classRanking.setArrv_Airpt_Cd(dataMap.get("arrv_Airpt_Cd"));
			classRanking.setSet_Ktr_Ine(Double.parseDouble(df.format((Float.parseFloat(dataMap.get("set_Ktr_Ine"))))));//每班旅客
			classRanking.setGuestamount(Double.parseDouble(df.format((Float.parseFloat(dataMap.get("guestamount"))))));
			rankings5.add(classRanking);
		}
		rootMap5.put("list", TextUtil.sortClassRanking(rankings5,"set_Ktr_Ine","DESC"));
		map.put("guest1",rootMap5);//均班客量排行
		
		Map<String, Map<String, String>> codelkMap = tempMap.get("codelkMap");
		Map<String, Object> rootMap6 = new HashMap<String, Object>();
		List<ClassRanking> rankings6 = new ArrayList<>();
		for(String key : codelkMap.keySet()){ 
			Map<String,String> dataMap = (Map<String, String>) codelkMap.get(key);
			ClassRanking classRanking = new ClassRanking();
			classRanking.setValue(dataMap.get("value"));
			classRanking.setDpt_AirPt_Cd(dataMap.get("dpt_AirPt_Cd"));
			classRanking.setArrv_Airpt_Cd(dataMap.get("arrv_Airpt_Cd"));
			rankings6.add(classRanking);
		}
		rootMap6.put("list", TextUtil.sortClassRanking(rankings6,"value","DESC"));
		map.put("amoun1",rootMap6);//客量排行
		
		Map<String, Map<String, String>> hsbcMap = tempMap.get("hsbcMap");
		Map<String, Object> rootMap7 = new HashMap<String, Object>();
		List<ClassRanking> rankings7 = new ArrayList<>();
		for(String key : hsbcMap.keySet()){ 
			Map<String,String> dataMap = (Map<String, String>) hsbcMap.get(key);
			ClassRanking classRanking = new ClassRanking();
			classRanking.setCount(dataMap.get("count"));
			classRanking.setCpy_Nm(dataMap.get("cpy_Nm"));
			rankings7.add(classRanking);
		}
		rootMap7.put("list", TextUtil.sortClassRanking(rankings7,"count","DESC"));
		map.put("cpy_class1",rootMap7);//航司班次排行
		
		Map<String, Map<String, String>> hsklMap = tempMap.get("hsklMap");
		Map<String, Object> rootMap8 = new HashMap<String, Object>();
		List<ClassRanking> rankings8 = new ArrayList<>();
		for(String key : hsklMap.keySet()){ 
			Map<String,String> dataMap = (Map<String, String>) hsklMap.get(key);
			ClassRanking classRanking = new ClassRanking();
			classRanking.setValue(dataMap.get("value"));
			classRanking.setCpy_Nm(dataMap.get("cpy_Nm"));
			rankings8.add(classRanking);
		}
		rootMap8.put("list", TextUtil.sortClassRanking(rankings8,"value","DESC"));
		map.put("cpy_amoun1",rootMap8);//航司客量排行,占比
		map.put("exceptionFlagJg",exceptionFlagJg);
		
		//出港新
		DOWQuery dowData2 = new DOWQuery();
		dowData2.setDpt_AirPt_Cd(dpt_AirPt_Cd);
		dowData2.setArrv_Airpt_Cd(null);
		dowData2.setIsIncludeExceptionData(query.getIsIncludeExceptionData());
		dowData2.setIsIncludeExceptionHuangduan(query.getIsIncludeExceptionHuangduan());
		dowData2.setMonthOrDay(query.getMonthOrDay());
		dowData2.setStartTime(query.getStartTime());
		dowData2.setEndTime(query.getEndTime());
		dowData2.setCpy_Nm(cpy_Nm);
		Map<String, Map<String, Map<String, String>>>  tempMap2 = iOutPortService.airportHistroy(dowData2);
		String exceptionFlagCg = tempMap2.get("exceptionFlag").get("exceptionFlag").get("exceptionFlag");
		map.put("exceptionFlagCg",exceptionFlagCg);
		map = getOutPort(tempMap2,map);
		return map;
	}
	//出港数据组装
	public Map<String, Object> getOutPort(Map<String, Map<String, Map<String, String>>>  tempMap, Map<String, Object> map){
		Map<String, Map<String, String>> monthll = tempMap.get("monthll");
		Map<String, Object> rootMap1 = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("0.00");
		List<Map<String, Object>> data1 = new ArrayList<Map<String, Object>>();
		String flag = "";
		if(monthll.isEmpty()){
			flag = "0";
		}else{
			for(String key : monthll.keySet()){ 
			    Map<String,String> dataMap = (Map<String, String>) monthll.get(key); 
			    Map<String, Object> map1 = new HashMap<String, Object>();
			    map1.put("label",dataMap.get("label"));
				map1.put("idd_moh",dataMap.get("idd_moh"));
				map1.put("Cla_Nbr",dataMap.get("Cla_Nbr"));
				map1.put("Tal_Nbr_Set",dataMap.get("Tal_Nbr_Set"));
				map1.put("Grp_moh",dataMap.get("Grp_moh"));
				map1.put("Grp_Ine",dataMap.get("Grp_Ine"));
				map1.put("Tol_Ine",dataMap.get("Tol_Ine"));
				map1.put("key",key);
				data1.add(map1);
			}
			flag = "1";
		}
		try {
			rootMap1.put("list",TextUtil.listSort(data1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		rootMap1.put("flag", flag);
		map.put("outPort2",rootMap1);//月客量走势
		
		Map<String, Map<String, String>> monthjbll = tempMap.get("monthjbll");
		Map<String, Object> rootMap2 = new HashMap<String, Object>();
		List<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>();
		for(String key : monthjbll.keySet()){ 
		    Map<String,String> dataMap = (Map<String, String>) monthjbll.get(key); 
		    Map<String, Object> map1 = new HashMap<String, Object>();
		    map1.put("label",dataMap.get("label"));
			map1.put("cla_Set",dataMap.get("cla_Set"));
			map1.put("cla_Per",dataMap.get("cla_Per"));
			map1.put("cla_Grp",dataMap.get("cla_Grp"));
			map1.put("flt_Ser_Ine",dataMap.get("flt_Ser_Ine"));
			map1.put("lod_For",dataMap.get("lod_For"));
			map1.put("idd_Dct",dataMap.get("idd_Dct"));
			map1.put("key",key);
			data2.add(map1);
		}
		try {
			rootMap2.put("list", TextUtil.listSort(data2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("evenPort2",rootMap2);//均班客量
		
		Map<String, Map<String, String>> codebcMap = tempMap.get("codebcMap");
		Map<String, Object> rootMap3 = new HashMap<String, Object>();
		List<ClassRanking> rankings = new ArrayList<>();
		for(String key : codebcMap.keySet()){ 
			 Map<String,String> dataMap = (Map<String, String>) codebcMap.get(key);
			ClassRanking classRanking = new ClassRanking();
			classRanking.setCount(dataMap.get("count"));
			classRanking.setDpt_AirPt_Cd(dataMap.get("dpt_AirPt_Cd"));
			classRanking.setArrv_Airpt_Cd(dataMap.get("arrv_Airpt_Cd"));
			rankings.add(classRanking);
		}
		rootMap3.put("list",TextUtil.sortClassRanking(rankings,"count","DESC"));
		map.put("class2",rootMap3);//班次
		
		Map<String, Map<String, String>> codezsMap = tempMap.get("codezsMap");
		Map<String, Object> rootMap4 = new HashMap<String, Object>();
		List<ClassRanking> rankings4 = new ArrayList<>();
		for(String key : codezsMap.keySet()){ 
			Map<String,String> dataMap = (Map<String, String>) codezsMap.get(key);
			ClassRanking classRanking = new ClassRanking();
			classRanking.setCount(dataMap.get("count"));//座公里收入
			classRanking.setSet_Ktr_Ine(Double.parseDouble(df.format((Float.parseFloat(dataMap.get("set_Ktr_Ine"))))));//总收入
			classRanking.setDpt_AirPt_Cd(dataMap.get("dpt_AirPt_Cd"));
			classRanking.setArrv_Airpt_Cd(dataMap.get("arrv_Airpt_Cd"));
			rankings4.add(classRanking);
		}
		rootMap4.put("list", TextUtil.sortClassRanking(rankings4,"count","DESC"));
		map.put("set2",rootMap4);//座公里排行
		
		Map<String, Map<String, String>> codejblkMap = tempMap.get("codejblkMap");
		Map<String, Object> rootMap5 = new HashMap<String, Object>();
		List<ClassRanking> rankings5 = new ArrayList<>();
		for(String key : codejblkMap.keySet()){ 
			Map<String,String> dataMap = (Map<String, String>) codejblkMap.get(key);
			ClassRanking classRanking = new ClassRanking();
			classRanking.setCount(dataMap.get("count"));//每班座位
			classRanking.setDpt_AirPt_Cd(dataMap.get("dpt_AirPt_Cd"));
			classRanking.setArrv_Airpt_Cd(dataMap.get("arrv_Airpt_Cd"));
			classRanking.setSet_Ktr_Ine(Double.parseDouble(df.format((Float.parseFloat(dataMap.get("set_Ktr_Ine"))))));//每班旅客
			classRanking.setGuestamount(0);
			rankings5.add(classRanking);
		}
		rootMap5.put("list", TextUtil.sortClassRanking(rankings5,"set_Ktr_Ine","DESC"));
		map.put("guest2",rootMap5);//均班客量排行
		
		Map<String, Map<String, String>> codelkMap = tempMap.get("codelkMap");
		Map<String, Object> rootMap6 = new HashMap<String, Object>();
		List<ClassRanking> rankings6 = new ArrayList<>();
		for(String key : codelkMap.keySet()){ 
			Map<String,String> dataMap = (Map<String, String>) codelkMap.get(key);
			ClassRanking classRanking = new ClassRanking();
			classRanking.setValue(dataMap.get("value"));
			classRanking.setDpt_AirPt_Cd(dataMap.get("dpt_AirPt_Cd"));
			classRanking.setArrv_Airpt_Cd(dataMap.get("arrv_Airpt_Cd"));
			rankings6.add(classRanking);
		}
		rootMap6.put("list", TextUtil.sortClassRanking(rankings6,"value","DESC"));
		map.put("amoun2",rootMap6);//客量排行
		
		Map<String, Map<String, String>> hsbcMap = tempMap.get("hsbcMap");
		Map<String, Object> rootMap7 = new HashMap<String, Object>();
		List<ClassRanking> rankings7 = new ArrayList<>();
		for(String key : hsbcMap.keySet()){ 
			Map<String,String> dataMap = (Map<String, String>) hsbcMap.get(key);
			ClassRanking classRanking = new ClassRanking();
			classRanking.setCount(dataMap.get("count"));
			classRanking.setCpy_Nm(dataMap.get("cpy_Nm"));
			rankings7.add(classRanking);
		}
		rootMap7.put("list", TextUtil.sortClassRanking(rankings7,"count","DESC"));
		map.put("cpy_class2",rootMap7);//航司班次排行
		
		Map<String, Map<String, String>> hsklMap = tempMap.get("hsklMap");
		Map<String, Object> rootMap8 = new HashMap<String, Object>();
		List<ClassRanking> rankings8 = new ArrayList<>();
		for(String key : hsklMap.keySet()){ 
			Map<String,String> dataMap = (Map<String, String>) hsklMap.get(key);
			ClassRanking classRanking = new ClassRanking();
			classRanking.setValue(dataMap.get("value"));
			classRanking.setCpy_Nm(dataMap.get("cpy_Nm"));
			rankings8.add(classRanking);
		}
		rootMap8.put("list", TextUtil.sortClassRanking(rankings8,"value","DESC"));
		map.put("cpy_amoun2",rootMap8);//航司客量排行,占比
		return map;
	}
	/**
	 * 出港/进港流量图形
	 * @param string 
	 * @param dowData
	 * @return
	 */
	@MethodNote(comment="机场历史运营:2")
	@MyMethodNote(comment="机场历史运营情况统计数据:2")
	public Map<String, Object> outPort_mychar(String dpt_AirPt_Cd,String startTime,String string, String state){
		DOWQuery dowData = new DOWQuery();
		dowData.setDpt_AirPt_Cd(dpt_AirPt_Cd);
		dowData.setLcl_Dpt_Day(startTime);
		dowData.setCpy_Nm(string);
		dowData.setState(state);
		String str = DataUtils.getYearState(dowData );
		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<OutPort> rows = iOutPortService.getOutPort(dowData);
		String flag = "1";
		if(rows!=null){
			if(rows.size()==0){
				flag = "0";
				Map<String, String> map = new HashMap<String, String>();
				List<Map<String, String>> data = new ArrayList<Map<String, String>>();
				map.put("label","0");
				map.put("idd_moh","0");
				map.put("Cla_Nbr","0");
				map.put("Tal_Nbr_Set","0");
				map.put("Grp_moh","0");
				map.put("Grp_Ine","0");
				map.put("Tol_Ine","0");
				map.put("titleY", "请检查你的查询条件");
				data.add(map);
				rootMap.put("list", data);
				rootMap.put("flag", flag);
				return rootMap;
			}
		}else{
			flag = "0";
			Map<String, String> map = new HashMap<String, String>();
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			map.put("label","0");
			map.put("idd_moh","0");
			map.put("Cla_Nbr","0");
			map.put("Tal_Nbr_Set","0");
			map.put("Grp_moh","0");
			map.put("Grp_Ine","0");
			map.put("Tol_Ine","0");
			map.put("titleY", "请检查你的查询条件");
			data.add(map);
			rootMap.put("list", data);
			rootMap.put("flag", flag);
			return rootMap;
		}
		
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		List<String[]> list = new ArrayList<String[]>();
		for (OutPort dt : rows) {
			String[] str1 = {dt.getMonth()+"月",dt.getIdd_moh()+"",dt.getCla_Nbr()+"",dt.getTal_Nbr_Set()+"",dt.getGrp_moh()+"",dt.getGrp_Ine()+"",dt.getTol_Ine()+"",str+"月流量"};
			list.add(str1);
		}
		for (String[] objects : list) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("label",objects[0]);
			map.put("idd_moh", objects[1]);
			map.put("Cla_Nbr", objects[2]);
			map.put("Tal_Nbr_Set", objects[3]);
			map.put("Grp_moh", objects[4]);
			map.put("Grp_Ine", objects[5]);
			map.put("Tol_Ine", objects[6]);
			map.put("titleY", objects[7]);
			data.add(map);
		}
		rootMap.put("list", data);
		rootMap.put("flag", flag);
		return rootMap;
	}
	/**
	 * 均班客流量走势图
	 * @param string 
	 * @param dowData
	 * @return
	 */
	@MethodNote(comment="机场历史运营:2")
	@MyMethodNote(comment="机场历史运营情况统计数据:2")
	public Map<String, Object> evenPort_mychar(String dpt_AirPt_Cd,String startTime,String string, String state) {
		DOWQuery dowData = new DOWQuery();
		dowData.setDpt_AirPt_Cd(dpt_AirPt_Cd);
		dowData.setLcl_Dpt_Day(startTime);
		dowData.setCpy_Nm(string);
		dowData.setState(state);
		String str = DataUtils.getYearState(dowData);
		Map<String, Object> rootMap = new HashMap<String, Object>();
		DOWObject<EvenPort> evenPort_mychar = iOutPortService.getEvenPort(dowData);
		if(evenPort_mychar==null){
			Map<String, String> map = new HashMap<String, String>();
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			map.put("label","0");
			map.put("tme_Nbr","0");
			map.put("cla_Nbr","0");
			map.put("tme_Cla_Moh","0");
			map.put("cla_Set", "0");
			map.put("cla_Per", "0");
			map.put("cla_Grp","0");
			map.put("flt_Ser_Ine", "0");
			map.put("lod_For","0");
			map.put("idd_Dct", "0");
			map.put("evenPort", "请检查你的查询条件");
			data.add(map);
			rootMap.put("list", data);
			return rootMap;
		}
		List<EvenPort> rows = evenPort_mychar.getRows();
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		List<String[]> list = new ArrayList<String[]>();
		for (EvenPort dt : rows) {
			String[] str1 = {dt.getMonth(),dt.getTme_Nbr()+"",dt.getCla_Nbr()+"",dt.getTme_Cla_Moh()+"",dt.getCla_Set()+"",dt.getCla_Per()+"",dt.getCla_Grp()+"",dt.getFlt_Ser_Ine()+"",dt.getLod_For()+"",dt.getIdd_Dct()+"",str+"均班客流量走势图"};
			list.add(str1);
		}
		for (String[] objects : list) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("label",objects[0]);
			map.put("tme_Nbr", objects[1]);
			map.put("cla_Nbr", objects[2]);
			map.put("tme_Cla_Moh", objects[3]);
			map.put("cla_Set", objects[4]);
			map.put("cla_Per", objects[5]);
			map.put("cla_Grp", objects[6]);
			map.put("flt_Ser_Ine", objects[7]);
			map.put("lod_For", objects[8]);
			map.put("idd_Dct", objects[9]);
			map.put("evenPort", objects[10]);
			data.add(map);
		}
		rootMap.put("list", data);
		return rootMap;
	}
	/**
	 * 班次排行的控制方法
	 * @param string 
	 * @param dowData
	 * @return
	 */
	@MethodNote(comment="机场历史运营:2")
	@MyMethodNote(comment="机场历史运营情况统计数据:2")
	public Map<String, Object> classRanking(String dpt_AirPt_Cd,String startTime,String string, String state) {
		DOWQuery dowData = new DOWQuery();
		dowData.setDpt_AirPt_Cd(dpt_AirPt_Cd);
		dowData.setLcl_Dpt_Day(startTime);
		dowData.setCpy_Nm(string);
		dowData.setState(state);
//		String str = DataUtils.getYearState(dowData);
		List<ClassRanking> rankings = iOutPortService.getClassRanking(dowData);
//		int i = 10 -rankings.size();
//		for (int j = 0; j < i; j++) {
//			ClassRanking classRanking = new ClassRanking();
//			classRanking.setCount("0");
//			classRanking.setDpt_AirPt_Cd("-- ");
//			classRanking.setArrv_Airpt_Cd("--");
//			rankings.add(classRanking);
//		}
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("list", rankings);
//		rootMap.put("year", str);
		return rootMap;
	}
	public Map<String, Object> cpy_classRanking(String dpt_AirPt_Cd,String startTime,String string, String state) {
		DOWQuery dowData = new DOWQuery();
		dowData.setDpt_AirPt_Cd(dpt_AirPt_Cd);
		dowData.setCpy_Nm(string);
		dowData.setLcl_Dpt_Day(startTime);
		dowData.setState(state);
//		String str = DataUtils.getYearState(dowData);
		List<ClassRanking> rankings = iOutPortService.getCpy_ClassRanking(dowData);
//		int i = 10 -rankings.size();
//		for (int j = 0; j < i; j++) {
//			ClassRanking classRanking = new ClassRanking();
//			classRanking.setCount("0");
//			classRanking.setCpy_Nm("--");
//			rankings.add(classRanking);
//		}
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("list", rankings);
//		rootMap.put("year", str);
		return rootMap;
	}
	/**
	 * 座公里收入排行的控制方法
	 * @param string 
	 * @param dowData
	 * @return
	 */
	@MethodNote(comment="机场历史运营:2")
	@MyMethodNote(comment="机场历史运营情况统计数据:2")
	public Map<String, Object> Set_Ktr_Ine(String dpt_AirPt_Cd,String startTime,String string, String state) {
		DOWQuery dowData = new DOWQuery();
		dowData.setDpt_AirPt_Cd(dpt_AirPt_Cd);
		dowData.setCpy_Nm(string);
		dowData.setLcl_Dpt_Day(startTime);
		dowData.setState(state);
//		String str = DataUtils.getYearState(dowData);
		List<ClassRanking> rankings = iOutPortService.getSet_Ktr_Ine(dowData);
//		int i = 10 -rankings.size();
//		for (int j = 0; j < i; j++) {
//			ClassRanking classRanking = new ClassRanking();
//			classRanking.setCount("0");
//			classRanking.setSet_Ktr_Ine(0);
//			classRanking.setDpt_AirPt_Cd("-- ");
//			classRanking.setArrv_Airpt_Cd("--");
//			rankings.add(classRanking);
//		}
		Map<String, Object> rootMap = new HashMap<String, Object>();
		rootMap.put("list", rankings);
//		rootMap.put("year", str);
		return rootMap;
	}
	/**
	 * 客量排行的控制方法
	 * @param string 
	 * @param dowData
	 * @return
	 */
	@MethodNote(comment="机场历史运营:2")
	@MyMethodNote(comment="机场历史运营情况统计数据:2")
	public Map<String, Object> guestamount(String dpt_AirPt_Cd,String startTime,String string, String state) {
		DOWQuery dowData = new DOWQuery();
		dowData.setDpt_AirPt_Cd(dpt_AirPt_Cd);
		dowData.setCpy_Nm(string);
		dowData.setLcl_Dpt_Day(startTime);
		dowData.setState(state);
//		String str = DataUtils.getYearState(dowData);
		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<ClassRanking> rankings = iOutPortService.getGuestamount(dowData);
//		int i = 10 -rankings.size();
//		for (int j = 0; j < i; j++) {
//			ClassRanking classRanking = new ClassRanking();
//			classRanking.setCount("0");
//			classRanking.setSet_Ktr_Ine(0);
//			classRanking.setGuestamount(0);
//			classRanking.setDpt_AirPt_Cd("-- ");
//			classRanking.setArrv_Airpt_Cd("--");
//			rankings.add(classRanking);
//		}
		rootMap.put("list", rankings);
//		rootMap.put("year", str);
		return rootMap;
	}
	@MethodNote(comment="机场历史运营:2")
	@MyMethodNote(comment="机场历史运营情况统计数据:2")
	public Map<String, Object> amountRanking(String dpt_AirPt_Cd,String startTime,String string, String state) {
		DOWQuery dowData = new DOWQuery();
		dowData.setDpt_AirPt_Cd(dpt_AirPt_Cd);
		dowData.setCpy_Nm(string);
		dowData.setLcl_Dpt_Day(startTime);
		dowData.setState(state);
//		String str = DataUtils.getYearState(dowData);
		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<ClassRanking> rankings = iOutPortService.getAmountRanking(dowData);
//		if(rankings.size()==0){
//			ClassRanking classRanking = new ClassRanking();
//			classRanking.setValue("0");
//			classRanking.setName("无数据");
//			rankings.add(classRanking);
//			rootMap.put("list", rankings);
//			rootMap.put("year", str);
//			return rootMap;
//		}
//		for (ClassRanking classRanking : rankings) {
//			classRanking.setName(classRanking.getDpt_AirPt_Cd()+classRanking.getArrv_Airpt_Cd());
//		}
		
		rootMap.put("list", rankings);
//		rootMap.put("year", str);
		return rootMap;
	}
	public Map<String, Object> cpy_amountRanking(String dpt_AirPt_Cd,String startTime,String string, String state) {
		DOWQuery dowData = new DOWQuery();
		dowData.setDpt_AirPt_Cd(dpt_AirPt_Cd);
		dowData.setCpy_Nm(string);
		dowData.setLcl_Dpt_Day(startTime);
		dowData.setState(state);
//		String str = DataUtils.getYearState(dowData);
		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<ClassRanking> rankings = iOutPortService.getCpy_AmountRanking(dowData);
//		if(rankings.size()==0){
//			ClassRanking classRanking = new ClassRanking();
//			classRanking.setValue("0");
//			classRanking.setName("无数据");
//			rankings.add(classRanking);
//			rootMap.put("list", rankings);
//			rootMap.put("year", str);
//			return rootMap;
//		}
//		for (ClassRanking classRanking : rankings) {
//			classRanking.setName(classRanking.getDpt_AirPt_Cd()+classRanking.getArrv_Airpt_Cd());
//		}
		
		rootMap.put("list", rankings);
//		rootMap.put("year", str);
		return rootMap;
	}
	
	@RequestMapping("/getYearList")
	@ResponseBody
	public Map<String,Object> getYearList(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<String> years = iOutPortService.getYears(UserContext.getcompanyItia());
			map.put("opResult", "0");
			map.put("nowDay", years);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
}
