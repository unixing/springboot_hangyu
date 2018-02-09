package org.ldd.ssm.hangyu.filter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.ldd.ssm.hangyu.utils.PublicOpinionUtil;

public class ContextInitListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//初始化配置文件内容到内存
//		PublicOpinionUtil.getPublicOpinionIp();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO 自动生成的方法存根
		
	}

}
