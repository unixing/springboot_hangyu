package org.ldd.ssm.crm.mapper;


import java.util.List;

import org.ldd.ssm.crm.domain.ResourceNew;

public interface ResourceNewMapper {
	void save(ResourceNew res);

	void delete(Long id);

	void update(ResourceNew res);
	
	List<String> getAllResourceUrls();
}
