package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.Fare;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.StartDateAndEndDate;

/**
 * 外部导入数据的DAO层
 */
public interface UpdataMapper {

	Fare get(Fare fare);

	void save(Z_Airdata data2);

	Z_Airdata getCkeckData(Z_Airdata z_Airdata);

	String getCompany(String user);

	List<String> getUpFile_company(long company_Id);

	List<String> getUpFile_company_list();

	void externalYBsave(Fare strJson);

	List<Z_Airdata> getStratDateAndEndDate(StartDateAndEndDate startDateAndEndDate);

	void saveList(List<Z_Airdata> saveFileData);

	List<Fare> getAll();

	List<Z_Airdata> getData_Source(StartDateAndEndDate startDateAndEndDate);

	void saveData_Source(List<Z_Airdata> saveFileData);
	
}
