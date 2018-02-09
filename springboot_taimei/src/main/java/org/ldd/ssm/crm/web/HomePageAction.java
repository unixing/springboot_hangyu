package org.ldd.ssm.crm.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.AirInfoData;
import org.ldd.ssm.crm.domain.AirportData;
import org.ldd.ssm.crm.domain.AirportFocus;
import org.ldd.ssm.crm.domain.AllAirLineFltDetailData;
import org.ldd.ssm.crm.domain.ChinaAriLineData;
import org.ldd.ssm.crm.domain.CityCoordinate;
import org.ldd.ssm.crm.domain.Company;
import org.ldd.ssm.crm.domain.Coords;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.HomePageData;
import org.ldd.ssm.crm.query.AllAirLineQuery;
import org.ldd.ssm.crm.query.FilterParamAirPortObject;
import org.ldd.ssm.crm.query.FilterParamAirPortQuery;
import org.ldd.ssm.crm.query.HavingDataQuery;
import org.ldd.ssm.crm.query.HomePageQuery;
import org.ldd.ssm.crm.service.AirportFocusService;
import org.ldd.ssm.crm.service.FczreptileService;
import org.ldd.ssm.crm.service.HomePageService;
import org.ldd.ssm.crm.service.ICompanyService;
import org.ldd.ssm.crm.service.IFocusFlightService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;


/**
 * 首页控制类
 * @Title:HomePageAction 
 * @Description:
 * @author taimei-gds 
 * @date 2016-6-12 下午3:44:01
 */
@Controller
public class HomePageAction {
	@Autowired
	private HomePageService homePageService;
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private AirportFocusService focusService;
	@Autowired
	private FczreptileService fczreptileService;
	@Autowired
	private IFocusFlightService iFocusFlightService;
	
	@RequestMapping("/homePage")
	@MethodNote(comment="默认:16")
	@MyMethodNote(comment="首页主页:2")
	public String getHomePage() {
		return "index/homePage";
	}
	@RequestMapping("/getFlash")
	public String getFlash() {
		return "index/flashs";
	}
	@RequestMapping("/success")
	@MethodNote(comment="默认:16")
	@MyMethodNote(comment="首页主页:2")
	public String getsuccess() {
		return "newHtml/success";
	}
	@RequestMapping("/fail")
	@MethodNote(comment="默认:16")
	public String getfail() {
		return "newHtml/fail";
	}
	
	/**
	 * 得到昨日收益、昨日总班次、进港、出港
	 * @Title: getYesterdayEarnings 
	 * @Description:  
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/getAirportData")
//	@MethodNote(comment="默认:18")
	@ResponseBody
	public Map<String, Object> getAirportData(){
		Map<String, Object> rootMap = new HashMap<String, Object>();
		//得到数据库中最新数据的日期
		String companyId = UserContext.getCompanyId();
		Company  company = companyService.load(Long.parseLong(companyId));
		HomePageQuery homePageQuery = new HomePageQuery();
		homePageQuery.setAirPort(company.getCpyAds());
		//homePageQuery.setAirPort("泸州");
		/*Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());*/
//		String yesterday = "2015-7-15";
		homePageQuery.setDate_sce("中航");
		homePageQuery.setDate(homePageService.getNewDate(homePageQuery));
		DecimalFormat df =  new DecimalFormat("0");
		//昨日收益
		rootMap.put("yesterdayEarnings",df.format(Double.parseDouble((homePageService.getYesterdayEarnings(homePageQuery)==null?"0.00":homePageService.getYesterdayEarnings(homePageQuery)))));
		//昨日总班次
		rootMap.put("yesterdayClasses", homePageService.getYesterdayClasses(homePageQuery));
		//昨日进港
		String yesterdayPutin = homePageService.getYesterdayPutin(homePageQuery);
		rootMap.put("yesterdayPutin", yesterdayPutin==null?"0":yesterdayPutin);
		//昨日出港
		String yesterdayLeave = homePageService.getYesterdayLeave(homePageQuery);
		rootMap.put("yesterdayLeave", yesterdayLeave==null?"0":yesterdayLeave);
		return rootMap;
	}
	
	/**
	 * 机场近10天的流量走势
	 * @Title: getAirportFlowData 
	 * @Description:  
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/getAirportFlowData")
	@ResponseBody
	public Map<String, Object> getAirportFlowData(){
		Map<String, Object> rootMap = new HashMap<String, Object>();
		String companyId = UserContext.getCompanyId();
		Company  company = companyService.load(Long.parseLong(companyId));
		HomePageQuery homePageQuery = new HomePageQuery();
		homePageQuery.setAirPort(company.getCpyAds());
//		homePageQuery.setAirPort("泸州");
		/*Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.DATE,   -11);
		String qianshiday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal2.getTime());*/
		homePageQuery.setDate_sce("中航");
		
		String yesterday = homePageService.getNewDate(homePageQuery);
//		String yesterday = "2016-4-15";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateStart = null;
		try {
			dateStart = sdf.parse(yesterday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long endtime  = dateStart.getTime()-24*3600*1000*9;
		Date dateEnd = new Date(endtime);
		String  qianshiday = sdf.format(dateEnd);
		
		homePageQuery.setStartTime(qianshiday);
		homePageQuery.setEndTime(yesterday);
		
		rootMap.put("airportFlowDataList", homePageService.getAirportFlowDataList(homePageQuery));
		rootMap.put("airportFlowDataList2", homePageService.getAirportFlow2DataList(homePageQuery));
		return rootMap;
	}
	
	/**
	 * 获得机场前十指标数据
	 * @Title: getAirportNormData 
	 * @Description:  
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/getAirportNormData")
	@ResponseBody
	public Map<String, Object> getAirportNormData(){
		Map<String, Object> rootMap = new HashMap<String, Object>();
		String companyId = UserContext.getCompanyId();
		Company  company = companyService.load(Long.parseLong(companyId));
		HomePageQuery homePageQuery = new HomePageQuery();
		homePageQuery.setAirPort(company.getCpyAds());
//		homePageQuery.setAirPort("泸州");
		/*Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());*/
//		String yesterday = "2015-7-15";
		homePageQuery.setDate_sce("中航");
		homePageQuery.setDate(homePageService.getNewDate(homePageQuery));
		rootMap.put("airportKzlData", homePageService.getAirportKzlDataList(homePageQuery));
		rootMap.put("airportZglData", homePageService.getAirportZglDataList(homePageQuery));
		rootMap.put("airportZsrData", homePageService.getAirportZsrDataList(homePageQuery));
		return rootMap;
	}
	@RequestMapping(value="/restful/getChinaAirLineData",produces="text/plain;charset=UTF-8")
	@MethodNote(comment="默认:16")
	@MyMethodNote(comment="全国航线数据查询:2")
	@ResponseBody
	public String getChinaAirLineData(){
		Map<String, Object> rootMap = new HashMap<String, Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		List<String> airLineList = homePageService.getChinaAirLineDataList();
//		List<CityCoordinate> cityCoordinateList = homePageService.getCityCoordinateList();
//		Map<String,String> cityCoordinateMap = new HashMap<String,String>();
//		for (CityCoordinate cityCoordinate : cityCoordinateList) {
//			cityCoordinateMap.put(cityCoordinate.getCityName(), cityCoordinate.getCityCoordinatee());
//		}
		Map<String, AirportData> airportDataMap = homePageService.getAirportInfoMap();
		List<ChinaAriLineData> chinaAriLineDataList = new ArrayList<ChinaAriLineData>();
		for (String airLine : airLineList) {
			ChinaAriLineData chinaAriLineData = new ChinaAriLineData();
			String fromName = airLine.substring(0, 3);
			String toName = airLine.substring(3, 6);
			if(airportDataMap.get(fromName)==null||airportDataMap.get(toName)==null){
				continue;
			}
			String namef = airportDataMap.get(fromName).getAptChaNam();
			String namet = airportDataMap.get(toName).getAptChaNam();
			chinaAriLineData.setFromName(namef);
			chinaAriLineData.setToName(namet);
			Coords coords = new Coords();
			String str1 = airportDataMap.get(fromName).getCoordinate();
			String str2 = airportDataMap.get(namet).getCoordinate();
			String [] strsFromName = new String[2];
			String [] strsToName = new String[2];
			if(!TextUtil.isEmpty(str1)){
				strsFromName = str1.split(",");
			}
			if(!TextUtil.isEmpty(str2)){
				strsToName = str2.split(",");
			}
			coords.setFromName(strsFromName);
			coords.setToName(strsToName);
			chinaAriLineData.setCoords(coords);
			chinaAriLineDataList.add(chinaAriLineData);
		}
		rootMap.put("chinaAriLineDataList",chinaAriLineDataList );
		rootMap.put("AriLineAirportDataList",airLineList );
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	@RequestMapping("/getAirportWeather")
	@MethodNote(comment="默认:16")
	@MyMethodNote(comment="机场天气查询:2")
	@ResponseBody
	public Map<String, Object>  getAirportWeather(String ioca){
		Map<String, Object> rootMap = new HashMap<String, Object>();
		String retStrtemp = "";
		try {
			Connection connect = Jsoup.connect("http://report.qdatm.net/content.aspx?obcc="+ioca);
			Document doc = connect.timeout(100000).get();
			Element body = doc.body();
			String text = body.text();
			String[] split = text.split("----------------------------------------------------------------------------------");
			for (String strs : split) {
				if((strs.contains("=")&&!strs.contains("青岛 "))||"ZSQD".equals(ioca)){
					String[] split2 = strs.split("=");
					for (String string2 : split2) {
						if(string2.length()>10&&(string2.contains("TAF")||string2.contains("METAR"))){
							if(string2.contains("METAR")){
								retStrtemp = "METAR" + string2.split("METAR")[1];
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		rootMap.put("weatherInfo",retStrtemp);
		return rootMap ;
	}
	/**
	 * @throws UnsupportedEncodingException 
	 * 获得该航季机场在飞航线
	 * @Title: getAirportOnLineData 
	 * @Description:  
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping(value="/getAirportOnLineData_1",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="首页线数据查询:2")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public String getAirportOnLineData(AirportFocus airportFocus){
		//解决参数乱码
		Map<String, Object> rootMap = new HashMap<String, Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		String airportName = UserContext.getCompanyName();
		String airportCode = UserContext.getcompanyItia();
		if("z_airdata".equals(airportCode)){
			airportName = "海口";
			airportCode = "HAK";
		}
		if(TextUtil.isEmpty(airportCode)){
			airportName = "十堰";
			airportCode = "WDS";
		}
		List<HomePageData> hpdata = UserContext.getHomePageDataList();
		rootMap.put("airportOnLineData", hpdata);
		rootMap.put("cityCoordinateList", homePageService.getCityCoordinateList());
		//机场信息MAP
		Map<String, AirportData> airportDataMap = homePageService.getAirportInfoMap();
		//取出机场三字码和四字码
		List<AirportData> icaoList = homePageService.getIcaoIataList();
		Map<String,AirportData> icaoMap = new HashMap<String,AirportData>();
		for (AirportData airportData : icaoList) {
			icaoMap.put(airportData.getIata(), airportData);
		}
		rootMap.put("icaoMap", icaoMap);
		rootMap.put("airportInfoMap", airportDataMap);
		//获取采集数据的航班号对
		rootMap.put("airportName",airportName);
		rootMap.put("airportCode",airportCode);
		if(airportDataMap.get(airportCode)!=null){
			rootMap.put("airportAirName",airportDataMap.get(airportCode).getAptChaNam());
		}
		//有销售动态的航班号列表
		List<String> fltNbrs = homePageService.getOnInstrutionsFlyNbrs(airportCode);
		//有客源的航线列表
		List<String> linees = homePageService.getOnInstrutionsLinees();
		rootMap.put("fltNbrs",fltNbrs);
		rootMap.put("linees",linees);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
		
	}
	@RequestMapping(value="/getAirportOnLineData_3",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="首页线数据查询:2")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public String getAirportOnLineData_3(AirportFocus airportFocus){
		//解决参数乱码
		Map<String, Object> rootMap = new HashMap<String, Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		String airportCode = UserContext.getcompanyItia();
//		String airportCode = "YIC";
		//获取采集数据的航班号对
		//有销售动态的航班号列表
		List<String> fltNbrs = homePageService.getOnInstrutionsFlyNbrs(airportCode);
		//有客源的航线列表
		List<String> linees = homePageService.getOnInstrutionsLinees();
		rootMap.put("fltNbrs",fltNbrs);
		rootMap.put("linees",linees);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
		
	}
	/**
	 * 首页其他数据加载
	 * @Title: getAirportOnLineData_2 
	 * @Description:  
	 * @param @param airportFocus
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/getAirportOnLineData_2",produces="text/plain;charset=UTF-8")
	@MyMethodNote(comment="首页线数据查询:2")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public String getAirportOnLineData_2(AirportFocus airportFocus){
		//解决参数乱码
		Map<String, Object> rootMap = new HashMap<String, Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		HomePageQuery homePageQuery = new HomePageQuery();
		String airportName = UserContext.getCompanyName();
		String airportCode = UserContext.getcompanyItia();
		if("z_airdata".equals(airportCode)){
			airportName = "海口";
			airportCode = "HAK";
		}
		if(TextUtil.isEmpty(airportCode)){
			airportName = "十堰";
			airportCode = "WDS";
		}
		homePageQuery.setAirPort(airportCode);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar a=Calendar.getInstance();
		int year = a.get(Calendar.YEAR);
		Date currentDate = new Date();
		Map<String,String> XQMap = TextUtil.getXQDate(year);
		Map<String,String> DCMap = TextUtil.getDCDate(year);
		String startDate1 = XQMap.get("startTime");
		String endDate1 = XQMap.get("endTime");
		String startDate2 = DCMap.get("startTime");
		String endDate2 = DCMap.get("endTime");
		String startday = "";
		String endday = "";
		Date sd1=null;
		Date ed1=null;
		try {
			sd1 = sdf.parse(startDate1);
			ed1 = sdf.parse(endDate1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(sd1.getTime()<=currentDate.getTime()&&currentDate.getTime()<=ed1.getTime()){
			startday = startDate1;
			endday = endDate1;
		}else{
			startday = startDate2;
			endday = endDate2;
		}
		homePageQuery.setStartTime(startday);
		homePageQuery.setEndTime(endday);
		Employee emp = UserContext.getUser();
		Long id = emp.getId();
		//获取本场数据参数设置--仪表盘
		AirportFocus airportFocusOld = focusService.select(id);
		//首次登陆默认选中项
		if(airportFocusOld==null){
			airportFocusOld = new AirportFocus();
			airportFocusOld.setId(id);
			airportFocusOld.setTimeDemension("最近1天");
			airportFocusOld.setFlightRange("所有航班");
			airportFocusOld.setDataRange("包含过站，包含甩飞");
			airportFocusOld.setFocusTarget("客量，收入，综合客座率，机场准点率");
			focusService.add(airportFocusOld);//当首次登录时设定默认值，往后更新
			rootMap.put("airportFocus", airportFocusOld);
		}else if(!"".equals(airportFocus.getTimeDemension())&&null!=airportFocus.getTimeDemension()){
			airportFocus.setId(id);
			focusService.update(airportFocus);//当首次登录时设定默认值，往后更新
			airportFocusOld = airportFocus;
			rootMap.put("airportFocus", airportFocus);
		}else{
			rootMap.put("airportFocus", airportFocusOld);
		}
		//设置查询参数
		if(airportFocusOld.getFlightRange().indexOf("关注航班")>-1){
			//查询当前用户关注航班号
			homePageQuery.setFlightRange(iFocusFlightService.getFocusFlightList(id));//查询关注航班号列表
			//查询当前用户关注航线
			homePageQuery.setFltRoutes(iFocusFlightService.getFlightRouteList(id));
		}
		String datee = homePageService.getNewDate(homePageQuery);
		homePageQuery.setDate(datee);
//		String datee = homePageQuery.getDate();
		if(TextUtil.isEmpty(datee)){
			homePageQuery.setDate(sdf.format(new Date()));
			datee = sdf.format(new Date());
		}
		String [] strr ;
		String retDatee="";
		if(!TextUtil.isEmpty(datee)){
			strr = datee.split("-");
			retDatee = strr[0]+"-"+Integer.parseInt(strr[1]) +"-"+Integer.parseInt(strr[2]);
		}
		rootMap.put("datee",retDatee);
		if(airportFocusOld.getTimeDemension().indexOf("1")>-1){
			homePageQuery.setStartTime(datee);
			homePageQuery.setEndTime(datee);
			String[] dates = datee.split("-");
			rootMap.put("timesection", dates[0]+"."+dates[1]+"."+dates[2]);
		}else if(airportFocusOld.getTimeDemension().indexOf("7")>-1){
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			try {
				date = dft.parse(datee);
				Long time = date.getTime()-6*24*60*60*1000l;
				homePageQuery.setStartTime(dft.format(new Date(time)));
				homePageQuery.setEndTime(datee);
				String[] startDate = dft.format(new Date(time)).split("-");
				String[] endDate = datee.split("-");
				rootMap.put("timesection", startDate[0]+"."+startDate[1]+"."+startDate[2]+
						"-"+endDate[0]+"."+endDate[1]+"."+endDate[2]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(airportFocusOld.getTimeDemension().indexOf("30")>-1){
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			try {
				date = dft.parse(datee);
				Long time = date.getTime()-29*24*60*60*1000l;
				homePageQuery.setStartTime(dft.format(new Date(time)));
				homePageQuery.setEndTime(datee);
				String[] startDate = dft.format(new Date(time)).split("-");
				String[] endDate = datee.split("-");
				rootMap.put("timesection", startDate[0]+"."+startDate[1]+"."+startDate[2]+
						"-"+endDate[0]+"."+endDate[1]+"."+endDate[2]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if("包含过站".equals(airportFocusOld.getDataRange())){
			homePageQuery.setDataRange("pass");
		}else if("包含甩飞".equals(airportFocusOld.getDataRange())){
			homePageQuery.setDataRange("thrown");
		}else if("包含过站，包含甩飞".equals(airportFocusOld.getDataRange())){
			homePageQuery.setDataRange("all");
		}
		//关注数据
		homePageQuery.setAirPort(airportCode);
//		String earning = homePageService.getYesterdayEarnings(homePageQuery);
		EvenPort evenPort = homePageService.getAirportFocusData(homePageQuery);
		//进港人数
		int travellerIn = homePageService.getAirportTravellerIn(homePageQuery);
		//出港人数
		int travellerOut = homePageService.getAirportTravellerOUT(homePageQuery);
		//班次
		int fltCount = homePageService.getFlightCount(homePageQuery);
		//获取机场准点率
		String airportIthad = fczreptileService.getAirportIthad(homePageQuery);//通过获取上个月准点率
		Double allPerson = Double.parseDouble(evenPort.getTal_count()+"");
		DecimalFormat df =  new DecimalFormat("##,###.00");
		DecimalFormat dff =  new DecimalFormat("##,###");
		rootMap.put("talInc",df.format(evenPort.getTal_Ine()));//总收入
		rootMap.put("talCount",dff.format(allPerson));//总人数
		if(TextUtil.isEmpty(airportIthad)){
			rootMap.put("airportIthad","0.0");//准点率
		}else{
			rootMap.put("airportIthad",airportIthad.replaceAll("%", "").replace("-", "."));//准点率
		}
		rootMap.put("travellerIn", travellerIn);//进港客量
		rootMap.put("travellerOut", travellerOut);//出港客量
		rootMap.put("fltcount", fltCount);//班次
		rootMap.put("lodFor", df.format(evenPort.getLod_For()));//客座率
		rootMap.put("claPer", fltCount==0?0:dff.format(allPerson/fltCount));//均班客量
		rootMap.put("iddDct", fltCount==0?df.format(0):df.format(evenPort.getTal_Ine()/fltCount));//均班收入
		rootMap.put("airportName",airportName);
		rootMap.put("airportCode",airportCode);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
		
	}
	/**
	 * @throws UnsupportedEncodingException 
	 * 获得该航季机场在飞航线
	 * @Title: getAirportOnLineData 
	 * @Description:  
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping(value="/getFocusData",produces="text/plain;charset=UTF-8")
	@MethodNote(comment="默认:16")
	@MyMethodNote(comment="首页线数据查询:2")
	@ResponseBody
	public String getFocusData(AirportFocus airportFocus) throws UnsupportedEncodingException{
		Map<String, Object> rootMap = new HashMap<String, Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		HomePageQuery homePageQuery = new HomePageQuery();
		String airportName = UserContext.getCompanyName();
		String airportCode = UserContext.getcompanyItia();
		if("z_airdata".equals(airportCode)){
			airportName = "海口";
			airportCode = "HAK";
		}
		if(TextUtil.isEmpty(airportCode)){
			airportName = "十堰";
			airportCode = "WDS";
		}
		homePageQuery.setAirPort(airportCode);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		homePageQuery.setAirPort(airportName);
		Employee emp = UserContext.getUser();
		Long id = emp.getId();
		//获取本场数据参数设置--仪表盘
		AirportFocus airportFocusOld = focusService.select(id);
		//首次登陆默认选中项
		if(airportFocusOld==null){
			airportFocusOld = new AirportFocus();
			airportFocusOld.setId(id);
			airportFocusOld.setTimeDemension("最近1天");
			airportFocusOld.setFlightRange("所有航班");
			airportFocusOld.setDataRange("包含过站，包含甩飞");
			airportFocusOld.setFocusTarget("客量，收入，综合客座率，机场准点率");
			focusService.add(airportFocusOld);//当首次登录时设定默认值，往后更新
			rootMap.put("airportFocus", airportFocusOld);
		}else if(!"".equals(airportFocus.getTimeDemension())&&null!=airportFocus.getTimeDemension()){
			airportFocus.setId(id);
			focusService.update(airportFocus);//当首次登录时设定默认值，往后更新
			airportFocusOld = airportFocus;
			rootMap.put("airportFocus", airportFocus);
		}else{
			rootMap.put("airportFocus", airportFocusOld);
		}
		//设置查询参数
		if(airportFocusOld.getFlightRange().indexOf("关注航班")>-1){
			//查询当前用户关注航班号
			homePageQuery.setFlightRange(iFocusFlightService.getFocusFlightList(id));
			//查询当前用户关注航线
			homePageQuery.setFltRoutes(iFocusFlightService.getFlightRouteList(id));
		}
		homePageQuery.setDate(homePageService.getNewDate(homePageQuery));
		String datee = homePageQuery.getDate();
		if(TextUtil.isEmpty(datee)){
			datee = sdf.format(new Date());
			homePageQuery.setDate(datee);
		}
		if(airportFocusOld.getTimeDemension().indexOf("1")>-1){
			homePageQuery.setStartTime(datee);
			homePageQuery.setEndTime(datee);
			String[] strDatae = datee.split("-");
			rootMap.put("timesection", strDatae[0]+"."+strDatae[1]+"."+strDatae[2]);
		}else if(airportFocusOld.getTimeDemension().indexOf("7")>-1){
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			try {
				date = dft.parse(datee);
				Long time = date.getTime()-6*24*60*60*1000l;
				homePageQuery.setStartTime(dft.format(new Date(time)));
				homePageQuery.setEndTime(datee);
				String[] startDate = dft.format(new Date(time)).split("-");
				String[] endDate = datee.split("-");
				rootMap.put("timesection", startDate[0]+"."+startDate[1]+"."+startDate[2]+
						"-"+endDate[0]+"."+endDate[1]+"."+endDate[2]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(airportFocusOld.getTimeDemension().indexOf("30")>-1){
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			try {
				date = dft.parse(datee);
				Long time = date.getTime()-29*24*60*60*1000l;
				homePageQuery.setStartTime(dft.format(new Date(time)));
				homePageQuery.setEndTime(datee);
				String[] startDate = dft.format(new Date(time)).split("-");
				String[] endDate = datee.split("-");
				rootMap.put("timesection", startDate[0]+"."+startDate[1]+"."+startDate[2]+
						"-"+endDate[0]+"."+endDate[1]+"."+endDate[2]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if("包含过站".equals(airportFocusOld.getDataRange())){
			homePageQuery.setDataRange("pass");
		}else if("包含甩飞".equals(airportFocusOld.getDataRange())){
			homePageQuery.setDataRange("thrown");
		}else if("包含过站，包含甩飞".equals(airportFocusOld.getDataRange())){
			homePageQuery.setDataRange("all");
		}
		//关注数据
		homePageQuery.setAirPort(airportCode);
		EvenPort evenPort = homePageService.getAirportFocusData(homePageQuery);
		//进港人数
		int travellerIn = homePageService.getAirportTravellerIn(homePageQuery);
		//出港人数
		int travellerOut = homePageService.getAirportTravellerOUT(homePageQuery);
		//班次
		int fltCount = homePageService.getFlightCount(homePageQuery);
		//获取机场准点率
		String airportIthad = fczreptileService.getAirportIthad(homePageQuery);//通过获取上个月准点率
		Double allPerson = Double.parseDouble(evenPort.getTal_count()+"");
		DecimalFormat df =  new DecimalFormat("##,###.00");
		DecimalFormat dff =  new DecimalFormat("##,###");
		rootMap.put("talInc",df.format(evenPort.getTal_Ine()));//总收入
		rootMap.put("talCount",dff.format(allPerson));//总人数
		rootMap.put("airportIthad",airportIthad.replaceAll("%", ""));//准点率
		rootMap.put("travellerIn", travellerIn);//进港客量
		rootMap.put("travellerOut", travellerOut);//出港客量
		rootMap.put("fltcount", fltCount);//班次
		rootMap.put("lodFor", df.format(evenPort.getLod_For()));//客座率
		rootMap.put("claPer", fltCount==0?0:dff.format(allPerson/fltCount));//均班客量
		rootMap.put("iddDct", fltCount==0?df.format(0):df.format(evenPort.getTal_Ine()/fltCount));//均班收入
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
		
	}
	
	/**
	 * 首页航线视图排行与趋势
	 * @Title: getAirportKPIData 
	 * @Description:  
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/getAirportKPIData",produces="text/plain;charset=UTF-8")
	@MethodNote(comment="默认:16")
	@MyMethodNote(comment="首页数据查询:2")
	@ResponseBody
	public String getAirportKPIData(){
//		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		HomePageQuery homePageQuery = new HomePageQuery();
		String airportItia = UserContext.getcompanyItia();
		homePageQuery.setAirPort(airportItia);
//		String airportName = UserContext.getCompanyName();
//		homePageQuery.setAirPort(airportName);
		homePageQuery.setDate(homePageService.getNewDate(homePageQuery));
		String datee = homePageQuery.getDate();
		if(TextUtil.isEmpty(datee)){
			homePageQuery.setDate(sdf.format(new Date()));
		}
//		rootMap = homePageService.getAirPortKPIData(homePageQuery);
		list = homePageService.getAirPortKPIDataNew(homePageQuery);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
//		hjson = gson.toJson(rootMap);
		hjson = gson.toJson(list);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	/**
	 * 
	 * @Title: getAirportViewData 
	 * @Description:  
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/getAirportViewData",produces="text/plain;charset=UTF-8")
	@MethodNote(comment="默认:16")
	@MyMethodNote(comment="首页机场视图:2")
	@ResponseBody
	public String getAirportViewData(){
		Map<String, Object> rootMap = new HashMap<String, Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		String airportCode = UserContext.getcompanyItia();
		if("z_airdata".equals(airportCode)){
			airportCode = "HAK";
		}
		if(TextUtil.isEmpty(airportCode)){
			airportCode = "HAK";
		}
		List<AirInfoData> airInfoDataList = homePageService.getAirInfoDataList();
//		List<AirInfoData> airInfoDataListFore = homePageService.getForeAirInfoDataList();
		List<AirInfoData> airInfoDataListFore = new ArrayList<AirInfoData>();
		rootMap.put("airportCode", airportCode);
		rootMap.put("foreAirInfoDataList", airInfoDataListFore);
		rootMap.put("airInfoDataList", airInfoDataList);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = ""; 
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	@RequestMapping("/selectAirLineData")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String, Object> selectAirLineData(HomePageQuery homePageQuery){
		Map<String, Object> rootMap = new HashMap<String, Object>();
		//设置为一个航季的数据
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar a=Calendar.getInstance();
		int year = a.get(Calendar.YEAR);
		Date currentDate = new Date();
		Map<String,String> XQMap = TextUtil.getXQDate(year);
		Map<String,String> DCMap = TextUtil.getDCDate(year);
		String startDate1 = XQMap.get("startTime");
		String endDate1 = XQMap.get("endTime");
		
		String startDate2 = DCMap.get("startTime");
		String endDate2 = DCMap.get("endTime");
		String startday = "";
		String endday = "";
		Date sd1=null;
		Date ed1=null;
		try {
			sd1 = sdf.parse(startDate1);
			ed1 = sdf.parse(endDate1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(sd1.getTime()<=currentDate.getTime()&&currentDate.getTime()<=ed1.getTime()){
			startday = startDate1;
			endday = endDate1;
		}else{
			startday = startDate2;
			endday = endDate2;
		}
		homePageQuery.setStartTime(startday);
		homePageQuery.setEndTime(endday);
		return rootMap;
	}
	/**
	 * 得到条件下是否有连续三十天没有数据
	 * @Title: getHavingDataByThire 
	 * @Description:  
	 * @param @param havingDataQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/restful/getHavingDataByThire")
	@ResponseBody
	public Map<String, Object> getHavingDataByThire(HavingDataQuery havingDataQuery){
//		havingDataQuery.setDpt_AirPt_Cd("LZO");
////		havingDataQuery.setPas_stp("WDS");
//		havingDataQuery.setArrv_Airpt_Cd("DCY");
//		havingDataQuery.setIsGoAndBack("1");
//		havingDataQuery.setFlt_ectq("LZOWDSDCY");
//		havingDataQuery.setFlt_ecth("DCYWDSLZO");
//		havingDataQuery.setIsIncludePas("on");
//		havingDataQuery.setIsIncludeRdf("on");
//		havingDataQuery.setIsIncludeExceptionData("on");
//		havingDataQuery.setIsIncludeExceptionHuangduan("on");
//		havingDataQuery.setStartTime("2016-01-01");
//		havingDataQuery.setEndTime("2017-07-31");
		Map<String, Object> rootMap = new HashMap<String, Object>();
		String flage = homePageService.getHavingDataByThire(havingDataQuery);
		//0表示无数据，1表示有30天间隔的数据，2表示没有间隔30天的数据
		rootMap.put("status", flage);
		return rootMap;
	}
	@RequestMapping("/restful/getAllAirLineData")
	@ResponseBody
	public Map<String, Object> getAllAirLineData(AllAirLineQuery allAirLineQuery){
		Map<String, Object> rootMap = new HashMap<String, Object>();
		List<AllAirLineFltDetailData> list = homePageService.getAllAirLineData(allAirLineQuery);
		List<AllAirLineFltDetailData> retList = new ArrayList<AllAirLineFltDetailData>();
		Map<String, AirportData> airportDataMap = homePageService.getAirportInfoMap();
			
		for (AllAirLineFltDetailData allAirLineFltDetailData : list) {
			String flyNbr = allAirLineFltDetailData.getFlyNbr();
			String fromName = allAirLineFltDetailData.getDpt_AirPt_Cd();
			String toName = allAirLineFltDetailData.getArrv_Airpt_Cd();
			if(airportDataMap.get(fromName)==null||airportDataMap.get(toName)==null){
				continue;
			}
			String namef = airportDataMap.get(fromName).getAptChaNam();
			String namet = airportDataMap.get(toName).getAptChaNam();
			allAirLineFltDetailData.setDpt_AirPt_Cd(namef);
			allAirLineFltDetailData.setArrv_Airpt_Cd(namet);
			if(!(flyNbr.length()>6)){
				retList.add(allAirLineFltDetailData);
			}
		}
		rootMap.put("list", TextUtil.sortAllAirLineFltDetailData(retList, "flyNbr", "ASC"));
		return rootMap;
	}
	/**
	 * 机场高级筛选
	 * @param query
	 * @return
	 */
	@RequestMapping("/findFilterParamAirPort")
	@ResponseBody
	public FilterParamAirPortObject findFilterParamAirPort(FilterParamAirPortQuery query){
		FilterParamAirPortObject airPortObject = new FilterParamAirPortObject();
		List<AirInfoData> findFilterParamAirPort = homePageService.findFilterParamAirPort(query);
		airPortObject.setLists(findFilterParamAirPort);
		return airPortObject;
	}
}
