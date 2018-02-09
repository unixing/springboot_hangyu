package org.ldd.ssm.hangyu.utils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;

import java.util.Date;

import javax.servlet.http.HttpSession;

public class SendSmsAliyun {
    public static boolean sample(String mobiles) {     
    	boolean result = true;
        HttpSession session = UserContext.getRequest().getSession();
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI1FSUv18CHLSa", "qIMTR9P5B0SzeplEMACjApourxeHS8");//appid和appsecret
        try {
        	DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");//接口地址
        	IAcsClient client = new DefaultAcsClient(profile);
        	SingleSendSmsRequest request = new SingleSendSmsRequest();
            request.setSignName("成都太美技术");//签名
            request.setTemplateCode("SMS_37875094");//模板code
            request.setRecNum(mobiles);//接收手机号，多个用英文逗号隔开
            String prevGetValidCodeTime = (String) (session.getAttribute("PrevValidCodeTime")==null?"":session.getAttribute("PrevValidCodeTime"));
            long currentTime = new Date().getTime();
            if("".equals(prevGetValidCodeTime)||Long.valueOf(prevGetValidCodeTime).longValue()-currentTime>5*60*1000){
            	//验证码生成
                int validnbr = (int)((Math.random()*9+1)*100000);
                request.setParamString("{validnbr:\""+validnbr+"\"}");//模板中的参数
//                request.setParamString("{validnbr:'"+validnbr+"'}");//模板中的参数
//                SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
                SingleSendSmsResponse response = client.getAcsResponse(request);
                //存储当前获取时间和验证码
//                session.setAttribute("validnbr", validnbr);
                //设置当前手机号存储对应的验证码
                session.setAttribute(mobiles,validnbr);
                session.setAttribute("PrevValidCodeTime", new Date().getTime()+"");
            }else{
            	//验证码生成
//                String validnbr = String.valueOf(session.getAttribute("validnbr"));
            	String validnbr = String.valueOf(session.getAttribute(mobiles));
            	request.setParamString("{validnbr:\""+validnbr+"\"}");//模板中的参数
//            	request.setParamString("{validnbr:'"+validnbr+"'}");//模板中的参数
//                SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
                SingleSendSmsResponse response = client.getAcsResponse(request);
            }
        } catch (ServerException e) {
        	session.removeAttribute("PrevValidCodeTime");
        	session.removeAttribute(mobiles);
            result = false;
            return result;
        }
        catch (ClientException e) {
        	session.removeAttribute("PrevValidCodeTime");
        	session.removeAttribute(mobiles);
            result = false;
            return result;
        }
        return result;
    }
    
    public static boolean sendInterviewNotice(String mobiles,String name,String time) {     
    	boolean result = true;
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI1FSUv18CHLSa", "qIMTR9P5B0SzeplEMACjApourxeHS8");//appid和appsecret
        try {
        	DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");//接口地址
        	IAcsClient client = new DefaultAcsClient(profile);
        	SingleSendSmsRequest request = new SingleSendSmsRequest();
            request.setSignName("成都太美技术");//签名
            request.setTemplateCode("SMS_59875052");//模板code
            request.setRecNum(mobiles);//接收手机号，多个用英文逗号隔开
        	//验证码生成
            request.setParamString("{name:\""+name+"\",time:\""+time+"\"}");//模板中的参数
            SingleSendSmsResponse response = client.getAcsResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
            result = false;
            return result;
        }
        catch (ClientException e) {
            e.printStackTrace();
            result = false;
            return result;
        }
        return result;
    }
    
    
    public static void main(String[] args) {
//    	String[] mobiles = {"15208258814"};
		boolean result = sample("15208258814");
//    	String[] mobiles = {"15208258814","13982146806"};
//    	StringBuffer mobileStr = new StringBuffer();
//    	for(int i=0;i<mobiles.length;i++){
//    		if(i==0){
//    			mobileStr.append(mobiles[i]);
//    		}else{
//    			mobileStr.append(","+mobiles[i]);
//    		}
//    	}
//		try {
			//微信第三方授权登录
//			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx520c15f417810387&redirect_uri="+URLDecoder.decode("https%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60", "utf-8")+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//		} catch (UnsupportedEncodingException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
	}
}
