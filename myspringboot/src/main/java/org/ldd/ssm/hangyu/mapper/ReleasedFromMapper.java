package org.ldd.ssm.hangyu.mapper;

import org.ldd.ssm.hangyu.domain.ReleasedFrom;

public interface ReleasedFromMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReleasedFrom record);

    int insertSelective(ReleasedFrom record);

    ReleasedFrom selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReleasedFrom record);

    int updateByPrimaryKey(ReleasedFrom record);
}