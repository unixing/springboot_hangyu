package org.ldd.ssm.hangyu.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.ldd.ssm.hangyu.domain.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Component
public class UserContext {

	public static final String USER_IN_SESSION = "user_in_session";
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
	public static final String CHATNAME="chatname";

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
	
	public static void setChatName(String name){
		getRequest().getSession().setAttribute(CHATNAME, name);
	}
	
	public static String getChatName(){
		return (String) getRequest().getSession().getAttribute(CHATNAME);
	}
	public static void setEmployee(String name,Employee employee){
		getRequest().getSession().setAttribute(name, employee);
	}
	
	public static Employee getEmployee(String name){
		return (Employee) getRequest().getSession().getAttribute(name);
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
	
	
	public static void setCompanyId(String companyId){
		getRequest().getSession().setAttribute(companyId_session,companyId );
	}
	public static void setVersionn(String version){
		getRequest().getSession().getServletContext().setAttribute(versionn, version);
	}
	public static String getVersionn(){
		return (String) getRequest().getSession().getServletContext().getAttribute(versionn);
	}
	public static void setUsertext(String usertext){
		getRequest().getSession().getServletContext().setAttribute(USERTEXT, usertext);
	}
	public static String getUsertext(){
		return (String) getRequest().getSession().getServletContext().getAttribute(USERTEXT);
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
	
	public static void setUser(Employee emp){
		getRequest().getSession().setAttribute(USER_IN_SESSION, emp);
	}
	
	public static Employee getUser(){
		return (Employee) getRequest().getSession().getAttribute(USER_IN_SESSION);
	}
	public static void rmoveUser(){
		getRequest().getSession().removeAttribute(USER_IN_SESSION);
	}
	//获得ip
	public static String getReuqestIp() {
		return getRequest().getRemoteAddr();
	}
	
	public static void rmoveUser(String str){
		getRequest().getSession().removeAttribute(str);
	}

	public static void setFareDate(String string, Map<String, Object> map) {
		getRequest().getSession().setAttribute(string,map);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getFareDate(String string) {
		return (Map<String, Object>)getRequest().getSession().getAttribute(string);
	}
	
	public static List<String> getUrlList() {
		return (List<String>)getRequest().getSession().getAttribute(URL_LIST);
	}

	public static void setUrlList(List<String> urlList) {
		getRequest().getSession().setAttribute(URL_LIST, urlList);
	}

	public static void setNullItia(Set<String> set){
		getRequest().getSession().setAttribute(NULLITIA, set);
	}
	public static Set<String> getNullItia(){
		return (Set<String>) getRequest().getSession().getAttribute(NULLITIA);
	}
	
	
}
