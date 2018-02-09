<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	//response.sendRedirect(request.getContextPath()+"/login.jsp");
%>
<script src="/indexSource/jquery1.8.3.js"></script>
<script type="text/javascript">
$(function(){
	if(window.parent){
		window.parent.location.href='login.jsp';
	}else{
		window.location.href='login.jsp';
	}
});
</script>