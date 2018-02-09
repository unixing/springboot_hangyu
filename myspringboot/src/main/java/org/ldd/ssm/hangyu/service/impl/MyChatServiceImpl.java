package org.ldd.ssm.hangyu.service.impl;

import org.ldd.ssm.hangyu.domain.Chat;
import org.ldd.ssm.hangyu.mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class MyChatServiceImpl {
	
	@Autowired
	private ChatMapper chatMapper;

	public Long getEmployeeIdByName(String fromName) {
		// TODO 自动生成的方法存根
		return chatMapper.getEmployeeIdByName(fromName);
	}

	public void insertChatText(Chat chat) {
		chatMapper.insertChatText(chat);
	}
	
	
}
