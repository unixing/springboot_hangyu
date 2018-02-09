package org.ldd.ssm.crm.mapper;

import org.ldd.ssm.crm.domain.Fare;

public interface FareMapper {
	void save(Fare fare);
	double get(String str);
}
