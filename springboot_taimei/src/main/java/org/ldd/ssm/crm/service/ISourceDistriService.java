package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.SourceDataAll;
import org.ldd.ssm.crm.domain.SourceDistriData;
import org.ldd.ssm.crm.query.SourceDistriDataQuery;

public interface ISourceDistriService {

	List<SourceDataAll> getSourceDistriData(SourceDistriDataQuery dataQuery);

	List<SourceDistriData> getSourceDistriDataByName(SourceDistriDataQuery dataQuery);

	List<SourceDataAll> getCustomersInfo(SourceDistriDataQuery dataQuery);
	
	List<String> getFltNbrList(SourceDistriDataQuery dataQuery);
	String getCustomerNewDate(SourceDistriDataQuery dataQuery);
}
