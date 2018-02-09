package org.ldd.ssm.crm.web;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.ldd.ssm.crm.domain.UserCommitInfo;
import org.ldd.ssm.crm.query.UserCommit;
import org.ldd.ssm.crm.service.IUserCommitService;
import org.ldd.ssm.crm.utils.UploadImage;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 用户反馈
 * @author wxm
 *
 * @date 2017-5-11
 */
@RequestMapping("/userCommit")
@Controller
public class UserCommitAction {
	@Resource
	private IUserCommitService userCommitService;
	
	
	@RequestMapping("/index")
	public String index(){
		return "/index/test1";
	}
	/**
	 * 
	 * @param userCommit
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String,Object> userCommitAdd(UserCommit userCommit,@RequestParam(value = "files", required = false)MultipartFile[] files,HttpServletRequest request){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			List<byte[]> userImgs=new ArrayList<>();
			if(files.length>0){
				for(int i=0;i<files.length;i++){
					if(files[i].getSize()>0){
						MultipartFile file=files[i];
						//原文件
						String genePicPath=request.getSession().getServletContext().getRealPath("/"+"images/userCommit");
						File file2 = new File(genePicPath);
						if(!file2.exists()){
							file2.mkdirs();
						}
			            //把上传的图片放到服务器的文件夹下  
				        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(genePicPath,file.getOriginalFilename()));
				        File oldFile = new File(genePicPath+"/"+file.getOriginalFilename());
						//获取
						String rootPath = UserContext.getRequest().getSession().getServletContext().getRealPath("/")+"images/userCommit";
						long time=System.currentTimeMillis();
						String lastName=file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length-1];
						File newFile= new File(rootPath+"/"+time+"."+lastName);
	 					UploadImage.zipImageFile(oldFile,newFile,875,425,0.8f);
						userImgs.add(getFileToByte(newFile));
						deleteDirectory(new File(genePicPath));
					}
				}
				}
				userCommit.setUserImgs(userImgs);
				boolean succ=userCommitService.userCommitAdd(userCommit);
				if(succ){
					map.put("msg","添加用户反馈成功");
	            	map.put("success", true);
	            	return map;
				}else{
					map.put("msg","添加用户反馈失败");
	            	map.put("success",false);
	            	return map;
				}

		} catch (IOException e) {
			e.printStackTrace();
			
		}catch(Exception exc){
			exc.printStackTrace();
		}
		return map;
	}
	
	 private static void deleteDirectory(File file) {  
	        if (file.isFile()) {// 表示该文件不是文件夹  
	            file.delete();  
	        } else {  
	            // 首先得到当前的路径  
	            String[] childFilePaths = file.list();  
	            for (String childFilePath : childFilePaths) {  
	                File childFile = new File(file.getAbsolutePath() + "/" + childFilePath);  
	                deleteDirectory(childFile);  
	            }  
	            file.delete();  
	        }  
	    } 
	 //将文件转为二进制
	public static byte[] getFileToByte(File file) {
        byte[] by = new byte[(int) file.length()];
        InputStream is=null;
        ByteArrayOutputStream bytestream = null;
        try {
            is = new FileInputStream(file);
            bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	try {
				bytestream.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return by;
    }

	
	/**
	 * 
	 * @param employeeId 当前登录的id
	 * @return
	 */
	@RequestMapping("/findUserCommitList")
	@ResponseBody
	public Map<String,Object> findUserCommitList(){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			Long employeeId=UserContext.getUser().getId();
			if(employeeId==null){
				map.put("success",false);
				map.put("data",null);
				map.put("msg","获取当前用户id失败");
			}else{
				List<UserCommit> data=userCommitService.findUserCommitList(employeeId);
				map.put("success",true);
				map.put("data",data);
				map.put("msg","返回反馈列表成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 
	 * @param userCommitInfoId
	 * @return
	 */
	@RequestMapping("/findUserCommitDetail")
	@ResponseBody
	public Map<String,Object> findUserCommitDetail(Long userCommitInfoId){
		Map<String,Object> map=new HashMap<String, Object>();
		//userCommitInfoId=1l;
		try {
			if(userCommitInfoId==null){
				map.put("success",false);
				map.put("data",null);
				map.put("msg","传入参数为空");
			}else{
				List<UserCommit> data=userCommitService.findUserCommitDetail(userCommitInfoId);
				map.put("success",true);
				map.put("data",data);
				map.put("msg","返回查看详情成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/updateState")
	@ResponseBody
	public Map<String,Object> updateState(UserCommitInfo record){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			if(null==record.getId()||null==record.getState()){
				map.put("success",false);
				map.put("data",null);
				map.put("msg","传入参数为空");
			}else{
				boolean succ=false;
				succ=userCommitService.updateState(record);
				if(succ){
					map.put("success",true);
					map.put("msg","修改反馈状态成功");
				}else{
					map.put("success",true);
					map.put("msg","修改反馈状态失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/exportDocument")
	@ResponseBody
	public void exportDocument(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {	
		XWPFDocument xdoc = null;
		FileInputStream is = null;
		OutputStream out=null; 
		try {
			String wordName="数聚空港2.0使用手册.docx";
	        wordName = new String(wordName.getBytes(), "iso8859-1");
	        File file = new File("/root/usersGuide.docx"); 
	        response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ wordName);
            is = new FileInputStream(file);
            out=response.getOutputStream();//获得一个output对象
            xdoc = new XWPFDocument(is);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally{
	        	try {
	        		xdoc.write(out);//Write out this document to an Outputstream.
	        		is.close();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	}
	
}
