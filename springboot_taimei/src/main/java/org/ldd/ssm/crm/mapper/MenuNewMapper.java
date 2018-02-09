package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.MenuNew;

public interface MenuNewMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MenuNew record);

    int insertSelective(MenuNew record);

    MenuNew selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MenuNew record);

    int updateByPrimaryKey(MenuNew record);
    
    List<MenuNew> getAllMenu();
}