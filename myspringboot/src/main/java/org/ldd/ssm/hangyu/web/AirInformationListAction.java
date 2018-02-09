package org.ldd.ssm.hangyu.web;

import org.ldd.ssm.hangyu.domain.AirportInfoNewThree;
import org.ldd.ssm.hangyu.service.AirListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class AirInformationListAction {
    @Autowired
    private AirListService airList;
    @RequestMapping("/airList")
    @ResponseBody
    public Map<String, Object> airList(){
        Map<String,Object> map = new HashMap<String, Object>();
        List<AirportInfoNewThree> listMes = airList.selectAllData();
        map.put("mes",listMes);
        return map;
    }
}
