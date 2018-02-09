package org.ldd.ssm.hangyu.service;

import java.util.Map;

public interface PublicOpinionService {

	public Map<String,Object> getPublicOpinionList(String code, Integer type, Integer codeType, Integer page, Integer pageSize);
	
}
