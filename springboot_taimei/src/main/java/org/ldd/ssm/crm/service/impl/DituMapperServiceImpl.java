package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.MapperData;
import org.ldd.ssm.crm.mapper.DituMapper;
import org.ldd.ssm.crm.service.DituMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 获取黑名单接口实现类
 * @Title:DituMapperServiceImpl 
 * @Description:
 * @author taimei-gds 
 * @date 2016-7-20 上午10:21:51
 */
@Service
public class DituMapperServiceImpl implements DituMapperService {
	@Autowired
	private DituMapper dituMapper;

	/* (non-Javadoc)
	 * @see org.ldd.ssm.crm.service.DituMapperService#getDataMapperList()
	 */
	public List<MapperData> getDataMapperList() {
		return dituMapper.getMapperDataList();
	}
	
}
