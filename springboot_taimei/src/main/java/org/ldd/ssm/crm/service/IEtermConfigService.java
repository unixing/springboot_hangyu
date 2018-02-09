package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.Instructions;

public interface IEtermConfigService {

	List<Instructions> getConfiguration_list(Long id);

	void updateConfig(Instructions instructions);

	void saveConfig(Instructions instructions);

	void deleteConfig(long id);
	
	List<Instructions> getEtermsByAccName(String name);
}
