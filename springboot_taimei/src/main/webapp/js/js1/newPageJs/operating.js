//请求数据
/**/
var table_time={
	s:"",
	e:""
};
var fone = true;
var High=0,tableData;
var ultimately={};
var airLine = parent.supData.lane;
var airports = parent.airportMap;
var flagMap = {};
var havingData="1";
var isnonFlag = $('#isnon-stop').is(':checked');
var ishasFlag = $('#ishas-stop').is(':checked');
if(typeof(airLine)=="undefined"){
	airLine = "";
}
$(function(){	
	parent.supData.linFlag = "1"; 
	var objj = parent.supData;
	if(typeof(objj.isIncloudPasstp)!="undefined"){
		if(objj.isIncloudPasstp=="on"){
			$('#isIncloudPasstp').attr('checked',true);
		}
	}
	setTimeout(function(){
		$('#startEndDate').daterangepicker(null, function(start, end, label) {});
	},1500);
	var flt = parent.supData.flt;
	if(airLine!=""){
		var cds = airLine.split("=");
		if('1'==flt){
			var dptabbr = cds[0] + "-" + airports[cds[0]].iata;
			var arrabbr = cds[1] + "-" + airports[cds[1]].iata;
			$('#cityChoice').attr("abbr",dptabbr);
			$('#cityChoice2').attr("abbr",arrabbr);
			$('#cityChoice').val(cds[0]);
			$('#cityChoice2').val(cds[1]);
		}else{
			if(cds.length==3){
				var dptabbr = cds[0] + "-" + airports[cds[0]].iata;
				var pstabbr = cds[1] + "-" + airports[cds[1]].iata;
				var arrabbr = cds[2] + "-" + airports[cds[2]].iata;
				$('#cityChoice').attr("abbr",dptabbr);
				$('#cityChoice3').attr("abbr",pstabbr);
				$('#cityChoice2').attr("abbr",arrabbr);
				$('#cityChoice').val(cds[0]);
				$('#cityChoice3').val(cds[1]);
				$('#cityChoice2').val(cds[2]);
				//变灰经停
				$('#pasDiv').addClass("_checkOp");
				$('#ishas-stop').attr("disabled","disabled");
				ishasFlag = false;
			}else{
				var dptabbr = cds[0] + "-" + airports[cds[0]].iata;
				var arrabbr = cds[1] + "-" + airports[cds[1]].iata;
				$('#cityChoice').attr("abbr",dptabbr);
				$('#cityChoice2').attr("abbr",arrabbr);
				$('#cityChoice').val(cds[0]);
				$('#cityChoice2').val(cds[1]);
			}
		}
	}


    /*****利用导航切换******/
    $(".lin-historical-body-navigation").on("click","div",function(event){
        if($(this).hasClass("body-navigation-element")){
            fone = true;
        	var thistop=$(this).offset().top;
        	if($(this).hasClass("TMnodata-div")){
            	getTheirTimeData(this,thistop);
        		return false;
        	}
        	segment($(this).attr("tag"));
        	$(".SD-nomal").eq(0).click();
        	if(havingData=="0"){
        		havingData = "1";
        		//$(this).addClass("TMnodata-div");
        		return;
        	}
            if(!$(this).hasClass("set")){
                $(this).addClass("set").siblings().removeClass("set");
                hasAbnormal($(this).attr('eflag'));
                if($(this).hasClass("two-way")){
                	if($(this).children().children().length==5){
                		var num=$(this).children().children().eq(0).text()+"="+$(this).children().children().eq(2).text()+"="+$(this).children().children().eq(4).text();
                        addElement(num);
                	}else{
                		var num=$(this).children().children().eq(0).text()+"="+$(this).children().children().eq(2).text();
                        addElement(num);
                	}
                }
                else{
                	if($(this).children().children().length==5){
                		var num=$(this).children().children().eq(0).text()+"-"+$(this).children().children().eq(2).text()+"-"+$(this).children().children().eq(4).text();
                        addElement(num);
                	}else{
                		var num=$(this).children().children().eq(0).text()+"-"+$(this).children().children().eq(2).text();
                        addElement(num);
                	}
                }
            }
        }
    });

    
    //航段无数据动画
    function TM_hd_null(){
        $("div.SD-hd-null").show();
        setTimeout(function(){
            $(".SD-hd-null").hide();
        }
        ,2000)
    }
    function TM_hd_lack(){
        $("div.TMlackdata").show();
        setTimeout(function(){
            $("div.TMlackdata").hide();
        }
        ,3000)    	
    }
    
    
    //无数据老样式，点击位置左侧划出
    function showTMnodata(t){
		$('.TMnodata').css("top",t+"px");  
		$('.TMnodata').css("left","-150px");
		$('.TMnodata').show();
		$('.TMnodata').animate({"left":"0px"});
		setTimeout(function(){
			$('.TMnodata').hide();
		},2000)
    }
    
    
    function getTheirTimeData(mythis,t){
    	
    	var tag = $(mythis).attr("tag");
    	for(var key in flagMap){
    		if(tag==key){
    			if(flagMap[key].flag=="0"){
    				havingData = "0";
    				TM_hd_null();
    			}
    		}
    	}
    	var isGoAndBack = "0";
    	var dpt_AirPt_Cdtemp = "";
    	var pas_stptemp = "";
    	var arrv_Airpt_Cdtemp = ""; 
    	if(tag.indexOf("=")>-1){
    		isGoAndBack = "1"
    		var tt = tag.split("=");
    		if(tt.length>2){
    			dpt_AirPt_Cdtemp = airports[tt[0]].iata;
    			pas_stptemp = airports[tt[1]].iata;
    			arrv_Airpt_Cdtemp = airports[tt[2]].iata;
    		}else{
    			dpt_AirPt_Cdtemp = airports[tt[0]].iata;
    			arrv_Airpt_Cdtemp = airports[tt[1]].iata;
    		}
    	}else{
    		isGoAndBack = "0"
    		var tt = tag.split("-");
    		if(tt.length>2){
    			dpt_AirPt_Cdtemp = airports[tt[0]].iata;
    			pas_stptemp = airports[tt[1]].iata;
    			arrv_Airpt_Cdtemp = airports[tt[2]].iata;
    		}else{
    			dpt_AirPt_Cdtemp = airports[tt[0]].iata;
    			arrv_Airpt_Cdtemp = airports[tt[1]].iata;
    		}
    	}
    	var date = $('#startEndDate').val();
    	//异常数据
    	var iscludeExe = '';
    	if($('#exceptionData').is(':checked')==true){
    		iscludeExe = 'on';
    	}
    	var isIncludeExceptionHuangduan="";
    	if($('#exceptionData1').is(':checked')==true){
    		isIncludeExceptionHuangduan = 'on';
    	}
    	//是否包含经停
    	var iscludePas = 'no';
    	if($('#ishas-stop').is(':checked')==true){
    		iscludePas = 'on';
    	}
    	//直飞
    	var ishasStop = 'no';
    	if($('#isnon-stop').is(':checked')==true){
    		ishasStop = 'on';
    	}
    	//航线来回
    	var dpt_AirPt_CdName = $('#cityChoice').val();
    	var pas_stpName = $('#cityChoice3').val();
    	var arrv_Airpt_CdName = $('#cityChoice2').val();
    	var flt_ectq ;
    	var flt_ecth ;
    	if(pas_stpName==""){
    		flt_ectq = airports[dpt_AirPt_CdName].iata + airports[arrv_Airpt_CdName].iata;
    		flt_ecth = airports[arrv_Airpt_CdName].iata + airports[dpt_AirPt_CdName].iata;
    	}else{
    		flt_ectq = airports[dpt_AirPt_CdName].iata +airports[pas_stpName].iata+ airports[arrv_Airpt_CdName].iata;
    		flt_ecth = airports[arrv_Airpt_CdName].iata +airports[pas_stpName].iata+ airports[dpt_AirPt_CdName].iata;
    	}
    	
    	var dateArray = date.split(' - ');
    	var startTime = dateArray[0];
    	var endTime = dateArray[1];
    	$.ajax({
    	    type:'post',
    	    url : '/restful/getHavingDataByThire',
    	    data:{
    	    	dpt_AirPt_Cd :dpt_AirPt_Cdtemp,	//根据点击的来
    	    	Arrv_Airpt_Cd: arrv_Airpt_Cdtemp ,	//根据点击的来
    	    	pas_stp:pas_stptemp ,	//根据点击的来
    	    	isGoAndBack:isGoAndBack,
    	    	startTime:startTime,
    	    	endTime:endTime,
    	    	flt_ectq:flt_ectq,
    	    	flt_ecth:flt_ecth,
    	    	isIncludeExceptionData: iscludeExe,//是否包含异常数据
    	    	isIncludeExceptionHuangduan: isIncludeExceptionHuangduan,//是否包含异常航段
    	    	isIncludePas: iscludePas,//是否包含经停
    	    	isIncludeRdf: ishasStop,//包含直飞
    	    	fltNbr:''   	    	
    	    },
    	    dataType : 'JSON',
    	    success : function(data) {
    	    	if(data){
    	    		if(data.status=="1"){
    	    			TM_hd_lack();
    	    		}
    	    	}
    	    }
    	});
    }
    var objs={
            back:function(){
            	if($('#cityChoice3').val()==""){
    				$('#pasDiv').removeClass("_checkOp");
            		$('#ishas-stop').removeAttr("disabled");
            		ishasFlag = true;
            	}else{
            		$('#pasDiv').addClass("_checkOp");
    				$('#isnon-stop').attr("disabled",false);
            		$('#ishas-stop').attr("disabled","disabled");
            		$('#ishas-stop').attr("checked", true)
    				ishasFlag = false;
            	}
        }
    };
    oub = objs;
    airControl(".selectCity",objs);
    //判断是否从菜单进入功能页面--start
	if(parent.supData.flag==1){
		parent.supData.flag=0;
		return;
	}
	//判断是否从菜单进入功能页面--end
	var dpt_AirPt_Cd = $('#cityChoice').val();
	var Arrv_Airpt_Cd = $('#cityChoice2').val();
	var pas_stp = $('#cityChoice3').val();
	if(pas_stp!=''){
		pas_stp = airports[pas_stp].iata;
	}
	if(dpt_AirPt_Cd!=''){
		dpt_AirPt_Cd = airports[dpt_AirPt_Cd].iata;
	}
	if(Arrv_Airpt_Cd!=''){
		Arrv_Airpt_Cd = airports[Arrv_Airpt_Cd].iata;
	}
	var isIncloudPasstp = '';
	if($('#ishas-stop').is(':checked')==true){
		isIncloudPasstp = 'on';
	}
	var exceptionData = '';
	//判断异常数据是否选中
	if($('#exceptionData').is(':checked')==true){
		exceptionData = 'on';
	}
	//判断从哪里来（功能间的切换）
	var startTime = parent.supData.startTime;
	var endTime = parent.supData.endTime;
	if(typeof(startTime)!="undefined"){
		 $('#startEndDate').val(startTime+' - '+ endTime);
		 send();
	}else{
		$.ajax({
		    type:'get',
		    url : '/restful/getTotalFlyAnalysisNewDate',
		    data:{
		    	dpt_AirPt_Cd :dpt_AirPt_Cd,
	        	Arrv_Airpt_Cd: Arrv_Airpt_Cd,
	        	pas_stp:pas_stp,
	        	isIncludePas:isIncloudPasstp,
		    },
		    dataType : 'jsonp',
		    success : function(data) {
		    	var date = data.success.newDate;
		    	changeDate('startEndDate',date);
		    	send();
		    }
		});
	}
});
function toEnName(nl){	//中文获取三字码
	var  name="";
	if(nl.indexOf("=")!=-1){
		for (var a=0 ; a<nl.split("=").length;a++){    		
			if(a==nl.split("=").length-1){
				name+= parent.airportMap[nl.split("=")[a]].iata;
			}else{
				name+= parent.airportMap[nl.split("=")[a]].iata+"=";
			}
		}
	}else if(nl.indexOf("-")!=-1){
		for (var a=0 ; a<nl.split("-").length;a++){    		
			if(a==nl.split("-").length-1){
				name+= parent.airportMap[nl.split("-")[a]].iata;
			}else{
				name+= parent.airportMap[nl.split("-")[a]].iata+"-";
			}
		}
	}
	return name;
	
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
function calculate(){
	if($(".lin-historical-body-box>div").length%3==0){
		High=infer(".lin-historical-body-box>div")[1]*($(".lin-historical-body-box>div").length/3)-infer(".lin-historical-body-box")[1];
	}else {
		High=infer(".lin-historical-body-box>div")[1]*(parseInt($(".lin-historical-body-box>div").length/3)+1)-infer(".lin-historical-body-box")[1];
	}
}

function drawing(id,option){
    var dom = document.getElementById(id);
    //用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
    var myChart = echarts.init(dom);
    myChart.setOption(option,true);
}
function send(){
	$(".lin-historical-body").removeClass("muhu");
	$(".err").css("display","none");
	$('#TM-sloading').show();
	//关闭所有选择
	$(".c-selectCity").nextAll().remove();
	$('.opensright').css('display','none');
	$('.lin-historical-body-box').css('top','0px');
	var dpt_AirPt_Cd = $('#cityChoice').val();
	if(dpt_AirPt_Cd!=''){
		dpt_AirPt_Cd = airports[dpt_AirPt_Cd].iata;
	}else{
		$(".lin-historical-body").addClass("muhu");
		$(".err").css("display","block");
		alert('请选择起始机场');
    	$('#TM-sloading').hide('normal');
		return;
	}
	var Arrv_Airpt_Cd = $('#cityChoice2').val();
	if(Arrv_Airpt_Cd!=''){
		Arrv_Airpt_Cd = airports[Arrv_Airpt_Cd].iata;
	}else{
		$(".lin-historical-body").addClass("muhu");
		$(".err").css("display","block");
		alert('请选择到达机场');
    	$('#TM-sloading').hide('normal');
		return;
	}
	//查询条件联动
	var dpt_AirPt_Cdtemp = $('#cityChoice').val();

	var pas_stptemp = $('#cityChoice3').val();
	var arrv_Airpt_Cdtemp = $('#cityChoice2').val();
	var object = parent.supData;
	if(pas_stptemp!=""){
		object.lane =dpt_AirPt_Cdtemp + "=" + pas_stptemp + "=" + arrv_Airpt_Cdtemp ;
	}else{
		object.lane =dpt_AirPt_Cdtemp + "=" + arrv_Airpt_Cdtemp ;
	}
	if($('#ishas-stop').is(':checked')==true){
		object.isIncloudPasstp = 'on';
		object.flt = 0;
	}else{
		object.flt = '1';
		delete object.isIncloudPasstp;
	}
	var pas_stp = $('#cityChoice3').val();
	if(pas_stp!=''){
		pas_stp = airports[pas_stp].iata;
		if(dpt_AirPt_Cd==Arrv_Airpt_Cd||dpt_AirPt_Cd==pas_stp||Arrv_Airpt_Cd==pas_stp){
			$(".lin-historical-body").addClass("muhu");
    		$(".err").css("display","block");
	    	$('#TM-sloading').hide('normal');
			return;
		}
		var obj = $('.lin-historical-body').find('#scroll-bar')[0];
		if(typeof(obj)=='undefined'){
		}
	}else{
		if($('#scroll-bar').length==1){
			$('#scroll-bar').remove();
		}
		if(dpt_AirPt_Cd==Arrv_Airpt_Cd){
			$(".lin-historical-body").addClass("muhu");
    		$(".err").css("display","block");
	    	$('#TM-sloading').hide('normal');
			return;
		}
	}
	var exceptionData = '';
	//判断异常数据是否选中
	if($('#exceptionData').is(':checked')==true){
		exceptionData = 'on';
	}
	var isIncloudPasstp = '';
	if($('#isIncloudPasstp').is(':checked')==true){
		isIncloudPasstp = 'on';
	}
	var date = $('#startEndDate').val();
	var startTime = '';
	var endTime = '';
	if(typeof(date)=='undefined'||date==null||date==''){
		$(".lin-historical-body").addClass("muhu");
		$(".err").css("display","block");
		alert('请选择起止日期');
    	$('#TM-sloading').hide('normal');
		return;
	}else{
		var dateArray = date.split(' - ');
		startTime = dateArray[0];
		endTime = dateArray[1];
		if(new Date(endTime).getTime()-new Date(new Date().format('yyyy-MM-dd')).getTime()>24*1000*60){
			alert('结束日期不能大于当前日期');
			$(".lin-historical-body").addClass("muhu");
    		$(".err").css("display","block");
	    	$('#TM-sloading').hide('normal');
			return;
		}
	}
	 //时间保存起来，用于其它功能
    object.startTime = startTime;
    object.endTime = endTime;
    if(new Date(parseInt(object.endTime.split("-")[0]),parseInt(object.endTime.split("-")[1]),parseInt(object.endTime.split("-")[2])).getTime()>new Date().getTime()){
		object.endTime = new Date().format('yyyy-MM-dd');
	}


	//直飞经停
	var isnon = 'no';
	if($('#isnon-stop').is(':checked')==true){
		isnon = 'on';
	}
	var ispas = 'no';
	if($('#ishas-stop').is(':checked')==true){
		ispas = 'on';
	}
	$.ajax({
	    type:'get',
	    url : '/restful/getTotalFlyAnalysisDataNew',
	    data:{
	    	dpt_AirPt_Cd :dpt_AirPt_Cd,
        	Arrv_Airpt_Cd:Arrv_Airpt_Cd,
        	pas_stp:pas_stp,
        	startDate:startTime,
        	endDate:endTime,
        	isIncludePas:isIncloudPasstp,
        	isIncludeExceptionData:$('#exceptionData').is(':checked')==true?"on":"",
        	isIncludeExceptionHuangduan:$('#exceptionData1').is(':checked')==true?"on":"",
            isIncludePas: ispas,
            isIncludeRdf: isnon
	    },
	    dataType : 'jsonp',
	    success : function(data) {
	    	if(pas_stp != ''){
	    		parent.supData.flt = 0;
	    		if($('#ishas-stop').is(':checked')==false){
	    			$('#ishas-stop').attr('checked',true);
	    			$('#isnon-stop').attr('disabled', false);
	    		}
	    	}
	    	$('#TM-sloading').hide('normal');
	    	for(var key in data.success){
	    		$(".lin_historical_tip").show();
	    		if(data.success[key].exceptionFlag == 1){
	    		}else if(data.success[key].exceptionFlag == 2){
	    			$(".lin_historical_tip").html("<span>&#xe64a;</span> 本结果包含异常数据");
	    		}else if(data.success[key].exceptionFlag == 3){
	    			$(".lin_historical_tip").html("<span>&#xe64a;</span> 本结果包含异常航班");
	    		}else if(data.success[key].exceptionFlag == 4){
	    			$(".lin_historical_tip").html("<span>&#xe64a;</span> 包含异常数据和异常航班");
	    		}
	    		setTimeout(function(){
	    			$(".lin_historical_tip").empty();
	    			$(".lin_historical_tip").hide();
	    		},5000)
	    		break;
	    	}
	    	for(var key in data.success){
	    		table_time.s=data.success[key].startDate;
	    		table_time.e=data.success[key].endDate;
	    		$("#_table_year_s").text(data.success[key].startDate.split("-")[0]);
	    		$("#_table_month_s").text(data.success[key].startDate.split("-")[1]);
	    		
	    		$("#_table_year_e").text(data.success[key].endDate.split("-")[0]);
	    		$("#_table_month_e").text(data.success[key].endDate.split("-")[1]);
	    	}
	    	
	    	tableData=data;
	        if(data!=null&&data!=''&&data.success!=null&&data.success!=''){
	        	ultimately={};
	        	var bz = true;
	        	//判断有无数据
	        	for(var key in data.success){
	        		var obj = data.success[key];
	        		flagMap[key] = {
	        				'flag': obj.flag,
	        				'exceptionFlag': obj.exceptionFlag
	        		}
	        		if(obj.flag=="1"){
	        			bz = false;
	        		}
	        	}
	        	for(var key in data.success){
	        		var obj = data.success[key];
	        		var dataMap = new Map();
	        		dataMap.time = obj.startDate+'-'+obj.endDate;
	        		var amoutAir = new Map();
	        		amoutAir.className = '航班量前10排行（班）';
	        		amoutAir.tagName = ['班次'];
	        		var amoutAirData = new Map();
	        		var seatIncome = new Map();
	        		seatIncome.className = '座收前10排行（元）';
	        		seatIncome.tagName = ["每班营收/1000","座公里收入*100"];
	        		var seatIncomeData = new Map();
	        		var peoples = new Map();
	        		peoples.className = '客量前10排行（人）';
	        		peoples.tagName = ['客量'];
	        		var peoplesData = new Map();
	        		var averagePeoples = new Map();
	        		averagePeoples.className = '均班客量前10排行（人）';
	        		averagePeoples.tagName = ["每班座位","每班旅客","每班团队"];
	        		var averagePeoplesData = new Map();
	        		var proportion = new Map();
	        		proportion.className = '客量占比';
	        		var proportionData = new Map();
	        		//获取参数值
	        		for(var k in obj){
	        			var m = obj[k];
	        			if(k=='hbl'){//航班量
	        				var iter = 0;
	        				for(var i in m){
	        					var a = m[i];
	        					var currData = new Map();
	        					if(typeof(a)!='undefined'&&a!=null){
	        						currData.id = 'AmountAir'+iter;
	        						var nameArray = [];
	        						var valArray = [];
	        						for(var j =0;j<a.length;j++){
	        							var b = a[j];
	        							if(b!=null){
	        								nameArray.push(b.Flt_Nbr);
	        								valArray.push(b.Flt_Nbr_num);
	        							}
	        						}
	        						if(a.length<10){
	        							for(var p=a.length;p<10;p++){
	        								valArray[p] = '-';
	        							}
	        						}
	        						currData.flight = nameArray;
	        						currData.amount = valArray;
	        						iter++;
	        					}
	        					amoutAirData[i] = currData;
	        				}
	        			}else if(k=='zs'){//座收
	        				var iter = 0;
	        				for(var i in m){
	        					var a = m[i];
	        					var currData = new Map();
	        					if(typeof(a)!='undefined'&&a!=null){
	        						currData.id = 'seatIncome'+iter;
	        						var nameArray = [];
	        						var avgFlightIncArry = [];
	        						var seatKtrIncArray = [];
	        						for(var j =0;j<a.length;j++){
	        							var b = a[j];
	        							if(b!=null){
	        								nameArray.push(b.Flt_Nbr);
	        								avgFlightIncArry.push((b.Tal_Nbr_num/1000).toFixed(2));
	        								seatKtrIncArray.push((b.Set_Ktr_Ine_num*100).toFixed(2));
	        							}
	        						}
	        						if(a.length<10){
	        							for(var p=a.length;p<10;p++){
	        								avgFlightIncArry[p] = '-';
	        								seatKtrIncArray[p] = '-';
	        							}
	        						}
	        						currData.flight = nameArray;
	        						currData.revenueRevenue = avgFlightIncArry;
	        						currData.seatRevenue = seatKtrIncArray;
	        						iter++;
	        					}
	        					seatIncomeData[i] = currData;
	        				}
	        			}else if(k=='kl'){//客量
	        				var iter = 0;
	        				for(var i in m){
	        					var a = m[i];
	        					var currData = new Map();
	        					if(typeof(a)!='undefined'&&a!=null){
	        						currData.id = 'peoples'+iter;
	        						var nameArray = [];
	        						var valArray = [];
	        						for(var j =0;j<a.length;j++){
	        							var b = a[j];
	        							if(b!=null){
	        								nameArray.push(b.Flt_Nbr);
	        								valArray.push(b.Pgs_Per_num);
	        							}
	        						}
	        						if(a.length<10){
	        							for(var p=a.length;p<10;p++){
	        								valArray[p] = '-';
	        							}
	        						}
	        						currData.flight = nameArray;
	        						currData.amount = valArray;
	        						iter++;
	        					}
	        				peoplesData[i] = currData;
	        				}
	        			}else if(k=='jbkl'){//均班客量
	        				var iter = 0;
	        				for(var i in m){
	        					var a = m[i];
	        					var currData = new Map();
	        					if(typeof(a)!='undefined'&&a!=null){
	        						currData.id = 'averagePeoples'+iter;
	        						var nameArray = [];
	        						var seatArray = [];
	        						var passengersArray = [];
	        						var teamArray = [];
	        						for(var j =0;j<a.length;j++){
	        							var b = a[j];
	        							if(b!=null){
	        								nameArray.push(b.Flt_Nbr);
	        								seatArray.push(b.Tal_Nbr_Set_num.toFixed(2));
	        								passengersArray.push(b.Pgs_Per_num.toFixed(2));
	        								teamArray.push(b.Grp_Nbr_num.toFixed(2));
	        							}
	        						}
	        						if(a.length<10){
	        							for(var p=a.length;p<10;p++){
	        								seatArray[p] = '-';
	        								passengersArray[p] = '-';
	        								teamArray[p] = '-';
	        							}
	        						}
	        						currData.flight = nameArray;
	        						currData.seat = seatArray;
	        						currData.passengers = passengersArray;
	        						currData.team = teamArray;
	        						iter++;
	        					}
	        					averagePeoplesData[i] = currData;
	        				}
	        			}else if(k=='klzb'){//客量占比
	        				var iter = 0;
	        				for(var i in m){
	        					var a = m[i];
	        					var currData = new Map();
	        					if(typeof(a)!='undefined'&&a!=null){
	        						currData.id = 'proportion'+iter;
	        						var nameArray = [];
	        						var proArray = [];
	        						for(var j =0;j<a.length;j++){
	        							var b = a[j];
	        							if(b!=null){
	        								nameArray.push(b.name);
	        								proArray.push(b.value);
	        							}
	        						}
	        						if(a.length<10){
	        							for(var p=a.length;p<10;p++){
	        								proArray[p] = '-';
	        							}
	        						}
	        						currData.flight = nameArray;
	        						currData.pro = proArray;
	        						iter++;
	        					}
	        					proportionData[i] = currData;
	        				}
	        			}
	        		}
	        		amoutAir.data = amoutAirData;
	        		dataMap.AmountAir = amoutAir;
	        		seatIncome.data = seatIncomeData;
	        		dataMap.seatIncome = seatIncome;
	        		peoples.data = peoplesData;
	        		dataMap.peoples = peoples;
	        		averagePeoples.data = averagePeoplesData;
	        		dataMap.averagePeoples = averagePeoples;
	        		proportion.data = proportionData;
	        		dataMap.proportion = proportion;
	        		ultimately[key] = dataMap;
	        	}
	        	if(bz){
	        		$(".lin-historical-body").addClass("muhu");
	        		$(".err").css("display","block");
	        	}else{
	        		$(".lin-historical-body").removeClass("muhu");
	        		$(".err").css("display","none");
	        	}
	        	addNav();
		        addElement($(".body-navigation-element").eq(0).attr("tag"));
		        //默认选中有数据的
		        var ii=0;
		        for(var key in ultimately){
		        	if(flagMap[key].flag=="1"){
		        		 $('.body-navigation-element').eq(ii).click();
		        		 break;
		        	}
		        	ii++
		        }
		        //$('.body-navigation-element').eq(0).click();
		        //默认执行一次
		        if($(".lin-historical-body-box>div").length%3==0){
		            if($(".lin-historical-body-box>div").length/3>2){
		                calculate();
		            }
		        }else {
		            if((parseInt($(".lin-historical-body-box>div").length/3)+1)>2){
		                calculate();
		            }
		        }

		        $(window).resize(function(){
		            for(var i=0;i<$(".body-navigation-element").length;i++){
		                if($(".body-navigation-element").eq(i).hasClass("set")){
		                    addElement($(".body-navigation-element").eq(i).attr("tag"));
		                }
		            }
		            if($(".lin-historical-body-box>div").length%3==0){
		                if($(".lin-historical-body-box>div").length/3>2){
		                    calculate();
		                }
		            }else {
		                if((parseInt($(".lin-historical-body-box>div").length/3)+1)>2){
		                    calculate();
		                }
		            }
		        });
	        }
	    }
	});
	$('#scroll-bar').css('top','0px');
}

/*计算区域大小*/
function box(){
    $(".lin-historical-body").eq(0).css({"height":infer(".lin-historical")[1]-infer(".sr-box-head")[1]+"px"});//计算内容区的高度
    $(".body-navigation-element").css({"height":infer(".lin-historical-body")[1]/parseInt($(".body-navigation-element").size())}); //计算每个导航块的大小
    for(var i=0;i<$(".tag-box").length;i++){//计算每个导航块的居中位置
        var tagH=(infer(".body-navigation-element")[1]-parseFloat($(".tag-box").eq(i).css("height").split("px")[0]))/2;
        $(".tag-box").eq(i).css({"margin-top":tagH+"px"});
    };
    var Lhbx=infer(".lin-historical-body")[0]-infer(".lin-historical-body-navigation")[0]-1;
    $(".lin-historical-body-box").css({"width":Lhbx+"px","height":infer(".lin-historical-body")[1]});//就按图区盒大小
}
/*添加导航*/
function addNav(){
    var ele="";
    var dataArray = {};
    for(var key in ultimately){
        if(key.indexOf('=')>0){
        	dataArray[key] = ultimately[key];
        }
    }
    for(var key in ultimately){
        if(key.indexOf('=')<0){
        	dataArray[key] = ultimately[key];
        }
    }
    //重新排列map内顺序，使往返航线首先放入map中
    ultimately = dataArray;
    //console.log(flagMap);
    //console.log(dataArray);

    var aaa="";

  //这里生成左侧导航  判断往返还是单段   new
    for(var key in ultimately){
    	//  eflag= "+ flagMap[key].exceptionFlag +"
        if(key.split("=").length>1){	//往返
        	let a = key.replace(/=/g,'<\/div><div class="element-tag">&#xe676;<\/div><div>');
        	if(flagMap[key].flag=="1"){	//判断是否有
            	aaa += "<div class='body-navigation-element two-way' tag="+key+" flag=" + flagMap[key].flag +" eflag= "+ flagMap[key].exceptionFlag +"><div class='tag-box'><div>" + a + "</div></div></div>";
        	}
        	else{
        		aaa += "<div class='body-navigation-element two-way TMnodata-div' tag="+key+" flag="+ flagMap[key].flag +" eflag= "+ flagMap[key].exceptionFlag +"><div class='tag-box'><div>" + a + "</div></div></div>";
        	}
        }
        else{
        	let a = key.replace(/-/g,'<\/div><div class="element-tag">&#xe648;<\/div><div>');
        	if(flagMap[key].flag=="1"){	//判断是否有
            	aaa += "<div class='body-navigation-element' tag="+key+" flag="+ flagMap[key].flag +" eflag= "+ flagMap[key].exceptionFlag +"><div class='tag-box'><div>" + a + "</div></div></div>";
        	}
        	else{
            	aaa += "<div class='body-navigation-element TMnodata-div' tag="+key+" flag="+ flagMap[key].flag +" eflag= "+ flagMap[key].exceptionFlag +"><div class='tag-box'><div>" + a + "</div></div></div>";
        	}
        }
    }

/*    //这里生成左侧导航  判断往返还是单段
    for(var key in ultimately){
        if(key.split("=").length>1){
        	if(key.split("=").length==3){
            	ele+="<div class='body-navigation-element two-way' tag='"+key+"'><div class='tag-box'><div>"+key.split("=")[0]+"</div> <div class='element-tag'>&#xe676;</div><div>"+key.split("=")[1]+"</div><div class='element-tag'>&#xe676;</div><div>"+key.split("=")[2]+"</div> </div> </div>";

        	}else{
        		ele+="<div class='body-navigation-element two-way' tag='"+key+"'><div class='tag-box'><div>"+key.split("=")[0]+"</div> <div class='element-tag'>&#xe676;</div><div>"+key.split("=")[1]+"</div></div> </div>";
        	}
        }else {
            if(key.split('-').length==3){
            	ele+="<div class='body-navigation-element' tag='"+key+"'><div class='tag-box'><div>"+key.split("-")[0]+"</div> <div class='element-tag'>&#xe648;</div><div>"+key.split("-")[1]+"</div><div class='element-tag'>&#xe648;</div><div>"+key.split("-")[2]+"</div> </div> </div>";
            }else{
            	ele+="<div class='body-navigation-element' tag='"+key+"'><div class='tag-box'><div>"+key.split("-")[0]+"</div> <div class='element-tag'>&#xe648;</div><div>"+key.split("-")[1]+"</div></div> </div>";
            }
        }
    }
    */

    $(".lin-historical-body-navigation").html(aaa);
    //$(".body-navigation-element").eq(0).addClass("set");
    //$(".body-navigation-element").eq(1).addClass("set");
}
function addElement(seg){
    box();//计算区域大小;
    var imgBox="";
    for(var t in ultimately[seg].AmountAir.data){
        imgBox+="<div id='"+ultimately[seg].AmountAir.data[t].id+"' class='AmountAir'></div>";
    }
    for(var t in ultimately[seg].seatIncome.data){
        imgBox+="<div id='"+ultimately[seg].seatIncome.data[t].id+"' class='seatIncome'></div>";
    }
    for(var t in ultimately[seg].peoples.data){
        imgBox+="<div id='"+ultimately[seg].peoples.data[t].id+"' class='peoples'></div>";
    }
    for(var t in ultimately[seg].proportion.data){
        imgBox+="<div id='"+ultimately[seg].proportion.data[t].id+"' class='proportion'></div>";
    }
    for(var t in ultimately[seg].averagePeoples.data){
        imgBox+="<div id='"+ultimately[seg].averagePeoples.data[t].id+"' class='averagePeoples'></div>";
    }
    $(".lin-historical-body-box").html(imgBox);
    picturesque(seg);
};
/************************图区域****************************/
function picturesque(seg){
    /*航班客量前10排行*/
    for(var t in ultimately[seg].AmountAir.data){
        var option = {
            title:{
                text:t,
                textStyle:{
                    color:"white",
                    fontWeight:"normal",
                    fontStyle:{
                        size:12
                    }
                },
                left:"3%",
                top:"16%"
            },
            grid: {
                left: '4%',
                right: '10%',
                bottom: '20%',
                top:"25%",
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                borderColor:'#d85430',
                borderWidth:1
            },
            toolbox: {
                show: false
            },
            xAxis:  {
                type: 'category',
                boundaryGap: true,
                data: ultimately[seg].AmountAir.data[t].flight,
                axisTick: {
                    alignWithLabel: false
                },
                axisLine:{
                    show:true,
                    lineStyle:{
                        color:'#475367'
                    }
                },
                splitLine:{
                    show:true,
                    lineStyle:{
                        color:'#475367'
                    }
                },
                axisLabel:{
                    interval:0,
                    rotate:60,
                    margin:10,
    	           	textStyle:{
   	                 color:"white"
   	            }
                }
            },
            yAxis: {
                show:true,
                type: 'value',
                boundaryGap: ['0%', '20%'],
                splitLine:{
                    show:false
                },
                axisLabel: {
                    formatter: '{value}',
    	           	textStyle:{
   	                 color:"white"
   	            }
                },
                axisLine:{
                    show:true,
                    lineStyle:{
                        color:'#475367'
                    }
                },
                axisTick:{
                    show:true
                }
            },
            legend: {
                x:'right',
                show:true,
                data: [{name:'班次',icon:'roundRect', textStyle: {color: 'white'}}],
                itemHeight :10,
                itemWidth:25,
                left:"70%",
                top:"10%"
            },
            series: [
                {
                    name:'班次',
                    type:'bar',
                    stack:'total',
                    label:{
                        normal:{
                            show: false,
                            position:'top'
                        }
                    },
                    barWidth: 20+'px',
                    itemStyle:{
                        normal:{
                            color:'#7ebce9',
                            label : {show: true, position: 'top',textStyle: {color: '#fff'}}
                        }
                    },
                    data:ultimately[seg].AmountAir.data[t].amount,
                    markLine: {
                        silent:false,
                        data: [
                            {
                                type: 'average',
                                name: '平均值'
                            }
                        ]
                    }
                }
            ]
        };
        drawing(ultimately[seg].AmountAir.data[t].id,option);
    };
    /*座收前10排行*/
    for(var t in ultimately[seg].seatIncome.data){
        var option = {
            title:{
                text:t,
                textStyle:{
                    color:"white",
                    fontWeight:"normal",
                    fontStyle:{
                        size:12
                    }
                },
                left:"3%",
                top:"16%"
            },
            grid: {
                left: '4%',
                right: '10%',
                bottom: '20%',
                top:"25%",
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                borderColor:'#d85430',
                borderWidth:1
            },
            toolbox: {
                show: false
            },
            xAxis:  {
                type: 'category',
                boundaryGap: true,
                data: ultimately[seg].seatIncome.data[t].flight,
                axisTick: {
                    alignWithLabel: false
                },
                axisLine:{
                    show:true,
                    lineStyle:{
                        color:'#475367'
                    }
                },
                splitLine:{
                    show:true,
                    lineStyle:{
                        color:'#475367'
                    }
                },
                axisLabel:{
                    interval:0,
                    rotate:60,
                    margin:10,
    	           	textStyle:{
   	                 color:"white"
   	            }
                }
            },
            yAxis: [
                {
                    type: 'value',
                    show:true,
                    boundaryGap: ['0%', '20%'],
                    splitLine:{
                        show:false
                    },
                    axisLabel: {
                        formatter: '{value}',
        	           	textStyle:{
       	                 color:"white"
       	            }
                    },
                    axisLine:{
                        show:true,
                        lineStyle:{
                            color:'#475367'
                        }
                    }
                },{
                    type: 'value',
                    show:false,
                    name:"座公里收入*100",
                    boundaryGap: ['0%', '20%'],
                    splitLine:{
                        show:false
                    },
                    axisLabel: {
                        formatter: '{value}',
        	           	textStyle:{
       	                 color:"white"
       	            }
                    },
                    axisLine:{
                        show:true,
                        lineStyle:{
                            color:'#475367'
                        }
                    }
                }],
            legend: {
                x:'right',
                show:true,
                data: [{name:'每班营收/1000',icon:'roundRect', textStyle: {color: 'white'}},{name:'座公里收入*100',icon:'roundRect',textStyle: {color: 'white'}}],
                itemHeight :10,
                itemWidth:25,
                left:"35%",
                top:"12%"
            },
            series: [
                {
                    name:"座公里收入*100",
                    type:'bar',
                    data:ultimately[seg].seatIncome.data[t].seatRevenue,
                    itemStyle:{
                        normal:{
                            color:'#7ebce9',
                            label : {show: false, position: 'top',textStyle: {color: '#fff'}}
                        }
                    },
                    barWidth:"25%",
                    markLine: {
                        silent:false,
                        data: [
                            {
                                type: 'average',
                                name: '平均值'
                            }
                        ]
                    }
                },
                {
                    name:"每班营收/1000",
                    type:'bar',
                    data:ultimately[seg].seatIncome.data[t].revenueRevenue,
                    itemStyle:{
                        silent:false,
                        normal:{
                            color:'#fdc671',
                            label : {show: false, position: 'top',textStyle: {color: '#fff'}}
                        }
                    },
                    barWidth:"25%",
                    markLine: {
                        silent:false,
                        data: [
                            {
                                type: 'average',
                                name: '平均值'
                            }
                        ]
                    }
                },
            ]
        };
        drawing(ultimately[seg].seatIncome.data[t].id,option);
    };
    /*客量前10排行*/
    for(var t in ultimately[seg].peoples.data){
        var option = {
            title:{
                text:t,
                textStyle:{
                    color:"white",
                    fontWeight:"normal",
                    fontStyle:{
                        size:12
                    }
                },
                left:"3%",
                top:"16%"
            },
            grid: {
                left: '4%',
                right: '10%',
                bottom: '20%',
                top:"25%",
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                borderColor:'#d85430',
                borderWidth:1
            },
            toolbox: {
                show: false
            },
            xAxis:  {
                type: 'category',
                boundaryGap: true,
                data: ultimately[seg].peoples.data[t].flight,
                axisTick: {
                    alignWithLabel: false
                },
                axisLine:{
                    show:true,
                    lineStyle:{
                        color:'#475367'
                    }
                },
                splitLine:{
                    show:true,
                    lineStyle:{
                        color:'#475367'
                    }
                },
                axisLabel:{
                    interval:0,
                    rotate:60,
                    margin:10,
    	           	textStyle:{
   	                 color:"white"
   	            }
                }
            },
            yAxis: {
                show:true,
                type: 'value',
                boundaryGap: ['0%', '20%'],
                splitLine:{
                    show:false
                },
                axisLabel: {
                    formatter: '{value}',
    	           	textStyle:{
   	                 color:"white"
   	            }
                },
                axisLine:{
                    show:true,
                    lineStyle:{
                        color:'#475367'
                    }
                },
                axisTick:{
                    show:true
                }
            },
            legend: {
                x:'right',
                show:true,
                data: [{name:'客量',icon:'roundRect', textStyle: {color: 'white'}}],
                itemHeight :10,
                itemWidth:25,
                left:"70%",
                top:"10%"
            },
            series: [
                {
                    name:'客量',
                    type:'bar',
                    stack:'total',
                    label:{
                        normal:{
                            show: false,
                            position:'top'
                        }
                    },
                    barWidth: 20,
                    itemStyle:{
                        normal:{
                            color:'#7ebce9',
                            label : {show: true, position: 'top',textStyle: {color: '#fff'}}
                        }
                    },
                    data:ultimately[seg].peoples.data[t].amount,
                    markLine: {
                        silent:false,
                        data: [
                            {
                                type: 'average',
                                name: '平均值'
                            }
                        ]
                    }
                }
            ]
        };
        drawing(ultimately[seg].peoples.data[t].id,option);
    };
    /*客量占比*/
    for(var t in ultimately[seg].proportion.data){
        var nums=[];
        for(var k=0;k<ultimately[seg].proportion.data[t].flight.length;k++){
            nums.push({value:ultimately[seg].proportion.data[t].pro[k],name:ultimately[seg].proportion.data[t].flight[k]});
        }
        var option = {
            title :{
                text:t,
                textStyle:{
                    color:"white",
                    fontWeight:"normal",
                    fontStyle:{
                        size:12
                    }
                },
                left:"3%",
                top:"16%"
            },
            tooltip : {
                trigger: 'item',
                borderColor:'#d85430',
                formatter: "{b}<br/>{c} ({d}%)",
                borderWidth:1
            },
            legend: {
                orient: 'vertical',
                left: 'right',
                top:"15%",
                data: ultimately[seg].proportion.data[t].flight,
                textStyle:{
                    color:"white"
                }

            },
            series : [
                {
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '50%'],
                    data:nums,
                    label: {
                        normal: {
                            show: false
                        }
                    },
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
        drawing(ultimately[seg].proportion.data[t].id,option);

    };
    /*均班客量前10排行*/
    for(var t in ultimately[seg].averagePeoples.data){
        var option = {
            title:{
                text:t,
                textStyle:{
                    color:"white",
                    fontWeight:"normal",
                    fontStyle:{
                        size:12
                    }
                },
                left:"3%",
                top:"16%"
            },
            grid: {
                left: '4%',
                right: '10%',
                bottom: '20%',
                top:"25%",
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                borderColor:'#d85430',
                borderWidth:1
            },
            toolbox: {
                show: false
            },
            xAxis:  {
                type: 'category',
                boundaryGap: true,
                data: ultimately[seg].averagePeoples.data[t].flight,
                axisTick: {
                    alignWithLabel: false
                },
                axisLine:{
                    show:true,
                    lineStyle:{
                        color:'#475367'
                    }
                },
                splitLine:{
                    show:true,
                    lineStyle:{
                        color:'#475367'
                    }
                },
                axisLabel:{
                    interval:0,
                    rotate:60,
                    margin:10,
    	           	textStyle:{
   	                 color:"white"
   	            }
                }
            },
            yAxis: [
                {
                    type: 'value',
                    show:true,
                    boundaryGap: ['0%', '20%'],
                    splitLine:{
                        show:false
                    },
                    axisLabel: {
                        formatter: '{value}',
        	           	textStyle:{
       	                 color:"white"
       	            }
                    },
                    axisLine:{
                        show:true,
                        lineStyle:{
                            color:'#475367'
                        }
                    }
                },{
                    type: 'value',
                    show:false,
                    name:"座公里收入*100",
                    boundaryGap: ['0%', '20%'],
                    splitLine:{
                        show:false
                    },
                    axisLabel: {
                        formatter: '{value}',
        	           	textStyle:{
       	                 color:"white"
       	            }
                    },
                    axisLine:{
                        show:true,
                        lineStyle:{
                            color:'#475367'
                        }
                    }
                }],
            legend: {
                x:'right',
                show:true,
                data: [{name:'每班旅客',icon:'roundRect', textStyle: {color: 'white'}},{name:'每班团队',icon:'roundRect',textStyle: {color: 'white'}},{name:'每班座位',icon:'roundRect',textStyle: {color: 'white'}}],
                itemHeight :10,
                itemWidth:25,
                left:"35%",
                top:"12%"
            },
            series: [
                {
                    name:"每班旅客",
                    type:'bar',
                    data:ultimately[seg].averagePeoples.data[t].passengers,
                    itemStyle:{
                        normal:{
                            color:'#fdc671',
                            label : {show: false, position: 'top',textStyle: {color: '#fff'}}
                        }
                    },
                    barWidth:"20%",
                    markLine: {
                        data: [
                            {
                                type: 'average',
                                name: '平均值'
                            }
                        ]
                    }
                },
                {
                    name:"每班团队",
                    type:'bar',
                    data:ultimately[seg].averagePeoples.data[t].team,
                    itemStyle:{
                        normal:{
                            color:'#52b778',
                            label : {show: false, position: 'top',textStyle: {color: '#fff'}}
                        }
                    },
                    barWidth:"20%",
                    markLine: {
                        data: [
                            {
                                type: 'average',
                                name: '平均值'
                            }
                        ]
                    }
                },
                {
                    name:"每班座位",
                    type:'bar',
                    data:ultimately[seg].averagePeoples.data[t].seat,
                    itemStyle:{
                        normal:{
                            color:'#7ebce9',
                            label : {show: false, position: 'top',textStyle: {color: '#fff'}}
                        }
                    },
                    barWidth:"20%",
                    markLine: {
                        data: [
                            {
                                type: 'average',
                                name: '平均值'
                            }
                        ]
                    }
                }
            ]
        };
        drawing(ultimately[seg].averagePeoples.data[t].id,option);
    }





    $(".AmountAir").eq(0).append("<div class='AmountAir-title'><p>"+ultimately[seg].time+"</p><h3>"+ultimately[seg].AmountAir.className+"</h3></div>");
    $(".seatIncome").eq(0).append("<div class='seatIncome-title'><p>"+ultimately[seg].time+"</p><h3>"+ultimately[seg].seatIncome.className+"</h3></div>");
    $(".peoples").eq(0).append("<div class='peoples-title'><p>"+ultimately[seg].time+"</p><h3>"+ultimately[seg].peoples.className+"</h3></div>");
    $(".proportion").eq(0).append("<div class='proportion-title'><p>"+ultimately[seg].time+"</p><h3>"+ultimately[seg].proportion.className+"</h3></div>");
    $(".averagePeoples").eq(0).append("<div class='averagePeoples-title'><p>"+ultimately[seg].time+"</p><h3>"+ultimately[seg].averagePeoples.className+"</h3></div>");
}

function changeDate(id,date){
	// 参数表示在当前日期下要增加的天数
	var d = new Date(date);
 	d.setDate(d.getDate()-30);
 	var month=d.getMonth()+1;
 	var day = d.getDate();
    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }
    $('#'+id).val(d.getFullYear() +'-'+ month +'-'+ day +' - '+ date);
}

$("#spae_sector").on("click",function(){
	if($(this).attr("tag")=="graph"){
		$(this).attr("tag","table");
		$(".spae_sector_text").html("&#xe6bc;");
		$("#spae_sector_cont").removeClass("spae_sector_cont_c");
		$("#scroll-bar").css("z-index","-1");
		$(".lin-historical-body-box").css("display","none");
		$(".lin-historical-body-box").css("display","none");
		
	}else{
		$(this).attr("tag","graph");
		$(".spae_sector_text").html("&#xe750;");
		$("#spae_sector_cont").addClass("spae_sector_cont_c");
		$("#scroll-bar").css("z-index","20");
		$(".lin-historical-body-box").css("display","block");
		addElement($(".set").attr("tag"));
	}
});

function getTime2Time($time1, $time2)
{
    var time1 = arguments[0], time2 = arguments[1];
    time1 = Date.parse(time1)/1000;
    time2 = Date.parse(time2)/1000;
    var time_ = time1 - time2;
    return (time_/(3600*24));
}
var t_data;
function segment(data){
	if(tableData){
		var table_list = new Object();
		var node="<ul>";
		for(var key in tableData.success[data].hbl){
			node+="<li class='SD-nomal' tag="+key+"><p>航段<br><span class='hd'>"+toEnName(key)+"</span></p></li>";
		}
		node+="</ul>";
		$("#SD-table-head").html(node);
		$(".SD-nomal").on("click",function(){
			  t_data={
				common:{
			            "航班号":[], //
			            "总班次（次）":[], //
			            "每班座位":[], //
			            "每班团队（人）":[], //
			            "散团总客量":[], //
                        "客座率（%）":[],
			            "每班团队营收（元）":[],
			            "每班营收（元）":[], //
			            "座公里收入（元）":[], //
			            "平均折扣（%）":[],
			            "散客折扣（%）":[],
			            "团队折扣（%）":[],
		        },
		        special:''

			  };
			  var jbdata={
					"航班号":"", //
		            "总班次（次）":0, //
		            "每班座位":0, //
		            "每班团队（人）":0, //
		            "散团总客量":0, //
		            "每班团队营收（元）":0,
		            "客座率（%）":0,
		            "每班营收（元）":0, //
		            "座公里收入（元）":0, //
		            "平均折扣（%）":0,
		            "散客折扣（%）":0,
		            "团队折扣（%）":0,
		            time:Math.abs(getTime2Time(table_time.s,table_time.e)),
		            number:0
			  };
			  var hzdata={
						"航班号":"", //
			            "总班次（次）":0, //
			            "每班座位":0, //
			            "每班团队（人）":0, //
			            "散团总客量":0, //
			            "每班团队营收（元）":0,
			            "客座率（%）":0,
			            "每班营收（元）":0, //
			            "座公里收入（元）":0, //
			            "平均折扣（%）":0,
			            "散客折扣（%）":0,
			            "团队折扣（%）":0,
			            time:Math.abs(getTime2Time(table_time.s,table_time.e)),
			            number:0
				  };

			  for(var i=0;i<tableData.success[data].excelData[data][$(this).attr("tag")].length;i++){
				  t_data.common["航班号"].push(tableData.success[data].excelData[data][$(this).attr("tag")][i].Flt_Nbr);
				  t_data.common["总班次（次）"].push(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].Flt_Nbr_num));
				  t_data.common["每班座位"].push(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbzw));
				  t_data.common["散团总客量"].push(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbkl));
				  t_data.common["每班团队（人）"].push(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbtd));
				  t_data.common["每班营收（元）"].push(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].Tal_Nbr_num));
				  t_data.common["座公里收入（元）"].push(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].Set_Ktr_Ine_num));
				  t_data.common["平均折扣（%）"].push(parseFloat(tableData.success[data].excelData[data][$(this).attr("tag")][i].pjzk)*100);
				  t_data.common["散客折扣（%）"].push((parseFloat(tableData.success[data].excelData[data][$(this).attr("tag")][i].skzk)*100).toFixed(2));
				  t_data.common["团队折扣（%）"].push(((parseFloat(tableData.success[data].excelData[data][$(this).attr("tag")][i].tdzk)*100)).toFixed(2));
				  t_data.common["客座率（%）"].push(parseFloat(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbkzl));
				  t_data.common["每班团队营收（元）"].push(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbtdsr));


				  jbdata["总班次（次）"]+=Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].Flt_Nbr_num); //
				  jbdata["每班座位"]+=Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbzw); //
				  jbdata["散团总客量"]+=Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbkl); //
				  jbdata["每班团队（人）"]+=Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbtd); //
				  jbdata["每班营收（元）"]+=Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].Tal_Nbr_num); //
				  jbdata["座公里收入（元）"]+=Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].Set_Ktr_Ine_num); //
				  jbdata["平均折扣（%）"]+=Number(parseFloat(tableData.success[data].excelData[data][$(this).attr("tag")][i].pjzk)*100); //
				  jbdata["散客折扣（%）"]+=Number(parseFloat(tableData.success[data].excelData[data][$(this).attr("tag")][i].skzk)*100); //
				  jbdata["团队折扣（%）"]+=Number(parseFloat(tableData.success[data].excelData[data][$(this).attr("tag")][i].tdzk)*100); //
				  jbdata["客座率（%）"]+=Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbkzl); //
				  jbdata["每班团队营收（元）"]+=Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbtdsr); //
				  jbdata.number++;


				  var zbc=parseFloat(tableData.success[data].excelData[data][$(this).attr("tag")][i].Flt_Nbr_num);
				  hzdata["总班次（次）"]+=zbc;//
				  hzdata["每班座位"]+=(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbzw)*zbc); //
				  hzdata["散团总客量"]+=(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbkl)*zbc); //
				  hzdata["每班团队（人）"]+=(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbtd)*zbc); //
				  hzdata["每班营收（元）"]+=(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].Tal_Nbr_num)*zbc); //
				  hzdata["座公里收入（元）"]+=Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].Set_Ktr_Ine_num) ;//
				  hzdata["平均折扣（%）"]+=parseFloat(tableData.success[data].excelData[data][$(this).attr("tag")][i].pjzk)*100; //
				  hzdata["散客折扣（%）"]+=parseFloat(tableData.success[data].excelData[data][$(this).attr("tag")][i].skzk)*100; //
				  hzdata["团队折扣（%）"]+=parseFloat(tableData.success[data].excelData[data][$(this).attr("tag")][i].tdzk); //
				  hzdata["客座率（%）"]+=(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbkzl)*zbc); //
				  hzdata["每班团队营收（元）"]+=(Number(tableData.success[data].excelData[data][$(this).attr("tag")][i].jbtdsr)*zbc); //
				  hzdata.number++;

			  }

			  /*
			   * 改变散团总客量、每班营收的算法
			   * changed by long
			   * 
			   * */
			  jbdata["总班次（次）"]=(jbdata["总班次（次）"]/jbdata.number).toFixed(0);
			  jbdata["每班座位"]=(jbdata["每班座位"]/jbdata.number).toFixed(0);
//			  jbdata["散团总客量"]=(jbdata["散团总客量"]/jbdata.number).toFixed(0);
			  jbdata["散团总客量"]=(hzdata["散团总客量"]/hzdata["总班次（次）"]).toFixed(2);
              jbdata["每班团队（人）"]=(jbdata["每班团队（人）"]/jbdata.number).toFixed(0);
//            jbdata["每班营收（元）"]=(jbdata["每班营收（元）"]/jbdata.number).toFixed(2);
              jbdata["每班营收（元）"]=(hzdata["每班营收（元）"]/hzdata["总班次（次）"]).toFixed(2);
              jbdata["座公里收入（元）"]=(jbdata["座公里收入（元）"]/jbdata.number).toFixed(2);
              jbdata["平均折扣（%）"]=(jbdata["平均折扣（%）"]/jbdata.number).toFixed(2);
              jbdata["散客折扣（%）"]=(jbdata["散客折扣（%）"]/jbdata.number).toFixed(2);
              jbdata["团队折扣（%）"]=(jbdata["团队折扣（%）"]/jbdata.number).toFixed(2);
              jbdata["客座率（%）"]=Number(hzdata["客座率（%）"]/hzdata["总班次（次）"]).toFixed(2);
              jbdata["每班团队营收（元）"]=(hzdata["每班团队营收（元）"]/hzdata["总班次（次）"]).toFixed(2);
              
              if(t_data.common["航班号"].length>0){
            	  t_data.common["航班号"].push("均班");
    			  t_data.common["总班次（次）"].push("-");
    			  t_data.common["每班座位"].push(Number(hzdata["每班座位"]).toFixed(0)/Number(hzdata["总班次（次）"]).toFixed(0));
    			  t_data.common["散团总客量"].push(jbdata["散团总客量"]);
    			  t_data.common["每班团队（人）"].push(Number(hzdata["每班团队（人）"]).toFixed(0)/Number(hzdata["总班次（次）"]).toFixed(0));
    			  t_data.common["每班营收（元）"].push(jbdata["每班营收（元）"]);
    			  t_data.common["座公里收入（元）"].push(jbdata["座公里收入（元）"]);
    			  t_data.common["平均折扣（%）"].push(jbdata["平均折扣（%）"]);
    			  t_data.common["散客折扣（%）"].push(jbdata["散客折扣（%）"]);
    			  t_data.common["团队折扣（%）"].push(jbdata["团队折扣（%）"]);
    			  t_data.common["客座率（%）"].push(jbdata["客座率（%）"]);
    			  t_data.common["每班团队营收（元）"].push(jbdata["每班团队营收（元）"]);
    			  
    			  t_data.common["航班号"].push("合计");
    	            t_data.common["总班次（次）"].push(Number(hzdata["总班次（次）"]).toFixed(0));
    	            t_data.common["每班座位"].push(Number(hzdata["每班座位"]).toFixed(0));
    	            t_data.common["散团总客量"].push(Number(hzdata["散团总客量"]).toFixed(0));
    	            t_data.common["每班团队（人）"].push(Number(hzdata["每班团队（人）"]).toFixed(0));
    	            t_data.common["每班营收（元）"].push(Number(hzdata["每班营收（元）"]).toFixed(2));
    	            t_data.common["座公里收入（元）"].push((Number(hzdata["座公里收入（元）"]/hzdata.number).toFixed(2)));
    	            t_data.common["平均折扣（%）"].push(Number(hzdata["平均折扣（%）"]/hzdata.number).toFixed(2));
    	            t_data.common["散客折扣（%）"].push(Number(hzdata["散客折扣（%）"]/hzdata.number).toFixed(2));
    	            t_data.common["团队折扣（%）"].push(Number(hzdata["团队折扣（%）"]/hzdata.number).toFixed(2));
    	            t_data.common["客座率（%）"].push(Number(hzdata["客座率（%）"]/hzdata["总班次（次）"]).toFixed(2));
    	            t_data.common["每班团队营收（元）"].push(Number(hzdata["每班团队营收（元）"]).toFixed(2));
                  $(this).addClass("SD-set").siblings().removeClass("SD-set");
                  $("#ta").html(spae_table1(t_data));
                  fone = false;
              }else{
                  if(fone){
                      $('.SD-nomal').eq($(this).index() + 1).click();
                  }else{
                      $('.SD-hd-null').css('display','block');
                      setTimeout(function(){
                          $('.SD-hd-null').css('display','none');
                      },2000)
                  }
              }

		})
	}
}

function bubbleSort(array,type) {
    var i = 0,
    len = array.length,
    j, d;
    if(len>3){
    	 if(type==1){
        	 for (; i < (len-2); i++) {
            	 for (j = 0; j < len-2; j++) {
            	        if (parseFloat(array[i]) < parseFloat(array[j])) {
            	            d =parseFloat(array[j]);
            	            console.log(d);
            	            array[j] = parseFloat(array[i]);
            	            array[i] = d;
            	            console.log(d)
            	            for(var key in t_data.common){
            	            	d=t_data.common[key][j];
              	            	t_data.common[key][j] = t_data.common[key][i];
              	            	t_data.common[key][i] = d;
              	            }
            	        }
            	  }
            }
        }else{
        	 for (; i < len-2; i++) {
            	 for (j = 0; j <len-2; j++) {
            	        if (parseFloat(array[i]) > parseFloat(array[j])) {
            	            d =parseFloat(array[j]);
            	            array[j] = parseFloat(array[i]);
            	            array[i] = d;
            	            for(var key in t_data.common){
            	            	d=t_data.common[key][j];
              	            	t_data.common[key][j] = t_data.common[key][i];
              	            	t_data.common[key][i] = d;
              	            }
            	        }
            	  }
            }
        }
    }
    $("#ta").html(spae_table1(t_data));
}
function table_px(ot,type){
	if($(ot).hasClass("_px_set0")){
		//倒叙
		var name=$(ot).attr("line");
		$("._px_span").removeClass("_px_set1");
		$("._px_span").removeClass("_px_set0");
		bubbleSort(JSON.parse(JSON.stringify(t_data.common[$(ot).attr("line")])),0);
		$("[line='"+name+"']").eq(1).addClass("_px_set1");
	}else{
		//正序
		var name=$(ot).attr("line");
		$("._px_span").removeClass("_px_set1");
		$("._px_span").removeClass("_px_set0");
		bubbleSort(JSON.parse(JSON.stringify(t_data.common[$(ot).attr("line")])),1);
		$("[line='"+name+"']").eq(1).addClass("_px_set0");
	}	
}

var hasAbnormal= function(statue){
	if(statue == 1) return false;
	$(".abnormalData_prompt").css({
	    'top': '5%',
    	'left': '44%'
	})
	$(".lin_historical_tip").show();
	if(statue == '2') $(".lin_historical_tip").html("<span>&#xe64a;</span> 当前查询条件下包含异常数据");//只包含异常数据
	if(statue == '3') $(".lin_historical_tip").html("<span>&#xe64a;</span> 当前查询条件下包含异常航班");//只包含异常航班
	if(statue == '4') $(".lin_historical_tip").html("<span>&#xe64a;</span> 当前查询条件下包含异常数据和异常航班");//both
	setTimeout(function(){
		$(".lin_historical_tip").empty();
		$(".lin_historical_tip").hide();
	},3000)
}


//for 直飞经停最少选一个       by long   2017-8-22
$('#isnon-stop').on('change',function(e){
	if(!$('#isnon-stop').is(':checked')==true){
		$('#ishas-stop').attr('disabled', true);
		$('#ishas-stop').attr('checked', true);
	}else{
		if(ishasFlag){
			$('#ishas-stop').attr('disabled', false);			
		}else{
			$('#ishas-stop').attr('disabled', !ishasFlag);
		}
	}
})
$('#ishas-stop').on('change',function(e){
	var mark = ishasFlag===mark?mark:ishasFlag;
	if(!$('#ishas-stop').is(':checked')==true){
		$('#isnon-stop').attr('disabled',true);
		$('#isnon-stop').attr('checked', true);
	}else{
		$('#isnon-stop').attr('disabled',!mark);		
	}
	mark = !mark;
})




