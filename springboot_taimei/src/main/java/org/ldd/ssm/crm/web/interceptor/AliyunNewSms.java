package org.ldd.ssm.crm.web.interceptor;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.ldd.ssm.crm.query.WatchRemindDto;
import org.ldd.ssm.crm.utils.UserContext;

/**
 * Created on 17/6/7.
 * 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可)
 * 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar
 * 2:aliyun-java-sdk-dysmsapi.jar
 *
 * 备注:Demo工程编码采用UTF-8
 * 国际短信发送请勿参照此DEMO
 */
public class AliyunNewSms {
//	阿里云错误码对比
//	OK								请求成功
//	isp.RAM_PERMISSION_DENY			RAM权限DENY
//	isv.OUT_OF_SERVICE				业务停机
//	isv.PRODUCT_UN_SUBSCRIPT		未开通云通信产品的阿里云客户
//	isv.PRODUCT_UNSUBSCRIBE			产品未开通
//	isv.ACCOUNT_NOT_EXISTS			账户不存在
//	isv.ACCOUNT_ABNORMAL			账户异常
//	isv.SMS_TEMPLATE_ILLEGAL		短信模板不合法
//	isv.SMS_SIGNATURE_ILLEGAL		短信签名不合法
//	isv.INVALID_PARAMETERS			参数异常
//	isp.SYSTEM_ERROR				系统错误
//	isv.MOBILE_NUMBER_ILLEGAL		非法手机号
//	isv.MOBILE_COUNT_OVER_LIMIT		手机号码数量超过限制
//	isv.TEMPLATE_MISSING_PARAMETERS	模板缺少变量
//	isv.BUSINESS_LIMIT_CONTROL		业务限流
//	isv.INVALID_JSON_PARAM			JSON参数不合法，只接受字符串值
//	isv.BLACK_KEY_CONTROL_LIMIT		黑名单管控
//	isv.PARAM_LENGTH_LIMIT			参数超出长度限制
//	isv.PARAM_NOT_SUPPORT_URL		不支持URL
//	isv.AMOUNT_NOT_ENOUGH			账户余额不足
    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAI1FSUv18CHLSa";
    static final String accessKeySecret = "qIMTR9P5B0SzeplEMACjApourxeHS8";

    public static boolean sendSms(String mobiles){
    	boolean result =true;
    	HttpSession session = UserContext.getRequest().getSession();
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e) {
			e.printStackTrace();
		}
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobiles);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("成都太美技术");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_37875094");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        String prevGetValidCodeTime = (String) (session.getAttribute("PrevValidCodeTime")==null?"":session.getAttribute("PrevValidCodeTime"));
        long currentTime = new Date().getTime();
        
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse;
		try {
			if("".equals(prevGetValidCodeTime)||Long.valueOf(prevGetValidCodeTime).longValue()-currentTime>5*60*1000){
	        	//验证码生成
	            int validnbr = (int)((Math.random()*9+1)*100000);
	            request.setTemplateParam("{validnbr:\""+validnbr+"\"}");//模板中的参数
	            sendSmsResponse = acsClient.getAcsResponse(request);
	            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
	            	session.setAttribute(mobiles,validnbr);
	            	session.setAttribute("PrevValidCodeTime", new Date().getTime()+"");
	            }else{
	            	result = false;
	            }
	        }else{
	        	String validnbr = String.valueOf(session.getAttribute(mobiles));
            	request.setTemplateParam("{validnbr:\""+validnbr+"\"}");//模板中的参数
	            sendSmsResponse = acsClient.getAcsResponse(request);
            	if(sendSmsResponse.getCode() == null || !"OK".equals(sendSmsResponse.getCode())) {
            		result = false;
            	}
	        }
		} catch (ServerException e) {
			e.printStackTrace();
			session.removeAttribute("PrevValidCodeTime");
        	session.removeAttribute(mobiles);
            result = false;
            return result;
		} catch (ClientException e) {
			e.printStackTrace();
			session.removeAttribute("PrevValidCodeTime");
        	session.removeAttribute(mobiles);
            result = false;
            return result;
		}
        return result;
    }
    
    public static boolean sendInterviewNotice(String mobiles,String name,String time) {     
    	boolean result = true;
    	//可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);//appid和appsecret
        try {
        	DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,  domain);//接口地址
        	IAcsClient client = new DefaultAcsClient(profile);
        	SendSmsRequest request = new SendSmsRequest();
            request.setSignName("成都太美技术");//签名
            request.setTemplateCode("SMS_59875052");//模板code
            request.setPhoneNumbers(mobiles);//接收手机号，多个用英文逗号隔开
        	//验证码生成
            request.setTemplateParam("{name:\""+name+"\",time:\""+time+"\"}");//模板中的参数
            SendSmsResponse response = client.getAcsResponse(request);
            if(response.getCode() == null || !"OK".equals(response.getCode())) {
        		result = false;
        	}
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
    
    public static boolean sendPriceWatching(WatchRemindDto dto) {     
    	boolean result = true;
    	//可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);//appid和appsecret
        try {
        	DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,  domain);//接口地址
        	IAcsClient client = new DefaultAcsClient(profile);
        	SendSmsRequest request = new SendSmsRequest();
            request.setSignName("成都太美技术");//签名
            request.setTemplateCode("SMS_100820046");//模板code
            request.setPhoneNumbers(dto.getUserTel());//接收手机号，多个用英文逗号隔开
        	//验证码生成
            request.setTemplateParam("{legs:\""+dto.getLegs()+"\",num:\""+dto.getNum()+"\",leg:\""+dto.getLeg()+"\",changeRate:\""+dto.getChangeRate()+"\",changePrice:\""+dto.getChangePrice()+"\"}");//模板中的参数
            SendSmsResponse response = client.getAcsResponse(request);
            if(response.getCode() == null || !"OK".equals(response.getCode())) {
        		result = false;
        	}
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


    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

    public static void main(String[] args) throws ClientException, InterruptedException {
    	
    	sendSms("15208258814");
    	
        //发短信
//        SendSmsResponse response = sendSms();
//        System.out.println("短信接口返回的数据----------------");
//        System.out.println("Code=" + response.getCode());
//        System.out.println("Message=" + response.getMessage());
//        System.out.println("RequestId=" + response.getRequestId());
//        System.out.println("BizId=" + response.getBizId());
//
//        Thread.sleep(3000L);
//
//        //查明细
//        if(response.getCode() != null && response.getCode().equals("OK")) {
//            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
//            System.out.println("短信明细查询接口返回数据----------------");
//            System.out.println("Code=" + querySendDetailsResponse.getCode());
//            System.out.println("Message=" + querySendDetailsResponse.getMessage());
//            int i = 0;
//            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
//            {
//                System.out.println("SmsSendDetailDTO["+i+"]:");
//                System.out.println("Content=" + smsSendDetailDTO.getContent());
//                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
//                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
//                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
//                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
//                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
//                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
//                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
//            }
//            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
//            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
//        }

    }
}

