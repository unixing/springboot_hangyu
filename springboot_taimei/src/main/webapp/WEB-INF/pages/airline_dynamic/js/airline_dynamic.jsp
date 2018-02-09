<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
Date.prototype.format = function(format){ 
	var args = { 
			"M+" : this.getMonth() + 1, 
			"d+" : this.getDate()-1, 
			"h+" : this.getHours(), 
			"m+" : this.getMinutes(), 
			"s+" : this.getSeconds(), 
			"q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter
			"S" : this.getMilliseconds() 
			}; 
	if(/(y+)/.test(format)) 
		format = format.replace(RegExp.$1,(this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for(var i in args) { 
		var n = args[i]; 
		if(new RegExp("("+ i +")").test(format)) 
			format = format.replace(RegExp.$1,RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length)); } 
	return format;
	};
$("#cc2").val(new Date().format("yyyy-MM-dd"));
var datas;

function clean(){
	remonveAlert();
}
function OpenWindow(index,remark){
	if( $(".float-box").length==0 && $(".change-set-up").length==0 ){
		if(remark=='-0'){
			$("#"+index).after("<li class='change-set-up'> <ul class='last-column' style='left:70%;'> "
			+"<li><input type='checkbox' value='天气原因'>天气原因</li> "
			+"<li><input type='checkbox' value='机械故障'>机械故障</li> "
			+"<li><input type='checkbox' value='航空管制'>航空管制</li> "
			+"<li><input type='checkbox' value='流量控制'>流量控制</li> "
			+"<li><input type='checkbox' value='其他'>其他</li>"
			+" <li id='separated'><input id='textData' type='text' color='#fff' placeholder='分号隔开'></li> <li class='determine'>"
			
			+" <div><img onclick='clean(2);' alt='图片无法正常显示'  title='点击取消' src='/images/icon/关闭-(2).png' style='width: 14px; height: 12px; margin-left: 10px; display: inline;'></div> "
			+" <div><img  onclick='addOrUpdate("+index+");' alt='图片无法正常显示'  title='点击保存' src='/images/icon/确认.png' style='width: 14px; height: 12px; margin-left: 10px;display: inline;'></div>"
			
			+"</li> </ul> </li>")
		}else{
			var columnData = "<div class='float-box'>" 
			+"<ul class='first-column'>" ;
			var columnEnd = "<li class='set-up' onclick='add("+index+")' >"
			+"&#xe613;"
			+"</li>"
			+" </ul>"
			+" </div>";
			var da = remark.split("|");
			for(var d in da){
				if(da[d]!=""){
					columnData += "<li class='ordinary-column'>"
					+(parseFloat(d)+1)+"."+da[d]
					+"</li>"
				}
			}
			$("#"+index).after(columnData + columnEnd);
		}	
	}else{
		remonveAlert();
	}
}
function remonveAlert(){
	$(".float-box").remove();
	$(".change-set-up").remove();
}
function addOrUpdate(inds){
	var checkbox =  $("input[type='checkbox']");
	var text = "";
	for(var index in checkbox){
		if(checkbox[index].checked&&checkbox[index].value!='其他'){
			text+=checkbox[index].value+"|";
		}
	}
	var sp = $("#textData").val();
	var sps = sp.split(";");
	for(var index in sps){
		if(sps[index]!=''){
			text+=sps[index]+"|";
		}
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/airline_dynameic_save?index='+inds+'&sp='+text,
		success:function(data){
			if(data.success){
				alert('添加成功');
				remonveAlert();
				sessionData();
			}else{
				alert('添加失败');
			}
		}
	})
	
}
function add(index){
	$("#"+index).after("<li class='change-set-up'> <ul class='last-column' style='left:130%;'>"
			+"<li><input type='checkbox' value='天气原因'>天气原因</li> "
			+"<li><input type='checkbox' value='机械故障'>机械故障</li> "
			+"<li><input type='checkbox' value='航空管制'>航空管制</li> "
			+"<li><input type='checkbox' value='流量控制'>流量控制</li> "
			+"<li><input type='checkbox' value='其他'>其他</li>"
			+" <li id='separated'><input id='textData' type='text' color='#fff' placeholder='分号隔开'></li> <li class='determine'>"
			
			+" <div><img onclick='clean(2);' alt='图片无法正常显示'  title='点击取消' src='/images/icon/关闭-(2).png' style='width: 14px; height: 12px; margin-left: 10px; display: inline;'></div> "
			+" <div><img  onclick='addOrUpdate("+index+");' alt='图片无法正常显示'  title='点击保存' src='/images/icon/确认.png' style='width: 14px; height: 12px; margin-left: 10px;display: inline;'></div>"
			
			+"</li> </ul> </li>")
}
function sessionData(){
	var hidden = $("#isen").css('display');
	if(hidden=='block'){
		$.ajax({
			url:'${pageContext.request.contextPath}/airline_dynameic_list?date='+$("#cc2").val(),
			success:function(data){
				if(data.success){
					datas = data;
					var col = $(".b_color").val();
					if(col=="进港"){
						getDataj(data.list[0]);
					}else{
						getDatac(data.list[1]);
					}
				}
			}
		})
	}else{
		getBar();
	}
}
function clickd(i){
	if(i=='0'){
		$("#b_color").attr('class','b_color')
		$("#c_color").attr('class','c_color')
		getDataj(datas.list[0]);
	}else{
		$("#b_color").attr('class','c_color')
		$("#c_color").attr('class','b_color')
		getDatac(datas.list[1]);
	}
}
var height = $(".sr-box-body").height()*0.875;
console.debug(height);
function getDataj(data){
		$('#MyTimeTable').bootstrapTable("destroy");
		$('#MyTimeTable').bootstrapTable({
            data:data,
            height:height,
            columns:[
				{
					field: 'flt_nbr',
					title: "<div style='color: white; font-size: 20px;'>航班号</div>",
					align: 'center',
					width: '80',
					},
				{
				field: 'dpt_AirPt_Cd',
				align: 'center',
				title: "<div style='color: white; font-size: 20px;'>始发地</div>",
				width: '80',
				},
				{
				field: 'arrv_Airpt_Cd',
				align: 'center',
				title: "<div style='color: white; font-size: 20px;'>到达地</div>",
				width: '80',
				},    
				{
				field: 'plan_q',
				align: 'center',
				title: "<div style='color: white; font-size: 20px;'>计划起飞</div>",
				width: '80',
				sortable: true,
				}, {
				field: 'actual_q',
				align: 'center',
				title: "<div style='color: white; font-size: 20px;'>实际起飞</div>",
				width: '80',
				sortable: true,
				}, {
				field: 'state',
				title: "<div style='color: white; font-size: 20px;'>状态</div>",
				align: 'center',
				width: '80',
				sortable: true,
				formatter:function(value,rows,index){
					if('取消'==value||'延误'==value){																	 
						return "<div id="+rows.id+" class='float'  style='margin-left: 25px;'>"+ value + "<img onclick=\"OpenWindow('"+rows.id+"','"+rows.remark+"')\" alt='图片无法正常显示'  title='点击可以添加备注' src='/images/icon/提醒-(1).png' style='width: 14px; height: 12px; margin-left: 10px;'></div>";
					}
					return value;
				}
				}, {
				field: 'zd_rate',
				align: 'center',
				title: "<div style='color: white; font-size: 20px;'>准点率</div>",
				width: '80',
				}
				]
        });
};
function getDatac(data){
		$('#MyTimeTable').bootstrapTable("destroy");
		$('#MyTimeTable').bootstrapTable({
			height:height,
            data:data,
            columns:[
				{
					field: 'flt_nbr',
					align: 'center',
					title: "<div style='color: white; font-size: 20px;'>航班号</div>",
					width: '80',
					},
				{
				field: 'dpt_AirPt_Cd',
				align: 'center',
				title: "<div style='color: white; font-size: 20px;'>始发地</div>",
				width: '80',
				},
				{
				field: 'arrv_Airpt_Cd',
				align: 'center',
				title: "<div style='color: white; font-size: 20px;'>到达地</div>",
				width: '80',
				},    
				{
				field: 'plan_c',
				align: 'center',
				title: "<div style='color: white; font-size: 20px;'>计划到达</div>",
				width: '80',
				sortable: true,
				}, {
				field: 'actual_c',
				align: 'center',
				title: "<div style='color: white; font-size: 20px;'>实际到达</div>",
				width: '80',
				sortable: true,
				}, {
				field: 'state',
				align: 'center',
				title: "<div style='color: white; font-size: 20px;'>状态</div>",
				width: '80',
				sortable: true,
				formatter:function(value,rows,index){
					if('取消'==value||'延误'==value){
						return "<span style='margin-left: 25px;'>"+ value + "<img id="+rows.id+" onclick='OpenWindow("+rows.id+","+rows.remark+");' alt='图片无法正常显示'  title='点击可以添加备注' src='/images/icon/提醒-(1).png' style='width: 14px; height: 12px; margin-left: 10px;'></span>"
					}
					return value;
				}
				}, {
				field: 'zd_rate',
				align: 'center',
				title: "<div style='color: white; font-size: 20px;'>准点率</div>",
				width: '80',
				}
				]
        });
};
sessionData();
var k = 5;	
function getAirPortData(i){
	if(i==0){
		if(k!=i){
			k=i;
			tag1();
			sessionData($("#cc2").val());
		}
	}else{
		if(k!=i){
			k=i;
			tag2();
			getBar();
		}
	}
}
// 选项卡1
function tag1(){
	$("#li2").attr("class","");
	$("#li1").attr("class","liset");
	$('#isp').attr("hidden","hidden"); 
	$('#isp2').attr("hidden","hidden"); 
	$('#main').attr("hidden","hidden"); 
	$('#isen').removeAttr("hidden"); 
}
// 选项卡2
function tag2(){
	$("#li1").attr("class","");
	$("#li2").attr("class","liset");
	$('#isen').attr("hidden","hidden"); 
	$('#isp').removeAttr("hidden"); 
	$('#isp2').removeAttr("hidden"); 
	$('#main').removeAttr("hidden"); 
	$('#MyTimeTable').bootstrapTable("destroy");
}
function getBar(){
	$.ajax({
		url:'${pageContext.request.contextPath}/airline_dynameic_graphics?date='+$("#cc2").val(),
		success:function(data){
			var dat = $("span");
			dat[7].innerText=data.month_zd+'%';
			dat[8].innerText=data.delay_cls;
			dat[9].innerText=data.cancel_cls;
			dat[10].innerText=data.normal_cls;
			graphics(data.exec);
		}
	});
}
function graphics(data){
	var xAiseArray = [];
	var dataArray = [];
	if(data!=null&&data.length>0){
		for(var i=data.length-1;i>-1;i--){
			var obj = data[i];
			xAiseArray.push(obj.name);
			dataArray.push(obj.val);
		}
	}else{
		alert('没有异常数据');
	}
	 // 路径配置
   require.config({
       paths: {
           echarts: 'http://echarts.baidu.com/build/dist'
       }
   });
   
   // 使用
   require(
       [
           'echarts',
           'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
       ],
       function (ec) {
           // 基于准备好的dom，初始化echarts图表
           var myChart = ec.init(document.getElementById('main')); 
           
           var option = {
           	color: ['#00FFFF'],
               tooltip: {
                   show: true,
                   formatter:function(params){
                	   return params.name+"："+params.value;
                   }
               },
               grid: {
                   left: '13%',
                   right: '14%',
                   bottom: '13%',
                   containLabel: true
               },
               xAxis : [
                   {
                       type : 'category',
                       data : xAiseArray,
                       axisTick: {
                           alignWithLabel: true
                       },
                       axisLabel: {
                           textStyle: {
                               color: '#fff'
                           }
                       }
                   }
               ],
               yAxis : [
                   {
                       type : 'value',
                       axisLabel: {
                           show: true,
                           textStyle: {
                               color: '#fff'
                           }
                       },
                       splitLine:{ 
                           show:false 
					   },
                       
                   }
               ],
               series : [
                   {
                       "name":"销量",
                       "type":"bar",
                       "barWidth" : 30,
                       "data":dataArray
                       
                   }
               ]
           };
   
           // 为echarts对象加载数据 
           myChart.setOption(option); 
       }
   );
}
</script>