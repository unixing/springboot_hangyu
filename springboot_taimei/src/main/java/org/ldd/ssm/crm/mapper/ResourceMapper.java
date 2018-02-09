package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.Resource;

public interface ResourceMapper {
	void save(Resource res);

	void delete(Long id);

	void update(Resource res);

	Resource get(Long id);

	List<Resource> list();


	List<Resource> getResourceByUsername(String username);
	
	List<Resource> selectAll();
	
	Resource load(String resourceUrl);
}
