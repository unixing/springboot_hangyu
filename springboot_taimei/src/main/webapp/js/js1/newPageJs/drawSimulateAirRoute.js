//画模拟航线
//  打开全国航线视图
var airRoutesArray=[];
//var cityCroods=[{'fromName':'兰州','toName':'十堰','coords':{'fromName':['103.51','36.04'],'toName':['110.47','32.40']}}];
var cityCroods=[];
var cityCroods1=[];
var cityCrood=[];
var cityCrood1=[];
var airPortName;
var airRoutesArrayTemporary=new Array();
function drawNationalAirline(){
	var Collection={
            "title":{"show":false,"text":"模拟迁徙","subtext":"数据纯属虚构","left":"center","textStyle":{"color":"#fff"}},
            "bmap":{"center":["110.47","32.40"],"zoom":6,"color":"red","roam":"move","mapStyle":{"styleJson":[{"featureType":"water","elementType":"all","stylers":{"color":"#071327","visibility":"on"}},{"featureType":"land","elementType":"all","stylers":{"color":"#223350","visibility":"on"}},{"featureType":"boundary","elementType":"all","stylers":{"color":"#465b6c","visibility":"on"}},{"featureType":"arterial","elementType":"all","stylers":{"visibility":"off"}},{"featureType":"highway","elementType":"all","stylers":{"visibility":"off"}},{"featureType":"label","elementType":"all","stylers":{"visibility":"off"}}]}},
            "tooltip":{ trigger: 'item'},
            "series":[
                      	{

                            "name": 'pm2.5',
                            "type": 'scatter',
                            "coordinateSystem": 'bmap',
                            "data": cityCrood,
                            "symbol":"circle",
                            "symbolSize": 8,
                            "label": {
                                "normal": {
                                    "show": false
                                },
                                "emphasis": {
                                    "show": false
                                }
                            },
                            "itemStyle": {
                            	normal:{
                            		"color":'#7ebbe8',
                            	},
                                "emphasis": {
                                    "borderColor": '#fff',
                                    "borderWidth": 1
                                }
                            }
                      	},
                        {
                            "coordinateSystem":"bmap",
                            "type":"lines","zlevel":0,
                            "lineStyle":{
                            "normal":{
                                "color":"#4088B6",
                                "width":"1.0",
                                "opacity":0.4,
                                "curveness":0.2},
                                "emphasis":{"color":"#3887be"}
                            },
                            "polyline":false,
                            "data":cityCroods,
                            "blendMode":"lighter"
                        }
                      ]
            };
			cityCroods.forEach(function(value){
				value.coords=[value.coords.toName,value.coords.fromName];
			});
        myChart.setOption(Collection,true);
}
function airRoutesList(){
	var option='<h3 class="simulate-title" style="text-align:center;color:white;line-height:30px;">模拟航线管理</h3>'
		  +'<div class="simulate-route-list">'
		  +'<h5>模拟航线列表<span class="addfltline" title="添加">&#xe6b0;</span><span class="listedite" title="编辑">&#xe65a;</span><span class="batchAdd" title="批量添加">&#xe6b6;</span></h5>'
		  +'<ul class="simulate-routes">';
		if(airRoutesArray.length>0){
			for(var i=0;i<airRoutesArray.length;i++){
				option +='<li>'+airRoutesArray[i]+'</li>';
			}
		}
		option +='</ul>'
		  +'</div>'
		  +'<div class="simulate-route-option">'
		  +'<h5 class="simulate-route-title">模拟航线添加</h5>'
		  +'<div class="fltlineadd"></div>'
		  +'</div>'
		  +'<div class="pla-ranking-shut simulateRoute-shut">&#xe612;</div>';
	$('.simulateRoutes').html(option);
}
$(function(){
//	var dom = document.getElementById("container");
//    var myChart = echarts.init(dom);
	$("#simulate-routes").on("click",function(){
        $("#simulate-routes").css("display","none");
        $('.pla-btn-switch_line').css('display','none');
        $("#global-routes").css('display','block');
        $(".pla-btn-switch,.pla-btn-flight,.pla-btn-trend").css("display","none");
        $(".pla-btn-national").css("display","block");
        $(".pla-btn-simulate").css("display","block");
	    //关闭其他框框
	    $(".pla-ranking").css({"display":"none"});
	    drawNationalAirline();
	});
	$('.pla-btn-simulate').on('click',function(){
		$('.simulateRoutes').css('display','block');
		airRoutesList();
	});
	$('.simulateRoutes').on('click','.simulateRoute-shut',function(){
		$('.simulateRoutes').css('display','none');
	});
	$('.simulateRoutes').on('click','.listedite',function(){
		//设置本场机场名称
		var childs = $('.simulate-routes').find('li');
		if(childs.length>0){
			var option = '<h5>模拟航线列表</h5>';
			$('.simulate-routes').find('li').each(function(){
				var fltline = $(this).text();
				var citys = fltline.split('=');
				option += '<div class="fltline"><div class="_set-place-duplexing">'
					  +'<div class="_set-name">航线</div>'
					  +'<div class="_set-begin" style="position: relative;">'
					  +'<input placeholder="始发地" class="selectCity c-selectCity c-selectChange cityChoice" type="text" value="'+citys[0]+'">'
					  +'</div>'
					  +'<div class="_set-change">&#xe65d;</div>'
					  +'<div class="_set-begin" style="position: relative;">'
					  +'<input placeholder="到达地" class="selectCity c-selectCity c-selectChange cityChoice2" type="text" value="'+(citys.length==2?citys[1]:citys[2])+'">'
					  +'</div>'
					  +'</div>'
					  +'<div class="_set-place">'
					  +'<div class="_set-name">经停</div>'
					  +'<div class="_set-begin" style="position: relative;">'
					  +'<input placeholder="可选" class="selectCity c-selectCity c-selectChange-stp cityChoice3" type="text" value="'+(citys.length==2?'':citys[1])+'">'
					  +'</div>'
					  +'</div>';
				//添加删除按钮
				option +='<span class="del-btn">&#xe84b;</span></div>';
			});
			//添加确认和取消按钮
			option +='<div class="simulate-list-edit">'
				+'<div class="personal-information-determine information-qe verify-btn">确认</div>'
				+'<div class="personal-information-cancel information-of cancel-btn">取消 </div></div>';
			$('.simulate-route-list').html(option);
			//机场选址控件
		    var objs={
		        back:function(){}
		    };
			airControl(".selectCity",objs);
		}
	});
	$('.simulateRoutes').on('click','.addfltline',function(){
		//设置本场机场名称
		airPortName=airportMap[cityIata].aptChaNam;
		$('.fltlineadd').html('<div class="_set-place-duplexing">'
			  +'<div class="_set-name">航线</div>'
			  +'<div class="_set-begin" style="position: relative;">'
			  +'<input id="cityChoice" placeholder="始发地" class="selectCity c-selectCity c-selectChange" type="text">'
			  +'</div>'
			  +'<div class="_set-change">&#xe65d;</div>'
			  +'<div class="_set-begin" style="position: relative;">'
			  +'<input id="cityChoice2" placeholder="到达地" class="selectCity c-selectCity c-selectChange" type="text">'
			  +'</div>'
			  +'</div>'
			  +'<div class="_set-place">'
			  +'<div class="_set-name">经停</div>'
			  +'<div class="_set-begin" style="position: relative;">'
			  +'<input id="cityChoice3" placeholder="可选" class="selectCity c-selectCity c-selectChange-stp" type="text">'
			  +'</div>'
			  +'</div>'
			  +'<span class="addlineverify">&#xe649;</span>'
			  +'<span class="addlinecancel">&#xe84b;</span>');
		//机场选址控件
	    var objs={
	        back:function(){}
	    };
		airControl(".selectCity",objs);
	});
	$('.simulateRoutes').on('click','.verify-btn',function(){
		var fltLineArray = new Array();
		var flag=false;
		if($('.fltline').length>0){
			airRoutesArrayTemporary=new Array();
			$('.fltline').each(function(){
				var dptAirPort = $(this).find('.cityChoice').val();
				var arrvAirPort = $(this).find('.cityChoice2').val();
				var passAirPort = $(this).find('.cityChoice3').val();
				if(dptAirPort==''||arrvAirPort==''){
					alert('航线起始机场不能为空');
					flag=true;
					return false;
				}
//				if(dptAirPort!=airPortName&&arrvAirPort!=airPortName&&passAirPort!=airPortName){
//					alert('航线中必须要包含本场');
//					flag=true;
//					return false;
//				}
				if(dptAirPort==arrvAirPort||arrvAirPort==passAirPort||passAirPort==dptAirPort){
					alert('航线中机场不能相同');
					flag=true;
					return false;
				}
				//临时列表数据
				if(passAirPort!=''){
					var fltine=dptAirPort+'='+passAirPort+'='+arrvAirPort;
					if($.inArray(fltine,airRoutesArrayTemporary)==-1){
						airRoutesArrayTemporary.push(fltine);
					}
				}else{
					var fltine=dptAirPort+'='+arrvAirPort;
					if($.inArray(fltine,airRoutesArrayTemporary)==-1){
						airRoutesArrayTemporary.push(fltine);
					}
				}
				//获取城市名称
				dptAirPort = airportMap[dptAirPort].ctyChaNam;
				arrvAirPort = airportMap[arrvAirPort].ctyChaNam;
				if(passAirPort!=''){//经停
					passAirPort = airportMap[passAirPort].ctyChaNam;
					var flt1 = dptAirPort+'='+passAirPort;
					var rFlt1 = passAirPort+'='+dptAirPort;
					var flt2 = passAirPort+'='+arrvAirPort;
					var rFlt2 = arrvAirPort+'='+passAirPort;
					if(!($.inArray(flt1,fltLineArray)>-1||$.inArray(rFlt1,fltLineArray)>-1)){
						fltLineArray.push(flt1);
					}
					if(!($.inArray(flt2,fltLineArray)>-1||$.inArray(rFlt2,fltLineArray)>-1)){
						fltLineArray.push(flt2);
					}
				}else{
					var flt1 = dptAirPort+'='+arrvAirPort;
					var rFlt1 = arrvAirPort+'='+dptAirPort;
					if(!($.inArray(flt1,fltLineArray)>-1||$.inArray(rFlt1,fltLineArray)>-1)){
						fltLineArray.push(flt1);
					}
				}
			});
			if(flag){
				return;
			}
			airRoutesArray = airRoutesArrayTemporary;
		}else{
			airRoutesArray =[];
			airRoutesArrayTemporary=[];
		}
		airRoutesList();
		cityCrood=[];
		cityCroods=[];
		if(fltLineArray.length>0){
			for(var i=0;i<fltLineArray.length;i++){
				var flt=fltLineArray[i];
				var citys = flt.split('=');
				var fltArout = {};
				fltArout["fromName"]=citys[0];
				fltArout["toName"]=citys[1];
				var point = {};
				for(var j=0;j<cityCoordinateList.length;j++){
					var city={};
					var key = cityCoordinateList[j].cityName;
					var valuee =cityCoordinateList[j].cityCoordinatee;
					var array = valuee.split(',');
					if(key==citys[0]){
						city["value"]=point["fromName"]=array;
						city["name"]=citys[0];
						cityCrood.push(city);
						if($.inArray(city,cityCrood)==-1){
							cityCrood.push(city);
						}
					}
					if(key==citys[1]){
						city["value"]=point["toName"]=array;
						city["name"]=citys[1];
						cityCrood.push(city);
						if($.inArray(city,cityCrood)==-1){
							cityCrood.push(city);
						}
					}
				}
				fltArout.coords=point;
				cityCroods.push(fltArout);
			}
			cityCroods1=cityCroods;
			cityCrood1=cityCrood;
		}
		//重新画航线图
		drawNationalAirline();
	});
	$('.simulateRoutes').on('click','.cancel-btn',function(){
		airRoutesList();
	});
	$('.simulateRoutes').on('click','.del-btn',function(){
		$(this).parent().remove();
	});
	$('.simulateRoutes').on('click','.addlineverify',function(){
		var dptAirPort = $('#cityChoice').val();
		var arrvAirPort=$('#cityChoice2').val();
		var passAirPort=$('#cityChoice3').val();
		if(dptAirPort==''||arrvAirPort==''){
			alert('航线起始机场不能为空');
			return;
		}
//		if(dptAirPort!=airPortName&&arrvAirPort!=airPortName&&passAirPort!=airPortName){
//			alert('航线中必须要包含本场');
//			return;
//		}
		if(dptAirPort==arrvAirPort||arrvAirPort==passAirPort||passAirPort==dptAirPort){
			alert('航线中机场不能相同');
			return;
		}
		//临时列表数据
		if(passAirPort!=''){
			var fltine=dptAirPort+'='+passAirPort+'='+arrvAirPort;
			if($.inArray(fltine,airRoutesArrayTemporary)==-1){
				airRoutesArrayTemporary.push(fltine);
			}else{
				alert('该航线已经存在');
				return;
			}
		}else{
			var fltine=dptAirPort+'='+arrvAirPort;
			if($.inArray(fltine,airRoutesArrayTemporary)==-1){
				airRoutesArrayTemporary.push(fltine);
			}else{
				alert('该航线已经存在');
				return;
			}
		}
		airRoutesArray = airRoutesArrayTemporary;
		//获取以存在模拟航线的航段
		var fltLineArray = new Array();
		if(airRoutesArray.length>0){
			for(var i=0;i<airRoutesArray.length;i++){
				var citys = airRoutesArray[i].split('=');
				//获取城市名称
				if(citys.length==3){
					dptAirPort = airportMap[citys[0]].ctyChaNam;
					arrvAirPort = airportMap[citys[2]].ctyChaNam;
					passAirPort = airportMap[citys[1]].ctyChaNam;
					var flt1 = dptAirPort+'='+passAirPort;
					var rFlt1 = passAirPort+'='+dptAirPort;
					var flt2 = passAirPort+'='+arrvAirPort;
					var rFlt2 = arrvAirPort+'='+passAirPort;
					if(!($.inArray(flt1,fltLineArray)>-1||$.inArray(rFlt1,fltLineArray)>-1)){
						fltLineArray.push(flt1);
					}
					if(!($.inArray(flt2,fltLineArray)>-1||$.inArray(rFlt2,fltLineArray)>-1)){
						fltLineArray.push(flt2);
					}
				}else{
					dptAirPort = airportMap[citys[0]].ctyChaNam;
					arrvAirPort = airportMap[citys[1]].ctyChaNam;
					var flt1 = dptAirPort+'='+arrvAirPort;
					var rFlt1 = arrvAirPort+'='+dptAirPort;
					if(!($.inArray(flt1,fltLineArray)>-1||$.inArray(rFlt1,fltLineArray)>-1)){
						fltLineArray.push(flt1);
					}
				}
			}
		}
		airRoutesList();
		if(fltLineArray.length>0){
			cityCrood=[];
			cityCroods=[];
			for(var i=0;i<fltLineArray.length;i++){
				var flt=fltLineArray[i];
				var citys = flt.split('=');
				var fltArout = {};
				fltArout["fromName"]=citys[0];
				fltArout["toName"]=citys[1];
				var point = {};
				for(var j=0;j<cityCoordinateList.length;j++){
					var city={};
					var key = cityCoordinateList[j].cityName;
					var valuee =cityCoordinateList[j].cityCoordinatee;
					var array = valuee.split(',');
					if(key==citys[0]){
						city['value']=point["fromName"]=array;
						city['name']=citys[0];
						if($.inArray(city,cityCrood)==-1){
							cityCrood.push(city);
						}
					}
					if(key==citys[1]){
						city['value']=point["toName"]=array;
						city['name']=citys[1];
						cityCrood.push(city);
						if($.inArray(city,cityCrood)==-1){
							cityCrood.push(city);
						}
					}
				}
				fltArout.coords=point;
				cityCroods.push(fltArout);
			}
			cityCroods1=cityCroods;
			cityCrood1=cityCrood;
		}
		//重新画航线图
		drawNationalAirline();
	});
	//批量添加
	$('.simulateRoutes').on('click','.batchAdd',function(){
		$('.fltlineadd').html('<textarea class="batchTextarea"></textarea><div class="simulate-list-edit">'
		+'<div class="personal-information-determine information-qe batchAdd-btn">确认</div>'
		+'<div class="personal-information-cancel information-of batchCancel-btn">取消 </div></div>;');
	});
	$('.simulateRoutes').on('click','.batchAdd-btn',function(){
		var text=$('.batchTextarea').val();
		console.log(text);
		var reg = new RegExp('\n','g');
		//替换换行
		text=text.replace(reg,'');
		//删除空格
		reg=new RegExp(' ','g');
		text=text.replace(reg,'');
		console.log(text);
		var fltArrays = text.split(';');
		airRoutesArrayTemporary=new Array();
		if(fltArrays.length>0){
			for(var i=0;i<fltArrays.length;i++){
				var flt = fltArrays[i];
				if(flt!=''){
					var dptAirPort = '';
					var arrvAirPort='';
					var passAirPort='';
					var citys=flt.split('=');
					if(citys.length==3){
						dptAirPort = airportMap[citys[0].toUpperCase()].ctyChaNam;
						arrvAirPort = airportMap[citys[2].toUpperCase()].ctyChaNam;
						passAirPort = airportMap[citys[1].toUpperCase()].ctyChaNam;
						if(dptAirPort=='undefined'||arrvAirPort=='undefined'||passAirPort=='undefined'){
//							alert('航线起始机场不能为空');
							return;
						}
						if(dptAirPort==arrvAirPort||arrvAirPort==passAirPort||passAirPort==dptAirPort){
//							alert('航线中机场不能相同');
							return;
						}
						flt=airportMap[citys[0].toUpperCase()].aptChaNam+'='+airportMap[citys[1].toUpperCase()].aptChaNam+'='+airportMap[citys[2].toUpperCase()].aptChaNam;
					}else if(citys.length==2){
						dptAirPort = airportMap[citys[0].toUpperCase()].ctyChaNam;
						arrvAirPort = airportMap[citys[1].toUpperCase()].ctyChaNam;
						if(dptAirPort=='undefined'||arrvAirPort=='undefined'){
//							alert('航线起始机场不能为空');
							return;
						}
						if(dptAirPort==arrvAirPort||arrvAirPort==passAirPort||passAirPort==dptAirPort){
//							alert('航线中机场不能相同');
							return;
						}
						flt=airportMap[citys[0].toUpperCase()].aptChaNam+'='+airportMap[citys[1].toUpperCase()].aptChaNam;
					}
					if($.inArray(flt,airRoutesArrayTemporary)==-1){
						airRoutesArrayTemporary.push(flt);
					}
				}
			}
			airRoutesArray = airRoutesArrayTemporary;
			//获取已存在模拟航线的航段
			var fltLineArray = new Array();
			if(airRoutesArray.length>0){
				for(var i=0;i<airRoutesArray.length;i++){
					var citys = airRoutesArray[i].split('=');
					//获取城市名称
					if(citys.length==3){
						var dptAirPort = airportMap[citys[0]].ctyChaNam;
						var arrvAirPort = airportMap[citys[2]].ctyChaNam;
						var passAirPort = airportMap[citys[1]].ctyChaNam;
						var flt1 = dptAirPort+'='+passAirPort;
						var rFlt1 = passAirPort+'='+dptAirPort;
						var flt2 = passAirPort+'='+arrvAirPort;
						var rFlt2 = arrvAirPort+'='+passAirPort;
						if(!($.inArray(flt1,fltLineArray)>-1||$.inArray(rFlt1,fltLineArray)>-1)){
							fltLineArray.push(flt1);
						}
						if(!($.inArray(flt2,fltLineArray)>-1||$.inArray(rFlt2,fltLineArray)>-1)){
							fltLineArray.push(flt2);
						}
					}else{
						var dptAirPort = airportMap[citys[0]].ctyChaNam;
						var arrvAirPort = airportMap[citys[1]].ctyChaNam;
						var flt1 = dptAirPort+'='+arrvAirPort;
						var rFlt1 = arrvAirPort+'='+dptAirPort;
						if(!($.inArray(flt1,fltLineArray)>-1||$.inArray(rFlt1,fltLineArray)>-1)){
							fltLineArray.push(flt1);
						}
					}
				}
			}
			airRoutesList();
			if(fltLineArray.length>0){
				cityCrood=[];
				cityCroods=[];
				for(var i=0;i<fltLineArray.length;i++){
					var flt=fltLineArray[i];
					var citys = flt.split('=');
					var fltArout = {};
					fltArout["fromName"]=citys[0];
					fltArout["toName"]=citys[1];
					var point = {};
					for(var j=0;j<cityCoordinateList.length;j++){
						var city={};
						var key = cityCoordinateList[j].cityName;
						var valuee =cityCoordinateList[j].cityCoordinatee;
						var array = valuee.split(',');
						if(key==citys[0]){
							city['value']=point["fromName"]=array;
							city['name']=citys[0];
							if($.inArray(city,cityCrood)==-1){
								cityCrood.push(city);
							}
						}
						if(key==citys[1]){
							city['value']=point["toName"]=array;
							city['name']=citys[1];
							cityCrood.push(city);
							if($.inArray(city,cityCrood)==-1){
								cityCrood.push(city);
							}
						}
					}
					fltArout.coords=point;
					cityCroods.push(fltArout);
				}
				cityCroods1=cityCroods;
				cityCrood1=cityCrood;
			}
			//重新画航线图
			drawNationalAirline();
		}
	});
	$('.simulateRoutes').on('click','.batchCancel-btn',function(){
		$('.fltlineadd').html('');
	});
	$('.simulateRoutes').on('click','.addlinecancel',function(){
		$('.fltlineadd').html('');
	});
});