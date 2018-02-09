package org.ldd.ssm.crm.web;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.ApplyMeasureHistory;
import org.ldd.ssm.crm.domain.MeasureResult;
import org.ldd.ssm.crm.query.AirCompanyInfoQuery;
import org.ldd.ssm.crm.query.AirportInfoData;
import org.ldd.ssm.crm.query.CostAnalyze;
import org.ldd.ssm.crm.query.LegProfitFocastQuery;
import org.ldd.ssm.crm.service.AirlineMeasureService;
import org.ldd.ssm.crm.utils.AirportCompareExportExcel;
import org.ldd.ssm.crm.utils.UserContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AirlineMeasureAction {
	
	@Resource
	private AirlineMeasureService airlineMeasureService;
	
	//机场对比 传入机场三字码 集合
	@RequestMapping("/airportComparison")
	@MyMethodNote(comment="开航机场信息对比:2")
	@ResponseBody
	public Map<String,Object> airportComparison(String iTATList){
		Map<String,Object> map=new HashMap<String, Object>();
		//iTATList="AKA,LYA,AYN";
		try {
			if(!iTATList.isEmpty()){
				String[] ArrayStr=iTATList.split(",");
				List<String> iATAs=Arrays.asList(ArrayStr);
				List<AirportInfoData> list=airlineMeasureService.findAirportCompareInfo(iATAs);
				if(!list.isEmpty()){
					map.put("success",true);
					map.put("data",list);
				}else{
					map.put("success",false);
					map.put("data",list);
				}
			}else{
				map.put("success",false);
				map.put("data",null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	//机场对比导出
	@RequestMapping("/airportComparisonExport")
	@MyMethodNote(comment="开航机场信息对比导出:2")
	@ResponseBody
	public ModelAndView airportComparisonExport(String iTATList,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		//iTATList="WDS,LHW,CAN";
		try {
			if(!iTATList.isEmpty()){
				String[] ArrayStr=iTATList.split(",");
				List<String> iATAs=Arrays.asList(ArrayStr);
				List<AirportInfoData> list=airlineMeasureService.findAirportCompareInfo(iATAs);
				Map<String,Object> map = new HashMap<String, Object>();
				Map<String, List<AirportInfoData>> model = new HashMap<String,List<AirportInfoData>>();
				model.put("list", list);
				return new ModelAndView(new AirportCompareExportExcel(map), model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//测算  传入起 终 经停点 开始 结束 时间
	/**
	 * 若传入开航起止时间，计算出上一年同时间段时间进行查询
	 * 若不传开航起止时间，则计算当前时间往前推一年的时间段作为参数
	 * 传入左边所有参数  航线 航距 速度 每小时成本  机场（通过航线自己找） 航司 
	 * @param dto
	 */
	@RequestMapping("/costCalculation")
	@MyMethodNote(comment="开航成本测算查询:2")
	@ResponseBody
	public Map<String,Object> costCalculation(LegProfitFocastQuery dto){
	/*	dto.setDistance(200);
		dto.setHourCost(5);
		dto.setSpeed(40);
		String s="CTU-WDS-SHA";
		dto.setFltRteCd(s);*/
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if(!dto.getFltRteCd().isEmpty()){
				String arraystr[]=dto.getFltRteCd().split("-");
				dto.setAirlineList(Arrays.asList(arraystr));
				CostAnalyze data=airlineMeasureService.findAirports(dto);
					map.put("success",true);
					map.put("data",data);
			}else{
				map.put("success",false);
				map.put("data",null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//申请测算 
	//插入申请测算历史表  航线 开航时间 停航时间  航线 航距 速度 每小时成本  机场（通过航线自己找） 航司
	@RequestMapping("/applyCalculation")
	@MyMethodNote(comment="申请收益预估:3")
	@ResponseBody
	public Map<String,Object> applyCalculation(ApplyMeasureHistory dto){
		Map<String,Object> map=new HashMap<>();
		try {
			dto.setFltRteCd(dto.getFltRteCd().replace("-",""));
			//用户id
			dto.setUserId(UserContext.getUser().getId());
			//判断是否有预估结果 航线 开航时间 停航时间 user_id相同
			boolean isExist=airlineMeasureService.isExist(dto);
			//若存在 提示用户
			if(isExist){
				map.put("success",false);
				map.put("isExist",true);
				map.put("msg","存在已测数据");
				return map;
			}
			boolean applyIsExist=airlineMeasureService.applyIsExist(dto);
			if(applyIsExist){
				map.put("success",false);
				map.put("isExist",true);
				map.put("msg","该申请已存在");
				return map;
			}
			//若不存在 继续申请测算
			//申请测算时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			dto.setApplyMeasureTime(sdf.format(date));
			dto.setState("1");//状态为测算中
			boolean succ=false;
			succ=airlineMeasureService.addApplyMeasure(dto);
			if(succ){
				map.put("success",true);
				map.put("isExist",false);
				map.put("msg","申请测算成功");
			}else{
				map.put("success",false);
				map.put("isExist",false);
				map.put("msg","申请测算失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 存在 并继续测算
	 * @param dto
	 * @return
	 */
	@RequestMapping("/goOnApplyCalculation")
	@MyMethodNote(comment="申请收益预估:3")
	@ResponseBody
	public Map<String,Object> goONApplyCalculation(ApplyMeasureHistory dto){
		Map<String,Object> map=new HashMap<>();
		try {
			//开始传入的航线是A-B-C
			dto.setFltRteCd(dto.getFltRteCd().replace("-",""));
			//用户id
			dto.setUserId(UserContext.getUser().getId());
			boolean applyIsExist=airlineMeasureService.applyIsExist(dto);
			if(applyIsExist){
				map.put("success",false);
				map.put("isExist",true);
				map.put("msg","该申请已存在");
				return map;
			}
			//申请测算时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			dto.setApplyMeasureTime(sdf.format(date));
			boolean succ=false;
			succ=airlineMeasureService.addApplyMeasure(dto);
			if(succ){
				map.put("success",true);
				map.put("isExist",false);
				map.put("msg","申请测算成功");
			}else{
				map.put("success",false);
				map.put("isExist",false);
				map.put("msg","申请测算失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 查看已有结果
	 * @param dto
	 * @return
	 */
	@RequestMapping("/applyCalculationCheckDetail")
	@MyMethodNote(comment="查看航线预估结果:2")
	@ResponseBody
	public Map<String,Object> applyCalculationCheckDetail(ApplyMeasureHistory dto){
		Map<String,Object> map=new HashMap<>();
		try {
			//用户id
			dto.setFltRteCd(dto.getFltRteCd().replace("-",""));
			dto.setUserId(UserContext.getUser().getId());
			List<MeasureResult> list=airlineMeasureService.getMeasureResult(dto);
			map.put("success",true);
			map.put("data",list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 测算申请历史列表
	 * @return
	 */
	@RequestMapping("/findMeasureHistoryList")
	@MyMethodNote(comment="开航测算申请历史列表查询:2")
	@ResponseBody
	public Map<String,Object> findMeasureHistoryList(){
		Map<String,Object> map=new HashMap<>();
		Long userId=UserContext.getUser().getId();
		try {
			List<ApplyMeasureHistory> list=airlineMeasureService.findApplyMeasureList(userId);
			map.put("success",true);
			map.put("data",list);
			return map;
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return map;
	}
	/**
	 * 查看详情测算结果 申请历史表id
	 * 参数航线 航距 速度 每小时成本  机场（通过航线自己找） 航司 
	 * @return
	 */
	@RequestMapping("/getMeasureResultDetail")
	@MyMethodNote(comment="开航测算详情查看:2")
	@ResponseBody
	public Map<String,Object> getMeasureResultDetail(Long id){
		Map<String,Object> map=new HashMap<>();
		try {
			if(id!=null&&id!=0){
				CostAnalyze data=airlineMeasureService.getApplyMeasureDetail(id);
				map.put("success",true);
				map.put("data",data);
				map.put("msg","查询成功");
			}else{
				map.put("success",false);
				map.put("data",null);
				map.put("msg","传入参数为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获得传入机场都能飞的所有机型（暂时不用）
	 * @param iTATList
	 * @return
	 */
	@RequestMapping("/getModelCanHandle")
	@ResponseBody
	public Map<String,Object> getModelCanHandle(String iTATList){
		Map<String,Object> map=new HashMap<>();
		if(!iTATList.isEmpty()){
			//iTATList="WDS,LHW,CAN";
			String[] ArrayStr=iTATList.split(",");
			List<String> iATAs=Arrays.asList(ArrayStr);
			List<String> data=airlineMeasureService.getModelCanHandle(iATAs);
			map.put("success",true);
			map.put("data",data);
		}else{
			map.put("success",false);
			map.put("data",null);
		}
		return map;
	}
	
	//成本测算导出
	//TODO
	public void costCalculationExport(){
		
	}
	
	//删除
	@RequestMapping("/updateState")
	@MyMethodNote(comment="删除开航测算申请表:3")
	@ResponseBody
	public Map<String,Object> updateStateById(ApplyMeasureHistory dto){
		Map<String,Object> map=new HashMap<>();
		try {
			if(dto.getId()!=null&&dto.getState()!=null){
				boolean succ=airlineMeasureService.updateStateById(dto);
				if(succ){
					map.put("success",true);
					map.put("msg","删除成功");
				}else{
					map.put("success",false);
					map.put("data","删除失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//航司列表
	@RequestMapping("/getAllAircompany")
	@ResponseBody
	public List<AirCompanyInfoQuery> getAllAircompany(String airType){
		try {
			return airlineMeasureService.getAllAirCompany(airType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//机型列表
	@RequestMapping("/getAllAirType")
	@ResponseBody
	public List<String> getAllAirType(Long aircompanyId){
		try {
			return airlineMeasureService.getAllAirType(aircompanyId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
