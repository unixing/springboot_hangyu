package org.ldd.ssm.crm.utils;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
/**
 * mybatis需要的SqlSession的工具类
 */
public class MybatisUtils {
	//单例模式下的SqlSessionFactory 对象
	private static  SqlSessionFactory factory;
	
	static{
		try {
			// 1、 获取配置文件
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			// 2、 加载配置文件，获得会话工厂
			factory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获得SqlSession
	 */
	public static SqlSession openSqlSession(){
		return factory.openSession();//返回openSession对象
	}
}
