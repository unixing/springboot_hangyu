package org.ldd.ssm.hangyu.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.hangyu.domain.Chat;
import org.ldd.ssm.hangyu.query.ChatDto;

public interface IChatService {

	List<Long> employees(String string);

	List<Chat> employeesChat(Long fromNameId, Long toNameId);
	
	Map<String,Object> getChatDetail(ChatDto chat);
	
	boolean  updateState(ChatDto dto);
	
	boolean  updateSystemState(ChatDto dto);
	
	List<Map<String,Object>> findChatList(ChatDto chat);
	
    Map<String, Object> getSystemMessage(ChatDto chat);
    
    boolean insertChatSelective(Chat chat);

}
