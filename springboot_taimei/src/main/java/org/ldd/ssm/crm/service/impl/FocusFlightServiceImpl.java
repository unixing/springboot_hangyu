package org.ldd.ssm.crm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.FocusFlight;
import org.ldd.ssm.crm.mapper.FocusFlightMapper;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.service.IFocusFlightService;
import org.ldd.ssm.crm.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class FocusFlightServiceImpl implements IFocusFlightService{
	Logger log = Logger.getLogger(FocusFlightServiceImpl.class);
	@Autowired
	private FocusFlightMapper objMapper;
	@Autowired
	private OutPortMapper outPortMapper;
	
	@Override
	public boolean save(List<FocusFlight> flts) {
		boolean result = false;
		try {
			if(flts!=null&&flts.size()>0){
				for(FocusFlight fft:flts){
					if(fft.getFlightNumber()==null||"".equals(fft.getFlightNumber())){
						continue;
					}
					fft.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
					objMapper.insertSelective(fft);
				}
			}
			result = true;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return result;
		}
		return result;
	}

	@Override
	public boolean update(List<FocusFlight> flts,Long employeeId) {
		boolean result = false;
		try {
			//删除原来的纪录
//			objMapper.deleteByEmployeeId(employeeId);
			//获取当前航季起止日期
			String[] seasonDate = DataUtils.getCurrentSeasonDate();
			if(flts!=null&&flts.size()>0){
				for(int i=0;i<flts.size();i++){
					if(flts.get(i).getFlightNumber()==null||"".equals(flts.get(i).getFlightNumber())){
						objMapper.delete(flts.get(i));
						continue;
					}
					FocusFlight obj = objMapper.load(flts.get(i),seasonDate[0],seasonDate[1]);
					if(obj!=null){
						objMapper.update(flts.get(i));
					}else{
						flts.get(i).setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
						objMapper.insertSelective(flts.get(i));
					}
				}
				return result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> getFocusFlight(Long employeeId) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取当前航季起止日期
			String[] seasonDate = DataUtils.getCurrentSeasonDate();
			List<FocusFlight> list = objMapper.getFocusFlight(employeeId,seasonDate[0],seasonDate[1]);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					FocusFlight flt = list.get(i);
					String fltroute = (flt.getPasAirport()==null||"".equals(flt.getPasAirport()))?(flt.getDptAirport()+"="+flt.getArrvAirport()):(flt.getDptAirport()+"="+flt.getPasAirport()+"="+flt.getArrvAirport());
					map.put(fltroute, Arrays.asList(flt.getFlightNumber().split(",")));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return map;
		}
		return map;
	}

	@Override
	public List<String> getFocusFlightList(Long employeeId) {
		if(employeeId==null||employeeId.longValue()==0){
			log.debug("getFocusFlightLisg:employeeId is invalid");
			return null;
		}
		List<String> fltList = null;
		try {
			//获取当前航季起止日期
			String[] seasonDate = DataUtils.getCurrentSeasonDate();
			List<FocusFlight> list = objMapper.getFocusFlight(employeeId,seasonDate[0],seasonDate[1]);
			int size = list.size();
			if(list!=null&&size>0){
				fltList = new ArrayList<String>();
				for(int i=0;i<size;i++){
					String flts = list.get(i).getFlightNumber();
					String[] fltArray = flts.split(",");
					if(fltArray!=null&&fltArray.length>0){
						for(String fltss :fltArray){
							String[] fltArrays = fltss.split("/");
							String commonStr = fltArrays[0].substring(0,fltArrays[0].length()-fltArrays[1].length());
							fltList.add(fltArrays[0]);
							fltList.add(commonStr+fltArrays[1]);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
		return fltList;
	}

	@Override
	public List<String> getFlightRouteList(Long employeeId) {
		if(employeeId==null||employeeId.longValue()==0){
			log.debug("getFocusFlightLisg:employeeId is invalid");
			return null;
		}
		List<String> fltList = null;
		try {
			String[] seasonDate = DataUtils.getCurrentSeasonDate();
			List<FocusFlight> list = objMapper.getFocusFlight(employeeId,seasonDate[0],seasonDate[1]);
			int size = list.size();
			if(list!=null&&size>0){
				fltList = new ArrayList<String>();
				for(int i=0;i<size;i++){
					FocusFlight obj = list.get(i);
					if(obj.getPasAirport()==null||"".equals(obj.getPasAirport())){
						fltList.add(obj.getDptAirport()+obj.getArrvAirport());
						fltList.add(obj.getArrvAirport()+obj.getDptAirport());
					}else{
						fltList.add(obj.getDptAirport()+obj.getPasAirport()+obj.getArrvAirport());
						fltList.add(obj.getArrvAirport()+obj.getPasAirport()+obj.getDptAirport());
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
		return fltList;
	}

}
