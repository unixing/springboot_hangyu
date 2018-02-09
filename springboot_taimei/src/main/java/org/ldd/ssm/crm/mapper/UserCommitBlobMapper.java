package org.ldd.ssm.crm.mapper;

import org.ldd.ssm.crm.domain.UserCommitBlob;
import org.ldd.ssm.crm.query.UserCommit;

public interface UserCommitBlobMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserCommit record);

    int insertSelective(UserCommitBlob record);

    UserCommitBlob selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCommitBlob record);

    int updateByPrimaryKeyWithBLOBs(UserCommitBlob record);

    int updateByPrimaryKey(UserCommitBlob record);
}