package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.FlightInfo;

public interface FlightInfoService {
public boolean add(FlightInfo flightInfo);
public boolean update(FlightInfo flightInfo);
public boolean delete(String ids);
public FlightInfo load(Integer id);
public List<FlightInfo> selectAll(String airport, String itia, Integer status);
public List<FlightInfo> selectAll2(String itia, Integer status);
public boolean groundOrGoAround(Integer id, int status);
public boolean saveRouteAssignMent(String itia, Long employeeId, String fltNbr);
public boolean deleteFltNbrByEmployeeId(String itia, Long employeeId);
public Map<String,Object> getFltNum(String itia, String dpt_AirPt_Cd, String arrv_Airpt_Cd, String pas_stp);
}
