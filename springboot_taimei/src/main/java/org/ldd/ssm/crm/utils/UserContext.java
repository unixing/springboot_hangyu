package org.ldd.ssm.crm.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.ldd.ssm.crm.domain.Company;
import org.ldd.ssm.crm.domain.Digitt;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.Fare;
import org.ldd.ssm.crm.domain.FlightRoute;
import org.ldd.ssm.crm.domain.HomePageData;
import org.ldd.ssm.crm.domain.MapperData;
import org.ldd.ssm.crm.domain.Menu;
import org.ldd.ssm.crm.domain.MenuNew;
import org.ldd.ssm.crm.domain.Resource;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.DOWQuery;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Component
public class UserContext {

	public static final String USER_IN_SESSION = "user_in_session";
	public static final String TEMP_USER_IN_SESSION = "temp_user_in_session";
	public static final String digitt_session = "digitt_session";
	public static final String companyId_session = "companyId_session";
	public static final String companyName_session = "companyName_session";
	public static final String companyItia_session = "companyItia_session";
	public static final String MENU_LIST = "menu_list";
	public static final String MENU_LIST_NEW = "menu_list_new";
	public static final String RESOURCE_LIST = "resource_list";
	public static final String URL_LIST = "url_list";
	public static final String COMPANY = "company";
	public static final String NULLITIA = "nullitia";
	public static final String MAPPER_LIST = "mapper_list";
	public static final String flyNum_LIST = "flyNum_LIST";
	public static final String versionn = "versionn";
	public static final String IP_NESS="ip_ness";
	public static final String ITIA_LIST="itia_list";
	public static final String AIRLINE_LIST="airline_list";
	public static final String USERTEXT="usertext";
	public static final String USERUUID="useruuid";

	//注入权限的service对象
	public static HttpServletRequest getRequest(){
		// 从RequestContextHolder中，获取ServletRequestAttributes
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		// 从ServletRequestAttributes中，获取request
		HttpServletRequest request = requestAttributes.getRequest();
		return request;
	}
	
	//注入权限的service对象
	public static HttpServletResponse getResponse(){
		// 从RequestContextHolder中，获取ServletRequestAttributes
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		// 从ServletRequestAttributes中，获取request
		HttpServletResponse response = requestAttributes.getResponse();
		return response;
	}
		
	public static void setIpNess(boolean ipness){
		getRequest().getSession().setAttribute(IP_NESS, ipness);
	}
	
	public static boolean getIpNess(){
		return (boolean) getRequest().getSession().getAttribute(IP_NESS);
	}
	
	public static void setItiaList(List<String> itias){
		getRequest().getSession().setAttribute(ITIA_LIST, itias);
	}
	
	public static List<String> getItiaList(){
		return (List<String>) getRequest().getSession().getAttribute(ITIA_LIST);
	}
	
	public static void setHomePageDataList(List<HomePageData> homePageDataList){
		getRequest().getSession().setAttribute(AIRLINE_LIST, homePageDataList);
	}
	
	public static List<HomePageData> getHomePageDataList(){
		return (List<HomePageData>) getRequest().getSession().getAttribute(AIRLINE_LIST);
	}
	
	public static void setCompanyId(String companyId){
		getRequest().getSession().setAttribute(companyId_session,companyId );
	}
	public static void setVersionn(String version){
		getRequest().getSession().getServletContext().setAttribute(versionn, version);
	}
	public static String getInterfacePath(){
		return (String) getRequest().getSession().getServletContext().getAttribute("interfacePath");
	}
	public static String getVersionn(){
		return (String) getRequest().getSession().getServletContext().getAttribute(versionn);
	}
	public static void setUsertext(String usertext){
		getRequest().getSession().setAttribute(USERTEXT, usertext);
	}
	public static String getUsertext(){
		return (String) getRequest().getSession().getAttribute(USERTEXT);
	}
	public static void setUseruuid(String useruuid){
		getRequest().getSession().setAttribute(USERUUID, useruuid);
	}
	public static String getUseruuid(){
		return (String) getRequest().getSession().getAttribute(USERUUID);
	}
	public static String getCompanyId(){
		return (String) getRequest().getSession().getAttribute(companyId_session);
	}
	
	public static void setcompanyItia(String companyItia){
		getRequest().getSession().setAttribute(companyItia_session,companyItia );
	}
	
	public static String getcompanyItia(){
		return (String) getRequest().getSession().getAttribute(companyItia_session);
	}
	
	public static void setMapperList(List<MapperData> mapperList){
		getRequest().getSession().setAttribute(MAPPER_LIST,mapperList );
	}
	@SuppressWarnings("unchecked")
	public static List<MapperData> getMapperList(){
		return (List<MapperData>) getRequest().getSession().getAttribute(MAPPER_LIST);
	}
	public static void setCompanyName(String companyName){
		getRequest().getSession().setAttribute(companyName_session,companyName );
	}
	
	public static String getCompanyName(){
		return (String) getRequest().getSession().getAttribute(companyName_session);
	}
	public static void setDigitt(Digitt digitt){
		getRequest().getSession().setAttribute(digitt_session, digitt);
	}
	
	public static Digitt getDigitt(){
		return (Digitt) getRequest().getSession().getAttribute(digitt_session);
	}
	
	public static void setUser(Employee emp){
		getRequest().getSession().setAttribute(USER_IN_SESSION, emp);
	}
	
	public static Employee getUser(){
		return (Employee) getRequest().getSession().getAttribute(USER_IN_SESSION);
	}
	public static void rmoveUser(){
		getRequest().getSession().removeAttribute(USER_IN_SESSION);
	}
	public static void setTempUser(Employee emp){
		getRequest().getSession().setAttribute(TEMP_USER_IN_SESSION, emp);
	}
	public static Employee getTempUser(){
		return (Employee) getRequest().getSession().getAttribute(TEMP_USER_IN_SESSION);
	}
	public static void rmoveTempUser(){
		getRequest().getSession().removeAttribute(TEMP_USER_IN_SESSION);
	}
	//获得ip
	public static String getReuqestIp() {
		return getRequest().getRemoteAddr();
	}
	//这下面3个方法是将数据放入作用域中, 以便使用
	public static void setData(String str,List<Z_Airdata> emp){
		getRequest().getSession().setAttribute(str, emp);
	}
	public static void setData(String str,DOWQuery dta_Sce){
		getRequest().getSession().setAttribute(str, dta_Sce);
	}
	public static void setData(String str,String emp){
		getRequest().getSession().setAttribute(str, emp);
	}
	@SuppressWarnings("unchecked")
	public static List<Z_Airdata> getData(String str){
		return (List<Z_Airdata>) getRequest().getSession().getAttribute(str);
	}
	@SuppressWarnings("unchecked")
	public static Set<Fare> getITIAData(String str){
		return (Set<Fare>)getRequest().getSession().getAttribute(str);
	}
	public static String getIndexData(String str){
		return (String)getRequest().getSession().getAttribute(str);
	}
	public static DOWQuery getDOWData(String str){
		return (DOWQuery)getRequest().getSession().getAttribute(str);
	}
	public static void rmoveUser(String str){
		getRequest().getSession().removeAttribute(str);
	}

	public static void setITIAData(String string, Set<Fare> list) {
		getRequest().getSession().setAttribute(string,list);
		
	}

	public static void setFareDate(String string, Map<String, Object> map) {
		getRequest().getSession().setAttribute(string,map);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getFareDate(String string) {
		return (Map<String, Object>)getRequest().getSession().getAttribute(string);
	}
	
	public static void setMenuNewList(List<MenuNew> menuList){
		getRequest().getSession().setAttribute(MENU_LIST_NEW, JSONArray.fromObject(menuList));
	}
	public static List<MenuNew> getMenuNewList(){
		JSONArray jsonArray = (JSONArray) getRequest().getSession().getAttribute(MENU_LIST_NEW);
		return (List<MenuNew>)jsonArray.toList(jsonArray, MenuNew.class);
	}
	
	public static List<String> getUrlList() {
		return (List<String>)getRequest().getSession().getAttribute(URL_LIST);
	}

	public static void setUrlList(List<String> urlList) {
		getRequest().getSession().setAttribute(URL_LIST, urlList);
	}
	
	public static void setResourceList(List<Resource> resourceList){
		getRequest().getSession().setAttribute(RESOURCE_LIST, resourceList);
	}
	public static List<Resource> getResourceList(){
		return (List<Resource>) getRequest().getSession().getAttribute(RESOURCE_LIST);
	}
	public static void setCompany(Company company){
		getRequest().getSession().setAttribute(COMPANY, company);
	}
	public static Company getCompany(){
		return (Company) getRequest().getSession().getAttribute(COMPANY);
	}
	public static void setNullItia(Set<String> set){
		getRequest().getSession().setAttribute(NULLITIA, set);
	}
	public static Set<String> getNullItia(){
		return (Set<String>) getRequest().getSession().getAttribute(NULLITIA);
	}
	
	public static void setFlyNumList(List<FlightRoute> flyNumList){
		getRequest().getSession().setAttribute(flyNum_LIST, flyNumList);
	}
	public static List<FlightRoute> getFlyNumList(){
		return (List<FlightRoute>) getRequest().getSession().getAttribute(flyNum_LIST);
	}
	public static List<Menu> getMenuList(){
		return (List<Menu>)getRequest().getSession().getAttribute(MENU_LIST);
	}
	public static void setMenuList(List<Menu> menuList){
		getRequest().getSession().setAttribute(MENU_LIST, menuList);
	}
}
