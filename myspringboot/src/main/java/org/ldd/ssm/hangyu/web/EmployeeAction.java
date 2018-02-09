package org.ldd.ssm.hangyu.web;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.ValidCode;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.service.EmployeeService;
import org.ldd.ssm.hangyu.service.ResponseService;
import org.ldd.ssm.hangyu.service.ValidCodeService;
import org.ldd.ssm.hangyu.utils.AliyunNewSms;
import org.ldd.ssm.hangyu.utils.Mail;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.utils.UploadImage;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EmployeeAction {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ResponseService responseService;
	@Autowired
	private ValidCodeService validCodeService;

	Logger log = Logger.getLogger(EmployeeAction.class);

//	/**
//	 * 修改用户在平台意向金状态
//	 */
//	@RequestMapping("/changeIntentionMoneyStatusForEmployee")
//	@ResponseBody
//	public Map<String, Object> changeIntentionMoneyStatusForEmployee(Long id,
//			String status) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (id == null || id == 0 || TextUtil.isEmpty(status)) {
//			map.put("opResult", "3");
//			return map;
//		}
//
//		try {
//			Employee paEmployee = new Employee();
//			paEmployee.setId(id);
//			paEmployee.setIntentionMoney(status);
//			boolean result = employeeService
//					.updateEmployeeIntentionMoneyStatus(paEmployee);
//
//			// 修改session中的意向金状态
//			Employee emp = UserContext.getUser();
//			emp.setIntentionMoney(status);
//			UserContext.rmoveUser();
//			UserContext.setUser(emp);
//			if (result) {
//				map.put("opResult", "0");
//			} else {
//				map.put("opResult", "1");
//			}
//		} catch (Exception e) {
//			log.error("There is something wrong here");
//			e.printStackTrace();
//			map.put("opResult", "2");
//			return map;
//		}
//
//		return map;
//	}

	@RequestMapping("/loadPersonalInfo")
	@ResponseBody
	public Map<String, Object> loadPersonalInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		Employee emp = UserContext.getUser();
		if (emp != null) {
			map.put("opResult", "0");
			map.put("user", emp);
			return map;
		} else {
			map.put("opResult", "1");
			return map;
		}
	}

	/**
	 * 用户签约
	 */
	@RequestMapping("/employeeSign")
	@ResponseBody
	public synchronized Map<String, Object> employeeSign() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!"1".equals(UserContext.getUser().getWhethersign().trim())) {
			map.put("opResult", "3");
			map.put("msg", "您不是未签约用户");
			return map;
		}
		Employee paramEmployee = new Employee();
		paramEmployee.setId(UserContext.getUser().getId());
		paramEmployee.setWhethersign("2");
		try {

			boolean result = employeeService
					.updateByPrimaryKeySelective(paramEmployee);
			if (result) {
				// 修改session中的状态
				Employee sessionEmployee = UserContext.getUser();
				sessionEmployee.setWhethersign("2");
				sessionEmployee.setWhethersignStr("申请签约");
				UserContext.rmoveUser();
				UserContext.setUser(sessionEmployee);
				map.put("opResult", "0");
				map.put("msg", "申请成功");
			} else {
				map.put("opResult", "1");//签约失败
				map.put("msg", "申请失败");
				return map;
			}
		} catch (Exception e) {
			map.put("opResult", "2");//系统错误
			map.put("msg", "系统错误");
			log.error("There is something wrong here");
			e.printStackTrace();
			return map;
		}
		return map;
		
	}
	@RequestMapping("/employee_validpwd")
	@ResponseBody
	public Map<String,Object> validpwd(String pwd){
		Map<String,Object> map = new HashMap<String, Object>();
		Employee obj = UserContext.getUser();
		if(obj.getPassword().equals(pwd)){
			map.put("opResult", "0");
		}else{
			map.put("opResult", "1");
		}
		return map;
	}
	@RequestMapping("/updateEmployee")
	@ResponseBody
	public Map<String,Object> update(Employee employee){
		return employeeService.updateEmployee(employee);
	}
	
	@RequestMapping("/employee_uhead")
	@ResponseBody
	public String uploadHead(String imagePath,MultipartFile myfile,HttpServletRequest request){
		Map<String,Object> map= new HashMap<String,Object>();
        if(myfile.isEmpty()){  
             map.put("opResult", "3");  
        } else{  
             String originalFilename=myfile.getOriginalFilename();
             String sufixName = originalFilename.split("\\.")[1];
             if(!"PNG".equals(StringUtils.upperCase(sufixName))&&!"JPG".equals(StringUtils.upperCase(sufixName))&&!"JPEG".equals(StringUtils.upperCase(sufixName))){
            	 map.put("opResult", "1");
		         return String.valueOf(JSONObject.fromObject(map));
             }
//             String fileBaseName=FilenameUtils.getBaseName(originalFilename);//获取文件名
//             String floderName=fileBaseName+"_" +new Date().getTime();  
              try{  
                   String genePicPath=request.getSession().getServletContext().getRealPath("/"+"upload/");
                    //把上传的图片放到服务器的文件夹下  
		           FileUtils.copyFileToDirectory((File) myfile, new File(genePicPath,originalFilename));
		           File oldFile = new File(genePicPath+"/"+originalFilename);
		           String rootPath = UserContext.getRequest().getSession().getServletContext().getRealPath("/");
		           String newFileName = new Date().getTime()+".jpg";
//		           String curPath = rootPath+"uploadimage\\"+newFileName;
		           File file = new File("/root/upload/");
		           if(!file.isDirectory()){
		        	   file.mkdirs();
		           }
		           String curPath = "/root/upload/"+newFileName;
		           File newFile = new File(curPath);
		           boolean flag = UploadImage.zipWidthHeightImageFile(oldFile, newFile, 118, 118, 1.0f);//上传头像
		           if(flag==true){
		        	   Employee emp = UserContext.getUser();
		        	   emp.setHeadPortrait("/root/upload/"+newFileName);
		        	   boolean result = employeeService.updateHeadPath(emp);
		        	   if(result==true){
		        		   String fileName = UUID.randomUUID().toString()+".jpg";
		        		   File tempFile = new File(rootPath+"uploadimage/"+UserContext.getUser().getId()+"/");
		        		   if(!tempFile.isDirectory()){
		        			   tempFile.mkdirs();
		        		   }
		        		   boolean sign = UploadImage.copyTheFile(newFile, new File(rootPath+"uploadimage/"+UserContext.getUser().getId()+"/"+fileName));
		        		   if(sign==true){
		        			   UserContext.getUser().setHeadPortrait("/uploadimage/"+UserContext.getUser().getId()+"/"+fileName);
		        			   map.put("opResult", "0");
		        			   map.put("fileName", fileName);
		        			   map.put("id", UserContext.getUser().getId());
		        		   }else{
		        			   map.put("opResult", "1"); 
		        		   }
		        	   }else{
		        		   map.put("opResult", "1");  
		        	   }
		           }else{
		        	   map.put("opResult", "1");
		           }
             } catch (Exception e) {  
            	map.put("opResult", "2");
     			return String.valueOf(JSONObject.fromObject(map));
             }  
        }
		return String.valueOf(JSONObject.fromObject(map));
	}
	
	@RequestMapping("/getValidCode")
	@ResponseBody
	public Map<String,Object> getValidCode(String contactWay){
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtil.isEmpty(contactWay)){
			map.put("opResult", "3");
			return map;
		}
		try {
			if(contactWay.indexOf("@")>-1){
				String validCode = (int)((Math.random()*9+1)*100000)+"";
	            long creatTime = new Date().getTime();
	            Long employeeId = UserContext.getUser().getId();
	            ValidCode emailValidStr = new ValidCode();
	            emailValidStr.setCreateTime(creatTime);
	            emailValidStr.setCode(validCode);
	            emailValidStr.setContactWay(contactWay);
	            boolean result = Mail.sendMail(contactWay,validCode,employeeId);
	            if(result){
	            	result = validCodeService.add(emailValidStr);
	            	if(result){
	    				map.put("opResult", "0");
	    			}else{
	    				map.put("opResult", "1");
	    			}
	            }else{
	            	map.put("opResult", "4");
	            }
			}else{
		        //验证码生成
		        int validnbr = (int)((Math.random()*9+1)*100000);
				boolean flag = AliyunNewSms.sendSms(contactWay,validnbr+"");
				if(flag){
//					UserContext.getRequest().getSession().setAttribute("contactWay", contactWay);
					ValidCode smValidCode = new ValidCode();
					long creatTime = new Date().getTime();
		            smValidCode.setCreateTime(creatTime);
		            smValidCode.setCode(validnbr+"");
		            smValidCode.setContactWay(contactWay);
					boolean result = validCodeService.add(smValidCode);
					if(result)
						map.put("opResult", "0");
					else
						map.put("opResult", "1");
				}else{
					map.put("opResult", "4");//发送失败
				}
			}
		} catch (Exception e) {
			log.error(e);
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	public String validCode(String contactWay,String code){
		String result = "0";
		long currentTime = new Date().getTime();
		if(TextUtil.isEmpty(contactWay)||TextUtil.isEmpty(code)){
			result = "3";//短信验证码为空
			return result;
		}
		ValidCode validCode = null;
		try {
			validCode = validCodeService.load(contactWay);
		} catch (Exception e) {
			log.error("There are errors in code");
			result = "2";
			return result;
		}
		if(validCode!=null){
			if(contactWay.indexOf("@")>-1){//邮箱
				if(currentTime-validCode.getCreateTime()>10*60*1000){//10分钟过期
					result = "4";
				}else{
					if(!code.equals(validCode.getCode())){
						result = "5";
					}else{
						//将严重过的验证删除、失效
						boolean delResult = validCodeService.delete(validCode.getId());
						if(!delResult){
							result = "6";//删除验证码失败
						}
					}
				}
			}else{//手机
				if(currentTime-validCode.getCreateTime()>10*60*1000){//3分钟过期
					result = "4";
				}else{
					if(!code.equals(validCode.getCode())){//验证码错误、不匹配
						result = "5";
					}else{
						//将严重过的验证删除、失效
						boolean delResult = validCodeService.delete(validCode.getId());
						if(!delResult){
							result = "6";//删除验证码失败
						}
					}
				}
			}
		}else{
			result = "1";//没有对应的数据
		}
		return result;
	}
	
	@RequestMapping("/bindMail")
	@ResponseBody
	public Map<String,Object> bindMail(String validStr,String email){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			long currentTime = new Date().getTime();
			if(TextUtil.isEmpty(validStr)||TextUtil.isEmpty(email)){
				map.put("opResult", "3");
				return map;
			}else{
				ValidCode validCode = validCodeService.load(email);
				if(validCode==null){
					map.put("opResult", "1");//获取验证码失败
					return map;
				}else{
					long validMailTime = validCode.getCreateTime().longValue();
					if(currentTime-validMailTime>10*60*1000){//10分钟验证过期
						map.put("opResult", "4");
					}else{
						if(validCode.getCode().equals(validStr)){
							Employee emp = new Employee();
							emp.setId(UserContext.getUser().getId());
							emp.setEmail(validCode.getContactWay());
							boolean result= employeeService.updateByPrimaryKeySelective(emp);
							if(result){
								//删除验证码
								boolean delResult = validCodeService.delete(validCode.getId());
								if(delResult){
									UserContext.getUser().setEmail(validCode.getContactWay());
									map.put("opResult", "0");
								}else{
									map.put("opResult", "6");//删除验证码失败
								}
							}else{
								map.put("opResult", "1");//修改用户信息失败
							}
						}else{
							map.put("opResult", "5");//验证码不匹配
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/unbindMail")
	@ResponseBody
	public Map<String,Object> unbindMail(){
		Map<String,Object> map = new HashMap<String,Object>();
		Employee emp = UserContext.getUser();
		try {
			boolean result = employeeService.unbindMail(emp.getId());
			if(result){
				UserContext.getUser().setEmail("");
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error(e);
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/switchPhone")
	@ResponseBody
	public Map<String,Object> switchPhone(String mobile,String validCode){
		Map<String,Object> map = new HashMap<String,Object>();
		Employee user = UserContext.getUser();
		if(TextUtil.isEmpty(mobile)||TextUtil.isEmpty(validCode)||mobile.equals(user.getPhone())){
			map.put("opResult", "3");//手机号重复
			return map;
		}
		try {
			String result = validCode(mobile,validCode);
			if("0".equals(result)){
				Employee emp = new Employee();
				emp.setId(user.getId());
				emp.setPhone(mobile);
				boolean flag = employeeService.updateByPrimaryKeySelective(emp);
				if(flag){
					UserContext.getUser().setPhone(mobile);
					map.put("opResult", result);
				}else{
					map.put("opResult", "1");
				}
			}else{
				map.put("opResult",result);
			}
		} catch (Exception e) {
			log.error(e);
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/validPhone")
	@ResponseBody
	public Map<String,Object> validPhone(String phone,String validType){
		return employeeService.validPhone(phone,validType);
	}
	
	@RequestMapping("/validEmail")
	@ResponseBody
	public Map<String,Object> validEmail(String email,String validType){
		return employeeService.validEmail(email, validType);
	}
	
	@RequestMapping("/validCode")
	@ResponseBody
	public Map<String,Object> valid(String contactWay,String code){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("opResult",validCode(contactWay, code));
		return map;
	}
	
	@RequestMapping("/resetPwd")
	@ResponseBody
	public Map<String,Object> resetPwd(String contactWay,String newPwd){
		return employeeService.resetPwd(contactWay, newPwd);
	}
	
}
