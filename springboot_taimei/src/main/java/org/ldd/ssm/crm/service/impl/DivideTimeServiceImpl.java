package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.ldd.ssm.crm.domain.DivideTime;
import org.ldd.ssm.crm.mapper.DivideTimeMapper;
import org.ldd.ssm.crm.service.DivideTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DivideTimeServiceImpl implements DivideTimeService {
	@Autowired
	private DivideTimeMapper timeMapper;

	public List<DivideTime> list(String fltDirect, int seasonId) {
		List<DivideTime> list = new ArrayList<DivideTime>();
		list = timeMapper.list(fltDirect, seasonId);
		return list;
	}
	
}
