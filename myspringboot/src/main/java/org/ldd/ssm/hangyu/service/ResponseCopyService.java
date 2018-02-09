package org.ldd.ssm.hangyu.service;

import java.util.List;

import org.ldd.ssm.hangyu.domain.ResponseCopy;

public interface ResponseCopyService {
	
	public boolean deleteResponseCopyByPrimaryKey(Long id);

	public boolean addResponseCopy(ResponseCopy responseCopy);

	public boolean addResponseCopySelective(ResponseCopy responseCopy);

	public ResponseCopy selectByPrimaryKey(Long id);
    
	public List<ResponseCopy> selectByResponseCopy(ResponseCopy responseCopy);
	
	public ResponseCopy selectLastRecord(Long employeeId, Long demandId);
}
