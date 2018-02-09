package org.ldd.ssm.hangyu.service;


import org.ldd.ssm.hangyu.domain.AirportInfoNewThree;

import java.util.List;

public interface AirListService {
    List<AirportInfoNewThree> selectAllData();
}
