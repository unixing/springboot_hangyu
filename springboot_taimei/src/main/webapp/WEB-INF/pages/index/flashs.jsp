<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>太美航空</title>
<style>
.aixuexi {
	height: 1080px;
}
</style>
<style type="text/css">
#backImg {
	background: url("${pageContext.request.contextPath}/images/lz1.jpg");
}
</style>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<%@include file="/WEB-INF/pages/common/css.jsp"%>
<%@include file="/WEB-INF/pages/common/commonNew.jsp"%>
<%@include file="/WEB-INF/pages/index/js/homePageJS.jsp"%>
<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
</head>
<body>
	<div id="backImg" style="width: 100%; height: 100%;">
		<div id="airportMapa" class="aixuexi"></div>
	</div>
</body>
<script type="text/javascript">
	window.onload = function(){
		$.ajax({
			url:'${pageContext.request.contextPath}/getAirportOnLineData',
			data:[],
			type:'post',
			success:function(data){
				airportMap2(data.airportOnLineData,"airportMapa",data.cityCoordinateList)
			}
		});
	}
	
	
	function airportMap2(data,id,cityCoordinateList){
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
          	arrAirport2.value = 70;
        	if(index==1){
        		nameTitle = data[index].airport+"蓝田机场在飞航线";
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

          	var color = ['#104E8B'];
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
          	            color: '#104E8B',
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
          	                position: 'left',
          	                formatter: '{b}',
          	                textStyle :{
          	                	fontSize:18,
          	                	fontWeight:'bold'
          	                }
          	            }
          	        },
          	        symbolSize: function (val) {
          	            return val[2] / 8;
          	        },
          	        itemStyle: {
          	            normal: {
          	                color: color[i],
          	              	fontSize:24
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
          		      backgroundColor: '',
	         		  	title : {
	         	        text: nameTitle,
	         	        left: 'center',
	         	        textStyle : {
	         	        	color: '#F0F8FF',
	           	            fontSize:48
	         	        },
          				top:'5%'
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
          		            color: 'white',
          		          	fontSize:24
          		        }
          		    },
          		    geo: {
          		        map: 'china',
          		        label: {
          		            emphasis: {
          		                show: true
          		            }
          		        },
          		        roam: true,
          		        itemStyle: {
          		            normal: {
          		                areaColor: '#FAFAD2',
          		                borderColor: '#5D478B'
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
</html>