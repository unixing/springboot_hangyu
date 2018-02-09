var searchJson={};

var airLine = parent.supData.lane;
var airports = parent.airportMap;
var airFltNbr = parent.supData.flight;
var flts = new Array();
var yearsList = new Object();
if(airFltNbr!=""&&typeof(airFltNbr)!="undefined"){
	flts = airFltNbr.split('/');
}
var currentCheckedYear = '';
var flight = '';
var indexflag = 1;
var myyearflag = null;
var first_a=[],first_b=[],first_c=[],ofirst_a=[],ofirst_b=[],ofirst_c=[],three_a=[],three_b=[],othree_a=[],othree_b=[],four_a=[],four_b=[],ofour_a=[],ofour_b=[],AyearArray=[],AoyearArray=[];//,AyearArray=[],AyearArray=[],AyearArray=[],AyearArray=[];


parent.supData.startTime = undefined;
parent.supData.endTime = undefined;
if(flts.length>=2){
	flight = flts[0]+"/"+flts[0].substring(0,4)+flts[1];
}
function getYears(flt){
	$(".c-selectCity").nextAll().remove();
	$("._set-allList").css({"display":"none"});
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
		return;
	}
	//查询条件联动
	var dpt_AirPt_Cdtemp = $('#cityChoice').val();
	var pas_stptemp = $('#cityChoice3').val();
	var arrv_Airpt_Cdtemp = $('#cityChoice2').val();
	var flt_nbr_Counttemp = $('._set-list-title').text();
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
	var exceptionData = 'no';
	//判断异常数据是否选中
	if($('#exceptionData').is(':checked')==true){
		exceptionData = 'on';
	}
	var flt_nbr_Count = flt;
	if(typeof(flt)=='undefined'||flt=='往返'){
		flt_nbr_Count = $('._set-list-title').html();
		if(flt_nbr_Count==null||flt_nbr_Count==''){
			alert("没有对应的航班号！");
			$(".sr-box-body-chart").addClass("muhu");
    		$(".reportErr").css("display","block");
    		$(".sr-box-body-date-footer").css("display","none");
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
	//获取页面参数
	$.ajax({
        type:'post',
        url : '/getYears',
        dataType : 'json',
        data:{
        	dpt_AirPt_Cd :dpt_AirPt_Cd,
        	Arrv_Airpt_Cd:Arrv_Airpt_Cd,
        	pas_stp:pas_stp,
        	flt_nbr_Count:flt_nbr_Count,
        	isIncludeExceptionData:exceptionData
        },
        async:false,
        success : function(data) {
        	var date = new Date();
    		if(data!=null&&data.list!=null&&data.list.length>0){
    			var list = data.list;
    			yearsList = data.list;
    			var year = parseInt(list[list.length-1]);
    			currentCheckedYear = year;
        		var startYear = year-4;
        		var endYear = year+1;
        		//赋值年份序列
        		$('#years').text(startYear+'-'+endYear);
        		var html = '';
        		for(var i=startYear;i<=endYear;i++){
        			if($.inArray(i+'',list)>-1){
        				if(year==i){
        					html+='<div class="year-btn hasData list">'+i+'</div>';
        				}else{
        					html+='<div class="year-btn hasData">'+i+'</div>';
        				}
        			}else{
        				html+='<div class="year-btn">'+i+'</div>';
        			}
        		}
        		$('.sr-box-body-date-body').html(html);
        		myyearflag = year;
    		}else{
    			var year = date.getFullYear();
    			currentCheckedYear = year;
        		var startYear = year-4;
        		var endYear = year+1;
        		//赋值年份序列
        		$('#years').text(startYear+'-'+endYear);
        		var html = '';
        		for(var i=startYear;i<=endYear;i++){
        			html+='<div class="year-btn">'+i+'</div>';
        		}
        		$('.sr-box-body-date-body').html(html);
    		}
        }
    });
}
$(function(){
	$("._set-query").click(function(e){
		e.stopPropagation(); //屏蔽事件冒泡
		send();
	}) ;
    /*********************************************请求数据********************************************/
	if(airLine!=null&&airLine!=""&&airLine!='undefined'&&airLine!="="&&airLine!="=="){
		var cds = airLine.split("="), dateChangeFlag = true;
		$('.sr-box-body-date-body').on('click','.hasData',function(){
			if(dateChangeFlag){
				dateChangeFlag = false;
				$('.year-btn').each(function(e){
					$(this).removeClass('list');
				});
				$(this).addClass('list');
				currentCheckedYear = $(this).text();
				send();
				setTimeout(function(){
					dateChangeFlag = true;
				},5000);
			}else{
				console.log('click frequently!');
			}
		});
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
					}
				}
			});
		}
	}
	getFlt_Nbr();
/***********************************************canvas区域************************************/
    /***各种封装****/
    $(window).resize(function(){
        //mid(saveData);
    });
    
    $("body").mouseup(function(){//鼠标松开取消事件绑定
        $("#income-set>li:nth-of-type(3)").unbind("mousemove");
    });
    $('.checkBtn').on('click',function(){
		$('.checkBtn').each(function(){
			$(this).removeClass('checkFlt');
		});
		$(this).addClass('checkFlt').css({"background-color":"#5a7aa9","color":"white"}).siblings().css({"background-color":"#e0dedf","color":"black"});
        send($(this).text());
	});
    changeCK();
    /*窗口变化自适应*/
    window.onresize=function(){
        //send($('.checkBtn').text());
    };
    var objs={
        back:function(){
        	getFlt_Nbr();
        }
    };
    oub = objs;
    airControl(".selectCity",objs);
});
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
	}
	//表示只查询航线下的航班号的标识。
	searchJson.isFltAir = "true";
	$.ajax({
        type:'post',
        url:'getHbh',//请求数据的地址	
        data:getairCode(searchJson),
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
        	        }
        	    };
        	    setChoose(list);
        	    if(indexflag==1){
        	    	getYears();
        	    	send();
        	    }
        	    indexflag ++;
        }
    });
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
//计算iframe的高度
function changeCK(){
	$(".sr-box-body").css("height",infer('body')[1]-39);
}
function send(flt){
    showLoading();
	$(".c-selectCity").nextAll().remove();
	$("._set-allList").css({"display":"none"});
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
		return;
	}
	//查询条件联动
	var dpt_AirPt_Cdtemp = $('#cityChoice').val();
	var pas_stptemp = $('#cityChoice3').val();
	var arrv_Airpt_Cdtemp = $('#cityChoice2').val();
	var flt_nbr_Counttemp = $('._set-list-title').text();
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
	var flt_nbr_Count = flt;
	if(typeof(flt)=='undefined'||flt=='往返'){
		flt_nbr_Count = $('._set-list-title').html();
		if(flt_nbr_Count==null||flt_nbr_Count==''){
			alert("没有对应的航班号！");
			$(".sr-box-body-chart").addClass("muhu");
    		$(".reportErr").css("display","block");
    		$(".sr-box-body-date-footer").css("display","none");
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
	var year = $('.list').text();
	var startTime = year+'-01-01';
	var endTime = year+'-12-31';	
	//获取页面参数
	$.ajax({
        type:'get',
        url : '/restful/getYearlyReportDataNew',
        dataType : 'jsonp',
        data:{
        	dpt_AirPt_Cd :dpt_AirPt_Cd,
        	Arrv_Airpt_Cd:Arrv_Airpt_Cd,
        	pas_stp:pas_stp,
        	flt_nbr_Count:flt_nbr_Count,
        	startTime:startTime,
        	endTime:endTime,
        	isIncludeExceptionData:exceptionData,
        	isIncludeExceptionHuangduan:exceptionFlyData
        },
        success : function(data) {
            closeLoading();
            if(Number(data.success.newmap.everyList[0].dataMap.zys) > 0){
            	$(".sr-box-body-chart").removeClass("muhu");
        		$(".reportErr").css("display","none");  
            	var bz = true;
	        	//判断有无数据
	        	if(data.success.executiveClass > 0){
	        		bz = false;
	        	}
	        	if(bz){
	        		$(".sr-box-body-chart").addClass("muhu");
	        		$(".reportErr").css("display","block");
	        	}else{
	        		$(".sr-box-body-chart").removeClass("muhu");
	        		$(".reportErr").css("display","none");
	        	}
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
                saveData=data;
                bclist=[];
                resetTableData();
            	createChanger(data, startTime, endTime, flt_nbr_Count, 3);
                create_hd_s(data,year);
                newmid(data.success.newmap.everyList[0],year,false,data.success.newmap.everyList[0]);
                //mid(data,year);
                //放入有数据的日期                
                var enddate= year + '01'; 
                var startdate = year + '12'
                var orgainEdate = '', orgainSdate = '';
                for(odate of Object.keys(data.success.newmap.everyList[0].dataMap.monthData)){                	
                	if(Number(odate.replace(/-/g,'')) <= Number(startdate)){
                		startdate = odate.replace(/-/g,'');
                		orgainSdate = odate;
                	}
                	if(Number(odate.replace(/-/g,'')) >= Number(enddate)){
                		enddate = odate.replace(/-/g,'');
                		orgainEdate = odate;
                	}
                }                	
                parent.supData.startTime = orgainSdate + '-01';
                parent.supData.endTime = orgainEdate + '-30';	//后期需要判断该月最后一天
            }else{
            	clearAllarr();
            	$(".sales-check-co ul.leglist").empty();
            	$(".sr-box-body-chart").addClass("muhu");
        		$(".reportErr").css("display","block");
            }
        },
        error : function() {
            closeLoading();
        	clearAllarr();
        	$(".sales-check-co ul.leglist").empty();
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
//function mid(data,year){
//	if(data!=null&&data.success.newmap.everyList[0].dataMap!=null){
//		$('#yearIncome').text((typeof(data.success.newmap.everyList[0].dataMap.zys)=='undefined'||data.success.newmap.everyList[0].dataMap.zys==null||data.success.newmap.everyList[0].dataMap.zys=='')?0.00:$.digitization(data.success.newmap.everyList[0].dataMap.zys));
//		$('#hourIncome').text((typeof(data.success.newmap.everyList[0].dataMap.xsys)=='undefined'||data.success.newmap.everyList[0].dataMap.xsys==null||data.success.newmap.everyList[0].dataMap.xsys=='')?0.00:$.digitization(data.success.newmap.everyList[0].dataMap.xsys));
//		$('#raskIncome').text((typeof(data.success.newmap.everyList[0].dataMap.zgl)=='undefined'||data.success.newmap.everyList[0].dataMap.zgl==null||data.success.newmap.everyList[0].dataMap.zgl=='')?0.00:data.success.newmap.everyList[0].dataMap.zgl);
//		$('#classAvgInner').text(formatNumber(toNum(data.success.newmap.everyList[0].dataMap.zys)/toNum(data.success.newmap.everyList[0].dataMap.zbc),2,1));
//		$('#yearAvgPlf').text((typeof(data.success.newmap.everyList[0].dataMap.kzl)=='undefined'||data.success.newmap.everyList[0].dataMap.kzl==null||data.success.newmap.everyList[0].dataMap.kzl=='')?0.00+'%':data.success.newmap.everyList[0].dataMap.kzl+'%');
//		$('#yearTotalPC').text((data.success.newmap.everyList[0].dataMap.zrs=='undefined'?(0+'人/'):$.digitization(parseInt(data.success.newmap.everyList[0].dataMap.zrs)))+'人/'+(data.success.newmap.everyList[0].dataMap.zbc=='undefined'?(0+'班'):$.digitization(data.success.newmap.everyList[0].dataMap.zbc)+'班'));
//		$("#yearAvgPC").text( data.success.newmap.everyList[0].dataMap.jbrs +'人/'+ data.success.newmap.everyList[0].dataMap.zbc +'班');
//		//$("#yearAvgPC").text(((data.success.newmap.everyList[0].dataMap.zrs=='undefined'||parseInt(data.success.newmap.everyList[0].dataMap.zrs)==0)?0:((data.success.newmap.everyList[0].dataMap.zbc=='undefined'||parseInt(data.success.newmap.everyList[0].dataMap.zbc)==0)?0:$.digitization((parseInt(data.success.newmap.everyList[0].dataMap.zrs)/parseInt(data.success.newmap.everyList[0].dataMap.zbc)).toFixed(2))))+'人/'+(data.success.newmap.everyList[0].dataMap.zbc=='undefined'?0:$.digitization(data.success.newmap.everyList[0].dataMap.zbc))+'班');
//		//$('.bc').empty();
//		$('.bc').text('实际班次：'+((typeof(data.success.executiveClass)=='undefined'||data.success.executiveClass==null||data.success.executiveClass=='')?0:$.digitization(data.success.executiveClass))+'班');
////		$('.bc').text('计划班次：'+((typeof(data.success.planClass)=='undefined'||data.success.planClass==null||data.success.planClass=='')?0:$.digitization(data.success.planClass))+'班/实际班次：'+((typeof(data.success.executiveClass)=='undefined'||data.success.executiveClass==null||data.success.executiveClass=='')?0:$.digitization(data.success.executiveClass))+'班');
//		var legentArray = [];
//		var incomeArray = [];
//		var plfArray = [];
//		var disArray = [];
//		var pcArray = [];
//		var hourIncomeArray = [];
//		var raskArray = [];
//		var incomeOldArray = [];
//		var plfOldArray = [];
//		var disOldArray = [];
//		var pcOldArray = [];
//		var disBcArray = new Map();
//		var disBcOldArray = new Map();
//		var pcBcArray = new Map();
//		var pcBcOldArray = new Map();
//		var showContentArray = new Map();
//		var showOldContentArray = new Map();
//		var yearArray = [year-1+'年',year+'年'];
//		if(data.success.newmap.everyList[0].dataMap.monthData!=null){
//			for(var key in data.success.newmap.everyList[0].dataMap.monthData){
//				var keys = parseInt(key.split('-')[1])+'月';
//				legentArray.push(keys);
//				var obj = data.success.newmap.everyList[0].dataMap.monthData[key];
//				showContentArray[keys] = key.split('-')[0]+'年：'+keys+'<br/>航班营收：'+$.digitization(obj.hbys)+'<br/>小时营收：'+$.digitization(obj.xssr)+'<br/>座公里收入：'+obj.set_Ktr_Ine;
//        		incomeArray.push(parseFloat(obj.hbys).toFixed(2));
//        		hourIncomeArray.push(obj.xssr);
//        		raskArray.push(obj.set_Ktr_Ine);
//        		plfArray.push(parseFloat(obj.pjkzl).toFixed(2));
//        		disArray.push(parseFloat(parseFloat(obj.banci)*parseFloat(obj.jbrs)).toFixed(2));
//        		pcArray.push(parseFloat(obj.jbrs).toFixed(2));
//        		disBcArray[keys] = key.split('-')[0]+'年：'+ obj.stzsr+'人/'+$.digitization(parseFloat(obj.banci))+'班';        		
//        		pcBcArray[keys] = key.split('-')[0]+'年：'+$.digitization(parseFloat(obj.jbrs).toFixed(2))+'人/'+$.digitization(parseFloat(obj.banci))+'班';
//			}
//			if(data.success.newmap.everyList[0].dataMap.lastmonthData!=null){
//				for(var key in data.success.newmap.everyList[0].dataMap.lastmonthData){
//					var keys = parseInt(key.split('-')[1])+'月';
//					var obj = data.success.newmap.everyList[0].dataMap.lastmonthData[key];
//					showOldContentArray[keys] = key.split('-')[0]+'年：'+keys+'<br/>航班营收：'+$.digitization(obj.hbys)+'<br/>小时营收：'+$.digitization(obj.xssr)+'<br/>座公里收入：'+obj.set_Ktr_Ine;
//					incomeOldArray.push(parseFloat(obj.hbys).toFixed(2));
//					plfOldArray.push(parseFloat(obj.pjkzl).toFixed(2));
//					disOldArray.push(parseFloat(parseFloat(obj.banci)*parseFloat(obj.jbrs)).toFixed(2));
//					pcOldArray.push(parseFloat(obj.jbrs).toFixed(2));
//					disBcOldArray[keys] = key.split('-')[0]+'年：'+$.digitization(parseFloat(parseFloat(obj.banci)*parseFloat(obj.jbrs)).toFixed(2))+'人/'+$.digitization(parseFloat(obj.banci))+'班';
//					pcBcOldArray[keys] = key.split('-')[0]+'年：'+$.digitization(parseFloat(obj.jbrs).toFixed(2))+'人/'+$.digitization(parseFloat(obj.banci))+'班';
//				}
//			}
//            var name="income"; //收入信息
//            theNewCurve(yearArray,name,legentArray,incomeArray,incomeOldArray,showContentArray,showOldContentArray);
//            var name="avg_set_ine"; //综合客座率
//            theCurve(yearArray,name,legentArray,plfArray,plfOldArray);
//            var name="person_dct"; //平均折扣
//            newTheCurve(yearArray,name,legentArray,disBcArray,disBcOldArray,disArray,disOldArray);
//            var name="person_avg";   //人数-柱状图
//            newTheCurve(yearArray,name,legentArray,pcBcArray,pcBcOldArray,pcArray,pcOldArray);
//            changew();  //重新计算大小
//		}else{
//			var name="income"; //收入信息
//            theNewCurve(yearArray,name,legentArray,incomeArray,incomeOldArray,showContentArray,showOldContentArray);
//            var name="avg_set_ine"; //综合客座率
//            theCurve(yearArray,name,legentArray,plfArray,plfOldArray);
//            var name="person_dct"; //平均折扣
//            newTheCurve(yearArray,name,legentArray,disBcArray,disBcOldArray,disArray,disOldArray);
//            var name="person_avg";   //人数-柱状图
//            newTheCurve(yearArray,name,legentArray,pcBcArray,pcBcOldArray,pcArray,pcOldArray);
//		}
//	}
//}
/**********************绘图函数*********echarts******************/
/*均线图*/
function theCurve(yearArray,name,axisArray,info,oldinfo){
	
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
            	if(params.length>1){
            		return params[0].name+'<br/>'+yearArray[0]+'：'+(params[1].value >0 ?params[1].value : '--')+'%<br/>'+yearArray[1]+'：'+(params[0].value >0 ?params[0].value : '--')+'%';
            	}else{
            		return params[0].name+'<br/>'+params[0].seriesName +":"+ (params[0].value >0 ?params[0].value : '--')+'%';
            	}
            },
            borderColor:'#d85430',
            borderWidth:1
        },
        legend: {
            x:'right',
            show:true,
 	    	data: [{name:yearArray[0],icon:'roundRect', textStyle: {color: 'white'}},{name:yearArray[1],icon:'roundRect',textStyle: {color: 'white'}}],
            itemHeight :6,
            itemWidth:25,
            left:"70%"
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
	        	name: yearArray[1],  
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
                        color: '#bd5741'
                    }
                },
 	            data: info
 	        },
			{
	        	name: yearArray[0],  
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
				itemStyle: {                   
                      normal: {
                        color: '#63c6d7'
                    }
                   },
				lineStyle: {
                    normal: {
                        width: 4,
                        color: '#63c6d7'
                    }
                },
 	            data: oldinfo
 	        }
        ]
    };
    myChart.setOption(option,true);
}

/*均线图*/
function theNewCurve(yearArray,name,axisArray,info,oldinfo,content,oldContent){
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
            	var showcontent = '';
            	if(params.length>1){
            		showcontent =  oldContent[params[0].name]+'<br/>'+content[params[0].name];
            	}else{
            		var flagIndex = 0;
            		for(var i=0;i<option.legend.data.length;i++){
            			if(params[0].seriesName==option.legend.data[i].name){
            				flagIndex = i;
            			}
            		}
            		if(flagIndex==0){
            			showcontent = oldContent[params[0].name];
            		}else{
            			showcontent += content[params[0].name];
            		}
            	}
            	return showcontent;
            },
            borderColor:'#d85430',
            borderWidth:1
        },
        legend: {
            x:'right',
            show:true,
 	    	data: [{name:yearArray[0],icon:'roundRect', textStyle: {color: 'white'}},{name:yearArray[1],icon:'roundRect',textStyle: {color: 'white'}}],
            itemHeight :6,
            itemWidth:25,
            left:"70%"
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
	        	name: yearArray[1],  
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
                        color: '#bd5741'
                    }
                },
 	            data: info
 	        },
			{
	        	name: yearArray[0],  
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
				itemStyle: {                   
                      normal: {
                        color: '#63c6d7'
                    }
                   },
				lineStyle: {
                    normal: {
                        width: 4,
                        color: '#63c6d7'
                    }
                },
 	            data: oldinfo
 	        }
        ]
    };
    myChart.setOption(option,true);
}

/*均线图*/
function newTheCurve(yearArray,name,axisArray,bcArray,bcOldArray,info,oldinfo){
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
            	var showcontent = params[0].name;
            	if(params.length>1){
            		showcontent += '<br/>'+bcOldArray[params[0].name];
            		showcontent += '<br/>'+bcArray[params[0].name];
            	}else{
            		var flagIndex = 0;
            		for(var i=0;i<option.legend.data.length;i++){
            			if(params[0].seriesName==option.legend.data[i].name){
            				flagIndex = i;
            			}
            		}
            		if(flagIndex==0){
            			showcontent += '<br/>'+bcOldArray[params[0].name];
            		}else{
            			showcontent += '<br/>'+bcArray[params[0].name];
            		}
            	}
            	return showcontent;
            },
            borderColor:'#d85430',
            borderWidth:1
        },
        legend: {
            x:'right',
            show:true,
 	    	data: [{name:yearArray[0],icon:'roundRect', textStyle: {color: 'white'}},{name:yearArray[1],icon:'roundRect',textStyle: {color: 'white'}}],
            itemHeight :6,
            itemWidth:25,
            left:"70%"
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
	        	name: yearArray[1],  
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
                        color: '#bd5741'
                    }
                },
 	            data: info
 	        },
			{
	        	name: yearArray[0],  
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
				itemStyle: {                   
                      normal: {
                        color: '#63c6d7'
                    }
                   },
				lineStyle: {
                    normal: {
                        width: 4,
                        color: '#63c6d7'
                    }
                },
 	            data: oldinfo
 	        }
        ]
    };
    myChart.setOption(option,true);
}
function lastyears(){
	var years = $('#years').text();
	years = parseInt(years.split('-')[0]);
	var startYear = years-6;
	var endYear = years-1;
	$('#years').text(startYear+'-'+endYear);
	var html = '';
	var arr = new Array();
	for(var a  = 0;a<yearsList.length;a++){
		arr[a] = parseInt(yearsList[a]);
	}
	for(var i=startYear;i<=endYear;i++){
		if(i==parseInt(currentCheckedYear)){
			html+='<div class="year-btn hasData list">'+i+'</div>';
		}else{
			if($.inArray(i,arr)>-1){
				html+='<div class="year-btn hasData">'+i+'</div>';
			}else{
				html+='<div class="year-btn">'+i+'</div>';
			}
		}
	}
	$('.sr-box-body-date-body').html(html);
}
function nextyears(){
	var years = $('#years').text();
	years = parseInt(years.split('-')[1]);
	var startYear = years+1;
	var endYear = years+6;
	$('#years').text(startYear+'-'+endYear);
	var html = '';
	var arr = new Array();
	for(var a  = 0;a<yearsList.length;a++){
		arr[a] = parseInt(yearsList[a]);
	}
	for(var i=startYear;i<=endYear;i++){
		if(i==parseInt(currentCheckedYear)){
			html+='<div class="year-btn hasData list">'+i+'</div>';
		}else{
			if($.inArray(i,arr)>-1){
				html+='<div class="year-btn hasData">'+i+'</div>';
			}else{
				html+='<div class="year-btn">'+i+'</div>';
			}
		}
	}
	$('.sr-box-body-date-body').html(html);
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




//头部航段导航
function create_hd_s(hdata,yearText){
	hasDataNavi = 0;
	create_abnormal(hdata.success.newmap.everyList[0].dataMap.exceptionFlag);
	Allarr.yeary=[];
	clearAllarr();
	$('.bc').text('实际班次：'+((typeof(hdata.success.executiveClass)=='undefined'||hdata.success.executiveClass==null||hdata.success.executiveClass=='')?0:$.digitization(hdata.success.executiveClass))+'班');
	var hddata= hdata.success.newmap;
	$(".sales-check-co ul.leglist").empty();
	var fff=0;
	$.each(hddata.everyList,function(i,el){	//创建
		if(el.flyName.split("-").length<3 && el.flyName.indexOf("=")<0){			
			if(el.dataMap.hasData){	//根据客座量判断
				hasDataNavi++;
				$(".sales-check-co ul.leglist").append("<li group=" + Math.ceil(fff) + " tag="+ el.flyCode +" isN='true' eflag="+ el.dataMap.exceptionFlag +">"+ el.flyName.split("-")[0] + "<span>&#xe60e;</span>"+el.flyName.split("-")[1] + "</li>");
			}
			else{
				$(".sales-check-co ul.leglist").append("<li class='TMnodata-opacity' group=" + Math.ceil(fff) + " tag="+ el.flyCode +" isN='false'>"+ el.flyName.split("-")[0] + "<span>&#xe60e;</span>"+el.flyName.split("-")[1] + "</li>");
			}
		}
		fff+=0.5;
	})

	var curGroup=1;
	$(".sales-check .lhead").click();    	//打开导航
	for(var a=0 ; a<$(".sales-check .leglist li").length ; a++){	//预全选
		if($(".sales-check .leglist li").eq(a).attr("isN")=="true"){
			$(".sales-check .leglist li").eq(a).addClass("ck");
		}
	}
	
	
	setTimeout(function(){	//第二次单击,从这里开始自选数据
		$(".sales-check .leglist li").unbind("click");
		$(".sales-check .leglist li").bind("click",function(){
			bclist= [];
			create_abnormal($(this).attr("eflag"));
			if($(this).attr("isN")=="true"){
				clearAllarr();
				naviChip=0,AyearArray=[],AoyearArray=[],first_a=[],first_b=[],first_c=[],ofirst_a=[],ofirst_b=[],ofirst_c=[],three_a=[],three_b=[],othree_a=[],othree_b=[],four_a=[],four_b=[],ofour_a=[],ofour_b=[];
				resetTableData();
				if($("#income-set li.checkFlt").text()=="往返"){	
					
					//naviChip=0,AyearArray=[],AoyearArray=[],first_a=[],first_b=[],first_c=[],ofirst_a=[],ofirst_b=[],ofirst_c=[],three_a=[],three_b=[],othree_a=[],othree_b=[],four_a=[],four_b=[],ofour_a=[],ofour_b=[];
					
					alltitle={};
					for(var i =0 ;i< $(".sales-check .leglist li").length;i++){
						$(".sales-check .leglist li").eq(i).removeClass("ck");
					}
					for(var i =0 ;i< $(".sales-check .leglist li").length;i++){
						if($(".sales-check .leglist li").eq(i).attr("group")==$(this).attr("group") && $(".sales-check .leglist li").eq(i).attr("isn") == "true"){//添加同组数据
							
							//这里填充数据true
							findData(hddata,$(".sales-check .leglist li").eq(i).attr("tag"),yearText,true);
							$(".sales-check .leglist li").eq(i).addClass("ck");
						}
					}					
				}
				else{
					$(this).addClass("ck").siblings().removeClass("ck");
					//填充这次的数据false
					findData(hddata,$(this).attr("tag"),yearText,false);
					curGroup=$(this).attr("group");				
				}
			}
			else{			
				return;
			}
		})		
	},100)
	
}


function findData(data,leg,yearText,type){//重绘图表
	naviChip+=1;
	  $.each(data.everyList,function(i,el){
	      if(leg==el.flyCode){	    	  
	    	  newmid(el,yearText,type, data.everyList[0]);
	      }
	  })
}







function newmid(data, yearText, type, totalData){	//TYPE为true就是一组，否则是单个
	if(naviChip == hasDataNavi){	//如果缺段时全选，则直接使用totalData数据
		resetTableData();
		naviChip = 0;
		bclist= [];
		newmid(totalData, yearText, false, totalData);
		return false;
	}
	if(bclist.length == 0){
		bclist= data.dataMap.bcDateList;
	}else{
		var bcNum= concat(bclist,data.dataMap.bcDateList);	//总班次
	}	
	var cyshj = testVal(data.dataMap.zys),
	cxsys = testVal(data.dataMap.xsys),
	czgl = testVal(data.dataMap.zgl),
	ckzl = testVal(data.dataMap.kzl),
	czrs = testVal(data.dataMap.zrs),
	cjbrs = testVal(data.dataMap.jbrs),	
	totalzbc = bcNum ? bcNum : testVal(data.dataMap.zbc);
	
    totalTitle(toNum(cyshj),"add","yshj");
    totalTitle(toNum(cxsys),"add","xsys");
    totalTitle(toNum(czgl),"add","zgl");
    totalTitle(toNum(ckzl),"add","kzl");
    totalTitle(toNum(czrs),"add","allPersom"); 
	if(type){//往返
		$('#yearIncome').text(formatNumber(allTitle.yshj,2,1));
		$('#hourIncome').text(naviChip == 0 ? formatNumber(data.dataMap.xsys,2,1) : '--');
		$('#raskIncome').text('--');
		$('#classAvgInner').text(formatNumber( allTitle.yshj/totalzbc ,2,1));
		$('#yearAvgPlf').text(parseFloat(allTitle.kzl/naviChip).toFixed(2) +'%');
		$('#yearTotalPC').text(formatNumber(allTitle.allPersom,0,1)+'人/'+ totalzbc +'班');  //总
		$("#yearAvgPC").text( parseFloat( allTitle.allPersom / totalzbc ).toFixed(2) +'人/'+ totalzbc +'班');    //均
	}
	else{//单程
		$('#yearIncome').text(cyshj>0 ? formatNumber(cyshj,2,1):'--');
		$('#hourIncome').text(cxsys >0 ?formatNumber(cxsys,2,1): '--');
		$('#raskIncome').text(czgl >0 ?formatNumber(czgl,2,1): '--');
		$('#classAvgInner').text(cyshj>0 ? formatNumber( cyshj/totalzbc ,2,1): '--');
		$('#yearAvgPlf').text(ckzl>0 ? (parseFloat(ckzl).toFixed(2) +'%'):'--');
		$('#yearTotalPC').text(formatNumber(czrs,0,1)+'人/'+ Number(totalzbc) +'班');  //总
		$("#yearAvgPC").text((data.dataMap.jbrs ? data.dataMap.jbrs : parseFloat( czrs / totalzbc ).toFixed(2)) +'人/'+ Number(totalzbc) +'班');		
	}
	
	//根据当前时间轴取出数据
	legentArray = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];
	$.each(legentArray, function(ii, ell){
        incomeArray[ii] = incomeArray[ii] ? incomeArray[ii] : 0;
        pieceZgl[ell] = pieceZgl[ell]? pieceZgl[ell] : 0;
        pieceXsys[ell] = pieceXsys[ell]? pieceXsys[ell] : 0;
        pieceKzl[ell] = pieceKzl[ell]? pieceKzl[ell] : 0;
        
        pieceStrs[ell] = pieceStrs[ell]? pieceStrs[ell] : 0;	//总人数
        pieceSkrs[ell] = pieceSkrs[ell]? pieceSkrs[ell] : 0;	//均班人数
        
        //----------------------------------------当前月数据
    	var nowMonth = data.dataMap.monthData[fullMonthDate(yearText,ell)];	
        if(nowMonth){
            incomeArray[ii] += toNum(nowMonth.hbys ? nowMonth.hbys : incomeArray[ii]);
            pieceZgl[ell] += toNum(nowMonth.set_Ktr_Ine ? nowMonth.set_Ktr_Ine : pieceZgl[ell]);
            
            pieceXsys[ell] += toNum(nowMonth.xssr ? nowMonth.xssr : pieceXsys[ell]);
            pieceKzl[ell] += toNum(nowMonth.pjkzl ? nowMonth.pjkzl : pieceKzl[ell]);             
            
            pieceSkrs[ell] += nowMonth.jbrs ? toNum(nowMonth.jbrs) : pieceSkrs[ell];
            pieceStrs[ell] += toNum(nowMonth.stzsr ? nowMonth.stzsr : pieceStrs[ell]);
            if(naviChip==0){	//全选直接填充汇总数据
            	var totalD= data.dataMap.monthData[fullMonthDate(yearText,ell)];
                showContentArray[Number(ell)+ '月'] = 
                	fullMonthDate(yearText,ell) + '月:' +
                    '<br>航班营收:' + ( Number(totalD.hbys) > 0 ?formatNumber(totalD.hbys,2,1):'--') + 
                    '<br>小时营收:' + ( Number(totalD.xssr) > 0 ?formatNumber(totalD.xssr,2,1):'--') + 
                    '<br>座公里收入:' + formatNumber(totalD.set_Ktr_Ine,2,1);                
            }else{	//非全选屏蔽座公里,小时营收求平均
                showContentArray[Number(ell)+ '月'] = 
                	fullMonthDate(yearText,ell) + '月:' +
                	'<br>航班营收:' + ( Number(incomeArray[ii]) > 0 ?formatNumber(incomeArray[ii],2,1):'--') + 
                	'<br>小时营收:' + formatNumber((pieceXsys[ell]/naviChip),2,1) + 
                	'<br>座公里收入: --';
            }
        }else{        	
            incomeArray[ii] += incomeArray[ii];
            pieceZgl[ell] += pieceZgl[ell];            
            pieceXsys[ell] += pieceXsys[ell];
            
            pieceKzl[ell] += pieceKzl[ell];
            
            pieceSkrs[ell] += pieceSkrs[ell];
            pieceStrs[ell] += pieceStrs[ell];   
            
            //当月没有数据
            showContentArray[Number(ell)+ '月'] = fullMonthDate(yearText,ell) 
            	+ '月:' 
            	+ '<br>航班营收: ' + ( incomeArray[ii] > 0 ?formatNumber(incomeArray[ii],2,1) :'--')
            	+ '<br>小时营收: ' + ( pieceXsys[ell] > 0 ?formatNumber(pieceXsys[ell]/(naviChip > 0? naviChip: 1),2,1)  :'--')
            	+ '<br>座公里收入: --';
        }
        
        
        
        
        
        //--------------------------------------上次月数据
        oincomeArray[ii] = oincomeArray[ii] ? oincomeArray[ii] : 0;
        opieceKzl[ell] = opieceKzl[ell]? opieceKzl[ell] : 0; 
        opieceXsys[ell] = opieceXsys[ell]? opieceXsys[ell] : 0; 
    	opieceZgl[ell] = opieceZgl[ell]? opieceZgl[ell] : 0; 

        opieceStrs[ell] = opieceStrs[ell]? opieceStrs[ell] : 0;	//总人数
        opieceSkrs[ell] = opieceSkrs[ell]? opieceSkrs[ell] : 0;	//均班人数
        var oldMonth = data.dataMap.lastmonthData[fullMonthDate(Number(yearText)-1,ell)];		
        if(oldMonth){
            oincomeArray[ii] += toNum(oldMonth.hbys ? oldMonth.hbys : oincomeArray[ii]);
        	
            opieceXsys[ell] += toNum(oldMonth.hbys ? oldMonth.xssr : opieceXsys[ell]);
        	opieceZgl[ell] += toNum(oldMonth.hbys ? oldMonth.set_Ktr_Ine : opieceZgl[ell]);
            
            opieceKzl[ell] += toNum(oldMonth.pjkzl ? oldMonth.pjkzl : opieceKzl[ell]); 
            
            opieceSkrs[ell] += oldMonth.jbrs ? toNum(oldMonth.jbrs) : opieceSkrs[ell];
            opieceStrs[ell] += toNum(oldMonth.stzsr ? oldMonth.stzsr : opieceStrs[ell])
            
            if(naviChip==0){	//全选
            	var totalOD= data.dataMap.monthData[fullMonthDate(Number(yearText)-1,ell)];
                oshowContentArray[Number(ell) + '月'] = 
                	fullMonthDate(Number(yearText)-1,ell) + '月:' + 
                    '<br>航班营收:' + (Number(oincomeArray[ii]) > 0?formatNumber(oincomeArray[ii],2,1): '--') + 
                    '<br>小时营收:' + (Number(opieceXsys[ell]) > 0?formatNumber(opieceXsys[ell],2,1): '--') + 
                    '<br>座公里收入:' + (Number(opieceZgl[ell]) > 0?formatNumber(opieceZgl[ell],2,1): '--') ; 
            }else{	//非全选屏蔽座公里
                oshowContentArray[Number(ell)+ '月'] = 
                	fullMonthDate(Number(yearText)-1,ell) +  '月:' +
                	'<br>航班营收:' + (Number(oincomeArray[ii]) > 0?formatNumber(oincomeArray[ii],2,1): '--') + 
                	'<br>小时营收:' + (Number(opieceXsys[ell]) > 0?formatNumber(opieceXsys[ell]/naviChip,2,1): '--') +
                	'<br>座公里收入: --'  ;
            }
        }else{
            oincomeArray[ii] += 0;
            opieceXsys[ell] += 0;
        	opieceZgl[ell] += 0;
            
            opieceKzl[ell] += 0;
            
            opieceSkrs[ell] += 0;
            opieceStrs[ell] += 0;
            
            
            if(naviChip==0){	//全选
            	var totalOD= data.dataMap.monthData[fullMonthDate(Number(yearText)-1,ell)];
                oshowContentArray[Number(ell) + '月'] = 
                	fullMonthDate(Number(yearText)-1,ell) + '月:' +
                    '<br>航班营收:' + ( Number(oincomeArray[ii]) > 0 ?formatNumber(oincomeArray[ii],2,1):'--') + 
                    '<br>小时营收:' + ( Number(opieceXsys[ell]) > 0 ?formatNumber(opieceXsys[ell],2,1):'--') + 
                    '<br>座公里收入:' + ( Number(opieceZgl[ell]) > 0 ?formatNumber(opieceZgl[ell],2,1):'--');                
            }else{	//单段只显示总营收
                $('#hourIncome').text('--');
                $('#raskIncome').text('--');
                oshowContentArray[Number(ell)+ '月'] = 
                	fullMonthDate(Number(yearText)-1,ell) + '月:' +
                	'<br>航班营收:' + (oincomeArray[ii]>0? formatNumber(oincomeArray[ii],2,1): '--')
                	+ '<br>小时营收:' + (opieceXsys[ell]>0? formatNumber(opieceXsys[ell]/(naviChip>0?naviChip:1),2,1):'--')
                	+ '<br>座公里收入: --';
            }
        }
        //去年月班次计算
        var ombcNum = 0;
        if(type){	//一组
        	if(!ombclist[ell]){
        		ombclist[ell] = oldMonth ? oldMonth.bcDateList : [];
        	}else{
        		ombcNum = concat(ombclist[ell], (oldMonth ? oldMonth.bcDateList : []));	//求并集后算总班次
        	}        	
        }else{
        	ombcNum = Number(oldMonth ? oldMonth.banci : '--');
        }

        //去年月班次计算
        var mbcNum = 0;
        if(type){	//一组
        	if(!mbclist[ell]){
        		mbclist[ell] = nowMonth ? nowMonth.bcDateList : [];
        	}else{
        		mbcNum = concat(mbclist[ell], (nowMonth ? nowMonth.bcDateList : []));	//求并集后算总班次
        	}        	
        }else{
        	mbcNum = Number(nowMonth ? nowMonth.banci : '--');
        }
        //展示X轴
        trueLegentArray[ii] = Number(ell) + '月';
        //展示总人数tooltip
        perbc[ell] = perbc[ell] ? perbc[ell] :null;
        perbc[ell] = (nowMonth ? Number(nowMonth.banci): perbc[ell]);
        operbc[ell] = operbc[ell] ? operbc[ell] :null;
        operbc[ell] = (oldMonth ? Number(oldMonth.banci): operbc[ell]);
        //本月
        showPieceStrs[Number(ell) + '月'] = yearText + '年: ' 
        	+  (pieceStrs[ell]==0 ? "--": formatNumber(pieceStrs[ell],0,1)) 
        	+ '人/' + (mbcNum>0 ? mbcNum : "--") + '班' ;
        //上次月
        showoPieceStrs[Number(ell) + '月'] = yearText - 1 + '年: ' 
        	+ (opieceStrs[ell]==0 ? "--": formatNumber(opieceStrs[ell],0,1)) 
        	+ '人/' + (ombcNum>0 ? ombcNum : "--") + '班' ;
        
        //展示均班人数tooltip
        perjb[ell] = perjb[ell] ? perjb[ell] : 0;
        perjb[ell] = (nowMonth ? Number(nowMonth.banci): perjb[ell]);
        operjb[ell] = operjb[ell] ? operjb[ell] : 0;
        operjb[ell] += (oldMonth ? Number(oldMonth.banci): operjb[ell]);
        //本月
        showPieceSkrs[Number(ell) + '月'] = yearText + '年: ' 
    		+  (pieceSkrs[ell]==0 ? "--": formatNumber(pieceSkrs[ell],2,1)) 
    		+ '人/' + (mbcNum>0 ? mbcNum : "--") + '班' ;
        //上次月
        showoPieceSkrs[Number(ell) + '月'] = yearText - 1 + '年: ' 
    		+ (opieceSkrs[ell]==0 ? "--": formatNumber(opieceSkrs[ell],2,1)) 
    		+ '人/' + (ombcNum>0 ? ombcNum : "--") + '班' ;
        
        
    })	//遍历日期结束
    var lastyear= yearText - 1 + '年';
    incomeArray = incomeArray.map(function(val){
        return val==0?"0.00":val;
    })
    oincomeArray = oincomeArray.map(function(val){
        return val==0?"0.00":val;
    })
	theNewCurve([lastyear, yearText+ '年'], 'income', trueLegentArray, incomeArray, oincomeArray, showContentArray,oshowContentArray);
	
	theCurve([lastyear, yearText+ '年'], 'avg_set_ine', trueLegentArray, mapToArr(pieceKzl), mapToArr(opieceKzl));
	
	newTheCurve([lastyear, yearText+ '年'], 'person_dct', trueLegentArray, showPieceStrs, showoPieceStrs, mapToArr(pieceStrs), mapToArr(opieceStrs));
    newTheCurve([lastyear, yearText+ '年'], 'person_avg', trueLegentArray, showPieceSkrs, showoPieceSkrs, mapToArr(pieceSkrs), mapToArr(opieceSkrs));   
}




function anewmid(data,yearText,type,totalData){	//TYPE为true就是一组，否则是单个
	if(bclist.length == 0){		//计算总班次
		bclist= data.dataMap.bcDateList;
	}else{
		var bcNum= countBc(bclist,data.dataMap.bcDateList);
	}
	var an=(typeof(data.dataMap.zys)=='undefined'||data.dataMap.zys==null||data.dataMap.zys=='')?0.00:data.dataMap.zys;
	var bn=(typeof(data.dataMap.xsys)=='undefined'||data.dataMap.xsys==null||data.dataMap.xsys=='')?0.00:data.dataMap.xsys;
	var cn=(typeof(data.dataMap.zgl)=='undefined'||data.dataMap.zgl==null||data.dataMap.zgl=='')?0.00:data.dataMap.zgl;
	var dn=(typeof(data.dataMap.kzl)=='undefined'||data.dataMap.kzl==null||data.dataMap.kzl=='')?0.00+'%':data.dataMap.kzl+'%';
	var enA=(data.dataMap.zrs=='undefined'? 0 :data.dataMap.zrs);
	var enB=(data.dataMap.zbc=='undefined'? 0 :data.dataMap.zbc);
	var fnA = $.digitization((parseInt(data.dataMap.jbrs)/parseInt(data.dataMap.zbc)).toFixed(2));
	var fnB=$.digitization(parseInt(data.dataMap.zbc).toFixed(2));
	totaltile(toNum(an),"add","yshj");    
	totaltile(toNum(bn),"add","xsys");    
	totaltile(toNum(cn),"add","zgl");    
	totaltile(toNum(dn),"add","kzl");     
	totaltile(toNum(enA),"add","zrs");    totaltile(toNum(enB),"add","zbc");    
	totaltile(toNum(fnA),"add","jbrs");     totaltile(toNum(fnB),"add","z");  
	//------------------标题填充
	if(type){//往返
		$('#yearIncome').text(formatNumber(alltitle.yshj,2,1));
		$('#hourIncome').text(formatNumber(alltitle.xsys/naviChip,2,1));
		$('#raskIncome').text(formatNumber(alltitle.zgl/naviChip, 2,1));
		$('#classAvgInner').text(formatNumber(alltitle.yshj/alltitle.zbc*2,2,1));
		$('#yearAvgPlf').text(parseFloat(alltitle.kzl/naviChip).toFixed(2) +'%');
		$('#yearTotalPC').text(formatNumber(alltitle.zrs,0,1)+'人/'+ bcNum +'班');  //总
		$("#yearAvgPC").text( parseFloat(alltitle.zrs/(bcNum)).toFixed(2) +'人/'+ bcNum +'班');    //均
	}
	else{//单程
		$('#yearIncome').text(formatNumber(an,2,1));
		$('#hourIncome').text(formatNumber(bn,2,1));
		$('#raskIncome').text(formatNumber(cn,2,1));
		$('#classAvgInner').text(formatNumber(toNum(an)/toNum(enB),2,1))
		$('#yearAvgPlf').text(parseFloat(toNum(dn)).toFixed(2) +'%');
		$('#yearTotalPC').text(formatNumber(enA,0,1)+'人/'+ toNum(enB) +'班');  //总
		$("#yearAvgPC").text( parseFloat(data.dataMap.jbrs).toFixed(2) +'人/'+ bcNum +'班');    //均		
	}
	
	
	//-----------------数组重构	
	
	
	var legentArray = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];
	var incomeArray = [];
	var plfArray = [];
	var disArray = [];
	var pcArray = [];
	var hourIncomeArray = [];
	var raskArray = [];
	var incomeOldArray = [];
	var plfOldArray = [];
	var disOldArray = [];
	var pcOldArray = [];
	var disBcArray = new Map();
	var disBcOldArray = new Map();
	var pcBcArray = new Map();
	var pcBcOldArray = new Map();
	var showContentArray = new Map();
	var showOldContentArray = new Map();
	var legendArray = [];
	var yearArray = new Map();
	var lastyearArray = new Map();
	if(yearText!=null&&yearText!=''){
	    if(yearText.indexOf('-')>-1){
	        var yearTitle = yearText.split('-');
	        legendArray.push((yearTitle[0]-1)+'-'+(yearTitle[1]-1)+'年');
	        legendArray.push(yearText+'年');
	    }else{
	        var yearTitle = yearText.split('-');
	        legendArray.push((parseInt(yearTitle)-1)+'年');
	        legendArray.push(yearTitle+'年');
	    }
	    
	}
	if(data.dataMap.zys!=null && data.dataMap.zys!="" && data.dataMap.zys!=0){
	    var bz = true;
	    var lastZys = 0;
	    var tipp=0;
	    for(var key in data.dataMap.monthData){
	        var keys = parseInt(key.split('-')[1])+'月';
	        var obj = data.dataMap.monthData[key];
	        incomeArray.push(parseFloat(obj.hbys).toFixed(2));
	        hourIncomeArray.push(obj.xssr);
	        raskArray.push(obj.set_Ktr_Ine);
	        plfArray.push(parseFloat(obj.pjkzl).toFixed(2));
	        disArray.push(parseFloat(parseFloat(obj.banci)*parseFloat(obj.jbrs)).toFixed(2));
	        pcArray.push(parseFloat(obj.jbrs).toFixed(2));
	        
	        if(AyearArray[tipp]>0){
	        	first_a[tipp]+= toNum(obj.hbys);
	        	first_b[tipp]+= toNum(obj.xssr);
	        	first_c[tipp]+= toNum(obj.set_Ktr_Ine);
	        	
	        	AyearArray[tipp]+= parseFloat(obj.pjkzl);
	        	
	        	three_a[tipp] += toNum(parseFloat(obj.banci)*parseFloat(obj.jbrs));
	        	three_b[tipp] += toNum(obj.banci);
	        	
	        	four_a[tipp]+=toNum(obj.jbrs);
	        	four_b[tipp]+=toNum(obj.banci);
	        }
	        else{
	        	first_a[tipp]= toNum(obj.hbys);
	        	first_b[tipp]= toNum(obj.xssr);
	        	first_c[tipp]= toNum(obj.set_Ktr_Ine);

		        AyearArray[tipp]=parseFloat(obj.pjkzl);
	        	
	        	three_a[tipp] = toNum(parseFloat(obj.banci)*parseFloat(obj.jbrs));
	        	three_b[tipp] = toNum(obj.banci);

	        	four_a[tipp]=toNum(obj.jbrs);
	        	four_b[tipp]=toNum(obj.banci);
	        }
	        if(naviChip>0){
	        	if($("#income-set .checkFlt").text() == "往返"){
			        showContentArray[keys] = key.split('-')[0]+'年：'+keys+'<br/>航班营收：'+ formatNumber(first_a[tipp],2,1) +'<br/>小时营收：--<br/>座公里收入：'+ formatNumber(first_c[tipp]/2,2,1) ;

	        	}else{
			        showContentArray[keys] = key.split('-')[0]+'年：'+keys+'<br/>航班营收：'+ formatNumber(first_a[tipp],2,1) +'<br/>小时营收：--<br/>座公里收入：'+ formatNumber(first_c[tipp],2,1) ;
	        	}
	        	
		        yearArray[keys] = key.split('-')[0]+'年：'+ parseFloat(AyearArray[tipp]/naviChip).toFixed(2) +'%';
		        
		        disBcArray[keys] = key.split('-')[0]+'年：'+ parseFloat(three_a[tipp]).toFixed(2)+'人/'+ parseFloat(three_b[tipp]/naviChip).toFixed(2) +'班';
		        
		        pcBcArray[keys] = key.split('-')[0]+'年：'+parseFloat(four_a[tipp]).toFixed(2)+'人/'+parseFloat(four_b[tipp]/naviChip).toFixed(2)+'班';	        	
	        }
	        else{
		        showContentArray[keys] = key.split('-')[0]+'年：'+keys+'<br/>航班营收：'+$.digitization(obj.hbys)+'<br/>小时营收：'+$.digitization(obj.xssr)+'<br/>座公里收入：'+obj.set_Ktr_Ine;
		        yearArray[keys] = key.split('-')[0]+'年：'+obj.pjkzl+'%';
		        disBcArray[keys] = key.split('-')[0]+'年：'+$.digitization(parseFloat(parseFloat(obj.banci)*parseFloat(obj.jbrs)).toFixed(2))+'人/'+$.digitization(parseFloat(obj.banci))+'班';
		        pcBcArray[keys] = key.split('-')[0]+'年：'+$.digitization(parseFloat(obj.jbrs).toFixed(2))+'人/'+$.digitization(parseFloat(obj.banci))+'班';	        	
	        }
	        tipp+=1;
	    }
	    if(data.dataMap.lastmonthData!=null){
	    	tipp=0;
	        for(var key in data.dataMap.lastmonthData){
	            var keys = parseInt(key.split('-')[1])+'月';
	            var obj = data.dataMap.lastmonthData[key];

		        if(AoyearArray[tipp]>0){
		        	ofirst_a[tipp]+= toNum(obj.hbys);
		        	ofirst_b[tipp]+= toNum(obj.xssr);
		        	ofirst_c[tipp]+= toNum(obj.set_Ktr_Ine);
		        	
		        	AoyearArray[tipp] += parseFloat(obj.pjkzl);
		        	
		        	othree_a[tipp] += toNum(parseFloat(obj.banci)*parseFloat(obj.jbrs));
		        	othree_b[tipp] += toNum(obj.banci);	
		        	
		        	ofour_a[tipp]+=toNum(obj.jbrs);
		        	ofour_b[tipp]+=toNum(obj.banci);
		        	
		        }
		        else{
		        	ofirst_a[tipp]= toNum(obj.hbys);
		        	ofirst_b[tipp]= toNum(obj.xssr);
		        	ofirst_c[tipp]= toNum(obj.set_Ktr_Ine);
		        	
			        AoyearArray[tipp] = parseFloat(obj.pjkzl);

		        	othree_a[tipp] = toNum(parseFloat(obj.banci)*parseFloat(obj.jbrs));
		        	othree_b[tipp] = toNum(obj.banci);

		        	ofour_a[tipp]=toNum(obj.jbrs);
		        	ofour_b[tipp]=toNum(obj.banci);
		        }
		        
		        
		        if(naviChip>0){

		        	if($("#income-set .checkFlt").text() == "往返"){
		        		showOldContentArray[keys] = key.split('-')[0]+'年：'+keys+'<br/>航班营收：'+ formatNumber(ofirst_a[tipp],2,1) +'<br/>小时营收：-- <br/>座公里收入：'+ formatNumber(ofirst_c[tipp]/2,2,1) ;

		        	}else{
		        		showOldContentArray[keys] = key.split('-')[0]+'年：'+keys+'<br/>航班营收：'+ formatNumber(ofirst_a[tipp],2,1) +'<br/>小时营收：-- <br/>座公里收入：'+ formatNumber(ofirst_c[tipp]/2,2,1) ;

				    }
		            lastyearArray[keys] = key.split('-')[0]+'年：'+ parseFloat(AoyearArray[tipp]/naviChip).toFixed(2) +'%';
	
			        disBcOldArray[keys] = key.split('-')[0]+'年：'+ parseFloat(othree_a[tipp]).toFixed(2)+'人/'+ parseFloat(othree_b[tipp]/naviChip).toFixed(2) +'班';
			        
			        pcBcOldArray[keys] = key.split('-')[0]+'年：'+parseFloat(ofour_a[tipp]).toFixed(2)+'人/'+parseFloat(ofour_b[tipp]/naviChip).toFixed(2)+'班';	
			        
		        }
		        else{
		        	yearArray[keys] = key.split('-')[0]+'年：'+obj.pjkzl+'%';
		            showOldContentArray[keys] = key.split('-')[0]+'年：'+keys+'<br/>航班营收：'+$.digitization(obj.hbys)+'<br/>小时营收：'+$.digitization(obj.xssr)+'<br/>座公里收入：'+obj.set_Ktr_Ine;
		            
		            disBcOldArray[keys] = key.split('-')[0]+'年：'+ parseFloat(toNum(obj.jbrs)).toFixed(2)+'人/'+ parseFloat(toNum(obj.banci) ).toFixed(2) +'班';
		            
		            pcBcOldArray[keys] = key.split('-')[0]+'年：'+$.digitization(parseFloat(obj.jbrs).toFixed(2))+'人/'+$.digitization(parseFloat(obj.banci))+'班';
		        }
	            
	            
	            
	            incomeOldArray.push(parseFloat(obj.hbys).toFixed(2));
	            lastZys += parseFloat(lastZys)+parseFloat(obj.hbys);
	            plfOldArray.push(parseFloat(obj.pjkzl).toFixed(2));
	            disOldArray.push(parseFloat(parseFloat(obj.banci)*parseFloat(obj.jbrs)).toFixed(2));
	            pcOldArray.push(parseFloat(obj.jbrs).toFixed(2));

	            tipp+=1;
	        }
	    }	    
	    //判断有无数据
	    if(data.dataMap.zys!="0.00"){
	        bz = false;
	    }
	    if(type){
		    var name="income"; //收入信息
		    var aaa=oArrCompA(incomeArray,"add","ys")
		    theNewCurve(legendArray,name,legentArray,aaa,oArrCompA(incomeOldArray,"add","oys"),showContentArray,showOldContentArray);
		    var name="avg_set_ine"; //综合客座率
		    theCurve(legendArray,name,legentArray,oArrCompA(plfArray,"add","kzl"),oArrCompA(plfOldArray,"add","okzl"),yearArray,lastyearArray);
		    var name="person_dct"; //平均折扣
		    newTheCurve(legendArray,name,legentArray,disBcArray,disBcOldArray,oArrCompA(disArray,"add","zk"),oArrCompA(disOldArray,"add","ozk"));
		    var name="person_avg";   //人数-柱状图
		    newTheCurve(legendArray,name,legentArray,pcBcArray,pcBcOldArray,oArrCompA(pcArray,"add","rs"),oArrCompA(pcOldArray,"add","ors"));

	    }
	    else{
		    var name="income"; //收入信息
		    theNewCurve(legendArray,name,legentArray,incomeArray,incomeOldArray,showContentArray,showOldContentArray);
		    var name="avg_set_ine"; //综合客座率
		    theCurve(legendArray,name,legentArray,plfArray,plfOldArray,yearArray,lastyearArray);
		    var name="person_dct"; //平均折扣
		    newTheCurve(legendArray,name,legentArray,disBcArray,disBcOldArray,disArray,disOldArray);
		    var name="person_avg";   //人数-柱状图
		    newTheCurve(legendArray,name,legentArray,pcBcArray,pcBcOldArray,pcArray,pcOldArray);
		    	    	
	    }	
	    changew();  //重新计算大小
	}else{
		return ;
	    $(".sr-box-body-chart").addClass("muhu");
	    $(".reportErr").css("display","block");
	    var name="income"; //收入信息
	    theNewCurve(legendArray,name,legentArray,incomeArray,incomeOldArray,showContentArray,showOldContentArray);
	    var name="avg_set_ine"; //综合客座率
	    theCurve(legendArray,name,legentArray,plfArray,plfOldArray);
	    var name="person_dct"; //平均折扣
	    newTheCurve(legendArray,name,legentArray,disBcArray,disBcOldArray,disArray,disOldArray);
	    var name="person_avg";   //人数-柱状图
	    newTheCurve(legendArray,name,legentArray,pcBcArray,pcBcOldArray,pcArray,pcOldArray);
	}
}
var alltitle={};

function totaltile(num,type,n){
	if(n in alltitle){
	    if(type=="add"){
	        alltitle[n]+=toNum(num);
	    }
	    else if(type=="sub"){
	        alltitle[n]-=toNum(num);
	    }
	}
	else{
	    alltitle[n]=toNum(num);
	}
}
