package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.BuidSchedule;
import org.ldd.ssm.crm.mapper.BuidScheduleMapper;
import org.ldd.ssm.crm.service.BuidScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BuidScheduleServiceImpl implements BuidScheduleService {
	@Autowired
	private BuidScheduleMapper buidMapper;
	public boolean add(BuidSchedule info) {
		boolean result = false;
		int activeLine = buidMapper.insertSelective(info);
		if(activeLine==1){
			result = true;
		}
		return result;
	}

	public boolean update(BuidSchedule info) {
		boolean result = false;
		int activeLine = buidMapper.updateByPrimaryKeySelective(info);
		if(activeLine==1){
			result = true;
		}
		return result;
	}

	public boolean batchdel(int[] ids) {
		if(ids!=null&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				int activeLine = buidMapper.deleteByPrimaryKey(ids[i]);
				if(activeLine==0){
					return false;
				}
			}
		}
		return true;
	}

	public List<BuidSchedule> search(String airportName) {
		return buidMapper.selectByName(airportName);
	}

	public BuidSchedule load(int id) {
		return buidMapper.selectByPrimaryKey(id);
	}

}
