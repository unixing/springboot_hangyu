package org.ldd.ssm.crm.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.ldd.ssm.crm.domain.AcquisitionTime;
import org.ldd.ssm.crm.domain.AllAirLineFltDetailData;
import org.ldd.ssm.crm.domain.Eachflightinfo;
import org.ldd.ssm.crm.domain.LinkTypeData;
import org.ldd.ssm.crm.domain.Rule;
import org.ldd.ssm.crm.exception.RuleException;
import org.ldd.ssm.crm.mapper.PassengerdetalisinfoMapper;
import org.ldd.ssm.crm.query.ProcessTaskObject;
import org.ldd.ssm.crm.query.ProcessTaskQuery;
import org.ldd.ssm.crm.service.IExtractService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author zhy
 * 
 */
@Service
public class ExtractServiceImpl implements IExtractService {
	@Autowired
	private PassengerdetalisinfoMapper lisinfoMapper;
	/**
	 * 爬虫,去哪网的时刻数据
	 */
	private  Date format;
	public void extract(Rule rule) {

		// 进行对rule的必要校验
		validateRule(rule);


		LinkTypeData data = null;
		try {
			// 拿到访问地址
			String url = rule.getUrl();
			// 拿到参数集合
			String[] params = rule.getParams();
			// 拿到参数值
			String[] values = rule.getValues();
			// 对返回的HTML，第一次过滤所用的标签，请先设置type
//			String resultTagName = rule.getResultTagName();
			// 拿到返回标签类型
//			int type = rule.getType();
			// 拿到返回类型
			int requestType = rule.getRequestMoethod();
			// 连接url
			Connection conn = Jsoup.connect(url);
			// 设置查询参数

			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					conn.data(params[i], values[i]);
				}
			}

			// 设置请求类型
			Document doc = null;
			switch (requestType) {
			case Rule.GET:
				doc = conn.timeout(100000).get();
				break;
			case Rule.POST:
				doc = conn.timeout(100000).post();
				break;
			}

			
			Elements select = doc.getElementsByAttributeValue("data-defer",
					"jmpPlugin");

			Elements select2 = doc.select(".abbr");
			Elements select3 = doc.select(".time");
			Elements select4 = doc.select(".airport");
			Elements select5 = doc.select(".weeks").select("span.blue,b.t");
			Elements select6 = doc.select(".result_type");
			data = new LinkTypeData();
			if(select.text().length()>=6){
				data.setFlyNub(select.text());
			}
			data.setAirCrft_Typ(select2.text());
			data.setSummary(select3.text());
			data.setContent(select4.text());
			ArrayList<String> getfomt = getfomt(select5.text());
			data.setArrayList(getfomt);
			data.setAirText(select6.text());
			
			save(data);
			
			Elements sele = doc.select(".schedule_down");
			for (Element element : sele) {
					String attr2 = element.attr("href");
						Rule rule2 = new Rule(attr2, new String[] {}, new String[] {}, null, -1,
								Rule.GET);
						extract(rule2);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 对传入的参数进行必要的校验
	 */

	public ArrayList<String> getfomt(String str) {
		ArrayList<String> arrayList = new ArrayList<String>();
		String replace = str.replace(" ", "").replaceAll("班", "").replace("餐食", "");
		String[] split = replace.split("期");
		for (String string : split) {
			arrayList.add(string);
		}
		return arrayList;
	}

	public void validateRule(Rule rule) {
		String url = rule.getUrl();
		if (TextUtil.isEmpty(url)) {
			throw new RuleException("url不能为空！");
		}
		if (!url.startsWith("http://")) {
			throw new RuleException("url的格式不正确！");
		}

		if (rule.getParams() != null && rule.getValues() != null) {
			if (rule.getParams().length != rule.getValues().length) {
				throw new RuleException("参数的键值对个数不匹配！");
			}
		}

	}
	
	
	public String get(String name) {
		
		return lisinfoMapper.get(name);
	}

	public void testname(String str,String name,Date format) {
		this.format = format;
		String str3 = "http://flights.ctrip.com/schedule/";
		if(str!=null){
			str3 = str3+str+".";
		}
		Rule rule = new Rule(str3+".html", new String[] {}, new String[] {}, null, -1,
				Rule.GET);
		extract2(rule,name);
	}

	public void extract2(Rule rule,String name) {

		// 进行对rule的必要校验
		validateRule(rule);

		try {
			// 拿到访问地址
			String url = rule.getUrl();
			// 拿到参数集合
			String[] params = rule.getParams();
			// 拿到参数值
			String[] values = rule.getValues();
			// 拿到返回类型
			int requestType = rule.getRequestMoethod();
			// 连接url
			Connection conn = Jsoup.connect(url);
			// 设置查询参数

			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					conn.data(params[i], values[i]);
				}
			}

			// 设置请求类型
			Document doc = null;
			switch (requestType) {
			case Rule.GET:
				doc = conn.timeout(100000).get();
				break;
			case Rule.POST:
				doc = conn.timeout(100000).post();
				break;
			}
			
			
			Elements select2 = doc.select(".m");
			for (Element element : select2) {
				String text = element.select(".m").text();
				if(name.equals((text.split("-"))[0])){
					List<Node> childNodes = element.childNodes();
					for (Node node : childNodes) {
						String attr = node.attr("href");
						if(attr!=null&&attr!=""){
							Rule rule2 = new Rule(attr, new String[] {}, new String[] {}, null, -1,
									Rule.GET);
							extract(rule2);
						}
					}
				}
			}
			
			

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void save(LinkTypeData data){
		String airCrft_Typ = data.getAirCrft_Typ();
		ArrayList<String> arrayList = data.getArrayList();
		if(data.getFlyNub()!=null&&data.getFlyNub()!=""){
			String[] air = airCrft_Typ.split(" +");
			String[] con = data.getContent().split(" +");
			String[] fly = data.getFlyNub().split(" +");
			String[] sum = data.getSummary().split(" +");
			String[] text = data.getAirText().split("到");
			for (int i = 1; i < air.length+1; i++) {
				Eachflightinfo each = new Eachflightinfo();
				each.setDpt_AirPt_Cd(text[0]);// 始发地
				each.setArrv_Airpt_Cd(text[1]);// 到达地
				each.setFlt_nbr(fly[i-1]);// 航班号
				each.setAirCrft_Typ(air[i-1]);// 机型
				each.setDpt_AirPt_pot(con[i*2-2]);// 出发机场
				each.setLcl_Dpt_Tm(sum[i*2-2]);// 出发时间
				each.setArrv_Airpt_pot(con[i*2-1]);// 到达机场
				each.setLcl_Arrv_Tm(sum[i*2-1]);// 到达时间
				each.setDays(arrayList.get(i));// 班期
				each.setGet_tim(format);
				lisinfoMapper.save(each); 
			}
		}
	}

	public List<String> getName() {
		return lisinfoMapper.list();
	}

	public List<String> get2(String name) {
		
		return lisinfoMapper.get2(name);
	}

	public ProcessTaskObject<Eachflightinfo> query(ProcessTaskQuery sQuery) {
		List<Eachflightinfo> eachflightinfos = lisinfoMapper.query(sQuery);
		//查询总数
		Integer queryTotal = lisinfoMapper.queryTotal(sQuery);
		//封装数据
		
		ProcessTaskObject<Eachflightinfo> permissionObject = new ProcessTaskObject<Eachflightinfo>(eachflightinfos, queryTotal);
		return permissionObject;
	}
	
	public ProcessTaskObject<Eachflightinfo> queryByIata(ProcessTaskQuery sQuery) {
		
		ProcessTaskObject<Eachflightinfo> permissionObject = null;
	
//		List<Eachflightinfo> eachflightinfos = lisinfoMapper.queryByIata(sQuery);
		List<Eachflightinfo> eachflightinfos = new ArrayList<Eachflightinfo>();
		//查询总数
//		Integer queryTotal = lisinfoMapper.queryTotalByIata(sQuery);
		Integer queryTotal = 0 ;
		Gson gson=new Gson();
		//封装数据
		//调用外部接口
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_schedule_by_field/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(sQuery.getDpt_AirPt_Cd())){
				connect.data("dep_airport_name", sQuery.getDpt_AirPt_Cd());
			}
			if(!TextUtil.isEmpty(sQuery.getArrv_Airpt_Cd())){
				connect.data("arr_airport_name", sQuery.getArrv_Airpt_Cd());
			}
			if(!TextUtil.isEmpty(sQuery.getDpt_AirPt_Cd_itia())){
				connect.data("dep_airport_code", sQuery.getDpt_AirPt_Cd_itia());
			}
			if(!TextUtil.isEmpty(sQuery.getArrv_Airpt_Cd_itia())){
				connect.data("arr_airport_code", sQuery.getArrv_Airpt_Cd_itia());
			}
			if(!TextUtil.isEmpty(sQuery.getGet_tim())){
				connect.data("crwl_date", sQuery.getGet_tim());
			}
			if(!TextUtil.isEmpty(sQuery.getMu())){
				connect.data("air_line", sQuery.getMu());
			}
			if(!TextUtil.isEmpty(sQuery.getDptTmStart())){
				connect.data("dep_start_time", sQuery.getDptTmStart());
			}
			if(!TextUtil.isEmpty(sQuery.getDptTmEnd())){
				connect.data("dep_end_time", sQuery.getDptTmEnd());
			}
			if(!TextUtil.isEmpty(sQuery.getArrvTmStart())){
				connect.data("arr_start_time", sQuery.getArrvTmStart());
			}
			if(!TextUtil.isEmpty(sQuery.getArrvTmEnd())){
				connect.data("arr_end_time", sQuery.getArrvTmEnd());
			}
			if(!TextUtil.isEmpty(sQuery.getFltNbrSort())){
				connect.data("flt_nbr_sort", (sQuery.getFltNbrSort().equals("DESC")||sQuery.getFltNbrSort().equals("desc"))?"-1":"1");
			}
			if(!TextUtil.isEmpty(sQuery.getDptTmSort())){
				connect.data("dpt_time_sort", sQuery.getDptTmSort());
			}
			if(!TextUtil.isEmpty(sQuery.getArrvTmSort())){
				connect.data("arr_time_sort", sQuery.getArrvTmSort());
			}
			if(sQuery.getOffset()!=null){
				connect.data("offset", sQuery.getOffset()+"");
			}
			if(sQuery.getLimit()!=null){
				connect.data("limit", sQuery.getLimit()+"");
			}
			connect.data("is_all", "false");
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			queryTotal = Integer.parseInt(returnData.get("result").getAsJsonObject().get("count").toString());
			eachflightinfos = gson.fromJson(returnData.get("result").getAsJsonObject().get("data"), new TypeToken<List<Eachflightinfo>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		permissionObject = new ProcessTaskObject<Eachflightinfo>(eachflightinfos, queryTotal);
		return permissionObject;
	}

	public ProcessTaskObject<Eachflightinfo> queryPort(ProcessTaskQuery sQuery) {
//		List<Eachflightinfo> eachflightinfos = lisinfoMapper.queryPort(sQuery);
		List<Eachflightinfo> eachflightinfos =  new ArrayList<Eachflightinfo>();
		//调用外部接口
		Gson gson=new Gson();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_schedule_by_field/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(sQuery.getDpt_AirPt_Cd())){
				connect.data("dep_airport_name", sQuery.getDpt_AirPt_Cd());
			}
			if(!TextUtil.isEmpty(sQuery.getArrv_Airpt_Cd())){
				connect.data("arr_airport_name", sQuery.getArrv_Airpt_Cd());
			}
			if(!TextUtil.isEmpty(sQuery.getDpt_AirPt_Cd_itia())){
				connect.data("dep_airport_code", sQuery.getDpt_AirPt_Cd_itia().toUpperCase());
			}
			if(!TextUtil.isEmpty(sQuery.getArrv_Airpt_Cd_itia())){
				connect.data("arr_airport_code", sQuery.getArrv_Airpt_Cd_itia().toUpperCase());
			}
			if(!TextUtil.isEmpty(sQuery.getGet_tim())){
				connect.data("crwl_date", sQuery.getGet_tim());
			}
			if(!TextUtil.isEmpty(sQuery.getMu())){
				connect.data("air_line", sQuery.getMu());
			}
			connect.data("is_all", "false");
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			eachflightinfos = gson.fromJson(returnData.get("result").getAsJsonObject().get("data"), new TypeToken<List<Eachflightinfo>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProcessTaskObject<Eachflightinfo> permissionObject = new ProcessTaskObject<Eachflightinfo>(eachflightinfos, null);
		return permissionObject;
	}

	public List<AcquisitionTime> getAll() {
		List<AcquisitionTime> all = lisinfoMapper.getAll();
		List<AcquisitionTime> newAll =new ArrayList<AcquisitionTime>();
		for (int i = 1; i <= all.size(); i++) {
			AcquisitionTime acquisitionTime = all.get(all.size()-i);
			newAll.add(acquisitionTime);
		}
		return newAll;
	}

	public ProcessTaskObject<Eachflightinfo> queryAll(ProcessTaskQuery sQuery) {
		List<Eachflightinfo> eachflightinfos = lisinfoMapper.queryAll(sQuery);
		//查询总数
		Integer queryTotal = lisinfoMapper.queryTotalAll(sQuery);
//		//封装数据
		
		ProcessTaskObject<Eachflightinfo> permissionObject = new ProcessTaskObject<Eachflightinfo>(eachflightinfos, queryTotal);
		return permissionObject;
	}
	
	public ProcessTaskObject<Eachflightinfo> queryAllByIata(ProcessTaskQuery sQuery) {
//		List<Eachflightinfo> eachflightinfos = lisinfoMapper.queryAllByIata(sQuery);
		//查询总数
//		Integer queryTotal = lisinfoMapper.queryTotalAllByIata(sQuery);
		//封装数据
		List<Eachflightinfo> eachflightinfos = new ArrayList<Eachflightinfo>();
		//查询总数
//		Integer queryTotal = lisinfoMapper.queryTotalByIata(sQuery);
		Integer queryTotal = 0 ;
		Gson gson=new Gson();
		//封装数据
		//调用外部接口
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_schedule_by_field/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(sQuery.getDpt_AirPt_Cd())){
				connect.data("dep_airport_name", sQuery.getDpt_AirPt_Cd());
			}
			if(!TextUtil.isEmpty(sQuery.getArrv_Airpt_Cd())){
				connect.data("arr_airport_name", sQuery.getArrv_Airpt_Cd());
			}
			if(!TextUtil.isEmpty(sQuery.getDpt_AirPt_Cd_itia())){
				connect.data("dep_airport_code", sQuery.getDpt_AirPt_Cd_itia().toUpperCase());
			}
			if(!TextUtil.isEmpty(sQuery.getArrv_Airpt_Cd_itia())){
				connect.data("arr_airport_code", sQuery.getArrv_Airpt_Cd_itia().toUpperCase());
			}
			if(!TextUtil.isEmpty(sQuery.getGet_tim())){
				connect.data("crwl_date", sQuery.getGet_tim());
			}
			if(!TextUtil.isEmpty(sQuery.getMu())){
				connect.data("air_line", sQuery.getMu());
			}
			if(!TextUtil.isEmpty(sQuery.getDptTmStart())){
				connect.data("dep_start_time", sQuery.getDptTmStart());
			}
			if(!TextUtil.isEmpty(sQuery.getDptTmEnd())){
				connect.data("dep_end_time", sQuery.getDptTmEnd());
			}
			if(!TextUtil.isEmpty(sQuery.getArrvTmStart())){
				connect.data("arr_start_time", sQuery.getArrvTmStart());
			}
			if(!TextUtil.isEmpty(sQuery.getArrvTmEnd())){
				connect.data("arr_end_time", sQuery.getArrvTmEnd());
			}
			if(!TextUtil.isEmpty(sQuery.getFltNbrSort())){
				connect.data("flt_nbr_sort", (sQuery.getFltNbrSort().equals("DESC")||sQuery.getFltNbrSort().equals("desc"))?"-1":"1");
			}
			if(!TextUtil.isEmpty(sQuery.getDptTmSort())){
				connect.data("dpt_time_sort", sQuery.getDptTmSort());
			}
			if(!TextUtil.isEmpty(sQuery.getArrvTmSort())){
				connect.data("arr_time_sort", sQuery.getArrvTmSort());
			}
			if(sQuery.getOffset()!=null){
				connect.data("offset", sQuery.getOffset()+"");
			}
			if(sQuery.getLimit()!=null){
				connect.data("limit", sQuery.getLimit()+"");
			}
			connect.data("is_all", "true");
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			queryTotal = Integer.parseInt(returnData.get("result").getAsJsonObject().get("count").toString());
			eachflightinfos = gson.fromJson(returnData.get("result").getAsJsonObject().get("data"), new TypeToken<List<Eachflightinfo>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProcessTaskObject<Eachflightinfo> permissionObject = new ProcessTaskObject<Eachflightinfo>(eachflightinfos, queryTotal);
		return permissionObject;
	}

	public ProcessTaskObject<Eachflightinfo> queryToRoRreturn(
			ProcessTaskQuery sQuery) {
		List<Eachflightinfo> eachflightinfos = lisinfoMapper.queryToRoRreturn(sQuery);
		//查询总数
		Integer queryTotal = lisinfoMapper.queryTotalToRoRreturn(sQuery);
		//封装数据
		
		ProcessTaskObject<Eachflightinfo> permissionObject = new ProcessTaskObject<Eachflightinfo>(eachflightinfos, queryTotal);
		return permissionObject;
	}
	
	public ProcessTaskObject<Eachflightinfo> queryToRoRreturnByIata(ProcessTaskQuery sQuery) {
		List<Eachflightinfo> eachflightinfos = null;
		Integer queryTotal = 0;
//		eachflightinfos = lisinfoMapper.queryToRoRreturnByIata(sQuery);
		//查询总数
//		queryTotal = lisinfoMapper.queryTotalToRoRreturnByIata(sQuery);
		//封装数据
		//调用外部接口
		Gson gson=new Gson();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_schedule_by_field/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(sQuery.getDpt_AirPt_Cd())){
				connect.data("dep_airport_name", sQuery.getDpt_AirPt_Cd());
			}
			if(!TextUtil.isEmpty(sQuery.getArrv_Airpt_Cd())){
				connect.data("arr_airport_name", sQuery.getArrv_Airpt_Cd());
			}
			if(!TextUtil.isEmpty(sQuery.getDpt_AirPt_Cd_itia())){
				connect.data("dep_airport_code", sQuery.getDpt_AirPt_Cd_itia().toUpperCase());
			}
			if(!TextUtil.isEmpty(sQuery.getArrv_Airpt_Cd_itia())){
				connect.data("arr_airport_code", sQuery.getArrv_Airpt_Cd_itia().toUpperCase());
			}
			if(!TextUtil.isEmpty(sQuery.getGet_tim())){
				connect.data("crwl_date", sQuery.getGet_tim());
			}
			if(!TextUtil.isEmpty(sQuery.getMu())){
				connect.data("air_line", sQuery.getMu());
			}
			if(!TextUtil.isEmpty(sQuery.getDptTmStart())){
				connect.data("dep_start_time", sQuery.getDptTmStart());
			}
			if(!TextUtil.isEmpty(sQuery.getDptTmEnd())){
				connect.data("dep_end_time", sQuery.getDptTmEnd());
			}
			if(!TextUtil.isEmpty(sQuery.getArrvTmStart())){
				connect.data("arr_start_time", sQuery.getArrvTmStart());
			}
			if(!TextUtil.isEmpty(sQuery.getArrvTmEnd())){
				connect.data("arr_end_time", sQuery.getArrvTmEnd());
			}
			if(!TextUtil.isEmpty(sQuery.getFltNbrSort())){
				connect.data("flt_nbr_sort", (sQuery.getFltNbrSort().equals("DESC")||sQuery.getFltNbrSort().equals("desc"))?"-1":"1");
			}
			if(!TextUtil.isEmpty(sQuery.getDptTmSort())){
				connect.data("dpt_time_sort", sQuery.getDptTmSort());
			}
			if(!TextUtil.isEmpty(sQuery.getArrvTmSort())){
				connect.data("arr_time_sort", sQuery.getArrvTmSort());
			}
			if(sQuery.getOffset()!=null){
				connect.data("offset", sQuery.getOffset()+"");
			}
			if(sQuery.getLimit()!=null){
				connect.data("limit", sQuery.getLimit()+"");
			}
			connect.data("is_all", "true");
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			queryTotal = Integer.parseInt(returnData.get("result").getAsJsonObject().get("count").toString());
			eachflightinfos = gson.fromJson(returnData.get("result").getAsJsonObject().get("data"), new TypeToken<List<Eachflightinfo>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProcessTaskObject<Eachflightinfo> permissionObject = new ProcessTaskObject<Eachflightinfo>(eachflightinfos, queryTotal);
		return permissionObject;
	}

	@Override
	public List<String> getNewEstCollectDate(ProcessTaskQuery sQuery) {
		List<String> list = new ArrayList<String>();
//		list = lisinfoMapper.getNewEstCollectDate(sQuery);
		Gson gson=new Gson();
		//调用外部接口
		try {
			
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_news_eight_day/").header("Accept", "*/*").ignoreContentType(true);
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			list = gson.fromJson(returnData.get("result"), new TypeToken<List<String>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ProcessTaskObject<Eachflightinfo> queryPortReturn(
			ProcessTaskQuery sQuery) {
		List<Eachflightinfo> eachflightinfos =  new ArrayList<Eachflightinfo>();
//		List<Eachflightinfo> eachflightinfos = lisinfoMapper.queryPortReturn(sQuery);
		Gson gson=new Gson();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_schedule_by_field/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(sQuery.getDpt_AirPt_Cd())){
				connect.data("dep_airport_name", sQuery.getDpt_AirPt_Cd());
			}
			if(!TextUtil.isEmpty(sQuery.getArrv_Airpt_Cd())){
				connect.data("arr_airport_name", sQuery.getArrv_Airpt_Cd());
			}
			if(!TextUtil.isEmpty(sQuery.getDpt_AirPt_Cd_itia())){
				connect.data("dep_airport_code", sQuery.getDpt_AirPt_Cd_itia().toUpperCase());
			}
			if(!TextUtil.isEmpty(sQuery.getArrv_Airpt_Cd_itia())){
				connect.data("arr_airport_code", sQuery.getArrv_Airpt_Cd_itia().toUpperCase());
			}
			if(!TextUtil.isEmpty(sQuery.getGet_tim())){
				connect.data("crwl_date", sQuery.getGet_tim());
			}
			if(!TextUtil.isEmpty(sQuery.getMu())){
				connect.data("air_line", sQuery.getMu());
			}
			connect.data("is_all", "true");
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			eachflightinfos = gson.fromJson(returnData.get("result").getAsJsonObject().get("data"), new TypeToken<List<Eachflightinfo>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProcessTaskObject<Eachflightinfo> permissionObject = new ProcessTaskObject<Eachflightinfo>(eachflightinfos, null);
		return permissionObject;
	}
	
	static String airportJson = "{'airports':["
			 + "{'code':'AHJ','airportName':'阿坝红原','pinyin':'abahongyuan','py':'abhy'},"
			 + "{'code':'YIE','airportName':'阿尔山伊尔施','pinyin':'aershanyiershi','py':'aesyes'},"
			 + "{'code':'AKU','airportName':'阿克苏温宿','pinyin':'akesuwensu','py':'aksws'},"
			 + "{'code':'AAT','airportName':'阿勒泰','pinyin':'aletai','py':'alt'},"
			 + "{'code':'NGQ','airportName':'阿里昆莎','pinyin':'alikunsha','py':'alks'},"
			 + "{'code':'AKA','airportName':'安康','pinyin':'ankang','py':'ak'},"
			 + "{'code':'AQG','airportName':'安庆天柱山','pinyin':'anqingtianzhushan','py':'aqtzs'},"
			 + "{'code':'AOG','airportName':'鞍山腾鳌','pinyin':'anshantengao','py':'asta'},"
			 + "{'code':'AVA','airportName':'安顺黄果树','pinyin':'anshunhuangguoshu','py':'ashgs'},"
			 + "{'code':'AYN','airportName':'安阳北郊','pinyin':'anyangbeijiao','py':'aybj'},"
			 + "{'code':'MFM','airportName':'澳门','pinyin':'aomen','py':'am'},"
			 + "{'code':'AEB','airportName':'百色田阳','pinyin':'baisetianyang','py':'bsty'},"
			 + "{'code':'BSD','airportName':'保山云端','pinyin':'baoshanyunduan','py':'bsyd'},"
			 + "{'code':'BAV','airportName':'包头二里半','pinyin':'baotouerliban','py':'btelb'},"
			 + "{'code':'RLK','airportName':'巴彦淖尔天吉泰','pinyin':'bayanzhuoertianjitai','py':'byzetjt'},"
			 + "{'code':'MFK','airportName':'北竿','pinyin':'beigan','py':'bg'},"
			 + "{'code':'BHY','airportName':'北海福成','pinyin':'beihaifucheng','py':'bhfc'},"
			 + "{'code':'NAY','airportName':'北京南苑','pinyin':'beijingnanyuan','py':'bjny'},"
			 + "{'code':'PEK','airportName':'北京首都','pinyin':'beijingshoudu','py':'bjsd'},"
			 + "{'code':'BFJ','airportName':'毕节飞雄','pinyin':'bijiefeixiong','py':'bjfx'},"
			 + "{'code':'BPL','airportName':'博乐阿拉山口','pinyin':'bolealashankou','py':'blalsk'},"
			 + "{'code':'CGQ','airportName':'长春龙嘉','pinyin':'changchunlongjia','py':'cclj'},"
			 + "{'code':'CGD','airportName':'常德桃花源','pinyin':'changdetaohuayuan','py':'cdthy'},"
			 + "{'code':'BPX','airportName':'昌都邦达','pinyin':'changdubangda','py':'cdbd'},"
			 + "{'code':'CSX','airportName':'长沙黄花','pinyin':'changshahuanghua','py':'cshh'},"
			 + "{'code':'CIH','airportName':'长治王村','pinyin':'changzhiwangcun','py':'czwc'},"
			 + "{'code':'CZX','airportName':'常州奔牛','pinyin':'changzhoubenniu','py':'czbn'},"
			 + "{'code':'CHG','airportName':'朝阳','pinyin':'chaoyang','py':'cy'},"
			 + "{'code':'CTU','airportName':'成都双流','pinyin':'chengdushuangliu','py':'cdsl'},"
			 + "{'code':'CIF','airportName':'赤峰玉龙','pinyin':'chifengyulong','py':'cfyl'},"
			 + "{'code':'JUH','airportName':'池州九华山','pinyin':'chizhoujiuhuashan','py':'czjhs'},"
			 + "{'code':'CKG','airportName':'重庆江北','pinyin':'chongqingjiangbei','py':'cqjb'},"
			 + "{'code':'DLU','airportName':'大理荒草坝','pinyin':'dalihuangcaoba','py':'dlhcb'},"
			 + "{'code':'DDG','airportName':'丹东浪头','pinyin':'dandonglangtou','py':'ddlt'},"
			 + "{'code':'DCY','airportName':'稻城亚丁','pinyin':'daochengyading','py':'dcyd'},"
			 + "{'code':'DQA','airportName':'大庆萨尔图','pinyin':'daqingsaertu','py':'dqset'},"
			 + "{'code':'DAT','airportName':'大同云冈','pinyin':'datongyungang','py':'DTYG'},"
			 + "{'code':'DAX','airportName':'达州河市','pinyin':'dazhouheshi','py':'dzhs'},"
			 + "{'code':'HXD','airportName':'德令哈','pinyin':'delingha','py':'dlh'},"
			 + "{'code':'DIG','airportName':'迪庆香格里拉','pinyin':'diqingxianggelila','py':'dqxgll'},"
			 + "{'code':'DOY','airportName':'东营胜利','pinyin':'dongyingshengli','py':'dysl'},"
			 + "{'code':'DNH','airportName':'敦煌','pinyin':'dunhuang','py':'dh'},"
			 + "{'code':'DLC','airportName':'大连周水子','pinyin':'dalianzhoushuizi','py':'dlzsz'},"
			 + "{'code':'EJN','airportName':'额济纳旗桃来','pinyin':'ejinaqitaolai','py':'ejnqtl'},"
			 + "{'code':'ENH','airportName':'恩施许家坪','pinyin':'enshixujiaping','py':'esxjp'},"
			 + "{'code':'ERL','airportName':'二连浩特赛乌素','pinyin':'erlianhaotesaiwusu','py':'elhtsws'},"
			 + "{'code':'LCX','airportName':'福建龙岩冠豸山','pinyin':'fujianlongyanguanzhishan','py':'fjlygzs'},"
			 + "{'code':'FUG','airportName':'阜阳西关','pinyin':'fuyangxiguan','py':'fyxg'},"
			 + "{'code':'FYJ','airportName':'抚远东极','pinyin':'fuyuandongji','py':'fydj'},"
			 + "{'code':'FYN','airportName':'富蕴','pinyin':'fuyun','py':'fy'},"
			 + "{'code':'FOC','airportName':'福州长乐','pinyin':'fuzhouchangle','py':'fzcl'},"
			 + "{'code':'KOW','airportName':'赣州黄金','pinyin':'ganzhouhuangjin','py':'gzhj'},"
			 + "{'code':'KHH','airportName':'高雄','pinyin':'gaoxiong','py':'gx'},"
			 + "{'code':'GOQ','airportName':'格尔木','pinyin':'geermu','py':'gem'},"
			 + "{'code':'GYS','airportName':'广元盘龙','pinyin':'guangyuanpanlong','py':'gypl'},"
			 + "{'code':'CAN','airportName':'广州白云','pinyin':'guangzhoubaiyun','py':'gzby'},"
			 + "{'code':'KWL','airportName':'桂林两江','pinyin':'guilinliangjiang','py':'gllj'},"
			 + "{'code':'KWE','airportName':'贵阳龙洞堡','pinyin':'guiyanglongdongbao','py':'gyldb'},"
			 + "{'code':'GYU','airportName':'固原六盘山','pinyin':'guyuanliupanshan','py':'gylps'},"
			 + "{'code':'HRB','airportName':'哈尔滨太平','pinyin':'haerbintaiping','py':'hebtp'},"
			 + "{'code':'HAK','airportName':'海口美兰','pinyin':'hailoumeilan','py':'hkml'},"
			 + "{'code':'HLD','airportName':'海拉尔东山','pinyin':'hailaerdongshan','py':'hleds'},"
			 + "{'code':'HMI','airportName':'哈密','pinyin':'hami','py':'hm'},"
			 + "{'code':'HDG','airportName':'邯郸','pinyin':'handan','py':'hd'},"
			 + "{'code':'HGH','airportName':'杭州萧山','pinyin':'hangzhouxiaoshan','py':'hzxs'},"
			 + "{'code':'HZG','airportName':'汉中城固','pinyin':'hanzhongchenggu','py':'hzcg'},"
			 + "{'code':'HCJ','airportName':'河池金城江','pinyin':'hechijinchengjiang','py':'hcjcj'},"
			 + "{'code':'HFE','airportName':'合肥新桥','pinyin':'hefeixinqiao','py':'hfxq'},"
			 + "{'code':'HEK','airportName':'黑河','pinyin':'heihe','py':'hh'},"
			 + "{'code':'HCN','airportName':'恒春五里亭','pinyin':'hengchunwuliting','py':'hcwlt'},"
			 + "{'code':'HNY','airportName':'衡阳南岳','pinyin':'hengyangnanyue','py':'hyny'},"
			 + "{'code':'HTN','airportName':'和田','pinyin':'hetian','py':'ht'},"
			 + "{'code':'HIA','airportName':'淮安涟水','pinyin':'huaianlianshui','py':'hals'},"
			 + "{'code':'HJJ','airportName':'怀化芷江','pinyin':'huaihuazhijiang','py':'hhzj'},"
			 + "{'code':'HUN','airportName':'花莲','pinyin':'hualian','py':'hl'},"
			 + "{'code':'TXN','airportName':'黄山屯溪','pinyin':'huangshantunxi','py':'hstx'},"
			 + "{'code':'HTT','airportName':'花土沟','pinyin':'huatugou','py':'htg'},"
			 + "{'code':'HET','airportName':'呼和浩特白塔','pinyin':'huhehaotebaita','py':'hhhtbt'},"
			 + "{'code':'HUZ','airportName':'惠州平潭','pinyin':'huizhoupingtan','py':'hzpt'},"
			 + "{'code':'JMU','airportName':'佳木斯','pinyin':'jiamusi','py':'jms'},"
			 + "{'code':'CYI','airportName':'嘉义','pinyin':'jiayi','py':'jy'},"
			 + "{'code':'JGN','airportName':'嘉峪关','pinyin':'jiayuguan','py':'jyg'},"
			 + "{'code':'SWA','airportName':'揭阳潮汕','pinyin':'jieyangchaoshan','py':'jycs'},"
			 + "{'code':'JIL','airportName':'吉林二台子','pinyin':'jilinertaizi','py':'jletz'},"
			 + "{'code':'TNA','airportName':'济南遥墙','pinyin':'jinanyaoqiang','py':'jnyq'},"
			 + "{'code':'JIC','airportName':'金昌金川','pinyin':'jinchangjinchuan','py':'jcjc'},"
			 + "{'code':'JDZ','airportName':'景德镇罗家','pinyin':'jingdezhenluojia','py':'jdzlj'},"
			 + "{'code':'JGS','airportName':'井冈山','pinyin':'jinggangshan','py':'jgs'},"
			 + "{'code':'JNG','airportName':'济宁曲阜','pinyin':'jiningqufu','py':'jnqf'},"
			 + "{'code':'JNZ','airportName':'锦州湾','pinyin':'jinzhouwan','py':'jzw'},"
			 + "{'code':'JIU','airportName':'九江庐山','pinyin':'jiujianglushan','py':'jjls'},"
			 + "{'code':'JZH','airportName':'九寨沟黄龙','pinyin':'jiuzhaigouhuanglong','py':'jzghl'},"
			 + "{'code':'JXA','airportName':'鸡西兴凯湖','pinyin':'jixixingkaihu','py':'jxxkh'},"
			 + "{'code':'KJI','airportName':'喀纳斯布尔津','pinyin':'kanasibuerjin','py':'knsbej'},"
			 + "{'code':'KGT','airportName':'康定','pinyin':'kangding','py':'kd'},"
			 + "{'code':'KHG','airportName':'喀什','pinyin':'kashi','py':'ks'},"
			 + "{'code':'KRY','airportName':'克拉玛依','pinyin':'kelamayi','py':'klmy'},"
			 + "{'code':'KCA','airportName':'库车龟兹','pinyin':'kucheqiuci','py':'kcqc'},"
			 + "{'code':'KRL','airportName':'库尔勒','pinyin':'kuerle','py':'kel'},"
			 + "{'code':'KMG','airportName':'昆明长水','pinyin':'kunmingchangshui','py':'kmcs'},"
			 + "{'code':'LXA','airportName':'拉萨贡嘎','pinyin':'lasagongga','py':'lsgg'},"
			 + "{'code':'LYG','airportName':'连云港白塔埠','pinyin':'lianyungangbaitabu','py':'lygbtb'},"
			 + "{'code':'LLB','airportName':'荔波','pinyin':'libo','py':'lb'},"
			 + "{'code':'LJG','airportName':'丽江三义','pinyin':'lijiangsanyi','py':'ljsy'},"
			 + "{'code':'LNJ','airportName':'临沧博尚','pinyin':'lincangboshang','py':'lcbs'},"
			 + "{'code':'LFQ','airportName':'临汾乔李','pinyin':'linfenqiaoli','py':'lfql'},"
			 + "{'code':'LXI','airportName':'林西','pinyin':'linxi','py':'lx'},"
			 + "{'code':'LYI','airportName':'临沂沐埠岭','pinyin':'linyimubuling','py':'lymbl'},"
			 + "{'code':'LZY','airportName':'林芝米林','pinyin':'linzhimilin','py':'lzml'},"
			 + "{'code':'HZH','airportName':'黎平','pinyin':'liping','py':'lp'},"
			 + "{'code':'LPF','airportName':'六盘水月照','pinyin':'liupanshuiyuezhao','py':'lpsyz'},"
			 + "{'code':'LZH','airportName':'柳州白莲','pinyin':'liuzhoubailian','py':'lubl'},"
			 + "{'code':'LYA','airportName':'洛阳北郊','pinyin':'luoyangbeijiao','py':'lybj'},"
			 + "{'code':'LZO','airportName':'泸州蓝田','pinyin':'luzhoulantian','py':'lzlt'},"
			 + "{'code':'LLV','airportName':'吕梁大武','pinyin':'lvliangdawu','py':'lldw'},"
			 + "{'code':'LUM','airportName':'芒市','pinyin':'mangshi','py':'ms'},"
			 + "{'code':'NZH','airportName':'满洲里西郊','pinyin':'manzhoulixijiao','py':'mzlxj'},"
			 + "{'code':'MXZ','airportName':'梅县长岗岌','pinyin':'meixianchanggangji','py':'mxcgj'},"
			 + "{'code':'MIG','airportName':'绵阳南郊','pinyin':'mianyangnanjiao','py':'mynj'},"
			 + "{'code':'OHE','airportName':'漠河古莲','pinyin':'mohegulian','py':'mhgl'},"
			 + "{'code':'MDG','airportName':'牡丹江海浪','pinyin':'mudanjianghailang','py':'mdjhl'},"
			 + "{'code':'MXZ','airportName':'梅县长岗岌','pinyin':'meixianchanggangji','py':'mxcgj'},"
			 + "{'code':'KHN','airportName':'南昌昌北','pinyin':'nanchangchangbei','py':'nccb'},"
			 + "{'code':'NAO','airportName':'南充高坪','pinyin':'nanchonggaoping','py':'nvgp'},"
			 + "{'code':'LZN','airportName':'南竿','pinyin':'nangan','py':'ng'},"
			 + "{'code':'NKG','airportName':'南京禄口','pinyin':'nanjinglukou','py':'njlk'},"
			 + "{'code':'NNG','airportName':'南宁吴圩','pinyin':'nanningwuxu','py':'nnwx'},"
			 + "{'code':'YXG','airportName':'南沙永暑礁','pinyin':'nanshayongshujiao','py':'nsysj'},"
			 + "{'code':'NTG','airportName':'南通兴东','pinyin':'nantongxingdong','py':'ntxd'},"
			 + "{'code':'NNY','airportName':'南阳姜营','pinyin':'nanyangjiangying','py':'nyjy'},"
			 + "{'code':'NGB','airportName':'宁波栎社','pinyin':'ningbolishe','py':'nbls'},"
			 + "{'code':'NLH','airportName':'宁蒗泸沽湖','pinyin':'ninglangluguhu','py':'nllgh'},"
			 + "{'code':'PIF','airportName':'屏东','pinyin':'pingdong','py':'pd'},"
			 + "{'code':'SYM','airportName':'普洱思茅','pinyin':'puersimao','py':'pesm'},"
			 + "{'code':'IQM','airportName':'且末','pinyin':'qiemo','py':'qm'},"
			 + "{'code':'CMJ','airportName':'七美','pinyin':'qimei','py':'qm'},"
			 + "{'code':'TAO','airportName':'青岛流亭','pinyin':'qingdaoliuting','py':'QDLT'},"
			 + "{'code':'IQN','airportName':'庆阳西峰','pinyin':'qingyangxifeng','py':'qyxf'},"
			 + "{'code':'SHP','airportName':'秦皇岛山海关','pinyin':'qinhuangdaoshanhaiguan','py':'qhdshg'},"
			 + "{'code':'NDG','airportName':'齐齐哈尔三家子','pinyin':'qiqihaersanjiazi','py':'qqhesjz'},"
			 + "{'code':'JJN','airportName':'泉州晋江','pinyin':'quanzhoujinjiang','py':'qzjj'},"
			 + "{'code':'JUZ','airportName':'衢州','pinyin':'quzhou','py':'qz'},"
			 + "{'code':'RIZ','airportName':'日照山字河','pinyin':'rizhaoshanzihe','py':'rzszh'},"
			 + "{'code':'SYX','airportName':'三亚凤凰','pinyin':'sanyafenghuang','py':'syfh'},"
			 + "{'code':'SHA','airportName':'上海虹桥','pinyin':'shanghaihongqiao','py':'shhq'},"
			 + "{'code':'PVG','airportName':'上海浦东','pinyin':'shanghaipudong','py':'shpd'},"
			 + "{'code':'SQD','airportName':'上饶三清山','pinyin':'shangraosanqingshan','py':'srsqs'},"
			 + "{'code':'KNH','airportName':'尚义','pinyin':'shangyi','py':'sy'},"
			 + "{'code':'HSC','airportName':'韶关','pinyin':'shaoguan','py':'sg'},"
			 + "{'code':'HPG','airportName':'神农架红坪','pinyin':'shennongjiahongping','py':'sljhp'},"
			 + "{'code':'SHE','airportName':'沈阳桃仙','pinyin':'shenyangtaoxian','py':'sytx'},"
			 + "{'code':'SZX','airportName':'深圳宝安','pinyin':'shenzhenbaoan','py':'szba'},"
			 + "{'code':'SHF','airportName':'石河子花园','pinyin':'shihezihuayuan','py':'shzhy'},"
			 + "{'code':'SJW','airportName':'石家庄正定','pinyin':'shijiazhuangzhengding','py':'sjzzd'},"
			 + "{'code':'WDS','airportName':'十堰武当山','pinyin':'shiyanwudangshan','py':'sywds'},"
			 + "{'code':'TSA','airportName':'台北松山','pinyin':'taibeisongshan','py':'tbss'},"
			 + "{'code':'TPE','airportName':'台北桃园','pinyin':'taibeitaoyuan','py':'tbty'},"
			 + "{'code':'TTT','airportName':'台东丰年','pinyin':'taidongfengnian','py':'tdfn'},"
			 + "{'code':'TNN','airportName':'台南','pinyin':'tainan','py':'tn'},"
			 + "{'code':'TYN','airportName':'太原武宿','pinyin':'taiyuanwusu','py':'tyws'},"
			 + "{'code':'TXG','airportName':'台中','pinyin':'taizhong','py':'tz'},"
			 + "{'code':'RMQ','airportName':'台中清泉岗','pinyin':'taizhongqingquangang','py':'taqqg'},"
			 + "{'code':'HYN','airportName':'台州路桥','pinyin':'taizhouluqiao','py':'tzlq'},"
			 + "{'code':'TVS','airportName':'唐山三女河','pinyin':'tangshansannvhe','py':'tssnh'},"
			 + "{'code':'TCZ','airportName':'腾冲驼峰','pinyin':'tengchongtuofeng','py':'tctf'},"
			 + "{'code':'TSN','airportName':'天津滨海','pinyin':'tianjinbinhai','py':'tjbh'},"
			 + "{'code':'THQ','airportName':'天水麦积山','pinyin':'tianshuimaijishan','py':'tsmjs'},"
			 + "{'code':'TNH','airportName':'通化','pinyin':'tonghua','py':'th'},"
			 + "{'code':'TGO','airportName':'通辽','pinyin':'tongliao','py':'tl'},"
			 + "{'code':'TEN','airportName':'铜仁凤凰','pinyin':'tongrenfenghuang','py':'trfh'},"
			 + "{'code':'TLQ','airportName':'吐鲁番交河','pinyin':'tulufanjiaohe','py':'tlfjh'},"
			 + "{'code':'WEF','airportName':'潍坊','pinyin':'weifang','py':'wf'},"
			 + "{'code':'WEH','airportName':'威海大水泊','pinyin':'weihaidashuipo','py':'whdsp'},"
			 + "{'code':'WNH','airportName':'文山普者黑','pinyin':'wenshanpuzhehei','py':'wspzh'},"
			 + "{'code':'WNZ','airportName':'温州龙湾','pinyin':'wenzhoulongwan','py':'wzlw'},"
			 + "{'code':'WUA','airportName':'乌海','pinyin':'wuhai','py':'wh'},"
			 + "{'code':'WUH','airportName':'武汉天河','pinyin':'wuhantianhe','py':'whth'},"
			 + "{'code':'UCB','airportName':'乌兰察布集宁','pinyin':'wulanchabujining','py':'wlcbjn'},"
			 + "{'code':'HLH','airportName':'乌兰浩特依勒力特','pinyin':'wulanheteyilelite','py':'wlhtyllt'},"
			 + "{'code':'URC','airportName':'乌鲁木齐地窝堡','pinyin':'wulumuqidiwobao','py':'wlmqdwb'},"
			 + "{'code':'WUX','airportName':'无锡硕放','pinyin':'wuxishuofang','py':'wxsf'},"
			 + "{'code':'WYS','airportName':'武夷山','pinyin':'wuyishan','py':'wxsf'},"
			 + "{'code':'WUZ','airportName':'梧州长洲岛','pinyin':'wuzhouchangzhoudao','py':'wzczd'},"
			 + "{'code':'XMN','airportName':'厦门高崎','pinyin':'xiamengaoqi','py':'xmgq'},"
			 + "{'code':'HKG','airportName':'香港赤黯角','pinyin':'xianggangchiliejiao','py':'xgclj'},"
			 + "{'code':'XFN','airportName':'襄阳刘集','pinyin':'xiangyangliuji','py':'xylj'},"
			 + "{'code':'XIY','airportName':'西安咸阳','pinyin':'xianxianyang','py':'xaxy'},"
			 + "{'code':'XIC','airportName':'西昌青山','pinyin':'xichangqingshan','py':'xcqs'},"
			 + "{'code':'XIL','airportName':'锡林浩特','pinyin':'xilinhaote','py':'xlht'},"
			 + "{'code':'XNT','airportName':'邢台褡裢','pinyin':'xingtaidalian','py':'xtdl'},"
			 + "{'code':'ACX','airportName':'兴义万峰林','pinyin':'xingyiwanfenglin','py':'xywfl'},"
			 + "{'code':'XNN','airportName':'西宁曹家堡','pinyin':'xiningcaojiabao','py':'xncjb'},"
			 + "{'code':'WUT','airportName':'忻州五台山','pinyin':'xinzhouwutaishan','py':'xzwts'},"
			 + "{'code':'JHG','airportName':'西双版纳嘎洒','pinyin':'xishuangbannagasa','py':'xsbngs'},"
			 + "{'code':'XUZ','airportName':'徐州观音','pinyin':'xuzhouguanyin','py':'xzgy'},"
			 + "{'code':'YNZ','airportName':'盐城南洋','pinyin':'yanchengnanyang','py':'ycny'},"
			 + "{'code':'YTY','airportName':'扬州泰州','pinyin':'yangzhoutaizhou','py':'yztz'},"
			 + "{'code':'YNJ','airportName':'延吉朝阳川','pinyin':'yanjichaoyangchuan','py':'yjcyc'},"
			 + "{'code':'YNT','airportName':'烟台蓬莱','pinyin':'tantaipenglai','py':'ytpl'},"
			 + "{'code':'YBP','airportName':'宜宾菜坝','pinyin':'yibincaiba','py':'ybcb'},"
			 + "{'code':'YIH','airportName':'宜昌三峡','pinyin':'yichangsanxia','py':'ycsx'},"
			 + "{'code':'LDS','airportName':'伊春林都','pinyin':'yichunlindu','py':'ycld'},"
			 + "{'code':'YIC','airportName':'宜春明月山','pinyin':'yichunmingyueshan','py':'ycmys'},"
			 + "{'code':'INC','airportName':'银川河东','pinyin':'yinchuanhedong','py':'ychd'},"
			 + "{'code':'YKH','airportName':'营口兰旗','pinyin':'yingkoulanqi','py':'yklq'},"
			 + "{'code':'YIN','airportName':'伊宁','pinyin':'yining','py':'yn'},"
			 + "{'code':'YIW','airportName':'义乌','pinyin':'yiwu','py':'yw'},"
			 + "{'code':'LLF','airportName':'永州零陵','pinyin':'yongzhoulingling','py':'yzll'},"
			 + "{'code':'RHT','airportName':'右旗巴丹吉林','pinyin':'youqibadanjilin','py':'yqbdjl'},"
			 + "{'code':'UYN','airportName':'榆林榆阳','pinyin':'yulinyuyang','py':'ylyy'},"
			 + "{'code':'YCU','airportName':'运城关公','pinyin':'yunchengguangong','py':'ycgg'},"
			 + "{'code':'YUS','airportName':'玉树巴塘','pinyin':'yushubatang','py':'ysbt'},"
			 + "{'code':'ZQZ','airportName':'张家口宁远','pinyin':'zhangjiakouningyuan','py':'zjkny'},"
			 + "{'code':'YZY','airportName':'张掖甘州','pinyin':'zhangyeganzhou','py':'zygz'},"
			 + "{'code':'ZHA','airportName':'湛江','pinyin':'zhanjiang','py':'zj'},"
			 + "{'code':'ZAT','airportName':'昭通','pinyin':'zhaotong','py':'zt'},"
			 + "{'code':'CGO','airportName':'郑州新郑','pinyin':'zhengzhouxinzheng','py':'zzxz'},"
			 + "{'code':'ZHY','airportName':'中卫沙坡头','pinyin':'zhongweishapotou','py':'zwspt'},"
			 + "{'code':'HSN','airportName':'舟山普陀山','pinyin':'zhoushanputuoshan','py':'zspts'},"
			 + "{'code':'ZUH','airportName':'珠海金湾','pinyin':'zhuhai','py':'zhjw'},"
			 + "{'code':'ZYI','airportName':'遵义新舟','pinyin':'zunyixinzhou','py':'zyxz'},"
			 + "{'code':'AXF','airportName':'左旗巴彦浩特','pinyin':'zuoqibayanhaote','py':'zqbyht'},"
			 + "{'code':'AXF','airportName':'阿拉善左旗','pinyin':'alashanzuoqi','py':'alszq'},"
			 + "{'code':'RHT','airportName':'阿拉善右旗','pinyin':'alashanyouqi','py':'alsyq'},"
			 + "{'code':'HZN','airportName':'沧州','pinyin':'cangzhou','py':'cz'},"
			 + "{'code':'NBS','airportName':'长白山','pinyin':'changbaishan','py':'cbs'},"
			 + "{'code':'CNI','airportName':'长海','pinyin':'changhai','py':'ch'},"
			 + "{'code':'LQP','airportName':'郴州','pinyin':'chenzhou','py':'cz'},"
			 + "{'code':'DYN','airportName':'丹阳','pinyin':'danyang','py':'dy'},"
			 + "{'code':'DZU','airportName':'大足','pinyin':'dazu','py':'dz'},"
			 + "{'code':'LUM','airportName':'德宏','pinyin':'dehong','py':'dh'},"
			 + "{'code':'DGM','airportName':'东莞','pinyin':'dongguan','py':'dg'},"
			 + "{'code':'DSN','airportName':'鄂尔多斯','pinyin':'eerduoshi','py':'eeds'},"
			 + "{'code':'EMH','airportName':'峨眉山','pinyin':'emeishan','py':'ems'},"
			 + "{'code':'EZH','airportName':'鄂州','pinyin':'ezhou','py':'ez'},"
			 + "{'code':'GHN','airportName':'广汉','pinyin':'guanghan','py':'gh'},"
			 + "{'code':'JGD','airportName':'加格达奇','pinyin':'jiagedaqi','py':'jgdq'},"
			 + "{'code':'JLE','airportName':'将乐','pinyin':'jiangle','py':'jl'},"
			 + "{'code':'JBD','airportName':'江门','pinyin':'jiangmen','py':'jm'},"
			 + "{'code':'JIS','airportName':'江山','pinyin':'jiangshan','py':'js'},"
			 + "{'code':'JAY','airportName':'江油','pinyin':'jiangyou','py':'jy'},"
			 + "{'code':'JIG','airportName':'建宁','pinyin':'jianling','py':'jl'},"
			 + "{'code':'JXS','airportName':'嘉兴','pinyin':'jiaxing','py':'jx'},"
			 + "{'code':'SHS','airportName':'荆州','pinyin':'jingzhou','py':'jz'},"
			 + "{'code':'KJH','airportName':'凯里','pinyin':'kaili','py':'kl'},"
			 + "{'code':'KVN','airportName':'昆山','pinyin':'kunshan','py':'ks'},"
			 + "{'code':'LHW','airportName':'兰州','pinyin':'lanzhou','py':'lz'},"
			 + "{'code':'LSG','airportName':'乐山','pinyin':'leshan','py':'ls'},"
			 + "{'code':'LIA','airportName':'梁平','pinyin':'liangping','py':'lp'},"
			 + "{'code':'LQQ','airportName':'辽阳','pinyin':'liaoyang','py':'ly'},"
			 + "{'code':'LQS','airportName':'陵水','pinyin':'lingshui','py':'ls'},"
			 + "{'code':'LCX','airportName':'龙岩','pinyin':'longyan','py':'ly'},"
			 + "{'code':'LUZ','airportName':'庐山','pinyin':'lushan','py':'ls'},"
			 + "{'code':'PZI','airportName':'攀枝花','pinyin':'panzhihua','py':'pzh'},"
			 + "{'code':'PNJ','airportName':'蓬莱','pinyin':'penglai','py':'pl'},"
			 + "{'code':'PXN','airportName':'萍乡','pinyin':'pingxiang','py':'px'},"
			 + "{'code':'JIQ','airportName':'黔江','pinyin':'qianjiang','py':'qj'},"
			 + "{'code':'JQJ','airportName':'潜江','pinyin':'qianjiang','py':'qj'},"
			 + "{'code':'RKZ','airportName':'日喀则','pinyin':'rikaze','py':'rkz'},"
			 + "{'code':'RUG','airportName':'如皋','pinyin':'rugao','py':'rg'},"
			 + "{'code':'NDH','airportName':'顺德','pinyin':'shunde','py':'sd'},"
			 + "{'code':'OSQ','airportName':'四平','pinyin':'siping','py':'sp'},"
			 + "{'code':'SZV','airportName':'苏州','pinyin':'suzhou','py':'sz'},"
			 + "{'code':'WOT','airportName':'望安','pinyin':'wangan','py':'wa'},"
			 + "{'code':'WXJ','airportName':'万宁','pinyin':'wanling','py':'wl'},"
			 + "{'code':'WXN','airportName':'万州','pinyin':'wanzhou','py':'wz'},"
			 + "{'code':'WEC','airportName':'文昌','pinyin':'wenchang','py':'wc'},"
			 + "{'code':'WHU','airportName':'芜湖','pinyin':'wuhu','py':'wh'},"
			 + "{'code':'WUY','airportName':'婺源','pinyin':'wuyuan','py':'wy'},"
			 + "{'code':'GXH','airportName':'夏河','pinyin':'xiahe','py':'xh'},"
			 + "{'code':'IUO','airportName':'咸宁','pinyin':'xianling','py':'xl'},"
			 + "{'code':'XEN','airportName':'兴城','pinyin':'xingcheng','py':'xc'},"
			 + "{'code':'XIN','airportName':'兴宁','pinyin':'xingning','py':'xn'},"
			 + "{'code':'XYW','airportName':'信阳','pinyin':'xingyang','py':'xy'},"
			 + "{'code':'ENY','airportName':'延安','pinyin':'yanan','py':'ya'},"
			 + "{'code':'YXG','airportName':'阳新','pinyin':'yangxin','py':'yx'},"
			 + "{'code':'YLN','airportName':'依兰','pinyin':'yilan','py':'yl'},"
			 + "{'code':'YIY','airportName':'益阳','pinyin':'yiyang','py':'yy'},"
			 + "{'code':'YUA','airportName':'元谋','pinyin':'yuanmou','py':'ym'},"
			 + "{'code':'YUG','airportName':'岳阳','pinyin':'yueyang','py':'yy'},"
			 + "{'code':'CJG','airportName':'张家港','pinyin':'zhangjiagang','py':'zjg'},"
			 + "{'code':'DYG','airportName':'张家界','pinyin':'zhangjiajie','py':'zjj'},"
			 + "{'code':'ZUJ','airportName':'镇江','pinyin':'zhenjiang','py':'zj'},"
			 + "{'code':'ZGN','airportName':'中山','pinyin':'zhongshan','py':'zs'}"
			+ "]}";
	
	public static void main(String[] args) {
		JSONObject object = JSONObject.fromObject(airportJson);
		JSONArray array = JSONArray.fromObject(object);
		for(int i =0;i<array.size();i++){
			System.out.println(array.get(i));
		}
	}
}
