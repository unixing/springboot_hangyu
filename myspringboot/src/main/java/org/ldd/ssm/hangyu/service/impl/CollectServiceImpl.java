package org.ldd.ssm.hangyu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.Collect;
import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.domain.DemandSearch;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.domain.Response;
import org.ldd.ssm.hangyu.domain.SimpleDemand;
import org.ldd.ssm.hangyu.mapper.ChatMapper;
import org.ldd.ssm.hangyu.mapper.CollectMapper;
import org.ldd.ssm.hangyu.mapper.ResponseMapper;
import org.ldd.ssm.hangyu.query.MyCollect;
import org.ldd.ssm.hangyu.service.CollectService;
import org.ldd.ssm.hangyu.utils.DateUtil;
import org.ldd.ssm.hangyu.utils.PageBean;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectServiceImpl implements CollectService {
	
	@Autowired
	private CollectMapper collectMapper;
	
	@Autowired
	private ResponseMapper responseMapper;
	
	@Autowired
	private ChatMapper chatMapper;
	
	Logger log=Logger.getLogger(CollectServiceImpl.class);

	@Override
	public Collect selectByPrimaryKey(Long loginId) {
		Collect collect = null;
		if (loginId == null || loginId == 0) {
			log.debug("selectByPrimaryKey:collectId is invalid");
			return collect;
		}
		try {
			collect = collectMapper.selectByPrimaryKey(loginId);
		} catch (Exception e) {
			log.error("selectByPrimaryKey:there is something here.");
			e.printStackTrace();
		}
		return collect;
	}

	@Override
	public List<SimpleDemand> addCollect(String demandIds) {
		List<SimpleDemand> list = null;
		if(TextUtil.isEmpty(demandIds)){
			log.debug("addCollect:demandIds is an invalid parameter");
			return list;
		}
		try {
			String[] demandIdArray = demandIds.split(",");
			list = new ArrayList<SimpleDemand>();
			for(String demandId:demandIdArray){
				SimpleDemand sd = new SimpleDemand();
				sd.setKey(demandId);
				Employee emp = UserContext.getUser();
				Collect collect = collectMapper.selectCollect(emp.getId(), Long.valueOf(demandId));
				if(collect!=null){
					sd.setVal(collect.getId()+"");
					list.add(sd);
					continue;
				}else{
					collect = new Collect();
					collect.setDemandId(Long.valueOf(demandId));
					collect.setEmployeeId(emp.getId());
					int activeLines = collectMapper.insertSelective(collect);
					if(activeLines>0){
						sd.setVal(collect.getId()+"");
						list.add(sd);
					}
				}
			}
		} catch (Exception e) {
			log.error("addCollect:there is something wrong here");
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@Override
	public boolean delCollect(Long collectId) {
		boolean result = false;
		if(collectId==null){
			log.debug("delCollect:collect is an invalid parameter");
			return result;
		}
		try {
			int activeLines = collectMapper.deleteByPrimaryKey(collectId);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.error("delCollect:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public PageBean<Demand> selectDemandByEmployeeId(DemandSearch demandSearch) {
		PageBean<Demand> pgList = null;
		if(demandSearch.getEmployeeId()==null||demandSearch.getEmployeeId()<=0||demandSearch.getPage()<=0){
			log.debug("selectDemandByEmployeeId:employeeId is an invalid parameter");
			return pgList;
		}
		try {
			int count = collectMapper.selectDemandCountByEmployeeId(demandSearch.getEmployeeId());
			if(count>0&&count>demandSearch.getStartIndex()){
				List<Demand> list = TextUtil.formatDemand(collectMapper.selectDemandListByEmployeeId(demandSearch));
				pgList = new PageBean<Demand>(count, list);
			}else if(count==0){
				pgList = new PageBean<Demand>(count, null);
			}
		} catch (Exception e) {
			log.error("selectDemandByEmployeeId: there is something wrong here");
			e.printStackTrace();
			return pgList;
		}
		return pgList;
	}

	@Override
	public Collect selectByDemandIdAndEmployeeId(Collect collect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteCollectByPrimaryKey(Long collectId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insert(Collect record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int insertSelective(Collect record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean updateByPrimaryKeySelective(Collect collect) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateByPrimaryKey(Collect collect) {
		// TODO Auto-generated method stub
		return false;
	}
	//分页查询委托和托管需求列表
	@Override
	public PageBean<MyCollect> selectMyCollectList(MyCollect myCollect) {
		PageBean<MyCollect> pageBeanList = null;
		if (myCollect == null) {
			log.debug("getMyCollectList:myCollect is an invalid parameter");
			return pageBeanList;
		}
		try {
			//将查询到的所有收藏list（总数）存放在对象中
			int totalCount = collectMapper.selectMyCollectListCount(myCollect);
			//如果总数0并且大于查询其实下标
			if (totalCount > 0 && totalCount > myCollect.getStartIndex()) {
				List<MyCollect> list = collectMapper.selectMyCollectList(myCollect);
				
				//遍历查询是否响应过，没响应则不能聊天
				Response paramResponse=new Response();
				for(MyCollect collect:list){
					//编辑发布时间格式
					collect.setReleaseTime(DateUtil.date2Str(
							DateUtil.StringToDate(collect.getReleaseTime()),
							"MM.dd.yyyy"));
					//未读消息条数
					collect.setUnreadNum(chatMapper.getNoReadChatRecordCountForTameiForWeituoAndTuoguan(UserContext.getUser().getId(), collect.getId()));
					paramResponse.setEmployeeId(UserContext.getUser().getId());
					paramResponse.setDemandId(collect.getId());
					List<Response> liResponses=responseMapper.selectByResponse(paramResponse);
					if (liResponses.size()>0) {
						collect.setWetherResponse(true);
					}else {
						collect.setWetherResponse(false);
					}
					
				}
				//每页显示条数为list
				pageBeanList = new PageBean<MyCollect>(totalCount,list,myCollect.getPageNo());
			}else {//每页显示为空
				pageBeanList = new PageBean<MyCollect>(totalCount,null,myCollect.getPageNo());
			}
		} catch (Exception e) {
			log.debug("There is something wrong here,please check it out");
			e.printStackTrace();
			return pageBeanList;
		}
		return pageBeanList;
	}

	@Override
	public boolean deleteByEmployeeIdAndDemandId(Collect collect) {
		boolean result = false;
		if(collect==null){
			log.debug("deleteByEmployeeIdAndDemandId:collect is an invalid parameter");
			return result;
		}
		try {
			int activeLines = collectMapper.deleteByEmployeeIdAndDemandId(collect);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.error("deleteByEmployeeIdAndDemandId:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public boolean isAlreadyCollect(Collect collect) {
		boolean result=false;
		if(collect.getDemandId()==null||collect.getDemandId()==0){
			log.debug("isAlreadyCollect:collect is an invalid parameter");
			return result;
		}
		try {
			Integer activelines=collectMapper.isAlreadyCollect(collect);
			if (activelines==1) {
				//已收藏
				result=true;
			}
		} catch (Exception e) {
			log.error("isAlreadyCollect:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		
		return result;
	}

	@Override
	public List<Collect> selectCollectsByDemandId(Long demandId) {
		List<Collect> list=new ArrayList<Collect>();
		if(demandId.longValue()==0||demandId==null){
			log.debug("selectCollectsByDemandId:demandId is null");
			return null;
		}
		try {
			list=collectMapper.selectCollectsByDemandId(demandId);
		} catch (Exception e) {
			log.error("selectCollectsByDemandId:there is something wrong here");
			return list;
		}
		return list;
	}
}
