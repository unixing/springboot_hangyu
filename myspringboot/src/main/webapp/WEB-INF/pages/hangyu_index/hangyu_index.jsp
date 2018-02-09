<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="chrome=1">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>航遇</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
  </head>
  <body>
  	<div align="center">
  		<form title="这是一个测试表单" id="zd"> 
  		
  			<table>
  				<thead>
  					<tr>
  						<th>日期</th>
  						<th>航班号</th>
  						<th>计划出港时间</th>
  						<th>实际起飞时间</th>
  						<th>出发地</th>
  						<th>始发地三字码</th>
  						<th>计划到达时间</th>
  						<th>实际到达时间</th>
  						<th>到达地</th>
  						<th>到达地三字码</th>
  						<th>准点率</th>
  						<th>状态</th>
  					</tr>
  				</thead>
  				<tbody>
  					<tr>
  						<td>12</td>
  						<td>3123</td>
  						<td>123</td>
  						<td>123</td>
  						<td>13</td>
  						<td>23</td>
  						<td>123</td>
  						<td>123</td>
  						<td>312</td>
  						<td>12</td>
  						<td>12</td>
  						<td>312</td>
  					</tr>
  				</tbody>
  			</table>
  			<input id="flt_nbr">
  			<input type="button" onclick="testMongodb()" value="查询mongodb">
  			<input type="button" onclick="testMysql()" value="查询mysql">
  		</form>
  	</div>
  	<img id="news" width="100px" height="100px"/>
  	<img id="news1" width="100px" height="100px"/>
  	<img id="news2" width="100px" height="100px"/>
  </body>
   <script type="text/javascript">
   		$(function(){
   			/* getDemandsByEmployee();
   			getDemandsForAirport();
   			getAirCompenyList();
   			demandAdd();
   			loaddemand();
   			findCollect();
   			getTrusteeshipDemandList();
   			getAirportListByCode();
   			getAirportTimeInfo();
   			getAirportTimeInfo(); 
   			loadAirportByCode();
   			getAirportTimeDistribution();
   			aircompenyDetail();
   			getResponseDetails();*/
   			//getByHangSi();
   			//getByJiChang();
   			//getByChengShi();
   			//getCityDetail();
   			//getAirportInformation();
   			getDemandsByCurrentCheckedAirportForEmployee();
   			getOthersDemandListIndex();
   			
   		});
   		function testMongodb(){
   			$.ajax({
   				url:'${pageContext.request.contextPath}/hangyu_list_mongodb',
   				success:function(data){
   					$("#zd").form("load",data);
   				}
   			})
   		}
   		function testMysql(){
   			$.ajax({
   				url:'${pageContext.request.contextPath}/hangyu_list_mysql',
   				success:function(data){
   					alert(data);
   				}
   			});
   		}
   		
   		function getDemandsByEmployee(){
   			$.ajax({
   				url:'/getDemandsForCurrentEmployee',
   				type:'post',
   				data:{
   					page:1
   				},
   				success:function(data){
   					if(data.opResult == '0'){
   						console.log(data.list);
   					}
   				}
   			});
   		}
   		
   		function getDemandsForAirport(){
   			$.ajax({
   				url:'/getDemandsForCurrentCheckedCity',
   				type:'post',
   				data:{
   					itia:'BSD',
   					page:1,
   					type:0,
   					itiaType:0
   				},
   				success:function(data){
   					if(data.opResult == '0'){
   						console.log(data.list);
   					}
   				}
   			});
   		}
   		
   		function getAirCompenyList(){
   			$.ajax({
   				url:'/airCompenyList',
   				type:'post',
   				data:[],
   				success:function(data){
   					if(data.opResult == '0'){
   						console.log(data.list);
   					}
   				}
   			});
   		}
   		
   		function demandAdd(){
   			var xuqiu = {};
   			xuqiu.publicwayStr = '1';
   			xuqiu.aircrfttyp = '4f';
   			xuqiu.employeeId=160;
   			$.ajax({
   				url:'/demandAdd',
   				type:'post',
   				data:xuqiu,
   				success:function(data){
   					if(data.opResult == '0'){
   						console.log(data.list);
   					}
   				}
   			});
   		}
   		
   		function loaddemand(){
   			$.ajax({
   				url:'/demandFind',
   				type:'post',
   				data:{
   					demandId:2
   				},
   				success:function(data){
   					if(data.opResult == '0'){
   						console.log(data.data);
   					}
   				}
   			});
   		}
   		
   		function findCollect(){
   			$.ajax({
   				url:'/getCollectDemandList',
   				type:'post',
   				data:{
   					page:1
   				},
   				success:function(data){
   					if(data.opResult == '0'){
   						console.log(data.data);
   					}
   				}
   			});
   		}
   		
   		function getTrusteeshipDemandList(){
   			$.ajax({
   				url:'/getTrusteeshipDemandList',
   				type:'post',
   				data:{
   					page:1
   				},
   				success:function(data){
   					if(data.opResult == '0'){
   						console.log(data.list);
   					}
   				}
   			});
   		}
   		function getAirportListByCode(){
   			$.ajax({
   				url:'/getAirportListByCode',
   				type:'post',
   				data:[],
   				success:function(data){
   					if(data.opResult == '0'){
   						console.log(data.list);
   					}
   				}
   			});
   		}
   		function getAirportTimeInfo(){
   			$.ajax({
   				url:'/getAirportTimeInfo',
   				type:'post',
   				data:{
   					itia:'PEK',
   					getTime:'2017.12.10',
   					orderField:"fltNbr",
   					orderType:1
   				},
   				success:function(data){
   					if(data.opResult=='0'){
   						console.log(data.timeList);
   						console.log(data.list);
   					}
   				}
   			});
   		}
   		
   		
   		function getAirportTimeDistribution(){
   			$.ajax({
   				url:'/getAirportTimeDistribution',
   				type:'post',
   				data:{
   					itia:'PEK',
   					getTime:'2017.12.10'
   				},
   				success:function(data){
   					if(data.opResult=='0'){
   						console.log(data.timeList);
   						console.log(data.list);
   					}
   				}
   			});
   		}
   		
   		function jxlExcel(){
   			//window.location.href="/jxlExcel?itia=PEK&getTime=2017.12.10";
   			$.ajax({
   				url:'/jxlExcel',
   				type:'get',
   				data:{
   					itia:'PEK',
   					getTime:'2017.12.10'
   				},
   				success:function(data){
   					window.open(url);
   				}
   			});
   		}
   		
   		function loadAirportByCode(){
   			$.ajax({
   				url:'/loadAirportByCode',
   				type:'post',
   				data:{
   					itia:'CAN'
   				},
   				success:function(data){
   					console.log(data);
   				}
   			});
   		}
   		
   		function aircompenyDetail(){
   			$.ajax({
   				url:'/aircompenyDetail',
   				type:'post',
   				data:{
   					itia:'CCD'
   				},
   				success:function(data){
   					console.log(data);
   				}
   			});
   		}
   		
   		function getResponseDetails(){
   			$.ajax({
   				url:'/getResponseDetails',
   				type:'post',
   				data:{
   					responseId:495,
   				},
   				success:function(data){
   					console.log(data);
   				}
   			});
   		}
   		
   		function getByHangSi(){
   			$.ajax({
   				url:'/getPublicOpinionList',
   				type:'post',
   				data:{
   					code:'CCD',
   					type:0,
   					codeType:0,
   					page:1
   				},
   				async:false,
   				success:function(data){
   					console.log(data);
   					$("#news").attr("src",data.obj[0].articleImage);
   				}
   			});
   		}
   		
   		function getByJiChang(){
   			$.ajax({
   				url:'/getPublicOpinionList',
   				type:'post',
   				data:{
   					code:'CAN',
   					type:0,
   					codeType:1,
   					page:1
   				},
   				async:false,
   				success:function(data){
   					console.log(data);
   					$("#news1").attr("src",data.obj[0].articleImage);
   				}
   			});
   		}
   		
   		function getByChengShi(){
   			$.ajax({
   				url:'/getPublicOpinionList',
   				type:'post',
   				data:{
   					code:'成都',
   					type:0,
   					codeType:2,
   					page:1
   				},
   				async:false,
   				success:function(data){
   					console.log(data);
   					$("#news2").attr("src",data.obj[0].articleImage);
   				}
   			});
   		}
   		
   		function getCityDetail(){
   			$.ajax({
   				url:"/getCityDetail",
   				type:'post',
   				data:{
   					cityName:"成都"
   				},
   				success:function(data){
   					console.log(data);
   				}
   			});
   		}
   		
   		function getAirportInformation(){
   			$.ajax({
   				url:'/loadAirportInfomation',
   				type:'post',
   				data:{
   					iata:'ZGGG'
   				},
   				success:function(data){
   					console.log(data);
   				}
   			});
   		}
   		
   		function getDemandsByCurrentCheckedAirportForEmployee(){
   			$.ajax({
   				url:'/getDemandsByCurrentCheckedAirportForEmployee',
   				type:'post',
   				data:{
   					itia:'CTU',
   					page:1
   				},
   				success:function(data){
   					console.log(data);
   				}
   			});
   		}
   		
   		function getOthersDemandListIndex(){
   			$.ajax({
   				url:'/getOthersDemandListIndex',
   				type:'post',
   				data:{
   					itia:'CTU',
   					page:1
   				},
   				success:function(data){
   					console.log(data);
   				}
   			});
   		}
    </script>
</html>