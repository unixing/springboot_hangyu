package org.ldd.ssm.hangyu.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.Collect;
import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.domain.DemandSearch;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.domain.SimpleDemand;
import org.ldd.ssm.hangyu.query.MyCollect;
import org.ldd.ssm.hangyu.service.CollectService;
import org.ldd.ssm.hangyu.utils.PageBean;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollectAction {

	@Autowired
	private CollectService collectService;
	
	Logger log = Logger.getLogger(CollectAction.class);
	
	@RequestMapping("/collectFind")
	@ResponseBody
	public Map<String, Object> collectFind(Long collectId){
		Map<String, Object> map=new HashMap<String, Object>();
		if(collectId == null || collectId == 0){
			map.put("opResult", "3");
			return map;
		}
		
		try {
			Collect collect = collectService.selectByPrimaryKey(collectId);
			if(collect != null){
				map.put("data", collect);
				map.put("opResult", "0");
			}else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("there is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		
		return map;
	}
	
	@RequestMapping("/addCollect")
	@ResponseBody
	public Map<String,Object> addCollect(String demandIds){
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtil.isEmpty(demandIds)){
			map.put("opResult", "3");
			return map;
		}
		try {
			List<SimpleDemand> list = collectService.addCollect(demandIds);
			if(list==null){
				map.put("opResult", "1");
			}else{
				map.put("opResult", "0");
				map.put("list", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Oh,there is something wrong here");
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/delCollect")
	@ResponseBody
	public Map<String,Object> delCollect(Long collectId){
		Map<String,Object> map = new HashMap<String,Object>();
		if(collectId==null||collectId<=0){
			map.put("opResult", "3");
			return map;
		}
		try {
			boolean result = collectService.delCollect(collectId);
			if(result){
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Oh,there is something wrong here");
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/delCollectByDemandId")
	@ResponseBody
	public Map<String,Object> delCollectByDemandId(Long demandId){
		Map<String,Object> map = new HashMap<String,Object>();
		if(demandId==null||demandId<=0){
			map.put("opResult", "3");
			return map;
		}
		try {
			Collect collect=new Collect();
			collect.setEmployeeId(UserContext.getUser().getId());
			collect.setDemandId(demandId);
			boolean result = collectService.deleteByEmployeeIdAndDemandId(collect);
			if(result){
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Oh,there is something wrong here");
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/getCollectDemandList")
	@ResponseBody
	public Map<String,Object> getCollectDemandList(int page){
		Map<String,Object> map = new HashMap<String,Object>();
		if(page<=0){
			log.debug("page is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			Employee emp = UserContext.getUser();
			DemandSearch demandSearch = new DemandSearch();
			demandSearch.setEmployeeId(emp.getId());
			demandSearch.setPage(page);
			demandSearch.setPageNo(PageBean.pageSize);
			demandSearch.setStartIndex(PageBean.pageSize*(page-1));
			PageBean<Demand> pgList = collectService.selectDemandByEmployeeId(demandSearch);
			if(pgList!=null){
				map.put("list", pgList);
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Oh,there`s some wrong happened");
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	//收藏列表分页查询
	@RequestMapping("/selectCollectList")
	@ResponseBody
	public Map<String,Object> selectMyCollectList(MyCollect myCollect) {
		Map<String, Object> map = new HashMap<String,Object>();
		//
		if (myCollect == null||myCollect.getPage() <= 0||myCollect.getPageNo() <= 0) {
			map.put("opResult", "3");
			return map;
		}
		try {
			//获取页码
			int page = myCollect.getPage();
			//获取页面显示的条数
			int pageNo = myCollect.getPageNo();
			myCollect.setPage(page);
			myCollect.setPageNo(pageNo);
			myCollect.setEmployeeId(UserContext.getUser().getId());
			//总条数
			myCollect.setStartIndex(pageNo*(page-1));
			//调用service层查找收藏的接口
			PageBean<MyCollect> pgList = collectService.selectMyCollectList(myCollect);
			//
			if (pgList == null) {
				map.put("opResult", "1");
				return map;
			}else {
				map.put("opResult", "0");
				map.put("list", pgList);
				return map;
			}
		} catch (Exception e) {
			log.error("It`s some wrong happened here ,please check carefully");
			e.printStackTrace();
			map.put("opResult", "2");
		}
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
