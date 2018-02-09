package org.ldd.ssm.hangyu.mapper;

import org.ldd.ssm.hangyu.domain.Intention;

public interface IntentionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Intention record);

    int insertSelective(Intention record);

    Intention selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Intention record);

    int updateByPrimaryKey(Intention record);
}