/**   
 * @Title: LogAspect.java 
 * @Package org.ldd.ssm.crm.aop 
 * @Description: TODO
 * @author taimei-gds   
 * @date 2016-7-7 下午4:22:21 
 * @version V1.0   
 */ 
package org.ldd.ssm.crm.aop;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.OperatorLog;
import org.ldd.ssm.crm.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Title:LogAspect 
 * @Description:
 * @author taimei-gds 
 * @date 2016-7-7 下午4:22:21
 */
@Aspect
public class LogAspect {
	@Autowired
	OperatorLogService operatorLogService;
//	@Before("execution(* org.ldd.ssm.crm.service..*.*(..))")
//	public void doBeforeInServiceLayer(JoinPoint joinPoint) {}
//	
//	@After("execution(* org.ldd.ssm.crm.service..*.*(..))")
//	public void doAfterInServiceLayer(JoinPoint joinPoint) {}
	
	@Around("execution(* org.ldd.ssm.crm.web.*Action.*(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
		HttpSession session = request.getSession(); 
		Employee employee = (Employee) session.getAttribute("user_in_session");
		String ip = request.getRemoteAddr(); 
		String serverIP = request.getLocalAddr()+":"+request.getLocalPort(); 
		MethodSignature signa = (MethodSignature) pjp.getSignature();
		Object[] args =  pjp.getArgs();
		Method method = signa.getMethod();
		MyMethodNote mn = method.getAnnotation(MyMethodNote.class);
		OperatorLog operatorLog = new OperatorLog();
		//方法注释
		String str = "";
		if(mn!=null){
			//如果没有添加注释的都不记录日志
			 str = mn.comment();
			 String [] strs = str.split(":");
			//注释用：分隔，操作类型(1、登陆，2、功能访问，3、数据操作，4、编辑修改，5、系统日志)
			operatorLog.setMethodCode(signa.getName());
			operatorLog.setMethodName(strs[0]);
			//操作时间
			Date date = new Date();
			operatorLog.setOpTime(sdf.format(date));
			//操作IP
			operatorLog.setOpIp(ip);
			if(employee!=null){
				//操作用户名
				operatorLog.setUserName(employee.getUsrNm());
				//操作用户ID
				operatorLog.setUserId(Integer.parseInt(employee.getId().toString()));
			}
			//写死了判断登陆，设置登陆人员 
			if("checkLogin".equals(signa.getName())){
				if(args!=null&&args[1]!=null){
					operatorLog.setUserName(args[1].toString());
				}else{
					operatorLog.setUserName("空名字");
				}
				
			}
			//日志类型
			operatorLog.setLogType(strs[1]);
			//设置参数
			String param = "";
			if(args!=null){
				for(Object object : args){
					if(object!=null)
						param = param + object.toString()+"-";
				}
			}
			operatorLog.setParams(param);
		
			Object result = null;
			//操作结果
			operatorLog.setOpResult("成功");
			//日志级别
			operatorLog.setLogRank("1");
			//服务器IP
			operatorLog.setServerIP(serverIP);
			try {
				result =  pjp.proceed();
			} catch (Exception e) {
				operatorLog.setLogType("1");
				operatorLog.setLogRank("1");
				operatorLog.setOpResult("失败");
			}
			if("checkLogin".equals(signa.getName())){
				HashMap<Object, Object> hh = (HashMap<Object, Object>) result;
				String ret = (String) hh.get("msg");
				String res = (String) hh.get("opResult");
				if(!TextUtil.isEmpty(ret)&&ret.contains("登陆失败")){
					if(args!=null&&args[2]!=null){
						operatorLog.setLogType("1");
						operatorLog.setLogRank("1");
						operatorLog.setOpResult("失败");
						operatorLog.setMethodName(operatorLog.getMethodName()+",密码为"+args[2].toString());
					}else{
						operatorLog.setLogType("1");
						operatorLog.setLogRank("1");
						operatorLog.setOpResult("失败");
						operatorLog.setMethodName(operatorLog.getMethodName()+",密码为");
					}
				}
				if("-1".equals(res)){
					operatorLog.setLogType("1");
					operatorLog.setLogRank("1");
					operatorLog.setOpResult("失败");
					operatorLog.setMethodName(operatorLog.getMethodName()+",账号禁用或者被删除状态");
				}
				if("-2".equals(res)){
					operatorLog.setLogType("1");
					operatorLog.setLogRank("1");
					operatorLog.setOpResult("失败");
					operatorLog.setMethodName(operatorLog.getMethodName()+",登录名和密码不正确");
				}
				if("-3".equals(res)){
					operatorLog.setLogType("1");
					operatorLog.setLogRank("1");
					operatorLog.setOpResult("失败");
					operatorLog.setMethodName(operatorLog.getMethodName()+",验证码错误");
				}
				if("-8".equals(res)){
					operatorLog.setLogType("1");
					operatorLog.setLogRank("1");
					operatorLog.setOpResult("失败");
					operatorLog.setMethodName(operatorLog.getMethodName()+",验证码错误");
				}
				if("4".equals(res)){
					operatorLog.setLogType("1");
					operatorLog.setLogRank("1");
					operatorLog.setOpResult("失败");
					operatorLog.setMethodName(operatorLog.getMethodName()+",后台系统首次登陆，后台没有绑定手机号");
				}
				if("3".equals(res)){
					operatorLog.setLogType("1");
					operatorLog.setLogRank("1");
					operatorLog.setOpResult("失败");
					operatorLog.setMethodName(operatorLog.getMethodName()+",已经绑定手机, 手机验证码登录");
				}
			}
			//删除
			operatorLogService.saveOperatorLog(operatorLog);
			return result;
		}else{
			return  pjp.proceed();
		}
	}
}