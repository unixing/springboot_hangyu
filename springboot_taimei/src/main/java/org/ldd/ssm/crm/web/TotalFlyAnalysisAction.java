package org.ldd.ssm.crm.web;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.ClassRanking;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.OutPort;
import org.ldd.ssm.crm.domain.TotalFly;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.query.SalesReportQuery;
import org.ldd.ssm.crm.query.TotalFlyAnalysisQuery;
import org.ldd.ssm.crm.service.TotalFlyAnalysisService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
/**
 * 共飞运营分析
 * @Title:TotalFlyAnalysisAction 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-7 下午3:10:27
 */
@Controller
public class TotalFlyAnalysisAction {
	@Autowired
	private TotalFlyAnalysisService totalFlyAnalysisService;
	@Autowired
	private OutPortMapper outPortMapper;
	/**
	 * 对比分析主页面
	 * @Title: totalFlyAnalysis 
	 * @Description:  
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping("/totalFlyAnalysis")
	@MyMethodNote(comment="共飞分析对比主页:2")
	@MethodNote(comment="共飞运营对比:3")
	public String totalFlyAnalysis() {
//			return "charts/totalFlyAnalysis/workersFly";
		return "newHtml/operating";
	}
	/**
	 * 根据条件获得航段走势分析各图表
	 * @Title: getTotalFlyAnalysisData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/getFlowAnalysisData")
	@ResponseBody
	@MethodNote(comment="共飞运营对比:3")
	@MyMethodNote(comment="共飞分析对比分析:2")
	public Map<String,Object> getFlowAnalysisData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) {
		Map<String,Object> retMap = new HashMap<String,Object>();
			//流量汇总图
			retMap.put("flowCount", getFlowCountData(totalFlyAnalysisQuery));
			//均班客量图
			retMap.put("allAmount", getAllAmountData(totalFlyAnalysisQuery));
		return retMap;
	}
	/**
	 * 获得共飞对比分析
	 * @Title: getTotalFlyAnalysisData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/getTotalFlyAnalysisData")
	@ResponseBody
	@MethodNote(comment="共飞运营对比:3")
	@MyMethodNote(comment="共飞分析对比分析:2")
	public Map<String,Object> getTotalFlyAnalysisData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) {
		Map<String,Object> retMap = new HashMap<String,Object>();
		totalFlyAnalysisQuery.setStartDate(totalFlyAnalysisQuery.getStartDate()+"-01");
		totalFlyAnalysisQuery.setEndDate(totalFlyAnalysisQuery.getEndDate()+"-"+TextUtil.getMaxDayOfMonth(totalFlyAnalysisQuery.getEndDate()));
//		if(!TextUtil.isEmpty(totalFlyAnalysisQuery.getFlightNum())){
			//航班量排行图
			retMap.put("flightNum", getFlightNumData(totalFlyAnalysisQuery));
//		}
//		if(!TextUtil.isEmpty(totalFlyAnalysisQuery.getRaskRanking())){
			//座收排行图
			retMap.put("raskRanking", getRaskRankingData(totalFlyAnalysisQuery));
//		}
//		if(!TextUtil.isEmpty(totalFlyAnalysisQuery.getPassengerRank())){
			//客量排行图
			retMap.put("passengerRank", getPassengerRankData(totalFlyAnalysisQuery));
//		}
//		if(!TextUtil.isEmpty(totalFlyAnalysisQuery.getPassengerCompared())){
			//客量占比图
			List<List<TotalFly>> totalFlyListList = getPassengerRankData(totalFlyAnalysisQuery);
			List<List<ClassRanking>> classRankingListList = new ArrayList<List<ClassRanking>>();
			for (List<TotalFly> totalFlylist : totalFlyListList) {
				List<ClassRanking> classRankingList = new ArrayList<ClassRanking>();
				for (TotalFly totalFly : totalFlylist) {
					ClassRanking classRanking = new ClassRanking();
					classRanking.setName(totalFly.getFlt_Nbr());
					classRanking.setValue(totalFly.getPgs_Per_Cls_num()+"");
					classRanking.setTitle(totalFly.getHangduan()+"   "+totalFly.getStartDate()+"至"+totalFly.getEndDate()+"客量占比");
					classRankingList.add(classRanking);
				}
				classRankingListList.add(classRankingList);
			}
			retMap.put("passengerCompared", classRankingListList);
//		}
//		if(!TextUtil.isEmpty(totalFlyAnalysisQuery.getAllClassRank())){
			//均班客量排行图
			retMap.put("allClassRank", getAllClassRankData(totalFlyAnalysisQuery));
//		}
		return retMap;
	}
	/**
	 * 共飞对比分析新方法
	 * @Title: getTotalFlyAnalysisDataNew 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/restful/getTotalFlyAnalysisDataNew",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="共飞分析对比查询:2")
	@MethodNote(comment="共飞运营对比:3")
	@ResponseBody
	public String getTotalFlyAnalysisDataNew(TotalFlyAnalysisQuery totalFlyAnalysisQuery){
		Map<String,Object> retMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		String dpt = totalFlyAnalysisQuery.getDpt_AirPt_Cd();
		String pst = totalFlyAnalysisQuery.getPas_stp();
		String arr = totalFlyAnalysisQuery.getArrv_Airpt_Cd();
		if(!"on".equals(totalFlyAnalysisQuery.getIsIncludeExceptionData())){
			totalFlyAnalysisQuery.setIsIncludeExceptionData(null);
		}
		if(!"on".equals(totalFlyAnalysisQuery.getIsIncludeExceptionHuangduan())){
			totalFlyAnalysisQuery.setIsIncludeExceptionHuangduan(null);
		}
		if(!"on".equals(totalFlyAnalysisQuery.getIsIncludePas())){
			totalFlyAnalysisQuery.setIsIncludePas(null);
		}
		if(!"on".equals(totalFlyAnalysisQuery.getIsIncludeRdf())){
			totalFlyAnalysisQuery.setIsIncludeRdf(null);
		}
		if(!TextUtil.isEmpty(pst)){
			//去程
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt);
			totalFlyAnalysisQuery.setPas_stp(pst);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arr);
			totalFlyAnalysisQuery.setIsGoAndBack(null);
			Map<String, Object> temp1 = new HashMap<String, Object>();
			try {
				temp1 = totalFlyAnalysisService.getTotalDataNew(totalFlyAnalysisQuery);
				retMap.put(outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd())+"-"+outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getPas_stp())+"-"+outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd()), temp1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//回程
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(arr);
			totalFlyAnalysisQuery.setPas_stp(pst);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(dpt);
			totalFlyAnalysisQuery.setIsGoAndBack(null);
			Map<String, Object> temp2 = new HashMap<String, Object>();
			try {
			 temp2 = totalFlyAnalysisService.getTotalDataNew(totalFlyAnalysisQuery);
			retMap.put(outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd())+"-"+outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getPas_stp())+"-"+outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd()),temp2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//来回
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt);
			totalFlyAnalysisQuery.setPas_stp(pst);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arr);
			totalFlyAnalysisQuery.setIsGoAndBack("on");
			Map<String, Object> temp3 = new HashMap<String, Object>();
			try {
			 temp3 = totalFlyAnalysisService.getTotalDataNew(totalFlyAnalysisQuery);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if("0".equals(temp1.get("flag"))||"0".equals(temp2.get("flag"))){
				temp3.put("flag", "0");
			}else{
				Map<String,List<Object>> temMap = (Map<String, List<Object>>) temp3.get("hbl");
				boolean b = false;
				for (Map.Entry<String,List<Object>> entry : temMap.entrySet()) {  
					List<Object> obj = entry.getValue();
					if(obj!=null&&obj.size()>0){
						b = true;
					}
				} 
				if(b){
					temp3.put("flag", "1");
				}else{
					temp3.put("flag", "0");
				}
			}
			retMap.put(outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd())+"="+outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getPas_stp())+"="+outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd()), temp3);
			
		}else{
			//去程
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt);
			totalFlyAnalysisQuery.setPas_stp(null);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arr);
			totalFlyAnalysisQuery.setIsGoAndBack(null);
			Map<String, Object> temp1 = totalFlyAnalysisService.getTotalDataNew(totalFlyAnalysisQuery);
			retMap.put(outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd())+"-"+outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd()), temp1);
			//回程
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(arr);
			totalFlyAnalysisQuery.setPas_stp(null);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(dpt);
			totalFlyAnalysisQuery.setIsGoAndBack(null);
			Map<String, Object> temp2 = totalFlyAnalysisService.getTotalDataNew(totalFlyAnalysisQuery);
			retMap.put(outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd())+"-"+outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd()), temp2);
			
			//来回
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt);
			totalFlyAnalysisQuery.setPas_stp(null);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arr);
			totalFlyAnalysisQuery.setIsGoAndBack("on");
			Map<String, Object> temp3 = totalFlyAnalysisService.getTotalDataNew(totalFlyAnalysisQuery);
			if("0".equals(temp1.get("flag"))||"0".equals(temp2.get("flag"))){
				temp3.put("flag", "0");
			}else{
				Map<String,List<Object>> temMap = (Map<String, List<Object>>) temp3.get("hbl");
				boolean b = false;
				for (Map.Entry<String,List<Object>> entry : temMap.entrySet()) {  
					List<Object> obj = entry.getValue();
					if(obj!=null&&obj.size()>0){
						b = true;
					}
				} 
				if(b){
					temp3.put("flag", "1");
				}else{
					temp3.put("flag", "0");
				}
			}
			retMap.put(outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getDpt_AirPt_Cd())+"="+outPortMapper.getairportNameByCode(totalFlyAnalysisQuery.getArrv_Airpt_Cd()), temp3);
		}
		//表格数据组装
		Map<String,Object> retNewMap = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("#.##");
		for (Map.Entry<String, Object> entry : retMap.entrySet()) {  
			String key = entry.getKey();
			Map<String,Object> duanMap = (Map<String, Object>) entry.getValue();
			Map<String,Object> hblMap = (Map<String, Object>) duanMap.get("hbl");
			Map<String,Object> zsMap = (Map<String, Object>) duanMap.get("zs");
			Map<String,Object> klMap = (Map<String, Object>) duanMap.get("kl");
			Map<String,Object> klzbMap = (Map<String, Object>) duanMap.get("klzb");
			Map<String,Object> jbklMap = (Map<String, Object>) duanMap.get("jbkl");
			Map<String,List<TotalFly>> temptf = new HashMap<String,List<TotalFly>>();
			for (Map.Entry<String, Object> entry2 : hblMap.entrySet()) {  
				String duankey = entry2.getKey();
				if(!TextUtil.isEmpty(duankey)&&duankey.contains("all")){
					List<TotalFly> totalFlyList = new ArrayList<TotalFly>();
					List<TotalFly> hbllist = (List<TotalFly>)hblMap.get(duankey);
					List<TotalFly> zslist = (List<TotalFly>)zsMap.get(duankey);
					List<TotalFly> kllist = (List<TotalFly>)klMap.get(duankey);
					List<TotalFly> klzblist = (List<TotalFly>)klzbMap.get(duankey);
					List<TotalFly> jbkllist = (List<TotalFly>)jbklMap.get(duankey);
					for (TotalFly tt : hbllist) {
						TotalFly totalFly = new TotalFly();
						String flyNbr = tt.getFlt_Nbr();
						double flyNbrNum = tt.getFlt_Nbr_num();
						double ybFare = tt.getyBFare();
						totalFly.setFlt_Nbr(flyNbr);
						totalFly.setFlt_Nbr_num(flyNbrNum);
						totalFly.setyBFare(ybFare);
						for (TotalFly t1 : zslist) {
							String flyNbr1 = t1.getFlt_Nbr();
							if(flyNbr.equals(flyNbr1)){
								double set_Ktr_Ine_num = t1.getSet_Ktr_Ine_num();
								double tal_Nbr_num = t1.getTal_Nbr_num();
								double tdys = t1.getJbtdsr(); 
								totalFly.setSet_Ktr_Ine_num(Double.parseDouble(df.format(set_Ktr_Ine_num)));
								totalFly.setTal_Nbr_num(tal_Nbr_num);
								totalFly.setJbtdsr(Double.parseDouble(df.format(tdys)));
							}
						}
						for (TotalFly t1 : kllist) {
							String flyNbr1 = t1.getFlt_Nbr();
							if(flyNbr.equals(flyNbr1)){
								double pgs_Per_num = t1.getPgs_Per_num();
								totalFly.setPgs_Per_num(pgs_Per_num);
							}
						}
						for (TotalFly t1 : klzblist) {
							String flyNbr1 = t1.getName();
							if(flyNbr.equals(flyNbr1)){
								double value = t1.getValue();
								totalFly.setValue(value);
							}
						}
						for (TotalFly t1 : jbkllist) {
							String flyNbr1 = t1.getFlt_Nbr();
							if(flyNbr.equals(flyNbr1)){
								double tal_Nbr_Set_num = t1.getTal_Nbr_Set_num();
								double pgs_Per_num = t1.getPgs_Per_num();
								double grp_Nbr_num = t1.getGrp_Nbr_num();
								totalFly.setJbzw(tal_Nbr_Set_num);
								totalFly.setJbkl(pgs_Per_num);
								totalFly.setJbtd(grp_Nbr_num);
								if(tal_Nbr_Set_num>0){
									totalFly.setJbkzl(Double.parseDouble(df.format(pgs_Per_num/tal_Nbr_Set_num))*100);
								}else{
									totalFly.setJbkzl(0);
								}
								//团队折扣，散客折扣，均班折扣
								totalFly.setTdzk(t1.getTdzk()/100);
								totalFly.setSkzk(t1.getSkzk()/100);
								totalFly.setPjzk(t1.getPjzk()/100);
							}
						}
						totalFlyList.add(totalFly);
					}
					temptf.put(duankey.replace("-all", ""), totalFlyList);
				}
			}
			retNewMap.put(key, temptf);
		} 
		
		for (Map.Entry<String, Object> entry : retMap.entrySet()) {  
			String key = entry.getKey();
			Map<String,Object> duanMap = (Map<String, Object>) entry.getValue();
			Map<String,Object> hblMap = (Map<String, Object>) duanMap.get("hbl");
			Map<String,Object> zsMap = (Map<String, Object>) duanMap.get("zs");
			Map<String,Object> klMap = (Map<String, Object>) duanMap.get("kl");
			Map<String,Object> klzbMap = (Map<String, Object>) duanMap.get("klzb");
			Map<String,Object> jbklMap = (Map<String, Object>) duanMap.get("jbkl");
			List<String> relist = new ArrayList<String>();
			for (Map.Entry<String, Object> entry2 : hblMap.entrySet()) {  
				String duankey = entry2.getKey();
				if(!TextUtil.isEmpty(duankey)&&duankey.contains("all")){
					relist.add(duankey);
				}
			}
			for (String duankey : relist) {
				hblMap.remove(duankey);
				zsMap.remove(duankey);
				klMap.remove(duankey);
				klzbMap.remove(duankey);
				jbklMap.remove(duankey);
			}
		}
		for (Map.Entry<String, Object> entry : retMap.entrySet()) {  
			String key = entry.getKey();
			Map<String, Object> tem =  (Map<String, Object>) retMap.get(key);
			tem.put("excelData", retNewMap);
		}
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	@RequestMapping(value="/restful/getTotalFlyAnalysisNewDate",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="共飞分析对比查询:2")
	@MethodNote(comment="共飞运营对比:3")
	@ResponseBody
	public String getTotalFlyAnalysisNewDate(TotalFlyAnalysisQuery totalFlyAnalysisQuery){
		Map<String,Object> retMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		//得到最新有数据的日期
		String newDate = totalFlyAnalysisService.getNewDate(totalFlyAnalysisQuery);
		retMap.put("newDate", newDate);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	/**
	 * 共飞运营分析来回
	 * @Title: getTotalFlyAnalysisDataHd 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/getTotalFlyAnalysisDataHd")
	@ResponseBody
	@MethodNote(comment="共飞运营对比:3")
	@MyMethodNote(comment="共飞分析对比分析:2")
	public Map<String,Object> getTotalFlyAnalysisDataHd(TotalFlyAnalysisQuery totalFlyAnalysisQuery) {
		
		String dpt_AirPt_Cd = totalFlyAnalysisQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_Cd = totalFlyAnalysisQuery.getArrv_Airpt_Cd();
		Map<String,Object> retMap = new HashMap<String,Object>();
		totalFlyAnalysisQuery.setStartDate(totalFlyAnalysisQuery.getStartDate()+"-01");
		totalFlyAnalysisQuery.setEndDate(totalFlyAnalysisQuery.getEndDate()+"-"+TextUtil.getMaxDayOfMonth(totalFlyAnalysisQuery.getEndDate()));
//		if(!TextUtil.isEmpty(totalFlyAnalysisQuery.getFlightNum())){
			//航班量排行图
			List<List<TotalFly>> totalFlyListListTal  = new ArrayList<List<TotalFly>>();
			List<List<TotalFly>> totalFlyListListQ  = getFlightNumData(totalFlyAnalysisQuery);
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(dpt_AirPt_Cd);
			List<List<TotalFly>> totalFlyListListH  = getFlightNumData(totalFlyAnalysisQuery);
			if(totalFlyListListQ!=null&&totalFlyListListH!=null){
				for(int i =0 ;i<totalFlyListListQ.size();i++){
					List<TotalFly> totalFlyListTal = new ArrayList<TotalFly>();
					if(totalFlyListListQ.get(i).size()>=totalFlyListListH.get(i).size()){
						for (TotalFly totalFly : totalFlyListListQ.get(i)) {
							String fly_nbr = totalFly.getFlt_Nbr();
							TotalFly totalFlyTal = new TotalFly();
							int flag = 0;
							for (TotalFly totalFlyh : totalFlyListListH.get(i)) {
								String strflyNbr = totalFlyh.getFlt_Nbr();
								if(!TextUtil.isEmpty(fly_nbr)&&!TextUtil.isEmpty(strflyNbr)){
									int lastNum1 = HbhCharater(fly_nbr.substring(5, 6));
									int lastNum2 = HbhCharater(strflyNbr.substring(5, 6));
									int twolastNum1 = HbhCharater(fly_nbr.substring(4, 5));
									String onelastNum1 = fly_nbr.substring(0, 4);
									String newStr1= "";
									String newStr2= "";
									if(lastNum1%2==0){
										//偶数
										if(lastNum1==0){
											newStr2 = onelastNum1 + (twolastNum1-1) + "9";
										}else{
											newStr2 = onelastNum1+ twolastNum1 + (lastNum1-1);
										}
									}else{
										if(lastNum1==9){
											newStr2 = onelastNum1 + (twolastNum1+1) + "0";
										}else{
											newStr2 = onelastNum1+ twolastNum1 + (lastNum1+1);
										}
									}
									
									 newStr1= strflyNbr.substring(0, 5)+lastNum2;
									if(newStr1.equals(newStr2)){
										flag++;
//										totalFlyTal.setFlt_Nbr(fly_nbr+"/"+strflyNbr);
										if(lastNum1==0){
											totalFlyTal.setFlt_Nbr(onelastNum1+(twolastNum1-1)+lastNum2+"/"+twolastNum1+lastNum1);
										}else if(lastNum2==0){
											totalFlyTal.setFlt_Nbr(onelastNum1+(twolastNum1-1)+lastNum1+"/"+twolastNum1+lastNum2);
										}else if(lastNum1>lastNum2){
											totalFlyTal.setFlt_Nbr(onelastNum1+twolastNum1+lastNum2+"/"+lastNum1);
										}else{
											totalFlyTal.setFlt_Nbr(onelastNum1+twolastNum1+lastNum1+"/"+lastNum2);
										}
										totalFlyTal.setFlt_Nbr_num(totalFly.getFlt_Nbr_num()+totalFlyh.getFlt_Nbr_num());
										totalFlyTal.setHangduan(totalFly.getHangduan().split("-")[0]+"="+totalFly.getHangduan().split("-")[1]);
										totalFlyTal.setStartDate(totalFly.getStartDate());
										totalFlyTal.setEndDate(totalFly.getEndDate());
										totalFlyTal.setJb(totalFly.getJb()+totalFlyh.getJb());
									}
								}else{
									totalFlyTal = totalFly;
								}
							}
							if(flag==0){
								totalFlyTal.setFlt_Nbr(fly_nbr);
							}
							totalFlyListTal.add(totalFlyTal);
						}
					}else{
						for (TotalFly totalFly : totalFlyListListH.get(i)) {
							String fly_nbr = totalFly.getFlt_Nbr();
							TotalFly totalFlyTal = new TotalFly();
							int flag = 0;
							for (TotalFly totalFlyh : totalFlyListListQ.get(i)) {
								String strflyNbr = totalFlyh.getFlt_Nbr();
								if(!TextUtil.isEmpty(fly_nbr)&&!TextUtil.isEmpty(strflyNbr)){
									int lastNum1 = HbhCharater(fly_nbr.substring(5, 6));
									int lastNum2 = HbhCharater(strflyNbr.substring(5, 6));
									int twolastNum1 = HbhCharater(fly_nbr.substring(4, 5));
									String onelastNum1 = fly_nbr.substring(0, 4);
									String newStr1= "";
									String newStr2= "";
									if(lastNum1%2==0){
										//偶数
										if(lastNum1==0){
											newStr2 = onelastNum1 + (twolastNum1-1) + "9";
										}else{
											newStr2 = onelastNum1+ twolastNum1 + (lastNum1-1);
										}
									}else{
										if(lastNum1==9){
											newStr2 = onelastNum1 + (twolastNum1+1) + "0";
										}else{
											newStr2 = onelastNum1+ twolastNum1 + (lastNum1+1);
										}
									}
									
									 newStr1= strflyNbr.substring(0, 5)+lastNum2;
									if(newStr1.equals(newStr2)){
										flag++;
//										totalFlyTal.setFlt_Nbr(fly_nbr+"/"+strflyNbr);
										if(lastNum1==0){
											totalFlyTal.setFlt_Nbr(onelastNum1+(twolastNum1-1)+lastNum2+"/"+twolastNum1+lastNum1);
										}else if(lastNum2==0){
											totalFlyTal.setFlt_Nbr(onelastNum1+(twolastNum1-1)+lastNum1+"/"+twolastNum1+lastNum2);
										}else if(lastNum1>lastNum2){
											totalFlyTal.setFlt_Nbr(onelastNum1+twolastNum1+lastNum2+"/"+lastNum1);
										}else{
											totalFlyTal.setFlt_Nbr(onelastNum1+twolastNum1+lastNum1+"/"+lastNum2);
										}
										totalFlyTal.setFlt_Nbr_num(totalFly.getFlt_Nbr_num()+totalFlyh.getFlt_Nbr_num());
										totalFlyTal.setHangduan(totalFly.getHangduan().split("-")[0]+"="+totalFly.getHangduan().split("-")[1]);
										totalFlyTal.setStartDate(totalFly.getStartDate());
										totalFlyTal.setEndDate(totalFly.getEndDate());
										totalFlyTal.setJb(totalFly.getJb()+totalFlyh.getJb());
									}
								}else{
									totalFlyTal = totalFly;
								}
							}
							if(flag==0){
								totalFlyTal.setFlt_Nbr(fly_nbr);
							}
							totalFlyListTal.add(totalFlyTal);
						}
					}
					totalFlyListListTal.add(totalFlyListTal);
				}
			}else{
				if(totalFlyListListQ==null){
					totalFlyListListTal = totalFlyListListQ;
				}else{
					totalFlyListListTal = totalFlyListListH;
				}
			}
			//list排序
			if(totalFlyListListTal!=null&&totalFlyListListTal.size()>0){
				for(int i = 0;i<totalFlyListListTal.size();i++){
					sort(totalFlyListListTal.get(i),"flt_Nbr_num","desc");
				}
			}
			retMap.put("flightNum",totalFlyListListTal );
//		}
//		if(!TextUtil.isEmpty(totalFlyAnalysisQuery.getRaskRanking())){
			//座收排行图
			List<List<TotalFly>> totalFlyListListTal1  = new ArrayList<List<TotalFly>>();
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
			List<List<TotalFly>> totalFlyListListQ1  = getRaskRankingData(totalFlyAnalysisQuery);
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(dpt_AirPt_Cd);
			List<List<TotalFly>> totalFlyListListH1  = getRaskRankingData(totalFlyAnalysisQuery);
			if(totalFlyListListQ1!=null&&totalFlyListListH1!=null){
				if(totalFlyListListQ1.size()>=totalFlyListListH1.size()){
					for(int i =0 ;i<totalFlyListListQ1.size();i++){
						List<TotalFly> totalFlyListTal = new ArrayList<TotalFly>();
						for (TotalFly totalFly : totalFlyListListQ1.get(i)) {
							String fly_nbr = totalFly.getFlt_Nbr();
							TotalFly totalFlyTal = new TotalFly();
							int flag = 0;
							for (TotalFly totalFlyh : totalFlyListListH1.get(i)) {
								String strflyNbr = totalFlyh.getFlt_Nbr();
								if(!TextUtil.isEmpty(fly_nbr)&&!TextUtil.isEmpty(strflyNbr)){
									int lastNum1 = HbhCharater(fly_nbr.substring(5, 6));
									int lastNum2 = HbhCharater(strflyNbr.substring(5, 6));
									int twolastNum1 = HbhCharater(fly_nbr.substring(4, 5));
									String onelastNum1 = fly_nbr.substring(0, 4);
									String newStr1= "";
									String newStr2= "";
									if(lastNum1%2==0){
										//偶数
										if(lastNum1==0){
											newStr2 = onelastNum1 + (twolastNum1-1) + "9";
										}else{
											newStr2 = onelastNum1+ twolastNum1 + (lastNum1-1);
										}
									}else{
										if(lastNum1==9){
											newStr2 = onelastNum1 + (twolastNum1+1) + "0";
										}else{
											newStr2 = onelastNum1+ twolastNum1 + (lastNum1+1);
										}
									}
									
									 newStr1= strflyNbr.substring(0, 5)+lastNum2;
									if(newStr1.equals(newStr2)){
									flag++;
//									totalFlyTal.setFlt_Nbr(fly_nbr+"/"+strflyNbr);
									if(lastNum1==0){
										totalFlyTal.setFlt_Nbr(onelastNum1+(twolastNum1-1)+lastNum2+"/"+twolastNum1+lastNum1);
									}else if(lastNum2==0){
										totalFlyTal.setFlt_Nbr(onelastNum1+(twolastNum1-1)+lastNum1+"/"+twolastNum1+lastNum2);
									}else if(lastNum1>lastNum2){
										totalFlyTal.setFlt_Nbr(onelastNum1+twolastNum1+lastNum2+"/"+lastNum1);
									}else{
										totalFlyTal.setFlt_Nbr(onelastNum1+twolastNum1+lastNum1+"/"+lastNum2);
									}
									totalFlyTal.setTal_Nbr_num(totalFly.getTal_Nbr_num()+totalFlyh.getTal_Nbr_num());
									totalFlyTal.setSet_Ktr_Ine_num(totalFly.getSet_Ktr_Ine_num()+totalFlyh.getSet_Ktr_Ine_num());
									totalFlyTal.setHangduan(totalFly.getHangduan().split("-")[0]+"="+totalFly.getHangduan().split("-")[1]);
									totalFlyTal.setStartDate(totalFly.getStartDate());
									totalFlyTal.setEndDate(totalFly.getEndDate());
									totalFlyTal.setJbys(totalFly.getJbys()+totalFlyh.getJbys());
									totalFlyTal.setJbzglsr(totalFly.getJbzglsr()+totalFlyh.getJbzglsr());
								}
								}else{
									totalFlyTal = totalFly;
								}
							}
								
							if(flag==0){
								totalFlyTal.setFlt_Nbr(fly_nbr);
							}
							totalFlyListTal.add(totalFlyTal);
						}
						totalFlyListListTal1.add(totalFlyListTal);
					}
					}			
				}else{
				if(totalFlyListListQ1==null){
					totalFlyListListTal1 = totalFlyListListQ1;
				}else{
					totalFlyListListTal1 = totalFlyListListH1;
				}
			}
			if(totalFlyListListTal1!=null&&totalFlyListListTal1.size()>0){
				for(int i = 0;i<totalFlyListListTal1.size();i++){
					sort(totalFlyListListTal1.get(i),"Set_Ktr_Ine_num","desc");
				}
			}
			retMap.put("raskRanking",totalFlyListListTal1 );
//		}
//		if(!TextUtil.isEmpty(totalFlyAnalysisQuery.getPassengerRank())){
			//客量排行图
			List<List<TotalFly>> totalFlyListListTal2  = new ArrayList<List<TotalFly>>();
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
			List<List<TotalFly>> totalFlyListListQ2  = getPassengerRankData(totalFlyAnalysisQuery);
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(dpt_AirPt_Cd);
			List<List<TotalFly>> totalFlyListListH2  = getPassengerRankData(totalFlyAnalysisQuery);
			if(totalFlyListListQ2!=null&&totalFlyListListH2!=null){
				if(totalFlyListListQ2.size()>=totalFlyListListH2.size()){
					for(int i =0 ;i<totalFlyListListQ2.size();i++){
						List<TotalFly> totalFlyListTal = new ArrayList<TotalFly>();
						for (TotalFly totalFly : totalFlyListListQ2.get(i)) {
							String fly_nbr = totalFly.getFlt_Nbr();
							TotalFly totalFlyTal = new TotalFly();
							int flag = 0;
							for (TotalFly totalFlyh : totalFlyListListH2.get(i)) {
								String strflyNbr = totalFlyh.getFlt_Nbr();
								if(!TextUtil.isEmpty(fly_nbr)&&!TextUtil.isEmpty(strflyNbr)){
									int lastNum1 = HbhCharater(fly_nbr.substring(5, 6));
									int lastNum2 = HbhCharater(strflyNbr.substring(5, 6));
									int twolastNum1 = HbhCharater(fly_nbr.substring(4, 5));
									String onelastNum1 = fly_nbr.substring(0, 4);
									String newStr1= "";
									String newStr2= "";
									if(lastNum1%2==0){
										//偶数
										if(lastNum1==0){
											newStr2 = onelastNum1 + (twolastNum1-1) + "9";
										}else{
											newStr2 = onelastNum1+ twolastNum1 + (lastNum1-1);
										}
									}else{
										if(lastNum1==9){
											newStr2 = onelastNum1 + (twolastNum1+1) + "0";
										}else{
											newStr2 = onelastNum1+ twolastNum1 + (lastNum1+1);
										}
									}
									
									 newStr1= strflyNbr.substring(0, 5)+lastNum2;
									if(newStr1.equals(newStr2)){
									flag++;
//									totalFlyTal.setFlt_Nbr(fly_nbr+"/"+strflyNbr);
									if(lastNum1==0){
										totalFlyTal.setFlt_Nbr(onelastNum1+(twolastNum1-1)+lastNum2+"/"+twolastNum1+lastNum1);
									}else if(lastNum2==0){
										totalFlyTal.setFlt_Nbr(onelastNum1+(twolastNum1-1)+lastNum1+"/"+twolastNum1+lastNum2);
									}else if(lastNum1>lastNum2){
										totalFlyTal.setFlt_Nbr(onelastNum1+twolastNum1+lastNum2+"/"+lastNum1);
									}else{
										totalFlyTal.setFlt_Nbr(onelastNum1+twolastNum1+lastNum1+"/"+lastNum2);
									}
									totalFlyTal.setPgs_Per_Cls_num(totalFly.getPgs_Per_Cls_num()+totalFlyh.getPgs_Per_Cls_num());
									totalFlyTal.setHangduan(totalFly.getHangduan().split("-")[0]+"="+totalFly.getHangduan().split("-")[1]);
									totalFlyTal.setStartDate(totalFly.getStartDate());
									totalFlyTal.setEndDate(totalFly.getEndDate());
									totalFlyTal.setJbkl(totalFly.getJbkl()+totalFlyh.getJbkl());
								}}else{
									totalFlyTal = totalFly;
								}
							}
							if(flag==0){
								totalFlyTal.setFlt_Nbr(fly_nbr);
							}
							totalFlyListTal.add(totalFlyTal);
						}
						totalFlyListListTal2.add(totalFlyListTal);
					}
					
				}
			}else{
				if(totalFlyListListQ2==null){
					totalFlyListListTal2 = totalFlyListListQ2;
				}else{
					totalFlyListListTal2 = totalFlyListListH2;
				}
			}
			if(totalFlyListListTal2!=null&&totalFlyListListTal2.size()>0){
				for(int i = 0;i<totalFlyListListTal2.size();i++){
					sort(totalFlyListListTal2.get(i),"pgs_Per_Cls_num","desc");
				}
			}
			retMap.put("passengerRank",totalFlyListListTal2 );
//		}
//		if(!TextUtil.isEmpty(totalFlyAnalysisQuery.getPassengerCompared())){
			//客量占比图
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
			List<List<TotalFly>> totalFlyListList = getPassengerRankData(totalFlyAnalysisQuery);
			List<List<ClassRanking>> classRankingListListq = new ArrayList<List<ClassRanking>>();
			for (List<TotalFly> totalFlylist : totalFlyListList) {
				List<ClassRanking> classRankingList = new ArrayList<ClassRanking>();
				for (TotalFly totalFly : totalFlylist) {
					ClassRanking classRanking = new ClassRanking();
					classRanking.setName(totalFly.getFlt_Nbr());
					classRanking.setValue(totalFly.getPgs_Per_Cls_num()+"");
					classRanking.setTitle(totalFly.getHangduan()+"   "+totalFly.getStartDate()+"至"+totalFly.getEndDate()+"客量占比");
					classRankingList.add(classRanking);
				}
				classRankingListListq.add(classRankingList);
			}
			
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(dpt_AirPt_Cd);
			List<List<TotalFly>> totalFlyListList2 = getPassengerRankData(totalFlyAnalysisQuery);
			List<List<ClassRanking>> classRankingListListh = new ArrayList<List<ClassRanking>>();
			for (List<TotalFly> totalFlylist : totalFlyListList2) {
				List<ClassRanking> classRankingList = new ArrayList<ClassRanking>();
				for (TotalFly totalFly : totalFlylist) {
					ClassRanking classRanking = new ClassRanking();
					classRanking.setName(totalFly.getFlt_Nbr());
					classRanking.setValue(totalFly.getPgs_Per_Cls_num()+"");
					classRanking.setTitle(totalFly.getHangduan().split("-")[0]+"="+totalFly.getHangduan().split("-")[1]+"   "+totalFly.getStartDate()+"至"+totalFly.getEndDate()+"客量占比");
					classRankingList.add(classRanking);
				}
				classRankingListListh.add(classRankingList);
			}
			List<List<ClassRanking>> classRankingListListtal = new ArrayList<List<ClassRanking>>();
			if(classRankingListListq!=null&&classRankingListListh!=null){
				if(classRankingListListq.size()>=classRankingListListh.size()){
					for(int i =0 ;i<classRankingListListq.size();i++){
						List<ClassRanking> classRankingListTal = new ArrayList<ClassRanking>();
						for (ClassRanking classRanking : classRankingListListq.get(i)) {
							String fly_nbr = classRanking.getName();
							ClassRanking classRankingTal = new ClassRanking();
							int flag = 0;
							for (ClassRanking classRankingh : classRankingListListh.get(i)) {
								String strflyNbr = classRankingh.getName();
								if(!TextUtil.isEmpty(fly_nbr)&&!TextUtil.isEmpty(strflyNbr)){
									int lastNum1 = HbhCharater(fly_nbr.substring(5, 6));
									int lastNum2 = HbhCharater(strflyNbr.substring(5, 6));
									int twolastNum1 = HbhCharater(fly_nbr.substring(4, 5));
									String onelastNum1 = fly_nbr.substring(0, 4);
									String newStr1= "";
									String newStr2= "";
									if(lastNum1%2==0){
										//偶数
										if(lastNum1==0){
											newStr2 = onelastNum1 + (twolastNum1-1) + "9";
										}else{
											newStr2 = onelastNum1+ twolastNum1 + (lastNum1-1);
										}
									}else{
										if(lastNum1==9){
											newStr2 = onelastNum1 + (twolastNum1+1) + "0";
										}else{
											newStr2 = onelastNum1+ twolastNum1 + (lastNum1+1);
										}
									}
									
									 newStr1= strflyNbr.substring(0, 5)+lastNum2;
									if(newStr1.equals(newStr2)){
									flag++;
//									classRankingTal.setName(fly_nbr+"/"+strflyNbr);
									if(lastNum1==0){
										classRankingTal.setName(onelastNum1+(twolastNum1-1)+lastNum2+"/"+twolastNum1+lastNum1);
									}else if(lastNum2==0){
										classRankingTal.setName(onelastNum1+(twolastNum1-1)+lastNum1+"/"+twolastNum1+lastNum2);
									}else if(lastNum1>lastNum2){
										classRankingTal.setName(onelastNum1+twolastNum1+lastNum2+"/"+lastNum1);
									}else{
										classRankingTal.setName(onelastNum1+twolastNum1+lastNum1+"/"+lastNum2);
									}
									classRankingTal.setValue(Double.parseDouble(classRanking.getValue())+Double.parseDouble(classRankingh.getValue())+"");
									classRankingTal.setTitle(classRankingh.getTitle());
								}}else{
									classRankingTal = classRanking;
								}
							}
							if(flag==0){
								classRankingTal.setName(fly_nbr);
							}
							classRankingListTal.add(classRankingTal);
						}
						classRankingListListtal.add(classRankingListTal);
					}
				}
			}else{
				if(classRankingListListq!=null){
					classRankingListListtal = classRankingListListh;
				}
				if(classRankingListListh!=null){
					classRankingListListtal = classRankingListListq;
				}
			}
			retMap.put("passengerCompared",classRankingListListtal );
//		}
//		if(!TextUtil.isEmpty(totalFlyAnalysisQuery.getAllClassRank())){
			//均班客量排行图
			List<List<TotalFly>> totalFlyListListTal3  = new ArrayList<List<TotalFly>>();
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(dpt_AirPt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(arrv_Airpt_Cd);
			List<List<TotalFly>> totalFlyListListQ3  = getAllClassRankData(totalFlyAnalysisQuery);
			totalFlyAnalysisQuery.setDpt_AirPt_Cd(arrv_Airpt_Cd);
			totalFlyAnalysisQuery.setArrv_Airpt_Cd(dpt_AirPt_Cd);
			List<List<TotalFly>> totalFlyListListH3  = getAllClassRankData(totalFlyAnalysisQuery);
			if(totalFlyListListQ3!=null&&totalFlyListListH3!=null){
				if(totalFlyListListQ3.size()>=totalFlyListListH3.size()){
					for(int i =0 ;i<totalFlyListListQ3.size();i++){
						List<TotalFly> totalFlyListTal = new ArrayList<TotalFly>();
						for (TotalFly totalFly : totalFlyListListQ3.get(i)) {
							String fly_nbr = totalFly.getFlt_Nbr();
							TotalFly totalFlyTal = new TotalFly();
							int flag = 0;
							for (TotalFly totalFlyh : totalFlyListListH3.get(i)) {
								String strflyNbr = totalFlyh.getFlt_Nbr();
								if(!TextUtil.isEmpty(fly_nbr)&&!TextUtil.isEmpty(strflyNbr)){
									int lastNum1 = HbhCharater(fly_nbr.substring(5, 6));
									int lastNum2 = HbhCharater(strflyNbr.substring(5, 6));
									int twolastNum1 = HbhCharater(fly_nbr.substring(4, 5));
									String onelastNum1 = fly_nbr.substring(0, 4);
									String newStr1= "";
									String newStr2= "";
									if(lastNum1%2==0){
										//偶数
										if(lastNum1==0){
											newStr2 = onelastNum1 + (twolastNum1-1) + "9";
										}else{
											newStr2 = onelastNum1+ twolastNum1 + (lastNum1-1);
										}
									}else{
										if(lastNum1==9){
											newStr2 = onelastNum1 + (twolastNum1+1) + "0";
										}else{
											newStr2 = onelastNum1+ twolastNum1 + (lastNum1+1);
										}
									}
									
									 newStr1= strflyNbr.substring(0, 5)+lastNum2;
									if(newStr1.equals(newStr2)){
									flag++;
//									totalFlyTal.setFlt_Nbr(fly_nbr+"/"+strflyNbr);
									if(lastNum1==0){
										totalFlyTal.setFlt_Nbr(onelastNum1+(twolastNum1-1)+lastNum2+"/"+twolastNum1+lastNum1);
									}else if(lastNum2==0){
										totalFlyTal.setFlt_Nbr(onelastNum1+(twolastNum1-1)+lastNum1+"/"+twolastNum1+lastNum2);
									}else if(lastNum1>lastNum2){
										totalFlyTal.setFlt_Nbr(onelastNum1+twolastNum1+lastNum2+"/"+lastNum1);
									}else{
										totalFlyTal.setFlt_Nbr(onelastNum1+twolastNum1+lastNum1+"/"+lastNum2);
									}
									totalFlyTal.setTal_Nbr_Set_num(totalFly.getTal_Nbr_Set_num()+totalFlyh.getTal_Nbr_Set_num());
									totalFlyTal.setPgs_Per_num(totalFly.getPgs_Per_num()+totalFlyh.getPgs_Per_num());
									totalFlyTal.setGrp_Nbr_num(totalFly.getGrp_Nbr_num()+totalFlyh.getGrp_Nbr_num());
									totalFlyTal.setHangduan(totalFly.getHangduan().split("-")[0]+"="+totalFly.getHangduan().split("-")[1]);
									totalFlyTal.setStartDate(totalFly.getStartDate());
									totalFlyTal.setEndDate(totalFly.getEndDate());
									totalFlyTal.setJbzw(totalFly.getJbzw()+totalFlyh.getJbzw());
									totalFlyTal.setJblk(totalFly.getJblk()+totalFlyh.getJblk());
									totalFlyTal.setJbtd(totalFly.getJbtd()+totalFlyh.getJbtd());
								}
								}else{
									totalFlyTal = totalFly;
								}
							}
							if(flag==0){
								totalFlyTal.setFlt_Nbr(fly_nbr);
							}
							totalFlyListTal.add(totalFlyTal);
						}
						totalFlyListListTal3.add(totalFlyListTal);
					}
					
				}
			}else{
				if(totalFlyListListQ3==null){
					totalFlyListListTal3 = totalFlyListListQ3;
				}else{
					totalFlyListListTal3 = totalFlyListListH3;
				}
			}
			if(totalFlyListListTal3!=null&&totalFlyListListTal3.size()>0){
				for(int i = 0;i<totalFlyListListTal3.size();i++){
					sort(totalFlyListListTal3.get(i),"pgs_Per_num","desc");
				}
			}
			retMap.put("allClassRank",totalFlyListListTal3 );
		
//		}
		return retMap;
	}
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
	/**
	 * 获得流量汇总图数据
	 * @Title: getFlowCountData 
	 * @Description:  
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public List<List<OutPort>> getFlowCountData(TotalFlyAnalysisQuery totalFlyAnalysisQuery){
		List<List<OutPort>> datas = totalFlyAnalysisService.getFlowCountData(totalFlyAnalysisQuery);
		return datas;
	}
	/**
	 * 获得均班客量图数据
	 * @Title: getAllAmountData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery  
	 * @param @return    
	 * @return List<List<EvenPort>>     
	 * @throws
	 */
	public List<List<EvenPort>> getAllAmountData(TotalFlyAnalysisQuery totalFlyAnalysisQuery){
		List<List<EvenPort>> datas = totalFlyAnalysisService.getAllAmountData(totalFlyAnalysisQuery);
		return datas;
	}
	/**
	 * 航班排行
	 * @Title: getFlightNumData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return List<List<TotalFly>>     
	 * @throws
	 */
	public List<List<TotalFly>> getFlightNumData(TotalFlyAnalysisQuery totalFlyAnalysisQuery){
		List<List<TotalFly>> datas = totalFlyAnalysisService.getFlightNumData(totalFlyAnalysisQuery);
		return datas;
	}
	/**
	 * 座收排行
	 * @Title: getRaskRankingData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return List<List<TotalFly>>     
	 * @throws
	 */
	public List<List<TotalFly>> getRaskRankingData(TotalFlyAnalysisQuery totalFlyAnalysisQuery){
		List<List<TotalFly>> datas = totalFlyAnalysisService.getRaskRankingData(totalFlyAnalysisQuery);
		return datas;
	}
	/**
	 * 客量排行
	 * @Title: getPassengerRankData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return List<List<TotalFly>>     
	 * @throws
	 */
	public List<List<TotalFly>> getPassengerRankData(TotalFlyAnalysisQuery totalFlyAnalysisQuery){
		List<List<TotalFly>> datas = totalFlyAnalysisService.getPassengerRankData(totalFlyAnalysisQuery);
		return datas;
	}
	/**
	 * 均班客量排行
	 * @Title: getAllClassRankData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return List<List<TotalFly>>     
	 * @throws
	 */
	public List<List<TotalFly>> getAllClassRankData(TotalFlyAnalysisQuery totalFlyAnalysisQuery){
		List<List<TotalFly>> datas = totalFlyAnalysisService.getAllClassRankData(totalFlyAnalysisQuery);
		return datas;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })  
    public void sort(List<TotalFly> targetList, final String sortField, final String sortMode) {  
        Collections.sort(targetList, new Comparator() {  
            public int compare(Object obj1, Object obj2) {   
                int retVal = 0;  
                try {  
                    //首字母转大写  
                    String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");   
                    String methodStr="get"+newStr;  
                    Method method1 = ((TotalFly)obj1).getClass().getMethod(methodStr, null);  
                    Method method2 = ((TotalFly)obj2).getClass().getMethod(methodStr, null);  
                    if (sortMode != null && "desc".equals(sortMode)) {  
                        retVal = method2.invoke(((TotalFly) obj2), null).toString().compareTo(method1.invoke(((TotalFly) obj1), null).toString()); // 倒序  
                    } else {  
                        retVal = method1.invoke(((TotalFly) obj1), null).toString().compareTo(method2.invoke(((TotalFly) obj2), null).toString()); // 正序  
                    }  
                } catch (Exception e) {  
                    throw new RuntimeException();  
                }  
                return retVal;  
            }  
        });  
    }  
}
