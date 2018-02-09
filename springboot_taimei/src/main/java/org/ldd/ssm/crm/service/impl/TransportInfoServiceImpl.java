package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.TransportInfo;
import org.ldd.ssm.crm.mapper.TransportInfoMapper;
import org.ldd.ssm.crm.service.TransportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TransportInfoServiceImpl implements TransportInfoService {
	@Autowired
	private TransportInfoMapper infoMapper;
	public boolean add(TransportInfo info) {
		boolean result = false;
		int activeLine = infoMapper.insertSelective(info);
		if(activeLine==1){
			result = true;
		}
		return result;
	}

	public boolean update(TransportInfo info) {
		boolean result = false;
		int activeLine = infoMapper.updateByPrimaryKeySelective(info);
		if(activeLine==1){
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

	public List<TransportInfo> search(String airportName) {
		return infoMapper.selectByName(airportName);
	}

	public TransportInfo load(int id) {
		return infoMapper.selectByPrimaryKey(id);
	}
}
