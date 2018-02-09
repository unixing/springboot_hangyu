var allData = {};
var currDate =  new Date().format("yyyy-MM-dd");
var currentCheckedMonth = '';
var currCheckedDate = '';
var planTakeOff = '';
var actualTakeOff = '';
var planArrive = '';
var actualArrive = '';
var inState = '';
var outState = '';
var factorsArray = ["天气","航空公司","流量","军事活动","空管","机场","联检","油料","离港系统","旅客","公共安全"];
$(function(){
	//获取用户对航班异常原因编辑权限
	var hasPermission = false;
	var menus1=window.parent.menus1;
	if(menus1!=null&&menus1.length>0){
		for(var i=0;i<menus1.length;i++){
			var menu=menus1[i];
			if(menu.id==14){
				hasPermission=true;
				break;
			}
		}
	}
	
	//调节背景层box-shadow
	$('.pla-supernatant-cont',window.parent.document).css('box-shadow','none');
    //默认使当前日期变红色
    $("._customers-nav-a").on("click",function(){
    	$('._customers-nav-a').each(function(){
    		$(this).removeClass('set');
    	});
    	$(this).addClass('set');
        if($(this).index()==1){
            if($(".sr-box-body-date-head").length==0){
                $("#yj-scroll").remove();
                $(".membder-background").remove();
                scheduledFlight();
            }
        }else if($(this).index()==0){
            if($(".membder-background").length==0){
                $(".sr-box-body-date-head").remove();
                $(".sr-box-body-date-body").remove();
                anomalyStatistics();
            }
        }
    });
    $('body').on('click','._export-table',function(){
		$('._set-gB').removeClass('_set-gB');
    	$(this).addClass('_set-gB');
    });
    $('body').on('click','.hasDataMonth',function(){
    	$('.list').removeClass('list');
    	$(this).addClass('list');
    	currentCheckedMonth = $('#year').text()+'-'+$(this).text().substring(0,$(this).text().indexOf('月'));
    	scheduledFlight();
    });
    $('body').on('click','.hasData',function(){
    	if(currDate==$('.list').attr('abbr')){
    		$('.list').css('color','red');
    	}
    	$('.list').removeClass('list');
    	$(this).addClass('list');
    	currCheckedDate = $(this).attr('abbr');
    	anomalyStatistics();
    });
    $('body').on('click','.remind',function(){
    	if(hasPermission){
    		$('.clickbtn').removeClass('clickbtn');
    		$(this).addClass('clickbtn');
    		$('.select').remove();
    		$('._table-over').after("<div class='select'>" +
    				"<ul>" +
    				"<li><input type='checkbox'>天气 </li>" +
    				"<li> <input type='checkbox'>航空公司 </li>" +
    				"<li> <input type='checkbox'>流量 </li>" +
    				"<li> <input type='checkbox'>军事活动 </li>" +
    				"<li> <input type='checkbox'>空管 </li>" +
    				"<li> <input type='checkbox'>机场 </li>" +
    				"<li> <input type='checkbox'>联检 </li>" +
    				"<li> <input type='checkbox'>油料</li>" +
    				"<li> <input type='checkbox'>离港系统 </li>" +
    				"<li> <input type='checkbox'>旅客 </li>" +
    				"<li> <input type='checkbox'>公共安全 </li>" +
    				"</ul>" +
    				"<div class='decision'>" +
    				"<span class='decision-t' title='确定' onclick='getFactors()'>&#xe649;</span>" +
    				"<span class='decision-f' title='取消' onclick='closeLayer()'>&#xe84b;</span>" +
    		"</div> </div>");
    	}
    });
    $('body').on('click','.abnormal-tag',function(){
    	if(hasPermission){
    		$('.clickbtn').removeClass('clickbtn');
    		$(this).addClass('clickbtn');
    		$('.select').remove();
    		var abnormalFactors = $(this).attr('factors').split('|');
    		var option = "<div class='select'><ul>";
    		for(var i=0;i<abnormalFactors.length;i++){
    			option += "<li><span>"+(i+1)+".</span>"+abnormalFactors[i]+"</li>";
    		}
    		option += "</ul>";
    		option += "<div class='decision'> <span class='decision-set' title='编辑' onclick='editFactors()'>&#xe613;</span><span class='decision-f' title='取消' onclick='closeLayer()' style='position: relative; top: -10px; font-size: 14px;'>&#xe84b;</span></div></div>";
    		$('._table-over').after(option);
    	}
    });
    $('body').on('click','._sequence',function(){
    	var content = $(this).parent().text();
    	if(content.indexOf('计划起飞')>-1){
    		if(planTakeOff==''||planTakeOff=='DESC'){
    			planTakeOff = 'ASC';
    		}else{
    			planTakeOff = 'DESC';
    		}
    		sortData('plan_q',planTakeOff);
    	}else if(content.indexOf('计划到达')>-1){
    		if(planArrive==''||planArrive=='DESC'){
    			planArrive = 'ASC';
    		}else{
    			planArrive = 'DESC';
    		}
    		sortData('plan_c',planArrive);
    	}else if(content.indexOf('实际起飞')>-1){
    		if(actualTakeOff==''||actualTakeOff=='DESC'){
    			actualTakeOff = 'ASC';
    		}else{
    			actualTakeOff = 'DESC';
    		}
    		sortData('actual_q',actualTakeOff);
    	}else if(content.indexOf('实际到达')>-1){
    		if(actualArrive==''||actualArrive == 'DESC'){
    			actualArrive = 'ASC';
    		}else{
    			actualArrive = 'DESC';
    		}
    		sortData('actual_c',actualArrive);
    	}
    });
    anomalyStatistics();
});
function loadWeather(ioca){
	$.ajax({
		url:'/getAirportWeather',
		type:'get',
		data:{
			ioca:ioca
		},
		success:function(data){
			if(data!=null&&data!=''){
				var weatherInfo =  data.weatherInfo;
				var ss = weatherInfo.split(" ");
				metar_decode(weatherInfo);
				$("#weather .ptxt").html(op_text);
//				var oo = $("#weather").html();
//				oo.replace(/摄氏度/g,"°C");
//				oo.replace(/度/g,"°");
//				oo.replace(/米每秒/g,"m/s");
//				oo.replace(/米/g,"m");
//				oo.replace(/百帕/g,"°hPa");
//				oo.replace(/英寸汞柱/g,"inHg");
//				$("#weather").html(oo);
			}
		}
	})
}
function editFactors(){
	var obj = $('.clickbtn');
	var factors = obj.attr('factors').split("|");
	$('.select').remove();
	var option = "<div class='select'><ul>";
	for(var i=0;i<11;i++){
		var flag = false;
		for(var j=0;j<factors.length;j++){
			if(factors[j]==factorsArray[i]){
				flag = true;
				break;
			}
		}
		if(flag == true){
			option += "<li><input type='checkbox' checked='checked'>"+factorsArray[i]+"</li>";
		}else{
			option += "<li><input type='checkbox'>"+factorsArray[i]+"</li>";
		}
	}
	option += "</ul><div class='decision'>" +
	"<span class='decision-t' title='确定' onclick='getFactors()'>&#xe649;</span>" +
	"<span class='decision-f' title='取消' onclick='closeLayer()'>&#xe84b;</span>" +
	"</div> </div>";
	$('._table-over').after(option);
}
function closeLayer(){
	$('.clickbtn').removeClass('clickbtn');
	$('.select').remove();
}
function getFactors(){
	var obj = $('.clickbtn');
	var obj1 = $('.clickbtn').prev();
	var lis = $('.select input[type="checkbox"]');
	$('.select').remove();
	var abbr = '';
	for(var i=0;i<lis.length;i++){
		if(lis[i].checked==true){
			abbr += abbr==''?factorsArray[i]:'|'+factorsArray[i];
		}
	}
	obj.attr('factors',abbr);
	var id= obj.attr('ids');
	obj.remove();
	if(abbr==''){
		obj1.after("<div class='remind' ids="+id+">&#xe64a;</div>");
	}else{
		obj1.after("<div class='abnormal-tag' factors='"+abbr+"' ids="+id+"></div>");
	}
	$.ajax({
		url:'/airline_dynameic_save',
		type:'post',
		data:{
			index:id,
			sp:abbr
		},
		async:false,
		success:function(data){
			if(data!=null&&data!=''){
				console.log(data);
			}else{
				alert('保存失败');
			}
	    	$('.clickbtn').removeClass('clickbtn');
		}
	});
}

function sorts(){
	var testArray = [{state:'正常'},{state:'取消'},{state:'可能取消'},{state:'备降'},{state:'备降后取消'},{state:'延误'},{state:'提前取消'},{state:'起飞'},{state:'返航'}];
	testArray = testArray.sort(
			function (a,b){ 
				if(a.state < b.state)
					return -1;  
				if(a.state > b.state)
					return 1;  
				return 0;
            }
	);
	console.log(testArray);
}

function sortData(field,sortType){
	var type = $('._set-gB').text();
	if(type=='进港'){
		if(field=='state'){
			if(sortType=='ASC'){
				if(allData!=null&&allData.list!=null&&allData.list.length>0){
					var data = allData.list[0];
					$('._time-body').remove();
					option = '';
					if(data!=null&&data.length>0){
						data = data.sort(
							function(a,b){
								if(a.state > b.state) 
									return -1;
								if(a.state < b.state) 
									return 1;
								return 0;
							}
						);
						option += '<div class="_time-body"> <div class="_table-title"><ul>';
//						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
						option += ' </ul></div>';
						option += '<div class="_table-box"><div class="_table-over">';
						for(var i=0;i<data.length;i++){
							var obj = data[i];
							option += "<ul>";
							option += "<li>"+obj.flt_nbr+"</li>";
							option += "<li>"+obj.dpt_AirPt_Cd+"</li>";
							option += "<li>"+obj.arrv_Airpt_Cd+"</li>";
							option += "<li>"+obj.plan_c+"</li>";
							option += "<li>"+obj.actual_c+"</li>";
							if(obj.state=="正常"){
								option += "<li>"+obj.state+"</li>";
							}else{
								if(obj.remark!=null&&obj.remark!=''){
									option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='abnormal-tag' factors='"+obj.remark+"' ids="+obj.id+"></div></li>";
								}else{
									option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='remind' ids="+obj.id+">&#xe64a;</div></li>";
								}
							}
							option += "<li>"+obj.zd_rate+"</li>";
							option += "</ul>";
						}
						option += '</div> </div> </div>';
					}else{
						option += '<div class="_time-body"> <div class="_table-title"><ul>';
//						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
						option += ' </ul></div>';
						option += '<div class="_table-box"><div class="_table-over">';
						option += "<ul>";
						option += "<li style='width:100%;'>未查询到进港数据</li>";
						option += "</ul>";
						option += '</div> </div> </div>';
					}
					$('._customers-box').append(option);
					reset();
					if(typeof(data)!='undefined'&&data!=null&&data.length>9){
						add("._customers","._table-over",15,100,"._customers","._table-box");
					}else{
						var scoll = $(".sr-box-body").find('#yj-scroll')[0];
						if(typeof(scoll)!='undefined'){
							$(scoll).remove();
						}
						$('._time-body').css({'height':(60+47*((typeof(data)=='undefined'||data==null||data.length==0)?1:data.length))+'px'});
					}
				}
			}else{
				if(allData!=null&&allData.list!=null&&allData.list.length>9){
					var data = allData.list[0];
					$('._time-body').remove();
					option = '';
					if(data!=null&&data.length>0){
						data = data.sort(
							function(a,b){
								if(a.state > b.state) 
									return 1;
								if(a.state < b.state) 
									return -1;
								return 0;
							}
						);
						option += '<div class="_time-body"> <div class="_table-title"><ul>';
//						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
						option += ' </ul></div>';
						option += '<div class="_table-box"><div class="_table-over">';
						for(var i=0;i<data.length;i++){
							var obj = data[i];
							option += "<ul>";
							option += "<li>"+obj.flt_nbr+"</li>";
							option += "<li>"+obj.dpt_AirPt_Cd+"</li>";
							option += "<li>"+obj.arrv_Airpt_Cd+"</li>";
							option += "<li>"+obj.plan_c+"</li>";
							option += "<li>"+obj.actual_c+"</li>";
							if(obj.state=="正常"){
								option += "<li>"+obj.state+"</li>";
							}else{
								if(obj.remark!=null&&obj.remark!=''){
									option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='abnormal-tag' factors='"+obj.remark+"' ids="+obj.id+"></div></li>";
								}else{
									option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='remind' ids="+obj.id+">&#xe64a;</div></li>";
								}
							}
							option += "<li>"+obj.zd_rate+"</li>";
							option += "</ul>";
						}
						option += '</div> </div> </div>';
					}else{
						option += '<div class="_time-body"> <div class="_table-title"><ul>';
//						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
						option += ' </ul></div>';
						option += '<div class="_table-box"><div class="_table-over">';
						option += "<ul>";
						option += "<li style='width:100%;'>未查询到进港数据</li>";
						option += "</ul>";
						option += '</div> </div> </div>';
					}
					$('._customers-box').append(option);
					reset();
					if(typeof(data)!='undefined'&&data!=null&&data.length>9){
						add("._customers","._table-over",15,100,"._customers","._table-box");
					}else{
						var scoll = $(".sr-box-body").find('#yj-scroll')[0];
						if(typeof(scoll)!='undefined'){
							$(scoll).remove();
						}
						$('._time-body').css({'height':(60+47*((typeof(data)=='undefined'||data==null||data.length==0)?1:data.length))+'px'});
					}
				}
			}
		}else{
			$.ajax({
				url:'/airline_dynameic_list_in',
				data:{
					date:currCheckedDate,
					field:field,
					sortType:sortType
				},
				type:'post',
				async:false,
				success:function(data){					
					$('._time-body').remove();
					option = '';
					if(data!=null&&data.data!=null){
						data = data.data;
						if(data!=null&&data.length>0){
							option += '<div class="_time-body"> <div class="_table-title"><ul>';
//							option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
							option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
							option += ' </ul></div>';
							option += '<div class="_table-box"><div class="_table-over">';
							for(var i=0;i<data.length;i++){
								var obj = data[i];
								option += "<ul>";
								option += "<li>"+obj.flt_nbr+"</li>";
								option += "<li>"+obj.dpt_AirPt_Cd+"</li>";
								option += "<li>"+obj.arrv_Airpt_Cd+"</li>";
								option += "<li>"+obj.plan_c+"</li>";
								option += "<li>"+obj.actual_c+"</li>";
								if(obj.state=="正常"){
									option += "<li>"+obj.state+"</li>";
								}else{
									if(obj.remark!=null&&obj.remark!=''){
										option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='abnormal-tag' factors='"+obj.remark+"' ids="+obj.id+"></div></li>";
									}else{
										option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='remind' ids="+obj.id+">&#xe64a;</div></li>";
									}
								}
								option += "<li>"+obj.zd_rate+"</li>";
								option += "</ul>";
							}
							option += '</div> </div> </div>';
						}else{
							option += '<div class="_time-body"> <div class="_table-title"><ul>';
//							option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
							option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
							option += ' </ul></div>';
							option += '<div class="_table-box"><div class="_table-over">';
							option += "<ul>";
							option += "<li style='width:100%;'>未查询到进港数据</li>";
							option += "</ul>";
							option += '</div> </div> </div>';
						}
						$('._customers-box').append(option);
						reset();
						if(typeof(data)!='undefined'&&data!=null&&data.length>9){
							add("._customers","._table-over",15,100,"._customers","._table-box");
						}else{
							var scoll = $(".sr-box-body").find('#yj-scroll')[0];
							if(typeof(scoll)!='undefined'){
								$(scoll).remove();
							}
							$('._time-body').css({'height':(60+47*((typeof(data)=='undefined'||data==null||data.length==0)?1:data.length))+'px'});
						}
					}
				}
			});
		}
	}else{
		if(field=='state'){
			if(sortType=='ASC'){
				if(allData!=null&&allData.list!=null&&allData.list.length>0){
					var data = allData.list[1];
					$('._time-body').remove();
					option = '';
					if(data!=null&&data.length>0){
						data = data.sort(
							function(a,b){
								if(a.state > b.state) 
									return -1;
								if(a.state < b.state) 
									return 1;
								return 0;
							}
						);
						option += '<div class="_time-body"> <div class="_table-title"><ul>';
//						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
						option += ' </ul></div>';
						option += '<div class="_table-box"><div class="_table-over">';
						for(var i=0;i<data.length;i++){
							var obj = data[i];
							option += "<ul>";
							option += "<li>"+obj.flt_nbr+"</li>";
							option += "<li>"+obj.dpt_AirPt_Cd+"</li>";
							option += "<li>"+obj.arrv_Airpt_Cd+"</li>";
							option += "<li>"+obj.plan_q+"</li>";
							option += "<li>"+obj.actual_q+"</li>";
							if(obj.state=="正常"){
								option += "<li>"+obj.state+"</li>";
							}else{
								if(obj.remark!=null&&obj.remark!=''){
									option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='abnormal-tag' factors='"+obj.remark+"' ids="+obj.id+"></div></li>";
								}else{
									option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='remind' ids="+obj.id+">&#xe64a;</div></li>";
								}
							}
							option += "<li>"+obj.zd_rate+"</li>";
							option += "</ul>";
						}
						option += '</div> </div> </div>';
					}else{
						option += '<div class="_time-body"> <div class="_table-title"><ul>';
//						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
						option += ' </ul></div>';
						option += '<div class="_table-box"><div class="_table-over">';
						option += "<ul>";
						option += "<li style='width:100%;'>未查询到进港数据</li>";
						option += "</ul>";
						option += '</div> </div> </div>';
					}
					$('._customers-box').append(option);
					reset();
					if(typeof(data)!='undefined'&&data!=null&&data.length>9){
						add("._customers","._table-over",15,100,"._customers","._table-box");
					}else{
						var scoll = $(".sr-box-body").find('#yj-scroll')[0];
						if(typeof(scoll)!='undefined'){
							$(scoll).remove();
						}
						$('._time-body').css({'height':(60+47*((typeof(data)=='undefined'||data==null||data.length==0)?1:data.length))+'px'});
					}
				}
			}else{
				if(allData!=null&&allData.list!=null&&allData.list.length>0){
					var data = allData.list[1];
					$('._time-body').remove();
					option = '';
					if(data!=null&&data.length>0){
						data = data.sort(
							function(a,b){
								if(a.state > b.state) 
									return 1;
								if(a.state < b.state) 
									return -1;
								return 0;
							}
						);
						option += '<div class="_time-body"> <div class="_table-title"><ul>';
//						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
						option += ' </ul></div>';
						option += '<div class="_table-box"><div class="_table-over">';
						for(var i=0;i<data.length;i++){
							var obj = data[i];
							option += "<ul>";
							option += "<li>"+obj.flt_nbr+"</li>";
							option += "<li>"+obj.dpt_AirPt_Cd+"</li>";
							option += "<li>"+obj.arrv_Airpt_Cd+"</li>";
							option += "<li>"+obj.plan_q+"</li>";
							option += "<li>"+obj.actual_q+"</li>";
							if(obj.state=="正常"){
								option += "<li>"+obj.state+"</li>";
							}else{
								if(obj.remark!=null&&obj.remark!=''){
									option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='abnormal-tag' factors='"+obj.remark+"' ids="+obj.id+"></div></li>";
								}else{
									option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='remind' ids="+obj.id+">&#xe64a;</div></li>";
								}
							}
							option += "<li>"+obj.zd_rate+"</li>";
							option += "</ul>";
						}
						option += '</div> </div> </div>';
					}else{
						option += '<div class="_time-body"> <div class="_table-title"><ul>';
//						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
						option += ' </ul></div>';
						option += '<div class="_table-box"><div class="_table-over">';
						option += "<ul>";
						option += "<li style='width:100%;'>未查询到进港数据</li>";
						option += "</ul>";
						option += '</div> </div> </div>';
					}
					$('._customers-box').append(option);
					reset();
					if(typeof(data)!='undefined'&&data!=null&&data.length>9){
						add("._customers","._table-over",15,100,"._customers","._table-box");
					}else{
						var scoll = $(".sr-box-body").find('#yj-scroll')[0];
						if(typeof(scoll)!='undefined'){
							$(scoll).remove();
						}
						$('._time-body').css({'height':(60+47*((typeof(data)=='undefined'||data==null||data.length==0)?1:data.length))+'px'});
					}
				}
			}
		}else{
			$.ajax({
				url:'/airline_dynameic_list_out',
				data:{
					date:currCheckedDate,
					field:field,
					sortType:sortType
				},
				type:'post',
				async:false,
				success:function(data){
					$('._time-body').remove();
					option = '';
					if(data!=null&&data.data!=null){
						data = data.data;
						if(data!=null&&data.length>0){
							option += '<div class="_time-body"> <div class="_table-title"><ul> ';
//							option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li><li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">状态<span class="_sequence">&#xe675;</span></li><li class="_ordinary">准点率</li>';
							option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li><li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">状态</li><li class="_ordinary">准点率</li>';
							option += ' </ul></div>';
							option += '<div class="_table-box"><div class="_table-over">';
							for(var i=0;i<data.length;i++){
								var obj = data[i];
								option += "<ul>";
								option += "<li>"+obj.flt_nbr+"</li>";
								option += "<li>"+obj.dpt_AirPt_Cd+"</li>";
								option += "<li>"+obj.arrv_Airpt_Cd+"</li>";
								option += "<li>"+obj.plan_q+"</li>";
								option += "<li>"+obj.actual_q+"</li>";
								if(obj.state=="正常"){
									option += "<li>"+obj.state+"</li>";
								}else{
									if(obj.remark!=null&&obj.remark!=''){
										option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='abnormal-tag' factors='"+obj.remark+"' ids="+obj.id+"></div></li>";
									}else{
										option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='remind' ids="+obj.id+">&#xe64a;</div></li>";
									}
								}
								option += "<li>"+obj.zd_rate+"</li>";
								option += "</ul>";
							}
							option += '</div> </div> </div>';
						}else{
							option += '<div class="_time-body"> <div class="_table-title"><ul> ';
//							option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li><li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">状态<span class="_sequence">&#xe675;</span></li><li class="_ordinary">准点率</li>';
							option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li><li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">状态</li><li class="_ordinary">准点率</li>';
							option += ' </ul></div>';
							option += '<div class="_table-box"><div class="_table-over">';
							option += "<ul>";
							option += "<li style='width:100%;'>未查询到出港数据</li>";
							option += "</ul>";
							option += '</div> </div> </div>';
						}
						$('._customers-box').append(option);
						reset();
						if(typeof(data)!='undefined'&&data!=null&&data.length>9){
							add("._customers","._table-over",15,100,"._customers","._table-box");
						}else{
							var scoll = $(".sr-box-body").find('#yj-scroll')[0];
							if(typeof(scoll)!='undefined'){
								$(scoll).remove();
							}
							$('._time-body').css({'height':(60+47*((typeof(data)=='undefined'||data==null||data.length==0)?1:data.length))+'px'});
						}
					}
				}
			});
		}
	}
}
function addmonth(){
	var achieveMonth = parseInt($('.achieve-month').text());
	if(achieveMonth==12){
		$('.achieve-month').text(1);
		achieveMonth = $('.achieve-year').text()+'-01-';
	}else{
		$('.achieve-month').text(achieveMonth+1);
		if(achieveMonth<9){
			achieveMonth = $('.achieve-year').text()+'-0'+(achieveMonth+1)+'-';
		}else{
			achieveMonth = $('.achieve-year').text()+'-'+(achieveMonth+1)+'-';
		}
	}
	addE(achieveMonth);
}
function subtractmonth(){
	var achieveMonth = parseInt($('.achieve-month').text());
	if(achieveMonth==1){
		$('.achieve-month').text(12);
		achieveMonth = $('.achieve-year').text()+'-12-';
	}else{
		$('.achieve-month').text(achieveMonth-1);
		if(achieveMonth<10){
			achieveMonth = $('.achieve-year').text()+'-0'+(achieveMonth-1)+'-';
		}else{
			achieveMonth = $('.achieve-year').text()+'-'+(achieveMonth-1)+'-';
		}
	}
	addE(achieveMonth);
}
function addyear(){
	var achieveYear = parseInt($('.achieve-year').text());
	$('.achieve-year').text(achieveYear+1);
	var achieveMonth = parseInt($('.achieve-month').text());
	if(achieveMonth<10){
		achieveYear = (achieveYear+1)+'-0'+achieveMonth+'-';
	}else{
		achieveYear = (achieveYear+1)+'-'+achieveMonth+'-';
	}
	addE(achieveYear);
}
function subtractyear(){
	var achieveYear = parseInt($('.achieve-year').text());
	$('.achieve-year').text(achieveYear-1);
	var achieveMonth = parseInt($('.achieve-month').text());
	if(achieveMonth<10){
		achieveYear = (achieveYear+1)+'-0'+achieveMonth+'-';
	}else{
		achieveYear = (achieveYear+1)+'-'+achieveMonth+'-';
	}
	addE(achieveYear);
}
function infer(name){
    var infer=[];
    infer.push(parseFloat($(name).css("width").split("px")[0]));
    infer.push(parseFloat($(name).css("height").split("px")[0]));
    infer.push(parseFloat($(name).css("margin-top").split("px")[0]));
    infer.push(parseFloat($(name).css("left").split("px")[0]));
    infer.push(parseFloat($(name).css("top").split("px")[0]));
    return infer;
}
function reset(){
    $("._customers").css({"height":(infer(".sr-box")[1]-infer(".sr-box-head")[1]+"px")});
    $("._customers-box").css({"width":(infer("._customers")[0]-infer("._customers-nav")[0]-infer(".sr-box-body-date")[0]-5)});
    if($("._customers-nav-a").length==1){
        $("._customers-nav-a").css("height","100%");
    }else if($("._customers-nav-a").length==3){
        $("._customers-nav-a").css("height","33.33%");
    }
}
function add(idL,idE,speed,sheight,cleN,KHB){
    if($("#yj-scroll").length==0){
        $("._customers-box").append("<div id='yj-scroll'></div>");
    }
    $("#yj-scroll").css({"border-radius":"2px","cursor":"pointer","position":"absolute","width":"8px","right":"2px","top":"0px","z-index":"999","height":sheight+"px","background-color":"#79b2df"});
    $(idL).on("mousewheel",function(e,delta){
        if(typeof($(idL).find('#yj-scroll')[0])!='undefined'){
        	if(delta=="1") {//上滑
                $(idE).css({"top":"+="+speed+"px"});
                if(infer(idE)[4]>=0){
                    $(idE).css({"top":"0px"});
                }
                var T=(infer(cleN)[1]-infer("#yj-scroll")[1])*infer(idE)[4]/(infer(KHB)[1]-infer(idE)[1]);
                $("#yj-scroll").css("top",T+"px");
            }else { //下滑
                $(idE).css({"top":"-="+speed+"px"});
                if(infer(idE)[4]<=-(infer(idE)[1]-infer(KHB)[1])){
                    $(idE).css({"top":-(infer(idE)[1]-infer(KHB)[1])+"px"});
                }
                var T=(infer(cleN)[1]-infer("#yj-scroll")[1])*infer(idE)[4]/(infer(KHB)[1]-infer(idE)[1]);
                $("#yj-scroll").css("top",T+"px");
            }
        }
    });
    $("#yj-scroll").on("mousedown",function(b){
        var H=b.screenY;
        var ct=infer("#yj-scroll")[4];
        $(".sr-box").mousemove(function(e){
            var top= e.screenY;
            if(H>top){
                var tops=ct-Math.abs(H-top);
                if(tops<=0){
                    $("#yj-scroll").css("top","0px");
                }else {
                    $("#yj-scroll").css("top",tops+"px");
                }
                var xt=(infer(KHB)[1]-infer(idE)[1])*infer("#yj-scroll")[4]/(infer(cleN)[1]-infer("#yj-scroll")[1]);
                $("._table-over").css("top",xt+"px");
            }else {
                var tops=ct+Math.abs(H-top);
                if(tops>=(infer(idL)[1]-infer("#yj-scroll")[1])){
                    $("#yj-scroll").css("top",(infer(idL)[1]-infer("#yj-scroll")[1])+"px");
                }else {
                    $("#yj-scroll").css("top",tops+"px");
                }
                var xt=(infer(KHB)[1]-infer(idE)[1])*infer("#yj-scroll")[4]/(infer(cleN)[1]-infer("#yj-scroll")[1]);
                $("._table-over").css("top",xt+"px");
            }
        });
    });
    $(".sr-box").on("mouseup",function(){
        $(".sr-box").unbind("mousemove");
    });
}

function inOutSwitch(type){
	var currData = allData.list[type];
	$('._time-body').remove();
	option = '';
	if(type==0){
		if(currData!=null&&currData.length>0){
			option += '<div class="_time-body"> <div class="_table-title"><ul>';
//			option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
			option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
			option += ' </ul></div>';
			option += '<div class="_table-box"><div class="_table-over">';
			for(var i=0;i<currData.length;i++){
				var obj = currData[i];
				option += "<ul>";
				option += "<li>"+obj.flt_nbr+"</li>";
				option += "<li>"+obj.dpt_AirPt_Cd+"</li>";
				option += "<li>"+obj.arrv_Airpt_Cd+"</li>";
				option += "<li>"+obj.plan_c+"</li>";
				option += "<li>"+obj.actual_c+"</li>";
				if(obj.state=="正常"){
					option += "<li>"+obj.state+"</li>";
				}else{
					if(obj.remark!=null&&obj.remark!=''){
						option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='abnormal-tag' factors='"+obj.remark+"' ids="+obj.id+"></div></li>";
					}else{
						option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='remind' ids="+obj.id+">&#xe64a;</div></li>";
					}
				}
				option += "<li>"+obj.zd_rate+"</li>";
				option += "</ul>";
			}
			option += '</div> </div> </div>';
		}else{
			option += '<div class="_time-body"> <div class="_table-title"><ul>';
//			option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
			option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
			option += ' </ul></div>';
			option += '<div class="_table-box"><div class="_table-over">';
			option += "<ul>";
			option += "<li style='width:100%;'>未查询到进港数据</li>";
			option += "</ul>";
			option += '</div> </div> </div>';
		}
		$('._customers-box').append(option);
		reset();
		if(typeof(currData)!='undefined'&&currData!=null&&currData.length>9){
			add("._customers","._table-over",15,100,"._customers","._table-box");
		}else{
			var scoll = $(".sr-box-body").find('#yj-scroll')[0];
			if(typeof(scoll)!='undefined'){
				$(scoll).remove();
			}
			$('._time-body').css({'height':(60+47*((typeof(currData)=='undefined'||currData==null||currData.length==0)?1:currData.length))+'px'});
		}
	}else if(type==1){
		if(currData!=null&&currData.length>0){
			option += '<div class="_time-body"> <div class="_table-title"><ul> ';
//			option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li><li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">状态<span class="_sequence">&#xe675;</span></li><li class="_ordinary">准点率</li>';
			option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li><li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">状态</li><li class="_ordinary">准点率</li>';
			option += ' </ul></div>';
			option += '<div class="_table-box"><div class="_table-over">';
			for(var i=0;i<currData.length;i++){
				var obj = currData[i];
				option += "<ul>";
				option += "<li>"+obj.flt_nbr+"</li>";
				option += "<li>"+obj.dpt_AirPt_Cd+"</li>";
				option += "<li>"+obj.arrv_Airpt_Cd+"</li>";
				option += "<li>"+obj.plan_q+"</li>";
				option += "<li>"+obj.actual_q+"</li>";
				if(obj.state=="正常"){
					option += "<li>"+obj.state+"</li>";
				}else{
					if(obj.remark!=null&&obj.remark!=''){
						option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='abnormal-tag' factors='"+obj.remark+"' ids="+obj.id+"></div></li>";
					}else{
						option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='remind' ids="+obj.id+">&#xe64a;</div></li>";
					}
				}
				option += "<li>"+obj.zd_rate+"</li>";
				option += "</ul>";
			}
			option += '</div> </div> </div>';
		}else{
			option += '<div class="_time-body"> <div class="_table-title"><ul> ';
//			option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li><li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">状态<span class="_sequence">&#xe675;</span></li><li class="_ordinary">准点率</li>';
			option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li><li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">状态</li><li class="_ordinary">准点率</li>';
			option += ' </ul></div>';
			option += '<div class="_table-box"><div class="_table-over">';
			option += "<ul>";
			option += "<li style='width:100%;'>未查询到出港数据</li>";
			option += "</ul>";
			option += '</div> </div> </div>';
		}
		$('._customers-box').append(option);
		reset();
		if(typeof(currData)!='undefined'&&currData!=null&&currData.length>9){
			add("._customers","._table-over",15,100,"._customers","._table-box");
		}else{
			var scoll = $(".sr-box-body").find('#yj-scroll')[0];
			if(typeof(scoll)!='undefined'){
				$(scoll).remove();
			}
			$('._time-body').css({'height':(60+47*((typeof(currData)=='undefined'||currData==null||currData.length==0)?1:currData.length))+'px'});
		}
	}
}

function anomalyStatistics(){
	var date = $('.list').attr('abbr');
	var type = $('._set-gB').text();
	$.ajax({
		url:'/airline_dynameic_list',
		type:'post',
		data:{
			date:date
		},
//		async:false,
		success:function(data){
        	$("#TM-sloading").hide('normal');
			$('._customers-box').empty();
			$("._customers-box").removeClass("muhu");
			$(".reportErr").css("display","none");
			if(typeof(data.indates)!='undefined'&&data.indates!=null&&data.indates.length>0){
				var indates = data.indates;
				allData.indates=indates;
				var lastDate = indates[indates.length-1];
				if((typeof(type)=='undefined'||type==''||type=="进港")&&$('.addMemo-body').length==0){
					currCheckedDate = lastDate;
					var dateMonth = lastDate.split('-');
					$(".air-weather").before("<div class='membder-background addMemo-body'> <div class='choose'> <h3 class='choose-title'>航班动态</h3> <div class='choose-set'> <div> <div class='choose-font-top choose-year-top'>&#xe663;</div> <div class='choose-time achieve-year'>"+dateMonth[0]+"</div> <div class='choose-font-bottom choose-year-bottom'>&#xe664;</div> </div> <div>年 </div> <div> <div class='choose-font-top choose-month-top'>&#xe663;</div> <div class='choose-time achieve-month'>"+parseInt(dateMonth[1])+"</div> <div class='choose-font-bottom choose-month-bottom'>&#xe664;</div> </div> </div> </div> <div class='calendar'></div> </div>");
					addE(lastDate);
					var j=0;
					var length = indates.length;
					$('.this-month').each(function(){
						var curr = $(this).attr('abbr');
						for(var i=j;i<length;i++){
							if(curr==indates[i]){
								$(this).addClass('hasData');
								j=i;
								if(i==length-1){
									$(this).addClass('list');
								}
								break;
							}
						}
						if(curr==currDate){
							$(this).css('color','red');
						}
					});
				}
			}else{
				if($('.addMemo-body').length==0){
					var dateMonth = currDate.split('-');
					$(".air-weather").before("<div class='membder-background addMemo-body'> <div class='choose'> <h3 class='choose-title'>航班动态</h3> <div class='choose-set'> <div> <div class='choose-font-top choose-year-top'>&#xe663;</div> <div class='choose-time achieve-year'>"+dateMonth[0]+"</div> <div class='choose-font-bottom choose-year-bottom'>&#xe664;</div> </div> <div>年 </div> <div> <div class='choose-font-top choose-month-top'>&#xe663;</div> <div class='choose-time achieve-month'>"+parseInt(dateMonth[1])+"</div> <div class='choose-font-bottom choose-month-bottom'>&#xe664;</div> </div> </div> </div> <div class='calendar'></div> </div>");
					addE(currDate);
					$('.this-month').each(function(){
						var curr = $(this).attr('abbr');
						if(curr==currDate){
							$(this).css('color','red');
						}
					});
				}
			}
//			if(typeof(data.outdates)!='undefined'&&data.outdates!=null&&data.outdates.length>0){
//				var outdates = data.outdates;
//				allData.outdates = outdates;
//				var lastDate = outdates[outdates.length-1];
//				if(type=="出港"){
//					currCheckedDate = lastDate;
//					var dateMonth = lastDate.split('-');
//					$(".air-weather").before("<div class='membder-background addMemo-body'> <div class='choose'> <h3 class='choose-title'>航班动态</h3> <div class='choose-set'> <div> <div class='choose-font-top choose-year-top'>&#xe663;</div> <div class='choose-time achieve-year'>"+dateMonth[0]+"</div> <div class='choose-font-bottom choose-year-bottom'>&#xe664;</div> </div> <div>年 </div> <div> <div class='choose-font-top choose-month-top'>&#xe663;</div> <div class='choose-time achieve-month'>"+parseInt(dateMonth[1])+"</div> <div class='choose-font-bottom choose-month-bottom'>&#xe664;</div> </div> </div> </div> <div class='calendar'></div> </div>");
//					addE(lastDate);
//					var j=0;
//					var length = outdates.length;
//					$('.this-month').each(function(){
//						for(var i=j;i<length;i++){
//							var curr = $(this).attr('abbr');
//							if(curr==outdates[i]){
//								$(this).addClass('hasData');
//								j=i;
//								if(i==length-1){
//									$(this).addClass('list');
//								}
//								break;
//							}
//						}
//					});
//				}
//			}
			if(data!=null&&data.list!=null&&data.list.length>0){
				allData.list = data.list;
				var list = data.list;
				var flag = true;
				if(list[0].length>0||list[1].length>0){
					flag = false;
				}
				var option = '';
				if(typeof(type)=='undefined'||type==''||type=="进港"){
					$('._customers-box').append("<div class='time-q-box'>" +
							"<div class='_time-head'>" +
							"<ul class='_time-head-box'>" +
							"<li class='_time-head-title'>航班动态列表</li>" +
							"<li class='_time-head-set'>" +
							"<div class='_export-table _set-gB' onclick='inOutSwitch(0)'>进港</div>" +
							"<div class='_export-table' onclick='inOutSwitch(1)'>出港</div>" +
							"</li></ul></div></div>");
					var arrvPort = list[0];
					if(arrvPort!=null&&arrvPort.length>0){
						option += '<div class="_time-body"> <div class="_table-title"><ul>';
//						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li> <li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li> <li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
						option += ' </ul></div>';
						option += '<div class="_table-box"><div class="_table-over">';
						for(var i=0;i<arrvPort.length;i++){
							var obj = arrvPort[i];
							option += "<ul>";
							option += "<li>"+obj.flt_nbr+"</li>";
							option += "<li>"+obj.dpt_AirPt_Cd+"</li>";
							option += "<li>"+obj.arrv_Airpt_Cd+"</li>";
							option += "<li>"+obj.plan_c+"</li>";
							option += "<li>"+obj.actual_c+"</li>";
							if(obj.state=="正常"){
								option += "<li>"+obj.state+"</li>";
							}else{
								if(obj.remark!=null&&obj.remark!=''){
									option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='abnormal-tag' factors='"+obj.remark+"' ids="+obj.id+"></div></li>";
								}else{
									option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='remind' ids="+obj.id+">&#xe64a;</div></li>";
								}
							}
							option += "<li>"+obj.zd_rate+"</li>";
							option += "</ul>";
						}
						option += '</div> </div> </div>';
					}else{
						option += '<div class="_time-body"> <div class="_table-title"><ul>';
//						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li> <li class="_sort">计划到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际到达<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
						option += ' </ul></div>';
						option += '<div class="_table-box"><div class="_table-over">';
						option += "<ul>";
						option += "<li style='width:100%;'>未查询到进港数据</li>";
						option += "</ul>";
						option += '</div> </div> </div>';
					}
					$('._customers-box').append(option);
					reset();
					if(typeof(arrvPort)!='undefined'&&arrvPort!=null&&arrvPort.length>9){
						add("._customers","._table-over",15,100,"._customers","._table-box");
					}else{
						var scoll = $(".sr-box-body").find('#yj-scroll')[0];
						if(typeof(scoll)!='undefined'){
							$(scoll).remove();
						}
						$('._time-body').css({'height':(60+47*((typeof(arrvPort)=='undefined'||arrvPort==null||arrvPort.length==0)?1:arrvPort.length))+'px'});
					}
				}else{
					$('._customers-box').append("<div class='time-q-box'>" +
							"<div class='_time-head'>" +
							"<ul class='_time-head-box'>" +
							"<li class='_time-head-title'>航班动态列表</li>" +
							"<li class='_time-head-set'>" +
							"<div class='_export-table' onclick='inOutSwitch(0)'>进港</div>" +
							"<div class='_export-table _set-gB' onclick='inOutSwitch(1)'>出港</div>" +
							"</li></ul></div></div>");
					var leavePort = data.list[1];
					if(leavePort!=null&&leavePort.length>0){
						option += '<div class="_time-body"> <div class="_table-title"><ul> ';
//						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li> <li class="_ordinary">到达地</li> <li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态<span class="_sequence">&#xe675;</span></li> <li class="_ordinary">准点率</li>';
						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li> <li class="_ordinary">到达地</li> <li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li> <li class="_sort">状态</li> <li class="_ordinary">准点率</li>';
						option += ' </ul></div>';
						option += '<div class="_table-box"><div class="_table-over">';
						for(var i=0;i<leavePort.length;i++){
							var obj = leavePort[i];
							option += "<ul>";
							option += "<li>"+obj.flt_nbr+"</li>";
							option += "<li>"+obj.dpt_AirPt_Cd+"</li>";
							option += "<li>"+obj.arrv_Airpt_Cd+"</li>";
							option += "<li>"+obj.plan_q+"</li>";
							option += "<li>"+obj.actual_q+"</li>";
							if(obj.state=="正常"){
								option += "<li>"+obj.state+"</li>";
							}else{
								if(obj.remark!=null&&obj.remark!=''){
									option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='abnormal-tag' factors='"+obj.remark+"' ids="+obj.id+"></div></li>";
								}else{
									option += "<li class='dispose'><div class='dispose-p'><div>"+obj.state+"</div></div><div class='remind' ids="+obj.id+">&#xe64a;</div></li>";
								}
							}
							option += "<li>"+obj.zd_rate+"</li>";
							option += "</ul>";
						}
						option += '</div> </div> </div>';
					}else{
						option += '<div class="_time-body"> <div class="_table-title"><ul> ';
//						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li><li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">状态<span class="_sequence">&#xe675;</span></li><li class="_ordinary">准点率</li>';
						option += '<li class="_ordinary">航班号</li> <li class="_ordinary">始发地</li><li class="_ordinary">到达地</li><li class="_sort">计划起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">实际起飞<span class="_sequence">&#xe675;</span></li><li class="_sort">状态</li><li class="_ordinary">准点率</li>';
						option += ' </ul></div>';
						option += '<div class="_table-box"><div class="_table-over">';
						option += "<ul>";
						option += "<li style='width:100%;'>未查询到出港数据</li>";
						option += "</ul>";
						option += '</div> </div> </div>';
					}
					$('._customers-box').append(option);
					if(typeof(leavePort)!='undefined'&&leavePort!=null&&leavePort.length>9){
						add("._customers","._table-over",15,100,"._customers","._table-box");
					}else{
						var scoll = $(".sr-box-body").find('#yj-scroll')[0];
						if(typeof(scoll)!='undefined'){
							$(scoll).remove();
						}
						$('._time-body').css({'height':(60+47*((typeof(leavePort)=='undefined'||leavePort==null||leavePort.length==0)?1:leavePort.length))+'px'});
					}
				}
				if(flag){
	        		$("._customers-box").addClass("muhu");
	        		$(".reportErr").css("display","block");
	        	}else{
	        		$("._customers-box").removeClass("muhu");
	        		$(".reportErr").css("display","none");
	        	}
			}else{
				$("._customers-box").addClass("muhu");
			}
			loadWeather(parent.iataMap[parent.airportCode].icao);
		}
	});
}

function scheduledFlight(){
	var month = typeof($('.list'))=="undefined"?"":$('.list').text();
	var year = typeof($('#year'))=="undefined"?"":$('#year').text();
	var date = '';
	if(month!=null&&month!=''&&year!=null&&year!=''){
		month = parseInt(month.replace('月',""));
		if(month<10){
			date = year + '-0' +month;
		}else{
			date = year + '-' +month;
		}
	}
	$.ajax({
		url:'/airline_dynameic_graphics',
		type:'post',
		data:{
			date:date
		},
		async:false,
		success:function(data){
			if(data!=null&&data!=''){
				$('._customers-box').empty();
				$("._customers-box").removeClass("muhu");
				$(".reportErr").css("display","none");
				if(data.months!=null&&data.months!=''&&$('.sr-box-body-date-head').length==0&&$('.sr-box-body-date-body').length==0){
					var months = data.months;
					var option = "<div class='sr-box-body-date-head'> <h3 class='abnormal'>异常情况</h3> <ul class='set-years'> <li onclick='lastyear(this)'>&#xe648;</li> <li id='year'>"+months[0].substring(0,4)+"</li> <li onclick='nextyear(this)'>&#xe648;</li> </ul> </div> <div class='sr-box-body-date-body'>";
					var length = months.length;
					var circleIndex = 1;
					var flag = false;
					for(var i =circleIndex;i<13;i++){
						for(var j=0;j<length;j++){
							var dateArray = months[j].split("-");
							var m = parseInt(dateArray[1]);
							if(m==i){
								flag = true;
								circleIndex = i+1;
								if(j==length-1){
									currentCheckedMonth = months[j];
									option += "<div class='month hasDataMonth list'>"+i+"月</div>";
								}else{
									option += "<div class='month hasDataMonth'>"+i+"月</div>";
								}
							}
						}
						if(!flag){
							option += "<div class='month'>"+i+"月</div>";
						}else{
							flag = false;
						}
					}
					option += " </div>";
					$(".air-weather").before(option);
				}else{
					if($('.sr-box-body-date-head').length==0&&$('.sr-box-body-date-body').length==0){
						var option = "<div class='sr-box-body-date-head'> <h3 class='abnormal'>异常情况</h3> <ul class='set-years'> <li onclick='lastyear(this)'>&#xe648;</li> <li id='year'>"+currDate.substring(0,4)+"</li> <li onclick='nextyear(this)'>&#xe648;</li> </ul> </div> <div class='sr-box-body-date-body'>";
						for(var i =1;i<13;i++){
							option += "<div class='month'>"+i+"月</div>";
						}
						option += " </div>";
						$(".air-weather").before(option);
					}
				}
				var option ="<div class='_abnormal'>" +
						"<div class='_abnormal-title'><ul class='_abnormal-title-box'>" +
						"<li><p>月准点率</p><div>"+data.month_zd+"%</div></li>" +
						"<li><p>延误班次（班）</p><div>"+data.delay_cls+"</div></li>" +
						"<li><p>取消班次（班）</p><div>"+data.cancel_cls+"</div></li>" +
						"<li><p>正常班次（班）</p><div>"+data.normal_cls+"</div></li>" +
						"</ul></div><div class='_abnormal-box' id='_abnormal-box'>" +
						"</div></div>";
				$("._customers-box").append(option);
				var exec = data.exec;
				var bz = true;
				if((parseInt(data.delay_cls)+parseInt(data.cancel_cls)+parseInt(data.normal_cls))>0){
					bz = false;
				}
				if(exec!=null&&exec.length>0){
					var nameArray = [];
					var valueArray = [];
					for(var i=0;i<5;i++){//只取前五名
						var obj = exec[i];
						nameArray.push(obj.name);
						valueArray.push(obj.val);
					}
					draw(nameArray,valueArray);
				}
				if(bz){
	        		$("._customers-box").addClass("muhu");
	        		$(".reportErr").css("display","block");
	        	}else{
	        		$("._customers-box").removeClass("muhu");
	        		$(".reportErr").css("display","none");
	        	}
			}else{
				$("._customers-box").addClass("muhu");
        		$(".reportErr").css("display","block");
			}
		}
	});
}

function draw(nameArray,valueArray){
    var dom = document.getElementById("_abnormal-box");
    var myChart = echarts.init(dom);
    var option = {
        title: {
            text: "异常原因top5",
            left:'5%',
            top:'0%',
            textStyle:{
                color:"white",
                fontWeight:"normal",
                fontSize:"13"
            }
        },
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '7%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : nameArray,
                axisTick: {
                    alignWithLabel: true
                },
                axisLabel:{
                    interval:0,
                    margin:10,
                    textStyle:{
                        color:"white"
                    }
                },
                splitLine: {
                    show:true,
                    lineStyle:{
                        color:"#1b2c4c"
                    }
                }
            }
        ],
        yAxis : [
            {
                type : 'value',
                splitLine: {
                    show:false,
                    lineStyle:{
                        color:"#1b2c4c"
                    }
                },
                axisLabel:{
                    textStyle:{
                        color:"white"
                    }
                }
            }
        ],
        series : [
            {
                name:'班次',
                type:'bar',
                barWidth: '25',
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#5896c7'},
                                {offset: 0.5, color: '#5076bf'},
                                {offset: 1, color: '#4b5cb6'}
                            ]
                        )
                    },
                    emphasis: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#4b5cb6'},
                                {offset: 0.5, color: '#5076bf'},
                                {offset: 1, color: '#5896c7'}
                            ]
                        )
                    }
                },
                data:valueArray
            }
        ]
    };
    myChart.setOption(option,true);
}

var sorting = {
	    //利用sort方法进行排序
	    systemSort: function(arr){
	        return arr.sort(function(a,b){
	            return a-b;
	        });
	    },
	 
	    //冒泡排序
	    bubbleSort: function(arr){
	        var len=arr.length, tmp;
	        for(var i=0;i<len-1;i++){
	            for(var j=0;j<len-1-i;j++){
	                if(arr[j]>arr[j+1]){
	                    tmp = arr[j];
	                    arr[j] = arr[j+1];
	                    arr[j+1] = tmp;
	                }
	            }
	        }
	        return arr;
	    },
	 
	    //快速排序
	    quickSort: function(arr){
	        var low=0, high=arr.length-1;
	        sort(low,high);
	        function sort(low, high){
	            if(low<high){
	                var mid = (function(low, high){
	                    var tmp = arr[low];
	                    while(low<high){
	                        while(low<high&&arr[high]>=tmp){
	                            high--;
	                        }
	                        arr[low] = arr[high];
	                        while(low<high&&arr[low]<=tmp){
	                            low++;
	                        }
	                        arr[high] = arr[low];
	                    }
	                    arr[low] = tmp;
	                    return low;
	                })(low, high);
	                sort(low, mid-1);
	                sort(mid+1,high);
	            }
	        }
	        return arr;
	    },
	 
	    //插入排序
	    insertSort: function(arr){
	        var len = arr.length;
	        for(var i=1;i<len;i++){
	            var tmp = arr[i];
	            for(var j=i-1;j>=0;j--){
	                if(tmp<arr[j]){
	                    arr[j+1] = arr[j];
	                }else{
	                    arr[j+1] = tmp;
	                    break;
	                }
	            }
	        }
	        return arr;
	    },
	 
	    //希尔排序
	    shellSort: function(arr){
	        var h = 1;
	        while(h<=arr.length/3){
	            h = h*3+1;  //O(n^(3/2))by Knuth,1973
	        }
	        for( ;h>=1;h=Math.floor(h/3)){
	            for(var k=0;k<h;k++){
	                for(var i=h+k;i<arr.length;i+=h){
	                    for(var j=i;j>=h&&arr[j]<arr[j-h];j-=h){
	                        var tmp = arr[j];
	                        arr[j] = arr[j-h];
	                        arr[j-h] = tmp;
	                    }
	                }
	            }
	        }
	        return arr;
	    }
	};

function lastyear(obj){
	var theYear = parseInt($(obj).next().text())-1;
	var checkedArray = currentCheckedMonth.split('-');
	var html = '';
	for(var i=1;i<=12;i++){
		if(theYear ==parseInt(checkedArray[0])&&i==parseInt(checkedArray[1])){
			html+='<div class="month list">'+i+'月</div>';
		}else{
			html+='<div class="month">'+i+'月</div>';
		}
	}
	$('.sr-box-body-date-body').html(html);
	$(obj).next().text(theYear);
	getMonthList();
}
function nextyear(obj){
	var theYear = parseInt($(obj).prev().text())+1;
	var checkedArray = currentCheckedMonth.split('-');
	var html = '';
	for(var i=1;i<=12;i++){
		if(theYear ==parseInt(checkedArray[0])&&i==parseInt(checkedArray[1])){
			html+='<div class="month list">'+i+'月</div>';
		}else{
			html+='<div class="month">'+i+'月</div>';
		}
	}
	$('.sr-box-body-date-body').html(html);
	$(obj).prev().text(theYear);
	getMonthList();
}
function getDateList(){
	var month = typeof($('.achieve-year'))=="undefined"?"":$('.achieve-year').text()+'-'+(parseInt($('.achieve-month').text())<10?('0'+$('.achieve-month').text()):$('.achieve-month').text());
	var inout = typeof($('._set-gB'))=='undefined'?"":($('._set-gB').text()=="进港"?'in':'out');
	$.ajax({
		url:'/getDatesList',
		type:'post',
		data:{
			inout:inout,
			month:month
		},
		success:function(data){
			if(data!=null&&data.dates!=null){
				var length = data.dates.length;
				var dates = data.dates;
				if(length>0){
					var i=0;
					$('.this-month').each(function(){
						var curr = $(this).attr('abbr');
						for(var j=i;j<length;j++){
							if(curr==dates[j]){
								$(this).addClass('hasData');
								if(curr==currCheckedDate){
									$(this).addClass('list');
								}
								i=j;
								break;
							}
						}
						if(curr==currDate){
							$(this).css('color','red');
						}
					});
				}else{
					$('.this-month').each(function(){
						var curr = $(this).attr('abbr');
						if(curr==currDate){
							$(this).css('color','red');
						}
					});
				}
			}
		}
	});
}
function getMonthList(){
	var year = $('#year').text();
	$.ajax({
		url:'/getMonthList',
		type:'post',
		data:{
			year:year
		},
		success:function(data){
			if(data!=null&&data.months!=null){
				console.log(currentCheckedMonth);
				var length = data.months.length;
				var months = data.months;
				var year = $('#year').text();
				if(length>0){
					var i=0;
					$('.month').each(function(){
						for(var j=i;j<length;j++){
							var text = $(this).text();
							text = text.substring(0,text.length-1);
							if(parseInt(text)==parseInt(months[j].substring(5,7))){
								$(this).addClass('hasDataMonth');
								i=j;
								if(currentCheckedMonth==year+'-'+text){
									$(this).addClass('list');
								}
								break;
							}
						}
					});
				}
			}
		}
	});
}