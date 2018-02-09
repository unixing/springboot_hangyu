package org.ldd.ssm.crm.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.service.IEmployeeService;
import org.ldd.ssm.crm.service.IResourceService;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 身份拦截器
 */
@Component
public class AuthInterceptor implements HandlerInterceptor{
	
	public static final String LOGIN_PATH="/checkLogin";
	public static final String SCAN_RESOURCE="/resource/scanResource";
	public static final String EMAIL_VALID="/validMail";
	public static final String SEND_NOTICE="/sendView";
	public static final String SEND_INTERVIEW_NOTICE="/sendInterviewNotice";
	public static final String SEND_CODE="/getLoginSmCode";
	public static final String BINDPHONE_OR_VALIDCODE_Login="/bindPhoneOrValidCode_Login";
	public static final String BINDPHONE_OR_VALIDCODE_Admin="/bindPhoneOrValidCode_Admin";
	public static final String VALID_PHONE="/validPhone";
	public static final String CREATE_NAME_AND_PWD="/createNamdAndPwd";
	public static final String AUTHIMAGE="/authImage";
	public static final String LOGIN="/login";
	public static final String HANGYU_LOGIN="/hangyu_login";
	public static final String VALID_CHANGE_PWD_CODE="/validChangePwdCode";
	public static final String UPDATE_PASSWORD="/updatePwd";
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IEmployeeService employeeService;
	/**
	 * 在调用控制器方法之前,拦截
	 * 返回值 true:则代表放行
	 * 返回值false:则代表拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
//		如果拦截失败应该使用Response来重定向到登录页面
		boolean flag = false;
		//拦截器执行顺序
		Employee user = UserContext.getUser();
		HttpSession session = UserContext.getRequest().getSession();
		String requestURI = request.getRequestURI();
		/*if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod2 = (HandlerMethod)handler;
			String name = handlerMethod2.getBeanType().getName();
			if(SCAN_RESOURCE.equals(name)){
				return true;
			}
		}*/
		if(user==null){
			if(requestURI.equals(EMAIL_VALID)){
				session.setAttribute("id", request.getParameter("id"));
				session.setAttribute("validStr", request.getParameter("validStr"));
			}
			if(requestURI.equals(SEND_NOTICE)||requestURI.equals(SEND_INTERVIEW_NOTICE)||
				requestURI.equals(SEND_CODE)||requestURI.equals(BINDPHONE_OR_VALIDCODE_Login)||
				requestURI.equals(BINDPHONE_OR_VALIDCODE_Login)||requestURI.equals(VALID_PHONE)||
				requestURI.equals(CREATE_NAME_AND_PWD)||requestURI.equals(AUTHIMAGE)||requestURI.equals(LOGIN)
				||requestURI.equals(HANGYU_LOGIN)||requestURI.equals(VALID_CHANGE_PWD_CODE)||requestURI.equals(UPDATE_PASSWORD)){//发送面试通知不拦截
				return true;
			}
		}else if(user!=null){
			//拦截未赋予权限的url--start
//			if(user.getUsrSts()!=99){
//				List<String> urls = UserContext.getUrlList();
//				if(urls!=null&&urls.size()>0){
//					if(!urls.contains(requestURI)){
//						//重定向跳转到登录页面
//						response.sendRedirect("/indexd");
//						return flag;
//					}
//				}
//			}//拦截未赋予权限的url--end
		}
		String parameter = request.getParameter("token");
		if(parameter!=null){
			user = employeeService.getToken(parameter);
		}
		//检查用户是否存在
		String header = request.getHeader("X-Requested-With");
		if(header!=null){
			if(user==null && !LOGIN_PATH.equals(requestURI)){
				//重定向跳转到登录页面
				//response.getWriter().print("nullUser");
				UserContext.getRequest().getSession().invalidate();
				response.sendRedirect("/index.jsp");
				return false;
			}
		}else{
			if(user==null && !LOGIN_PATH.equals(requestURI)){
				//重定向跳转到登录页面,每天跳转到首页,登录时都将之前的用户给设置为NULL
				UserContext.getRequest().getSession().invalidate();
				response.sendRedirect("/index.jsp");
				return false;
			}
		}
		if(user!=null&&user.getUsrSts()==99){
			return true;
		}
		//判断类型
//		if(handler instanceof HandlerMethod){
//			//强制转换
//			HandlerMethod handlerMethod = (HandlerMethod)handler;
//			//获得请求的控制器
//			String urlName = handlerMethod.getBeanType().getName();
//			//获得请求的方法
//			String methodName = handlerMethod.getMethod().getName();
//			//拼接成数据库资源库的格式
//			String resourceName = urlName+":"+methodName;
//			//获得根据资源查询的数据库资源
//			flag = resourceService.checkPermission(resourceName, UserContext.getResourceList());
//			//如果没有,则说明没有资格访问.
//			if(!flag){
//				//没有权限控制,不需要访问
//				response.sendRedirect("/resource_hasNoPermission");
//				return flag;
//			}
//		}
		return true;
	}
	/**
	 * 在调用控制器方法之后,拦截(在生成视图之前)
	 */
	public void postHandle(HttpServletRequest requset, HttpServletResponse response,
			Object handler, ModelAndView mv) throws Exception {
//		如果拦截失败应该使用Response来重定向到登录页面
		//拦截器执行顺序
	}
	
	/**
	 * 在视图生成后拦截(后台所有的逻辑方法完成之后)
	 */
	public void afterCompletion(HttpServletRequest requset,
			HttpServletResponse response, Object handler, Exception exc)
			throws Exception {
//		如果拦截失败应该使用Response来重定向到登录页面
		//拦截器执行顺序
	}
}
