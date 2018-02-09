<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>数据空港</title>
    <meta name="renderer" content="webkit" />
</head>
<body>
    <script src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/jquery1.8.3.js"></script>
	<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/echarts.js"></script>

<div id="app"></div>
<script type="text/javascript" src="/echarts/build/dist3/bmap.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=TDmhTStuIIhX3LsAf3bNZV60SZoloqdC"></script>
<script src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/./../../vue/vue_two/dist/superlative.build.js"></script>

</body>
</html>

