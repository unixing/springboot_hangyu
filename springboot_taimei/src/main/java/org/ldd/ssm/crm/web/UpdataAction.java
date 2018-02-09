package org.ldd.ssm.crm.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.Fare;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.service.IUpdataService;
import org.ldd.ssm.crm.utils.AjaxResult;
import org.ldd.ssm.crm.utils.DataUtils;
import org.ldd.ssm.crm.utils.ParserDataUtils;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.utils.Z_AirdataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 权限的控制层
 */
@Controller
public class UpdataAction {
	@Autowired
	private IUpdataService updataService;
	// 定位到页面
	@RequestMapping("/updata")
	@MyMethodNote(comment="数据上传主页:2")
	public String index() {
		UserContext.rmoveUser("airdatas");
		UserContext.rmoveUser("airdatas2");
		return "/updata/externalData";
	}

	/**
	 * execl文件上传控制器
	 * 
	 * @param file
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/externalDataInsert")
	@ResponseBody
	@MyMethodNote(comment="数据上传:2")
	public AjaxResult upload(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model, String cars) {
		if (file != null && request != null && model != null) {
			String path = request.getSession().getServletContext()
					.getRealPath("upload");
			String fileName = file.getOriginalFilename();
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存
			try {
				file.transferTo(targetFile);
				//取到Execl文件数据
				List<String[]> imp = ParserDataUtils.imp(targetFile);
				if(imp==null){
					return new AjaxResult("文件验证不合法");
				}
				for (String[] strings : imp) {
					if(strings.length!=51){
						return new AjaxResult("文件表头长度验证不合法,请严格按照模型排序");
					}
				}
				String itia = updataService.getCompany(cars);
				List<List<String[]>> saveExecl = Z_AirdataUtils.saveExecl(imp);
				Set<String> checkItia = updataService.checkItia(saveExecl);//没有YB和航距的
				if(checkItia!=null&&checkItia.size()>0){
					return new AjaxResult(checkItia);
				}
				List<Z_Airdata> airdatas = Z_AirdataUtils.getWrongData(saveExecl.get(0),itia);
				UserContext.setData("airdatas", airdatas);
				UserContext.setData("newData", new ArrayList<Z_Airdata>());
				//异常数据
				List<Z_Airdata> airdatas2 = Z_AirdataUtils.getWrongData(saveExecl.get(1),itia);
				UserContext.setData("airdatas2", airdatas2);
				//上传完毕后删除文件
				targetFile.delete();
			} catch (Exception e) {
				targetFile.delete();
				if("Unable to recognize OLE stream".equals(e.getMessage())){
					return new AjaxResult("传入文件版本过高,请另存为97-2003版");
				}
				e.printStackTrace();
			}
		}
		return new AjaxResult("验证完成");
	}
	/**
	 * 这里就是将有异常的数据显示到前台
	 * @return
	 */
	@RequestMapping("/externalData")
	@ResponseBody
	public List<Z_Airdata> externalData()	{
		List<Z_Airdata> data = UserContext.getData("airdatas2");
		
		return data;
	}
	@RequestMapping("/upFile_company_list")
	@ResponseBody
	public List<String> upFile_company_list()	{
		Employee user = UserContext.getUser();
		if(user.getUsrSts()==99){
			return updataService.getUpFile_company_list(null);
		}else{
			return updataService.getUpFile_company_list(UserContext.getCompanyId());
		}
	}
	/**
	 * 这里就是将好的数据给保存下来
	 * @return
	 */
	@RequestMapping("/externalSave")
	@ResponseBody
	@MyMethodNote(comment="数据上传保存:3")
	public List<Z_Airdata> externalSave(String company_name,String dta_Sce)	{
		try {
			List<Z_Airdata> data = UserContext.getData("airdatas");
			List<Z_Airdata> data2 = UserContext.getData("newData");
			for (Z_Airdata z_Airdata : data2) {
				data.add(z_Airdata);
			}
			List<Z_Airdata> saveFileData = updataService.saveFileData(data,dta_Sce);
			
			List<Z_Airdata> z_Airdatas = updataService.savaz_AirDate(saveFileData);
			UserContext.rmoveUser("newData");
			UserContext.rmoveUser("airdatas");
			UserContext.rmoveUser("airdatas2");
			return z_Airdatas;
		} catch (Exception e) {
			return null;
		}
		
	}
	/**
	 * 删除作用域中的数据
	 * @return
	 */
	@RequestMapping("/externalCancel")
	public String externalCancel()	{
		UserContext.rmoveUser("newData");
		UserContext.rmoveUser("airdatas");
		UserContext.rmoveUser("airdatas2");
		return "/updata/externalData";
	}
	/**
	 * 取出作用域中正常的数据,添加修改的数据后,再次保存如作用域
	 * @return
	 */
	@RequestMapping("/addCheckData")
	@MyMethodNote(comment="完善异常数据:3")
	public String addCheckData(Z_Airdata strJson)	{
		//取出作用域中的一个空缓存作用于每次人工修改的数据存放处
		List<Z_Airdata> newData = UserContext.getData("newData");
		//循环,每次都从空缓存中的第一个的id标示开始从正常数据缓存中开始取出,一旦匹配则替换,若不匹配则add
		if(newData.size()!=0){
			for (int i = 0; i < newData.size(); i++) {
				if(DataUtils.isSameDate(newData.get(i).getLcl_Dpt_Day(), strJson.getLcl_Dpt_Day())){
					newData.set(i, strJson);
					UserContext.setData("newData", newData);
					return "/updata/externalData";
				}
			}
			newData.add(strJson);
		}else{
			newData.add(strJson);
		}
		UserContext.setData("newData", newData);
		return "/updata/externalData";
	}
	@RequestMapping("/externalYBsave")
	@ResponseBody
	public AjaxResult externalYBsave(Fare strJson)	{
		updataService.externalYBsave(strJson);
		return new AjaxResult("保存成功");
	}
}
