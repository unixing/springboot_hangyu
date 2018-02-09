var ultimately="";
/*滚动条*/
var High=0,table_data="";

var dpt_AirPt_Cd;
var airports = parent.airportMap;
var currentCheckedYear = '';
var ff1 = true, ff2 = true, ff3 = true;
var rangerTime = ['2017-01-01', '2017-1-30'];
var monthDayFlag = 'day';
var exflaga=1,exflagb=1,exflagc=1;

function fullDay(list){		//补足day，求某月最后一天
	var dd1 = list[0], dd2 = list[1];
	dd1 = dd1 + '-01';
	var new_date = new Date(dd2.split('-')[0],dd2.split('-')[1],1);
	dd2 = dd2 + '-' +(new Date(new_date.getTime()-1000*60*60*24)).getDate();
	return [dd1,dd2];
}

function changeDate(dateList){
	$('#reservation').daterangepicker({
		startDate: dateList[0],
		endDate: dateList[1],
		dateLimit : {
			  days : 366
		  },
	},null);
	$('#reservation').val(dateList.join(' - '));
	$('.daterangepicker .applyBtn').on('click', function(){
		setTimeout(function(){			
			rangerTime = $('#reservation').val().split(' - ');
			if(rangerTime[0].split('-').length<3) {
				rangerTime = fullDay(rangerTime);
				monthDayFlag = 'month';
			}else{
				monthDayFlag = 'day';
			}
			
		},50)
	})
}
$(function(){
	var dptabbr = airports[parent.cityIata].aptChaNam + "-" + parent.cityIata;
	changeDate(rangerTime);	
	$('#cityChoice').attr("abbr",dptabbr);
	$('#cityChoice').val(airports[parent.cityIata].aptChaNam);
	$('.air-name').text(airports[parent.cityIata].aptChaNam);
	getYears();
    $(".Into").on("click",function(){
    	$(".exportExl").attr("onclick","javascript:method1('ta')");
		create_abnormal(exflaga);
    	toggleBody(false);
        if(!$(this).hasClass("set")){
            $(this).addClass("set").siblings().removeClass("set");
            drawing();
        }
        if(ff1){
    		$(".lin-historical-body-box").addClass("muhu");
    		$(".reportErr").css("display","block");
    		$('#scroll-bar').css("display","none");
    	}else{
    		$(".lin-historical-body-box").removeClass("muhu");
    		$(".reportErr").css("display","none");
    		$('#scroll-bar').css("display","block");
    	}
    });
    $(".Out").on("click",function(){
    	$(".exportExl").attr("onclick","javascript:method1('ta')");
		create_abnormal(exflagb);
    	toggleBody(false);
        if(!$(this).hasClass("set")){
            $(this).addClass("set").siblings().removeClass("set");
            out();
        }
        if(ff2){
    		$(".lin-historical-body-box").addClass("muhu");
    		$(".reportErr").css("display","block");
    		$('#scroll-bar').css("display","none");
    	}else{
    		$(".lin-historical-body-box").removeClass("muhu");
    		$(".reportErr").css("display","none");
    		$('#scroll-bar').css("display","block");
    	}
    });
    $(".Total").on("click",function(){
    	if($('#totalTableBody tr').length === 0){
            $("#TM-sloading").show();
    	}
    	$(".exportExl").attr("onclick","javascript:method1('totalTable')");
		create_abnormal(exflagc);
        if(!$(this).hasClass("set")){
            $(this).addClass("set").siblings().removeClass("set");
        }
        if(false){
    		$(".lin-historical-body-box").addClass("muhu");
    		$(".reportErr").css("display","block");
    		$('#scroll-bar').css("display","none");
    	}else{
    		$(".lin-historical-body-box").removeClass("muhu");
    		$(".reportErr").css("display","none");
    		$('#scroll-bar').css("display","block");
    	}
        toggleBody(true);
    });
    
    send();
    var objs={
            back:function(){
//            	getFlt_Nbr();
            }
        };
	oub = objs;
    airControl(".selectCity",objs);
});
//控制区域显示，
function toggleBody(tag){
	if(tag){
        $('#oldIO').hide();
        $('#newTotal').show();
		$('#spae_sector').hide();
	}else{
        $('#oldIO').show();
        $('#newTotal').hide();
		$('#spae_sector').show();    		
	}
}
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
				//符号的转义
//				op_text.replace(/摄氏度/,"°C");
//				op_text.replace(/度/,"°");
//				op_text.replace(/米每秒/,"m/s");
//				op_text.replace(/米/,"m");
//				op_text.replace(/百帕/,"°hPa");
//				op_text.replace(/英寸汞柱/,"inHg");
				$("#weather .ptxt").html(op_text);
				
			}
		}
	})
}
function calculate(){
    High=infer("#inTrafficAnalysis")[1]*4;
}
function box(){
    $(".lin-historical-body").eq(0).css({"height":infer(".lin-historical")[1]-infer(".sr-box-head")[1]+"px"});//计算内容区的高度
    $(".body-navigation-element").css({"height":infer(".lin-historical-body")[1]/parseInt($(".body-navigation-element").size())}); //计算每个导航块的大小
    $(".lin-historical-body-navigation>div").css("line-height",infer(".lin-historical-body-navigation")[1]/3+"px"); //让导航字体居中
    $(".lin-historical-body-navigation>div").eq(0).text('进港');
    $(".lin-historical-body-navigation>div").eq(0).addClass("set").siblings().removeClass("set");
    $(".lin-historical-body-navigation>div").eq(1).text('出港');
    var Lhbx=infer(".lin-historical-body")[0]-infer(".lin-historical-body-navigation")[0]-301;
//    $(".lin-historical-body-box").css({"width":Lhbx+"px","height":infer(".lin-historical-body")[1]});//就按图区盒大小
    $(".lin-historical-body-box").css({"height":infer(".lin-historical-body")[1]});//宽度改为铺满
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
function send(){
	$("#TM-sloading").show();
	$('#totalTableBody').empty();
	//关闭所有选择框
	ff = true;
	$(".c-selectCity").nextAll().remove();
	dpt_AirPt_Cd = $('#cityChoice').val().toUpperCase();
	var cpy_Nm = $('#cpy_Nm').val();
	if(dpt_AirPt_Cd!=''){
		$('.air-name').text(dpt_AirPt_Cd);//设置机场名称
		dpt_AirPt_Cd = airports[dpt_AirPt_Cd].iata;
	}else{
		ff1 = true;
		ff2 = true;
		$(".lin-historical-body-box").addClass("muhu");
		$(".reportErr").css("display","block");
		alert('请选择起始机场');
    	$("#TM-sloading").hide('normal');
		return;
    }
    var fstart,fend;
    if(monthDayFlag=='month'){
        fstart = rangerTime[0];
        fend = getDateToLastDay(rangerTime[1]);
    }else{
        fstart = rangerTime[0];
        fend = rangerTime[1];
    }
    if(daysBetween(fstart,fend)>366){
        $('#TM-sloading').hide('normal');
		$(".lin-historical-body-box").addClass("muhu");
		$(".reportErr").css("display","block");
        return alert("请将范围控制在 366天 以内！");
    }

	var exData = $('#exceptionData').is(':checked')? 'on' : 'no',
	exFlight = $('#exceptionFlyData').is(':checked')? 'on' : 'no';
	toggleBody(false);
	$.ajax({
        url : '/airport_Operating_History',
        type: 'POST',
        data:{
        	dpt_AirPt_Cd :dpt_AirPt_Cd,
	    	startTime: rangerTime[0],
	    	endTime: rangerTime[1],
	    	cpy_Nm : cpy_Nm,
	    	isIncludeExceptionData: exData,
	    	isIncludeExceptionHuangduan: exFlight,
	    	monthOrDay: monthDayFlag
	    },
        success : function(data) {
            $("#TM-sloading").hide('normal');
        	exflaga=data.exceptionFlagCg,exflagb=data.exceptionFlagJg;
        	table_data=JSON.parse(JSON.stringify(data))
        	var bz1 = true;
        	var bz2 = true;
        	if(data.outPort1.flag == "1"){
        		bz1 = false;
        		ff1 = false;
        		$('#scroll-bar').css("display","block");
        	}else{
        		$('#scroll-bar').css("display","none");
        		ff1 = true;
        	}
        	if(data.outPort2.flag == "1"){
        		bz2 = false;
        		ff2 = false;
        	}else{
        		ff2 = true;
        	}
        	//出港----------------------------------------------------
//        	月流量走势分析数据组装
        	var month = [];//月流量走势分析
        	var m_k = [];//客流
        	var m_z = [];//座位
        	var m_b = [];//班次*100
        	var m_g = [];//团队
        	var m_gs = [];//团队收入/100
        	var m_s = [];//收入/1000
        	for(var i in data.outPort1.list){
        		month[i] = data.outPort1.list[i].label;
        		m_k[i] = data.outPort1.list[i].idd_moh;
        		m_z[i] = data.outPort1.list[i].Tal_Nbr_Set;
        		m_b[i] =Number(data.outPort1.list[i].Cla_Nbr)*100;
        		m_g[i] = data.outPort1.list[i].Grp_moh;
        		m_gs[i] = (Number(data.outPort1.list[i].Grp_Ine)/100).toFixed(2);
        		m_s[i] = (Number(data.outPort1.list[i].Tol_Ine)/1000).toFixed(2);
        	}
//        	均班客流量走势分析数据组装
        	var j_month = [];//月流量走势分析
        	var j_k = [];//客流
        	var j_z = [];//座位
        	var j_b = [];//班次*100
        	var j_g = [];//团队
        	var j_gs = [];//团队收入/100
        	var j_s = [];//收入/1000
        	for(var i in data.evenPort1.list){
        		j_month[i] = data.evenPort1.list[i].label;
        		j_k[i] = data.evenPort1.list[i].cla_Per;
        		j_z[i] = data.evenPort1.list[i].cla_Set;
        		j_b[i] =data.evenPort1.list[i].lod_For;
        		j_g[i] = data.evenPort1.list[i].cla_Grp;
        		j_gs[i] = data.evenPort1.list[i].flt_Ser_Ine;
        		j_s[i] = data.evenPort1.list[i].idd_Dct;
        	}
//        	年班次前十排行数据组装
        	var n_d = [];
        	var n_d_a = [];
        	var n_d_a_m = [];
        	for(var i in data.class1.list){
        		n_d[i] = data.class1.list[i].count;
//        		n_d_a[i] = data.class1.list[i].dpt_AirPt_Cd +"-"+data.class1.list[i].arrv_Airpt_Cd;
        		n_d_a[i] = data.class1.list[i].dpt_AirPt_Cd +"-"+data.class1.list[i].arrv_Airpt_Cd;
        		n_d_a_m[n_d_a[i]] = data.class1.list[i].dpt_AirPt_Cd +"-"+data.class1.list[i].arrv_Airpt_Cd;
        	}
//        	座公里收入前十排行数据组装
        	var s_z = [];
        	var s_b = [];
        	var s_d_a = [];
        	var s_d_a_m = [];
        	for(var i in data.set1.list){
        		s_b[i] = data.set1.list[i].set_Ktr_Ine;
        		s_z[i] = data.set1.list[i].count;
//        		s_d_a[i] = data.set1.list[i].dpt_AirPt_Cd + "-" +data.set1.list[i].arrv_Airpt_Cd;
        		s_d_a[i] = data.set1.list[i].dpt_AirPt_Cd +"-"+data.set1.list[i].arrv_Airpt_Cd;
        		s_d_a_m[s_d_a[i]] = data.set1.list[i].dpt_AirPt_Cd +"-"+data.set1.list[i].arrv_Airpt_Cd;
        	}
//        	旅客前十排行数据组装 ,各航线年客量占比
        	var l_z = [];
        	var l_d_a = [];
        	var l_d_a_m = [];
        	for(var i in data.amoun1.list){
        		l_z[i] = data.amoun1.list[i].value;
//        		l_d_a[i] = data.amoun1.list[i].dpt_AirPt_Cd + "-" +data.amoun1.list[i].arrv_Airpt_Cd;
        		l_d_a[i] = data.amoun1.list[i].dpt_AirPt_Cd +"-"+data.amoun1.list[i].arrv_Airpt_Cd;
        		l_d_a_m[l_d_a[i]] = data.amoun1.list[i].dpt_AirPt_Cd +"-"+data.amoun1.list[i].arrv_Airpt_Cd;
        	}
//        	出港均班前十排行数据组装
        	var j_z_2 = [];
        	var j_l_2 = [];
        	var j_t_2 = [];
        	var j_d_a_2 = [];
        	var j_d_a_2_m = [];
        	for(var i in data.guest1.list){
        		j_z_2[i] = data.guest1.list[i].count;
        		j_l_2[i] = data.guest1.list[i].set_Ktr_Ine;
        		j_t_2[i] = data.guest1.list[i].guestamount;
//        		j_d_a_2[i] = data.guest1.list[i].dpt_AirPt_Cd + "-" +data.guest1.list[i].arrv_Airpt_Cd;
        		j_d_a_2[i] = data.guest1.list[i].dpt_AirPt_Cd +"-"+data.guest1.list[i].arrv_Airpt_Cd;
        		j_d_a_2_m[j_d_a_2[i]] = data.guest1.list[i].dpt_AirPt_Cd +"-"+data.guest1.list[i].arrv_Airpt_Cd;
        	}
//        	各航司旅客前十,客量占比,数据组装
        	var h_z = [];
        	var h_d_a = [];
        	for(var i in data.cpy_amoun1.list){
        		h_z[i] = data.cpy_amoun1.list[i].value;
        		h_d_a[i] = data.cpy_amoun1.list[i].cpy_Nm;
        	}
//        	各航司年班次前十排行
        	var h_b_2 =[];
        	var h_b_d_a_2 = [];
        	for(var i in data.cpy_class1.list){
        		h_b_2[i] = data.cpy_class1.list[i].count;
        		h_b_d_a_2[i] = data.cpy_class1.list[i].cpy_Nm;
        	}
        	
        	//进港------------------------------------------------------------
//        	月流量走势分析数据组装
        	var c_month = [];//月流量走势分析
        	var c_m_k = [];//客流
        	var c_m_z = [];//座位
        	var c_m_b = [];//班次*100
        	var c_m_g = [];//团队
        	var c_m_gs = [];//团队收入/100
        	var c_m_s = [];//收入/1000
        	for(var i in data.outPort2.list){
        		c_month[i] = data.outPort2.list[i].label;
        		c_m_k[i] = data.outPort2.list[i].idd_moh;
        		c_m_z[i] = data.outPort2.list[i].Tal_Nbr_Set;
        		c_m_b[i] =Number(data.outPort2.list[i].Cla_Nbr)*100;
        		c_m_g[i] = data.outPort2.list[i].Grp_moh;
        		c_m_gs[i] = (Number(data.outPort2.list[i].Grp_Ine)/100).toFixed(2);
        		c_m_s[i] = (Number(data.outPort2.list[i].Tol_Ine)/1000).toFixed(2);
        	}
//        	均班客流量走势分析数据组装
        	var c_j_month = [];//月流量走势分析
        	var c_j_k = [];//客流
        	var c_j_z = [];//座位
        	var c_j_b = [];//班次*100
        	var c_j_g = [];//团队
        	var c_j_gs = [];//团队收入/100
        	var c_j_s = [];//收入/1000
        	for(var i in data.evenPort2.list){
        		c_j_month[i] = data.evenPort2.list[i].label;
        		c_j_k[i] = data.evenPort2.list[i].cla_Per;
        		c_j_z[i] = data.evenPort2.list[i].cla_Set;
        		c_j_b[i] =data.evenPort2.list[i].lod_For;
        		c_j_g[i] = data.evenPort2.list[i].cla_Grp;
        		c_j_gs[i] = data.evenPort2.list[i].flt_Ser_Ine;
        		c_j_s[i] = data.evenPort2.list[i].idd_Dct;
        	}
//        	年班次前十排行数据组装
        	var c_n_d = [];
        	var c_n_d_a = [];
        	var c_n_d_a_m = [];
        	for(var i in data.class2.list){
        		c_n_d[i] = data.class2.list[i].count;
//        		c_n_d_a[i] = data.class2.list[i].dpt_AirPt_Cd +"-"+data.class2.list[i].arrv_Airpt_Cd;
        		c_n_d_a[i] = data.class2.list[i].dpt_AirPt_Cd +"-"+data.class2.list[i].arrv_Airpt_Cd;
        		c_n_d_a_m[c_n_d_a[i]] = data.class2.list[i].dpt_AirPt_Cd +"-"+data.class2.list[i].arrv_Airpt_Cd;
        		
        	}
//        	座公里收入前十排行数据组装
        	var c_s_z = [];
        	var c_s_b = [];
        	var c_s_d_a = [];
        	var c_s_d_a_m = [];
        	for(var i in data.set2.list){
        		c_s_b[i] = data.set2.list[i].set_Ktr_Ine;
        		c_s_z[i] = data.set2.list[i].count;
//        		c_s_d_a[i] = data.set2.list[i].dpt_AirPt_Cd + "-" +data.set2.list[i].arrv_Airpt_Cd;
        		c_s_d_a[i] = data.set2.list[i].dpt_AirPt_Cd +"-"+data.set2.list[i].arrv_Airpt_Cd;
        		c_s_d_a_m[c_s_d_a[i]] = data.set2.list[i].dpt_AirPt_Cd +"-"+data.set2.list[i].arrv_Airpt_Cd;
        	}
//        	旅客前十排行数据组装 ,各航线年客量占比
        	var c_l_z = [];
        	var c_l_d_a = [];
        	var c_l_d_a_m = [];
        	for(var i in data.amoun2.list){
        		c_l_z[i] = data.amoun2.list[i].value;
//        		c_l_d_a[i] = data.amoun2.list[i].dpt_AirPt_Cd + "-" +data.amoun2.list[i].arrv_Airpt_Cd;
        		c_l_d_a[i] = data.amoun2.list[i].dpt_AirPt_Cd +"-"+data.amoun2.list[i].arrv_Airpt_Cd;
        		c_l_d_a_m[c_l_d_a[i]] = data.amoun2.list[i].dpt_AirPt_Cd +"-"+data.amoun2.list[i].arrv_Airpt_Cd;
        	}
//        	出港均班前十排行数据组装
        	var c_j_z_2 = [];
        	var c_j_l_2 = [];
        	var c_j_t_2 = [];
        	var c_j_d_a_2 = [];
        	var c_j_d_a_2_m = [];
        	for(var i in data.guest2.list){
        		c_j_z_2[i] = data.guest2.list[i].count;
        		c_j_l_2[i] = data.guest2.list[i].set_Ktr_Ine;
        		c_j_t_2[i] = data.guest2.list[i].guestamount;
//        		c_j_d_a_2[i] = data.guest2.list[i].dpt_AirPt_Cd + "-" +data.guest2.list[i].arrv_Airpt_Cd;
        		c_j_d_a_2[i] = data.guest2.list[i].dpt_AirPt_Cd +"-"+data.guest2.list[i].arrv_Airpt_Cd;
        		c_j_d_a_2_m[c_j_d_a_2[i]] = data.guest2.list[i].dpt_AirPt_Cd +"-"+data.guest2.list[i].arrv_Airpt_Cd;
        	}
//        	各航司旅客前十,客量占比,数据组装
        	var c_h_z = [];
        	var c_h_d_a = [];
        	for(var i in data.cpy_amoun2.list){
        		c_h_z[i] = data.cpy_amoun2.list[i].value;
        		c_h_d_a[i] = data.cpy_amoun2.list[i].cpy_Nm;
        	}
//        	各航司年班次前十排行
        	var c_h_b_2 =[];
        	var c_h_b_d_a_2 = [];
        	for(var i in data.cpy_class2.list){
        		c_h_b_2[i] = data.cpy_class2.list[i].count;
        		c_h_b_d_a_2[i] = data.cpy_class2.list[i].cpy_Nm;
        		
        	}
            if(data){
	            ultimately={
	                enterT:{
	                    inTA:{
	                        id:"inTrafficAnalysis",
	                        name:"流量走势分析",
	                        time:month,
	                        data:[m_k,m_z,m_b,m_g,m_gs,m_s],
	                        calibration:[ "客流", "座位", "班次*100", "团队","团队收入/100","收入/1000"]
	                    },
	                    actsa:{
	                        id:"analysis",
	                        name:"均班客流量走势分析",
	                        time:j_month,
	                        data:[j_k,j_z,j_b,j_g,j_gs,j_s],
	                        calibration:[ "客流", "座位", "客座率", "团队","团队收入/100","收入/1000"]
	                    },
	                    averageA:{
	                        className:"班次",
	                        id:"averageA",
	                        name:"班次前十排行",
	                        data:n_d,
	                        xalse:n_d_a,
	                        keyval:n_d_a_m
	                    },
	                    kttr:{
	                        name:"座公里收入前十排行",
	                        subname:"",
	                        id:"kttr",
	                        classNmae:["座公里收入*100","每班收入/1000"],
	                        datazuo:s_z,
	                        databan:s_b,
	                        xalse:s_d_a,
	                        keyval:s_d_a_m
	                    },
	                    rp10: {
	                        id:"rp10",
	                        className:"客量",
	                        name:"旅客前十排行",
	                        data:l_z,
	                        xalse:l_d_a,
	                        keyval:l_d_a_m
	                    },
	                    tracpq10:{
	                        id:"tracpq10",
	                        name: "均班客量前十排行",
	                        classNmae:["每班旅客","每班座位","每班团队"],
	                        datal:j_l_2,
	                        dataz:j_z_2,
	                        datat:j_t_2,
	                        xalse:j_d_a_2,
	                        keyval:j_d_a_2_m
	                    },
	                    deguest10: {
	                        id:"de-guest10",
	                        className:"客量",
	                        name:"航司客量前十排行",
	                        data:h_z,
	                        xalse:h_d_a
	                    },
	                    deshift10: {
	                        id:"deshift10",
	                        className:"班次",
	                        name:"航司班次前十排行",
	                        data:h_b_2,
	                        xalse:h_b_d_a_2
	                    },
	                    routes:{
	                        name:"各航线客量占比",
	                        id:"routes",
	                        className:l_d_a,
	                        data:l_z,
	                        keyval:l_d_a_m
	                    },
	                    sDepartment:{
	                        name:"各航司客量占比",
	                        id:"sDepartment",
	                        className:h_d_a,
	                        data:h_z
	                    }
	                },
	                clearance:{
	                	inTA:{
	                        id:"inTrafficAnalysis",
	                        name:"流量走势分析",
	                        time:c_month,
	                        data:[c_m_k,c_m_z,c_m_b,c_m_g,c_m_gs,c_m_s],
	                        calibration:[ "客流", "座位", "班次*100", "团队","团队收入/100","收入/1000"]
	                    },
	                    actsa:{
	                        id:"analysis",
	                        name:"均班客流量走势分析",
	                        time:c_j_month,
	                        data:[c_j_k,c_j_z,c_j_b,c_j_g,c_j_gs,c_j_s],
	                        calibration:[ "客流", "座位", "客座率", "团队","团队收入/100","收入/1000"]
	                    },
	                    averageA:{
	                        className:"班次",
	                        id:"averageA",
	                        name:"班次前十排行",
	                        data:c_n_d,
	                        xalse:c_n_d_a,
	                        keyval:c_n_d_a_m
	                    },
	                    kttr:{
	                        name:"座公里收入前十排行",
	                        subname:"",
	                        id:"kttr",
	                        classNmae:["座公里收入*100","每班收入/1000"],
	                        datazuo:c_s_z,
	                        databan:c_s_b,
	                        xalse:c_s_d_a,
	                        keyval:c_s_d_a_m
	                    },
	                    rp10: {
	                        id:"rp10",
	                        className:"客量",
	                        name:"旅客前十排行",
	                        data:c_l_z,
	                        xalse:c_l_d_a,
	                        keyval:c_l_d_a_m
	                    },
	                    tracpq10:{
	                        id:"tracpq10",
	                        name: "均班客量前十排行",
	                        classNmae:["每班旅客","每班座位","每班团队"],
	                        datal:c_j_l_2,
	                        dataz:c_j_z_2,
	                        datat:c_j_t_2,
	                        xalse:c_j_d_a_2,
	                        keyval:c_j_d_a_2_m
	                    },
	                    deguest10: {
	                        id:"de-guest10",
	                        className:"客量",
	                        name:"航司客量前十排行",
	                        data:c_h_z,
	                        xalse:c_h_d_a
	                    },
	                    deshift10: {
	                        id:"deshift10",
	                        className:"班次",
	                        name:"航司班次前十排行",
	                        data:c_h_b_2,
	                        xalse:c_h_b_d_a_2
	                    },
	                    routes:{
	                        name:"各航线客量占比",
	                        id:"routes",
	                        className:c_l_d_a,
	                        data:c_l_z,
	                        keyval:c_l_d_a_m
	                    },
	                    sDepartment:{
	                        name:"各航司客量占比",
	                        id:"sDepartment",
	                        className:c_h_d_a,
	                        data:c_h_z
	                    }
	                }
	            };
	            if(bz1){
	        		$(".lin-historical-body-box").addClass("muhu");
	        		$(".reportErr").css("display","block");
	        	}else{
	        		$(".lin-historical-body-box").removeClass("muhu");
	        		$(".reportErr").css("display","none");
	        	}
	            box();
	            drawing();
	            $(window).resize(function(){
	                box();
	                if($(".set").next().length==1){
	                    drawing();
	                }else {
	                    out();
	                }
	                calculate();//默认执行一次
	            });
	            calculate();//默认执行一次
            }
//            loadWeather(airports[dpt_AirPt_Cd].icao);
//            loadWeather(parent.iataMap[dpt_AirPt_Cd].icao);
        },error : function() {//FIXME  请求数据后此函数内容应放在success里面 -暂时做无求情数据测试
        }
    });
	getTotalData();
}
/*进港方法*/
function drawing(){
    /**/
    movements(ultimately.enterT.inTA);
    movements(ultimately.enterT.actsa);
    /**/
    legend2(ultimately.enterT.averageA);
    legend2(ultimately.enterT.rp10);
    legend2(ultimately.enterT.deguest10);
    legend2(ultimately.enterT.deshift10);
    /**/
    legend3(ultimately.enterT.kttr);
    /**/
    legend4(ultimately.enterT.tracpq10);
    /**/
    legend5(ultimately.enterT.routes);
    legend5(ultimately.enterT.sDepartment);
    $("._export-table").eq(0).click();
}
/*出港方法*/
function out(){
    movements(ultimately.clearance.inTA);
    movements(ultimately.clearance.actsa);
    /**/
    legend2(ultimately.clearance.averageA);
    legend2(ultimately.clearance.rp10);
    legend2(ultimately.clearance.deguest10);
    legend2(ultimately.clearance.deshift10);
    /**/
    legend3(ultimately.clearance.kttr);
    /**/
    legend4(ultimately.clearance.tracpq10);
    /**/
    legend5(ultimately.clearance.routes);
    legend5(ultimately.clearance.sDepartment);
    $("._export-table").eq(0).click();
}

/******绘图函数*********/
/*单混合折-柱图*/
function movements(coll){
    var dom = document.getElementById(coll.id);
    var myChart = echarts.init(dom);
	var arr = coll.time.concat();
    if(coll.time[0].indexOf('月')>-1){	//月添加分隔符
    	for( var item in arr){
    		arr[item] = coll.time[item].substr(0,4)+'-'+coll.time[item].substr(4);
    	}
    }
    var option = {
        title: {
            text: coll.name,
            left:'10%',
            top:'5%',
            textStyle:{
                color:"white",
                fontWeight:"normal"
            }
        },
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            borderColor:'#d85430',
            borderWidth:1,
        },
        legend: {
            data: coll.calibration,
            align: 'left',
            right: "5%",
            top:"15%",
            textStyle:{
                color:"white"
            }
        },
        grid: {
            left: '4%',
            right: '10%',
            bottom: '20%',
            top:"25%",
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : arr,
                axisTick: {
                    alignWithLabel: true
                },
                splitLine: {
                    show:true,
                    lineStyle:{
                        color:"#1b2c4c"
                    }
                },
                axisLabel:{
                    show:true,
                    formatter: function(p){
                    	if(p.split('-').length>2){
                        	return Number(p.split('-')[1])+'.'+Number(p.split('-')[2]);	//日期格式修改                    		
                    	}else{
                    		return parseInt(p.substr(-3))+'月';
                    	}
                    },
    	           	textStyle:{
   	                 color:"white"
    	           	}
                },
                axisLine:{
                    lineStyle:{
                        color:"#24324a"
                    }
                }
            }
        ],
        yAxis : [
            {
                type : 'value',
                splitLine: {
                    show:false
                },
                axisLabel:{
                    show:true,
    	           	textStyle:{
      	                 color:"white"
       	           	}
                },
                axisLine:{
                    lineStyle:{
                        color:"#24324a"
                    }
                }
            }
        ],
        series : [
            {
                name:coll.calibration[0],
                smooth: true,
                type:'bar',
                showSymbol:false,
                itemStyle:{
                    normal:{
                        color:"#1f4e7f"
                    }
                },
                barWidth: '50%',
                data:coll.data[0]
            },
            {
                name:coll.calibration[1],
                type:'line',
                itemStyle:{
                    normal:{
                        color:"#84acda"
                    }
                },
                data:coll.data[1]
            },
            {
                name:coll.calibration[2],
                type:'line',
                itemStyle:{
                    normal:{
                        color:"#52b778"
                    }
                },
                symbol:"rect",
                data:coll.data[2]
            },
            {
                name:coll.calibration[3],
                type:'line',
                itemStyle:{
                    normal:{
                        color:"#af9060"
                    }
                },
                symbol:"triangle",
                data:coll.data[3]
            },
            {
                name:coll.calibration[4],
                type:'line',
                itemStyle:{
                    normal:{
                        color:"#8e672e"
                    }
                },
                symbol:"triangle",
                data:coll.data[4]
            },
            {
                name:coll.calibration[5],
                type:'line',
                itemStyle:{
                    normal:{
                        color:"#d35b4c"
                    }
                },
                symbol:"triangle",
                data:coll.data[5]
            }

        ]
    };
    myChart.setOption(option, true);
}
/*单柱状图*/
function legend2(coll){
    var dom = document.getElementById(coll.id);
    var myChart = echarts.init(dom);
    var option = {
        title: {
            text: coll.name,
            left:'10%',
            top:'5%',
            textStyle:{
                color:"white",
                fontWeight:"normal"
            }
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
            formatter:function(params){
                return typeof(coll.keyval)!='undefined'?(coll.keyval[params[0].name]+'<br/>'+params[0].seriesName+'：'+params[0].value):(params[0].seriesName+'：'+params[0].value);
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
            data: coll.xalse,
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
                rotate:45,
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
            data: [{name:coll.className,icon:'roundRect', textStyle: {color: 'white'}}],
            itemHeight :10,
            itemWidth:25,
            left:"80%",
            top:"10%"
        },
        series: [
            {
                name:coll.className,
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
                data:coll.data
            }
        ]
    };
    myChart.setOption(option, true);
}
/*双柱图*/
function legend3(coll){
    var dom = document.getElementById(coll.id);
    var myChart = echarts.init(dom);
    var option = {
        title: {
            text: coll.name,
            left:'10%',
            top:'5%',
            textStyle:{
                color:"white",
                fontWeight:"normal"
            },
//            subtext: '（不过站）'
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
            formatter:function(params){
            	if(params.length==2){
            		return coll.keyval[params[0].name]+'<br/>'+params[0].seriesName+'：'+params[0].value+'<br/>'+params[1].seriesName+'：'+params[1].value;
            	}else{
            		return coll.keyval[params[0].name]+'<br/>'+params[0].seriesName+'：'+params[0].value;
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
            boundaryGap: true,
            data: coll.xalse,
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
                rotate:45,
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
                name:coll.classNmae[1],
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
            data: [{name:coll.classNmae[0],icon:'roundRect', textStyle: {color: 'white'}},{name:coll.classNmae[1],icon:'roundRect',textStyle: {color: 'white'}}],
            itemHeight :10,
            itemWidth:25,
            left:"55%",
            top:"10%"
        },
        series: [
            {
                name:coll.classNmae[0],
                type:'bar',
                data:coll.datazuo,
                itemStyle:{
                    normal:{
                        color:'#7ebce9',
                        label : {show: false, position: 'top',textStyle: {color: '#fff'}}
                    }
                },
                barWidth:20
            },
            {
                name:coll.classNmae[1],
                type:'bar',
                data:coll.databan,
                itemStyle:{
                    normal:{
                        color:'#fdc671',
                        label : {show: false, position: 'top',textStyle: {color: '#fff'}}
                    }
                },
                barWidth:20
            }
        ]
    };
    myChart.setOption(option, true);
}
/*三柱图*/
function legend4(coll){
    var dom = document.getElementById(coll.id);
    var myChart = echarts.init(dom);
    var option = {
        title: {
            text: coll.name,
            left:'10%',
            top:'5%',
            textStyle:{
                color:"white",
                fontWeight:"normal"
            },
//            subtext: '（不过站）'
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
            formatter:function(params){
            	if(params.length==3){
            		return coll.keyval[params[0].name]+'<br/>'+params[0].seriesName+'：'+params[0].value+'<br/>'+params[1].seriesName+'：'+params[1].value+'<br/>'+params[2].seriesName+'：'+params[2].value;
            	}else if(params.length==2){
            		return coll.keyval[params[0].name]+'<br/>'+params[0].seriesName+'：'+params[0].value+'<br/>'+params[1].seriesName+'：'+params[1].value;
            	}else{
            		return coll.keyval[params[0].name]+'<br/>'+params[0].seriesName+'：'+params[0].value+'<br/>';
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
            boundaryGap: true,
            data: coll.xalse,
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
                rotate:45,
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
            data: [{name:coll.classNmae[0],icon:'roundRect', textStyle: {color: 'white'}},{name:coll.classNmae[1],icon:'roundRect',textStyle: {color: 'white'}},{name:coll.classNmae[2],icon:'roundRect',textStyle: {color: 'white'}}],
            itemHeight :10,
            itemWidth:25,
            left:"50%",
            top:"12%"
        },
        series: [
            {
                name:coll.classNmae[0],
                type:'bar',
                data:coll.datal,
                itemStyle:{
                    normal:{
                        color:'#7ebce9',
                        label : {show: false, position: 'top',textStyle: {color: '#fff'}}
                    }
                },
                barWidth:15
            },
            {
                name:coll.classNmae[1],
                type:'bar',
                data:coll.dataz,
                itemStyle:{
                    normal:{
                        color:'#fdc671',
                        label : {show: false, position: 'top',textStyle: {color: '#fff'}}
                    }
                },
                barWidth:15
            },
            {
                name:coll.classNmae[2],
                type:'bar',
                data:coll.datat,
                itemStyle:{
                    normal:{
                        color:'#52b778',
                        label : {show: false, position: 'top',textStyle: {color: '#fff'}}
                    }
                },
                barWidth:15
            }
        ]
    };
    myChart.setOption(option, true);
}
/*混合柱-饼图*/
function legend5(coll){
	var newdata = [];
	for(var i in coll.data){
		newdata[i] =  {value:coll.data[i], name:coll.className[i]};
	}
    var dom = document.getElementById(coll.id);
    var myChart = echarts.init(dom);
    var option = {
        title: {
            text: coll.name,
            left:'10%',
            top:'5%',
            textStyle:{
                color:"white",
                fontWeight:"normal"
            }
        },
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            borderColor:'#d85430',
            borderWidth:1
        },
        grid: {
            left: '70%',
            right: '4%',
            bottom: '10%',
            top:"10%",
            containLabel: true
        },
        calculable : true,
        legend: {
            data:coll.className,
            left:"10%",
            right:"70%",
            top:"25%",
            textStyle:{
                color:"white"
            }
        },
        xAxis : [
            {
                show:false,
                type : 'value',
                position: 'right'
            }
        ],
        yAxis : [
            {
                show:true,
                type : 'category',
                splitLine : {show : false},

                axisLine:{
                    show:false,
                    lineStyle:{
                        color:"#fff"
                    }
                },
                axisTick:{
                    show:false
                },
                data : coll.className
            }
        ],
        series : [
            {
                name:'排行',
                type:'bar',
                tooltip : {
                    trigger: 'item',
//                    formatter: '{b} : {c}',
                    formatter:function(params){
                    	return typeof(params.percent)=='undefined'?(typeof(coll.keyval)!='undefined'?(coll.keyval[params.name]+'：'+params.value+'人'):(params.name+'：'+params.value+'人')):(typeof(coll.keyval)!='undefined'?(coll.keyval[params.name]+'：'+params.percent+'%（'+params.value+'人）'):(params.name+'：'+params.value+'人'));
                    },
                    borderColor:'#d85430',
                    borderWidth:1
                },
                barWidth: "18",
                data:coll.data,
                itemStyle:{
                    normal:{
                        color:"#83acda"
                    }
                }

            },
            {
                name:'占比',
                type:'pie',
                tooltip : {
                    trigger: 'item',
//                    formatter: '{b} : {c} ({d}%)',
                    formatter:function(params){
                    	return typeof(params.percent)=='undefined'?(typeof(coll.keyval)!='undefined'?(coll.keyval[params.name]+'：'+params.value+'人'):(params.name+'：'+params.value+'人')):(typeof(coll.keyval)!='undefined'?(coll.keyval[params.name]+'：'+params.percent+'%（'+params.value+'人）'):(params.name+'：'+params.percent+'%（'+params.value+'人）'));
                    },
                    borderColor:'#d85430',
                    borderWidth:1
                },
                center:["45%","50%"],
                radius : [0, "50%"],
                label: {
                    normal: {
                        show: false
                    }
                },
                itemStyle :{
                    normal : {
                        labelLine : {
                            length : 20
                        }
                    }
                },
                data: newdata
            }
        ]
    };
    myChart.setOption(option, true);
}
function lastyears(){
	var years = $('#years').text();
	years = parseInt(years.split('-')[0]);
	var startYear = years-6;
	var endYear = years-1;
	$('#years').text(startYear+'-'+endYear);
	var html = '';
	for(var i=startYear;i<=endYear;i++){
		if(i==parseInt(currentCheckedYear)){
			html+='<div class="year-btn list">'+i+'</div>';
		}else{
			html+='<div class="year-btn">'+i+'</div>';
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
	for(var i=startYear;i<=endYear;i++){
		if(i==parseInt(currentCheckedYear)){
			html+='<div class="year-btn list">'+i+'</div>';
		}else{
			html+='<div class="year-btn">'+i+'</div>';
		}
	}
	$('.sr-box-body-date-body').html(html);
}

//日历绑定事件
$(function(){
	$(".sr-box-body-date-body").on("click",'.hasData',function(val){
		$(".list").attr("class","year-btn hasData");
		$(val.target).attr("class","year-btn hasData list");
		currentCheckedYear = $(this).text();
		send();
	});
});

//--------------------------------获取最近有数据的日期
function getYears(){
	$.ajax({
		url:'getYearList',
		type:'get',
		async:false,
		success:function(data){
			if(data!=null&&data.opResult=='0'){
				rangerTime[1] = data.nowDay[0];
				rangerTime[0] = SD_getPreDate(rangerTime[1], 90);
				changeDate(rangerTime);
				
			}else{
				var nowDay = new Date();
				rangerTime[1] = nowDay.getFullYear() +'-'+ (nowDay.getMonth()+1) + '-' + nowDay.getDate();
				rangerTime[0] = SD_getPreDate(rangerTime[1], 31);
				changeDate(rangerTime);
			}
		}
	});
}
$("#spae_sector").on("click",function(){
	
	if($(this).attr("tag")=="graph"){
		$(this).attr("tag","table");
		$(".spae_sector_text").html("&#xe6bc;");
		$("#spae_sector_cont").removeClass("spae_sector_cont_c");
		$("#scroll-bar").css("z-index","-1");
		$(".lin-historical-body-box").css("opacity","0");
	}else{
		$(this).attr("tag","graph");
		$(".spae_sector_text").html("&#xe750;");
		$("#spae_sector_cont").addClass("spae_sector_cont_c");	
		$("#scroll-bar").css("z-index","20");
		$(".lin-historical-body-box").css("opacity","1");
	}
});
$("._export-table").on("click",function(){
	$(this).addClass("_set-gB").siblings().removeClass("_set-gB");
	
});

var t_data0;
var t_data1;
function segment(){
	 t_data0={
	        common:{
	            "日期":[], //
	            "天数":[], //
	            "班次/月":[], //
	            "班次/日":[], //
	            "每班座位（个）":[], //
	            "每班团队（人）":[],
                "每班旅客（人）":[],
	            "客座率（%）":[], //
                "团队营收（元）":[], //
	            "每班营收（元）":[]
	        },
	        special:''
	};

	 if(monthDayFlag == 'day'){
         t_data1={
             common:{
                 "日期":[], //
                 "班次/日":[], //
                 "座位/日":[], //
                 "团队客量/日":[],
                 "散团总客量/日":[],
                 "团队营收/日":[], //
                 "散团总营收/日":[], //
                 "平均客座率（%）":[], //
             },
             special:''
         }
     }else{
         t_data1={
             common:{
                 "日期":[], //
                 "班次/月":[], //
                 "座位/月":[], //
                 "团队客量/月":[],
                 "散团总客量/月":[],
                 "团队营收/月":[], //
                 "散团总营收/月":[], //
                 "平均客座率（%）":[], //
             },
             special:''
         };
     }
	setTimeout(function(){
		var _tltle_data={
				jbys:0,
				jbkl:0,
				ljbc:0,
				jbzw:0,
		};
		if($(".Into").hasClass("set")){
			if($("._export-table").eq(1).hasClass("_set-gB")){
				for(var i=0;i<table_data.evenPort1.list.length;i++){
                    // 修改日期格式    by：long   2017-9-15    ·············start
                    var odate = table_data.evenPort1.list[i].key;
                    var das = monthDayFlag  === 'month'? new Date(odate.substr(0,4),parseInt(odate.substr(4,2)),0).getDate() : 1;
                    // if(monthDayFlag  === 'month'){
                    //     das = new Date(odate.substr(0,4),parseInt(odate.substr(4,2)),0).getDate()
                    // }else{
                    //     das = 1;
                    // }
                    // 修改日期格式    by：long   2017-9-15    ·············end
					t_data0.common["日期"].push(table_data.evenPort1.list[i].key.split("")[0]=="0"?table_data.evenPort1.list[i].key.split("")[1]:table_data.evenPort1.list[i].key);
					t_data0.common["天数"].push(das);
                    
                    t_data0.common["班次/月"].push(monthDayFlag  === 'month' ? (Number(table_data.outPort1.list[i].Cla_Nbr).toFixed(2)):'-')
                    t_data0.common["班次/日"].push(monthDayFlag  === 'day' ?((Number(table_data.outPort1.list[i].Cla_Nbr)/das).toFixed(2)):'-');
                    
					t_data0.common["每班座位（个）"].push(Number(table_data.evenPort1.list[i].cla_Set).toFixed(2));
					t_data0.common["每班旅客（人）"].push(Number(table_data.evenPort1.list[i].cla_Per).toFixed(2));
					t_data0.common["每班团队（人）"].push(Number(table_data.evenPort1.list[i].cla_Grp).toFixed(2));
					t_data0.common["团队营收（元）"].push((Number(table_data.evenPort1.list[i].flt_Ser_Ine)*100).toFixed(2));
					t_data0.common["客座率（%）"].push(Number(table_data.evenPort1.list[i].lod_For).toFixed(2));
					t_data0.common["每班营收（元）"].push((Number(table_data.evenPort1.list[i].idd_Dct)*1000).toFixed(2));
					_tltle_data.jbys+=Number(table_data.evenPort1.list[i].idd_Dct)*1000;
					_tltle_data.jbkl+=Number(table_data.evenPort1.list[i].cla_Per);
				}
				$("#ta").html(spae_tablex(t_data0));
				_tltle_data.jbys=Number(_tltle_data.jbys/table_data.evenPort1.list.length);
				_tltle_data.jbkl=Number(_tltle_data.jbkl/table_data.evenPort1.list.length);
				$("#_space_titles").html("<span>"+ rangerTime[0] +"至"+ rangerTime[1] +"</span>&nbsp;&nbsp;"+$(".set").text()+"&nbsp;&nbsp;")
				$(".lin_historical_er").html("<li>均班客流："+_tltle_data.jbkl.toFixed(2)+"</li><li>均班营收："+_tltle_data.jbys.toFixed(0)+"</li>");
			}else{
				for(var i=0;i<table_data.outPort1.list.length;i++){
					t_data1.common["日期"].push(table_data.evenPort1.list[i].key.split("")[0]=="0"?table_data.evenPort1.list[i].key.split("")[1]:table_data.evenPort1.list[i].key);
					if(monthDayFlag == 'day'){
                        t_data1.common["班次/日"].push(Number(table_data.outPort1.list[i].Cla_Nbr).toFixed(0));
                        t_data1.common["座位/日"].push(Number(table_data.outPort1.list[i].Tal_Nbr_Set).toFixed(0));
                        t_data1.common["散团总客量/日"].push(Number(table_data.outPort1.list[i].idd_moh).toFixed(0));
                        t_data1.common["团队客量/日"].push(Number(table_data.outPort1.list[i].Grp_moh).toFixed(0));
                        t_data1.common["团队营收/日"].push(Number(table_data.outPort1.list[i].Grp_Ine).toFixed(0));
                        t_data1.common["散团总营收/日"].push(Number(table_data.outPort1.list[i].Tol_Ine).toFixed(0));
                    }else{
                        t_data1.common["班次/月"].push(Number(table_data.outPort1.list[i].Cla_Nbr).toFixed(0));
                        t_data1.common["座位/月"].push(Number(table_data.outPort1.list[i].Tal_Nbr_Set).toFixed(0));
                        t_data1.common["散团总客量/月"].push(Number(table_data.outPort1.list[i].idd_moh).toFixed(0));
                        t_data1.common["团队客量/月"].push(Number(table_data.outPort1.list[i].Grp_moh).toFixed(0));
                        t_data1.common["团队营收/月"].push(Number(table_data.outPort1.list[i].Grp_Ine).toFixed(0));
                        t_data1.common["散团总营收/月"].push(Number(table_data.outPort1.list[i].Tol_Ine).toFixed(0));
                    }
                    t_data1.common["平均客座率（%）"].push(((Number(table_data.outPort1.list[i].idd_moh)/Number(table_data.outPort1.list[i].Tal_Nbr_Set))*100).toFixed(2));
					_tltle_data.jbys+=Number(table_data.outPort1.list[i].Tol_Ine);
					_tltle_data.jbkl+=Number(table_data.outPort1.list[i].idd_moh);
					_tltle_data.ljbc+=Number(table_data.outPort1.list[i].Cla_Nbr);
                    _tltle_data.jbzw+=Number(table_data.outPort1.list[i].Tal_Nbr_Set);
				}
				$("#ta").html(spae_tablex(t_data1));
				_tltle_data.jbys=Number(_tltle_data.jbys);
				_tltle_data.jbkl=Number(_tltle_data.jbkl);
				_tltle_data.ljbc=Number(_tltle_data.ljbc);
				$("#_space_titles").html("<span>"+ rangerTime[0] +"至"+ rangerTime[1] +"</span>&nbsp;&nbsp;"+$(".set").text()+"&nbsp;&nbsp;")
				$(".lin_historical_er").html("<li>累计客流："+_tltle_data.jbkl.toFixed(0)+"</li><li>累计营收："+_tltle_data.jbys.toFixed(0)+"</li><li>累计班次："+_tltle_data.ljbc.toFixed(0)+"</li><li>平均客座率："+((_tltle_data.jbkl/_tltle_data.jbzw)*100).toFixed(2)+"</li>");
			}
		}else{
            if($("._export-table").eq(1).hasClass("_set-gB")){
                for(var i=0;i<table_data.evenPort2.list.length;i++){
                    var odate = table_data.evenPort1.list[i].key;
                    var das = monthDayFlag === 'month' ? new Date(odate.substr(0,4),parseInt(odate.substr(4,2)),0).getDate() : 1;
                    // if(monthDayFlag === 'month'){
                    //     das = new Date(odate.substr(0,4),parseInt(odate.substr(4,2)),0).getDate()
                    // }else{
                    //     das=1;
                    //     //das = (new Date(odate.substr(0,4)+'/'+odate.substr(4,2) +'/' + odate.substr(6,2)).getDate());
                    // }
                    t_data0.common["日期"].push(table_data.evenPort2.list[i].key.split("")[0]=="0"?table_data.evenPort2.list[i].key.split("")[1]:table_data.evenPort2.list[i].key);
                    t_data0.common["天数"].push(das);


                    t_data0.common["班次/月"].push(monthDayFlag  === 'month' ? (Number(table_data.outPort1.list[i].Cla_Nbr).toFixed(2)):'-')
                    t_data0.common["班次/日"].push(monthDayFlag  === 'day' ?((Number(table_data.outPort1.list[i].Cla_Nbr)/das).toFixed(2)):'-');



                    t_data0.common["每班座位（个）"].push(Number(table_data.evenPort2.list[i].cla_Set).toFixed(2));
                    t_data0.common["每班旅客（人）"].push(Number(table_data.evenPort2.list[i].cla_Per).toFixed(2));
                    t_data0.common["每班团队（人）"].push(Number(table_data.evenPort2.list[i].cla_Grp).toFixed(2));
                    t_data0.common["团队营收（元）"].push((Number(table_data.evenPort2.list[i].flt_Ser_Ine)*100).toFixed(2));
                    t_data0.common["客座率（%）"].push(Number(table_data.evenPort2.list[i].lod_For).toFixed(2));
                    t_data0.common["每班营收（元）"].push((Number(table_data.evenPort2.list[i].idd_Dct)*1000).toFixed(2));
                    _tltle_data.jbys+=Number(table_data.evenPort2.list[i].idd_Dct)*1000;
                    _tltle_data.jbkl+=Number(table_data.evenPort2.list[i].cla_Per);
                }
                $("#ta").html(spae_tablex(t_data0));
                _tltle_data.jbys=Number(_tltle_data.jbys/table_data.evenPort2.list.length);
                _tltle_data.jbkl=Number(_tltle_data.jbkl/table_data.evenPort2.list.length);
                $("#_space_titles").html("<span>"+ rangerTime[0] +"至"+ rangerTime[1] +"</span>&nbsp;&nbsp;"+$(".set").text()+"&nbsp;&nbsp;")
                $(".lin_historical_er").html("<li>均班客流："+_tltle_data.jbkl.toFixed(2)+"</li><li>均班营收："+_tltle_data.jbys.toFixed(0)+"</li>");
            }else{
                for(var i=0;i<table_data.outPort2.list.length;i++){
                    if(monthDayFlag == 'day'){
                        t_data1.common["班次/日"].push(Number(table_data.outPort2.list[i].Cla_Nbr).toFixed(0));
                        t_data1.common["座位/日"].push(Number(table_data.outPort2.list[i].Tal_Nbr_Set).toFixed(0));
                        t_data1.common["散团总客量/日"].push(Number(table_data.outPort2.list[i].idd_moh).toFixed(0));
                        t_data1.common["团队客量/日"].push(Number(table_data.outPort2.list[i].Grp_moh).toFixed(0));
                        t_data1.common["团队营收/日"].push(Number(table_data.outPort2.list[i].Grp_Ine).toFixed(0));
                        t_data1.common["散团总营收/日"].push(Number(table_data.outPort2.list[i].Tol_Ine).toFixed(0));
                    }else{
                        t_data1.common["班次/月"].push(Number(table_data.outPort2.list[i].Cla_Nbr).toFixed(0));
                        t_data1.common["座位/月"].push(Number(table_data.outPort2.list[i].Tal_Nbr_Set).toFixed(0));
                        t_data1.common["散团总客量/月"].push(Number(table_data.outPort2.list[i].idd_moh).toFixed(0));
                        t_data1.common["团队客量/月"].push(Number(table_data.outPort2.list[i].Grp_moh).toFixed(0));
                        t_data1.common["团队营收/月"].push(Number(table_data.outPort2.list[i].Grp_Ine).toFixed(0));
                        t_data1.common["散团总营收/月"].push(Number(table_data.outPort2.list[i].Tol_Ine).toFixed(0));
                    }
                    t_data1.common["日期"].push(table_data.evenPort2.list[i].key.split("")[0]=="0"?table_data.evenPort2.list[i].key.split("")[1]:table_data.evenPort2.list[i].key);
                    t_data1.common["平均客座率（%）"].push(((Number(table_data.outPort2.list[i].idd_moh)/Number(table_data.outPort2.list[i].Tal_Nbr_Set))*100).toFixed(2));
                    _tltle_data.jbys+=Number(table_data.outPort2.list[i].Tol_Ine);
                    _tltle_data.jbkl+=Number(table_data.outPort2.list[i].idd_moh);
                    _tltle_data.ljbc+=Number(table_data.outPort2.list[i].Cla_Nbr);
                    _tltle_data.jbzw+=Number(table_data.outPort2.list[i].Tal_Nbr_Set);
                }
                $("#ta").html(spae_tablex(t_data1));
                _tltle_data.jbys=Number(_tltle_data.jbys);
                _tltle_data.jbkl=Number(_tltle_data.jbkl);
                _tltle_data.ljbc=Number(_tltle_data.ljbc);
                $("#_space_titles").html("<span>"+ rangerTime[0] +"至"+ rangerTime[1] +"</span>&nbsp;&nbsp;"+$(".set").text()+"&nbsp;&nbsp;")
                $(".lin_historical_er").html("<li>累计客流："+_tltle_data.jbkl.toFixed(0)+"</li><li>累计营收："+_tltle_data.jbys.toFixed(0)+"</li><li>累计班次："+_tltle_data.ljbc.toFixed(0)+"</li><li>平均客座率："+((_tltle_data.jbkl/_tltle_data.jbzw)*100).toFixed(2)+"</li>");

            }
		}		
	},50)	
}
function bubbleSort(array,type) {
    var i = 0,
    len = array.length,
    j, d;
    if(type==1){
    	 for (; i < len; i++) {
        	 for (j = 0; j < len; j++) {
        	        if (parseFloat(array[i]) < parseFloat(array[j])) {
        	            d =parseFloat(array[j]);
        	            array[j] = parseFloat(array[i]);
        	            array[i] = d;
        	            if($("._export-table").eq(1).hasClass("_set-gB")){
        	            	 for(var key in t_data0.common){
              	            	d=parseFloat(t_data0.common[key][j]);
              	            	t_data0.common[key][j] = parseFloat(t_data0.common[key][i])!==parseFloat(t_data0.common[key][i])?"-":parseFloat(t_data0.common[key][i]);
              	            	t_data0.common[key][i] = d;
              	            }
        	            }else{
        	            	
        	            	 for(var key in t_data1.common){
              	            	d=parseFloat(t_data1.common[key][j]);
              	            	t_data1.common[key][j] = parseFloat(t_data1.common[key][i]);
              	            	t_data1.common[key][i] = d;
              	            }
        	            }  
        	        }
        	  }
        }
    }else{
    	 for (; i < len; i++) {
        	 for (j = 0; j <len; j++) {
        	        if (parseFloat(array[i]) > parseFloat(array[j])) {
        	            d =parseFloat(array[j]);
        	            array[j] = parseFloat(array[i]);
        	            array[i] = d;
        	            if($("._export-table").eq(1).hasClass("_set-gB")){
        	            	 for(var key in t_data0.common){
	            	            	d=parseFloat(t_data0.common[key][j]);
	            	            	t_data0.common[key][j] = parseFloat(t_data0.common[key][i])!==parseFloat(t_data0.common[key][i])?"-":parseFloat(t_data0.common[key][i]);
	            	            	t_data0.common[key][i] = d;
	            	            }
	       	            }else{
	       	            	 for(var key in t_data1.common){
	            	            	d=parseFloat(t_data1.common[key][j]);
	            	            	t_data1.common[key][j] = parseFloat(t_data1.common[key][i]);
	            	            	t_data1.common[key][i] = d;
	            	          }
	       	            }  
        	        }
        	  }
        }
    }
      if($("._export-table").eq(1).hasClass("_set-gB")){
    	  $("#ta").html(spae_tablex(t_data0));
      }else{
    	  $("#ta").html(spae_tablex(t_data1));
      }
      
    
}
function table_px(ot,type){
	if($(ot).children('._px_span').hasClass("_px_set0")){
		//倒叙
		var name=$(ot).attr("line");
		$("._px_span").removeClass("_px_set1");
		$("._px_span").removeClass("_px_set0");
		if($("._export-table").eq(1).hasClass("_set-gB")){
			bubbleSort(JSON.parse(JSON.stringify(t_data0.common[$(ot).attr("line")])),0)
		}else{
			bubbleSort(JSON.parse(JSON.stringify(t_data1.common[$(ot).attr("line")])),0)
		}	
		$("[line='"+name+"']").eq(1).addClass("_px_set1");
	}else{
		//正序
		var name=$(ot).attr("line");
		$("._px_span").removeClass("_px_set1");
		$("._px_span").removeClass("_px_set0");
		if($("._export-table").eq(1).hasClass("_set-gB")){
			bubbleSort(JSON.parse(JSON.stringify(t_data0.common[$(ot).attr("line")])),1)
		}else{
			bubbleSort(JSON.parse(JSON.stringify(t_data1.common[$(ot).attr("line")])),1)
		}
		$("[line='"+name+"']").eq(1).addClass("_px_set0");
	}	
}





//-----------------------------------------------------------------龙洪
//-----------------------------------------------------------------汇总页面
//-----------------------------------------------------------------2017-7

function getTotalData() {
    var ioca = dpt_AirPt_Cd,
        exData = $('#exceptionData').is(':checked') ? 'on' : 'no',
        exFlight = $('#exceptionFlyData').is(':checked') ? 'on' : 'no',
        cpy_Nm = $('#cpy_Nm').val();
    $.ajax({
            url: '/airport_fltNbr_flydata',
            type: 'GET',
            data: {
                dpt_AirPt_Cd: ioca,
                startTime: rangerTime[0],
                endTime: rangerTime[1],
                cpy_Nm: cpy_Nm,
                isIncludeExceptionData: exData,
                isIncludeExceptionHuangduan: exFlight,
                monthOrDay: monthDayFlag
            },
            dataType: 'json',
        })
        .done(function (data) {
            $("#TM-sloading").hide('normal');
            if(data.list.length > 0) {
                drawTable(data);
                $('.abnormalData_prompt').css('top', '1%');
                create_abnormal(data.exceptionFlag);
            } else {
                $(".exportExl").attr("onclick", "javascript: void(null)");
            }
            $('#timeRange').text(rangerTime.join('-'));
            $('#portName').text(airports[parent.cityIata].aptChaNam);
        })
        .fail(function () {
            console.log("error");
        })
}

//做成表格显示
function drawTable(tdata){
	$('#totalTableBody').empty();
	var otbody = $('#totalTableBody');
	var ohtml = '';
	var data = tdata.list;
	var tbc= 0,ttdkl= 0,tstkl= 0,tkzl= 0,ttdsr= 0,tzsr= 0,tzk= 0,tzgl= 0, counter= 0; 
	for(var key in data){//--------------------外层循环一组航班号
		var a = data[key];
		var i = Object.keys(a)[0], el = a[i];
		var nameList= i.split(',');
		if(el.length>1){	//两个以上有汇总
			for(var j = 0; j < el.length-1; j++){//--------------------内层循环一组航线
				var val = el[j][Object.keys(el[j])[0]];
				var valname = Object.keys(el[j])[0];
				if(Number(val.stzsr)>0){
					if(j==0){
						ohtml += '<tr><td rowspan='+ el.length +'>'+ nameList[1] +'</td><td rowspan='+ el.length +'>'+ formatToCood(nameList[0]) +'</td><td>'+ formatToCood(valname) +'</td><td>'+ val.bc +'</td><td>'+ val.tdkl +'</td><td>'+ val.stzkl +'</td><td>'+ val.kzl +'</td><td>'+ val.tdsr +'</td><td>'+ val.stzsr +'</td><td>'+ val.pjzk +'</td><td>'+ val.zglsr +'</td></tr>';

					}else{
						ohtml += '<tr><td>'+ formatToCood(valname) +'</td><td>'+ val.bc +'</td><td>'+ val.tdkl +'</td><td>'+ val.stzkl +'</td><td>'+ val.kzl +'</td><td>'+ val.tdsr +'</td><td>'+ val.stzsr +'</td><td>'+ val.pjzk +'</td><td>'+ val.zglsr +'</td></tr>';
					}
				}else{
					if(j==0){
						ohtml += '<tr><td rowspan='+ el.length +'>'+ nameList[1] +'</td><td rowspan='+ el.length +'>'+ formatToCood(nameList[0]) +'</td><td>'+ formatToCood(valname) +'</td><td colspan="8">暂无数据</td></tr>';

					}else{
						ohtml += '<tr><td>'+ formatToCood(valname) +'</td><td colspan="8">暂无数据</td></tr>';
					}					
				}
			}
			//航班合计------取最后一个对象
			var totalval = el[el.length - 1][Object.keys(el[el.length - 1])[0]];	
			tbc += Number(totalval.bc), ttdkl += Number(totalval.tdkl), tstkl += Number(totalval.stzkl), tkzl += Number(totalval.kzl), ttdsr += Number(totalval.tdsr), tzsr += Number(totalval.stzsr), tzk += Number(totalval.pjzk), tzgl += Number(totalval.zglsr);
			counter++;		
			ohtml += '<tr class="sumtr"><td>合计</td><td>'+ totalval.bc +'</td><td>'+ totalval.tdkl +'</td><td>'+ totalval.stzkl +'</td><td>'+ totalval.kzl +'</td><td>'+ totalval.tdsr +'</td><td>'+ totalval.stzsr +'</td><td>'+ totalval.pjzk +'</td><td>'+ totalval.zglsr +'</td></tr>';
		}else{
			var val = el[0][Object.keys(el[0])[0]];
			var valname = Object.keys(el[0])[0];
			ohtml += '<tr><td>'+ nameList[1] +'</td><td>'+ formatToCood(nameList[0]) +'</td><td>'+ formatToCood(valname) +'</td><td>'+ val.bc +'</td><td>'+ val.tdkl +'</td><td>'+ val.stzkl +'</td><td>'+ val.kzl +'</td><td>'+ val.tdsr +'</td><td>'+ val.stzsr +'</td><td>'+ val.pjzk +'</td><td>'+ val.zglsr +'</td></tr>';
			tbc += Number(val.bc), ttdkl += Number(val.tdkl), tstkl += Number(val.stzkl), tkzl += Number(val.kzl), ttdsr += Number(val.tdsr), tzsr += Number(val.stzsr), tzk += Number(val.pjzk), tzgl += Number(val.zglsr);
			counter++;			
		}
	}
	//总计行-------前端计算
	//for formatter value    changed by longhong.2017-8-15
	//ohtml = '<tr><td>总计</td><td>-</td><td>-</td><td>'+ tbc.toFixed(2) +'</td><td>'+ ttdkl.toFixed(2) +'</td><td>'+ tstkl.toFixed(2) +'</td><td>'+ (tkzl/counter).toFixed(2) +'</td><td>'+ ttdsr.toFixed(2) +'</td><td>'+ tzsr.toFixed(2) +'</td><td>'+ (tzk/counter).toFixed(2) +'</td><td>'+ (tzgl/counter).toFixed(2) +'</td></tr>' + ohtml;
	ohtml = '<tr><td>总计</td><td>-</td><td>-</td><td>'+ (!!tbc?tbc.toFixed(1):'-') +'</td><td>'+ (!!ttdkl?ttdkl.toFixed(2):'-') +'</td><td>'+ (!!tstkl?tstkl.toFixed(2):'-') +'</td><td>'+ (!!tkzl?(tkzl/counter).toFixed(2):'-') +'</td><td>'+ (!!ttdsr?ttdsr.toFixed(2):'-') +'</td><td>'+ (!!tzsr?tzsr.toFixed(2):'-') +'</td><td>'+ (!!tzk?(tzk/counter).toFixed(2):'-') +'</td><td>'+ (!!tzgl?(tzgl/counter).toFixed(2):'-') +'</td></tr>' + ohtml;
	$(otbody).html(ohtml);
}
	
function daysBetween(sDate1,sDate2){	//判断两个日期相差的天数
	//Date.parse() 解析一个日期时间字符串，并返回1970/1/1 午夜距离该日期时间的毫秒数
	var time1 = Date.parse(new Date(sDate1));
	var time2 = Date.parse(new Date(sDate2));
	var nDays = Math.abs(parseInt((time2 - time1)/1000/3600/24));
	return nDays;
};

//CANWDSLHW => ["广州", "十堰", "兰州"]
var formatToCood = function (iataList){  
	iataList = iataList.replace(/-/g,'');
    if(iataList){
        var newlist=[];
        for(let i = 0 ; i < iataList.length; i+=3){
            var a= parent.airportMap[iataList.substr(i,3)]?parent.airportMap[iataList.substr(i,3)].ctyChaNam:iataList.substr(i,3);
            newlist.push(a);
        }
        return newlist.join('-');
    }
    else{
        return "--";
    }
}


//滚动监听    
$('#newTotal').on('scroll',function(){
    var oWidth=$("#totalTable thead").css("width");
    if($("#totalTable").offset().top<70){
        $(".back-to-top").show();
        $(".copyTableWrap").show();
        $("#copyTable").css("width",oWidth);
    }
    else{
        $(".back-to-top").hide();
        $(".copyTableWrap").hide();
    }
});

//回到顶部按钮
$(".back-to-top").click(function() {
    $("#newTotal").animate({scrollTop:0}, 'fast');
});

//-------------------------------------- 获取某个日期之前的某天
var SD_getPreDate = function (currDate, num) {
    num = Math.floor(num);
    var myDate = new Date(currDate);
    var lw = new Date(myDate - 1000 * 60 * 60 * 24 * num); //num表示天数
    var lastY = lw.getFullYear();
    var lastM = lw.getMonth()+1;
    var lastD = lw.getDate();
    var startdate=lastY+"-"+(lastM<10 ? "0" + lastM : lastM)+"-"+(lastD<10 ? "0"+ lastD : lastD);
    return startdate;
}


//--------------------异常数据、航班提示
var create_abnormal = function(flag){
	if(flag){
		if(status == 1) return;//没有异常
		$(".abnormalData_prompt").show();
		if(flag == '2') $(".abnormalData_prompt").html("<span>&#xe64a;</span> 当前结果包含异常数据");//只包含异常数据
		if(flag == '3') $(".abnormalData_prompt").html("<span>&#xe64a;</span> 当前结果包含异常航班");//只包含异常航班
		if(flag == '4') $(".abnormalData_prompt").html("<span>&#xe64a;</span> 当前结果包含异常数据和异常航班");//both
		setTimeout(function(){
			$(".abnormalData_prompt").empty();
			$(".abnormalData_prompt").hide();
		},3000)		
	}else{
		console.log('error: line 1908');
		return;
	}
}


function getDateToLastDay(d){
    var fd = new Date(d),
        m = fd.getMonth()+1,
        y = fd.getFullYear(),
        nd = new Date(y,m,0),
        ds = nd.getDate();
        m = m<10? '0'+m: m;
    return y+'-'+m + '-' + ds;
}