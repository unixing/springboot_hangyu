
<%@ page language='java' contentType='text/html; charset=UTF-8'%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    <title>航遇</title>
    <%--<link rel="icon" href="./src/static/img/favicon-20171102013443485.ico" type="image/x-icon">--%>
<link href="./../../../dist/main.css" rel="stylesheet"></head>
<body>
<div id="app"></div>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=TDmhTStuIIhX3LsAf3bNZV60SZoloqdC"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/CurveLine/1.5/src/CurveLine.min.js"></script>
<script src="https://cdn.bootcss.com/three.js/r83/three.min.js"></script>
<script src="http://wow.techbrood.com/uploads/1703/learning-three-js-2e/libs/loaders/DDSLoader.js"></script>
<script src="http://wow.techbrood.com/uploads/1703/learning-three-js-2e/libs/loaders/OBJLoader.js"></script>
<script src="http://wow.techbrood.com/uploads/151001/postprocessing.js"></script>
<script>
    //var socketIp = 'ws://localhost:8029/socket?name=';
    var socketIp = 'ws://192.168.22.9/socket?name=';
</script>
<script type="text/javascript" src="./../../../dist/main.build.js"></script>
</body>
</html>
