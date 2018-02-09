package org.ldd.ssm.crm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.Company;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.FlightInfo;
import org.ldd.ssm.crm.domain.FlightRoute;
import org.ldd.ssm.crm.service.FlightInfoService;
import org.ldd.ssm.crm.service.FlightRouteService;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FlightInfoAction {
	@Autowired
	private FlightInfoService flightInfoService;
	@Autowired
	private FlightRouteService flightRouteService;
	Logger log = Logger.getLogger(FlightInfoAction.class);
	
	@RequestMapping("/flightinfo_show")
//	@MethodNote(comment="航线航班管理:15")
	public String display(){
		return "flight/flightinfo";
	}
	
	@RequestMapping("/flightinfo_add")
//	@MethodNote(comment="航线航班管理:15")
	@ResponseBody
	public Map<String,Object> add(FlightInfo info){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//设置航线状态
			info.setStatus(1);
			Company cpy = UserContext.getCompany();
			info.setItia(cpy.getCpyItia());
			boolean result = flightInfoService.add(info);
			if(result){
				map.put("message", "添加成功");
				map.put("opResult", "0");
			}else{
				map.put("message", "添加失败");
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
			log.error(e);
		}
		return map;
	}
	
	@RequestMapping("/flightinfo_update")
//	@MethodNote(comment="航线航班管理:15")
	@ResponseBody
	public Map<String,Object> update(FlightInfo info){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = flightInfoService.update(info);
			if(result){
				map.put("message", "修改成功");
				map.put("opResult", "0");
			}else{
				map.put("message", "修改失败");
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
			log.error(e);
		}
		return map;
	}
	
	@RequestMapping("/flightinfo_groundOrGoAround")
//	@MethodNote(comment="航线航班管理:15")
	@ResponseBody
	public Map<String,Object> groundOrGoAround(Integer id,int status){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = flightInfoService.groundOrGoAround(id, status);
			if(result){
				map.put("message", "操作成功");
				map.put("opResult", "0");
			}else{
				map.put("message", "操作失败");
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
			log.error(e);
		}
		return map;
	}
	@RequestMapping("/flightinfo_batchdel")
//	@MethodNote(comment="航线航班管理:15")
	@ResponseBody
	public Map<String,Object> delete(String ids){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = flightInfoService.delete(ids);
			if(result){
				map.put("message", "删除成功");
				map.put("opResult", "0");
			}else{
				map.put("message", "删除失败");
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
			log.error(e);
		}
		return map;
	}
	
	@RequestMapping("/flightinfo_load")
//	@MethodNote(comment="航线航班管理:15")
	@ResponseBody
	public Map<String,Object> load(Integer id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			FlightInfo info = flightInfoService.load(id);
			if(info!=null){
				map.put("info", info);
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
			log.error(e);
		}
		return map;
	}
	
	@RequestMapping("/flightinfo_search")
//	@MethodNote(comment="航线航班管理:15")
	@ResponseBody
	public Map<String,Object> selectAll(String airport){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Company cpy = UserContext.getCompany();
			Employee emp = UserContext.getUser();
			List<FlightInfo> list = null;
			if(emp.getUsrSts()==99){
				list = flightInfoService.selectAll(airport,null,0);
			}else{
				list = flightInfoService.selectAll(airport,cpy.getCpyItia(),0);
			}
			map.put("list", list);
		} catch (Exception e) {
			map.put("message", "操作异常");
			log.error(e);
		}
		return map;
	}
	
	@RequestMapping("/flightinfo_routeAssignment")
//	@MethodNote(comment="航线航班管理:15")
	@ResponseBody
	public Map<String,Object> selectAll(Long employeeId){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<FlightInfo> list = null;
			List<FlightRoute> fltNbrList = null;
			Company cpy = UserContext.getCompany();
			String itia = cpy.getCpyItia();
			Employee emp = UserContext.getUser();
			if(emp==null){
				map.put("message", "请重新登陆");
				return map;
			}
			if(emp.getUsrSts()==99){
				list = flightInfoService.selectAll2(null,1);
				fltNbrList = flightRouteService.selectByEmployee(employeeId, null);
			}else{
				list = flightInfoService.selectAll2(itia,1);
				fltNbrList = flightRouteService.selectByEmployee(employeeId, itia);
			}
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					String fltNbr = list.get(i).getFltNbr();
					boolean flag = false;
					if(fltNbr!=null&&!"".equals(fltNbr)){
						String[] flts = fltNbr.split(",");
						List<FlightRoute> newList = new ArrayList<FlightRoute>();
						if(fltNbrList!=null&&fltNbrList.size()>0){
							for(int k=0;k<flts.length;k++){
								boolean result = false;
								for(int j=0;j<fltNbrList.size();j++){
									if(flts[k].equals(fltNbrList.get(j).getFltNbr())&&fltNbrList.get(j).getRouteId().equals(list.get(i).getId())){
										fltNbrList.get(j).setType(1);
										newList.add(fltNbrList.get(j));
										//内部循环中当有一条数据被选中则返回true
										result = true;
										flag = true;
										break;
									}
								}
								if(!result){
									FlightRoute obj = new FlightRoute();
									obj.setFltNbr(flts[k]);
									newList.add(obj);
								}
							}
						}else{
							for(int j=0;j<flts.length;j++){
								FlightRoute obj = new FlightRoute();
								obj.setFltNbr(flts[j]);
								obj.setType(0);
								newList.add(obj);
							}
						}
						list.get(i).setFlts(newList);
					}
					if(flag){
						list.get(i).setType(1);
					}
				}
			}
			map.put("opResult", "0");
			map.put("list", list);
		} catch (Exception e) {
			map.put("message", "操作异常");
			log.error(e);
		}
		return map;
	}
	
	@RequestMapping("/flightinfo_saveRouteAssignment")
//	@MethodNote(comment="航线航班管理:15")
	@ResponseBody
	public Map<String,Object> saveRouteAssignment(Long employeeId,String fltNbr){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Employee emp = UserContext.getUser();
			if(emp==null){
				map.put("message", "session过期，请重新登陆");
			}
			boolean result = false;
			if(emp.getUsrSts()==99){
				result = flightInfoService.saveRouteAssignMent(null,employeeId, fltNbr);
			}else{
				Company cpy = UserContext.getCompany();
				result = flightInfoService.saveRouteAssignMent(cpy.getCpyItia(),employeeId, fltNbr);
			}
			if(result){
				map.put("opResult", "0");
				map.put("message", "保存成功");
			}else{
				map.put("message", "保存失败");
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
			log.error(e);
		}
		return map;
	}
	@RequestMapping("/getFltNum")
//	@MethodNote(comment="航线航班管理:15")
	@ResponseBody
	public Map<String,Object> getFltNum(String dpt_AirPt_Cd,String arrv_Airpt_Cd,String pas_stp){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Company company = UserContext.getCompany();
			if(company!=null){
				map = flightInfoService.getFltNum(company.getCpyItia(), dpt_AirPt_Cd, arrv_Airpt_Cd, pas_stp);
			}	
		} catch (Exception e) {
			map.put("message", "操作异常");
			log.error(e);
		}
		return map;
	}
}
