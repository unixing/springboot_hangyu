<%@page import="org.ldd.ssm.crm.utils.UserContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
var datas_i;//数据一对象
var datas_j;//数据二对象
var dataByName;
Date.prototype.format = function(format,i,j){ 
	var args = { 
			"M+" : this.getMonth() +i, 
			"d+" : this.getDate()-j, 
			"h+" : this.getHours(), 
			"m+" : this.getMinutes(), 
			"s+" : this.getSeconds(), 
			"q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter
			"S" : this.getMilliseconds() 
			};
	if(this.getDate()==1&&j>0){
		switch(this.getMonth()){
			case 5:case 7:case 10:case 12:
				args = { 
					"M+" : this.getMonth() -1, 
					"d+" : 30, 
					"h+" : this.getHours(), 
					"m+" : this.getMinutes(), 
					"s+" : this.getSeconds(), 
					"q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter
					"S" : this.getMilliseconds() 
					};
				if(/(y+)/.test(format)) 
					format = format.replace(RegExp.$1,(this.getFullYear() + "").substr(4 - RegExp.$1.length));
				break;
			case 0:
				args = { 
					"M+" : 11, 
					"d+" : 30, 
					"h+" : this.getHours(), 
					"m+" : this.getMinutes(), 
					"s+" : this.getSeconds(), 
					"q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter
					"S" : this.getMilliseconds() 
					};
				if(/(y+)/.test(format)) 
					format = format.replace(RegExp.$1,(this.getFullYear()-1 + "").substr(4 - RegExp.$1.length));
				break;
			case 3:
				if((this.getFullYear()%4==0&&this.getFullYear()%100!=0)||this.getFullYear()%400==0){
					args = { 
							"M+" : this.getMonth() -1, 
							"d+" : 29, 
							"h+" : this.getHours(), 
							"m+" : this.getMinutes(), 
							"s+" : this.getSeconds(), 
							"q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter
							"S" : this.getMilliseconds() 
							};
				}else{
					args = { 
							"M+" : this.getMonth() -1, 
							"d+" : 28, 
							"h+" : this.getHours(), 
							"m+" : this.getMinutes(), 
							"s+" : this.getSeconds(), 
							"q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter
							"S" : this.getMilliseconds() 
							};
				}
				if(/(y+)/.test(format)) 
					format = format.replace(RegExp.$1,(this.getFullYear() + "").substr(4 - RegExp.$1.length));
				break;
			case 2:case 4:case 6:case 8:case 9:case 11:
				args = { 
					"M+" : this.getMonth() -1, 
					"d+" : 31, 
					"h+" : this.getHours(), 
					"m+" : this.getMinutes(), 
					"s+" : this.getSeconds(), 
					"q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter
					"S" : this.getMilliseconds() 
					};
				if(/(y+)/.test(format)) 
					format = format.replace(RegExp.$1,(this.getFullYear() + "").substr(4 - RegExp.$1.length));
				break;
			case 1:
				args = { 
					"M+" : 12, 
					"d+" : 31, 
					"h+" : this.getHours(), 
					"m+" : this.getMinutes(), 
					"s+" : this.getSeconds(), 
					"q+" : Math.floor((this.getMonth() + 3) / 3),  //quarter
					"S" : this.getMilliseconds() 
					};
				if(/(y+)/.test(format)) 
					format = format.replace(RegExp.$1,(this.getFullYear()-1 + "").substr(4 - RegExp.$1.length));
				break;
		}
	}else{
		if(/(y+)/.test(format)) 
			format = format.replace(RegExp.$1,(this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for(var i in args) { 
		var n = args[i]; 
		if(new RegExp("("+ i +")").test(format)) 
			format = format.replace(RegExp.$1,RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length)); } 
	return format;
	};
	
//设置查询参数
$("#lcl_Dpt_Tm").val(new Date().format("yyyy-MM-dd",0,1));
$("#lcl_Arrv_Tm").val(new Date().format("yyyy-MM-dd",1,0));
var supData = parent.supData.lane.split("=");
if(supData.length==2){
	$("#dpt_AirPt_Cd").val(supData[0]);
	$("#arrv_Airpt_Cd").val(supData[1]);
}
if(supData.length==3){
	$("#dpt_AirPt_Cd").val(supData[0]);
	$("#pas_stp").val(supData[1]);
	$("#arrv_Airpt_Cd").val(supData[2]);
}
function getSearchJson(){
	var searchJson = {}; 
	searchJson.dpt_AirPt_Cd = $("#dpt_AirPt_Cd").val();
	searchJson.pas_stp = $("#pas_stp").val();
	searchJson.arrv_Airpt_Cd = $("#arrv_Airpt_Cd").val();
	searchJson.lcl_Dpt_Tm = $("#lcl_Dpt_Tm").val();
	searchJson.lcl_Arrv_Tm = $("#lcl_Arrv_Tm").val();
	searchJson.flt_nbr = $("#flt_nbr").val();
	return searchJson;
}

function getairCode(searchJson){
	var airports = parent.airportMap;
	if(searchJson.dpt_AirPt_Cd!=""){
		searchJson.dpt_AirPt_Cd = airports[searchJson.dpt_AirPt_Cd].iata;
	}
	if(searchJson.pas_stp!=""){
		searchJson.pas_stp = airports[searchJson.pas_stp].iata;
	}
	if(searchJson.arrv_Airpt_Cd!=""){
		searchJson.arrv_Airpt_Cd = airports[searchJson.arrv_Airpt_Cd].iata;
	}
	return  searchJson;
}

var searchJson = getairCode(getSearchJson());
//参数准备完毕------------------------------------------------------
function lis(){
	$(".sr-box-body-report > ul").empty();
	if($("#pas_stp").val()!=""&&$("#pas_stp").val()!=null){
		var itia = $("#cityChoice").val();
		if(itia==searchJson.dpt_AirPt_Cd){
			append($("#dpt_AirPt_Cd").val(),$("#pas_stp").val(),-1);
			append($("#dpt_AirPt_Cd").val(),$("#arrv_Airpt_Cd").val(),-2);
		}
		if(itia==searchJson.pas_stp){
			append($("#dpt_AirPt_Cd").val(),$("#pas_stp").val(),-1);
			append($("#pas_stp").val(),$("#arrv_Airpt_Cd").val(),-2);
	
		}
		if(itia==searchJson.arrv_Airpt_Cd){
			append($("#dpt_AirPt_Cd").val(),$("#arrv_Airpt_Cd").val(),-1);
			append($("#pas_stp").val(),$("#arrv_Airpt_Cd").val(),-2);
		}
	}else{
		var li = "<li id='li1'  class='liset' style='padding-top: 170px; height: 100%' onclick='getAirPortData(0);'><div style= 'padding-top: 400%;'>"+
		$("#dpt_AirPt_Cd").val()+"<br>↑↓<br>"+
		$("#arrv_Airpt_Cd").val()+
		"</div></li>";
		$(".sr-box-body-report > ul").append(li);
	}
}
lis();
getSourceDistriData(getairCode(getSearchJson()));
function check(){
	lis();
	var searchJson = getSearchJson();
	if(searchJson.dpt_AirPt_Cd==''||searchJson.arrv_Airpt_Cd==''){
		return alert("请输入始发地和到达地");
	}
	getSourceDistriData(getairCode(getSearchJson()));
}
function getSourceDistriData(searchJson){
	$.ajax({
		url:'${pageContext.request.contextPath}/getSourceDistriData',
		data:searchJson,
		success:function(data){
			datas_i = new ;
			datas_j = null;
			if(data!=null&&data.length>0){
				if(data[0]&&data[1]){
					datas_i = data[0];
					datas_j = data[1];
					checkSourceDistribution();
				}else if(data[0]){
					datas_i = data[0];
					checkSourceDistribution();
				}else{
					var i;
					datas_i = i;
					datas_j = i;
					$("#main").empty();
					$("#main1").empty();
					$("#main2").empty();
					$("#main3").empty();
				}
			}else{
				alert('没有可以展示的数据，请重新选择');
			}
		}
	});
}
function append(i,j,k){
	var li = "";
	if(k==-1){
		li = "<li id='li1'  class='liset' style='padding-top: 170px; height: 30%' onclick='SourceDistribution(0);'><div>";
	}
	if(k!=-1){
		li = "<li id='li2' style='padding-top: 170px; height: 30%' onclick='SourceDistribution(1);'><div>";
	}
	li += i+"<br><font size='1px'>↑↓</font><br>" + j + "</div></li>";
	$(".sr-box-body-report > ul").append(li);
}

function ontable(name){
	var searchJson = getSearchJson();
	searchJson.dpt_AirPt_Cd = $(".liset").text().split("↑↓")[0];
	searchJson.arrv_Airpt_Cd = $(".liset").text().split("↑↓")[1];
	searchJson =  getairCode(searchJson);
	searchJson.name = name;
	$("#mcity").html(name+"客源分布");
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/getSourceDistriDataByName',
		async:false,
		data:searchJson,
		success:function(data1){
			if(name=="北京"||name=="上海"||name=="天津"||name=="重庆"){
				var count = 0;
				for(var i=0;i<data1.length;i++){
					count += data1[i].number;
				}
				var obj = new Object();
				obj.city = name;
				obj.number = count;
				dataByName = [obj];
			}else{
				dataByName = data1;
			}
		}
	});
	graphics2("main1");
}
function checkSourceDistribution(){
	graphics("main",datas_i);
	ontable(datas_i.sous[0].city);
	$("#mcity").html(datas_i.sous[0].city+"客源分布");
	graphics2("main1");
	graphics_pie("main2",datas_i);
	graphics_pie2("main3",datas_i);
}
var k = 5;	
function SourceDistribution(i){
		if(i==0){
			if(k!=i){
				k=i;
				tag1();
				//组装新的数据图形
				if(datas_i){
					graphics("main",datas_i);
					ontable(datas_i.sous[0].city);
					$("#mcity").html(datas_i.sous[0].city+"客源分布");
					graphics2("main1");
					graphics_pie("main2",datas_i);
					graphics_pie2("main3",datas_i);
				}
			}
		}else{
			if(k!=i){
				k=i;
				tag2();
				//组装新的数据图形
				if(datas_j){
					graphics("main",datas_j);
					ontable(datas_j.sous[0].city);
					$("#mcity").html(datas_i.sous[0].city+"客源分布");
					graphics2("main1");
					graphics_pie("main2",datas_j);
					graphics_pie2("main3",datas_j);
				}
			}
		}
	
	
}
//选项卡1
function tag1(){
	$("#li2").attr("class","");
	$("#li1").attr("class","liset");
}
// 选项卡2
function tag2(){
	$("#li1").attr("class","");
	$("#li2").attr("class","liset");
}

function graphics(id,data){
	
	var arr =  [];
	var arr_nbr = [];
	for(var i in data.sous){
		arr[i] = data.sous[i].city;
		arr_nbr[i] = data.sous[i].number;
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
          var myChart = ec.init(document.getElementById(id)); 
          
          var option = {
          	color: ['#00FFFF'],
              tooltip: {
                  show: true
              },
              xAxis : [
                  {
                      type : 'category',
                      data : arr,
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
                      "barWidth" : 15,
                      "data":arr_nbr
                      
                  }
              ]
          };
  
          // 为echarts对象加载数据 
          myChart.setOption(option); 
	    var ecConfig = require('echarts/config');
	  	function eConsole(param) {
	  		ontable(param.name);
	  	}
	  	myChart.on(ecConfig.EVENT.CLICK, eConsole);
     }
  );
}
function graphics2(id){
	var arr =  [];
	var arr_nbr = [];
	for(var i in dataByName){
		arr[i] = dataByName[i].city;
		arr_nbr[i] = dataByName[i].number;
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
          var myChart = ec.init(document.getElementById(id)); 
          
          var option = {
          	color: ['#00FFFF'],
              tooltip: {
                  show: true
              },
              xAxis : [
                  {
                      type : 'category',
                      data : arr,
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
                      "barWidth" : 15,
                      "data":arr_nbr
                      
                  }
              ]
          };
  
          // 为echarts对象加载数据 
          myChart.setOption(option); 
      }
  );
}
function graphics_pie(id,data){
	var man = data.manNumber;
	var wuman = data.wumanNumber;
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
	          'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
	      ],
	      function (ec) {
	          // 基于准备好的dom，初始化echarts图表
	          var myChart = ec.init(document.getElementById(id)); 
	          
	          var option = {
	        		  
	        		    tooltip : {
	        		        trigger: 'item',
	        		        formatter: "{a} <br/>{b} : {c} ({d}%)"
	        		    },
	        		    legend: {
	        		        orient: 'vertical',
	        		        x: 'right',
							y: 'top',     
	        		        data: ['男','女'],
		        		    textStyle: {
		                        color: '#fff'
		                    }
	        		    },
	        		    series : [
	        		        {
	        		            name: '访问来源',
	        		            type: 'pie',
	        		            data:[
	        		                {value:man, name:'男'},
	        		                {value:wuman, name:'女'},
	        		            ],
	        		            itemStyle: {
	        		                emphasis: {
	        		                    shadowBlur: 10,
	        		                    shadowOffsetX: 0,
	        		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	        		                }
	        		            }
	        		        }
	        		    ]
	        		};
	          // 为echarts对象加载数据 
	          myChart.setOption(option); 
	      }
	  );
}
function graphics_pie2(id,data){
	var man = data.age;
	
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
	          'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
	      ],
	      function (ec) {
	          // 基于准备好的dom，初始化echarts图表
	          var myChart = ec.init(document.getElementById(id)); 
	          
	          var option = {
	        		    tooltip : {
	        		        trigger: 'item',
	        		        formatter: "{a} <br/>{b} : {c} ({d}%)"
	        		    },
	        		    legend: {
	        		        orient: 'vertical',
	        		        x: 'right',
							y: 'top',   
	        		        data: ['2岁以下:','2-12岁:','12-22岁:','22-45岁:','','45-60岁:','60岁以上:'],
		        		    textStyle: {
		                        color: '#fff'
		                    }
	        		    },
	        		    series : [
	        		        {
	        		            name: '',
	        		            type: 'pie',
	        		            data:[
	        		                {value:man.age_1, name:'2岁以下:'},
	        		                {value:man.age_2, name:'2-12岁:'},
	        		                {value:man.age_3, name:'12-22岁:'},
	        		                {value:man.age_4, name:'22-45岁:'},
	        		                {value:man.age_5, name:'45-60岁:'},
	        		                {value:man.age_6, name:'60岁以上:'},
	        		            ],
	        		            itemStyle: {
	        		                emphasis: {
	        		                    shadowBlur: 10,
	        		                    shadowOffsetX: 0,
	        		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	        		                }
	        		            }
	        		        }
	        		    ]
	        		};
	          // 为echarts对象加载数据 
	          myChart.setOption(option); 
	      }
	  );
}
</script>