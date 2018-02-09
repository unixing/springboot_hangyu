package org.ldd.ssm.hangyu.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.ldd.ssm.hangyu.domain.Chat;
import org.ldd.ssm.hangyu.query.ChatDto;
import org.ldd.ssm.hangyu.service.impl.MyChatServiceImpl;
import org.ldd.ssm.hangyu.utils.MessageUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("deprecation")
public class MyMessageInbound extends MessageInbound {
	
	private  String name;//new websokect 传入的用户id
	private static MyChatServiceImpl chatService;
	private static ApplicationContext cs;
	//存放所有登录用户的Map集合，键：每个用户的唯一标识（用户名）  
	public static  Map<String, MyMessageInbound> socketList = new HashMap<String, MyMessageInbound>();  
	  
	public MyMessageInbound() {
		super();
	}

	public MyMessageInbound(String name) {
		super();
		this.name = name;
		
	}
	 static{
		 if(null==cs){
				cs=new ClassPathXmlApplicationContext("applicationContext.xml");    
			}
			chatService = (MyChatServiceImpl) cs.getBean("iMysqlTest");
	 }
	
	 public static  void send(ChatDto params) throws IOException {  
			String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			params.setDate(date);
			params.setChatFlag(params.getFromNameId()>params.getToNameId()?params.getToNameId()+"-"+params.getFromNameId()+"-"+params.getDemandId():params.getFromNameId()+"-"+params.getToNameId()+"-"+params.getDemandId());
			params.setTextType("0");
			params.setState("1");
			if(null!=params.getFromNameId()&&null!=params.getToNameId()&&!"null".equals(params.getToNameId())&&!"null".equals(params.getFromNameId())){
				if("all".equals(params.getToNameId())){
					String content = MessageUtil.sendContent(MessageUtil.MESSAGE,params);
					broadcastAll(content);
				}else{
					try {
						singleChat(params);
					} catch (IOException e) {
//						e.printStackTrace();
						System.out.println("当前websocket通讯连接已经断开:"+e.getMessage());
					}		
				}
				Chat chat = new Chat();
				chat.setFromNameId(params.getFromNameId());
				chat.setToNameId(params.getToNameId());
				chat.setDemandId(params.getDemandId());
				chat.setText(params.getText());
				chat.setDate(date);
				chat.setTextType("0");
				chat.setState("1");
				chatService.insertChatText(chat);
			}
			/*else{
				params.setText("请选择聊天对象");
				String content = MessageUtil.sendContent(MessageUtil.REMIND,params);
				broadcastAll(content);
			}*/
	    }  
	

	private static void singleChat(ChatDto dto) throws IOException {
		String content = MessageUtil.sendContent(MessageUtil.MESSAGE,dto);
		broadcastOneToOne(dto.getToNameId(),dto.getFromNameId(),content);
	}
	
	public static void broadcastAll(String message){
		Set<Map.Entry<String,MyMessageInbound>> set = InitServlet.getSocketList().entrySet();
		WsOutbound outbound = null;
		for(Map.Entry<String,MyMessageInbound> messageInbound: set){
			try {
				outbound = messageInbound.getValue().getWsOutbound();
				outbound.writeTextMessage(CharBuffer.wrap(message));
				outbound.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void broadcastOneToOne(Long toNameId,Long fromNameId,String message){
		Set<Map.Entry<String,MyMessageInbound>> set = InitServlet.getSocketList().entrySet();
		WsOutbound outbound = null;
		for(Map.Entry<String,MyMessageInbound> messageInbound: set){
			try {
				if(messageInbound.getKey().equals(toNameId+"")||messageInbound.getKey().equals(fromNameId+"")){
					outbound = messageInbound.getValue().getWsOutbound();
					outbound.writeTextMessage(CharBuffer.wrap(message));
					outbound.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void onOpen(WsOutbound outbound) { 
		super.onOpen(outbound);
		if(null!=name){
			InitServlet.getSocketList().put(name, this);//存放客服ID与用户
		}
		System.out.println("执行open");
	}
	
	@Override  
	protected void onClose(int status) { 
		if(null!=name){
			InitServlet.getSocketList().remove(name);//删除客服ID与用户
			System.out.println("用户" + name + "退出");
		}
		/*String names = getNames();
		String content = MessageUtil.sendContent(MessageUtil.USER,names);
		broadcastAll(content);*/
		super.onClose(status);
	}  
	
	@Override
	public int getReadTimeout() {
		return 0;
	}

	@Override
	protected void onTextMessage(CharBuffer arg0) throws IOException {
	}  

	@Override  
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {  

	}  
	
//	private String getNames() {
//	Map<String,MessageInbound> exitUser = InitServlet.getSocketList();
//	Iterator<String> it=exitUser.keySet().iterator();
//	String names = "";
//	while(it.hasNext()){
//		String key=it.next();
//		names += key + ",";
//	}
//	String namesTemp = "";
//	if(!TextUtil.isEmpty(names)){
//		namesTemp = names.substring(0,names.length()-1);
//	}
//	return namesTemp;
//}


}