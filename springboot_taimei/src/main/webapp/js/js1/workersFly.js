function send(){
	removeDiv();
	var abzs = $('#abflowcharts').attr('class');
	var bazs = $('#baflowcharts').attr('class');
	var abgf = $('#abtotalcharts').attr('class');
	var bagf = $('#batotalcharts').attr('class');
	var abbagf = $('#abbatotalcharts').attr('class');
	if(abzs=="tab-pane fade active in"){
		abflowAsiay();
	}else if(bazs=="tab-pane fade active in"){
		baflowAsiay();
	}else if(abgf=="tab-pane fade active in"){
		abtotalflyAsiay();
	}else if(bagf=="tab-pane fade active in"){
		batotalflyAsiay();
	}else if(abbagf=="tab-pane fade active in"){
		abbatotalflyAsiay();
	}
};
function abflowAsiay(){
	removeDiv();
	$('.in').addClass('psc-sr-body');
	$('.in').removeClass('tab-pane fade active in');
	$('#abflowcharts').attr('class','tab-pane fade active in').css('display','block');
	$('.psc-sr-icon ul li div').css('border-bottom','none');
	$(".abzs").css('border-bottom','2px solid #69caff');
	$('.psc-sr-body').css('display','none');
	var custormersearchForm;
	custormersearchForm = $("#DOW-searchForm");
	var searchJson = custormersearchForm.searchJson();
	advancedQuery(searchJson,"abflowcharts");
}
function baflowAsiay(){
	removeDiv();
	$('.in').addClass('psc-sr-body');
	$('.in').removeClass('tab-pane fade active in');
	$('#baflowcharts').attr('class','tab-pane fade active in').css('display','block');
	$('.psc-sr-icon ul li div').css('border-bottom','none');
	$(".bazs").css('border-bottom','2px solid #69caff');
	$('.psc-sr-body').css('display','none');
	var custormersearchForm;
	custormersearchForm = $("#DOW-searchForm");
	var searchJson = custormersearchForm.searchJson();
	var dpt_AirPt_Cd = searchJson.dpt_AirPt_Cd;
	var arrv_Airpt_Cd = searchJson.Arrv_Airpt_Cd;
	searchJson.dpt_AirPt_Cd = arrv_Airpt_Cd;
	searchJson.Arrv_Airpt_Cd = dpt_AirPt_Cd;
	advancedQuery(searchJson,"baflowcharts");
}
function advancedQuery(searchJson,id){
	var width = $(document.body).width()/2+"px";
	var charts = $("#"+id);
	var dataAll = 0;
	if(""!=searchJson.startDate&&""!=searchJson.endDate&&""!=searchJson.dpt_AirPt_Cd&&""!=searchJson.Arrv_Airpt_Cd){
		$.ajax({
			beforeSend: function(){
				$('#myTabContent').hide();
				$("#wate").attr("style","display: block;");
	        },
	        complete:function(){
	        	$("#wate").attr("style","display: none;");
	        	$('#myTabContent').show();
	        },
	        type:'post',
	        url:'/getFlowAnalysisData',
	        data:searchJson,
	        success:function(data){
	        	if(data.flowCount!=null&&data.flowCount!=""){
	        		for(var i=0;i<data.flowCount.length;i++){
	        			 var isShow = 0 ;
	        			 for(var index in data.flowCount[i]){
	        				 dataAll = dataAll + Number(data.flowCount[i][index].tal_Nbr_Set);
	        				 isShow = isShow + Number(data.flowCount[i][index].tal_Nbr_Set);
		    	         }
	        			 if(isShow<=0){
	        			 }else{
	        				 var main=$("<div class='col-md-6' id='main"+id+i+"' style='margin:0px 0px 30px 3%;height: 320px;float:left;width: 45%;border: 1px solid #f1f1f1;border-top: 2px solid #74bbff;' align='center'></div>");
    	        			 charts.append(main);
	        				 flowCountEcharts(data.flowCount[i],"main"+id+i,searchJson);
	        			 }
	        		}
	        	}
	        	 if(data.allAmount!=null&&data.allAmount!=""){
	        		for(var i=0;i<data.allAmount.length;i++){
	        			 var isShow = 0 ;
	        			 for(var index in data.allAmount[i]){
	        				 dataAll = dataAll + Number(data.allAmount[i][index].cla_Set);
	        				 isShow = isShow + Number(data.allAmount[i][index].cla_Set);
		    	         }
	        			 if(isShow<=0){
	        			 }else{
	        				 var EvenPort=$("<div class='col-md-6' id='evenpot"+id+i+"' style='margin:0px 0px 30px 3%;height: 320px;float:left;width: 45%;border: 1px solid #f1f1f1;border-top: 2px solid #74bbff;' align='center'></div>"); 
    	        			 charts.append(EvenPort);
    	        			 allAmountEcharts(data.allAmount[i],"evenpot"+id+i,searchJson);
	        			 }
	        		}
	        	} 
	        	 if(dataAll<=0){
    				 alert("当前条件下无数据，请检查查询条件");
    			 }
	        }
	    }); 
	}else{
		alert("请检查查询条件");
	}
};
//移除DIV
function removeDiv(){
	$("#abflowcharts div").remove();
	$("#baflowcharts div").remove();
	$("#abtotalcharts div").remove();
	$("#batotalcharts div").remove();
	$("#abbatotalcharts div").remove();
};
function flowCountEcharts(flowCountData,divId,searchJson){
	require.config({
        paths: {
            echarts: '/echarts/build/dist'
        }
    });
    require(
            [
                'echarts',
                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/bar'
            ],
            function (ec) {  
                var chart = document.getElementById(divId);  
                
                var echart = ec.init(chart);  
                  
                echart.showLoading({  
                    text: '正在努力加载中...'  
                });  
                  
        	 	var oneName = [];//X轴
         	    var idd_moh = [];// 客流/月
         	    var cla_Nbr = [];// 班次/月
         	    var tal_Nbr_Set = [];// 座位/月
         	    var grp_moh = [];//团队人数/月
         	    var grp_Ine = [];//团队收入/月/100
         	    var tol_Ine = [];//收入/月/1000
        	    var tenName = '';
                  
                // 同步执行  
                $.ajaxSettings.async = false;  
                  
	        	for(var index in flowCountData){
    	        	oneName[index]  = flowCountData[index].month+"月";
    	        	idd_moh[index]  = flowCountData[index].idd_moh;
    	        	cla_Nbr[index]  = flowCountData[index].cla_Nbr;
    	        	tal_Nbr_Set[index]  = flowCountData[index].tal_Nbr_Set;
    	        	grp_moh[index]  = flowCountData[index].grp_moh;
    	        	grp_Ine[index]  = flowCountData[index].grp_Ine/100;
    	        	tol_Ine[index]  = flowCountData[index].tol_Ine/1000;
    	        	tenName = flowCountData[index].year+"年"+flowCountData[index].hangduan;
	        	}
                  
                var option = { 
                		title : {
                	        text: tenName+"  流量走势图",
                	        x: 'center'
                	    },
                    tooltip: {  
                        show: true  
                    },  
                    legend: {  
                        data: ['','客流/月','班次/月','座位/月','团队人数/月','团队收入/月/100','收入/月/1000']  
                    },
                    toolbox: {
                        show : true,
                        orient: 'vertical',
                        x: 'right',
                        y: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    xAxis: [  
                        {  
                            type: 'category',  
                            data: oneName
                        }  
                    ],  
                    yAxis: [  
                        {  
                            type: 'value'  
                        }  
                    ],  
                    series : [
							{  
								'name': "客流/月",  
							    'type': 'bar',  
							    'data': idd_moh
							 },
							{  
								'name': "班次/月",  
							    'type': 'line',  
							    'data': cla_Nbr
							 },
							{  
								'name': "座位/月",  
							    'type': 'line',  
							    'data': tal_Nbr_Set
							 },
							{  
								'name': "团队人数/月",  
							    'type': 'line',  
							    'data': grp_moh
							 },
							{  
								'name': "团队收入/月/100",  
							    'type': 'line',  
							    'data': grp_Ine
							 },
							{  
								'name': "收入/月/1000",  
							    'type': 'line',  
							    'data': tol_Ine
							 }
                  	    ]
                };  
            echart.setOption(option);  
            echart.hideLoading();  
        }
    );
}
//均班客量
function allAmountEcharts(allAmountData,divId,searchJson){
	require.config({
        paths: {
            echarts: '/echarts/build/dist'
        }
    });
    require(
            [
                'echarts',
                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/bar'
            ],
            function (ec) {  
                var chart = document.getElementById(divId);  
                
                var echart = ec.init(chart);  
                  
                echart.showLoading({  
                    text: '正在努力加载中...'  
                });  
                  
                var xtext = [];//日期
         	 	//var tme_Nbr = [];//天数
         	    //var cla_Nbr = [];// 班次/月
         	    //var tme_Cla_Moh = [];//日班次
         	    var cla_Set = [];//每班座位
         	    var cla_Per = [];//每班旅客
         	    var cla_Grp = [];//每班团队
         	    var flt_Ser_Ine = [];//团队收入/100
         	    var lod_For = [];//客座率
         	    var idd_Dct = [];//每班收入/1000
         	    var evenPort = '';//title
                  
                // 同步执行  
                $.ajaxSettings.async = false;  
                  
	        	for(var index in allAmountData){
	        		//tme_Nbr[index] = Number(allAmountData[index].tme_Nbr); 
	            	//cla_Nbr[index] = Number(allAmountData[index].cla_Nbr); 
	            	//tme_Cla_Moh[index] = Number(allAmountData[index].tme_Cla_Moh); 
	            	cla_Set[index] = Number(allAmountData[index].cla_Set); 
	            	cla_Per[index] = Number(allAmountData[index].cla_Per); 
	            	cla_Grp[index] = Number(allAmountData[index].cla_Grp); 
	            	flt_Ser_Ine[index] = Number(allAmountData[index].flt_Ser_Ine)/100; 
	            	lod_For[index] = Number(allAmountData[index].lod_For)*100; 
	            	idd_Dct[index] = Number(allAmountData[index].idd_Dct)/1000; 
	                xtext[index] = allAmountData[index].month;
	                evenPort = allAmountData[index].year+"年"+allAmountData[index].hangduan;
	        	}
                  
                var option = { 
                		title : {
                	        text: evenPort+" 均班客量走势图",
                	        x: 'center'
                	    },
                    tooltip: {  
                        show: true  
                    },  
                    legend: {  
                    	 data: ['','每班座位','每班旅客','每班团队','团队收入/100','客座率','每班收入/1000']  
                    },
                    toolbox: {
                        show : true,
                        orient: 'vertical',
                        x: 'right',
                        y: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    xAxis: [  
                        {  
                            type: 'category',  
                            data: xtext
                        }  
                    ],  
                    yAxis: [  
                        {  
                            type: 'value'  
                        }  
                    ],  
                    series : [
								 {  
										 'name': '每班座位',  
								     'type': 'line',  
								     'data': cla_Set  
								  },
									{  
										 'name': '每班旅客',  
								    'type': 'line',  
								    'data': cla_Per  
								 },
									
									{  
										 'name': '每班团队',  
								    'type': 'line',  
								    'data': cla_Grp  
								 },
									{  
										 'name': '团队收入/100',  
								    'type': 'line',  
								    'data': flt_Ser_Ine  
								 },
									{  
										 'name': '客座率',  
								    'type': 'line',  
								    'data': lod_For  
								 },
									{  
										 'name': '每班收入/1000',  
								    'type': 'line',  
								    'data': idd_Dct  
								 }
                  	    ]
                };  
            echart.setOption(option);  
            echart.hideLoading();  
        }
    );
}
window.onload = function(){
	var airLine = parent.supData.lane;
	var airports = parent.airportMap;
	if(airLine!=null&&airLine!=""&&airLine!='undefined'){
		var cds = airLine.split("=");
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth();
		$('#startDate').val(year+'-01');
		if(month>=9){
			$('#endDate').val(year+'-'+(month+1));
		}else{
			$('#endDate').val(year+'-0'+(month+1));
		}
		if(cds.length==3){
			$('#cityChoice').val(airports[cds[0]].aptChaNam);
			$('#cityChoice3').val(airports[cds[1]].aptChaNam);
			$('#cityChoice2').val(airports[cds[2]].aptChaNam);
			$("#abzs").html(cds[0]+"<span>&#xe622;</span>"+cds[1]+"<span>&#xe622;</span>"+cds[2]+"走势分析");
			$("#bazs").html(cds[2]+"<span>&#xe622;</span>"+cds[1]+"<span>&#xe622;</span>"+cds[0]+"走势分析");
			$("#abgf").html(cds[0]+"<span>&#xe622;</span>"+cds[1]+"<span>&#xe622;</span>"+cds[2]+"共飞对比分析");
			$("#bagf").html(cds[2]+"<span>&#xe622;</span>"+cds[1]+"<span>&#xe622;</span>"+cds[0]+"共飞对比分析");
			$("#abbagf").html(cds[0]+"<span>&#xe621;</span>"+cds[1]+"<span>&#xe621;</span>"+cds[2]+"共飞对比分析");
		}else{
			$('#cityChoice').val(airports[cds[0]].aptChaNam);
			$('#cityChoice2').val(airports[cds[1]].aptChaNam);
			$("#abzs").html(cds[0]+"<span>&#xe622;</span>"+cds[1]+"走势分析");
			$("#bazs").html(cds[1]+"<span>&#xe622;</span>"+cds[0]+"走势分析");
			$("#abgf").html(cds[0]+"<span>&#xe622;</span>"+cds[1]+"共飞对比分析");
			$("#bagf").html(cds[1]+"<span>&#xe622;</span>"+cds[0]+"共飞对比分析");
			$("#abbagf").html(cds[0]+"<span>&#xe621;</span>"+cds[1]+"共飞对比分析");
		}
		send();
	}
};
var cityPicker = new HzwCityPicker({
	data: data,
	target: 'cityChoice',
	valType: 'k-v',
	hideCityInput: {
	},
	callback: function(){
		var custormersearchForm;
		custormersearchForm = $("#DOW-searchForm");
		var searchJson = custormersearchForm.searchJson();
		if(""!=searchJson.dpt_AirPt_Cd&&""!=searchJson.Arrv_Airpt_Cd){
			if(""!=searchJson.pas_stp){
				$("#abzs").html(searchJson.dpt_AirPt_Cd+"<span>&#xe622;</span>"+searchJson.pas_stp+"<span>&#xe622;</span>"+searchJson.Arrv_Airpt_Cd+"走势分析");
				$("#bazs").html(searchJson.Arrv_Airpt_Cd+"<span>&#xe622;</span>"+searchJson.pas_stp+"<span>&#xe622;</span>"+searchJson.dpt_AirPt_Cd+"走势分析");
				$("#abgf").html(searchJson.dpt_AirPt_Cd+"<span>&#xe622;</span>"+searchJson.pas_stp+"<span>&#xe622;</span>"+searchJson.Arrv_Airpt_Cd+"共飞对比分析");
				$("#bagf").html(searchJson.Arrv_Airpt_Cd+"<span>&#xe622;</span>"+searchJson.pas_stp+"<span>&#xe622;</span>"+searchJson.dpt_AirPt_Cd+"共飞对比分析");
				$("#abbagf").html(searchJson.dpt_AirPt_Cd+"<span>&#xe621;</span>"+searchJson.pas_stp+"<span>&#xe621;</span>"+searchJson.Arrv_Airpt_Cd+"共飞对比分析");
			}else{
				$("#abzs").html(searchJson.dpt_AirPt_Cd+"<span>&#xe622;</span>"+searchJson.Arrv_Airpt_Cd+"走势分析");
				$("#bazs").html(searchJson.Arrv_Airpt_Cd+"<span>&#xe622;</span>"+searchJson.dpt_AirPt_Cd+"走势分析");
				$("#abgf").html(searchJson.dpt_AirPt_Cd+"<span>&#xe622;</span>"+searchJson.Arrv_Airpt_Cd+"共飞对比分析");
				$("#bagf").html(searchJson.Arrv_Airpt_Cd+"<span>&#xe622;</span>"+searchJson.dpt_AirPt_Cd+"共飞对比分析");
				$("#abbagf").html(searchJson.dpt_AirPt_Cd+"<span>&#xe621;</span>"+searchJson.Arrv_Airpt_Cd+"共飞对比分析");
			}
		}
	}
});
cityPicker.init();
var cityPicker2 = new HzwCityPicker({
	data: data,
	target: 'cityChoice2',
	valType: 'k-v',
	hideCityInput: {
	},
	callback: function(){
		var custormersearchForm;
		custormersearchForm = $("#DOW-searchForm");
		var searchJson = custormersearchForm.searchJson();
		if(""!=searchJson.dpt_AirPt_Cd&&""!=searchJson.Arrv_Airpt_Cd){
			if(""!=searchJson.pas_stp){
				$("#abzs").html(searchJson.dpt_AirPt_Cd+"<span>&#xe622;</span>"+searchJson.pas_stp+"<span>&#xe622;</span>"+searchJson.Arrv_Airpt_Cd+"走势分析");
				$("#bazs").html(searchJson.Arrv_Airpt_Cd+"<span>&#xe622;</span>"+searchJson.pas_stp+"<span>&#xe622;</span>"+searchJson.dpt_AirPt_Cd+"走势分析");
				$("#abgf").html(searchJson.dpt_AirPt_Cd+"<span>&#xe622;</span>"+searchJson.pas_stp+"<span>&#xe622;</span>"+searchJson.Arrv_Airpt_Cd+"共飞对比分析");
				$("#bagf").html(searchJson.Arrv_Airpt_Cd+"<span>&#xe622;</span>"+searchJson.pas_stp+"<span>&#xe622;</span>"+searchJson.dpt_AirPt_Cd+"共飞对比分析");
				$("#abbagf").html(searchJson.dpt_AirPt_Cd+"<span>&#xe621;</span>"+searchJson.pas_stp+"<span>&#xe621;</span>"+searchJson.Arrv_Airpt_Cd+"共飞对比分析");
			}else{
				$("#abzs").html(searchJson.dpt_AirPt_Cd+"<span>&#xe622;</span>"+searchJson.Arrv_Airpt_Cd+"走势分析");
				$("#bazs").html(searchJson.Arrv_Airpt_Cd+"<span>&#xe622;</span>"+searchJson.dpt_AirPt_Cd+"走势分析");
				$("#abgf").html(searchJson.dpt_AirPt_Cd+"<span>&#xe622;</span>"+searchJson.Arrv_Airpt_Cd+"共飞对比分析");
				$("#bagf").html(searchJson.Arrv_Airpt_Cd+"<span>&#xe622;</span>"+searchJson.dpt_AirPt_Cd+"共飞对比分析");
				$("#abbagf").html(searchJson.dpt_AirPt_Cd+"<span>&#xe621;</span>"+searchJson.Arrv_Airpt_Cd+"共飞对比分析");
			}
		}
	}
});
cityPicker2.init();
var cityPicker3 = new HzwCityPicker({
	data: data,
	target: 'cityChoice3',
	valType: 'k-v',
	hideCityInput: {
	},
	callback: function(){
		var custormersearchForm;
		custormersearchForm = $("#DOW-searchForm");
		var searchJson = custormersearchForm.searchJson();
		if(""!=searchJson.dpt_AirPt_Cd&&""!=searchJson.Arrv_Airpt_Cd){
			if(""!=searchJson.pas_stp){
				$("#abzs").html(searchJson.dpt_AirPt_Cd+"<span>&#xe622;</span>"+searchJson.pas_stp+"<span>&#xe622;</span>"+searchJson.Arrv_Airpt_Cd+"走势分析");
				$("#bazs").html(searchJson.Arrv_Airpt_Cd+"<span>&#xe622;</span>"+searchJson.pas_stp+"<span>&#xe622;</span>"+searchJson.dpt_AirPt_Cd+"走势分析");
				$("#abgf").html(searchJson.dpt_AirPt_Cd+"<span>&#xe622;</span>"+searchJson.pas_stp+"<span>&#xe622;</span>"+searchJson.Arrv_Airpt_Cd+"共飞对比分析");
				$("#bagf").html(searchJson.Arrv_Airpt_Cd+"<span>&#xe622;</span>"+searchJson.pas_stp+"<span>&#xe622;</span>"+searchJson.dpt_AirPt_Cd+"共飞对比分析");
				$("#abbagf").html(searchJson.dpt_AirPt_Cd+"<span>&#xe621;</span>"+searchJson.pas_stp+"<span>&#xe621;</span>"+searchJson.Arrv_Airpt_Cd+"共飞对比分析");
			}else{
				$("#abzs").html(searchJson.dpt_AirPt_Cd+"<span>&#xe622;</span>"+searchJson.Arrv_Airpt_Cd+"走势分析");
				$("#bazs").html(searchJson.Arrv_Airpt_Cd+"<span>&#xe622;</span>"+searchJson.dpt_AirPt_Cd+"走势分析");
				$("#abgf").html(searchJson.dpt_AirPt_Cd+"<span>&#xe622;</span>"+searchJson.Arrv_Airpt_Cd+"共飞对比分析");
				$("#bagf").html(searchJson.Arrv_Airpt_Cd+"<span>&#xe622;</span>"+searchJson.dpt_AirPt_Cd+"共飞对比分析");
				$("#abbagf").html(searchJson.dpt_AirPt_Cd+"<span>&#xe621;</span>"+searchJson.Arrv_Airpt_Cd+"共飞对比分析");
			}
		}
	}
});
cityPicker3.init();
 $("#check-all").on('ifUnchecked', function(event) {
     $("input[type='checkbox']", ".table-mailbox").iCheck("uncheck");
 });
 $("#check-all").on('ifChecked', function(event) {
     $("input[type='checkbox']", ".table-mailbox").iCheck("check");
 });
 function subbTime(){
	custormersearchForm = $("#DOW-searchForm");
	var searchJson = custormersearchForm.searchJson();
	/* var starttime = new Date(searchJson.startDate) ;
	var endtime = new Date(searchJson.endDate);
	var cha = endtime.valueOf() - starttime.valueOf();
	var yearcha = cha/(365*24*60*60*1000);
	if(yearcha>2){
		alert("你选择的时间段过长，请选择两年以内的时间段");
	} */
	var starttime = searchJson.startDate;
	var endtime = searchJson.endDate;
	var cha = endtime.split("-")[0] - starttime.split("-")[0];
	if(cha>=2){
		alert("你选择的时间段过长，请选择两年以内的时间段");
	}
 }
 function abtotalflyAsiay(){
	removeDiv();
	$('.in').addClass('psc-sr-body');
	$('.in').removeClass('tab-pane fade active in');
	$('#abtotalcharts').attr('class','tab-pane fade active in').css('display','block');
	$('.psc-sr-icon ul li div').css('border-bottom','none');
	$(".abgf").css('border-bottom','2px solid #69caff');
	$('.psc-sr-body').css('display','none');
	var custormersearchForm;
	custormersearchForm = $("#DOW-searchForm");
	var searchJson = custormersearchForm.searchJson();
	totalflyAnalysis(searchJson,"abtotalcharts");
}
function batotalflyAsiay(){
	removeDiv();
	$('.in').addClass('psc-sr-body');
	$('.in').removeClass('tab-pane fade active in');
	$('#batotalcharts').attr('class','tab-pane fade active in').css('display','block');
	$('.psc-sr-icon ul li div').css('border-bottom','none');
	$(".bagf").css('border-bottom','2px solid #69caff');
	$('.psc-sr-body').css('display','none');
	var custormersearchForm;
	custormersearchForm = $("#DOW-searchForm");
	var searchJson = custormersearchForm.searchJson();
	var dpt_AirPt_Cd = searchJson.dpt_AirPt_Cd;
	var arrv_Airpt_Cd = searchJson.Arrv_Airpt_Cd;
	searchJson.dpt_AirPt_Cd = arrv_Airpt_Cd;
	searchJson.Arrv_Airpt_Cd = dpt_AirPt_Cd;
	totalflyAnalysis(searchJson,"batotalcharts");
}
function abbatotalflyAsiay(){
	removeDiv();
	$('.in').addClass('psc-sr-body');
	$('.in').removeClass('tab-pane fade active in');
	$('#abbatotalcharts').attr('class','tab-pane fade active in').css('display','block');
	$('.psc-sr-icon ul li div').css('border-bottom','none');
	$(".abbagf").css('border-bottom','2px solid #69caff');
	$('.psc-sr-body').css('display','none');
	var custormersearchForm;
	custormersearchForm = $("#DOW-searchForm");
	var searchJson = custormersearchForm.searchJson();
	totalflyAnalysisHd(searchJson,"abbatotalcharts");
}
//共飞分析
function totalflyAnalysisHd(searchJson,id){
	var charts = $("#"+id);
	var dataAll = 0;
	var width = $(document.body).width()/2+"px";
	if(""!=searchJson.startDate&&""!=searchJson.endDate&&""!=searchJson.dpt_AirPt_Cd&&""!=searchJson.Arrv_Airpt_Cd){
		$.ajax({
			beforeSend: function(){
				$('#myTabContent').hide();
				$("#wate").attr("style","display: block;");
	        },
	        complete:function(){
	        	$("#wate").attr("style","display: none;");
	        	$('#myTabContent').show();
	        },
	        type:'post',
	        url:'/getTotalFlyAnalysisDataHd',
	        data:searchJson,
	        success:function(data){
	        	if(data.flightNum!=null&&data.flightNum!=""){
	        		for(var i=0;i<data.flightNum.length;i++){
	        			 var isShow = 0 ;
	        			 for(var index in data.flightNum[i]){
	        				 dataAll = dataAll + Number(data.flightNum[i][index].flt_Nbr_num);
	        				 isShow = isShow + Number(data.flightNum[i][index].flt_Nbr_num);
		    	         }
	        			 if(isShow<=0){
	        			 }else{
	        				 var flightNum=$("<div class='col-md-6' id='flightNum"+id+i+"' style='margin:0px 0px 30px 3%;height: 320px;float:left;width: 45%;border: 1px solid #f1f1f1;border-top: 2px solid #74bbff;' align='center'></div>");
    	        			 charts.append(flightNum);
    	        			 flightNumEcharts(data.flightNum[i],"flightNum"+id+i);
	        			 }
	        		}
	        	}
	             if(data.raskRanking!=null&&data.raskRanking!=""){
	        		for(var i=0;i<data.raskRanking.length;i++){
	        			 var isShow = 0 ;
	        			 for(var index in data.raskRanking[i]){
	        				 dataAll = dataAll + Number(data.raskRanking[i][index].jbys);
	        				 isShow = isShow + Number(data.raskRanking[i][index].jbys);
		    	         }
	        			 if(isShow<=0){
	        			 }else{
	        				 var raskRanking=$("<div class='col-md-6' id='raskRanking"+id+i+"' style='margin:0px 0px 30px 3%;height: 320px;float:left;width: 45%;border: 1px solid #f1f1f1;border-top: 2px solid #74bbff;' align='center'></div>"); 
    	        			 charts.append(raskRanking);
    	        			 raskRankingEcharts(data.raskRanking[i],"raskRanking"+id+i);
	        			 } 
	        		}
	        	} 
	        	 if(data.passengerRank!=null&&data.passengerRank!=""){
	        		for(var i=0;i<data.passengerRank.length;i++){
	        			 var isShow = 0 ;
	        			 for(var index in data.passengerRank[i]){
	        				 dataAll = dataAll + Number(data.passengerRank[i][index].pgs_Per_Cls_num);
	        				 isShow = isShow + Number(data.passengerRank[i][index].pgs_Per_Cls_num);
		    	         }
	        			 if(isShow<=0){
	        			 }else{
	        				 var passengerRank=$("<div class='col-md-6' id='passengerRank"+id+i+"' style='margin:0px 0px 30px 3%;height: 320px;float:left;width: 45%;border: 1px solid #f1f1f1;border-top: 2px solid #74bbff;' align='center'></div>"); 
    	        			 charts.append(passengerRank);
    	        			 passengerRankEcharts(data.passengerRank[i],"passengerRank"+id+i);
	        			 } 
	        		}
	        	} 
	        	 if(data.passengerCompared!=null&&data.passengerCompared!=""){
	        		for(var i=0;i<data.passengerCompared.length;i++){
	        			 var isShow = 0 ;
	        			 for(var index in data.passengerCompared[i]){
	        				 dataAll = dataAll + Number(data.passengerCompared[i][index].value);
	        				 isShow = isShow + Number(data.passengerCompared[i][index].value);
		    	         }
	        			 if(isShow<=0){
	        			 }else{
	        				 var passengerCompared=$("<div class='col-md-6' id='passengerCompared"+id+i+"' style='margin:0px 0px 30px 3%;height: 320px;float:left;width: 45%;border: 1px solid #f1f1f1;border-top: 2px solid #74bbff;' align='center'></div>"); 
    	        			 charts.append(passengerCompared);
    	        			 passengerComparedEcharts(data.passengerCompared[i],"passengerCompared"+id+i);
	        			 }
	        		}
	        	}  
	        	 if(data.allClassRank!=null&&data.allClassRank!=""){
	        		for(var i=0;i<data.allClassRank.length;i++){
	        			 var isShow = 0 ;
	        			 for(var index in data.allClassRank[i]){
	        				 dataAll = dataAll + Number(data.allClassRank[i][index].jbzw);
	        				 isShow = isShow + Number(data.allClassRank[i][index].jbzw);
		    	         }
	        			 if(isShow<=0){
	        			 }else{
	        				 var allClassRank=$("<div class='col-md-6' id='allClassRank"+id+i+"' style='margin:0px 0px 30px 3%;height: 320px;float:left;width: 45%;border: 1px solid #f1f1f1;border-top: 2px solid #74bbff;' align='center'></div>"); 
    	        			 charts.append(allClassRank);
    	        			 allClassRankEcharts(data.allClassRank[i],"allClassRank"+id+i);
	        			 }
	        		}
	        	}  
	        	 if(dataAll<=0){
        				 alert("当前条件下无数据，请检查查询条件");
        			 }
	        }
		
	    }); 
	}else{
		alert("请检查查询条件");
	}
};
//共飞分析
function totalflyAnalysis(searchJson,id){
	var charts = $("#"+id);
	 var dataAll = 0;
	var width = $(document.body).width()/2+"px";
	if(""!=searchJson.startDate&&""!=searchJson.endDate&&""!=searchJson.dpt_AirPt_Cd&&""!=searchJson.Arrv_Airpt_Cd){
		$.ajax({
			beforeSend: function(){
				$('#myTabContent').hide();
				$("#wate").attr("style","display: block;");
	        },
	        complete:function(){
	        	$("#wate").attr("style","display: none;");
	        	$('#myTabContent').show();
	        },
	        type:'post',
	        url:'/getTotalFlyAnalysisData',
	        data:searchJson,
	        success:function(data){
	        	if(data.flightNum!=null&&data.flightNum!=""){
	        		for(var i=0;i<data.flightNum.length;i++){
	        			 var isShow = 0 ;
	        			 for(var index in data.flightNum[i]){
	        				 dataAll = dataAll + Number(data.flightNum[i][index].flt_Nbr_num);
	        				 isShow = isShow + Number(data.flightNum[i][index].flt_Nbr_num);
		    	         }
	        			 if(isShow<=0){
	        			 }else{
	        				 var flightNum=$("<div class='col-md-6' id='flightNum"+id+i+"' style='margin:0px 0px 30px 3%;height: 320px;float:left;width: 45%;border: 1px solid #f1f1f1;border-top: 2px solid #74bbff;' align='center'></div>");
    	        			 charts.append(flightNum);
    	        			 flightNumEcharts(data.flightNum[i],"flightNum"+id+i);
	        			 }
	        		}
	        	}
	             if(data.raskRanking!=null&&data.raskRanking!=""){
	        		for(var i=0;i<data.raskRanking.length;i++){
	        			var isShow = 0 ;
	        			 for(var index in data.raskRanking[i]){
	        				 dataAll = dataAll + Number(data.raskRanking[i][index].jbys);
	        				 isShow = isShow + Number(data.raskRanking[i][index].jbys);
		    	         }
	        			 if(isShow<=0){
	        			 }else{
	        				 var raskRanking=$("<div class='col-md-6' id='raskRanking"+id+i+"' style='margin:0px 0px 30px 3%;height: 320px;float:left;width: 45%;border: 1px solid #f1f1f1;border-top: 2px solid #74bbff;' align='center'></div>"); 
    	        			 charts.append(raskRanking);
    	        			 raskRankingEcharts(data.raskRanking[i],"raskRanking"+id+i);
	        			 } 
	        		}
	        	} 
	        	 if(data.passengerRank!=null&&data.passengerRank!=""){
	        		for(var i=0;i<data.passengerRank.length;i++){
	        			var isShow = 0 ;
	        			 for(var index in data.passengerRank[i]){
	        				 dataAll = dataAll + Number(data.passengerRank[i][index].pgs_Per_Cls_num);
	        				 isShow = isShow + Number(data.passengerRank[i][index].pgs_Per_Cls_num);
		    	         }
	        			 if(isShow<=0){
	        			 }else{
	        				 var passengerRank=$("<div class='col-md-6' id='passengerRank"+id+i+"' style='margin:0px 0px 30px 3%;height: 320px;float:left;width: 45%;border: 1px solid #f1f1f1;border-top: 2px solid #74bbff;' align='center'></div>"); 
    	        			 charts.append(passengerRank);
    	        			 passengerRankEcharts(data.passengerRank[i],"passengerRank"+id+i);
	        			 } 
	        		}
	        	} 
	        	 if(data.passengerCompared!=null&&data.passengerCompared!=""){
	        		for(var i=0;i<data.passengerCompared.length;i++){
	        			var isShow = 0 ;
	        			 for(var index in data.passengerCompared[i]){
	        				 dataAll = dataAll + Number(data.passengerCompared[i][index].value);
	        				 isShow = isShow + Number(data.passengerCompared[i][index].value);
		    	         }
	        			 if(isShow<=0){
	        			 }else{
	        				 var passengerCompared=$("<div class='col-md-6' id='passengerCompared"+id+i+"' style='margin:0px 0px 30px 3%;height: 320px;float:left;width: 45%;border: 1px solid #f1f1f1;border-top: 2px solid #74bbff;' align='center'></div>"); 
    	        			 charts.append(passengerCompared);
    	        			 passengerComparedEcharts(data.passengerCompared[i],"passengerCompared"+id+i);
	        			 }
	        			 
	        		}
	        	}  
	        	 if(data.allClassRank!=null&&data.allClassRank!=""){
	        		for(var i=0;i<data.allClassRank.length;i++){
	        			var isShow = 0 ;
	        			 for(var index in data.allClassRank[i]){
	        				 dataAll = dataAll + Number(data.allClassRank[i][index].jbzw);
	        				 isShow = isShow + Number(data.allClassRank[i][index].jbzw);
		    	         }
	        			 if(isShow<=0){
	        			 }else{
	        				 var allClassRank=$("<div class='col-md-6' id='allClassRank"+id+i+"' style='margin:0px 0px 30px 3%;height: 320px;float:left;width: 45%;border: 1px solid #f1f1f1;border-top: 2px solid #74bbff;' align='center'></div>"); 
    	        			 charts.append(allClassRank);
    	        			 allClassRankEcharts(data.allClassRank[i],"allClassRank"+id+i);
	        			 }
	        		}
	        	} 
	        	 if(dataAll<=0){
        				 alert("当前条件下无数据，请检查查询条件");
        			 }
	        }
		
	    }); 
	}else{
		alert("请检查查询条件");
	}
};


 
function flightNumEcharts(data,id){
	require.config({
        paths: {
            echarts: '/echarts/build/dist'
        }
    });
    require(
            [
                'echarts',
                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/bar'
            ],
            function (ec) {  
                var chart = document.getElementById(id);  
                var echart = ec.init(chart);  
                  
                echart.showLoading({  
                    text: '正在努力加载中...'  
                });  
                  
                var one = [];
        	 	var oneName = [];
        	    var tenName = '';
                var jb =[];
                // 同步执行  
                $.ajaxSettings.async = false;  
                var index=0;
        	    for(index in data){
                	one[index] = data[index].flt_Nbr; 
                	oneName[index] = Number(data[index].flt_Nbr_num); 
                 	tenName = data[index].hangduan+"  "+data[index].startDate+"至"+data[index].endDate;
                 	jb[index] = data[index].jb;
    	        }
        	    if(index<9){
        	    	for(var i=parseInt(index)+1;i<10;i++){
        	    		one[i]= '-';
//        	    		oneName[i] = 0;
        	    	}
        	    }
                var option = { 
                		title : {
                	        text: tenName+"航班量前十排行",
                	        x: 'center'
                	    },
                    tooltip: {  
                        show: true  
                    },  
                    legend: {  
                        data: ['','班次',]  
                    },
                    toolbox: {
                        show : true,
                        orient: 'vertical',
                        x: 'right',
                        y: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    xAxis: [  
                         {  
                            type: 'category', 
                            axisLabel:{
                                interval:0,
                              rotate:45,
                              margin:2,
                              textStyle:{
                                  color:"#222"
                              }
                          },
                            data: one
                        }  
                    ],  
                    grid: { // 控制图的大小，调整下面这些值就可以，
                        x: 40,
                        x2: 100,
                        y2: 80,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
                    },
                    yAxis: [  
                        {  
                            type: 'value'  
                        }  
                    ],  
                    series : [
                  	    	{  
                  	    		 'name': "班次",  
                                 'type': 'bar',  
                                 'data': oneName,
                                 markLine : {
                                     data : [
                                         // 纵轴，默认
                                      {type : 'average', name : '平均值'},
                                     ]
                                 }
                              }
                  	    ]
                };  
            echart.setOption(option);  
            echart.hideLoading();  
        }
    );
};


function raskRankingEcharts(data,id){
	require.config({
        paths: {
            echarts: '/echarts/build/dist'
        }
    });
    require(
            [
                'echarts',
                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/bar'
            ],
            function (ec) {  
                var chart = document.getElementById(id);  
                var echart = ec.init(chart);  
                  
                echart.showLoading({  
                    text: '正在努力加载中...'  
                });  
                  
                var one = [];
        	 	var Tal_Nbr_num = [];//每班营收
        	 	var Set_Ktr_Ine_num = [];//座公里收入
        	    var tenName = '';
                // 同步执行  
                $.ajaxSettings.async = true;  
                 
        	    for(var index in data){
        	    	one[index] = data[index].flt_Nbr;
                	Tal_Nbr_num[index] = Number((data[index].tal_Nbr_num/1000).toFixed(2)); 
                	Set_Ktr_Ine_num[index] = Number(data[index].set_Ktr_Ine_num)*100; 
                 	tenName = data[index].hangduan+"  "+data[index].startDate+"至"+data[index].endDate;
    	        }
               if(index<9){
            	   for(var i= parseInt(index)+1;i<10;i++){
            		   one[i] = '-';
            	   }
               }
                var option = { 
                		title : {
                	        text: tenName+"座收前十排行",
                	        x: 'center'
                	    },
                    tooltip: {  
                        show: true  
                    },  
                    legend: {  
                        data: ['','每班营收/1000','座公里收入*100']  
                    },
                    toolbox: {
                        show : true,
                        orient: 'vertical',
                        x: 'right',
                        y: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    xAxis: [  
                        {  
                            type: 'category', 
                            axisLabel:{
                                interval:0,
                              rotate:45,
                              margin:2,
                              textStyle:{
                                  color:"#222"
                              }
                          },
                            data: one
                        }  
                    ],  
                    grid: { // 控制图的大小，调整下面这些值就可以，
                        x: 40,
                        x2: 100,
                        y2: 80,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
                    },
                    yAxis: [  
                        {  
                            type: 'value'  
                        }  
                    ],  
                    series : [
                  	    	{  
                  	    		 'name': "每班营收/1000",  
                                 'type': 'bar',  
                                 'data': Tal_Nbr_num,
                                 markLine : {
                                     data : [
                                         // 纵轴，默认
                                      {type : 'average', name : '平均值'},
                                     ]
                                 }
                              },
                              {  
                  	    		 'name': "座公里收入*100",  
                                 'type': 'bar',  
                                 'data': Set_Ktr_Ine_num,
                                 markLine : {
                                     data : [
                                         // 纵轴，默认
                                      {type : 'average', name : '平均值'},
                                     ]
                                 }
                              }
                  	    ]
                };  
            echart.setOption(option);  
            echart.hideLoading();  
        }
    );
};


function passengerRankEcharts(data,id){
	require.config({
        paths: {
            echarts: '/echarts/build/dist'
        }
    });
    require(
            [
                'echarts',
                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/bar'
            ],
            function (ec) {  
                var chart = document.getElementById(id);  
                var echart = ec.init(chart);  
                  
                echart.showLoading({  
                    text: '正在努力加载中...'  
                });  
                  
                var one = [];
                var jbkl = [];
        	 	var oneName = [];
        	    var tenName = '';
                  
                // 同步执行  
                $.ajaxSettings.async = false;  
                var index = 0;
        	    for(index in data){
                	one[index] = data[index].flt_Nbr; 
                	jbkl[index] = data[index].jbkl; 
                	oneName[index] = Number(data[index].pgs_Per_Cls_num); 
                 	tenName = data[index].hangduan+"  "+data[index].startDate+"至"+data[index].endDate;
    	        }
               if(index<9){
            	   for(var i=parseInt(index)+1;i<10;i++){
            		   one[i] = '-';
            	   }
               }
                var option = { 
                		title : {
                	        text: tenName+"客量前十排行",
                	        x: 'center'
                	    },
                    tooltip: {  
                        show: true  
                    },  
                    legend: {  
                        data: ['','客量']  
                    },
                    toolbox: {
                        show : true,
                        orient: 'vertical',
                        x: 'right',
                        y: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    xAxis: [  
                        {  
                            type: 'category',  
                            axisLabel:{
                                interval:0,
                              rotate:45,
                              margin:2,
                              textStyle:{
                                  color:"#222"
                              }
                          },
                            data: one
                        }  
                    ],  
                    grid: { // 控制图的大小，调整下面这些值就可以，
                        x: 50,
                        x2: 100,
                        y2: 80,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
                    },
                    yAxis: [  
                        {  
                            type: 'value'  
                        }  
                    ],  
                    series : [
                  	    	{  
                  	    		 'name': "客量",  
                                 'type': 'bar',  
                                 'data': oneName,
                                 markLine : {
                                     data : [
                                         // 纵轴，默认
                                      {type : 'average', name : '平均值'},
                                     ]
                                 }
                              }
                  	    ]
                };  
            echart.setOption(option);  
            echart.hideLoading();  
        }
    );
};


function allClassRankEcharts(data,id){
	require.config({
        paths: {
            echarts: '/echarts/build/dist'
        }
    });
    require(
            [
                'echarts',
                'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/bar'
            ],
            function (ec) {  
                var chart = document.getElementById(id);  
                var echart = ec.init(chart);  
                  
                echart.showLoading({  
                    text: '正在努力加载中...'  
                });  
                  
                var jbzw = [];
                var jblk = [];
                var jbtd = [];
                var one = [];
        	 	var Tal_Nbr_Set_num = [];
        	 	var Pgs_Per_num = [];
        	 	var Grp_Nbr_num = [];
        	    var tenName = '';
                  
                // 同步执行  
                $.ajaxSettings.async = false;  
                var index = 0;
        	    for(index in data){
        	    	jbzw[index] = data[index].jbzw; 
        	    	jblk[index] = data[index].jblk; 
        	    	jbtd[index] = data[index].jbtd; 
                	one[index] = data[index].flt_Nbr; 
                	Tal_Nbr_Set_num[index] = Number(data[index].tal_Nbr_Set_num); 
                	Pgs_Per_num[index] = Number(data[index].pgs_Per_num); 
                	Grp_Nbr_num[index] = Number(data[index].grp_Nbr_num); 
                 	tenName = data[index].hangduan+"  "+data[index].startDate+"至"+data[index].endDate;
    	        }
        	    if(index<9){
        	    	for(var i=parseInt(index)+1;i<10;i++){
        	    		one[i] = '-';
        	    	}
        	    }
                var option = { 
                		title : {
                	        text: tenName+"均班客量前十排行",
                	        x: 'center'
                	    },
                    tooltip: {  
                        show: true  
                    },  
                    legend: {  
                        data: ['','每班座位','每班旅客','每班团队']  
                    },
                    toolbox: {
                        show : true,
                        orient: 'vertical',
                        x: 'right',
                        y: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    xAxis: [  
                        {  
                            type: 'category',  
                            axisLabel:{
                              interval:0,
                              rotate:45,
                              margin:2,
                              textStyle:{
                                  color:"#222"
                              }
                          },
                            data: one
                        }  
                    ],  
                    grid: { // 控制图的大小，调整下面这些值就可以，
                        x: 40,
                        x2: 100,
                        y2: 80,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
                    },
                    yAxis: [  
                        {  
                            type: 'value'  
                        }  
                    ],  
                    series : [
                  	    	{  
                  	    		 'name': "每班座位",  
                                 'type': 'bar',  
                                 'data': Tal_Nbr_Set_num,
                                 markLine : {
                                     data : [
                                         // 纵轴，默认
                                      {type : 'average', name : '平均值'},
                                     ]
                                 }
                              },
                  	    	{  
                  	    		 'name': "每班旅客",  
                                 'type': 'bar',  
                                 'data': Pgs_Per_num,
                                 markLine : {
                                     data : [
                                         // 纵轴，默认
                                      {type : 'average', name : '平均值'},
                                     ]
                                 }
                              },
                  	    	{  
                  	    		 'name': "每班团队",  
                                 'type': 'bar',  
                                 'data': Grp_Nbr_num,
                                 markLine : {
                                     data : [
                                         // 纵轴，默认
                                      {type : 'average', name : '平均值'},
                                     ]
                                 }
                              }
                  	    ]
                };  
            echart.setOption(option);  
            echart.hideLoading();  
        }
    );
};

function passengerComparedEcharts(data,id){
	require.config({
        paths: {
            echarts: '/echarts/build/dist'
        }
    });
    
    // 使用
    require(
        [
            'echarts',
            'echarts/chart/pie',
            'echarts/chart/funnel'
        ],
        function (ec) {  
            var chart = document.getElementById(id);  
            var echart = ec.init(chart);  
              
            echart.showLoading({  
                text: '正在努力加载中...'  
            });  
              
            var xtext = [];//标题
    	    var idd_Dct = [];//客量/名称
    	    var evenPort = '';//title
              
            // 同步执行  
            $.ajaxSettings.async = false;  
              
            for(var index in data){
                xtext[index] = data[index].name;//给X轴TEXT赋值
                evenPort=data[index].title;
            }
            idd_Dct = data;
            
              
            option = {
            	    title : {
            	        text: evenPort,
            	        x:'center'
            	    },
            	    tooltip : {
            	        trigger: 'item',
            	        formatter: "{a} <br/>{b} : {c} ({d}%)"
            	    },
            	    legend: {
            	        orient : 'vertical',
            	        x : 'left',
            	        data:xtext
            	    },
            	    toolbox: {
            	        show : true,
                        orient: 'vertical',
                        x: 'right',
                        y: 'center',
            	        feature : {
            	            mark : {show: true},
            	            dataView : {show: true, readOnly: false},
            	            magicType : {
            	                show: true,
            	                type: ['pie', 'funnel'],
            	                option: {
            	                    funnel: {
            	                        x: '25%',
            	                        width: '50%',
            	                        funnelAlign: 'left',
            	                        max: 1548
            	                    }
            	                }
            	            },
            	            restore : {show: true},
            	            saveAsImage : {show: true}
            	        }
            	    },
            	    calculable : true,
            	    series : [
            	        {
            	            name:'客量占比',
            	            type:'pie',
            	            radius : '55%',
            	            center: ['50%', '60%'],
            	            data:idd_Dct
            	        }
            	    ]
            	};
            echart.setOption(option);  
            echart.hideLoading();  
        }
    );
};