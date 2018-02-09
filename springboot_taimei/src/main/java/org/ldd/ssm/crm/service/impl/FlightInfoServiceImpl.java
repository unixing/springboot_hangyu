package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.FlightInfo;
import org.ldd.ssm.crm.domain.FlightRoute;
import org.ldd.ssm.crm.mapper.FlightInfoMapper;
import org.ldd.ssm.crm.mapper.FlightRouteMapper;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.service.FlightInfoService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class FlightInfoServiceImpl implements FlightInfoService {
	@Autowired
	private FlightInfoMapper objMapper;
	@Autowired
	private FlightRouteMapper flightRouteMapper;
	@Autowired
	private OutPortMapper outPortMapper;
	Logger log = Logger.getLogger(FlightInfoServiceImpl.class);
	public boolean add(FlightInfo flightInfo) {
		boolean result = false;
		try {
			String pasStp = flightInfo.getPas_stp();
			if(pasStp!=null&&!"".equals(pasStp)){
				flightInfo.setFltRteCd(outPortMapper.getAirCodeByName(
						flightInfo.getDpt_AirPt_Cd())
						+
						outPortMapper.getAirCodeByName(flightInfo.getPas_stp())
						+
						outPortMapper.getAirCodeByName(flightInfo.getArrv_Airpt_Cd()));
			}else{
				flightInfo.setFltRteCd(
						outPortMapper.getAirCodeByName(flightInfo.getDpt_AirPt_Cd())
						+outPortMapper.getAirCodeByName(flightInfo.getArrv_Airpt_Cd()));
			}
			int activeLines = objMapper.insertSelective(flightInfo);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	public boolean update(FlightInfo flightInfo) {
		boolean result = false;
		try {
			String pasStp = flightInfo.getPas_stp();
			if(pasStp!=null&&!"".equals(pasStp)){
				flightInfo.setFltRteCd(outPortMapper.getAirCodeByName(
						flightInfo.getDpt_AirPt_Cd())
						+
						outPortMapper.getAirCodeByName(flightInfo.getPas_stp())
						+
						outPortMapper.getAirCodeByName(flightInfo.getArrv_Airpt_Cd()));
			}else{
				flightInfo.setFltRteCd(
						outPortMapper.getAirCodeByName(flightInfo.getDpt_AirPt_Cd())
						+outPortMapper.getAirCodeByName(flightInfo.getArrv_Airpt_Cd()));
			}
			int activeLines = objMapper.updateByPrimaryKeySelective(flightInfo);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	public boolean delete(String ids) {
		boolean result = false;
		try {
			String[] idArray = ids.split(",");
			if(idArray!=null&&idArray.length>0){
				for(int i=0;i<idArray.length;i++){
					int activeLines = objMapper.deleteByPrimaryKey(Integer.valueOf(idArray[i]));
					if(activeLines>0){
						result = true;
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	public FlightInfo load(Integer id) {
		FlightInfo info = null;
		try {
			info  = objMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			log.error(e);
		}
		return info;
	}

	public List<FlightInfo> selectAll(String airport,String itia,Integer status) {
		List<FlightInfo> list = null;
		List<FlightInfo> newList = new ArrayList<FlightInfo>();
		try {
			//将机场名转换为机场三字码
//			airport = outPortMapper.getAirCodeByName(airport);
			list = objMapper.selectAll(itia,status);
			if(list!=null&&list.size()>0){
				for(FlightInfo info:list){
					String flt = info.getFltRteCd();
					if(flt.length()==9){
						info.setDpt_AirPt_Cd(outPortMapper.getNameByCode(flt.substring(0,3)));
						info.setArrv_Airpt_Cd(outPortMapper.getNameByCode(flt.substring(6)));
						info.setPas_stp(outPortMapper.getNameByCode(flt.substring(3,6)));
						if(info.getAirport().contains(airport)){
							newList.add(info);
						}
					}else{
						info.setDpt_AirPt_Cd(outPortMapper.getNameByCode(flt.substring(0,3)));
						info.setArrv_Airpt_Cd(outPortMapper.getNameByCode(flt.substring(3)));
						if(info.getAirport().contains(airport)){
							newList.add(info);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return newList;
	}

	public List<FlightInfo> selectAll2(String itia,Integer status) {
		List<FlightInfo> list = null;
		try {
			//将机场名转换为机场三字码
//			airport = outPortMapper.getAirCodeByName(airport);
			list = objMapper.selectAll(itia,status);
			if(list!=null&&list.size()>0){
				for(FlightInfo info:list){
					String flt = info.getFltRteCd();
					if(flt.length()==9){
						info.setDpt_AirPt_Cd(outPortMapper.getNameByCode(flt.substring(0,3)));
						info.setArrv_Airpt_Cd(outPortMapper.getNameByCode(flt.substring(6)));
						info.setPas_stp(outPortMapper.getNameByCode(flt.substring(3,6)));
					}else{
						info.setDpt_AirPt_Cd(outPortMapper.getNameByCode(flt.substring(0,3)));
						info.setArrv_Airpt_Cd(outPortMapper.getNameByCode(flt.substring(3)));
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return list;
	}
	
	public boolean groundOrGoAround(Integer id, int status) {
		boolean result = false;
		try {
			int activeLines = objMapper.groundOrGoAround(id, status);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.error(e);
			return result;
		}
		return result;
	}

	public boolean saveRouteAssignMent(String itia,Long employeeId,
			String fltNbr) {
		boolean result = false;
		boolean deleteHistoryRecored = deleteFltNbrByEmployeeId(itia, employeeId);
		try {
			if(!deleteHistoryRecored){
				return result;
			}
			if(fltNbr!=null&&!"".equals(fltNbr)){
				//去掉末尾的逗号
				fltNbr = fltNbr.substring(0,fltNbr.length()-1);
				String[] flts = fltNbr.split(",");
				if(flts!=null&&flts.length>0){
					for(int i=0;i<flts.length;i++){
						String[] fltInfo = flts[i].split(":");
						FlightRoute obj = new FlightRoute();
						obj.setEmployeeId(employeeId);
						obj.setRouteId(Integer.valueOf(fltInfo[0]));
						obj.setFltNbr(fltInfo[1]);
						flightRouteMapper.insertSelective(obj);
					}
					result = true;
				}
			}else{
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return result;
		}
		return result;
	}

	public boolean deleteFltNbrByEmployeeId(String itia,Long employeeId) {
		boolean result = false;
		try {
			int hasCount = flightRouteMapper.selectFlightNbrCount(itia, employeeId);
			if(hasCount>0){
				int activeLines = flightRouteMapper.deleteByEmployeeId(itia, employeeId);
				if(hasCount==activeLines){
					result = true;
				}
			}else{
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> getFltNum(String itia, String dpt_AirPt_Cd,
			String arrv_Airpt_Cd ,String pas_stp) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> fltList = new ArrayList<String>();
		List<String> fltList2 = new ArrayList<String>();
		List<String> flts = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		dpt_AirPt_Cd = outPortMapper.getAirCodeByName(dpt_AirPt_Cd);
		arrv_Airpt_Cd = outPortMapper.getAirCodeByName(arrv_Airpt_Cd);
		try {
			if(pas_stp!=null&&!"".equals(pas_stp)){
				pas_stp = outPortMapper.getAirCodeByName(pas_stp);
				//查询起点-终点航班号
				fltList = objMapper.getFltNum(itia, dpt_AirPt_Cd, arrv_Airpt_Cd, dpt_AirPt_Cd+pas_stp+arrv_Airpt_Cd);
				//查询终点-起点航班号
				fltList2 = objMapper.getFltNum(itia, arrv_Airpt_Cd, dpt_AirPt_Cd, arrv_Airpt_Cd+pas_stp+dpt_AirPt_Cd);
				set.addAll(FuseFltNbr(fltList, fltList2));
				//查询起点-经停航班号
				fltList = objMapper.getFltNum(itia, dpt_AirPt_Cd, pas_stp, dpt_AirPt_Cd+pas_stp+arrv_Airpt_Cd);
				//查询经停-起点航班号
				fltList2 = objMapper.getFltNum(itia, pas_stp, dpt_AirPt_Cd, arrv_Airpt_Cd+pas_stp+dpt_AirPt_Cd);
				set.addAll(FuseFltNbr(fltList, fltList2));
				//查询经停-终点航班号
				fltList = objMapper.getFltNum(itia, pas_stp, arrv_Airpt_Cd, dpt_AirPt_Cd+pas_stp+arrv_Airpt_Cd);
				//查询终点-经停航班号
				fltList2 = objMapper.getFltNum(itia, arrv_Airpt_Cd, pas_stp, arrv_Airpt_Cd+pas_stp+dpt_AirPt_Cd);
				set.addAll(FuseFltNbr(fltList, fltList2));
			}else{
				//查询起点-终点航班号
				fltList = objMapper.getFltNum(itia, dpt_AirPt_Cd, arrv_Airpt_Cd, dpt_AirPt_Cd+arrv_Airpt_Cd);
				//查询终点-起点航班号
				fltList2 = objMapper.getFltNum(itia, arrv_Airpt_Cd, dpt_AirPt_Cd, arrv_Airpt_Cd+dpt_AirPt_Cd);
				set.addAll(FuseFltNbr(fltList, fltList2));
			}
			if(set!=null&&set.size()>0){
				for(String str:set){
					flts.add(str);
				}
			}
			map.put("list", flts);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return map;
		}
		return map;
	}
	
	public Set<String> FuseFltNbr(List<String> list,List<String> list2){
		Set<String> set = new HashSet<String>();
		if(list!=null&&list.size()>0&&list2!=null&&list2.size()>0){
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list2.size(); j++) {
					String fly_nbr = list.get(i);
					String strflyNbr = list2.get(j);
					if(!TextUtil.isEmpty(fly_nbr)&&!TextUtil.isEmpty(strflyNbr)){
						int lastNum1 = HbhCharater(fly_nbr.substring(5, 6));
						int lastNum2 = HbhCharater(strflyNbr.substring(5, 6));
						int twolastNum1 = HbhCharater(fly_nbr.substring(4, 5));
						String onelastNum1 = fly_nbr.substring(0, 4);
						String newStr1= "";
						String newStr2= "";
						if(lastNum1%2==0){
							//偶数
							if(lastNum1==0){
								newStr2 = onelastNum1 + (twolastNum1-1) + "9";
							}else{
								newStr2 = onelastNum1+ twolastNum1 + (lastNum1-1);
							}
						}else{
							if(lastNum1==9){
								newStr2 = onelastNum1 + (twolastNum1+1) + "0";
							}else{
								newStr2 = onelastNum1+ twolastNum1 + (lastNum1+1);
							}
						}
						newStr1= strflyNbr.substring(0, 5)+lastNum2;
						if(newStr1.equals(newStr2)){
							set.add(fly_nbr);
							set.add(strflyNbr);
						}
					}
				}
			}
		}
		return set;
	}
	
	public int HbhCharater(String numnew){
		int lastH = 0;
		if("Z".equals(numnew)){
			lastH = 0;
		}else if("Y".equals(numnew)){
			lastH = 1;
		}else if("X".equals(numnew)){
			lastH = 2;
		}else if("W".equals(numnew)){
			lastH = 3;
		}else if("V".equals(numnew)){
			lastH = 4;
		}else if("U".equals(numnew)){
			lastH = 5;
		}else if("T".equals(numnew)){
			lastH = 6;
		}else if("S".equals(numnew)){
			lastH = 7;
		}else if("R".equals(numnew)){
			lastH = 8;
		}else if("Q".equals(numnew)){
			lastH = 9;
		}else  
		lastH = Integer.parseInt(numnew);
		return lastH;
	}
}
