package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ldd.ssm.hangyu.domain.Response;

public interface ResponseMapper {
	/*
	 * 按需求id查询有几位用户对该该需求发起了意向
	 */
	@Select("SELECT COUNT(r.id) FROM response r WHERE r.demand_id=#{id} ")
	int selectByPrimaryKeyForCount(Long id);
	
    int deleteByPrimaryKey(Long id);

    int insert(Response record);

    int insertSelective(Response record);

    Response selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Response record);

    int updateByPrimaryKey(Response record);
    
    Response selectByDemandIdAndEmployeeId(Response response);
    
    List<Response> selectByResponse(Response response);
    
    int updateResponseNewInfoStatus(Response response);
    
    String selectIntentionCompanyName(Long employeeId);
    
    List<Response> selectReponseByEmployee(@Param("demandType") String demandType, @Param("responseProgress") String responseProgress, @Param("employeeId") Long employeeId, @Param("orderType") int orderType, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
    
    int selectReponseCountByEmployee(@Param("demandType") String demandType, @Param("responseProgress") String responseProgress, @Param("employeeId") Long employeeId);
    
    Response selectResponseDetails(@Param("responseId") Long responseId);
    
    List<Response> selectByDemandId(Long demandId);
    //查数据库中是否有该用户对该需求的意向
    @Select("SELECT COUNT(r.id) FROM response r WHERE r.demand_id=#{demandId} AND r.employee_id=#{employeeId}")
    int selectResponseIdCountByEmployeeIdAndDemandId(@Param("employeeId") Long employeeId, @Param("demandId") Long demandId);
    
    int selectResponseCountByDemandId(@Param("demandId") Long demandId);
    
    int updateResponsesByDemandId(@Param("demandId") Long demandId);
}