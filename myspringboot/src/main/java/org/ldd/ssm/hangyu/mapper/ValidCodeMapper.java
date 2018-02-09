package org.ldd.ssm.hangyu.mapper;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.ValidCode;

public interface ValidCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ValidCode record);

    int insertSelective(ValidCode record);

    ValidCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ValidCode record);

    int updateByPrimaryKey(ValidCode record);
    
    ValidCode selectValidCode(@Param("contactWay") String contactWay);
    
    //最新验证码生成后将原来的验证码都失效（删除）
    int deleteValidCodeByContactWay(@Param("contactWay") String contactWay);
    //最新验证码生成后将原来的验证码都失效（修改状态，这样会导致验证码表的数据越来愈多）
    int updateValidCodeByContactWay(@Param("contactWay") String contactWay);
}