package org.ldd.ssm.crm.service;

import java.io.IOException;
import java.util.List;

import org.ldd.ssm.crm.domain.Resource;
import org.springframework.stereotype.Service;

@Service
public interface IResourceService {

	void save(Resource res);

	void delete(Long id);

	void update(Resource res);

	Resource get(Long id);

	List<Resource> list();

	Resource checkLogin(String username, String password);
	
	List<Resource> getResourceList(Long employeeId, Long datSreCpyId, Long roleId);
	
	void scanResource() throws IOException;
	
	boolean checkPermission(String resourceUrl, List<Resource> resources);
	public List<Resource> getCurrentEmployeeResource(Long companyId, Long employeeId);
}
