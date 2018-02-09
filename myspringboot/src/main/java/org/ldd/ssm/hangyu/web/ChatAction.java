package org.ldd.ssm.hangyu.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.Chat;
import org.ldd.ssm.hangyu.query.ChatDto;
import org.ldd.ssm.hangyu.service.IChatService;
import org.ldd.ssm.hangyu.socket.MyMessageInbound;
import org.ldd.ssm.hangyu.utils.PageBean;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChatAction {
	@Autowired
	private IChatService chatService;
	
	Logger log = Logger.getLogger(ChatAction.class);
	@RequestMapping("/chat")
	public String socketChat(){
		return "/socket/index";
	}
	@RequestMapping("/employees")
	@ResponseBody
	public List<Long> employees(){
		return chatService.employees(UserContext.getUser().getUsername());
	}
	@RequestMapping("/employeesChat")
	@ResponseBody
	public List<Chat> employeesChat(Long fromNameId,Long toNameId){
		
		return chatService.employeesChat(fromNameId,toNameId);
	}
	
    /** 
     * 向客户端推送消息 
     * */  
    @ResponseBody  
    @RequestMapping(value = "/api/send")  
    public Map<String, String> send(ChatDto params) {  
        Map<String, String> result = new HashMap<String, String>();  
        try {  
            MyMessageInbound.send(params);  
            result.put("message", "success");  
        } catch (IOException e) {  
            result.put("message", e.getLocalizedMessage());  
            e.printStackTrace();  
        }  
        return result;  
    }  
    /**
     * 修改状态为已读
     * @param params
     * @return
     */
    @ResponseBody  
    @RequestMapping(value = "/updateState")  
    public Map<String, Object> updateState(ChatDto dto) {  
    	Map<String,Object> map=new HashMap<String,Object>();
        try {  
        	if(null==dto.getFromNameId()||null==dto.getToNameId()){
        		map.put("opResult", "1");
            	map.put("msg", "传入参数为空");
            	return map;
        	}
        	boolean succ=chatService.updateState(dto);
        	if(succ){
        		map.put("opResult", "0");
            	map.put("msg", "修改状态成功");
        	}else{
        		map.put("opResult", "1");
            	map.put("msg", "修改状态失败");
        	}
        
        } catch (Exception e) {  
        	log.error(
					"updateState,there is something wrong here",
					e);
			e.printStackTrace();
			map.put("opResult", "2");
        }  
        return map;  
    }  
    
    /**
     * 修改状态为已读
     * @param params
     * @return
     */
    @ResponseBody  
    @RequestMapping(value = "/updateSystemState")  
    public Map<String, Object> updateSystemState(ChatDto dto) {  
    	Map<String,Object> map=new HashMap<String,Object>();
        try {  
        	if(null==dto.getFromNameId()){
        		map.put("opResult", "1");
            	map.put("msg", "传入当前用户id为空");
            	return map;
        	}
        	boolean succ=chatService.updateSystemState(dto);
        	if(succ){
        		map.put("opResult", "0");
            	map.put("msg", "修改状态成功");
        	}else{
        		map.put("opResult", "1");
            	map.put("msg", "修改状态失败");
        	}
        
        } catch (Exception e) {  
        	log.error(
					"updateSystemState,there is something wrong here",
					e);
			e.printStackTrace();
			map.put("opResult", "2");
        }  
        return map;  
    }  
	/**
	 * 我的发布 ：fromNameId  当前需求发布者的id（当前登陆者）； toNameId （意向对象）;  demandId （我发布的需求id）;
	 * @param chat
	 * @return
	 */
	@RequestMapping("/openChat")
	@ResponseBody
	public Map<String,Object> openChat(ChatDto chat){
		Map<String,Object> map=new HashMap<String,Object>();
		if(chat.getPage()==0||chat.getPageNo()==0){
			chat.setPage(1);
			chat.setPageNo(100);
		}
		chat.setStartIndex(chat.getPageNo() * (chat.getPage() - 1));
		try {
			if(null==chat.getFromNameId()&&null==chat.getToNameId()&&null==chat.getDemandId()){
				map.put("opResult","1");
				map.put("msg","传入参数为空");
				return map;
			}else if(null!=chat.getFromNameId()&&null!=chat.getToNameId()){//点对点
				List<Map<String,Object>> list=new ArrayList<>();
				Map<String,Object> chatMap=chatService.getChatDetail(chat);
				if(null==chatMap){
					map.put("opResult","1");
					map.put("msg","该需求需求状态不是正常或者审核通过的");
					return map;
				}else{
					list.add(chatMap);
					map.put("data",list);
					map.put("opResult","0");
					map.put("msg","查询成功");
				}
			}else if(null!=chat.getFromNameId()&&null==chat.getToNameId()&&null==chat.getDemandId()){//首页点进去的
				map.put("data",chatService.findChatList(chat));
				map.put("opResult","0");
				map.put("msg","查询成功");
			}
			//系统消息
			map.put("systemMessage",chatService.getSystemMessage(chat));
		} catch (Exception e) {
			log.error(
					"openChat,there is something wrong here",
					e);
			e.printStackTrace();
			map.put("opResult", "2");
		}
		return map;
	}
	
	
	@RequestMapping("/openSystemChat")
	@ResponseBody
	public Map<String,Object> openSystemChat(ChatDto chat){
		Map<String,Object> map=new HashMap<String,Object>();
		if(chat.getPage()==0){
			chat.setPage(1);
		}
		chat.setPageNo(100);
		chat.setStartIndex(chat.getPageNo()  * (chat.getPage() - 1));
		try {
			//系统消息
			map.put("opResult","0");
			map.put("msg","查询成功");
			map.put("systemMessage",chatService.getSystemMessage(chat));
		} catch (Exception e) {
			log.error(
					"openSystemChat,there is something wrong here",
					e);
			e.printStackTrace();
			map.put("opResult", "2");
		}
		return map;
	}
	
	
	@RequestMapping("/openNormalChat")
	@ResponseBody
	public Map<String,Object> openNormalChat(ChatDto chat){
		Map<String,Object> map=new HashMap<String,Object>();
		if(chat.getPage()==0){
			chat.setPage(1);
		}
		chat.setPageNo(100);
		chat.setStartIndex(chat.getPageNo()  * (chat.getPage() - 1));
		try {
			 if(null!=chat.getFromNameId()&&null!=chat.getToNameId()){//点对点
					List<Map<String,Object>> list=new ArrayList<>();
					Map<String,Object> chatMap=chatService.getChatDetail(chat);
					if(null==chatMap){
						map.put("opResult","1");
						map.put("msg","该需求需求状态不是正常或者审核通过的");
						return map;
					}else{
						list.add(chatMap);
						map.put("data",list);
						map.put("opResult","0");
						map.put("msg","查询成功");
					}
				}
		} catch (Exception e) {
			log.error(
					"openNormalChat,there is something wrong here",
					e);
			e.printStackTrace();
			map.put("opResult", "2");
		}
		return map;
	}
}
