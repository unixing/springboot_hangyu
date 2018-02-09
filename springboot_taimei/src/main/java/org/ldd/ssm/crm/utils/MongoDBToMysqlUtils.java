package org.ldd.ssm.crm.utils;

import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoDBToMysqlUtils {
	
	/**
	 * 连接mongodb数据库方法
	 * @return
	 */
	public static MongoClient mongoDBCollection(){
		//地址
		ServerAddress addr = new ServerAddress("192.168.11.11", 27017);
		/**
		 * 权限验证: 
		 * 参数一:权限验证表  
		 * 参数二:账号
		 * 参数三:密码
		 */
		MongoCredential credential = MongoCredential.createCredential("verify_ldd", "admin", "Ldd_123!".toCharArray());
		//参数设置
		MongoClientOptions options = MongoClientOptions.builder().serverSelectionTimeout(1000).build();
		
		MongoClient mongoClient = new MongoClient(addr, Arrays.asList(credential), options);
		
		return mongoClient;
	}
}
