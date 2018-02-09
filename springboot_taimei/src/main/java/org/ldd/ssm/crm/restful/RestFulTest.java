package org.ldd.ssm.crm.restful;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.ldd.ssm.crm.domain.Company;
import org.ldd.ssm.crm.domain.Digitt;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.FlightRoute;
import org.ldd.ssm.crm.domain.MapperData;
import org.ldd.ssm.crm.exception.LogicException;
import org.ldd.ssm.crm.query.HomePageQuery;
import org.ldd.ssm.crm.service.BasicDataService;
import org.ldd.ssm.crm.service.DituMapperService;
import org.ldd.ssm.crm.service.FlightRouteService;
import org.ldd.ssm.crm.service.HomePageService;
import org.ldd.ssm.crm.service.ICompanyService;
import org.ldd.ssm.crm.service.IEmployeeService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UploadImage;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class RestFulTest {
	@Autowired
	private HomePageService homePageService;
	@Autowired
	 private IEmployeeService employeeService;
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private BasicDataService basicDataService;
	@Autowired
	private DituMapperService dituMapperService;
	@Autowired
	private FlightRouteService flightRouteService;
	@RequestMapping(value="/restful/getAirportOnLineData",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getAirportOnLineData(){
		Map<String, Object> rootMap = new HashMap<String, Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		HomePageQuery homePageQuery = new HomePageQuery();
		String airportName = UserContext.getCompanyName();
		String airportCode = UserContext.getcompanyItia();
		homePageQuery.setAirPort(airportName);
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
		rootMap.put("airportOnLineData", homePageService.getAirportOnLineDataList(homePageQuery));
		rootMap.put("cityCoordinateList", homePageService.getCityCoordinateList());
		homePageQuery.setAirPort(airportName);
		homePageQuery.setDate(homePageService.getNewDate(homePageQuery));
		//昨日收益
		String earning = homePageService.getYesterdayEarnings(homePageQuery);
		//昨日进港
		homePageQuery.setAirPort(airportName);
		String yesterdayPutin = homePageService.getYesterdayPutin(homePageQuery);
		//昨日出港
		homePageQuery.setAirPort(airportName);
		String yesterdayLeave = homePageService.getYesterdayLeave(homePageQuery);
		Double allPerson = Double.parseDouble(yesterdayPutin==null?"0":yesterdayPutin) + Integer.parseInt(yesterdayLeave==null?"0":yesterdayLeave);
		DecimalFormat df =  new DecimalFormat("##,###.00");
		DecimalFormat dff =  new DecimalFormat("##,###");
		rootMap.put("earning",df.format(Double.parseDouble(earning==null?"0.00":earning)));
		rootMap.put("allPerson",dff.format(allPerson));
		rootMap.put("onTime","89%");
		rootMap.put("airportName",airportName);
		rootMap.put("airportCode",airportCode);
		
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	
	@RequestMapping(value="/restful/checkLogin",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String checkLogin(String username,String password){
		Map<String,Object> map = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		try{
			// 检查登陆
			Employee emp = employeeService.checkLogin(username, password);
			Digitt	digit = basicDataService.get();
			// 注册session
			UserContext.setUser(emp);
			UserContext.setDigitt(digit);
			//设置公司ID为全局.
			UserContext.setCompanyId(employeeService.getCompanyId(emp.getId())+"");
			Company  company = companyService.load(employeeService.getCompanyId(emp.getId()));
			if(company!=null){
				UserContext.setCompanyName(company.getCpyAds());
				UserContext.setcompanyItia(company.getCpyItia());
			}else{
				UserContext.setCompanyName("十堰");
				UserContext.setcompanyItia("WDS");
			}
			//设置黑名单为全局变量
			List<MapperData> mapperDataList = dituMapperService.getDataMapperList();
			UserContext.setMapperList(mapperDataList);
			//设置能操作的航班航线为全局变量
			List<FlightRoute> fltNbrList = flightRouteService.selectByEmployee(emp.getId(),UserContext.getcompanyItia());
			UserContext.setFlyNumList(fltNbrList);
			//将头像复制到指定路径
			if(!"".equals(emp.getHeadPath())){
				String fileName = UUID.randomUUID().toString()+".jpg";
				File newFile = new File(UserContext.getRequest().getSession().getServletContext().getRealPath("/")+"uploadimage/"+emp.getId()+"/"+fileName);
				if(!newFile.isDirectory()){
					newFile.mkdirs();
				}
				File oldFile = new File(emp.getHeadPath());
				boolean result = UploadImage.copyTheFile(oldFile, newFile);
				if(result==true){
					UserContext.getUser().setHeadPath("/uploadimage/"+emp.getId()+"/"+fileName);
				}
			}
			// 封装响应信息
			map.put("success", true);
			map.put("msg", "登陆成功！！");
		}catch(LogicException e){
			map.put("success", false);
			map.put("msg", "登陆失败，"+e.getMessage()+"！！");
			map.put("errorCode", e.getErrorCode());
		}
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(map);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr;
	}
	
}
