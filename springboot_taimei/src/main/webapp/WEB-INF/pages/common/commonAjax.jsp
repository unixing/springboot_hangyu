<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(document).ajaxComplete(function(event,xhr,options){
		if(xhr.responseText=='nullUser'){
			location.href ="/login.jsp"
		}
	});
</script>