package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.AddFltNbr;
import org.ldd.ssm.crm.domain.Conflictresolution;
import org.ldd.ssm.crm.domain.Instructions;
import org.ldd.ssm.crm.query.AirLineObject;
import org.ldd.ssm.crm.query.AirLineQuery;

public interface ETermMapper {

	void addeTerm(Instructions instructions);

	void addeTermCt(Conflictresolution conflictresolution);

	void addETermFlt_Rte_Cd(AddFltNbr addFltNbr);

	void addFlt_One(AddFltNbr addFltNbr);

	void addFlt_two(AddFltNbr addFltNbr);

	void addFlt_three(AddFltNbr addFltNbr);

	List<Instructions> getFindEtermByUserId(Long id);

	List<AddFltNbr> getFindAirlineByEtermId(long id);

	void addeTermAuto(Instructions instructions);

	void eterm_delete(String id);

	void etermAirInfo_delete(String id);

	void etermAuto_delete(String id);

	void etermConf_delete(String id);

	String getFindUserByEtermId(String id);

	Instructions getFindInstrByEterm_id(String id);

	void updateEtermState(Instructions iTIA);

	List<Instructions> getIATAAll(Instructions instructions);

	List<Instructions> getAll(Instructions instructions);

	List<AirLineObject> getAirPt(AirLineQuery airLineQuery);

	void UpdateEtermState(Instructions instructions);

	List<Instructions> getEtermsByName(@Param("etermName") String etermName, @Param("companyName") String companyName);
}
