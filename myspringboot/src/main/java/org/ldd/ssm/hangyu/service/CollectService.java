package org.ldd.ssm.hangyu.service;

import java.util.List;

import org.ldd.ssm.hangyu.domain.Collect;
import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.domain.DemandSearch;
import org.ldd.ssm.hangyu.domain.SimpleDemand;
import org.ldd.ssm.hangyu.query.MyCollect;
import org.ldd.ssm.hangyu.utils.PageBean;

public interface CollectService {
    public Collect selectByPrimaryKey(Long collectId);
    
	public Collect selectByDemandIdAndEmployeeId(Collect collect);
	
	public boolean deleteCollectByPrimaryKey(Long collectId);
	
	public boolean insert(Collect record);
    
	public int insertSelective(Collect record);

    public boolean updateByPrimaryKeySelective(Collect collect);

    public boolean updateByPrimaryKey(Collect collect);

    public List<SimpleDemand> addCollect(String demandIds);
    
    public boolean delCollect(Long collectId);
    
    public PageBean<Demand> selectDemandByEmployeeId(DemandSearch demandSearch);
    //列表
    public PageBean<MyCollect> selectMyCollectList(MyCollect myCollect);
    
    public boolean deleteByEmployeeIdAndDemandId(Collect collect);
    
    public boolean isAlreadyCollect(Collect collect);
    
    public List<Collect> selectCollectsByDemandId(Long demandId);

}
