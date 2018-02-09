package org.ldd.ssm.crm.service;

import java.io.IOException;
import java.util.List;

public interface IResourceNewService {

	List<String> getAllResourceUrls();
	public void scanResource() throws IOException;
}
