package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.MenuNew;
import org.ldd.ssm.crm.mapper.MenuNewMapper;
import org.ldd.ssm.crm.service.IMenuNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class MenuNewServiceImpl implements IMenuNewService{
	@Autowired
	private MenuNewMapper objMapper;
	Logger log = Logger.getLogger(MenuNewServiceImpl.class);
	@Override
	public List<MenuNew> getAllMenu() {
		List<MenuNew> list = null;
		try {
			list = objMapper.getAllMenu();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return list;
		}
		return list;
	}

}
