var weekdayArray = ['一','二','三','四','五','六','日'];
var fieldMap = {"始发地":"dpt_AirPt_Cd","到达地":"arrv_Airpt_Cd","航班号":"flt_nbr","机型":"airCrft_Typ","出发时间":"lcl_Dpt_Tm","出发机场":"dpt_AirPt_pot","到达时间":"lcl_Arrv_Tm","到达机场":"arrv_Airpt_pot","班期":"days"};
var fields = {dpt_AirPt_Cd:"始发地",arrv_Airpt_Cd:"到达地",flt_nbr:"航班号",airCrft_Typ:"机型",lcl_Dpt_Tm:"出发时间",dpt_AirPt_pot:"出发机场",lcl_Arrv_Tm:"到达时间",arrv_Airpt_pot:"到达机场",days:"班期"};
var fieldWidths = {dpt_AirPt_Cd:7,arrv_Airpt_Cd:7,flt_nbr:7,airCrft_Typ:7,lcl_Dpt_Tm:13,dpt_AirPt_pot:13,lcl_Arrv_Tm:13,arrv_Airpt_pot:13,days:20};
var airports = parent.airportMap;
var allData = {};
var fltNbrSort = "";
var dptTmSort = "";
var arrvTmSort = "";
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
	$(".sr-box-body").css({"height":(infer(".sr-box")[1]-infer(".sr-box-head")[1]+"px")});
	$('._time-body').css({'height':(infer('.time-q-box')[1]-infer('._time-head')[1])-5+'px'});
	$('._table-box').css({'height':(infer('._time-body')[1]-infer('._table-title')[1])+'px'});
}
function getSearchJson(){
	var searchJson = {};
	var flag = true;
	var dpt_AirPt_Cd = $('#cityChoice').val();
	if(dpt_AirPt_Cd!=''){
		searchJson.dpt_AirPt_Cd_itia = airports[dpt_AirPt_Cd].iata;
	}
	var Arrv_Airpt_Cd = $('#cityChoice2').val();
	if(Arrv_Airpt_Cd!=''){
		searchJson.Arrv_Airpt_Cd_itia = airports[Arrv_Airpt_Cd].iata;
	}
	if(dpt_AirPt_Cd==''&&Arrv_Airpt_Cd==''){
		$(".err_time").css("display","block");
//		$("._time-body").addClass("muhu");
		alert('请选择一个起飞机场或者到达机场');
		flag = false;
		return flag;
	}
	var fieldArray = [];
	$('input[type="checkbox"]').each(function(e){
		if($(this).is(":checked")==true){
			fieldArray.push(fieldMap[$(this).next().text()]);
		}
	});
	searchJson.fieldArray = fieldArray;
	searchJson.mu= $('#airline').val();
	var goOrReturn = $('#goOrReturn').text();
	if('往返'==goOrReturn){
		searchJson.goOrReturn = 'return';
	}else{
		searchJson.goOrReturn = 'go';
	}
	searchJson.get_tim = $('._set-list-title').text();
	return searchJson;
}
function getNewestCollectDate(){
	$.ajax({
		url:'/getNewEstCollectDate',
		type:'post',
		data:[],
		async:false,
		success:function(data){
			$('._set-list-title').empty();
			var list={
        	        data:data, //st节点集合
        	        summary:"false", //是否包含往返 true包含false不包含
        	        name:"#get_tim",  //添加list节点
        	        cleName:".sr-box",   //取消绑定事件函数节点
        	        fun:function(){
        	        }
        	    };
			setChoose(list);
		}
	});
}

function validateFormat(id){
	var time = $('#'+id).val();
	var result = false;
	if(typeof(time)!='undefined'&&time!=null&&time!=''){
		var contentArray = time.split(':');
		if(contentArray.length==2){
			var z= /^[0-9]*$/;
			var flag = false;
			for(var i=0;i<2;i++){
				if(z.test(contentArray[0])&&z.test(contentArray[1])){
					if(parseInt(contentArray[0])>23||parseInt(contentArray[1])>59){
						flag = true;
					}else{
						flag = false;
					}
				}else{
					flag = true;
				}
			}
			if(flag){
				alert('请输入正确时刻');
				$('#'+id).val('');
			}else{
				result =  true;
			}
		}else{
			alert('请输入正确时刻');
			$('#'+id).val('');
		}
	}
	return result;
}

function exportExcel(){
	var dpt_AirPt_Cd = $('#cityChoice').val();
	if(dpt_AirPt_Cd!=''){
		$('#dpt_AirPt_Cd').val(airports[dpt_AirPt_Cd].iata);
	}
	var Arrv_Airpt_Cd = $('#cityChoice2').val();
	if(Arrv_Airpt_Cd!=''){
		$('#Arrv_Airpt_Cd').val(airports[Arrv_Airpt_Cd].iata);
	}
	if(dpt_AirPt_Cd==''&&Arrv_Airpt_Cd==''){
		$(".err_time").css("display","block");
//		$("._time-body").addClass("muhu");
		
		
		alert('请选择一个起飞机场或者到达机场');
		return;
	}
	var goOrReturn = $('#goOrReturn').text();
	if('往返'==goOrReturn){
		$('#flightLine').val('return');
	}else{
		$('#flightLine').val('go');
	}
	$('#collectTime').val($('._set-list-title').text());
	$('#excelForm').submit();
}

function getCurrConditionData(timeType,id){
	//将数据框位置还原
	$('#_table-over').css("top","0px");
	var searchJson = getSearchJson();
	if(searchJson == null || searchJson == false){
		return;
	}
	var inputTime = $('#'+id).val();
	if(inputTime!=''&&!validateFormat(id)){
		return;
	}
	var fieldArray = searchJson.fieldArray;
	if(fieldArray.length==0){
		alert('请至少选择一列显示');
		return;
	}
	var listWidth = 0;
	if(timeType==1){
		if(id.indexOf('Start')>0){
			if(allData.total!=null&&allData.total>0){
				$(".err_time").css("display","none");
				var option = '';
				for(var j=0;j<fieldArray.length;j++){
					listWidth += infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j]);
				}
				var dataArray = allData.rows;
				//算高度
				var currentCount = 0;
				$('#_table-over').empty();
				for(var i=0;i<allData.total;i++){
					var obj = dataArray[i];
					if((inputTime==''||(new Date(new Date().format("yyyy-MM-dd")+' '+inputTime).getTime()<=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Dpt_Tm).getTime()))
							&&(validateFormat('dptTmEnd')==false?true:new Date(new Date().format("yyyy-MM-dd")+' '+$('#dptTmEnd').val()).getTime()>=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Dpt_Tm).getTime())
							&&(validateFormat('arrvTmStart')==false?true:new Date(new Date().format("yyyy-MM-dd")+' '+$('#arrvTmStart').val()).getTime()<=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Arrv_Tm).getTime())
							&&(validateFormat('arrvTmEnd')==false?true:new Date(new Date().format("yyyy-MM-dd")+' '+$('#arrvTmEnd').val()).getTime()>=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Arrv_Tm).getTime())){
						currentCount++;
						option += '<ul>';
							for(var j=0;j<fieldArray.length;j++){
								if(fieldArray[j]=='days'){
									if(obj.days!=null&&obj.days!=''){
			                        	option += '<li class="flightDate" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px"><ul class="_day-set">';
			                        	var days = obj.days;
			                        	for(var k=0;k<7;k++){
			                        		if(days.indexOf(weekdayArray[k])>=0){
			                        			option += '<li>'+weekdayArray[k]+'</li>';
			                        		}else{
			                        			option += '<li class="_day-set-im">'+weekdayArray[k]+'</li>';
			                        		}
			                        	}
			                        	option += '</ul></li>';
			                        }
								}else if(fieldArray[j]=='dpt_AirPt_Cd'){
									option += '<li class="dptCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='arrv_Airpt_Cd'){
									option += '<li class="arrvCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='flt_nbr'){
									option += '<li class="flt_nbr" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='airCrft_Typ'){
									option += '<li class="airportType" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px;overflow:hidden" title="'+eval('obj.'+fieldArray[j])+'">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='lcl_Dpt_Tm'){
									option += '<li class="dptTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='dpt_AirPt_pot'){
									option += '<li class="dptAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='lcl_Arrv_Tm'){
									option += '<li class="arrvTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else{
									option += '<li class="arrvAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}
							}
	                      option += '</ul>';
					}
				}
				$('#_table-over').append(option);
				reset();//算大小
				if(currentCount>12){
					add(".sr-box-body","._table-over",15,100,".sr-box-body","._table-box");
				}else{
					var scoll = $(".sr-box-body").find('#yj-scroll')[0];
					if(typeof(scoll)!='undefined'){
						$(scoll).remove();
					}
					$('._time-body').css({'height':(72+47*currentCount)+'px'});
				}
				$('._time-body').css({'width':listWidth+'px'});
			}else{
				$(".err_time").css("display","block");
			}
		}else if(id.indexOf('End')>0){
			if(allData.total!=null&&allData.total>0){
				$(".err_time").css("display","none");
				var option = '';
				for(var j=0;j<fieldArray.length;j++){
					listWidth += infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j]);
				}
				var dataArray = allData.rows;
				var currentCount =0;
				$('#_table-over').empty();
				for(var i=0;i<allData.total;i++){
					var obj = dataArray[i];
					if((inputTime==''||(new Date(new Date().format("yyyy-MM-dd")+' '+inputTime).getTime()>=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Dpt_Tm).getTime()))
							&&(validateFormat('dptTmStart')==false?true:new Date(new Date().format("yyyy-MM-dd")+' '+$('#dptTmStart').val()).getTime()<=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Dpt_Tm).getTime())
							&&(validateFormat('arrvTmStart')==false?true:new Date(new Date().format("yyyy-MM-dd")+' '+$('#arrvTmStart').val()).getTime()<=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Arrv_Tm).getTime())
							&&(validateFormat('arrvTmEnd')==false?true:new Date(new Date().format("yyyy-MM-dd")+' '+$('#arrvTmEnd').val()).getTime()>=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Arrv_Tm).getTime())){
						currentCount++;	
						option += '<ul>';
							for(var j=0;j<fieldArray.length;j++){
								if(fieldArray[j]=='days'){
									if(obj.days!=null&&obj.days!=''){
			                        	option += '<li class="flightDate" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px"><ul class="_day-set">';
			                        	var days = obj.days;
			                        	for(var k=0;k<7;k++){
			                        		if(days.indexOf(weekdayArray[k])>=0){
			                        			option += '<li>'+weekdayArray[k]+'</li>';
			                        		}else{
			                        			option += '<li class="_day-set-im">'+weekdayArray[k]+'</li>';
			                        		}
			                        	}
			                        	option += '</ul></li>';
			                        }
								}else if(fieldArray[j]=='dpt_AirPt_Cd'){
									option += '<li class="dptCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='arrv_Airpt_Cd'){
									option += '<li class="arrvCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='flt_nbr'){
									option += '<li class="flt_nbr" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='airCrft_Typ'){
									option += '<li class="airportType" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px;overflow:hidden" title="'+eval('obj.'+fieldArray[j])+'">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='lcl_Dpt_Tm'){
									option += '<li class="dptTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='dpt_AirPt_pot'){
									option += '<li class="dptAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='lcl_Arrv_Tm'){
									option += '<li class="arrvTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else{
									option += '<li class="arrvAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}
							}
	                        option += '</ul>';
					}
				}
				$('#_table-over').append(option);
				reset();//算大小
				if(currentCount>12){
					add(".sr-box-body","._table-over",15,100,".sr-box-body","._table-box");
				}else{
					var scoll = $(".sr-box-body").find('#yj-scroll')[0];
					if(typeof(scoll)!='undefined'){
						$(scoll).remove();
					}
					$('._time-body').css({'height':(72+47*currentCount)+'px'});
				}
				$('._time-body').css({'width':listWidth+'px'});
			}else{
				$(".err_time").css("display","block");
			}
		}
	}else if(timeType==2){
		if(id.indexOf('Start')>0){
			if(allData.total!=null&&allData.total>0){
				$(".err_time").css("display","none");
				var option = '';
				for(var j=0;j<fieldArray.length;j++){
					listWidth += infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j]);
				}
				var currentCount = 0;
				var dataArray = allData.rows;
				$('#_table-over').empty();
				for(var i=0;i<allData.total;i++){
					var obj = dataArray[i];
					if((inputTime==''||(new Date(new Date().format("yyyy-MM-dd")+' '+inputTime).getTime()<=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Arrv_Tm).getTime()))
							&&(validateFormat('dptTmEnd')==false?true:new Date(new Date().format("yyyy-MM-dd")+' '+$('#dptTmEnd').val()).getTime()>=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Dpt_Tm).getTime())
							&&(validateFormat('dptTmStart')==false?true:new Date(new Date().format("yyyy-MM-dd")+' '+$('#dptTmStart').val()).getTime()<=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Dpt_Tm).getTime())
							&&(validateFormat('arrvTmEnd')==false?true:new Date(new Date().format("yyyy-MM-dd")+' '+$('#arrvTmEnd').val()).getTime()>=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Arrv_Tm).getTime())){
							option += '<ul>';
							currentCount++;
							for(var j=0;j<fieldArray.length;j++){
								if(fieldArray[j]=='days'){
									if(obj.days!=null&&obj.days!=''){
			                        	option += '<li class="flightDate" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px"><ul class="_day-set">';
			                        	var days = obj.days;
			                        	for(var k=0;k<7;k++){
			                        		if(days.indexOf(weekdayArray[k])>=0){
			                        			option += '<li>'+weekdayArray[k]+'</li>';
			                        		}else{
			                        			option += '<li class="_day-set-im">'+weekdayArray[k]+'</li>';
			                        		}
			                        	}
			                        	option += '</ul></li>';
			                        }
								}else if(fieldArray[j]=='dpt_AirPt_Cd'){
									option += '<li class="dptCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='arrv_Airpt_Cd'){
									option += '<li class="arrvCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='flt_nbr'){
									option += '<li class="flt_nbr" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='airCrft_Typ'){
									option += '<li class="airportType" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px;overflow:hidden" title="'+eval('obj.'+fieldArray[j])+'">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='lcl_Dpt_Tm'){
									option += '<li class="dptTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='dpt_AirPt_pot'){
									option += '<li class="dptAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='lcl_Arrv_Tm'){
									option += '<li class="arrvTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else{
									option += '<li class="arrvAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}
							}
	                        option += '</ul>';
					}
				}
				$('#_table-over').append(option);
				reset();//算大小
				if(currentCount>12){
					add(".sr-box-body","._table-over",15,100,".sr-box-body","._table-box");
				}else{
					var scoll = $(".sr-box-body").find('#yj-scroll')[0];
					if(typeof(scoll)!='undefined'){
						$(scoll).remove();
					}
					$('._time-body').css({'height':(72+47*currentCount)+'px'});
				}
				$('._time-body').css({'width':listWidth+'px'});
			}else{
				$(".err_time").css("display","block");
			}
		}else if(id.indexOf('End')>0){
			if(allData.total!=null&&allData.total>0){
				$(".err_time").css("display","none");
				var option = '';
				for(var j=0;j<fieldArray.length;j++){
					listWidth += infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j]);
				}
				var dataArray = allData.rows;
				var currentCount = 0;
				$('#_table-over').empty();
				for(var i=0;i<allData.total;i++){
					var obj = dataArray[i];
					if((inputTime==''||(new Date(new Date().format("yyyy-MM-dd")+' '+inputTime).getTime()>=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Arrv_Tm).getTime()))
							&&(validateFormat('dptTmEnd')==false?true:new Date(new Date().format("yyyy-MM-dd")+' '+$('#dptTmEnd').val()).getTime()>=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Dpt_Tm).getTime())
							&&(validateFormat('arrvTmStart')==false?true:new Date(new Date().format("yyyy-MM-dd")+' '+$('#arrvTmStart').val()).getTime()<=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Arrv_Tm).getTime())
							&&(validateFormat('dptTmStart')==false?true:new Date(new Date().format("yyyy-MM-dd")+' '+$('#dptTmStart').val()).getTime()<=new Date(new Date().format('yyyy-MM-dd')+' '+obj.lcl_Dpt_Tm).getTime())){
						currentCount++;	
						option += '<ul>';
							for(var j=0;j<fieldArray.length;j++){
								if(fieldArray[j]=='days'){
									if(obj.days!=null&&obj.days!=''){
			                        	option += '<li class="flightDate" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px"><ul class="_day-set">';
			                        	var days = obj.days;
			                        	for(var k=0;k<7;k++){
			                        		if(days.indexOf(weekdayArray[k])>=0){
			                        			option += '<li>'+weekdayArray[k]+'</li>';
			                        		}else{
			                        			option += '<li class="_day-set-im">'+weekdayArray[k]+'</li>';
			                        		}
			                        	}
			                        	option += '</ul></li>';
			                        }
								}else if(fieldArray[j]=='dpt_AirPt_Cd'){
									option += '<li class="dptCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='arrv_Airpt_Cd'){
									option += '<li class="arrvCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='flt_nbr'){
									option += '<li class="flt_nbr" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='airCrft_Typ'){
									option += '<li class="airportType" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px;overflow:hidden" title="'+eval('obj.'+fieldArray[j])+'">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='lcl_Dpt_Tm'){
									option += '<li class="dptTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='dpt_AirPt_pot'){
									option += '<li class="dptAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='lcl_Arrv_Tm'){
									option += '<li class="arrvTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else{
									option += '<li class="arrvAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}
							}
	                        option += '</ul>';
					}
				}
				$('#_table-over').append(option);
				reset();//算大小
				if(currentCount>12){
					add(".sr-box-body","._table-over",15,100,".sr-box-body","._table-box");
				}else{
					var scoll = $(".sr-box-body").find('#yj-scroll')[0];
					if(typeof(scoll)!='undefined'){
						$(scoll).remove();
					}
					$('._time-body').css({'height':(72+47*currentCount)+'px'});
				}
				$('._time-body').css({'width':listWidth+'px'});
			}else{
				$(".err_time").css("display","block");
			}
		}
	}
}
function sort(sorttype){
	$('#TM-sloading').show();
	var searchJson = getSearchJson();
	if(searchJson == null || searchJson == false){
		return;
	}
	searchJson.sorttype = sorttype;
	if(sorttype==1){
		if(fltNbrSort=="ASC"){
			searchJson.fltNbrSort =fltNbrSort= "DESC";
		}else{
			searchJson.fltNbrSort =fltNbrSort= "ASC";
		}
	}else if(sorttype==2){
		if(dptTmSort=="ASC"){
			searchJson.dptTmSort = dptTmSort = "DESC";
		}else{
			searchJson.dptTmSort = dptTmSort = "ASC";
		}
	}else if(sorttype == 3){
		if(arrvTmSort=="ASC"){
			searchJson.arrvTmSort =arrvTmSort= "DESC";
		}else{
			searchJson.arrvTmSort =arrvTmSort= "ASC";
		}
	}
	var dptTmStart = $('#dptTmStart').val();
	if(typeof(dptTmStart)!='undefined'&&dptTmStart!=null&&dptTmStart!=''){
		searchJson.dptTmStart = dptTmStart;
	}
	var dptTmEnd = $("#dptTmEnd").val();
	if(typeof(dptTmEnd)!='undefined'&&dptTmEnd!=null&&dptTmEnd!=''){
		searchJson.dptTmEnd = dptTmEnd;
	}
	var arrvTmStart = $("#arrvTmStart").val();
	if(typeof(arrvTmStart)!='undefined'&&arrvTmStart!=null&&arrvTmStart!=''){
		searchJson.arrvTmStart = arrvTmStart;
	}
	var arrvTmEnd = $("#arrvTmEnd").val();
	if(typeof(arrvTmEnd)!='undefined'&&arrvTmEnd!=null&&arrvTmEnd!=''){
		searchJson.arrvTmEnd = arrvTmEnd;
	}
	var fieldArray = searchJson.fieldArray;
	if(fieldArray.length==0){
		alert('请至少选择一列显示');
		return;
	}
	var listWidth = 0;
	$.ajax({
		url:'/processTask_list3',
		type:'post',
		dataType:'json',
		data:searchJson,
		traditional: true,
		async:false,
		success:function(data){
            $('#TM-sloading').hide();
			if(data!=null){
				if(data.total!=null&&data.total>0){
					$(".err_time").css("display","none");
					var option = '';
					for(var j=0;j<fieldArray.length;j++){
						listWidth += infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j]);
					}
					var dataArray = data.rows;
					var flag = true;
					//判断两个集合是否内容顺序一致--start
					for(var i=0;i<data.total;i++){
						var obj = dataArray[i];
						var obj1 = allData.rows[i];
						if(sorttype==1){
							if(obj.flt_nbr!=obj1.flt_nbr){
								flag = false;
								break;
							}
						}else if(sorttype==2){
							if(obj.lcl_Dpt_Tm!=obj1.lcl_Dpt_Tm){
								flag = false;
								break;
							}
						}else if(sorttype == 3){
							if(obj.lcl_Arrv_Tm!=obj1.lcl_Arrv_Tm){
								flag = false;
								break;
							}
						}
					}
					if(flag){
						dataArray = allData.rows;
					}else{
						allData.rows = dataArray;
					}
					//判断两个集合是否内容顺序一致--end
					$('#_table-over').empty();
					$('#_table-over').css('top','0px');
					for(var i=0;i<data.total;i++){
						var obj = dataArray[i];
						option += '<ul>';
						for(var j=0;j<fieldArray.length;j++){
							if(fieldArray[j]=='days'){
								if(obj.days!=null&&obj.days!=''){
		                        	option += '<li class="flightDate" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px"><ul class="_day-set">';
		                        	var days = obj.days;
		                        	for(var k=0;k<7;k++){
		                        		if(days.indexOf(weekdayArray[k])>=0){
		                        			option += '<li>'+weekdayArray[k]+'</li>';
		                        		}else{
		                        			option += '<li class="_day-set-im">'+weekdayArray[k]+'</li>';
		                        		}
		                        	}
		                        	option += '</ul></li>';
		                        }
							}else if(fieldArray[j]=='dpt_AirPt_Cd'){
								option += '<li class="dptCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
							}else if(fieldArray[j]=='arrv_Airpt_Cd'){
								option += '<li class="arrvCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
							}else if(fieldArray[j]=='flt_nbr'){
								option += '<li class="flt_nbr" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
							}else if(fieldArray[j]=='airCrft_Typ'){
								option += '<li class="airportType" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px;overflow:hidden" title="'+eval('obj.'+fieldArray[j])+'">'+eval('obj.'+fieldArray[j])+'</li>';
							}else if(fieldArray[j]=='lcl_Dpt_Tm'){
								option += '<li class="dptTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
							}else if(fieldArray[j]=='dpt_AirPt_pot'){
								option += '<li class="dptAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
							}else if(fieldArray[j]=='lcl_Arrv_Tm'){
								option += '<li class="arrvTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
							}else{
								option += '<li class="arrvAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
							}
						}
                        option += '</ul>';
					}
					$('#_table-over').append(option);
					reset();//算大小
					if(data.total>12){
						add(".sr-box-body","._table-over",15,100,".sr-box-body","._table-box");
					}else{
						var scoll = $(".sr-box-body").find('#yj-scroll')[0];
						if(typeof(scoll)!='undefined'){
							$(scoll).remove();
						}
						$('._time-body').css({'height':(72+47*data.total)+'px'});
					}
					$('._time-body').css({'width':listWidth+'px'});
				}else{
					$(".err_time").css("display","block");
				}
			}else{
				alert('服务器异常，请稍后再试');
			}
		}
	});
}

function changeFieldS(fieldArray){
	var searchJson = {};
	var dpt_AirPt_Cd = $('#cityChoice').val();
	if(dpt_AirPt_Cd!=''){
		searchJson.dpt_AirPt_Cd_itia = airports[dpt_AirPt_Cd].iata;
	}
	var Arrv_Airpt_Cd = $('#cityChoice2').val();
	if(Arrv_Airpt_Cd!=''){
		searchJson.Arrv_Airpt_Cd_itia = airports[Arrv_Airpt_Cd].iata;
	}
	searchJson.fieldArray = fieldArray;
	searchJson.mu= $('#airline').val();
	var goOrReturn = $('#goOrReturn').text();
	if('往返'==goOrReturn){
		searchJson.goOrReturn = 'return';
	}else{
		searchJson.goOrReturn = 'go';
	}
	searchJson.get_tim = $('._set-list-title').text();
	var listWidth = 0;
	var theEle = $('.time-q-box').find('._time-body')[0];
	if(typeof(theEle)!='undefined'){
		$(theEle).remove();
	}
	if(allData!=null&&allData!=''){
		if(allData.total!=null&&allData.total>0){
			var option = '';
			option += '<div class="_time-body"><div class="_table-title"><ul>';
			for(var i=0;i<fieldArray.length;i++){
				if(fieldArray[i]=="flt_nbr"){
					option += '<li class="_sort" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i])+'px">'+eval("fields."+fieldArray[i])+'<span class="_sequence" onclick="sort(1)">&#xe675;</span></li>';
				}else if(fieldArray[i]=="lcl_Dpt_Tm"){
					option += '<li class="_hybrid" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i])+'px"><div>'+eval("fields."+fieldArray[i])+'<span class="_sequence" onclick="sort(2)">&#xe675;</span></div>';
					option += '<div><input type="text" id="dptTmStart" placeholder="time"><span>-</span><input type="text" id="dptTmEnd" placeholder="time"></div></li>';
				}else if(fieldArray[i]=="lcl_Arrv_Tm"){
					option += '<li class="_hybrid" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i])+'px"><div>'+eval("fields."+fieldArray[i])+'<span class="_sequence" onclick="sort(3)">&#xe675;</span></div>';
					option += '<div><input type="text" id="arrvTmStart"  placeholder="time"><span>-</span><input type="text" id="arrvTmEnd" placeholder="time"></div></li>';
				}else if(fieldArray[i]=='dpt_AirPt_pot'||fieldArray[i]=='arrv_Airpt_pot'){
					option += '<li class="_depart" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i])+'px">'+eval("fields."+fieldArray[i])+'</li>';
					}else if(fieldArray[i]=='days'){
						option += '<li class="_last" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i])+'px">'+eval("fields."+fieldArray[i])+'</li>';
					}else{
						option += '<li class="_ordinary" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i])+'px">'+eval("fields."+fieldArray[i])+'</li>';
					}
					listWidth += infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i]);
				}
			option += '</ul></div><div class="_table-box"><div class="_table-over" id="_table-over">';
			var dataArray = allData.rows;
			for(var i=0;i<allData.total;i++){
				var obj = dataArray[i];
					option += '<ul>';
					for(var j=0;j<fieldArray.length;j++){
						if(fieldArray[j]=='days'){
							if(obj.days!=null&&obj.days!=''){
	                        	option += '<li class="flightDate" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px"><ul class="_day-set">';
	                        	var days = obj.days;
	                        	for(var k=0;k<7;k++){
	                        		if(days.indexOf(weekdayArray[k])>=0){
	                        			option += '<li>'+weekdayArray[k]+'</li>';
	                        		}else{
	                        			option += '<li class="_day-set-im">'+weekdayArray[k]+'</li>';
	                        		}
	                        	}
	                        	option += '</ul></li>';
	                        }
						}else if(fieldArray[j]=='dpt_AirPt_Cd'){
							option += '<li class="dptCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
						}else if(fieldArray[j]=='arrv_Airpt_Cd'){
							option += '<li class="arrvCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
						}else if(fieldArray[j]=='flt_nbr'){
							option += '<li class="flt_nbr" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
						}else if(fieldArray[j]=='airCrft_Typ'){
							option += '<li class="airportType" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px;overflow:hidden" title="'+eval('obj.'+fieldArray[j])+'">'+eval('obj.'+fieldArray[j])+'</li>';
						}else if(fieldArray[j]=='lcl_Dpt_Tm'){
							option += '<li class="dptTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
						}else if(fieldArray[j]=='dpt_AirPt_pot'){
							option += '<li class="dptAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
						}else if(fieldArray[j]=='lcl_Arrv_Tm'){
							option += '<li class="arrvTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
						}else{
							option += '<li class="arrvAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
						}
					}
                    option += '</ul>';
			}
            option += '</div></div></div>';
			$('.time-q-box').append(option);
			reset();//算大小
			if(allData.total>12){
				add(".sr-box-body","._table-over",15,100,".sr-box-body","._table-box");
			}else{
				var scoll = $(".sr-box-body").find('#yj-scroll')[0];
				if(typeof(scoll)!='undefined'){
					$(scoll).remove();
				}
				$('._time-body').css({'height':(72+47*allData.total)+'px'});
			}
			$('._time-body').css({'width':listWidth+'px'});
		}
	}
}

function send(){
    if(parent.bgName != undefined){
        var name = airports[parent.bgName].aptChaNam;
        $('#cityChoice').val(name).attr('abbr',name + parent.bgName);
        delete parent.bgName;
    }
	var searchJson = {};
	var dpt_AirPt_Cd = $('#cityChoice').val();
	var Arrv_Airpt_Cd = $('#cityChoice2').val();

    if(dpt_AirPt_Cd!=''){
        searchJson.dpt_AirPt_Cd_itia = airports[dpt_AirPt_Cd].iata;
    }
	if(Arrv_Airpt_Cd!=''){
		searchJson.Arrv_Airpt_Cd_itia = airports[Arrv_Airpt_Cd].iata;
	}
	if(dpt_AirPt_Cd==''&&Arrv_Airpt_Cd==''){
		$(".err_time").css("display","block");
//		$("._time-body").addClass("muhu");
		$("._time-body").empty();
		$("#yj-scroll").remove();
		alert('请选择一个起飞机场或者到达机场');
		return;
	}
	//每次查询都为全数据查询
	var fieldArray = [];
	$('input[type="checkbox"]').each(function(e){
		$(this).attr('checked',true);
		fieldArray.push(fieldMap[$(this).next().text()]);
	});
	searchJson.fieldArray = fieldArray;
	searchJson.mu= $('#airline').val();
	var goOrReturn = $('#goOrReturn').text();
	if('往返'==goOrReturn){
		searchJson.goOrReturn = 'return';
	}else{
		searchJson.goOrReturn = 'go';
	}
	searchJson.get_tim = $('._set-list-title').text();
	var listWidth = 0;
	$.ajax({
		url:'/processTask_list3',
		type:'post',
		dataType:'json',
		traditional: true,
		data:searchJson,
		success:function(data){
			$('#TM-sloading').hide('normal');
			var theEle = $('.time-q-box').find('._time-body')[0];
			if(typeof(theEle)!='undefined'){
				$(theEle).remove();
			}
			//消除滚动条
			var scoll = $(".sr-box-body").find('#yj-scroll')[0];
			if(typeof(scoll)!='undefined'){
				$(scoll).remove();
			}
			if(data!=null){
				allData = data;
				if(data.total!=null&&data.total>0){
					$(".err_time").css("display","none");
					var option = '';
					option += '<div class="_time-body"><div class="_table-title"><ul>';
						for(var i=0;i<fieldArray.length;i++){
							if(fieldArray[i]=="flt_nbr"){
								option += '<li class="_sort" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i])+'px">'+eval("fields."+fieldArray[i])+'<span class="_sequence" onclick="sort(1)">&#xe675;</span></li>';
							}else if(fieldArray[i]=="lcl_Dpt_Tm"){
								option += '<li class="_hybrid" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i])+'px"><div>'+eval("fields."+fieldArray[i])+'<span class="_sequence" onclick="sort(2)">&#xe675;</span></div>';
								option += '<div><input type="text" id="dptTmStart" placeholder="time"><span>-</span><input type="text" id="dptTmEnd" placeholder="time"></div></li>';
							}else if(fieldArray[i]=="lcl_Arrv_Tm"){
								option += '<li class="_hybrid" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i])+'px"><div>'+eval("fields."+fieldArray[i])+'<span class="_sequence" onclick="sort(3)">&#xe675;</span></div>';
								option += '<div><input type="text" id="arrvTmStart"  placeholder="time"><span>-</span><input type="text" id="arrvTmEnd" placeholder="time"></div></li>';
							}else if(fieldArray[i]=='dpt_AirPt_pot'||fieldArray[i]=='arrv_Airpt_pot'){
								option += '<li class="_depart" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i])+'px">'+eval("fields."+fieldArray[i])+'</li>';
							}else if(fieldArray[i]=='days'){
								option += '<li class="_last" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i])+'px">'+eval("fields."+fieldArray[i])+'</li>';
							}else{
								option += '<li class="_ordinary" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i])+'px">'+eval("fields."+fieldArray[i])+'</li>';
							}
							listWidth += infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[i]);
						}
					option += '</ul></div><div class="_table-box"><div class="_table-over" id="_table-over">';
					var dataArray = data.rows;
					for(var i=0;i<data.total;i++){
						var obj = dataArray[i];
							option += '<ul>';
							for(var j=0;j<fieldArray.length;j++){
								if(fieldArray[j]=='days'){
									if(obj.days!=null&&obj.days!=''){
			                        	option += '<li class="flightDate" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px"><ul class="_day-set">';
			                        	var days = obj.days;
			                        	for(var k=0;k<7;k++){
			                        		if(days.indexOf(weekdayArray[k])>=0){
			                        			option += '<li>'+weekdayArray[k]+'</li>';
			                        		}else{
			                        			option += '<li class="_day-set-im">'+weekdayArray[k]+'</li>';
			                        		}
			                        	}
			                        	option += '</ul></li>';
			                        }
								}else if(fieldArray[j]=='dpt_AirPt_Cd'){
									option += '<li class="dptCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='arrv_Airpt_Cd'){
									option += '<li class="arrvCity" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='flt_nbr'){
									option += '<li class="flt_nbr" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='airCrft_Typ'){
									option += '<li class="airportType" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px;overflow:hidden" title="'+eval('obj.'+fieldArray[j])+'">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='lcl_Dpt_Tm'){
									option += '<li class="dptTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='dpt_AirPt_pot'){
									option += '<li class="dptAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else if(fieldArray[j]=='lcl_Arrv_Tm'){
									option += '<li class="arrvTime" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}else{
									option += '<li class="arrvAirport" style="width:'+infer('.time-q-box')[0]*0.9/100*eval('fieldWidths.'+fieldArray[j])+'px">'+eval('obj.'+fieldArray[j])+'</li>';
								}
							}
	                        option += '</ul>';
					}
	                option += '</div></div></div>';
					$('.time-q-box').append(option);
					reset();//算大小
					if(data.total>12){
						add(".sr-box-body","._table-over",15,100,".sr-box-body","._table-box");
					}else{
						$('._time-body').css({'height':(72+47*data.total)+'px'});
					}
					$('._time-body').css({'width':listWidth+'px'});
				}else{
					$(".err_time").css("display","block");
				}
			}else{
				alert('服务器异常，请稍后再试');
			}
		}
	});
}
$(function(){
    /*测各种块大小*/
    getNewestCollectDate();
    $("._set>div").on("click",function(event){//阻止事件冒泡
        $("._set-list").css("display","block");
        event.stopPropagation();
    });
    $(".drop-down-list>li").on("click",function(){
    	$(".drop-down-show").text($(this).text());
    	$(".drop-down-list").css({"display":"none"});
    });
    $("._set-list").on("click",function(event){ //阻止事件冒泡
        event.stopPropagation();
    });
    $(".sr-box").on("click",function(){
        if($("._set-list").css("display")=="block"){
            $("._set-list").css("display","none");
        }
        if($('.drop-down-list').css('display')=='block'){
        	$('.drop-down-list').css('display','none');
        }
    });
    $(".drop-down-show").on("click",function(event){
    	$(".drop-down-list").css({"display":"block"});
    	event.stopPropagation();
    });
    $('._set-list').on('click','input[type="checkbox"]',function(){
    	var fieldArray = [];
    	$('input[type="checkbox"]').each(function(e){
    		if($(this).is(":checked")==true){
    			fieldArray.push(fieldMap[$(this).next().text()]);
    		}
    	});
    	if(fieldArray.length==0){
    		alert('请至少选择一列显示');
    		$(this).attr('checked',true);
    		return;
    	}
    	changeFieldS(fieldArray);
    });
    //Leng CODE------------------------
    $('body').on('blur','#dptTmStart',function(){
    	getCurrConditionData(1,'dptTmStart');
    });
	$('body').on('blur','#dptTmEnd',function(){
		getCurrConditionData(1,'dptTmEnd');
	});
	$('body').on('blur','#arrvTmStart',function(){
		getCurrConditionData(2,'arrvTmStart');
	});
	$('body').on('blur','#arrvTmEnd',function(){
		getCurrConditionData(2,'arrvTmEnd');
	});
	//LIU CODE------------------------------------
	$('body').on('keyup','#dptTmStart',function(){
		checkL("dptTmStart");
	});
	$('body').on('keyup','#dptTmEnd',function(){
		checkL("dptTmEnd");
	});
	$('body').on('keyup','#arrvTmStart',function(){
		checkL("arrvTmStart");
	});
	$('body').on('keyup','#arrvTmEnd',function(){
		checkL("arrvTmEnd");
	});
	//验证后面输入的时间是否小于前面的时间
	function checkL(id){
		var date = $("#dptTmStart").val();
		var date2 = $("#dptTmEnd").val();
		if(date.indexOf(":")!=-1&&date2.indexOf(":")!=-1){
			checkLD(date,date2,id);
		}
		var date = $("#arrvTmStart").val();
		var date2 = $("#arrvTmEnd").val();
		if(date.length==5&&date2.length==5){
			checkLD(date,date2,id);
		}
	}
	function checkLD(val1,val2,id){
		var vas1 = val1.split(":");
		console.debug(vas1);
		var vas2 = val2.split(":");
		if(parseInt(vas1[0])>parseInt(vas2[0])){
			$("#"+id).val("");
		}
		if(parseInt(vas1[0])==parseInt(vas2[0])&&parseInt(vas1[1])>parseInt(vas2[1])){
			$("#"+id).val("");
		}
		
	}
	//LIU END----------------------------------------
	
    $(window).resize(function(){
        reset();//算大小
    });
    var objs={
        back:function(){
        	//getFlt_Nbr();
        }
    };
    oub = objs;
    airControl(".selectCity",objs);
    $('#cityChoice').val(airports[parent.cityIata].aptChaNam);
    send();
});
function add(idL,idE,speed,sheight,cleN,KHB){
	if($("#yj-scroll").length==0){
		$(".sr-box-body").append("<div id='yj-scroll'></div>");
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
		$(".sr-box-body").mousemove(function(e){
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
	$(cleN).on("mouseup",function(){
		$(cleN).unbind("mousemove");
	});
}
cmp = function( x, y ) { 
	// If both x and y are null or undefined and exactly the same 
	if ( x === y ) { 
	 return true; 
	} 
	 
	// If they are not strictly equal, they both need to be Objects 
	if ( ! ( x instanceof Object ) || ! ( y instanceof Object ) ) { 
	 return false; 
	} 
	 
	//They must have the exact same prototype chain,the closest we can do is
	//test the constructor. 
	if ( x.constructor !== y.constructor ) { 
	 return false; 
	} 
	  
	for ( var p in x ) { 
	 //Inherited properties were tested using x.constructor === y.constructor
	 if ( x.hasOwnProperty( p ) ) { 
	 // Allows comparing x[ p ] and y[ p ] when set to undefined 
	 if ( ! y.hasOwnProperty( p ) ) { 
	  return false; 
	 } 
	 
	 // If they have the same strict value or identity then they are equal 
	 if ( x[ p ] === y[ p ] ) { 
	  continue; 
	 } 
	 
	 // Numbers, Strings, Functions, Booleans must be strictly equal 
	 if ( typeof( x[ p ] ) !== "object" ) { 
	  return false; 
	 } 
	 
	 // Objects and Arrays must be tested recursively 
	 if ( ! Object.equals( x[ p ], y[ p ] ) ) { 
	  return false; 
	 } 
	 } 
	} 
	 
	for ( p in y ) { 
	 // allows x[ p ] to be set to undefined 
	 if ( y.hasOwnProperty( p ) && ! x.hasOwnProperty( p ) ) { 
	 return false; 
	 } 
	} 
	return true; 
	};