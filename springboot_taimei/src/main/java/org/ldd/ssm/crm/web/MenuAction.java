package org.ldd.ssm.crm.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.HomePageData;
import org.ldd.ssm.crm.domain.Menu;
import org.ldd.ssm.crm.domain.MenuNew;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.query.HomePageQuery;
import org.ldd.ssm.crm.service.HomePageService;
import org.ldd.ssm.crm.service.ICompanyService;
import org.ldd.ssm.crm.service.IDepartmentService;
import org.ldd.ssm.crm.service.IMenuNewService;
import org.ldd.ssm.crm.service.IMenuService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 *User的前台控制器层
 */
@Controller
public class MenuAction {
	//spring注解注入对象
	@Autowired
	private IMenuService menuService;//旧菜单服务层对象
	@Autowired
	private IMenuNewService menuNewService;
	@Autowired
	private IDepartmentService departmentService;
	@Autowired
	private OutPortMapper outPortMapper;
	@Autowired
	private HomePageService homePageService;
	@Autowired
	private ICompanyService companyService;
	//springMVC请求的映射地址
	@RequestMapping("/childer")
	@ResponseBody
	/**
	 * 查询子菜单
	 * @param id : 父菜单的id
	 */
	public List<Menu> getChilder(Long id) {
			return menuService.getChilder(id);
	}
	//前台页面定位
	@RequestMapping("/indexd")
	@MethodNote(comment="默认:16")
	public String get(String itia){
		//重定向到欢迎页面,基于springMVC
		//得到用户信息，并根据用户信息得到用户对应的机场（如果多个机场则进入potrol页面，如果只有一个则到航线页面）和角色，并根据角色获得对应的资源和菜单。放入session中或者返回到页面保存起来
		Employee emp = UserContext.getUser();
		List<MenuNew> menuList=null;
		if(emp.getUsrSts()==99){
			menuList = menuNewService.getAllMenu();
		}else{
			menuList = menuService.getMenuList(emp.getId());
			if(menuList==null||menuList.size()==0){//当为空时，账户为后台创建，没有关联默认角色
				menuList = menuNewService.getAllMenu();
			}
		}
		for(MenuNew menu:menuList){
			if("默认".equals(menu.getName())){
				menuList.remove(menu);
				break;
			}
		}
		Gson gson=new Gson();
		String hjson = "";
		hjson = gson.toJson(menuService.getMenuAllList());
		UserContext.getRequest().getSession().setAttribute("menuAll", hjson);
		String hjson2 = "";
		hjson2 = gson.toJson(menuList);
		UserContext.getRequest().getSession().setAttribute("menu",hjson2 );
		//查询对应的机场
		//查询对应的集团公司
//		Department department = departmentService.load(emp.getDepartmentId());
//		if(department==null){
//			//当前用户所对应的部门不存在，即相应的机场或机场集团不存在，返回登陆
//			UserContext.getRequest().getSession().invalidate();
//			return "redirect:/login.jsp";
//		}
//		Long companyId = department.getCompanyId();
//		itia="WDS";
		Long companyId =Long.valueOf(UserContext.getCompanyId());
		List<String> iataList = menuService.getUserAirportList(companyId);
		if(iataList!=null){
			if(iataList.size()>1){//portal
				UserContext.setItiaList(iataList);
				if(itia!=null&&!"".equals(itia)){
					//由于页面跳转使用get方式，防止用户随意输入三字码
					boolean flag = false;
					for(String szm:iataList){
						if(szm.equals(itia)){
							flag=true;
						}
					}
					if(flag){
						UserContext.setCompanyName(outPortMapper.getNameByCode(itia));
						UserContext.setcompanyItia(itia);
						setLineToSession();
						//进入机场页面
						return "/index/index";
					}else{
						UserContext.setCompanyName(companyService.load(companyId).getCpyNm());
						//进入potorl页面
						return "/index/portal";
					}
				}else{
					UserContext.setCompanyName(companyService.load(companyId).getCpyNm());
					//进入potorl页面
					return "/index/portal";
				}
			}else if(iataList.size()==1){//机场
				//进入机场页面
				UserContext.setCompanyName(outPortMapper.getNameByCode(iataList.get(0)));
				UserContext.setcompanyItia(iataList.get(0));
				setLineToSession();
				return "/index/index";
			}else{
				//该用户没有对应的机场,返回登陆页面
				UserContext.getRequest().getSession().invalidate();
				return "redirect:/login.jsp";
			}
		}else{
			//该用户没有对应的机场,返回登陆页面
			UserContext.getRequest().getSession().invalidate();
			return "redirect:/login.jsp";
		}
	}
	@RequestMapping("/hangyu")
	@MethodNote(comment="默认:16")
	public String hangyu(String itia){
		//重定向到欢迎页面,基于springMVC
		//得到用户信息，并根据用户信息得到用户对应的机场（如果多个机场则进入potrol页面，如果只有一个则到航线页面）和角色，并根据角色获得对应的资源和菜单。放入session中或者返回到页面保存起来
		Employee emp = UserContext.getUser();
		List<MenuNew> menuList=null;
		if(emp.getUsrSts()==99){
			menuList = menuNewService.getAllMenu();
		}else{
			menuList = menuService.getMenuList(emp.getId());
			if(menuList==null||menuList.size()==0){//当为空时，账户为后台创建，没有关联默认角色
				menuList = menuNewService.getAllMenu();
			}
		}
		for(MenuNew menu:menuList){
			if("默认".equals(menu.getName())){
				menuList.remove(menu);
				break;
			}
		}
		Gson gson=new Gson();
		String hjson = "";
		hjson = gson.toJson(menuService.getMenuAllList());
		UserContext.getRequest().getSession().setAttribute("menuAll", hjson);
		String hjson2 = "";
		hjson2 = gson.toJson(menuList);
		UserContext.getRequest().getSession().setAttribute("menu",hjson2 );
		Long companyId =Long.valueOf(UserContext.getCompanyId());
		List<String> iataList = menuService.getUserAirportList(companyId);
		if(iataList!=null){
			if(iataList.size()>1){//portal
				UserContext.setItiaList(iataList);
				if(itia!=null&&!"".equals(itia)){
					//由于页面跳转使用get方式，防止用户随意输入三字码
					boolean flag = false;
					for(String szm:iataList){
						if(szm.equals(itia)){
							flag=true;
						}
					}
					if(flag){
						UserContext.setCompanyName(outPortMapper.getNameByCode(itia));
						UserContext.setcompanyItia(itia);
						setLineToSession();
						//进入机场页面
						return "/index/hangyu";
					}else{
						UserContext.setCompanyName(companyService.load(companyId).getCpyNm());
						//进入potorl页面
						return "/index/hangyu";
					}
				}else{
					UserContext.setCompanyName(companyService.load(companyId).getCpyNm());
					//进入potorl页面
					return "/index/hangyu";
				}
			}else if(iataList.size()==1){//机场
				//进入机场页面
				UserContext.setCompanyName(outPortMapper.getNameByCode(iataList.get(0)));
				UserContext.setcompanyItia(iataList.get(0));
				setLineToSession();
				return "/index/hangyu";
			}else{
				//该用户没有对应的机场,返回登陆页面
				UserContext.getRequest().getSession().invalidate();
				return "redirect:/login.jsp";
			}
		}else{
			//该用户没有对应的机场,返回登陆页面
			UserContext.getRequest().getSession().invalidate();
			return "redirect:/login.jsp";
		}
	}
	public  void setLineToSession(){
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
		List<HomePageData> hpdata = homePageService.getAirportOnLineDataList(homePageQuery);
		//将首页的所有航线放入到seesion中
		UserContext.setHomePageDataList(hpdata);
	}
	@RequestMapping("/menu_list")
	@ResponseBody
	public Map<String,Object> getList(Long companyId){
		Map<String,Object> map = new HashMap<String,Object>();    
		try {                                                     
			Employee emp = UserContext.getUser();                 
			List<Menu> list = null;                               
			if(emp.getUsrSts()==99){                              
				list = menuService.getParent(companyId, null);    
			}else{                                                
				list = menuService.getParent(companyId,emp.getId());
			}                                                     
			map.put("opResult", "0");
			map.put("list", list);
		} catch (Exception e) {
			map.put("message","操作异常");
		}
		return map;
	}
}
