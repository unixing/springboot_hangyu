<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
window.onload = function(){
	//第一次进入该页，加载航班号对应的航线和第一个航线的内容
	var searchJson = getSearchJosn();
	getFlyNum(searchJson);
 }
 //查询方法
 function doSend(){
	 var searchJson = getSearchJosn();
	 searchJson.flt_nbr_Count = $("#inputNum").val();
	 if(searchJson.flt_nbr_Count==""){
		 alert("请输入查询航班号");
	 }else{
		 var ul=$("#lines");  
		 ul.find("li").remove();
		 var di = $(".psc-sod-body-content");
		 di.remove();
		 getFlyNum(searchJson);
	 }
 }
 //获得航段信息
 function getFlyNum(searchJson){
	 $.ajax({
			type:'post',
	        url:'${pageContext.request.contextPath}/getBuyTicketReportLine',
	        data:searchJson,
	        success:function(data){
	        	var ul=$("#lines");  
	        	$("#flyNum").html(searchJson.flt_nbr_Count);  
	        	if(data.toString()==''){
	        		ul.append("<li value='没有数据'>没有数据或权限</li>");
	        	}else{
	        		for(var a in data){
		                 ul.append("<li value="+data[a]+">"+data[a]+"</li>"); 
		                 if(a==0){
		                	 ul.find("li").css({"background-color":"#3787be","color":"white"}).siblings().css({"background-color":"white","color":"#3787be"})	;
			        	 }
		        	}
	        		var line = data[0];
	        		var str = line.split("-");
	        		if(str.length>2){
	        			searchJson.pst_cd = str[1];
	        			searchJson.Dpt_AirPt_Cd = str[0];
	        			searchJson.Arrv_Airpt_Cd = str[2];
	        		}else{
	        			searchJson.Dpt_AirPt_Cd = str[0];
	        			searchJson.Arrv_Airpt_Cd = str[1];
	        		}
	        		getLineData(searchJson);
	        	}
	        	//绑定事件
	        	onclickData();
	        }
		});
 }
 //点击事件绑定
 function onclickData(){
	 $(".psc-sod-body-Rbox>ul>li").on("click",function(){
	        $(this).css({"background-color":"#3787be","color":"white"}).siblings().css({"background-color":"white","color":"#3787be"})
	        var searchJson = getSearchJosn();
	        var line = $(this).html();
	        var str = line.split("-");
			if(str.length>2){
    			searchJson.pst_cd = str[1];
    			searchJson.Dpt_AirPt_Cd = str[0];
    			searchJson.Arrv_Airpt_Cd = str[2];
    		}else{
    			searchJson.Dpt_AirPt_Cd = str[0];
    			searchJson.Arrv_Airpt_Cd = str[1];
    		}
			var di = $(".psc-sod-body-content");
			di.remove();
			getLineData(searchJson);
	 	});
 }
 //组装查询参数
 function getSearchJosn(){
	 var searchJson = new Object();
		var object = parent.supData;
		var flight = object.flight;
		var lane = object.lane;
		var dpt = flight.split("/"); 
		if($("#inputNum").val()!=""){
			searchJson.flt_nbr_Count = $("#inputNum").val();
		}else{
			searchJson.flt_nbr_Count = dpt[0];
		}
		var nowdate=new Date();
		searchJson.startTime = addDate(nowdate,-1);
		searchJson.endTime = addDate(nowdate,7);
		return searchJson;
 }
 //得到航线详细信息
function getLineData(searchJson){
	$.ajax({
		type:'post',
        url:'${pageContext.request.contextPath}/getBuyTicketReportLineData',	
        data:searchJson,
        success:function(data){
        	$("#flyNum").html(data.flyNum);   
        	if(data.list.toString()!=""){
        		var a = "";
        		var arrrall = [-1,0,1,2,3,7,15,30];
        		for(var index in data.list){
        			//最大值日期和值
        			var maxx = data.list[index].maxDayandDate.split(",");
        			//起飞日期
        			var datea = data.list[index].fltNumDate
        			var datee = addDate(new Date(),0);
        			var maxDay = DateDiff(datea,maxx[0]);
        			var chazhiDay = DateDiff(datea,datee);
        			var arrr = [];
        			arrr.length = 0;
        			for(var i =0;i<arrrall.length;i++){
        				if(arrrall[i]>=chazhiDay){
        					arrr.push(arrrall[i]);
        				}
        			}
        			var dateeStrr = datea.split("-");
        			var today ;
        			var todayarr;
        			if(chazhiDay==-1){today = data.list[index].progressf1;}
        			if(chazhiDay==0){today = data.list[index].progress0;}
        			if(chazhiDay==1){today = data.list[index].progress1;}
        			if(chazhiDay==2){today = data.list[index].progress2;}
        			if(chazhiDay==3){today = data.list[index].progress3;}
        			if(chazhiDay==4){today = data.list[index].progress4;}
        			if(chazhiDay==5){today = data.list[index].progress5;}
        			if(chazhiDay==6){today = data.list[index].progress6;}
        			if(chazhiDay==7){today = data.list[index].progress7;}
        			if(today==null||today=="null"){
        				today = "0%,0";
        			}
        			if($.inArray(maxDay, arrr)<0){
        				arrr.push(maxDay);
        			}
        			if($.inArray(chazhiDay, arrr)<0){
        				arrr.push(chazhiDay);
        			}
        			//数组从小到大排序
        			arrr.sort(function(a,b){
        	            return a-b;
                    });
        			
        			todayarr = today.split(",");
        			if(todayarr[0]=="0%"){
        				a += " <div class='psc-sod-body-content'><p>"+dateeStrr[1]+"-"+dateeStrr[2]+"</p><div class='psc-sod-body-content-progressBar'><div><div style='opacity:0;width:"+todayarr[0]+"'><span>"+todayarr[0]+"/"+todayarr[1]+"</span></div><ul class='psc-sod-body-content-progressBar-identifier'>"
        			}else{
        				a += " <div class='psc-sod-body-content'><p>"+dateeStrr[1]+"-"+dateeStrr[2]+"</p><div class='psc-sod-body-content-progressBar'><div><div style='width:"+todayarr[0]+"'><span>"+todayarr[0]+"/"+todayarr[1]+"</span></div><ul class='psc-sod-body-content-progressBar-identifier'>"
        			}
        			var temparr = [];
        			temparr.length = 0;
        			for(var i = 0;i<arrr.length;i++){
        				var today;
        				var todayarr;
        				var temp;
        				if(arrr[i]==30){today = data.list[index].progressd30;}
        				if(arrr[i]==-1){today = data.list[index].progressf1;}
        				if(arrr[i]==0){today = data.list[index].progress0;}
        				if(arrr[i]==1){today = data.list[index].progress1;}
        				if(arrr[i]==2){today = data.list[index].progress2;}
        				if(arrr[i]==3){today = data.list[index].progress3;}
        				if(arrr[i]==4){today = data.list[index].progress4;}
        				if(arrr[i]==5){today = data.list[index].progress5;}
        				if(arrr[i]==6){today = data.list[index].progress6;}
        				if(arrr[i]==7){today = data.list[index].progress7;}
        				if(arrr[i]==8){today = data.list[index].progress8;}
        				if(arrr[i]==9){today = data.list[index].progress9;}
        				if(arrr[i]==10){today = data.list[index].progress10;}
        				if(arrr[i]==11){today = data.list[index].progress11;}
        				if(arrr[i]==12){today = data.list[index].progress12;}
        				if(arrr[i]==13){today = data.list[index].progress13;}
        				if(arrr[i]==14){today = data.list[index].progress14;}
        				if(arrr[i]==15){today = data.list[index].progress15;}
        				if(arrr[i]==16){today = data.list[index].progress16;}
        				if(arrr[i]==17){today = data.list[index].progress17;}
        				if(arrr[i]==18){today = data.list[index].progress18;}
        				if(arrr[i]==19){today = data.list[index].progress19;}
        				if(arrr[i]==20){today = data.list[index].progress20;}
        				if(arrr[i]==21){today = data.list[index].progress21;}
        				if(arrr[i]==22){today = data.list[index].progress22;}
        				if(arrr[i]==23){today = data.list[index].progress23;}
        				if(arrr[i]==24){today = data.list[index].progress24;}
        				if(arrr[i]==25){today = data.list[index].progress25;}
        				if(arrr[i]==26){today = data.list[index].progress26;}
        				if(arrr[i]==27){today = data.list[index].progress27;}
        				if(arrr[i]==28){today = data.list[index].progress28;}
        				if(arrr[i]==29){today = data.list[index].progress29;}
        				if(today==null||today=="null"){
        					today = "0%,0";
        				}else{
	        				var content = ""; 
	        				todayarr = today.split(",");
	        				if($.inArray(todayarr[0], temparr)<0){
	        					temparr.push(todayarr[0]);
	        					content =  todayarr[0].replace("%","");
	        					a += "<li mystyle='"+todayarr[0]+"' style='left:"+todayarr[0]+"' onmouseover='showData(this,"+content+","+todayarr[1]+");' onmouseout='leaveData(this)'> <div >&#xe61e;</div><p>"+arrr[i]+"</p></li>"
	        				}else{
	        					a += "<li mystyle='"+todayarr[0]+"' style='left:"+todayarr[0]+";display:none'> <div >&#xe61e;</div><p>"+arrr[i]+"</p></li>"
	        				}
        				}
        			}
        			a += " </ul></div><div style='width:"+maxx[1]+"%'></div> <div style='display:none'class='psc-sod-body-content-progressBar-prompt'><p></p><span></span></div></div></div>";
        		}
        		$("#dataarea").after(a);
        	}
        	
        }
	});
}
 //鼠标移上去事件
 function showData(mythis,dayy,persons){
	var left = $(mythis).css("left");
	var values = "";
	var divv = $(mythis).parent().parent().next().next();
	var liOjb = $(mythis).parent().children();
	for(var i=0;i<liOjb.length;i++){
		if($(liOjb[i]).css("display")=="none"){
			var leftt = $(liOjb[i]).attr("mystyle");
			if(leftt==$(mythis).attr("mystyle")){
				values =values +""+ $(liOjb[i]).find("p").html()+",";
			}
		}else{
			if($(liOjb[i]).attr("mystyle")==$(mythis).attr("mystyle")){
				values =values + $(liOjb[i]).find("p").html()+",";
			}
		}
	}
	values = values.substring(0,values.length-1);
	divv.find("p").html("距离起飞"+values+"天,"+dayy+"%,"+persons+"人");
	var widthh = $(divv).css("width");
	divv.css({"left":dayy+"%","margin-left":-(parseInt(widthh.replace("px","")))/2+"px"});
	divv.css("display","");
 }
 //鼠标离开事件
 function leaveData(mythis){
	var divv = $(mythis).parent().parent().next().next();
	divv.css("display","none");
 }
 //数组移除指定值
function removeByValue(arr, val) {
	  for(var i=0; i<arr.length; i++) {
	    if(arr[i] == val) {
	      arr.splice(i, 1);
	      break;
	    }
	  }
}
 //日期加减函数
function addDate(date,days){ 
	var d=new Date(date); 
	d.setDate(d.getDate()+days); 
	var month=d.getMonth()+1; 
	var day = d.getDate(); 
	if(month<10){ 
	month = "0"+month; 
	} 
	if(day<10){ 
	day = "0"+day; 
	} 
	var val = d.getFullYear()+"-"+month+"-"+day; 
	return val; 
}
 //两个日期相减
function DateDiff(d1,d2){
	var day = 24 * 60 * 60 *1000;
	try{  
		  var dateArr = d1.split("-");
		  var checkDate = new Date();
		  checkDate.setFullYear(dateArr[0], dateArr[1]-1, dateArr[2]);
		  var checkTime = checkDate.getTime();
		  var dateArr2 = d2.split("-");
		  var checkDate2 = new Date();
		  checkDate2.setFullYear(dateArr2[0], dateArr2[1]-1, dateArr2[2]);
		  var checkTime2 = checkDate2.getTime();
		  var cha = (checkTime - checkTime2)/day; 
		  return cha;
	  }catch(e){
	 	  return false;
	  }
	}
 </script>