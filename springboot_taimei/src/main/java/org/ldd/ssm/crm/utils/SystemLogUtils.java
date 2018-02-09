package org.ldd.ssm.crm.utils;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.ldd.ssm.crm.domain.SystemLog;
import org.ldd.ssm.crm.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 系统日志工具
 *   切面：
 *      切入点 （在哪里做事情）
 *      通知    （增强）
 *      连接点 （切入时的上下文信息）
 */
@Component
public class SystemLogUtils {
	@Autowired
	private ISystemLogService logService;
	
	public void setLogService(ISystemLogService logService) {
		this.logService = logService;
	}
	
	public void writeLog(JoinPoint joinPoint){
		Object serviceObj = joinPoint.getTarget();
		if(serviceObj instanceof ISystemLogService){// 如果是记录日志本身操作，则忽略切面
			return;
		}
		Class<? extends Object> serviceClz = joinPoint.getTarget().getClass();
		
		String methodName = joinPoint.getSignature().getName();
		// 创建日志对象
		SystemLog log = new SystemLog();
		// 封装日志属性
		log.setOpUser(UserContext.getUser());
		log.setOpTime(new Date());
		log.setOpIp(UserContext.getReuqestIp());
		//拼装成做的事情
		String function = serviceClz.getName()+":"+methodName;
		log.setFunction(function );
		// 保存日志
		logService.save(log);
	}
}
