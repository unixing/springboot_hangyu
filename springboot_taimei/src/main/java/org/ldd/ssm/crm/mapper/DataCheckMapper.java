package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.DateCheckQuery;

public interface DataCheckMapper {

	List<Z_Airdata> getDataCheck(DateCheckQuery query);

	Integer getTotal(DateCheckQuery query);

}
