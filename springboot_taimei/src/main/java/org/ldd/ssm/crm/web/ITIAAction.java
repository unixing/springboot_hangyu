package org.ldd.ssm.crm.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Fare;
import org.ldd.ssm.crm.service.IITIAService;
import org.ldd.ssm.crm.utils.AjaxResult;
import org.ldd.ssm.crm.utils.ParserDataUtils;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ITIAAction {
	@Autowired
	private IITIAService iitiaService;
	@RequestMapping("/ITIA")
	public String index(){
		return "/itia/itiaIndex";
	}
	@RequestMapping("/itiaChech")
	@ResponseBody
	public AjaxResult upload(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model, String cars) {
		if (file != null && request != null && model != null) {
			String path = request.getSession().getServletContext().getRealPath("upload");
			String fileName = file.getOriginalFilename();
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				file.transferTo(targetFile);
				List<String[]> imp = ParserDataUtils.imp(targetFile);
				if(imp==null){
					return new AjaxResult("文件验证不合法");
				}
				for (String[] strings : imp) {
					if(strings.length!=51){
						return new AjaxResult("文件表头长度验证不合法,请严格按照模型排序");
					}
				}
				Set<Fare> list = iitiaService.find(imp);
				UserContext.setITIAData("itia", list);
				//上传完毕后删除文件 LJGLZO 丽江-泸州 800 574
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
	@RequestMapping("/itiaUpdate")
	@ResponseBody
	public Set<Fare> ajaxResult(Fare fare){
		Set<Fare> itiaData = UserContext.getITIAData("itia");
		return itiaData;
	}
	@RequestMapping("/saveITIA")
	@ResponseBody
	public AjaxResult saveITIA(Fare fare){
		Map<String, Object> map = UserContext.getFareDate("save");
		if(map==null){
			map = new HashMap<String, Object>();
		}
		Fare mapFare = (Fare)map.get(fare.getVoyageCode());
		
		if(mapFare==null){
			map.put(fare.getVoyageCode(), fare);
		}else{
			mapFare.setVoyageName(fare.getVoyageName());
			mapFare.setyBFare(fare.getyBFare());
			mapFare.setSailingDistance(fare.getSailingDistance());
		}
		UserContext.setFareDate("save",map);
		return new AjaxResult("添加成功");
	}
	
	@RequestMapping("/saveFare")
	@ResponseBody
	public AjaxResult save(){
		Map<String, Object> map = UserContext.getFareDate("save");
		iitiaService.save(map);
		return new AjaxResult("添加成功");
	}
	
}
