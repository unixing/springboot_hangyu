package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.Base;
import org.ldd.ssm.crm.mapper.BaseMapper;
import org.ldd.ssm.crm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BaseServiceImpl implements BaseService {
	@Autowired
	private BaseMapper baseMapper;
	public boolean add(Base info) {
		boolean result = false;
		int activeLine = baseMapper.insertSelective(info);
		if(activeLine == 1){
			result = true;
		}
		return result;
	}

	public boolean update(Base info) {
		boolean result = false;
		int activeLine = baseMapper.updateByPrimaryKeySelective(info);
		if(activeLine == 1){
			result = true;
		}
		return result;
	}

	public boolean batchdel(int[] ids) {
		if(ids!=null&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				int activeLine = baseMapper.deleteByPrimaryKey(ids[i]);
				if(activeLine==0){
					return false;
				}
			}
		}
		return true;
	}

	public List<Base> search(String baseName) {
		return baseMapper.selectByName(baseName);
	}

	public Base load(int id) {
		return baseMapper.selectByPrimaryKey(id);
	}

}
