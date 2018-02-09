package org.ldd.ssm.hangyu.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 身份拦截器
 */
@Component
public class EtermAuthInterceptor implements HandlerInterceptor{
	public static final String LOGIN_PATH="/chat";
	public static final String CHECK_LOGIN="/login";
	public static final String GETVALIDCODE="/getValidCode";
	public static final String VALIDPHONE="/validPhone";
	public static final String REGISTER="/register";
	public static final String RESETPWD="/resetPwd";
	public static final String VALIDCODE="/validCode";
	/**
	 * 在调用控制器方法之前,拦截
	 * 返回值 true:则代表放行
	 * 返回值false:则代表拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
//		拦截器执行顺序
		Employee user = UserContext.getUser();
		String requestURI = request.getRequestURI();
		if("/".equals(requestURI)){
			response.sendRedirect(request.getContextPath()+"/chat");
			return false;
		}
		//检查用户是否存在
		String header = request.getHeader("X-Requested-With");
		if(header!=null){
			if(user==null 
					&& !LOGIN_PATH.equals(requestURI)
					&& !CHECK_LOGIN.equals(requestURI)){
			//重定向跳转到登录页面
				response.getWriter().print("nullUser");
				return false;
			}
			return true;
		}else{
			if(user==null 
					&& !LOGIN_PATH.equals(requestURI)
					&& !CHECK_LOGIN.equals(requestURI)
					&& !GETVALIDCODE.equals(requestURI)
					&& !VALIDPHONE.equals(requestURI)
					&& !REGISTER.equals(requestURI)
					&& !RESETPWD.equals(requestURI)
					&& !VALIDCODE.equals(requestURI)){
			//重定向跳转到登录页面
				response.sendRedirect(request.getContextPath()+"/chat");
				return false;
			}
			return true;
		}
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
