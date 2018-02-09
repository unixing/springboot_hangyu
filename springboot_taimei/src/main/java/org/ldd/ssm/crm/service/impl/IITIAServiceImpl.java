package org.ldd.ssm.crm.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ldd.ssm.crm.domain.Fare;
import org.ldd.ssm.crm.mapper.IITIAMapper;
import org.ldd.ssm.crm.service.IITIAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 导入外部接口的方法
 * 
 */
@Service
public class IITIAServiceImpl implements IITIAService {
	@Autowired
	private IITIAMapper iitia;
	public Set<Fare> find(List<String[]> imp) {
		Set<Fare> set = new HashSet<Fare>();
		Set<String> list = new HashSet<String>();
		for (String[] strings : imp) {
			String str = strings[1];
			Fare fare = iitia.find(str);
			if(fare==null){
				list.add(str);
			}
		}
		for (String fare : list) {
			Fare fa = new Fare();
			fa.setVoyageCode(fare);
			set.add(fa);
		}
		return set;
	}
	public void save(Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {  
			iitia.save((Fare)entry.getValue());
		}
	}
	
	
}
