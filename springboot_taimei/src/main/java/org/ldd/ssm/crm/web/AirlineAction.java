package org.ldd.ssm.crm.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.AllClzIncome;
import org.ldd.ssm.crm.domain.Company;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.IncomeCount;
import org.ldd.ssm.crm.domain.IncomeCountTime;
import org.ldd.ssm.crm.domain.OutPort;
import org.ldd.ssm.crm.domain.SalePlanData;
import org.ldd.ssm.crm.domain.TimeCount;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.query.AirLineQueryNew;
import org.ldd.ssm.crm.query.CabinSeatSetQuery;
import org.ldd.ssm.crm.query.DOWQuery;
import org.ldd.ssm.crm.query.MonthParameterQuery;
import org.ldd.ssm.crm.query.MonthSalesPlanQuery;
import org.ldd.ssm.crm.service.IAirLineService;
import org.ldd.ssm.crm.utils.DataUtils;
import org.ldd.ssm.crm.utils.ParameterUtils;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;


@Controller
public class AirlineAction {
	@Autowired
	private IAirLineService iAirLineService;
	@Autowired
	private OutPortMapper outPortMapper;
	//springMVC请求的映射地址
	@MyMethodNote(comment="航线历史收益统计主页:2")
	@MethodNote(comment="航线历史收益统计:1")
	@RequestMapping("/airline")
	public String getOutPort() {
//			return "charts/airLine/airline";
		return "newHtml/line-historicalRevenue";
	}
	
	/**
	 * 航线历史运营统计数据接口
	 * @Title: getAirportHistroyData 
	 * @Description:  
	 * @param @param airLineQueryNew
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/restful/getAirportHistroyData",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="航线历史收益统计查询:2")
	@MethodNote(comment="航线历史收益统计:1")
	@ResponseBody
	public String getAirportHistroyData(AirLineQueryNew airLineQueryNew){
		Map<String,Object> retMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		airLineQueryNew.setHangsi(null);
		//判断条件
		if(TextUtil.isEmpty(airLineQueryNew.getPas_stp())){
			airLineQueryNew.setPas_stp(null);
		}
		if(!"on".equals(airLineQueryNew.getIsIncludePas())){
			airLineQueryNew.setIsIncludePas(null);
		}
		if(!"on".equals(airLineQueryNew.getIsIncludeExceptionData())){
			airLineQueryNew.setIsIncludeExceptionData(null);
		}
		if(!"on".equals(airLineQueryNew.getIsIncludeExceptionHuangduan())){
			airLineQueryNew.setIsIncludeExceptionHuangduan(null);
		}
		if(!"on".equals(airLineQueryNew.getIsIncludeRdf())){
			airLineQueryNew.setIsIncludeRdf(null);
		}
		if(!TextUtil.isEmpty(airLineQueryNew.getFlt_nbr())){
			if("汇总".equals(airLineQueryNew.getFlt_nbr())){
				airLineQueryNew.setFlt_nbr(null);
			}else{
				String [] str = airLineQueryNew.getFlt_nbr().split("/");
				if(str.length==2){
					if(HbhCharater(str[0].substring(str[0].length()-1, str[0].length()))%2==1){
						airLineQueryNew.setGoNum(str[0]);
						airLineQueryNew.setHuiNum(str[1]);
					}else{
						airLineQueryNew.setGoNum(str[1]);
						airLineQueryNew.setHuiNum(str[0]);
					}
				}
			}
		}
		Map<String,Object> airLineDataMap = new HashMap<String,Object>();
		airLineDataMap = iAirLineService.getAirportHistroyData(airLineQueryNew);
		for (String key : airLineDataMap.keySet()) {
			Map<String,String> strMap = (Map<String, String>) airLineDataMap.get(key);
			String sksrd = strMap.get("sksrd").replace(",", "");
			String tdsrd = strMap.get("tdsrd").replace(",", "");
			if((Double.parseDouble(sksrd)+Double.parseDouble(tdsrd))>0){
				strMap.put("flag", "1");
			}else{
				strMap.put("flag", "0");
			}
		}
		retMap.put("map", airLineDataMap);
		retMap.put("Dpt_AirPt_Cd", outPortMapper.getairportNameByCode(airLineQueryNew.getDpt_AirPt_Cd()));
		retMap.put("Pas_stp", outPortMapper.getairportNameByCode(airLineQueryNew.getPas_stp()));
		retMap.put("Arrv_Airpt_Cd", outPortMapper.getairportNameByCode(airLineQueryNew.getArrv_Airpt_Cd()));
		retMap.put("Dpt_AirPt_Cd_code", airLineQueryNew.getDpt_AirPt_Cd());
		retMap.put("Pas_stp_code", airLineQueryNew.getPas_stp());
		retMap.put("Arrv_Airpt_Cd_code", airLineQueryNew.getArrv_Airpt_Cd());
		retMap.put("flt_nbr", airLineQueryNew.getFlt_nbr()==null?"汇总":airLineQueryNew.getFlt_nbr());
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	@RequestMapping(value="/restful/getAirportHistroyNewDate",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="航线历史收益统计查询:2")
	@MethodNote(comment="航线历史收益统计:1")
	@ResponseBody
	public String getAirportHistroyNewDate(AirLineQueryNew airLineQueryNew){
		Map<String,Object> retMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		airLineQueryNew.setHangsi(null);
		//判断条件
		if(TextUtil.isEmpty(airLineQueryNew.getPas_stp())){
			airLineQueryNew.setPas_stp(null);
		}
		if(!"on".equals(airLineQueryNew.getIsIncludePas())){
			airLineQueryNew.setIsIncludePas(null);
		}
		if(!TextUtil.isEmpty(airLineQueryNew.getFlt_nbr())){
			if("汇总".equals(airLineQueryNew.getFlt_nbr())){
				airLineQueryNew.setFlt_nbr(null);
			}else{
				String [] str = airLineQueryNew.getFlt_nbr().split("/");
				if(str.length==2){
					if(HbhCharater(str[0].substring(str[0].length()-1, str[0].length()))%2==1){
						airLineQueryNew.setGoNum(str[0]);
						airLineQueryNew.setHuiNum(str[1]);
					}else{
						airLineQueryNew.setGoNum(str[1]);
						airLineQueryNew.setHuiNum(str[0]);
					}
				}
			}
		}
		//得到最新有数据的日期
		String newDate = iAirLineService.getNewDate(airLineQueryNew);
		retMap.put("newDate", newDate);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	
	
	/**
	 * 出港/进港流量图形
	 * @param dowData
	 * @return
	 */
	@MyMethodNote(comment="航线历史收益统计功能:2")
	@MethodNote(comment="航线历史收益统计:1")
	@RequestMapping("/airline_list")
	@ResponseBody
	public Map<String, Object> airline_lists(DOWQuery dowData){
		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();//装所有图表数据的集合
		//拿到始发,经停,到达 三地的三字码
		dowData  = iAirLineService.getAll_Itia(dowData);
		//这里应该把参数进行从新组合
		List<DOWQuery> queries = ParameterUtils.getArrayParameter(dowData);
		List<Z_Airdata> airdatas = null;
		if(queries.size()==6){
			airdatas = iAirLineService.getAll_Para_Data(dowData.getLcl_Dpt_Day().replaceAll("年", ""),queries.get(0).getFlt_Rte_Cd(),queries.get(3).getFlt_Rte_Cd());//查询参数中的年份的所有数据
		}else if(queries.size()==2){
			airdatas = iAirLineService.getAll_Para_Data(dowData.getLcl_Dpt_Day().replaceAll("年", ""),queries.get(0).getFlt_Rte_Cd(),queries.get(1).getFlt_Rte_Cd());//查询参数中的年份的所有数据
		}
		if(airdatas!=null){
			for (DOWQuery dowQuery : queries) {
				lists.add(airline_list(dowQuery,airdatas));
			}
		}
		rootMap.put("lists", lists);
		return rootMap;
	}
	/**
	 * 生成图表
	 * @param dowData
	 * @return
	 */
	public Map<String, Object> airline_list(DOWQuery dowData,List<Z_Airdata> airdatas){
		String str = DataUtils.getYearState(dowData);
		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<OutPort> rows = iAirLineService.getOutPort(dowData,airdatas);
		if(rows.size()==0){
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
		return rootMap;
	}
	/**
	 * 均班客流量走势图
	 * @param dowData
	 * @return
	 */
	@MethodNote(comment="航线历史收益统计:1")
	@RequestMapping("/airline_list2")
	@ResponseBody
	public Map<String, Object> list2(DOWQuery dowData) {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();//装所有图表数据的集合
		//拿到始发,经停,到达 三地的三字码
		dowData  = iAirLineService.getAll_Itia(dowData);
		//这里应该把参数进行从新组合
		List<DOWQuery> queries = ParameterUtils.getArrayParameter(dowData);
		List<Z_Airdata> airdatas = null;
		if(queries.size()==6){
			airdatas = iAirLineService.getAll_Para_Data(dowData.getLcl_Dpt_Day().replaceAll("年", ""),queries.get(0).getFlt_Rte_Cd(),queries.get(3).getFlt_Rte_Cd());//查询参数中的年份的所有数据
		}else if(queries.size()==2){
			airdatas = iAirLineService.getAll_Para_Data(dowData.getLcl_Dpt_Day().replaceAll("年", ""),queries.get(0).getFlt_Rte_Cd(),queries.get(1).getFlt_Rte_Cd());//查询参数中的年份的所有数据
		}
		if(airdatas!=null){
			for (DOWQuery dowQuery : queries) {
				lists.add(airline_list2(airdatas,dowQuery));
			}
		}
		rootMap.put("lists", lists);
		return rootMap;
	}
	
	public Map<String, Object> airline_list2(List<Z_Airdata> airdatas,DOWQuery dowData) {
		String str = DataUtils.getYearState(dowData);
		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<EvenPort> rows = iAirLineService.getEvenPort(airdatas,dowData);
		if(rows.size()==0){
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
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		List<String[]> list = new ArrayList<String[]>();
		for (EvenPort dt : rows) {
			String[] str1 = {dt.getMonth()+"月",dt.getTme_Nbr()+"",dt.getCla_Nbr()+"",dt.getTme_Cla_Moh()+"",dt.getCla_Set()+"",dt.getCla_Per()+"",dt.getCla_Grp()+"",dt.getFlt_Ser_Ine()+"",dt.getLod_For()+"",dt.getIdd_Dct()+"",str+"均班客流量走势图"};
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
	 * 获得当前查询的航线或者经停航线的所有航班号
	 * @param dowData
	 * @return
	 */
	@MethodNote(comment="航线历史收益统计:1;销售报表:13;机场历史运营:2;客源组成:4")
	@RequestMapping("/getFlt_Nbr")
	@ResponseBody
	public Map<String, Object> getFlt_Nbr(DOWQuery dowData) {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<DOWQuery> arrayParameter = iAirLineService.getParameter(dowData);
		List<List<String>> flt_Nbr =new ArrayList<List<String>>();
		List<String> list = new ArrayList<String>();
		for (DOWQuery dowQuery : arrayParameter) {
			flt_Nbr.add(iAirLineService.getFlt_Nbr(dowQuery));
		}
		for (int i = 0; i < flt_Nbr.get(0).size(); i++) {
			for (int j = 0; j < flt_Nbr.get(1).size(); j++) {
				String fly_nbr = flt_Nbr.get(0).get(i);
				String strflyNbr = flt_Nbr.get(1).get(j);
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
						if(lastNum1%2==0){
							list.add("航班号"+strflyNbr+"/"+fly_nbr);
						}else{
							list.add("航班号"+fly_nbr+"/"+strflyNbr);
						}
					}
				}
			}
		}
		rootMap.put("list", list);
		return rootMap;
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
	 * 获得当前查询的航线或者经停航线的所有航班号
	 * @param dowData
	 * @return
	 */
	@RequestMapping("/getMonthFltNum")
//	@MethodNote(comment="航线历史收益统计:1")
	@ResponseBody
	public Map<String, Object> getMonthFltNum(DOWQuery dowData) {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<DOWQuery> arrayParameter = iAirLineService.getParameter(dowData);
		List<List<String>> flt_Nbr =new ArrayList<List<String>>();
		List<String> list = new ArrayList<String>();
		for (DOWQuery dowQuery : arrayParameter) {
			dowQuery.setCompanyId(Long.valueOf(UserContext.getCompanyId()));
			flt_Nbr.add(iAirLineService.getMonthFltNum(dowQuery));
		}
		for (int i = 0; i < flt_Nbr.get(0).size(); i++) {
			for (int j = 0; j < flt_Nbr.get(1).size(); j++) {
				String fly_nbr = flt_Nbr.get(0).get(i);
				String strflyNbr = flt_Nbr.get(1).get(j);
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
						list.add("航班号"+fly_nbr+"/"+strflyNbr);
					}
				}
			}
		}
		rootMap.put("list", list);
		return rootMap;
	}
	/**
	 * 获得当前查询的航线或者经停航线的总飞时间
	 * @param dowData
	 * @return
	 */
	@RequestMapping("/getTimeCount")
	@MethodNote(comment="销售报表:13")
	@ResponseBody
	public Map<String, Object> getTimeCount(DOWQuery dowData) {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		if(dowData.getPas_stp()==""){
			dowData.setPas_stp(null);
		}
		if(dowData.getCpy_Nm()==""){
			dowData.setCpy_Nm(null);
		}
		if("汇总".equals(dowData.getFlt_nbr())){
			dowData.setFlt_nbr(null);
		}else{
			String [] flyNbr = dowData.getFlt_nbr().substring(3, dowData.getFlt_nbr().length()).split("/");
			dowData.setGoNum(flyNbr[0]);
			dowData.setArrNum(flyNbr[1]);
		}
		dowData.setLcl_Dpt_Day(dowData.getLcl_Dpt_Day().substring(0, 4));
		String arrayParameter = iAirLineService.getTimeCount(dowData);
		java.text.DecimalFormat  df   =new   java.text.DecimalFormat("0.00");
		if(arrayParameter!=null){
			arrayParameter = df.format(Double.parseDouble(arrayParameter));
		}else{
			arrayParameter = "0.00";
		}
		rootMap.put("list", arrayParameter);
		return rootMap;
	}
	/**
	 * 保存新填的总飞时间
	 * @param dowData
	 * @return
	 */
	@RequestMapping("/saveTimeCount")
	@MethodNote(comment="销售报表:13")
	@ResponseBody
	public Map<String, Object> saveTimeCount(DOWQuery dowData) {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		//查询数据库是否存在当前的航线总飞数据,如果有则更新,如果没有则添加
		String saveOrUpdata = "";
		if(dowData.getPas_stp()==""){
			dowData.setPas_stp(null);
		}
		if(dowData.getCpy_Nm()==""){
			dowData.setCpy_Nm(null);
		}
		TimeCount timecount= iAirLineService.getTimeCountData(dowData);
		
		if(timecount!=null){
			timecount.setTim_cout(dowData.getFlt_Rte_Cd());
			saveOrUpdata = iAirLineService.updata(timecount);
		}else {
			timecount = new TimeCount();
			timecount.setLcl_dpt_day(dowData.getLcl_Dpt_Day());
			timecount.setDpt_airpt_cd(dowData.getDpt_AirPt_Cd());
			timecount.setArrv_airpt_cd(dowData.getArrv_Airpt_Cd());
			timecount.setFlt_nbr(dowData.getFlt_nbr());
			timecount.setPas_stp(dowData.getPas_stp());
			timecount.setCpy_nm(dowData.getCpy_Nm());
			timecount.setTim_cout(dowData.getFlt_Rte_Cd());//作为总飞时长
			timecount.setDta_sce(dowData.getDta_Sce());
			saveOrUpdata = iAirLineService.save(timecount);
		}
		rootMap.put("list", "新的总飞时间"+saveOrUpdata+"成功");
		return rootMap;
	}
	/**
	 * 查询新的航线统计的数据表格
	 * @param dowData
	 * @return
	 */
	@MyMethodNote(comment="航线统计表格:2")
//	@MethodNote(comment="航线历史收益统计:1")
	@RequestMapping("/getTableData")
	@ResponseBody
	public Map<String, Object> getTableData(DOWQuery dowData) {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		//拿到统计数据,数据结构为最外层一个List集合, 里面装每个装数据集合的集合, 集合中装每个月的数据
		List<List<AllClzIncome>> airdatas = iAirLineService.getTableData(dowData);
		rootMap.put("list", airdatas);
		return rootMap;
	}
	/**
	 * 查询航线汇总的参数
	 * @param dowData
	 * @return
	 */
	@RequestMapping("/getDateAndCost")
	@ResponseBody
	public Map<String, Object> getDateAndCost(IncomeCountTime countTime) {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		//拿到统计数据,数据结构为最外层一个List集合, 里面装每个装数据集合的集合, 集合中装每个月的数据
		IncomeCountTime airdatas = iAirLineService.getDateAndCost(countTime);
		rootMap.put("list", airdatas);
		return rootMap;
	}
	/**
	 * 保存航线汇总的参数
	 * @param dowData
	 * @return
	 */
	@RequestMapping("/saveParameter")
	@ResponseBody
	public Map<String, Object> saveParameter(IncomeCountTime countTime) {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		//拿到统计数据,数据结构为最外层一个List集合, 里面装每个装数据集合的集合, 集合中装每个月的数据
		//查询或更新
		IncomeCountTime airdatas = iAirLineService.getDateAndCost(countTime);
		if(airdatas!=null){
			countTime.setId(airdatas.getId());
			iAirLineService.updataParameter(countTime);
		}else{
			iAirLineService.saveParameter(countTime);
		}
		rootMap.put("list", true);
		return rootMap;
	}
	/**
	 * 生成收益汇总数据
	 * @param dowData
	 * @return
	 */
	@MyMethodNote(comment="收益汇总:2")
//	@MethodNote(comment="生成收益汇总:72")
	@RequestMapping("/getDataTable")
	@ResponseBody
	public Map<String, Object> getDataTable(IncomeCountTime countTime) {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		//拿到统计数据,数据结构为最外层一个List集合, 里面装每个装数据集合的集合, 集合中装每个月的数据
		List<IncomeCount> countTimes = iAirLineService.getDataTable(countTime);
		rootMap.put("list", countTimes);
		return rootMap;
	}
	@MyMethodNote(comment="月度销售预案:2")
	@RequestMapping("/monthSalesPlan")
	public String getMonthSalesPlan(){
		return "charts/monthSalesPlan/airline";
	}
	@RequestMapping("/monthSalesPlanParam")
	@ResponseBody
	public Map<String,Object> getMonthSalesPlanParam(MonthSalesPlanQuery query){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		//设置公司
		Company company = UserContext.getCompany();
		if(company!=null){
			query.setCompanyId(company.getId());
		}
		if(query.getFlt_nbr()!=null&&"汇总".equals(query.getFlt_nbr())){
			query.setFlt_nbr(null);
		}
		rootMap = iAirLineService.getMonthSalePlanParam(query);
		return rootMap;
	}
	@MyMethodNote(comment="淡旺季数据:2")
	@RequestMapping("/getMonthData")
	@ResponseBody
	public Map<String,Object> getMonthData(MonthSalesPlanQuery query){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		//设置公司
		Company company = UserContext.getCompany();
		if(company!=null){
			query.setCompanyId(company.getId());
		}
		if(query.getFlt_nbr()!=null&&"汇总".equals(query.getFlt_nbr())){
			query.setFlt_nbr(null);
		}
		rootMap = iAirLineService.getMonthData(query);
		return rootMap;
	}
	@MyMethodNote(comment="淡旺季数据:3")
//	@MethodNote(comment="淡旺季数据:81")
	@RequestMapping("/saveMonthParam")
	@ResponseBody
	public Map<String,Object> saveMonthParam(MonthParameterQuery query){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap = iAirLineService.saveMonthParam(query);
		return returnMap;
	}
	@MyMethodNote(comment="淡旺季数据:3")
//	@MethodNote(comment="预案数据:81")
	@RequestMapping("/saveSalePlanData")
	@ResponseBody
	public Map<String,Object> saveSalePlanData(SalePlanData data){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap = iAirLineService.saveSalePlanData(data);
		return returnMap;
	}
	@RequestMapping("/deleteSalePlanData")
	@ResponseBody
	public Map<String,Object> deleteSalePlanData(Integer seasonId,String fltNbr){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap = iAirLineService.deleteSalePlanData(seasonId,fltNbr);
		return returnMap;
	}
	@MyMethodNote(comment="淡旺季数据:2")
	@RequestMapping("/getCabinSeatSetData")
	@ResponseBody
	public Map<String,Object> getCabinSeatSetData(MonthSalesPlanQuery query){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		//设置公司
		Company company = UserContext.getCompany();
		if(company!=null){
			query.setCompanyId(company.getId());
		}
		rootMap = iAirLineService.getCabinSeatSetData(query);
		return rootMap;
	}
	@MyMethodNote(comment="舱位设定数据:3")
//	@MethodNote(comment="舱位设定数据:81")
	@RequestMapping("/saveCabinSeatSetData")
	@ResponseBody
	public Map<String,Object> saveCabinSeatSetData(CabinSeatSetQuery query){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		rootMap = iAirLineService.saveCabinSeatSetData(query);
		return rootMap;
	}
	
	@RequestMapping("/deleteCabinSeatSetData")
	@ResponseBody
	public Map<String,Object> deleteCabinSeatSetData(Integer seasonId,String fltNbr){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		rootMap = iAirLineService.deleteCabinSeatSetData(seasonId,fltNbr);
		return rootMap;
	}
	
	@RequestMapping("/getCurrentFlightSalePlan")
	@ResponseBody
	public Map<String,Object> getCurrentFlightSalePlan(MonthSalesPlanQuery query){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map = iAirLineService.getCurrentFlightSalePlan(query);
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
			return map;
		}
		return map;
	}
}
