package org.ldd.ssm.crm.service.impl;

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
import org.ldd.ssm.crm.domain.SourceDistriData;
import org.ldd.ssm.crm.domain.Traveler;
import org.ldd.ssm.crm.mapper.SourceDistriMapper;
import org.ldd.ssm.crm.query.SourceDistriDataQuery;
import org.ldd.ssm.crm.service.ISourceDistriService;
import org.ldd.ssm.crm.utils.SourceDistrUtils;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SourceDistriServiceImpl implements ISourceDistriService {
	@Autowired 
	private SourceDistriMapper sourceDistriMapper;
	
	@Override
	public List<SourceDataAll> getSourceDistriData(SourceDistriDataQuery dataQuery) {
		List<CityID> idcardmappercity = sourceDistriMapper.getIdcardmapperprovince();
		//对数据进行分组 key为航段
		Map<String,List<Traveler>> list_data = new HashMap<String,List<Traveler>>();
		List<SourceDataAll> alls = new ArrayList<SourceDataAll>();
		list_data = SourceDistrUtils.factoryByArrv(sourceDistriMapper,dataQuery);
		 for (Map.Entry<String, List<Traveler>> m :list_data.entrySet())  {  
	            if(m.getValue()!=null&&m.getValue().size()>0){
					SourceDataAll all = SourceDistrUtils.factory(m.getKey(),m.getValue(),idcardmappercity);
					//添加进集合中
					alls.add(all);
				}
	     }  
		return alls;
	}

	@Override
	public List<SourceDistriData> getSourceDistriDataByName(SourceDistriDataQuery dataQuery) {
		//查到省份的身份证号
		CityID idcardmapperProvince = sourceDistriMapper.getIdcardmapperProvinceByF(dataQuery.getName());
		//查询省份中的旅人
		dataQuery.setName(idcardmapperProvince.getNumber());
		List<Traveler> sourceDistriDataByF = sourceDistriMapper.getSourceDistriDataByF(dataQuery);
		//查到省份中的所有市级的身份证号
		List<CityID> idcardmappercity = sourceDistriMapper.getIdcardmapperCityByF(dataQuery.getName());
		Map<String, List<Traveler>> map = new HashMap<String, List<Traveler>>();
		if(sourceDistriDataByF.size()>0){
			for (Traveler traveler : sourceDistriDataByF) {
				//根据身份证前2位,对旅客分组,获得同一个省份的数据
				if(traveler.getPax_Id_Nbr()==null){
					continue;
				}
				//判断男女
				String pax_Id_Nbr = traveler.getPax_Id_Nbr();
				if(pax_Id_Nbr.length()==18){
					String substring = traveler.getPax_Id_Nbr().substring(0, 4);
					List<Traveler> list_Z = map.get(substring);
					if(list_Z==null){
						List<Traveler> listd = new ArrayList<Traveler>();
						listd.add(traveler);
						map.put(substring, listd);
					}else{
						list_Z.add(traveler);
						map.put(substring, list_Z);
					}
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
					sous.add(data);
				}
			}
			
		}
		Collections.sort(sous);
		return sous;
	}
	public List<SourceDataAll> getCustomersInfo(SourceDistriDataQuery dataQuery){
		try {
			List<CityID> idcardmapperprovice = sourceDistriMapper.getIdcardmapperprovince();//获取所有省份
			List<CityID> idcardmappercity = sourceDistriMapper.getIdcardmappercity();//获取所有城市
			Map<String,Object> citymapperprovince = new HashMap<String,Object>();
			if("no".equals(dataQuery.getIsIncludePas())){
				dataQuery.setIsIncludePas(null);
			}
			if("no".equals(dataQuery.getIsIncludeRdf())){
				dataQuery.setIsIncludeRdf(null);
			}
			//对省份和城市进行分组
			for(int i=0;i<idcardmapperprovice.size();i++){
				CityID o = idcardmapperprovice.get(i);
				List<CityID> currArray = new ArrayList<CityID>();
				for(int j=0;j<idcardmappercity.size();j++){
					CityID obj = idcardmappercity.get(j);
					if(o.getNumber().equals(obj.getNumber().substring(0,2))){
						currArray.add(obj);
					}
				}
				citymapperprovince.put(o.getCity(), currArray);
			}
			//对数据进行分组
			Map<String,List<Traveler>> list_data = new LinkedHashMap<String,List<Traveler>>();
			List<SourceDataAll> alls = new ArrayList<SourceDataAll>();
			//对查询条件和查询数据进行封装
			list_data = SourceDistrUtils.factoryByArrv(sourceDistriMapper,dataQuery);//在该函数里判断 是否中停 
			//遍历航段的数据
			for (Map.Entry<String, List<Traveler>> m :list_data.entrySet()){  
	            if(m.getValue()!=null&&m.getValue().size()>0){
					SourceDataAll all = SourceDistrUtils.factory(m.getKey(),m.getValue(),idcardmapperprovice,citymapperprovince);
					//添加进集合中
					alls.add(all);
				}
			}
			return alls;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getFltNbrList(SourceDistriDataQuery dataQuery) {
		if("no".equals(dataQuery.getIsIncludePas())){
			dataQuery.setIsIncludePas(null);
		}
		if("no".equals(dataQuery.getIsIncludeRdf())){
			dataQuery.setIsIncludeRdf(null);
		}
		List<String> fltList = sourceDistriMapper.getFltNbrList(dataQuery);
		return fltList;
	}

	@Override
	public String getCustomerNewDate(SourceDistriDataQuery dataQuery) {
		if("no".equals(dataQuery.getIsIncludePas())){
			dataQuery.setIsIncludePas(null);
		}
		if("no".equals(dataQuery.getIsIncludeRdf())){
			dataQuery.setIsIncludeRdf(null);
		}
		List<String> airlines=sourceDistriMapper.getAllFltNbrList(UserContext.getcompanyItia());
		if(!airlines.isEmpty()){
			dataQuery.setAirlines(airlines);
		}
		String str = sourceDistriMapper.getCustomerNewDate(dataQuery);
		if(TextUtil.isEmpty(str)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(new Date());
		}else{
			return str;
		}
		
	}
	
	
}
