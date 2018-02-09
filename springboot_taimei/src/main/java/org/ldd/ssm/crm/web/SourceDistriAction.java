package org.ldd.ssm.crm.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.SourceDataAll;
import org.ldd.ssm.crm.domain.SourceDistriData;
import org.ldd.ssm.crm.query.FlyNum;
import org.ldd.ssm.crm.query.SalesReportQuery;
import org.ldd.ssm.crm.query.SourceDistriDataQuery;
import org.ldd.ssm.crm.service.ISourceDistriService;
import org.ldd.ssm.crm.service.SalesReportService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class SourceDistriAction {
	@Autowired
	private ISourceDistriService sourceDistriService;
	@Autowired
	private SalesReportService salesReportService;
	@RequestMapping("/SourceDistribution")
	@MethodNote(comment="客源组成:4")
	@MyMethodNote(comment="客源组成主页:2")
	public String SourceDistribution(){
		return "newHtml/customers";
//		return "SourceDistribution/SourceDistribution";
	}
	@RequestMapping("/getSourceDistriData")
	@MyMethodNote(comment="客源组成查询:2")
	@MethodNote(comment="客源组成:4")
	@ResponseBody
	public List<SourceDataAll> getSourceDistriData(SourceDistriDataQuery dataQuery){
		List<SourceDataAll> datas = sourceDistriService.getSourceDistriData(dataQuery);
		return datas;
	}
	@RequestMapping("/getSourceDistriDataByName")
	@MyMethodNote(comment="客源组成查询:2")
	@MethodNote(comment="客源组成:4")
	@ResponseBody
	public List<SourceDistriData> getSourceDistriDataByName(SourceDistriDataQuery dataQuery){
		List<SourceDistriData> datas = sourceDistriService.getSourceDistriDataByName(dataQuery);
		return datas;
	}
	@RequestMapping("/getAllSourceDistriData")
	@MyMethodNote(comment="客源组成查询:2")
	@MethodNote(comment="客源组成:4")
	@ResponseBody
	public List<SourceDataAll> getAllSourceDistriData(SourceDistriDataQuery dataQuery,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		if(TextUtil.isEmpty(dataQuery.getIsIncludePas())){
			dataQuery.setIsIncludePas(null);
		}
		if(dataQuery.getFlt_nbr().equals("汇总")){
			dataQuery.setFlt_nbr(null);
			//得到该航线的所有航班号
			SalesReportQuery salesReportQuery = new SalesReportQuery();
			salesReportQuery.setDpt_AirPt_Cd(dataQuery.getDpt_AirPt_Cd());
			salesReportQuery.setPas_stp(dataQuery.getPas_stp());
			salesReportQuery.setArrv_Airpt_Cd(dataQuery.getArrv_Airpt_Cd());
			salesReportQuery.setIsIncludePasDpt(dataQuery.getIsIncludePas());
			List<FlyNum> hbhList = salesReportService.getHbh(salesReportQuery);
			List<String> fltNbrs = new ArrayList<String>();
			//如果航班号为空, 则表示没有数据
			if(hbhList.size()<=0){
				return null;
			}
			for (FlyNum flyNum : hbhList) {
				fltNbrs.add(flyNum.getFlyNum());
			}
			dataQuery.setFltnbrs(fltNbrs);
		}
		//查询并整理客源分布数据
		List<SourceDataAll> datas = sourceDistriService.getCustomersInfo(dataQuery);
		for (SourceDataAll sourceDataAll : datas) {//机场往返两个对象数据
			List<Integer> integers = new ArrayList<Integer>();
			List<String> keys = new ArrayList<String>();
			List<List<SourceDistriData>> mps = new ArrayList<List<SourceDistriData>>();
			Map<String, Object> linkedDataMap = new LinkedHashMap<String, Object>();
			Map<String, Object> dataMap = sourceDataAll.getDataMap();
			if(dataMap==null){
				continue;
			}
			//每个机场旅客对应的省份key和省份下面城市人数value遍历
			for (Map.Entry<String,Object> sourceDataAll2 : dataMap.entrySet()) {
				String key = sourceDataAll2.getKey();
				keys.add(key);
				@SuppressWarnings("unchecked")
				List<SourceDistriData> value = (ArrayList<SourceDistriData>)sourceDataAll2.getValue();
				mps.add(value);
				int i = 0;
				for (SourceDistriData sourceDistriData : value) {
					i+=sourceDistriData.getNumber();
				}
				Collections.sort(value);//对城市人数排序,调用集合工具类
				integers.add(i);
			}
			//对省份人数排序
			while(integers.size()>0){
				int index = 0;
				int in = 0;
				for (int i = 0; i < integers.size(); i++) {
					Integer integer = integers.get(i);
					if(integer>in){
						index = i;
						in = integer ;
					}
				}
				
				linkedDataMap.put(keys.get(index), mps.get(index));
				keys.remove(index);
				mps.remove(index);
				integers.remove(index);
			}
			sourceDataAll.setDataMap(linkedDataMap);
             
		}
		return datas;
	}
	@RequestMapping("/getFltNbrList")
	@MethodNote(comment="客源组成:4")
	@ResponseBody
	public List<String> getFltNbrList(SourceDistriDataQuery dataQuery){
		List<String> list = sourceDistriService.getFltNbrList(dataQuery);
		List<String> hblist = new ArrayList<String>();
		String tempFlyNum = "";
		String tempFlyNum2 = "";
		for (String str : list) {
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
		return hblist;
	}
	@RequestMapping(value="/restful/getCustomerNewDate",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="客源分析查询:2")
	@MethodNote(comment="客源组成:4")
	@ResponseBody
	public String getCustomerNewDate(SourceDistriDataQuery dataQuery){
		Map<String,Object> retMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		//得到最新有数据的日期
		String newDate = sourceDistriService.getCustomerNewDate(dataQuery);
		retMap.put("newDate", newDate);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
}
