package org.ldd.ssm.crm.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Department;
import org.ldd.ssm.crm.domain.EmailValidStr;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.FocusFlight;
import org.ldd.ssm.crm.query.HomePageQuery;
import org.ldd.ssm.crm.service.DituMapperService;
import org.ldd.ssm.crm.service.FlightRouteService;
import org.ldd.ssm.crm.service.HomePageService;
import org.ldd.ssm.crm.service.IAirLineService;
import org.ldd.ssm.crm.service.ICompanyService;
import org.ldd.ssm.crm.service.IDepartmentService;
import org.ldd.ssm.crm.service.IEmailValidStrService;
import org.ldd.ssm.crm.service.IEmployeeRoleService;
import org.ldd.ssm.crm.service.IEmployeeService;
import org.ldd.ssm.crm.service.IFocusFlightService;
import org.ldd.ssm.crm.service.IRoleMenuNewService;
import org.ldd.ssm.crm.utils.DataUtils;
import org.ldd.ssm.crm.utils.Mail;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UploadImage;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.AliyunNewSms;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.ldd.ssm.crm.web.interceptor.SendSmsAliyun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
/**
 *User的前台控制器层
 */
@Controller
public class EmployeeAction {
	Gson gson=new Gson();
	//spring注解注入对象
	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IDepartmentService departmentService;
	@Autowired
	private ICompanyService companyService;
	@Autowired
	private DituMapperService dituMapperService;
	@Autowired
	private FlightRouteService flightRouteService;
	@Autowired
	private IEmployeeRoleService employeeRoleService;
	@Autowired
	private IRoleMenuNewService roleMenuNewService;
	@Autowired
	private IAirLineService iAirLineService;
	@Autowired
	private IFocusFlightService iFocusFlightService;
	@Autowired
	private IEmailValidStrService iEmailValidStrService;
	@Autowired
	private IEmployeeRoleService iEmployeeRoleService;
	@Autowired
	private HomePageService homePageService;
	Logger log = Logger.getLogger(EmployeeAction.class);
	//springMVC请求的映射地址
	@MyMethodNote(comment="成员主页:2")
	@MethodNote(comment="成员管理:10")
	@RequestMapping("/employee_show")
	public String execut(){
		//重定向到欢迎页面,基于springMVC
		return "permission/employee";
	}
	
	
	@RequestMapping("/sendView")
	public String sendInterviewNotice(){
		return "sm/sendInterviewNotice";
	}
	
	@RequestMapping("/employee_update")
	@ResponseBody
	@MethodNote(comment="成员管理:10")
	@MyMethodNote(comment="修改成员:3")
	public String update(String usrNm,String phone,Long departmentId,String compellation,String email,String duty){
		Map<String,Object> map = new HashMap<String,Object>();
		String retStr = "";
		String hjson = "";
		//判断用户名是否为空
//		if(usrNm==null||"".equals(usrNm)){
//			map.put("opResult", "3");
//			hjson = gson.toJson(map);
//			retStr = retStr + "{ \"success\": "+hjson+ "}";
//			return retStr;
//		}
		String usrNm2 = UserContext.getUser().getUsrNm();
		//判断修改用户名和当前登录的账号用户名是否一样, 如果是, 则不查询判断是否存在用户名 
		if(!TextUtil.isEmpty(usrNm)&&!usrNm.equals(usrNm2)){
			boolean userNameIsExist=employeeService.IsEmployeeBeExist(usrNm);
			if(!userNameIsExist){
				map.put("opResult", "4");
				hjson = gson.toJson(map);
				retStr = retStr + "{ \"success\": "+hjson+ "}";
				return retStr;
			}
		}
		Employee emp = new Employee();
		emp.setUsrNm(usrNm);
		emp.setPhone(phone);
		emp.setCompellation(compellation);
		emp.setEmail(email);
		emp.setDepartmentId(departmentId);
		emp.setDuty(duty);
		Employee currEmp = UserContext.getUser();
		emp.setId(currEmp.getId());
		if(!TextUtil.isEmpty(usrNm)){
			map.put("userText",usrNm);
		}else{
			map.put("userText",phone);
		}
		try {
			boolean result = employeeService.update(emp);
			if(result){
				map.put("opResult", "0");
				emp = employeeService.get(emp.getId());
				UserContext.setUser(emp);
				UserContext.setUsertext(usrNm);
				map.put("user", emp);
			}else{
				map.put("opResult", "1");
			}
			hjson = gson.toJson(map);
			retStr = retStr + "{ \"success\": "+hjson+ "}";
		} catch (Exception e) {
			map.put("opResult", "2");
			e.printStackTrace();
			hjson = gson.toJson(map);
			retStr = retStr + "{ \"success\": "+hjson+ "}";
			return retStr;
		}
		return retStr;
	}
	
	
	@RequestMapping("/employee_valid")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public String validPwd(String pwd){
		Map<String,Object> map = new HashMap<String,Object>();
		String retStr = UserContext.getRequest().getParameter("callback");
		String hjson = "";
		if(pwd==null||"".equals(pwd)){
			map.put("opResult", "3");
			hjson = gson.toJson(map);
			retStr = retStr + "({ \"success\": "+hjson+ "})";
			return retStr;
		}
		try {
			Employee emp = UserContext.getUser();
			if(pwd.equals(emp.getUsrPwd())){
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
			hjson = gson.toJson(map);
			retStr = retStr + "({ \"success\": "+hjson+ "})";
		} catch (Exception e) {
			map.put("opResult", "2");
			hjson = gson.toJson(map);
			retStr = retStr + "({ \"success\": "+hjson+ "})";
			return retStr;
		}
		return retStr;
	}
	/**
	 *	查询操作
	 */
	@RequestMapping("/employee_search") //请求注入映射路径
	@ResponseBody //自动响应jason数据模型
	@MethodNote(comment="成员管理:10")
	@MyMethodNote(comment="查询成员:2")
	public Map<String, Object> list(Long companyId,String usrNm){
		Map<String,Object> map = new HashMap<String,Object>();
		Employee emp = UserContext.getUser();
		try {
			List<Employee> list = null;
			if(emp.getUsrSts()==99){//状态99为内置超级管理员
				list = employeeService.list(null,usrNm);//查询所有成员
			}else{
				list = employeeService.list(companyId,usrNm);
			}
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Department dep = departmentService.load(list.get(i).getDepartmentId());
					list.get(i).setDepartment(dep);
				}
			}
			map.put("opResult", "0");
			map.put("list",list);
		} catch (Exception e) {
			map.put("opResult", "1");
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 	删除操作
	 */
	@RequestMapping("/employee_delete")
	@ResponseBody
	@MethodNote(comment="成员管理:10")
	@MyMethodNote(comment="删除成员:3")
	public Map<String, Object> delete(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = employeeService.delete(id);
			if(result){
				map.put("opResult", "0");
				map.put("message", "删除成功！");
			}else{
				map.put("message", "删除失败！");
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		//重定向到欢迎页面,基于springMVC
		return map;
	}
	
	/**
	 * 	保存操作
	 */
	@RequestMapping("/employee_add")
	@ResponseBody
	@MethodNote(comment="成员管理:10")
	@MyMethodNote(comment="添加成员:2")
	public Map<String, Object> save(Employee emp){
		Map<String,Object> map = new HashMap<String,Object>();
		if(emp==null){
			map.put("opResult", "3");
			return map;
		}
		try {
//			Employee user = UserContext.getUser();
			emp.setUsrSts(1);
//			emp.setDepartmentId(user.getDepartmentId());
			boolean result = employeeService.save(emp);
			if(result){
				map.put("opResult", "0");
				map.put("message", "添加成功！");
			}else{
				map.put("message", "添加失败！");
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		//重定向到欢迎页面,基于springMVC
		return map;
	}
	
	@RequestMapping(value="/employee_load",produces = "text/html;charset=UTF-8")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public String load(){
		Map<String,Object> map = new HashMap<String,Object>();
		String retStr = UserContext.getRequest().getParameter("callback");
		String hjson = "";
		try {
			Employee obj = UserContext.getUser();
			List<Department> list = departmentService.list(Long.valueOf(UserContext.getCompanyId()), "");
			for(int i=0;i<list.size();i++){
				if(list.get(i).getId().longValue()==obj.getDepartmentId().longValue()){
					obj.setDepartment(list.get(i));
				}
			}
			if(!"".equals(obj.getBgPath())){
				String fileName = UUID.randomUUID().toString()+".jpg";
				File newFile = new File(UserContext.getRequest().getSession().getServletContext().getRealPath("/")+"uploadimage/"+obj.getId()+"/");
				if(!newFile.isDirectory()){
					newFile.mkdirs();
				}
				newFile = new File(UserContext.getRequest().getSession().getServletContext().getRealPath("/")+"uploadimage/"+obj.getId()+"/"+fileName);
				File oldFile = new File(UserContext.getUser().getBgPath());
				boolean result = UploadImage.copyTheFile(oldFile, newFile);
				if(result==true){
					obj.setBgPath("/uploadimage/"+UserContext.getUser().getId()+"/"+fileName);
					UserContext.getUser().setBgPath("/uploadimage/"+UserContext.getUser().getId()+"/"+fileName);
				}
			}
			if(!"".equals(obj.getHeadPath())){
				String fileName = UUID.randomUUID().toString()+".jpg";
				File newFile = new File(UserContext.getRequest().getSession().getServletContext().getRealPath("/")+"uploadimage/"+obj.getId()+"/");
				if(!newFile.isDirectory()){
					newFile.mkdirs();
				}
				newFile = new File(UserContext.getRequest().getSession().getServletContext().getRealPath("/")+"uploadimage/"+obj.getId()+"/"+fileName);
				File oldFile = new File(UserContext.getUser().getHeadPath());
				boolean result = UploadImage.copyTheFile(oldFile, newFile);
				if(result==true){
					obj.setBgPath("/uploadimage/"+UserContext.getUser().getId()+"/"+fileName);
					UserContext.getUser().setHeadPath("/uploadimage/"+UserContext.getUser().getId()+"/"+fileName);
				}
			}
			map.put("opResult", "0");
			map.put("obj", obj);
			map.put("list", list);
			hjson = gson.toJson(map);
			retStr = retStr + "({ \"success\": "+hjson+ "})";
		} catch (Exception e) {
			map.put("opResult", "1");
			map.put("message", e.getMessage());
			e.printStackTrace();
			hjson = gson.toJson(map);
			retStr = retStr + "({ \"success\": "+hjson+ "})";
			return retStr;
		}
		return retStr;
	}
	
	@RequestMapping("/employee_validpwd")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> validpwd(String pwd){
		Map<String,Object> map = new HashMap<String, Object>();
		Employee obj = UserContext.getUser();
		if(obj.getUsrPwd().equals(pwd)){
			map.put("opResult", "0");
		}else{
			map.put("opResult", "1");
		}
		return map;
	}
	
	@RequestMapping("/employee_upwd")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public String upwd(String usrPwd){
		Map<String,Object> map = new HashMap<String, Object>();
		String retStr = UserContext.getRequest().getParameter("callback");
		String hjson = "";
		if(usrPwd==null||"".equals(usrPwd)){
			map.put("opResult", "3");
			hjson = gson.toJson(map);
			retStr = retStr+ "({ \"success\": "+hjson+ "})";
			return retStr;
		}
		Employee currEmp = UserContext.getUser();
		if(currEmp.getUsrPwd().equals(usrPwd)){
			map.put("opResult", "4");
			hjson = gson.toJson(map);
			retStr = retStr+ "({ \"success\": "+hjson+ "})";
			return retStr;
		}
		Employee emp = new Employee();
		try {
			emp.setId(currEmp.getId());
			emp.setUsrPwd(usrPwd);
			boolean result = employeeService.update(emp);
			if(result==true){
				UserContext.getUser().setUsrPwd(usrPwd);
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
			hjson = gson.toJson(map);
			retStr = retStr+ "({ \"success\": "+hjson+ "})";
		} catch (Exception e) {
			map.put("opResult", "2");
			hjson = gson.toJson(map);
			retStr = retStr+ "({ \"success\": "+hjson+ "})";
			return retStr;
		}
		return retStr;
	}
	
	@RequestMapping("/employee_uhead")
	@MethodNote(comment="默认:16")
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
		           FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(genePicPath,originalFilename));
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
		        	   emp.setHeadPath("/root/upload/"+newFileName);
		        	   boolean result = employeeService.updateHeadPath(emp);
		        	   if(result==true){
		        		   String fileName = UUID.randomUUID().toString()+".jpg";
		        		   File tempFile = new File(rootPath+"uploadimage/"+UserContext.getUser().getId()+"/");
		        		   if(!tempFile.isDirectory()){
		        			   tempFile.mkdirs();
		        		   }
		        		   boolean sign = UploadImage.copyTheFile(newFile, new File(rootPath+"uploadimage/"+UserContext.getUser().getId()+"/"+fileName));
		        		   if(sign==true){
		        			   UserContext.getUser().setHeadPath("/uploadimage/"+UserContext.getUser().getId()+"/"+fileName);
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
	
	@RequestMapping("/employee_ubg")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public String uploadBackground(String picParams,MultipartFile myfile,HttpServletRequest request){
		Map<String,Object> map= new HashMap<String,Object>();  
        if(myfile.isEmpty()){  
             map.put( "opResult", "3");  
        } else{
             String originalFilename=myfile.getOriginalFilename();
             String[] names = originalFilename.split("\\.");
             String sufixName = names[1];
             if(!"PNG".equals(StringUtils.upperCase(sufixName))&&!"JPG".equals(StringUtils.upperCase(sufixName))&&!"JPEG".equals(StringUtils.upperCase(sufixName))){
            	 map.put("opResult", "1");
		         return String.valueOf(JSONObject.fromObject(map));
             }
//             String fileBaseName=FilenameUtils.getBaseName(originalFilename);//获取文件名
//             String floderName=fileBaseName+"_" +new Date().getTime();  
              try{  
                   String genePicPath=request.getSession().getServletContext().getRealPath("/"+"upload/");
                    //把上传的图片放到服务器的文件夹下  
		           FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(genePicPath,originalFilename));
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
		           boolean flag = UploadImage.zipWidthHeightImageFile(oldFile, newFile, 220, 150, 1.0f);//上传背景
		           if(flag==true){
		        	   Employee emp = UserContext.getUser();
		        	   emp.setBgPath("/root/upload/"+newFileName);
		        	   boolean result = employeeService.updateBackgroundPath(emp);
		        	   if(result==true){
		        		   String fileName = UUID.randomUUID().toString()+".jpg";
		        		   File tempFile = new File(rootPath+"uploadimage/"+UserContext.getUser().getId()+"/");
		        		   if(!tempFile.isDirectory()){
		        			   tempFile.mkdirs();
		        		   }
		        		   boolean sign = UploadImage.copyTheFile(newFile, new File(rootPath+"uploadimage/"+UserContext.getUser().getId()+"/"+fileName));
		        		   if(sign==true){
		        			   UserContext.getUser().setBgPath("/uploadimage/"+UserContext.getUser().getId()+"/"+fileName);
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
	@MethodNote(comment="默认:16")
	@ResponseBody
	public String getValidCode(String mobile){
		Map<String,Object> map = new HashMap<String,Object>();
		if(mobile==null||"".equals(mobile)){
			mobile = UserContext.getUser().getPhone();
			if(mobile==null||"".equals(mobile)){
				map.put("opResult", "3");
				return String.valueOf(JSONObject.fromObject(map));
			}
		}
		try {
			boolean flag = AliyunNewSms.sendSms(mobile);
			if(flag){
				UserContext.getRequest().getSession().setAttribute("getCodePhone", mobile);
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error(e);
			map.put("opResult", "2");
			return String.valueOf(JSONObject.fromObject(map));
		}
		return String.valueOf(JSONObject.fromObject(map));
	}
	@RequestMapping("/validAccount")
	@ResponseBody
	public String validAccount(String mobile,String smCode){
		String result = "0";
		long currentTime = new Date().getTime();
		HttpSession session = UserContext.getRequest().getSession();
		String prevVlidCodeTime = (String) (session.getAttribute("PrevValidCodeTime")==null?"":session.getAttribute("PrevValidCodeTime"));
		if("".equals(prevVlidCodeTime)){
			result = "3";//未生产短信验证码
			return result;
		}
		long prevGetSmCodeTime = Long.valueOf(prevVlidCodeTime);
		if(currentTime-prevGetSmCodeTime<=5*60*1000){
			String currentValidCode = String.valueOf(session.getAttribute(mobile));
			String getCodePhone = String.valueOf(session.getAttribute("getCodePhone"));
			if(!mobile.equals(getCodePhone)||!smCode.equals(currentValidCode)){
				result = "1";//验证码不匹配
				return result;
			}else{
				//清空对应时间和验证码
				session.removeAttribute("PrevValidCodeTime");
				session.removeAttribute(mobile);
			}
		}else{
			return "1";//验证码过期
		}
		return result;
	}
	
	public String validSMCode(String mobile,String smCode){
		String result = "0";
		long currentTime = new Date().getTime();
		HttpSession session = UserContext.getRequest().getSession();
		String prevVlidCodeTime = (String) (session.getAttribute("PrevValidCodeTime")==null?"":session.getAttribute("PrevValidCodeTime"));
		if("".equals(prevVlidCodeTime)){
			result = "3";//未生产短信验证码
			return result;
		}
		long prevGetSmCodeTime = Long.valueOf(prevVlidCodeTime);
		if(currentTime-prevGetSmCodeTime<=5*60*1000){
			String currentValidCode = String.valueOf(session.getAttribute(mobile));
			String getCodePhone = String.valueOf(session.getAttribute("getCodePhone"));
			if(!mobile.equals(getCodePhone)||!smCode.equals(currentValidCode)){
				result = "1";//验证码不匹配
				return result;
			}else{
				//清空对应时间和验证码
				session.removeAttribute("PrevValidCodeTime");
				session.removeAttribute(mobile);
			}
		}else{
			return "1";//验证码过期
		}
		return result;
	}
	
	@RequestMapping("/validMail")
	@MethodNote(comment="默认:16")
//	@ResponseBody
	public String validMailBind(String validStr,Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			long currentTime = new Date().getTime();
			EmailValidStr emailValidStr = iEmailValidStrService.load(id);
			if(emailValidStr==null){
//				map.put("opResult", "3");//验证过期
				return "newHtml/fail";
			}else{
				long validMailTime = emailValidStr.getCreateTime().longValue();
				if(currentTime-validMailTime>30*60*1000){//30分钟验证过期
//					map.put("opResult", "3");//验证过期
					return "newHtml/fail";
				}else{
					Employee emp = new Employee();
					emp.setId(UserContext.getUser().getId());
					emp.setEmail(emailValidStr.getEmail());
					boolean result= employeeService.update(emp);
					if(result){
						UserContext.getUser().setEmail(emailValidStr.getEmail());
//						map.put("opResult", "0");
						return "newHtml/success";
					}else{
//						map.put("opResult", "1");//修改失败
						return "newHtml/fail";
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
			map.put("opResult", "2");
//			return String.valueOf(JSONObject.fromObject(map));
			return "newHtml/fail";
		}
//		return String.valueOf(JSONObject.fromObject(map));
	}
	
	@RequestMapping("/unbindMail")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public String unbindMail(){
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
			return String.valueOf(JSONObject.fromObject(map));
		}
		return String.valueOf(JSONObject.fromObject(map));
	}
	
	@RequestMapping("/bindMail")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public String bindMail(String email){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//设置邮箱验证标识
            String validStr = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            long validMailTime = new Date().getTime();
            Long employeeId = UserContext.getUser().getId();
            EmailValidStr emailValidStr = new EmailValidStr();
            emailValidStr.setCreateTime(validMailTime);
            emailValidStr.setEmployeeId(employeeId);
            emailValidStr.setValidStr(validStr);
            emailValidStr.setEmail(email);
            boolean result = iEmailValidStrService.addOrUpdate(emailValidStr);
            if(result){
            	result = Mail.sendMail(email,validStr,employeeId);
    			if(result){
    				map.put("opResult", "0");
    			}else{
    				map.put("opResult", "1");
    			}
            }else{
            	map.put("opResult", "1");
            }
		} catch (Exception e) {
			log.error(e);
			map.put("opResult", "2");
			return String.valueOf(JSONObject.fromObject(map));
		}
		return String.valueOf(JSONObject.fromObject(map));
	}
	
	@RequestMapping("/switchPhone")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public String switchPhone(String mobile,String validCode){
		Map<String,Object> map = new HashMap<String,Object>();
		Employee user = UserContext.getUser();
		if(mobile==null||mobile.length()<=0||validCode==null||validCode.length()<=0||mobile.equals(user.getPhone())){
			map.put("opResult", "3");
			return String.valueOf(JSONObject.fromObject(map));
		}
		try {
			String result = validSMCode(mobile,validCode);
			if("0".equals(result)){
				Employee emp = new Employee();
				emp.setId(user.getId());
				emp.setPhone(mobile);
				boolean flag = employeeService.update(emp);
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
			return String.valueOf(JSONObject.fromObject(map));
		}
		return String.valueOf(JSONObject.fromObject(map));
	}
	
	
	@RequestMapping(value="/getCurrentSeasonFocusFlts",produces = "text/html;charset=UTF-8")
	@MethodNote(comment="关注航班配置:9")
	@ResponseBody
	public String getCurrentSeasonFocusFlts(){
		Employee emp = UserContext.getUser();
		Map<String,Object> map= new HashMap<String,Object>();
		try {
			//获取最新有数据航季-start
			//初始化查询对象
			HomePageQuery homePageQuery = new HomePageQuery();
			String airportName = UserContext.getCompanyName();
			String airportCode = UserContext.getcompanyItia();
			if("z_airdata".equals(airportCode)){
				airportName = "海口";
			}
			if(TextUtil.isEmpty(airportCode)){
				airportName = "十堰";
			}
//			homePageQuery.setAirPort(airportName);
			homePageQuery.setAirPort(airportCode);
			String newDate = homePageService.getNewDate(homePageQuery);
			String[] selectDate = DataUtils.getCurrentSeasonTimes(newDate);
			Map<String,Object> allMap = iAirLineService.getCurrentFltData(selectDate[0], selectDate[1], UserContext.getcompanyItia());
			boolean focusFlag = true;//是否所有航班都被关注
			if(allMap==null){
				map.put("opResult", "1");
			}else{
				Map<String,Object> focusMap = iFocusFlightService.getFocusFlight(emp.getId());
				Set<String> keySet = allMap.keySet();
				Iterator<String> it = keySet.iterator();
				if(it.hasNext()){
					while (it.hasNext()) {
						String key = it.next();
						Set<String> flt= (Set<String>) allMap.get(key);
						List<String> focusFlt = (List<String>)focusMap.get(key);
						List<FocusFlight> flts = new ArrayList<FocusFlight>();
						Iterator<String> allFlts = flt.iterator();
						while (allFlts.hasNext()) {
							boolean flag = false;
							String currFlt = allFlts.next();
							if(focusFlt!=null){
								for(int j=0;j<focusFlt.size();j++){
									if(focusFlt.get(j).equals(currFlt)){
										flag = true;
										flts.add(new FocusFlight(currFlt,1));//关注
									}
								}
							}
							if(!flag){
								flts.add(new FocusFlight(currFlt,0));//未关注
								focusFlag = false;
							}
						}
//						for(int i=0;i<flt.size();i++){
//							boolean flag = false;
//							for(int j=0;j<focusFlt.size();j++){
//								if(focusFlt.get(j).equals(flt.get(i))){
//									flag = true;
//									flts.add(new FocusFlight(flt.get(i),1));//关注
//								}
//							}
//							if(!flag){
//								flts.add(new FocusFlight(flt.get(i),0));//未关注
//							}
//						}
						allMap.put(key, flts);//替换原有列表
					}
				}else{
					focusFlag = false;
					keySet = allMap.keySet();
					it = keySet.iterator();
					while (it.hasNext()) {
						String key = it.next();
						Set<String> flt= (Set<String>) allMap.get(key);
						List<FocusFlight> flts = new ArrayList<FocusFlight>();
						Iterator<String> its = flt.iterator();
						while(its.hasNext()){
							flts.add(new FocusFlight(its.next(),0));//未关注
						}
						allMap.put(key, flts);//替换原有列表
					}
				}
				map.put("map", allMap);
				map.put("opResult", 0);
				map.put("focusFlag", focusFlag);
			}
		} catch (Exception e) {
			log.error(e);
			map.put("opResult", "2");
			return String.valueOf(JSONObject.fromObject(map));
		}
		
		String result =String.valueOf(JSONObject.fromObject(map));
		return result;
	}
	
	@RequestMapping("/updateFocusFltList")
	@MethodNote(comment="关注航班配置:9")
	@ResponseBody
	public String updataFocusFltList(String fltStr){
		Map<String,Object> map = new HashMap<String, Object>();
		String retStr = "";
		String hjson = "";
		if(fltStr==null){
			map.put("opResult", "3");
			hjson = gson.toJson(map);
			retStr = retStr+ "{ \"success\": "+hjson+ "}";
			return retStr;
		}
		try {
			//当前端所有的航班都取消关注
			if("".equals(fltStr)){
				iFocusFlightService.update(null, UserContext.getUser().getId());
			}
			//拼装数据
			String[] groupData = fltStr.split(";");
			List<FocusFlight> list = new ArrayList<FocusFlight>();
			if(groupData!=null&&groupData.length>0){
				for(int i=0;i<groupData.length;i++){
					String[] team = groupData[i].split(":");
					String[] citys = team[0].split("=");
					FocusFlight fft = new FocusFlight();
					if(citys.length==2){
						fft.setDptAirport(citys[0]);
						fft.setArrvAirport(citys[1]);
						fft.setEmployeeId(UserContext.getUser().getId());
						fft.setFlightNumber(team.length==1?"":team[1]);
						list.add(fft);
					}else if(citys.length==3){
						fft.setDptAirport(citys[0]);
						fft.setPasAirport(citys[1]);
						fft.setArrvAirport(citys[2]);
						fft.setEmployeeId(UserContext.getUser().getId());
						fft.setFlightNumber(team.length==1?"":team[1]);
						list.add(fft);
					}
				}
			}
			Map<String,Object> fltMap = iFocusFlightService.getFocusFlight(UserContext.getUser().getId());
			Set<String> setKey = fltMap.keySet();
			Iterator<String> it = setKey.iterator();
			boolean result = false;
			if(it.hasNext()){//原有数据，更新
				result = iFocusFlightService.update(list, UserContext.getUser().getId());
			}else{//原无数据，添加
				result = iFocusFlightService.save(list);
			}
			if(result){
				map.put("opResult", "0");
				hjson = gson.toJson(map);
				retStr = retStr+ "{ \"success\": "+hjson+ "}";
			}else{
				map.put("opResult", "1");
				hjson = gson.toJson(map);
				retStr = retStr+ "{ \"success\": "+hjson+ "}";
			}
		} catch (Exception e) {
			log.error(e);
			map.put("opResult", "2");
			hjson = gson.toJson(map);
			retStr = retStr+ "{ \"success\": "+hjson+ "}";
			return retStr;
		}
		return retStr;
	}
	
	@MethodNote(comment="成员管理:10")
	@RequestMapping("/getEmployees")
	@ResponseBody
	public Map<String,Object> getEmployees(String name){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<Employee> list = iEmployeeRoleService.getCurrAirportEmployees(UserContext.getUser().getId(),name);
			map.put("opResult", "0");
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/sendInterviewNotice")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> sendInterviewNotice(String phone,String name,String time){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if(phone==null||"".equals(phone)||name==null||"".equals(name)||time==null||"".equals(time)){
				map.put("opResult", "3");
				return map;
			}
			boolean result = AliyunNewSms.sendInterviewNotice(phone, name, time);
			if(result){
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
//	public static void main(String[] args) {
//		//创建Map集合，并添加元素  
//        Map<Integer,String> map = new HashMap<Integer,String>();  
////        map.put(2,"zhangsan");  
////        map.put(6,"lisi");  
////        map.put(3,"wangwu");  
////        map.put(4,"heihei");  
////        map.put(5,"xixi");  
//        //获取map集合中的所有键的Set集合  
//        Set<Integer> keySet = map.keySet();  
//        //有了Set集合就可以获取其迭代器，取值  
//        Iterator<Integer> it = keySet.iterator();  
//        while (it.hasNext())  
//        {  
//            Integer i = it.next();  
//            String s = map.get(i);  
//        }
//	}
}
