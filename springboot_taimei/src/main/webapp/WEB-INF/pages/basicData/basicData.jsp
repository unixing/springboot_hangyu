<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>太美航空</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<%@include file="/WEB-INF/pages/common/css.jsp"%>
</head>
<body>
	<aside>
		<font size="2" color="gray"> <br> &emsp;当前位置:<a href="#">数据管理</a>》<a
			href="#">基础数据维护</a>
		</font><br>
		<br>
		<form id="DOW-searchForm">
			<font size="2" color="gray"> 保留小数位数:<input name="digit"
				value="${digit }" size="4"> <input type="hidden" name="id"
				value="${id }" size="4"> &emsp;<a href="#"
				class="btn btn-success" onclick="send();">保存</a>
			</font>
		</form>
	</aside>
	<%@include file="/WEB-INF/pages/common/common.jsp"%>
	<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
</body>
<script type="text/javascript">
	function getParameter(id){
		var custormersearchForm;
	// 		拿到form表单对象
		custormersearchForm = $("#DOW-searchForm");
	// 		调用自定义方法, 内部处理好数据结构拿到json数据对象
		var searchJson = custormersearchForm.searchJson();
		return searchJson;
	};
		function send(){
			var searchJson = getParameter();
			$.ajax({
		        type:'post',
		        url:'${pageContext.request.contextPath}/saveBasicData',//请求数据的地址	
		        data:searchJson,
		        success:function(data){
					alert(data.info);
		        }
		    });
		};
</script>
</html>