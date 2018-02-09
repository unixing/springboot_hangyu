//组装查询参数
 function getSearchJosn(){
	 var searchJson = new Object();
		var object = parent.supData;
		var flight = object.flight;
		var lane = object.lane;
		var dpt = flight.split("/"); 
		if($("#inputNum").val()!=""){
			searchJson.flt_nbr_Count = $("#inputNum").val();
		}else{
			searchJson.flt_nbr_Count = dpt[0];
		}
		var aa = $("#startEndDate").val().split(" - ");
		var nowdate=new Date();
		searchJson.startTime = aa[0];
		searchJson.endTime = aa[1];
		return searchJson;
 }
 //日期加减函数
 function addDate(date,days){ 
 	var d=new Date(date); 
 	d.setDate(d.getDate()+days); 
 	var month=d.getMonth()+1; 
 	var day = d.getDate(); 
 	if(month<10){ 
 		month = "0"+month; 
 	} 
 	if(day<10){ 
 		day = "0"+day; 
 	} 
 	var val = d.getFullYear()+"-"+month+"-"+day; 
 	return val; 
 }
$(function(){
	changeDate('startEndDate');
	$('#startEndDate').daterangepicker(null, function(start, end, label) {});
	//为查询按钮绑定事件
	$(".sr-box-head-btn").click(function(){
		send();
	}) ;
    /*测大小*/
    function infer(name){
        var infer=[];
        infer.push(parseFloat($(name).css("width").split("px")[0]));
        infer.push(parseFloat($(name).css("height").split("px")[0]));
        infer.push(parseFloat($(name).css("margin-top").split("px")[0]));
        infer.push(parseFloat($(name).css("left").split("px")[0]));
        return infer;
    }
    function box(){
        $(".lin-historical-body").eq(0).css({"height":infer(".lin-historical")[1]-infer(".sr-box-head")[1]+"px"});//计算内容区的高度
        $(".body-navigation-element").css({"height":infer(".lin-historical-body")[1]/parseInt($(".body-navigation-element").size())}); //计算每个导航块的大小
        for(var i=0;i<$(".tag-box").length;i++){//计算每个导航块的居中位置
            var tagH=(infer(".body-navigation-element")[1]-parseFloat($(".tag-box").eq(i).css("height").split("px")[0]))/2;
            $(".tag-box").eq(i).css({"margin-top":tagH+"px"});
        };
        var Lhbx=infer(".lin-historical-body")[0]-352;
        $(".lin-historical-body-box").css({"width":Lhbx+"px","height":infer(".lin-historical-body")[1]})//就按图区盒大小
        $(".element-lines-box").css({"height":infer(".lin-historical-body-box")[1]-infer(".lin-historical-body-box-titles")[1]});
        $(".lines-box").css({"width":infer(".element-lines-box")[0] *0.75+"px"});
        $(".gradient>div").css({"width":infer(".element-lines-box")[0] *0.75+"px"});
    }
   
    var ultimately = new Object();
    var fisrt = "";
    var hangxian = {};
    function send(){
	   	ultimately = null;
	   	ultimately = new Object();
        fisrt = "";
        $.ajax({
           type:'get',
           url:'/restful/getBuyTicketReportLineDataNew',
           data:getSearchJosn(),
           dataType : 'jsonp',
           success : function(data) {
       		var hangData = data.success.dataMap;
       		if(hangData!=null&&hangData!=""){
       			var index = 0;
           		for(var a in hangData){
           			if(index==0){
           				fisrt = a;
           			}
           			index++;
           			var data1 = hangData[a].list;
           			hangxian[a]= hangData[a].flyNum;
           			var temp1 = new Object();
           			for(var b in data1){
           				var datea = data1[b].fltNumDate
           				var str = datea.split("-");
           				var temp2 = new Object();
//               				//第-1天的数据
           				if("undefined"!=typeof(data1[b].progressf1)){
           					numf1 = data1[b].progressf1.split(",");
               				numf1_bfb = numf1[0];
               				numf1_zrs = numf1[1];
               				var numf1_array = new Array();
               				numf1_array.push(parseInt(numf1_bfb.replace("%","")));
               				numf1_array.push(parseInt(numf1_zrs));
               				temp2["-1"] = numf1_array;
           				}
           				if("undefined"!=typeof(data1[b].progress0)){
           					num0 = data1[b].progress0.split(",");
           					num0_bfb = num0[0];
           					num0_zrs = num0[1];
           					var num0_array = new Array();
           					num0_array.push(parseInt(num0_bfb.replace("%","")));
           					num0_array.push(parseInt(num0_zrs));
           					temp2["0"] = num0_array;
           				}
           				if("undefined"!=typeof(data1[b].progress1)){
           					num1 = data1[b].progress1.split(",");
           					num1_bfb = num1[0];
           					num1_zrs = num1[1];
           					var num1_array = new Array();
           					num1_array.push(parseInt(num1_bfb.replace("%","")));
           					num1_array.push(parseInt(num1_zrs));
           					temp2["1"] = num1_array;
           				}
           				if("undefined"!=typeof(data1[b].progress2)){
           					num2 = data1[b].progress2.split(",");
           					num2_bfb = num2[0];
           					num2_zrs = num2[1];
           					var num2_array = new Array();
           					num2_array.push(parseInt(num2_bfb.replace("%","")));
           					num2_array.push(parseInt(num2_zrs));
           					temp2["2"] = num2_array;
           				}
           				if("undefined"!=typeof(data1[b].progress3)){
           					num3 = data1[b].progress3.split(",");
           					num3_bfb = num3[0];
           					num3_zrs = num3[1];
           					var num3_array = new Array();
           					num3_array.push(parseInt(num3_bfb.replace("%","")));
           					num3_array.push(parseInt(num3_zrs));
           					temp2["3"] = num3_array;
           				}
           				if("undefined"!=typeof(data1[b].progress7)){
           					num7 = data1[b].progress7.split(",");
           					num7_bfb = num3[0];
           					num7_zrs = num3[1];
           					var num7_array = new Array();
           					num7_array.push(parseInt(num7_bfb.replace("%","")));
           					num7_array.push(parseInt(num7_zrs));
           					temp2["7"] = num7_array;
           				}
           				if("undefined"!=typeof(data1[b].progress15)){
           					num15 = data1[b].progress15.split(",");
           					num15_bfb = num15[0];
           					num15_zrs = num15[1];
           					var num15_array = new Array();
           					num15_array.push(parseInt(num15_bfb.replace("%","")));
           					num15_array.push(parseInt(num15_zrs));
           					temp2["15"] = num15_array;
           				}
           				if("undefined"!=typeof(data1[b].progress30)){
           					num30 = data1[b].progress30.split(",");
           					num30_bfb = num30[0];
           					num30_zrs = num30[1];
           					var num30_array = new Array();
           					num30_array.push(parseInt(num30_bfb.replace("%","")));
           					num30_array.push(parseInt(num30_zrs));
           					temp2["30"] = num30_array;
           				}
           				temp1[str[1]+"."+str[2]] = temp2;
           			}
           			ultimately[a] = temp1;
           		}
           		creatNav(ultimately);
                change(ultimately,fisrt); 
                initrightData(data);
       		}else{
       			alert("没有相关数据！");
       		}
            },
           error : function() {//FIXME  请求数据后此函数内容应放在success里面 -暂时做无求情数据测试

           }
          });
      }
    /*第一次请求数据*/
    send();
    var airports = parent.airportMap;
    function initrightData(data){
    	$("#inputNum").val(data.success.fltNumq);
    	$(".time-line-fli").html(data.success.fltNumq);
    	
    	$(".change-line-f").find("p").html(airports[data.success.dptName].iata);
    	$(".change-line-f").find("div").html(airports[data.success.dptName].aptChaNam);
    	
    	$(".change-line-o").find("p").html(airports[data.success.pstName].iata);
    	$(".change-line-o").find("div").html(airports[data.success.pstName].aptChaNam);
    	
    	$(".change-line-l").find("p").html(airports[data.success.arrName].iata);
    	$(".change-line-l").find("div").html(airports[data.success.arrName].aptChaNam);
    }
    function creatNav(data){
        /*创建左侧导航栏节点*/
        var ele="";
        for(var key in data){
            var nums=key.split("-");
            if(nums.length==3){
                ele+="<div class='body-navigation-element' abbr='"+nums[0]+"-"+nums[1]+"-"+nums[2]+"'><div class='tag-box'><div>"+nums[0]+"</div><div class='element-tag'>&#xe648;</div><div>"+nums[1]+"</div><div class='element-tag'>&#xe648;</div><div>"+nums[2]+"</div></div></div>"
            }else {
                ele+="<div class='body-navigation-element' abbr='"+nums[0]+"-"+nums[1]+"'><div class='tag-box'><div>"+nums[0]+"</div><div class='element-tag'>&#xe648;</div><div>"+nums[1]+"</div></div></div>";
            }
        }
        $(".lin-historical-body-navigation").html(ele);
        if(ele!=""){
        	$(".lin-historical-body-navigation").find("div").eq(0).addClass("set-liset");
        }
        
    }
    function change(data,path){
        /*创建内容节点*/
        var eleBox="";
        eleBox+="<div class='lin-historical-body-box-titles'><div>起飞日期</div><div>客座率/客量</div></div><div class='element-lines-box'>";
        for(var x in data[path]){
            eleBox+="<div class='element-lines'><p class='lines-title'>"+x+"</p><div class='lines-box'><div class='calibration'>";
            for(var t=0;t<=100;t++){
            	if(data[path][x]["-1"]&&t==data[path][x]["-1"][0]){
                    eleBox+="<div class='selected-date' abbr='"+data[path][x]["-1"][0]+"-"+data[path][x]["-1"][1]+"' tag='"+x+"' line='"+path+"'><p>"+-1+"</p></div>"
                }else if(data[path][x][0]&&t==data[path][x][0][0]){
                    eleBox+="<div class='selected-date' abbr='"+data[path][x][1][0]+"-"+data[path][x][1][1]+"' tag='"+x+"' line='"+path+"'><p>"+0+"</p></div>"
                }else if(data[path][x][1]&&t==data[path][x][1][0]){
                    eleBox+="<div class='selected-date' abbr='"+data[path][x][1][0]+"-"+data[path][x][1][1]+"' tag='"+x+"' line='"+path+"'><p>"+1+"</p></div>"
                }else if(data[path][x][2]&&t==data[path][x][2][0]){
                    eleBox+="<div class='selected-date' abbr='"+data[path][x][2][0]+"-"+data[path][x][2][1]+"' tag='"+x+"' line='"+path+"'><p>"+2+"</p></div>"
                }else if(data[path][x][3]&&t==data[path][x][3][0]){
                    eleBox+="<div class='selected-date' abbr='"+data[path][x][3][0]+"-"+data[path][x][3][1]+"' tag='"+x+"' line='"+path+"'><p>"+3+"</p></div>"
                }else if(data[path][x][7]&&t==data[path][x][7][0]){
                    eleBox+="<div class='selected-date' abbr='"+data[path][x][7][0]+"-"+data[path][x][7][1]+"' tag='"+x+"' line='"+path+"'><p>"+7+"</p></div>"
                }else if(data[path][x][15]&&t==data[path][x][15][0]){
                    eleBox+="<div class='selected-date' abbr='"+data[path][x][15][0]+"-"+data[path][x][15][1]+"' tag='"+x+"' line='"+path+"'><p>"+15+"</p></div>"
                }else if(data[path][x][30]&&t==data[path][x][30][0]){
                    eleBox+="<div class='selected-date' abbr='"+data[path][x][30][0]+"-"+data[path][x][30][1]+"' tag='"+x+"' line='"+path+"'><p>"+30+"</p></div>"
                }else {
                    eleBox+="<div></div>"
                }
            }
            eleBox+="</div><div class='gradient'><div></div></div><div class='air-taimei' abbr='"+x+"'>&#xe637;</div><div class='difference'></div></div><div class='describe'><p>100%</p><div>距起飞天数</div></div></div>";
        }
        $(".lin-historical-body-box").html(eleBox);
        /*上刻度线*/
        for(var i=0;i< $(".calibration").length;i++){
            for(var j=0;j<=100;j++){
                $(".calibration").eq(i).children().eq(j).css({"left":j+"%"});
            }
        }
        /*修改渐变长度和飞机位置*/
        for(var i=0;i< $(".air-taimei").length;i++){
            var keys=0;
            var abbrs=$(".air-taimei").eq(i).attr("abbr");
            var nu="";
            for(var j in data[path][abbrs]){
                if(data[path][abbrs][j][0]>=keys&&j!="-1"){
                    nu=j;
                    keys=data[path][abbrs][j][0];
                }else if(j=="-1"){
                    nu=j;
                }
            }
            $(".air-taimei").eq(i).css({"left":data[path][abbrs][nu][0]+"%"});
            $(".gradient").eq(i).css({"width":data[path][abbrs][nu][0]+"%"});
        }

        /*已经起飞-1*/
        for(var key in data[path]){
            if(data[path][key]["-1"]){
                var initial=data[path][key]["-1"][0];
                var t="";
                if(data[path][key]["0"]!=undefined&&data[path][key]["0"]!=""){
                    t=data[path][key]["0"][0];
                }else if(data[path][key]["1"]!=undefined&&data[path][key]["1"]!=""){
                    t=data[path][key]["1"][0];
                }else if(data[path][key]["2"]!=undefined&&data[path][key]["2"]!=""){
                    t=data[path][key]["2"][0];
                }else if(data[path][key]["3"]!=undefined&&data[path][key]["3"]!=""){
                    t=data[path][key]["3"][0];
                }else if(data[path][key]["7"]!=undefined&&data[path][key]["7"]!=""){
                    t=data[path][key]["7"][0];
                }else if(data[path][key]["15"]!=undefined&&data[path][key]["15"]!=""){
                    t=data[path][key]["15"][0];
                }else if(data[path][key]["30"]!=undefined&&data[path][key]["30"]!=""){
                    t=data[path][key]["30"][0];
                }
                for(var x=0;x<$(".air-taimei").length;x++){
                    if($(".air-taimei").eq(x).attr("abbr")==key){
                        $(".air-taimei").eq(x).next().css({"left":initial+"%","width":(t-initial)+"%"});
                    }
                }

            }
        };
        box();
    }
    $(".lin-historical-body-navigation").on("click","div",function(){
        if($(this).attr("class")=="body-navigation-element"){
            $(this).addClass("set-liset").siblings().removeClass("set-liset");
            change(ultimately,$(this).attr("abbr"))
            //改变右边的航班号
            var a = hangxian[$(this).attr("abbr")];
            $(".time-line-fli").html(a);
            $("#inputNum").val(a);	
        }
    });
    $(".lin-historical-body-box").on("mouseover","div",function(){
        if($(this).attr("class")=="selected-date"){
            if($(this).children().length==1){
            	var tem="";
                for(var u in ultimately[$(this).attr("line")][$(this).attr("tag")]){
                    if(ultimately[$(this).attr("line")][$(this).attr("tag")][u][0]==$(this).attr("abbr").split("-")[0]){
                        tem+=u+","
                    }
                }
                $(this).append("<div class='remove'>距离起飞"+ tem+"天"+$(this).attr("abbr").split("-")[0]+"%/"+$(this).attr("abbr").split("-")[1]+"人</div>")
            }
        }
    });
    $(".lin-historical-body-box").on("mouseout","div",function(){
        if($(this).attr("class")=="selected-date"){
            if($(this).children().length>1){
                $(".remove").remove();
            }
        }
    });
    window.onresize=function(){
        box();
    }
    function changeDate(id){
    	// 参数表示在当前日期下要增加的天数  
        var now = new Date();
        var startTime = addDate(now,-1);
        var endTime = addDate(now,7);
        $('#'+id).val(startTime +' - '+ endTime);
    }
});



















