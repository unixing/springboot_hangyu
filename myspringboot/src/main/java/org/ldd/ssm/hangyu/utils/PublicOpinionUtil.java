package org.ldd.ssm.hangyu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.ldd.ssm.hangyu.domain.PublicOpinion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:basic_parameter.properties")
public class PublicOpinionUtil {
	//ssm框架去配置文件
//	public static String CAPPLICATION_HOST;
//	public static String BROWSE_PICTURE_IP;
//	public static void getPublicOpinionIp(){
//		InputStream is = PublicOpinionUtil.class.getResourceAsStream("classpath:basic_parameter.properties");
//		Properties p = new Properties();
//		try {
//			p.load(is);
//			CAPPLICATION_HOST = p.getProperty("public_opinion_ip");
//			BROWSE_PICTURE_IP = p.getProperty("browse_picture_ip");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	public static String CAPPLICATION_HOST;
	public static String BROWSE_PICTURE_IP;
	@Value("${public_opinion_ip}")
	public void setCapplicationHost(String capplicationHost){
		CAPPLICATION_HOST = capplicationHost;
	}
	@Value("${browse_picture_ip}")
	public void setBrowsePictureIp(String browsePictureIp){
		BROWSE_PICTURE_IP = browsePictureIp;
	}
	public static List<PublicOpinion> getPublicOpinionForAirLine(String code,Integer page,Integer pageSize) throws IOException{
		Connection connect =  Jsoup.connect(CAPPLICATION_HOST+"public_opinion/get_article_by_assigncode/").header("Accept", "*/*").ignoreContentType(true);
		connect.data("airline_code", code);
		connect.data("display_count", pageSize+"");
		connect.data("current_page", page+"");
		Document doc = connect.timeout(100000).post();
		JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
		Iterator<JsonElement> iterator = returnData.get("result").getAsJsonArray().iterator();
		List<PublicOpinion> opinions = new ArrayList<PublicOpinion>();
		while(iterator.hasNext()){
			PublicOpinion opinion = new PublicOpinion();
			JsonObject jsonObject = iterator.next().getAsJsonObject();
			opinion.setArticleUrl(jsonObject.get("article_Url").getAsString());
			opinion.setArticleContent(jsonObject.get("article_content").getAsString());
			opinion.setArticleCount(jsonObject.get("article_count").getAsString());
			opinion.setArticleFrom(jsonObject.get("article_from").getAsString());
			opinion.setArticleImage(TextUtil.isEmpty(jsonObject.get("article_image").getAsString())?"":BROWSE_PICTURE_IP+jsonObject.get("article_image").getAsString());
			opinion.setArticleTime(jsonObject.get("article_time").getAsString());
			opinion.setArticleTitle(jsonObject.get("article_title").getAsString());
			opinion.setArticleType(jsonObject.get("article_type").getAsString());
			opinions.add(opinion);
		}
		return opinions;
	}
	
	public static List<PublicOpinion> getPublicOpinionForAirport(String code,Integer page,Integer pageSize) throws IOException{
		Connection connect =  Jsoup.connect(CAPPLICATION_HOST+"public_opinion/get_article_by_assigncode/").header("Accept", "*/*").ignoreContentType(true);
		connect.data("airport_code", code);
		connect.data("display_count", pageSize+"");
		connect.data("current_page", page+"");
		Document doc = connect.timeout(100000).post();
		JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
		Iterator<JsonElement> iterator = returnData.get("result").getAsJsonArray().iterator();
		List<PublicOpinion> opinions = new ArrayList<PublicOpinion>();
		while(iterator.hasNext()){
			PublicOpinion opinion = new PublicOpinion();
			JsonObject jsonObject = iterator.next().getAsJsonObject();
			opinion.setArticleUrl(jsonObject.get("article_Url").getAsString());
			opinion.setArticleContent(jsonObject.get("article_content").getAsString());
			opinion.setArticleCount(jsonObject.get("article_count").getAsString());
			opinion.setArticleFrom(jsonObject.get("article_from").getAsString());
			opinion.setArticleImage(TextUtil.isEmpty(jsonObject.get("article_image").getAsString())?"":BROWSE_PICTURE_IP+jsonObject.get("article_image").getAsString());
			opinion.setArticleTime(jsonObject.get("article_time").getAsString());
			opinion.setArticleTitle(jsonObject.get("article_title").getAsString());
			opinion.setArticleType(jsonObject.get("article_type").getAsString());
			opinions.add(opinion);
		}
		return opinions;
	}
	
	public static List<PublicOpinion> getPublicOpinionForCity(String code,Integer page,Integer pageSize) throws IOException{
		Connection connect =  Jsoup.connect(CAPPLICATION_HOST+"public_opinion/get_article_by_searchkey/").header("Accept", "*/*").ignoreContentType(true);
		connect.data("search_key", code);//code城市名称
		connect.data("display_count", pageSize+"");
		connect.data("current_page", page+"");
		Document doc = connect.timeout(100000).post();
		JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
		Iterator<JsonElement> iterator = returnData.get("result").getAsJsonArray().iterator();
		List<PublicOpinion> opinions = new ArrayList<PublicOpinion>();
		while(iterator.hasNext()){
			PublicOpinion opinion = new PublicOpinion();
			JsonObject jsonObject = iterator.next().getAsJsonObject();
			opinion.setArticleUrl(jsonObject.get("article_Url").getAsString());
			opinion.setArticleContent(jsonObject.get("article_content").getAsString());
			opinion.setArticleCount(jsonObject.get("article_count").getAsString());
			opinion.setArticleFrom(jsonObject.get("article_from").getAsString());
			opinion.setArticleImage(TextUtil.isEmpty(jsonObject.get("article_image").getAsString())?"":BROWSE_PICTURE_IP+jsonObject.get("article_image").getAsString());
			opinion.setArticleTime(jsonObject.get("article_time").getAsString());
			opinion.setArticleTitle(jsonObject.get("article_title").getAsString());
			opinion.setArticleType(jsonObject.get("article_type").getAsString());
			opinions.add(opinion);
		}
		return opinions;
	}
}
