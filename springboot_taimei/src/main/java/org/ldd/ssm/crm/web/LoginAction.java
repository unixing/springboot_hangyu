package org.ldd.ssm.crm.web;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.AirportData;
import org.ldd.ssm.crm.domain.EmailValidStr;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.FlightRoute;
import org.ldd.ssm.crm.domain.HomePageData;
import org.ldd.ssm.crm.domain.MapperData;
import org.ldd.ssm.crm.domain.Token;
import org.ldd.ssm.crm.exception.LogicException;
import org.ldd.ssm.crm.query.HomePageQuery;
import org.ldd.ssm.crm.service.BasicDataService;
import org.ldd.ssm.crm.service.DituMapperService;
import org.ldd.ssm.crm.service.FlightRouteService;
import org.ldd.ssm.crm.service.HomePageService;
import org.ldd.ssm.crm.service.ICompanyService;
import org.ldd.ssm.crm.service.IEmailValidStrService;
import org.ldd.ssm.crm.service.IEmployeeRoleService;
import org.ldd.ssm.crm.service.IEmployeeService;
import org.ldd.ssm.crm.service.IMenuNewService;
import org.ldd.ssm.crm.service.IPortalDataService;
import org.ldd.ssm.crm.service.IResourceNewService;
import org.ldd.ssm.crm.service.IRoleMenuNewService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UploadImage;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.AliyunNewSms;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.ldd.ssm.crm.web.interceptor.SendSmsAliyun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aliyuncs.http.HttpResponse;
@Controller
public class LoginAction {
	@Autowired
	private IPortalDataService portalDataService;
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
	@Autowired
	private IEmployeeRoleService employeeRoleService;
	@Autowired
	private IRoleMenuNewService roleMenuNewService;
	@Autowired
	private IEmailValidStrService iEmailValidStrService;
	@Autowired
	private IMenuNewService menuNewService;
	@Autowired
	private IResourceNewService resourceNewService;
	@Autowired
	private HomePageService homePageService;
	Logger logger = Logger.getLogger(LoginAction.class);
//	//验证登录
	@RequestMapping("/login")
	public String login(){
		return "login/login";
	}
	@RequestMapping("/hangyu_login")
	public String hangyu_login(String uuid){
		UserContext.setUseruuid(uuid);
		if(!TextUtil.isEmpty(uuid)){
			Employee emp = employeeService.getEmployeByUUID(uuid);
			UserContext.setUser(emp);
		}
		return "login/hangyu_login";
	}
	//验证登录
	@MyMethodNote(comment="用户登录:1")
	@RequestMapping("/checkLogin")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String, Object> checkLogin(HttpSession session,String username,String password,String authImage,String token,String wheres){
		Map<String,Object> map = new HashMap<String,Object>();
		String hangyuflage = "false";
		if(!TextUtil.isEmpty(wheres)&&wheres.contains("hangyu")){
			//航遇那边过来的模拟用户
			String uuid = UserContext.getUseruuid();
			if(TextUtil.isEmpty(uuid)){
				map.put("hangyu_havingUser", "false");
				return map;
			}
			Employee emp = employeeService.getEmployeByUUID(uuid);
			if(emp==null){
				map.put("hangyu_havingUser", "false");
				return map;
			}
			hangyuflage = "true";
			//超级管理员登陆直接放行
			map = loginOption(session, emp, token);
			map.put("hangyu_havingUser", "true");
			return map;
		}
		UserContext.setUsertext(username);
		map.put("hangyu", hangyuflage);
		try{
			//检查登录名和密码是否正确
			if(TextUtil.isEmpty(username)||TextUtil.isEmpty(password)){
				map.put("opResult", "-2");
				//重置登陆错误次数
				employeeService.updateDefeated_number(username,0);
				return map;
			}
			if(!TextUtil.isEmpty(authImage)){
				String attribute = (String) session.getAttribute("verifyCode");  
				if(!TextUtil.isEmpty(attribute)){
					if(!authImage.toLowerCase().equals(attribute.toLowerCase())){
						map.put("opResult", "-8");
						return map;
					}
				}else{
					map.put("opResult", "-8");
					return map;
				}
			}
			// 检查登陆,并且跳过是否验证真实密码, 如果跳过, 则使用默认123456密码来验证
			Employee emp = employeeService.checkLogin(username, password);
			//判断账号是否在有效期之内
			//是否跳过手机验证
			if(!emp.isContinueCheck()){
				map = loginOption(session, emp, token);
				return map;
			}
			if(emp.getValidity()!=null){
				boolean validity = validityDate(emp.getValidity());
				if(validity){
					map.put("opResult", "-3");
					return map;
				}
			}
			int status = emp.getUsrSts();
			if(status==99){
				//超级管理员登陆直接放行
				map = loginOption(session, emp, token);
				return map;
			}else if(status==-1){
				//账号禁用或者被删除状态，
				map.put("opResult", "-1");
				return map;
			}
			if(emp.isIscheck()){
				map = loginOption(session, emp, token);
			}else{
				if(emp.getPhone()==null||"".equals(emp.getPhone())){
					map.put("opResult", "4");//后台系统首次登陆，后台没有绑定手机号
					UserContext.setUser(emp);
					return map;
				}else{
					map.put("opResult", "3");//已经绑定手机, 手机验证码登录
					UserContext.setUser(emp);
					return map;
				}
			}
			employeeService.updateDefeatedNnone(username);
		}catch(LogicException e){
			map.put("success", false);
			map.put("msg", "登陆失败，"+e.getMessage()+"！！");
			map.put("errorCode", e.getErrorCode());
			//如果密码错误, 则将密码记录错误数记录上
			if(e.getErrorCode()==-101){
				employeeService.updateDefeated_number(username,1);
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	private boolean validityDate(Date validity) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(validity);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(new Date());
		if(calendar.compareTo(calendar2)==1){
			return false;
		}
		return true;
	}
	@MyMethodNote(comment="用户退出:1")
	@RequestMapping("/outLogin")
	@MethodNote(comment="默认:16")
	public String outLogin(){
			// 注销session
//			UserContext.rmoveUser();
		UserContext.getRequest().getSession().invalidate();
		return "redirect:/login.jsp";
	}
	@MyMethodNote(comment="清除缓存:1")
	@RequestMapping("/cleranHistroy")
	@MethodNote(comment="默认:16")
	public String cleranHistroy(){
		// 注销session
		return "newHtml/cleranHistroy";
	}
	@RequestMapping(value = "/restful/versionExchange")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String, String> versionExchange(){
		Map<String,String> map = new HashMap<String,String>();
		//改文件名
//		Date dd = new Date();
//		String strtime = dd.getTime()+"";
//		File newFile = new File(UserContext.getRequest().getSession().getServletContext().getRealPath("/")+"css");
//		File [] files =newFile.listFiles();
//		String name1 = UserContext.getRequest().getSession().getServletContext().getRealPath("/")+"css/css"+strtime;
//		File ff1 = files[0];
//		boolean b = ff1.renameTo(new File(name1)); 
//	    File newFile1 = new File(UserContext.getRequest().getSession().getServletContext().getRealPath("/")+"js");
//	    File [] files1 =newFile1.listFiles();
//	    File ff2 = files1[0];
//	    String name2 = UserContext.getRequest().getSession().getServletContext().getRealPath("/")+"js/js"+strtime;
//	    boolean c = ff2.renameTo(new File(name2)); 
//	    UserContext.setVersionn(strtime);
		return map ;
	}
	@RequestMapping("/IoSOutLogin")
	@ResponseBody
	public Map<String, String> IoSOutLogin(String token){
		Map<String,String> map = new HashMap<String, String>();
		if(token == null){
			map.put("states", "o");
		}else{
			Employee token2 = employeeService.getToken(token);
			if(token2==null){
				map.put("states", "o");
			}else{
				employeeService.deleteTokenByemp_id(token2.getId());
				map.put("states", "1");
			}
		}
		return map;
	}
	
	public String validMail(String validStr,Long id){
		String resultStr = "1";
		try {
			long currentTime = new Date().getTime();
			EmailValidStr emailValidStr = iEmailValidStrService.load(id);
			if(emailValidStr==null){
//				map.put("opResult", "3");//验证过期
				return "newHtml/fail";
			}else{
				long validMailTime = emailValidStr.getCreateTime().longValue();
				if(currentTime-validMailTime>30*60*1000){//30分钟验证过期
					resultStr = "3";
				}else{
					Employee emp = new Employee();
					emp.setId(UserContext.getUser().getId());
					emp.setEmail(emailValidStr.getEmail());
					boolean result= employeeService.update(emp);
					if(result){
						UserContext.getUser().setEmail(emailValidStr.getEmail());
						resultStr = "0";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStr = "2";
			return resultStr;
		}
		return resultStr;
	}
	
	@RequestMapping("/validPhone")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> validPhone(String mobile){
		Map<String,Object> map=new HashMap<String,Object>();
		if(mobile==null||"".equals(mobile)){
			map.put("opResult", "3");
			return map;
		}
		try {
			Employee emp = employeeService.getEmployeeByPhone(mobile);
			if(emp==null){
				map.put("opResult", "0");
			}else{
				map.put("opResult","1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/bindPhoneOrValidCode_Login")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> bindPhoneOrValidCode_Login(String mobile,String validCode){
		
		Map<String,Object> map = new HashMap<String,Object>();
		//判断手机号和验证码不为空
		if(TextUtil.isEmpty(mobile)||TextUtil.isEmpty(validCode)){
			map.put("opResult", "3");//验证码错误
			return map;
		}
		//判断手机用户是否存在
		Employee emp = employeeService.getEmployeeByPhone(mobile);
		
		if(emp==null){
			map.put("opResult", "6");//用户不存在
			return map;
		}
		if(emp.getValidity()!=null){
			boolean validity = validityDate(emp.getValidity());
			if(validity){
				map.put("opResult", "-3");
				return map;
			}
		}
		HttpSession session = UserContext.getRequest().getSession();
		//判断用户是否需要验证
		if(!emp.isContinueCheck()){
			//是否跳过验证
			UserContext.setUsertext(mobile);
			UserContext.setUser(emp);
			loginOption(session,emp,null);
			map.put("opResult", "4");//4代表已经绑定完毕
			employeeService.updateDefeatedNnoneBymobile(mobile);
		}else{
			try {
				String code = (int) session.getAttribute(mobile) + "";
//				validCode = "";
//				String code = "";
				//验证码是否存在
				if (validCode.equals(code)) {
					// 判断账号是否停用
					if (emp.getUsrSts() == -1) {
						map.put("opResult", "-1");// -1账号停用
						return map;
					}
					// 如果状态是已验证, 或者账号状态99
					if (emp.isIscheck() || emp.getUsrSts() == 99) {
						UserContext.setUsertext(mobile);
						UserContext.setUser(emp);
						loginOption(session, emp, null);
						map.put("opResult", "4");// 4代表已经绑定完毕
					} else {
						UserContext.setUser(emp);
						map.put("opResult", "0");// 0表示未验证
					}
					employeeService.updateDefeatedNnoneBymobile(mobile);
					return map;
				} else {
					map.put("opResult", "1");
				}
			} catch (Exception e) {
				map.put("opResult", "2");
				return map;
			}
		}
		
		return map;
	}
	@RequestMapping("/bindPhoneOrValidCode_Admin")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> bindPhoneOrValidCode_Admin(String mobile,String validCode){
		Map<String,Object> map = new HashMap<String,Object>();
		if(mobile==null||"".equals(mobile)||validCode==null||"".equals(validCode)){
			map.put("opResult", "3");
			return map;
		}
		try {
			HttpSession session = UserContext.getRequest().getSession();
			String	code = (int)session.getAttribute(mobile)+"";
			if(validCode.equals(code)){
				Employee emp = UserContext.getUser();
				if(emp.getValidity()!=null){
					boolean validity = validityDate(emp.getValidity());
					if(validity){
						map.put("opResult", "-3");
						return map;
					}
				}
				emp.setPhone(mobile);//管理员首次登陆绑定手机号
				boolean result = employeeService.update(emp);
				if(!result){
					map.put("opResult", "1");//绑定失败
					return map;
				}
				employeeService.updateDefeatedNnoneBymobile(mobile);
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/createNamdAndPwd")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> createUserNameAndPassword(String userName,String password){
		Map<String,Object> map=new HashMap<String,Object>();
		if(TextUtil.isEmpty(password)){
			map.put("opResult", "3");
			return map;
		}
		try {
			Employee emp = UserContext.getUser();
			emp.setUsrPwd(password);
			emp.setFirstLogin(1);
			emp.setIscheck(true);
			//是否选择设置用户名
			if(!TextUtil.isEmpty(userName)){
				UserContext.setUsertext(userName);
				emp.setUsrNm(userName);
				//查询是否存在用户名
				String findemp = employeeService.findEMP(userName);
				//用户名是否存在
				if(TextUtil.isEmpty(findemp)){
					//更新用户
					boolean result = employeeService.update(emp);
					if(result){
						UserContext.setUser(emp);
						map = loginOption(UserContext.getRequest().getSession(), emp, null);
					}else{
						map.put("opResult", "1");
					}
				}else{
					map.put("opResult", "4");
				}
			}else{
//				String usrNm = emp.getUsrNm();//账户本身的用户名
//				if(TextUtil.isEmpty(usrNm)){
//					emp.setUsrNm(emp.getPhone());
//				}
				
				//没有选择设置用户, 直接设置密码
				boolean result = employeeService.update(emp);
				if(result){
					UserContext.setUser(emp);
					map = loginOption(UserContext.getRequest().getSession(), emp, null);
				}else{
					map.put("opResult", "1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/validChangePwdCode")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> validChangePwdCode(String mobile,String code){
		Map<String,Object> map=new HashMap<String,Object>();
		if(mobile==null||"".equals(mobile)||code==null||"".equals(code)){
			map.put("opResult", "3");
			return map;
		}
		long currentTime = new Date().getTime();
		HttpSession session = UserContext.getRequest().getSession();
		String prevVlidCodeTime = (String) (session.getAttribute("PrevValidCodeTime")==null?"":session.getAttribute("PrevValidCodeTime"));
		if("".equals(prevVlidCodeTime)){
			map.put("opResult", "3");
			return map;
		}
		try {
			long prevGetSmCodeTime = Long.valueOf(prevVlidCodeTime);
			if(currentTime-prevGetSmCodeTime<=5*60*1000){
				String currentValidCode = String.valueOf(session.getAttribute(mobile));
				if(!code.equals(currentValidCode)){
					map.put("opResult", "1");
					return map;
				}else{
					//清空对应时间和验证码
					session.removeAttribute("PrevValidCodeTime");
					session.removeAttribute(mobile);
					map.put("opResult", "0");
					return map;
				}
			}else{
				map.put("opResult", "1");
				return map;
			}
		} catch (Exception e) {
			map.put("opResult", "2");
			return map;
		}
	}
	
	@RequestMapping("/getLoginSmCode")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> getLoginSmCode(String mobile){
		Map<String,Object> map=new HashMap<String,Object>();
		if(mobile==null||"".equals(mobile)){
			map.put("opResult", "3");
			return map;
		}
		try {
			Employee emp = employeeService.getEmployeeByPhone(mobile);
			if(emp==null){
				map.put("opResult", "5");//手机号未绑定账户
				return map;
			}
			boolean flag = AliyunNewSms.sendSms(mobile);
			if(flag){
//				UserContext.getRequest().getSession().setAttribute("getCodePhone", mobile);
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/getPortalData")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> getPortalData(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取当前用户可以查看机场
			Map<String,Map<String,Object>> lists = new HashMap<String,Map<String,Object>>();
			
			String companyName = companyService.load(Long.valueOf(UserContext.getCompanyId())).getCpyNm();
			if("太美集团".equals(companyName)){
				//如果是太美集团(例外，只需要航线，不用其他数据)
				 lists = portalDataService.getPortalDataByAirportsByTaimei(UserContext.getItiaList());
			}else{
				 lists = portalDataService.getPortalDataByAirports(UserContext.getItiaList());
			}
			if(lists!=null){
				map.put("opResult", "0");
				map.put("list", lists);
				map.put("groupName", UserContext.getCompanyName());
				map.put("userName", UserContext.getUsertext());
				Map<String, AirportData> airportDataMap = homePageService.getAirportInfoMap();
				//取出机场三字码和四字码
//				List<AirportData> icaoList = homePageService.getIcaoIataList();
//				Map<String,AirportData> icaoMap = new HashMap<String,AirportData>();
//				for (AirportData airportData : icaoList) {
//					icaoMap.put(airportData.getIata(), airportData);
//				}
//				map.put("icaoMap", icaoMap);
				map.put("airportInfoMap", airportDataMap);
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	public Map<String,Object> loginOption(HttpSession session,Employee emp,String token){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			// 注册session
			UserContext.setUser(emp);
			//获取服务器ip地址，并判断是否为测试服务器
			boolean ipness=getAddress();
			UserContext.setIpNess(ipness);
			//本地测试模拟航线
//			UserContext.setIpNess(true);
			//设置公司ID为全局.
			Long companyId=employeeService.getCompanyId(emp.getId());
			UserContext.setCompanyId(companyId+"");
//			Company  company = companyService.load(companyId);
//			if(company!=null){
//				UserContext.setCompanyName(company.getCpyAds());
//				UserContext.setcompanyItia(company.getCpyItia());
//			}else{
//				UserContext.setCompanyName("海口");
//				UserContext.setcompanyItia(null);
//			}
			//设置黑名单为全局变量
			List<MapperData> mapperDataList = dituMapperService.getDataMapperList();
			UserContext.setMapperList(mapperDataList);
			//设置能操作的航班航线为全局变量
			List<FlightRoute> fltNbrList = flightRouteService.selectByEmployee(emp.getId(),UserContext.getcompanyItia());
			UserContext.setFlyNumList(fltNbrList);
			// 如果登录成功,创建ios需要的token信息------------------------------------------------------
			String randomUUID = UUID.randomUUID().toString();
			if(token!=null){
				Token newToken = new Token();
				newToken.setUuid(randomUUID);
				newToken.setEmployee_id(emp.getId());
				employeeService.deleteTokenByemp_id(emp.getId());
				employeeService.saveToken(newToken);
			}
			//将头像复制到指定路径
			if(!"".equals(emp.getHeadPath())){
				String fileName = UUID.randomUUID().toString()+".jpg";
				File newFile = new File(UserContext.getRequest().getSession().getServletContext().getRealPath("/")+"uploadimage/"+emp.getId()+"/");
				if(!newFile.isDirectory()){
					newFile.mkdirs();
				}
				newFile = new File(UserContext.getRequest().getSession().getServletContext().getRealPath("/")+"uploadimage/"+emp.getId()+"/"+fileName);
				File oldFile = new File(emp.getHeadPath());
				boolean result = UploadImage.copyTheFile(oldFile, newFile);
				if(result==true){
					UserContext.getUser().setHeadPath("/uploadimage/"+emp.getId()+"/"+fileName);
				}
			}
			//封装菜单列表存入session
//			if(emp.getUsrSts()!=99){
//				Long roleId = employeeRoleService.load(company.getCpyItia(), emp.getId());
//				if(roleId!=null){
//					UserContext.getUser().setRoleId(roleId);
//					List<String> urls = roleMenuNewService.getCurrentEmployeeResource(roleId);
////					List<MenuNew> menus = roleMenuNewService.selectMenuList(roleId);
//					UserContext.setUrlList(urls);
////					UserContext.setMenuNewList(menus);
//				}
//			}else{//超级管理员
//				List<String> urls = resourceNewService.getAllResourceUrls();
////				List<MenuNew> menus = menuNewService.getAllMenu();
//				UserContext.setUrlList(urls);
////				UserContext.setMenuNewList(menus);
//			}
			// 封装响应信息
			String validStr = (String)session.getAttribute("validStr");
			Long id = session.getAttribute("id")==null?0l:Long.valueOf((String)session.getAttribute("id"));
			if(validStr==null||"".equals(validStr)||id==null||id.longValue()<=0){
				map.put("success", true);
				map.put("msg", "登陆成功！！");
				map.put("token",randomUUID);
			}else{
				//清空id和验证字符
				session.removeAttribute("id");
				session.removeAttribute("validStr");
				String opResult = validMail(validStr, id);
				map.put("success", true);
				map.put("opResult", opResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opRestult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/updatePwd")
	@ResponseBody
	public Map<String,Object> updatePassword(String mobile,String password){
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
			map.put("opResult", "3");
			return map;
		}
		try {
			Employee emp = employeeService.getEmployeeByPhone(mobile);
			if(emp!=null){
				if(StringUtils.isEmpty(emp.getUsrPwd())){
					boolean result = employeeService.updateEmployeeForPwd(emp.getId(), password);
					if(result){
						map.put("opResult", "0");
					}else{
						map.put("opResult", "1");
					}
				}else{
					if(!emp.getUsrPwd().equals(password)){
						boolean result = employeeService.updateEmployeeForPwd(emp.getId(), password);
						if(result){
							map.put("opResult", "0");
						}else{
							map.put("opResult", "1");
						}
					}else{
						map.put("opResult","4");
					}
				}
			}else{
				map.put("opResult", "1");
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
	}
	
	protected boolean getAddress() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
		String serverIP = request.getLocalAddr();
//		InetAddress address;
//		try {
//			address = InetAddress.getLocalHost();
//			String hostAddress = address.getHostAddress();
			if("192.168.22.8".equals(serverIP)){
				return true;
			}else{
				return false;
			}
//			
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		return false;
		
//		boolean result=false;
//        try {
//            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements();) {
//                NetworkInterface networkInterface = interfaces.nextElement();
//                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
//                    continue;
//                }
//                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
//                if (addresses.hasMoreElements()) {
//                	if("192.168.22.8".equals(addresses.nextElement().getHostAddress())){
//                		result=true;
//                		break;
//                	}
//                }
//            }
//        } catch (SocketException e) {
//            logger.debug("Error when getting host ip address: <{}>.", e);
//            return result;
//        }
//        return result;
    }
}