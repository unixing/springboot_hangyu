package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.Instructions;
import org.ldd.ssm.crm.query.ETermQuery;
import org.ldd.ssm.crm.query.EtermInfoObject;
import org.ldd.ssm.crm.utils.AjaxResult;

public interface IETermService {

	void addeTerm(ETermQuery query);

	List<EtermInfoObject> getAirportOnLineData();

	AjaxResult eterm_delete(String id);

	AjaxResult eterm_test(String id)throws Exception ;

	List<Instructions> getEtermByName(String etermName, String companyName);
}
