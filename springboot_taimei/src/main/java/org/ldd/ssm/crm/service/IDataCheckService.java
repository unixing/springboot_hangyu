package org.ldd.ssm.crm.service;

import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.DataCheckObject;
import org.ldd.ssm.crm.query.DateCheckQuery;

public interface IDataCheckService {

	DataCheckObject<Z_Airdata> getDataCheck(DateCheckQuery query);

}
