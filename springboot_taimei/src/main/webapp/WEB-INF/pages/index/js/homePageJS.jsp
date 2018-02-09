<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/echarts/build/dist/china.js"
	type="text/javascript"></script>
<script type="text/javascript">
function aaa(){
	location.href ="/getFlash"
}
$(function(){ 
	$.ajax({
		url:'${pageContext.request.contextPath}/getAirportData',
		data:[],
		type:'post',
		success:function(data){
			$("#zrsy").html(""+fomatDigit(data.yesterdayEarnings,0));
			$("#zrzbc").html("  "+fomatDigit(data.yesterdayClasses,0));
			$("#zrrg").html("  "+fomatDigit(data.yesterdayPutin,0));
			$("#zrcg").html("  "+fomatDigit(data.yesterdayLeave,0));
		}
	});
	$.ajax({
		url:'${pageContext.request.contextPath}/getAirportFlowData',
		data:[],
		type:'post',
		success:function(data){
			flowEcharts(data.airportFlowDataList,"flowData",data.airportFlowDataList2);
		}
	});
	$.ajax({
		url:'${pageContext.request.contextPath}/getAirportNormData',
		data:[],
		type:'post',
		success:function(data){
			zhibiaoData(data.airportKzlData,"zhibiaoData",1)
		}
	});
	$.ajax({
		url:'${pageContext.request.contextPath}/getAirportOnLineData',
		data:[],
		type:'post',
		success:function(data){
			airportMap(data.airportOnLineData,"airportMap",data.cityCoordinateList)
		}
	});
});

function zhibiaoOnclick(index){
	$.ajax({
		url:'${pageContext.request.contextPath}/getAirportNormData',
		data:[],
		type:'post',
		success:function(data){
			if(index==1){
				zhibiaoData(data.airportKzlData,"zhibiaoData",1)
				$("#kzl").css("color","#75A5C2");
				$("#zgl").css("color","black");
				$("#zsr").css("color","black");
			}
			if(index==2){
				zhibiaoData(data.airportZglData,"zhibiaoData",2)
				$("#kzl").css("color","black");
				$("#zgl").css("color","#75A5C2");
				$("#zsr").css("color","black");
			}
			if(index==3){
				zhibiaoData(data.airportZsrData,"zhibiaoData",3)
				$("#kzl").css("color","black");
				$("#zgl").css("color","black");
				$("#zsr").css("color","#75A5C2");
			}
			
		}
	});
}
function flowEcharts(data,id,data2){
	
	 var one = [];
 	var oneName = [];
 	var twoName = [];
    var tenName = '		机场最新流量走势';
	for(var index in data){
		var date1 = [];
		date1 =  data[index].date.split("-");
    	one[index] = date1[1]+"-"+date1[2];
    	oneName[index] = data[index].persons;
    }
    for(var i in data2){
    	twoName[i] = (data2[i].zsr/100).toFixed(2);
    }
	var chart = echarts.init(document.getElementById(id));
	 option = {
			 title : {
      	        text: tenName,
      	        left: 'left',
      	        textStyle : {
      	            color: '#454545',
      	            fontSize:14
      	        }
      	    },
      	    grid :{
      	    	x:70
      	    },
	 	    tooltip : {
	 	        trigger: 'axis'
	 	    },
	 	    legend: {
	 	    	data: ['','客流/日（人）','收入/日/100（元）'] ,
	 	    	x:'right',
	 	    },
	 	    calculable : true,
	 	    xAxis : [
	 	        {
	 	            type : 'category',
	 	            boundaryGap : false,
	 	            data : one,
	 	           axisLabel: {
                   	rotate: 0,
                   }
	 	        }
	 	    ],
	 	    yAxis : [
	 	        {
	 	            type : 'value'
	 	        }
	 	    ],
	 	    series : [
	 	        {
 	        		name: "客流/日（人）",  
                    type: 'line', 
                    smooth:true,
	 	            label: {
	 	                normal: {
	 	                    show: false,
	 	                    position: 'top'
	 	                }
	 	            },
	 	           	symbol:'triangle',
	 	           	symbolSize:'8',
	 	            itemStyle: {normal: {areaStyle: {type: 'default', color:'#75A5C2'},color:'#9DC0CE',borderColor: '#9DC0CE'}},
	 	            data: oneName
	 	        },
	 	        {
	 	        	name: "收入/日/100（元）",  
                    type: 'line', 
                    smooth:true,
	 	            label: {
	 	                normal: {
	 	                    show: false,
	 	                    position: 'top'
	 	                }
	 	            },
	 	           	symbolSize:'8',
	 	            itemStyle: {normal: {areaStyle: {type: 'default',color:'#E3E3E3'},color:'#518498', borderColor: '#518498'}},
	 	            data: twoName
	 	        }
	 	    ]
	 }
	 chart.setOption(option);  
	 chart.hideLoading();  
};

function zhibiaoData(data,id,indexx){
     var chart = echarts.init(document.getElementById(id));
     var zhibiao = '座公里(元)';
     if(indexx==1){
     	zhibiao = '客座率';
 	}
 	if(indexx==2){
 		zhibiao = '座公里(元)';
 	}
 	if(indexx==3){
 		zhibiao = '总收入(元)';
 	}
     
    var one = [];
	var oneName = [];
    var tenName = '		最新收益指标排行';
    
    for(var index in data){
     	one[index] = data[index].flyNum;
     	if(indexx==1){
     		oneName[index] = Number(data[index].kzl).toFixed(2);
     	}
     	if(indexx==2){
     		oneName[index] = Number(data[index].zgl).toFixed(2);
     	}
     	if(indexx==3){
     		oneName[index] = Number(data[index].zsr).toFixed(2);
     	}
    }
   	    option = {
   	    		title : {
   	      	        text: tenName,
   	      	        left: 'left',
   	      	        textStyle : {
	   	      	       	color: '#454545',
	     	            fontSize:14
   	      	        }
   	      	    },
   	      	grid :{
      	    	x:70
      	    },
   	    	    tooltip : {
   	    	    	show: true
   	    	    },
   	    	    legend: {
   	    	    	data: ['',zhibiao] ,
   	    	    	show:true,
   	    	    	x:'right'
   	    	    },
   	    	    xAxis : [
   	    	        {
   	    	        	type : 'value'
   	    	        }
   	    	    ],
   	    	    yAxis : [
   	    	        {
    	            	type : 'category',
    	    	        data : one
   	    	        }
   	    	    ],
   	    	    series : [
   	    	        {
   	    	        	name: zhibiao,  
                        type: 'bar',  
   	    	            data: oneName,
   	    	         	label: {
 	 	                	normal: {
	 	 	                    show: false,
	 	 	                  	formatter: function(a){
	 	 	                  		return ""
	 	 	                  	}
 	 	                	},
   	    	     			emphasis:{
   	    	     				show:false,
   	    	     				formatter:'{a}--{b}-- {c}'
   	    	     			}
 	 	            	},
   	    	            itemStyle: {normal: {color:'#9DC0CE'}}
   	    	        }
   	    	    ]
   	    	}
   	 chart.setOption(option);  
   	 chart.hideLoading();  
};

function airportMap(data,id,cityCoordinateList){
  		var chart = echarts.init(document.getElementById(id));
          var oneName2 = [];
          var oneName3 = [];
          var nameTitle = '';
          for(var index in data){
          	var one = [];
       	 	var airport = {};
       	 	var arrAirport = {};
          	var one2 = [];
       	 	var airport2 = {};
       	 	var arrAirport2 = {};
          	airport2.name = data[index].airport;
          	arrAirport2.name = data[index].arrAirport;
          	arrAirport2.value = 50;
          	if(index==0){
          		nameTitle = data[index].airport+"机场在飞航线";
          	}
          	one2.push(airport2);
          	one2.push(arrAirport2);
          	oneName2.push(one2);
       }
          for(var index in data){
          	var one = [];
       	 	var airport = {};
       	 	var arrAirport = {};
          	var one2 = [];
       	 	var airport2 = {};
       	 	var arrAirport2 = {};
          	airport2.name = data[index].airport;
          	arrAirport2.name = data[index].arrAirport;
          	arrAirport2.value = index*10;
          	one2.push(airport2);
          	one2.push(arrAirport2);
          	oneName3.push(one2);
       }
          var geoCoordMap = {};
          
          for(var index in cityCoordinateList){
        	  var key = cityCoordinateList[index].cityName;
        	  var valuee =cityCoordinateList[index].cityCoordinatee;
        	  var array = valuee.split(",");
        	  geoCoordMap[key] =array;
          }
            var JCData = [];
            if(oneName2.length>0){
        	     JCData = oneName2;
            }else{
            	 JCData = [
           	                [{name:'北京'}, {name:'上海',value:95}],
						    [{name:'北京'}, {name:'广州',value:90}],
						    [{name:'北京'}, {name:'大连',value:80}],
						    [{name:'北京'}, {name:'南宁',value:70}],
						    [{name:'北京'}, {name:'南昌',value:60}],
						    [{name:'北京'}, {name:'拉萨',value:50}],
						    [{name:'北京'}, {name:'长春',value:40}],
						    [{name:'北京'}, {name:'包头',value:30}],
						    [{name:'北京'}, {name:'重庆',value:20}],
						    [{name:'北京'}, {name:'常州',value:10}]
           	               ];
            	 nameTitle = "暂无数据，这是模拟机场";
            }
          	var planePath = 'path://M1705.06,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705.06,1318.313z';
          	var convertData = function (data) {
          	    var res = [];
          	    for (var i = 0; i < data.length; i++) {
          	        var dataItem = data[i];
          	        var fromCoord = geoCoordMap[dataItem[0].name];
          	        var toCoord = geoCoordMap[dataItem[1].name];
          	        if (fromCoord && toCoord) {
          	            res.push([{
          	                name: dataItem[0].name,
          	                coord: fromCoord
          	            }, {
          	                name: dataItem[1].name,
          	                coord: toCoord
          	            }]);
          	        }
          	    }
          	    return res;
          	};

          	var color = ['#518498'];
          	var series = [];
          	[[nameTitle, JCData]].forEach(function (item, i) {
          	    series.push({
          	        name: nameTitle,
          	        type: 'lines',
          	        zlevel: 1,
          	        effect: {
          	            show: true,
          	            period: 6,
          	            trailLength: 0.7,
          	            color: '#518498',
          	            symbolSize: 3
          	        },
          	        lineStyle: {
          	            normal: {
          	                color: color[i],
          	                width: 0,
          	                curveness: 0.2
          	            }
          	        },
          	        data: convertData(item[1])
          	    },
          	    {
          	        name: nameTitle,
          	        type: 'lines',
          	        zlevel: 2,
          	        effect: {
          	            show: true,
          	            period: 6,
          	            trailLength: 0,
          	            symbol: planePath,
          	            symbolSize: 15
          	        },
          	        lineStyle: {
          	            normal: {
          	                color: color[i],
          	                width: 1,
          	                opacity: 0.4,
          	                curveness: 0.2
          	            }
          	        },
          	        data: convertData(item[1])
          	    },
          	    {
          	        name: nameTitle,
          	        type: 'effectScatter',
          	        coordinateSystem: 'geo',
          	        zlevel: 2,
          	        rippleEffect: {
          	            brushType: 'stroke'
          	        },
          	        label: {
          	            normal: {
          	                show: true,
          	                position: 'right',
          	                formatter: '{b}'
          	            }
          	        },
          	        symbolSize: function (val) {
          	            return val[2] / 8;
          	        },
          	        itemStyle: {
          	            normal: {
          	                color: color[i]
          	            }
          	        },
          	        data: item[1].map(function (dataItem) {
          	        	if(geoCoordMap[dataItem[1].name]== undefined){
          	        		return {
          	        			name: dataItem[1].name,
               	                value:  [116.24,39.55, "1"]
          	        		}
          	        	}
          	            return {
          	                name: dataItem[1].name,
          	                value: geoCoordMap[dataItem[1].name].concat([dataItem[1].value])
          	            };
          	        })
          	    });
          	});
          	option = {
          		    backgroundColor: 'WHITE',
	         		  title : {
	         	        text: nameTitle,
	         	        left: 'left',
	         	        textStyle : {
	         	        	color: '#454545',
	           	            fontSize:14
	         	        }
	         	    },
          		    tooltip : {
          		        trigger: 'item'
          		    },
	          		  toolbox: {
	                      show : true,
	                      orient: 'vertical',
	                      x: 'right',
	                      y: 'center',
	                      feature : {
	                          mark : {show: true},
	                          dataView : {show: true, readOnly: false},
	                          restore : {show: true},
	                          saveAsImage : {show: true}
	                      }
	                  },
          		    legend: {
          		        orient: 'vertical',
          		        top: 'bottom',
          		        left: 'right',
          		        data:[nameTitle],
          		        textStyle: {
          		            color: 'black'
          		        }
          		    },
          		    geo: {
          		        map: 'china',
          		        label: {
          		            emphasis: {
          		                show: false
          		            }
          		        },
          		        roam: true,
          		        itemStyle: {
          		            normal: {
          		                areaColor: '#E3E3E3',
          		                borderColor: 'WHITE'
          		            },
          		            emphasis: {
          		                areaColor: '#E3E3E3'
          		            }
          		        }
          		    },
          		    series: series
          		};
          	chart.setOption(option);  
          	chart.hideLoading();  
};
</script>