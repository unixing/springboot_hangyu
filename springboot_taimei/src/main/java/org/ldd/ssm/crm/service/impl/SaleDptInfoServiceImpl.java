package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.SaleDptInfo;
import org.ldd.ssm.crm.mapper.SaleDptInfoMapper;
import org.ldd.ssm.crm.service.SaleDptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SaleDptInfoServiceImpl implements SaleDptInfoService {
	@Autowired
	private SaleDptInfoMapper infoMapper;
	public boolean add(SaleDptInfo info) {
		boolean result = false;
		int activeLine = infoMapper.insertSelective(info);
		if(activeLine == 1){
			result = true;
		}
		return result;
	}

	public boolean update(SaleDptInfo info) {
		boolean result = false;
		int activeLine = infoMapper.updateByPrimaryKeySelective(info);
		if(activeLine == 1){
			result = true;
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

	public List<SaleDptInfo> search(String dptName) {
		return infoMapper.selectByName(dptName);
	}

	public SaleDptInfo load(int id) {
		return infoMapper.selectByPrimaryKey(id);
	}

}
