package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.domain.DemandSearch;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.query.CommissionedAndCustody;
import org.ldd.ssm.hangyu.query.DemandDto;
import org.ldd.ssm.hangyu.query.MyOrder;

public interface DemandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Demand record);

    int insertSelective(Demand record);

    Demand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Demand record);

    int updateByPrimaryKey(Demand record);
    
    List<Demand> getDemandsForCurrentEmployee(DemandSearch demandSearch);
    
    int getDemandsForCurrentEmployeeCount(DemandSearch demandSearch);
    
    List<Demand> getDemandsForCurrentCheckedCity(DemandSearch demandSearch);
    
    int getDemandsForCurrentCheckedCityCount(DemandSearch demandSearch);
    
    List<DemandDto> getAllDemands(Employee dto);
    
    List<Demand> selectTrusteeshipDemandList(DemandSearch demandSearch);
    
    int selectCountTrusteeshipDemand(DemandSearch demandSearch);
    
    List<Long> selectSonDemandsByParentDemandId(Long demandId);
    
    List<CommissionedAndCustody> findCommissionedAndCustody(CommissionedAndCustody demandSearch);
    
    int findCountCommissionedAndCustodyDemand(CommissionedAndCustody demandSearch);
    
    Demand selectResponseRefrenceDemand(Long demandId);
    
    List<Demand> selectTheReleasedDemandOfMine(@Param("demandType") String demandType,
                                               @Param("demandprogress") String demandprogress, @Param("employeeId") Long employeeId,
                                               @Param("orderType") int orderType, @Param("startIndex") int startIndex,
                                               @Param("pageSize") int pageSize);
    
    int selectCountTheReleasedDemandOfMine(@Param("demandType") String demandType,
                                           @Param("demandprogress") String responseProgress, @Param("employeeId") Long employeeId);
    
    List<Demand> selectDemandOfReviewList(@Param("demandType") String demandType,
                                          @Param("demandState") String demandState,
                                          @Param("orderType") int orderType, @Param("startIndex") int startIndex,
                                          @Param("pageSize") int pageSize);
    
    int selectCountDemandOfReviewList(@Param("demandType") String demandType,
                                      @Param("demandState") String demandState);
    
    int changeDemandIntentionStateByDemandId(@Param("demandId") Long demandId, @Param("intentionMoneyState") String intentionMoneyState);
    
    List<MyOrder> findMyOrderList(MyOrder MyOrder);
    
    int findCountMyOrder(MyOrder MyOrder);
    
    List<Demand> selectSonDemandsByParentId(Long id);
    
    Long selectDemandIdToSystemMessage(Demand demand);
    
    Long getAirportId(Employee dto);
    
    Long getAircompenyId(Employee dto);

	int getDemandsByCurrentCheckedAirportForEmployeeCount(DemandSearch demandSearch);

	List<Demand> getDemandsByCurrentCheckedAirportForEmployee(DemandSearch demandSearch);

	int getOthersDemandListIndexCount(DemandSearch demandSearch);

	List<Demand> getOthersDemandListIndex(DemandSearch demandSearch);
	
	List<DemandDto> getAllDemandsCount(Employee dto);
    
}