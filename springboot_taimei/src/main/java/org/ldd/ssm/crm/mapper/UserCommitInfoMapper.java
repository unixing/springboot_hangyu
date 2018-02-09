package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.UserCommitInfo;
import org.ldd.ssm.crm.query.UserCommit;

public interface UserCommitInfoMapper {

    int insert(UserCommitInfo record);

    List<UserCommit> findUserCommitInfo(Long employeeId);
    
    int updateByPrimaryKeySelective(UserCommitInfo record);

    int updateByPrimaryKey(UserCommitInfo record);
    
    int updateState(UserCommitInfo record);
}