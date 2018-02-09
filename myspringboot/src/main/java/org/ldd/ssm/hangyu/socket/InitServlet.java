package org.ldd.ssm.hangyu.socket;

import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.catalina.websocket.MessageInbound;
public class InitServlet extends HttpServlet {

    private static final long serialVersionUID = -3163557381361759907L;  
    
	
	private static HashMap<String,MyMessageInbound> socketList;    
      
    public void init(ServletConfig config) throws ServletException {    
        InitServlet.socketList = new HashMap<String,MyMessageInbound>();    
        super.init(config);    
        System.out.println("聊天系统启动");    
        
    }    
        
    public static HashMap<String,MyMessageInbound> getSocketList() {   
        return InitServlet.socketList;    
    }    
}