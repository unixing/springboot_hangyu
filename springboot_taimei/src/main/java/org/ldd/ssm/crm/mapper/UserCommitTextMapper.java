package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.UserCommitText;
import org.ldd.ssm.crm.query.UserCommit;

public interface UserCommitTextMapper {

    int insert(UserCommit record);
    
    List<UserCommit> findUserCommitDetail(Long userCommitInfoId);

    int insertSelective(UserCommitText record);

    UserCommitText selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCommitText record);

    int updateByPrimaryKey(UserCommitText record);
}