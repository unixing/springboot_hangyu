package org.ldd.ssm.hangyu.socket;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
@SuppressWarnings("deprecation")
public class MyWebSocketServlet extends WebSocketServlet {
	private static final long serialVersionUID = -6488889268352650321L;

	
	protected StreamInbound createWebSocketInbound(String name,HttpServletRequest request) {
		String parameter = request.getParameter("name");
		return new MyMessageInbound(parameter);
	}
}