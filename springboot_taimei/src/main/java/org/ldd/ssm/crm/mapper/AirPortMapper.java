package org.ldd.ssm.crm.mapper;

import org.ldd.ssm.crm.domain.AirPort;
import org.ldd.ssm.crm.domain.Passengerdetalisinfo;
import org.ldd.ssm.crm.domain.Traveler;

public interface AirPortMapper {
	void save(AirPort airPort);
	void save2(Traveler traveler);
	void save3(Passengerdetalisinfo passengerdetalisinfo);
}
