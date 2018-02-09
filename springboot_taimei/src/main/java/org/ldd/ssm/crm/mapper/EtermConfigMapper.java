package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.Instructions;

public interface EtermConfigMapper {

	List<Instructions> getConfiguration_list(Long id);

	void updateEterm_account(Instructions instructions);

	void updateConflictresolution(Instructions instructions);

	void updateAutomaticmanual(Instructions instructions);

	void saveEterm_account(Instructions instructions);

	void saveConflictresolution(Instructions instructions);

	void saveAutomaticmanual(Instructions instructions);

	void deleteEterm_account(long id);

	void deleteConflictresolution(long id);

	void deleteAutomaticmanual(long id);
	
	List<Instructions> getEtermsByName(@Param("etermName") String etermName);
}
