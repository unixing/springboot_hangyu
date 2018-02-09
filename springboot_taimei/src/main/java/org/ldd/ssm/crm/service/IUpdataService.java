package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Set;

import org.ldd.ssm.crm.domain.Fare;
import org.ldd.ssm.crm.domain.Z_Airdata;

/**
 * 导入外部数据的接口
 */
public interface IUpdataService {

	List<Z_Airdata> saveFileData(List<Z_Airdata> data, String dta_Sce) throws Exception;

	String getCompany(String cars);

	List<String> getUpFile_company_list(String companyId);

	Set<String> checkItia(List<List<String[]>> saveExecl);

	void externalYBsave(Fare strJson);

	List<Z_Airdata> savaz_AirDate(List<Z_Airdata> saveFileData);

}
