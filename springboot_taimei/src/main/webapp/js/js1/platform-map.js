/*
 * @Author: lonhon 
 * @Date: 2017-11-10 11:52:13 
 * @Last Modified by: github.lonhon
 * @Last Modified time: 2018-01-08 16:59:41
 */
"use strict"
var supData={},allLineD,stated = 0,cityCoordinateList,airportMap = {},iataMap = {},airportName,airportCode,airPortName,Odata,oldStickOption = '',stickOption="",DataList,ops,airllist,foreignPort;
var af_state = false;
window.showLoading = function(flag){
	var odom = $('#TM-loading');
	if(flag){	//无背景色
		odom.css('background-color','rgba(29,44,72,0)');
	}
	odom.show();
}
window.closeLoading = function(){
	var odom = $('#TM-loading');
	odom.fadeOut('normal');
	odom.css('background-color','rgba(29,44,72,.6)');
}

//重置仪表盘数据
function instrument(d){
    var thisData = d;
    /*配置仪表盘机场-日期*/
    $(".material-fact-cityTime").text(thisData.success.timesection);
    // $("#cityName").text(thisData.success.airportAirName);    //转移到第一个接口中

    /*配置仪表盘设置*/
    //日期
    if(thisData.success.airportFocus.timeDemension=="最近1天"){
        $("#yesterday").prop("checked",true);
    }else if(thisData.success.airportFocus.timeDemension=="最近7天"){
        $("#sevenDay").prop("checked",true);
    }else if(thisData.success.airportFocus.timeDemension=="最近30天"){
        $("#thirtyDay").prop("checked",true);
    };
    //航班
    if(thisData.success.airportFocus.flightRange=="所有航班"){
        $("#allFlights").prop("checked",true);
    }else if(thisData.success.airportFocus.flightRange=="关注航班"){
        $("#focusFlights").prop("checked",true);
    }
    //数据统计
    $("#station").prop("checked",false);
    $("#jiltFly").prop("checked",false);
    if(thisData.success.airportFocus.dataRange.indexOf("包含过站")>-1){
        $("#station").prop("checked",true);
    }
    if(thisData.success.airportFocus.dataRange.indexOf("包含甩飞")>-1){
        $("#jiltFly").prop("checked",true);
    }
    //先清空
    $('.material-fact-cityBody').empty();
    //指标
    var domContainer = '';
    if(thisData.success.airportFocus.focusTarget.split("，").indexOf("客量")!=-1){
        $("#guestAmount").prop("checked",true);
        domContainer += "<div class='material-fact material-fact-amount'><span class='material-fact-icon'>&#xe63d;</span><div><p>客量（人）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+thisData.success.talCount+"</span>（出港：<span>"+thisData.success.travellerOut+"</span>/进港：<span>"+thisData.success.travellerIn+"</span>）</div></div></div>";
    };
    if(thisData.success.airportFocus.focusTarget.split("，").indexOf("班次")!=-1){
        $("#shift").prop("checked",true);
        domContainer += "<div class='material-fact-shift material-fact'><span class='material-fact-icon'>&#xe64d;</span><div><p>班次（班）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+thisData.success.fltcount+"</span></div></div></div>";
    }
    if(thisData.success.airportFocus.focusTarget.split("，").indexOf("客票收入")!=-1){
        $("#income").prop("checked",true);
        domContainer +="<div class='material-fact-peoples material-fact'><span class='material-fact-icon'>&#xe63b;</span><div><p>客票收入（元）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+thisData.success.talInc+"</span></div></div></div>";
    };
    if(thisData.success.airportFocus.focusTarget.split("，").indexOf("均班客量")!=-1){
        $("#passengerVolume").prop("checked",true);
        domContainer +="<div class='material-fact-shiftPeoples material-fact'><span class='material-fact-icon'>&#xe681;</span><div><p>均班客量（人）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+thisData.success.claPer+"</span></div></div></div>";
    };
    if(thisData.success.airportFocus.focusTarget.split("，").indexOf("均班收入")!=-1){
        $("#classIncome").prop("checked",true);
        domContainer +="<div class='material-fact-shiftPeoples material-fact'><span class='material-fact-icon'>&#xe63b;</span><div><p>均班收入（元）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+thisData.success.iddDct+"</span></div></div></div>";
    };
    if(thisData.success.airportFocus.focusTarget.split("，").indexOf("综合客座率")!=-1){
        $("#loadFactors").prop("checked",true);
        domContainer +="<div class='material-fact-zhfactors material-fact'><span class='material-fact-icon'>&#xe682;</span><div><p>综合客座率（%）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+ parseFloat(thisData.success.lodFor) +"</span></div></div></div>";
    };
    if(thisData.success.airportFocus.focusTarget.split("，").indexOf("机场准点率")!=-1){
        $("#airportPunctuality").prop("checked",true);
        domContainer +="<div class='material-fact-punctuality material-fact'><span class='material-fact-icon'>&#xe639;</span><div><p>机场准点率（%）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+thisData.success.airportIthad+"</span></div></div></div>";
    }
    $(".material-fact-cityBody").append(domContainer);
}
function getFocusData(eventPort){
	$.ajax(
	        {
	            type:'get',
	            url : '/getFocusData',
	            data:eventPort,
	            dataType : 'jsonp',
	            success : function(data) {
	                if(data){
                        Odata.success = $.extend(Odata.success,data.success);
                        instrument(data);
	                    $(".material-fact-installBox").css("display","none");
	                }
	            },
	            error : function() {
	            	
	            }
	        }
	    );
}
var dom = document.getElementById("container");
var myChart = echarts.init(dom);
window.mapDom = myChart;
var HBData = "";
var geoCoordMap = {};
var airData={};
/* 切换机场视图 */
var geoAirMap={};
var newData=[];
var planePath = 'path://M1705.06,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705.06,1318.313z';
var planePath1 = 'path://M511.983627 1022.005576c-177.413666 0-356.430852-48.131207-356.430852-155.653059 0-83.935668 122.605386-135.997394 236.549507-148.835793l6.237051-0.718361L240.863766 464.627063c-5.077645-9.096169-9.868765-18.74697-14.093996-28.512381l-4.1536-8.400321-0.652869-3.360538c-13.843286-35.848463-20.852934-73.371054-20.852934-111.49842 0-171.404812 139.484821-310.86098 310.917262-310.86098 171.427324 0 310.887586 139.456169 310.887586 310.86098 0 38.164205-7.024997 75.670423-20.906146 111.49842l-2.082428 5.38873 0.194428 0-1.603521 3.300162c-4.535293 10.694573-9.552563 20.965497-15.000646 30.699186L511.853667 914.597311l-64.345494-105.092523-2.430352 0.211824c-104.22783 8.933463-170.69873 37.702694-188.130751 53.720505l-3.148713 2.898003 3.148713 2.914376c22.738887 21.12411 110.175285 54.535057 248.78825 55.520501l12.375865 0.020466-0.016373-0.020466c138.357138-0.969071 225.969545-34.304294 248.898767-55.385425l3.229554-2.967588-3.284813-2.898003c-12.722766-11.271718-59.075467-33.511231-130.370233-46.566572l54.754045-87.675852c114.248044 25.745361 177.125093 74.360592 177.125093 137.074935 0 107.527992-179.017186 155.653059-356.453365 155.653059L511.983627 1022.004553zM511.977487 145.159054c-73.081459 0-132.527362 59.488883-132.527362 132.598994 0 73.05076 59.440787 132.48029 132.527362 132.48029 73.132624 0 132.62253-59.429531 132.62253-132.48029C644.600017 204.647937 585.110111 145.159054 511.977487 145.159054L511.977487 145.159054zM511.977487 145.159054';
var planePath2 = 'path://M786.496 161.163l-3.537-3.507C713.308 88.89 617.627 46.381 511.992 46.381c-105.507 0-201.123 42.418-270.76 111.073l-3.926 3.911c-68.646 69.668-111.074 165.237-111.074 270.775 0 105.633 42.495 201.295 111.275 270.965l3.522 3.508 270.978 271.006 271.169-271.146 3.132-3.164c68.889-69.697 111.459-165.424 111.459-271.164C897.772 326.521 855.277 230.86 786.496 161.163L786.496 161.163zM512.008 541.766c-74.545 0-134.974-60.447-134.974-134.977 0-30.806 10.283-59.152 27.614-81.856 24.663-32.299 63.574-53.151 107.359-53.151 74.55 0 134.978 60.463 134.978 135.008C646.985 481.318 586.558 541.766 512.008 541.766L512.008 541.766zM512.008 541.766';
var convertData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var dataItem = data[i];
        var fromCoord = geoCoordMap[dataItem[0].name];
        var toCoord = geoCoordMap[dataItem[1].name];
        if (fromCoord && toCoord) {
            res.push({
                fromName: dataItem[0].name,
                toName: dataItem[1].name,
                coords: [fromCoord, toCoord]
            });
        }
    }
    //去重
    var retres = [];
    for(var a in res){
    	var afromName = res[a].fromName;
    	var atoName = res[a].toName;
    	var acoords = res[a].coords;
    	var a = 1;
    	if(retres.length>0){
    		for(var b in retres){
    			var bfromName = retres[b].fromName;
            	var btoName = retres[b].toName;
            	if((afromName.trim()==bfromName.trim())&&(atoName.trim()==btoName.trim())){
            		a = 0;
            	}
        	}
    		if(a==1){
    			retres.push({
                    fromName: afromName,
                    toName: atoName,
                    coords: acoords
                });
    		}
    	}else{
    		retres.push({
                fromName: afromName,
                toName: atoName,
                coords: acoords
            });
    	}
    	
    }
    return retres;
};
var convertData1 = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        if (geoCoord) {
            res.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value)
            });
        }
    }
    return res;
};
var convertData2 = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoAirMap[data[i].name];
        if (geoCoord) {
            if(data[i].name==airData.oneself.airName){
                res.push({
                    name: data[i].name,
                    iata: data[i].iata,
                    value: geoCoord.concat(data[i].value),
                    symbol:planePath2,
                    symbolSize:[15, 20],
                    itemStyle:{
                        normal:{
                            borderColor:"#d85330",
                            color:"#d85330"
                        }
                    }
                });
            }else {
                res.push({
                    name: data[i].name,
                    iata: data[i].iata,
                    value: geoCoord.concat(data[i].value),
                    typeSelf:data[i].typeSelf,
                });
            }
        }
    }
    return res;
};
var color = ['#fb9c4e', '#ffa022', '#46bnewData9'];
var series = [];
var optioned="";
/**/
var oldmap = {
    'styleJson': [
                  { //海洋颜色
                      "featureType": "water",
                      "elementType": "all",
                      "stylers": {
                          "color": "#071327",
                          "visibility": "on"
                      }
                  },
                  { //陆地颜色
                      "featureType": "land",
                      "elementType": "all",
                      "stylers": {
                          "color": "#223350",
                          "visibility": "on"
                      }
                  },
                  { //行政区划（边界）
                      "featureType": "boundary",
                      "elementType": "all",
                      "stylers": {
                          "color": "#465b6c",
                          "visibility": "on"
                      }
                  },
                  { //城市主路
                      "featureType": "arterial",
                      "elementType": "all",
                      "stylers": {
                          "visibility": "off"
                      }
                  },
                  { //高速公路
                      "featureType": "highway",
                      "elementType": "all",
                      "stylers": {
                          "visibility": "off"
                      }
                  },
                  {  //行政标注
                      "featureType": "label",
                      "elementType": "all",
                      "stylers": {
                          "visibility": "off"
                      }
                  }
    ]
};
var option = {
        title : {
            show: false,
            text: '数聚空港',
            left: 'center',
            textStyle : {
                color: '#fff'
            }
        },
        bmap:{
                center:[],
                zoom: 6,
                color:"red",
                roam:"move",
                mapStyle: oldmap,
                scaleLimit:{
                    min:5,
                    max:10
                }
        	},
        tooltip : {
            show: false,
            showDelay:0,
            enterable:"true",
            alwaysShowContent:"false",//默认一直显示提示框
            triggerOn:"click",//点击触发提示框
            trigger: 'item',
            formatter:function(params){
                return '';
            }
        },
        series: series
    };
function demo(){
    series = [];
    [['出港航班', HBData]].forEach(function (item, i) {
        series.push(
            {
                name: item[0] + ' ',
                coordinateSystem: 'bmap',
                type: 'lines',
                zlevel: 0,
                effect: {
                    show: true,
                    period: 10,
                    trailLength: 0,
                    symbol: planePath,//planePath
                    color:"#7fbce9",
                    symbolSize: 8    //修改飞机大小
                },
                lineStyle: {
                    normal: {
                        color: "#4cc5f7", //航线颜色
                        width: 1.5,
                        opacity: 0.4,
                        curveness:0.2
                    },
                    emphasis:{
                        color:"#3887be"
                    }
                },
                polyline:false,
                data:DataList.data0
            },
            {
                type: 'scatter',
                coordinateSystem: 'bmap',
                data:convertData1([{name: Odata.success.airportName, value: 100}]),
                zlevel: 10,
                symbolSize: [15, 20],
                symbol:planePath2,
                symbolOffset:[0,-8],
                label: {
                    normal: {
                        show: true,
                        formatter:Odata.success.airportName,
                        position:"right",
                        textStyle:{
                            color:'white', //航班起点颜色
                            fontSize:12
                        },
                        itemStyle:{

                        }
                    },
                    emphasis: {
                        show: false
                    }
                },
                itemStyle: {
                    emphasis: {
                       
                    },
                    normal:{
                        color:'#d85230'
                    }
                }
            },
            {
                name: item[0] + ' ',
                type: 'scatter',
                coordinateSystem: 'bmap',
                symbol:'circle',
                symbolSize: 5,
                zlevel: 10,
                label: {
                    normal: {
                        show: true,
                        position: 'right',
                        formatter: '{b}'
                    },
                    emphasis:{
                        show:true
                    }
                },
                itemStyle: {
                    normal: {
                    	 color: "#7fbce9"  //终点或者经停点颜色
                    }
                },
                data:DataList.point0
            },
            {
                name: item[0] + ' ',
                coordinateSystem: 'bmap',
                type: 'lines',
                zlevel: 0,
                effect: {
                    show: true,
                    period: 10,
                    trailLength: 0,
                    symbol: planePath,//planePath
                    color:"#7fbce9",
                    symbolSize: 8    //修改飞机大小
                },
                lineStyle: {
                    normal: {
                        color: "#7fbce9", //x航线颜色
                        width: 1.5,
                        opacity: 0.4,
                        curveness:0.15,
                        type:'dashed'
                    },
                    emphasis:{
                        color:"#3887be"
                    }
                },
                polyline:false,
                data:DataList.data2
            },
            {
                name: item[0] + ' ',
                coordinateSystem: 'bmap',
                type: 'lines',
                zlevel: 0,
                lineStyle: {
                    normal: {
                        color: "#898989", //s航线颜色
                        width: 1.5,
                        opacity: 0.4,
                        curveness:0.1
                    },
                    emphasis:{
                        color:"#3887be"
                    }
                },
                polyline:false,
                data:DataList.data1
            },
            {
                name: item[0] + ' ',
                type: 'scatter',
                coordinateSystem: 'bmap',
                symbol:'circle',
                symbolSize: 5,
                zlevel: 10,
                label: {
                    normal: {
                        show: true,
                        position: 'right',
                        formatter: '{b}'
                    },
                    emphasis:{
                        show:true
                    }
                },
                itemStyle: {
                    normal: {
                        color: "#898989"  //终点或者经停点颜色
                    }
                },
                data: DataList.point1
            },
            {
                name: item[0] + ' ',
                type: 'scatter',
                coordinateSystem: 'bmap',
                symbol:'circle',
                symbolSize: 5,
                zlevel: 10,
                label: {
                    normal: {
                        show: true,
                        position: 'right',
                        formatter: '{b}'
                    },
                    emphasis:{
                        show:true
                    }
                },
                itemStyle: {
                    normal: {
                        color: "#7fbce9"  //终点或者经停点颜色
                    }
                },
                data:DataList.point2
            }
        );
    });
    option.series=series;
    optioned=option.series;
    myChart.setOption(option, true);
    stickOption=option;
}

function getCurrentSeasonFocusFlts(){
	$.ajax({
        type:'post',
        url : '/getCurrentSeasonFocusFlts',
        dataType : 'json',
        async:false,
        success : function(data){
            var arr=[];
            for(var key in data.map){
                var name='';
                var flightNumben=[];
                let ks=key.split('=');
                data.map[key].forEach((val)=>{
                    if(val.state==0){
                        flightNumben.push({flight:val.flightNumber,tag:false})
                    }else {
                        flightNumben.push({flight:val.flightNumber,tag:true})
                    }
                });
                ks.forEach((code)=>{
                    for(let code in airportMap){
                    	name+=airportMap[code].city+"=";
                    };
                });
                if(name.charAt(name.length-1)=="="){
                    name = name.substr(0, name.length-1);
                }
                arr.push({
                    name:name,
                    code:key,
                    data:flightNumben
                })
            }
            var storage=window.sessionStorage;
            storage.setItem('atten',JSON.stringify(arr));
            this.historyData=JSON.parse(JSON.stringify(arr));
            this.temporary=JSON.parse(JSON.stringify(arr));
            this.checked=data.focusFlag;
        }
    });
}

$(function(){
/********************************************地图JS*************************************************************/
    function instrument2(data){
        /*配置仪表盘机场-日期*/
        $(".material-fact-cityTime").text(data.success.timesection);
        /*配置仪表盘设置*/
        //日期
        if(data.success.airportFocus.timeDemension=="最近1天"){
            $("#yesterday").prop("checked",true);
        }else if(data.success.airportFocus.timeDemension=="最近7天"){
            $("#sevenDay").prop("checked",true);
        }else if(data.success.airportFocus.timeDemension=="最近30天"){
            $("#thirtyDay").prop("checked",true);
        };
        //航班
        if(data.success.airportFocus.flightRange=="所有航班"){
            $("#allFlights").prop("checked",true);
        }else if(data.success.airportFocus.flightRange=="关注航班"){
            $("#focusFlights").prop("checked",true);
        }
        //数据统计
        if(data.success.airportFocus.dataRange=="包含过站，包含甩飞"){
            $("#station").prop("checked",true);
            $("#jiltFly").prop("checked",true);
        }else if(data.success.airportFocus.dataRange=="包含过站"){
            $("#station").prop("checked",true);
        }else if(data.success.airportFocus.dataRange=="包含甩飞"){
            $("#jiltFly").prop("checked",true);
        };
        //先清空
        $('.material-fact-cityBody').empty();
        //指标
        if(data.success.airportFocus.focusTarget.split("，").indexOf("客量")!=-1){
            $("#guestAmount").prop("checked",true);
            $(".material-fact-cityBody").append("<div class='material-fact material-fact-amount'><span class='material-fact-icon'>&#xe63d;</span><div><p>客量（人）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+data.success.talCount+"</span>（出港：<span>"+data.success.travellerOut+"</span>/进港：<span>"+data.success.travellerIn+"</span>）</div></div></div>");
        };
        if(data.success.airportFocus.focusTarget.split("，").indexOf("班次")!=-1){
            $("#shift").prop("checked",true);
            $(".material-fact-cityBody").append("<div class='material-fact-shift material-fact'><span class='material-fact-icon'>&#xe64d;</span><div><p>班次（班）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+data.success.fltcount+"</span></div></div></div>");
        }
        if(data.success.airportFocus.focusTarget.split("，").indexOf("客票收入")!=-1){
            $("#income").prop("checked",true);
            $(".material-fact-cityBody").append("<div class='material-fact-peoples material-fact'><span class='material-fact-icon'>&#xe63b;</span><div><p>客票收入（元）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+data.success.talInc+"</span></div></div></div>");
        };
        if(data.success.airportFocus.focusTarget.split("，").indexOf("均班客量")!=-1){
            $("#passengerVolume").prop("checked",true);
            $(".material-fact-cityBody").append("<div class='material-fact-shiftPeoples material-fact'><span class='material-fact-icon'>&#xe681;</span><div><p>均班客量（人）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+data.success.claPer+"</span></div></div></div>");
        };
        if(data.success.airportFocus.focusTarget.split("，").indexOf("均班收入")!=-1){
            $("#classIncome").prop("checked",true);
            $(".material-fact-cityBody").append("<div class='material-fact-shiftPeoples material-fact'><span class='material-fact-icon'>&#xe63b;</span><div><p>均班收入（元）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+data.success.iddDct+"</span></div></div></div>");
        };
        if(data.success.airportFocus.focusTarget.split("，").indexOf("综合客座率")!=-1){
            $("#loadFactors").prop("checked",true);
            $(".material-fact-cityBody").append("<div class='material-fact-zhfactors material-fact'><span class='material-fact-icon'>&#xe682;</span><div><p>综合客座率（%）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+data.success.lodFor+"</span></div></div></div>");
        };
        if(data.success.airportFocus.focusTarget.split("，").indexOf("机场准点率")!=-1){
            $("#airportPunctuality").prop("checked",true);
            $(".material-fact-cityBody").append("<div class='material-fact-punctuality material-fact'><span class='material-fact-icon'>&#xe639;</span><div><p>机场准点率（%）</p><div class='material-fact-datas'><span class='material-fact-dataBig'>"+data.success.airportIthad+"</span></div></div></div>");
        }
    }
    
    $(".material-fact-installS").on('click',function(event){
    	//判断sessionStroage中存不存在对象atten，不存在添加
    	var storage = window.sessionStorage;
		var atten=JSON.parse(storage.getItem('atten'));
    	if(atten==null){
			getCurrentSeasonFocusFlts();
    	}
    	event.stopPropagation();
    	//关闭右边弹出框
    	$('.pla-ranking').css('display','none');
        if($(".material-fact-installBox").css("display")=="none"){
        	instrumentNew();
            $(".material-fact-installBox").css("display","block");
        }else {
            $(".material-fact-installBox").css("display","none");
        }
    });
    $('.material-fact-installBox').on('click','#focusFlights',function(event){
    	event.stopPropagation();
    	var content = $(this).parent().text();
    	if(content.indexOf('关注航班')>-1){
    		var storage = window.sessionStorage;
    		var atten=JSON.parse(storage.getItem('atten'));
    		if(atten!=null&&atten.length>0){
    			var flag = false;
    			for(var i=0;i<atten.length;i++){
    				var obj = atten[i];
    				var list = obj.data;
    				if(list!=null&&list.length>0){
    					for(var el in list){
    						if(list[el].tag){
    							flag = true;
    	    					break;
    						}
    					}
    				}
    			}
    			if(flag==false){
    				var res = confirm('请勾选关注航班');
    				if(res){
	    				$('.sSettings').click();
	    				setTimeout(function(){
	    					$('.fltSet')[0].click();
	    				}, 300);
    				}else{
    					$("#allFlights").prop("checked",true);
    				}
    			}
    		}
    	}
    });
    $(".jruit").on("click",function(){
        var le=0;
        for(var i=0;i< $(".jruit").length;i++){
            if($(".jruit").eq(i).prop("checked")==true){
                le++;
            }
        };
        if(le>4){
           $(this).prop("checked",false);
            $('.indicators-tip').addClass('indicators-color');
            setTimeout(function(){
               $('.indicators-tip').removeClass('indicators-color');
            },1000);
        }
    });
    $(".indicators-bth-t").on("click",function(){
        var airportFocus={
            timeDemension:"",
            flightRange:"",
            dataRange:"",
            focusTarget:""
        };
        if($("#yesterday").prop("checked")==true){
            airportFocus.timeDemension="最近1天";
        }else if($("#sevenDay").prop("checked")==true){
            airportFocus.timeDemension="最近7天";
        }else if($("#thirtyDay").prop("checked")==true){
            airportFocus.timeDemension="最近30天";
        };
        if($("#allFlights").prop("checked")==true){
            airportFocus.flightRange="所有航班";
        }else {
            airportFocus.flightRange="关注航班";
        };
        if($("#station").prop("checked")==true && $("#jiltFly").prop("checked")==true){
            airportFocus.dataRange="包含过站，包含甩飞";
        }else if($("#station").prop("checked")==true){
            airportFocus.dataRange="包含过站";
        }else if($("#jiltFly").prop("checked")==true){
            airportFocus.dataRange="包含甩飞";
        }else {
            airportFocus.dataRange="";
        }
        for(var i=0;i<7;i++){
            if($(".jruit").eq(i).prop("checked")==true){
                if(airportFocus.focusTarget==""){
                    airportFocus.focusTarget+=$(".jruit").eq(i).next('span').text();
                }else {
                    airportFocus.focusTarget+="，"+$(".jruit").eq(i).next('span').text();
                }
            }
        };
        getFocusData(airportFocus);
    });
    $(".indicators-bth-f").on("click",function(){
    	$(".material-fact-installBox").css("display","none");
    });

    var saveAirportOnLineData = (function(d){   //接口完整性检查     
        var _data = [];
        return {
            addCount: function(d){
                if(_data.length>1){
                    var _linesData = { success: $.extend(_data[0].success, _data[1].success, d.success)};
                    sessionStorage.setItem('linesData',JSON.stringify(_linesData));
                    Odata=_linesData;
                    _data = [Odata];
                }else{
                    _data.push(d);
                }        
            }
        }
    }())
    
    var getLinesPanelData = function(){//   3.航班号数据for航线拓展
        $.ajax({
            url : '/getAirportOnLineData_3',
            type: 'GET',
            dataType: 'jsonp',
        })
        .done(function(res) {
            if(res.success.fltNbrs.length>0){
                saveAirportOnLineData.addCount(res);
            }else{
                window.closeLoading();
//                alert('无此机场数据，请重新选择。');
            }
        })
        .fail(function() {
            console.log("error");
        })
    }

    var getDashboardData = function (){//   2.底部仪表盘数据
        $.ajax({
            url : '/getAirportOnLineData_2',
            type: 'GET',
            dataType: 'jsonp',
        })
        .done(function(res) {
            saveAirportOnLineData.addCount(res);
            instrument(res);
        })
        .fail(function() {
            console.log("error");
        })
    }
    getDashboardData();


    var hasPortTest = function(fdt,source){//3成立则true
        if(fdt.length===3){
            var f = fdt[0]||'',
                d = fdt[1]||'',
                t = fdt[2]||'';
            if(source[f] && source[d] && source[t]){
                return true;
            }
        }else{
            var f = fdt[0]||'',
                t = fdt[1]||'';         
            if(source[f] && source[t]){
                return true;
            }
        }
        return false;
    }
    var compareSource = function(la,lb){
        for(var a in lb){
            var i = lb[a],
                f = i.airport,
                d = i.dptAirport,
                t = i.arrAirport;
            if(d===undefined){//直飞情况下，保证ft有
                if(!hasPortTest([f,t],la)){
                    lb.splice(a,1);
                    //删除lb.i
                }
            }else{//保证全部都有
                if(!hasPortTest([f,d,t],la)){
                    lb.splice(a,1);
                    //删除lb.i
                }
            }
        }
    }
    var getAirportFocusData = function (){//    1.地图各航点for绘制
    	if(userUuid) return ;
        $.ajax({
            url : '/getAirportOnLineData_1',
            type: 'GET',
            dataType: 'jsonp',
        })
        .done(function(data) {
            if(data){
                getLinesPanelData();
                $("#cityName").text(data.success.airportAirName);
                saveAirportOnLineData.addCount(data);
                airportMap = data.success.airportInfoMap;
                // airPortName = airportMap[cityIata].aptChaNam;
                
                airPortName = airportName = data.success.airportName;
                airportCode = data.success.airportCode;
                iataMap = data.success.icaoMap;
                var onLineData = [];                            
                //装载所有航班号+地名
                var lhlhlh={};
                var hong="";	//装航班号
                var hbiata=[];	//装三字码
                for(var int = 0; int<data.success.airportOnLineData.length; int++){
                    if(data.success.airportOnLineData[int].flyNum!=""){
                        onLineData.push(data.success.airportOnLineData[int]);
                    }
                    var b = data.success.airportOnLineData[int].flyNum.split(",");
                    for(var a=0 ; a<b.length; a++){
                        var el = data.success.airportOnLineData[int];
                        if(el.dptAirport){
                            lhlhlh[b[a]]= el.airport+"="+ el.dptAirport+"="+el.arrAirport;
                        }
                        else{
                            lhlhlh[b[a]]=el.airport+"="+el.arrAirport;    	                    		
                        }                    	
                        
                        hong+=el.flyNum+ ',';
                        if(el.dptAirport){
                            hbiata.push(el.airport+"="+ el.dptAirport+"="+el.arrAirport);
                        }
                        else{
                            hbiata.push(el.airport+"="+el.arrAirport);    	                    		
                        }
                    }  
                }
                cityCoordinateList = data.success.cityCoordinateList;
                window.searchNum=lhlhlh;    	                	
                window.searchCNnum=hbiata;
                window.airFlyIata = data.success.airportOnLineData = onLineData;
                compareSource(airportMap,onLineData);
                for(var index in data.success.cityCoordinateList){
                    var key = data.success.cityCoordinateList[index].cityName;
                    var valuee =data.success.cityCoordinateList[index].cityCoordinatee;
                    var array = valuee.split(",");
                    geoCoordMap[key] =array;    //赋值城市经纬度
                }
                DataList=initMapData(data);
                mid(data);
                // instrument(data);//移到请求2
                getLinesData(data);
                $(".material-fact-installBox").css("display","none");
            }
        })
        .fail(function() {
            alert("数据请求错误，请刷新重试。");
        })
        .always(function() {
            window.closeLoading();
        })
    }
    getAirportFocusData();


     //航线是否历史，有无数据，是否在飞数据组装
    function initMapData(data){
    	var datas = data.success.airportOnLineData;
    	var airlineMap = data.success.airlineMap;
    	var localCode = data.success.airportCode;
    	var data0 = new Array();//在飞航线数据集合
    	var data1 = new Array();//历史航线数据集合
        var data2 = new Array();//无数据航线数据集合
    	for(var int = 0; int<datas.length;int++){
    		var airport = datas[int].airport;
    		var arrAirport = datas[int].arrAirport;
            var dptAirport = datas[int].dptAirport;
    		if(typeof (dptAirport)=="undefined"||dptAirport==null){
    			if(datas[int].state=="0"){
    				var c1 = true;
    				for(var i =0;i<data0.length;i++){
    					if((data0[i].toName==airportMap[arrAirport].ctyChaNam&&data0[i].fromName==airportMap[airport].ctyChaNam)||(data0[i].toName==airportMap[airport].ctyChaNam&&data0[i].fromName==airportMap[arrAirport].ctyChaNam)){
    						c1 = false;
    					}
    				}
    				if(c1){
    					var tempObj = new Object();
    					tempObj.fromName = airportMap[airport].ctyChaNam;
    					tempObj.toName = airportMap[arrAirport].ctyChaNam;
                        tempObj.type = 0;
    					var coords = new Array();
    					coords[0] = geoCoordMap[tempObj.fromName];
    					coords[1] = geoCoordMap[tempObj.toName];
    					tempObj.coords = coords;
    					data0.push(tempObj);
    				}
        		}
    		}else{
    			if(datas[int].state=="0"){
    				if(airport==localCode){
    					var c1 = true;
        				var c2 = true;
        				for(var i =0;i<data0.length;i++){
                            if((data0[i].toName==airportMap[dptAirport].ctyChaNam&&data0[i].fromName==airportMap[airport].ctyChaNam)||(data0[i].toName==airportMap[airport].ctyChaNam&&data0[i].fromName==airportMap[dptAirport].ctyChaNam)){
                                c1 = false;
                            }
                            if((data0[i].toName==airportMap[dptAirport].ctyChaNam&&data0[i].fromName==airportMap[arrAirport].ctyChaNam)||(data0[i].toName==airportMap[arrAirport].ctyChaNam&&data0[i].fromName==airportMap[dptAirport].ctyChaNam)){
                                c2 = false; 
                            }
        				}
        				if(c1){
	    					var tempObj = new Object();
	            			tempObj.fromName = airportMap[airport].ctyChaNam;
	            			tempObj.toName = airportMap[dptAirport].ctyChaNam;
                            tempObj.type = 0;
	            			var coords = new Array();
	            			coords[0] = geoCoordMap[tempObj.fromName];
	            			coords[1] = geoCoordMap[tempObj.toName];
	            			tempObj.coords = coords;
	            			data0.push(tempObj);
        				}
        				if(c2){
	            			var tempObj2 = new Object();
	            			tempObj2.fromName = airportMap[dptAirport].ctyChaNam;
	            			tempObj2.toName = airportMap[arrAirport].ctyChaNam;
                            tempObj2.type = 0;
	            			var coords2 = new Array();
	            			coords2[0] = geoCoordMap[tempObj2.fromName];
	            			coords2[1] = geoCoordMap[tempObj2.toName];
	            			tempObj2.coords = coords2;
	            			data0.push(tempObj2);
        				}
    				}
        			if(dptAirport==localCode){
        				var c1 = true;
        				var c2 = true;
        				for(var i =0;i<data0.length;i++){
        					if((data0[i].toName==airportMap[arrAirport].ctyChaNam&&data0[i].fromName==airportMap[dptAirport].ctyChaNam)||(data0[i].toName==airportMap[arrAirport].ctyChaNam&&data0[i].fromName==airportMap[dptAirport].ctyChaNam)){
        						c1 = false;
        					}
        					if((data0[i].toName==airportMap[airport].ctyChaNam&&data0[i].fromName==airportMap[dptAirport].ctyChaNam)||(data0[i].toName==airportMap[airport].ctyChaNam&&data0[i].fromName==airportMap[dptAirport].ctyChaNam)){
        						c2 = false; 
        					}
        				}
        				if(c1){
	        				var tempObj = new Object();
	            			tempObj.fromName = airportMap[dptAirport].ctyChaNam;
	            			tempObj.toName = airportMap[arrAirport].ctyChaNam;
                            tempObj.type = 0;
	            			var coords = new Array();
	            			coords[0] = geoCoordMap[tempObj.fromName];
	            			coords[1] = geoCoordMap[tempObj.toName];
	            			tempObj.coords = coords;
	            			data0.push(tempObj);
        				}
        				if(c2){
	            			var tempObj2 = new Object();
	            			tempObj2.fromName = airportMap[dptAirport].ctyChaNam;
	            			tempObj2.toName = airportMap[airport].ctyChaNam;
                            tempObj2.type = 0;
	            			var coords2 = new Array();
	            			coords2[0] = geoCoordMap[tempObj2.fromName];
	            			coords2[1] = geoCoordMap[tempObj2.toName];
	            			tempObj2.coords = coords2;
	            			data0.push(tempObj2);
        				}
        			}
        		}
    		}
    	}
    	for(var int = 0; int<datas.length;int++){
    		var airport = datas[int].airport;
    		var arrAirport = datas[int].arrAirport;
            var dptAirport = datas[int].dptAirport;
    		if((typeof (dptAirport)=="undefined"||dptAirport==null)&&airportMap[arrAirport]){
        		if(datas[int].state=="1"){
        			var c1 = true;
    				for(var i =0;i<data1.length;i++){
    					if((data1[i].toName==airportMap[arrAirport].ctyChaNam&&data1[i].fromName==airportMap[airport].ctyChaNam)||(data1[i].toName==airportMap[airport].ctyChaNam&&data1[i].fromName==airportMap[arrAirport].ctyChaNam)){
    						c1 = false;
    					}
    				}
    				for(var i =0;i<data0.length;i++){
    					if((data0[i].toName==airportMap[arrAirport].ctyChaNam&&data0[i].fromName==airportMap[airport].ctyChaNam)||(data0[i].toName==airportMap[airport].ctyChaNam&&data0[i].fromName==airportMap[arrAirport].ctyChaNam)){
    						c1 = false;
    					}
    				}
    				if(c1){
	        			var tempObj = new Object();
	        			tempObj.fromName = airportMap[airport].ctyChaNam;
	        			tempObj.toName = airportMap[arrAirport].ctyChaNam;
                        tempObj.type = 1;
	        			var coords = new Array();
	        			coords[0] = geoCoordMap[tempObj.fromName];
	        			coords[1] = geoCoordMap[tempObj.toName];
	        			tempObj.coords = coords;
	        			data1.push(tempObj);
    				}
        		}
    		}else{
        		if(datas[int].state=="1"){
        			if(airport==localCode &&airportMap[arrAirport] ){
        				var c1 = true;
        				var c2 = true;
        				for(var i =0;i<data1.length;i++){
        					if((data1[i].toName==airportMap[airport].ctyChaNam&&data1[i].fromName==airportMap[dptAirport].ctyChaNam)||(data1[i].toName==airportMap[dptAirport].ctyChaNam&&data1[i].fromName==airportMap[airport].ctyChaNam)){
        						c1 = false;
                            }
        					if((data1[i].toName==airportMap[arrAirport].ctyChaNam&&data1[i].fromName==airportMap[dptAirport].ctyChaNam)||(data1[i].toName==airportMap[dptAirport].ctyChaNam&&data1[i].fromName==airportMap[arrAirport].ctyChaNam)){
        						c2 = false; 
        					}
        				}
        				for(var i =0;i<data0.length;i++){
        					if((data0[i].toName==airportMap[airport].ctyChaNam&&data0[i].fromName==airportMap[dptAirport].ctyChaNam)||(data0[i].toName==airportMap[dptAirport].ctyChaNam&&data0[i].fromName==airportMap[airport].ctyChaNam)){
        						c1 = false;
        					}
        					if((data0[i].toName==airportMap[arrAirport].ctyChaNam&&data0[i].fromName==airportMap[dptAirport].ctyChaNam)||(data0[i].toName==airportMap[dptAirport].ctyChaNam&&data0[i].fromName==airportMap[arrAirport].ctyChaNam)){
        						c2 = false; 
        					}
        				}
        				if(c1){
        					var tempObj = new Object();
        					tempObj.fromName = airportMap[airport].ctyChaNam;
        					tempObj.toName = airportMap[dptAirport].ctyChaNam;
                            tempObj.type = 1;
        					var coords = new Array();
        					coords[0] = geoCoordMap[tempObj.fromName];
        					coords[1] = geoCoordMap[tempObj.toName];
        					tempObj.coords = coords;
        					data1.push(tempObj);
        				}
            			if(c2){
            				var tempObj2 = new Object();
            				tempObj2.fromName = airportMap[dptAirport].ctyChaNam;
            				tempObj2.toName = airportMap[arrAirport].ctyChaNam;
                            tempObj2.type = 1;
            				var coords2 = new Array();
            				coords2[0] = geoCoordMap[tempObj2.fromName];
            				coords2[1] = geoCoordMap[tempObj2.toName];
            				tempObj2.coords = coords2;
            				data1.push(tempObj2);
            			}
    				}
        			if(dptAirport==localCode){
        				var c1 = true;
        				var c2 = true;
        				for(var i =0;i<data1.length;i++){
        					if((data1[i].toName==airportMap[arrAirport].ctyChaNam&&data1[i].fromName==airportMap[dptAirport].ctyChaNam)||(data1[i].toName==airportMap[dptAirport].ctyChaNam&&data1[i].fromName==airportMap[arrAirport].ctyChaNam)){
        						c1 = false;
        					}
        					if((data1[i].toName==airportMap[airport].ctyChaNam&&data1[i].fromName==airportMap[dptAirport].ctyChaNam)||(data1[i].toName==airportMap[dptAirport].ctyChaNam&&data1[i].fromName==airportMap[airport].ctyChaNam)){
        						c2 = false; 
        					}
        				}
        				for(var i =0;i<data0.length;i++){
        					if((data0[i].toName==airportMap[arrAirport].ctyChaNam&&data0[i].fromName==airportMap[dptAirport].ctyChaNam)||(data0[i].toName==airportMap[dptAirport].ctyChaNam&&data0[i].fromName==airportMap[arrAirport].ctyChaNam)){
        						c1 = false;
        					}
        					if((data0[i].toName==airportMap[airport].ctyChaNam&&data0[i].fromName==airportMap[dptAirport].ctyChaNam)||(data0[i].toName==airportMap[dptAirport].ctyChaNam&&data0[i].fromName==airportMap[airport].ctyChaNam)){
        						c2 = false; 
        					}
        				}
        				if(c1){
	        				var tempObj = new Object();
	            			tempObj.fromName = airportMap[dptAirport].ctyChaNam;
	            			tempObj.toName = airportMap[arrAirport].ctyChaNam;
                            tempObj.type = 1;
	            			var coords = new Array();
	            			coords[0] = geoCoordMap[tempObj.fromName];
	            			coords[1] = geoCoordMap[tempObj.toName];
	            			tempObj.coords = coords;
	            			data1.push(tempObj);
        				}
        				if(c2){
	            			var tempObj2 = new Object();
	            			tempObj2.fromName = airportMap[dptAirport].ctyChaNam;
	            			tempObj2.toName = airportMap[airport].ctyChaNam;
                            tempObj2.type = 1;
	            			var coords2 = new Array();
	            			coords2[0] = geoCoordMap[tempObj2.fromName];
	            			coords2[1] = geoCoordMap[tempObj2.toName];
	            			tempObj2.coords = coords2;
	            			data1.push(tempObj2);
        				}
        			}
        		}
    		}
    	}

    	for(var int = 0; int<datas.length;int++){
    		var airport = datas[int].airport;
    		var arrAirport = datas[int].arrAirport;
    		var dptAirport = datas[int].dptAirport;
    		if(typeof (dptAirport)=="undefined"||dptAirport==null){
        		if(datas[int].state=="2"){
        			var c1 = true;
        			for(var i =0;i<data2.length;i++){
    					if((data2[i].toName==airportMap[arrAirport].ctyChaNam&&data2[i].fromName==airportMap[airport].ctyChaNam)||(data2[i].toName==airportMap[airport].ctyChaNam&&data2[i].fromName==airportMap[arrAirport].ctyChaNam)){
    						c1 = false;
    					}
    				}
    				if(c1){
	        			var tempObj = new Object();
	        			tempObj.fromName = airportMap[airport].ctyChaNam;
	        			tempObj.toName = airportMap[arrAirport].ctyChaNam;
                        tempObj.type = 2;
	        			var coords = new Array();
	        			coords[0] = geoCoordMap[tempObj.fromName];
	        			coords[1] = geoCoordMap[tempObj.toName];
	        			tempObj.coords = coords;
	        			data2.push(tempObj);
    				}
        		}
    		}else{
        		if(datas[int].state=="2"){
        			if(airport==localCode){
        				var c1 = true;
        				var c2 = true;
        				for(var i =0;i<data2.length;i++){
        					if((data2[i].toName==airportMap[dptAirport].ctyChaNam&&data2[i].fromName==airportMap[airport].ctyChaNam)||(data2[i].toName==airportMap[airport].ctyChaNam&&data2[i].fromName==airportMap[dptAirport].ctyChaNam)){
        						c1 = false;
        					}
        					if((data2[i].toName==airportMap[dptAirport].ctyChaNam&&data2[i].fromName==airportMap[arrAirport].ctyChaNam)||(data2[i].toName==airportMap[arrAirport].ctyChaNam&&data2[i].fromName==airportMap[dptAirport].ctyChaNam)){
        						c2 = false; 
        					}
        				}
        				if(c1){
	        				var tempObj = new Object();
	            			tempObj.fromName = airportMap[airport].ctyChaNam;
	            			tempObj.toName = airportMap[dptAirport].ctyChaNam;
                            tempObj.type = 2;
	            			var coords = new Array();
	            			coords[0] = geoCoordMap[tempObj.fromName];
	            			coords[1] = geoCoordMap[tempObj.toName];
	            			tempObj.coords = coords;
	            			data2.push(tempObj);
        				}
        				if(c2){
	            			var tempObj2 = new Object();
	            			tempObj2.fromName = airportMap[dptAirport].ctyChaNam;
	            			tempObj2.toName = airportMap[arrAirport].ctyChaNam;
                            tempObj2.type = 2;
	            			var coords2 = new Array();
	            			coords2[0] = geoCoordMap[tempObj2.fromName];
	            			coords2[1] = geoCoordMap[tempObj2.toName];
	            			tempObj2.coords = coords2;
	            			data2.push(tempObj2);
        				}
    				}
        			if(dptAirport==localCode){
        				var c1 = true;
        				var c2 = true;
        				for(var i =0;i<data2.length;i++){
        					if((data2[i].toName==airportMap[dptAirport].ctyChaNam&&data2[i].fromName==airportMap[arrAirport].ctyChaNam)||(data2[i].toName==airportMap[arrAirport].ctyChaNam&&data2[i].fromName==airportMap[dptAirport].ctyChaNam)){
        						c1 = false;
        					}
        					if((data2[i].toName==airportMap[dptAirport].ctyChaNam&&data2[i].fromName==airportMap[airport].ctyChaNam)||(data2[i].toName==airportMap[airport].ctyChaNam&&data2[i].fromName==airportMap[dptAirport].ctyChaNam)){
        						c2 = false; 
        					}
        				}
        				if(c1){
	        				var tempObj = new Object();
	            			tempObj.fromName = airportMap[dptAirport].ctyChaNam;
	            			tempObj.toName = airportMap[arrAirport].ctyChaNam;
                            tempObj.type = 2;
	            			var coords = new Array();
	            			coords[0] = geoCoordMap[tempObj.fromName];
	            			coords[1] = geoCoordMap[tempObj.toName];
	            			tempObj.coords = coords;
	            			data2.push(tempObj);
        				}
        				if(c2){
	            			var tempObj2 = new Object();
	            			tempObj2.fromName = airportMap[dptAirport].ctyChaNam;
	            			tempObj2.toName = airportMap[airport].ctyChaNam;
                            tempObj2.type = 2;
	            			var coords2 = new Array();
	            			coords2[0] = geoCoordMap[tempObj2.fromName];
	            			coords2[1] = geoCoordMap[tempObj2.toName];
	            			tempObj2.coords = coords2;
	            			data2.push(tempObj2);
        				}
        			}
        		}
    		}
    	}
    	//找到历史在飞无数据航线后  给每条航线（两个对应的点）添加数据航线航段航班号信息
    	for(var d =0;d<data0.length;d++){
    		var objTemp = data0[d];
    		var fromName = objTemp.fromName;
    		var toName = objTemp.toName;
    		var fromCode = airportMap[fromName].iata.split("/");
    		var toCode = airportMap[toName].iata.split("/");
    		
    		var allLine = new Object();
    		allLine.title = fromName+"="+toName;
    		var period = new Array();
    		var iin = 0;
    		for(var aa = 0;aa<toCode.length;aa++){
    			var flag = false;
    			for(var int = 0; int<datas.length;int++){
            		var airport = datas[int].airport;
            		var arrAirport = datas[int].arrAirport;
            		var dptAirport = datas[int].dptAirport;
            		var statetemp  = datas[int].state;
            		var line1 = "";
            		if(typeof(dptAirport)=="undefined"){
            			line1 = airport +"-"+ arrAirport;
            		}else{
            			line1 = airport +"-"+dptAirport+"-"+ arrAirport;
            		}
            		if(fromCode.length==1){
            			if(statetemp=="0"&&(line1.indexOf(fromCode[0])>=0&&line1.indexOf(toCode[aa])>=0)){
            				flag = true;
            			}
            		}else{
            			if(statetemp=="0"&&((line1.indexOf(fromCode[0])>=0&&line1.indexOf(toCode[aa])>=0)||(line1.indexOf(fromCode[1])>=0&&line1.indexOf(toCode[aa])>=0))){
            				flag = true;
            			}
            		}
        		}
    			//添加航段
        		if(flag){
        			var tempobj = new Object();
        			if(fromCode.length==1){
	        			tempobj.code = fromCode[0] + "-" + toCode[aa];
	        			tempobj.name = airportMap[fromCode[0]].aptChaNam+"-"+airportMap[toCode[aa]].aptChaNam;
        			}else{
        				tempobj.code = fromCode[0] + "-" + toCode[aa];
	        			tempobj.name = airportMap[fromCode[0]].aptChaNam+"-"+airportMap[toCode[aa]].aptChaNam;
        			}
        			var lineses = new Array();
        			var index = 0;
        			for(var int = 0; int<datas.length;int++){
                		var airport = datas[int].airport;
                		var arrAirport = datas[int].arrAirport;
                        var dptAirport = datas[int].dptAirport;
                		var statetemp  = datas[int].state;
                		var line1 = "";
                		if(typeof(dptAirport)=="undefined"){
                			line1 = airport +"-"+ arrAirport;
                		}else{
                			line1 = airport +"-"+dptAirport+"-"+ arrAirport;
                		}
                		if(fromCode.length==1){
	                		if((statetemp=="0"||statetemp=="1")&&(line1.indexOf(fromCode[0])>=0&&line1.indexOf(toCode[aa])>=0)){
	                			var objTemp = new Object();
	                			if(typeof(dptAirport)=="undefined"){
	                				objTemp.code = airport +"-"+ arrAirport;
	                				objTemp.name = airportMap[airport].aptChaNam +"-"+ airportMap[arrAirport].aptChaNam;
	                    		}else{
	                    			objTemp.code = airport +"-"+dptAirport+"-"+ arrAirport;
	                    			objTemp.name = airportMap[airport].aptChaNam +"-"+airportMap[dptAirport].aptChaNam+"-"+ airportMap[arrAirport].aptChaNam;
	                    		}
	                			objTemp.flyNumber = datas[int].flyNum.split(",");
	                			lineses[index] = objTemp;
	                			index ++;
	                		}
                		}else{
	                		if((statetemp=="0"||statetemp=="1")&&((line1.indexOf(fromCode[0])>=0&&line1.indexOf(toCode[aa])>=0)||(line1.indexOf(fromCode[1])>=0&&line1.indexOf(toCode[aa])>=0))){
	                			var objTemp = new Object();
	                			if(typeof(dptAirport)=="undefined"){
	                				objTemp.code = airport +"-"+ arrAirport;
	                				objTemp.name = airportMap[airport].aptChaNam +"-"+ airportMap[arrAirport].aptChaNam;
	                    		}else{
	                    			objTemp.code = airport +"-"+dptAirport+"-"+ arrAirport;
	                    			objTemp.name = airportMap[airport].aptChaNam +"-"+airportMap[dptAirport].aptChaNam+"-"+ airportMap[arrAirport].aptChaNam;
	                    		}
	                			objTemp.flyNumber = datas[int].flyNum.split(",");
	                			lineses[index] = objTemp;
	                			index ++;
	                		}
                		
                		}
        			}
        			tempobj.lines = lineses;
        			period[iin] = tempobj;
            		iin++;
        		}
    		}
    		allLine.period = period;
    		data0[d].allLine = allLine;
    	}
    	
    	for(var d =0;d<data1.length;d++){
    		var objTemp = data1[d],
                fromName = objTemp.fromName,
                toName = objTemp.toName,
                fromCode = airportMap[fromName].iata.split("/"),
                toCode = airportMap[toName].iata.split("/"),
                allLine = new Object(),
                period = new Array(),
                iin = 0;
    		allLine.title = fromName+"="+toName;            
    		for(var aa = 0;aa<toCode.length;aa++){
    			var flag = false;
    			for(var int = 0; int<datas.length;int++){
            		var airport = datas[int].airport;
            		var arrAirport = datas[int].arrAirport;
            		var dptAirport = datas[int].dptAirport;
            		var statetemp  = datas[int].state;
            		var line1 = "";
            		if(typeof(dptAirport)=="undefined"){
            			line1 = airport +"-"+ arrAirport;
            		}else{
            			line1 = airport +"-"+dptAirport+"-"+ arrAirport;
            		}
            		if(statetemp=="1"&&(line1.indexOf(fromCode[0])>=0&&line1.indexOf(toCode[aa])>=0)){
            			flag = true;
            		}
        		}
    			//添加航段
        		if(flag){
        			var tempobj = new Object();
        			tempobj.code = fromCode[0] + "-" + toCode[aa];
        			tempobj.name = airportMap[fromCode[0]].aptChaNam+"-"+airportMap[toCode[aa]].aptChaNam;
        			var lineses = new Array();
        			var index = 0;
        			for(var int = 0; int<datas.length;int++){
                		var airport = datas[int].airport;
                		var arrAirport = datas[int].arrAirport;
                		var dptAirport = datas[int].dptAirport;
                		var statetemp  = datas[int].state;
                		var line1 = "";
                		if(typeof(dptAirport)=="undefined"){
                			line1 = airport +"-"+ arrAirport;
                		}else{
                			line1 = airport +"-"+dptAirport+"-"+ arrAirport;
                		}
                		if(statetemp=="1"&&(line1.indexOf(fromCode[0])>=0&&line1.indexOf(toCode[aa])>=0)){
                			var objTemp = new Object();
                			if(typeof(dptAirport)=="undefined"){
                				objTemp.code = airport +"-"+ arrAirport;
                				objTemp.name = airportMap[airport].aptChaNam +"-"+ airportMap[arrAirport].aptChaNam;
                    		}else{
                    			objTemp.code = airport +"-"+dptAirport+"-"+ arrAirport;
                    			objTemp.name = airportMap[airport].aptChaNam +"-"+airportMap[dptAirport].aptChaNam+"-"+ airportMap[arrAirport].aptChaNam;
                    		}
                			objTemp.flyNumber = datas[int].flyNum.split(",");
                			lineses[index] = objTemp;
                			index ++;
                		}
        			}
        			tempobj.lines = lineses;
        			period[iin] = tempobj;
            		iin++;
        		}
    		}
    		allLine.period = period;
    		data1[d].allLine = allLine;
    	}
    	
    	for(var d =0;d<data2.length;d++){
    		var objTemp = data2[d];
    		var fromName = objTemp.fromName;
    		var toName = objTemp.toName;
    		var fromCode = airportMap[fromName].iata.split("/");
    		var toCode = airportMap[toName].iata.split("/");
    		
    		var allLine = new Object();
    		allLine.title = fromName+"="+toName;
    		var period = new Array();
    		var iin = 0;
    		for(var aa = 0;aa<toCode.length;aa++){
    			var flag = false;
    			for(var int = 0; int<datas.length;int++){
            		var airport = datas[int].airport;
            		var arrAirport = datas[int].arrAirport;
            		var dptAirport = datas[int].dptAirport;
            		var statetemp  = datas[int].state;
            		var line1 = "";
            		if(typeof(dptAirport)=="undefined"){
            			line1 = airport  +"-"+ arrAirport;
            		}else{
            			line1 = airport  +"-"+dptAirport +"-"+ arrAirport;
            		}
            		if(statetemp=="2"&&(line1.indexOf(fromCode[0])>=0&&line1.indexOf(toCode[aa])>=0)){
            			flag = true;
            		}
        		}
    			//添加航段
        		if(flag){
        			var tempobj = new Object();
        			tempobj.code = fromCode[0] + "-" + toCode[aa];
        			tempobj.name = airportMap[fromCode[0]].aptChaNam+"-"+airportMap[toCode[aa]].aptChaNam;
        			var lineses = new Array();
        			var index = 0;
        			for(var int = 0; int<datas.length;int++){
                		var airport = datas[int].airport;
                		var arrAirport = datas[int].arrAirport;
                		var dptAirport = datas[int].dptAirport;
                		var statetemp  = datas[int].state;
                		var line1 = "";
                		if(typeof(dptAirport)=="undefined"){
                			line1 = airport  +"-"+ arrAirport;
                		}else{
                			line1 = airport  +"-"+dptAirport +"-"+ arrAirport;
                		}
                		if(statetemp=="2"&&(line1.indexOf(fromCode[0])>=0&&line1.indexOf(toCode[aa])>=0)){
                			var objTemp = new Object();
                			if(typeof(dptAirport)=="undefined"){
                				objTemp.code = airport +"-"+ arrAirport;
                				objTemp.name = airportMap[airport].aptChaNam +"-"+ airportMap[arrAirport].aptChaNam;
                    		}else{
                    			objTemp.code = airport +"-"+dptAirport+"-"+ arrAirport;
                    			objTemp.name = airportMap[airport].aptChaNam +"-"+airportMap[dptAirport].aptChaNam+"-"+ airportMap[arrAirport].aptChaNam;
                    		}
                			objTemp.flyNumber = datas[int].flyNum.split(",");
                			lineses[index] = objTemp;
                			index ++;
                		}
        			}
        			tempobj.lines = lineses;
        			period[iin] = tempobj;
            		iin++;
        		}
    		}
    		allLine.period = period;
    		data2[d].allLine = allLine;
    	}
    	var point0 = new Array();
    	var point1 = new Array();
    	var point2 = new Array();
    	var p0index = 0;
    	for(var d =0;d<data0.length;d++){
    		var obj = new Object();
    		obj.name = data0[d].toName;
            obj.type = data0[d].type;
            
            if(window.airportMap[data0[d].toName]){
                obj.code = window.airportMap[data0[d].toName].iata;
                if(window.airportMap[data0[d].toName].iata.length>3){
                    obj.code = window.airportMap[data0[d].toName].iata.substring(0,3);
                }
            }
    		var value = new Array();
    		value[0] = data0[d].coords[1][0];
    		value[1] = data0[d].coords[1][1];
    		value[2] = "30";
    		obj.value = value;
    		var flage = false;
    		for(var i =0;i<point0.length;i++){
    			if(obj.name==point0[i].name){
    				flage = true;
        		}
    		}
    		if(!flage){
    			point0[p0index] = obj;
    			p0index++;
    		}
    		
    	}
        var pindex = 0;
    	for(var d =0;d<data1.length;d++){
            var obj = new Object();
            obj.name = data1[d].toName;
            obj.type = data1[d].type;

            if(window.airportMap[data1[d].toName]){
                obj.code = window.airportMap[data1[d].toName].iata;
                if(window.airportMap[data1[d].toName].iata.length>3){
                    obj.code = window.airportMap[data1[d].toName].iata.substring(0,3);
                }
            }
            
            var value = new Array();
            value[0] = data1[d].coords[1][0];
            value[1] = data1[d].coords[1][1];
            value[2] = "30";
            obj.value = value;
            var flage = false;
            for(var i =0;i<point0.length;i++){
                if(obj.name==point0[i].name){
                    flage = true;
                }
            }
            for(var i =0;i<point1.length;i++){
                if(obj.name==point1[i].name){
                    flage = true;
                }
            }
            if(!flage){
                point1[pindex] = obj;
                pindex++;
            }
    	}
    	var p2index = 0;
    	for(var d =0;d<data2.length;d++){
    		var obj = new Object();
    		obj.name = data2[d].toName;
    		obj.type = data2[d].type;
    		var value = new Array();
    		value[0] = data2[d].coords[1][0];
    		value[1] = data2[d].coords[1][1];
    		value[2] = "30";
    		obj.value = value;
    		var flage = false;
    		for(var i =0;i<point1.length;i++){
    			if(obj.name==point1[i].name){
    				flage = true;
        		}
    		}
    		for(var i =0;i<point0.length;i++){
    			if(obj.name==point0[i].name){
    				flage = true;
        		}
    		}
    		for(var i =0;i<point2.length;i++){
    			if(obj.name==point2[i].name){
    				flage = true;
    			}
    		}
    		if(!flage){
    			point2[p2index] = obj;
    			p2index++;
    		}
    		
    	}
    	return {
    		data0:data0,
    		data1:data1,
    		data2:data2,
    		point0:point0,
    		point1:point1,
    		point2:point2,
    	}
    }

    function mid(data1){
        Odata=data1;
        var datatemp = data1.success.airportOnLineData;
        var data = [];
        //三字码转化为城市名字--地图显示
        for(var index in datatemp){
        	var temp = datatemp[index];
            var tempObject = new Object();
        	if(temp.flyNum !="" && airportMap[temp.arrAirport] ){//mxz
	        	tempObject.airport = airportMap[temp.airport].ctyChaNam;
	        	tempObject.arrAirport = airportMap[temp.arrAirport].ctyChaNam;
	        	if(typeof(temp.dptAirport) != "undefined"){
	        		tempObject.dptAirport = airportMap[temp.dptAirport].ctyChaNam;
	        	}
	        	tempObject.flyNum = temp.flyNum;
	        	data.push(tempObject);
        	}
        }
        for(var index in data1.success.cityCoordinateList){
            var key = data1.success.cityCoordinateList[index].cityName;
            var valuee =data1.success.cityCoordinateList[index].cityCoordinatee;
            var array = valuee.split(",");
            geoCoordMap[key] =array;    //赋值城市经纬度
        }
        option.bmap.center=geoCoordMap[data1.success.airportName];//设置居中位置
        /*航线组装*/
        var Sdata = [];
        for(var index in data){
            var one2 = [];
            var airport2 = {};
            var arrAirport2 = {};
            var dptAirport2 = {};
            airport2.name = data[index].airport;
            airport2.value = 30;
            arrAirport2.name = data[index].arrAirport;
            arrAirport2.value = 30;

            one2.push(airport2);
            if(data[index].dptAirport!=null){
                dptAirport2.name = data[index].dptAirport;
                dptAirport2.value = 30;
                one2.push(dptAirport2);
            }
            one2.push(arrAirport2);
            Sdata.push(one2);
        }
        for(var i=0;i<Sdata.length;i++){
            if(Sdata[i].length==3){
                var tag=1;
                var midData=[];
                if(Sdata[i][0].name==data1.success.airportName){
                    midData.push(Sdata[i][0],Sdata[i][1]);
                    midData.push(Sdata[i][1],Sdata[i][2]);
                }else {
                    midData.push(Sdata[i][1],Sdata[i][0]);
                    midData.push(Sdata[i][1],Sdata[i][2]);
                }
                for(var j=0;j<newData.length;j++){
                    if(midData[0].name==newData[j][0].name&&midData[1].name==newData[j][1].name){
                        tag=0;
                    }
                }
                if(tag==1){
                    newData.push(midData);
                }
                midData=[];
                tag=1;
                midData.push(Sdata[i][1],Sdata[i][2]);
                for(var j=0;j<newData.length;j++){
                    if(midData[0].name==newData[j][0].name&&midData[1].name==newData[j][1].name){
                        tag=0;
                    }
                }
                if(tag==1){
                    newData.push(midData);
                }
            }else if(Sdata[i].length==2){
                var midData=[];
                midData.push(Sdata[i][0],Sdata[i][1]);
                newData.push(midData);
                midData=[];
            }
        }
        HBData=newData;
        return demo();
    }
    setTimeout(function () {
    	getAirinfoData();
    }, 1000);
    var foreignFormatter = function(data){
        var res = [];
        for(var item of data){
            res.push({
                name: item.airInfoName,
                value: [item.city_coordinate_j, item.city_coordinate_w, 10]
            })
        }
        return res;
    }
    function getAirinfoData(){
    	$.ajax({
            type:'GET',
            url : '/getAirportViewData',
            dataType : 'jsonp',
            success : function(data) {
                if(data){
                    sessionStorage.setItem('airjw',JSON.stringify(data));
                    airllist = data.success;
                    
                    var airllists = [];
                    data.success.airInfoDataList.forEach(function(v){
                    	var objs = v;
                    	if(objs.airInfoName == undefined){
                    		objs.airInfoName = '';
                    	}
                    	if(objs.iata == undefined){
                    		objs.iata = '';
                    	}
                    	if(objs.icao == undefined){
                    		objs.icao = '';
                    	}
                    	airllists.push(objs);
                    })
                     data.success.airInfoDataList = airllists;
                    var list = data.success.airInfoDataList;
                    var airportCode = data.success.airportCode;
                    var oneself = new Object();
                    oneself.code = airportCode;
                    var listData = {};
                    for(var i=0;i<list.length;i++){
                        var a = list[i];
                        if(a.iata == airportCode){
                            oneself.airName = a.airInfoName;
                        }
                        var airInfo = new Object();
                        airInfo.airName = a.airInfoName;
                        airInfo.iata = a.iata;
                        airInfo.airZB = [a.city_coordinate_w,a.city_coordinate_j];
                        
                        airInfo.icao = a.icao || "-";
                        airInfo.flyLevel = a.flyLevel || "-";
                        airInfo.trackN = a.pdNum || "-";
                        airInfo.zLine = a.onlineFlyNum || "-";
                        airInfo.airLevel = a.airportBG || "-";
                        airInfo.nLine = a.glFlyNum || "-";
                        airInfo.wLine = a.gjFlyNum || "-";
                        airInfo.peos = a.flyPerson || "-";
                        airInfo.yearr2 = a.lkjzDate ? "（"+a.lkjzDate+"）" : "-";
                        airInfo.yearr1 = a.hxjzDate ? "（"+a.hxjzDate+"）" : "-";
                        airInfo.gjhx = a.gjFlyNum ? a.gjFlyNum : "-";
                        airInfo.gnhx = a.glFlyNum ? a.glFlyNum : "-";
                        airInfo.tgnhx = a.glhdDate ? "（"+a.glhdDate+"）" : "-";
                        airInfo.tgjhx = a.gjhdDate ? "（"+a.gjhdDate.substring(0,4)+"）" : "-";

                        listData[a.iata] = airInfo;
                    }
                    airData["listData"] = listData;
                    airData["oneself"] = oneself;
                    foreignPort = foreignFormatter(data.success.foreAirInfoDataList);
                    window.portinfo=airData["listData"];
                    if(userUuid){
                        calculate();
                        $('#TM-loading').hide();
                        $('#calculate-back').hide();
                        $('#hlcs-wrap').remove();
                    }                    	
                }
                
            },
            error : function() {}
        });
    }
    $(".pla-btn-switch").click(function(){
        clear_Advabced_filter_option();        
    	$("#line-kg").css({"display":"block"});    	
    	$("#line-kg2").css({"display":"none"});    	
    	$(".their-own").css({"display":"none"});
    	$("#airFlightt").css("display","none");
        if($(this).attr("tag")=="line"){
            stated = 1;
        	$("#line-kg").css("display","none");
        	$("#their-own-body-flying").css({"text-decoration":"underline","cursor":"pointer"});
        	 var amapList=[];
             for(var key in airData.listData){
                 geoAirMap[airData.listData[key].airName]=airData.listData[key].airZB;
                 geoAirMap[airData.listData[key].airName].iata=airData.listData[key].iata;
             }
             for(var key in geoAirMap){
                 amapList.push({
                     name:key,
                     value:150,
                     typeSelf:'outs',
                     iata: geoAirMap[key].iata
                });
             };
             $(".pla-btn-switch").html("&#xe66a");
             $(".pla-btn-switch").attr("tag","air");
             var datys=convertData2(amapList);
             ops={
                 "title":{"show":false,"text":"数聚空港","subtext":"太美航空","left":"center","textStyle":{"color":"#fff"}},
                 "bmap":{"center":["110.47","32.40"],"zoom":6,"color":"red","roam":"move","mapStyle":{"styleJson":[{"featureType":"water","elementType":"all","stylers":{"color":"#071327","visibility":"on"}},{"featureType":"land","elementType":"all","stylers":{"color":"#223350","visibility":"on"}},{"featureType":"boundary","elementType":"all","stylers":{"color":"#465b6c","visibility":"on"}},{"featureType":"arterial","elementType":"all","stylers":{"visibility":"off"}},{"featureType":"highway","elementType":"all","stylers":{"visibility":"off"}},{"featureType":"label","elementType":"all","stylers":{"visibility":"off"}}]}},
                 "tooltip":{show:false,"showDelay":0,"enterable":"true","alwaysShowContent":"true","triggerOn":"click","trigger":"item"},
                 "series":[{"name":"出港航班 ","type":"scatter","coordinateSystem":"bmap","symbol":"circle","symbolSize":5,"zlevel":10,"label":{"normal":{"show":false,"position":"right","formatter":"{b}"},"emphasis":{"show":true}},"itemStyle":{"normal":{"color":"#7fbce9"}},"data":""}]};
             ops.series[0].data=datys;   
             ops.series[0].label.normal.show=false;
             if(window.airportName === '十堰'){
                ops.series.push({
                    "name":"外国机场",
                    "type":"scatter",
                    "coordinateSystem":"bmap",
                    "symbol":"circle",
                    "symbolSize":3,"zlevel":10,
                    "label":{"normal":{"show":false,"position":"right","formatter":"{b}"},"emphasis":{"show":true}},
                    "itemStyle":{"normal":{"color":"#ffa022"}},
                    "data":foreignPort
                   })
             }
             myChart.setOption(ops, true);
             //航线视图下
        }else{
            stated = 0;
        	$("#line-kg").css("display","block");
        	$("#their-own-body-flying").css({"text-decoration":"none","cursor":"default"});
            $(this).attr("tag","line");
            $(this).html("&#xe668");
            myChart.setOption(stickOption, true);
            //机场视图下
        }        
    });
    
    var unclick=true;
    //航线拓展
    $("#their-own-body-flying").bind("click",function(){
        if($(".pla-btn .pla-btn-switch").attr("tag")=="air" && unclick){ 
            $('#portPanelLoading').show();
            $('.full-layer').show();
            unclick=false;
            var click_ioca=$("#their-ownA .their-own-iata").text();	//获取ioca号
            var opsA=JSON.stringify(ops);
            var opsB=JSON.stringify(stickOption);
            var oops = turn_on_this_plane(click_ioca,opsA,opsB);
            setTimeout(function(){
                unclick=true;
            },2000);
        } else {
            return false;
        }
    })
    
    $("#pla-btn-qhs").on("click",function(){
    	$("#their-ownA").css("display","none");
    	$("#their-ownB").css("display","none");
    });
    
    $("#airsubmit").click(function(){
    	$("#line-kg").css({"display":"none"});
    	var fliData = new Object();
    	var idata = new Object();
    	idata.stratCity = $("#startcity").val();
    	idata.pasCity1 = $("#pascity1").val();
    	idata.pasCity2 = $("#pascity2").val();
    	idata.endCity = $("#endcity").val();
    	var allArr = new Array();
    	if(idata.stratCity==""&&idata.pasCity1==""&&idata.pasCity2==""&&idata.endCity==""){
    		$.ajax(
    		        {
    		            type:'get',
    		            url : '/restful/getAllFlightAirlineData',
    		            data:idata,
    		            dataType : 'jsonp',
    		            success : function(data) {
    		            	if(data){
    		            		var dd = data.success;
    		            		var i = 0;
    		            		for(var key in dd){
    		            			var objTemp2 = new Object();
    		            			var  dalist = dd[key];
    		            			var arrTemp = new Array();
                                    var ll = 0;
    		            			for ( var int = 0; int < dalist.length; int++) {
    									var objTemp = new Object();
    									objTemp.fromName = dalist[int].stratCity;
    									objTemp.toName = dalist[int].endCity;
    									var arr1 = GPS.wd_encrypt(dalist[int].stratCityPoit2/10000,dalist[int].stratCityPoit1/10000);
    									var arr2 = GPS.wd_encrypt(dalist[int].endCityCityPoit2/10000,dalist[int].endCityCityPoit1/10000);
    									objTemp.coords = [[arr1.lat,arr1.lon],[arr2.lat,arr2.lon]];
    									var lengt = parseInt(GPS.distance(arr1.lat,arr1.lon,arr2.lat,arr2.lon)/1000);
    									if(int == 0|| int == (dalist.length-1)){
    										ll = ll + lengt;
    									}
    									arrTemp[int] = objTemp;
    								}
    		            			objTemp2.data = arrTemp;
    		            			objTemp2.mapkey = key;
    		            			allArr[i] = objTemp2;
    		            			i++;
    		            		}
    		            	}
    		            	
    		            	var ss = new Array();
    		            	//装线
    		            	for ( var int = 0; int < allArr.length; int++) {
    		            		var aa ={
                                    "coordinateSystem":"bmap",
                                    "type":"lines",
                                    "zlevel":0,
                                    "tooltip":{
                                    	"show":false
                                    },
                                    "lineStyle":{
                                    "normal":{
                                        "color":"#59c4e6",
                                        "width":"3",
                                        "opacity":0.7,
                                        "curveness":0
                                        },
                                        "emphasis":{"color":"#59c4e6"}
                                    },
                                    "polyline":false,
                                    "data":allArr[int].data,
                                    "blendMode":"lighter"
                                }		            		
    		            		ss[int] = aa;
    						}
    		            	//装点
    		            	var points = new Array();
    		            	for ( var int = 0; int < allArr.length; int++) {
    		            		var a = allArr[int].data;
    		            		var key = allArr[int].mapkey;
    		            		for(var i =0;i<a.length;i++){
    		            			var temp1 = new Object();
    			            		var temp2 = new Object();
    		            			if(key.indexOf(a[i].fromName)>-1){
    		            				 temp1.name = a[i].fromName
    			            			 temp1.value = a[i].coords[0];
    			            			 temp1.value.push("30");
    		            			}
    		            			if(key.indexOf(a[i].toName)>-1){
    		            				temp2.name = a[i].toName
    		            				temp2.value = a[i].coords[1];
    		            				temp2.value.push("30");
    		            			}
    		            			var flage = true;
    			            		for(var k=0;k<points.length;k++){
    			            			if(points[k].name==temp1.name){
    			            				flage = false;
    			            			}
    			            		}
    			            		if(flage){
    			            			points.push(temp1);
    			            		}
    			            		var flage2 = true;
    			            		for(var k=0;k<points.length;k++){
    			            			if(points[k].name==temp2.name){
    			            				flage2 = false;
    			            			}
    			            		}
    			            		if(flage2){
    			            			points.push(temp2);
    			            		}
    		            		}
    						}
    		            	var ap = {
    		            		"name":"航点",
    		            		"type":"scatter",
    		            		"coordinateSystem":"bmap",
    		            		"symbol":"circle",
    		            		"symbolSize":5,
    		            		"zlevel":10,
    		            		"label":{
    		            			"normal":{
    		            				"show":true,
    		            				"position":"right",
    		            				"formatter":"{b}"
    		            			},
    		            			"emphasis":{
    		            				"show":false
    		            			}
    		            		},
    		            		"itemStyle":{
    		            			"normal":{
    		            				"color":"red"
    		            			}
    		            		},
    		            		"data":points
    		            	}
    	            		ss.push(ap);
    		                var Collection={
    		                        "title":{"show":false,"left":"center","textStyle":{"color":"#fff"}},
    		                        "bmap":{
    		                        	"center":["110.47","32.40"],
    		                        	"zoom":6,
    		                        	"color":"red",
    		                        	"roam":"move",
    		                        	"type":BMAP_HYBRID_MAP,
    		                        	"mapStyle":{
    		                        		"styleJson":[{
    		                        			"featureType":"water",
    		                        			"elementType":"all",
    		                        			"stylers":{
    		                        				"color":"#071327",
    		                        				"visibility":"on"
    		                        				}
    		                        		},
    		                        		{
    		                        			"featureType":"land",
    		                        			"elementType":"all",
    		                        			"stylers":{
    		                        				"color":"#223350",
    		                        				"visibility":"on"
    		                        				}
    		                        		},
    		                        		{
    		                        			"featureType":"boundary",
    		                        			"elementType":"all",
    		                        			"stylers":{
    		                        				"color":"#465b6c",
    		                        				"visibility":"on"
    		                        				}
    		                        		},
    		                        		{
    		                        			"featureType":"arterial",
    		                        			"elementType":"all",
    		                        			"stylers":{
    		                        				"visibility":"off"
    		                        				}
    		                        		},
    		                        		{
    		                        			"featureType":"highway",
    		                        			"elementType":"all",
    		                        			"stylers":{
    		                        				"visibility":"off"
    		                        				}
    		                        		},
    		                        		{
    		                        			"featureType":"label",
    		                        			"elementType":"all",
    		                        			"stylers":{
    		                        				"visibility":"off"
    		                        				}
    		                        		}]
    		                        	}
    		                        },
    		                        "tooltip":{ trigger: 'item'},
    		                        "series":ss
    		                        };
    		                    myChart.setOption(Collection,true);
    		                },
    		                
    		            error : function() {}
    		        }
    		);
    	}else{
    		$.ajax(
    		        {
    		            type:'get',
    		            url : '/restful/getFlightAirlineData',
    		            data:idata,
    		            dataType : 'jsonp',
    		            success : function(data) {
    		            	if(data){
    		            		var dd = data.success;
    		            		var i = 0;
    		            		var tempAirportList = "";
    		            		for(var key in dd){
    		            			tempAirportList = key;
    		            			var objTemp2 = new Object();
    		            			var  dalist = dd[key];
    		            			var arrTemp = new Array();
    		            			var ll = 0;
    		            			for ( var int = 0; int < dalist.length; int++) {
    									var objTemp = new Object();
    									objTemp.fromName = dalist[int].stratCity;
    									objTemp.toName = dalist[int].endCity;
    									var arr1 = GPS.wd_encrypt(dalist[int].stratCityPoit2/10000,dalist[int].stratCityPoit1/10000);
    									var arr2 = GPS.wd_encrypt(dalist[int].endCityCityPoit2/10000,dalist[int].endCityCityPoit1/10000);
    									objTemp.coords = [[arr1.lat,arr1.lon],[arr2.lat,arr2.lon]];
    									var lengt = parseInt(GPS.distance(arr1.lat,arr1.lon,arr2.lat,arr2.lon)/1000);
    									if(int == 0|| int == (dalist.length-1)){
    										ll = ll + lengt;
    									}
    									arrTemp[int] = objTemp;
    								}
    		            			var aa = key.split("-");
    		            			var keeyy = parseInt(aa[aa.length-1])+ll;
    		            			var okey = "";
    		            			for(var k=0;k<aa.length-1;k++){
    		            				okey = okey +"-"+ aa[k];
    		            			}
    		            			okey = okey + "-"+ keeyy;
    		            			okey = okey.substring(1,okey.length);
    		            			objTemp2.data = arrTemp;
    		            			objTemp2.mapkey = okey;
    		            			allArr[i] = objTemp2;
    		            			i++;
    		            		}
    		            	}
    		            	
    		            	var ss = new Array();
    		            	var colors = ["#59c4e6","#edafda","#93b7e3","#a5e7f0","#cbb0e3","#6be6c1","#FFFFED","#EEEBBB","#AAACCD"];
    		            	//装线
    		            	for ( var int = 0; int < allArr.length; int++) {
    		            		var aa ={
                                    "coordinateSystem":"bmap",
                                    "type":"lines",
                                    "zlevel":0,
                                    "tooltip":{
                                    	"show":false
                                    },
                                    "lineStyle":{
                                    "normal":{
                                        "color":colors[int],
                                        "width":"3",
                                        "opacity":0.7,
                                        "curveness":0
                                        },
                                        "emphasis":{"color":colors[int]}
                                    },
                                    "polyline":false,
                                    "data":allArr[int].data,
                                    "blendMode":"lighter"
                                }		            		
    		            		ss[int] = aa;
    						}
    		            	//装点
    		            	var points = new Array();
    		            	for ( var int = 0; int < allArr.length; int++) {
    		            		var a = allArr[int].data;
    		            		for(var i =0;i<a.length;i++){
    		            			var temp1 = new Object();
    			            		var temp2 = new Object();
    		            			if(tempAirportList.indexOf(a[i].fromName)>-1){
    		            				 temp1.name = a[i].fromName
    			            			 temp1.value = a[i].coords[0];
    			            			 temp1.value.push("30");
    		            			}
    		            			if(tempAirportList.indexOf(a[i].toName)>-1){
    		            				temp2.name = a[i].toName
    		            				temp2.value = a[i].coords[1];
    		            				temp2.value.push("30");
    		            			}
    		            			var flage = true;
    			            		for(var k=0;k<points.length;k++){
    			            			if(points[k].name==temp1.name){
    			            				flage = false;
    			            			}
    			            		}
    			            		if(flage){
    			            			points.push(temp1);
    			            		}
    			            		var flage2 = true;
    			            		for(var k=0;k<points.length;k++){
    			            			if(points[k].name==temp2.name){
    			            				flage2 = false;
    			            			}
    			            		}
    			            		if(flage2){
    			            			points.push(temp2);
    			            		}
    		            		}
    						}
    		            	var ap = {
    		            		"name":"机场",
    		            		"type":"scatter",
    		            		"coordinateSystem":"bmap",
    		            		"symbol":"circle",
    		            		"symbolSize":5,
    		            		"zlevel":10,
    		            		"label":{
    		            			"normal":{
    		            				"show":true,
    		            				"position":"right",
    		            				"formatter":"{b}"
    		            			},
    		            			"emphasis":{
    		            				"show":false
    		            			}
    		            		},
    		            		"itemStyle":{
    		            			"normal":{
    		            				"color":"red"
    		            			}
    		            		},
    		            		"data":points
    		            	}
    	            		ss.push(ap);
    		                var Collection={
    		                        "title":{"show":false,"left":"center","textStyle":{"color":"#fff"}},
    		                        "bmap":{
    		                        	"center":["110.47","32.40"],
    		                        	"zoom":6,
    		                        	"color":"red",
    		                        	"roam":"move",
    		                        	"type":BMAP_HYBRID_MAP,
    		                        	"mapStyle":{
    		                        		"styleJson":[{
    		                        			"featureType":"water",
    		                        			"elementType":"all",
    		                        			"stylers":{
    		                        				"color":"#071327",
    		                        				"visibility":"on"
    		                        				}
    		                        		},
    		                        		{
    		                        			"featureType":"land",
    		                        			"elementType":"all",
    		                        			"stylers":{
    		                        				"color":"#223350",
    		                        				"visibility":"on"
    		                        				}
    		                        		},
    		                        		{
    		                        			"featureType":"boundary",
    		                        			"elementType":"all",
    		                        			"stylers":{
    		                        				"color":"#465b6c",
    		                        				"visibility":"on"
    		                        				}
    		                        		},
    		                        		{
    		                        			"featureType":"arterial",
    		                        			"elementType":"all",
    		                        			"stylers":{
    		                        				"visibility":"off"
    		                        				}
    		                        		},
    		                        		{
    		                        			"featureType":"highway",
    		                        			"elementType":"all",
    		                        			"stylers":{
    		                        				"visibility":"off"
    		                        				}
    		                        		},
    		                        		{
    		                        			"featureType":"label",
    		                        			"elementType":"all",
    		                        			"stylers":{
    		                        				"visibility":"off"
    		                        				}
    		                        		}]
    		                        	}
    		                        },
    		                        "tooltip":{ trigger: 'item'},
    		                        "series":ss
    		                        };
    		                    myChart.setOption(Collection,true);
    		                  //画图例
    			            	var str = "<ul class='line-icon'>";
    			            	for(var int = 0; int < allArr.length; int++){
    			            		var key = allArr[int].mapkey;
    			            		str = str + "<li><font  color = '"+colors[int]+"'style='letter-spacing: -1px'>──────</font>" + key + "</li>";
    			            	}
    			            	str = str + "</ul>";
    			            	$("#line-kg2").html(str);
    			            	$("#line-kg2").css({"display":"block"});
    		                },
    		            error : function() {}
    		        }
    		    );
    	}
    	$(".their-own").css({"display":"none"});
    	if($(this).attr("tag")=="line"){
    		var amapList=[];
    		$(".pla-btn-switch").html("&#xe66a");
    		$(".pla-btn-switch").attr("tag","air");
    		var datys="";
    		ops={
    				"title":{"show":false,"text":"模拟迁徙","subtext":"数据纯属虚构","left":"center","textStyle":{"color":"#fff"}},
    				"bmap":{"center":["110.47","32.40"],"zoom":6,"color":"red","roam":"move","mapStyle":{"styleJson":[{"featureType":"water","elementType":"all","stylers":{"color":"#071327","visibility":"on"}},{"featureType":"land","elementType":"all","stylers":{"color":"#223350","visibility":"on"}},{"featureType":"boundary","elementType":"all","stylers":{"color":"#465b6c","visibility":"on"}},{"featureType":"arterial","elementType":"all","stylers":{"visibility":"off"}},{"featureType":"highway","elementType":"all","stylers":{"visibility":"off"}},{"featureType":"label","elementType":"all","stylers":{"visibility":"off"}}]}},
    				"tooltip":{show:false,"showDelay":0,"enterable":"true","alwaysShowContent":"true","triggerOn":"click","trigger":"item"},
    				"series":[{"name":"出港航班 ","type":"scatter","coordinateSystem":"bmap","symbol":"circle","symbolSize":5,"zlevel":10,"label":{"normal":{"show":false,"position":"right","formatter":"{b}"},"emphasis":{"show":true}},"itemStyle":{"normal":{"color":"#7fbce9"}},"data":""}]};
    		ops.series[0].data=datys;
    		ops.series[0].label.normal.show=false;
    		myChart.setOption(ops, true);
    	}
    });
    
    $(".pla-btn-switch_line").click(function(){
    	$("#airFlightt").css("display","block");
    });
    $("#quexiao").click(function(){
    	$("#airFlightt").css("display","none");
    });
    /*if (option && typeof option === "object") {
        myChart.setOption(option, true);
    };*/
    /*改变窗口大小*/
    /*放大*/
    /*
    $(".pla-zoom>div:nth-of-type(1)").click(function(){
        var num = myChart.getOption();//获取上次保留的实时数据
        if(num.bmap[0].zoom<8){
            if($(".pla-btn-switch").attr("tag")=="air"&&$('#global-routes').css('display')!='none'){
                if(num.bmap[0].zoom>=6){
                    num.series[0].label.normal.show=true;
                    //航线拓展判断   longhong
                    if(num.series.length>1){
                    	num.series[0].label.normal.show=false;
                    	num.series[1].label.normal.show=true;
                    }
                }
            };
            num.bmap[0].zoom+=1;
            myChart.setOption(num,true);
        };
    });
    //缩小
    $(".pla-zoom>div:nth-of-type(2)").click(function(){
        var num = myChart.getOption();//获取上次保留的实时数据
        if(num.bmap[0].zoom>5){
            if($(".pla-btn-switch").attr("tag")=="air"&&$('#global-routes').css('display')!='none'){
                if(num.bmap[0].zoom<=7){
                    num.series[0].label.normal.show=false;
                    //航线拓展判断   longhong
                    if(num.series.length>1){
                    	num.series[0].label.normal.show=true;
                    	num.series[1].label.normal.show=false;
                    }
                }
            }
            num.bmap[0].zoom-=1;
            myChart.setOption(num, true);
        };
    });
    */
    /*
    if(parent.airportName === '十堰'){
        $(".pla-zoom div").eq(0).click(function(){
            var num = myChart.getOption();//获取上次保留的实时数据
            if(num.bmap[0].zoom<99){
                if($(".pla-btn-switch").attr("tag")=="air"&&$('#global-routes').css('display')!='none'){
                    if(num.bmap[0].zoom>=0){
                        //航线拓展判断   longhong
                        if(num.series.length>1){
                            num.series[0].label.normal.show=false;
                            num.series[1].label.normal.show=true;
                        }
                        num.series[0].label.normal.show=true;
                    }
                };
                num.bmap[0].zoom+=1;
                myChart.setOption(num,true);
            };
        });
        //缩小
        $(".pla-zoom div").eq(1).click(function(){
            var num = myChart.getOption();//获取上次保留的实时数据
            if(num.bmap[0].zoom>0){
                if($(".pla-btn-switch").attr("tag")=="air"&&$('#global-routes').css('display')!='none'){
                    if(num.bmap[0].zoom<=99){
                        //航线拓展判断   longhong
                        if(num.series.length>1){
                            num.series[0].label.normal.show=true;
                            num.series[1].label.normal.show=false;
                        }
                        num.series[0].label.normal.show=false;
                    }
                }
                num.bmap[0].zoom-=1;
                myChart.setOption(num, true);
            };
        });
    }else{
        $(".pla-zoom div").eq(0).click(function(){
            var num = myChart.getOption();//获取上次保留的实时数据
            if(num.bmap[0].zoom<8){
                if($(".pla-btn-switch").attr("tag")=="air"&&$('#global-routes').css('display')!='none'){
                    if(num.bmap[0].zoom>=6){
                        //航线拓展判断   longhong
                        if(num.series.length>1){
                            num.series[0].label.normal.show=false;
                            num.series[1].label.normal.show=true;
                        }
                        num.series[0].label.normal.show=true;
                    }
                };
                num.bmap[0].zoom+=1;
                myChart.setOption(num,true);
            };
        });
        //缩小
        $(".pla-zoom div").eq(1).click(function(){
            var num = myChart.getOption();//获取上次保留的实时数据
            if(num.bmap[0].zoom>5){
                if($(".pla-btn-switch").attr("tag")=="air"&&$('#global-routes').css('display')!='none'){
                    if(num.bmap[0].zoom<=7){
                        //航线拓展判断   longhong
                        if(num.series.length>1){
                            num.series[0].label.normal.show=true;
                            num.series[1].label.normal.show=false;
                        }
                        num.series[0].label.normal.show=false;
                    }
                }
                num.bmap[0].zoom-=1;
                myChart.setOption(num, true);
            };
        });
    }
    */
    
    $(".pla-zoom div").eq(0).click(function(){
        var num = myChart.getOption();//获取上次保留的实时数据
        if(num.bmap[0].zoom<99){
            if($(".pla-btn-switch").attr("tag")=="air"&&$('#global-routes').css('display')!='none'){
                if(num.bmap[0].zoom>=5){
                    num.series[0].label.normal.show=true;
                    //航线拓展判断   longhong
                    if(num.series.length>2){
                        num.series[0].label.normal.show=false;
                        num.series[1].label.normal.show=true;
                    }
                }
            };
            num.bmap[0].zoom+=1;
            myChart.setOption(num,true);
        };
    });
    /*缩小*/
    $(".pla-zoom div").eq(1).click(function(){
        var num = myChart.getOption();//获取上次保留的实时数据
        if(num.bmap[0].zoom>0){
            if($(".pla-btn-switch").attr("tag")=="air"&&$('#global-routes').css('display')!='none'){
                if(num.bmap[0].zoom<=6){
                    num.series[0].label.normal.show=false;
                    //航线拓展判断   longhong
                    if(num.series.length>2){
                        num.series[0].label.normal.show=true;
                        num.series[1].label.normal.show=false;
                    }
                }
            }
            num.bmap[0].zoom-=1;
            myChart.setOption(num, true);
        };
    });

    /*浮层选择航线*/
    $(".pla-promptBox-rate>ul>li").on("click",function(){
        var num = myChart.getOption();
        for(var i=0;i<Odata.success.airportOnLineData.length;i++){
            var nOdata=Odata.success.airportOnLineData[i].flyNum.split(",");
            for(var j=0;j<nOdata.length;j++){
                if( $(this).parent().prev().html()==nOdata[j]){
                    if(Odata.success.airportOnLineData[i].dptAirport){
                        if(airportMap[Odata.success.airportOnLineData[i].dptAirport].ctyChaNam==Odata.success.airportName){
                            num.series[2].data=[{
                                coords:[geoCoordMap[Odata.success.airportOnLineData[i].dptAirport],geoCoordMap[Odata.success.airportOnLineData[i].airport]],
                                lineStyle: {
                                    normal: {
                                        color:"#3887be"
                                    }
                                }
                            },{
                                coords:[geoCoordMap[Odata.success.airportOnLineData[i].dptAirport],geoCoordMap[Odata.success.airportOnLineData[i].arrAirport]],
                                lineStyle: {
                                    normal: {
                                        color:"#3887be"
                                    }
                                }
                            }];
                        }else {
                            num.series[2].data=[{
                                coords:[geoCoordMap[Odata.success.airportOnLineData[i].airport],geoCoordMap[Odata.success.airportOnLineData[i].dptAirport]],
                                lineStyle: {
                                    normal: {
                                        color:"#3887be"
                                    }
                                }
                            },{
                                coords:[geoCoordMap[Odata.success.airportOnLineData[i].dptAirport],geoCoordMap[Odata.success.airportOnLineData[i].arrAirport]],
                                lineStyle: {
                                    normal: {
                                        color:"#3887be"
                                    }
                                }
                            }];
                        }
                    }else {//两点
                        num.series[2].data=[{
                            coords:[geoCoordMap[Odata.success.airportOnLineData[i].airport],geoCoordMap[Odata.success.airportOnLineData[i].arrAirport]],
                            lineStyle: {
                                normal: {
                                    color:"#3887be"
                                }
                            }
                        }];
                    }
                }
            }
        }
    });
    var summaryData={};
    function getLinesData(data){
    	var keydata = convertData(HBData);
    	var olddata = data.success.airportOnLineData;
    	var airportCode = data.success.airportCode;
    	for(var a=0; a<keydata.length;a++){
    		var objtemp = keydata[a];
    		var objvalue = objtemp.fromName + "-" + objtemp.toName;
    		var fromiata = airportMap[objtemp.fromName].iata;
    		//占时只考虑了到达的城市有两个以上机场的情况，没有考虑去的城市有两个机场的情况
    		var toiatatemp1 = airportMap[objtemp.toName].iata;
    		var dataee = new Array();
			var tostrtemp1 = toiatatemp1.split("/");
			for(var i=0; i<tostrtemp1.length;i++){
				var flage = 0;
				var listobj = new Object();
				var toiata = tostrtemp1[i];
				var lines2 =toiata + fromiata;
				var lines1 =fromiata + toiata;
				var tempName  = new Array();
				var tempCode  = new Array();
				var allCity  = new Object();
				var allCityChiled  = new Object();
				for(var b=0; b<olddata.length;b++){
					var dptcode = "";
					var pstcode = "";
					var arrcode = "";
					if(typeof(olddata[b].airport) != "undefined"){
						dptcode = olddata[b].airport;
					}
					if(typeof(olddata[b].dptAirport) != "undefined"){
						pstcode = olddata[b].dptAirport;
					}
					if(typeof(olddata[b].arrAirport) != "undefined"){
						arrcode = olddata[b].arrAirport;
					}
					var linestemp = dptcode + pstcode + arrcode;
					if(("l"+linestemp+"l").indexOf(lines1)>0||("l"+linestemp+"l").indexOf(lines2)>0){
						flage = 1;
						var linesNametemp = "";
						var linnesOjb = new Object();
						var flyNumarr  = olddata[b].flyNum.split(",");
						if(pstcode!=""){
							linesNametemp = airportMap[dptcode].aptChaNam +"-"+ airportMap[pstcode].aptChaNam +"-"+ airportMap[arrcode].aptChaNam;
							linnesOjb["allAir"] = [airportMap[dptcode].aptChaNam,airportMap[pstcode].aptChaNam,airportMap[arrcode].aptChaNam];
							linnesOjb["allCode"] = [dptcode,pstcode,arrcode];
							linnesOjb["allFlight"] = flyNumarr;
							if(airportCode == dptcode){
								linnesOjb["loc"] = 1;
							}
							if(airportCode == pstcode){
								linnesOjb["loc"] = 2;
							}
							if(airportCode == arrcode){
								linnesOjb["loc"] = 3;
							}
						}else{
							linesNametemp = airportMap[dptcode].aptChaNam +"-"+ airportMap[arrcode].aptChaNam;
							linnesOjb["allAir"] = [airportMap[dptcode].aptChaNam,airportMap[arrcode].aptChaNam];
							linnesOjb["allCode"] = [dptcode,arrcode];
							linnesOjb["allFlight"] = flyNumarr;
							if(airportCode == dptcode){
								linnesOjb["loc"] = 1;
							}
							if(airportCode == arrcode){
								linnesOjb["loc"] = 2;
							}
						}
						allCityChiled[linesNametemp] = linnesOjb;
						if(!contains(tempName,airportMap[fromiata].aptChaNam)){
							tempName.push(airportMap[fromiata].aptChaNam);
						}
						if(!contains(tempName,airportMap[toiata].aptChaNam)){
							tempName.push(airportMap[toiata].aptChaNam);
						}
						if(!contains(tempCode,fromiata)){
							tempCode.push(fromiata);
						}
						if(!contains(tempCode,toiata)){
							tempCode.push(toiata);
						}
					}
				}
				listobj["city"] = tempName;
				listobj["code"] = tempCode;
				listobj["allCity"] = allCityChiled;
				if(flage == 1){
					dataee.push(listobj);
				}
			}
			var tempdd = new Object();
			tempdd["list"] = dataee;
    		summaryData[objvalue] = tempdd;
    	}
    }
    //数组是否包含某个值
    function contains(arr, obj) {  
        var i = arr.length;  
        while (i--) {  
            if (arr[i] === obj) {  
                return true;  
            }  
        }  
        return false;  
    }  
    /*提示框动态设置*/
    window.chart_openTool=function(params){
    	if($('.pla-btn-national').css('display')=='block'){//全国视图
    		return;
    	}
    	var oChangeA= $("#their-ownA"), retline;
    	var oChangeB=$("#their-ownB");
    	oChangeA.css("transform","rotateY(0deg)");
		oChangeA.css("z-index","2");
		oChangeB.css("transform","rotateY(-180deg)");
		oChangeB.css("z-index","1");
		
        if(params.seriesType=="lines"){
            if(params.data.type!=undefined){
                if(params.data.type==2){
                    return
                }
            }else {
                return
            }
            var dats=params.data.allLine;
            $(".pla-promptBox").css({"left":params.event.offsetX+"px","top":params.event.offsetY+"px"});
            var tipright='';
            tipright+="<div class='_tipTile _tipFlight' ><div class='_tipTile-box _minTile'><div>"+dats.title.split('=')[0]+"</div><div>=</div><div>"+dats.title.split('=')[1]+"</div></div></div><div class='_tipBody'><div class='_tipBodyBox'>";
            dats.period.forEach((val,index)=>{
                tipright+="<div class='_tipLines'><div class='_mid-t _tipFlight' abbr='"+val.name.split('-')+"'><div class='_mid-tag'>"+(index+1)+"</div><div class='_mid-box'><div class='_mid-code'><div class='_mid-max'>"+val.code.split('-')[0]+"</div> <div class='_mid-min'>=</div><div class='_mid-max'>"+val.code.split('-')[1]+"</div></div><div class='_mid-city'><div class='_mid-max'><div>"+val.name.split('-')[0]+"</div></div><div class='_mid-min'>=</div><div class='_mid-max'><div>"+val.name.split('-')[1]+"</div></div></div></div></div>";
                val.lines.forEach((cox)=>{
                    var ai='';
                    cox.flyNumber.forEach((kis)=>{
                        ai+="<div  class='_tipFlight' abbr='"+cox.name.split('-')+"-"+kis+"'>"+kis+"</div>";
                    });
                    if(cox.code.split('-').length==2){
                        if(airportCode==cox.code.split('-')[0]){
                            tipright+="<div class='_mid-b'><div class='_mid-b-line _tipFlight'abbr='"+cox.name.split('-')+"'><div class='_mid-b-line-h1'></div><div class='_mid-b-line-b'><div class='_mid-b-line-b1 _mid-b-line-tag'><h2>"+cox.code.split('-')[0]+"</h2><div><p>"+cox.name.split('-')[0]+"</p></div></div><div class='_mid-b-line-b2'><h2></h2><p></p></div><div class='_mid-b-line-b3'><h2>"+cox.code.split('-')[1]+"</h2><div><p>"+cox.name.split('-')[1]+"</p></div></div></div></div><div class='_mid-b-flight'>"+ai+"</div></div>"
                        }else if(airportCode==cox.code.split('-')[1]){
                            tipright+="<div class='_mid-b'><div class='_mid-b-line _tipFlight'abbr='"+cox.name.split('-')+"'><div class='_mid-b-line-h1'></div><div class='_mid-b-line-b'><div class='_mid-b-line-b1'><h2>"+cox.code.split('-')[0]+"</h2><div><p>"+cox.name.split('-')[0]+"</p></div></div><div class='_mid-b-line-b2  _mid-b-line-tag'><h2></h2><p></p></div><div class='_mid-b-line-b3'><h2>"+cox.code.split('-')[1]+"</h2><div><p>"+cox.name.split('-')[1]+"</p></div></div></div></div><div class='_mid-b-flight'>"+ai+"</div></div>"
                        }
                    }else if(cox.code.split('-').length==3){
                        if(airportCode==cox.code.split('-')[0]){
                            tipright+="<div class='_mid-b'><div class='_mid-b-line _tipFlight'abbr='"+cox.name.split('-')+"'><div class='_mid-b-line-h'></div><div class='_mid-b-line-b'><div class='_mid-b-line-b1 _mid-b-line-tag'><h2>"+cox.code.split('-')[0]+"</h2><div><p>"+cox.name.split('-')[0]+"</p></div></div><div class='_mid-b-line-b2'><h2>"+cox.code.split('-')[1]+"</h2><div><p>"+cox.name.split('-')[1]+"</p></div></div><div class='_mid-b-line-b3'><h2>"+cox.code.split('-')[2]+"</h2><div><p>"+cox.name.split('-')[2]+"</p></div></div></div></div><div class='_mid-b-flight'>"+ai+"</div></div>"
                        }else if(airportCode==cox.code.split('-')[1]){
                            tipright+="<div class='_mid-b'><div class='_mid-b-line _tipFlight'abbr='"+cox.name.split('-')+"'><div class='_mid-b-line-h'></div><div class='_mid-b-line-b'><div class='_mid-b-line-b1'><h2>"+cox.code.split('-')[0]+"</h2><div><p>"+cox.name.split('-')[0]+"</p></div></div><div class='_mid-b-line-b2 _mid-b-line-tag'><h2>"+cox.code.split('-')[1]+"</h2><div><p>"+cox.name.split('-')[1]+"</p></div></div><div class='_mid-b-line-b3'><h2>"+cox.code.split('-')[2]+"</h2><div><p>"+cox.name.split('-')[2]+"</p></div></div></div></div><div class='_mid-b-flight'>"+ai+"</div></div>"
                        }else if(airportCode==cox.code.split('-')[2]){
                            tipright+="<div class='_mid-b'><div class='_mid-b-line _tipFlight'abbr='"+cox.name.split('-')+"'><div class='_mid-b-line-h'></div><div class='_mid-b-line-b'><div class='_mid-b-line-b1'><h2>"+cox.code.split('-')[0]+"</h2><div><p>"+cox.name.split('-')[0]+"</p></div></div><div class='_mid-b-line-b2'><h2>"+cox.code.split('-')[1]+"</h2><div><p>"+cox.name.split('-')[1]+"</p></div></div><div class='_mid-b-line-b3 _mid-b-line-tag'><h2>"+cox.code.split('-')[2]+"</h2><div><p>"+cox.name.split('-')[2]+"</p></div></div></div></div><div class='_mid-b-flight'>"+ai+"</div></div>"
                        }
                    }
                })
            });
            tipright+="</div>";
            $(".pla-prompTip").html(tipright);
            tipRight();
            $(".pla-prompRight").css("display","none");
            return;
        }else if(params.seriesType=="scatter"){
            if (params.data.typeSelf!=undefined){
                for(var u in airData.listData){
                    if(airData.listData[u].airName==params.name || params.name.indexOf(airData.listData[u].airName)>-1){
                        $(".their-own-airName").text(airData.listData[u].airName);
                        $(".their-own-icao").text(airData.listData[u].icao);
                        $(".their-own-iata").text(airData.listData[u].iata);
                        $(".their-own-flyLevel").text(airData.listData[u].flyLevel);
                        $(".their-own-trackN").text(airData.listData[u].trackN);
                        
                        $(".their-own-zLine").text(airData.listData[u].gnhx);
                        $(".their-own-nLine").text(" "+airData.listData[u].gjhx);
                        $(".yearr1").text(airData.listData[u].tgnhx);
                        $(".yearr2").text(airData.listData[u].tgjhx);

                        $(".their-own-wLine").text(" "+airData.listData[u].wLine);
                        $(".their-own-peos").text(airData.listData[u].peos);
                        $(".their-own-wirLevel").text(" "+airData.listData[u].wLine);
                        $(".their-own-airLevel").text(airData.listData[u].airLevel);
                    }
                };
                $(".their-own-body-link").remove();
                setTimeout(function(){
                    $(".their-own").css({"display":"block","left":params.event.offsetX+"px","top":params.event.offsetY+"px"});
                },50)
            }else{
                if(params.data.type=="2"){
                    return;
                }
                var u = Odata.success.airportCode;
                var c = "";
                if(airportMap[params.data.name]!=undefined){
                    c = airportMap[params.data.name].iata.length>3? airportMap[params.data.name].iata.substring(0,3) : airportMap[params.data.name].iata ;
                }else{
                    return alert("无数据，稍后重试");
                }
                var cc = new Array();
                if(c.length>3){
                    cc = c.split("/");
                }
                // if(c==u||c==""){//本机场
                if(params.seriesIndex===1){//本机场
                     $(".their-own-airName").text(airData.listData[u].airName);
                     $(".their-own-icao").text(airData.listData[u].icao);
                     $(".their-own-iata").text(airData.listData[u].iata);
                     $(".their-own-flyLevel").text(airData.listData[u].flyLevel);
                     $(".their-own-trackN").text(airData.listData[u].trackN);
                     $(".their-own-zLine").text(airData.listData[u].zLine);
                     $(".their-own-nLine").text(" "+airData.listData[u].nLine);
                     
                     $(".their-own-wLine").text(" "+airData.listData[u].wLine);
                     $(".their-own-peos").text(airData.listData[u].peos);
                     $(".their-own-wirLevel").text(" "+airData.listData[u].wLine);
                     $(".their-own-airLevel").text(airData.listData[u].airLevel);
                     $(".yearr1").text(airData.listData[u].yearr1);
                     $(".yearr2").text(airData.listData[u].yearr2);
                     if($(".their-own-body-link").length==0){
                         $(".their-own-body-peoples").after("<div class='their-own-body-link'><div style='display:"+(menus2[1].type==1?'block':'none')+"'><p>&#xe629;</p><div>机场历史运营</div></div><div style='display:"+(menus2[5].type==1?'block':'none')+"'><p>&#xe665;</p><div>航班动态</div></div><div style='display:"+(menus2[6].type==1?'block':'none')+"'><p>&#xe666;</p><div>时刻分布</div></div></div>");
                     };
                     setTimeout(function(){
                        $(".their-own").css({"display":"block","left":params.event.offsetX+"px","top":params.event.offsetY+"px"});
                    },50)
                }else {
                    var onName = params.name;
                    var dats2 = new Object();
                    for(var i = 0;i<DataList.data0.length;i++){
                        if(onName == DataList.data0[i].toName){
                            if(JSON.stringify(dats2)=="{}"){
                                dats2 = JSON.parse(JSON.stringify(DataList.data0[i].allLine));
                                dats2.title = airportName + "=" + onName;
                                for(var j = 0;j<dats2.period.length;j++){
                                    dats2.period[j].name = airportMap[airportCode].aptChaNam + "-" + airportMap[dats2.period[j].code.split("-")[1]].aptChaNam;
                                    dats2.period[j].code = airportMap[airportCode].iata + "-" + airportMap[dats2.period[j].code.split("-")[1]].iata;
                                }
                            }else{
                                //设置它的标题
                                dats2.title = airportName + "=" + onName;
                                for(var m=0;m<DataList.data0[i].allLine.period.length;m++){
                                    var falge = true;
                                    var codem = DataList.data0[i].allLine.period[m].code.split("-");
                                    var liness = DataList.data0[i].allLine.period[m].lines ;
                                    for(var n=0;n<dats2.period.length;n++){
                                        var coden = dats2.period[n].code.split("-");
                                        if(coden[1]==codem[1]){
                                            var lines1 = dats2.period[n].lines;
                                            dats2.period[n].name = airportMap[airportCode].aptChaNam + "-" + airportMap[codem[1]].aptChaNam;
                                            dats2.period[n].code = airportMap[airportCode].iata + "-" + airportMap[codem[1]].iata;
                                            retline = JSON.parse(JSON.stringify(liness));
                                            //去掉重复的数据
                                            for ( var jj = 0; jj < lines1.length; jj++) {
                                                var flat = true;
                                                for(var ii = 0 ;ii<liness.length;ii++){
                                                    if(liness[ii].code==lines1[jj].code){
                                                        flat = false;
                                                    }
                                                }
                                                if(flat){
                                                    retline.push(lines1[jj]);
                                                }
                                            }
                                            dats2.period[n].lines = retline;
                                            falge = false;
                                        }
                                    }
                                    if(falge){
                                        //新加一个period
                                        var period = new Object();
                                        period.name = airportMap[airportCode].aptChaNam + "-" + airportMap[codem[1]].aptChaNam;
                                        period.code = airportMap[airportCode].iata + "-" + airportMap[codem[1]].iata;
                                        period.lines = JSON.parse(JSON.stringify(liness));
                                        dats2.period.push(period);
                                    }
                                }
                            }
                        }
                    }
                    for(var i = 0;i<DataList.data1.length;i++){
                        if(onName == DataList.data1[i].toName){
                            if(JSON.stringify(dats2)=="{}"){
                                dats2 = JSON.parse(JSON.stringify(DataList.data1[i].allLine));
                                dats2.title = airportName + "=" + onName;
                                for(var j = 0;j<dats2.period.length;j++){
                                    dats2.period[j].name = airportMap[airportCode].aptChaNam + "-" + airportMap[dats2.period[j].code.split("-")[1]].aptChaNam;
                                    dats2.period[j].code = airportMap[airportCode].iata + "-" + airportMap[dats2.period[j].code.split("-")[1]].iata;
                                }
                            }else{
                                //设置它的标题
                                dats2.title = airportName + "=" + onName;
                                for(var m=0;m<DataList.data1[i].allLine.period.length;m++){
                                    var falge = true;
                                    var codem = DataList.data1[i].allLine.period[m].code.split("-");
                                    var liness = DataList.data1[i].allLine.period[m].lines ;
                                    for(var n=0;n<dats2.period.length;n++){
                                        var coden = dats2.period[n].code.split("-");
                                        if(coden[1]==codem[1]){
                                            var lines1 = dats2.period[n].lines;
                                            dats2.period[n].name = airportMap[airportCode].aptChaNam + "-" + airportMap[codem[1]].aptChaNam;
                                            dats2.period[n].code = airportMap[airportCode].iata + "-" + airportMap[codem[1]].iata;
                                            retline = JSON.parse(JSON.stringify(liness));
                                            //去掉重复的数据
                                            for ( var jj = 0; jj < lines1.length; jj++) {
                                                var flat = true;
                                                for(var ii = 0 ;ii<liness.length;ii++){
                                                    if(liness[ii].code==lines1[jj].code){
                                                        flat = false;
                                                    }
                                                }
                                                if(flat){
                                                    retline.push(lines1[jj]);
                                                }
                                            }
                                            dats2.period[n].lines = retline;
                                            falge = false;
                                        }
                                    }
                                    if(falge){
                                        //新加一个period
                                        var period = new Object();
                                        period.name = airportMap[airportCode].aptChaNam + "-" + airportMap[codem[1]].aptChaNam;
                                        period.code = airportMap[airportCode].iata + "-" + airportMap[codem[1]].iata;
                                        period.lines = JSON.parse(JSON.stringify(liness));
                                        dats2.period.push(period);
                                    }
                                }
                            }
                        }
                    }
                    $(".pla-promptBox").css({"left":params.event.offsetX+"px","top":params.event.offsetY+"px"});
                    var tipright='';
                    if(JSON.stringify(dats2)==='{}'){return false;}
                    tipright+="<div class='_tipTile _tipFlight' ><div class='_tipTile-box _minTile'><div>"+dats2.title.split('=')[0]+"</div><div>=</div><div>"+dats2.title.split('=')[1]+"</div></div></div><div class='_tipBody'><div class='_tipBodyBox'>";
                    dats2.period.forEach((val,index)=>{
                        tipright+="<div class='_tipLines'><div class='_mid-t _tipFlight' abbr='"+val.name.split('-')+"'><div class='_mid-tag'>"+(index+1)+"</div><div class='_mid-box'><div class='_mid-code'><div class='_mid-max'>"+val.code.split('-')[0]+"</div> <div class='_mid-min'>=</div><div class='_mid-max'>"+val.code.split('-')[1]+"</div></div><div class='_mid-city'><div class='_mid-max'><div>"+val.name.split('-')[0]+"</div></div><div class='_mid-min'>=</div><div class='_mid-max'><div>"+val.name.split('-')[1]+"</div></div></div></div></div>";
                        val.lines.forEach((cox)=>{
                            var ai='';
                            cox.flyNumber.forEach((kis)=>{
                                ai+="<div  class='_tipFlight' abbr='"+cox.name.split('-')+"-"+kis+"'>"+kis+"</div>";
                            });
                            if(cox.code.split('-').length==2){
                                if(airportCode==cox.code.split('-')[0]){
                                    tipright+="<div class='_mid-b'><div class='_mid-b-line _tipFlight'abbr='"+cox.name.split('-')+"'><div class='_mid-b-line-h1'></div><div class='_mid-b-line-b'><div class='_mid-b-line-b1 _mid-b-line-tag'><h2>"+cox.code.split('-')[0]+"</h2><div><p>"+cox.name.split('-')[0]+"</p></div></div><div class='_mid-b-line-b2'><h2></h2><p></p></div><div class='_mid-b-line-b3'><h2>"+cox.code.split('-')[1]+"</h2><div><p>"+cox.name.split('-')[1]+"</p></div></div></div></div><div class='_mid-b-flight'>"+ai+"</div></div>"
                                }else if(airportCode==cox.code.split('-')[1]){
                                    tipright+="<div class='_mid-b'><div class='_mid-b-line _tipFlight'abbr='"+cox.name.split('-')+"'><div class='_mid-b-line-h1'></div><div class='_mid-b-line-b'><div class='_mid-b-line-b1'><h2>"+cox.code.split('-')[0]+"</h2><div><p>"+cox.name.split('-')[0]+"</p></div></div><div class='_mid-b-line-b2  _mid-b-line-tag'><h2></h2><p></p></div><div class='_mid-b-line-b3'><h2>"+cox.code.split('-')[1]+"</h2><div><p>"+cox.name.split('-')[1]+"</p></div></div></div></div><div class='_mid-b-flight'>"+ai+"</div></div>"
                                }
                            }else if(cox.code.split('-').length==3){
                                if(airportCode==cox.code.split('-')[0]){
                                    tipright+="<div class='_mid-b'><div class='_mid-b-line _tipFlight'abbr='"+cox.name.split('-')+"'><div class='_mid-b-line-h'></div><div class='_mid-b-line-b'><div class='_mid-b-line-b1 _mid-b-line-tag'><h2>"+cox.code.split('-')[0]+"</h2><div><p>"+cox.name.split('-')[0]+"</p></div></div><div class='_mid-b-line-b2'><h2>"+cox.code.split('-')[1]+"</h2><div><p>"+cox.name.split('-')[1]+"</p></div></div><div class='_mid-b-line-b3'><h2>"+cox.code.split('-')[2]+"</h2><div><p>"+cox.name.split('-')[2]+"</p></div></div></div></div><div class='_mid-b-flight'>"+ai+"</div></div>"
                                }else if(airportCode==cox.code.split('-')[1]){
                                    tipright+="<div class='_mid-b'><div class='_mid-b-line _tipFlight'abbr='"+cox.name.split('-')+"'><div class='_mid-b-line-h'></div><div class='_mid-b-line-b'><div class='_mid-b-line-b1'><h2>"+cox.code.split('-')[0]+"</h2><div><p>"+cox.name.split('-')[0]+"</p></div></div><div class='_mid-b-line-b2 _mid-b-line-tag'><h2>"+cox.code.split('-')[1]+"</h2><div><p>"+cox.name.split('-')[1]+"</p></div></div><div class='_mid-b-line-b3'><h2>"+cox.code.split('-')[2]+"</h2><div><p>"+cox.name.split('-')[2]+"</p></div></div></div></div><div class='_mid-b-flight'>"+ai+"</div></div>"
                                }else if(airportCode==cox.code.split('-')[2]){
                                    tipright+="<div class='_mid-b'><div class='_mid-b-line _tipFlight'abbr='"+cox.name.split('-')+"'><div class='_mid-b-line-h'></div><div class='_mid-b-line-b'><div class='_mid-b-line-b1'><h2>"+cox.code.split('-')[0]+"</h2><div><p>"+cox.name.split('-')[0]+"</p></div></div><div class='_mid-b-line-b2'><h2>"+cox.code.split('-')[1]+"</h2><div><p>"+cox.name.split('-')[1]+"</p></div></div><div class='_mid-b-line-b3 _mid-b-line-tag'><h2>"+cox.code.split('-')[2]+"</h2><div><p>"+cox.name.split('-')[2]+"</p></div></div></div></div><div class='_mid-b-flight'>"+ai+"</div></div>"
                                }
                            }
                        })
                    });
                    tipright+="</div>";
                    $(".pla-prompTip").html(tipright);
                    tipRight();
                    $(".pla-prompRight").css("display","none");
                    return;
                };
               
            }
            
        }
    }
    myChart.on('click', function (params) {
    	window.chart_openTool(params);
    });
    //c
    function tipRight(){
    	 var perm={
				   show3:'none',
				   show1:'none',
				   show4:'none',
				   show5:'none',
				   show8:'none',
				   show13:'none',
		 }
    	 for(var i=0;i<menus2.length;i++){
    		 	if(menus2[i].id==1&&menus2[i].type==1){
					perm.show1='block';
				}else if(menus2[i].id==3&&menus2[i].type==1){
					perm.show3='block';
				}else if(menus2[i].id==4&&menus2[i].type==1){
					perm.show4='block';
				}else if(menus2[i].id==5&&menus2[i].type==1){
					perm.show5='block';
				}else if(menus2[i].id==13&&menus2[i].type==1){
					perm.show13='block';
				}else if(menus2[i].id==8&&menus2[i].type==1){
					perm.show8='block';
				}
    	 }
       setTimeout(function(){
           $(".pla-promptBox").css("display","block");
           var topDis = parseInt($('.pla-promptBox').css('top').split('px')[0]);
           var bottomDis = parseInt($(".pla-promptBox").css('bottom').split('px')[0]);
           if(bottomDis<0){
        	   $(".pla-promptBox").css('top',(topDis+bottomDis)+'px'); 
           }
           $("._tipFlight").on("mouseover",function(){
               var fTop=infer("._tipBodyBox")[4];
               var num=$("._tipFlight").index($(this));
               var zTOP=0;
               if(num==0){
                   //$(".pla-prompRight").css("top","0px")//$(".pla-prompRight").css("top","0px");//暂时预留
               }else {
                   for(var i=0;i<num;i++){
                       zTOP+=parseFloat($("._tipFlight").eq(i).css("height").split("px")[0]);
                   }
                   $(".pla-prompRight").css("top",zTOP+fTop+"px");
               }
               if($(this).hasClass("_mid-t")){
            	   var abbrs=$(this).attr("abbr").split("-")[0].split(",");
            	   var lines = Odata.success.linees;
            	   var flag = false;
            	   if(abbrs.length==2){
            		   var a1 = airportMap[abbrs[0]].iata;
                	   var a2 = airportMap[abbrs[1]].iata;
                	   for(var k = 0;k<lines.length;k++){
                		   if(lines[k].indexOf(a1+a2)>-1||lines[k]==(a1+a2)){
                			   flag = true; 
                		   }
                		   if(lines[k].length>6){
                			   if((lines[k].substring(0,3) + lines[k].substring(6,9))==(a1+a2)){
                				   flag = true; 
                			   }
                		   }
                	   }
            	   }
            	   if(flag){ 
            		   $(".pla-prompRight-box").html("<div style='display:"+perm.show3+"'><div class='pla-prompTip-ico'>&#xe61a;</div><div class='pla-prompTip-des'>共飞运营对比</div></div><div style='display:"+perm.show1+"'><div class='pla-prompTip-ico'>&#xe629;</div><div class='pla-prompTip-des'>航线历史收益统计</div></div><div style='display:"+perm.show4+"'><div class='pla-prompTip-ico'>&#xe618;</div><div class='pla-prompTip-des'>客源组成</div></div>")
            		   if(perm.show1=='none'&&perm.show3=='none'&&perm.show4=='none'){
            			   $(".pla-prompRight").attr({"tag":num,"flt":1}).css("display","none");
            		   }else{
            			   $(".pla-prompRight").attr({"tag":num,"flt":1}).css("display","block");
            		   }
            	   }else{
            		   $(".pla-prompRight-box").html("<div style='display:"+perm.show3+"'><div class='pla-prompTip-ico'>&#xe61a;</div><div class='pla-prompTip-des'>共飞运营对比</div></div><div style='display:"+perm.show1+"'><div class='pla-prompTip-ico'>&#xe629;</div><div class='pla-prompTip-des'>航线历史收益统计</div></div>")
            		  
            		   if(perm.show1=='none'&&perm.show3=='none'){
            			   $(".pla-prompRight").attr({"tag":num,"flt":1}).css("display","none");
            		   }else{
            			   $(".pla-prompRight").attr({"tag":num,"flt":1}).css("display","block");
            		   }
            	   }
               }else if($(this).hasClass("_mid-b-line")){
            	   var abbrs=$(this).attr("abbr").split("-")[0].split(",");
            	   var lines = Odata.success.linees;
            	   var flag = false;
            	   if(abbrs.length==2){
            		   var a1 = airportMap[abbrs[0]].iata;
                	   var a2 = airportMap[abbrs[1]].iata;
                	   for(var k = 0;k<lines.length;k++){
                		   if(lines[k].indexOf(a1+a2)>-1||lines[k]==(a1+a2)){
                			   flag = true; 
                		   }
                		   if(lines[k].length>6){
                			   if((lines[k].substring(0,3) + lines[k].substring(6,9))==(a1+a2)){
                				   flag = true; 
                			   }
                		   }
                	   }
            	   }
            	   if(abbrs.length==3){
            		   var a1 = airportMap[abbrs[0]].iata;
                	   var a2 = airportMap[abbrs[1]].iata;
                	   var a3 = airportMap[abbrs[2]].iata;
                	   for(var k = 0;k<lines.length;k++){
	                	   if(lines[k]==(a1+a2+a3)){
	            			   flag = true; 
	            		   }
                	   }
            	   }
            	   if(flag){
	            	   $(".pla-prompRight-box").html("<div style='display:"+perm.show3+"'><div class='pla-prompTip-ico'>&#xe61a;</div><div class='pla-prompTip-des'>共飞运营对比</div></div><div style='display:"+perm.show1+"'><div class='pla-prompTip-ico'>&#xe629;</div><div class='pla-prompTip-des'>航线历史收益统计</div></div><div style='display:"+perm.show4+"'><div class='pla-prompTip-ico'>&#xe618;</div><div class='pla-prompTip-des'>客源组成</div></div>")
	                  
	            	   if(perm.show1=='none'&&perm.show3=='none'&&perm.show4=='none'){
	            		   $(".pla-prompRight").attr({"tag":num,"flt":0}).css("display","none");
            		   }else{
            			   $(".pla-prompRight").attr({"tag":num,"flt":0}).css("display","block");
            		   }
            	   }else{
            		   if(perm.show1=='none'&&perm.show3=='none'){
            			   $(".pla-prompRight").css('display','none');
            		   }
            		   $(".pla-prompRight-box").html("<div style='display:"+perm.show3+"'><div class='pla-prompTip-ico'>&#xe61a;</div><div class='pla-prompTip-des'>共飞运营对比</div></div><div style='display:"+perm.show1+"'><div class='pla-prompTip-ico'>&#xe629;</div><div class='pla-prompTip-des'>航线历史收益统计</div></div>")
	                  
            		   if(perm.show1=='none'&&perm.show3=='none'){
            			   $(".pla-prompRight").attr({"tag":num,"flt":0}).css("display","none");
            		   }else{
            			   $(".pla-prompRight").attr({"tag":num,"flt":0}).css("display","block");
            		   }
            	   }
               }else{
                   if(!$(this).hasClass("_tipTile")){
                	   var abbrs=$(this).attr("abbr").split("-")[1];
                	   var lesss=Odata.success.fltNbrs;
                	   if(lesss.indexOf(abbrs)!=-1){
                		   $(".pla-prompRight-box").html("<div style='display:"+perm.show13+"'>" +
                		   		"<div class='pla-prompTip-ico'>&#xe628;</div><div class='pla-prompTip-des'>销售报表</div></div>" +
                		   		"<div style='display:"+perm.show1+"'><div class='pla-prompTip-ico'>&#xe629;</div><div class='pla-prompTip-des'>航线历史收益统计</div></div>" +
                		   				"<div style='display:"+perm.show5+"'><div class='pla-prompTip-ico'>&#xe624;</div><div class='pla-prompTip-des'>销售动态</div></div>" +
                		   						"<div style='display:"+perm.show8+"'><div class='pla-prompTip-ico'>&#xe688;</div><div class='pla-prompTip-des'>销售数据</div></div>" + 
                		   						"<div style='display:"+perm.show8+"'><div class='pla-prompTip-ico'>&#xe6a9;</div><div class='pla-prompTip-des'>客票分析</div></div>"
                		   						)
                		   if(perm.show1=='none'&&perm.show5=='none'&&perm.show13=='none'&&perm.show8=='none'){
                			   $(".pla-prompRight").attr({"tag":num,"flt":0}).css("display","none");	   
                		   }else{
                			   $(".pla-prompRight").attr({"tag":num,"flt":0}).css("display","block");	   
                		   }
                	   }else{
                		   $(".pla-prompRight-box").html("<div style='display:"+perm.show13+"'>" +
                		   		"<div class='pla-prompTip-ico'>&#xe628;</div><div class='pla-prompTip-des'>销售报表</div></div>" +
                		   		"<div style='display:"+perm.show1+"'><div class='pla-prompTip-ico'>&#xe629;</div><div class='pla-prompTip-des'>航线历史收益统计</div></div>" +
                		   				"<div style='display:"+perm.show8+"'><div class='pla-prompTip-ico'>&#xe688;</div><div class='pla-prompTip-des'>销售数据</div></div>" + 
                		   				"<div style='display:"+perm.show8+"'><div class='pla-prompTip-ico'>&#xe6a9;</div><div class='pla-prompTip-des'>客票分析</div></div>"                		   				
                		   )
                		   if(perm.show1=='none'&&perm.show13=='none'&&perm.show8=='none'){
                			   $(".pla-prompRight").attr({"tag":num,"flt":0}).css("display","none");	   
                		   }else{
                			   $(".pla-prompRight").attr({"tag":num,"flt":0}).css("display","block");	   
                		   }
                	   }   
                   }
               }
               $(".pla-prompRight").on("mouseover",function(){
                   $("._tipFlight").removeClass("_pr-set");
                   $("._tipFlight").eq($(".pla-prompRight").attr("tag")).addClass("_pr-set");
               });
               $(".pla-prompRight").on("mouseout",function(){
                   $("._tipFlight").removeClass("_pr-set");
               });
           });
           addBar(".pla-prompTip","._tipBody","._tipBodyBox");
       },20);
    }
    //关闭浮沉框
    $(".pla-promptBox-legTitle>span").on("click",function(){
        $(".pla-promptBox").animate({"opacity":"0"},200,function(){
            var num = myChart.getOption();
            num.series[2].data=[];
            $(".pla-promptBox").css("display","none");
        });
    });
    /* 有无数据航线区分开关 */
    $('#turnLine').on('click',function(){
        if($('.pla-btn-switch').attr('tag')=='air'||$("#global-routes").css('display')=='none'){
            return;
        }
        if($(this).hasClass('iskg0')){
            $(this).removeClass('iskg0').addClass('iskg1');
            $(".turn-off").addClass('iskgCkecked');
            //历史航线的点
            var temp = option.series[5].data;
            //无数据航线
            var tempdata = option.series[3].data;
            var ret = [];
            for(var j=0;j<temp.length;j++){
            	 for(var i=0;i<tempdata.length;i++){
            		 if(temp[j].name==tempdata[i].toName||temp[j].name==tempdata[i].fromName){
            			var obj = new Object();
            			obj = JSON.parse(JSON.stringify( temp[j]));
            			obj.type = "2";
            			ret.push(obj); 
            		 }
            	 }
            }
            option.series[4].data = [];
            option.series[5].data = ret;
        }else {
            $(this).removeClass('iskg1').addClass('iskg0');
            $(".turn-off").removeClass('iskgCkecked');
            option.series[4].data=DataList.data1;
            option.series[5].data=DataList.point1;
            stickOption=option;
        }
        myChart.setOption(option,true);
    });

    /*绑定机场视图弹出框功能*/
    $(".their-own-body").on("click","div",function(){
    	if($(this).parent().attr("class")=="their-own-body-link"){
    		var namee = $(this).children("div").html();
    		var fnURL = [];
       	 	fnURL[0] = new Object();fnURL[0].name="机场历史运营";fnURL[0].urll="/outPort";
            fnURL[1] = new Object();fnURL[1].name="航班动态";fnURL[1].urll="/airline_dynamic";
            fnURL[2] = new Object();fnURL[2].name="时刻分布";fnURL[2].urll="/processTask";
           for(var a in fnURL){
          	 if(namee==fnURL[a].name){
          		$("#pages",window.document.body).attr("src",fnURL[a].urll);
          	 };
          }
           $(".pla-supernatant").css({"display":"block","background-color":"rgba(0,0,0,0.6)"});
           $(".pla-promptBox").css({"display":"none"});
           $(".pla-supernatant-cont").css({"display":"block"});
           $(".pla-supernatant-cont").animate({"opacity":"1","top":"5%"},500);
           $(".their-own").css({"display":"none"});
    	}
    });
    /*打开功能窗口*/
    $(".pla-supernatant-del").on("click",function(){ //关闭
    	supData = {};//清空
        $(".pla-supernatant-cont").animate({"opacity":"0","top":"-30px"},500,function(){
            $(".pla-supernatant-cont").css({"display":"none"});
            $(".pla-supernatant").css({"display":"none","background-color":"rgba(0,0,0,0)"});
        });
    });
    $(".pla-spread").delegate(".menu a","click",function(){ //关闭
        supData.contentType = $(this).html();
        $(".pla-supernatant").css({"display":"block","background-color":"rgba(0,0,0,0.6)"});
        $(".pla-supernatant-cont").css({"display":"block"});
        $(".pla-supernatant-cont").animate({"opacity":"1","top":"5%"},500);
    });
    //从菜单进入功能页面--start
    $('.flagClass').on('click',function(){
    	supData.flag = 1;
    });
    //从菜单进入功能页面--end

    //函数节流
    function lon_throttle(fn,c){
        if(fn.id){}clearTimeout(fn.id);
        fn.id = setTimeout(function(){
            fn();
        },600)
    }
    var changeSizeFlowWindow = function(){
        $(".pla-supernatant-cont").css({"height":parseFloat($("body").css("height").split("px")[0])*0.9+"px"});
        myChart.resize();
    }
    //窗口改变修改大小
    window.onresize=function(){//做个节流
        lon_throttle(changeSizeFlowWindow);
    };
    var selfAirPortName = sessionStorage.getItem('linesData') ? JSON.parse(sessionStorage.getItem('linesData')).success.airportCode : null;
    window.mapDom.name = selfAirPortName;
    $.ajax({
        url:"/restful/getChinaAirLineData",
        dataType:'jsonp',
        type:"GET",
        success:function (data) {
            localStorage.setItem('pChinaAllLinesOptionSeries' +selfAirPortName, JSON.stringify(data.success.chinaAriLineDataList));
            sessionStorage.setItem('allLineD' +selfAirPortName, JSON.stringify(data));
            allLineD = data;
        }
    })
    //  打开全国航线视图
    $("#global-routes").on("click",function(){
        stated = 2;
        clear_Advabced_filter_option();
        selfAirPortName = !!null ?  selfAirPortName : (sessionStorage.getItem('linesData') ? JSON.parse(sessionStorage.getItem('linesData')).success.airportCode : null);
        window.mapDom.name = selfAirPortName;
    	$("#their-ownA").css("display","none");
    	$("#their-ownB").css("display","none");
    	$("#line-kg").css("display","none");
    	$(".pla-search").css("display","none");
        $(".pla-data").hide('normal');
    	if(localStorage.getItem("pChinaAllLinesOption" + selfAirPortName)){	//从本地获取全国航线视图数据
        // if(false){	//从本地获取全国航线视图数据
    		var storageOption = JSON.parse(localStorage.getItem("pChinaAllLinesOption" + selfAirPortName));
            myChart.setOption(storageOption,true);
            $("#global-routes").css("display","none");
            $('.pla-btn-switch_line').css('display','none');
            if(ipness){
            	$(".pla-btn-simulate").css("display","none");
            	$("#simulate-routes").css('display','block');
            }
            $(".pla-btn-switch,.pla-btn-flight,.pla-btn-trend").css("display","none");
            $(".pla-btn-national").css("display","block");
		    //关闭其他框框
		    $(".pla-ranking").css({"display":"none"});
		    storageOption = null;
    		return openSearch();
        }
        
        var Collection={
            "title":{"show":false,"text":"全国视图","subtext":"全国航线视图","left":"center","textStyle":{"color":"#fff"}},
            "bmap":{"center":["110.47","32.40"],"zoom":6,"color":"red","roam":"move","mapStyle":{"styleJson":[{"featureType":"water","elementType":"all","stylers":{"color":"#071327","visibility":"on"}},{"featureType":"land","elementType":"all","stylers":{"color":"#223350","visibility":"on"}},{"featureType":"boundary","elementType":"all","stylers":{"color":"#465b6c","visibility":"on"}},{"featureType":"arterial","elementType":"all","stylers":{"visibility":"off"}},{"featureType":"highway","elementType":"all","stylers":{"visibility":"off"}},{"featureType":"label","elementType":"all","stylers":{"visibility":"off"}}]}},
            "tooltip":{ trigger: 'item',show:false},
            "series":[
                {
                    "name": "本场航线",
                    "coordinateSystem":"bmap",
                    "type":"lines",
                    "zlevel":0,
                    "lineStyle":{
                        "normal":{"color":"#f36234","width":"1.8","opacity":0.8,"curveness":0.2},
                        "emphasis":{"color":"#e65122"}
                    },
                    "polyline":false,
                    "data":DataList.data0,
                    "label": {
                        normal: {
                            show: false
                        }
                    },
                    "blendMode":"lighter",
                    "zlevel": 2
                },
                {
                    "name": "主要城市",
                    "coordinateSystem":"bmap",
                    "type":"scatter",
                    "label": {
                        normal: {
                            formatter: '{b}',
                            position: 'right',
                            show: true,
                            textStyle:{
                                color:"white"
                            }
                        }
                    },
                    "itemStyle": {
                        normal: {
                            color: '#eaf4ff',
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },
                    "data":[{"name":"北京","value":["116.24", "39.55",0]},{"name":"上海","value":["121.29", "31.14",0]},{"name":"深圳","value":["114.07", "22.33",0]},{"name":"广州","value":["113.14", "23.08",0]},{"name":"昆明","value":["102.42", "25.04",0]},{"name":"成都","value":["104.04","30.40",0]},{"name":"杭州","value":["120.10","30.16",0]},{"name":"武汉","value":["114.17","30.35",0]},{"name":"重庆","value":["106.33","29.35",0]},{"name":"南京","value":["118.46","32.03",0]},{"name":"天津","value":["117.12","39.02",0]},{"name":"西安","value":["108.57","34.17",0]},{"name":"长沙","value":["112.59","28.12",0]},{"name":"沈阳","value":["123.25","41.48",0]},{"name":"青岛","value":["120.18","36.03",0]},{"name":"郑州","value":["113.40","34.46",0]},{"name":"大连","value":["121.36","38.55",0]},{"name":"宁波","value":["121.33","29.52",0]}],
                    "zlevel": 3
                },
                {
                    "name": "其它航线",
                    "coordinateSystem":"bmap",
                    "type":"lines",
                    "zlevel":0,
                    "lineStyle":{
                        "normal":{
                            "color":"#4ba3fd",
                            "width":"0.7",
                            "opacity":0.15,
                            "curveness":0.2
                        },
                        "emphasis":{"color":"#3887be"}
                    },
                    "polyline":false,
                    "data":allLineD.success.chinaAriLineDataList,
                    "blendMode":"lighter",
                    "zlevel": 1
                }
            ]
        };
        allLineD.success.chinaAriLineDataList.forEach(function(value){
            value.coords=[value.coords.toName,value.coords.fromName];
        });
        myChart.setOption(Collection,true);
        localStorage.setItem('pChinaAllLinesOption' + selfAirPortName, JSON.stringify(Collection));
        $("#global-routes").css("display","none");
        $('.pla-btn-switch_line').css('display','none');
        if(ipness){
            $(".pla-btn-simulate").css("display","none");
            $("#simulate-routes").css('display','block');
        }
        $(".pla-btn-switch,.pla-btn-flight,.pla-btn-trend").css("display","none");
        $(".pla-btn-national").css("display","block");
        openSearch();
        //关闭其他框框
        $(".pla-ranking").css({"display":"none"});
    });
    $(".pla-btn-national").on("click",function(){
    	$('#air').hide();
    	window.location.hash = '';	//清除路由    	
        if($('.pla-btn-switch').attr('tag')=='air'){
            stated = 0;
            myChart.setOption(ops, true);
        }else {
            stated = 1;
            $("#line-kg").css("display","block");
            myChart.setOption(stickOption, true);
        }
    	$(".pla-search").css("display","block");
        $(".pla-data").show();
        $("#global-routes").css("display","block");//全国航线
        $('.pla-btn-switch_line').css('display','block');
        $(".pla-btn-switch,.pla-btn-flight,.pla-btn-trend").css("display","block");
        $(".pla-btn-national").css("display","none");
        if(ipness){
        	$("#simulate-routes").css('display','block');//模拟航线
            $(".simulateRoutes").css("display","none");
            $('.pla-btn-simulate').css("display","none");
        }
        $('#advanced-filter-box').show();
    });
});
function instrumentNew(){
    /*配置仪表盘设置*/
    //日期
    if(Odata.success.airportFocus.timeDemension=="最近1天"){
        $("#yesterday").prop("checked",true);
    }else if(Odata.success.airportFocus.timeDemension=="最近7天"){
        $("#sevenDay").prop("checked",true);
    }else if(Odata.success.airportFocus.timeDemension=="最近30天"){
        $("#thirtyDay").prop("checked",true);
    };
    //航班
    if(Odata.success.airportFocus.flightRange=="所有航班"){
        $("#allFlights").prop("checked",true);
    }else if(Odata.success.airportFocus.flightRange=="关注航班"){
        $("#focusFlights").prop("checked",true);
    }
    //数据统计
    $("#station").prop("checked",false);
    $("#jiltFly").prop("checked",false);
    if(Odata.success.airportFocus.dataRange=="包含过站，包含甩飞"){
        $("#station").prop("checked",true);
        $("#jiltFly").prop("checked",true);
    }else if(Odata.success.airportFocus.dataRange=="包含过站"){
        $("#station").prop("checked",true);
        $("#jiltFly").prop("checked",false);
    }else if(Odata.success.airportFocus.dataRange=="包含甩飞"){
    	$("#station").prop("checked",false);
        $("#jiltFly").prop("checked",true);
    };
    //指标
    if(Odata.success.airportFocus.focusTarget.split("，").indexOf("客量")!=-1){
        $("#guestAmount").prop("checked",true);
    }else{
    	$("#guestAmount").prop("checked",false);
    }
    if(Odata.success.airportFocus.focusTarget.split("，").indexOf("班次")!=-1){
        $("#shift").prop("checked",true);
    }else{
    	$("#shift").prop("checked",false);
    }
    if(Odata.success.airportFocus.focusTarget.split("，").indexOf("客票收入")!=-1){
        $("#income").prop("checked",true);
    }else{
    	$("#income").prop("checked",false);
    }
    if(Odata.success.airportFocus.focusTarget.split("，").indexOf("均班客量")!=-1){
        $("#passengerVolume").prop("checked",true);
    }else{
    	$("#passengerVolume").prop("checked",false);
    }
    if(Odata.success.airportFocus.focusTarget.split("，").indexOf("均班收入")!=-1){
        $("#classIncome").prop("checked",true);
    }else{
    	$("#classIncome").prop("checked",false);
    }
    if(Odata.success.airportFocus.focusTarget.split("，").indexOf("综合客座率")!=-1){
        $("#loadFactors").prop("checked",true);
    }else{
    	$("#loadFactors").prop("checked",false);
    }
    if(Odata.success.airportFocus.focusTarget.split("，").indexOf("机场准点率")!=-1){
        $("#airportPunctuality").prop("checked",true);
    }else{
    	$("#airportPunctuality").prop("checked",false);
    }
}

//【874机场视图功能延伸】longhong

	//请求数据
	function turn_on_this_plane(iata,hx,air){
		var this_ajax={};
		var a;		
		var this_data=$.parseJSON(air);
		var hxJSON=$.parseJSON(hx);

		
		$.ajax({
			url: "/airportFlight/findAirportFlight",
			type: "GET",
			async: true,
			dataType: "json",
			data: {iATA: iata},
			success:function(ddd){
                var getTo=[],fromw,fromj;
                $('#portPanelLoading').hide('normal');
                $('.full-layer').hide('normal');
				//重组数据
				fromj = ddd.data.airportLocation.cityCoordinateJ;
				fromw = ddd.data.airportLocation.cityCoordinateW;
				$.each(ddd.data.airportLocationList,function(i,el){
					getTo.push({
                        "coords":[[fromj,fromw],[el.cityCoordinateJ,el.cityCoordinateW]],
                        "name":el.airlnCd,
                        "fromName": ddd.data.airportLocation.airlnCd,
                        "toName":el.airlnCd
                    });
				});
				this_data.series[0].data=getTo;
				this_data.series.length=3;
				this_data.series[1].data.value=[111.92,36.2];
				this_data.series.push(hxJSON.series[0]);
				var new_tooltip={
					'trigger':"none"		
				}		
				//重格式化
				this_data.tooltip=new_tooltip;
				this_data.series.splice(1,2);
				this_data.series[0].effect=null;
				this_data.series[0].name="当前在飞航线";
				this_data.series[0].label={};
				//添加标签
				this_data.series[0].label.normal={
						'show':true,
						'formatter':'{b}',
						'textStyle':{
							'color':'#999',
							'fontSize':12
						}
				};
				this_data.series[1].label.emphasis.show="true";
				this_data.series[1].name="";
				this_data.tooltip={};
				this_data.tooltip.trigger= "none" ;
				this_data.tooltip.show=false;
                this_data.tooltip.formatter='{b}';
                myChart.setOption(this_data, true);
			},
			error:function(){
				console.log("请求失败。");
				$('#portPanelLoading').hide('normal');
			}
		})
    }
    /*
    *   航线/航点高级筛选
    *   long
    *   2017-9-27
    */
    var advanced_filter_option = {
        "fly_area_grade":[],
        "lastyear_person":[],
        "airport_type":[],
        "subsidy_type":[]
    }
    function arrRemoveByValue(arr, val) {   //删除数组中指定元素
        for(var i=0; i<arr.length; i++) {
            if(arr[i] == val) {
                arr.splice(i, 1);
                break;
            }
        }
    }
    $("#advanced-filter-turn").on('click', function(e){ //开关
        return $("#advanced-filter-container").toggle();
        
    })
    $("#advanced-filter-box").on('click', function(e){
        return $("#change-container").children().hide();
    })
    $("#af-pramas-reseter").on('click', function(e){    //重置所有选项
        advanced_filter_option = {
            "fly_area_grade":[],
            "lastyear_person":[],
            "airport_type":[],
            "subsidy_type":[]
        };
        $('.af-checklist-item').each(function(i,id){
            if($(this).hasClass('af-checklist-item-check')){
                return $(this).removeClass('af-checklist-item-check');
            }
        })
        $("#lastyear-person-a").val('');
        $("#lastyear-person-b").val('');
        if(af_state){
            af_state = false;
            $("#advanced-filter-container").hide();
            if($("#pla-btn-qhs .pla-btn-switch").attr("tag")==="line"){
                return myChart.setOption(stickOption, true);
            }else{
                return myChart.setOption(ops, true);
            }
        }
    })
    $('.af-checklist-item').on('click', function(e){    //item选择
        var _type = $(this).parent('div').attr('data')
        var _data = $(this).text();
        if($(this).hasClass('af-checklist-item-check')){
            arrRemoveByValue(advanced_filter_option[_type],_data);
            return $(this).removeClass('af-checklist-item-check');
        }
        advanced_filter_option[_type].push(_data);
        return $(this).addClass('af-checklist-item-check');
    })

    $('.af-input-box-item').on('input',function(e){
        return $(this).val(parseInt($(this).val().replace(/\D/g,''))>0 ? parseInt($(this).val().replace(/\D/g,'')) : 0);
    });

    function analysisOption(){
        var _count = 0;
        for(var item in advanced_filter_option){
            if(item !== 'lastyear_person' && advanced_filter_option[item].length > 0){
                _count++;
            }
        }
        if(advanced_filter_option.lastyear_person[0]+advanced_filter_option.lastyear_person[1] >= 0) _count++;
        return (_count>0?true:false);
    }
    
    function af_airline(data){//航线视图
        var _option = JSON.parse(JSON.stringify(stickOption)), _series = [], _linesData = [], _cityData = [];
        var selfCoord = window.airportMap[window.airportCode].coordinate.split(',');    //当前机场
        _option.series.map(function(ser){
            if(ser.type == 'scatter' && ser.symbol !=='circle'){    //填充本机场series
                _series.push(ser);
            };
            if(ser.type == 'lines' && ser.data.length > 0){    //从所有航线中遍历对比
                ser.data.map(function(item){
                    if(data.indexOf(item.toName) > -1){
                        var newitem = {//航线-lines
                            formName: window.airportName,
                            toName: item.toName,
                            type: item.type,
                            allLine: item.allLine,
                            coords: [selfCoord,item.coords[1]]
                        }
                        _linesData.push(newitem);
                        _cityData.push({//城市-scatter
                            name: item.toName,
                            type: 1,
                            value: item.coords[1].length==2 ? item.coords[1].concat([30]) : item.coords[1]
                        })
                        data.splice(data.indexOf(item.toName), 1);  //用完就甩，避重
                    }
                })
            }
        })
        if(_linesData.length == 0 ) {return showWarring('暂无符合筛选条件的机场，请切换条件后重试')};
        _series.push({  //填充航线series
            coordinateSystem : "bmap",
            polyline : false,
            type : "lines",
            zlevel: 0,
            lineStyle:{
                normal:{
                    color: 'rgb(241,124,103)',
                    curveness: 0.1,
                    opacity: 0.4,
                    width: 1.5
                }
            },
            data:_linesData
        });
        _series.push(_option.series[2])
        _series[2].data = _cityData;
        _option.series = _series;
        _option.bmap.zoom = 5;
        $("#advanced-filter-container").hide();
        af_state = true;
        return myChart.setOption(_option,true);
    }
    function af_airport(data){//机场视图
        var oppp = JSON.parse(JSON.stringify(ops));
        var newData = [];
        oppp.series[0].data.map(function(item){
            if(data.indexOf(item.name) > -1 ){
                newData.push(item);
            }
        });
        if(newData.length === 0){ return showWarring('暂无符合筛选条件的机场，请切换条件后重试') };
        oppp.series[0].data = newData;
        oppp.series[0].symbolSize = 12;
        oppp.series[0].label.normal.show = true;
        oppp.bmap.zoom = 5;
        $("#advanced-filter-container").hide();
        af_state = true;
        return myChart.setOption(oppp,true);
    }


    $("#af-search-btn").on('click',function(e){//查询目标机场
        var viewType = $('#pla-btn-qhs .pla-btn-switch').attr('tag') == 'line' ? true:false;
        var _pernuma = parseInt($('#lastyear-person-a').val()) > 0 ? parseInt($('#lastyear-person-a').val()) : 0;
        var _pernumb = parseInt($('#lastyear-person-b').val()) > 0 ? parseInt($('#lastyear-person-b').val()) : 0;
        var _val = _pernuma > _pernumb?[_pernumb,_pernuma]:[_pernuma,_pernumb];
        advanced_filter_option.lastyear_person = _val;
        if(analysisOption()){//判断查询条件是否有效
            return ($.ajax({
                    type:'get',
                    url : '/findFilterParamAirPort',
                    data: {
                        'fly_area_grade': advanced_filter_option.fly_area_grade.join(','),
                        'lastyear_person': _pernuma + _pernumb >0 ? advanced_filter_option.lastyear_person.join(','): '',
                        'airport_type': advanced_filter_option.airport_type.join(','),
                        'subsidy_type': advanced_filter_option.subsidy_type.join(',')
                    },
                    dataType : 'json',
                    success : function(data) {
                        var mock = data.lists;
                        var dataList = [],itList = [], ctList = [];
                        mock.map(function(item){
                            dataList.push({
                                city: item.city,
                                iata: item.iata
                            })
                            ctList.push(item.city);
                            itList.push(iataToPortCN(item.iata));
                        })
                        if(viewType){
                            af_airline(ctList);
                        }else{
                            af_airport(itList);
                        }
                    },
                    error : function() {
                    }
                }
            ));
        }else{
            return showWarring('请输入正确的筛选条件')
            //这里表示参数有误
        }
    })
    var ad_resetMap = function(){
        $('#ad-resetMap').hide();
        return $(".pla-btn-switch").attr("tag")=='line' ? myChart.setOption(stickOption, true) : myChart.setOption(ops, true);
    }
    var showWarring = function(text){
        $('#homeWarning p').html(text)
        $('#homeWarning').css('display', 'flex');
        return setTimeout(function(){
            $('#homeWarning').css('display', 'none');
        },2500)
    }

    //-------------------------------------- 三字码转机场
    var iataToPortZN = function (iata){  // CANWDSLHW => ["广州白云", "十堰武当山", "兰州中川"]
        if(iata){
            var zn = ''
            window.nationalAirport.map(function(item){
                if(item.code  == iata){
                    zn = item.airportName;
                }
            })
            return zn;
        }
        else{
            return "---";
        }
    }
    //-------------------------------------- 三字码转机场
    var iataToPortCN = function (iata){  // CANWDSLHW => ["广州白云", "十堰武当山", "兰州中川"]
        if(iata){
            var zn = ''
            for(var key in airportMap){
                if(airportMap[key].iata  == iata){
                    zn = airportMap[key].aptChaNam;
                }
            }
            return zn;
        }
        else{
            return "---";
        }
    }
    var clear_Advabced_filter_option = function(){//重置筛选        
        $("#advanced-filter-container").hide();
        var _flag = false;
        for(var item in advanced_filter_option){
            if(advanced_filter_option[item].length > 0){
                advanced_filter_option[item] = [];
                _flag = true;
            }
        }
        if(_flag){
            $('#lastyear-person-a').val('');
            $('#lastyear-person-b').val('');
            $('.af-checklist-item-check').each(function(){
                $(this).removeClass('af-checklist-item-check');
            })
        }
    }
    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
        history.pushState(null, null, document.URL);
    });