package org.ldd.ssm.hangyu.mapper;

import org.ldd.ssm.hangyu.domain.PersonalCenter;

public interface PersonalCenterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PersonalCenter record);

    int insertSelective(PersonalCenter record);

    PersonalCenter selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PersonalCenter record);

    int updateByPrimaryKey(PersonalCenter record);
}