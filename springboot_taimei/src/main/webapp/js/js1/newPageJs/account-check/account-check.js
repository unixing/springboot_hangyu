$(document).ready(function() {
    var flightList=[],  //航班对
        flight_pre=[],  //总航段
        flight_pre_text="",  //航段leg
        newflight_pre_text = "",
        end_time="",  //终止日期
        start_time="",    //开始日期
        current_Flight="",  //当前航班号
        current_lane="",	//当前航线
        leg_list="",
        salesDatas={},
        ajaxmark= false;
//------------------------------------------判断入口
    if(parent.supData.flight){
    	if(parent.supData.lane=="" || parent.supData.lane== null ){	//判断入口航班号是否正确
    		alert('请输入航班号和日期！');
        	$("#dataLoading").hide('normal');
    		$(".err").css("display","block");
    		if(parent.supData.flight!=""){
    	        let list=parent.supData.flight.split("/");
    	        list=fliNum_shape(list);
    	        let _Flight=list[0];
    		}
    		else{
        		$("#SD-head-inquire").empty();
        		$("#reservation").empty();
    		}
    	} else {
    		$(".err").hide();
        	if(daysBetween(parent.supData.endTime, parent.supData.startTime) > 62){
        		parent.supData.startTime = SD_getDate_62(parent.supData.endTime);
        	}
    		var aaaaa= parent.supData.lane.split("=");
			flight_pre_text = toEnName(aaaaa);
    		current_lane= parent.supData.lane;
	        flightList= parent.supData.flight.split("/");
	        flightList= fliNum_shape(flightList);
	        current_Flight= flightList[0];
	        $("#SD-head-inquire").val(current_Flight);
            var aatime= SD_getCurrent_date(current_Flight,parent.supData.lane);
            /*
	        if(aatime === ''){
	        	current_Flight= flightList[1];
	        	aatime= SD_getCurrent_date(current_Flight,parent.supData.lane);	        	
	        	setTimeout(function(){
	        		parent.supData.startTime= 'undefined',
	        		parent.supData.endTime= 'undefined';
	        		$(".lin-historical-body-navigation div")[1].click();
	        	},500);
            }
            */
	        if(aatime === ''){
	        	$(".err").css("display","block");
	        	return;
	        }	        
	        if(parent.supData.endTime && parent.supData.endTime != "undefined"){
	        	end_time= parent.supData.endTime;
	        	start_time= parent.supData.startTime;
	        	$('#sr-box-head-inquire ._set-query').click();	        	
	        }
	        else if(aatime!= 0 && aatime!= undefined && aatime!= "" && aatime!=null){
	        	if(aatime!="1"){
	        		end_time= aatime;
	        		start_time= SD_getDate_30(end_time);        
	        		$("#reservation").val(start_time + " - " + end_time);
	        		$(".SD-set-list-title").empty();
	        		$(".SD-set-list-title").text(parent.supData.lane);
	        		getFliList(current_Flight,start_time,end_time);
	        		SD_fill_hz(current_Flight,flight_pre_text,start_time,end_time,true);	        	
	        	}
	        }
	        else{
	        	return;
	        	error_info(true);
	        }
    	}
    }else{
    	$("#SD-cover").show();
        $("#SD-head-inquire").focus();
        if(parent.supData.flag== 1){
        	parent.supData.flag= 0;
        }
        else{
        	alert('请输入航班号和日期！');
        	$("#dataLoading").hide('normal');
    		$(".err").css("display","block");
        }
        
    }
    // console.clear();
    
//------------------------------------------有父级
    
        //格式化数据
    var soupList = function(list){
        if(list.length>0){
            //改变日期格式
            list[0]=list[0].replace(/-/g,".");
            list[1]=list[1].replace(/-/g,".");
            //如果同年则去掉后一年份
            if(list[0].substring(0,4)===list[1].substring(0,4)){                        
                list[1]=list[1].slice(5);
            }
            //合并起止日期
            list[0]=list[0]+"-"+list[1];                    
            list.splice(1,1);
            //删除最后一项
            list.pop();
            //添加千分位
            list[list.length-1]=toThousands(list[list.length-1]);
            list[list.length-2]=toThousands(list[list.length-2]);
            return list;
        }
        else{        	        	
            error_info(true);
        }
    }
    var SD_fill_hbDetail = function (fli,alldata) {
    //默认返回第一个航班号的数据，否则根据来源.
        var jslist = [];
        if(current_Flight){
            $.each(alldata.data, function(i,content) {
                if(fli==i){
                    $.each(content, function(index, val){
                        jslist.push(val);
                    })
                }
            });
        }
        else{
            $.each(alldata.data, function(i,content) {
                for(let mark=0 ; mark<headlist.length;mark++){
                    if(headlist[mark]==i){
                        $.each(content, function(index, val) {
                            jslist.push(val);
                        })
                    }
                }
            });                     

        } 
        //填充头部汇总项
        if(jslist&&jslist[0]&&jslist[1]){
            jslist = soupList(jslist); 
            $.each($("#SD-head-title .sumDetail"),function(i){
                $(this).text(jslist[i]);
            })
            return jslist
        }
        else{        	        	
            error_info(true);
        }
    }
    var handleSalesData = function(data,dfirst,fli){
        var oPicker = $("#SD-table-head ul");  //航段选择 
        salesDatas=data;
        var alldata=data;
        //创建头部汇总
        var headlist= [];
        var flaglist= [];
        var jslist= [];
        var personMark= 0;
        $.each(alldata.data, function(i,content,fli) {
            personMark+= Number(content.personNum);
            headlist.push(i);
        });
        if(personMark == 0){
            error_info(true);
            return;                	
        }
        var flinum="";
        if(dfirst){
            if( Number(headlist[0].substr(-2,2))  > Number(headlist[1].substr(-2,2)) ){
                flaglist.reverse();
            }
            SD_left_navi(headlist,flaglist);
            $(".lin-historical-body-navigation .body_navig:first-child").addClass("current");
        }
        /*
        setTimeout(function(){
            if(data.data[fli].personNum ==0 && $(".lin-historical-body-navigation .body_navig:last-child").text() != fli){
                $(".lin-historical-body-navigation .body_navig:last-child").click();
            }
        },100)
        */
        jslist = SD_fill_hbDetail(fli,alldata);
        //航段填充
        var SD_leg=[];
        $.each(alldata.data,function(i,content){
            if(i==fli){
                $.each(content.segmentInfoList,function(ii,ccontent){
                    SD_leg.push(ccontent.leg);
                    oPicker.append("<li tag='"+ ccontent.flag +"'><p>航段<br><span>" + ccontent.leg +"</span></p></li>");
                })
            }
        })
        
        $("#SD-table-head ul span").addClass("hd");
        leg_list = SD_leg[0];
        //每次显示第一个航段的详情信息
        fill_table_xq(current_Flight,flight_pre_text,SD_leg[0],1);   
        open_headchoose();
        default_table();
    }
    
    //获取航线数据并填充
    function SD_fill_hz(fli,pre,ds,de,dfirst){
        var oTitle = $(".sumDetail");    //航班汇总
        var tttxt = newflight_pre_text==''? pre : newflight_pre_text;
        var Table_title= [];
        $.ajax({
           url: "/SalesData/findSalesData",
           type: "get",
           data:{
           		"fltRteCd": tttxt,
           		"endTime" : de,
           		"startTime" : ds,
           		"flightNum" : fli,
           		"rows": "35"
           },
           dataType: "json",
        })
        .done(function(data) {
            if(data.success == true){
                handleSalesData(data,dfirst,fli);
            }
            else{
                console.log("没得数据");
            }
        })
        .fail(function() {
            console.log("服务器异常，请稍后重试！");
        })
        .always(function() {
            // body...
        })
    }

    //默认显示第一个的数据
    function default_table(){       
        let name = $("#SD-table-head ul li:first-child").text().replace("航段","");
        let mark=current_Flight;
        SD_fill_table_data(name,mark);
    }

    //航段选择事件
    function open_headchoose(){
        var Table_picker=$("#SD-table-head ul li");
        $("#SD-table-head ul li:first").addClass("chooseSDLi");
        $("#SD-table-head ul li:first").css("opacity","1");
        $("#SD-table-head ul li:first").css("background","linear-gradient(to right, #1c436a , #3c4880)").siblings().css("background","#13233e");
        Table_picker.bind("click",function() {

            if($(this).hasClass("chooseSDLi")){
                return false;
            }
            else{
                if($(this).attr("tag")=="true"){
                    var name=$(this).text().replace("航段",""); 
                    leg_list=name;
                    SD_fill_table_data(name,current_Flight);
                    $(this).addClass("chooseSDLi").siblings().removeClass("chooseSDLi");
                    $(this).css("opacity","1").siblings().css("opacity","0.7");
                    $(this).css("background","linear-gradient(to right, #1c436a , #3c4880)").siblings().css("background","#13233e");
                    fill_table_xq(current_Flight,flight_pre_text,name,1);

                    
                }
                else{
                    SD_hd_null();
                }
            }
        });     
    }
    
    
    
    //汇总标题，汇总舱位    填充，选中更新
    function SD_fill_table_data(preFlyName,fli){
        fill_Minlist(salesDatas);//填充航段汇总信息
        //航段汇总标题
        function fill_Minlist(alldata){
            let detail_list=[];
            let cw_hong={};
            tag=0;
            //提取数据
            $.each(alldata.data, function(i,content) {
                //取出当前航班的数据
                if(i==fli){
                    $.each(content.segmentInfoList,function(ii,ccontent){
                        if(ccontent.leg==preFlyName && ccontent!==null){
                            cw_hong.data=ccontent;
                            $.each(ccontent,function(iii, cccontent) {
                                if(iii=="travellerTicketInfoList"){
                                }
                                detail_list[tag]=cccontent;
                                tag++;
                            });
                        }
                    })
                }
            });        
            fill_table_cw(cw_hong);
            detail_list = shapePre(detail_list);
            //格式化，删除无用元素
            function shapePre(list){
                list.splice(0,1);
                list.pop();
                list.pop();
                return list;
            }
            //填充航段汇总标题
            $.each($("#SD-min-list li span"),function(index,content){
                $(this).text(toThousands(detail_list[index]));
            })            
            let pagetxt=$("#personNum span").text().replace(/,/g,"");
            let totalPage=Math.ceil(pagetxt/35);
            SD_dopages(totalPage,pagetxt);
        }

        //航段舱位表格
        function fill_table_cw(detail){
            $("#SD-sum-tableA td").remove();
            $("#SD-sum-tableB td").remove();
            var SD_sum_table_trAa=$("#SD-sum-tableA tr")[0];
            var SD_sum_table_trAb=$("#SD-sum-tableA tr")[1];
            var SD_sum_table_trAc=$("#SD-sum-tableA tr")[2];
            var sum_table={};
            sum_table.personNum = [];
            sum_table.ticketPri = [];
            sum_table.frtSpe = [];
            if(detail.data){
            	error_info(false);
                $.each(detail.data.spaceInfoList,function(i, content) {
                    sum_table.personNum[i]=content.personNum;
                    sum_table.ticketPri[i]=content.ticketPri;
                    sum_table.frtSpe[i]=content.frtSpe;
                });
            }
            else{
            }
            var colsNum=0;
            var colsNumW=0;
            
            $(".SD-table-sum").find(".sum-table").eq(0).siblings().remove();
            var SD_tableNum=Math.ceil(sum_table.personNum.length/8);
            var loga=1;
            for(let a=1;a<SD_tableNum;a++){
                $(".SD-table-sum").append($(".SD-table-sum .sum-table").first().clone());
            }
            
            if(sum_table.personNum.length){                
                for(let a=0;a<8;a++){
                    if(a>sum_table.personNum.length-1){
                        break;
                    }                    
                    if(sum_table.ticketPri[a]==null||sum_table.ticketPri[a]==""){sum_table.ticketPri[a]='-'};
                    $(".SD-table-sum").find(".sum-table").eq(0).find("tr").eq(0).append("<td>"+sum_table.frtSpe[a]+"</td>");
                    
                    $(".SD-table-sum").find(".sum-table").eq(0).find("tr").eq(1).append("<td>"+toThousands(sum_table.personNum[a])+"</td>");
                    $(".SD-table-sum").find(".sum-table").eq(0).find("tr").eq(2).append("<td>"+toThousands(sum_table.ticketPri[a])+"</td>"); 
                }
                if(sum_table.personNum.length > 8){                
                    for(let a=8;a<16;a++){
                        if(a>sum_table.personNum.length-1){
                            break;
                        }
                        if(sum_table.ticketPri[a]==null||sum_table.ticketPri[a]==""){sum_table.ticketPri[a]='-'};
                        $(".SD-table-sum").find(".sum-table").eq(1).find("tr").eq(0).append("<td>"+sum_table.frtSpe[a]+"</td>");
                        $(".SD-table-sum").find(".sum-table").eq(1).find("tr").eq(1).append("<td>"+toThousands(sum_table.personNum[a])+"</td>");
                        $(".SD-table-sum").find(".sum-table").eq(1).find("tr").eq(2).append("<td>"+toThousands(sum_table.ticketPri[a])+"</td>"); 
                    }
                    if(sum_table.personNum.length > 15){                
                        for(let a=16;a<24;a++){
                            if(a>sum_table.personNum.length-1){
                                break;
                            }
                            if(sum_table.ticketPri[a]==null||sum_table.ticketPri[a]==""){sum_table.ticketPri[a]='-'};
                            $(".SD-table-sum").find(".sum-table").eq(2).find("tr").eq(0).append("<td>"+sum_table.frtSpe[a]+"</td>");
                            $(".SD-table-sum").find(".sum-table").eq(2).find("tr").eq(1).append("<td>"+toThousands(sum_table.personNum[a])+"</td>");
                            $(".SD-table-sum").find(".sum-table").eq(2).find("tr").eq(2).append("<td>"+toThousands(sum_table.ticketPri[a])+"</td>"); 
                        }
                        if(sum_table.personNum.length > 23){                
                            for(let a=24;a<32;a++){
                                if(a>sum_table.personNum.length-1){
                                    break;
                                }
                                if(sum_table.ticketPri[a]==null||sum_table.ticketPri[a]==""){sum_table.ticketPri[a]='/'};
                                $(".SD-table-sum").find(".sum-table").eq(3).find("tr").eq(0).append("<td>"+sum_table.frtSpe[a]+"</td>");
                                $(".SD-table-sum").find(".sum-table").eq(3).find("tr").eq(1).append("<td>"+toThousands(sum_table.personNum[a])+"</td>");
                                $(".SD-table-sum").find(".sum-table").eq(3).find("tr").eq(2).append("<td>"+toThousands(sum_table.ticketPri[a])+"</td>"); 
                            }
                            }
                    }
                }
            }
            creatReapt(sum_table.personNum.length);
        }

    }
    
    //航段详情表格  新增
    function fill_table_xq(fli,flt,leg,dopage,doorder,dosort){
        var detail={};
        if(doorder==null) doorder="flightDate";
        if(dosort==null) dosort="desc";
        var tttxt = newflight_pre_text==''?flight_pre_text:newflight_pre_text;
        $.ajax({
            url: '/SalesData/findTicketInfo',
            type: 'get',
            dataType: 'json',
            data: {
                flightNum: fli,
                fltRteCd: tttxt,
                leg: leg,
                endTime: end_time,
                startTime: start_time,
                order: doorder,
                sort: dosort,
                rows:'35',
                page: dopage
                }
        })
        .done(function(detail) {
        	$("#dataLoading").hide('normal');
            $("#SD-detail-table").empty();
            if(detail.data!=null){
                $("#SD-detail-table").append("<tbody id=\"detail_container\"></tbody");
                $.each(detail.data,function(i,content) {
                //外层添加行，内层单元格                           
                    $("#SD-detail-table tbody").append("<tr></tr>")
                    $.each(content,function(a,ell){
                    	 if(a=="ticketPri"){
                    		 ell=toThousands(ell);
                    	 }
                         $("<td>"+ ell +"</td>").appendTo($("#SD-detail-table tr")[i]);
                    })
                    $("#SD-detail-table").append("</tr>")        
                })
            }
            else{
                $("#SD-detail-table").append("<tr><td>暂无数据</td></tr>");
            }
        })
        .fail(function() {
            console.log("error");
        })
    }
    
    
    function fliListHandler(list,Flight,ts,te){
        var Fligh_list=[];
        $.each(list,function(index, el) {           
            Fligh_list.push(el);
        });
        $.each(Fligh_list,function(i,el){
            if(el.length==flight_pre_text.length){
                flight_pre_text=el;
                $(".SD-set-list-title").attr('tag',el);
            }
        })
        if(Fligh_list.length==0){
            if(Number(current_Flight.substring(3)) % 2 === 0){
                setTimeout(function(){
                    $('.body_navig').eq(1).attr("flag","false").addClass("TMnodata-div");
                },800)
            }else{
                setTimeout(function(){
                    $('.body_navig').eq(0).attr("flag","false").addClass("TMnodata-div");
                },800)
            }
            return error_info(true);
            if(current_Flight == Flight){
                var newfli='';
                if(Number(Flight.substr(-2))%2 == 0){
                    newfli= Flight.substring(0,Flight.length-2) + (Number(Flight.substr(-2))-1);
                } else {
                    newfli= Flight.substring(0,Flight.length-2) + (Number(Flight.substr(-2))+1);
                }
                getFliList(newfli,ts,te);
                $('.body_navig').eq(0).attr("flag","false").addClass("TMnodata-div");
            }else{
                error_info(true);
            }
        }
        else{
            current_Flight= Flight;
            flight_txt=Fligh_list[0];
            createFlist(Fligh_list);
        }
    }
    //获取航线列表
    function getFliList(Flight,timeSS,timeEE){
        var _list;
        $.ajax({
            url: "/SalesData/findFltRteCdList",
            type: "get",
            async: false,
            dataType: "json",
            data: {
                endTime : timeEE, 
                startTime : timeSS,
                flightNum : Flight,                
            },
        })
        .done(function(list) {
            fliListHandler(list,Flight,timeSS,timeEE);
            _list = list;
        })
        .fail(function() {
            console.log("获取航段失败");
        })
        return _list
    }
    

    
    var another_fli= function(fli){
    	var newfli='';
    	if(Number(fli.substr(-2))%2 == 0){
    		newfli= fli.substring(0,fli.length-2) + (Number(fli.substr(-2))-1);
    	} else {
    		newfli= fli.substring(0,fli.length-2) + (Number(fli.substr(-2))+1);
    	}
    	return newfli;
    }
    
    function createFlist(list){
    	$(".SD-airportNam ul").empty();
    	for ( let i =0 ; i < list.length ;i++){
    		
        	$(".SD-airportNam ul").append("<li tag=" + list[i] + ">" + formatToZN(list[i]) + "</li>");
    	}
    	$(".SD-airportNam ul li").bind("click",function(){
    		$("#sr-box-head-inquire .SD-set-list-title").text($(this).text());    		
    		$('.SD-set-list-title').attr("tag",$(this).attr("tag"));
    		flight_pre_text = $(this).attr("tag");
    	})
    }
    
    
    function formatToZN(iataList){	//三字码转机场名
    	if(iataList){
    		let newlist=[];
    		for(let i = 0 ; i < iataList.length; i+=3){
    			let a= parent.airportMap[iataList.substr(i,3)].aptChaNam;
    			newlist.push(a);
    		}
    		return newlist.join("=");
    	}
    	else{
    		return "---";
    	}
    }
    
    
    
    //格式化航班号,左侧导航
    function fliNum_shape(fliNum){
        if ( fliNum.length >0 ){
            let a= fliNum[0].length;
            fliNum[1]=fliNum[0].substring(0,a-2)+fliNum[1];
            for(let i=0;i<fliNum.length;i++){
                $(".lin-historical-body-navigation").append('<div class="body_navig" tag="hong"><span>'+ fliNum[i] +'</span></div>');       
            }
            $(".body_navig:first-child").addClass("current");
        }
        else{
            console.log("无航班数据！");
        }
        //左侧导航
        return fliNum;
    }
    
    
    //清空
    function SD_clear_all(){
    	$("#dataLoading").show();
        $("#SD-head-title span.sumDetail").text("");    //航班汇总
        $("#SD-table-head ul").empty(); //航段选择器        
        $("#SD-min-list span").empty(); //清空航段汇总    
        $("#SD-sum-tableA td").remove();    //仓位清空
        $("#SD-detail-table").empty();  //详细清空
    }
    
    
//------------------------------------------顶部查询栏目
    $('#reservation').daterangepicker();
    $('.set-month-back').hide();
    //航班号change
    $("#sr-box-head-inquire #SD-head-inquire").bind("input propertychange", function() { 
        if($(this).val()){
        	SD_get_portName($(this).val());
        }
    });

    //航线下拉框
    $(".SD-set-list-title").bind("click",function(){
    	$(".SD-airportNam").toggle();
    	$("body").click(function (e) {
    	    if (!$(e.target).closest(".SD-set-list-title").length) {
    	    	$(".SD-airportNam").hide(); 
    	    }
    	});
    })
        //查询按钮
    $("#sr-box-head-inquire div._set-query").bind("click",function(){
    	newflight_pre_text = flight_pre_text;
    	let fli=$("#sr-box-head-inquire #SD-head-inquire").val();
    	//检测是否正在显示所查询的航班数据
        var a = $("#reservation").val().split(" - ");
        if(fli){
        	let flag=false;
        	if(fli!=current_Flight){
        		flag=true;
        	}
        	if(a==""||a==null){
        		alert("请选择起止日期!")
        		return false;
        	}
        	if(true){
                start_time=a[0];
                end_time=a[1];
                let sdate=new Date(a[0]);
                let edate=new Date(a[1]);
                let datee=new Date();
                if($(".SD-set-list-title").text()==""){
                	alert("没有相关航线");
                	$("#sr-box-head-inquire #SD-head-inquire").val("")
                	parent.supData.flight=parent.supData.lane = "";
                	error_info(true);

                	$("#dataLoading").hide('normal');
                	$(".err").show();
                }
                else if(sdate<=datee && edate<=datee && sdate<=edate){
                	$("#SD-cover").hide();
                    $(".err").hide();
                    SD_clear_all();
                    current_Flight=$("#sr-box-head-inquire #SD-head-inquire").val();
                    //获取航段，填充左侧导航，填充数据
                    flight_pre_text = newflight_pre_text;
                    getFliList(current_Flight,start_time,end_time);
                    SD_fill_hz(current_Flight,newflight_pre_text,start_time,end_time,true);
                    SD_fill_table_data(newflight_pre_text,current_Flight);
                }
                else{
                    alert("起始日期不能大于当前日期。")
    	        	
                	error_info(true);
                    return false;
                };        		
        	}
        	else{
        		return false;
        	}
        }
        else{
        	error_info(true);        	
            alert("请输入正确的日期和航班");
        	return false;            
        }
    })
    
    
    
    //航线自动填充
    function SD_get_portName(fli){
    	var a = $("#reservation").val().split(" - ");
        var cstart_time=a[0];
        var cend_time=a[1];
        var airports = parent.airportMap;
        $.ajax({
            url: '/SalesData/findFltRteCdList',
            type: 'get',
            dataType: 'json',
            data: {
                endTime : cend_time, 
                startTime : cstart_time,
                flightNum : fli,
                }
        })
        .done(function(data) {
            if(data){
            	if(data.length==1){
                	if(data.join("").length>3){
                		let a=[];
                		let c=[];
                		let b=data.join("").length/3;
                		let str=data.join("");
                		let j=0;
                		for(let i=0;i<b;i++){
                			a[i]=str.substring(i*3,(i+1)*3);
                			c[i]=airports[a[i]].aptChaNam;
                		}
                    	$(".SD-set-list-title").empty();
                		$(".SD-set-list-title").text(c.join("="));
                	}
            	}
            	else if(data.length>1){
            		//console.log(data);
/*            		for(let a=0;a<data.length;a++){
                		let q=[];
                		let c=[];
                		let b=data.join("").length/3;
                		let str=data.join("");
                		let j=0;
                		for(let i=0;i<b;i++){
                			q[i]=str.substring(i*3,(i+1)*3);
                			c[i]=airports[a[i]].aptChaNam;
                		}
                		console.log(q,c);
            		}*/
            	}
            }
        })
        .fail(function() {
        })
    }

    
    
//------------------------------------------通用工具
    
    //今天日期
    function SD_getNowDate(){
        let SD_date=new Date();
        let y=SD_date.getFullYear();
        let m=SD_date.getMonth()+1;
        let d=SD_date.getDate();
        if(m<10) m='0'+ m;
        if(d<10) d='0'+ d;
        let Now_date=y + '-' + m + '-' + d;
        return Now_date;
    }
    //获取三十天前日期
    function SD_getDate_30 (currDate) {
        var myDate = new Date(currDate);
        var lw = new Date(myDate - 1000 * 60 * 60 * 24 * 30); //最后一个数字表示天数
        var lastY = lw.getFullYear();
        var lastM = lw.getMonth()+1;
        var lastD = lw.getDate();
        var startdate=lastY+"-"+(lastM<10 ? "0" + lastM : lastM)+"-"+(lastD<10 ? "0"+ lastD : lastD);//30天之前日期
        return startdate;
    }
    //获取62天前日期
    function SD_getDate_62 (currDate) {
        var myDate = new Date(currDate);
        var lw = new Date(myDate - 1000 * 60 * 60 * 24 * 62); //最后一个数字表示天数
        var lastY = lw.getFullYear();
        var lastM = lw.getMonth()+1;
        var lastD = lw.getDate();
        var startdate=lastY+"-"+(lastM<10 ? "0" + lastM : lastM)+"-"+(lastD<10 ? "0"+ lastD : lastD);//30天之前日期
        return startdate;
    }
    
    function toEnName(nl){	//中文获取三字码
    	let name="";
    	for (let a=0 ; a<nl.length;a++){    		
    		name+= parent.airportMap[nl[a]].iata ;
    	}
    	return name;
    }
    
    //获取最新有数据的日期
    function SD_getCurrent_date(fli,fname){
    	//判断从哪里来（功能间的切换）
    	var startTime = parent.supData.startTime;
    	var endTime = parent.supData.endTime;
    	if(typeof(endTime) != "undefined"){
    		$("#reservation").val(startTime + " - " + endTime);
    		$(".SD-set-list-title").empty();
	        $(".SD-set-list-title").text(parent.supData.lane);
            var preList = getFliList(current_Flight,startTime,endTime);
            preList = preList===flight_pre_text?flight_pre_text:preList;
            
	        SD_fill_hz(current_Flight,flight_pre_text,startTime,endTime,true);
	        return false;
    	}else{
	        var hhh='';
	        $.ajax({
	            url: '/SalesData/getCurrentTime',
	            type: 'get',
	            async: false,
	            dataType: 'JSON',
	            data: {
	            	flightNum: fli,
	            	fltRteCd:toEnName(fname.split("="))
            	}
	        })
	        .done(function(getdate) {
	        	if(!$.isEmptyObject(getdate)){
	                hhh = getdate.data;
	        	}
	        	else{
	        		return;
	        	}
	        })
	        .fail(function() {
	            console.log("error");
	        })    
	        return hhh;
    	}
    }

    //转换为千分位数字
    function toThousands(num) {
        return (num || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
    }
    
    //航段无数据动画
    function SD_hd_null(){
        $("div.SD-hd-null").show();
        setTimeout(function(){
            $(".SD-hd-null").hide();
        }
        ,2000)
    }
    
    //滚动条监听    
    $('.SD-src-main').on('scroll',function(){
        let oWidth=$("#SD-detail-table").css("width");
        if($("#SD-detail-table").offset().top<70){
            $(".back-to-top").show();
            $("#detail-table-head").addClass("headhhhh");
            $("#detail-table-head").css("width",oWidth);
        }
        else{
            $(".back-to-top").hide();
            $("#detail-table-head").removeClass("headhhhh");
        }
    });
    
    //回到顶部按钮
    $(".back-to-top").click(function() {
        $(".SD-src-main").animate({scrollTop:0}, 'fast');
    });

    // 导出按钮
    $(".SD-outer").click(function() {
    	//$(".SD-print-link").text("确认导出"+ current_Flight +"于"+ start_time + end_time + "的所有数据吗？" );
    	//$(".SD-print-link").toggle();
    	
        //默认导出所有航段
        let xlslink="/SalesData/exportSalesdata?";
        if($("#SD-detail-table td").text()=="暂无数据"){
            alert("暂无数据可供导出");
            
        }
        else{
            xlslink+="flightNum=" + current_Flight + "&fltRteCd=" + flight_pre_text + "&endTime=" + end_time +"&startTime="+start_time ;
            $(this).attr("href",xlslink)
        }
    })    

    //无数据时
    function error_info(flag){
    	if(flag){
    		$(".SD-src-main").addClass("blur");
        	$("#dataLoading").hide('normal');
            $(".err").show();
    	}
    	else{
    		$(".SD-src-main").removeClass("blur");
            $(".err").hide();    		
    	}
    }

    //创建重复汇总表格
    function creatReapt(alltd){
        let tablewidth=Number($(".SD-table-sum #SD-sum-tableA").css("width")); 
        let lasttb = $(".SD-table-sum").find(".sum-table").last().find("tr:first td").length + 1;
        var widdd=0;
        if(alltd){
            if(alltd>8){
            	let a =Number(alltd);
                widdd = (a % 8 + 1)/9;
                if(widdd<0.2)widdd=1;
                widdd = widdd*100 + "%";
                $(".SD-table-sum").find(".sum-table").last().css("width",widdd);
            }
        }
    }
    
    
    
  //--------------------------------------------------分页功能
    function SD_dopages(sumpages,dataNum,source,type,dosort){
    	$('#nav-long').empty();
    	setTimeout(function(){
			if( $('#detail_container tr').length <2 ){
				sumpages=1;	
			}
			else{
		    	//显示分页
	    	        laypage({
	    	            cont: 'nav-long', 
	    	            pages: sumpages, 
	    	            curr:  1, 
	    	            first:1,
	    	            last: sumpages,
	    	            prev:'＜',
	    	            next:'＞',
	    	            jump: function(obj, first){ //分页回调 
	    	            	if(first){}            	
	    	            	else if(dosort){
	    	                	fill_table_xq(current_Flight,flight_pre_text,leg_list,obj.curr,source,type);
	    	                	$(".SD-src-main").animate({scrollTop:580}, 'fast');
	    	            		
	    	            	}
	    	            	else{
	    	                	fill_table_xq(current_Flight,flight_pre_text,leg_list,obj.curr);
	    	                	$(".SD-src-main").animate({scrollTop:580}, 'fast');
                            }
	    	            	table_foot(obj.curr,obj.last,Number(dataNum));
	    	            }
	    	          });
			}
    	},500);
    	
    }
    
    //底部填充
    function table_foot(indexPage,totalPage,num){
            if(totalPage>1){
                $(".nav-startTr").text(indexPage*35-34);                
                $(".nav-endTr").text(indexPage*35);
                if(indexPage*35 > num) $(".nav-endTr").text(num);
                $(".nav-totalTr").text(num);
            }
            else{       	
                $(".nav-startTr").text("");
                $(".nav-endTr").text("");
                $(".nav-totalTr").text(""); 
            }
            let curdatas=$("#personNum>span").text();
            if(curdatas<36 && curdatas>0){
                $(".nav-startTr").text(1);
                $(".nav-endTr").text(curdatas);
                $(".nav-totalTr").text(curdatas);
            }
    }

    
  //--------------------------------------------------排序功能
    
    //详情表格排序功能1 
    SD_table_sort();
    function SD_table_sort(){    
        //航班日期
        var a=true;
        $("#detail-table-head #date_sort").click(function(){
                var source="flightDate";        
                var sortType="desc";
                if(a){
                    sortType="asc";
                    $(this).text("航班日期        ↑");              
                }
                else{
                    sortType="desc";
                    $(this).text("航班日期        ↓");     
                }
                a=!a;
                click_sort(source,sortType);            
        })
        //舱位
        $("#detail-table-head #cw_sort").click(function(){
            var source="frtSpe";        
            var sortType="desc";
            if(a){
                sortType="desc";
                $(this).text("舱位        ↓");                    
            }
            else{
                sortType="asc";
                $(this).text("舱位        ↑"); 
            }
            a=!a;
            click_sort(source,sortType);
            
        })
        //票款
        var b=true;
        $("#detail-table-head #price_sort").click(function(){
            var source="ticketPri";
            var sortType="desc";
            if(b){
                sortType="desc";
                    $(this).text("票款        ↓"); 
            }
            else{
                sortType="asc";
                $(this).text("票款        ↑"); 
            }
            b=!b;
            click_sort(source,sortType);
        })
        function click_sort(source,type){
            if($("#SD-detail-table tr").length>2){

                SD_detail_sort(source,type);
            }
            else{
                return false;
            }
        }
    
    }
    //排序，请求数据
    function SD_detail_sort(source,type){
    	//默认一页数据
    	fill_table_xq(current_Flight,flight_pre_text,leg_list,1,source,type);
    	//重分页
        let pagetxt=$("#personNum span").text().replace(/,/g,"");
        let totalPage=Math.ceil(pagetxt/35);
    	SD_dopages(totalPage,pagetxt,source,type,true);
    }
    
    //详情表格填充函数
    //fill_table_xq(current_Flight,flight_pre_text,leg_list,2);
    
    
    //将值传入其他页面
    function SD_sendp(li){    	
    	li = li.sort();
    	let txt=li[0]+'/'+li[1].substr(-2,2);
    	parent.supData.flight=txt;
    	parent.supData.lane=$('.SD-set-list-title').text();
    	//时间保存起来，用于其它功能
    	parent.supData.startTime = start_time;
    	parent.supData.endTime = end_time;
    }

    Date.prototype.format = function(format){
    	var o = { 
    		"M+" : this.getMonth()+1, //month 
    		"d+" : this.getDate(), //day 
    		"h+" : this.getHours(), //hour 
    		"m+" : this.getMinutes(), //minute 
    		"s+" : this.getSeconds(), //second 
    		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
    		"S" : this.getMilliseconds() //millisecond 
    	};
    }
    	
    	
    	
    //重新填充左侧航班号
    function SD_left_navi(list,flist){
        $(".lin-historical-body-navigation").empty();
        for(let i=0;i<list.length;i++){
            $(".lin-historical-body-navigation").append('<div class="body_navig" flag='+flist[i] +'><span>'+ list[i] +'</span></div>');  
            continue;
        	if(flist[i]=='true'){
                $(".lin-historical-body-navigation").append('<div class="body_navig" flag='+flist[i] +'><span>'+ list[i] +'</span></div>');  
        	}
        	else{
                $(".lin-historical-body-navigation").append('<div class="body_navig TMnodata-div" flag='+flist[i] +'><span>'+ list[i] +'</span></div>');  
        	}     
        };
        SD_sendp(list);
        //调整默认为用户输入的航班号
    	if($(".body_navig:first-child span").text()==$("#SD-head-inquire").val()){
    		//一切正常，顺序正常
    	}
    	else{
    		$(".body_navig:last-child span").text($(".body_navig:first-child span").text());
    		$(".body_navig:first-child span").text($("#SD-head-inquire").val());
    	}
        var thisflag=true;
        
        //左侧导航点击
        $(".body_navig").on("click",function(){
        	if($(this).attr("flag")=="false"){
        		$("div.SD-hd-null p").text("暂无该航班数据！");
                $("div.SD-hd-null").show();
                setTimeout(function(){
                	$("div.SD-hd-null p").text("暂无该航段数据！");
                    $(".SD-hd-null").hide();
                },2000)
        		return $(this).addClass("TMnodata-div");
        	}
        	else if($(this).hasClass("current")){
                console.log("正在显示该航班信息");
            }else{
                if(thisflag){
                    thisflag= false;
                    $("input#SD-head-inquire").val($(this).text());
                    SD_get_portName($(this).text());
                    $(this).siblings().removeClass("current");   
                    $(this).addClass("current");      
                    SD_clear_all();
                    current_Flight=$(this).text();
                    //填充航班汇总
                    getFliList(current_Flight,start_time,end_time); //改变航班对应总航段
                    SD_fill_hz(current_Flight,flight_pre_text,start_time,end_time,false); //填充数据
                    
                    setTimeout(function(){
                        thisflag=true;
                        // error_info(false);
                    },4000);
                    
                }
                else{
                    console.log("请勿重复点击,会卡！");
                }   
            }
        })  
        
    }
    
    $(".daterangepicker button.applyBtn").click(function(){
    	setTimeout(function(){
        	let timeList= $("#reservation").val();
        	let ta = timeList.split(" - ")[0],tb = timeList.split(" - ")[1];
        	if(daysBetween(ta,tb) > 61){
        		alert("所选择的日期范围超过2个月！");
        		$("#reservation").val(start_time + ' - ' + end_time);
        		timeList= $("#reservation").val();
        		ta = timeList.split(" - ")[0];
        		tb = timeList.split(" - ")[1];
        	}
        	getFliList(current_Flight,ta,tb);    		
    	},100)
    })    

    function daysBetween(sDate1,sDate2){	//判断两个日期相差的天数
    	//Date.parse() 解析一个日期时间字符串，并返回1970/1/1 午夜距离该日期时间的毫秒数
    	var time1 = Date.parse(new Date(sDate1));
    	var time2 = Date.parse(new Date(sDate2));
    	var nDays = Math.abs(parseInt((time2 - time1)/1000/3600/24));
    	return nDays;
    };
})