package org.ldd.ssm.hangyu.utils;

import java.util.HashMap;
import java.util.Map;

import org.ldd.ssm.hangyu.query.ChatDto;

import net.sf.json.JSONObject;
public class MessageUtil {
	
	public final static String TYPE = "type";
	public final static String DATA = "data";
	
	public final static String MESSAGE = "message";
	public final static String REMIND = "remind";
	
	public final static String USER = "user";
	

	public static String sendContent(String type, ChatDto chat) {
		Map<String,Object> userMap = new HashMap<String,Object>();
		userMap.put(MessageUtil.TYPE, type);
		userMap.put(MessageUtil.DATA, chat);
		JSONObject json = JSONObject.fromObject(userMap);
		return json.toString();
	}
}