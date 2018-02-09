package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.TimeResource;
import org.ldd.ssm.crm.mapper.TimeResourceMapper;
import org.ldd.ssm.crm.service.TimeResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TimeResourceServiceImpl implements TimeResourceService {
	@Autowired
	private TimeResourceMapper timeMapper;
	public boolean add(TimeResource resource) {
		boolean result = false;
		int activeLine = timeMapper.insertSelective(resource);
		if(activeLine == 1){
			result = true;
		}
		return result;
	}

	public boolean update(TimeResource resource) {
		boolean result = false;
		int activeLine = timeMapper.updateByPrimaryKeySelective(resource);
		if(activeLine == 1){
			result = true;
		}
		return result;
	}

	public boolean batchdel(int[] ids) {
		if(ids!=null&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				int activeLine = timeMapper.deleteByPrimaryKey(ids[i]);
				if(activeLine==0){
					return false;
				}
			}
		}
		return true;
	}

	public List<TimeResource> search(String terminal) {
		return timeMapper.selectByName(terminal);
	}

	public TimeResource load(int id) {
		return timeMapper.selectByPrimaryKey(id);
	}

}
