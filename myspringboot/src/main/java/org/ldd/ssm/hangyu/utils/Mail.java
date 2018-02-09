package org.ldd.ssm.hangyu.utils;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.MyEmailAutherticator;
public class Mail {
    static Logger log = Logger.getLogger(Mail.class);
    //发送邮件的帐号和密码
//    private String username="575836173@qq.com";
	private static String username = "support_cd@taimei-air.com";	//发送邮箱需要时企业邮箱
//    private String password="leng19890529fmb";
//    private String password = "pmzpgvhfwmxybcjf";
	private static String password = "CDtaimei888";
    
//    private String host = "smtp.qq.com";
    private static String host = "smtp.exmail.qq.com";
    
    private static String mail_head_name = "航线交易";
    
    private static String mail_head_value = "航线交易";
    
//    private String mail_to = "15208258814@163.com";
    
//    private String mail_to = "575836173@qq.com";
    
    private static String mail_from = "support_cd@taimei-air.com";
    
    private static String mail_subject = "绑定邮箱";
    
//    private String mail_body = "尊敬的用户，您好：\r\n这是一封注册认证邮件，请点击以下链接确认：\r\n "+URLEncodeUtils.encodeURL("http://cm.taimei-air.com:9100/login.jsp")+"\r\n如果链接不能点击，请复制地址到浏览器，然后直接打开。";
    
    private static String mail_body = "";
    
    private static String personalName = "航线交易";
    
    public static boolean sendMail(String mail_to,String validStr,Long id) throws SendFailedException{
    	boolean result = true;
        try {
             //发送邮件的props文件
            Properties props = new Properties();
            // 初始化props
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            
            //进行邮件服务用户认证
            Authenticator auth = new MyEmailAutherticator(username,password);
            
            // 创建session,和邮件服务器进行通讯
            Session session = Session.getDefaultInstance(props,auth);
            
            // 创建mime类型邮件
            MimeMessage message = new MimeMessage(session);
            //设置邮件格式
            message.setContent("邮箱验证码","text/html;charset=utf-8");
            // 设置主题
            message.setSubject(mail_subject);
            //设置邮件内容
//            mail_body = "尊敬的用户，您好：\r\n这是一封注册认证邮件，请点击以下链接确认：\r\nhttp://cm.taimei-air.com:9100/validMail?validStr="+validStr+"\r\n如果链接不能点击，请复制地址到浏览器，然后直接打开。";
//            mail_body = "尊敬的用户，您好：\r\n这是一封注册认证邮件，请点击以下链接确认：\r\nhttp://192.168.18.7:8090/validMail?validStr="+validStr+"&id="+id+"\r\n如果链接不能点击，请复制地址到浏览器，然后直接打开。";
//            mail_body = "尊敬的用户，您好：\r\n这是一封注册认证邮件，请点击以下链接确认：\r\nhttp://192.168.22.8/validMail?validStr="+validStr+"&id="+id+"\r\n如果链接不能点击，请复制地址到浏览器，然后直接打开。";
            mail_body = "尊敬的用户，您好：\r\n您的邮箱验证码为"+validStr+",邮箱验证码将在10分钟后失效，请注意查收。";
            message.setText(mail_body);
//            message.setDescription("邮件验证码");
            //设置邮件标题
            message.setHeader(mail_head_name, mail_head_value);
            message.setSentDate(new Date());//设置邮件发送时期
            Address address = new InternetAddress(mail_from,personalName);
            //设置邮件发送者的地址
            message.setFrom(address);
            
            //======单发邮件======
            //设置邮件接收者的地址
            Address toaddress = new InternetAddress(mail_to);
            // 设置收件人
            message.addRecipient(Message.RecipientType.TO,toaddress);
            
            //======群发邮件======
//            List<String> recipients = new ArrayList<String>();
//            recipients.add("123456789@qq.com");
//            recipients.add("234567890@gmail.com");
//            final int num = recipients.size();
//            InternetAddress[] addresses = new InternetAddress[num];
//            for (int i = 0; i < num; i++) {
//                addresses[i] = new InternetAddress(recipients.get(i));
//            }
//            message.setRecipients(Message.RecipientType.TO, addresses);
            // 发送邮件
            Transport.send(message);
        } catch (Exception e) {
        	log.error(e);
            result = false;
            return result;
        }
        return result;
    }
}