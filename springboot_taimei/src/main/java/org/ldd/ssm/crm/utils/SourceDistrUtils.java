package org.ldd.ssm.crm.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.CityID;
import org.ldd.ssm.crm.domain.SourceDataAll;
import org.ldd.ssm.crm.domain.SourceData_Age;
import org.ldd.ssm.crm.domain.SourceDistriData;
import org.ldd.ssm.crm.domain.Traveler;
import org.ldd.ssm.crm.mapper.SourceDistriMapper;
import org.ldd.ssm.crm.query.SourceDistriDataQuery;

public class SourceDistrUtils {

	public static SourceDataAll factory(String leg,List<Traveler> list2, List<CityID> idcardmappercity) {
		Map<String, List<Traveler>> map2 = new LinkedHashMap<String, List<Traveler>>();
		map2.put("两岁以下", new ArrayList<Traveler>());
		map2.put("2-12岁", new ArrayList<Traveler>());
		map2.put("12-22岁", new ArrayList<Traveler>());
		map2.put("22-45岁", new ArrayList<Traveler>());
		map2.put("45-60岁", new ArrayList<Traveler>());
		map2.put("60岁以上", new ArrayList<Traveler>());
		SourceDataAll all = new SourceDataAll();
		SourceData_Age ages = new SourceData_Age();
		
		int pag = 0;//女数
		
		Map<String, List<Traveler>> map = new HashMap<String, List<Traveler>>();
		for (Traveler traveler : list2) {
			//根据身份证前2位,对旅客分组,获得同一个省份的数据
			if(traveler.getPax_Id_Nbr()==null){
				continue;
			}
			//判断男女
			String pax_Id_Nbr = traveler.getPax_Id_Nbr();
			if(pax_Id_Nbr.length()==18){
				String substring = traveler.getPax_Id_Nbr().substring(0, 2);
				List<Traveler> list_Z = map.get(substring);
				if(list_Z==null){
					List<Traveler> listd = new ArrayList<Traveler>();
					listd.add(traveler);
					map.put(substring, listd);
				}else{
					list_Z.add(traveler);
					map.put(substring, list_Z);
				}
				String substri = pax_Id_Nbr.substring(pax_Id_Nbr.length()-2, pax_Id_Nbr.length()-1);
				if(Integer.valueOf(substri)%2==0){
					pag+=1;
				}
				int date = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
				
				String age_sub = pax_Id_Nbr.substring(pax_Id_Nbr.length()-12, pax_Id_Nbr.length()-4);
				
				int age = date - Integer.valueOf(age_sub);
				if(age<20000){
					List<Traveler> list = map2.get("两岁以下"); //(1)．婴儿期0－3周月； 
					list.add(traveler);
					map2.put("两岁以下", list);
				}else if(age<120000){
					List<Traveler> list = map2.get("2-12岁");//(2)．小儿期4周月—2.5岁;
					list.add(traveler);
					map2.put("2-12岁", list);
				}else if(age<220000){
					List<Traveler> list = map2.get("12-22岁"); //(3)．幼儿期2.5岁后—6岁; 
					list.add(traveler);
					map2.put("12-22岁", list);
				}else if(age<450000){
					List<Traveler> list = map2.get("22-45岁");//(1)．启蒙期7岁—10岁; 
					list.add(traveler);
					map2.put("22-45岁", list);
				}else if(age<=600000){
					List<Traveler> list = map2.get("45-60岁"); //(2)．逆反期11岁—14岁; 
					list.add(traveler);
					map2.put("45-60岁", list);
				}else if(age>600000){
					List<Traveler> list = map2.get("60岁以上");//(3)．成长期15岁—17岁。 
					list.add(traveler);
					map2.put("60岁以上", list);
				}
			}
		}
		List<SourceDistriData> sous = new ArrayList<SourceDistriData>();
		for (Map.Entry<String,List<Traveler>> trave : map.entrySet()) {
			
			SourceDistriData data = new SourceDistriData();
			String key = trave.getKey();//身份证前4位
			int size = trave.getValue().size();
			for (CityID city : idcardmappercity) {
				if(key.equals(city.getNumber())){
					data.setNumber(size);
					data.setCity(city.getCity());
				}
			}
			
			sous.add(data);
		}
		Collections.sort(sous);
		
		all.setSous(sous);
		
		
		for (Map.Entry<String,List<Traveler>> traveler2 : map2.entrySet()) {
			String key = traveler2.getKey();
			int size = traveler2.getValue().size();
			if("两岁以下".equals(key)){
				ages.setAge_1(size+"");
			}else if("2-12岁".equals(key)){
				ages.setAge_2(size+"");
			}else if("12-22岁".equals(key)){
				ages.setAge_3(size+"");
			}else if("22-45岁".equals(key)){
				ages.setAge_4(size+"");
			}else if("45-60岁".equals(key)){
				ages.setAge_5(size+"");
			}else if("60岁以上".equals(key)){
				ages.setAge_6(size+"");
			}
		}
//		BigDecimal divide = new BigDecimal(pag).divide(new BigDecimal(list2.size()),2,BigDecimal.ROUND_HALF_UP);
//		BigDecimal bigDecimal = new BigDecimal(100);
//		BigDecimal multiply = divide.multiply(bigDecimal);
		all.setManNumber(pag+"");
		all.setWumanNumber((list2.size()-pag)+"");
		all.setAge(ages);
		all.setLeg(leg);
		return all;
	}
	
	public static SourceDataAll factory(String leg,List<Traveler> list2, List<CityID> idcardmappercity,Map<String,Object> citymapperprovince) {
		Map<String, Map<String,Object>> map2 = new LinkedHashMap<String, Map<String,Object>>();
		Map<String,Object> map3= new HashMap<String,Object>();
		map3.put("男", 0);
		map3.put("女", 0);
		map2.put("两岁以下", map3);
		map3= new HashMap<String,Object>();
		map3.put("男", 0);
		map3.put("女", 0);
		map2.put("2-12岁", map3);
		map3= new HashMap<String,Object>();
		map3.put("男", 0);
		map3.put("女", 0);
		map2.put("12-22岁", map3);
		map3= new HashMap<String,Object>();
		map3.put("男", 0);
		map3.put("女", 0);
		map2.put("22-45岁", map3);
		map3= new HashMap<String,Object>();
		map3.put("男", 0);
		map3.put("女", 0);
		map2.put("45-60岁", map3);
		map3= new HashMap<String,Object>();
		map3.put("男", 0);
		map3.put("女", 0);
		map2.put("60岁以上", map3);
		SourceDataAll all = new SourceDataAll();
//		SourceData_Age ages = new SourceData_Age();
//		int pag = 0;//女数
		Map<String, List<Traveler>> map = new HashMap<String, List<Traveler>>();
		for (Traveler traveler : list2) {
			//根据身份证前2位,对旅客分组,获得同一个省份的数据
			if(traveler.getPax_Id_Nbr()==null){
				continue;
			}
			//判断男女
			String pax_Id_Nbr = traveler.getPax_Id_Nbr();
			if(pax_Id_Nbr.length()==18){
				String substring = traveler.getPax_Id_Nbr().substring(0, 2);
				List<Traveler> list_Z = map.get(substring);
				if(list_Z==null){
					List<Traveler> listd = new ArrayList<Traveler>();
					listd.add(traveler);
					map.put(substring, listd);
				}else{
					list_Z.add(traveler);
					map.put(substring, list_Z);
				}
				String substri = pax_Id_Nbr.substring(pax_Id_Nbr.length()-2, pax_Id_Nbr.length()-1);
//				if(Integer.valueOf(substri)%2==0){
//					pag+=1;
//				}
				int date = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
				
				String age_sub = pax_Id_Nbr.substring(pax_Id_Nbr.length()-12, pax_Id_Nbr.length()-4);
				
				int age = date - Integer.valueOf(age_sub);
				if(age<20000){
					Map<String,Object> map4 = map2.get("两岁以下");//(1)．婴儿期0－3周月； 
					int man = (int) map4.get("男");
					int woman = (int) map4.get("女");
					if(Integer.valueOf(substri)%2==0){
						woman+=1;
					}else{
						man+=1;
					}
					map4.put("男", man);
					map4.put("女", woman);
					map2.put("两岁以下", map4);
				}else if(age<120000){
					Map<String,Object> map4 = map2.get("2-12岁");//(2)．小儿期4周月—2.5岁;
					int man = (int) map4.get("男");
					int woman = (int) map4.get("女");
					if(Integer.valueOf(substri)%2==0){
						woman+=1;
					}else{
						man+=1;
					}
					map4.put("男", man);
					map4.put("女", woman);
					map2.put("2-12岁", map4);
				}else if(age<220000){
					Map<String,Object> map4 = map2.get("12-22岁"); //(3)．幼儿期2.5岁后—6岁; 
					int man = (int) map4.get("男");
					int woman = (int) map4.get("女");
					if(Integer.valueOf(substri)%2==0){
						woman+=1;
					}else{
						man+=1;
					}
					map4.put("男", man);
					map4.put("女", woman);
					map2.put("12-22岁", map4);
				}else if(age<450000){
					Map<String,Object> map4 = map2.get("22-45岁");//(1)．启蒙期7岁—10岁; 
					int man = (int) map4.get("男");
					int woman = (int) map4.get("女");
					if(Integer.valueOf(substri)%2==0){
						woman+=1;
					}else{
						man+=1;
					}
					map4.put("男", man);
					map4.put("女", woman);
					map2.put("22-45岁", map4);
				}else if(age<=600000){
					Map<String,Object> map4 = map2.get("45-60岁"); //(2)．逆反期11岁—14岁; 
					int man = (int) map4.get("男");
					int woman = (int) map4.get("女");
					if(Integer.valueOf(substri)%2==0){
						woman+=1;
					}else{
						man+=1;
					}
					map4.put("男", man);
					map4.put("女", woman);
					map2.put("45-60岁", map4);
				}else if(age>600000){
					Map<String,Object> map4 = map2.get("60岁以上");//(3)．成长期15岁—17岁。 
					int man = (int) map4.get("男");
					int woman = (int) map4.get("女");
					if(Integer.valueOf(substri)%2==0){
						woman+=1;
					}else{
						man+=1;
					}
					map4.put("男", man);
					map4.put("女", woman);
					map2.put("60岁以上", map4);
				}
			}
		}
		Map<String,Object> dataMap = new HashMap<String,Object>();
		//将旅客划分带对应的城市中
		for (Map.Entry<String,List<Traveler>> trave : map.entrySet()) {
			
			String key = trave.getKey();//身份证前2位
			int size = trave.getValue().size();
			for (CityID city : idcardmappercity) {
				if(key.equals(city.getNumber())){//匹配对应省份对象
					if(size>0){
						@SuppressWarnings("unchecked")
						List<CityID> citys = (List<CityID>) citymapperprovince.get(city.getCity());//根据身份两字码 ,获得城市对象集合
						List<SourceDistriData> sous = new ArrayList<SourceDistriData>();
						//遍历城市集合,找到当前城市下的所有旅客
						for(int i=0;i<citys.size();i++){
							CityID c = citys.get(i);
							int count = 0;//统计旅客总数变量
							SourceDistriData data = new SourceDistriData();
							//遍历旅客集合,使用每个旅客去对应城市编码
							for(int j=0;j<trave.getValue().size();j++){
								//获得身份证前6位
								String substring = trave.getValue().get(j).getPax_Id_Nbr().substring(0,6);
								if(c.getNumber().equals(substring)){
									count++;
								}
							}
							if(count>0){
								data.setCity(c.getCity());
								data.setNumber(count);
								data.setCity_x(c.getCity_x());
								data.setCity_y(c.getCity_y());
								sous.add(data);
							}
						}
						if(sous.size()>0){
//							try{
//								Collections.sort(sous);
//							}catch (Exception e) {
//								e.printStackTrace();
//							}
//							
							dataMap.put(city.getCity(),sous);
						}
					}
				}
			}
		}
		for (Map.Entry<String,Object> cityID : dataMap.entrySet()) {
			@SuppressWarnings("unchecked")
			List<SourceDistriData> list = (List<SourceDistriData>)cityID.getValue();
			//判断是否存在相同的城市名,如果有, 则相加
			for (int i=0; i < list.size() ;i++) {
				SourceDistriData sous = list.get(i);
				int j = i;
				j = j==i?j+1:i;
				for (; j < list.size(); j++) {
					SourceDistriData souy = list.get(j);
					if(sous.getCity().equals(souy.getCity())){
						sous.setNumber(sous.getNumber()+souy.getNumber());
						if(j<0){
							j=0;
						}
						list.remove(j);
						j--;
					}
				}
			}
		}
		all.setLeg(leg);
		all.setDataMap(dataMap);
		all.setPeopleStruct(map2);
		return all;
	}

	public static Map<String,List<Traveler>> factoryByArrv(SourceDistriMapper sourceDistriMapper,SourceDistriDataQuery dataQuery) {
		String getcompanyItia = UserContext.getcompanyItia();
		dataQuery.setItia(getcompanyItia);
		//对数据进行分组
		String dpt_AirPt_Cd = dataQuery.getDpt_AirPt_Cd();
		String pas_stp = dataQuery.getPas_stp();
		String arrv_Airpt_Cd = dataQuery.getArrv_Airpt_Cd();
		String flt_nbr[] = {"",""};//初始化两个长度的数组, 以免后面取值的时候报错
		if(!TextUtil.isEmpty(dataQuery.getFlt_nbr())){
			flt_nbr = dataQuery.getFlt_nbr().split("/");
		}
		dataQuery.setFlt_nbr(flt_nbr[0]);//航班号1
		dataQuery.setName(flt_nbr[1]);//航班号2
		List<String> airlines=sourceDistriMapper.getAllFltNbrList(UserContext.getcompanyItia());
		if(!airlines.isEmpty()){
			dataQuery.setAirlines(airlines);
		}
		List<Traveler> list = sourceDistriMapper.getSourceDistriData(dataQuery);
		
		List<Traveler> list1 = new ArrayList<Traveler>();
		List<Traveler> list2 = new ArrayList<Traveler>();
		List<Traveler> list3 = new ArrayList<Traveler>();
		List<Traveler> list4 = new ArrayList<Traveler>();
		List<Traveler> list5 = new ArrayList<Traveler>();
		List<Traveler> list6 = new ArrayList<Traveler>();
		Map<String,List<Traveler>> map= new LinkedHashMap<String,List<Traveler>>();
		if(!TextUtil.isEmpty(dataQuery.getPas_stp())){
			if(getcompanyItia.equals(dpt_AirPt_Cd)){//出发地
				for (Traveler traveler : list) {
					//从该机场出发到另外两个地点的 单程 和 往返
					//出发地-经停  经停-出发地
					//往返总共的
					if((dpt_AirPt_Cd.equals(traveler.getBk_Ofc_Cd())&&pas_stp.equals(traveler.getRsp_Ofc_Cd()))){
						list1.add(traveler);
						list5.add(traveler);
					}
					if((pas_stp.equals(traveler.getBk_Ofc_Cd())&&dpt_AirPt_Cd.equals(traveler.getRsp_Ofc_Cd()))){
						list2.add(traveler);
						list5.add(traveler);
					}
					// 出发地-到达地   到达地-出发地
					//往返总共的
					if((dpt_AirPt_Cd.equals(traveler.getBk_Ofc_Cd())&&arrv_Airpt_Cd.equals(traveler.getRsp_Ofc_Cd()))){
						list3.add(traveler);
						list6.add(traveler);
					}
					if((arrv_Airpt_Cd.equals(traveler.getBk_Ofc_Cd())&&dpt_AirPt_Cd.equals(traveler.getRsp_Ofc_Cd()))){
						list4.add(traveler);
						list6.add(traveler);
					}
				}
				map.put(dpt_AirPt_Cd+'='+pas_stp, list5);
				map.put(dpt_AirPt_Cd+'-'+pas_stp, list1);
				map.put(pas_stp+'-'+dpt_AirPt_Cd, list2);
				map.put(dpt_AirPt_Cd+'='+arrv_Airpt_Cd, list6);
				map.put(dpt_AirPt_Cd+'-'+arrv_Airpt_Cd, list3);
				map.put(arrv_Airpt_Cd+'-'+dpt_AirPt_Cd, list4);
			}else if(getcompanyItia.equals(pas_stp)){
				for (Traveler traveler : list) {
					if((dpt_AirPt_Cd.equals(traveler.getBk_Ofc_Cd())&&pas_stp.equals(traveler.getRsp_Ofc_Cd()))){
						list1.add(traveler);
						list5.add(traveler);
					}
					if((pas_stp.equals(traveler.getBk_Ofc_Cd())&&dpt_AirPt_Cd.equals(traveler.getRsp_Ofc_Cd()))){
						list2.add(traveler);
						list5.add(traveler);
					}
					
					if((pas_stp.equals(traveler.getBk_Ofc_Cd())&&arrv_Airpt_Cd.equals(traveler.getRsp_Ofc_Cd()))){
						list3.add(traveler);
						list6.add(traveler);
					}
					if((arrv_Airpt_Cd.equals(traveler.getBk_Ofc_Cd())&&pas_stp.equals(traveler.getRsp_Ofc_Cd()))){
						list4.add(traveler);
						list6.add(traveler);
					}
					
				}
				map.put(dpt_AirPt_Cd+'='+pas_stp, list5);
				map.put(dpt_AirPt_Cd+'-'+pas_stp, list1);
				map.put(pas_stp+'-'+dpt_AirPt_Cd, list2);
				map.put(pas_stp+'='+arrv_Airpt_Cd, list6);
				map.put(pas_stp+'-'+arrv_Airpt_Cd, list3);
				map.put(arrv_Airpt_Cd+'-'+pas_stp, list4);
				
			}else if(getcompanyItia.equals(arrv_Airpt_Cd)){
				for (Traveler traveler : list) {
					if((dpt_AirPt_Cd.equals(traveler.getBk_Ofc_Cd())&&arrv_Airpt_Cd.equals(traveler.getRsp_Ofc_Cd()))){
						list1.add(traveler);
						list5.add(traveler);
					}
					if((arrv_Airpt_Cd.equals(traveler.getBk_Ofc_Cd())&&dpt_AirPt_Cd.equals(traveler.getRsp_Ofc_Cd()))){
						list2.add(traveler);
						list5.add(traveler);
					}
					if((pas_stp.equals(traveler.getBk_Ofc_Cd())&&arrv_Airpt_Cd.equals(traveler.getRsp_Ofc_Cd()))){
						list3.add(traveler);
						list6.add(traveler);
					}
					if(((arrv_Airpt_Cd.equals(traveler.getBk_Ofc_Cd())&&pas_stp.equals(traveler.getRsp_Ofc_Cd())))){
						list4.add(traveler);
						list6.add(traveler);
					}
				}
				map.put(dpt_AirPt_Cd+'='+arrv_Airpt_Cd, list5);
				map.put(dpt_AirPt_Cd+'-'+arrv_Airpt_Cd, list1);
				map.put(arrv_Airpt_Cd+'-'+dpt_AirPt_Cd, list2);
				map.put(pas_stp+'='+arrv_Airpt_Cd, list6);
				map.put(pas_stp+'-'+arrv_Airpt_Cd, list3);
				map.put(arrv_Airpt_Cd+'-'+pas_stp, list4);
			}
		}else{
			for (Traveler traveler : list) {
				//从该机场出发到另外两个地点的 单程 和 往返
				//出发地-经停  经停-出发地
				//往返总共的
				if((dpt_AirPt_Cd.equals(traveler.getBk_Ofc_Cd())&&arrv_Airpt_Cd.equals(traveler.getRsp_Ofc_Cd()))){
					list1.add(traveler);
					list5.add(traveler);
				}
				if((arrv_Airpt_Cd.equals(traveler.getBk_Ofc_Cd())&&dpt_AirPt_Cd.equals(traveler.getRsp_Ofc_Cd()))){
					list2.add(traveler);
					list5.add(traveler);
				}
			}
			map.put(dpt_AirPt_Cd+'='+arrv_Airpt_Cd, list5);
			map.put(dpt_AirPt_Cd+'-'+arrv_Airpt_Cd, list1);
			map.put(arrv_Airpt_Cd+'-'+dpt_AirPt_Cd, list2);
		}
		return map;
	}

}
