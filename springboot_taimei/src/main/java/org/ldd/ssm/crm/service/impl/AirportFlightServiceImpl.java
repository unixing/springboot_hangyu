package org.ldd.ssm.crm.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.ldd.ssm.crm.domain.AirportFlight;
import org.ldd.ssm.crm.domain.AirportLocation;
import org.ldd.ssm.crm.domain.Eachflightinfo;
import org.ldd.ssm.crm.mapper.AirportFlightMapper;
import org.ldd.ssm.crm.service.AirportFlightService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

@Service
public class AirportFlightServiceImpl implements AirportFlightService{

	@Resource 
	private AirportFlightMapper airportFlightMapper;
	
	@Override
	public AirportFlight getAirportFlight(String iATA) {
		AirportFlight airportFlight=new AirportFlight();
		airportFlight.setAirportLocation(airportFlightMapper.getAirportLocation(iATA));
		List<AirportLocation> retlist = new ArrayList<AirportLocation>();
		List<AirportLocation> retlist1 = new ArrayList<AirportLocation>();
		List<String> retlistTemp = new ArrayList<String>();
		Gson gson=new Gson();
		//调用外部接口
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_news_code_by_code/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(iATA)){
				connect.data("airport_code", iATA.toUpperCase());
			}
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			retlistTemp = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<String>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		retlist1 = airportFlightMapper.getAirportLocations(iATA);
		for (String str : retlistTemp) {
			for (AirportLocation airportLocation : retlist1) {
				if(str.equals(airportLocation.getiATA())){
					retlist.add(airportLocation);
				}
			}
		}
		airportFlight.setAirportLocationList(retlist);
		return airportFlight;
	}

}
