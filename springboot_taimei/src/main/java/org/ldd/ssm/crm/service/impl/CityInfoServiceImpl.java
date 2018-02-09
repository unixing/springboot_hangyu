package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.CityInfo;
import org.ldd.ssm.crm.mapper.CityInfoMapper;
import org.ldd.ssm.crm.service.CityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CityInfoServiceImpl implements CityInfoService {
	@Autowired
	private CityInfoMapper infoMapper;
	public boolean add(CityInfo info) {
		boolean result = false;
		int acctiveLine = infoMapper.insertSelective(info);
		if(acctiveLine==1){
			result =true;
		}
		return result;
	}

	public boolean update(CityInfo info) {
		boolean result = false;
		int acctiveLine = infoMapper.updateByPrimaryKeySelective(info);
		if(acctiveLine==1){
			result =true;
		}
		return result;
	}

	public boolean batchdel(int[] ids) {
		if(ids!=null&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				int activeLine = infoMapper.deleteByPrimaryKey(ids[i]);
				if(activeLine==0){
					return false;
				}
			}
		}
		return true;
	}

	public List<CityInfo> search(String cityName) {
		return infoMapper.selectByName(cityName);
	}

	public CityInfo load(int id) {
		return infoMapper.selectByPrimaryKey(id);
	}

}
