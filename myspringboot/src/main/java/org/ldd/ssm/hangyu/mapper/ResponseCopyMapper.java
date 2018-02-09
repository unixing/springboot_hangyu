package org.ldd.ssm.hangyu.mapper;

import java.util.List;
import org.ldd.ssm.hangyu.domain.ResponseCopy;

public interface ResponseCopyMapper {
	int deleteByPrimaryKey(Long id);

    int insert(ResponseCopy record);

    int insertSelective(ResponseCopy record);

    ResponseCopy selectByPrimaryKey(Long id);
    
    List<ResponseCopy> selectByResponseCopy(ResponseCopy responseCopy);
    
    ResponseCopy selectLastRecord(ResponseCopy responseCopy);
}
