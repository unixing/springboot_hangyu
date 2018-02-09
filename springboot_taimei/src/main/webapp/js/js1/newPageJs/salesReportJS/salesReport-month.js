var searchJson={};
//默认参数
var pcname="p-number";   //人数-柱状图
var piename="combined";   //人数-饼图
var legentArray = [];
var pcArray = [];
var pcOldArray = [];
var pcFit = [];
var pcGrp = [];
var pcOldFit = [];
var pcOldGrp = [];
var pieArray = [];
var monthDataArray = [];
var titleList = [];
var airLine = parent.supData.lane;
var airports = parent.airportMap;
var airFltNbr = parent.supData.flight;
var flts = new Array();
var pageflag="month";
if(airFltNbr!=""&&typeof(airFltNbr)!="undefined"){
	flts = airFltNbr.split('/');
}
var currentCheckedMonth = '';
var flight = '';
if(flts.length>=2){
	flight = flts[0]+"/"+flts[0].substring(0,4)+flts[1];
}
$(function(){
	$("._set-query").click(function(e){
		e.stopPropagation(); //屏蔽事件冒泡
		send();
	}) ;
	$(".c-selectCity ").on("input",function(){
		 getFlt_Nbr();
	}) ;
    /*********************************************请求数据********************************************/
	if(airLine!=null&&airLine!=""&&airLine!='undefined'&&airLine!="="&&airLine!="=="){
		var cds = airLine.split("=");
		if(flight!=""){
			$('#goFltNbr').text(flts[0]);
			if(flts[1].length==2){
				$('#backFltNbr').text(flts[0].substring(0,4)+flts[1]);
			}else{
				$('#backFltNbr').text(flts[0].substring(0,5)+flts[1]);
			}
		}
		var flt_nbr_Count = flight;
		if(cds.length==3){
			$.ajax({
				url:'/restful/getExchangereprot',
				type:'get',
				dataType : 'jsonp',
				data:{
					dpt_AirPt_Cd :airports[cds[0]].iata,
		        	Arrv_Airpt_Cd:airports[cds[2]].iata,
		        	pas_stp:airports[cds[1]].iata,
		        	flt_nbr_Count:flt_nbr_Count
				},
				async:false,
				success:function(data){
					if(data!=null&&data.success!=null){
						if($(".change-line-o").hasClass("change-line-o2")){
							$(".change-line-o").removeClass("change-line-o2");
							$(".change-line").removeClass("change-line2");
						};
						if(typeof(data.success.Pas_stp_code)=="undefined"){
							$(".change-line-o").addClass("change-line-o2");
							$(".change-line").addClass("change-line2");
						};
						$('#cityChoice').val(data.success.Dpt_AirPt_Cd);
			    		$('#cityChoice3').val(data.success.Pas_stp);
			    		$('#cityChoice2').val(data.success.Arrv_Airpt_Cd);
			    		var dptabbr = data.success.Dpt_AirPt_Cd + "-" + data.success.Dpt_AirPt_Cd_code;
						var pstabbr = data.success.Pas_stp + "-" + data.success.Pas_stp_code;
						var arrabbr = data.success.Arrv_Airpt_Cd + "-" + data.success.Arrv_Airpt_Cd_code;
						$('#cityChoice').attr("abbr",dptabbr);
						$('#cityChoice3').attr("abbr",pstabbr);
						$('#cityChoice2').attr("abbr",arrabbr);
						$('#departureItia').text(data.success.Dpt_AirPt_Cd_code);
						$('#departureCity').text(data.success.Dpt_AirPt_Cd);
						$('#passItia').text(data.success.Pas_stp_code);
						$('#passCity').text(data.success.Pas_stp);
						$('#arriveItia').text(data.success.Arrv_Airpt_Cd_code);
						$('#arriveCity').text(data.success.Arrv_Airpt_Cd);
						getFlt_Nbr();
					}
				}
			});
		}else{
			$.ajax({
				url:'/restful/getExchangereprot',
				type:'get',
				dataType : 'jsonp',
				data:{
					dpt_AirPt_Cd :airports[cds[0]].iata,
		        	Arrv_Airpt_Cd:airports[cds[1]].iata,
		        	flt_nbr_Count:flt_nbr_Count
				},
				async:false,
				success:function(data){
					if(data!=null&&data.success!=null){
						if($(".change-line-o").hasClass("change-line-o2")){
							$(".change-line-o").removeClass("change-line-o2");
							$(".change-line").removeClass("change-line2");
						};
						if(typeof(data.success.Pas_stp_code)=="undefined"){
							$(".change-line-o").addClass("change-line-o2");
							$(".change-line").addClass("change-line2");
						};
						$('#cityChoice').val(data.success.Dpt_AirPt_Cd);
			    		$('#cityChoice2').val(data.success.Arrv_Airpt_Cd);
			    		var dptabbr = data.success.Dpt_AirPt_Cd + "-" + data.success.Dpt_AirPt_Cd_code;
						var arrabbr = data.success.Arrv_Airpt_Cd + "-" + data.success.Arrv_Airpt_Cd_code;
						$('#cityChoice').attr("abbr",dptabbr);
						$('#cityChoice2').attr("abbr",arrabbr);
						$('.change-line-o').css('display','none');
						$('#departureItia').text(data.success.Dpt_AirPt_Cd_code);
						$('#departureCity').text(data.success.Dpt_AirPt_Cd);
						$('#arriveItia').text(data.success.Arrv_Airpt_Cd_code);
						$('#arriveCity').text(data.success.Arrv_Airpt_Cd);
						getFlt_Nbr();
					}
				}
			});
		}
	}
	$('.checkBtn').on('click',function(){
		$('.checkBtn').each(function(){
			$(this).removeClass('checkFlt');
		});
		$(this).addClass('checkFlt').css({"background-color":"#5a7aa9","color":"white"}).siblings().css({"background-color":"#e0dedf","color":"black"});
        send($(this).text());
	});
/***********************************************canvas区域************************************/
    /***各种封装****/
    $(window).resize(function(){
        //mid();
    });
    changew();
    changeCK();
    var dateChangeFlag = true;
    /*日历控件*/
    $('.sr-box-body-date-body').on("click",".hasData",function(){
    	if(dateChangeFlag){
    		dateChangeFlag = false;
            $(this).addClass('list').siblings().removeClass('list');
            currentCheckedMonth = $('#year').text()+'-'+$(this).text().substring(0,$(this).text().indexOf('月'));
            $('.checkFlt').removeClass('checkFlt');
        	$('#income-set .checkBtn:nth-child(2)').addClass('checkFlt');
            send($('.checkFlt').text());
			setTimeout(function(){
				dateChangeFlag = true;
			},2000);
    	}else{
			console.log('click frequently!');    		
    	}
    });
    getMonths($('.checkFlt').text(),1);
    send($('.checkFlt').text());
    var objs={
        back:function(){
        	getFlt_Nbr();
        }
    };
    oub = objs;
    airControl(".selectCity",objs);
});
function getMonths(flt,type){
	var dpt_AirPt_Cd = $('#cityChoice').val();
	if(dpt_AirPt_Cd!=''){
		dpt_AirPt_Cd = airports[dpt_AirPt_Cd].iata;
	}
	var Arrv_Airpt_Cd = $('#cityChoice2').val();
	if(Arrv_Airpt_Cd!=''){
		Arrv_Airpt_Cd = airports[Arrv_Airpt_Cd].iata;
	}
	var pas_stp = $('#cityChoice3').val();
	if(pas_stp!=''){
		pas_stp = airports[pas_stp].iata;
	}
	var flt_nbr_Count = flt;
	if(typeof(flt)=='undefined'||flt=='往返'){
		flt_nbr_Count = $('._set-list-title').html();
		if(flt_nbr_Count==null||flt_nbr_Count==''){
			$('.time-box').each(function(){
				for(var i=0;i<100;i++){
					$(this).removeClass('hasData');
				}
			});
			return;
		}
	}
	var exceptionData = 'no';
	//判断异常数据是否选中
	if($('#exceptionData').is(':checked')==true){
		exceptionData = 'on';
	}
	
	var year = '';
	if(type==1){
		if($('#year').text()!=''){
			year = $('#year').text();
		}
	}
	$.ajax({
		url:'/getMonths',
		type:'post',
		data:{
			dpt_AirPt_Cd :dpt_AirPt_Cd,
        	Arrv_Airpt_Cd:Arrv_Airpt_Cd,
        	pas_stp:pas_stp,
        	year:year,
        	flt_nbr_Count:flt_nbr_Count,
        	isIncludeExceptionData:exceptionData
		},
		async:false,
		success:function(data){
			if(data!=null){
				if(typeof(data.year)=='undefined'){
					if(data.list!=null&&data.list.length>0){
						var currentYear = currentCheckedMonth.split('-');
						var year = $('#year').text();
						if(year==currentYear[0]){
							$('.time-box').each(function(){
								for(var i=0;i<data.list.length;i++){
									if(parseInt($(this).text().split('月')[0])==data.list[i]){
										$(this).addClass('hasData');
										if(data.list[i]==parseInt(currentYear[1])){//当前选择的一周
											$(this).addClass('list');
											$('.list').css('outline','none');
										}
									}
								}
							});
						}else{
							$('.time-box').each(function(){
								for(var i=0;i<data.list.length;i++){
									if(parseInt($(this).text().split('月')[0])==data.list[i]){
										$(this).addClass('hasData');
									}
								}
							});
						}
					}
				}else{
					if(data.year!=null){
						if(data.list!=null&&data.list.length>0){
							var year=data.year;
							$('#year').text(year);
							var html = '';
							for(var i=0;i<12;i++){
								var flag = false;
								for(var j=0;j<data.list.length;j++){
									if(data.list[j]==i+1){
										flag = true;
										if(j==data.list.length-1){
											currentCheckedMonth = year +'-'+ (i+1);
											html+='<div class="time-box hasData list">'+(i+1)+'月</div>';
										}else{
											html+='<div class="time-box hasData">'+(i+1)+'月</div>';
										}
									}
								}
								if(flag==false){
									html+='<div class="time-box">'+(i+1)+'月</div>';
								}
							}
							$('.sr-box-body-date-body').html(html);
						}
					}else{
						alert('当前条件下没有数据');
					}
				}
			}else{
				alert('服务器异常，请稍后再试');
			}
		},error:function(data){
			console.log(data);
		}
	});
}
function changeFltNbr(){
	var flt_nbr_Count = $('._set-list-title').html();
	flt_nbr_Count = flt_nbr_Count.split('/');
	$('#goFltNbr').text(flt_nbr_Count[0]);
	$('#backFltNbr').text(flt_nbr_Count[1]);
}
function getFlt_Nbr(){
	var dpt_AirPt_Cd = typeof(airports[$('#cityChoice').val()])=='undefined'?'':airports[$('#cityChoice').val()].iata;
	var pas_stp = typeof(airports[$('#cityChoice3').val()])=='undefined'?'':airports[$('#cityChoice3').val()].iata;
	var arrv_Airpt_Cd = typeof(airports[$('#cityChoice2').val()])=='undefined'?'':airports[$('#cityChoice2').val()].iata;
	if(searchJson.dpt_AirPt_Cd!=dpt_AirPt_Cd||pas_stp!=searchJson.pas_stp||arrv_Airpt_Cd!=searchJson.arrv_Airpt_Cd){
		searchJson.dpt_AirPt_Cd = dpt_AirPt_Cd;
		searchJson.pas_stp = pas_stp;
		searchJson.arrv_Airpt_Cd = arrv_Airpt_Cd;
	}else{
		return;
	}
	//表示只查询航线下的航班号的标识。
	searchJson.isFltAir = "true";
	$.ajax({
        type:'post',
        url:'getHbh',//请求数据的地址	
        data:getairCode(searchJson),
        async:false,
        success:function(data){
        	var dats = new Array();
            if(data!=null&&data.list!=null&&data.list.length>0){
            	for(var index in data.list){
                	dats.push(data.list[index]);
                }
            }
            var list={
        	        data:dats, //st节点集合
        	        summary:"false", //是否包含往返 true包含false不包含
        	        name:"._set-list-set",  //添加list节点
        	        cleName:".sr-box",   //取消绑定事件函数节点
        	        flyNbr :flight,
        	        fun:function(){
        	        	var flt_nbr_Count = $('._set-list-title').html();
        	        	flt_nbr_Count = flt_nbr_Count.split('/');
        	        	$('#goFltNbr').text(flt_nbr_Count[0]);
        	        	$('#backFltNbr').text(flt_nbr_Count[1]);
        	        	$('.checkFlt').removeClass('checkFlt');
        	        	$('#income-set .checkBtn:nth-child(2)').addClass('checkFlt');//$("#test tr td:nth-child(2)")获取第二个子元素
        	        	getMonths($('.checkFlt').text(),2);
        	        	send($('.checkFlt').text());
        	        }
        	    };
        	    setChoose(list);
        }
    });
}
//计算iframe的高度
function changeCK(){
	$(".sr-box-body").css("height",infer('body')[1]-39);
}
/*测各种块大小*/
function infer(name){
    var infer=[];
    infer.push(parseFloat($(name).css("width").split("px")[0]));
    infer.push(parseFloat($(name).css("height").split("px")[0]));
    infer.push(parseFloat($(name).css("margin-top").split("px")[0]));
    infer.push(parseFloat($(name).css("left").split("px")[0]));
    return infer;
}
function changew(){
    /*计算中间块大小*/
    var Lwidth=infer(".sr-box-body-report")[0];
    var Zwidth=infer(".sr-box")[0];
    var Rwidth=infer(".sr-box-body-date")[0];
    var Swidth=Zwidth-Lwidth-Rwidth-2;
    $(".sr-box-body-chart").css("width",Swidth+"px");
    /*计算绘图区域大小*/
    var Cheight=infer(".sr-box-body-chart-income")[1]-infer(".p-height")[1]-infer(".p-height")[2]-infer(".d-height")[1]-44;
    var Cwidth=infer(".graph-table")[0];
    $(".graph-table").css("height",Cheight);
    $("#income").attr({"width":Cwidth,"height":Cheight});
    $("#avg_set_ine").attr({"width":Cwidth,"height":Cheight});
    $("#person_dct").attr({"width":Cwidth,"height":Cheight});
	$("#person_avg").attr({"width":Cwidth,"height":Cheight});
}
function send(flt){
	showLoading();
	var dpt_AirPt_Cdtemp = $('#cityChoice').val();
	var pas_stptemp = $('#cityChoice3').val();
	var arrv_Airpt_Cdtemp = $('#cityChoice2').val();
	var flt_nbr_Counttemp = $('._set-list-title').text();
	$(".sr-box-body-chart").removeClass("muhu");
	$(".reportErr").css("display","none");
	$(".sr-box-body-date-footer").css("display","block");
	//关闭所有选择框
	$(".c-selectCity").nextAll().remove();
	$("._set-allList").css({"display":"none"});
	var dpt_AirPt_Cd = $('#cityChoice').val();
	if(dpt_AirPt_Cd!=''){
		dpt_AirPt_Cd = airports[dpt_AirPt_Cd].iata;
	}else{
		alert("请选择起始机场！");
		$(".sr-box-body-chart").addClass("muhu");
		$(".reportErr").css("display","block");
		$(".sr-box-body-date-footer").css("display","none");
		getMonths($('.checkFlt').text(),2);
		return;
	}
	var Arrv_Airpt_Cd = $('#cityChoice2').val();
	if(Arrv_Airpt_Cd!=''){
		Arrv_Airpt_Cd = airports[Arrv_Airpt_Cd].iata;
	}else{
		alert("请选择到达机场！");
		$(".sr-box-body-chart").addClass("muhu");
		$(".reportErr").css("display","block");
		$(".sr-box-body-date-footer").css("display","none");
		getMonths($('.checkFlt').text(),2);
		return;
	}
	//查询条件联动
	
	var object = parent.supData;
	if(pas_stptemp!=""){
		object.lane =dpt_AirPt_Cdtemp + "=" + pas_stptemp + "=" + arrv_Airpt_Cdtemp ;
	}else{
		object.lane =dpt_AirPt_Cdtemp + "=" + arrv_Airpt_Cdtemp ;
	}
	if(flt_nbr_Counttemp!=""){
		var flttemp = flt_nbr_Counttemp.split("/");
		var ff = flttemp[0]+"/"+flttemp[1].substring(flttemp[1].length-2,flttemp[1].length);
		object.flight = ff;
	}
	var pas_stp = $('#cityChoice3').val();
	if(pas_stp!=''){
		pas_stp = airports[pas_stp].iata;
	}
	var flt_nbr_Count = flt;
	if(typeof(flt)=='undefined'||flt=='往返'){
		flt_nbr_Count = $('._set-list-title').html();
		if(flt_nbr_Count==null||flt_nbr_Count==''){
			alert("没有对应的航班号！");
			$(".sr-box-body-chart").addClass("muhu");
    		$(".reportErr").css("display","block");
    		$(".sr-box-body-date-footer").css("display","none");
    		getMonths($('.checkFlt').text(),2);
			return;
		}
		//默认选中往返
        $("#income-set").find("li").eq(0).css("background-color","rgb(224, 222, 223)");
        $("#income-set").find("li").eq(1).css("background-color","rgb(90, 122, 169)");
        $("#income-set").find("li").eq(2).css("background-color","rgb(224, 222, 223)");
        $("#income-set").find("li").eq(0).css("color","black");
        $("#income-set").find("li").eq(1).css("color","white");
        $("#income-set").find("li").eq(2).css("color","black");
	}
	var exceptionData = 'no';
	//判断异常数据是否选中
	if($('#exceptionData').is(':checked')==true){
		exceptionData = 'on';
	}

	var exceptionFlyData = 'no';
	//判断异常航班是否选中
	if($('#exceptionFlyData').is(':checked')==true){
		exceptionFlyData = 'on';
	}
	
	var month = $('.list').text();
	month = parseInt(month.substring(0,month.indexOf('月')));
	var year = $('#year').text();
	var firstDay = '';
	var lastDay = '';
	if(typeof(month) !='undefined'){
		switch(month){
			case 1:case 3:case 5:case 7:case 8:
				firstDay = year+'-0'+month+'-01';
				lastDay = year+'-0'+month+'-31';
				break;
			case 10:case 12:
				firstDay = year+'-'+month+'-01';
				lastDay = year+'-'+month+'-31';
				break;
			case 4:case 6:case 9:
				firstDay = year+'-0'+month+'-01';
				lastDay = year+'-0'+month+'-30';
				break;
			case 11:
				firstDay = year+'-'+month+'-01';
				lastDay = year+'-'+month+'-30';
				break;
			case 2:
				year = parseInt(year);
				if((year%4==0&&year%100!=0)||year%400==0){
					firstDay = year+'-0'+month+'-01';
					lastDay = year+'-0'+month+'-29';
				}else{
					firstDay = year+'-0'+month+'-01';
					lastDay = year+'-0'+month+'-28';
				}
				break;
		}
	}
	//时间保存起来，用于其它功能
    object.startTime = firstDay;
    object.endTime = lastDay;
    if(new Date(parseInt(object.endTime.split("-")[0]),parseInt(object.endTime.split("-")[1]),parseInt(object.endTime.split("-")[2])).getTime()>new Date().getTime()){
		object.endTime = new Date().format('yyyy-MM-dd');
	}
	//获取页面参数
	$.ajax({
        type:'get',
        url : '/restful/getMonthlyReportDataNew',
        dataType : 'jsonp',
        data:{
        	dpt_AirPt_Cd :dpt_AirPt_Cd,
        	Arrv_Airpt_Cd:Arrv_Airpt_Cd,
        	pas_stp:pas_stp,
        	flt_nbr_Count:flt_nbr_Count,
        	startTime:firstDay,
        	endTime:lastDay,
        	isIncludeExceptionData:exceptionData,
        	isIncludeExceptionHuangduan:exceptionFlyData
        },
        success : function(data) {
            closeLoading();
            if(Number(data.success.executiveClass) !=0){
            	//判定航班号组成，构造航线
            	if(flt_nbr_Count.indexOf('/')>-1){
            		fakeCss('.change-line-o:before,.change-line-l::before{content:"\\e683"}');
            	}else{
            		//取单一航班号最后一位数
            		var lastChar = flt_nbr_Count.substring(flt_nbr_Count.length-1);
            		//奇数为去程，偶数为返程
            		if(parseInt(lastChar)%2==0){
            			fakeCss('.change-line-o:before,.change-line-l::before{content:"\\e697"}');
            		}else{
            			fakeCss('.change-line-o:before,.change-line-l::before{content:"\\e698"}');
            		}
            	}
            	if(data.success.goNum!=""&&data.success.backNum!=""){
            		$('#goFltNbr').text(data.success.goNum);
                	$('#backFltNbr').text(data.success.backNum);
            	}

            	createChanger(data, firstDay, lastDay, flt_nbr_Count, 1);
            	resetTableData();
				create_hd(data);
				naviChip= 0;
				dataCompare(data.success.newmap.everyList[0], false, data.success.newmap.everyList[0]);
                saveData=data;
                if(Number(data.success.newmap.everyList[0].dataMap.hbys) == 0){
                	$(".sr-box-body-chart").addClass("muhu");
            		$(".reportErr").css("display","block");
                }
            }else{
            	$(".sr-box-body-chart").addClass("muhu");
        		$(".reportErr").css("display","block");            	
            }
    		$('#departureItia').text(dpt_AirPt_Cd);        		
    		$('#arriveItia').text(Arrv_Airpt_Cd);
    		$('#passItia').text(pas_stp);
    		pas_stp == '' ? $(".change-line").addClass("change-line2"): $(".change-line").removeClass("change-line2");    		
    		pas_stp == '' ? $('.change-line-o').addClass('change-line-o2'): $('.change-line-o').removeClass('change-line-o2');
        },
        error : function() {
            closeLoading();
    		$(".sr-box-body-chart").addClass("muhu");
    		$(".reportErr").css("display","block");        	
        }
    });
}
function fakeCss(t){
	s=document.createElement('style');
	s.innerText=t;
	document.body.appendChild(s);
}
function mergeArray(array1,array2){
	for(var i =0;i<array1.length;i++){
		for(var j=0;j<array2.length;j++){
			if(array1[i]==array2[j]){
				array1.splice(i,1);
				i--;
				break;
			}
		}
	}
	for(var i=0;i<array2.length;i++){
		array1.push(array2[i]);
	}
	return array1;
}

/**********************绘图函数*********echarts******************/
/*均线图*/
function theCurve(name,axisArray,info,oldinfo){
    var dom = document.getElementById(name);
    var myChart = echarts.init(dom);
    option = {
        grid: {
            left: '4%',
            right: '20%',
            bottom: '5%',
            containLabel: true
        },
        tooltip : {
            trigger: 'axis',
            formatter:function(params){
            	if(typeof(params.length)=='number'){
            		return typeof(params[0].value)=='undefined'?'':$.digitization(params[0].value)+'%';
            	}else{
            		return params.name+':'+$.digitization(params.value)+'%';
            	}
            },
            borderColor:'#d85430',
            borderWidth:1
        },
        calculable : true,
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: axisArray,
            silent:true,
            axisLine:{
                show:true,
                lineStyle:{
                    color:"#2e416c"
                }
            },
            splitLine:{
                show:true,
                lineStyle:{
                    color:"#304b76",
                    opacity:0.6
                }
            },
            axisLabel:{
           	 	interval:0,
	           	textStyle:{
	                 color:"white"
	            }
           }
        },
        yAxis : [
            {
                type : 'value',
                axisLine:{
                    show:false,
                    onZero:false
                },
                axisLabel:{
                    show:false
                },
                splitLine:{
                    show:false
                },
                axisTick:{
                    show:false
                }
            }
        ],
        series : [
            {
                name: "本月",
                type: 'line',
                smooth:true,
                label: {
                    normal: {
                        show: false,
                        position: 'top'
                    }
                },
                symbol:'circle',
	           	symbolSize:'10',
                lineStyle: {
                    normal: {
                        width: 4,
                        color: '#d85332'
                    }
                },
                data: info,
                markLine: {
                    data: [
                        {
                            type: 'average',
                            name: '平均值',
                            label: {
                                normal: {
                                    position: 'end',
                                    formatter: '本月平均\n{c}',
                                    color:"#d85332"
                                }
                            },
                            lineStyle:{
                                normal:{
                                    color:"#d95330"
                                }
                            }
                        }
                    ]
                }
            },
            {
                name: "上月",
                type: 'line',
                smooth:true,
                label: {
                    normal: {
                        show: false,
                        position: 'top'
                    }
                },
                symbol:'circle',
                symbolSize:'0',
                lineStyle: {
                    normal: {
                        width: 0,
                        color: '#bd5741'
                    }
                },
//                data: oldinfo,
                markLine: {
                    data: [
                        {
                            type: 'average',
                            name: '平均值',
                            label: {
                                normal: {
                                    position: 'end',
                                    formatter: ''
                                }
                            }},
                            {
                            type: 'average',
                            name: '平均值',
                            label: {
                                normal: {
                                    position: 'end',
                                    formatter: '上月平均\n{c}'
                                }
                            },
                            lineStyle:{
                                normal:{
                                    color:"#87c4e1"
                                }
                            }
                        }
                    ]
                }
            }
        ]
    };
    myChart.setOption(option,true);
};

/*均线图*/
function theNewCurve(name,axisArray,info,oldinfo,content){
    var dom = document.getElementById(name);
    var myChart = echarts.init(dom);
    option = {
        grid: {
            left: '4%',
            right: '20%',
            bottom: '5%',
            containLabel: true
        },
        tooltip : {
            trigger: 'axis',
            formatter:function(params){
            	if(typeof(params.length)=='number'){
            		return content[params[0].name];
            	}else{
            		return params.name+':'+$.digitization(params.value);
            	}
            },
            borderColor:'#d85430',
            borderWidth:1
        },
        calculable : true,
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: axisArray,
            silent:true,
            axisLine:{
                show:true,
                lineStyle:{
                    color:"#2e416c"
                }
            },
            splitLine:{
                show:true,
                lineStyle:{
                    color:"#304b76",
                    opacity:0.6
                }
            },
            axisLabel:{
            	 interval:0,
 	           	textStyle:{
	                 color:"white"
	            }
            }
        },
        yAxis : [
            {
                type : 'value',
                axisLine:{
                    show:false,
                    onZero:false
                },
                axisLabel:{
                    show:false
                },
                splitLine:{
                    show:false
                },
                axisTick:{
                    show:false
                }
            }
        ],
        series : [
            {
                name: "本月",
                type: 'line',
                smooth:true,
                label: {
                    normal: {
                        show: false,
                        position: 'top'
                    }
                },
                symbol:'circle',
	           	symbolSize:'10',
                lineStyle: {
                    normal: {
                        width: 4,
                        color: '#d85332'
                    }
                },
                data: info,
                markLine: {
                    data: [
                        {
                            type: 'average',
                            name: '平均值',
                            label: {
                                normal: {
                                    position: 'end',
                                    formatter: '本月平均\n{c}',
                                    color:"#d85332"
                                }
                            },
                            lineStyle:{
                                normal:{
                                    color:"#d95330"
                                }
                            }
                        }
                    ]
                }
            },
            {
                name: "上月",
                type: 'line',
                smooth:true,
                label: {
                    normal: {
                        show: false,
                        position: 'top'
                    }
                },
                symbol:'circle',
                symbolSize:'0',
                lineStyle: {
                    normal: {
                        width: 0,
                        color: '#bd5741'
                    }
                },
//                data: oldinfo,
                markLine: {
                    data: [
                        {
                            type: 'average',
                            name: '平均值',
                            label: {
                                normal: {
                                    position: 'end',
                                    formatter: ''
                                }
                            }},
                            {
                            type: 'average',
                            name: '平均值',
                            label: {
                                normal: {
                                    position: 'end',
                                    formatter: '上月平均\n{c}'
                                }
                            },
                            lineStyle:{
                                normal:{
                                    color:"#87c4e1"
                                }
                            }
                        }
                    ]
                }
            }
        ]
    };
    myChart.setOption(option,true);
};

/*柱状图*/
function barThourthReport(id,axisArray,info,oldInfo){
    var dom = document.getElementById(id);
    //用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
    var myChart = echarts.init(dom);
    option = {
        grid: {
            left: '4%',
            right: '20%',
            bottom: '5%',
            containLabel: true
        },
        tooltip: {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter:function(params){
            	if(typeof(params.length)=='number'){
            		return $.digitization(params[0].value);
            	}else{
            		return params.name+':'+$.digitization(params.value);
            	}
            },
            borderColor:'#d85430',
            borderWidth:1
        },
        toolbox: {
            show: false
        },
        xAxis:  {
            type: 'category',
            boundaryGap: false,
            data: axisArray,
            axisTick: {
                alignWithLabel: true
            },
            axisLine:{
                show:true,
                lineStyle:{
                    color:'#283d68'
                }
            },
            axisLabel:{
           	 	interval:0,
	           	textStyle:{
	                 color:"white"
	            }
           }
        },
        yAxis: {
            show:false,
            type: 'value',
            axisLabel: {
                formatter: '{value} °C',
	           	textStyle:{
	                 color:"white"
	            }
            }
        },
        series: [
            {
                name:'本月均班',
                type:'bar',
                stack:'total',
                label:{
                    normal:{
                        position:'top'
                    }
                },
                symbol:'circle',
	           	symbolSize:'10',
                barWidth: 10,
                itemStyle:{
                    normal:{
                        color:'#d85430',
                        label : {show: true, position: 'top',textStyle: {color: '#fff'}}
                    }
                },
                data:info,
                markLine: {
                    data: [
                        {
                            type: 'average',
                            name: '平均值',
                            label: {
                                normal: {
                                    position: 'end',
                                    formatter: ''
                                }
                            }},
                        {
                            type: 'average',
                            name: '平均值',
                            label: {
                                normal: {
                                    position: 'end',
                                    formatter: '  本月平均\n{c}'
                                }
                            }}
                    ]
                }
            },
            {
                name: "上月均班",
                type: 'line',
                smooth:true,
                label: {
                    normal: {
                        show: false,
                        position: 'top'
                    }
                },
                symbol:'circle',
                symbolSize:'0',
                lineStyle: {
                    normal: {
                        width: 0,
                        color: '#bd5741'
                    }
                },
//                data: oldInfo,
                markLine: {
                    data: [
                        {
                            type: 'average',
                            name: '平均值',
                            label: {
                                normal: {
                                    position: 'end',
                                    formatter: ''
                                }
                            }},
                        {
                            type: 'average',
                            name: '平均值',
                            label: {
                                normal: {
                                    position: 'end',
                                    formatter: '  上月平均\n{c}'
                                }
                            },
                            lineStyle:{
                                normal:{
                                    color:"#7ec0da"
                                }
                            }
                        }
                    ]
                }
            }
        ]
    };
    myChart.setOption(option,true);
    myChart.on("mouseover",function(parmes){
    	if(parmes.seriesName=="本月均班"&&parmes.name!='平均值'){
    		pieFivthReport(piename,monthDataArray[parmes.name]);
    	}
    });
    myChart.on("mouseout",function(parmes){
    	pieFivthReport(piename,pieArray);
    });
}
/*饼状图*/
function pieFivthReport(id,monthData){
	var pcData = [];
	pcData.push({value:monthData[0], name:'团队总人数',selected:true,itemStyle:{normal:{color:'#1470c5',}}});
	pcData.push({value:monthData[1], name:'散客总人数',itemStyle:{normal:{color:'#64c7d9',}}});
    var dom = document.getElementById(id);
    //用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
    var myChart = echarts.init(dom);
    myChart.option = {
        tooltip: {
            trigger: 'item',
            formatter:function(params){
            	return params.name+'：'+params.value;
            },
            borderColor:'#d85430',
            borderWidth:1
        },
        legend: {
            orient: 'vertical',
            left:"55%",
            itemWidth:10,
            itemHeight:10,
            textStyle:{color:'#fff'},
            data:['团队总人数','散客总人数'],
            formatter:function(params){
                if(params=='团队总人数'){
                    return params+'\n'+$.digitization(monthData[0])+'人';
                }else{
                    return params+'\n'+$.digitization(monthData[1])+'人';
                }
            }
        },
        series: [
            {
                name:'访问来源',
                type:'pie',
                selectedMode: 'single',
                radius: [0, '70%'],
                center:["25%","50%"],
//                silent:true,
                label: {
                    normal: {
                    	show:false,
                        position: 'center',
//                        formatter:function(params){
//                            if(params.dataIndex==1){
//                                return '合计';
//                            }
//                            return "";
//                        },
                        textStyle:{
                            fontSize:'20',
                            color:'#6aa2bd'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:pcData
            }
        ]
    };
    myChart.setOption(myChart.option,true);
    myChart.on("mouseover",function(parmes){
    	if(parmes.name=="团队总人数"){
    		barThourthReport(pcname,trueLegentArray,pieBarData(monthDataArray, true), []);
    	}else{
    		barThourthReport(pcname,trueLegentArray,pieBarData(monthDataArray, false), []);
    	}
    });
    myChart.on("mouseout",function(parmes){
    	myChart.option.series[0].data[0].selected=true;
    	myChart.setOption(myChart.option,true);
    	barThourthReport(pcname,trueLegentArray,fourChartA,[]);
    });
}
function lastyear(obj){
	var theYear = parseInt($(obj).next().text())-1;
	var html = '';
	for(var i=1;i<=12;i++){
		html+='<div class="time-box">'+i+'月</div>';
	}
	$('.sr-box-body-date-body').html(html);
	$(obj).next().text(theYear);
	getMonths($('.checkFlt').text(),1);
}
function nextyear(obj){
	var theYear = parseInt($(obj).prev().text())+1;
	var html = '';
	for(var i=1;i<=12;i++){
		html+='<div class="time-box">'+i+'月</div>';
	}
	$('.sr-box-body-date-body').html(html);
	$(obj).prev().text(theYear);
	getMonths($('.checkFlt').text(),1);
}
function getairCode(searchJson){
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