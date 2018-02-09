package org.ldd.ssm.crm.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.ldd.ssm.crm.domain.AirInfoData;
import org.ldd.ssm.crm.domain.AirportData;
import org.ldd.ssm.crm.domain.AllAirLineFltDetailData;
import org.ldd.ssm.crm.domain.CityCoordinate;
import org.ldd.ssm.crm.domain.Eachflightinfo;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.FocusFlight;
import org.ldd.ssm.crm.domain.HomePageData;
import org.ldd.ssm.crm.domain.HomepageZdlDomin;
import org.ldd.ssm.crm.domain.Yesterday_ZD;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.mapper.AirInfoDataMapper;
import org.ldd.ssm.crm.mapper.AirlineDynameicMapper;
import org.ldd.ssm.crm.mapper.BuyTicketReportMapper;
import org.ldd.ssm.crm.mapper.FocusFlightMapper;
import org.ldd.ssm.crm.mapper.HomePageMapper;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.mapper.SalesReportServiceMapper;
import org.ldd.ssm.crm.mapper.SourceDistriMapper;
import org.ldd.ssm.crm.query.AllAirLineQuery;
import org.ldd.ssm.crm.query.FilterParamAirPortObject;
import org.ldd.ssm.crm.query.FilterParamAirPortQuery;
import org.ldd.ssm.crm.query.HavingDataQuery;
import org.ldd.ssm.crm.query.HomePageQuery;
import org.ldd.ssm.crm.query.SalesReportQuery;
import org.ldd.ssm.crm.service.HomePageService;
import org.ldd.ssm.crm.utils.DataUtils;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
/**
 * 首页实现service
 * @Title:HomePageServiceImpl 
 * @Description:
 * @author taimei-gds 
 * @date 2016-6-12 下午3:50:03
 */
@Service
public class HomePageServiceImpl implements HomePageService {
	@Autowired
	private HomePageMapper homePageMapper;
	@Autowired
	private OutPortMapper outPortMapper;
	@Autowired
	private AirlineDynameicMapper airlineDynameicMapper;
	@Autowired
	private SalesReportServiceMapper salesReportServiceMapper;
	@Autowired
	private AirInfoDataMapper airInfoDataMapper;
	@Autowired
	private FocusFlightMapper focusFlightMapper;
	@Autowired
	private BuyTicketReportMapper buyTicketReportMapper;
	@Autowired 
	private SourceDistriMapper sourceDistriMapper;
	Logger log = Logger.getLogger(HomePageServiceImpl.class);

	public String getYesterdayEarnings(HomePageQuery homePageQuery) {
		String jc_name = homePageQuery.getAirPort();
		String jc_code = outPortMapper.getAirCodeByName(jc_name);
		homePageQuery.setAirPort(jc_code);
		String ruselt = homePageMapper.getYesterdayEarnings(homePageQuery);
		homePageQuery.setAirPort(jc_name);
		
		return ruselt;
	}

	public String getYesterdayClasses(HomePageQuery homePageQuery) {
		String jc_name = homePageQuery.getAirPort();
		String jc_code = outPortMapper.getAirCodeByName(jc_name);
		homePageQuery.setAirPort(jc_code);
		String ruselt = homePageMapper.getYesterdayClasses(homePageQuery);
		homePageQuery.setAirPort(jc_name);
		return ruselt;
	}

	public String getYesterdayPutin(HomePageQuery homePageQuery) {
		String jc_name = homePageQuery.getAirPort();
		String jc_code = outPortMapper.getAirCodeByName(jc_name);
		homePageQuery.setAirPort(jc_code);
		String ruselt = homePageMapper.getYesterdayPutin(homePageQuery);
		homePageQuery.setAirPort(jc_name);
		return ruselt;
	}

	public String getYesterdayLeave(HomePageQuery homePageQuery) {
		String jc_name = homePageQuery.getAirPort();
		String jc_code = outPortMapper.getAirCodeByName(jc_name);
		homePageQuery.setAirPort(jc_code);
		String ruselt = homePageMapper.getYesterdayLeave(homePageQuery);
		homePageQuery.setAirPort(jc_name);
		return ruselt;
	}

	public List<HomePageData> getAirportFlowDataList(HomePageQuery homePageQuery) {
		String jc_name = homePageQuery.getAirPort();
		String jc_code = outPortMapper.getAirCodeByName(jc_name);
		homePageQuery.setAirPort(jc_code);
		List<HomePageData> ruselt = homePageMapper.getAirportFlowDataList(homePageQuery);
		homePageQuery.setAirPort(jc_name);
		return ruselt;
	}
	public List<HomePageData> getAirportFlow2DataList(HomePageQuery homePageQuery) {
		String jc_name = homePageQuery.getAirPort();
		String jc_code = outPortMapper.getAirCodeByName(jc_name);
		homePageQuery.setAirPort(jc_code);
		List<HomePageData> ruselt = homePageMapper.getAirportFlow2DataList(homePageQuery);
		return ruselt;
	}

	

	public List<HomePageData> getAirportOnLineDataList(HomePageQuery homePageQuery) {
		String airportCode = homePageQuery.getAirPort();
		//得到所有航班号和排名
//		homePageQuery.setCompanyId(UserContext.getCompanyId());
		List<HomePageData> ruselt = homePageMapper.getAirportOnLineDataList(homePageQuery);
		//对航线进行分类处理
		List<String> fltList = new ArrayList<String>();
		List<HomePageData> ruseltret = new ArrayList<HomePageData>();
		for (HomePageData homePageData : ruselt) {
			if(!fltList.contains(homePageData.getFlt_ret_cd())){
				fltList.add(homePageData.getFlt_ret_cd());
			}
		}
		List<String> fltListAll = new ArrayList<String>();
		for (String flt : fltList) {
			flt.replace(" ", "");
			boolean flag = false;
			if(flt.length()==9){
				String str1 = flt.substring(0, 3);
				String str2 = flt.substring(3, 6);
				String str3 = flt.substring(6, 9);
				String flttemp = str3 + str2 + str1;
				for (String flt2 : fltList) {
					if(flt2.equals(flttemp)){
						flag = true;
						if(!fltListAll.contains(flt)){
							fltListAll.add(flt2);
						}
					}
				}
				if(flag==false){
					if(!fltListAll.contains(flt)){
						fltListAll.add(flt);
					}
				}
			}else{
				String str1 = flt.substring(0, 3);
				String str2 = flt.substring(3, 6);
				String flttemp =  str2 + str1;
				for (String flt2 : fltList) {
					if(flt2.equals(flttemp)){
						flag = true;
						if(!fltListAll.contains(flt)){
							fltListAll.add(flt2);
						}
					}
				}
				if(flag==false){
					if(!fltListAll.contains(flt)){
						fltListAll.add(flt);
					}
				}
			}
		}
		List<String> fltListRet = new ArrayList<String>();
		for (String str : fltListAll) {
			if(str.length()>6){
				String str1 = str.substring(0, 3);
				String str2 = str.substring(3, 6);
				String str3 = str.substring(6, 9);
				if(str3.equals(airportCode)){
					str = str3 + str2 + str1;
				}
			}else{
				String str1 = str.substring(0, 3);
				String str2 = str.substring(3, 6);
				if(str2.equals(airportCode)){
					str =  str2 + str1;
				}
			}
			fltListRet.add(str);
		}
		//查询该条件下的所有航班号
//		List<HomePageData> fltNumList = homePageMapper.getSelectAirLineData(homePageQuery);
//		List<HomePageData> fltNumList = new ArrayList<HomePageData>();
		for (String string : fltListRet) {
			HomePageData hp = new HomePageData();
			hp.setAirport(string.substring(0,3));
			if(string.length()>6){
				hp.setArrAirport(string.substring(6,9));
				hp.setDptAirport(string.substring(3,6));
			}else{
				hp.setArrAirport(string.substring(3,6));
			}
			String fltNum="";
			if(string.length()==9){
				String str1 = string.substring(0, 3); 
				String str2 = string.substring(3, 6); 
				String str3 = string.substring(6, 9); 
				String str4 = string;
				String str5 = str3+str2+str1;
				boolean ff1 = false;
				boolean ff2 = false;
				for (HomePageData homePageData : ruselt) {
					String flt = homePageData.getFlt_ret_cd();
					String fltN = homePageData.getFlyNum();
					if(str4.equals(flt)||str5.equals(flt)){
						fltNum = fltNum + fltN + ",";
						String sstate = homePageData.getState();
						if("0".equals(sstate)){
							ff1 = true ;
						}
						if("1".equals(sstate)){
							ff2 = true ;
						}
					}
				}
				if(!TextUtil.isEmpty(fltNum)){
					fltNum = fltNum.substring(0,fltNum.length()-1);
				}
				if(ff1){
					hp.setState("0");
				}else if(ff2){
					hp.setState("1");
				}else{
					hp.setState("2");
				}
				hp.setFlyNum(fltNum);
			}else{
				String str1 = string.substring(0, 3); 
				String str2 = string.substring(3, 6); 
				String str4 = string;
				String str5 = str2+str1;
				boolean ff1 = false;
				boolean ff2 = false;
				for (HomePageData homePageData : ruselt) {
					String flt = homePageData.getFlt_ret_cd();
					String fltN = homePageData.getFlyNum();
					if(str4.equals(flt)||str5.equals(flt)){
						fltNum = fltNum + fltN + ",";
						String sstate = homePageData.getState();
						if("0".equals(sstate)){
							ff1 = true ;
						}
						if("1".equals(sstate)){
							ff2 = true ;
						}
					}
				}
				if(!TextUtil.isEmpty(fltNum)){
					fltNum = fltNum.substring(0,fltNum.length()-1);
				}
				hp.setFlyNum(fltNum);
				if(ff1){
					hp.setState("0");
				}else if(ff2){
					hp.setState("1");
				}else{
					hp.setState("2");
				}
			}
			ruseltret.add(hp);
		}
		//航班号对合并
		for (HomePageData homePageData : ruseltret) {
			String fltNums = homePageData.getFlyNum();
			String newFlyNum = "";
			if(!TextUtil.isEmpty(fltNums)){
				String [] fltNumarr = fltNums.split(",");
				String tempFlyNum = "";
				for (String str : fltNumarr) {
					if(str.length()==6&&TextUtil.isNum(str.substring(5, 6))){
						String firststr = str.substring(0, 2);
						int lasttwostr = Integer.parseInt(str.substring(2, 5));
						int laststr = Integer.parseInt(str.substring(5, 6));
						String pdStr = "";
						boolean flag = false;
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
						for (String str2 : fltNumarr) {
							if(pdStr.equals(str2)){
								flag = true;
								if(laststr%2==0){
									tempFlyNum = str2 + "/" + str;
								}else{
									tempFlyNum = str + "/" + str2;
								}
								if(!newFlyNum.contains(str2)){
									newFlyNum = newFlyNum + tempFlyNum+",";
								}
							}
						}
						if(flag==false){
							if(laststr%2==0){
								tempFlyNum = pdStr + "/" + str;
							}else{
								tempFlyNum = str + "/" + pdStr;
							}
							if(!newFlyNum.contains(str)){
								newFlyNum = newFlyNum + tempFlyNum+",";
							}
						}
					}
				}
			}
			if(newFlyNum.length()>0){
				newFlyNum = newFlyNum.substring(0, newFlyNum.length()-1);
			}
			String newFlyNum2 = "";
			String [] sttt = newFlyNum.split(",");
			for (String string : sttt) {
				if(!TextUtil.isEmpty(string)){
					String [] s = string.split("/");
					newFlyNum2 = newFlyNum2 +s[0]+"/"+s[1].substring(4, 6) + ",";
				}
			}
			if(newFlyNum2.length()>0){
				newFlyNum2 = newFlyNum2.substring(0, newFlyNum2.length()-1);
			}
			homePageData.setFlyNum(newFlyNum2);
		}
		return ruseltret;
	}

	public List<HomePageData> getAirportKzlDataList(HomePageQuery homePageQuery) {
		String jc_name = homePageQuery.getAirPort();
		String jc_code = outPortMapper.getAirCodeByName(jc_name);
		homePageQuery.setAirPort(jc_code);
		List<HomePageData> ruselt = new ArrayList<HomePageData>();
		List<HomePageData> ruseltNew = new ArrayList<HomePageData>();
		//1、取出所有包含机场的航班号-
		List<String> fltNumList = homePageMapper.getFltNum(homePageQuery);
		//2、取出条件下的所有数据
		List<Z_Airdata> zairdataList = homePageMapper.getZairdataList(homePageQuery);
		//3、循环数据找到航班对应的三条数据
		for (String fltnum : fltNumList) {
			double persons = 0;
			double sitee = 0;
			double kzl = 0;
			double personsd = 0;
			double personsl = 0;
			
			for (Z_Airdata z_Airdata : zairdataList) {
				String fltRteCd = z_Airdata.getFlt_Rte_Cd();
				
				if(fltnum.equals(z_Airdata.getFlt_Nbr())){
					if(fltRteCd.length()>7){
						//经停
						int index = fltRteCd.indexOf(jc_code);
						String dpt = fltRteCd.substring(0,3);
						String pst = fltRteCd.substring(3,6);
						String arr = fltRteCd.substring(6,9);
						//短段数据
						if(index==0){
							if(!TextUtil.isEmpty(jc_code)&&((jc_code.equals(z_Airdata.getDpt_AirPt_Cd())&&pst.equals(z_Airdata.getArrv_Airpt_Cd()))||(pst.equals(z_Airdata.getDpt_AirPt_Cd())&&arr.equals(z_Airdata.getArrv_Airpt_Cd())))){
								personsd = personsd + z_Airdata.getPgs_Per_Cls();
							}
						}else if(index==3){
							if(!TextUtil.isEmpty(jc_code)&&((dpt.equals(z_Airdata.getDpt_AirPt_Cd())&&jc_code.equals(z_Airdata.getArrv_Airpt_Cd()))||(jc_code.equals(z_Airdata.getDpt_AirPt_Cd())&&arr.equals(z_Airdata.getArrv_Airpt_Cd())))){
								personsd = personsd + z_Airdata.getPgs_Per_Cls();
							}
						}else{
							if(!TextUtil.isEmpty(jc_code)&&((dpt.equals(z_Airdata.getDpt_AirPt_Cd())&&pst.equals(z_Airdata.getArrv_Airpt_Cd()))||(pst.equals(z_Airdata.getDpt_AirPt_Cd())&&jc_code.equals(z_Airdata.getArrv_Airpt_Cd())))){
								personsd = personsd + z_Airdata.getPgs_Per_Cls();
							}
						}
						sitee = z_Airdata.getTal_Nbr_Set();
						//长段人数
						if(index==0){
							if(!TextUtil.isEmpty(jc_code)&&((jc_code.equals(z_Airdata.getDpt_AirPt_Cd())&&arr.equals(z_Airdata.getArrv_Airpt_Cd())))){
								personsl =  z_Airdata.getPgs_Per_Cls();
							}
						}else if(index==3){
							if(!TextUtil.isEmpty(jc_code)&&((dpt.equals(z_Airdata.getDpt_AirPt_Cd())&&arr.equals(z_Airdata.getArrv_Airpt_Cd())))){
								personsl =  z_Airdata.getPgs_Per_Cls();
							}
						}else{
							if(!TextUtil.isEmpty(jc_code)&&((dpt.equals(z_Airdata.getDpt_AirPt_Cd())&&jc_code.equals(z_Airdata.getArrv_Airpt_Cd())))){
								personsl = z_Airdata.getPgs_Per_Cls();
							}
						}
					}else{
						//直飞
						persons =  z_Airdata.getPgs_Per_Cls();
						sitee = z_Airdata.getTal_Nbr_Set();
					}
				}
			}
			if(persons>0){
				//直飞
				if(persons>0&&sitee>0){
					kzl = persons/sitee;
				}
			}else{
				kzl = (personsd+2*personsl)/(2*sitee);
			}
			HomePageData homePageData = new HomePageData();
			homePageData.setFlyNum(fltnum);
			homePageData.setKzl(kzl+"");
			ruselt.add(homePageData);
		}
		sort(ruselt,"kzl","asc");
		if(ruselt.size()>10){
			for (int i =0;i<10;i++) {
				ruseltNew.add(ruselt.get(i));
			}
		}else{
			ruseltNew = ruselt;
		}
		homePageQuery.setAirPort(jc_name);
		return ruseltNew;
	}

	public List<HomePageData> getAirportZglDataList(HomePageQuery homePageQuery) {
		String jc_name = homePageQuery.getAirPort();
		String jc_code = outPortMapper.getAirCodeByName(jc_name);
		homePageQuery.setAirPort(jc_code);
		List<HomePageData> ruselt = new ArrayList<HomePageData>();
		List<HomePageData> ruseltNew = new ArrayList<HomePageData>();
		//1、取出所有包含机场的航班号-
		List<String> fltNumList = homePageMapper.getFltNum(homePageQuery);
		//2、取出条件下的所有数据
		List<Z_Airdata> zairdataList = homePageMapper.getZairdataList(homePageQuery);
		//3、循环数据找到航班对应的三条数据
		for (String fltnum : fltNumList) {
			double duandistance = 0;
			double sitee = 0;
			double zys = 0;
			double zgl = 0;
			for (Z_Airdata z_Airdata : zairdataList) {
				String fltRteCd = z_Airdata.getFlt_Rte_Cd();
				
				if(fltnum.equals(z_Airdata.getFlt_Nbr())){
					if(fltRteCd.length()>7){
						//经停
						int index = fltRteCd.indexOf(jc_code);
						String dpt = fltRteCd.substring(0,3);
						String pst = fltRteCd.substring(3,6);
						String arr = fltRteCd.substring(6,9);
						//短段数据
						if(index==0){
							if(!TextUtil.isEmpty(jc_code)&&((jc_code.equals(z_Airdata.getDpt_AirPt_Cd())&&pst.equals(z_Airdata.getArrv_Airpt_Cd()))||(pst.equals(z_Airdata.getDpt_AirPt_Cd())&&arr.equals(z_Airdata.getArrv_Airpt_Cd())))){
								duandistance = duandistance + z_Airdata.getSailingDistance();
							}
						}else if(index==3){
							if(!TextUtil.isEmpty(jc_code)&&((dpt.equals(z_Airdata.getDpt_AirPt_Cd())&&jc_code.equals(z_Airdata.getArrv_Airpt_Cd()))||(jc_code.equals(z_Airdata.getDpt_AirPt_Cd())&&arr.equals(z_Airdata.getArrv_Airpt_Cd())))){
								duandistance = duandistance + z_Airdata.getSailingDistance();
							}
						}else{
							if(!TextUtil.isEmpty(jc_code)&&((dpt.equals(z_Airdata.getDpt_AirPt_Cd())&&pst.equals(z_Airdata.getArrv_Airpt_Cd()))||(pst.equals(z_Airdata.getDpt_AirPt_Cd())&&jc_code.equals(z_Airdata.getArrv_Airpt_Cd())))){
								duandistance = duandistance + z_Airdata.getSailingDistance();
							}
						}
						sitee = z_Airdata.getTal_Nbr_Set();
						zys = zys + z_Airdata.getTotalNumber();
					}else{
						//直飞
						duandistance =  z_Airdata.getSailingDistance();
						sitee = z_Airdata.getTal_Nbr_Set();
						zys = z_Airdata.getTotalNumber();
					}
				}
			}
			if(zys>0&&sitee>0&&duandistance>0){
				zgl = zys/sitee/duandistance;
			}
			HomePageData homePageData = new HomePageData();
			homePageData.setFlyNum(fltnum);
			homePageData.setZgl(zgl+"");
			ruselt.add(homePageData);
		}
		//排序  取前十
		sort(ruselt,"zgl","asc");
		if(ruselt.size()>10){
			for (int i =0;i<10;i++) {
				ruseltNew.add(ruselt.get(i));
			}
		}else{
			ruseltNew = ruselt;
		}
		homePageQuery.setAirPort(jc_name);
		return ruseltNew;
	}

	public List<HomePageData> getAirportZsrDataList(HomePageQuery homePageQuery) {
		String jc_name = homePageQuery.getAirPort();
		String jc_code = outPortMapper.getAirCodeByName(jc_name);
		homePageQuery.setAirPort(jc_code);
		List<HomePageData> ruselt = homePageMapper.getAirportZsrDataList(homePageQuery);
		homePageQuery.setAirPort(jc_name);
		return ruselt;
	}

	public String getNewDate(HomePageQuery homePageQuery) {
//		String jc_name = homePageQuery.getAirPort();
//		String regex = "^[A-Za-z]{3}$";
//		if(!jc_name.matches(regex)){
//			String jc_code = outPortMapper.getAirCodeByName(jc_name);
//			homePageQuery.setAirPort(jc_code);
//		}
		String ruselt = homePageMapper.getNewDate(homePageQuery);
		return ruselt;
	}
	

	public List<CityCoordinate> getCityCoordinateList() {
		return homePageMapper.getCityCoordinateList();
	}

	/* (non-Javadoc)
	 * @see org.ldd.ssm.crm.service.HomePageService#getSelectAirLineData(org.ldd.ssm.crm.query.HomePageQuery)
	 */
	@Override
	public List<HomePageData> getSelectAirLineData(HomePageQuery homePageQuery) {
		String jc_name = homePageQuery.getAirPort();
		String jc_code = outPortMapper.getAirCodeByName(jc_name);
		String arrjc_name = homePageQuery.getArrAirport();
		String arrjc_code = outPortMapper.getAirCodeByName(arrjc_name);
		
		homePageQuery.setAirPort(jc_code);
		homePageQuery.setArrAirport(arrjc_code);
		List<HomePageData> homePageDataList = homePageMapper.getSelectAirLineData(homePageQuery);
		List<String> airLineList = new ArrayList<String>();
		for (HomePageData homePageData : homePageDataList) {
			String flt = homePageData.getFlt_ret_cd();
			if(!airLineList.contains(flt)){
				airLineList.add(flt);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ldd.ssm.crm.service.HomePageService#getAirportInfoMap()
	 */
	@Override
	public Map<String, AirportData> getAirportInfoMap() {
		Map<String, AirportData> airportDataMap = new HashMap<String, AirportData>();
		List<AirportData> airportDataList =  outPortMapper.getAirPortDataNew();
		for (AirportData airportData : airportDataList) {
			if(airportDataMap.containsKey(airportData.getCtyChaNam())){
				AirportData airportDatatemp = new AirportData();
				airportDatatemp = (AirportData) airportDataMap.get(airportData.getCtyChaNam()).clone();
				airportDatatemp.setIata(airportDatatemp.getIata()+"/"+airportData.getIata());
				airportDataMap.put(airportData.getCtyChaNam(), airportDatatemp);
			}else{
				airportDataMap.put(airportData.getCtyChaNam(), airportData);
			}
		}
		for (AirportData airportData : airportDataList) {
			airportDataMap.put(airportData.getIata(), airportData);
		}
		for (AirportData airportData : airportDataList) {
			airportDataMap.put(airportData.getAptChaNam(), airportData);
		}
		return airportDataMap;
	}

	@Override
	public Map<String, Object> getAirPortKPIData(HomePageQuery homePageQuery) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#.##");
		DecimalFormat df2 = new DecimalFormat("#,###.##");
		String jc_code = outPortMapper.getAirCodeByName(homePageQuery.getAirPort());
		String day = homePageQuery.getDate();
		String endTime = day;
		String startTime = TextUtil.addDateDay(endTime, -9);
		homePageQuery.setStartTime(startTime);
		homePageQuery.setEndTime(endTime);
		homePageQuery.setAirPort(jc_code);
		//获得前10天的所有数据
		List <Z_Airdata> allList =  homePageMapper.getKPIZAirdataList(homePageQuery);
		List <Z_Airdata> todayList = new ArrayList<Z_Airdata>();
		//这一天的所有准点率数据
		List<Yesterday_ZD> yesterdayZDList = airlineDynameicMapper.getGetAirline_dynameic_list_day(day,day,jc_code);
		for (Z_Airdata z_Airdata : allList) {
			String todayDay = sdf.format(z_Airdata.getLcl_Dpt_Day());
			if(todayDay.equals(day)){
				todayList.add(z_Airdata);
			}
		}
		//计算航班排名
		List<String> flyNbrList = new ArrayList<String>();
		for (Z_Airdata z_Airdata : todayList) {
			String flyNbr = z_Airdata.getFlt_Nbr();
			if(!flyNbrList.contains(flyNbr)){
				flyNbrList.add(flyNbr);
			}
		}
		List<HomePageData> homePageDataList = new ArrayList<HomePageData>();
		List<HomePageData> rethomePageDataList0 = new ArrayList<HomePageData>();
		List<HomePageData> rethomePageDataList1 = new ArrayList<HomePageData>();
		List<HomePageData> rethomePageDataList2 = new ArrayList<HomePageData>();
		List<HomePageData> rethomePageDataList22 = new ArrayList<HomePageData>();
		List<HomePageData> rethomePageDataList3 = new ArrayList<HomePageData>();
		List<HomePageData> rethomePageDataList4 = new ArrayList<HomePageData>();
		List<HomePageData> rethomePageDataList5 = new ArrayList<HomePageData>();
		List<HomePageData> rethomePageDataList55 = new ArrayList<HomePageData>();
		for (String str : flyNbrList) {
			int index = 0;
			double zsr = 0.0;
			double kzl = 0.0;
			int zrs = 0;
			for (Z_Airdata z_Airdata : todayList) {
				String flyNbr = z_Airdata.getFlt_Nbr();
				if(str.equals(flyNbr)){
					index ++;
					zsr = zsr + z_Airdata.getTotalNumber();
					if(z_Airdata.getTal_Nbr_Set()>0){
						kzl = kzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
					}
					zrs = zrs + z_Airdata.getPgs_Per_Cls();
				}
			}
			if(index>0){
				kzl = kzl/index;
			}else{
				kzl = 0.0;
			}
			int ff = 0;
			Double zdl = 0.0;
			for (Yesterday_ZD yesterday_ZD : yesterdayZDList) {
				String flyNbr = yesterday_ZD.getFlt_nbr();
				if(str.equals(flyNbr)){
					ff ++;
					zdl = zdl + Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
				}
			}
			HomePageData homePageData = new HomePageData();
			homePageData.setFlyNum(str);
			homePageData.setPersons(zrs+"");
			homePageData.setKzl(df.format(kzl));
			homePageData.setZsr(zsr+"");
			if(ff>0){
				zdl = zdl /(double)ff;
			}
			homePageData.setZdl(df.format(zdl));
			homePageDataList.add(homePageData);
		}
		Map<String, Object> retmap = new HashMap<String, Object>();
		homePageDataList = sortNew(homePageDataList, "zsr", "DESC"); 
		int i = 0;
		for (HomePageData homePageData : homePageDataList) {
			if(i<10){
				rethomePageDataList0.add(homePageData);
			}
			i++;
		}
		retmap.put("topten_zsr", rethomePageDataList0);
		
		homePageDataList = sortNew(homePageDataList, "persons", "DESC"); 
		int j = 0;
		for (HomePageData homePageData : homePageDataList) {
			if(j<10){
				rethomePageDataList1.add(homePageData);
			}
			j++;
		}
		retmap.put("topten_kll", rethomePageDataList1);
		
		homePageDataList = sortNew(homePageDataList, "kzl", "DESC"); 
		int k = 0;
		for (HomePageData homePageData : homePageDataList) {
			if(k<10){
				rethomePageDataList2.add(homePageData);
			}
			k++;
		}
		retmap.put("topten_kzl", rethomePageDataList2);
		
		homePageDataList = sortNew(homePageDataList, "zdl", "DESC"); 
		int l = 0;
		for (HomePageData homePageData : homePageDataList) {
			if(l<10){
				rethomePageDataList22.add(homePageData);
			}
			l++;
		}
		retmap.put("topten_zdl", rethomePageDataList22);
		
		homePageDataList = sortNew(homePageDataList, "zsr", "ASC"); 
		int m = 0;
		for (HomePageData homePageData : homePageDataList) {
			if(m<10){
				rethomePageDataList3.add(homePageData);
			}
			m++;
		}
		retmap.put("afterten_zsr", rethomePageDataList3);
		
		homePageDataList = sortNew(homePageDataList, "persons", "ASC"); 
		int n = 0;
		for (HomePageData homePageData : homePageDataList) {
			if(n<10){
				rethomePageDataList4.add(homePageData);
			}
			n++;
		}
		retmap.put("afterten_kll", rethomePageDataList4);
		
		homePageDataList = sortNew(homePageDataList, "kzl", "ASC"); 
		int q = 0;
		for (HomePageData homePageData : homePageDataList) {
			if(q<10){
				rethomePageDataList5.add(homePageData);
			}
			q++;
		}
		retmap.put("afterten_kzl", rethomePageDataList5);
		
		homePageDataList = sortNew(homePageDataList, "zdl", "ASC"); 
		int r = 0;
		for (HomePageData homePageData : homePageDataList) {
			if(r<10){
				rethomePageDataList55.add(homePageData);
			}
			r++;
		}
		retmap.put("afterten_zdl", rethomePageDataList55);
		int zkl = 0;
		double zsr = 0.0;
		int zsk = 0;
		int ztd = 0;
		for (Z_Airdata z_Airdata : allList) {
			zsr = zsr + z_Airdata.getTotalNumber();
			zkl = zkl + z_Airdata.getPgs_Per_Cls();
			zsk = zsk + (z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr());
			ztd = ztd + z_Airdata.getGrp_Nbr();
		}
		retmap.put("zkl", zkl);
		retmap.put("zsr", df2.format(zsr));
		retmap.put("zsk", zsk);
		retmap.put("ztd", ztd);
		//进10天的有数据的天数
		List<String> dayList = new ArrayList<String>();
		for (Z_Airdata z_Airdata : allList) {
			String daytemp = sdf.format(z_Airdata.getLcl_Dpt_Day());
			if(!dayList.contains(daytemp)){
				dayList.add(daytemp);
			}
		}
		List<HomePageData> HomePageDataListzs = new ArrayList<HomePageData>();
		for (String dayy : dayList) {
			int zkltemp = 0;
			double zsrtemp = 0.0;
			for (Z_Airdata z_Airdata : allList) {
				String daytemp = sdf.format(z_Airdata.getLcl_Dpt_Day());
				if(dayy.equals(daytemp)){
					zsrtemp = zsrtemp + z_Airdata.getTotalNumber()/1000.0;
					zkltemp = zkltemp + z_Airdata.getPgs_Per_Cls();
				}
			}
			HomePageData homePageData = new HomePageData();
			homePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
			homePageData.setPersons(zkltemp+"");
			homePageData.setZsr(df.format(zsrtemp));
			HomePageDataListzs.add(homePageData);
		}
		retmap.put("zsData", HomePageDataListzs);
		return retmap;
	}  
	
	public List<Map<String, Object>> getAirPortKPIDataNew(HomePageQuery homePageQuery) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
//			String jc_code = outPortMapper.getAirCodeByName(homePageQuery.getAirPort());
			String jc_code=homePageQuery.getAirPort();
			String day = homePageQuery.getDate();
			String endTime = day;
			String startTime = TextUtil.addDateDay(endTime, -29);
			homePageQuery.setStartTime(startTime);
			homePageQuery.setEndTime(endTime);
//			homePageQuery.setAirPort(jc_code);
			//获得前30天的所有数据
			List <Z_Airdata> allList =  homePageMapper.getKPIZAirdataList(homePageQuery);
			//将查询时间根据时间分割成周
			List<Yesterday_ZD> yesterdayZDList = new ArrayList<Yesterday_ZD>();
			SalesReportQuery salesReportQuery = new SalesReportQuery();
			salesReportQuery.setDpt_AirPt_Cd(jc_code);
			salesReportQuery.setStartTime(startTime);
			salesReportQuery.setEndTime(endTime);
			List <Z_Airdata> dataList = salesReportServiceMapper.getSalesReportsByDate(salesReportQuery); 
//			Map<String,List<String>> timeStr = getListStr(startTime,endTime);
//			for (Entry<String, List<String>> timeStrMap : timeStr.entrySet()) {
//				List<String> value = timeStrMap.getValue();
//				yesterdayZDList.addAll(airlineDynameicMapper.getGetAirline_dynameic_list_day(value.get(0),value.get(1),jc_code));
//			}
//			yesterdayZDList.addAll(airlineDynameicMapper.getGetAirline_dynameic_list_day(startTime,endTime,jc_code));
			//调用外部接口
			Gson gson = new Gson();
			List <HomepageZdlDomin> HomepageZdlDominhzList1 = new ArrayList<HomepageZdlDomin>();
			List <HomepageZdlDomin> HomepageZdlDominhzList2 = new ArrayList<HomepageZdlDomin>();
			List <HomepageZdlDomin> HomepageZdlDominjgList1 = new ArrayList<HomepageZdlDomin>();
			List <HomepageZdlDomin> HomepageZdlDominjgList2 = new ArrayList<HomepageZdlDomin>();
			List <HomepageZdlDomin> HomepageZdlDomincgList1 = new ArrayList<HomepageZdlDomin>();
			List <HomepageZdlDomin> HomepageZdlDomincgList2 = new ArrayList<HomepageZdlDomin>();
			try {
				Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_rate_by_code/").header("Accept", "*/*").ignoreContentType(true);
				if(!TextUtil.isEmpty(jc_code)){
					connect.data("airport_code", jc_code);
				}
				Document doc = connect.timeout(100000).post();
				JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
				//汇总前十
				HomepageZdlDominhzList1 = gson.fromJson(returnData.get("result").getAsJsonObject().get("all").getAsJsonObject().get("desc").getAsJsonArray(), new TypeToken<List<HomepageZdlDomin>>(){}.getType());
				//汇总后十
				HomepageZdlDominhzList2 = gson.fromJson(returnData.get("result").getAsJsonObject().get("all").getAsJsonObject().get("asc").getAsJsonArray(), new TypeToken<List<HomepageZdlDomin>>(){}.getType());
				//进港前十
				HomepageZdlDominjgList1 = gson.fromJson(returnData.get("result").getAsJsonObject().get("arr").getAsJsonObject().get("desc").getAsJsonArray(), new TypeToken<List<HomepageZdlDomin>>(){}.getType());
				//进港后十
				HomepageZdlDominjgList2 = gson.fromJson(returnData.get("result").getAsJsonObject().get("arr").getAsJsonObject().get("asc").getAsJsonArray(), new TypeToken<List<HomepageZdlDomin>>(){}.getType());
				//出港前十
				HomepageZdlDomincgList1 = gson.fromJson(returnData.get("result").getAsJsonObject().get("dep").getAsJsonObject().get("desc").getAsJsonArray(), new TypeToken<List<HomepageZdlDomin>>(){}.getType());
				//出港后十
				HomepageZdlDomincgList2 = gson.fromJson(returnData.get("result").getAsJsonObject().get("dep").getAsJsonObject().get("asc").getAsJsonArray(), new TypeToken<List<HomepageZdlDomin>>(){}.getType());
			} catch (IOException e) {
				e.printStackTrace();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			for (Yesterday_ZD yesterday_ZD : yesterdayzdListtemp) {
//				for (Z_Airdata z_Airdata : dataList) {
//					String lcld = sdf.format(z_Airdata.getLcl_Dpt_Day());
//					if(z_Airdata.getFlt_Nbr().equals(yesterday_ZD.getFlt_nbr())&&lcld.equals(yesterday_ZD.getAir_date())&&z_Airdata.getDpt_AirPt_Cd().equals(yesterday_ZD.getDpt_AirPt_Cd_itia())&&z_Airdata.getArrv_Airpt_Cd().equals(yesterday_ZD.getArrv_Airpt_Cd_itia())){
//						Yesterday_ZD yesterday_ZDTemp = new Yesterday_ZD();
//						yesterday_ZDTemp.setAir_date(lcld);
//						yesterday_ZDTemp.setDpt_AirPt_Cd(z_Airdata.getDpt_AirPt_Cd());
//						yesterday_ZDTemp.setArrv_Airpt_Cd(z_Airdata.getArrv_Airpt_Cd());
//						yesterday_ZDTemp.setFlt_nbr(z_Airdata.getFlt_Nbr());
//						yesterday_ZDTemp.setRemark(z_Airdata.getFlt_Rte_Cd());
//						yesterday_ZDTemp.setZd_rate(yesterday_ZD.getZd_rate());
//						yesterdayZDList.add(yesterday_ZDTemp);
//					}
//				}
//			}
			//最近30天的所有准点率数据
//			for (int i = 0; i < yesterdayZDList.size(); i++) {
//				Yesterday_ZD yesterday_ZD = yesterdayZDList.get(i);
//				String zd_rate = yesterday_ZD.getZd_rate().replaceAll("[^0-9]", "");
//				if(TextUtil.isEmpty(zd_rate)){
//					yesterdayZDList.remove(i);
//					i--;
//				}
//			}
			DecimalFormat df = new DecimalFormat("#.##");
			List<HomePageData> homePageDataList1 = new ArrayList<HomePageData>();
			int i1 = 0;
			for (HomepageZdlDomin HomepageZdlDomin : HomepageZdlDominhzList1) {
				if(i1<10){
					HomePageData homePageData = new HomePageData();
					homePageData.setFlyNum(HomepageZdlDomin.getFlt_nbr());
					homePageData.setZdl(df.format(Double.parseDouble(HomepageZdlDomin.getRate().replace("%", ""))/100));
					homePageDataList1.add(homePageData);
				}
				i1++;
			}
			List<HomePageData> homePageDataList11 = new ArrayList<HomePageData>();
			int i2 = 0;
			for (HomepageZdlDomin HomepageZdlDomin : HomepageZdlDominhzList2) {
				if(i2<10){
					HomePageData homePageData = new HomePageData();
					homePageData.setFlyNum(HomepageZdlDomin.getFlt_nbr());
					homePageData.setZdl(df.format(Double.parseDouble(HomepageZdlDomin.getRate().replace("%", ""))/100));
					homePageDataList11.add(homePageData);
				}
				i2++;
			}
			List<HomePageData> homePageDataList2 = new ArrayList<HomePageData>();
			int i3 = 0;
			for (HomepageZdlDomin HomepageZdlDomin : HomepageZdlDominjgList1) {
				if(i3<10){
					HomePageData homePageData = new HomePageData();
					homePageData.setFlyNum(HomepageZdlDomin.getFlt_nbr());
					homePageData.setZdl(df.format(Double.parseDouble(HomepageZdlDomin.getRate().replace("%", ""))/100));
					homePageDataList2.add(homePageData);
				}
				i3++;
			}
			List<HomePageData> homePageDataList22 = new ArrayList<HomePageData>();
			int i4 = 0;
			for (HomepageZdlDomin HomepageZdlDomin : HomepageZdlDominjgList2) {
				if(i4<10){
					HomePageData homePageData = new HomePageData();
					homePageData.setFlyNum(HomepageZdlDomin.getFlt_nbr());
					homePageData.setZdl(df.format(Double.parseDouble(HomepageZdlDomin.getRate().replace("%", ""))/100));
					homePageDataList22.add(homePageData);
				}
				i4++;
			}
			List<HomePageData> homePageDataList3 = new ArrayList<HomePageData>();
			int i5 = 0;
			for (HomepageZdlDomin HomepageZdlDomin : HomepageZdlDomincgList1) {
				if(i5<10){
					HomePageData homePageData = new HomePageData();
					homePageData.setFlyNum(HomepageZdlDomin.getFlt_nbr());
					homePageData.setZdl(df.format(Double.parseDouble(HomepageZdlDomin.getRate().replace("%", ""))/100));
					homePageDataList3.add(homePageData);
				}
				i5++;
			}
			List<HomePageData> homePageDataList33 = new ArrayList<HomePageData>();
			int i6 = 0;
			for (HomepageZdlDomin HomepageZdlDomin : HomepageZdlDomincgList2) {
				if(i6<10){
					HomePageData homePageData = new HomePageData();
					homePageData.setFlyNum(HomepageZdlDomin.getFlt_nbr());
					homePageData.setZdl(df.format(Double.parseDouble(HomepageZdlDomin.getRate().replace("%", ""))/100));
					homePageDataList33.add(homePageData);
				}
				i6++;
			}
			//获取最近第一天航班排行数据
			Map<String,Object> oneData = getAirportData(homePageQuery,allList,  endTime, 0);
			oneData.put("afterten_zdl", homePageDataList11);
			oneData.put("topten_zdl", homePageDataList1);
			oneData.put("jafterten_zdl", homePageDataList22);
			oneData.put("jtopten_zdl", homePageDataList2);
			oneData.put("cafterten_zdl", homePageDataList33);
			oneData.put("ctopten_zdl", homePageDataList3);
			//获取最近一周航班排行数据
			Map<String,Object> sevenData = getAirportData(homePageQuery,allList,  endTime, -6);
			sevenData.put("afterten_zdl", homePageDataList11);
			sevenData.put("topten_zdl", homePageDataList1);
			sevenData.put("jafterten_zdl", homePageDataList22);
			sevenData.put("jtopten_zdl", homePageDataList2);
			sevenData.put("cafterten_zdl", homePageDataList33);
			sevenData.put("ctopten_zdl", homePageDataList3);
			//获取最近一个月航班排行数据
			Map<String,Object> thirtyData = getAirportData(homePageQuery,allList,  endTime, -29);
			thirtyData.put("afterten_zdl", homePageDataList11);
			thirtyData.put("topten_zdl", homePageDataList1);
			thirtyData.put("jafterten_zdl", homePageDataList22);
			thirtyData.put("jtopten_zdl", homePageDataList2);
			thirtyData.put("cafterten_zdl", homePageDataList33);
			thirtyData.put("ctopten_zdl", homePageDataList3);
			list.add(oneData);
			list.add(sevenData);
			list.add(thirtyData);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return list;
		}
		return list;
	}
	
	private Map<String,List<String>> getListStr(String startTime, String endTime) {
		Map<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			int holidayTime = holidayTime(startTime, endTime);
			for (int i = 0; i < holidayTime; i++) {
				List<String> list = new ArrayList<String>();
				calendar.setTime(simpleDateFormat.parse(startTime));
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				startTime = simpleDateFormat.format(calendar.getTime());
				list.add(startTime);
				i++;
				calendar.setTime(simpleDateFormat.parse(startTime));
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				startTime = simpleDateFormat.format(calendar.getTime());
				list.add(startTime);
				map.put(i+"", list);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return map;
	}

	public int holidayTime(String startTime,String endTime) throws ParseException {
		// 穿件简单时间格式独享，设置时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 取得现在的时间，首先使用Calendar的getInstance()取得一个Calendar的实例
		Calendar cl = Calendar.getInstance();
		// 将假期开始时间设置给Calendar对象
		cl.setTime(sdf.parse(startTime));
		// 将时间转换成毫秒
		long begin = cl.getTimeInMillis();
		// 将假期结束时间设置给Calendar对象
		cl.setTime(sdf.parse(endTime));
		// 将时间转换成毫秒
		long end = cl.getTimeInMillis();
		// 计算两个时间差（天）
		long days = (end - begin) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(days));

	}

	public Map<String,Object> getAirportData(HomePageQuery homePageQuery,List<Z_Airdata> allList,String day,int days){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#.##");
		DecimalFormat df2 = new DecimalFormat("#,###.##");
		List <Z_Airdata> todayList = new ArrayList<Z_Airdata>();
		Map<String, Object> retmap = new HashMap<String, Object>();
		String airport = homePageQuery.getAirPort();
		//最近一天的所有准点率数据
		try {
			Long endTime = sdf.parse(day).getTime();
			Long startTime = sdf.parse(TextUtil.addDateDay(day, days)).getTime();
			for (Z_Airdata z_Airdata : allList) {
				Long todayDay = z_Airdata.getLcl_Dpt_Day().getTime();
				if(todayDay>=startTime&&todayDay<=endTime){
					todayList.add(z_Airdata);
				}
			}
			//计算航班排名
			List<String> flyNbrList = new ArrayList<String>();
			for (Z_Airdata z_Airdata : todayList) {
				String flyNbr = z_Airdata.getFlt_Nbr();
				if(!flyNbrList.contains(flyNbr)){
					flyNbrList.add(flyNbr);
				}
			}
			List<HomePageData> homePageDataList = new ArrayList<HomePageData>();
			List<HomePageData> rethomePageDataList0 = new ArrayList<HomePageData>();
			List<HomePageData> rethomePageDataList1 = new ArrayList<HomePageData>();
			List<HomePageData> rethomePageDataList2 = new ArrayList<HomePageData>();
			List<HomePageData> rethomePageDataList22 = new ArrayList<HomePageData>();
			List<HomePageData> rethomePageDataList3 = new ArrayList<HomePageData>();
			List<HomePageData> rethomePageDataList4 = new ArrayList<HomePageData>();
			List<HomePageData> rethomePageDataList5 = new ArrayList<HomePageData>();
			List<HomePageData> rethomePageDataList55 = new ArrayList<HomePageData>();
			List<HomePageData> chomePageDataList = new ArrayList<HomePageData>();
			List<HomePageData> crethomePageDataList0 = new ArrayList<HomePageData>();
			List<HomePageData> crethomePageDataList1 = new ArrayList<HomePageData>();
			List<HomePageData> crethomePageDataList2 = new ArrayList<HomePageData>();
			List<HomePageData> crethomePageDataList22 = new ArrayList<HomePageData>();
			List<HomePageData> crethomePageDataList3 = new ArrayList<HomePageData>();
			List<HomePageData> crethomePageDataList4 = new ArrayList<HomePageData>();
			List<HomePageData> crethomePageDataList5 = new ArrayList<HomePageData>();
			List<HomePageData> crethomePageDataList55 = new ArrayList<HomePageData>();
			List<HomePageData> jhomePageDataList = new ArrayList<HomePageData>();
			List<HomePageData> jrethomePageDataList0 = new ArrayList<HomePageData>();
			List<HomePageData> jrethomePageDataList1 = new ArrayList<HomePageData>();
			List<HomePageData> jrethomePageDataList2 = new ArrayList<HomePageData>();
			List<HomePageData> jrethomePageDataList22 = new ArrayList<HomePageData>();
			List<HomePageData> jrethomePageDataList3 = new ArrayList<HomePageData>();
			List<HomePageData> jrethomePageDataList4 = new ArrayList<HomePageData>();
			List<HomePageData> jrethomePageDataList5 = new ArrayList<HomePageData>();
			List<HomePageData> jrethomePageDataList55 = new ArrayList<HomePageData>();
			for (String str : flyNbrList) {
				int indexkzl=0;
				double zsr = 0.0;
				double kzl = 0.0;
				int zrs = 0;
				int cindexkzl = 0;
				double czsr = 0.0;
				double ckzl = 0.0;
				int czrs = 0;
				int jindexkzl = 0;
				double jzsr = 0.0;
				double jkzl = 0.0;
				int jzrs = 0;
				int hzs=0;
				int jtqdcds_kzl=0;
				int jtqddds_kzl=0;
				int jtzdcds_kzl=0;
				int jtzddds_kzl=0;
				int jtcgs_kzl=0;
				int jtjgs_kzl=0;
				int zfds_kzl=0;
				int zfcgds_kzl=0;
				int zfjgds_kzl=0;
				double hzkzl=0.0;
				double jtqdcdkzl=0.0;
				double jtqdddkzl=0.0;
				double jtzdcdkzl=0.0;
				double jtzdddkzl=0.0;
				double jtcgkzl=0.0;
				double jtjgkzl=0.0;
				double zfdskzl=0.0;
				double zfcgdskzl=0.0;
				double zfjgdskzl=0.0;
				boolean flag = false;
				for (int i=0;i<todayList.size();i++) {
					Z_Airdata z_Airdata = todayList.get(i);
					String flyNbr = z_Airdata.getFlt_Nbr();
					if(str.equals(flyNbr)){
						zsr = zsr + z_Airdata.getTotalNumber();
						zrs = zrs + z_Airdata.getPgs_Per_Cls();
						if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港数据
							jzsr = jzsr + z_Airdata.getTotalNumber();
							jzrs = jzrs + z_Airdata.getPgs_Per_Cls();
						}
						if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港数据
							czsr = czsr + z_Airdata.getTotalNumber();
							czrs = czrs + z_Airdata.getPgs_Per_Cls();
						}
						flag=true;
						String flt = z_Airdata.getFlt_Rte_Cd();
						if(i==0){
							if(flt.length()==9){//经停
								hzs++;
								if(z_Airdata.getTal_Nbr_Set()>0){
									hzkzl = hzkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								if(flt.startsWith(airport)){//起点
									//出港
									if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
										if(z_Airdata.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
											jtqdcds_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												jtqdcdkzl = jtqdcdkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}else{
											jtqddds_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												jtqdddkzl = jtqdddkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}
									}
								}else if(flt.endsWith(airport)){//终点
									//进港
									if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){
										if(z_Airdata.getDpt_AirPt_Cd().equals(flt.substring(0,3))){
											jtzdcds_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												jtzdcdkzl = jtzdcdkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}else{
											jtzddds_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												jtzdddkzl = jtzdddkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}
									}
								}else{//经停
									//出港
									if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
										jtcgs_kzl++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											jtcgkzl = jtcgkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
									}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
										jtjgs_kzl++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											jtjgkzl = jtjgkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
									}
								}
							}else if(flt.length()==6){//直飞
								//汇总
								zfds_kzl++;
								if(z_Airdata.getTal_Nbr_Set()>0){
									zfdskzl = zfdskzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
								}
								if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
									zfcgds_kzl++;
									if(z_Airdata.getTal_Nbr_Set()>0){
										zfcgdskzl = zfcgdskzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
									}
								}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
									zfjgds_kzl++;
									if(z_Airdata.getTal_Nbr_Set()>0){
										zfjgdskzl = zfjgdskzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
									}
								}
							}
						}else if(i!=todayList.size()-1){
							Z_Airdata prev = todayList.get(i-1);
							String prevflt = prev.getFlt_Rte_Cd();
							String date = sdf.format(z_Airdata.getLcl_Dpt_Day());
							String prevdate = sdf.format(prev.getLcl_Dpt_Day());
							if(flt.length()==9){//经停
								if(prevdate.equals(date)){//日期相同
									hzs++;
									if(z_Airdata.getTal_Nbr_Set()>0){
										hzkzl = hzkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
									}
									if(flt.startsWith(airport)){//起点
										//出港
										if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
											if(z_Airdata.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
												jtqdcds_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtqdcdkzl = jtqdcdkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}else{
												jtqddds_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtqdddkzl = jtqdddkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}
										}
									}else if(flt.endsWith(airport)){//终点
										//进港
										if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){
											if(z_Airdata.getDpt_AirPt_Cd().equals(flt.substring(0,3))){
												jtzdcds_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtzdcdkzl = jtzdcdkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}else{
												jtzddds_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtzdddkzl = jtzdddkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}
										}
									}else{//经停
										//出港
										if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
											jtcgs_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												jtcgkzl = jtcgkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
											jtjgs_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												jtjgkzl = jtjgkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}
									}
								}else{
									if(hzs>0){
										//编辑旧数据,重置变量
										indexkzl++;
										kzl+=hzkzl/hzs;
										hzs=0;
										hzkzl=0.0;
										if(prevflt.startsWith(airport)){//起点
											//出港
											if(jtqdcds_kzl==0&&jtqddds_kzl>0){
												ckzl+=jtqdddkzl/jtqddds_kzl;
												cindexkzl++;
											}else if(jtqddds_kzl==0&&jtqdcds_kzl>0){
												ckzl+=jtqdcdkzl/jtqdcds_kzl;
												cindexkzl++;
											}else if(jtqddds_kzl>0&&jtqdcds_kzl>0){
												ckzl+=(jtqdcdkzl/jtqdcds_kzl+jtqdddkzl/jtqddds_kzl)/2;
												cindexkzl++;
											}
											jtqdcds_kzl=0;
											jtqddds_kzl=0;
											jtqdcdkzl=0.0;
											jtqdddkzl=0.0;
										}else if(prevflt.endsWith(airport)){//终点
											//进港
											if(jtzdcds_kzl==0&&jtzddds_kzl>0){
												jkzl+=jtzdddkzl/jtzddds_kzl;
												jindexkzl++;
											}else if(jtzddds_kzl==0&&jtzdcds_kzl>0){
												jkzl+=jtzdcdkzl/jtzdcds_kzl;
												jindexkzl++;
											}else if(jtzdcds_kzl>0&&jtzddds_kzl>0){
												jkzl+=(jtzdcdkzl/jtzdcds_kzl+jtzdddkzl/jtzddds_kzl)/2;
												jindexkzl++;
											}
											jtzdcds_kzl=0;
											jtzddds_kzl=0;
											jtzdcdkzl=0.0;
											jtzdddkzl=0.0;
										}else{//经停
											//出港
											if(jtcgs_kzl>0){
												ckzl+=jtcgkzl/jtcgs_kzl;
												cindexkzl++;
												jtcgkzl=0.0;
												jtcgs_kzl=0;
											}
											//进港
											if(jtjgs_kzl>0){
												jkzl+=jtjgkzl/jtjgs_kzl;
												jindexkzl++;
												jtjgkzl=0.0;
												jtjgs_kzl=0;
											}
										}
										//新增数据
										hzs++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											hzkzl = hzkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
										if(flt.startsWith(airport)){//起点
											//出港
											if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
												if(z_Airdata.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
													jtqdcds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtqdcdkzl = jtqdcdkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}else{
													jtqddds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtqdddkzl = jtqdddkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}
											}
										}else if(flt.endsWith(airport)){//终点
											//进港
											if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){
												if(z_Airdata.getDpt_AirPt_Cd().equals(flt.substring(0,3))){
													jtzdcds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtzdcdkzl = jtzdcdkzl + z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}else{
													jtzddds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtzdddkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}
											}
										}else{//经停
											//出港
											if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
												jtcgs_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtcgkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
												jtjgs_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtjgkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}
										}
									}else{
										//新增数据
										hzs++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											hzkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
										if(flt.startsWith(airport)){//起点
											//出港
											if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
												if(z_Airdata.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
													jtqdcds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtqdcdkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}else{
													jtqddds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtqdddkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}
											}
										}else if(flt.endsWith(airport)){//终点
											//进港
											if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){
												if(z_Airdata.getDpt_AirPt_Cd().equals(flt.substring(0,3))){
													jtzdcds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtzdcdkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}else{
													jtzddds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtzdddkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}
											}
										}else{//经停
											//出港
											if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
												jtcgs_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtcgkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
												jtjgs_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtjgkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}
										}
									}
									
								}
							}else if(flt.length()==6){//直飞
								if(prevdate.equals(date)){//日期相同
									//汇总
									zfds_kzl++;
									if(z_Airdata.getTal_Nbr_Set()>0){
										zfdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
									}
									if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
										zfcgds_kzl++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											zfcgdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
									}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
										zfjgds_kzl++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											zfjgdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
									}
								}else{
									if(zfds_kzl>0){
										//编辑原有数据
										kzl+=zfdskzl/zfds_kzl;
										indexkzl++;
										zfds_kzl=0;
										zfdskzl=0.0;
										if(zfcgds_kzl>0){
											ckzl+=zfcgdskzl/zfcgds_kzl;
											cindexkzl++;
											zfcgdskzl=0.0;
											zfcgds_kzl=0;
										}
										if(zfjgds_kzl>0){
											jkzl+=zfjgdskzl/zfjgds_kzl;
											jindexkzl++;
											zfjgdskzl=0.0;
											zfjgds_kzl=0;
										}
										//添加新数据
										//汇总
										zfds_kzl++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											zfdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
										if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
											zfcgds_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												zfcgdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
											zfjgds_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												zfjgdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}
									}else{
										//添加新数据
										//汇总
										zfds_kzl++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											zfdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
										if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
											zfcgds_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												zfcgdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
											zfjgds_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												zfjgdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}
									}
								}
							}
						}else{
							Z_Airdata prev = todayList.get(i-1);
							String prevflt = prev.getFlt_Rte_Cd();
							String date = sdf.format(z_Airdata.getLcl_Dpt_Day());
							String prevdate = sdf.format(prev.getLcl_Dpt_Day());
							if(flt.length()==9){//经停
								if(prevdate.equals(date)){//日期相同
									hzs++;
									if(z_Airdata.getTal_Nbr_Set()>0){
										hzkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
									}
									if(flt.startsWith(airport)){//起点
										//出港
										if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
											if(z_Airdata.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
												jtqdcds_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtqdcdkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}else{
												jtqddds_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtqdddkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}
										}
									}else if(flt.endsWith(airport)){//终点
										//进港
										if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){
											if(z_Airdata.getDpt_AirPt_Cd().equals(flt.substring(0,3))){
												jtzdcds_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtzdcdkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}else{
												jtzddds_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtzdddkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}
										}
									}else{//经停
										//出港
										if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
											jtcgs_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												jtcgkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
											jtjgs_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												jtjgkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}
									}
									//编辑数据
									indexkzl++;
									kzl+=hzkzl/hzs;
									hzs=0;
									hzkzl=0.0;
									if(prevflt.startsWith(airport)){//起点
										//出港
										if(jtqdcds_kzl==0&&jtqddds_kzl>0){
											ckzl+=jtqdddkzl/jtqddds_kzl;
											cindexkzl++;
										}else if(jtqddds_kzl==0&&jtqdcds_kzl>0){
											ckzl+=jtqdcdkzl/jtqdcds_kzl;
											cindexkzl++;
										}else if(jtqddds_kzl>0&&jtqdcds_kzl>0){
											ckzl+=(jtqdcdkzl/jtqdcds_kzl+jtqdddkzl/jtqddds_kzl)/2;
											cindexkzl++;
										}
										jtqdcds_kzl=0;
										jtqddds_kzl=0;
										jtqdcdkzl=0.0;
										jtqdddkzl=0.0;
									}else if(prevflt.endsWith(airport)){//终点
										//进港
										if(jtzdcds_kzl==0&&jtzddds_kzl>0){
											jkzl+=jtzdddkzl/jtzddds_kzl;
											jindexkzl++;
										}else if(jtzddds_kzl==0&&jtzdcds_kzl>0){
											jkzl+=jtzdcdkzl/jtzdcds_kzl;
											jindexkzl++;
										}else if(jtzdcds_kzl>0&&jtzddds_kzl>0){
											jkzl+=(jtzdcdkzl/jtzdcds_kzl+jtzdddkzl/jtzddds_kzl)/2;
											jindexkzl++;
										}
										jtzdcds_kzl=0;
										jtzddds_kzl=0;
										jtzdcdkzl=0.0;
										jtzdddkzl=0.0;
									}else{//经停
										//出港
										if(jtcgs_kzl>0){
											ckzl+=jtcgkzl/jtcgs_kzl;
											cindexkzl++;
											jtcgkzl=0.0;
											jtcgs_kzl=0;
										}
										//进港
										if(jtjgs_kzl>0){
											jkzl+=jtjgkzl/jtjgs_kzl;
											jindexkzl++;
											jtjgkzl=0.0;
											jtjgs_kzl=0;
										}
									}
								}else{
									if(hzs>0){
										//编辑旧数据,重置变量
										indexkzl++;
										kzl+=hzkzl/hzs;
										hzs=0;
										hzkzl=0.0;
										if(prevflt.startsWith(airport)){//起点
											//出港
											if(jtqdcds_kzl==0&&jtqddds_kzl>0){
												ckzl+=jtqdddkzl/jtqddds_kzl;
												cindexkzl++;
											}else if(jtqddds_kzl==0&&jtqdcds_kzl>0){
												ckzl+=jtqdcdkzl/jtqdcds_kzl;
												cindexkzl++;
											}else if(jtqddds_kzl>0&&jtqdcds_kzl>0){
												ckzl+=(jtqdcdkzl/jtqdcds_kzl+jtqdddkzl/jtqddds_kzl)/2;
												cindexkzl++;
											}
											jtqdcds_kzl=0;
											jtqddds_kzl=0;
											jtqdcdkzl=0.0;
											jtqdddkzl=0.0;
										}else if(prevflt.endsWith(airport)){//终点
											//进港
											if(jtzdcds_kzl==0&&jtzddds_kzl>0){
												jkzl+=jtzdddkzl/jtzddds_kzl;
												jindexkzl++;
											}else if(jtzddds_kzl==0&&jtzdcds_kzl>0){
												jkzl+=jtzdcdkzl/jtzdcds_kzl;
												jindexkzl++;
											}else if(jtzdcds_kzl>0&&jtzddds_kzl>0){
												jkzl+=(jtzdcdkzl/jtzdcds_kzl+jtzdddkzl/jtzddds_kzl)/2;
												jindexkzl++;
											}
											jtzdcds_kzl=0;
											jtzddds_kzl=0;
											jtzdcdkzl=0.0;
											jtzdddkzl=0.0;
										}else{//经停
											//出港
											if(jtcgs_kzl>0){
												ckzl+=jtcgkzl/jtcgs_kzl;
												cindexkzl++;
												jtcgkzl=0.0;
												jtcgs_kzl=0;
											}
											//进港
											if(jtjgs_kzl>0){
												jkzl+=jtjgkzl/jtjgs_kzl;
												jindexkzl++;
												jtjgkzl=0.0;
												jtjgs_kzl=0;
											}
										}
										//新增数据
										hzs++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											hzkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
										if(flt.startsWith(airport)){//起点
											//出港
											if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
												if(z_Airdata.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
													jtqdcds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtqdcdkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}else{
													jtqddds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtqdddkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}
											}
										}else if(flt.endsWith(airport)){//终点
											//进港
											if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){
												if(z_Airdata.getDpt_AirPt_Cd().equals(flt.substring(0,3))){
													jtzdcds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtzdcdkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}else{
													jtzddds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtzdddkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}
											}
										}else{//经停
											//出港
											if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
												jtcgs_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtcgkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
												jtjgs_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtjgkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}
										}
										//再次编辑数据
										indexkzl++;
										kzl+=hzkzl/hzs;
										hzs=0;
										hzkzl=0.0;
										if(prevflt.startsWith(airport)){//起点
											//出港
											if(jtqdcds_kzl==0&&jtqddds_kzl>0){
												ckzl+=jtqdddkzl/jtqddds_kzl;
												cindexkzl++;
											}else if(jtqddds_kzl==0&&jtqdcds_kzl>0){
												ckzl+=jtqdcdkzl/jtqdcds_kzl;
												cindexkzl++;
											}else if(jtqddds_kzl>0&&jtqdcds_kzl>0){
												ckzl+=(jtqdcdkzl/jtqdcds_kzl+jtqdddkzl/jtqddds_kzl)/2;
												cindexkzl++;
											}
											jtqdcds_kzl=0;
											jtqddds_kzl=0;
											jtqdcdkzl=0.0;
											jtqdddkzl=0.0;
										}else if(prevflt.endsWith(airport)){//终点
											//进港
											if(jtzdcds_kzl==0&&jtzddds_kzl>0){
												jkzl+=jtzdddkzl/jtzddds_kzl;
												jindexkzl++;
											}else if(jtzddds_kzl==0&&jtzdcds_kzl>0){
												jkzl+=jtzdcdkzl/jtzdcds_kzl;
												jindexkzl++;
											}else if(jtzdcds_kzl>0&&jtzddds_kzl>0){
												jkzl+=(jtzdcdkzl/jtzdcds_kzl+jtzdddkzl/jtzddds_kzl)/2;
												jindexkzl++;
											}
											jtzdcds_kzl=0;
											jtzddds_kzl=0;
											jtzdcdkzl=0.0;
											jtzdddkzl=0.0;
										}else{//经停
											//出港
											if(jtcgs_kzl>0){
												ckzl+=jtcgkzl/jtcgs_kzl;
												cindexkzl++;
												jtcgkzl=0.0;
												jtcgs_kzl=0;
											}
											//进港
											if(jtjgs_kzl>0){
												jkzl+=jtjgkzl/jtjgs_kzl;
												jindexkzl++;
												jtjgkzl=0.0;
												jtjgs_kzl=0;
											}
										}
									}else{
										//新增数据
										hzs++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											hzkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
										if(flt.startsWith(airport)){//起点
											//出港
											if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
												if(z_Airdata.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
													jtqdcds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtqdcdkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}else{
													jtqddds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtqdddkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}
											}
										}else if(flt.endsWith(airport)){//终点
											//进港
											if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){
												if(z_Airdata.getDpt_AirPt_Cd().equals(flt.substring(0,3))){
													jtzdcds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtzdcdkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}else{
													jtzddds_kzl++;
													if(z_Airdata.getTal_Nbr_Set()>0){
														jtzdddkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
													}
												}
											}
										}else{//经停
											//出港
											if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){
												jtcgs_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtcgkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
												jtjgs_kzl++;
												if(z_Airdata.getTal_Nbr_Set()>0){
													jtjgkzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
												}
											}
										}
										//编辑数据
										indexkzl++;
										kzl+=hzkzl/hzs;
										hzs=0;
										hzkzl=0.0;
										if(prevflt.startsWith(airport)){//起点
											//出港
											if(jtqdcds_kzl==0&&jtqddds_kzl>0){
												ckzl+=jtqdddkzl/jtqddds_kzl;
												cindexkzl++;
											}else if(jtqddds_kzl==0&&jtqdcds_kzl>0){
												ckzl+=jtqdcdkzl/jtqdcds_kzl;
												cindexkzl++;
											}else if(jtqddds_kzl>0&&jtqdcds_kzl>0){
												ckzl+=(jtqdcdkzl/jtqdcds_kzl+jtqdddkzl/jtqddds_kzl)/2;
												cindexkzl++;
											}
											jtqdcds_kzl=0;
											jtqddds_kzl=0;
											jtqdcdkzl=0.0;
											jtqdddkzl=0.0;
										}else if(prevflt.endsWith(airport)){//终点
											//进港
											if(jtzdcds_kzl==0&&jtzddds_kzl>0){
												jkzl+=jtzdddkzl/jtzddds_kzl;
												jindexkzl++;
											}else if(jtzddds_kzl==0&&jtzdcds_kzl>0){
												jkzl+=jtzdcdkzl/jtzdcds_kzl;
												jindexkzl++;
											}else if(jtzdcds_kzl>0&&jtzddds_kzl>0){
												jkzl+=(jtzdcdkzl/jtzdcds_kzl+jtzdddkzl/jtzddds_kzl)/2;
												jindexkzl++;
											}
											jtzdcds_kzl=0;
											jtzddds_kzl=0;
											jtzdcdkzl=0.0;
											jtzdddkzl=0.0;
										}else{//经停
											//出港
											if(jtcgs_kzl>0){
												ckzl+=jtcgkzl/jtcgs_kzl;
												cindexkzl++;
												jtcgkzl=0.0;
												jtcgs_kzl=0;
											}
											//进港
											if(jtjgs_kzl>0){
												jkzl+=jtjgkzl/jtjgs_kzl;
												jindexkzl++;
												jtjgkzl=0.0;
												jtjgs_kzl=0;
											}
										}
									}
									
								}
							}else if(flt.length()==6){//直飞
								if(prevdate.equals(date)){//日期相同
									//汇总
									zfds_kzl++;
									if(z_Airdata.getTal_Nbr_Set()>0){
										zfdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
									}
									if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
										zfcgds_kzl++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											zfcgdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
									}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
										zfjgds_kzl++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											zfjgdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
									}
									//编辑原有数据
									kzl+=zfdskzl/zfds_kzl;
									indexkzl++;
									zfds_kzl=0;
									zfdskzl=0.0;
									if(zfcgds_kzl>0){
										ckzl+=zfcgdskzl/zfcgds_kzl;
										cindexkzl++;
										zfcgdskzl=0.0;
										zfcgds_kzl=0;
									}
									if(zfjgds_kzl>0){
										jkzl+=zfjgdskzl/zfjgds_kzl;
										jindexkzl++;
										zfjgdskzl=0.0;
										zfjgds_kzl=0;
									}
								}else{
									if(zfds_kzl>0){
										//编辑原有数据
										kzl+=zfdskzl/zfds_kzl;
										indexkzl++;
										zfds_kzl=0;
										zfdskzl=0.0;
										if(zfcgds_kzl>0){
											ckzl+=zfcgdskzl/zfcgds_kzl;
											cindexkzl++;
											zfcgdskzl=0.0;
											zfcgds_kzl=0;
										}
										if(zfjgds_kzl>0){
											jkzl+=zfjgdskzl/zfjgds_kzl;
											jindexkzl++;
											zfjgdskzl=0.0;
											zfjgds_kzl=0;
										}
										//添加新数据
										//汇总
										zfds_kzl++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											zfdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
										if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
											zfcgds_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												zfcgdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
											zfjgds_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												zfjgdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}
										//编辑原有数据
										kzl+=zfdskzl/zfds_kzl;
										indexkzl++;
										zfds_kzl=0;
										zfdskzl=0.0;
										if(zfcgds_kzl>0){
											ckzl+=zfcgdskzl/zfcgds_kzl;
											cindexkzl++;
											zfcgdskzl=0.0;
											zfcgds_kzl=0;
										}
										if(zfjgds_kzl>0){
											jkzl+=zfjgdskzl/zfjgds_kzl;
											jindexkzl++;
											zfjgdskzl=0.0;
											zfjgds_kzl=0;
										}
									}else{
										//添加新数据
										//汇总
										zfds_kzl++;
										if(z_Airdata.getTal_Nbr_Set()>0){
											zfdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
										}
										if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
											zfcgds_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												zfcgdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}else if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
											zfjgds_kzl++;
											if(z_Airdata.getTal_Nbr_Set()>0){
												zfjgdskzl+=z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100;
											}
										}
										//编辑数据
										kzl+=zfdskzl/zfds_kzl;
										indexkzl++;
										zfds_kzl=0;
										zfdskzl=0.0;
										if(zfcgds_kzl>0){
											ckzl+=zfcgdskzl/zfcgds_kzl;
											cindexkzl++;
											zfcgdskzl=0.0;
											zfcgds_kzl=0;
										}
										if(zfjgds_kzl>0){
											jkzl+=zfjgdskzl/zfjgds_kzl;
											jindexkzl++;
											zfjgdskzl=0.0;
											zfjgds_kzl=0;
										}
									}
								}
							}
						}
					}else{
						if(flag){
							flag=false;
							Z_Airdata prev=todayList.get(i-1);
							String flt=prev.getFlt_Rte_Cd();
							if(flt.length()==9){
								//编辑旧数据,重置变量
								indexkzl++;
								kzl+=hzkzl/hzs;
								hzs=0;
								hzkzl=0.0;
								if(flt.startsWith(airport)){//起点
									//出港
									if(jtqdcds_kzl==0&&jtqddds_kzl>0){
										ckzl+=jtqdddkzl/jtqddds_kzl;
										cindexkzl++;
									}else if(jtqddds_kzl==0&&jtqdcds_kzl>0){
										ckzl+=jtqdcdkzl/jtqdcds_kzl;
										cindexkzl++;
									}else if(jtqddds_kzl>0&&jtqdcds_kzl>0){
										ckzl+=(jtqdcdkzl/jtqdcds_kzl+jtqdddkzl/jtqddds_kzl)/2;
										cindexkzl++;
									}
									jtqdcds_kzl=0;
									jtqddds_kzl=0;
									jtqdcdkzl=0.0;
									jtqdddkzl=0.0;
								}else if(flt.endsWith(airport)){//终点
									//进港
									if(jtzdcds_kzl==0&&jtzddds_kzl>0){
										jkzl+=jtzdddkzl/jtzddds_kzl;
										jindexkzl++;
									}else if(jtzddds_kzl==0&&jtzdcds_kzl>0){
										jkzl+=jtzdcdkzl/jtzdcds_kzl;
										jindexkzl++;
									}else if(jtzdcds_kzl>0&&jtzddds_kzl>0){
										jkzl+=(jtzdcdkzl/jtzdcds_kzl+jtzdddkzl/jtzddds_kzl)/2;
										jindexkzl++;
									}
									jtzdcds_kzl=0;
									jtzddds_kzl=0;
									jtzdcdkzl=0.0;
									jtzdddkzl=0.0;
								}else{//经停
									//出港
									if(jtcgs_kzl>0){
										ckzl+=jtcgkzl/jtcgs_kzl;
										cindexkzl++;
										jtcgkzl=0.0;
										jtcgs_kzl=0;
									}
									//进港
									if(jtjgs_kzl>0){
										jkzl+=jtjgkzl/jtjgs_kzl;
										jindexkzl++;
										jtjgkzl=0.0;
										jtjgs_kzl=0;
									}
								}
							}else if(flt.length()==6){
								//编辑原有数据
								kzl+=zfdskzl/zfds_kzl;
								indexkzl++;
								zfds_kzl=0;
								zfdskzl=0.0;
								if(zfcgds_kzl>0){
									ckzl+=zfcgdskzl/zfcgds_kzl;
									cindexkzl++;
									zfcgdskzl=0.0;
									zfcgds_kzl=0;
								}
								if(zfjgds_kzl>0){
									jkzl+=zfjgdskzl/zfjgds_kzl;
									jindexkzl++;
									zfjgdskzl=0.0;
									zfjgds_kzl=0;
								}
							}
						}
					}
				}
				if(indexkzl>0){
					kzl = kzl/indexkzl;
				}else{
					kzl = 0.0;
				}
				//出港客座率
				if(cindexkzl>0){
					ckzl = ckzl/cindexkzl;
				}else{
					ckzl = 0.0;
				}
				//进港客座率
				if(jindexkzl>0){
					jkzl = jkzl/jindexkzl;
				}else{
					jkzl = 0.0;
				}
				int ff = 0;
				int jtff =0;//汇总经停
				int zfff=0;//汇总直飞
				int cff = 0;
				int czfff=0;//出港直飞
				int jff = 0;
				int jzfff=0;//进港直飞
				int jtqdcds=0;//经停航线-起点-长段个数
				int jtqddds=0;//经停航线-起点-短段个数
				int jtzdcds=0;//经停航线-终点-长段个数
				int jtzddds=0;//经停航线-终点-短段个数
				int jtjgs=0;//经停航线-经停-进港个数
				int jtcgs=0;//经停航线-经停-出港个数
				Double jtqdcdzdl=0.0;//经停航线-起点-长段准点率
				Double jtqdddzdl=0.0;//经停航线-起点-短段准点率
				Double jtzdcdzdl=0.0;//经停航线-终点-长段准点率
				Double jtzdddzdl=0.0;//经停航线-终点-短段准点率
				Double jtjgzdl=0.0;//经停航线-经停-进港准点率
				Double jtcgzdl=0.0;//经停航线-经停-出港准点率
				Double zdl = 0.0;
				Double zfzdl = 0.0;//汇总直飞
				Double czfzdl =0.0;//出港直飞
				Double jzfzdl =0.0;//进港直飞
				Double jtzdl = 0.0;//汇总经停准点
				Double czdl = 0.0;
				Double jzdl = 0.0;
				boolean zdflag=false;
//				List<Double> hzzdl=new ArrayList<Double>();//当前航班号对应的所有航线汇总准点率列表
//				List<Double> cgzdl=new ArrayList<Double>();//当前航班号对应的所有航线出港准点率列表
//				List<Double> jgzdl=new ArrayList<Double>();//当前航班号对应的所有航线进港准点率列表
//				for (int i=0;i<yesterdayZDList.size();i++) {
//					Yesterday_ZD yesterday_ZD = yesterdayZDList.get(i);
//					String flyNbr = yesterday_ZD.getFlt_nbr();
//					//在指定时间内
//					long currentTime = sdf.parse(yesterday_ZD.getAir_date()).getTime();
//					if(startTime<=currentTime&&currentTime<=endTime){
//						if(str.equals(flyNbr)){
//							zdflag=true;
//							String flt=yesterday_ZD.getRemark();
//							if(flt.length()==9){//包含经停地航线
//								if(i==0){
//									//汇总
//									jtff++;
//									jtzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//									if(flt.startsWith(airport)){//起点
//										//出港
//										if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){
//											if(yesterday_ZD.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
//												jtqdcds++;
//												jtqdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}else{
//												jtqddds++;
//												jtqdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}
//										}
//									}else if(flt.endsWith(airport)){//终点
//										//进港
//										if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){
//											if(yesterday_ZD.getDpt_AirPt_Cd().equals(flt.substring(0,3))){//长段
//												jtzdcds++;
//												jtzdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}else{
//												jtzddds++;
//												jtzdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}
//										}
//									}else{//经停
//										if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//											jtcgs++;
//											jtcgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//										}else if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){//进港
//											jtjgs++;
//											jtjgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//										}
//									}
//								}else if(i!=yesterdayZDList.size()-1){
//									Yesterday_ZD prev = yesterdayZDList.get(i-1);//查看上一个对象的航线
//									String prevflt = prev.getRemark();
//									String prevdate = prev.getAir_date();
//									String date = yesterday_ZD.getAir_date();
//									if(prevdate.equals(date)){//同一天只有一个航班号
//										//汇总
//										jtff++;
//										jtzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//										if(flt.startsWith(airport)){//起点
//											//出港
//											if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//长段
//												if(yesterday_ZD.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
//													jtqdcds++;
//													jtqdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}else{
//													jtqddds++;
//													jtqdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}
//											}
//										}else if(flt.endsWith(airport)){//终点
//											//进港
//											if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){
//												if(yesterday_ZD.getDpt_AirPt_Cd().equals(flt.substring(0,3))){//长段
//													jtzdcds++;
//													jtzdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}else{
//													jtzddds++;
//													jtzdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}
//											}
//										}else{//经停
//											if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//												jtcgs++;
//												jtcgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}else if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){//进港
//												jtjgs++;
//												jtjgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}
//										}
//									}else{//航线变化
//										if(jtff>0){
//											//计算原有值
//											//计算汇总
//											zdl+=jtzdl/jtff;
//											ff++;
////											hzzdl.add(jtzdl/jtff);
//											if(prevflt.startsWith(airport)){//起点
//												//出港
//												jtqdcdzdl = jtqdcds==0?0.0:jtqdcdzdl/jtqdcds;
//												jtqdddzdl = jtqddds==0?0.0:jtqdddzdl/jtqddds;
//												if(jtqdcdzdl.doubleValue()==0&&jtqdddzdl.doubleValue()!=0){
//													czdl+=jtqdddzdl;
//													cff++;
////													cgzdl.add(jtqdddzdl);
//												}else if(jtqdddzdl.doubleValue()==0&&jtqdcdzdl.doubleValue()!=0){
//													czdl+=jtqdcdzdl;
//													cff++;
////													cgzdl.add(jtqdcdzdl);
//												}else if(jtqdcdzdl.doubleValue()!=0&&jtqdddzdl.doubleValue()!=0){
//													czdl+=(jtqdddzdl+jtqdcdzdl)/2;
//													cff++;
////													cgzdl.add((jtqdddzdl+jtqdcdzdl)/2);
//												}
//												jtqdcdzdl=0.0;
//												jtqdddzdl=0.0;
//												jtqdcds=0;
//												jtqddds=0;
//											}else if(prevflt.endsWith(airport)){//终点
//												//进港
//												jtzdcdzdl = jtzdcds==0?0.0:jtzdcdzdl/jtzdcds;
//												jtzdddzdl = jtzddds==0?0.0:jtzdddzdl/jtzddds;
//												if(jtzdcdzdl.doubleValue()==0&&jtzdddzdl.doubleValue()!=0){
//													jzdl+=jtzdddzdl;
//													jff++;
////													jgzdl.add(jtzdddzdl);
//												}else if(jtzdddzdl.doubleValue()==0&&jtzdcdzdl.doubleValue()!=0){
//													jzdl+=jtzdcdzdl;
//													jff++;
////													jgzdl.add(jtzdcdzdl);
//												}else if(jtzdcdzdl.doubleValue()!=0&&jtzdddzdl.doubleValue()!=0){
//													jzdl+=(jtzdddzdl+jtzdcdzdl)/2;
//													jff++;
////													jgzdl.add((jtzdddzdl+jtzdcdzdl)/2);
//												}
//												jtzdcdzdl=0.0;
//												jtzdddzdl=0.0;
//												jtzdcds=0;
//												jtzddds=0;
//											}else{//经停
//												//出港
//												jtcgzdl=jtcgs==0?0.0:jtcgzdl/jtcgs;
//												if(jtcgzdl.doubleValue()>0){
//													czdl+=jtcgzdl;
//													cff++;
////													cgzdl.add(jtcgzdl);
//												}
//												//进港
//												jtjgzdl=jtjgs==0?0.0:jtjgzdl/jtjgs;
//												if(jtjgzdl.doubleValue()>0){
//													jzdl+=jtjgzdl;
//													jff++;
////													jgzdl.add(jtjgzdl);
//												}
//												jtcgzdl=0.0;
//												jtjgzdl=0.0;
//												jtcgs=0;
//												jtjgs=0;
//											}
//											//添加新数据
//											//汇总
//											jtff++;
//											jtzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											if(flt.startsWith(airport)){//起点
//												//出港
//												if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//长段
//													if(yesterday_ZD.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
//														jtqdcds++;
//														jtqdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}else{
//														jtqddds++;
//														jtqdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}
//												}
//											}else if(flt.endsWith(airport)){//终点
//												//进港
//												if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){
//													if(yesterday_ZD.getDpt_AirPt_Cd().equals(flt.substring(0,3))){//长段
//														jtzdcds++;
//														jtzdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}else{
//														jtzddds++;
//														jtzdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}
//												}
//											}else{//经停
//												if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//													jtcgs++;
//													jtcgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}else if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){//进港
//													jtjgs++;
//													jtjgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}
//											}
//										}else{
//											//汇总
//											jtff++;
//											jtzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											if(flt.startsWith(airport)){//起点
//												//出港
//												if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//长段
//													if(yesterday_ZD.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
//														jtqdcds++;
//														jtqdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}else{
//														jtqddds++;
//														jtqdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}
//												}
//											}else if(flt.endsWith(airport)){//终点
//												//进港
//												if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){
//													if(yesterday_ZD.getDpt_AirPt_Cd().equals(flt.substring(0,3))){//长段
//														jtzdcds++;
//														jtzdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}else{
//														jtzddds++;
//														jtzdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}
//												}
//											}else{//经停
//												if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//													jtcgs++;
//													jtcgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}else if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){//进港
//													jtjgs++;
//													jtjgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}
//											}
//										}
//										
//									}
//								}else{
//									Yesterday_ZD prev = yesterdayZDList.get(i-1);//查看上一个对象的航线
//									String prevflt = prev.getRemark();
//									String prevdate = prev.getAir_date();
//									String date = yesterday_ZD.getAir_date();
//									if(prevdate.equals(date)){//同一天只有一个航班号
//										//汇总
//										jtff++;
//										jtzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//										if(flt.startsWith(airport)){//起点
//											//出港
//											if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//长段
//												if(yesterday_ZD.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
//													jtqdcds++;
//													jtqdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}else{
//													jtqddds++;
//													jtqdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}
//											}
//										}else if(flt.endsWith(airport)){//终点
//											//进港
//											if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){
//												if(yesterday_ZD.getDpt_AirPt_Cd().equals(flt.substring(0,3))){//长段
//													jtzdcds++;
//													jtzdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}else{
//													jtzddds++;
//													jtzdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}
//											}
//										}else{//经停
//											if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//												jtcgs++;
//												jtcgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}else if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){//进港
//												jtjgs++;
//												jtjgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}
//										}
//										//计算汇总
//										zdl+=jtzdl/jtff;
//										ff++;
////										hzzdl.add(jtzdl/jtff);
//										if(prevflt.startsWith(airport)){//起点
//											//出港
//											jtqdcdzdl = jtqdcds==0?0.0:jtqdcdzdl/jtqdcds;
//											jtqdddzdl = jtqddds==0?0.0:jtqdddzdl/jtqddds;
//											if(jtqdcdzdl.doubleValue()==0&&jtqdddzdl.doubleValue()!=0){
//												czdl+=jtqdddzdl;
//												cff++;
////												cgzdl.add(jtqdddzdl);
//											}else if(jtqdddzdl.doubleValue()==0&&jtqdcdzdl.doubleValue()!=0){
//												czdl+=jtqdcdzdl;
//												cff++;
////												cgzdl.add(jtqdcdzdl);
//											}else if(jtqdcdzdl.doubleValue()!=0&&jtqdddzdl.doubleValue()!=0){
//												czdl+=(jtqdddzdl+jtqdcdzdl)/2;
//												cff++;
////												cgzdl.add((jtqdddzdl+jtqdcdzdl)/2);
//											}
//											jtqdcdzdl=0.0;
//											jtqdddzdl=0.0;
//											jtqdcds=0;
//											jtqddds=0;
//										}else if(prevflt.endsWith(airport)){//终点
//											//进港
//											jtzdcdzdl = jtzdcds==0?0.0:jtzdcdzdl/jtzdcds;
//											jtzdddzdl = jtzddds==0?0.0:jtzdddzdl/jtzddds;
//											if(jtzdcdzdl.doubleValue()==0&&jtzdddzdl.doubleValue()!=0){
//												jzdl+=jtzdddzdl;
//												jff++;
////												jgzdl.add(jtzdddzdl);
//											}else if(jtzdddzdl.doubleValue()==0&&jtzdcdzdl.doubleValue()!=0){
//												jzdl+=jtzdcdzdl;
//												jff++;
////												jgzdl.add(jtzdcdzdl);
//											}else if(jtzdcdzdl.doubleValue()!=0&&jtzdddzdl.doubleValue()!=0){
//												jzdl+=(jtzdddzdl+jtzdcdzdl)/2;
//												jff++;
////												jgzdl.add((jtzdddzdl+jtzdcdzdl)/2);
//											}
//											jtzdcdzdl=0.0;
//											jtzdddzdl=0.0;
//											jtzdcds=0;
//											jtzddds=0;
//										}else{//经停
//											//出港
//											jtcgzdl=jtcgs==0?0.0:jtcgzdl/jtcgs;
//											if(jtcgzdl.doubleValue()>0){
//												czdl+=jtcgzdl;
//												cff++;
////												cgzdl.add(jtcgzdl);
//											}
//											//进港
//											jtjgzdl=jtjgs==0?0.0:jtjgzdl/jtjgs;
//											if(jtjgzdl.doubleValue()>0){
//												jzdl+=jtjgzdl;
//												jff++;
////												jgzdl.add(jtjgzdl);
//											}
//											jtcgzdl=0.0;
//											jtjgzdl=0.0;
//											jtcgs=0;
//											jtjgs=0;
//										}
//									}else{//航线变化
//										if(jtff>0){
//											//计算原有值
//											//计算汇总
//											zdl+=jtzdl/jtff;
//											ff++;
////											hzzdl.add(jtzdl/jtff);
//											if(prevflt.startsWith(airport)){//起点
//												//出港
//												jtqdcdzdl = jtqdcds==0?0.0:jtqdcdzdl/jtqdcds;
//												jtqdddzdl = jtqddds==0?0.0:jtqdddzdl/jtqddds;
//												if(jtqdcdzdl.doubleValue()==0&&jtqdddzdl.doubleValue()!=0){
//													czdl+=jtqdddzdl;
//													cff++;
////													cgzdl.add(jtqdddzdl);
//												}else if(jtqdddzdl.doubleValue()==0&&jtqdcdzdl.doubleValue()!=0){
//													czdl+=jtqdcdzdl;
//													cff++;
////													cgzdl.add(jtqdcdzdl);
//												}else if(jtqdcdzdl.doubleValue()!=0&&jtqdddzdl.doubleValue()!=0){
//													czdl+=(jtqdddzdl+jtqdcdzdl)/2;
//													cff++;
////													cgzdl.add((jtqdddzdl+jtqdcdzdl)/2);
//												}
//												jtqdcdzdl=0.0;
//												jtqdddzdl=0.0;
//												jtqdcds=0;
//												jtqddds=0;
//											}else if(prevflt.endsWith(airport)){//终点
//												//进港
//												jtzdcdzdl = jtzdcds==0?0.0:jtzdcdzdl/jtzdcds;
//												jtzdddzdl = jtzddds==0?0.0:jtzdddzdl/jtzddds;
//												if(jtzdcdzdl.doubleValue()==0&&jtzdddzdl.doubleValue()!=0){
//													jzdl+=jtzdddzdl;
//													jff++;
////													jgzdl.add(jtzdddzdl);
//												}else if(jtzdddzdl.doubleValue()==0&&jtzdcdzdl.doubleValue()!=0){
//													jzdl+=jtzdcdzdl;
//													jff++;
////													jgzdl.add(jtzdcdzdl);
//												}else if(jtzdcdzdl.doubleValue()!=0&&jtzdddzdl.doubleValue()!=0){
//													jzdl+=(jtzdddzdl+jtzdcdzdl)/2;
//													jff++;
////													jgzdl.add((jtzdddzdl+jtzdcdzdl)/2);
//												}
//												jtzdcdzdl=0.0;
//												jtzdddzdl=0.0;
//												jtzdcds=0;
//												jtzddds=0;
//											}else{//经停
//												//出港
//												jtcgzdl=jtcgs==0?0.0:jtcgzdl/jtcgs;
//												if(jtcgzdl.doubleValue()>0){
//													czdl+=jtcgzdl;
//													cff++;
////													cgzdl.add(jtcgzdl);
//												}
//												//进港
//												jtjgzdl=jtjgs==0?0.0:jtjgzdl/jtjgs;
//												if(jtjgzdl.doubleValue()>0){
//													jzdl+=jtjgzdl;
//													jff++;
////													jgzdl.add(jtjgzdl);
//												}
//												jtcgzdl=0.0;
//												jtjgzdl=0.0;
//												jtcgs=0;
//												jtjgs=0;
//											}
//											//添加新数据
//											//汇总
//											jtff++;
//											jtzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											if(flt.startsWith(airport)){//起点
//												//出港
//												if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//长段
//													if(yesterday_ZD.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
//														jtqdcds++;
//														jtqdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}else{
//														jtqddds++;
//														jtqdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}
//												}
//											}else if(flt.endsWith(airport)){//终点
//												//进港
//												if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){
//													if(yesterday_ZD.getDpt_AirPt_Cd().equals(flt.substring(0,3))){//长段
//														jtzdcds++;
//														jtzdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}else{
//														jtzddds++;
//														jtzdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}
//												}
//											}else{//经停
//												if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//													jtcgs++;
//													jtcgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}else if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){//进港
//													jtjgs++;
//													jtjgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}
//											}
//											//计算汇总
//											zdl+=jtzdl/jtff;
//											ff++;
////											hzzdl.add(jtzdl/jtff);
//											if(prevflt.startsWith(airport)){//起点
//												//出港
//												jtqdcdzdl = jtqdcds==0?0.0:jtqdcdzdl/jtqdcds;
//												jtqdddzdl = jtqddds==0?0.0:jtqdddzdl/jtqddds;
//												if(jtqdcdzdl.doubleValue()==0&&jtqdddzdl.doubleValue()!=0){
//													czdl+=jtqdddzdl;
//													cff++;
////													cgzdl.add(jtqdddzdl);
//												}else if(jtqdddzdl.doubleValue()==0&&jtqdcdzdl.doubleValue()!=0){
//													czdl+=jtqdcdzdl;
//													cff++;
////													cgzdl.add(jtqdcdzdl);
//												}else if(jtqdcdzdl.doubleValue()!=0&&jtqdddzdl.doubleValue()!=0){
//													czdl+=(jtqdddzdl+jtqdcdzdl)/2;
//													cff++;
////													cgzdl.add((jtqdddzdl+jtqdcdzdl)/2);
//												}
//												jtqdcdzdl=0.0;
//												jtqdddzdl=0.0;
//												jtqdcds=0;
//												jtqddds=0;
//											}else if(prevflt.endsWith(airport)){//终点
//												//进港
//												jtzdcdzdl = jtzdcds==0?0.0:jtzdcdzdl/jtzdcds;
//												jtzdddzdl = jtzddds==0?0.0:jtzdddzdl/jtzddds;
//												if(jtzdcdzdl.doubleValue()==0&&jtzdddzdl.doubleValue()!=0){
//													jzdl+=jtzdddzdl;
//													jff++;
////													jgzdl.add(jtzdddzdl);
//												}else if(jtzdddzdl.doubleValue()==0&&jtzdcdzdl.doubleValue()!=0){
//													jzdl+=jtzdcdzdl;
//													jff++;
////													jgzdl.add(jtzdcdzdl);
//												}else if(jtzdcdzdl.doubleValue()!=0&&jtzdddzdl.doubleValue()!=0){
//													jzdl+=(jtzdddzdl+jtzdcdzdl)/2;
//													jff++;
////													jgzdl.add((jtzdddzdl+jtzdcdzdl)/2);
//												}
//												jtzdcdzdl=0.0;
//												jtzdddzdl=0.0;
//												jtzdcds=0;
//												jtzddds=0;
//											}else{//经停
//												//出港
//												jtcgzdl=jtcgs==0?0.0:jtcgzdl/jtcgs;
//												if(jtcgzdl.doubleValue()>0){
//													czdl+=jtcgzdl;
//													cff++;
////													cgzdl.add(jtcgzdl);
//												}
//												//进港
//												jtjgzdl=jtjgs==0?0.0:jtjgzdl/jtjgs;
//												if(jtjgzdl.doubleValue()>0){
//													jzdl+=jtjgzdl;
//													jff++;
////													jgzdl.add(jtjgzdl);
//												}
//												jtcgzdl=0.0;
//												jtjgzdl=0.0;
//												jtcgs=0;
//												jtjgs=0;
//											}
//										}else{
//											//汇总
//											jtff++;
//											jtzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											if(flt.startsWith(airport)){//起点
//												//出港
//												if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//长段
//													if(yesterday_ZD.getArrv_Airpt_Cd().equals(flt.substring(6))){//长段
//														jtqdcds++;
//														jtqdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}else{
//														jtqddds++;
//														jtqdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}
//												}
//											}else if(flt.endsWith(airport)){//终点
//												//进港
//												if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){
//													if(yesterday_ZD.getDpt_AirPt_Cd().equals(flt.substring(0,3))){//长段
//														jtzdcds++;
//														jtzdcdzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}else{
//														jtzddds++;
//														jtzdddzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//													}
//												}
//											}else{//经停
//												if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//													jtcgs++;
//													jtcgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}else if(yesterday_ZD.getArrv_Airpt_Cd().equals(airport)){//进港
//													jtjgs++;
//													jtjgzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//												}
//											}
//											//计算汇总
//											zdl+=jtzdl/jtff;
//											ff++;
////											hzzdl.add(jtzdl/jtff);
//											if(prevflt.startsWith(airport)){//起点
//												//出港
//												jtqdcdzdl = jtqdcds==0?0.0:jtqdcdzdl/jtqdcds;
//												jtqdddzdl = jtqddds==0?0.0:jtqdddzdl/jtqddds;
//												if(jtqdcdzdl.doubleValue()==0&&jtqdddzdl.doubleValue()!=0){
//													czdl+=jtqdddzdl;
//													cff++;
////													cgzdl.add(jtqdddzdl);
//												}else if(jtqdddzdl.doubleValue()==0&&jtqdcdzdl.doubleValue()!=0){
//													czdl+=jtqdcdzdl;
//													cff++;
////													cgzdl.add(jtqdcdzdl);
//												}else if(jtqdcdzdl.doubleValue()!=0&&jtqdddzdl.doubleValue()!=0){
//													czdl+=(jtqdddzdl+jtqdcdzdl)/2;
//													cff++;
////													cgzdl.add((jtqdddzdl+jtqdcdzdl)/2);
//												}
//												jtqdcdzdl=0.0;
//												jtqdddzdl=0.0;
//												jtqdcds=0;
//												jtqddds=0;
//											}else if(prevflt.endsWith(airport)){//终点
//												//进港
//												jtzdcdzdl = jtzdcds==0?0.0:jtzdcdzdl/jtzdcds;
//												jtzdddzdl = jtzddds==0?0.0:jtzdddzdl/jtzddds;
//												if(jtzdcdzdl.doubleValue()==0&&jtzdddzdl.doubleValue()!=0){
//													jzdl+=jtzdddzdl;
//													jff++;
////													jgzdl.add(jtzdddzdl);
//												}else if(jtzdddzdl.doubleValue()==0&&jtzdcdzdl.doubleValue()!=0){
//													jzdl+=jtzdcdzdl;
//													jff++;
////													jgzdl.add(jtzdcdzdl);
//												}else if(jtzdcdzdl.doubleValue()!=0&&jtzdddzdl.doubleValue()!=0){
//													jzdl+=(jtzdddzdl+jtzdcdzdl)/2;
//													jff++;
////													jgzdl.add((jtzdddzdl+jtzdcdzdl)/2);
//												}
//												jtzdcdzdl=0.0;
//												jtzdddzdl=0.0;
//												jtzdcds=0;
//												jtzddds=0;
//											}else{//经停
//												//出港
//												jtcgzdl=jtcgs==0?0.0:jtcgzdl/jtcgs;
//												if(jtcgzdl.doubleValue()>0){
//													czdl+=jtcgzdl;
//													cff++;
////													cgzdl.add(jtcgzdl);
//												}
//												//进港
//												jtjgzdl=jtjgs==0?0.0:jtjgzdl/jtjgs;
//												if(jtjgzdl.doubleValue()>0){
//													jzdl+=jtjgzdl;
//													jff++;
////													jgzdl.add(jtjgzdl);
//												}
//												jtcgzdl=0.0;
//												jtjgzdl=0.0;
//												jtcgs=0;
//												jtjgs=0;
//											}
//										}
//									}
//								}
//							}else if(flt.length()==6){//直飞航线
//								if(i==0){
//									zfff++;
//									zfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//									if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//										czfff++;
//										czfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//									}else{//进港
//										jzfff++;
//										jzfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//									}
//								}else if(i!=yesterdayZDList.size()-1){
//									Yesterday_ZD prev = yesterdayZDList.get(i-1);
//									String date = yesterday_ZD.getAir_date();
//									String prevdate = prev.getAir_date();
//									if(prevdate.equals(date)){//时间相同
//										zfff++;
//										zfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//										if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//											czfff++;
//											czfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//										}else{//进港
//											jzfff++;
//											jzfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//										}
//									}else{
//										if(zfff>0){
//											//编辑原数据
//											zdl+=zfzdl/zfff;
//											ff++;
////											hzzdl.add(zfzdl/zfff);
//											if(czfff>0){
//												czdl+=czfzdl/czfff;
//												cff++;
////												cgzdl.add(czfzdl/czfff);
//												zfzdl=0.0;
//												zfff=0;
//												czfzdl=0.0;
//												czfff=0;
//											}
//											if(jzfff>0){
//												jzdl+=jzfzdl/jzfff;
//												jff++;
////												jgzdl.add(jzfzdl/jzfff);
//												zfzdl=0.0;
//												zfff=0;
//												jzfzdl=0.0;
//												jzfff=0;
//											}
//											//添加新数据
//											zfff++;
//											zfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//												czfff++;
//												czfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}else{//进港
//												jzfff++;
//												jzfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}
//										}else{
//											//添加新数据
//											zfff++;
//											zfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//												czfff++;
//												czfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}else{//进港
//												jzfff++;
//												jzfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}
//										}
//									}
//								}else{
//									Yesterday_ZD prev = yesterdayZDList.get(i-1);
//									String date = yesterday_ZD.getAir_date();
//									String prevdate = prev.getAir_date();
//									if(prevdate.equals(date)){//时间相同
//										zfff++;
//										zfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//										if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//											czfff++;
//											czfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//										}else{//进港
//											jzfff++;
//											jzfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//										}
//										//编辑原数据
//										zdl+=zfzdl/zfff;
//										ff++;
////										hzzdl.add(zfzdl/zfff);
//										if(czfff>0){
//											czdl+=czfzdl/czfff;
//											cff++;
////											cgzdl.add(czfzdl/czfff);
//											zfzdl=0.0;
//											zfff=0;
//											czfzdl=0.0;
//											czfff=0;
//										}
//										if(jzfff>0){
//											jzdl+=jzfzdl/jzfff;
//											jff++;
////											jgzdl.add(jzfzdl/jzfff);
//											zfzdl=0.0;
//											zfff=0;
//											jzfzdl=0.0;
//											jzfff=0;
//										}
//									}else{
//										if(zfff>0){
//											//编辑原数据
//											zdl+=zfzdl/zfff;
//											ff++;
////											hzzdl.add(zfzdl/zfff);
//											if(czfff>0){
//												czdl+=czfzdl/czfff;
//												cff++;
////												cgzdl.add(czfzdl/czfff);
//												zfzdl=0.0;
//												zfff=0;
//												czfzdl=0.0;
//												czfff=0;
//											}
//											if(jzfff>0){
//												jzdl+=jzfzdl/jzfff;
//												jff++;
////												jgzdl.add(jzfzdl/jzfff);
//												zfzdl=0.0;
//												zfff=0;
//												jzfzdl=0.0;
//												jzfff=0;
//											}
//											//添加新数据
//											zfff++;
//											zfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//												czfff++;
//												czfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}else{//进港
//												jzfff++;
//												jzfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}
//											//编辑原数据
//											zdl+=zfzdl/zfff;
//											ff++;
////											hzzdl.add(zfzdl/zfff);
//											if(czfff>0){
//												czdl+=czfzdl/czfff;
//												cff++;
////												cgzdl.add(czfzdl/czfff);
//												zfzdl=0.0;
//												zfff=0;
//												czfzdl=0.0;
//												czfff=0;
//											}
//											if(jzfff>0){
//												jzdl+=jzfzdl/jzfff;
//												jff++;
////												jgzdl.add(jzfzdl/jzfff);
//												zfzdl=0.0;
//												zfff=0;
//												jzfzdl=0.0;
//												jzfff=0;
//											}
//										}else{
//											//添加新数据
//											zfff++;
//											zfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											if(yesterday_ZD.getDpt_AirPt_Cd().equals(airport)){//出港
//												czfff++;
//												czfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}else{//进港
//												jzfff++;
//												jzfzdl+=Double.parseDouble(yesterday_ZD.getZd_rate().replace("%", ""));
//											}
//											//编辑原数据
//											zdl+=zfzdl/zfff;
//											ff++;
////											hzzdl.add(zfzdl/zfff);
//											if(czfff>0){
//												czdl+=czfzdl/czfff;
//												cff++;
////												cgzdl.add(czfzdl/czfff);
//												zfzdl=0.0;
//												zfff=0;
//												czfzdl=0.0;
//												czfff=0;
//											}
//											if(jzfff>0){
//												jzdl+=jzfzdl/jzfff;
//												jff++;
////												jgzdl.add(jzfzdl/jzfff);
//												zfzdl=0.0;
//												zfff=0;
//												jzfzdl=0.0;
//												jzfff=0;
//											}
//										}
//									}
//								}
//							}
//						}else{
//							if(zdflag){
//								zdflag=false;
//								Yesterday_ZD prev=yesterdayZDList.get(i-1);
//								String flt = prev.getRemark();
//								if(flt.length()==9){
//									//计算原有值
//									//计算汇总
//									zdl+=jtzdl/jtff;
//									ff++;
////									hzzdl.add(jtzdl/jtff);
//									if(flt.startsWith(airport)){//起点
//										//出港
//										jtqdcdzdl = jtqdcds==0?0.0:jtqdcdzdl/jtqdcds;
//										jtqdddzdl = jtqddds==0?0.0:jtqdddzdl/jtqddds;
//										if(jtqdcdzdl.doubleValue()==0&&jtqdddzdl.doubleValue()!=0){
//											czdl+=jtqdddzdl;
//											cff++;
////											cgzdl.add(jtqdddzdl);
//										}else if(jtqdddzdl.doubleValue()==0&&jtqdcdzdl.doubleValue()!=0){
//											czdl+=jtqdcdzdl;
//											cff++;
////											cgzdl.add(jtqdcdzdl);
//										}else if(jtqdcdzdl.doubleValue()!=0&&jtqdddzdl.doubleValue()!=0){
//											czdl+=(jtqdddzdl+jtqdcdzdl)/2;
//											cff++;
////											cgzdl.add((jtqdddzdl+jtqdcdzdl)/2);
//										}
//										jtqdcdzdl=0.0;
//										jtqdddzdl=0.0;
//										jtqdcds=0;
//										jtqddds=0;
//									}else if(flt.endsWith(airport)){//终点
//										//进港
//										jtzdcdzdl = jtzdcds==0?0.0:jtzdcdzdl/jtzdcds;
//										jtzdddzdl = jtzddds==0?0.0:jtzdddzdl/jtzddds;
//										if(jtzdcdzdl.doubleValue()==0&&jtzdddzdl.doubleValue()!=0){
//											jzdl+=jtzdddzdl;
//											jff++;
////											jgzdl.add(jtzdddzdl);
//										}else if(jtzdddzdl.doubleValue()==0&&jtzdcdzdl.doubleValue()!=0){
//											jzdl+=jtzdcdzdl;
//											jff++;
////											jgzdl.add(jtzdcdzdl);
//										}else if(jtzdcdzdl.doubleValue()!=0&&jtzdddzdl.doubleValue()!=0){
//											jzdl+=(jtzdddzdl+jtzdcdzdl)/2;
//											jff++;
////											jgzdl.add((jtzdddzdl+jtzdcdzdl)/2);
//										}
//										jtzdcdzdl=0.0;
//										jtzdddzdl=0.0;
//										jtzdcds=0;
//										jtzddds=0;
//									}else{//经停
//										//出港
//										jtcgzdl=jtcgs==0?0.0:jtcgzdl/jtcgs;
//										if(jtcgzdl.doubleValue()>0){
//											czdl+=jtcgzdl;
//											cff++;
////											cgzdl.add(jtcgzdl);
//										}
//										//进港
//										jtjgzdl=jtjgs==0?0.0:jtjgzdl/jtjgs;
//										if(jtjgzdl.doubleValue()>0){
//											jzdl+=jtjgzdl;
//											jff++;
////											jgzdl.add(jtjgzdl);
//										}
//										jtcgzdl=0.0;
//										jtjgzdl=0.0;
//										jtcgs=0;
//										jtjgs=0;
//									}
//								}else if(flt.length()==6){
//									//编辑原数据
//									zdl+=zfzdl/zfff;
//									ff++;
////									hzzdl.add(zfzdl/zfff);
//									if(czfff>0){
//										czdl+=czfzdl/czfff;
//										cff++;
////										cgzdl.add(czfzdl/czfff);
//										zfzdl=0.0;
//										zfff=0;
//										czfzdl=0.0;
//										czfff=0;
//									}
//									if(jzfff>0){
//										jzdl+=jzfzdl/jzfff;
//										jff++;
////										jgzdl.add(jzfzdl/jzfff);
//										zfzdl=0.0;
//										zfff=0;
//										jzfzdl=0.0;
//										jzfff=0;
//									}
//								}
//							}
//						}
//					}
//				}
				//汇总
				HomePageData homePageData = new HomePageData();
				homePageData.setFlyNum(str);
				homePageData.setPersons(zrs+"");
				homePageData.setKzl(df.format(kzl));
				homePageData.setZsr(zsr+"");
				if(ff>0){
					zdl = zdl /(double)ff;
				}
				homePageData.setZdl(df.format(zdl));
				homePageDataList.add(homePageData);
				//出港
				HomePageData chomePageData = new HomePageData();
				chomePageData.setFlyNum(str);
				chomePageData.setPersons(czrs+"");
				chomePageData.setKzl(df.format(ckzl));
				chomePageData.setZsr(czsr+"");
				if(cff>0){
					czdl = czdl /(double)cff;
				}
				chomePageData.setZdl(df.format(czdl));
				chomePageDataList.add(chomePageData);
				//进港
				HomePageData jhomePageData = new HomePageData();
				jhomePageData.setFlyNum(str);
				jhomePageData.setPersons(jzrs+"");
				jhomePageData.setKzl(df.format(jkzl));
				jhomePageData.setZsr(jzsr+"");
				if(jff>0){
					jzdl = jzdl /(double)jff;
				}
				jhomePageData.setZdl(df.format(jzdl));
				jhomePageDataList.add(jhomePageData);
			}
			//汇总
			homePageDataList = sortNew(homePageDataList, "zsr", "DESC"); 
			for (HomePageData homePageData : homePageDataList) {
				Double zsr = Double.valueOf(homePageData.getZsr());
				if(zsr>0){
					if(rethomePageDataList0.size()<10){
						rethomePageDataList0.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("topten_zsr", rethomePageDataList0);
			
			homePageDataList = sortNew(homePageDataList, "persons", "DESC"); 
			for (HomePageData homePageData : homePageDataList) {
				Double persons = Double.valueOf(homePageData.getPersons());
				if(persons>0){
					if(rethomePageDataList1.size()<10){
						rethomePageDataList1.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("topten_kll", rethomePageDataList1);
			
			homePageDataList = sortNew(homePageDataList, "kzl", "DESC"); 
			for (HomePageData homePageData : homePageDataList) {
				Double kzl=Double.valueOf(homePageData.getKzl());
				if(kzl>0){
					if(rethomePageDataList2.size()<10){
						rethomePageDataList2.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("topten_kzl", rethomePageDataList2);
			
			homePageDataList = sortNew(homePageDataList, "zdl", "DESC"); 
			for (HomePageData homePageData : homePageDataList) {
				Double zdl = Double.valueOf(homePageData.getZdl());
				if(zdl>0){
					if(rethomePageDataList22.size()<10){
						rethomePageDataList22.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("topten_zdl", rethomePageDataList22);
			
			homePageDataList = sortNew(homePageDataList, "zsr", "ASC"); 
			for (HomePageData homePageData : homePageDataList) {
				Double zsr = Double.valueOf(homePageData.getZsr());
				if(zsr>0){
					if(rethomePageDataList3.size()<10){
						rethomePageDataList3.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("afterten_zsr", rethomePageDataList3);
			
			homePageDataList = sortNew(homePageDataList, "persons", "ASC"); 
			for (HomePageData homePageData : homePageDataList) {
				Double persons = Double.valueOf(homePageData.getPersons());
				if(persons>0){
					if(rethomePageDataList4.size()<10){
						rethomePageDataList4.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("afterten_kll", rethomePageDataList4);
			
			homePageDataList = sortNew(homePageDataList, "kzl", "ASC"); 
			for (HomePageData homePageData : homePageDataList) {
				Double kzl = Double.valueOf(homePageData.getKzl());
				if(kzl>0){
					if(rethomePageDataList5.size()<10){
						rethomePageDataList5.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("afterten_kzl", rethomePageDataList5);
			
			homePageDataList = sortNew(homePageDataList, "zdl", "ASC"); 
			for (HomePageData homePageData : homePageDataList) {
				Double zdl = Double.valueOf(homePageData.getZdl());
				if(zdl>0){
					if(rethomePageDataList55.size()<10){
						rethomePageDataList55.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("afterten_zdl", rethomePageDataList55);
			//出港
			chomePageDataList = sortNew(chomePageDataList, "zsr", "DESC"); 
			for (HomePageData homePageData : chomePageDataList) {
				Double zsr = Double.valueOf(homePageData.getZsr());
				if(zsr>0){
					if(crethomePageDataList0.size()<10){
						crethomePageDataList0.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("ctopten_zsr", crethomePageDataList0);
			
			chomePageDataList = sortNew(chomePageDataList, "persons", "DESC"); 
			for (HomePageData homePageData : chomePageDataList) {
				Double persons = Double.valueOf(homePageData.getPersons());
				if(persons>0){
					if(crethomePageDataList1.size()<10){
						crethomePageDataList1.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("ctopten_kll", crethomePageDataList1);
			
			chomePageDataList = sortNew(chomePageDataList, "kzl", "DESC"); 
			for (HomePageData homePageData : chomePageDataList) {
				Double kzl = Double.valueOf(homePageData.getKzl());
				if(kzl>0){
					if(crethomePageDataList2.size()<10){
						crethomePageDataList2.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("ctopten_kzl", crethomePageDataList2);
			
			chomePageDataList = sortNew(chomePageDataList, "zdl", "DESC"); 
			for (HomePageData homePageData : chomePageDataList) {
				Double zdl = Double.valueOf(homePageData.getZdl());
				if(zdl>0){
					if(crethomePageDataList22.size()<10){
						crethomePageDataList22.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("ctopten_zdl", crethomePageDataList22);
			
			chomePageDataList = sortNew(chomePageDataList, "zsr", "ASC"); 
			for (HomePageData homePageData : chomePageDataList) {
				Double zsr = Double.valueOf(homePageData.getZsr());
				if(zsr>0){
					if(crethomePageDataList3.size()<10){
						crethomePageDataList3.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("cafterten_zsr", crethomePageDataList3);
			
			chomePageDataList = sortNew(chomePageDataList, "persons", "ASC"); 
			for (HomePageData homePageData : chomePageDataList) {
				Double persons = Double.valueOf(homePageData.getPersons());
				if(persons>0){
					if(crethomePageDataList4.size()<10){
						crethomePageDataList4.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("cafterten_kll", crethomePageDataList4);
			
			chomePageDataList = sortNew(chomePageDataList, "kzl", "ASC"); 
			for (HomePageData homePageData : chomePageDataList) {
				Double kzl = Double.valueOf(homePageData.getKzl());
				if(kzl>0){
					if(crethomePageDataList5.size()<10){
						crethomePageDataList5.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("cafterten_kzl", crethomePageDataList5);
			
			chomePageDataList = sortNew(chomePageDataList, "zdl", "ASC"); 
			for (HomePageData homePageData : chomePageDataList) {
				Double zdl = Double.valueOf(homePageData.getZdl());
				if(zdl>0){
					if(crethomePageDataList55.size()<10){
						crethomePageDataList55.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("cafterten_zdl", crethomePageDataList55);
			//进港
			jhomePageDataList = sortNew(jhomePageDataList, "zsr", "DESC"); 
			for (HomePageData homePageData : jhomePageDataList) {
				Double zsr = Double.valueOf(homePageData.getZsr());
				if(zsr>0){
					if(jrethomePageDataList0.size()<10){
						jrethomePageDataList0.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("jtopten_zsr", jrethomePageDataList0);
			
			jhomePageDataList = sortNew(jhomePageDataList, "persons", "DESC"); 
			for (HomePageData homePageData : jhomePageDataList) {
				Double persons = Double.valueOf(homePageData.getPersons());
				if(persons>0){
					if(jrethomePageDataList1.size()<10){
						jrethomePageDataList1.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("jtopten_kll", jrethomePageDataList1);
			
			jhomePageDataList = sortNew(jhomePageDataList, "kzl", "DESC"); 
			for (HomePageData homePageData : jhomePageDataList) {
				Double kzl = Double.valueOf(homePageData.getKzl());
				if(kzl>0){
					if(jrethomePageDataList2.size()<10){
						jrethomePageDataList2.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("jtopten_kzl", jrethomePageDataList2);
			
			jhomePageDataList = sortNew(jhomePageDataList, "zdl", "DESC"); 
			for (HomePageData homePageData : jhomePageDataList) {
				Double zdl = Double.valueOf(homePageData.getZdl());
				if(zdl>0){
					if(jrethomePageDataList22.size()<10){
						jrethomePageDataList22.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("jtopten_zdl", jrethomePageDataList22);
			
			jhomePageDataList = sortNew(jhomePageDataList, "zsr", "ASC"); 
			for (HomePageData homePageData : jhomePageDataList) {
				Double zsr = Double.valueOf(homePageData.getZsr());
				if(zsr>0){
					if(jrethomePageDataList3.size()<10){
						jrethomePageDataList3.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("jafterten_zsr", jrethomePageDataList3);
			
			jhomePageDataList = sortNew(jhomePageDataList, "persons", "ASC"); 
			for (HomePageData homePageData : jhomePageDataList) {
				Double persons = Double.valueOf(homePageData.getPersons());
				if(persons>0){
					if(jrethomePageDataList4.size()<10){
						jrethomePageDataList4.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("jafterten_kll", jrethomePageDataList4);
			
			jhomePageDataList = sortNew(jhomePageDataList, "kzl", "ASC"); 
			for (HomePageData homePageData : jhomePageDataList) {
				Double kzl = Double.valueOf(homePageData.getKzl());
				if(kzl>0){
					if(jrethomePageDataList5.size()<10){
						jrethomePageDataList5.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("jafterten_kzl", jrethomePageDataList5);
			
			jhomePageDataList = sortNew(jhomePageDataList, "zdl", "ASC"); 
			for (HomePageData homePageData : jhomePageDataList) {
				Double zdl = Double.valueOf(homePageData.getZdl());
				if(zdl>0){
					if(jrethomePageDataList55.size()<10){
						jrethomePageDataList55.add(homePageData);
					}else{
						break;
					}
				}
			}
			retmap.put("jafterten_zdl", jrethomePageDataList55);
			//汇总-所有
			int zkl = 0;
			double zsr = 0.0;
			int zsk = 0;
			int ztd = 0;
			//汇总-过站
			int pzkl = 0;
			double pzsr = 0.0;
			int pzsk = 0;
			int pztd = 0;
			//汇总-甩飞
			int tzkl = 0;
			double tzsr = 0.0;
			int tzsk = 0;
			int tztd = 0;
			//汇总-不包含过站也不包含甩飞
			int nzkl = 0;
			double nzsr = 0.0;
			int nzsk = 0;
			int nztd = 0;
			//出港-所有
			int czkl = 0;
			double czsr = 0.0;
			int czsk = 0;
			int cztd = 0;
			//出港-过站
			int cpzkl = 0;
			double cpzsr = 0.0;
			int cpzsk = 0;
			int cpztd = 0;
			//出港-甩飞
			int ctzkl = 0;
			double ctzsr = 0.0;
			int ctzsk = 0;
			int ctztd = 0;
			//出港-既不包含过站也不包含甩飞
			int cnzkl = 0;
			double cnzsr = 0.0;
			int cnzsk = 0;
			int cnztd = 0;
			//进港-所有
			int jzkl = 0;
			double jzsr = 0.0;
			int jzsk = 0;
			int jztd = 0;
			//进港-过站
			int jpzkl = 0;
			double jpzsr = 0.0;
			int jpzsk = 0;
			int jpztd = 0;
			//进港-甩飞
			int jtzkl = 0;
			double jtzsr = 0.0;
			int jtzsk = 0;
			int jtztd = 0;
			//进港-既不包含过站也不包含甩飞
			int jnzkl = 0;
			double jnzsr = 0.0;
			int jnzsk = 0;
			int jnztd = 0;
			//获取相关的班次
			//总班次-所有
			int zbc = 0;
			//总班次-包含过站
			int pzbc = 0;
			//总班次-包含甩飞
			int tzbc = 0;
			//总班次-既不包含过站也不包含甩飞
			int nzbc = 0;
			//出港总班次-所有
			int czbc = 0;
			//出港总班次-包含过站
			int cpzbc = 0;
			//出港总班次-包含甩飞
			int ctzbc = 0;
			//出港总班次-既不包含过站也不包含甩飞
			int cnzbc = 0;
			//进港总班次-所有
			int jzbc = 0;
			//进港总班次-包含过站
			int jpzbc = 0;
			//进港总班次-包含甩飞
			int jtzbc = 0;
			//进港总班次-既不包含过站也不包含甩飞
			int jnzbc = 0;
			//出港集合-所有
			List<Z_Airdata> list1 = new ArrayList<Z_Airdata>();
			//出港集合-包含过站
			List<Z_Airdata> plist1 = new ArrayList<Z_Airdata>();
			//出港集合-包含甩飞
			List<Z_Airdata> tlist1 = new ArrayList<Z_Airdata>();
			//出港集合-既不包含过站也不包含甩飞
			List<Z_Airdata> nlist1 = new ArrayList<Z_Airdata>();
			//进港集合-所有
			List<Z_Airdata> list2 = new ArrayList<Z_Airdata>();
			//进港集合-包含过站
			List<Z_Airdata> plist2 = new ArrayList<Z_Airdata>();
			//进港集合-包含甩飞
			List<Z_Airdata> tlist2 = new ArrayList<Z_Airdata>();
			//进港集合-既不包含过站也不包含甩飞
			List<Z_Airdata> nlist2 = new ArrayList<Z_Airdata>();
			for (int i=0;i<todayList.size();i++) {
				//判断时间区间
				Z_Airdata obj = todayList.get(i);
				//设置部分公共局部变量
				String fltAroute = obj.getFlt_Rte_Cd();
				//包含过站与甩飞
				zsr = zsr + obj.getTotalNumber();
				zkl = zkl + obj.getPgs_Per_Cls();
				zsk = zsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
				ztd = ztd + obj.getGrp_Nbr();
				if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
					czsr = czsr + obj.getTotalNumber();
					czkl = czkl + obj.getPgs_Per_Cls();
					czsk = czsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
					cztd = cztd + obj.getGrp_Nbr();
				}
				if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
					jzsr = jzsr + obj.getTotalNumber();
					jzkl = jzkl + obj.getPgs_Per_Cls();
					jzsk = jzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
					jztd = jztd + obj.getGrp_Nbr();
				}
				if(i==0){
					//所有
					zbc++;
					pzbc++;
					tzbc++;
					nzbc++;
					if(obj.getDpt_AirPt_Cd().equals(airport)){
						list1.add(obj);
					}else if(obj.getArrv_Airpt_Cd().equals(airport)){
						list2.add(obj);
					}
					if((airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd()))
							||(airport.equals(fltAroute.substring(3, 6))&&fltAroute.length()==9
							&&!airport.equals(obj.getDpt_AirPt_Cd())&&!airport.equals(obj.getArrv_Airpt_Cd()))){//包含过站
						pzsr = pzsr + obj.getTotalNumber();
						pzkl = pzkl + obj.getPgs_Per_Cls();
						pzsk = pzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
						pztd = pztd + obj.getGrp_Nbr();
						if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
							cpzsr = cpzsr + obj.getTotalNumber();
							cpzkl = cpzkl + obj.getPgs_Per_Cls();
							cpzsk = cpzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							cpztd = cpztd + obj.getGrp_Nbr();
						}
						if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
							jpzsr = jpzsr + obj.getTotalNumber();
							jpzkl = jpzkl + obj.getPgs_Per_Cls();
							jpzsk = jpzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							jpztd = jpztd + obj.getGrp_Nbr();
						}
						if(obj.getDpt_AirPt_Cd().equals(airport)){
							plist1.add(obj);
						}else if(obj.getArrv_Airpt_Cd().equals(airport)){
							plist2.add(obj);
						}
					}
					if((airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd())
							||((fltAroute.startsWith(airport)||fltAroute.endsWith(airport))
									&&!airport.equals(obj.getDpt_AirPt_Cd())&&!airport.equals(obj.getArrv_Airpt_Cd())))){//包含甩飞
						tzsr = tzsr + obj.getTotalNumber();
						tzkl = tzkl + obj.getPgs_Per_Cls();
						tzsk = tzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
						tztd = tztd + obj.getGrp_Nbr();
						if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
							ctzsr = ctzsr + obj.getTotalNumber();
							ctzkl = ctzkl + obj.getPgs_Per_Cls();
							ctzsk = ctzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							ctztd = ctztd + obj.getGrp_Nbr();
						}
						if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
							jtzsr = jtzsr + obj.getTotalNumber();
							jtzkl = jtzkl + obj.getPgs_Per_Cls();
							jtzsk = jtzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							jtztd = jtztd + obj.getGrp_Nbr();
						}
						if(obj.getDpt_AirPt_Cd().equals(airport)){
							tlist1.add(obj);
						}else if(obj.getArrv_Airpt_Cd().equals(airport)){
							tlist2.add(obj);
						}
					}
					if(airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd())){//不含过站与甩飞
						nzsr = nzsr + obj.getTotalNumber();
						nzkl = nzkl + obj.getPgs_Per_Cls();
						nzsk = nzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
						nztd = nztd + obj.getGrp_Nbr();
						if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
							cnzsr = cnzsr + obj.getTotalNumber();
							cnzkl = cnzkl + obj.getPgs_Per_Cls();
							cnzsk = cnzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							cnztd = cnztd + obj.getGrp_Nbr();
						}
						if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
							jnzsr = jnzsr + obj.getTotalNumber();
							jnzkl = jnzkl + obj.getPgs_Per_Cls();
							jnzsk = jnzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							jnztd = jnztd + obj.getGrp_Nbr();
						}
						if(obj.getDpt_AirPt_Cd().equals(airport)){
							nlist1.add(obj);
						}else{
							nlist2.add(obj);
						}
					}
				}else if(i!=todayList.size()-1){
					//所有
					Z_Airdata prevObj = todayList.get(i-1);
					Date prevDate = prevObj.getLcl_Dpt_Day();
					Date date = obj.getLcl_Dpt_Day();
					if(prevDate.equals(date)){
						String prevFlt = prevObj.getFlt_Nbr();
						String flt = obj.getFlt_Nbr();
						if(prevFlt.equals(flt)){
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								list1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								list2.add(obj);
							}
						}else{
							zbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									czbc += list1.size();
									jzbc += list2.size();
								}else{
									czbc += (int)Math.ceil(list1.size()/2.0);
									jzbc += (int)Math.ceil(list2.size()/2.0);
								}
							}else{
								czbc += list1.size();
								jzbc += list2.size();
							}
							list1 = new ArrayList<Z_Airdata>();
							list2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								list1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								list2.add(obj);
							}
						}
					}else{
						zbc++;
						//获取原有数据
						String fltline = prevObj.getFlt_Rte_Cd();
						if(fltline.length()==9){
							String midAirport = fltline.substring(3,6);
							if(midAirport.equals(airport)){//经停
								czbc += list1.size();
								jzbc += list2.size();
							}else{
								czbc += (int)Math.ceil(list1.size()/2.0);
								jzbc += (int)Math.ceil(list2.size()/2.0);
							}
						}else{
							czbc += list1.size();
							jzbc += list2.size();
						}
						list1 = new ArrayList<Z_Airdata>();
						list2 = new ArrayList<Z_Airdata>();
						if(obj.getDpt_AirPt_Cd().equals(airport)){
							list1.add(obj);
						}else if(obj.getArrv_Airpt_Cd().equals(airport)){
							list2.add(obj);
						}
					}
					if((airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd()))
							||(airport.equals(fltAroute.substring(3, 6))&&fltAroute.length()==9
							&&!airport.equals(obj.getDpt_AirPt_Cd())&&!airport.equals(obj.getArrv_Airpt_Cd()))){//包含过站
						pzsr = pzsr + obj.getTotalNumber();
						pzkl = pzkl + obj.getPgs_Per_Cls();
						pzsk = pzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
						pztd = pztd + obj.getGrp_Nbr();
						if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
							cpzsr = cpzsr + obj.getTotalNumber();
							cpzkl = cpzkl + obj.getPgs_Per_Cls();
							cpzsk = cpzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							cpztd = cpztd + obj.getGrp_Nbr();
						}
						if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
							jpzsr = jpzsr + obj.getTotalNumber();
							jpzkl = jpzkl + obj.getPgs_Per_Cls();
							jpzsk = jpzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							jpztd = jpztd + obj.getGrp_Nbr();
						}
//						Z_Airdata prevObj = todayList.get(i-1);
//						Date prevDate = prevObj.getLcl_Dpt_Day();
//						Date date = obj.getLcl_Dpt_Day();
						if(prevDate.equals(date)){
							String prevFlt = prevObj.getFlt_Nbr();
							String flt = obj.getFlt_Nbr();
							if(prevFlt.equals(flt)){
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									plist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									plist2.add(obj);
								}
							}else{
								pzbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}else{
										cpzbc += (int)Math.ceil(plist1.size()/2.0);
										jpzbc += (int)Math.ceil(plist2.size()/2.0);
									}
								}else{
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}
								plist1 = new ArrayList<Z_Airdata>();
								plist2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									plist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									plist2.add(obj);
								}
							}
						}else{
							pzbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}else{
									cpzbc += (int)Math.ceil(plist1.size()/2.0);
									jpzbc += (int)Math.ceil(plist2.size()/2.0);
								}
							}else{
								cpzbc += plist1.size();
								jpzbc += plist2.size();
							}
							plist1 = new ArrayList<Z_Airdata>();
							plist2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								plist1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								plist2.add(obj);
							}
						}
					}else{
						if(prevDate.equals(date)){
							String prevFlt = prevObj.getFlt_Nbr();
							String flt = obj.getFlt_Nbr();
							if(!prevFlt.equals(flt)){
								pzbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}else{
										cpzbc += (int)Math.ceil(plist1.size()/2.0);
										jpzbc += (int)Math.ceil(plist2.size()/2.0);
									}
								}else{
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}
								plist1 = new ArrayList<Z_Airdata>();
								plist2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									plist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									plist2.add(obj);
								}
							}
						}else{
							pzbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}else{
									cpzbc += (int)Math.ceil(plist1.size()/2.0);
									jpzbc += (int)Math.ceil(plist2.size()/2.0);
								}
							}else{
								cpzbc += plist1.size();
								jpzbc += plist2.size();
							}
							plist1 = new ArrayList<Z_Airdata>();
							plist2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								plist1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								plist2.add(obj);
							}
						}
					}
					if((airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd())
							||((fltAroute.startsWith(airport)||fltAroute.endsWith(airport))
									&&!airport.equals(obj.getDpt_AirPt_Cd())&&!airport.equals(obj.getArrv_Airpt_Cd())))){//包含甩飞
						tzsr = tzsr + obj.getTotalNumber();
						tzkl = tzkl + obj.getPgs_Per_Cls();
						tzsk = tzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
						tztd = tztd + obj.getGrp_Nbr();
						if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
							ctzsr = ctzsr + obj.getTotalNumber();
							ctzkl = ctzkl + obj.getPgs_Per_Cls();
							ctzsk = ctzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							ctztd = ctztd + obj.getGrp_Nbr();
						}
						if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
							jtzsr = jtzsr + obj.getTotalNumber();
							jtzkl = jtzkl + obj.getPgs_Per_Cls();
							jtzsk = jtzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							jtztd = jtztd + obj.getGrp_Nbr();
						}
//						Z_Airdata prevObj = todayList.get(i-1);
//						Date prevDate = prevObj.getLcl_Dpt_Day();
//						Date date = obj.getLcl_Dpt_Day();
						if(prevDate.equals(date)){
							String prevFlt = prevObj.getFlt_Nbr();
							String flt = obj.getFlt_Nbr();
							if(prevFlt.equals(flt)){
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									tlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									tlist2.add(obj);
								}
							}else{
								tzbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}else{
										ctzbc += (int)Math.ceil(tlist1.size()/2.0);
										jtzbc += (int)Math.ceil(tlist2.size()/2.0);
									}
								}else{
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}
								tlist1 = new ArrayList<Z_Airdata>();
								tlist2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									tlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									tlist2.add(obj);
								}
							}
						}else{
							tzbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}else{
									ctzbc += (int)Math.ceil(tlist1.size()/2.0);
									jtzbc += (int)Math.ceil(tlist2.size()/2.0);
								}
							}else{
								ctzbc += tlist1.size();
								jtzbc += tlist2.size();
							}
							tlist1 = new ArrayList<Z_Airdata>();
							tlist2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								tlist1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								tlist2.add(obj);
							}
						}
					}else{
						if(prevDate.equals(date)){
							String prevFlt = prevObj.getFlt_Nbr();
							String flt = obj.getFlt_Nbr();
							if(!prevFlt.equals(flt)){
								tzbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}else{
										ctzbc += (int)Math.ceil(tlist1.size()/2.0);
										jtzbc += (int)Math.ceil(tlist2.size()/2.0);
									}
								}else{
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}
								tlist1 = new ArrayList<Z_Airdata>();
								tlist2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									tlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									tlist2.add(obj);
								}
							}
						}else{
							tzbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}else{
									ctzbc += (int)Math.ceil(tlist1.size()/2.0);
									jtzbc += (int)Math.ceil(tlist2.size()/2.0);
								}
							}else{
								ctzbc += tlist1.size();
								jtzbc += tlist2.size();
							}
							tlist1 = new ArrayList<Z_Airdata>();
							tlist2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								tlist1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								tlist2.add(obj);
							}
						}
					}
					if(airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd())){//不含过站与甩飞
						nzsr = nzsr + obj.getTotalNumber();
						nzkl = nzkl + obj.getPgs_Per_Cls();
						nzsk = nzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
						nztd = nztd + obj.getGrp_Nbr();
						if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
							cnzsr = cnzsr + obj.getTotalNumber();
							cnzkl = cnzkl + obj.getPgs_Per_Cls();
							cnzsk = cnzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							cnztd = cnztd + obj.getGrp_Nbr();
						}
						if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
							jnzsr = jnzsr + obj.getTotalNumber();
							jnzkl = jnzkl + obj.getPgs_Per_Cls();
							jnzsk = jnzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							jnztd = jnztd + obj.getGrp_Nbr();
						}
//						Z_Airdata prevObj = todayList.get(i-1);
//						Date prevDate = prevObj.getLcl_Dpt_Day();
//						Date date = obj.getLcl_Dpt_Day();
						if(prevDate.equals(date)){
							String prevFlt = prevObj.getFlt_Nbr();
							String flt = obj.getFlt_Nbr();
							if(prevFlt.equals(flt)){
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									nlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									nlist2.add(obj);
								}
							}else{
								nzbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}else{
										cnzbc += (int)Math.ceil(nlist1.size()/2.0);
										jnzbc += (int)Math.ceil(nlist2.size()/2.0);
									}
								}else{
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}
								nlist1 = new ArrayList<Z_Airdata>();
								nlist2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									nlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									nlist2.add(obj);
								}
							}
						}else{
							nzbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}else{
									cnzbc += (int)Math.ceil(nlist1.size()/2.0);
									jnzbc += (int)Math.ceil(nlist2.size()/2.0);
								}
							}else{
								cnzbc += nlist1.size();
								jnzbc += nlist2.size();
							}
							nlist1 = new ArrayList<Z_Airdata>();
							nlist2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								nlist1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								nlist2.add(obj);
							}
						}
					}else{
						if(prevDate.equals(date)){
							String prevFlt = prevObj.getFlt_Nbr();
							String flt = obj.getFlt_Nbr();
							if(!prevFlt.equals(flt)){
								nzbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}else{
										cnzbc += (int)Math.ceil(nlist1.size()/2.0);
										jnzbc += (int)Math.ceil(nlist2.size()/2.0);
									}
								}else{
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}
								nlist1 = new ArrayList<Z_Airdata>();
								nlist2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									nlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									nlist2.add(obj);
								}
							}
						}else{
							nzbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}else{
									cnzbc += (int)Math.ceil(nlist1.size()/2.0);
									jnzbc += (int)Math.ceil(nlist2.size()/2.0);
								}
							}else{
								cnzbc += nlist1.size();
								jnzbc += nlist2.size();
							}
							nlist1 = new ArrayList<Z_Airdata>();
							nlist2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								nlist1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								nlist2.add(obj);
							}
						}
					}
				}else{
					//所有
					Z_Airdata prevObj = todayList.get(i-1);
					Date prevDate = prevObj.getLcl_Dpt_Day();
					Date date = obj.getLcl_Dpt_Day();
					if(prevDate.equals(date)){
						String prevFlt = prevObj.getFlt_Nbr();
						String flt = obj.getFlt_Nbr();
						if(prevFlt.equals(flt)){
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								list1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								list2.add(obj);
							}
							//获取原有数据
							String fltline = obj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									czbc += list1.size();
									jzbc += list2.size();
								}else{
									czbc += (int)Math.ceil(list1.size()/2.0);
									jzbc += (int)Math.ceil(list2.size()/2.0);
								}
							}else{
								czbc += list1.size();
								jzbc += list2.size();
							}
						}else{
							zbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									czbc += list1.size();
									jzbc += list2.size();
								}else{
									czbc += (int)Math.ceil(list1.size()/2.0);
									jzbc += (int)Math.ceil(list2.size()/2.0);
								}
							}else{
								czbc += list1.size();
								jzbc += list2.size();
							}
							list1 = new ArrayList<Z_Airdata>();
							list2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								list1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								list2.add(obj);
							}
							//获取原有数据
							fltline = obj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									czbc += list1.size();
									jzbc += list2.size();
								}else{
									czbc += (int)Math.ceil(list1.size()/2.0);
									jzbc += (int)Math.ceil(list2.size()/2.0);
								}
							}else{
								czbc += list1.size();
								jzbc += list2.size();
							}
						}
					}else{
						zbc++;
						//获取原有数据
						String fltline = prevObj.getFlt_Rte_Cd();
						if(fltline.length()==9){
							String midAirport = fltline.substring(3,6);
							if(midAirport.equals(airport)){//经停
								czbc += list1.size();
								jzbc += list2.size();
							}else{
								czbc += (int)Math.ceil(list1.size()/2.0);
								jzbc += (int)Math.ceil(list2.size()/2.0);
							}
						}else{
							czbc += list1.size();
							jzbc += list2.size();
						}
						list1 = new ArrayList<Z_Airdata>();
						list2 = new ArrayList<Z_Airdata>();
						if(obj.getDpt_AirPt_Cd().equals(airport)){
							list1.add(obj);
						}else if(obj.getArrv_Airpt_Cd().equals(airport)){
							list2.add(obj);
						}
						//获取原有数据
						fltline = obj.getFlt_Rte_Cd();
						if(fltline.length()==9){
							String midAirport = fltline.substring(3,6);
							if(midAirport.equals(airport)){//经停
								czbc += list1.size();
								jzbc += list2.size();
							}else{
								czbc += (int)Math.ceil(list1.size()/2.0);
								jzbc += (int)Math.ceil(list2.size()/2.0);
							}
						}else{
							czbc += list1.size();
							jzbc += list2.size();
						}
					}
					if((airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd()))
							||(airport.equals(fltAroute.substring(3, 6))&&fltAroute.length()==9
							&&!airport.equals(obj.getDpt_AirPt_Cd())&&!airport.equals(obj.getArrv_Airpt_Cd()))){//包含过站
						pzsr = pzsr + obj.getTotalNumber();
						pzkl = pzkl + obj.getPgs_Per_Cls();
						pzsk = pzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
						pztd = pztd + obj.getGrp_Nbr();
						if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
							cpzsr = cpzsr + obj.getTotalNumber();
							cpzkl = cpzkl + obj.getPgs_Per_Cls();
							cpzsk = cpzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							cpztd = cpztd + obj.getGrp_Nbr();
						}
						if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
							jpzsr = jpzsr + obj.getTotalNumber();
							jpzkl = jpzkl + obj.getPgs_Per_Cls();
							jpzsk = jpzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							jpztd = jpztd + obj.getGrp_Nbr();
						}
//						Z_Airdata prevObj = todayList.get(i-1);
//						Date prevDate = prevObj.getLcl_Dpt_Day();
//						Date date = obj.getLcl_Dpt_Day();
						if(prevDate.equals(date)){
							String prevFlt = prevObj.getFlt_Nbr();
							String flt = obj.getFlt_Nbr();
							if(prevFlt.equals(flt)){
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									plist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									plist2.add(obj);
								}
								//获取原有数据
								String fltline = obj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}else{
										cpzbc += (int)Math.ceil(plist1.size()/2.0);
										jpzbc += (int)Math.ceil(plist2.size()/2.0);
									}
								}else{
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}
							}else{
								pzbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}else{
										cpzbc += (int)Math.ceil(plist1.size()/2.0);
										jpzbc += (int)Math.ceil(plist2.size()/2.0);
									}
								}else{
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}
								plist1 = new ArrayList<Z_Airdata>();
								plist2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									plist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									plist2.add(obj);
								}
								//获取原有数据
								fltline = obj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}else{
										cpzbc += (int)Math.ceil(plist1.size()/2.0);
										jpzbc += (int)Math.ceil(plist2.size()/2.0);
									}
								}else{
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}
							}
						}else{
							pzbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}else{
									cpzbc += (int)Math.ceil(plist1.size()/2.0);
									jpzbc += (int)Math.ceil(plist2.size()/2.0);
								}
							}else{
								cpzbc += plist1.size();
								jpzbc += plist2.size();
							}
							plist1 = new ArrayList<Z_Airdata>();
							plist2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								plist1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								plist2.add(obj);
							}
							//获取原有数据
							fltline = obj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}else{
									cpzbc += (int)Math.ceil(plist1.size()/2.0);
									jpzbc += (int)Math.ceil(plist2.size()/2.0);
								}
							}else{
								cpzbc += plist1.size();
								jpzbc += plist2.size();
							}
						}
					}else{
//						Z_Airdata prevObj = todayList.get(i-1);
//						Date prevDate = prevObj.getLcl_Dpt_Day();
//						Date date = obj.getLcl_Dpt_Day();
						if(prevDate.equals(date)){
							String prevFlt = prevObj.getFlt_Nbr();
							String flt = obj.getFlt_Nbr();
							if(prevFlt.equals(flt)){
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									plist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									plist2.add(obj);
								}
								//获取原有数据
								String fltline = obj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}else{
										cpzbc += (int)Math.ceil(plist1.size()/2.0);
										jpzbc += (int)Math.ceil(plist2.size()/2.0);
									}
								}else{
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}
							}else{
								pzbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}else{
										cpzbc += (int)Math.ceil(plist1.size()/2.0);
										jpzbc += (int)Math.ceil(plist2.size()/2.0);
									}
								}else{
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}
								plist1 = new ArrayList<Z_Airdata>();
								plist2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									plist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									plist2.add(obj);
								}
								//获取原有数据
								fltline = obj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}else{
										cpzbc += (int)Math.ceil(plist1.size()/2.0);
										jpzbc += (int)Math.ceil(plist2.size()/2.0);
									}
								}else{
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}
							}
						}else{
							pzbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}else{
									cpzbc += (int)Math.ceil(plist1.size()/2.0);
									jpzbc += (int)Math.ceil(plist2.size()/2.0);
								}
							}else{
								cpzbc += plist1.size();
								jpzbc += plist2.size();
							}
							plist1 = new ArrayList<Z_Airdata>();
							plist2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								plist1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								plist2.add(obj);
							}
							//获取原有数据
							fltline = obj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									cpzbc += plist1.size();
									jpzbc += plist2.size();
								}else{
									cpzbc += (int)Math.ceil(plist1.size()/2.0);
									jpzbc += (int)Math.ceil(plist2.size()/2.0);
								}
							}else{
								cpzbc += plist1.size();
								jpzbc += plist2.size();
							}
						}
					}
					if((airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd())
							||((fltAroute.startsWith(airport)||fltAroute.endsWith(airport))
									&&!airport.equals(obj.getDpt_AirPt_Cd())&&!airport.equals(obj.getArrv_Airpt_Cd())))){//包含甩飞
						tzsr = tzsr + obj.getTotalNumber();
						tzkl = tzkl + obj.getPgs_Per_Cls();
						tzsk = tzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
						tztd = tztd + obj.getGrp_Nbr();
						if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
							ctzsr = ctzsr + obj.getTotalNumber();
							ctzkl = ctzkl + obj.getPgs_Per_Cls();
							ctzsk = ctzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							ctztd = ctztd + obj.getGrp_Nbr();
						}
						if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
							jtzsr = jtzsr + obj.getTotalNumber();
							jtzkl = jtzkl + obj.getPgs_Per_Cls();
							jtzsk = jtzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							jtztd = jtztd + obj.getGrp_Nbr();
						}
//						Z_Airdata prevObj = todayList.get(i-1);
//						Date prevDate = prevObj.getLcl_Dpt_Day();
//						Date date = obj.getLcl_Dpt_Day();
						if(prevDate.equals(date)){
							String prevFlt = prevObj.getFlt_Nbr();
							String flt = obj.getFlt_Nbr();
							if(prevFlt.equals(flt)){
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									tlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									tlist2.add(obj);
								}
								//获取原有数据
								String fltline = obj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}else{
										ctzbc += (int)Math.ceil(tlist1.size()/2.0);
										jtzbc += (int)Math.ceil(tlist2.size()/2.0);
									}
								}else{
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}
							}else{
								tzbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}else{
										ctzbc += (int)Math.ceil(tlist1.size()/2.0);
										jtzbc += (int)Math.ceil(tlist2.size()/2.0);
									}
								}else{
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}
								tlist1 = new ArrayList<Z_Airdata>();
								tlist2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									tlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									tlist2.add(obj);
								}
								//获取原有数据
								fltline = obj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}else{
										ctzbc += (int)Math.ceil(tlist1.size()/2.0);
										jtzbc += (int)Math.ceil(tlist2.size()/2.0);
									}
								}else{
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}
							}
						}else{
							tzbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}else{
									ctzbc += (int)Math.ceil(tlist1.size()/2.0);
									jtzbc += (int)Math.ceil(tlist2.size()/2.0);
								}
							}else{
								ctzbc += tlist1.size();
								jtzbc += tlist2.size();
							}
							tlist1 = new ArrayList<Z_Airdata>();
							tlist2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								tlist1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								tlist2.add(obj);
							}
							//获取原有数据
							fltline = obj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}else{
									ctzbc += (int)Math.ceil(tlist1.size()/2.0);
									jtzbc += (int)Math.ceil(tlist2.size()/2.0);
								}
							}else{
								ctzbc += tlist1.size();
								jtzbc += tlist2.size();
							}
						}
					}else{
//						Z_Airdata prevObj = todayList.get(i-1);
//						Date prevDate = prevObj.getLcl_Dpt_Day();
//						Date date = obj.getLcl_Dpt_Day();
						if(prevDate.equals(date)){
							String prevFlt = prevObj.getFlt_Nbr();
							String flt = obj.getFlt_Nbr();
							if(prevFlt.equals(flt)){
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									tlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									tlist2.add(obj);
								}
								//获取原有数据
								String fltline = obj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}else{
										ctzbc += (int)Math.ceil(tlist1.size()/2.0);
										jtzbc += (int)Math.ceil(tlist2.size()/2.0);
									}
								}else{
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}
							}else{
								tzbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}else{
										ctzbc += (int)Math.ceil(tlist1.size()/2.0);
										jtzbc += (int)Math.ceil(tlist2.size()/2.0);
									}
								}else{
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}
								tlist1 = new ArrayList<Z_Airdata>();
								tlist2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									tlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									tlist2.add(obj);
								}
								//获取原有数据
								fltline = obj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}else{
										ctzbc += (int)Math.ceil(tlist1.size()/2.0);
										jtzbc += (int)Math.ceil(tlist2.size()/2.0);
									}
								}else{
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}
							}
						}else{
							tzbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}else{
									ctzbc += (int)Math.ceil(tlist1.size()/2.0);
									jtzbc += (int)Math.ceil(tlist2.size()/2.0);
								}
							}else{
								ctzbc += tlist1.size();
								jtzbc += tlist2.size();
							}
							tlist1 = new ArrayList<Z_Airdata>();
							tlist2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								tlist1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								tlist2.add(obj);
							}
							//获取原有数据
							fltline = obj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									ctzbc += tlist1.size();
									jtzbc += tlist2.size();
								}else{
									ctzbc += (int)Math.ceil(tlist1.size()/2.0);
									jtzbc += (int)Math.ceil(tlist2.size()/2.0);
								}
							}else{
								ctzbc += tlist1.size();
								jtzbc += tlist2.size();
							}
						}
					}
					if(airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd())){//不含过站与甩飞
						nzsr = nzsr + obj.getTotalNumber();
						nzkl = nzkl + obj.getPgs_Per_Cls();
						nzsk = nzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
						nztd = nztd + obj.getGrp_Nbr();
						if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
							cnzsr = cnzsr + obj.getTotalNumber();
							cnzkl = cnzkl + obj.getPgs_Per_Cls();
							cnzsk = cnzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							cnztd = cnztd + obj.getGrp_Nbr();
						}
						if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
							jnzsr = jnzsr + obj.getTotalNumber();
							jnzkl = jnzkl + obj.getPgs_Per_Cls();
							jnzsk = jnzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							jnztd = jnztd + obj.getGrp_Nbr();
						}
//						Z_Airdata prevObj = todayList.get(i-1);
//						Date prevDate = prevObj.getLcl_Dpt_Day();
//						Date date = obj.getLcl_Dpt_Day();
						if(prevDate.equals(date)){
							String prevFlt = prevObj.getFlt_Nbr();
							String flt = obj.getFlt_Nbr();
							if(prevFlt.equals(flt)){
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									nlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									nlist2.add(obj);
								}
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}else{
										cnzbc += (int)Math.ceil(nlist1.size()/2.0);
										jnzbc += (int)Math.ceil(nlist2.size()/2.0);
									}
								}else{
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}
							}else{
								nzbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}else{
										cnzbc += (int)Math.ceil(nlist1.size()/2.0);
										jnzbc += (int)Math.ceil(nlist2.size()/2.0);
									}
								}else{
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}
								nlist1 = new ArrayList<Z_Airdata>();
								nlist2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									nlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									nlist2.add(obj);
								}
								//获取原有数据
								fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}else{
										cnzbc += (int)Math.ceil(nlist1.size()/2.0);
										jnzbc += (int)Math.ceil(nlist2.size()/2.0);
									}
								}else{
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}
							}
						}else{
							nzbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}else{
									cnzbc += (int)Math.ceil(nlist1.size()/2.0);
									jnzbc += (int)Math.ceil(nlist2.size()/2.0);
								}
							}else{
								cnzbc += nlist1.size();
								jnzbc += nlist2.size();
							}
							nlist1 = new ArrayList<Z_Airdata>();
							nlist2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								nlist1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								nlist2.add(obj);
							}
							//获取原有数据
							fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}else{
									cnzbc += (int)Math.ceil(nlist1.size()/2.0);
									jnzbc += (int)Math.ceil(nlist2.size()/2.0);
								}
							}else{
								cnzbc += nlist1.size();
								jnzbc += nlist2.size();
							}
						}
					}else{
//						Z_Airdata prevObj = todayList.get(i-1);
//						Date prevDate = prevObj.getLcl_Dpt_Day();
//						Date date = obj.getLcl_Dpt_Day();
						if(prevDate.equals(date)){
							String prevFlt = prevObj.getFlt_Nbr();
							String flt = obj.getFlt_Nbr();
							if(prevFlt.equals(flt)){
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									nlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									nlist2.add(obj);
								}
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}else{
										cnzbc += (int)Math.ceil(nlist1.size()/2.0);
										jnzbc += (int)Math.ceil(nlist2.size()/2.0);
									}
								}else{
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}
							}else{
								nzbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}else{
										cnzbc += (int)Math.ceil(nlist1.size()/2.0);
										jnzbc += (int)Math.ceil(nlist2.size()/2.0);
									}
								}else{
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}
								nlist1 = new ArrayList<Z_Airdata>();
								nlist2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									nlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									nlist2.add(obj);
								}
								//获取原有数据
								fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}else{
										cnzbc += (int)Math.ceil(nlist1.size()/2.0);
										jnzbc += (int)Math.ceil(nlist2.size()/2.0);
									}
								}else{
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}
							}
						}else{
							nzbc++;
							//获取原有数据
							String fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}else{
									cnzbc += (int)Math.ceil(nlist1.size()/2.0);
									jnzbc += (int)Math.ceil(nlist2.size()/2.0);
								}
							}else{
								cnzbc += nlist1.size();
								jnzbc += nlist2.size();
							}
							nlist1 = new ArrayList<Z_Airdata>();
							nlist2 = new ArrayList<Z_Airdata>();
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								nlist1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								nlist2.add(obj);
							}
							//获取原有数据
							fltline = prevObj.getFlt_Rte_Cd();
							if(fltline.length()==9){
								String midAirport = fltline.substring(3,6);
								if(midAirport.equals(airport)){//经停
									cnzbc += nlist1.size();
									jnzbc += nlist2.size();
								}else{
									cnzbc += (int)Math.ceil(nlist1.size()/2.0);
									jnzbc += (int)Math.ceil(nlist2.size()/2.0);
								}
							}else{
								cnzbc += nlist1.size();
								jnzbc += nlist2.size();
							}
						}
					}
				}
			}
			//汇总-所有
			retmap.put("zkl", zkl);
			retmap.put("zsr", df2.format(zsr));
			retmap.put("zsk", zsk);
			retmap.put("ztd", ztd);
			retmap.put("zbc", zbc);
			//汇总-包含过站
			retmap.put("pzkl", pzkl);
			retmap.put("pzsr", df2.format(pzsr));
			retmap.put("pzsk", pzsk);
			retmap.put("pztd", pztd);
			retmap.put("pzbc", pzbc);
			//汇总-包含甩飞
			retmap.put("tzkl", tzkl);
			retmap.put("tzsr", df2.format(tzsr));
			retmap.put("tzsk", tzsk);
			retmap.put("tztd", tztd);
			retmap.put("tzbc", tzbc);
			//汇总-不含过站，不含甩飞
			retmap.put("nzkl", nzkl);
			retmap.put("nzsr", df2.format(nzsr));
			retmap.put("nzsk", nzsk);
			retmap.put("nztd", nztd);
			retmap.put("nzbc", nzbc);
			//出港-所有
			retmap.put("czkl", czkl);
			retmap.put("czsr", df2.format(czsr));
			retmap.put("czsk", czsk);
			retmap.put("cztd", cztd);
			retmap.put("czbc", czbc);
			//出港-含过站
			retmap.put("cpzkl", cpzkl);
			retmap.put("cpzsr", df2.format(cpzsr));
			retmap.put("cpzsk", cpzsk);
			retmap.put("cpztd", cpztd);
			retmap.put("cpzbc", cpzbc);
			//出港-含甩飞
			retmap.put("ctzkl", ctzkl);
			retmap.put("ctzsr", df2.format(ctzsr));
			retmap.put("ctzsk", ctzsk);
			retmap.put("ctztd", ctztd);
			retmap.put("ctzbc", ctzbc);
			//出港-不含过站，不含甩飞
			retmap.put("cnzkl", cnzkl);
			retmap.put("cnzsr", df2.format(cnzsr));
			retmap.put("cnzsk", cnzsk);
			retmap.put("cnztd", cnztd);
			retmap.put("cnzbc", cnzbc);
			//进港-所有
			retmap.put("jzkl", jzkl);
			retmap.put("jzsr", df2.format(jzsr));
			retmap.put("jzsk", jzsk);
			retmap.put("jztd", jztd);
			retmap.put("jzbc", jzbc);
			//进港-含过站
			retmap.put("jpzkl", jpzkl);
			retmap.put("jpzsr", df2.format(jpzsr));
			retmap.put("jpzsk", jpzsk);
			retmap.put("jpztd", jpztd);
			retmap.put("jpzbc", jpzbc);
			//进港-含甩飞
			retmap.put("jtzkl", jtzkl);
			retmap.put("jtzsr", df2.format(jtzsr));
			retmap.put("jtzsk", jtzsk);
			retmap.put("jtztd", jtztd);
			retmap.put("jtzbc", jtzbc);
			//进港-不含过站，不含甩飞
			retmap.put("jnzkl", jnzkl);
			retmap.put("jnzsr", df2.format(jnzsr));
			retmap.put("jnzsk", jnzsk);
			retmap.put("jnztd", jnztd);
			retmap.put("jnzbc", jnzbc);
			//获取所有数据对应的汇总数据和列表
			//汇总-所有
			List<HomePageData> HomePageDataListzs = new ArrayList<HomePageData>();
			//汇总-含过站
			List<HomePageData> pHomePageDataListzs = new ArrayList<HomePageData>();
			//汇总-含甩飞
			List<HomePageData> tHomePageDataListzs = new ArrayList<HomePageData>();
			//汇总-不含过站与甩飞
			List<HomePageData> nHomePageDataListzs = new ArrayList<HomePageData>();
			//出港-所有
			List<HomePageData> cHomePageDataListzs = new ArrayList<HomePageData>();
			//出港-含过站
			List<HomePageData> cpHomePageDataListzs = new ArrayList<HomePageData>();
			//出港-含甩飞
			List<HomePageData> ctHomePageDataListzs = new ArrayList<HomePageData>();
			//出港-不含过站与甩飞
			List<HomePageData> cnHomePageDataListzs = new ArrayList<HomePageData>();
			//进港-所有
			List<HomePageData> jHomePageDataListzs = new ArrayList<HomePageData>();
			//进港-含过站
			List<HomePageData> jpHomePageDataListzs = new ArrayList<HomePageData>();
			//进港-含甩飞
			List<HomePageData> jtHomePageDataListzs = new ArrayList<HomePageData>();
			//进港-不含过站与甩飞
			List<HomePageData> jnHomePageDataListzs = new ArrayList<HomePageData>();
			if(days<0){
				//有数据的天数
				List<String> dayList = new ArrayList<String>();
				for (Z_Airdata z_Airdata : todayList) {
					String daytemp = sdf.format(z_Airdata.getLcl_Dpt_Day());
					if(!dayList.contains(daytemp)){
						dayList.add(daytemp);
					}
				}
				Collections.sort(dayList);
				for (String dayy : dayList) {
					//汇总-所有
					int zkltemp = 0;
					double zsrtemp = 0.0;
					//汇总-含过站
					int pzkltemp = 0;
					double pzsrtemp = 0.0;
					//汇总-含甩飞
					int tzkltemp = 0;
					double tzsrtemp = 0.0;
					//汇总-不含过站与甩飞
					int nzkltemp = 0;
					double nzsrtemp = 0.0;
					//出港-所有
					int czkltemp = 0;
					double czsrtemp = 0.0;
					//出港-含过站
					int cpzkltemp = 0;
					double cpzsrtemp = 0.0;
					//出港-含甩飞
					int ctzkltemp = 0;
					double ctzsrtemp = 0.0;
					//出港-不含过站与甩飞
					int cnzkltemp = 0;
					double cnzsrtemp = 0.0;
					//进港-所有
					int jzkltemp = 0;
					double jzsrtemp = 0.0;
					//进港-含过站
					int jpzkltemp = 0;
					double jpzsrtemp = 0.0;
					//进港-含甩飞
					int jtzkltemp = 0;
					double jtzsrtemp = 0.0;
					//进港-不含过站与甩飞
					int jnzkltemp = 0;
					double jnzsrtemp = 0.0;
					for (Z_Airdata z_Airdata : todayList) {
						String daytemp = sdf.format(z_Airdata.getLcl_Dpt_Day());
						String fltAroute = z_Airdata.getFlt_Rte_Cd();
						if(dayy.equals(daytemp)){
							zsrtemp = zsrtemp + z_Airdata.getTotalNumber()/1000.0;
							zkltemp = zkltemp + z_Airdata.getPgs_Per_Cls();
							if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
								czsrtemp = czsrtemp + z_Airdata.getTotalNumber()/1000.0;
								czkltemp = czkltemp + z_Airdata.getPgs_Per_Cls();
							}
							if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
								jzsrtemp = jzsrtemp + z_Airdata.getTotalNumber()/1000.0;
								jzkltemp = jzkltemp + z_Airdata.getPgs_Per_Cls();
							}
							if((airport.equals(z_Airdata.getDpt_AirPt_Cd())||airport.equals(z_Airdata.getArrv_Airpt_Cd()))
									||(airport.equals(fltAroute.substring(3, 6))&&fltAroute.length()==9
									&&!airport.equals(z_Airdata.getDpt_AirPt_Cd())&&!airport.equals(z_Airdata.getArrv_Airpt_Cd()))){//含过站
								pzsrtemp = pzsrtemp + z_Airdata.getTotalNumber()/1000.0;
								pzkltemp = pzkltemp + z_Airdata.getPgs_Per_Cls();
								if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
									cpzsrtemp = cpzsrtemp + z_Airdata.getTotalNumber()/1000.0;
									cpzkltemp = cpzkltemp + z_Airdata.getPgs_Per_Cls();
								}
								if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
									jpzsrtemp = jpzsrtemp + z_Airdata.getTotalNumber()/1000.0;
									jpzkltemp = jpzkltemp + z_Airdata.getPgs_Per_Cls();
								}
							}
							if((airport.equals(z_Airdata.getDpt_AirPt_Cd())||airport.equals(z_Airdata.getArrv_Airpt_Cd())
									||((fltAroute.startsWith(airport)||fltAroute.endsWith(airport))
											&&!airport.equals(z_Airdata.getDpt_AirPt_Cd())&&!airport.equals(z_Airdata.getArrv_Airpt_Cd())))){//含甩飞
								tzsrtemp = tzsrtemp + z_Airdata.getTotalNumber()/1000.0;
								tzkltemp = tzkltemp + z_Airdata.getPgs_Per_Cls();
								if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
									ctzsrtemp = ctzsrtemp + z_Airdata.getTotalNumber()/1000.0;
									ctzkltemp = ctzkltemp + z_Airdata.getPgs_Per_Cls();
								}
								if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
									jtzsrtemp = jtzsrtemp + z_Airdata.getTotalNumber()/1000.0;
									jtzkltemp = jtzkltemp + z_Airdata.getPgs_Per_Cls();
								}
							}
							if(airport.equals(z_Airdata.getDpt_AirPt_Cd())||airport.equals(z_Airdata.getArrv_Airpt_Cd())){//不会过站与甩飞
								nzsrtemp = nzsrtemp + z_Airdata.getTotalNumber()/1000.0;
								nzkltemp = nzkltemp + z_Airdata.getPgs_Per_Cls();
								if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
									cnzsrtemp = cnzsrtemp + z_Airdata.getTotalNumber()/1000.0;
									cnzkltemp = cnzkltemp + z_Airdata.getPgs_Per_Cls();
								}
								if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
									jnzsrtemp = jnzsrtemp + z_Airdata.getTotalNumber()/1000.0;
									jnzkltemp = jnzkltemp + z_Airdata.getPgs_Per_Cls();
								}
							}
						}
					}
					//汇总-所有
					HomePageData homePageData = new HomePageData();
					homePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
					homePageData.setPersons(zkltemp+"");
					homePageData.setZsr(df.format(zsrtemp));
					HomePageDataListzs.add(homePageData);
					//汇总-含过站
					HomePageData phomePageData = new HomePageData();
					phomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
					phomePageData.setPersons(pzkltemp+"");
					phomePageData.setZsr(df.format(pzsrtemp));
					pHomePageDataListzs.add(phomePageData);
					//汇总-含甩飞
					HomePageData thomePageData = new HomePageData();
					thomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
					thomePageData.setPersons(tzkltemp+"");
					thomePageData.setZsr(df.format(tzsrtemp));
					tHomePageDataListzs.add(thomePageData);
					//汇总-不含过站与甩飞
					HomePageData nhomePageData = new HomePageData();
					nhomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
					nhomePageData.setPersons(nzkltemp+"");
					nhomePageData.setZsr(df.format(nzsrtemp));
					nHomePageDataListzs.add(nhomePageData);
					//进港-所有
					HomePageData jhomePageData = new HomePageData();
					jhomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
					jhomePageData.setPersons(jzkltemp+"");
					jhomePageData.setZsr(df.format(jzsrtemp));
					jHomePageDataListzs.add(jhomePageData);
					//进港-含过站
					HomePageData jphomePageData = new HomePageData();
					jphomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
					jphomePageData.setPersons(jpzkltemp+"");
					jphomePageData.setZsr(df.format(jpzsrtemp));
					jpHomePageDataListzs.add(jphomePageData);
					//进港-含甩飞
					HomePageData jthomePageData = new HomePageData();
					jthomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
					jthomePageData.setPersons(jtzkltemp+"");
					jthomePageData.setZsr(df.format(jtzsrtemp));
					jtHomePageDataListzs.add(jthomePageData);
					//进港-不含过站与甩飞
					HomePageData jnhomePageData = new HomePageData();
					jnhomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
					jnhomePageData.setPersons(jnzkltemp+"");
					jnhomePageData.setZsr(df.format(jnzsrtemp));
					jnHomePageDataListzs.add(jnhomePageData);
					//出港-所有
					HomePageData chomePageData = new HomePageData();
					chomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
					chomePageData.setPersons(czkltemp+"");
					chomePageData.setZsr(df.format(czsrtemp));
					cHomePageDataListzs.add(chomePageData);
					//出港-含过站
					HomePageData cphomePageData = new HomePageData();
					cphomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
					cphomePageData.setPersons(cpzkltemp+"");
					cphomePageData.setZsr(df.format(cpzsrtemp));
					cpHomePageDataListzs.add(cphomePageData);
					//出港-含甩飞
					HomePageData cthomePageData = new HomePageData();
					cthomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
					cthomePageData.setPersons(ctzkltemp+"");
					cthomePageData.setZsr(df.format(ctzsrtemp));
					ctHomePageDataListzs.add(cthomePageData);
					//出港-不含过站与甩飞
					HomePageData cnhomePageData = new HomePageData();
					cnhomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
					cnhomePageData.setPersons(cnzkltemp+"");
					cnhomePageData.setZsr(df.format(cnzsrtemp));
					cnHomePageDataListzs.add(cnhomePageData);
				}
			}
			retmap.put("zsData", HomePageDataListzs);
			retmap.put("pzsData", pHomePageDataListzs);
			retmap.put("tzsData", tHomePageDataListzs);
			retmap.put("nzsData", nHomePageDataListzs);
			retmap.put("jzsData", jHomePageDataListzs);
			retmap.put("jpzsData", jpHomePageDataListzs);
			retmap.put("jtzsData", jtHomePageDataListzs);
			retmap.put("jnzsData", jnHomePageDataListzs);
			retmap.put("czsData", cHomePageDataListzs);
			retmap.put("cpzsData", cpHomePageDataListzs);
			retmap.put("ctzsData", ctHomePageDataListzs);
			retmap.put("cnzsData", cnHomePageDataListzs);
			//获取关注航班对应的数据
			//获取当前航季起止日期
			String[] seasonDate = DataUtils.getCurrentSeasonDate();
			List<FocusFlight> focusFlts = focusFlightMapper.getFocusFlight(UserContext.getUser().getId(),seasonDate[0],seasonDate[1]);
			//关注航班号
			List<String> flts = new ArrayList<String>();
			List<String> fltRoutes = new ArrayList<String>();
			for(FocusFlight fft:focusFlts){
				String fight = fft.getFlightNumber();
				String[] flights = fight.split(",");
				for(String flt:flights){
					String[] fltStr = flt.split("/");
					flts.add(fltStr[0]);
					flts.add(fltStr[0].substring(0,fltStr[0].length()-fltStr[1].length())+fltStr[1]);
				}
				if(fft.getPasAirport()==null||"".equals(fft.getPasAirport())){
					fltRoutes.add(fft.getDptAirport()+fft.getArrvAirport());
					fltRoutes.add(fft.getArrvAirport()+fft.getDptAirport());
				}else{
					fltRoutes.add(fft.getDptAirport()+fft.getPasAirport()+fft.getArrvAirport());
					fltRoutes.add(fft.getArrvAirport()+fft.getPasAirport()+fft.getDptAirport());
				}
			}
			retmap.put("focusFlt",flts);//前台判断是否关注了航班
			if(days<0&&flts.size()>0){
				//再次封装查询对象
				//获取关注航班最新有数据日期
				homePageQuery.setFlightRange(flts);
				homePageQuery.setFltRoutes(fltRoutes);
				String newDate = homePageMapper.getNewDate(homePageQuery);
				String startDate = new SimpleDateFormat().format(new Date());
				if(!TextUtil.isEmpty(newDate)){
					 startDate = TextUtil.addDateDay(newDate, days);
				}else{
					newDate = startDate;
				}
//				if(days==-6){
//					homePageQuery.setStartTime("2017-03-24");
//					homePageQuery.setEndTime("2017-03-29");
//					startTime = sdf.parse("2017-03-24").getTime();
//					endTime = sdf.parse("2017-03-29").getTime();
//				}else{
					homePageQuery.setStartTime(startDate);
					homePageQuery.setEndTime(newDate);
					startTime = sdf.parse(startDate).getTime();
					endTime = sdf.parse(newDate).getTime();
//				}
				//获取关注航班在当前时间区间内的数据
				//重置列表
				//汇总-所有
				HomePageDataListzs = new ArrayList<HomePageData>();
				//汇总-含过站
				pHomePageDataListzs = new ArrayList<HomePageData>();
				//汇总-含甩飞
				tHomePageDataListzs = new ArrayList<HomePageData>();
				//汇总-不会过站与甩飞
				nHomePageDataListzs = new ArrayList<HomePageData>();
				//出港-所有
				cHomePageDataListzs = new ArrayList<HomePageData>();//重置列表
				//出港-含过站
				cpHomePageDataListzs = new ArrayList<HomePageData>();//重置列表
				//出港-含甩飞
				ctHomePageDataListzs = new ArrayList<HomePageData>();//重置列表
				//出港-不含过站与甩飞
				cnHomePageDataListzs = new ArrayList<HomePageData>();//重置列表
				//进港-所有
				jHomePageDataListzs = new ArrayList<HomePageData>();//重置列表
				//进港-含过站
				jpHomePageDataListzs = new ArrayList<HomePageData>();//重置列表
				//进港-含甩飞
				jtHomePageDataListzs = new ArrayList<HomePageData>();//重置列表
				//进港-不含过站与甩飞
				jnHomePageDataListzs = new ArrayList<HomePageData>();//重置列表
				List<Z_Airdata> focusData = homePageMapper.getKPIZAirdataListNew(homePageQuery);
				if(focusData!=null&&focusData.size()>0){
					//汇总-所有
					zkl = 0;
					zsr = 0.0;
					zsk = 0;
					ztd = 0;
					zbc = 0;
					//汇总-含过站
					pzkl = 0;
					pzsr = 0.0;
					pzsk = 0;
					pztd = 0;
					pzbc = 0;
					//汇总-含甩飞
					tzkl = 0;
					tzsr = 0.0;
					tzsk = 0;
					tztd = 0;
					tzbc = 0;
					//汇总-不含过站与甩飞
					nzkl = 0;
					nzsr = 0.0;
					nzsk = 0;
					nztd = 0;
					nzbc = 0;
					//出港-所有
					czkl = 0;
					czsr = 0.0;
					czsk = 0;
					cztd = 0;
					czbc = 0;
					//出港-含过站
					cpzkl = 0;
					cpzsr = 0.0;
					cpzsk = 0;
					cpztd = 0;
					cpzbc = 0;
					//出港-含甩飞
					ctzkl = 0;
					ctzsr = 0.0;
					ctzsk = 0;
					ctztd = 0;
					ctzbc = 0;
					//出港-不含过站与甩飞
					cnzkl = 0;
					cnzsr = 0.0;
					cnzsk = 0;
					cnztd = 0;
					cnzbc = 0;
					//进港-所有
					jzkl = 0;
					jzsr = 0.0;
					jzsk = 0;
					jztd = 0;
					jzbc = 0;
					//进港-含过站
					jpzkl = 0;
					jpzsr = 0.0;
					jpzsk = 0;
					jpztd = 0;
					jpzbc = 0;
					//进港-含甩飞
					jtzkl = 0;
					jtzsr = 0.0;
					jtzsk = 0;
					jtztd = 0;
					jtzbc = 0;
					//进港-不含过站与甩飞
					jnzkl = 0;
					jnzsr = 0.0;
					jnzsk = 0;
					jnztd = 0;
					jnzbc = 0;
					//重置数据
					//出港-所有
					list1 = new ArrayList<Z_Airdata>();//出港
					//出港-含过站
					plist1 = new ArrayList<Z_Airdata>();//出港
					//出港-含甩飞
					tlist1 = new ArrayList<Z_Airdata>();//出港
					//出港-不含过站与甩飞
					nlist1 = new ArrayList<Z_Airdata>();//出港
					//进港-所有
					list2 = new ArrayList<Z_Airdata>();//进港
					//进港-含过站
					plist2 = new ArrayList<Z_Airdata>();//进港
					//进港-含甩飞
					tlist2 = new ArrayList<Z_Airdata>();//进港
					//进港-不含过站与
					nlist2 = new ArrayList<Z_Airdata>();//进港
					for (int i =0;i<focusData.size();i++) {
						Z_Airdata obj = focusData.get(i);
						String fltAroute = obj.getFlt_Rte_Cd();
						zsr = zsr + obj.getTotalNumber();
						zkl = zkl + obj.getPgs_Per_Cls();
						zsk = zsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
						ztd = ztd + obj.getGrp_Nbr();
						if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
							czsr = czsr + obj.getTotalNumber();
							czkl = czkl + obj.getPgs_Per_Cls();
							czsk = czsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							cztd = cztd + obj.getGrp_Nbr();
						}
						if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
							jzsr = jzsr + obj.getTotalNumber();
							jzkl = jzkl + obj.getPgs_Per_Cls();
							jzsk = jzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
							jztd = jztd + obj.getGrp_Nbr();
						}
						if(i==0){
							//所有
							zbc++;
							pzbc++;
							tzbc++;
							nzbc++;
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								list1.add(obj);
							}else if(obj.getArrv_Airpt_Cd().equals(airport)){
								list2.add(obj);
							}
							if((airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd()))
									||(airport.equals(fltAroute.substring(3, 6))&&fltAroute.length()==9
									&&!airport.equals(obj.getDpt_AirPt_Cd())&&!airport.equals(obj.getArrv_Airpt_Cd()))){//包含过站
								pzsr = pzsr + obj.getTotalNumber();
								pzkl = pzkl + obj.getPgs_Per_Cls();
								pzsk = pzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
								pztd = pztd + obj.getGrp_Nbr();
								if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
									cpzsr = cpzsr + obj.getTotalNumber();
									cpzkl = cpzkl + obj.getPgs_Per_Cls();
									cpzsk = cpzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									cpztd = cpztd + obj.getGrp_Nbr();
								}
								if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
									jpzsr = jpzsr + obj.getTotalNumber();
									jpzkl = jpzkl + obj.getPgs_Per_Cls();
									jpzsk = jpzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									jpztd = jpztd + obj.getGrp_Nbr();
								}
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									plist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									plist2.add(obj);
								}
							}
							if((airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd())
									||((fltAroute.startsWith(airport)||fltAroute.endsWith(airport))
											&&!airport.equals(obj.getDpt_AirPt_Cd())&&!airport.equals(obj.getArrv_Airpt_Cd())))){//包含甩飞
								tzsr = tzsr + obj.getTotalNumber();
								tzkl = tzkl + obj.getPgs_Per_Cls();
								tzsk = tzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
								tztd = tztd + obj.getGrp_Nbr();
								if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
									ctzsr = ctzsr + obj.getTotalNumber();
									ctzkl = ctzkl + obj.getPgs_Per_Cls();
									ctzsk = ctzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									ctztd = ctztd + obj.getGrp_Nbr();
								}
								if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
									jtzsr = jtzsr + obj.getTotalNumber();
									jtzkl = jtzkl + obj.getPgs_Per_Cls();
									jtzsk = jtzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									jtztd = jtztd + obj.getGrp_Nbr();
								}
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									tlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									tlist2.add(obj);
								}
							}
							if(airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd())){//不含过站与甩飞
								nzsr = nzsr + obj.getTotalNumber();
								nzkl = nzkl + obj.getPgs_Per_Cls();
								nzsk = nzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
								nztd = nztd + obj.getGrp_Nbr();
								if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
									cnzsr = cnzsr + obj.getTotalNumber();
									cnzkl = cnzkl + obj.getPgs_Per_Cls();
									cnzsk = cnzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									cnztd = cnztd + obj.getGrp_Nbr();
								}
								if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
									jnzsr = jnzsr + obj.getTotalNumber();
									jnzkl = jnzkl + obj.getPgs_Per_Cls();
									jnzsk = jnzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									jnztd = jnztd + obj.getGrp_Nbr();
								}
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									nlist1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									nlist2.add(obj);
								}
							}
						}else if(i!=focusData.size()-1){
							//所有
							Z_Airdata prevObj = focusData.get(i-1);
							Date prevDate = prevObj.getLcl_Dpt_Day();
							Date date = obj.getLcl_Dpt_Day();
							if(prevDate.equals(date)){
								String prevFlt = prevObj.getFlt_Nbr();
								String flt = obj.getFlt_Nbr();
								if(prevFlt.equals(flt)){
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										list1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										list2.add(obj);
									}
								}else{
									zbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											czbc += list1.size();
											jzbc += list2.size();
										}else{
											czbc += (int)Math.ceil(list1.size()/2.0);
											jzbc += (int)Math.ceil(list2.size()/2.0);
										}
									}else{
										czbc += list1.size();
										jzbc += list2.size();
									}
									list1 = new ArrayList<Z_Airdata>();
									list2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										list1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										list2.add(obj);
									}
								}
							}else{
								zbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										czbc += list1.size();
										jzbc += list2.size();
									}else{
										czbc += (int)Math.ceil(list1.size()/2.0);
										jzbc += (int)Math.ceil(list2.size()/2.0);
									}
								}else{
									czbc += list1.size();
									jzbc += list2.size();
								}
								list1 = new ArrayList<Z_Airdata>();
								list2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									list1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									list2.add(obj);
								}
							}
							if((airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd()))
									||(airport.equals(fltAroute.substring(3, 6))&&fltAroute.length()==9
									&&!airport.equals(obj.getDpt_AirPt_Cd())&&!airport.equals(obj.getArrv_Airpt_Cd()))){//包含过站
								pzsr = pzsr + obj.getTotalNumber();
								pzkl = pzkl + obj.getPgs_Per_Cls();
								pzsk = pzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
								pztd = pztd + obj.getGrp_Nbr();
								if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
									cpzsr = cpzsr + obj.getTotalNumber();
									cpzkl = cpzkl + obj.getPgs_Per_Cls();
									cpzsk = cpzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									cpztd = cpztd + obj.getGrp_Nbr();
								}
								if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
									jpzsr = jpzsr + obj.getTotalNumber();
									jpzkl = jpzkl + obj.getPgs_Per_Cls();
									jpzsk = jpzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									jpztd = jpztd + obj.getGrp_Nbr();
								}
//								Z_Airdata prevObj = focusData.get(i-1);
//								Date prevDate = prevObj.getLcl_Dpt_Day();
//								Date date = obj.getLcl_Dpt_Day();
								if(prevDate.equals(date)){
									String prevFlt = prevObj.getFlt_Nbr();
									String flt = obj.getFlt_Nbr();
									if(prevFlt.equals(flt)){
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											plist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											plist2.add(obj);
										}
									}else{
										pzbc++;
										//获取原有数据
										String fltline = prevObj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cpzbc += plist1.size();
												jpzbc += plist2.size();
											}else{
												cpzbc += (int)Math.ceil(plist1.size()/2.0);
												jpzbc += (int)Math.ceil(plist2.size()/2.0);
											}
										}else{
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}
										plist1 = new ArrayList<Z_Airdata>();
										plist2 = new ArrayList<Z_Airdata>();
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											plist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											plist2.add(obj);
										}
									}
								}else{
									pzbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}else{
											cpzbc += (int)Math.ceil(plist1.size()/2.0);
											jpzbc += (int)Math.ceil(plist2.size()/2.0);
										}
									}else{
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}
									plist1 = new ArrayList<Z_Airdata>();
									plist2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										plist1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										plist2.add(obj);
									}
								}
							}else{
								if(prevDate.equals(date)){
									String prevFlt = prevObj.getFlt_Nbr();
									String flt = obj.getFlt_Nbr();
									if(!prevFlt.equals(flt)){
										pzbc++;
										//获取原有数据
										String fltline = prevObj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cpzbc += plist1.size();
												jpzbc += plist2.size();
											}else{
												cpzbc += (int)Math.ceil(plist1.size()/2.0);
												jpzbc += (int)Math.ceil(plist2.size()/2.0);
											}
										}else{
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}
										plist1 = new ArrayList<Z_Airdata>();
										plist2 = new ArrayList<Z_Airdata>();
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											plist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											plist2.add(obj);
										}
									}
								}else{
									pzbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}else{
											cpzbc += (int)Math.ceil(plist1.size()/2.0);
											jpzbc += (int)Math.ceil(plist2.size()/2.0);
										}
									}else{
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}
									plist1 = new ArrayList<Z_Airdata>();
									plist2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										plist1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										plist2.add(obj);
									}
								}
							}
							if((airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd())
									||((fltAroute.startsWith(airport)||fltAroute.endsWith(airport))
											&&!airport.equals(obj.getDpt_AirPt_Cd())&&!airport.equals(obj.getArrv_Airpt_Cd())))){//包含甩飞
								tzsr = tzsr + obj.getTotalNumber();
								tzkl = tzkl + obj.getPgs_Per_Cls();
								tzsk = tzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
								tztd = tztd + obj.getGrp_Nbr();
								if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
									ctzsr = ctzsr + obj.getTotalNumber();
									ctzkl = ctzkl + obj.getPgs_Per_Cls();
									ctzsk = ctzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									ctztd = ctztd + obj.getGrp_Nbr();
								}
								if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
									jtzsr = jtzsr + obj.getTotalNumber();
									jtzkl = jtzkl + obj.getPgs_Per_Cls();
									jtzsk = jtzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									jtztd = jtztd + obj.getGrp_Nbr();
								}
//								Z_Airdata prevObj = focusData.get(i-1);
//								Date prevDate = prevObj.getLcl_Dpt_Day();
//								Date date = obj.getLcl_Dpt_Day();
								if(prevDate.equals(date)){
									String prevFlt = prevObj.getFlt_Nbr();
									String flt = obj.getFlt_Nbr();
									if(prevFlt.equals(flt)){
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											tlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											tlist2.add(obj);
										}
									}else{
										tzbc++;
										//获取原有数据
										String fltline = prevObj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												ctzbc += tlist1.size();
												jtzbc += tlist2.size();
											}else{
												ctzbc += (int)Math.ceil(tlist1.size()/2.0);
												jtzbc += (int)Math.ceil(tlist2.size()/2.0);
											}
										}else{
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}
										tlist1 = new ArrayList<Z_Airdata>();
										tlist2 = new ArrayList<Z_Airdata>();
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											tlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											tlist2.add(obj);
										}
									}
								}else{
									tzbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}else{
											ctzbc += (int)Math.ceil(tlist1.size()/2.0);
											jtzbc += (int)Math.ceil(tlist2.size()/2.0);
										}
									}else{
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}
									tlist1 = new ArrayList<Z_Airdata>();
									tlist2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										tlist1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										tlist2.add(obj);
									}
								}
							}else{
								if(prevDate.equals(date)){
									String prevFlt = prevObj.getFlt_Nbr();
									String flt = obj.getFlt_Nbr();
									if(!prevFlt.equals(flt)){
										tzbc++;
										//获取原有数据
										String fltline = prevObj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												ctzbc += tlist1.size();
												jtzbc += tlist2.size();
											}else{
												ctzbc += (int)Math.ceil(tlist1.size()/2.0);
												jtzbc += (int)Math.ceil(tlist2.size()/2.0);
											}
										}else{
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}
										tlist1 = new ArrayList<Z_Airdata>();
										tlist2 = new ArrayList<Z_Airdata>();
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											tlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											tlist2.add(obj);
										}
									}
								}else{
									tzbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}else{
											ctzbc += (int)Math.ceil(tlist1.size()/2.0);
											jtzbc += (int)Math.ceil(tlist2.size()/2.0);
										}
									}else{
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}
									tlist1 = new ArrayList<Z_Airdata>();
									tlist2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										tlist1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										tlist2.add(obj);
									}
								}
							}
							if(airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd())){//不含过站与甩飞
								nzsr = nzsr + obj.getTotalNumber();
								nzkl = nzkl + obj.getPgs_Per_Cls();
								nzsk = nzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
								nztd = nztd + obj.getGrp_Nbr();
								if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
									cnzsr = cnzsr + obj.getTotalNumber();
									cnzkl = cnzkl + obj.getPgs_Per_Cls();
									cnzsk = cnzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									cnztd = cnztd + obj.getGrp_Nbr();
								}
								if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
									jnzsr = jnzsr + obj.getTotalNumber();
									jnzkl = jnzkl + obj.getPgs_Per_Cls();
									jnzsk = jnzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									jnztd = jnztd + obj.getGrp_Nbr();
								}
//								Z_Airdata prevObj = focusData.get(i-1);
//								Date prevDate = prevObj.getLcl_Dpt_Day();
//								Date date = obj.getLcl_Dpt_Day();
								if(prevDate.equals(date)){
									String prevFlt = prevObj.getFlt_Nbr();
									String flt = obj.getFlt_Nbr();
									if(prevFlt.equals(flt)){
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											nlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											nlist2.add(obj);
										}
									}else{
										nzbc++;
										//获取原有数据
										String fltline = prevObj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cnzbc += nlist1.size();
												jnzbc += nlist2.size();
											}else{
												cnzbc += (int)Math.ceil(nlist1.size()/2.0);
												jnzbc += (int)Math.ceil(nlist2.size()/2.0);
											}
										}else{
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}
										nlist1 = new ArrayList<Z_Airdata>();
										nlist2 = new ArrayList<Z_Airdata>();
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											nlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											nlist2.add(obj);
										}
									}
								}else{
									nzbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}else{
											cnzbc += (int)Math.ceil(nlist1.size()/2.0);
											jnzbc += (int)Math.ceil(nlist2.size()/2.0);
										}
									}else{
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}
									nlist1 = new ArrayList<Z_Airdata>();
									nlist2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										nlist1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										nlist2.add(obj);
									}
								}
							}else{
								if(!prevDate.equals(date)){
									nzbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}else{
											cnzbc += (int)Math.ceil(nlist1.size()/2.0);
											jnzbc += (int)Math.ceil(nlist2.size()/2.0);
										}
									}else{
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}
									nlist1 = new ArrayList<Z_Airdata>();
									nlist2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										nlist1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										nlist2.add(obj);
									}
								}else{
									String prevFlt=prevObj.getFlt_Rte_Cd();
									String flt=obj.getFlt_Rte_Cd();
									if(!prevFlt.equals(flt)){
										nzbc++;
										//获取原有数据
										if(prevFlt.length()==9){
											String midAirport = prevFlt.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cnzbc += nlist1.size();
												jnzbc += nlist2.size();
											}else{
												cnzbc += (int)Math.ceil(nlist1.size()/2.0);
												jnzbc += (int)Math.ceil(nlist2.size()/2.0);
											}
										}else{
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}
										nlist1 = new ArrayList<Z_Airdata>();
										nlist2 = new ArrayList<Z_Airdata>();
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											nlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											nlist2.add(obj);
										}
									}
								}
							}
						}else{
							//所有
							Z_Airdata prevObj = focusData.get(i-1);
							Date prevDate = prevObj.getLcl_Dpt_Day();
							Date date = obj.getLcl_Dpt_Day();
							if(prevDate.equals(date)){
								String prevFlt = prevObj.getFlt_Nbr();
								String flt = obj.getFlt_Nbr();
								if(prevFlt.equals(flt)){
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										list1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										list2.add(obj);
									}
									String fltline = obj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											czbc += list1.size();
											jzbc += list2.size();
										}else{
											czbc += (int)Math.ceil(list1.size()/2.0);
											jzbc += (int)Math.ceil(list2.size()/2.0);
										}
									}else{
										czbc += list1.size();
										jzbc += list2.size();
									}
								}else{
									zbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											czbc += list1.size();
											jzbc += list2.size();
										}else{
											czbc += (int)Math.ceil(list1.size()/2.0);
											jzbc += (int)Math.ceil(list2.size()/2.0);
										}
									}else{
										czbc += list1.size();
										jzbc += list2.size();
									}
									list1 = new ArrayList<Z_Airdata>();
									list2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										list1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										list2.add(obj);
									}
									fltline = obj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											czbc += list1.size();
											jzbc += list2.size();
										}else{
											czbc += (int)Math.ceil(list1.size()/2.0);
											jzbc += (int)Math.ceil(list2.size()/2.0);
										}
									}else{
										czbc += list1.size();
										jzbc += list2.size();
									}
								}
							}else{
								zbc++;
								//获取原有数据
								String fltline = prevObj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										czbc += list1.size();
										jzbc += list2.size();
									}else{
										czbc += (int)Math.ceil(list1.size()/2.0);
										jzbc += (int)Math.ceil(list2.size()/2.0);
									}
								}else{
									czbc += list1.size();
									jzbc += list2.size();
								}
								list1 = new ArrayList<Z_Airdata>();
								list2 = new ArrayList<Z_Airdata>();
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									list1.add(obj);
								}else if(obj.getArrv_Airpt_Cd().equals(airport)){
									list2.add(obj);
								}
								fltline = obj.getFlt_Rte_Cd();
								if(fltline.length()==9){
									String midAirport = fltline.substring(3,6);
									if(midAirport.equals(airport)){//经停
										czbc += list1.size();
										jzbc += list2.size();
									}else{
										czbc += (int)Math.ceil(list1.size()/2.0);
										jzbc += (int)Math.ceil(list2.size()/2.0);
									}
								}else{
									czbc += list1.size();
									jzbc += list2.size();
								}
							}
							if((airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd()))
									||(airport.equals(fltAroute.substring(3, 6))&&fltAroute.length()==9
									&&!airport.equals(obj.getDpt_AirPt_Cd())&&!airport.equals(obj.getArrv_Airpt_Cd()))){//包含过站
								pzsr = pzsr + obj.getTotalNumber();
								pzkl = pzkl + obj.getPgs_Per_Cls();
								pzsk = pzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
								pztd = pztd + obj.getGrp_Nbr();
								if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
									cpzsr = cpzsr + obj.getTotalNumber();
									cpzkl = cpzkl + obj.getPgs_Per_Cls();
									cpzsk = cpzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									cpztd = cpztd + obj.getGrp_Nbr();
								}
								if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
									jpzsr = jpzsr + obj.getTotalNumber();
									jpzkl = jpzkl + obj.getPgs_Per_Cls();
									jpzsk = jpzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									jpztd = jpztd + obj.getGrp_Nbr();
								}
//								Z_Airdata prevObj = focusData.get(i-1);
//								Date prevDate = prevObj.getLcl_Dpt_Day();
//								Date date = obj.getLcl_Dpt_Day();
								if(prevDate.equals(date)){
									String prevFlt = prevObj.getFlt_Nbr();
									String flt = obj.getFlt_Nbr();
									if(prevFlt.equals(flt)){
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											plist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											plist2.add(obj);
										}
										//获取原有数据
										String fltline = obj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cpzbc += plist1.size();
												jpzbc += plist2.size();
											}else{
												cpzbc += (int)Math.ceil(plist1.size()/2.0);
												jpzbc += (int)Math.ceil(plist2.size()/2.0);
											}
										}else{
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}
									}else{
										pzbc++;
										//获取原有数据
										String fltline = prevObj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cpzbc += plist1.size();
												jpzbc += plist2.size();
											}else{
												cpzbc += (int)Math.ceil(plist1.size()/2.0);
												jpzbc += (int)Math.ceil(plist2.size()/2.0);
											}
										}else{
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}
										plist1 = new ArrayList<Z_Airdata>();
										plist2 = new ArrayList<Z_Airdata>();
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											plist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											plist2.add(obj);
										}
										//获取原有数据
										fltline = obj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cpzbc += plist1.size();
												jpzbc += plist2.size();
											}else{
												cpzbc += (int)Math.ceil(plist1.size()/2.0);
												jpzbc += (int)Math.ceil(plist2.size()/2.0);
											}
										}else{
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}
									}
								}else{
									pzbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}else{
											cpzbc += (int)Math.ceil(plist1.size()/2.0);
											jpzbc += (int)Math.ceil(plist2.size()/2.0);
										}
									}else{
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}
									plist1 = new ArrayList<Z_Airdata>();
									plist2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										plist1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										plist2.add(obj);
									}
									//获取原有数据
									fltline = obj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}else{
											cpzbc += (int)Math.ceil(plist1.size()/2.0);
											jpzbc += (int)Math.ceil(plist2.size()/2.0);
										}
									}else{
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}
								}
							}else{
								if(prevDate.equals(date)){
									String prevFlt = prevObj.getFlt_Nbr();
									String flt = obj.getFlt_Nbr();
									if(prevFlt.equals(flt)){
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											plist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											plist2.add(obj);
										}
										//获取原有数据
										String fltline = obj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cpzbc += plist1.size();
												jpzbc += plist2.size();
											}else{
												cpzbc += (int)Math.ceil(plist1.size()/2.0);
												jpzbc += (int)Math.ceil(plist2.size()/2.0);
											}
										}else{
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}
									}else{
										pzbc++;
										//获取原有数据
										String fltline = prevObj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cpzbc += plist1.size();
												jpzbc += plist2.size();
											}else{
												cpzbc += (int)Math.ceil(plist1.size()/2.0);
												jpzbc += (int)Math.ceil(plist2.size()/2.0);
											}
										}else{
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}
										plist1 = new ArrayList<Z_Airdata>();
										plist2 = new ArrayList<Z_Airdata>();
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											plist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											plist2.add(obj);
										}
										//获取原有数据
										fltline = obj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cpzbc += plist1.size();
												jpzbc += plist2.size();
											}else{
												cpzbc += (int)Math.ceil(plist1.size()/2.0);
												jpzbc += (int)Math.ceil(plist2.size()/2.0);
											}
										}else{
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}
									}
								}else{
									pzbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}else{
											cpzbc += (int)Math.ceil(plist1.size()/2.0);
											jpzbc += (int)Math.ceil(plist2.size()/2.0);
										}
									}else{
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}
									plist1 = new ArrayList<Z_Airdata>();
									plist2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										plist1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										plist2.add(obj);
									}
									//获取原有数据
									fltline = obj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											cpzbc += plist1.size();
											jpzbc += plist2.size();
										}else{
											cpzbc += (int)Math.ceil(plist1.size()/2.0);
											jpzbc += (int)Math.ceil(plist2.size()/2.0);
										}
									}else{
										cpzbc += plist1.size();
										jpzbc += plist2.size();
									}
								}
							}
							if((airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd())
									||((fltAroute.startsWith(airport)||fltAroute.endsWith(airport))
											&&!airport.equals(obj.getDpt_AirPt_Cd())&&!airport.equals(obj.getArrv_Airpt_Cd())))){//包含甩飞
								tzsr = tzsr + obj.getTotalNumber();
								tzkl = tzkl + obj.getPgs_Per_Cls();
								tzsk = tzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
								tztd = tztd + obj.getGrp_Nbr();
								if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
									ctzsr = ctzsr + obj.getTotalNumber();
									ctzkl = ctzkl + obj.getPgs_Per_Cls();
									ctzsk = ctzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									ctztd = ctztd + obj.getGrp_Nbr();
								}
								if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
									jtzsr = jtzsr + obj.getTotalNumber();
									jtzkl = jtzkl + obj.getPgs_Per_Cls();
									jtzsk = jtzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									jtztd = jtztd + obj.getGrp_Nbr();
								}
//								Z_Airdata prevObj = focusData.get(i-1);
//								Date prevDate = prevObj.getLcl_Dpt_Day();
//								Date date = obj.getLcl_Dpt_Day();
								if(prevDate.equals(date)){
									String prevFlt = prevObj.getFlt_Nbr();
									String flt = obj.getFlt_Nbr();
									if(prevFlt.equals(flt)){
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											tlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											tlist2.add(obj);
										}
										//获取原有数据
										String fltline = obj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												ctzbc += tlist1.size();
												jtzbc += tlist2.size();
											}else{
												ctzbc += (int)Math.ceil(tlist1.size()/2.0);
												jtzbc += (int)Math.ceil(tlist2.size()/2.0);
											}
										}else{
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}
									}else{
										tzbc++;
										//获取原有数据
										String fltline = prevObj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												ctzbc += tlist1.size();
												jtzbc += tlist2.size();
											}else{
												ctzbc += (int)Math.ceil(tlist1.size()/2.0);
												jtzbc += (int)Math.ceil(tlist2.size()/2.0);
											}
										}else{
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}
										tlist1 = new ArrayList<Z_Airdata>();
										tlist2 = new ArrayList<Z_Airdata>();
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											tlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											tlist2.add(obj);
										}
										//获取原有数据
										fltline = obj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												ctzbc += tlist1.size();
												jtzbc += tlist2.size();
											}else{
												ctzbc += (int)Math.ceil(tlist1.size()/2.0);
												jtzbc += (int)Math.ceil(tlist2.size()/2.0);
											}
										}else{
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}
									}
								}else{
									tzbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}else{
											ctzbc += (int)Math.ceil(tlist1.size()/2.0);
											jtzbc += (int)Math.ceil(tlist2.size()/2.0);
										}
									}else{
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}
									tlist1 = new ArrayList<Z_Airdata>();
									tlist2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										tlist1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										tlist2.add(obj);
									}
									//获取原有数据
									fltline = obj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}else{
											ctzbc += (int)Math.ceil(tlist1.size()/2.0);
											jtzbc += (int)Math.ceil(tlist2.size()/2.0);
										}
									}else{
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}
								}
							}else{
								if(prevDate.equals(date)){
									String prevFlt = prevObj.getFlt_Nbr();
									String flt = obj.getFlt_Nbr();
									if(prevFlt.equals(flt)){
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											tlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											tlist2.add(obj);
										}
										//获取原有数据
										String fltline = obj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												ctzbc += tlist1.size();
												jtzbc += tlist2.size();
											}else{
												ctzbc += (int)Math.ceil(tlist1.size()/2.0);
												jtzbc += (int)Math.ceil(tlist2.size()/2.0);
											}
										}else{
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}
									}else{
										tzbc++;
										//获取原有数据
										String fltline = prevObj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												ctzbc += tlist1.size();
												jtzbc += tlist2.size();
											}else{
												ctzbc += (int)Math.ceil(tlist1.size()/2.0);
												jtzbc += (int)Math.ceil(tlist2.size()/2.0);
											}
										}else{
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}
										tlist1 = new ArrayList<Z_Airdata>();
										tlist2 = new ArrayList<Z_Airdata>();
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											tlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											tlist2.add(obj);
										}
										//获取原有数据
										fltline = obj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												ctzbc += tlist1.size();
												jtzbc += tlist2.size();
											}else{
												ctzbc += (int)Math.ceil(tlist1.size()/2.0);
												jtzbc += (int)Math.ceil(tlist2.size()/2.0);
											}
										}else{
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}
									}
								}else{
									tzbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}else{
											ctzbc += (int)Math.ceil(tlist1.size()/2.0);
											jtzbc += (int)Math.ceil(tlist2.size()/2.0);
										}
									}else{
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}
									tlist1 = new ArrayList<Z_Airdata>();
									tlist2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										tlist1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										tlist2.add(obj);
									}
									//获取原有数据
									fltline = obj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											ctzbc += tlist1.size();
											jtzbc += tlist2.size();
										}else{
											ctzbc += (int)Math.ceil(tlist1.size()/2.0);
											jtzbc += (int)Math.ceil(tlist2.size()/2.0);
										}
									}else{
										ctzbc += tlist1.size();
										jtzbc += tlist2.size();
									}
								}
							}
							if(airport.equals(obj.getDpt_AirPt_Cd())||airport.equals(obj.getArrv_Airpt_Cd())){//不含过站与甩飞
								nzsr = nzsr + obj.getTotalNumber();
								nzkl = nzkl + obj.getPgs_Per_Cls();
								nzsk = nzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
								nztd = nztd + obj.getGrp_Nbr();
								if(obj.getDpt_AirPt_Cd().equals(airport)){//出港
									cnzsr = cnzsr + obj.getTotalNumber();
									cnzkl = cnzkl + obj.getPgs_Per_Cls();
									cnzsk = cnzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									cnztd = cnztd + obj.getGrp_Nbr();
								}
								if(obj.getArrv_Airpt_Cd().equals(airport)){//进港
									jnzsr = jnzsr + obj.getTotalNumber();
									jnzkl = jnzkl + obj.getPgs_Per_Cls();
									jnzsk = jnzsk + (obj.getPgs_Per_Cls() - obj.getGrp_Nbr());
									jnztd = jnztd + obj.getGrp_Nbr();
								}
//								Z_Airdata prevObj = focusData.get(i-1);
//								Date prevDate = prevObj.getLcl_Dpt_Day();
//								Date date = obj.getLcl_Dpt_Day();
								if(prevDate.equals(date)){
									String prevFlt = prevObj.getFlt_Nbr();
									String flt = obj.getFlt_Nbr();
									if(prevFlt.equals(flt)){
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											nlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											nlist2.add(obj);
										}
										//获取原有数据
										String fltline = obj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cnzbc += nlist1.size();
												jnzbc += nlist2.size();
											}else{
												cnzbc += (int)Math.ceil(nlist1.size()/2.0);
												jnzbc += (int)Math.ceil(nlist2.size()/2.0);
											}
										}else{
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}
									}else{
										nzbc++;
										//获取原有数据
										String fltline = prevObj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cnzbc += nlist1.size();
												jnzbc += nlist2.size();
											}else{
												cnzbc += (int)Math.ceil(nlist1.size()/2.0);
												jnzbc += (int)Math.ceil(nlist2.size()/2.0);
											}
										}else{
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}
										nlist1 = new ArrayList<Z_Airdata>();
										nlist2 = new ArrayList<Z_Airdata>();
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											nlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											nlist2.add(obj);
										}
										//获取原有数据
										fltline = obj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cnzbc += nlist1.size();
												jnzbc += nlist2.size();
											}else{
												cnzbc += (int)Math.ceil(nlist1.size()/2.0);
												jnzbc += (int)Math.ceil(nlist2.size()/2.0);
											}
										}else{
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}
									}
								}else{
									nzbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}else{
											cnzbc += (int)Math.ceil(nlist1.size()/2.0);
											jnzbc += (int)Math.ceil(nlist2.size()/2.0);
										}
									}else{
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}
									nlist1 = new ArrayList<Z_Airdata>();
									nlist2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										nlist1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										nlist2.add(obj);
									}
									//获取原有数据
									fltline = obj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}else{
											cnzbc += (int)Math.ceil(nlist1.size()/2.0);
											jnzbc += (int)Math.ceil(nlist2.size()/2.0);
										}
									}else{
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}
								}
							}else{
								if(prevDate.equals(date)){
									String prevFlt = prevObj.getFlt_Nbr();
									String flt = obj.getFlt_Nbr();
									if(prevFlt.equals(flt)){
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											nlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											nlist2.add(obj);
										}
										//获取原有数据
										String fltline = obj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cnzbc += nlist1.size();
												jnzbc += nlist2.size();
											}else{
												cnzbc += (int)Math.ceil(nlist1.size()/2.0);
												jnzbc += (int)Math.ceil(nlist2.size()/2.0);
											}
										}else{
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}
									}else{
										nzbc++;
										//获取原有数据
										String fltline = prevObj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cnzbc += nlist1.size();
												jnzbc += nlist2.size();
											}else{
												cnzbc += (int)Math.ceil(nlist1.size()/2.0);
												jnzbc += (int)Math.ceil(nlist2.size()/2.0);
											}
										}else{
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}
										nlist1 = new ArrayList<Z_Airdata>();
										nlist2 = new ArrayList<Z_Airdata>();
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											nlist1.add(obj);
										}else if(obj.getArrv_Airpt_Cd().equals(airport)){
											nlist2.add(obj);
										}
										//获取原有数据
										fltline = obj.getFlt_Rte_Cd();
										if(fltline.length()==9){
											String midAirport = fltline.substring(3,6);
											if(midAirport.equals(airport)){//经停
												cnzbc += nlist1.size();
												jnzbc += nlist2.size();
											}else{
												cnzbc += (int)Math.ceil(nlist1.size()/2.0);
												jnzbc += (int)Math.ceil(nlist2.size()/2.0);
											}
										}else{
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}
									}
								}else{
									nzbc++;
									//获取原有数据
									String fltline = prevObj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}else{
											cnzbc += (int)Math.ceil(nlist1.size()/2.0);
											jnzbc += (int)Math.ceil(nlist2.size()/2.0);
										}
									}else{
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}
									nlist1 = new ArrayList<Z_Airdata>();
									nlist2 = new ArrayList<Z_Airdata>();
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										nlist1.add(obj);
									}else if(obj.getArrv_Airpt_Cd().equals(airport)){
										nlist2.add(obj);
									}
									//获取原有数据
									fltline = obj.getFlt_Rte_Cd();
									if(fltline.length()==9){
										String midAirport = fltline.substring(3,6);
										if(midAirport.equals(airport)){//经停
											cnzbc += nlist1.size();
											jnzbc += nlist2.size();
										}else{
											cnzbc += (int)Math.ceil(nlist1.size()/2.0);
											jnzbc += (int)Math.ceil(nlist2.size()/2.0);
										}
									}else{
										cnzbc += nlist1.size();
										jnzbc += nlist2.size();
									}
								}
							}
						}
					}
					//汇总-所有
					retmap.put("fzkl", zkl);
					retmap.put("fzsr", df2.format(zsr));
					retmap.put("fzsk", zsk);
					retmap.put("fztd", ztd);
					retmap.put("fzbc", zbc);
					//汇总-含过站
					retmap.put("fpzkl", pzkl);
					retmap.put("fpzsr", df2.format(pzsr));
					retmap.put("fpzsk", pzsk);
					retmap.put("fpztd", pztd);
					retmap.put("fpzbc", pzbc);
					//汇总-含甩飞
					retmap.put("ftzkl", tzkl);
					retmap.put("ftzsr", df2.format(tzsr));
					retmap.put("ftzsk", tzsk);
					retmap.put("ftztd", tztd);
					retmap.put("ftzbc", tzbc);
					//汇总-不含过站与甩飞
					retmap.put("fnzkl", nzkl);
					retmap.put("fnzsr", df2.format(nzsr));
					retmap.put("fnzsk", nzsk);
					retmap.put("fnztd", nztd);
					retmap.put("fnzbc", nzbc);
					//出港-所有
					retmap.put("cfzkl", czkl);
					retmap.put("cfzsr", df2.format(czsr));
					retmap.put("cfzsk", czsk);
					retmap.put("cfztd", cztd);
					retmap.put("cfzbc", czbc);
					//出港-含过站
					retmap.put("cfpzkl", cpzkl);
					retmap.put("cfpzsr", df2.format(cpzsr));
					retmap.put("cfpzsk", cpzsk);
					retmap.put("cfpztd", cpztd);
					retmap.put("cfpzbc", cpzbc);
					//出港-含甩飞
					retmap.put("cftzkl", ctzkl);
					retmap.put("cftzsr", df2.format(ctzsr));
					retmap.put("cftzsk", ctzsk);
					retmap.put("cftztd", ctztd);
					retmap.put("cftzbc", ctzbc);
					//出港-不含过站与甩飞
					retmap.put("cfnzkl", cnzkl);
					retmap.put("cfnzsr", df2.format(cnzsr));
					retmap.put("cfnzsk", cnzsk);
					retmap.put("cfnztd", cnztd);
					retmap.put("cfnzbc", cnzbc);
					//进港-所有
					retmap.put("jfzkl", jzkl);
					retmap.put("jfzsr", df2.format(jzsr));
					retmap.put("jfzsk", jzsk);
					retmap.put("jfztd", jztd);
					retmap.put("jfzbc", jzbc);
					//进港-含过站
					retmap.put("jfpzkl", jpzkl);
					retmap.put("jfpzsr", df2.format(jpzsr));
					retmap.put("jfpzsk", jpzsk);
					retmap.put("jfpztd", jpztd);
					retmap.put("jfpzbc", jpzbc);
					//进港-含甩飞
					retmap.put("jftzkl", jtzkl);
					retmap.put("jftzsr", df2.format(jtzsr));
					retmap.put("jftzsk", jtzsk);
					retmap.put("jftztd", jtztd);
					retmap.put("jftzbc", jtzbc);
					//进港-不含过站与甩飞
					retmap.put("jfnzkl", jnzkl);
					retmap.put("jfnzsr", df2.format(jnzsr));
					retmap.put("jfnzsk", jnzsk);
					retmap.put("jfnztd", jnztd);
					retmap.put("jfnzbc", jnzbc);
					//有数据的天数
					List<String> dayList = new ArrayList<String>();
					for (Z_Airdata z_Airdata : focusData) {
						//判断时间区间
						Long currDateTiime = z_Airdata.getLcl_Dpt_Day().getTime();
						if(currDateTiime>=startTime&&currDateTiime<=endTime){
							String daytemp = sdf.format(z_Airdata.getLcl_Dpt_Day());
							if(!dayList.contains(daytemp)){
								dayList.add(daytemp);
							}
						}
					}
					Collections.sort(dayList);
					for (String dayy : dayList) {
						//汇总-所有
						int zkltemp = 0;
						double zsrtemp = 0.0;
						//汇总-含过站
						int pzkltemp = 0;
						double pzsrtemp = 0.0;
						//汇总-含甩飞
						int tzkltemp = 0;
						double tzsrtemp = 0.0;
						//汇总-不含过站与甩飞
						int nzkltemp = 0;
						double nzsrtemp = 0.0;
						//进港-所有
						int jzkltemp = 0;
						double jzsrtemp = 0.0;
						//进港-含过站
						int jpzkltemp = 0;
						double jpzsrtemp = 0.0;
						//进港-含甩飞
						int jtzkltemp = 0;
						double jtzsrtemp = 0.0;
						//进港-不会过站与甩飞
						int jnzkltemp = 0;
						double jnzsrtemp = 0.0;
						//出港-所有
						int czkltemp = 0;
						double czsrtemp = 0.0;
						//出港-含过站
						int cpzkltemp = 0;
						double cpzsrtemp = 0.0;
						//出港-含甩飞
						int ctzkltemp = 0;
						double ctzsrtemp = 0.0;
						//出港-不含过站与甩飞
						int cnzkltemp = 0;
						double cnzsrtemp = 0.0;
						for (Z_Airdata z_Airdata : focusData) {
							String daytemp = sdf.format(z_Airdata.getLcl_Dpt_Day());
							String fltAroute = z_Airdata.getFlt_Rte_Cd();
							if(dayy.equals(daytemp)){
								zsrtemp = zsrtemp + z_Airdata.getTotalNumber()/1000.0;
								zkltemp = zkltemp + z_Airdata.getPgs_Per_Cls();
								if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
									jzsrtemp = jzsrtemp + z_Airdata.getTotalNumber()/1000.0;
									jzkltemp = jzkltemp + z_Airdata.getPgs_Per_Cls();
								}
								if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
									czsrtemp = czsrtemp + z_Airdata.getTotalNumber()/1000.0;
									czkltemp = czkltemp + z_Airdata.getPgs_Per_Cls();
								}
								if((airport.equals(z_Airdata.getDpt_AirPt_Cd())||airport.equals(z_Airdata.getArrv_Airpt_Cd()))
										||(airport.equals(fltAroute.substring(3, 6))&&fltAroute.length()==9
										&&!airport.equals(z_Airdata.getDpt_AirPt_Cd())&&!airport.equals(z_Airdata.getArrv_Airpt_Cd()))){
									pzsrtemp = pzsrtemp + z_Airdata.getTotalNumber()/1000.0;
									pzkltemp = pzkltemp + z_Airdata.getPgs_Per_Cls();
									if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
										jpzsrtemp = jpzsrtemp + z_Airdata.getTotalNumber()/1000.0;
										jpzkltemp = jpzkltemp + z_Airdata.getPgs_Per_Cls();
									}
									if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
										cpzsrtemp = cpzsrtemp + z_Airdata.getTotalNumber()/1000.0;
										cpzkltemp = cpzkltemp + z_Airdata.getPgs_Per_Cls();
									}
								}
								if((airport.equals(z_Airdata.getDpt_AirPt_Cd())||airport.equals(z_Airdata.getArrv_Airpt_Cd())
										||((fltAroute.startsWith(airport)||fltAroute.endsWith(airport))
												&&!airport.equals(z_Airdata.getDpt_AirPt_Cd())&&!airport.equals(z_Airdata.getArrv_Airpt_Cd())))){
									tzsrtemp = tzsrtemp + z_Airdata.getTotalNumber()/1000.0;
									tzkltemp = tzkltemp + z_Airdata.getPgs_Per_Cls();
									if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
										jtzsrtemp = jtzsrtemp + z_Airdata.getTotalNumber()/1000.0;
										jtzkltemp = jtzkltemp + z_Airdata.getPgs_Per_Cls();
									}
									if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
										ctzsrtemp = ctzsrtemp + z_Airdata.getTotalNumber()/1000.0;
										ctzkltemp = ctzkltemp + z_Airdata.getPgs_Per_Cls();
									}
								}
								if(airport.equals(z_Airdata.getDpt_AirPt_Cd())||airport.equals(z_Airdata.getArrv_Airpt_Cd())){
									nzsrtemp = nzsrtemp + z_Airdata.getTotalNumber()/1000.0;
									nzkltemp = nzkltemp + z_Airdata.getPgs_Per_Cls();
									if(z_Airdata.getArrv_Airpt_Cd().equals(airport)){//进港
										jnzsrtemp = jnzsrtemp + z_Airdata.getTotalNumber()/1000.0;
										jnzkltemp = jnzkltemp + z_Airdata.getPgs_Per_Cls();
									}
									if(z_Airdata.getDpt_AirPt_Cd().equals(airport)){//出港
										cnzsrtemp = cnzsrtemp + z_Airdata.getTotalNumber()/1000.0;
										cnzkltemp = cnzkltemp + z_Airdata.getPgs_Per_Cls();
									}
								}
							}
						}
						//汇总-所有
						HomePageData homePageData = new HomePageData();
						homePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
						homePageData.setPersons(zkltemp+"");
						homePageData.setZsr(df.format(zsrtemp));
						HomePageDataListzs.add(homePageData);
						//汇总-含过站
						HomePageData phomePageData = new HomePageData();
						phomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
						phomePageData.setPersons(pzkltemp+"");
						phomePageData.setZsr(df.format(pzsrtemp));
						pHomePageDataListzs.add(phomePageData);
						//汇总-含甩飞
						HomePageData thomePageData = new HomePageData();
						thomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
						thomePageData.setPersons(tzkltemp+"");
						thomePageData.setZsr(df.format(tzsrtemp));
						tHomePageDataListzs.add(thomePageData);
						//汇总-不会过站与甩飞
						HomePageData nhomePageData = new HomePageData();
						nhomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
						nhomePageData.setPersons(nzkltemp+"");
						nhomePageData.setZsr(df.format(nzsrtemp));
						nHomePageDataListzs.add(nhomePageData);
						//出港-所有
						HomePageData chomePageData = new HomePageData();
						chomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
						chomePageData.setPersons(czkltemp+"");
						chomePageData.setZsr(df.format(czsrtemp));
						cHomePageDataListzs.add(chomePageData);
						//出港-含过站
						HomePageData cphomePageData = new HomePageData();
						cphomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
						cphomePageData.setPersons(cpzkltemp+"");
						cphomePageData.setZsr(df.format(cpzsrtemp));
						cpHomePageDataListzs.add(cphomePageData);
						//出港-含甩飞
						HomePageData cthomePageData = new HomePageData();
						cthomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
						cthomePageData.setPersons(ctzkltemp+"");
						cthomePageData.setZsr(df.format(ctzsrtemp));
						ctHomePageDataListzs.add(cthomePageData);
						//出港-不含过站与甩飞
						HomePageData cnhomePageData = new HomePageData();
						cnhomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
						cnhomePageData.setPersons(cnzkltemp+"");
						cnhomePageData.setZsr(df.format(cnzsrtemp));
						cnHomePageDataListzs.add(cnhomePageData);
						//进港-所有
						HomePageData jhomePageData = new HomePageData();
						jhomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
						jhomePageData.setPersons(jzkltemp+"");
						jhomePageData.setZsr(df.format(jzsrtemp));
						jHomePageDataListzs.add(jhomePageData);
						//进港-含过站
						HomePageData jphomePageData = new HomePageData();
						jphomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
						jphomePageData.setPersons(jpzkltemp+"");
						jphomePageData.setZsr(df.format(jpzsrtemp));
						jpHomePageDataListzs.add(jphomePageData);
						//进港-含甩飞
						HomePageData jthomePageData = new HomePageData();
						jthomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
						jthomePageData.setPersons(jtzkltemp+"");
						jthomePageData.setZsr(df.format(jtzsrtemp));
						jtHomePageDataListzs.add(jthomePageData);
						//进港-不含过站与甩飞
						HomePageData jnhomePageData = new HomePageData();
						jnhomePageData.setDate(Integer.parseInt(dayy.split("-")[1])+"."+Integer.parseInt(dayy.split("-")[2]));
						jnhomePageData.setPersons(jnzkltemp+"");
						jnhomePageData.setZsr(df.format(jnzsrtemp));
						jnHomePageDataListzs.add(jnhomePageData);
					}
				}
				//汇总-所有
				retmap.put("fzsData", HomePageDataListzs);//汇总
				//汇总-含过站
				retmap.put("fpzsData", pHomePageDataListzs);//汇总
				//汇总-含甩飞
				retmap.put("ftzsData", tHomePageDataListzs);//汇总
				//汇总-不含过站与甩飞
				retmap.put("fnzsData", nHomePageDataListzs);//汇总
				//出港-所有
				retmap.put("cfzsData", cHomePageDataListzs);//出港
				//出港-含过站
				retmap.put("cfpzsData", cpHomePageDataListzs);//出港
				//出港-含甩飞
				retmap.put("cftzsData", ctHomePageDataListzs);//出港
				//出港-不含过站与甩飞
				retmap.put("cfnzsData", cnHomePageDataListzs);//出港
				//进港-所有
				retmap.put("jfzsData", jHomePageDataListzs);//进港
				//进港-含过站
				retmap.put("jfpzsData", jpHomePageDataListzs);//进港
				//进港-含甩飞
				retmap.put("jftzsData", jtHomePageDataListzs);//进港
				//进港-不含过站与甩飞
				retmap.put("jfnzsData", jnHomePageDataListzs);//进港
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return null;
		}
		return retmap;
	}
	
	@SuppressWarnings("unchecked")
	public List<HomePageData> sortNew(List<HomePageData> targetList, final String sortField, final String sortMode) {  
	        Collections.sort(targetList, new Comparator() {  
	        	public int compare(Object obj1, Object obj2) {   
//	            	double retVal = 0.0;
	        		int retVal = 0;
	                try {  
	                    //首字母转大写  
//	                    String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");   
//	                    String methodStr="get"+newStr;  
//	                    Method method1 = ((HomePageData)obj1).getClass().getMethod(methodStr, null);  
//	                    Method method2 = ((HomePageData)obj2).getClass().getMethod(methodStr, null);  
//	                    if (sortMode != null && "DESC".equals(sortMode)) {  
//	                        retVal = Double.parseDouble(method1.invoke(((HomePageData) obj2), null).toString())-(Double.parseDouble(method1.invoke(((HomePageData) obj1), null).toString())); // 倒序
//	                    } else {  
//	                        retVal = Double.parseDouble(method2.invoke(((HomePageData) obj1), null).toString())-(Double.parseDouble(method1.invoke(((HomePageData) obj2), null).toString())); // 正序
//	                    } 
	                    if("zsr".equals(sortField)){
	                    	HomePageData data1 = (HomePageData) obj1;
	                    	HomePageData data2 = (HomePageData) obj2;
	                    	if("DESC".equals(sortMode)){
	                    		if(Double.valueOf(data1.getZsr()).doubleValue()>Double.valueOf(data2.getZsr()).doubleValue()){
	                    			retVal = -1;
	                    		}else if(Double.valueOf(data1.getZsr()).doubleValue()<Double.valueOf(data2.getZsr()).doubleValue()){
	                    			retVal = 1;
	                    		}
	                    	}else{
	                    		if(Double.valueOf(data1.getZsr()).doubleValue()>Double.valueOf(data2.getZsr()).doubleValue()){
	                    			retVal = 1;
	                    		}else if(Double.valueOf(data1.getZsr()).doubleValue()<Double.valueOf(data2.getZsr()).doubleValue()){
	                    			retVal = -1;
	                    		}
	                    	}
	                    }else if("persons".equals(sortField)){
	                    	HomePageData data1 = (HomePageData) obj1;
	                    	HomePageData data2 = (HomePageData) obj2;
	                    	if("DESC".equals(sortMode)){
	                    		if(Integer.valueOf(data1.getPersons())>Integer.valueOf(data2.getPersons())){
	                    			retVal = -1;
	                    		}else if(Integer.valueOf(data1.getPersons())<Integer.valueOf(data2.getPersons())){
	                    			retVal = 1;
	                    		}
	                    	}else{
	                    		if(Integer.valueOf(data1.getPersons())>Integer.valueOf(data2.getPersons())){
	                    			retVal = 1;
	                    		}else if(Integer.valueOf(data1.getPersons())<Integer.valueOf(data2.getPersons())){
	                    			retVal = -1;
	                    		}
	                    	}
	                    }else if("kzl".equals(sortField)){
	                    	HomePageData data1 = (HomePageData) obj1;
	                    	HomePageData data2 = (HomePageData) obj2;
	                    	if("DESC".equals(sortMode)){
	                    		if(Double.valueOf(data1.getKzl()).doubleValue()>Double.valueOf(data2.getKzl()).doubleValue()){
	                    			retVal = -1;
	                    		}else if(Double.valueOf(data1.getKzl()).doubleValue()<Double.valueOf(data2.getKzl()).doubleValue()){
	                    			retVal = 1;
	                    		}
	                    	}else{
	                    		if(Double.valueOf(data1.getKzl()).doubleValue()>Double.valueOf(data2.getKzl()).doubleValue()){
	                    			retVal = 1;
	                    		}else if(Double.valueOf(data1.getKzl()).doubleValue()<Double.valueOf(data2.getKzl()).doubleValue()){
	                    			retVal = -1;
	                    		}
	                    	}
	                    }else if("zdl".equals(sortField)){
	                    	HomePageData data1 = (HomePageData) obj1;
	                    	HomePageData data2 = (HomePageData) obj2;
	                    	if("DESC".equals(sortMode)){
	                    		if(Double.valueOf(data1.getZdl()).doubleValue()>Double.valueOf(data2.getZdl()).doubleValue()){
	                    			retVal = -1;
	                    		}else if(Double.valueOf(data1.getZdl()).doubleValue()<Double.valueOf(data2.getZdl()).doubleValue()){
	                    			retVal = 1;
	                    		}
	                    	}else{
	                    		if(Double.valueOf(data1.getZdl()).doubleValue()>Double.valueOf(data2.getZdl()).doubleValue()){
	                    			retVal = 1;
	                    		}else if(Double.valueOf(data1.getZdl()).doubleValue()<Double.valueOf(data2.getZdl()).doubleValue()){
	                    			retVal = -1;
	                    		}
	                    	}
	                    }
	                } catch (Exception e) {  
	                    throw new RuntimeException();  
	                }  
	                return (int)Math.ceil(retVal);
	            }    
	        }); 
	        return targetList;
	    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })  
    public void sort(List<HomePageData> targetList, final String sortField, final String sortMode) {  
        Collections.sort(targetList, new Comparator() {  
        	public int compare(Object obj1, Object obj2) {   
            	double retVal = 0.0;  
                try {  
                    //首字母转大写  
                    String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");   
                    String methodStr="get"+newStr;  
                    Method method1 = ((HomePageData)obj1).getClass().getMethod(methodStr, null);  
                    Method method2 = ((HomePageData)obj2).getClass().getMethod(methodStr, null);  
                    if (sortMode != null && "desc".equals(sortMode)) {  
                        retVal = Double.parseDouble(method2.invoke(((HomePageData) obj2), null).toString())-(Double.parseDouble(method1.invoke(((HomePageData) obj1), null).toString())); // 倒序  
                    } else {  
                        retVal = Double.parseDouble(method2.invoke(((HomePageData) obj1), null).toString())-(Double.parseDouble(method1.invoke(((HomePageData) obj2), null).toString())); // 正序  
                    }  
                } catch (Exception e) {  
                    throw new RuntimeException();  
                }  
                return (int)retVal;  
            }  
        });  
    }

	@Override
	public String getYesterdayAllLineTraveller(HomePageQuery homePageQuery) {
		String name = homePageQuery.getAirPort();
		String code = outPortMapper.getAirCodeByName(name);
		homePageQuery.setAirPort(code);
		String ruselt = homePageMapper.getYesterdayAllLineTraveller(homePageQuery);
		homePageQuery.setAirPort(name);
		return ruselt;
	}

	@Override
	public List<AirInfoData> getAirInfoDataList() {
//		List<AirInfoData> airlist = airInfoDataMapper.getAirInfoDataList();
		List<AirInfoData> airlist = airInfoDataMapper.getAirInfoDataListNew();
		//调用外部接口
		//在飞航点和国内航线和最新日期
		Gson gson = new Gson();
		JsonObject returnData2 = null;
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_nationwide_waypoint/").header("Accept", "*/*").ignoreContentType(true);
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			returnData2 = returnData.get("result").getAsJsonObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		DecimalFormat df  = new DecimalFormat("#,###");
		for (AirInfoData airInfoData : airlist) {
			String iata = airInfoData.getIata();
			if(returnData2.get(iata)!=null){
				JsonObject jsonn = returnData2.get(iata).getAsJsonObject();
				String onlineNbr = jsonn.get("cn_num").getAsString();
				String guojiNbr = jsonn.get("en_num").getAsString();
				String onlineTime = jsonn.get("cn_date").getAsString();
				String guojiTime = jsonn.get("en_date").getAsString();
				airInfoData.setGlFlyNum(onlineNbr);
				airInfoData.setGjFlyNum(guojiNbr);
				airInfoData.setGlhdDate(onlineTime);
				airInfoData.setGjhdDate(guojiTime);
			}
			if(!TextUtil.isEmpty(airInfoData.getFlyPerson())){
				try {
					airInfoData.setFlyPerson(df.format(Integer.parseInt(airInfoData.getFlyPerson())));
				} catch (Exception e) {
				}
			}
		}
		return airlist;
	}
	@Override
	public List<AirInfoData> getForeAirInfoDataList() {
		//国外机场表里取出来的所有的数据
		List<AirInfoData> airlisttemp = airInfoDataMapper.getAirInfoDataListByOtherContory();
		return airlisttemp;
	}
	
	@Override
	public EvenPort getAirportFocusData(HomePageQuery homePageQuery) {
		EvenPort evenPort = null;
		if(homePageQuery==null){
			log.debug("getAirPortFocusData:homePageQuery is invalide");
			return evenPort;
		}
		try {
			evenPort = homePageMapper.selectFocusData(homePageQuery);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return evenPort;
		}
		return evenPort;
	}

	@Override
	public int getAirportTravellerIn(HomePageQuery homePageQuery) {
		int travellerCount = 0;
		if(homePageQuery==null){
			log.debug("getAirportTravellerIn:homePageQuery is invalidate");
			return travellerCount;
		}
		try {
			travellerCount = homePageMapper.selectTravellerIN(homePageQuery);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return travellerCount;
		}
		return travellerCount;
	}

	@Override
	public int getAirportTravellerOUT(HomePageQuery homePageQuery) {
		int travellerCount = 0;
		if(homePageQuery==null){
			log.debug("getAirportTravellerOUT:homePageQuery is invalide");
			return travellerCount;
		}
		try {
			travellerCount = homePageMapper.selectTravellerOUT(homePageQuery);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return travellerCount;
		}
		return travellerCount;
	}

	@Override
	public int getFlightCount(HomePageQuery homePageQuery) {
		int fltCount = 0;
		try {
			List<Integer> dayList = homePageMapper.selectFlightCount(homePageQuery);
			if(dayList!=null&&dayList.size()>0){
				for(int i=0;i<dayList.size();i++){
					fltCount += dayList.get(i);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return fltCount;
		}
		return fltCount;
	}

	@Override
	public List<String> getOnInstrutionsFlyNbrs(String airportCode) {
		List<String> flyNbrsTemp = homePageMapper.getIntrutionsFlyNbr(airportCode);
		//航班号配对
		String tempFlyNum = "";
		String tempFlyNum2 = "";
		List<String> hblist = new ArrayList<String>();
		for (String str : flyNbrsTemp) {
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
		List<String> flyNbrs = new ArrayList<String>();
		for (String string : hblist) {
			String [] tempstr = string.split("/");
			String newStr = "";
			if(tempstr.length>=2){
				newStr = tempstr[0] +"/"+ tempstr[1].substring(4,6);
			}
			flyNbrs.add(newStr);
		}
		return flyNbrs;
	}

	@Override
	public List<String> getChinaAirLineDataList() {
		//调用外部接口
		Gson gson=new Gson();
//		List<String> flyList = homePageMapper.getChinaAirLineDataList(null);
		List<String> flyListtep = new ArrayList<String>();
		List<String> flyList = new ArrayList<String>();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_news_leg_by_airport/").header("Accept", "*/*").ignoreContentType(true);
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			flyListtep = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<String>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String string : flyListtep) {
			flyList.add(string.replace("-", ""));
		}
		return flyList;
	}

	@Override
	public List<AirportData> getIcaoIataList() {
		return outPortMapper.getAirPortData();
	}

	@Override
	public List<String> getOnInstrutionsLinees() {
//		BuyTicketReportQuery buyTicketReportQuery = new BuyTicketReportQuery();
		List<String> flyNbrsList = sourceDistriMapper.getHavingDataFlyNbrList();
		List<String> airlineList =  buyTicketReportMapper.getAirLineByFlyNbrs(flyNbrsList);
		return airlineList;
	}
	 /** 
     * 根据年 月 获取对应的月份 天数 
     * */
	 public int getDaysByYearMonth(int year, int month) {  
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
	}   
	@Override
	public String getHavingDataByThire(HavingDataQuery havingDataQuery) {
		String status = "2";//0表示无数据，1表示有30天间隔的数据，2表示没有间隔30天的数据
		if(TextUtil.isEmpty(havingDataQuery.getPas_stp())){
			havingDataQuery.setPas_stp(null);
		}
		if(TextUtil.isEmpty(havingDataQuery.getIsGoAndBack())){
			havingDataQuery.setIsGoAndBack(null);
		}
		if(TextUtil.isEmpty(havingDataQuery.getIsIncludePas())){
			havingDataQuery.setIsIncludePas(null);
		}
		if(TextUtil.isEmpty(havingDataQuery.getIsIncludeRdf())){
			havingDataQuery.setIsIncludeRdf(null);
		}
		if(TextUtil.isEmpty(havingDataQuery.getFltNbr())||"汇总".equals(havingDataQuery.getFltNbr())){
			havingDataQuery.setFltNbr(null);
		}else{
			String [] flt = havingDataQuery.getFltNbr().split("/");
			havingDataQuery.setFltNbrq(flt[0]);
			havingDataQuery.setFltNbrh(flt[1]);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!TextUtil.isEmpty(havingDataQuery.getStartTime())&&!TextUtil.isEmpty(havingDataQuery.getEndTime())){
			String end = havingDataQuery.getEndTime();
			if(end.length()<9){
				end = end + "-"+getDaysByYearMonth(Integer.parseInt(end.split("-")[0]),Integer.parseInt(end.split("-")[1]));
			}
			havingDataQuery.setEndTime(end);
			String start = havingDataQuery.getStartTime();
			if(start.length()<9){
				start = start + "-01";
			}
			havingDataQuery.setStartTime(start);
		}
		if(!TextUtil.isEmpty(havingDataQuery.getFlt_ectq())&&havingDataQuery.getFlt_ectq().length()>6){
			//经停航线
			havingDataQuery.setLineFlag("2");
		}else{
			havingDataQuery.setLineFlag("1");
		}
		List<Z_Airdata> tempList = homePageMapper.getTimeData(havingDataQuery);
		List<String> dateList = new ArrayList<String>();
		String strrfalgep = "";
		if(TextUtil.isEmpty(havingDataQuery.getPas_stp())){
			if(TextUtil.isEmpty(havingDataQuery.getIsGoAndBack())){
				strrfalgep = "single_false";
			}else{
				strrfalgep = "single_true";
			}
		}else{
			if(TextUtil.isEmpty(havingDataQuery.getIsGoAndBack())){
				strrfalgep = "true";
			}else{
				strrfalgep = "false";
			}
		}
		Map<String,Object> dataMap2p = TextUtil.getIsIncludeExceptionHangduan(tempList, havingDataQuery.getIsIncludeExceptionHuangduan(),strrfalgep);
		List<Z_Airdata> list1 = (List<Z_Airdata>) dataMap2p.get("zairdataListB");
		for (Z_Airdata z_Airdata : list1) {
			String lcl = sdf.format(z_Airdata.getLcl_Dpt_Day());
			if(!dateList.contains(lcl)){
				dateList.add(lcl);
			}
		}
		String temp = "";
		if(dateList!=null&&dateList.size()>0){
			for (int i=0; i<dateList.size(); i++) {
				if(i==0){
					temp = dateList.get(i);
					String end = havingDataQuery.getEndTime();
					try {
						Date datetemp = sdf.parse(temp);
						Date daytemp = sdf.parse(end);
						if((daytemp.getTime()-datetemp.getTime())/3600/1000/24>=30){
							status = "1";
							break;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
				}else{
					String day = dateList.get(i);
					try {
						Date daytemp = sdf.parse(day);
						Date datetemp = sdf.parse(temp);
						if((datetemp.getTime()-daytemp.getTime())/3600/1000/24>=30){
							status = "1";
							break;
						}else{
							temp = dateList.get(i-1);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if(i==dateList.size()-1){
					temp = dateList.get(i);
					String start = havingDataQuery.getStartTime();
					try {
						Date datetemp = sdf.parse(temp);
						Date daytemp = sdf.parse(start);
						if((datetemp.getTime()-daytemp.getTime())/3600/1000/24>=30){
							status = "1";
							break;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			status = "1";
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see org.ldd.ssm.crm.service.HomePageService#getAllAirLineData(org.ldd.ssm.crm.query.AllAirLineQuery)
	 */
	@Override
	public List<AllAirLineFltDetailData> getAllAirLineData(AllAirLineQuery allAirLineQuery) {
//		String newTime = homePageMapper.getNewLineDate();
		Gson gson=new Gson();
		//调用外部接口
		String newTime = "";
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_new_date/").header("Accept", "*/*").ignoreContentType(true);
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			newTime = returnData.get("result").getAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		allAirLineQuery.setNewTime(newTime);
		List<AllAirLineFltDetailData> list = new ArrayList<AllAirLineFltDetailData>();
		//调用外部接口
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_fltnbr_airtype/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(allAirLineQuery.getAirport())){
				connect.data("airport_code", allAirLineQuery.getAirport().toUpperCase());
			}
			if(!TextUtil.isEmpty(allAirLineQuery.getHangSi())){
				connect.data("air_line", allAirLineQuery.getHangSi());
			}
			if(!TextUtil.isEmpty(allAirLineQuery.getNewTime())){
				connect.data("crwl_date", allAirLineQuery.getNewTime());
			}
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			list = gson.fromJson(returnData.get("result"), new TypeToken<List<AllAirLineFltDetailData>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<AirInfoData> findFilterParamAirPort(FilterParamAirPortQuery query) {
		String[] fly_area_grade = query.getFly_area_grade();//飞行区级别
		//拼接飞行区级别sql
		String fly_area_gradeSql = "";
		if(fly_area_grade!=null&&fly_area_grade.length>0){
			fly_area_gradeSql = "(";
			for (int i = 0; i < fly_area_grade.length; i++) {
				String string = fly_area_grade[i];
				if(i<fly_area_grade.length-1){
					fly_area_gradeSql += "air.airfieldLvl = '"+string+"' or ";
				}else{
					fly_area_gradeSql += "air.airfieldLvl = '"+string+"')";
				}
			}
		}
		//拼接机场吞吐量sql
		String[] lastyear_person = query.getLastyear_person();//机场吞吐量
		String lastyear_personSql = "";
		if(lastyear_person!=null&&lastyear_person.length==1){
			lastyear_personSql += "flow.passengerThroughput >= "+lastyear_person[0]+"";
		}else if(lastyear_person!=null&&lastyear_person.length==2){
			lastyear_personSql += "(flow.passengerThroughput >= "+lastyear_person[0]+" and ";
			lastyear_personSql += "flow.passengerThroughput <= "+lastyear_person[1]+")";
		}
		
		String[] subsidy_type = query.getSubsidy_type();//机场补贴
		//拼接机场补贴sql
		String subsidy_typeSql = "";
		if(subsidy_type!=null&&subsidy_type.length==1){
			subsidy_typeSql += "air.isRewardPolicy = '否'";
		}else if(subsidy_type!=null&&subsidy_type.length==2){
			subsidy_typeSql += "(air.isRewardPolicy = '否' or ";
			subsidy_typeSql += "air.isRewardPolicy = '是')";
			
		}
		//拼接机场类别
		String[] airport_type = query.getAirport_type();//机场类别
		String airpotCls = "";//判断是否需要高原机场
		String airpotClsFalse = "";//判断是否需要高原机场
		
		String specialAirport = "";//判断是否需要特殊机场
		String specialAirportFalse = "";//判断是否需要特殊机场
		
		String port = "";//判断是否需要口岸机场
		String portFalse = "";//判断是否需要口岸机场
		
		String coordinateAirport = "";//判断是否需要协调机场
		String coordinateAirportFalse = "";//判断是否需要协调机场
		for (String string : airport_type) {
			//判断数组当前的条件属于哪类
			if(string.contains("高原")){
				if(string.contains("非")){
					airpotClsFalse+="air.airpotCls != '高原机场'";
				}else{
					airpotCls+="air.airpotCls = '高原机场'";
				}
			}else if(string.contains("特殊")){
				if(string.contains("非")){
					specialAirportFalse+="air.airpotCls = '否'";
				}else{
					specialAirport+="air.airpotCls = '是'";
				}
			}else if(string.contains("口岸")){
				if(string.contains("非")){
					portFalse+="air.port = '否'";
				}else{
					port+="air.port = '是'";
				}
			}else if(string.contains("协调")){
				if(string.contains("非")){
					coordinateAirportFalse+=" air.coordinateAirport = '否' ";
				}else{
					coordinateAirport+=" air.coordinateAirport = '是' ";
				}
			}
		}
		//拼接机场类别sql
		String airportSql = "";
		if (!TextUtil.isEmpty(airpotCls)) {
			airportSql+=airpotCls+" or ";
		}
		if (!TextUtil.isEmpty(airpotClsFalse)) {
			airportSql+=airpotClsFalse+" or ";
		}

		if (!TextUtil.isEmpty(specialAirport)) {
			airportSql+=specialAirport+" or ";
		}
		if (!TextUtil.isEmpty(specialAirportFalse)) {
			airportSql+=specialAirportFalse+" or ";
		}

		if (!TextUtil.isEmpty(port)) {
			airportSql+=port+" or ";
		}
		if (!TextUtil.isEmpty(portFalse)) {
			airportSql+=portFalse+" or ";
		}

		if (!TextUtil.isEmpty(coordinateAirport)) {
			airportSql+=coordinateAirport+" or ";
		}
		if (!TextUtil.isEmpty(coordinateAirportFalse)) {
			airportSql+=coordinateAirportFalse+" or ";
		}
		if (!TextUtil.isEmpty(airportSql)){
			airportSql = airportSql.substring(0, airportSql.length()-3);
			
			airportSql ="("+airportSql+")";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		String lastYear = new SimpleDateFormat("yyyy").format(calendar.getTime());
		String SelectSql = "select air.iATA iata,air.city city from airportinfonewthree air join flowofsubsidiary flow on air.id = flow.airportinfonewthree_id WHERE 1=1"; 
		//拼接飞行区级别sql
		if(!TextUtil.isEmpty(fly_area_gradeSql)){
			SelectSql += " and " + fly_area_gradeSql;
		}
		//拼接机场吞吐量sql
		if(!TextUtil.isEmpty(lastyear_personSql)){
			SelectSql += " and " + lastyear_personSql +" and flow.year = '"+lastYear+"'";
		}
		//拼接机场补贴sql
		if(!TextUtil.isEmpty(subsidy_typeSql)){
			SelectSql += " and " + subsidy_typeSql;
		}
		//拼接机场类别sql
		if(!TextUtil.isEmpty(airportSql)){
			SelectSql += " and " + airportSql;
		}
		List<AirInfoData> lists = homePageMapper.findFilterParamAirPort(SelectSql);
		
		return lists;
	}

	
}
