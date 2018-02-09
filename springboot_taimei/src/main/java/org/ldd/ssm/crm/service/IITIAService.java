package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ldd.ssm.crm.domain.Fare;

/**
 * 导入外部数据的接口
 */
public interface IITIAService {

	Set<Fare> find(List<String[]> imp);

	void save(Map<String, Object> map);

}
