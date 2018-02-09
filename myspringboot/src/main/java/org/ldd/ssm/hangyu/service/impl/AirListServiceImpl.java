package org.ldd.ssm.hangyu.service.impl;

import org.ldd.ssm.hangyu.domain.AirportInfoNewThree;
import org.ldd.ssm.hangyu.mapper.AirportInfoNewThreeMapper;
import org.ldd.ssm.hangyu.service.AirListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirListServiceImpl implements AirListService {

    @Autowired
    AirportInfoNewThreeMapper airportInfoNewThreeMapper;

    @Override
    public List<AirportInfoNewThree> selectAllData() {
        return airportInfoNewThreeMapper.selectAllData();
    }

}
