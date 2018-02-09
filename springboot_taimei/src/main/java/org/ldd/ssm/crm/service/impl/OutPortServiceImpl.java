package org.ldd.ssm.crm.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ldd.ssm.crm.domain.AirLineAllInfo;
import org.ldd.ssm.crm.domain.ClassRanking;
import org.ldd.ssm.crm.domain.DOW;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.EveryDuanInfo;
import org.ldd.ssm.crm.domain.OutPort;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.mapper.DOWMapper;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.query.AirportOperatingHistoryQuery;
import org.ldd.ssm.crm.query.DOWObject;
import org.ldd.ssm.crm.query.DOWQuery;
import org.ldd.ssm.crm.service.IOutPortService;
import org.ldd.ssm.crm.utils.DOWDataUtils;
import org.ldd.ssm.crm.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OutPortServiceImpl implements IOutPortService  {
	@Autowired
	private DOWMapper dowMapper;
	@Autowired
	private OutPortMapper outPortMapper;
	/**
	 * 拿到全年的所有的月份统计数据
	 */
	public DOWObject<OutPort> getMethod(DOWQuery dta_Sce) {
		String airCode = outPortMapper.getAirCodeByName(dta_Sce.getDpt_AirPt_Cd());
		if(TextUtil.isEmpty(airCode)){
			return null;
		}
		dta_Sce.setDpt_AirPt_Cd(airCode);
		//1,先查询当前年下的所有月份
		List<OutPort> rows = new ArrayList<OutPort>();
		dta_Sce.setLcl_Dpt_Day(dta_Sce.getLcl_Dpt_Day());
		//拿到有数据的月份
		List<DOW> method = dowMapper.getMethod(dta_Sce);
		//拿到条件查询的航距
		int airDistacne = outPortMapper.getAirDistacne(dta_Sce.getDpt_AirPt_Cd()+dta_Sce.getArrv_Airpt_Cd());
		//2,根据当前月查询数据每个月数据,统计每个月的数据
		for (DOW dow : method) {
			dta_Sce.setMonth(dow.getMethod());
			OutPort date = outPortMapper.getMonthDate(dta_Sce);
			date.setMonth(dow.getMethod());
			date.setFlt_Ser_Ine(new BigDecimal(date.getFlt_Ser_Ine()/airDistacne).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
			date.setAvg_Dct(new BigDecimal(date.getAvg_Dct()*100).setScale(3, BigDecimal.ROUND_HALF_UP).intValue());
			date.setIdd_Dct(new BigDecimal(date.getIdd_Dct()*100).setScale(3, BigDecimal.ROUND_HALF_UP).intValue());
			date.setGrp_Dct(new BigDecimal(date.getGrp_Dct()*100).setScale(3,BigDecimal.ROUND_HALF_UP).intValue());
			rows.add(date);
		}
		return new DOWObject<OutPort>(rows, rows.size());
	}
	/**
	 * 拿到图表所需要统计数据
	 */
	public List<OutPort> getOutPort(DOWQuery dta_Sce) {
//		String airCode = outPortMapper.getAirCodeByName(dta_Sce.getDpt_AirPt_Cd());
		String airCode = dta_Sce.getDpt_AirPt_Cd();
		if(TextUtil.isEmpty(airCode)){
			return null;
		}
		if ("1".equals(dta_Sce.getState())) {
			dta_Sce.setDpt_AirPt_Cd(airCode);
			dta_Sce.setArrv_Airpt_Cd(null);
		}else if("0".equals(dta_Sce.getState())){
			dta_Sce.setDpt_AirPt_Cd(null);
			dta_Sce.setArrv_Airpt_Cd(airCode);
		}
		List<OutPort> rows = new ArrayList<OutPort>();
		//拿到有数据的月份
		List<DOW> method = dowMapper.getMethod(dta_Sce);
		if(method.isEmpty()){
			return null;
		}
		//2,根据当前月查询数据每个月数据,统计每个月的数据
		for (DOW dow : method) {
			dta_Sce.setMonth(dow.getMethod());
			OutPort date = outPortMapper.getMonthDate(dta_Sce);
			//这里判断的拿到的不是空数据
			if(date.getCla_Nbr()!=0&&date.getTal_Nbr_Set()!=0&&date.getIdd_moh()!=0){
				date.setMonth(dow.getMethod());
				rows.add(date);
			}
		}
		return rows;
	}
	/**
	 * 拿到年数据的所有月份的均班统计数据
	 */
	public DOWObject<EvenPort> getEvenPort(DOWQuery dta_Sce) {
		String airCode = dta_Sce.getDpt_AirPt_Cd();
//		String airCode = outPortMapper.getAirCodeByName(dta_Sce.getDpt_AirPt_Cd());
		if(TextUtil.isEmpty(airCode)){
			return null;
		}
		if ("1".equals(dta_Sce.getState())) {
			dta_Sce.setDpt_AirPt_Cd(airCode);
			dta_Sce.setArrv_Airpt_Cd(null);
		}else if("0".equals(dta_Sce.getState())){
			dta_Sce.setDpt_AirPt_Cd(null);
			dta_Sce.setArrv_Airpt_Cd(airCode);
		}
		List<EvenPort> rows = new ArrayList<EvenPort>();
		List<DOW> method = dowMapper.getMethod(dta_Sce);
		if(method.isEmpty()){
			return null;
		}
		for (DOW dow : method) {
			dta_Sce.setMonth(dow.getMethod());
			EvenPort data = outPortMapper.getEvenPort(dta_Sce);
			data.setMonth(dow.getMethod()+"月");
			data.setTme_Nbr(DOWDataUtils.getNumbertime(dta_Sce.getLcl_Dpt_Day(),dow.getMethod()));
			data.setIdd_Dct(new BigDecimal(data.getIdd_Dct()/1000).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
			data.setLod_For(new BigDecimal(data.getLod_For()*100).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
			data.setFlt_Ser_Ine((new BigDecimal(data.getFlt_Ser_Ine()).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue())/100);
			rows.add(data);
		}
		
		return new DOWObject<EvenPort>(rows, rows.size());
	}
	public List<ClassRanking> getClassRanking(DOWQuery dta_Sce) {
		String airCode = dta_Sce.getDpt_AirPt_Cd();
//		String airCode = outPortMapper.getAirCodeByName(dta_Sce.getDpt_AirPt_Cd());
		if(TextUtil.isEmpty(airCode)){
			return null;
		}
		if ("1".equals(dta_Sce.getState())) {
			dta_Sce.setDpt_AirPt_Cd(airCode);
			dta_Sce.setArrv_Airpt_Cd(null);
		}else if("0".equals(dta_Sce.getState())){
			dta_Sce.setDpt_AirPt_Cd(null);
			dta_Sce.setArrv_Airpt_Cd(airCode);
		}
		List<ClassRanking> set_Ktr_Ine = outPortMapper.getClassRanking(dta_Sce);
		for (ClassRanking classRanking : set_Ktr_Ine) {
			classRanking.setDpt_AirPt_Cd(outPortMapper.getNameByCode(classRanking.getDpt_AirPt_Cd()));
			classRanking.setArrv_Airpt_Cd(outPortMapper.getNameByCode(classRanking.getArrv_Airpt_Cd()));
		}
		return set_Ktr_Ine;
	}
	public List<ClassRanking> getSet_Ktr_Ine(DOWQuery dta_Sce) {
		String airCode = dta_Sce.getDpt_AirPt_Cd();
		if(TextUtil.isEmpty(airCode)){
			return null;
		}
		if ("1".equals(dta_Sce.getState())) {
			dta_Sce.setDpt_AirPt_Cd(airCode);
			dta_Sce.setArrv_Airpt_Cd(null);
		}else if("0".equals(dta_Sce.getState())){
			dta_Sce.setDpt_AirPt_Cd(null);
			dta_Sce.setArrv_Airpt_Cd(airCode);
		}
		List<ClassRanking> set_Ktr_Ine = outPortMapper.getSet_Ktr_Ine(dta_Sce);
		for (ClassRanking classRanking : set_Ktr_Ine) {
			classRanking.setCount(new BigDecimal(classRanking.getCount()).setScale(2,BigDecimal.ROUND_HALF_UP).abs().toString());
		}
		return set_Ktr_Ine;
	}
	/**
	 * 历史运营各个指标计算
	 * @Title: airportHistroy 
	 * @Description:  
	 * @param @param zairdataListAllList
	 * @param @return    
	 * @return Map<String,List<Object>>     
	 * @throws
	 */
	public Map<String,Map<String,Map<String,String>>> airportHistroy(DOWQuery dta_Sce){
		List<Z_Airdata> zairdataListAllListtemp = outPortMapper.getSet_Ktr_IneNew(dta_Sce);
		//是否包含异常航段
		String exceptionFlag = "1";
		Map<String,Object> dataMapp = TextUtil.getIsIncludeExceptionData(zairdataListAllListtemp, dta_Sce.getIsIncludeExceptionData());
		List<Z_Airdata>  dataListtemp = (List<Z_Airdata>) dataMapp.get("zairdataListB");
		String dataFlage = (String) dataMapp.get("flage");
		String strrfalgep = "single_false";
		Map<String,Object> dataMap2p = TextUtil.getIsIncludeExceptionHangduan(dataListtemp, dta_Sce.getIsIncludeExceptionHuangduan(),strrfalgep);
		List<Z_Airdata> zairdataListAllList = (List<Z_Airdata>) dataMap2p.get("zairdataListB");
		String dataFlage2 = (String) dataMap2p.get("flage");
		if("on".equals(dta_Sce.getIsIncludeExceptionData())&&"on".equals(dta_Sce.getIsIncludeExceptionHuangduan())){
			if("true".equals(dataFlage)&&"true".equals(dataFlage2)){
				exceptionFlag = "4";
			}else{
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}else{
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}else{
			if("on".equals(dta_Sce.getIsIncludeExceptionData())){
				//是否包含异常数据
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}
			}else{
				if("on".equals(dta_Sce.getIsIncludeExceptionHuangduan())){
					//是否包含异常航段
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}
		Map<String,Map<String,Map<String,String>>> retMap = new HashMap<String,Map<String,Map<String,String>>>();
		String monthOrDay = dta_Sce.getMonthOrDay();
		if("month".equals(monthOrDay)){
			//按月份流量走势
			SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<String> months = new ArrayList<String>();
			List<String> codes = new ArrayList<String>();
			List<String> hss = new ArrayList<String>();
			//计算出所有月份
			for (Z_Airdata z_airdata : zairdataListAllList) {
				String dptCode = z_airdata.getDpt_AirPt_Cd();
				String arrCode = z_airdata.getArrv_Airpt_Cd();
				String hs = z_airdata.getCpy_Nm();
				String day = sdf.format(z_airdata.getLcl_Dpt_Day());
				String [] str = day.split("-");
				if(!months.contains(str[0]+""+str[1])){
					months.add(str[0]+""+str[1]);
				}
				if(!codes.contains(dptCode+arrCode)){
					codes.add(dptCode+arrCode);
				}
				if(!hss.contains(hs)){
					hss.add(hs);
				}
			}
			Map<String,Map<String,String>> monthllMap = new HashMap<String,Map<String,String>>();
			Map<String,Map<String,String>> monthjbllMap = new HashMap<String,Map<String,String>>();
			DecimalFormat df = new DecimalFormat("0.00");
			for (String month : months) {
				double monthBc = 0.00;
				double monthPerson = 0.00;
				double monthSet = 0.00;
				double monthSr = 0.00;
				double monthTd = 0.00;
				double monthTdsr = 0.00;
				Map<String,String> llMap = new HashMap<String,String>();
				Map<String,String> jbllMap = new HashMap<String,String>();
				for (Z_Airdata z_airdata : zairdataListAllList) {
					String day = sdf.format(z_airdata.getLcl_Dpt_Day());
					String [] str = day.split("-");
					if((str[0]+""+str[1]).equals(month)){
						monthPerson = monthPerson + z_airdata.getPgs_Per_Cls();
						monthSet = monthSet + z_airdata.getTal_Nbr_Set();
						monthSr = monthSr + z_airdata.getTotalNumber();
						monthTd = monthTd + z_airdata.getGrp_Nbr();
						monthTdsr = monthTdsr + z_airdata.getGrp_Ine().doubleValue();
					}
				}
				for (int i = 1; i < 32; i++) {
					List<String> fltNbrs = new ArrayList<String>();
					String dayTemp = "";
					if(i<10){
						dayTemp = month.substring(0, 4)+"-"+month.substring(4, 6)+"-0"+i;
					}else{
						dayTemp = month.substring(0, 4)+"-"+month.substring(4, 6)+"-"+i;
					}
					for (Z_Airdata z_airdata : zairdataListAllList) {
						String day = sdf.format(z_airdata.getLcl_Dpt_Day());
						String fltnbr = z_airdata.getFlt_Nbr();
						if(day.equals(dayTemp)){
							if(!fltNbrs.contains(fltnbr)){
								monthBc ++;
								fltNbrs.add(fltnbr);
							}
						}
					}
					
				}
				//月流量
				llMap.put("label",month+"月");
				llMap.put("idd_moh",df.format(monthPerson)+"");
				llMap.put("Cla_Nbr",df.format(monthBc)+"");
				llMap.put("Tal_Nbr_Set",df.format(monthSet)+"");
				llMap.put("Grp_moh",df.format(monthTd)+"");
				llMap.put("Grp_Ine",df.format(monthTdsr));
				llMap.put("Tol_Ine",monthSr+"");
				monthllMap.put(month, llMap);
				//均班流量
				jbllMap.put("label",month+"月");
				jbllMap.put("cla_Set", df.format(monthSet/monthBc));
				jbllMap.put("cla_Per", df.format(monthPerson/monthBc));
				jbllMap.put("cla_Grp",df.format(monthTd/monthBc));
				jbllMap.put("flt_Ser_Ine", df.format(monthTdsr/monthBc/100));
				jbllMap.put("lod_For",df.format((monthPerson/monthBc)/(monthSet/monthBc)*100));
				jbllMap.put("idd_Dct",df.format(monthSr/monthBc/1000));
				monthjbllMap.put(month, jbllMap);
			}
			retMap.put("monthll", monthllMap);
			retMap.put("monthjbll", monthjbllMap);
			Map<String,Map<String,String>> codebcMap = new HashMap<String,Map<String,String>>();
			Map<String,Map<String,String>> codezsMap = new HashMap<String,Map<String,String>>();
			Map<String,Map<String,String>> codelkMap = new HashMap<String,Map<String,String>>();
			Map<String,Map<String,String>> codejblkMap = new HashMap<String,Map<String,String>>();
			for (String code : codes) {
				double codeBc = 0.0;
				double codePerson = 0.0;
				double codeSet = 0.0;
				double codeSr = 0.0;
				double codeIne = 0.0;
				double monthTd = 0.0;
				Map<String,String> bcMap = new HashMap<String,String>();
				Map<String,String> zsMap = new HashMap<String,String>();
				Map<String,String> lkMap = new HashMap<String,String>();
				Map<String,String> jbklMap = new HashMap<String,String>();
				for (Z_Airdata z_airdata : zairdataListAllList) {
					String dptCode = z_airdata.getDpt_AirPt_Cd();
					String arrCode = z_airdata.getArrv_Airpt_Cd();
					if(code.equals(dptCode+arrCode)){
						codeBc ++;
						codePerson = codePerson + z_airdata.getPgs_Per_Cls();
						codeSet = codeSet + z_airdata.getTal_Nbr_Set();
						codeSr = codeSr + z_airdata.getTotalNumber();
						monthTd = monthTd + z_airdata.getGrp_Nbr();
						if(z_airdata.getSailingDistance()>0&&z_airdata.getTal_Nbr_Set()>0){
							codeIne = codeIne + (double)z_airdata.getTotalNumber()/(double)z_airdata.getSailingDistance()/(double)z_airdata.getTal_Nbr_Set();
						}
					}
				}
				//班次排行
				bcMap.put("count", df.format(codeBc)+"");
				bcMap.put("dpt_AirPt_Cd",code.substring(0, 3));
				bcMap.put("arrv_Airpt_Cd", code.substring(3, 6));
				codebcMap.put(code, bcMap);
				//坐收排行
				if(codeSr/codeBc/1000>1){
					zsMap.put("set_Ktr_Ine",df.format(codeSr/codeBc/1000));
					zsMap.put("count", df.format((codeIne/codeBc)*100));
					zsMap.put("dpt_AirPt_Cd",code.substring(0, 3));
					zsMap.put("arrv_Airpt_Cd", code.substring(3, 6));
					codezsMap.put(code, zsMap);
				}
				//旅客排行
				lkMap.put("value", df.format(codePerson)+"");
				lkMap.put("dpt_AirPt_Cd",code.substring(0, 3));
				lkMap.put("arrv_Airpt_Cd", code.substring(3, 6));
				codelkMap.put(code, lkMap);
				//均班客量排行
				jbklMap.put("count", df.format(codeSet/codeBc)+"");
				jbklMap.put("set_Ktr_Ine", df.format(codePerson/codeBc)+"");
				jbklMap.put("guestamount", df.format(monthTd/codeBc)+"");
				jbklMap.put("dpt_AirPt_Cd",code.substring(0, 3));
				jbklMap.put("arrv_Airpt_Cd", code.substring(3, 6));
				codejblkMap.put(code, jbklMap);
			}
			
			retMap.put("codebcMap", codebcMap);
			retMap.put("codezsMap", codezsMap);
			retMap.put("codelkMap", codelkMap);
			retMap.put("codejblkMap", codejblkMap);
			Map<String,Map<String,String>> hsbcMap = new HashMap<String,Map<String,String>>();
			Map<String,Map<String,String>> hsklMap = new HashMap<String,Map<String,String>>();
			for (String hs : hss) {
				double hsBc = 0.0;
				double hsPerson = 0.0;
				Map<String,String> bcMap = new HashMap<String,String>();
				Map<String,String> klMap = new HashMap<String,String>();
				List<String> hsFlag = new ArrayList<String>();
				for (Z_Airdata z_airdata : zairdataListAllList) {
					String hstemp = z_airdata.getCpy_Nm();
					String tt = sdf.format(z_airdata.getLcl_Dpt_Day())+","+z_airdata.getFlt_Nbr()+","+z_airdata.getFlt_Rte_Cd();
					if(hs.equals(hstemp)&&!hsFlag.contains(tt)){
						hsBc++;
						hsFlag.add(tt);
						hsPerson = hsPerson + z_airdata.getPgs_Per_Cls();
					}
				}
				//航司班次排行
				bcMap.put("count", df.format(hsBc));
				bcMap.put("cpy_Nm", hs);
				hsbcMap.put(hs, bcMap);
				//航司客量排行
				klMap.put("value", df.format(hsPerson));
				klMap.put("cpy_Nm", hs);
				hsklMap.put(hs, klMap);
			}
			retMap.put("hsbcMap", hsbcMap);
			retMap.put("hsklMap", hsklMap);
		}else{
			//按日流量走势
			SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<String> days = new ArrayList<String>();
			List<String> codes = new ArrayList<String>();
			List<String> hss = new ArrayList<String>();
			//计算出所有天数
			for (Z_Airdata z_airdata : zairdataListAllList) {
				String dptCode = z_airdata.getDpt_AirPt_Cd();
				String arrCode = z_airdata.getArrv_Airpt_Cd();
				String hs = z_airdata.getCpy_Nm();
				String day = sdf.format(z_airdata.getLcl_Dpt_Day());
				if(!days.contains(day)){
					days.add(day);
				}
				if(!codes.contains(dptCode+arrCode)){
					codes.add(dptCode+arrCode);
				}
				if(!hss.contains(hs)){
					hss.add(hs);
				}
			}
			Map<String,Map<String,String>> monthllMap = new HashMap<String,Map<String,String>>();
			Map<String,Map<String,String>> monthjbllMap = new HashMap<String,Map<String,String>>();
			DecimalFormat df = new DecimalFormat("0.00");
			for (String day : days) {
				double dayBc = 0.00;
				double dayPerson = 0.00;
				double daySet = 0.00;
				double daySr = 0.00;
				double dayTd = 0.00;
				double dayTdsr = 0.00;
				List<String> fltNbrs = new ArrayList<String>();
				Map<String,String> llMap = new HashMap<String,String>();
				Map<String,String> jbllMap = new HashMap<String,String>();
				for (Z_Airdata z_airdata : zairdataListAllList) {
					String daytemp = sdf.format(z_airdata.getLcl_Dpt_Day());
					if(daytemp.equals(day)){
						String fltnbr = z_airdata.getFlt_Nbr();
						if(!fltNbrs.contains(fltnbr)){
							dayBc ++;
							fltNbrs.add(fltnbr);
						}
						dayPerson = dayPerson + z_airdata.getPgs_Per_Cls();
						daySet = daySet + z_airdata.getTal_Nbr_Set();
						daySr = daySr + z_airdata.getTotalNumber();
						dayTd = dayTd + z_airdata.getGrp_Nbr();
						dayTdsr = dayTdsr + z_airdata.getGrp_Ine().doubleValue();
					}
				}
				//日流量
				llMap.put("label",day);
				llMap.put("idd_moh",df.format(dayPerson)+"");
				llMap.put("Cla_Nbr",df.format(dayBc)+"");
				llMap.put("Tal_Nbr_Set",df.format(daySet)+"");
				llMap.put("Grp_moh",df.format(dayTd)+"");
				llMap.put("Grp_Ine",df.format(dayTdsr));
				llMap.put("Tol_Ine",daySr+"");
				monthllMap.put(day.replace("-", ""), llMap);
				//均班流量
				jbllMap.put("label",day);
				jbllMap.put("cla_Set", df.format(daySet/dayBc));
				jbllMap.put("cla_Per", df.format(dayPerson/dayBc));
				jbllMap.put("cla_Grp",df.format(dayTd/dayBc));
				jbllMap.put("flt_Ser_Ine", df.format(dayTdsr/dayBc/100));
				jbllMap.put("lod_For",df.format((dayPerson/dayBc)/(daySet/dayBc)*100));
				jbllMap.put("idd_Dct",df.format(daySr/dayBc/1000));
				monthjbllMap.put(day.replace("-", ""), jbllMap);
			}
			retMap.put("monthll", monthllMap);
			retMap.put("monthjbll", monthjbllMap);
			Map<String,Map<String,String>> codebcMap = new HashMap<String,Map<String,String>>();
			Map<String,Map<String,String>> codezsMap = new HashMap<String,Map<String,String>>();
			Map<String,Map<String,String>> codelkMap = new HashMap<String,Map<String,String>>();
			Map<String,Map<String,String>> codejblkMap = new HashMap<String,Map<String,String>>();
			for (String code : codes) {
				double codeBc = 0.0;
				double codePerson = 0.0;
				double codeSet = 0.0;
				double codeSr = 0.0;
				double codeIne = 0.0;
				double monthTd = 0.0;
				Map<String,String> bcMap = new HashMap<String,String>();
				Map<String,String> zsMap = new HashMap<String,String>();
				Map<String,String> lkMap = new HashMap<String,String>();
				Map<String,String> jbklMap = new HashMap<String,String>();
				for (Z_Airdata z_airdata : zairdataListAllList) {
					String dptCode = z_airdata.getDpt_AirPt_Cd();
					String arrCode = z_airdata.getArrv_Airpt_Cd();
					if(code.equals(dptCode+arrCode)){
						codeBc ++;
						codePerson = codePerson + z_airdata.getPgs_Per_Cls();
						codeSet = codeSet + z_airdata.getTal_Nbr_Set();
						codeSr = codeSr + z_airdata.getTotalNumber();
						monthTd = monthTd + z_airdata.getGrp_Nbr();
						if(z_airdata.getSailingDistance()>0&&z_airdata.getTal_Nbr_Set()>0){
							codeIne = codeIne + (double)z_airdata.getTotalNumber()/(double)z_airdata.getSailingDistance()/(double)z_airdata.getTal_Nbr_Set();
						}
					}
				}
				//班次排行
				bcMap.put("count", df.format(codeBc)+"");
				bcMap.put("dpt_AirPt_Cd",code.substring(0, 3));
				bcMap.put("arrv_Airpt_Cd", code.substring(3, 6));
				codebcMap.put(code, bcMap);
				//坐收排行
				if(codeSr/codeBc/1000>1){
					zsMap.put("set_Ktr_Ine",df.format(codeSr/codeBc/1000));
					zsMap.put("count", df.format((codeIne/codeBc)*100));
					zsMap.put("dpt_AirPt_Cd",code.substring(0, 3));
					zsMap.put("arrv_Airpt_Cd", code.substring(3, 6));
					codezsMap.put(code, zsMap);
				}
				//旅客排行
				lkMap.put("value", df.format(codePerson)+"");
				lkMap.put("dpt_AirPt_Cd",code.substring(0, 3));
				lkMap.put("arrv_Airpt_Cd", code.substring(3, 6));
				codelkMap.put(code, lkMap);
				//均班客量排行
				jbklMap.put("count", df.format(codeSet/codeBc)+"");
				jbklMap.put("set_Ktr_Ine", df.format(codePerson/codeBc)+"");
				jbklMap.put("guestamount", df.format(monthTd/codeBc)+"");
				jbklMap.put("dpt_AirPt_Cd",code.substring(0, 3));
				jbklMap.put("arrv_Airpt_Cd", code.substring(3, 6));
				codejblkMap.put(code, jbklMap);
			}
			
			retMap.put("codebcMap", codebcMap);
			retMap.put("codezsMap", codezsMap);
			retMap.put("codelkMap", codelkMap);
			retMap.put("codejblkMap", codejblkMap);
			Map<String,Map<String,String>> hsbcMap = new HashMap<String,Map<String,String>>();
			Map<String,Map<String,String>> hsklMap = new HashMap<String,Map<String,String>>();
			for (String hs : hss) {
				double hsBc = 0.0;
				double hsPerson = 0.0;
				Map<String,String> bcMap = new HashMap<String,String>();
				Map<String,String> klMap = new HashMap<String,String>();
				List<String> hsFlag = new ArrayList<String>();
				for (Z_Airdata z_airdata : zairdataListAllList) {
					String hstemp = z_airdata.getCpy_Nm();
					String tt = sdf.format(z_airdata.getLcl_Dpt_Day())+","+z_airdata.getFlt_Nbr()+","+z_airdata.getFlt_Rte_Cd();
					if(hs.equals(hstemp)&&!hsFlag.contains(tt)){
						hsBc++;
						hsFlag.add(tt);
						hsPerson = hsPerson + z_airdata.getPgs_Per_Cls();
					}
				}
				//航司班次排行
				bcMap.put("count", df.format(hsBc));
				bcMap.put("cpy_Nm", hs);
				hsbcMap.put(hs, bcMap);
				//航司客量排行
				klMap.put("value", df.format(hsPerson));
				klMap.put("cpy_Nm", hs);
				hsklMap.put(hs, klMap);
			}
			retMap.put("hsbcMap", hsbcMap);
			retMap.put("hsklMap", hsklMap);
		}
		Map<String,Map<String,String>> p1 = new HashMap<String,Map<String,String>>();
		Map<String,String> p2 = new HashMap<String,String>();
		p2.put("exceptionFlag", exceptionFlag);
		p1.put("exceptionFlag", p2);
		retMap.put("exceptionFlag", p1);
		return retMap;
		
	}
	
	
	public List<ClassRanking> getGuestamount(DOWQuery dta_Sce) {
		String airCode = dta_Sce.getDpt_AirPt_Cd();
//		String airCode = outPortMapper.getAirCodeByName(dta_Sce.getDpt_AirPt_Cd());
		if(TextUtil.isEmpty(airCode)){
			return null;
		}
		if ("1".equals(dta_Sce.getState())) {
			dta_Sce.setDpt_AirPt_Cd(airCode);
			dta_Sce.setArrv_Airpt_Cd(null);
		}else if("0".equals(dta_Sce.getState())){
			dta_Sce.setDpt_AirPt_Cd(null);
			dta_Sce.setArrv_Airpt_Cd(airCode);
		}
		List<ClassRanking> set_Ktr_Ine = outPortMapper.getGuestamount(dta_Sce);
		for (ClassRanking classRanking : set_Ktr_Ine) {
			classRanking.setCount(new BigDecimal(classRanking.getCount()).setScale(2,BigDecimal.ROUND_HALF_UP).abs().toString());
//			classRanking.setDpt_AirPt_Cd(outPortMapper.getNameByCode(classRanking.getDpt_AirPt_Cd()));
//			classRanking.setArrv_Airpt_Cd(outPortMapper.getNameByCode(classRanking.getArrv_Airpt_Cd()));
		}
		return set_Ktr_Ine;
	}
	public List<ClassRanking> getAmountRanking(DOWQuery dta_Sce) {
		String airCode = dta_Sce.getDpt_AirPt_Cd();
//		String airCode = outPortMapper.getAirCodeByName(dta_Sce.getDpt_AirPt_Cd());
		if(TextUtil.isEmpty(airCode)){
			return null;
		}
		if ("1".equals(dta_Sce.getState())) {
			dta_Sce.setDpt_AirPt_Cd(airCode);
			dta_Sce.setArrv_Airpt_Cd(null);
		}else if("0".equals(dta_Sce.getState())){
			dta_Sce.setDpt_AirPt_Cd(null);
			dta_Sce.setArrv_Airpt_Cd(airCode);
		}
		List<ClassRanking> rankings = outPortMapper.getAmountRanking(dta_Sce);
//		for (ClassRanking classRanking : rankings) {
//			classRanking.setDpt_AirPt_Cd(outPortMapper.getNameByCode(classRanking.getDpt_AirPt_Cd()));
//			classRanking.setArrv_Airpt_Cd(outPortMapper.getNameByCode(classRanking.getArrv_Airpt_Cd()));
//		}
		return rankings;
	}
	/**
	 * 航司班次排行
	 */
	@Override
	public List<ClassRanking> getCpy_ClassRanking(DOWQuery dta_Sce) {
		String airCode = dta_Sce.getDpt_AirPt_Cd();
//		String airCode = outPortMapper.getAirCodeByName(dta_Sce.getDpt_AirPt_Cd());
		if(TextUtil.isEmpty(airCode)){
			return null;
		}
		if ("1".equals(dta_Sce.getState())) {
			dta_Sce.setDpt_AirPt_Cd(airCode);
			dta_Sce.setArrv_Airpt_Cd(null);
		}else if("0".equals(dta_Sce.getState())){
			dta_Sce.setDpt_AirPt_Cd(null);
			dta_Sce.setArrv_Airpt_Cd(airCode);
		}
		List<ClassRanking> set_Ktr_Ine = outPortMapper.getCpy_ClassRanking(dta_Sce);
		return set_Ktr_Ine;
	}
	@Override
	public List<ClassRanking> getCpy_AmountRanking(DOWQuery dta_Sce) {
		String airCode = dta_Sce.getDpt_AirPt_Cd();
//		String airCode = outPortMapper.getAirCodeByName(dta_Sce.getDpt_AirPt_Cd());
		if(TextUtil.isEmpty(airCode)){
			return null;
		}
		if ("1".equals(dta_Sce.getState())) {
			dta_Sce.setDpt_AirPt_Cd(airCode);
			dta_Sce.setArrv_Airpt_Cd(null);
		}else if("0".equals(dta_Sce.getState())){
			dta_Sce.setDpt_AirPt_Cd(null);
			dta_Sce.setArrv_Airpt_Cd(airCode);
		}
		List<ClassRanking> rankings = outPortMapper.getCpy_AmountRanking(dta_Sce);
		return rankings;
	}
	@Override
	public List<String> getYears(String itia) {
		if(itia==null||"".equals(itia)){
			return null;
		}
		List<String> years = null;
		try {
			years = outPortMapper.getYearList(itia);
		} catch (Exception e) {
			e.printStackTrace();
			return years;
		}
		return years;
	}
	@Override
	public Map<String, List<Map<String, Map<String, String>>>> getFltNbrInfoData(AirportOperatingHistoryQuery query) {
		Map<String, List<Map<String, Map<String, String>>>> retMap = new HashMap<String, List<Map<String, Map<String, String>>>>();
		DecimalFormat df = new DecimalFormat("0.00");
		List<Z_Airdata> dataList1 = outPortMapper.getZairdatasList(query);
		List<Z_Airdata> dataList = new ArrayList<Z_Airdata>(); 
		for (Z_Airdata z_Airdata : dataList1) {
			String br = z_Airdata.getFlt_Nbr();
			String last = br.substring(br.length()-1, br.length());
			String newBr = "";
			if(!TextUtil.isNum(last)){
				last = TextUtil.HbhCharater(last)+"";
				newBr = br.substring(0, br.length()-1)+last;
			}else{
				newBr = br;	
			}
			if(newBr.length()>=6){
				z_Airdata.setFlt_Nbr(newBr);
				dataList.add(z_Airdata);
			}
		}
		List<Z_Airdata> dataListtemp = new ArrayList<Z_Airdata>();
		List<Z_Airdata> dataListNew = new ArrayList<Z_Airdata>();
		//是否包含异常航段
		String exceptionFlag = "1";
		Map<String,Object> dataMapp = TextUtil.getIsIncludeExceptionData(dataList, query.getIsIncludeExceptionData());
		dataListtemp = (List<Z_Airdata>) dataMapp.get("zairdataListB");
		String dataFlage = (String) dataMapp.get("flage");
		String strrfalgep = "true";
		Map<String,Object> dataMap2p = TextUtil.getIsIncludeExceptionHangduan(dataListtemp, query.getIsIncludeExceptionHuangduan(),strrfalgep);
		dataListNew = (List<Z_Airdata>) dataMap2p.get("zairdataListB");
		String dataFlage2 = (String) dataMap2p.get("flage");
		if("on".equals(query.getIsIncludeExceptionData())&&"on".equals(query.getIsIncludeExceptionHuangduan())){
			if("true".equals(dataFlage)&&"true".equals(dataFlage2)){
				exceptionFlag = "4";
			}else{
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}else{
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}else{
			if("on".equals(query.getIsIncludeExceptionData())){
				//是否包含异常数据
				if("true".equals(dataFlage)){
					exceptionFlag = "2";
				}
			}else{
				if("on".equals(query.getIsIncludeExceptionHuangduan())){
					//是否包含异常航段
					if("true".equals(dataFlage2)){
						exceptionFlag = "3";
					}
				}
			}
		}
		List<String> flyNbrHangxianList = new ArrayList<String>();
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		for (Z_Airdata zairdata : dataListNew) {
			String flyNbr = zairdata.getFlt_Nbr();
			String fltRetCd = zairdata.getFlt_Rte_Cd();
			String key1 =  fltRetCd +","+ flyNbr;
			if(!flyNbrHangxianList.contains(key1)){
				flyNbrHangxianList.add(key1);
			}
		}
		Map<String,List<Z_Airdata>> tempMap1 = new HashMap<String,List<Z_Airdata>>();
		for (String key1 : flyNbrHangxianList) {
			for (Z_Airdata zairdata : dataListNew) {
				String keytemp = zairdata.getFlt_Rte_Cd() +","+zairdata.getFlt_Nbr();
				if(key1.equals(keytemp)){
					if(tempMap1.containsKey(key1)){
						List<Z_Airdata> tempList = 	tempMap1.get(key1);
						tempList.add(zairdata);
					}else{
						List<Z_Airdata> tempList = new ArrayList<Z_Airdata>();
						tempList.add(zairdata);
						tempMap1.put(key1, tempList);
					}
				}
			}
		}
		for (Entry<String,List<Z_Airdata>> entry : tempMap1.entrySet()) {
			String key = entry.getKey();
			String [] str =  key.split(",");
			if(str.length<2){
				continue;
			}
			String airline =str[0];
			String flyNbrgo =str[1];
			String flyNbrback ="";
			String backLine ="";
			if(airline.trim().length()==6){
				backLine = airline.substring(3, 6)+airline.substring(0, 3);
			}else{
				backLine = airline.substring(6, 9)+airline.substring(3, 6)+airline.substring(0, 3);
			}
			String firststr = flyNbrgo.substring(0, 2);
			String lastStr = flyNbrgo.substring(flyNbrgo.length()-1, flyNbrgo.length());
			if(!TextUtil.isNum(lastStr)){
				lastStr = TextUtil.HbhCharater(lastStr)+"";
				flyNbrgo = flyNbrgo.substring(0, flyNbrgo.length()-1)+lastStr;
			}
			int lasttwostr  = Integer.parseInt(flyNbrgo.substring(2, flyNbrgo.length()));
			if(lasttwostr%2==0){
				flyNbrback = firststr + (lasttwostr-1);
			}else{
				flyNbrback = firststr +  (lasttwostr+1);
			}
			List<Z_Airdata> zairdataList = new ArrayList<Z_Airdata>();
			List<Z_Airdata> zairdataList1 = entry.getValue();
			List<Z_Airdata> zairdataList2 = new ArrayList<Z_Airdata>();
			for (Entry<String,List<Z_Airdata>> entry1 : tempMap1.entrySet()) {
				String tempKey = entry1.getKey();
				if(tempKey.equals(backLine+","+flyNbrback)){
					zairdataList2 = entry1.getValue();
				}
			}
			zairdataList.addAll(zairdataList1);
			zairdataList.addAll(zairdataList2);
			double bc = 0.0;
			double tdkl = 0.0;
			double stzkl = 0.0;
			double kzl = 0.0;
			double tdsr = 0.0;
			double stzsr = 0.0;
			double pjzk = 0.0;
			double zglsr = 0.0;
			if(airline.trim().length()==6){
				//直飞只有一个航段
				String dptcd = airline.substring(0, 3);
				String arrcd = airline.substring(3, 6);
				List<Map<String, Map<String, String>>> templist = new ArrayList<Map<String, Map<String, String>>>();
				templist.add(getDuanData(dptcd,arrcd,zairdataList1));
				retMap.put(key,templist);
			}else if(airline.trim().length()==9){
				//经停有三个航段加合计
				String dptcd = airline.substring(0, 3);
				String pascd = airline.substring(3, 6);
				String arrcd = airline.substring(6, 9);
				List<Map<String, Map<String, String>>> templist = new ArrayList<Map<String, Map<String, String>>>();
				List<Z_Airdata> zairdataListd1 = new ArrayList<Z_Airdata>();
				for (Z_Airdata z_Airdata : zairdataList) {
					String dpt = z_Airdata.getDpt_AirPt_Cd();
					String arr = z_Airdata.getArrv_Airpt_Cd();
					if(dpt.equals(dptcd)&&arr.equals(pascd)){
						zairdataListd1.add(z_Airdata);
					}
				}
				templist.add(getDuanData(dptcd,pascd,zairdataListd1));

				List<Z_Airdata> zairdataListd2 = new ArrayList<Z_Airdata>();
				for (Z_Airdata z_Airdata : zairdataList) {
					String dpt = z_Airdata.getDpt_AirPt_Cd();
					String arr = z_Airdata.getArrv_Airpt_Cd();
					if(dpt.equals(dptcd)&&arr.equals(arrcd)){
						zairdataListd2.add(z_Airdata);
					}
				}
				templist.add(getDuanData(dptcd,arrcd,zairdataListd2));
				List<Z_Airdata> zairdataListd3 = new ArrayList<Z_Airdata>();
				for (Z_Airdata z_Airdata : zairdataList) {
					String dpt = z_Airdata.getDpt_AirPt_Cd();
					String arr = z_Airdata.getArrv_Airpt_Cd();
					if(dpt.equals(pascd)&&arr.equals(arrcd)){
						zairdataListd3.add(z_Airdata);
					}
				}
				templist.add(getDuanData(pascd,arrcd,zairdataListd3));
				//加合计
				List<String> dateList =  new ArrayList<String>();
				for (Z_Airdata z_Airdata : zairdataList1) {
					String datee = sdf.format(z_Airdata.getLcl_Dpt_Day());
					if(!dateList.contains(datee)){
						dateList.add(datee);
					}
				}
				int zglindex =0;
				int kzlindex =0;
				int zkindex =0;
				List<Z_Airdata> zairdataListTemp = new ArrayList<Z_Airdata>();
				zairdataListTemp.addAll(zairdataListd1);
				zairdataListTemp.addAll(zairdataListd2);
				zairdataListTemp.addAll(zairdataListd3);
				for (String day : dateList) {
					bc++;
					AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
					
					airLineAllInfo = TextUtil.getAirLineAllInfo(zairdataListTemp,day,dptcd,pascd,arrcd);
					EveryDuanInfo everyDuanInfo= TextUtil.getEveryDuanInfo(airLineAllInfo);
					stzsr = stzsr + everyDuanInfo.getGoAndBack_sr();
					tdkl = tdkl + everyDuanInfo.getGoAndBack_tdrs();
					stzkl = stzkl + everyDuanInfo.getGoAndBack_rs();
					tdsr = tdsr + everyDuanInfo.getGoAndBack_tdsr();
					if(everyDuanInfo.getGoAndBack_zgl()>0){
						zglsr = zglsr + everyDuanInfo.getGoAndBack_zgl();
						zglindex++;
					}
					if(everyDuanInfo.getGoAndBack_kzl()>0){
						kzl = kzl + everyDuanInfo.getGoAndBack_kzl();
						kzlindex++;
					}
					if(everyDuanInfo.getGoAndBack_zk()>0){
						pjzk = pjzk + everyDuanInfo.getGoAndBack_zk();
						zkindex++;
					}
				}
				Map<String,Map<String,String>> lineMap = new HashMap<String,Map<String,String>>();
				Map<String,String> dataMap = new HashMap<String,String>();
				dataMap.put("bc", bc+"");
				dataMap.put("tdkl", df.format(tdkl));
				dataMap.put("stzkl", df.format(stzkl));
				dataMap.put("tdsr", df.format(tdsr));
				dataMap.put("stzsr", df.format(stzsr));
				dataMap.put("kzl", df.format(kzl/kzlindex));
				dataMap.put("zglsr", df.format(zglsr/zglindex));
				dataMap.put("pjzk", df.format(pjzk/zkindex));
				lineMap.put(dptcd+"-"+pascd+"-"+arrcd, dataMap);
				templist.add(lineMap);
				retMap.put(key, templist);
			}
		}
		List<Map<String, Map<String, String>>> l1 = new ArrayList<Map<String, Map<String, String>>>();
		Map<String,Map<String,String>> p1 = new HashMap<String,Map<String,String>>();
		Map<String,String> m1 = new HashMap<String,String>();
		m1.put("exceptionFlag", exceptionFlag);
		p1.put("exceptionFlag", m1);
		l1.add(p1);
		retMap.put("exceptionFlag", l1);
		return retMap;
	}
	
	public  Map<String, Map<String, String>> getDuanData(String dptCode,String arrCode,List<Z_Airdata> dataList){
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("0.00");
		List<String> dateList =  new ArrayList<String>();
		for (Z_Airdata z_Airdata : dataList) {
			String datee = sdf.format(z_Airdata.getLcl_Dpt_Day());
			if(!dateList.contains(datee)){
				dateList.add(datee);
			}
		}
		double bc = 0.0;
		double tdkl = 0.0;
		double stzkl = 0.0;
		double kzl = 0.0;
		double tdsr = 0.0;
		double stzsr = 0.0;
		double pjzk = 0.0;
		double zglsr = 0.0;
		int zglindex =0;
		int kzlindex =0;
		int zkindex =0;
		for (String day : dateList) {
			double daytdkl = 0.0;
			double daystzkl = 0.0;
			double daytdsr = 0.0;
			double daystzsr = 0.0;
			double daypjzkq = 0.0;
			double daykzlq = 0.0;
			double dayzglsrq = 0.0;
			for (Z_Airdata z_Airdata : dataList) {
				//特定航线特定航班号每一天有多条数据
				String dpt = z_Airdata.getDpt_AirPt_Cd();
				String arr = z_Airdata.getArrv_Airpt_Cd();
				String date = sdf.format(z_Airdata.getLcl_Dpt_Day());
				if((dptCode.equals(dpt)&&arrCode.equals(arr))&&date.equals(day)){
					//去程
					//去团队客量
					daytdkl = daytdkl + z_Airdata.getGrp_Nbr();
					//去散团总客量
					daystzkl = daystzkl + z_Airdata.getPgs_Per_Cls();
					//去客座率
					if(z_Airdata.getTal_Nbr_Set()>0){
						daykzlq = z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
					}
					//去团队收入
					daytdsr = daytdsr + z_Airdata.getGrp_Ine().doubleValue();
					//去散团总收入
					daystzsr = daystzsr + (double) z_Airdata.getTotalNumber();
					//去的平均折扣
					daypjzkq = z_Airdata.getAvg_Dct().doubleValue();
					//去的座公里
					dayzglsrq = (double) z_Airdata.getSet_Ktr_Ine().doubleValue();
				}
			}
			bc = bc + 1;
			tdkl = tdkl +  daytdkl;
			stzkl = stzkl + daystzkl;
			tdsr = tdsr + daytdsr;
			stzsr = stzsr + daystzsr;
			if(daykzlq>0){
				kzlindex++;
				kzl = kzl + daykzlq;
			}
			if(daypjzkq>0){
				zkindex++;
				pjzk = pjzk + daypjzkq;
			}
			if(dayzglsrq>0){
				zglsr = zglsr + dayzglsrq;
				zglindex++;
			}
		}
		Map<String,Map<String,String>> lineMap = new HashMap<String,Map<String,String>>();
		Map<String,String> dataMap = new HashMap<String,String>();
		if(bc<=0){
			dataMap.put("bc", "0");
			dataMap.put("tdkl", "0");
			dataMap.put("stzkl", "0");
			dataMap.put("tdsr", "0");
			dataMap.put("stzsr", "0");
			dataMap.put("kzl", "0");
			dataMap.put("pjzk", "0");
			dataMap.put("zglsr", "0");

		}else{
			dataMap.put("bc", bc+"");
			dataMap.put("tdkl", df.format(tdkl));
			dataMap.put("stzkl", df.format(stzkl));
			dataMap.put("tdsr", df.format(tdsr));
			dataMap.put("stzsr", df.format(stzsr));
			dataMap.put("kzl", df.format(kzl/kzlindex));
			dataMap.put("pjzk", df.format(pjzk/zkindex));
			dataMap.put("zglsr", df.format(zglsr/zglindex));
		}
		lineMap.put(dptCode+"-"+arrCode, dataMap);
		return lineMap;
	}
}
