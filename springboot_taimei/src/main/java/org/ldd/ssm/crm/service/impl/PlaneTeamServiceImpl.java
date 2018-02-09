package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.PlaneTeam;
import org.ldd.ssm.crm.mapper.PlaneTeamMapper;
import org.ldd.ssm.crm.service.PlaneTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PlaneTeamServiceImpl implements PlaneTeamService {
	@Autowired
	private PlaneTeamMapper teamMapper;
	public boolean add(PlaneTeam info) {
		boolean result = false;
		int activeLine = teamMapper.insertSelective(info);
		if(activeLine == 1){
			result = true;
		}
		return result;
	}

	public boolean update(PlaneTeam info) {
		boolean result = false;
		int activeLine = teamMapper.updateByPrimaryKeySelective(info);
		if(activeLine == 1){
			result = true;
		}
		return result;
	}

	public boolean batchdel(int[] ids) {
		if(ids!=null&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				int activeLine = teamMapper.deleteByPrimaryKey(ids[i]);
				if(activeLine==0){
					return false;
				}
			}
		}
		return true;
	}

	public List<PlaneTeam> search(String teamName) {
		return teamMapper.selectByName(teamName);
	}

	public PlaneTeam load(int id) {
		return teamMapper.selectByPrimaryKey(id);
	}

}
