package org.ldd.ssm.crm.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.ldd.ssm.crm.domain.GuestrateFlpj;
import org.ldd.ssm.crm.domain.Yesterday_ZD;
import org.ldd.ssm.crm.query.FlightPriceCompDto;
import org.ldd.ssm.crm.query.FlightSaleData;
import org.ldd.ssm.crm.query.LatestAirFare;
import org.ldd.ssm.crm.service.TicketPriceFromMongoService;
import org.ldd.ssm.crm.utils.MongoDBToMysqlUtils;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Service
public class TicketPriceFromMongoServiceImpl implements TicketPriceFromMongoService{
/*	@Resource
	MongoDBToMysqlUtils MongoDBToMysqlUtils;*/
	
	
	
	public List<LatestAirFare> findTicketTrends(GuestrateFlpj dto){
//		MongoClient mongoClient = MongoDBToMysqlUtils.mongoDBCollection();
//        DB get_db_credit = mongoClient.getDB("air_fare_db");//数据库名  
//        DBCollection collection = get_db_credit.getCollection("tbl_xc_latest_air_fare");//集合名，对应mysql中的表名  
//        
//        BasicDBObject filter_dbobject = new BasicDBObject();  
//        BasicDBObject queryColumn = new BasicDBObject();
//        BasicDBObject orderColumn = new BasicDBObject();
//
//        filter_dbobject.put("flight_no",dto.getFltNbr());  
//        queryColumn.put("search_date",1);
//        queryColumn.put("flight_no", 1);
//        queryColumn.put("source_airport_3code", 1);
//        queryColumn.put("destination_airport_3code", 1);
//        queryColumn.put("lowest_price", 1);
//        queryColumn.put("daily_time", 1);
//        queryColumn.put("_id", 0);
//        orderColumn.put("search_date",1);
//        
//        DBCursor cursor = collection.find(filter_dbobject,queryColumn).sort(orderColumn);  
//        //把结果集输出成list类型  
//        List<DBObject> list = cursor.toArray();  
//		JSONArray relativeJSONArray=JSONArray.fromObject(list);
////		List<LatestAirFare> relativeList=JSONArray.toList(relativeJSONArray, new LatestAirFare(), new JsonConfig());
//		mongoClient.close();
		//调用外部接口
		Gson gson = new Gson();
		List<LatestAirFare> relativeList = new ArrayList<LatestAirFare>();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"xiecheng_airfare/get_fare_from_xiecheng/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(dto.getFltNbr())){
				connect.data("flight_no", dto.getFltNbr());
			}
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			relativeList = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<LatestAirFare>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return relativeList;
	}
	
	
	
	public List<LatestAirFare> findFlightManagePrice(GuestrateFlpj dto){
//		MongoClient mongoClient = MongoDBToMysqlUtils.mongoDBCollection();
//        DB get_db_credit = mongoClient.getDB("air_fare_db");//数据库名  
//        DBCollection collection = get_db_credit.getCollection("tbl_xc_latest_air_fare");//集合名，对应mysql中的表名  
//        
//        BasicDBObject filter_dbobject = new BasicDBObject();  
//        BasicDBObject queryColumn = new BasicDBObject();
//        BasicDBObject orderColumn = new BasicDBObject();
//
//        filter_dbobject.put("flight_no",dto.getFltNbr());  
//        filter_dbobject.put("source_airport_3code",dto.getDptAirptCd());  
//        filter_dbobject.put("destination_airport_3code",dto.getArrvAirptCd()); 
//        
//        queryColumn.put("search_date",1);
//        queryColumn.put("flight_no", 1);
//        queryColumn.put("source_airport_3code", 1);
//        queryColumn.put("destination_airport_3code", 1);
//        queryColumn.put("lowest_price", 1);
//        queryColumn.put("daily_time", 1);
//        queryColumn.put("_id", 0);
//        orderColumn.put("search_date",1);
//        
//        DBCursor cursor = collection.find(filter_dbobject,queryColumn).sort(orderColumn);  
//        //把结果集输出成list类型  
//        List<DBObject> list = cursor.toArray();  
//		JSONArray relativeJSONArray=JSONArray.fromObject(list);
//		List<LatestAirFare> relativeList=JSONArray.toList(relativeJSONArray, new LatestAirFare(), new JsonConfig());
//
//		mongoClient.close();
//		
		//调用外部接口
		Gson gson = new Gson();
		List<LatestAirFare> relativeList = new ArrayList<LatestAirFare>();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"xiecheng_airfare/get_fare_from_xiecheng/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(dto.getFltNbr())){
				connect.data("flight_no", dto.getFltNbr());
			}
			if(!TextUtil.isEmpty(dto.getDptAirptCd())){
				connect.data("source_airport_3code", dto.getDptAirptCd());
			}
			if(!TextUtil.isEmpty(dto.getArrvAirptCd())){
				connect.data("destination_airport_3code", dto.getArrvAirptCd());
			}
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			relativeList = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<LatestAirFare>>(){}.getType());
//			System.out.println(relativeList+"---3");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return relativeList;
	}
	
	public List<LatestAirFare> findFlightManageHistory(GuestrateFlpj dto){
//		MongoClient mongoClient = MongoDBToMysqlUtils.mongoDBCollection();
//        DB get_db_credit = mongoClient.getDB("air_fare_db");//数据库名  
//        DBCollection collection = get_db_credit.getCollection("flight_lowest_price");//集合名，对应mysql中的表名  
//        //条件模型
//        BasicDBObject filter_dbobject = new BasicDBObject();  
//        BasicDBObject queryColumn = new BasicDBObject();
//        BasicDBObject orderColumn = new BasicDBObject();
//        //设置查询条件
//        filter_dbobject.put("flight_no",dto.getFltNbr());  
//        filter_dbobject.put("source_airport_3code",dto.getDptAirptCd());  
//        filter_dbobject.put("destination_airport_3code",dto.getArrvAirptCd()); 
//        filter_dbobject.put("start_date",dto.getOnGutDte()); 
//        //设置返回参数
//        queryColumn.put("search_date",1);
//        queryColumn.put("flight_no", 1);
//        queryColumn.put("source_airport_3code", 1);
//        queryColumn.put("destination_airport_3code", 1);
//        queryColumn.put("lowest_price", 1);
//        queryColumn.put("avg_price", 1);
//        queryColumn.put("_id", 0);
//        queryColumn.put("start_date",1);
//        orderColumn.put("search_date",1);
//       
//        //执行
//        DBCursor cursor = collection.find(filter_dbobject,queryColumn).sort(orderColumn);  
//        //把结果集输出成list类型  
//        List<DBObject> list = cursor.toArray();  
//		JSONArray relativeJSONArray=JSONArray.fromObject(list);
//		List<LatestAirFare> relativeList=JSONArray.toList(relativeJSONArray, new LatestAirFare(), new JsonConfig());
//		mongoClient.close();
		
		//调用外部接口
		Gson gson = new Gson();
		List<LatestAirFare> relativeList = new ArrayList<LatestAirFare>();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"xiecheng_airfare/get_lowest_price_from_xiecheng").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(dto.getFltNbr())){
				connect.data("flight_no", dto.getFltNbr());
			}
			if(!TextUtil.isEmpty(dto.getDptAirptCd())){
				connect.data("source_airport_3code", dto.getDptAirptCd());
			}
			if(!TextUtil.isEmpty(dto.getArrvAirptCd())){
				connect.data("destination_airport_3code", dto.getArrvAirptCd());
			}
			if(!TextUtil.isEmpty(dto.getOnGutDte())){
				connect.data("start_date", dto.getOnGutDte());
			}
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			relativeList = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<LatestAirFare>>(){}.getType());
//			System.out.println(dto+"--"+relativeList+"---2");
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return relativeList;
	}
	
	
	public List<LatestAirFare> findFlightManagePriceByDate(FlightPriceCompDto dto){
//		MongoClient mongoClient = MongoDBToMysqlUtils.mongoDBCollection();
//        DB get_db_credit = mongoClient.getDB("air_fare_db");//数据库名  
//        DBCollection collection = get_db_credit.getCollection("tbl_xc_latest_air_fare");//集合名，对应mysql中的表名  
//        
//        BasicDBObject filter_dbobject = new BasicDBObject();  
//        BasicDBObject queryColumn = new BasicDBObject();
//        BasicDBObject orderColumn = new BasicDBObject();
//        BasicDBObject queryDate = new BasicDBObject();
//        
//        queryDate.put("$gt", dto.getCompareDate());
//        filter_dbobject.put("flight_no",dto.getFltNbr());  
//        filter_dbobject.put("source_airport_3code",dto.getDptAirptCd());  
//        filter_dbobject.put("destination_airport_3code",dto.getArrvAirptCd()); 
//        filter_dbobject.put("search_date",queryDate); 
//        
//        queryColumn.put("search_date",1);
//        queryColumn.put("flight_no", 1);
//        queryColumn.put("source_airport_3code", 1);
//        queryColumn.put("destination_airport_3code", 1);
//        queryColumn.put("lowest_price", 1);
//        queryColumn.put("daily_time", 1);
//        queryColumn.put("_id", 0);
//        orderColumn.put("search_date",1);
//        
//        DBCursor cursor = collection.find(filter_dbobject,queryColumn).sort(orderColumn);  
//        //把结果集输出成list类型  
//        List<DBObject> list = cursor.toArray();  
//		JSONArray relativeJSONArray=JSONArray.fromObject(list);
//		List<LatestAirFare> relativeList=JSONArray.toList(relativeJSONArray, new LatestAirFare(), new JsonConfig());
//		mongoClient.close();
		
		//调用外部接口
		Gson gson = new Gson();
		List<LatestAirFare> relativeList = new ArrayList<LatestAirFare>();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"xiecheng_airfare/get_fare_from_xiecheng/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(dto.getFltNbr())){
				connect.data("flight_no", dto.getFltNbr());
			}
			if(!TextUtil.isEmpty(dto.getDptAirptCd())){
				connect.data("source_airport_3code", dto.getDptAirptCd());
			}
			if(!TextUtil.isEmpty(dto.getArrvAirptCd())){
				connect.data("destination_airport_3code", dto.getArrvAirptCd());
			}
			if(!TextUtil.isEmpty(dto.getCompareDate())){
				connect.data("search_date", dto.getCompareDate());
			}
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			relativeList = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<LatestAirFare>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativeList;
	}



	@Override
	public FlightSaleData getRealTimeFare(String param) throws UnknownHostException, IOException{
	    Socket client =null;
	    InputStream is =null;
	    FlightSaleData flightSaleData=new FlightSaleData();
		try {
		 //向服务器端发送请求，服务器IP地址和服务器监听的端口号  
        client = new Socket("192.168.11.11",8006);  
        //通过printWriter 来向服务器发送消息  
        PrintWriter printWriter = new PrintWriter(client.getOutputStream());  
        System.out.println("连接已建立...");  
        //发送消息  
        printWriter.print(param);  
          
        printWriter.flush();  
          
        //InputStreamReader是低层和高层串流之间的桥梁  
        //client.getInputStream()从Socket取得输入串流  
       /* InputStreamReader streamReader = new InputStreamReader(client.getInputStream());  
          
        //链接数据串流，建立BufferedReader来读取，将BufferReader链接到InputStreamReder  
        BufferedReader reader = new BufferedReader(streamReader,100);
      //  System.out.println("dsd");
        String advice =reader.readLine();  
        System.out.println("接收到服务器的消息 ："+advice);  
        reader.close(); 
        client.close();*/
        
        is = client.getInputStream();  
        byte[] bytes = new byte[500000];  
        int n = is.read(bytes);  
        String s=new String(bytes, 0, n);

	     /**code
			*102：无直飞（没有这个航段的飞机）
			200：查询成功
			300：暂时售罄（没有查询到该航班的信息）
			400：查询失败（程序出错）
			501：操作频繁（IP暂时被禁）
			**/
           List<LatestAirFare> listResult = new ArrayList<LatestAirFare>(); 
           List<LatestAirFare> list = new ArrayList<LatestAirFare>(); 
           JSONArray jsonArray = JSONArray.fromObject(s);//把String转换为json 
	       list = JSONArray.toList(jsonArray,new LatestAirFare(),new JsonConfig());//这里的t是Class<T> 
	       for(LatestAirFare dto:list){
	    	   if(dto.getCode().equals("200")){
	    		   listResult.add(dto);
	    	   }
	       }
	       if(listResult.size()>0){
	    	   flightSaleData.setCurrerntFlightPriceList(list);
		       flightSaleData.setSelectIntimeFlag(true);
		       flightSaleData.setSelectIntimeMsg("及时查询成功");
	       }else{
		       flightSaleData.setSelectIntimeFlag(false);
		       flightSaleData.setSelectIntimeMsg("及时查询失败");
	       }
       is.close();  
       client.close(); 
       return flightSaleData;
		} finally {
			 is.close();  
		       client.close(); 
		}
	}
	
	

	public List<LatestAirFare> findPriceLegs(GuestrateFlpj dto){
		MongoClient mongoClient = MongoDBToMysqlUtils.mongoDBCollection();
        DB get_db_credit = mongoClient.getDB("air_fare_db");//数据库名  
        DBCollection collection = get_db_credit.getCollection("tbl_xc_latest_air_fare");//集合名，对应mysql中的表名  
        
        BasicDBObject filter_dbobject = new BasicDBObject();  
        BasicDBObject queryColumn = new BasicDBObject();
        BasicDBObject orderColumn = new BasicDBObject();

        filter_dbobject.put("flight_no",dto.getFltNbr());  
     
        queryColumn.put("source_airport_3code", 1);
        queryColumn.put("destination_airport_3code", 1);
      
        queryColumn.put("_id", 0);
    
        BasicDBObject key = new BasicDBObject();  
        key.put("source_airport_3code",true);
        key.put("destination_airport_3code",true);
        
        BasicDBObject initial = new BasicDBObject();  
        String reduce = "function(obj,pre){}";  
        BasicDBList returnList = (BasicDBList)collection.group(key, filter_dbobject, initial,reduce); 
        Object[] listFare= returnList.toArray();
		JSONArray relativeJSONArray=JSONArray.fromObject(listFare);
		List<LatestAirFare> relativeList=JSONArray.toList(relativeJSONArray, new LatestAirFare(), new JsonConfig());
		
		
		mongoClient.close();
		return relativeList;
	}
	
}
