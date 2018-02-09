/**
 * 
 */
package org.ldd.ssm.hangyu.utils;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author Taimei
 *
 */
public class JdbcDecryptPropertiesFile extends PropertyPlaceholderConfigurer{
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,Properties props) throws BeansException {
		String usernameKey = "jdbc.mysql.username";
		String passwordKey = "jdbc.mysql.password";
		 //读取配置文件中的密文
        String username = props.getProperty(usernameKey);
        String password = props.getProperty(passwordKey);
        //避免包之间的依赖
        if(!TextUtil.isEmpty(username) && !"null".equals(username)){
            //将密文转换为明文
            String decPassword = Decryption.decryptBasedDes(username);
            //将解密后的密码放入property对象中
            props.setProperty(usernameKey, decPassword);
        }
        if(!TextUtil.isEmpty(password)  && !"null".equals(password)){
        	//将密文转换为明文
        	String decPassword = Decryption.decryptBasedDes(password);
        	//将解密后的密码放入property对象中
        	props.setProperty(passwordKey, decPassword);
        }
		super.processProperties(beanFactoryToProcess, props);
	}
}
