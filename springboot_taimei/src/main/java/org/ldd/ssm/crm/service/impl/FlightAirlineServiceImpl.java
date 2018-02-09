package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.ldd.ssm.crm.domain.FlightAirline;
import org.ldd.ssm.crm.mapper.FlightAirlineMapper;
import org.ldd.ssm.crm.query.FlightAirlineQuery;
import org.ldd.ssm.crm.service.FlightAirlineService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightAirlineServiceImpl implements FlightAirlineService {
	@Autowired
	FlightAirlineMapper flightAirlineMapper;
	@Override
	public Map<String,List<FlightAirline>> getFlightAirlineData(FlightAirlineQuery flightAirlineQuery) {
		//将传入的参数的三字码转为四字码
		flightAirlineQuery=flightAirlineMapper.translateIataToIcao(flightAirlineQuery);
		Map<String,List<FlightAirline>>  retMap = new HashMap<String, List<FlightAirline>>();
		String stratCity = flightAirlineQuery.getStratCity();
		String endCity = flightAirlineQuery.getEndCity();
		String pasCity1 = flightAirlineQuery.getPasCity1();
		String pasCity2 = flightAirlineQuery.getPasCity2();
		//组装有多少条线
	 	List<String> linesgo = new ArrayList<String>();
	 	List<String> linesback = new ArrayList<String>();
		if(TextUtil.isEmpty(pasCity1)){
			pasCity1 = "";
			flightAirlineQuery.setPasCity1(null);
		}
		if(TextUtil.isEmpty(pasCity2)){
			pasCity2 = "";
			flightAirlineQuery.setPasCity2(null);
		}
		if(!TextUtil.isEmpty(stratCity)&&!TextUtil.isEmpty(endCity)){
			//去程
			List<FlightAirline> flightAirlineList1 = flightAirlineMapper.getFlightAirlineData(flightAirlineQuery);
			//航线分组
			List<String> listgo1 = new ArrayList<String>();
			List<String> listgo2 = new ArrayList<String>();
			List<String> listgo3 = new ArrayList<String>();
			for (FlightAirline flightAirline : flightAirlineList1) {
				if(TextUtil.isEmpty(pasCity1)&&TextUtil.isEmpty(pasCity2)){
					if(flightAirline.getStratCity().equals(stratCity)&&flightAirline.getEndCity().equals(endCity)){
						listgo1.add(flightAirline.getStratCityName()+"-"+flightAirline.getEndCityName()+"-"+flightAirline.getFlightAirlineId()+"-"+flightAirline.getAirLineDistance());
					}
				}else{
					if(!TextUtil.isEmpty(pasCity1)&&TextUtil.isEmpty(pasCity2)){
						if(flightAirline.getStratCity().equals(stratCity)&&flightAirline.getEndCity().equals(pasCity1)){
							listgo1.add(flightAirline.getStratCityName()+"-"+flightAirline.getEndCityName()+"-"+flightAirline.getFlightAirlineId()+"-"+flightAirline.getAirLineDistance());
						}
						if(flightAirline.getStratCity().equals(pasCity1)&&flightAirline.getEndCity().equals(endCity)){
							listgo2.add(flightAirline.getStratCityName()+"-"+flightAirline.getEndCityName()+"-"+flightAirline.getFlightAirlineId()+"-"+flightAirline.getAirLineDistance());
						}
					}else{
						if(!TextUtil.isEmpty(pasCity1)&&!TextUtil.isEmpty(pasCity2)){
							if(flightAirline.getStratCity().equals(stratCity)&&flightAirline.getEndCity().equals(pasCity1)){
								listgo1.add(flightAirline.getStratCityName()+"-"+flightAirline.getEndCityName()+"-"+flightAirline.getFlightAirlineId()+"-"+flightAirline.getAirLineDistance());
							}
							if(flightAirline.getStratCity().equals(pasCity1)&&flightAirline.getEndCity().equals(pasCity2)){
								listgo2.add(flightAirline.getStratCityName()+"-"+flightAirline.getEndCityName()+"-"+flightAirline.getFlightAirlineId()+"-"+flightAirline.getAirLineDistance());
							}
							if(flightAirline.getStratCity().equals(pasCity2)&&flightAirline.getEndCity().equals(endCity)){
								listgo3.add(flightAirline.getStratCityName()+"-"+flightAirline.getEndCityName()+"-"+flightAirline.getFlightAirlineId()+"-"+flightAirline.getAirLineDistance());
							}
						}
					}
				}
			}
			if(listgo1.size()>0&&listgo2.size()==0&&listgo3.size()==0){
				for (String string : listgo1) {
					linesgo.add(string);
				}
			}else{
				if(listgo1.size()>0&&listgo2.size()>0&&listgo3.size()==0){
					for (String string1 : listgo1) {
						for (String string2 : listgo2) {
							linesgo.add(string1+","+string2);
						}
					}
				}else{
					if(listgo1.size()>0&&listgo2.size()>0&&listgo3.size()>0){
						for (String string1 : listgo1) {
							for (String string2 : listgo2) {
								for (String string3 : listgo3) {
									linesgo.add(string1+","+string2+","+string3);
								}
							}
						}
					}
				}
			}
			if(flightAirlineList1!=null&&flightAirlineList1.size()!=0){
				List<String> flightIds = new ArrayList<String>();
				List<String> pointIds = new ArrayList<String>();
				List<String> pointIds2 = new ArrayList<String>();
				for (FlightAirline flightAirline : flightAirlineList1) {
					flightIds.add(flightAirline.getFlightAirlineId());
					if("机场".equals(flightAirline.getStartType())){
						pointIds.add(flightAirline.getPointId());
					}else{
						pointIds2.add(flightAirline.getPointId());
					}
					if("机场".equals(flightAirline.getEndType())){
						pointIds.add(flightAirline.getPointId2());
					}else{
						pointIds2.add(flightAirline.getPointId2());
					}
				}
				List<FlightAirline> guanxiList = flightAirlineMapper.getGuanXiData(flightIds);
				List<String> guanxiIds = new ArrayList<String>();
				for (FlightAirline flightAirline : guanxiList) {
					guanxiIds.add(flightAirline.getPointId());
				}
				//找到所有途径点的坐标 名字
				List<FlightAirline> pointList = flightAirlineMapper.getPointData(guanxiIds);
				//得到起始的point
				List<FlightAirline> pointListCity = new ArrayList<FlightAirline>();
				if(pointIds.size()>0){
					List<FlightAirline> pointListCity1 = flightAirlineMapper.getAirportData(pointIds);
					pointListCity.addAll(pointListCity1);
				}
				if(pointIds2.size()>0){
					List<FlightAirline> pointListCity2 = flightAirlineMapper.getPointData(pointIds2);
					pointListCity.addAll(pointListCity2);
				}
				for (FlightAirline flightAirline0 : flightAirlineList1) {
					List<FlightAirline> tempList = new ArrayList<FlightAirline>();
					for (FlightAirline flightAirline1 : guanxiList) {
						for (FlightAirline flightAirline2 : pointList) {
							if(flightAirline0.getFlightAirlineId().equals(flightAirline1.getFlightAirlineId())&&flightAirline1.getPointId().equals(flightAirline2.getPointId())){
								flightAirline2.setIndexx(flightAirline1.getIndexx());
								FlightAirline f = new FlightAirline();
								try {
									f = (FlightAirline)flightAirline2.clone();
								} catch (CloneNotSupportedException e) {
									e.printStackTrace();
								}
								tempList.add(f);
							} 
						}
					}
					for (FlightAirline flightAirline : pointListCity) {
						if(flightAirline.getPointId().equals(flightAirline0.getPointId())){
							flightAirline.setStratCity(flightAirline0.getStratCity());
							//起点放在第一个
							flightAirline.setIndexx(0+"");
							//flightAirline.setStratCity(flightAirline0.getStratCity());
							FlightAirline f = new FlightAirline();
							try {
								f = (FlightAirline)flightAirline.clone();
								tempList.add(0,f);
							} catch (CloneNotSupportedException e) {
								e.printStackTrace();
							}
						}
						if(flightAirline.getPointId().equals(flightAirline0.getPointId2())){
							//flightAirline.setStratCity(flightAirline0.getStratCity());
							//终点放在最后一个
							flightAirline.setIndexx(tempList.size()+1+"");
							flightAirline.setStratCity(flightAirline0.getEndCity());
							FlightAirline f = new FlightAirline();
							try {
								f = (FlightAirline)flightAirline.clone();
								tempList.add(f);
							} catch (CloneNotSupportedException e) {
								e.printStackTrace();
							}
						}
					}
					TextUtil.sortFlightAirline(tempList,"indexx","ASC");
					retMap.put(flightAirline0.getStratCityName()+"-"+flightAirline0.getEndCityName()+"-"+/*+flightAirline0.getFlightAirlineId()+*/"go"+"-"+flightAirline0.getAirLineDistance(), tempList);
				}
			}
			
			//返程
			flightAirlineQuery.setStratCity(endCity);
			flightAirlineQuery.setPasCity1(pasCity2);
			flightAirlineQuery.setPasCity2(pasCity1);
			flightAirlineQuery.setEndCity(stratCity);
			if(TextUtil.isEmpty(pasCity2)){
				flightAirlineQuery.setPasCity1(null);
			}
			if(TextUtil.isEmpty(pasCity1)){
				flightAirlineQuery.setPasCity2(null);
			}
			List<FlightAirline> flightAirlineList1h = flightAirlineMapper.getFlightAirlineData(flightAirlineQuery);
			
			//航线分组
			List<String> listgo1h = new ArrayList<String>();
			List<String> listgo2h = new ArrayList<String>();
			List<String> listgo3h = new ArrayList<String>();
			for (FlightAirline flightAirline : flightAirlineList1h) {
				if(TextUtil.isEmpty(pasCity1)&&TextUtil.isEmpty(pasCity2)){
					if(flightAirline.getStratCity().equals(endCity)&&flightAirline.getEndCity().equals(stratCity)){
						listgo1h.add(flightAirline.getStratCityName()+"-"+flightAirline.getEndCityName()+"-"+flightAirline.getFlightAirlineId()+"-"+flightAirline.getAirLineDistance());
					}
				}else{
					if(!TextUtil.isEmpty(pasCity1)&&TextUtil.isEmpty(pasCity2)){
						if(flightAirline.getStratCity().equals(pasCity1)&&flightAirline.getEndCity().equals(stratCity)){
							listgo1h.add(flightAirline.getStratCityName()+"-"+flightAirline.getEndCityName()+"-"+flightAirline.getFlightAirlineId()+"-"+flightAirline.getAirLineDistance());
						}
						if(flightAirline.getStratCity().equals(endCity)&&flightAirline.getEndCity().equals(pasCity1)){
							listgo2h.add(flightAirline.getStratCityName()+"-"+flightAirline.getEndCityName()+"-"+flightAirline.getFlightAirlineId()+"-"+flightAirline.getAirLineDistance());
						}
					}else{
						if(!TextUtil.isEmpty(pasCity1)&&!TextUtil.isEmpty(pasCity2)){
							if(flightAirline.getStratCity().equals(pasCity1)&&flightAirline.getEndCity().equals(stratCity)){
								listgo1h.add(flightAirline.getStratCityName()+"-"+flightAirline.getEndCityName()+"-"+flightAirline.getFlightAirlineId()+"-"+flightAirline.getAirLineDistance());
							}
							if(flightAirline.getStratCity().equals(pasCity2)&&flightAirline.getEndCity().equals(pasCity1)){
								listgo2h.add(flightAirline.getStratCityName()+"-"+flightAirline.getEndCityName()+"-"+flightAirline.getFlightAirlineId()+"-"+flightAirline.getAirLineDistance());
							}
							if(flightAirline.getStratCity().equals(endCity)&&flightAirline.getEndCity().equals(pasCity2)){
								listgo3h.add(flightAirline.getStratCityName()+"-"+flightAirline.getEndCityName()+"-"+flightAirline.getFlightAirlineId()+"-"+flightAirline.getAirLineDistance());
							}
						}
					}
				}
			}
			if(listgo1h.size()>0&&listgo2h.size()==0&&listgo3h.size()==0){
				for (String string : listgo1h) {
					linesback.add(string);
				}
			}else{
				if(listgo1h.size()>0&&listgo2h.size()>0&&listgo3h.size()==0){
					for (String string1 : listgo1h) {
						for (String string2 : listgo2h) {
							linesback.add(string2+","+string1);
						}
					}
				}else{
					if(listgo1h.size()>0&&listgo2h.size()>0&&listgo3h.size()>0){
						for (String string1 : listgo1h) {
							for (String string2 : listgo2h) {
								for (String string3 : listgo3h) {
									linesback.add(string3+","+string2+","+string1);
								}
							}
						}
					}
				}
			}
			
			if(flightAirlineList1h!=null&&flightAirlineList1h.size()!=0){
				List<String> flightIdsh = new ArrayList<String>();
				List<String> pointIds1h = new ArrayList<String>();
				List<String> pointIds2h = new ArrayList<String>();
				for (FlightAirline flightAirline : flightAirlineList1h) {
					flightIdsh.add(flightAirline.getFlightAirlineId());
					if("机场".equals(flightAirline.getStartType())){
						pointIds1h.add(flightAirline.getPointId());
					}else{
						pointIds2h.add(flightAirline.getPointId());
					}
					if("机场".equals(flightAirline.getEndType())){
						pointIds1h.add(flightAirline.getPointId2());
					}else{
						pointIds2h.add(flightAirline.getPointId2());
					}
				}
				List<FlightAirline> guanxiListh = flightAirlineMapper.getGuanXiData(flightIdsh);
				List<String> guanxiIdsh = new ArrayList<String>();
				for (FlightAirline flightAirline : guanxiListh) {
					guanxiIdsh.add(flightAirline.getPointId());
				}
				List<FlightAirline> pointListh = flightAirlineMapper.getPointData(guanxiIdsh);
				//得到起始的point
				List<FlightAirline> pointListCityh = new ArrayList<FlightAirline>();
				if(pointIds1h.size()>0){
					List<FlightAirline> pointListCity1h = flightAirlineMapper.getAirportData(pointIds1h);
					pointListCityh.addAll(pointListCity1h);
				}
				if(pointIds2h.size()>0){
					List<FlightAirline> pointListCity2h = flightAirlineMapper.getPointData(pointIds2h);
					pointListCityh.addAll(pointListCity2h);
				}
				for (FlightAirline flightAirline0 : flightAirlineList1h) {
					List<FlightAirline> tempList = new ArrayList<FlightAirline>();
					for (FlightAirline flightAirline1 : guanxiListh) {
						for (FlightAirline flightAirline2 : pointListh) {
							if(flightAirline0.getFlightAirlineId().equals(flightAirline1.getFlightAirlineId())&&flightAirline1.getPointId().equals(flightAirline2.getPointId())){
								flightAirline2.setIndexx(flightAirline1.getIndexx());
								FlightAirline f = new FlightAirline();
								try {
									f = (FlightAirline)flightAirline2.clone();
								} catch (CloneNotSupportedException e) {
									e.printStackTrace();
								}
								tempList.add(f);
							} 
						}
					}
					for (FlightAirline flightAirline : pointListCityh) {
						if(flightAirline.getPointId().equals(flightAirline0.getPointId())){
							flightAirline.setStratCity(flightAirline0.getStratCity());
							flightAirline.setIndexx(0+"");
							flightAirline.setStratCity(flightAirline0.getStratCity());
							FlightAirline f = new FlightAirline();
							try {
								f = (FlightAirline)flightAirline.clone();
								tempList.add(0,f);
							} catch (CloneNotSupportedException e) {
								e.printStackTrace();
							}
						}
						if(flightAirline.getPointId().equals(flightAirline0.getPointId2())){
							flightAirline.setStratCity(flightAirline0.getStratCity());
							flightAirline.setIndexx(tempList.size()+1+"");
							flightAirline.setStratCity(flightAirline0.getEndCity());
							FlightAirline f = new FlightAirline();
							try {
								f = (FlightAirline)flightAirline.clone();
								tempList.add(f);
							} catch (CloneNotSupportedException e) {
								e.printStackTrace();
							}
						}
					}
					TextUtil.sortFlightAirline(tempList,"indexx","ASC");
					retMap.put(flightAirline0.getStratCityName()+"-"+flightAirline0.getEndCityName()+"-"+/*flightAirline0.getFlightAirlineId()+*/"back"+"-"+flightAirline0.getAirLineDistance(), tempList);
				}
			}
			}
		 Map<String, List<FlightAirline>> rMap = new HashMap<String, List<FlightAirline>>();
		 for (Entry<String, List<FlightAirline>> m :retMap.entrySet())  {  
	           String key = m.getKey();
 	           List<FlightAirline> fflist = m.getValue();  
	           List<FlightAirline> fflisttemp = new ArrayList<FlightAirline>();
	           for (int i = 0;i<(fflist.size()-1);i++) {
	        	   FlightAirline flightAirline = new FlightAirline();
	        	   flightAirline.setStratCity(fflist.get(i).getStratCity());
	        	   flightAirline.setStratCityPoit1(fflist.get(i).getStratCityPoit1());
	        	   flightAirline.setStratCityPoit2(fflist.get(i).getStratCityPoit2());
	        	   flightAirline.setEndCity(fflist.get(i+1).getStratCity());
	        	   flightAirline.setEndCityCityPoit1(fflist.get(i+1).getStratCityPoit1());
	        	   flightAirline.setEndCityCityPoit2(fflist.get(i+1).getStratCityPoit2());
	        	   fflisttemp.add(flightAirline);
	           }
	           //成都-沈阳 站点列表 去掉成都和沈阳
	           rMap.put(key, fflisttemp);
	       } 
			//A-B B-c 合并为A-B-C
		     Map<String, List<FlightAirline>> tMap = new HashMap<String, List<FlightAirline>>();
		     int idn = 1;
		     for (String str : linesgo) {
		    	 List<FlightAirline> tt = new ArrayList<FlightAirline>();
		    	 for(String key : rMap.keySet()){ 
		    		 String [] airline = key.split("-");
		    		 if("go".equals(airline[3])){
		    			 if(str.contains(airline[0]+"-"+airline[1]+"-"+airline[2])){
		    				 tt.addAll(rMap.get(key));
		    			 }
		    		 }
		    	 } 
		    	 String [] arr = str.split(",");
		    	 String keytemp = "";
		    	 if(arr.length==1){
		    		 keytemp = arr[0].split("-")[0]+"-"+arr[0].split("-")[1]+"-go"+"-"+idn+"-"+arr[0].split("-")[3];
		    	 }
		    	 if(arr.length==2){
		    		 keytemp = arr[0].split("-")[0]+"-"+arr[0].split("-")[1]+"-"+arr[1].split("-")[1]+"-go"+"-"+idn+"-"+(Integer.parseInt(arr[0].split("-")[3])+Integer.parseInt(arr[1].split("-")[3]));
		    	 }
		    	 if(arr.length==3){
		    		 keytemp = arr[0].split("-")[0]+"-"+arr[0].split("-")[1]+"-"+arr[1].split("-")[1]+"-"+arr[2].split("-")[1]+"-go"+"-"+idn+"-"+(Integer.parseInt(arr[0].split("-")[3])+Integer.parseInt(arr[1].split("-")[3])+Integer.parseInt(arr[2].split("-")[3]));
		    	 }
		    	 tMap.put(keytemp, tt);
		    	 idn ++;
			}
		     int idnh = 1;
		     for (String str : linesback) {
		    	 List<FlightAirline> tt = new ArrayList<FlightAirline>();
		    	 for(String key : rMap.keySet()){ 
		    		 String [] airline = key.split("-");
		    		 if("back".equals(airline[3])){
		    			 if(str.contains(airline[0]+"-"+airline[1]+"-"+airline[2])){
		    				 tt.addAll(rMap.get(key));
		    			 }
		    		 }
		    	 } 
		    	 String [] arr = str.split(",");
		    	 String keytemp = "";
		    	 if(arr.length==1){
		    		 keytemp = arr[0].split("-")[0]+"-"+arr[0].split("-")[1]+"-back"+"-"+idnh+"-"+arr[0].split("-")[3];
		    	 }
		    	 if(arr.length==2){
		    		 keytemp = arr[0].split("-")[0]+"-"+arr[0].split("-")[1]+"-"+arr[1].split("-")[1]+"-back"+"-"+idnh+"-"+(Integer.parseInt(arr[0].split("-")[3])+Integer.parseInt(arr[1].split("-")[3]));
		    	 }
		    	 if(arr.length==3){
		    		 keytemp = arr[0].split("-")[0]+"-"+arr[0].split("-")[1]+"-"+arr[1].split("-")[1]+"-"+arr[2].split("-")[1]+"-back"+"-"+idnh+"-"+(Integer.parseInt(arr[0].split("-")[3])+Integer.parseInt(arr[1].split("-")[3])+Integer.parseInt(arr[2].split("-")[3]));
		    	 }
		    	 tMap.put(keytemp, tt);
		    	 idnh ++;
			}
		     return rMap;//返回单个航段
		     //return tMap;//返回整条航线
	}
	@Override
	public Map<String, List<FlightAirline>> getAllFlightAirlineData() {
		List<FlightAirline> flightAirlineAllList = flightAirlineMapper.getAllFlightAirlineData();
		Map<String, List<FlightAirline>> retMap = new HashMap<String, List<FlightAirline>>();
		 Map<String, List<FlightAirline>> tMap = new HashMap<String, List<FlightAirline>>();
		List<String> airwayIdList = new ArrayList<String>();
		if(flightAirlineAllList!=null&&flightAirlineAllList.size()>0){
			for (FlightAirline flightAirline : flightAirlineAllList) {
				String airwayId = flightAirline.getAirwayId();
				String indentify = flightAirline.getAirwayIndentify();
				if(!airwayIdList.contains(airwayId)&&!TextUtil.isEmpty(indentify)&&((indentify.subSequence(0, 1).equals("h")||indentify.subSequence(0, 1).equals("H"))||(indentify.subSequence(0, 1).equals("j")||indentify.subSequence(0, 1).equals("J"))||(indentify.subSequence(0, 1).equals("Z")||indentify.subSequence(0, 1).equals("z"))||(indentify.subSequence(0, 1).equals("X")||indentify.subSequence(0, 1).equals("x")))){
					airwayIdList.add(airwayId);
				}
			}
		}
		
		if(flightAirlineAllList!=null&&flightAirlineAllList.size()>0){
			for (String id : airwayIdList) {
				for (FlightAirline flightAirline : flightAirlineAllList) {
					String airwayId = flightAirline.getAirwayId();
					if(id.equals(airwayId)){
						List<FlightAirline> flightAirlinetemplist = retMap.get(airwayId);
						if(flightAirlinetemplist!=null&&flightAirlinetemplist.size()>0){
							flightAirlinetemplist.add(flightAirline);
						}else{
							flightAirlinetemplist = new ArrayList<FlightAirline>();
							flightAirlinetemplist.add(flightAirline);
							retMap.put(airwayId, flightAirlinetemplist);
						}
					}
				}
			}
		}
	 for (Entry<String, List<FlightAirline>> m :retMap.entrySet())  {  
           String key = m.getKey();
           String indetify = "";
           List<FlightAirline> fflist = m.getValue();  
           String airline = fflist.get(0).getAirwayName()+"-"+fflist.get(fflist.size()-1).getAirwayName();
           List<FlightAirline> fflisttemp = new ArrayList<FlightAirline>();
           for (int i = 0;i<(fflist.size()-1);i++) {
        	   indetify = fflist.get(i).getAirwayIndentify();
        	   FlightAirline flightAirline = new FlightAirline();
        	   flightAirline.setStratCity(fflist.get(i).getAirwayName());
        	   flightAirline.setStratCityPoit1(fflist.get(i).getAirwayLat());
        	   flightAirline.setStratCityPoit2(fflist.get(i).getAirwayLong());
        	   flightAirline.setEndCity(fflist.get(i+1).getAirwayName());
        	   flightAirline.setEndCityCityPoit1(fflist.get(i+1).getAirwayLat());
        	   flightAirline.setEndCityCityPoit2(fflist.get(i+1).getAirwayLong());
        	   fflisttemp.add(flightAirline);
           }
           tMap.put(key+"--"+indetify+"--"+airline, fflisttemp);
       } 
		return tMap;
	}
	
}