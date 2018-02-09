package org.ldd.ssm.hangyu.service;

import java.util.List;

import org.ldd.ssm.hangyu.domain.Response;
import org.ldd.ssm.hangyu.utils.PageBean;

public interface ResponseService {
	
	public int selectByPrimaryKeyForCount(Long demandId);
	
	public Response selectByDemandIdAndEmployeeId(Response response);
	
	public boolean addResponseSelective(Response response);
	
	public Response selectByPrimaryKey(Long responseId);
	
	public List<Response> selectByResponse(Response response);
	
	public boolean deleteResponseByPrimaryKey(Long responseId);
	
	public boolean addResponse(Response response);
	
	public boolean updateResponseSelective(Response response);
	
	public boolean updateResponse(Response response);
	
	public boolean updateResponseNewInfoStatus(String newinfo, Long id);
	
	public PageBean<Response> getResponseByEmployee(String demandType, String responseProgress, Long employeeId, int orderType, int page, int pageSize);

	public String selectIntentionCompanyName(Long employeeId);
	
	public Response getResponseDetails(Long responseId);
	
	public List<Response> selectByDemandId(Long demandId);
	
	public int selectResponseIdCountByEmployeeIdAndDemandId(Long employeeId, Long demandId);
}
