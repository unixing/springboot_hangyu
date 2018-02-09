package org.ldd.ssm.hangyu.service;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.domain.DemandSearch;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.query.CommissionedAndCustody;
import org.ldd.ssm.hangyu.query.DemandDto;
import org.ldd.ssm.hangyu.query.MyOrder;
import org.ldd.ssm.hangyu.utils.PageBean;

public interface DemandService {

	public PageBean<Demand> getDemandsForCurrentEmployee(DemandSearch demandSearch);
	
	public PageBean<Demand> getDemandsForCurrentCheckedCity(DemandSearch demandSearch);
	
	public List<DemandDto> getAllDemands(Employee dto);
	
	public List<DemandDto> getAllDemandsNew(Employee dto);
	
	public boolean demandAdd(Demand demand) throws Exception;
	
	public boolean demandUpdate(Demand demand) throws Exception;
	
	public boolean demandDel(Long demandId) throws Exception;
	
	public Demand demandFind(Long demandId);
	
	public PageBean<Demand> getTrusteeshipDemandList(DemandSearch demandSearch);
	
	public List<Long> SonDemandsByParentDemandId(Long demandId);
	
	public PageBean<CommissionedAndCustody> findCommissionedAndCustodyDemandList(CommissionedAndCustody commissionedAndCustody);
	
	public PageBean<Demand> getTheReleaseDemandOfMine(String demandType, String demandprogress, Long employeeId, int page, int pageSize, int orderType);

	public PageBean<Demand> getDemandOfReviewList(String demandType,
                                                  String demandState, int page, int pageSize, int orderType);
	
	public boolean checkDemand(Long demandId, String demandState, String rek);
	
	public boolean changeDemandIntentionStateByDemandId(Long demandId, String intentionMoneyState);
	
	public PageBean<MyOrder> findMyOrderList(MyOrder myOrder);
	
	public List<Demand> selectSonDemandsByParentId(Long id);

	public PageBean<Demand> getDemandsByCurrentCheckedAirportForEmployee(
            DemandSearch demandSearch);

	public Map<String, Object> getOthersDemandListIndex(String iata, int page);
}
