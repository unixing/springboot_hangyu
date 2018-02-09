/*
 * @Author: lonhon 
 * @Date: 2017-11-10 11:51:46 
 * @Last Modified by: github.lonhon
 * @Last Modified time: 2018-01-30 11:46:04
 */
;
"use strict"
$(".pla-supernatant-cont").css({"height":parseFloat($("body").css("height").split("px")[0])*0.9+"px"});

var idTmr;
var drawlap
function  getExplorer() {
    var explorer = window.navigator.userAgent ;
    //ie
    if (explorer.indexOf("MSIE") >= 0) {
        return 'ie';
    }
    //firefox
    else if (explorer.indexOf("Firefox") >= 0) {
        return 'Firefox';
    }
    //Chrome
    else if(explorer.indexOf("Chrome") >= 0){
        return 'Chrome';
    }
    //Opera
    else if(explorer.indexOf("Opera") >= 0){
        return 'Opera';
    }
    //Safari
    else if(explorer.indexOf("Safari") >= 0){
        return 'Safari';
    }
}
function method1(tableid) {//整个表格拷贝到EXCEL中
    if(getExplorer()=='ie')
    {
        var curTbl = document.getElementById(tableid);
        var oXL = new ActiveXObject("Excel.Application");

        //创建AX对象excel
        var oWB = oXL.Workbooks.Add();
        //获取workbook对象
        var xlsheet = oWB.Worksheets(1);
        //激活当前sheet
        var sel = document.body.createTextRange();
        sel.moveToElementText(curTbl);
        //把表格中的内容移到TextRange中
        sel.select;
        //全选TextRange中内容
        sel.execCommand("Copy");
        //复制TextRange中内容
        xlsheet.Paste();
        //粘贴到活动的EXCEL中
        oXL.Visible = true;
        //设置excel可见属性

        try {
            var fname = oXL.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");
        } catch (e) {
            print("Nested catch caught " + e);
        } finally {
            oWB.SaveAs(fname);

            oWB.Close(savechanges = false);
            //xls.visible = false;
            oXL.Quit();
            oXL = null;
            //结束excel进程，退出完成
            //window.setInterval("Cleanup();",1);
            idTmr = window.setInterval("Cleanup();", 1);
        }
    }
    else
    {
        tableToExcel(tableid)
    }
}
function Cleanup() {
    window.clearInterval(idTmr);
    CollectGarbage();
};
var tableToExcel = (function() {
    var uri = 'data:application/vnd.ms-excel;base64,',
        template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
        base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },
        format = function(s, c) {
            return s.replace(/{(\w+)}/g,
                function(m, p) { return c[p]; }) }
    return function(table, name) {
        if (!table.nodeType) table = document.getElementById(table)
        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
        window.location.href = uri + base64(format(template, ctx))
    }
})()
var key = 0;
var curLineStyle,haveRedraw=false;
var cs_cgair = function (air_name) {  //城市 转详细信息
    for(var j = 0; j < airllist.airInfoDataList.length; j++ ){
        if(air_name == airllist.airInfoDataList[j].airInfoName){
            return airllist.airInfoDataList[j];
        }
    }
};
var cs_fourcode = function (_code) {  //四字码转城市
    for(var j = 0; j < airllist.airInfoDataList.length; j++ ){
        if(_code == airllist.airInfoDataList[j].icao){
            return airllist.airInfoDataList[j].city;
        }
    }
};
var cs_thereCode = function (air_name) {  //城市 转详细信息
    for(var j = 0; j < airllist.airInfoDataList.length; j++ ){
        if(air_name == airllist.airInfoDataList[j].airInfoName){
            return airllist.airInfoDataList[j].iata;
        }
    }
};
var cs_codeZname = function (air_code) {  //城市 转详细信息
    for(var j = 0; j < airllist.airInfoDataList.length; j++ ){
        if(typeof(air_code) != "string"){
            air_code = air_code.toString();
        };
        if(air_code.toUpperCase() == airllist.airInfoDataList[j].iata){
            return airllist.airInfoDataList[j];
        };
    };
};

/**
 * 城市机场三字码信息转换
 * @param code{String}
 * return {Object} 机场对应的所有信息
 * author yj 9/12
 * */
var codeScu = function (str) {
    for(var j = 0; j < airllist.airInfoDataList.length; j++ ){
        if(str == airllist.airInfoDataList[j].airInfoName || str == airllist.airInfoDataList[j].icao || str == airllist.airInfoDataList[j].iata){
            return airllist.airInfoDataList[j];
        };
    };
};
//LIU_DD  左上角菜单隐藏的功能
$(function(){
	$(document).on("click",function(){
		if(key!=0){
			if("block"==$(".pla-spread li")[1].style.display){
				for(var int=1;int < $(".pla-spread li").length;int++){
					if($(".pla-spread li")[int].style.display){
						$(".pla-spread li")[int].style.display="none";
					}
				}
			}
		}else{
			key+=1;
		}
	});

//测试待定**************************************
    /*展开栏目控制*/
    $(".pla-spread>ul>li:nth-of-type(1)").click(function(){
    	//清空选择的缓存航线航班
    	var object = parent.supData;
    	delete object.flight ;
		object.lane ="";
    	key=0;
        if($(".pla-spread>ul>li:nth-of-type(2)").css("display")=="none"){
            for(var i=0;i<=$(".pla-spread>ul>li").length;i++){
                $(".pla-spread>ul>li:nth-of-type("+i+")").css({"display":"block"});
            }
        }else {
            for(var i=2;i<=$(".pla-spread>ul>li").length;i++){
                $(".pla-spread>ul>li:nth-of-type("+i+")").css({"display":"none"});
            }
        }
    });
	$(".pla-tool-useName").click(function(event){
        event.stopPropagation();
        $(".useName-list").css({"display":"none"});
        $('.airportList').css({"display":"none"});
        if($(".se-list").css("display")=="none"){
            $(".se-list").css({"display":"block"});
        }else if($(".se-list").css("display")=="block"){
            $(".se-list").css({"display":"none"});
        }
    });
    $(".pla-tool-set").click(function(event){
        event.stopPropagation();
        $(".se-list").css({"display":"none"});
        $('.airportList').css({"display":"none"});
        if($(".useName-list").css("display")=="none"){
            $(".useName-list").css({"display":"block"});
        }else if($(".useName-list").css("display")=="block"){
            $(".useName-list").css({"display":"none"});
        }
    });
	$('.pla-tool-name').click(function(event){
		event.stopPropagation();
        $(".se-list").css({"display":"none"});
        $(".useName-list").css({"display":"none"});
        if($(".airportList").css("display")=="none"){
            $(".airportList").css({"display":"block"});
        }else if($(".airportList").css("display")=="block"){
            $(".airportList").css({"display":"none"});
        }
	});
    $("body").click(function(){
        $(".useName-list").css({"display":"none"});
        $(".se-list").css({"display":"none"});
        $('.airportList').css({"display":"none"});
        $('.pla-ranking').css({"display":"none"});
        $('.material-fact-installBox').css({"display":"none"});
    });
    //去除冒泡
    $('.pla-ranking').on('click',function(event){
  	  	event.stopPropagation();
    });
    $('.material-fact-installBox').on('click',function(event){
    	event.stopPropagation();
    });
    /*鼠标移入二级菜单*/
    $(".pla-spread>ul>li").on("mouseover",function(){
        if($(this).html()=="统计"){

        }
    });
    $(".pla-spread>ul>li:nth-of-type(1)").nextAll().on("click",function(){
    	key=0;
        $(this).children("div,ul").css("display","block");
        $(this).siblings().children("div,ul").css("display","none");
    });
    /*工具栏目-账号-天气 展开二级菜单*/
    $(".pla-tool>ul>li:nth-of-type(2)").on("mouseover",function(){
        $(".pla-tool>ul>li:nth-of-type(2)>ul").css({"display":"block"});
    });
    $(".pla-tool>ul>li:nth-of-type(3)").on("mouseover",function(){
    	$(".pla-tool>ul>li:nth-of-type(3)>ul").css({"display":"block"});
    });
    $(".pla-tool>ul>li:nth-of-type(2)").on("mouseleave",function(){
        $(".pla-tool>ul>li:nth-of-type(2)>ul").css({"display":"none"});
    });
    $(".pla-tool>ul>li:nth-of-type(3)").on("mouseleave",function(){
    	$(".pla-tool>ul>li:nth-of-type(3)>ul").css({"display":"none"});
    });
    /*页尾信息栏目*/
    $(".pla-tog").on("click",function(){
        $(this).css({"left":"-120px"});
        setTimeout(function(){
            $(".pla-data").css({"left":"0px"});
        },500);
    });
    $(".pla-data").on("click",'.material-fact-installF',function(){
    	$(".material-fact-installBox").hide("slow");
        $(".pla-data").css({"left":"-100%"});
        setTimeout(function(){
            $(".pla-tog").css({"left":"0px"});
        },1000);
    });
    /***设置界面控制***/
    $(".pla-tool-account>li:nth-of-type(2)").on("click",function(){
        $(".pla-set").css({"display":"block","background-color":"rgba(0,0,0,0.6)"});
        $(".pla-set-interface").animate({
            "margin-top":"3%"
        },300);
    });
    /***退出界面***/
    $(".se-list>li:last-child").on("click",function(){
    	//清空浏览器缓存，window.sessionStorage
        window.sessionStorage.clear();
        clearAllCookie();
    	localStorage.removeItem("_lisj"+useName_n);
        window.location = "/login";
    	// window.location = "/outLogin";
    });
    /***清除cookie***/
    function clearAllCookie() {  
        return ;
        var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
        if(keys) {
            for(var i = keys.length; i--;)  {
                document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString();
            }
        }  
    } 
    $(".pla-set-interface>div:nth-of-type(1)>span:nth-of-type(2)").on("click",function(){
        $(".pla-set-interface").animate({
            "margin-top":"100%"//
        },300,function(){
            $(".pla-set").css({"display":"none"});
        });
    });
    ///*拖拽提示框*/
    $(".pla-promptBox").mousedown(function(event){
        var e = event || window.event;
        var top=parseInt($(".pla-promptBox").css("top").split("px")[0]);
        var left=parseInt($(".pla-promptBox").css("left").split("px")[0]);
        if(e.target.className!="rolling"){
            $("body").mousemove(function(event){
                var b = event || window.event;
                $(".pla-promptBox").css({"top":top-e.screenY+b.screenY,"left":left-e.screenX+b.screenX});
            });
        }
    });

    /*拖拽框-机场视角*/
    $(".their-own").mousedown(function(event){
        var e = event || window.event;
        var top=parseInt($(".their-own").css("top").split("px")[0]);
        var left=parseInt($(".their-own").css("left").split("px")[0]);
        if(e.target.className!="rolling"){
            $("body").mousemove(function (event){
                var b = event || window.event;
                $(".their-own").css({"top":top-e.screenY+b.screenY,"left":left-e.screenX+b.screenX});
            });
        }
    });
    $("body").mouseup(function(){//鼠标松开取消事件绑定
        $("body").unbind("mousemove");
    });

    /*模拟滚动条*/
    $(".pla-promptBox").on("mousewheel","div",function(e,delta){
    	if($(this).attr("class")=="content-rolling"){
	        var countHeight=0;//总的航线航班内容高度
	        for(var i= 0;i<$(".pla-promptBox-cont-line").length;i++){
	            countHeight+=parseFloat($(".pla-promptBox-cont-line").eq(i).css("height").split("px")[0]);
	        }
	        var h=parseInt($(".pla-promptBox-cont").css("height").split("px")[0]);//固定内容高度
	        var rh=parseInt($(".pla-promptBox-cont").css("height").split("px")[0])-parseInt($(".rolling").css("height").split("px")[0]);//滚动条的可滚动高度
	        if(countHeight>h){
	            $(".rolling").css("display","block");
	            if(delta=="-1"){
	                $(this).css("top","-=30");
	                $(".rolling").css("top",-parseInt($(".content-rolling").css("top").split("px")[0])*rh/Math.abs(countHeight-h));
	                if(parseInt(Math.abs($(this).css("top").split("px")[0]))<Math.abs(countHeight-h)){

	                }else {
	                    $(this).css("top",-Math.abs(countHeight-h));
	                    $(".rolling").css("top",rh);
	                }
	            }else if(delta=="1"){
	                $(this).css("top","+=30");
	                $(".rolling").css("top",-parseInt($(".content-rolling").css("top").split("px")[0])*rh/Math.abs(countHeight-h));
	                if(parseInt($(this).css("top").split("px")[0])<0){

	                }else {
	                    $(this).css("top",0);
	                    $(".rolling").css("top",0);
	                }
	            }
	        }else {
	            $(".rolling").css("display","none");
	        }
    	}
    });
    /*鼠标拖动滚动条*/
    $(".pla-promptBox").on("mousedown","div",function(b){
    	if($(this).attr("class")=="rolling"){
	        var H=0;//总的航线航班内容高度
	        for(var i= 0;i<$(".pla-promptBox-cont-line").length;i++){
	            H+=parseFloat($(".pla-promptBox-cont-line").eq(i).css("height").split("px")[0]);
	        }
	        var cleY= b.screenY;
	        var oldTop=parseInt($(".rolling").css("top").split("px")[0]);
	        var h=parseInt($(".pla-promptBox-cont").css("height").split("px")[0]-$(".rolling").css("height").split("px")[0]);
	        var kH=parseInt($(".pla-promptBox-cont").css("height").split("px")[0]);
	        $(".pla-box").mousemove(function(e){
	            if(H>kH){
	                $(".rolling").css("top",oldTop+(e.screenY-cleY));
	                if($(".rolling").css("top").split("px")[0]>h){
	                    $(".rolling").css("top",h);
	                }else if($(".rolling").css("top").split("px")[0]<0){
	                    $(".rolling").css("top",0);
	                }
	                $(".content-rolling").css("top",-parseInt($(".rolling").css("top").split("px")[0])*(H-kH)/h);
	            }
	        });
    	}
    });

    /*右侧选择信息框*/
    $(".pla-promptBox").on("mouseover","li",function(){
    	if($(this).parent().attr("class")=="right-rolling-ul right-rolling-cont"){
	        var tag=$(this).index();
	        $(this).css({"background-color":"#dfdfdf"}).siblings().css({"background-color":"white"});
	        $(".right-rolling-title>li").eq(tag).css({"background-color":"#354d89","color":"white"}).siblings().css({"background-color":"transparent","color":"#7c9ec9"});
	        $(this).children("span").css("opacity","1");
	        $(this).siblings().children("span").css("opacity","0");
	        for(var i=0;i<$(".count-flight,.count-lines").length;i++){
	            if($(".count-flight,.count-lines").eq(i).attr("tag")=="set"){
	                $(".count-flight,.count-lines").eq(i).css("background-color","rgba(223,223,223,1)");
	            }else {
	                $(".count-flight,.count-lines").eq(i).css("background-color","white");
	            }
	        }
    	}
    });

    /*右侧选择信息框*/
    $(".pla-tool-accounts>li:nth-of-type(1)").on("click",function(){
			$("#pages",parent.document.body).attr("src","/processTask");
    		$(".pla-supernatant").css({"display":"block","background-color":"rgba(0,0,0,0.6)"});
    		$(".pla-promptBox").css({"display":"none"});
    		$(".pla-supernatant-cont").css({"display":"block"});
    		$(".pla-supernatant-cont").animate({"opacity":"1","top":"5%"},500);
    });
    $(".pla-promptBox").on("mouseout","li",function(){
    	if($(this).parent().attr("class")=="right-rolling-ul right-rolling-cont"){
    		$(".count-flight,.count-lines").css("background-color","white");
	        var tag=$(this).index();
	        $(".right-rolling-cont>li").css({"background-color":"white"});
	        $(".right-rolling-title>li").eq(tag).css({"background-color":"transparent","color":"#7c9ec9"});
	        $(".right-rolling-cont>li>span").css("opacity","0");

    	}
    });
    //适应信息框位置
    /*右侧选择信息框*/
    var fnURL = [
        {
            name: '共飞运营对比',
            urll: '/totalFlyAnalysis'
        },
        {
            name: '航线历史收益统计',
            urll: '/airline'
        },
        {
            name: '销售报表',
            urll: '/salesReport'
        },
        {
            name: '销售动态',
            urll: '/buyTicketReport'
        },
        {
            name: '客源组成',
            urll: '/SourceDistribution'
        },
        {
            name: '销售数据',
            urll: '/SalesData/accountCheck'
        },
        {
            name: '客票分析',
            urll: '/ticketMonitor'
        }
    ]
    $(".pla-promptBox").on("click","div",function(){
    	if($(this).hasClass("pla-prompTip-des")){
    		if($(this).parent().hasClass("lis")){
    			return false;
    		}
    		supData.flt=$('.pla-prompRight').attr('flt');//判断是否为航段标记
    		supData.pageclass=$(this).html();
//        	var fnURL = [];
//        	 fnURL[0] = new Object();fnURL[0].name="共飞运营对比";fnURL[0].urll="/totalFlyAnalysis";
//             fnURL[1] = new Object();fnURL[1].name="航线历史收益统计";fnURL[1].urll="/airline";
//             fnURL[2] = new Object();fnURL[2].name="销售报表";fnURL[2].urll="/salesReport";
//             fnURL[4] = new Object();fnURL[4].name="销售动态";fnURL[4].urll="/buyTicketReport";
//             fnURL[5] = new Object();fnURL[5].name="客源组成";fnURL[5].urll="/SourceDistribution";
//             fnURL[6] = new Object();fnURL[6].name="销售数据";fnURL[6].urll="/SalesData/accountCheck";
//             fnURL[7] = new Object();fnURL[7].name="客票分析";fnURL[7].urll="/SalesData/accountCheck";
            for(var a in fnURL){
				if(supData.pageclass==fnURL[a].name){
                    $("#pages",parent.document.body).attr("src",fnURL[a].urll);
                    //阻止页面后退
                    $("#pages",parent.document.body).append(`<script type='text/javascript'>
                        history.pushState(null, null, document.URL);
                        window.addEventListener('popstate', function () {
                            return history.pushState(null, null, document.URL);
                        });
                    </script>`);
				};
            }
            $(".pla-supernatant").css({"display":"block","background-color":"rgba(0,0,0,0.6)"});
            $(".pla-promptBox").css({"display":"none"});
            $(".pla-supernatant-cont").css({"display":"block"});
            $(".pla-supernatant-cont").animate({"opacity":"1","top":"5%"},500);
            if(typeof(supData.flight)!="undefined"&&supData.flight!=""){
            	supData.linFlag = "0";
            }else{
            	supData.linFlag = "1";
            }
    	}
    });

    $(".pla-promptBox").on("mouseover","div",function(){
    	if($(this).hasClass("_tipFlight")){
    		var abbr = $(this).attr("abbr");
    		if(typeof(abbr) != "undefined"){
    			var abbrarr = abbr.split("-");
    			var airline = "";
    			var flynum = "";
    			if(abbrarr.length>1){
    				var lines = abbrarr[0].split(",");
    				if(lines.length>2){
    					airline = lines[0]+"="+lines[1]+"="+lines[2];
    				}else{
    					airline = lines[0]+"="+lines[1];
    				}
    				flynum = abbrarr[1];
    				supData={"lane":airline,"flight":flynum};
    			}else{
    				var lines = abbrarr[0].split(",");
    				if(lines.length>2){
    					airline = lines[0]+"="+lines[1]+"="+lines[2];
    				}else{
    					airline = lines[0]+"="+lines[1];
    				}
    				supData={"lane":airline};
    			}

    		}
        }
    });
    $("#container").on("click",function(){
        $(".pla-promptBox").css("display","none");
        $(".their-own").css("display","none");
    });
    //LIU_DD 菜单消失效果
	$("div:not(div.pla-promptBox, div.pla-prompTip, div.pla-prompRight, div.pla-prompRight-box, div.pla-prompRight-sj)").on("click",function(){
		 $(".pla-promptBox").css("display","none");
	});
	$(".pla-promptBox").on("mousemove","div",function(){
            if($(this).attr("class")=="count-flight"||$(this).attr("class")=="count-lines"){
                $(".count-flight,.count-lines").attr("tag","");
                $(".pla-promptBox-title").attr("tag","");
                $(this).attr("tag","set");
            }
            if($(this).attr("class")=="pla-promptBox-title"){
            	$(".pla-promptBox-title").attr("tag","");
            	$(".count-flight,.count-lines").attr("tag","");
            	$(this).attr("tag","set");
            }
    });
    $(".pla-promptBox").on("mouseout","div",function(){
        if($(this).attr("class")=="count-flight"||$(this).attr("class")=="count-lines"){
            $(this).css("background-color","white");
        }
    });

});
function closeIframe(){
	$('.pla-supernatant').hide();
}


//2017-3-30 主页搜索按钮
$(".search-iconarea").click(function(){
	var tip=$(".pla-btn-switch").attr("tag");
	var Spanel=$(".pla-search .pla-search-panel");
	if(tip=="line"){
		if($(Spanel).css("display")=="none"){
			$(Spanel).show();
			$(this).addClass("searchSet");
			open_search(true);
		}
		else{
			$(Spanel).hide();
			$("input#pla-search-id").val("");
			$(this).removeClass("searchSet");
		}
		$("input#pla-search-id").focus();
	} else if (tip=="air") {
		if($(Spanel).css("display")=="none"){
			$(Spanel).show();
			$(this).addClass("searchSet");
			open_search(false);
		}
		else{
			$(Spanel).hide();
			$("input#pla-search-id").val("");
			$(this).removeClass("searchSet");
		}
		$("input#pla-search-id").focus();
	}
	else{
		console.log("暂只支持航线视图");
	}
	//阻止
	$(Spanel).click(function(event){
		event.stopPropagation();
	})
})


var formateFly = function(data){
	var source=[];
	for(key in data){
		var a='';
		if(data[key].dptAirport){
			a= data[key].airport + '=' + data[key].dptAirport+ '=' +  data[key].arrAirport;
		}else{
			a= data[key].airport + '=' +  data[key].arrAirport;
		}
		a= reshape(a)
		data[key].flyNum.split(',').forEach(function(item){
			source.push(fullFlyNum(item)+a);
		})
	}
	return source;
}
//-------------------------------------- 航班对展开
var fullFlyNum = function (num){   // HU7305/06 => HU7305/HU7306
    if(num.split('/').length>1 && num.split('/')[0].length != num.split('/')[1].length){
        return num.split('/')[0] + '/' + num.split('/')[0].replace(num.split('/')[0].substr(-2,2),'') + num.split('/')[1];
    }else{
        return num;
    }
}
//开启搜索，自动补全
function open_search(thisflag){
    thisHasData = false;
	var type=$("#pla-search-tor").val();
    var flyNum=[];	//航班号
	flyNum = formateFly(parent.airFlyIata);	//转换
	var portdot=[]; //航点	
	$.each(parent.nationalAirport,function(i,el){
		portdot.push(el.code+'('+ el.airportName + ')');
	})
	$("#pla-search-id").typeahead({
		source:flyNum,
		items:5
	})
	search_getNum($("#pla-search-id"),flyNum);
	$(".for-search-select p").unbind("click");
	$(".for-search-select .slt li").unbind("click");
	if(thisflag){
		$(".for-search-select p").bind("click",function(){
			if($(".for-search-select .slt").css("display")=="none"){
				$(".for-search-select .slt").show();
			}
			else{
				$(".for-search-select .slt").hide();
			}
		})

		$(".for-search-select .slt li").bind("click",function(event){
			$(".for-search-select span.search-type-t").text($(this).text()).attr("tag",$(this).attr("tag"));
			$(".for-search-select .slt").hide();
			$(".pla-search-panel-body").empty();
			if($(this).attr("tag")=="fdot"){
				$(".pla-search-panel-body").append('<input id="pla-search-id" type="text" placeholder="输入三字码、机场后敲击回车搜索">');
				$(".pla-search-panel-body #pla-search-id").typeahead({
					source:portdot
				})
				search_getNum($(".pla-search-panel-body #pla-search-id"),portdot);

			}
			else{
				$(".pla-search-panel-body").append('<input id="pla-search-id" type="text" placeholder="输入航班号后敲击回车搜索">');
				$(".pla-search-panel-body #pla-search-id").typeahead({
					source:flyNum
				})
				search_getNum($(".pla-search-panel-body #pla-search-id"),flyNum);
			}
		})
	}else{
		$(".pla-search-panel-body").empty();
		$(".for-search-select span.search-type-t").text("航点").attr("tag","fdot");
		$(".pla-search-panel-body").append('<input id="pla-search-id" type="text" placeholder="输入三字码、机场后敲击回车搜索">');
		$(".pla-search-panel-body #pla-search-id").typeahead({
			source:portdot
		})
		search_getNum($(".pla-search-panel-body #pla-search-id"),portdot);
	}

}


function mouseChoose(){//鼠标点击事件
	$('ul.dropdown-menu').bind('click',function(){
		setTimeout(function(){
			var e = jQuery.Event("keyup");
			e.which = 13;
			e.keyCode= 13;
			$(".pla-search-panel-body #pla-search-id").trigger(e);
		},200)
	})
}

//回车确认事件
function search_getNum(oInput,con){
	$(oInput).bind("keyup",function(e){
		if(e.keyCode=="27"){//ESC
			$(".pla-search .pla-search-panel").hide();
			return false;
		} else if (e.keyCode=="13"){
			if(isContain($(this).val(),con)){
                ad_resetMap();
                search_reset_map($(this).val(),$("#pla-search-tor").attr("tag"));
				return;
			} else {
                search_openTip();
				$(this).val("");
			}
		}
	})
	mouseChoose();
}


//判断是否存在
function isContain(i,con){
	var has=false;
	for(key in con){
		if(con[key] == i){
			has=true;
		}
    }
	return has;
}

var thisHasData = false;
//弹出提示
function search_openTip(){
    setTimeout(function(){
        if(!thisHasData){
            $(".SD-hd-null").css({
                'top':'64%',
                'left':'82%'
            });
            $('.SD-hd-null').show();
            setTimeout(function(){
                $(".SD-hd-null").slideUp();
            },1000)
        }
    },100)
}
//重置地图
function search_reset_map(val,flag){
	var par={};
	var num = JSON.parse(JSON.stringify(stickOption));
	$(".search-iconarea").click();
    $("#af-pramas-reseter").click();//高级筛选重置
    $("#advanced-filter-container").hide(0);//高级筛选隐藏
	if($('.pla-btn .pla-btn-switch').attr('tag')=='air'){	//机场视图
		//航点——机场
		var ct = search_getCity(val.substr(0,3));
		reDrawDot(val.substr(0,3),ct,"port");
		haveRedraw=true;
	}
	else{	//航线视图
		curLineStyle={};
		//判断航点还是航班号获取坐标点
		if(flag=="fnum"){
			num.series = search_delLine(num.series,val.substr(9),true);
			num.tooltip.formatter=function(params){};
			myChart.setOption(num);
			haveRedraw=true;
		}
		else{//航点——城市
			var ct = search_getCity(val.substr(0,3));
			reDrawDot(val.substr(0,3),ct,"city");
			haveRedraw=true;
		}
	}
	if(flag=="fnum"){
		par.seriesType="lines";
		par.data=num.series[num.series.length-1].data[num.series[num.series.length-1].data.length-1];
	}
	else if(flag=="fdot"){
		par={
			"seriesType":"scatter",
			"data":{
				"name":getCityNameThree(val)
			},
			"componentType":"series",
			"name":getCityNameThree(val),

		}
		if(!getCityNameThree(val)){
			return false;
		}
	}
	if($('.pla-btn .pla-btn-switch').attr('tag')=='air'){
		par.data.typeSelf="outs";
		par.name=getAirPortName(val);
	}

	par.event={
			offsetX:"200",
			offsetY:"200"
	}

	$(".pla-promptBox").hide();
	$(".their-own").hide();
	if(val.substr(0,3) == parent.cityIata){
		par.data.typeSelf= undefined;
	}
	parent.chart_openTool(par);
}


//5-16
//航点绘图
function reDrawDot(name,ZN,type){
	var newa = JSON.parse(JSON.stringify(myChart.getOption().series)); //自己用
	var newb = JSON.parse(JSON.stringify(myChart.getOption()));	//还回去
    var nowdot="",
        newseries={};
	var flag=false;
	if(newa[newa.length-1].name=="搜索功能增加series"){
		newa.pop();
	}
	if(type=="city"){//航线	PUSH
		for(var i=0;i<newa.length;i++){
			if(newa[i].type=="scatter" && newa[i].data.length>0){
				for(var a=0;a<newa[i].data.length;a++){
					if(newa[i].data[a].name==ZN){
						nowdot=newa[i].data[a];
						newseries=JSON.parse(JSON.stringify(newa[i]))
						flag=true;
					}
				}
			}
		}
	}
	else{//机场	PUSH
		var portname= search_getPort(name);
		for(var i=0;i<newa[0].data.length;i++){
			if(newa[0].data[i].iata==name){
				nowdot=newa[0].data[i];
				newseries=JSON.parse(JSON.stringify(newa[0]));
				flag=true
			}
		}
    }
	if(flag){
        thisHasData = true;
		newseries.name="航线搜索新增";
		newseries.data=[];
		newseries.data.push(nowdot);
		newseries.label={};
		newseries.type="effectScatter";
		newseries.symbolSize=10;
		newseries.itemStyle={
				normal:{
					color: "red",
					opacity: 0.8
				}
		}
		newa.push(newseries);
        newb.series=newa;
		myChart.setOption(newb);
	}
	else{
		return false
	}
	return nowdot;
}

function getCityNameThree(iata,flag){
	if(iata){
		var code = iata.substring(0,3);
		if(parent.airportMap[code]){
			return parent.airportMap[code].ctyChaNam;
		}
		else{
			search_openTip();
			return false;
		}
	}
	else{return false}
}

function getAirPortName(iata){
    var code = iata.substring(0,3);
	for (var a=0;a<parent.nationalAirport.length;a++){
		if(parent.nationalAirport[a].code==code){
			return parent.nationalAirport[a].airportName;
		}
	}
}

//航点重构原有线
function search_deldot(series,dot){
	var flag=false;//判断当前是否存在该航点
	for(var a=0; a< series[2].data.length;a++){
		if(series[2].data[a].name==dot) {flag=true};
	}
	for(var a=0; a< series[5].data.length;a++){
		if(series[5].data[a].name==dot) {flag=true};
	}


	if(flag){
		//search_delLine(series,dot,false);
	}
	else{
		alert("机场暂无此航点信息！");
		return false;
	}
}



//重构原有线
function search_delLine(series,nam,type){
	var newline=[];
	var flag=0;
	if(type){//航班号
		//从在飞
		for(var a= series[0].data.length - 1 ; a>-1 ; a--){
			if(nam.indexOf(series[0].data[a].fromName)!=-1){
				if(nam.indexOf(series[0].data[a].toName)!=-1){
					newline.push(series[0].data[a]);
					curLineStyle=series[0].lineStyle;
					flag=1;
				}
			}
		}
		//从历史
		if(flag!=1){
			for(var a= series[4].data.length - 1 ; a>-1 ; a--){
				if(nam.indexOf(series[4].data[a].fromName)!=-1){
					if(nam.indexOf(series[4].data[a].toName)!=-1){
						newline.push(series[4].data[a]);
						curLineStyle=series[4].lineStyle;
						flag=2;
					}
				}
			}
		}
	}

	function research(p,t,data){
		var result={};
		for(var a= data.length - 1 ; a>-1 ; a--){
			if(t==data[a].toName){
				if(p==data[a].fromName){
					result = data[a];
					data.splice[a,1];
					break;
				}
			}
		}
		return result;
	}

	var news={};
	if(flag==1){
		news=JSON.parse(JSON.stringify(series[0]));
	}
	else if(flag==2){
		news=JSON.parse(JSON.stringify(series[4]));
	}
	if(flag>0){
		news.name="航线搜索新增";
		news.data=newline;
		news.lineStyle.normal.color="#d85330";
		news.lineStyle.emphasis.color="d85330"
		news.lineStyle.emphasis.width=2.5;
		series.push(news);
		return series;
	}
	else{
		return false;
	}
}

//三字码转换中文地名
function reshape(itlist){
	var l=itlist.split("=");
	for(var i =0 ; i<l.length;i++){
		l[i]=parent.airportMap[l[i]].ctyChaNam;
	}
	return l.join("=") ;
}

//中文地名返回坐标
function it_to_coords(cnlist){
	var adrArr=[];
	var a = cnlist.split("=");
	var allcoor = parent.cityCoordinateList;
	if(a.length > 1){
		for(var q = 0 ; q<allcoor.length;q++){
			for(var w= 0 ; w< a.length ;w++){
				if(allcoor[q].cityName==a[w]){
					adrArr.push(allcoor[q].cityCoordinatee.split(","));
				}
			}
		}
	}
	else{
		return false;
	}
	return adrArr
}
//通过航班号获取三字码
function search_fromFly(num){
	var list={};
	var itlist=[];
	for(var i=0;i<parent.airFlyIata.length;i++){
		if(parent.airFlyIata[i].flyNum==num){
			list=parent.airFlyIata[i];
			itlist.push(parent.airFlyIata[i].airport);
			if(parent.airFlyIata[i].arrAirport){
				itlist.push(parent.airFlyIata[i].arrAirport);
			}
			itlist.push(parent.airFlyIata[i].dptAirport);
		}
		else{
			//显示错误信息。
		}
	}
	return itlist;
}


//通过三字码得到城市名
function search_getCity(it){
	if(parent.airportMap[it]){
		var ct =parent.airportMap[it].ctyChaNam;
		return ct;
	}
	else{
		search_openTip();
		return false;
	}
}
//通过三字码得到机场
function search_getPort(it){
	var port="";
	for(var i=0;i<parent.nationalAirport.length;i++){
		if(parent.nationalAirport[i].code==it){
			port=parent.nationalAirport[i].airportName;
		}
	}
	if(port!=""){
		return port;
	}
	else{
		search_openTip();
		return false;
	}
}

//航班号格式化
function formatFlyNum(val,flag){
	var list=val.split('/');
	if(list.length>2 || list.length<1) return false;
	var newval=list[1];
	if(flag){	//变长
		list[1]=list[0].substr(0,list[0].length-2)+ newval ;
	}
	else{	//变短
		list[1]=list[1].substr(list[1].length-2);
	}
	newval=list.join('/');
	return  newval;
}


//冒泡关闭
$(document).click(function(e){
	var _con = $('.pla-search');   // 搜索条
    if(!_con.is(e.target) && _con.has(e.target).length === 0){
    	$(".pla-search-panel-body #pla-search-id").val("");
    	$('.pla-search .pla-search-panel').hide();
    	$('.search-iconarea').removeClass("searchSet");
    }

});






//-------------------------------------------用户反馈
	var Rsp_flag= true;
	var open_user_response = function(){
		$('#response-parent').show();
		$('#R-newRsp .img-input').on('change',function(){	//添加图片
			var reader = new FileReader();
			var img = new Image();
			reader.readAsDataURL(this.files[0]);
			reader.onload = function(e){
				var mb = (e.total/1024)/1024;
				if(mb>= 5){
					alert('图片不能大于5M!');
					$(this).reset();
				}else{
					img.src = this.result;
					img.style.width = "50px";
					img.style.height = "50px";
				}
			}
			$(this).parents('li').append(img);
			$(this).parents('li').append('<span class="img-clear">X</span>');

		})

		$('#R-newRsp .diyImgFile').on('click', 'span.img-clear', function(){	//图片删除
			$(this).siblings('input').val('');
			$(this).siblings('img').remove();
			$(this).remove();
			event.stopPropagation();
		})

		$('#R-newRsp #rp-submit').click(function(){		//提交按钮
			if($('#response-text').val() ==''){
				$('#response-text').focus();
				$('#response-text').attr('placeholder','不能为空！')
				return;
			} else if($('#userTelNum').val() ==''){
				$('#userTelNum').focus();
				$('#userTelNum').attr('placeholder','不能为空！')
				return;
			}

			var oMyForm = new FormData($('#R-newRsp #response-form')[0]);
	    	$.ajax({
	    	    url: '/userCommit/add',
	    	    type: 'POST',
	    	    dataType: 'JSON',
	    	    data: oMyForm,
	    	    processData: false,
	    	    contentType: false
	    	})
	    	.done(function(rdata) {
	    		if(rdata.success || rdata.success=='true'){
	    			$('#R-newRsp .form-container').hide();
	    			$('#R-newRsp').append('<div class="rp-comTip"><h3>提交成功</h3><p>感谢您的反馈与意见，我们会尽快安排工作人员解决您的问题。</p><div>');
	    			setTimeout(function(){
	    				$('#R-newRsp .rp-comTip').remove();
	    				$("#response-form")[0].reset();
	    				$('#userTelNum').val(userPhone);
	    				$("#response-form img").remove();
	    				$("#response-form span.img-clear").remove();
	    				$('#R-newRsp .form-container').show();
	    			},1000)

	    		}
	    	})
	    	.always(function() {
	    		//console.log('over');
	    	})
		})
		$('#R-newRsp #rp-cancel').click(function(){	//取消按钮
			$("#response-form")[0].reset();
			$('#response-parent').hide();
		})


		$('#R-naviNew').on('click',function(){	//新建反馈
			$('#R-newRsp').show();
			$('#R-oldRsp').hide();

			$(this).addClass('response-checked').siblings().removeClass('response-checked');
		})




		var txtarea = document.getElementById('response-text');	  //文本域字数限制。
		txtarea.oninput= function(evnet){
			if(this.value.length > 300){
				this.value.length = 300;
			}else{
				$('#wordnum').text(this.value.length);
			}
		}
	}
	$('#R-naviOld').on('click',function(){	//历史反馈
		//初始化工作
		$('#R-newRsp').hide();
		$('#R-oldRsp').show();
		$(this).addClass('response-checked').siblings().removeClass('response-checked');
		$('#R-oldRsp .form-container').css({'top': '0'});

		//提取历史反馈
    	$.ajax({
    	    url: '/userCommit/findUserCommitList',
    	    type: 'POST',
    	    dataType: 'JSON'
    	})
    	.done(function(rdata) {
    		if(rdata.success || rdata.success== 'true'){
    			$('#R-oldRsp #rp-tableBody').empty();
    			createOldRsp(rdata);
    		}
    	})
    	.always(function() {
    		//console.log('over');
    	})
	})

	var createOldRsp = function(oldRsp){	//历史反馈——创建消息列表
		var content= $('#R-oldRsp #rp-tableBody');
		for(var i = oldRsp.data.length-1 ; i > -1 ; i--){
			var d= oldRsp.data[i];
			var nodeA= '<tr><td>' + d.textDateFormat + '</td><td>'+ d.title + '</td><td>'+ d.state + '</td><td><p class="Rsp-btn" status="close" infoStatus='+ d.state +' msgID='+d.userCommitInfoId+'>查看详情&nbsp;<span>&#xe88e;</span></p></td></tr>';
			$(content).append(nodeA);
		}
		open_oldRsp();
	}


	var open_oldRsp =function(){	//历史反馈——详情
		$('#R-oldRsp .Rsp-btn').bind('click',function(){
			var infoStatus= $(this).attr('infoStatus');
			var msgId= $(this).attr('msgID');
			//发送请求      打开一个列表项目   改变箭头方向
			if($(this).attr('status') == 'close'){	//打开
				$('div.detailContainer').parents('tr.newtr').remove();
				resetBtn();
				$(this).children('span').html("&#xe61f;");
				$(this).parents('tr').after('<tr class="newtr"><td colspan="4"><div class="detailContainer" id="oldDetail"></div></td></tr>');
				$(this).attr('status','opened');
				var oTr=  $(this).parents('tr').next().children().children();
				//发送请求
		    	$.ajax({
		    	    url: '/userCommit/findUserCommitDetail',
		    	    type: 'POST',
		    	    dataType: 'JSON',
		    	    async: false,
		    	    data: {
		    	    	userCommitInfoId : msgId
		    	    }
		    	})
		    	.done(function(odata) {
		    		if(odata.success || odata.success=='true'){
	    				fillRspInfo(oTr, odata.data, infoStatus, msgId);
		    		}else{

		    			$(oTr).text("服务异常，请稍后再试");
		    		}
		    	})
		    	.always(function() {
		    		//console.log('over');
		    	})
			}else{	//关闭
				if($(this).parents('tr').next().hasClass('newtr')){
					$(this).parents('tr').next().remove();
				}
				$(this).attr('status','close');
				$(this).children('span').html("&#xe88e;");
				//return;
			}
		})
	}
	var fillRspInfo = function(oDiv, data, status, msgId){		//反馈详情	填充内容
		var msgPhone='';
		if($.isArray(data)){	//有多条
			for(var a=0; a<data.length; a++){
				$(oDiv).append('<div class="rsp-detail-pices"></div>');
				var nowDiv= $('.rsp-detail-pices').eq(a);
				msgPhone= data[a].phone;
				var userText= '<p>'+ data[a].userText +'</p>';	//文字内容		
				if(data[a].userImgsList != null){
					var oUl='<ul class="imgList">';
					var imgBaseList= [];
					for(var i =0; i<data[a].userImgsList.length; i++){
						oUl+='<li tag='+ a +'>图片' + (i+1) + '.jpg</li>';
						imgBaseList.push(data[a].userImgsList[i].blobImg);
					}
					oUl += '</ul>';
				}
				$(nowDiv).append('<h6>我的描述：</h6>');
				$(nowDiv).append('<p class="rsp-date">时间：'+ data[a].userUpdateDateFormat +'</p>');
				$(nowDiv).append(userText);
				$(nowDiv).append(oUl);

				if(status == '待处理' && data[a].adminText == null){	//待处理
					//null
				}else{	//已处理||已解决
					$(nowDiv).append('<h6>空港小助手：</h6>');
					$(nowDiv).append('<p>'+ data[a].adminText +'</p>');
				}
			}
		}else{	//有一条
			return;
			msgPhone= data.phone;
			var userText= '<p>'+ data.userText +'</p>';	//文字内容		
			if(data.userImgsList != null){
				var oUl='<ul class="imgList">';
				var imgBaseList= [];
				for(var i =0; i<data.userImgsList.length; i++){
					oUl+='<li>图片' + (i+1) + '.jpg</li>';
					imgBaseList.push(data.userImgsList[i].blobImg);
				}
				oUl += '</ul>';
			}
			$(oDiv).append('<h6>我的描述：</h6>');
			$(oDiv).append(userText);
			$(oDiv).append(oUl);

			if(status == '待处理' ){	//待处理
				$(oDiv).append('<div class="askArea"><p>是否已解决该问题？</p><button id="RspOver" infoID='+ msgId +' class="btn-blue">已解决</button></div>');
			}else{	//已处理||已解决
				$(oDiv).append('<h6>空港小助手：</h6>');
				$(oDiv).append('<p>'+ data.adminText +'</p>');

			}
		}

		if(status == '已回复'){
			$(oDiv).append('<div class="askArea"><p>是否已解决该问题？</p><button id="RspOver" infoID='+ msgId +' class="btn-blue">已解决</button><button id="RspContinue" class="btn-gray">继续提问</button></div>');
		}






		//各种按钮绑定事件

		$('#rp-table .detailContainer li').on('click', function(){	//图片
			$('#rp-table .detailContainer .rsp-bigimg').remove();
			var imgindex= $(this).index();
			var imglist= '<img src="data:image/jpg;base64,'+ data[$(this).attr('tag')].userImgsList[imgindex].blobImg +'" />';
			$(oDiv).append('<div class="rsp-bigimg">'+ imglist +'</div>');
		})


		//已解决
		$('#rp-table #RspOver').on('click', function(){
			var thisID= $(this).attr('infoID');
			rsp_change_state(2, thisID)

		})

		//继续提问
		$('#rp-table #RspContinue').on('click', function(){
			$(this).parents('div.askArea').hide();
			$(oDiv).append('<h2>补充反馈及意见<span class="QuiredIcon">*<span></h2>');
			$(oDiv).append($('#response-form').clone());
			$('#rp-table #userTelNum').val(msgPhone);
			//文本域字数限制。
			$('#rp-table #response-text').on('input propertychange', function() {
				if(this.value.length > 300){
					this.value.length = 300;
				}else{
					$('#rp-table #wordnum').text(this.value.length);
				}
			});
			
			$('#oldDetail #userTelNum').val(msgPhone);
			$('#rp-table #rp-submit').click(function(){	//提交
				var oMyForm = new FormData($('#rp-table #response-form')[0]);
				oMyForm.append('userCommitInfoId', msgId);
		    	$.ajax({
		    	    url: '/userCommit/add',
		    	    type: 'POST',
		    	    dataType: 'JSON',
		    	    data: oMyForm,
		    	    processData: false,
		    	    contentType: false
		    	})
		    	.done(function(rdata) {
		    		if(rdata.success || rdata.success=='true'){
		    			$('#R-naviOld').click();
		    			$('#R-oldRsp .form-container').hide();
		    			$('#R-oldRsp').append('<div class="rp-comTip"><h3>提交成功</h3><p>感谢您的反馈与意见，我们会尽快安排工作人员解决您的问题。</p><div>');
		    			setTimeout(function(){
		    				$('#R-oldRsp .rp-comTip').remove();
		    				$('#R-oldRsp .form-container').show();
		    			},1000)

		    			rsp_change_state(0, msgId);
		    		}
		    	})

			})
			$('#rp-table #rp-cancel').click(function(){	//取消
				$('#R-naviOld').click();
			})


			$('#R-oldRsp .img-input').on('change',function(){	//添加图片
				var reader = new FileReader();
				var img = new Image();
				reader.readAsDataURL(this.files[0]);
				reader.onload = function(e){
					var mb = (e.total/1024)/1024;
					if(mb>= 5){
						alert('图片大于5M!');
					}else{
						img.src = this.result;
						img.style.width = "50px";
						img.style.height = "50px";
					}
				}
				$(this).parents('li').append(img);
				$(this).parents('li').append('<span class="img-clear">X</span>');

			})

			$('#R-oldRsp .diyImgFile').on('click', 'span.img-clear', function(){	//图片删除
				$(this).siblings('input').val('');
				$(this).siblings('img').remove();
				$(this).remove();
			})
		})


	}



	//	冒泡关闭图片
	$(document).on('click', function(e){
		var e = e || window.event; //浏览器兼容性
		var _con = $('.rsp-bigimg');   // 图片区域
		var _con_btn = $('#rp-table .detailContainer li');
	    if(!_con_btn.is(e.target) && _con_btn.has(e.target).length === 0){
				$('.rsp-bigimg').fadeOut('fast');
    	}

	})
	
	var resetBtn= function(){	//所有按钮重置为close		
		for(var i=0; i<$('#rp-tableBody .Rsp-btn').length; i++){
			$('#rp-tableBody .Rsp-btn').eq(i).attr('status','close');
			$('#rp-tableBody .Rsp-btn').eq(i).children('span').html('&#xe88e;');
		}
	}


	var rsp_change_state = function(s, id){
    	$.ajax({
    	    url: '/userCommit/updateState',
    	    type: 'get',
    	    dataType: 'JSON',
    	    data: {
    	    	id : id,
    	    	state : s
    	    }
    	})
    	.done(function(odata) {
    	})
    	.always(function() {
    		$('#R-naviOld').click();
    	})
	}

	$('#Rsp-closer').click(function(){	//关闭按钮
		$('#rp-cancel').click();
	})

	$('#userResponse').click(function(){	//打开面板
		$('#userTelNum').val(userPhone);	//填充电话
		if(Rsp_flag){
			open_user_response();
			$('#R-naviNew').click();
			Rsp_flag= false;
		}else{
			$('#response-parent').show();
			$('#R-naviNew').click();
			return;
		}

	})


	//帮助手册
	$('#userGuide').click(function(){
		$('.SD-hd-null p').text('正在下载手册...');
		$('.SD-hd-null').css('left','45%');
		$('.SD-hd-null').show();
		setTimeout(function(){
			$('.SD-hd-null').hide();
			$('.SD-hd-null p').text('暂无数据！');
			$('.SD-hd-null').css('left','34%');
		},1000)
	})
/* 经停数据 */
var nodeList_jt =
    "<div class='cs_calculate_direct'> " +
    "<div> " +
    "<span>起始点：</span> " +
    "<div class='cs_calculate_wid'> " +
    "<input type='text' class='cs_calculate_jccq cs_in' tys='0' id='cs_calculate_qsd' /> "+"<div class='cs_calculate_tip'></div>"+
    "</div> " +
    "</div> " +
    "<div> " +
    "<span>终点：</span> " +
    "<div class='cs_calculate_wid'> " +
    "<input class='cs_calculate_jccq cs_in' tys='2' id='cs_calculate_zd' /> " +
    "<div class='cs_calculate_tip'></div>"+
    "</div> " +
    "</div> " +
    "<div> " +
    "<span>距离：</span> " +
    "<div> " +
    "<input type='text' id='cs_calculate_jl' /> " +
    "</div> " +
    "<span class='cs_calculate_icon'>km</span> " +
    "</div> " +
    "</div>"
/* 直飞数据 */
var nodeList_zf =
    "<div class='cs_calculate_direct'> " +
    "<div> " +
    "<span>起始点：</span> " +
    "<div class='cs_calculate_wid'> " +
    "<input type='text' class='cs_calculate_jccq cs_in' tys='0' id='cs_calculate_qsd' /> "+"<div class='cs_calculate_tip'></div>"+
    "</div> " +
    "</div> " +
            "<div> " +
                "<span>最大距离：</span> " +
                "<div> " +
                    "<input id='cs_maxVal'type='text' /> " +
                "</div> " +
                "<span class='cs_calculate_icon'>km</span> " +
            "</div> " +
    "<div> " +
    "<span>最小距离：</span> " +
    "<div> " +
    "<input id='cs_minVal'type='number' /> " +
    "</div> " +
    "<span class='cs_calculate_icon'>km</span> " +
    "</div> " +
    "</div> " ;
/* 甩飞数据 */
var nodeList_sf = "<div class='cs_calculate_direct'> " +
    "<div> " +
    "<span>起始点：</span> " +
    "<div class='cs_calculate_wid'> " +
    "<input type='text'  class='cs_calculate_jccq cs_in' tys='0' id='cs_calculate_qsd' /> "+"<div class='cs_calculate_tip'></div>"+
    "</div> " +
    "</div> " +
    "<div> " +
    "<span>经停点：</span> " +
    "<div class='cs_calculate_wid'> " +
    "<input class='cs_calculate_jccq cs_in' tys='1'  id='cs_calculate_jtd' /> " +
    "<div class='cs_calculate_tip'></div>"+
    "</div> " +
    "</div> " +
    "<div> " +
    "<span>最大距离：</span> " +
    "<div> " +
    "<input id='cs_maxVal'  type='text' /> " +
    "</div> " +
    "<span class='cs_calculate_icon'>km</span> " +
    "</div> " +
    "<div> " +
    "<span>最小距离：</span> " +
    "<div> " +
    "<input id='cs_minVal'  type='text' /> " +
    "</div> " +
    "<span class='cs_calculate_icon'>km</span> " +
    "</div> " +
    "</div> " ;

/* 申请列表 */
var cs_sqlb = "<div class='cs_measure' id='cs_measure'><div class='cs_measureHaed'><div>测算时间</div><div>航线名称</div><div>详情</div></div><div class='cs_measureBody' id='cs_measureBody'></div></div>";
var cs_csjgXx = " <div class='cs_ultimately' id='cs_ultimately'><div id='reckonTip'><div class='reckonTipO' id='reckonTipO'><h5>已提交申请</h5><span>测算完成后，系统会为您发送消息推送，请注意查收！</span></div><div class='reckonTipR' id='reckonTipR'><span id='reckonTipR_close'>&#xe84b;</span><p>该航线已有预估结果，是否重新预估？</p><div><div onclick='expected()'>查看预估结果</div> <div onclick='afresh()'>重新申请预估</div></div></div></div><div class='cs_ultimatelyHead'><div class='cs_ultimatelyHeadS'>测算结果</div><div>机场信息</div><div>航司信息</div></div><div class='cs_ultimatelyBody' id='cs_ultimatelyBody'></div><div class='cs_ultimatelyClear'><div id='cs_ultimatelyClearX' title='关闭'>&#xe611;</div><div id='cs_ultimatelyClearS' title='收起'>&#xe88e;</div></div></div>";
/* 机场对比视图数据 */
var cs_jcdbV ="<div id='cs_contrastBox'><div id='cs_contrastW'></div><div id='cs_contrastA'><div id='cs_table_query' class='cs_calculate_qj'><span>&#xe645;&nbsp;&nbsp;</span>导出表格</div><div id='cs_table_jump' class='cs_calculate_qj'><span>&#xe6bf;&nbsp;&nbsp;</span>下一步，成本测算</div></div><div id='cs_contrastBoxC'>&#xe612;</div></div>";
       


/* 初次加载数据 */
var nodeList =
    "<div class='cs_calculate_box'>"+
    "<div class='cs_calculate_head'>" +
    "<div class='cs_calculate_head_set cs_calculate_headS'>开航找点</div>" +
    "<div class='cs_calculate_headS'>申请列表</div> " +
    "</div> " +cs_sqlb+
    "<div id='cs_calculate_khzd'>"+
        "<div class='cs_calculate_item'> " +/////////////
            "<div class='cs_calculate_itemN cs_calculate_itemN_set'>甩飞</div> " +
            "<div class='cs_calculate_itemN'>直飞</div> " +
            "<div class='cs_calculate_itemN cs_calculate_itemB'>经停</div> " +
            "<div class='cs_calculate_itemZ' id='cs_calculate_itemZ'>&#xe611;</div> " +
        "</div> " +
        "<div id='cs_jcContrast'>" +   // 开航条件
            "<div class='cs_calculate_direct'> " +
                "<div> " +
                    "<span>起始点：</span> " +
                    "<div class='cs_calculate_wid'> " +
                        "<input type='text' class='cs_calculate_jccq cs_in' tys='0' id='cs_calculate_qsd' /> "+
                        "<div class='cs_calculate_tip'></div>"+
                    "</div> " +
                "</div> " +
                "<div> " +
                    "<span>经停点：</span> " +
                    "<div class='cs_calculate_wid'> " +
                        "<input class='cs_calculate_jccq cs_in' tys='1'  id='cs_calculate_jtd' /> " +
                        "<div class='cs_calculate_tip'></div>"+
                    "</div> " +
                "</div> " +
            "<div> " +
            "<span>最大距离：</span> " +
            "<div> " +
            "<input id='cs_maxVal' type='text'/> " +
            "</div> " +
            "<span class='cs_calculate_icon'>km</span> " +
            "</div> " +
            "<div> " +
            "<span>最小距离：</span> " +
            "<div> " +
            "<input id='cs_minVal'type='text'/> " +
            "</div> " +
            "<span class='cs_calculate_icon'>km</span> " +
            "</div> " +
            "</div> " +
            "<div class='cs_calculate_gjsx'><div class='cs_calculate_gjsxIcon'><div>高级筛选&nbsp;&nbsp;<span class='cs_calculate_gjsxIconR' id='cs_calculate_gjsxIconC'>&#xe6c8;</span></div></div><div class='cs_calculate_gjsxBox'>" +
        `
        <div class='cs_calculate_gjsxDetailed gjsxDetailed'>
            <span>飞行区等级：</span> 
            <div class='cs_calculate_gjsxDetailedC'>
                <input type='text' value='4F/4E/4D/4C' disabled='disabled' id='cs_fxqdj' class='disabledInp'>
                <div class='cs_calculateJcdjJxBox'>
                    <div class='cs_calculateJcdjJxBoxHead'>
                        <div> 
                            <div class='cs_calculateJcdjJxBoxChan cs_calculateJcdjJxBoxSet'>&#xe748;</div>
                        </div> 
                    </div> 
                    <div class='cs_calculateJcdjJxBoxBody'>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='4F'> <span></span>4F </div>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='4E'> <span></span>4E </div>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='4D'> <span></span>4D </div>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='4C'> <span></span>4C </div>
                    </div>
                 </div>
             </div>
             <div class='cs_calculate_gjsxDetailedIcon cs_calculateJcdjJx'> <span>&#xe6c8;</span> </div>
          </div>
            <div class='cs_calculate_gjsxDetailed'>
            <span>上一年度吞吐量：</span> 
            <div class='cs-throughput'>
                <input type='text' id='cs-throughput-min' />
                <span>～</span>
                <input type='text' id='cs-throughput-max' />
            </div>
          </div>
          <div class='cs_calculate_gjsxDetailed gjsxDetailed'>
            <span>机场类型：</span> 
            <div class='cs_calculate_gjsxDetailedC'>
                <input type='text' id='cs_jclx' value='高原机场/非高原机场/特殊机场/非特殊机场/口岸机场/非口岸机场/协调机场/非协调机场' disabled='disabled' class='disabledInp'>
                <div class='cs_calculateJcdjJxBox'>
                    <div class='cs_calculateJcdjJxBoxHead'>
                        <div> 
                            <div class='cs_calculateJcdjJxBoxChan cs_calculateJcdjJxBoxSet'>&#xe748;</div>
                        </div> 
                    </div> 
                    <div class='cs_calculateJcdjJxBoxBody'>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='高原机场'> <span></span>高原机场 </div>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='非高原机场'> <span></span>非高原机场 </div>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='特殊机场'> <span></span>特殊机场 </div>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='非特殊机场'> <span></span>非特殊机场 </div>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='口岸机场'> <span></span>口岸机场 </div>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='非口岸机场'> <span></span>非口岸机场 </div>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='协调机场'> <span></span>协调机场 </div>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='非协调机场'> <span></span>非协调机场 </div>
                    </div>
                 </div>
             </div>
             <div class='cs_calculate_gjsxDetailedIcon cs_calculateJcdjJx'> <span>&#xe6c8;</span> </div>
          </div>
           <div class='cs_calculate_gjsxDetailed gjsxDetailed'>
            <span>补贴政策：</span> 
            <div class='cs_calculate_gjsxDetailedC'>
                <input type='text' id='cs_zhbt' value='有补贴/无补贴' disabled='disabled' class='disabledInp'>
                <div class='cs_calculateJcdjJxBox'>
                    <div class='cs_calculateJcdjJxBoxHead'>
                        <div> 
                            <div class='cs_calculateJcdjJxBoxChan cs_calculateJcdjJxBoxSet'>&#xe748;</div>
                        </div> 
                    </div> 
                    <div class='cs_calculateJcdjJxBoxBody'>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='有补贴'> <span></span>有补贴 </div>
                        <div class='cs_calculateJcdjJxItem cs_calculateJcdjJxAf' tag='无补贴'> <span></span>无补贴 </div>
                    </div>
                 </div>
             </div>
             <div class='cs_calculate_gjsxDetailedIcon cs_calculateJcdjJx'> <span>&#xe6c8;</span> </div>
          </div>
          `
    +
    "</div></div>"+
            "<div id='cs_calculate_skip' class='cs_calculate_skip'> " +
                "<div id='cs_calculate_query' class='cs_calculate_qj cs_button'> " +
                    "<span>&#xe691;&nbsp;&nbsp;</span>查询目标机场 " +
                "</div> " +
                "<div id='cs_calculate_jump' class='cs_calculate_qj cs_button cs_button_gray'> " +
                    "<span>&#xe6c2;&nbsp;&nbsp;</span>跳过机场对比 " +
                "</div> " +
            "</div>"+
        "</div>"+
        "<div id='cs_information'>" +
            "<div class='cs_informationI' id='cs_informationIqs'> " +
                "<span>起始点：</span> " +
                "<div class='cs_calculate_wid'> " +
                    "<input type='text' class='cs_calculate_jccq cs_in'  tys='0' id='cs_information_q' /> "+
                    "<div class='cs_calculate_tip'></div>"+
                "</div> " +
            "</div> " +
            "<div class='cs_informationI' id='cs_informationIjt'> " +
                "<span>经停点：</span> " +
                "<div class='cs_calculate_wid'> " +
                    "<input type='text' class='cs_calculate_jccq cs_in'  tys='1' id='cs_information_j' /> "+
                    "<div class='cs_calculate_tip'></div>"+
                "</div> " +
            "</div> " +
            "<div class='cs_informationI' id='cs_informationIzd'> " +
                "<span>终点：</span> " +
                "<div class='cs_calculate_wid'>" +
                    "<input type='text' class='cs_calculate_jccq cs_in' tys='2' id='cs_information_z' /> "+
                    "<div class='cs_calculate_tip'></div>"+
                "</div> " +
            "</div> " +
            "<div class='cs_informationItemW'><span>航路：</span><div class='cs_informationItemWbox'><div><div class='cs_informationItemWyy'><span class='cs_informationIcon'>&#xe662;</span><span>选择已有航路</span></div><div class='cs_informationItemWyyx'><div id='cs_informationItemWyyxCt' tag=''></div><span id='cs_informationItemWyyxC'>&#xe6c8;</span><div class='cs_informationItemWset' id='cs_informationItemWyyxBox'></div></div></div><div><span class='cs_informationIcon'>&#xe662;</span><span id='cs_selfd'>自定义航路</span><span id='cs_selfdbtn'>已经绘制航路<span title='删除绘制' id='delLines'>&#xe69e;</span><span></div></div></div><div class='cs_informationItem0'><span>航距：</span><div><input id='cs_hjVal'type='text' /></div><span class='cs_calculate_icon'>km</span></div><div class='cs_calculate_gjsxDetailed cs_calculate_gjsxDetailedX gjsxDetailed'><span>航司：</span><div id='detailedC' class='cs_calculate_gjsxDetailedC cs_calculate_gjsxDetailedCX'><span class='clear-content'>X</span><input type='text' disabled='disabled' id='cs_hsSet'><div  class='cs_calculateJcdj' id='cs_hsList'></div></div><div class='cs_calculate_gjsxDetailedIcon' id='cs_hsListIcon'><span>&#xe6c8;</span></div></div><div class='cs_informationItem2'><div class='cs_informationSetMin'><span>机型：</span><div class='cs_informationSetMinInput'><span class='clear-content'>X</span><input type='text' disabled='disabled' id='cs_jxSet'><div id='cs_jxList'></div> </div><div class='cs_informationSetMinIcon' id='cs_jxListIcon'><span>&#xe6c8;</span></div></div><div class='cs_informationSetI'><span>成本：</span><div class='cs_informationSetIInput'><input type='text' id='cs_cbSet' /></div><span class='cs_calculate_icon'>w/h</span></div></div> <div class='cs_informationItem2'><div class='cs_informationSetI'><span>座位数：</span><div class='cs_informationSetIInput'><input type='text' id='cs_zwsSet' /></div><span class='cs_calculate_icon'>个</span></div><div class='cs_informationSetI'><span>航速：</span><div class='cs_informationSetIInput'><input type='text' id='cs_sdSet' /></div><span class='cs_calculate_icon'>km</span></div></div> "+
            "<div class='cs_informationItem2'><div class='cs_informationSetI'><span>飞行时间：</span><div class='cs_informationSetIInput'><input type='text' id='cs_ifxsj' readonly='readonly' /></div><span class='cs_calculate_icon'>h</span></div><div class='cs_informationSetI'><span>轮档时间：</span><div class='cs_informationSetIInput'><input type='text' id='cs_ildsj' /></div><span class='cs_calculate_icon'>h</span></div></div> " +
            "<div class='cs_informationItem2'><div class='cs_informationSetS'><span>开航时间：</span><div class='cs_informationSetIInput'><input type='text' id='cs_khsjSet' onClick='calendar.show({ id: this })' /></div></div><div class='cs_informationSetS'><span>停航时间：</span><div class='cs_informationSetIInput'><input type='text' id='cs_thsjSet' onClick='calendar.show({ id: this })' /></div></div></div><div class='cs_calculate_skip'><div id='cs_informationBack' class='cs_calculate_qj cs_button'><span>&#xe68a;&nbsp;&nbsp;</span>返回上一步</div><div id='cs_informationBg' class='cs_calculate_qj cs_button'><span>&#xe6bf;&nbsp;&nbsp;</span>开始测算 </div></div>"+
        "</div>"+
    "</div>"+
    "<div>"+
    "</div>"+

    "</div>"+
    "<div class='cs_jcdb cs_jcdb_none' id='cs_jcdb'>" + //机场对比
    "<div class='cs_jcdb_t'>"+
    "<div>机场对比</div>"+
    "<span id='cs_jcdb_addDb'>&#xe6b0;</span>"+
    "</div>"+
    "<div id='cs_jcdb_b'>" +
    "</div>"+
    "<div class='cs_calculate_skip cs_jcdb_bgdb'> " +
    "<div id='cs_calculate_bgdb' class='cs_jcdb_bgdbN cs_button'> " +
    "<span>&#xe6c0;&nbsp;&nbsp;</span>开始对比 " +
    "</div> " +
    "</div>"+
    "</div>"+
    "<div id='cs_contrastC'>" +
        cs_jcdbV+
        cs_csjgXx+
    "</div>"
;
    // 构造航路

    var constructionSeaway = function(){
	    var csy_amapList = [];
        for(var i = 0 ;i < airllist.airInfoDataList.length; i++){  //获取本场的坐标
            if(airllist.airportCode == airllist.airInfoDataList[i].iata){
                csy_amapList.push({
                    name:airllist.airInfoDataList[i].airInfoName,
                    value:[airllist.airInfoDataList[i].city_coordinate_w,airllist.airInfoDataList[i].city_coordinate_j,100],
                    type:2
                });
                break;
            };
        };

        var seaway = {},
        newmap = {
            "styleJson": [
                { //海洋颜色
                    "featureType": "water",
                    "elementType": "all",
                    "stylers": {
                        "color": "#d6d6d5",
                        "visibility": "on"
                    }
                },
                {   // 陆地颜色
                    "featureType": "land",
                    "elementType": "all",
                    "stylers": {
                        "color": "#f6f6f6",
                        "visibility": "on"
                    }
                },
                {   //边境线
                    "featureType": "boundary",
                    "elementType": "all",
                    "stylers": {
                        "color": "#bdbdbd",
                        "visibility": "on"
                    }
                },
                {   //城市主路
                    "featureType": "arterial",
                    "elementType": "all",
                    "stylers": {
                        "visibility": "off"
                    }
                },
                {   //高速公路
                    "featureType": "highway",
                    "elementType": "all",
                    "stylers": {
                        "visibility": "off"
                    }
                },
                {  //行政标注
                    "featureType": "label",
                    "elementType": "all",
                    "stylers": {
                        "visibility": "off"
                    }
                }]
        },
        oldmap = {
            "styleJson":[
                {
                    "featureType":"water",
                    "elementType":"all",
                    "stylers":{"color":"#071327","visibility":"on"}
                },{
                    "featureType":"land",
                    "elementType":"all",
                    "stylers":{"color":"#223350","visibility":"on"}
                },{
                    "featureType":"boundary","elementType":"all","stylers":{"color":"#465b6c","visibility":"on"}
                },{
                    "featureType":"arterial","elementType":"all","stylers":{"visibility":"off"}
                },{
                    "featureType":"highway","elementType":"all","stylers":{"visibility":"off"}
                },{
                    "featureType":"label","elementType":"all","stylers":{"visibility":"off"}
                }
            ]
        },
        selfPort = {
            type: 'scatter',
            coordinateSystem: 'bmap',
            data:csy_amapList,
            zlevel: 15,
            symbolSize: [15, 20],
            symbol:planePath2,
            symbolOffset:[0,-8],
            itemStyle: {
                normal:{
                    color:'#d85230'
                }
            }
        };
        
        seaway.hj_ops = {
            "title":{"show":false},
            "bmap":{
                "center":["110.47","32.40"],
                "zoom":6,
                "color":"red",
                "roam":"move",
                "mapStyle": userUuid?newmap:oldmap
            },
            "tooltip":{show:false,"showDelay":0,"enterable":"true","alwaysShowContent":"true","triggerOn":"click","trigger":"item"},
            "series":[]
        };

        seaway.surper = function() {
            myChart.setOption(this.hj_ops,true); // 重置地图+本场点
            this.map = myChart.getModel().getComponent('bmap').getBMap();
        };
        seaway.surper();
        return seaway;
    };

	// 航路测算功能
    var removeDuplicatedItem = function (ar) {
        var ret = [];
        for (var i = 0, j = ar.length; i < j; i++) {
            if (ret.indexOf(ar[i]) === -1) {
                ret.push(ar[i]);
            }
        }
        return ret;
    }
    $("#calculate-back").click(function () {
        measureData = {
            measure:false,
            type:0,
            data:{
                s:"",
                z:"",
                j:"",
                maxjl:"",
                minjl:"",
                jl:""
            },
            stage:0
        };
        $("#delLines").click();
        $("#pla-btn-qhs,.pla-search,#line-kg,.pla-tool,#nationSeaerch").show();
        $("#cs_tag,.pla-zoom,#calculate-back").hide();
        $(".pla-zoom").show();
        $("#pla-data").css("display","flex");
        $("#cs_calculate").html("");
        $(this).removeClass("calculate-tip");

        switch(stated){
            case 0:
                myChart.setOption(stickOption, true);
                break;
            case 1:
                $('.pla-btn-switch').attr('tag','line').click();
                break;
            case 2:
                $('#global-routes').click();
                break;
        }
    });
    $("#calculate-back").mouseover(function () {
        $(this).addClass("calculate-tip");
    });
    $("#calculate-back").mouseout(function () {
        $(this).removeClass("calculate-tip");
    });
    
    
    var expected =function () {
        var cs_xinxd = cs_xinx;
        var eda = {
            fltRteCd:cs_xinxd.fltRteCd,
            startTime:cs_xinxd.startTime,
            endTime:cs_xinxd.endTime
        };
        $.ajax({
            url:"/applyCalculation",
            type:"post",
            data:eda,
            success:function(ds){
                afreshNode(ds.data);
                $("#reckonTipR_close").click();
            }
        })
    };
    var afresh = function () {
        var cs_xinxd = cs_xinx;
        $.ajax({
            url:"/goOnApplyCalculation",
            type:"post",
            data:cs_xinxd,
            success:function(suc){
                if(suc.success){
                    $("#reckonTip").toggle();
                    $("#reckonTipO").toggle();
                    setTimeout(function () {
                        $("#reckonTip").toggle();
                        $("#reckonTipO").toggle();
                    },1500);
                }else{
                    alert("申请测算失败")
                };
            }
        });
    };
    var ultimatelySqsy = function () {
        $.ajax({
            url:"/applyCalculation",
            type:"get",
            data:cs_xinx,
            success:function (dt) {
                if(dt.success){
                    if(dt.isExist){
                        $("#reckonTip").toggle();
                        $("#reckonTipR").toggle();
                    }else{
                        $("#reckonTip").toggle();
                        $("#reckonTipO").toggle();
                        setTimeout(function () {
                            $("#reckonTip").toggle();
                            $("#reckonTipO").toggle();
                        },1500);
                        cslb();
                    };
                }else{
                    alert(dt.msg);
                };
            }
        });
    };
    var afreshNode = function (focastResult) {
        var djNode = "";
        djNode +="<div class='cs_ultimatelyYgT'>";
        for (var i = 0;i < focastResult.length;i ++){
            djNode +="<div class='cs_ultimatelyYgTI' tag='"+i+"'><p>"+focastResult[i].msg+"</p><p>"+focastResult[i].time+"</p></div>";
        };
        djNode +="</div>";
        djNode +="<table class='cs_ultimatelyTable1' id='cs_ultimatelyTable1' cellpadding='0' cellspacing='0'></table>";
        var nae = 'cs_ultimatelyTable1';
        djNode +="<span style='float: right'>*以上结果仅提供参考，如需详细测算结果，请与我们联系</span><div class='cs_ultimatelyBtn cs_button' onclick='method1("+nae+")'>导出表格</div>";
        $("#cs_ultimatelyCbcsX").html(djNode);
    };
    var cs_measureXX = function (data) {
        $("#cs_contrastBox").css("display","none");
        $("#cs_ultimately").css("display","block");
        $("#cs_contrastC").css("display","block");
        for(var i = 0;i < data.data.airportList.length;i ++){
            for(var key in data.data.airportList[i]){
                if(data.data.airportList[i][key] == undefined || data.data.airportList[i][key] == 'null' || data.data.airportList[i][key] == ''){
                    data.data.airportList[i][key] = "-";
                };
            };
        };
        for(var key in data.data.airCompanyInfo){
            if(data.data.airCompanyInfo[key] == undefined || data.data.airCompanyInfo[key] == 'null' || data.data.airCompanyInfo[key] == ''){
                data.data.airCompanyInfo[key] = "-";
            };
        };
        for(var key in data.data.focastResult){
            if(data.data.focastResult[key] == undefined || data.data.focastResult[key] == 'null' || data.data.focastResult[key] == ''){
                data.data.focastResult[key] = "-";
            };
        };
        var dad = data.data.focastResult.fltRteCd.split("");
        var resultCode = "";
        var resultName = "";
        for(var j = 0,h = -3; j < dad.length/3 ;j ++){
            h += 3;
            resultCode += cs_codeZname(data.data.focastResult.fltRteCd.substr(h,3)).iata + (dad.length/3 == (j+1) ? "":"-");
            resultName += cs_codeZname(data.data.focastResult.fltRteCd.substr(h,3)).airInfoName + (dad.length/3 == (j+1) ? "":"-");
        };

        // 拼接测算结果节点
        var djNode ="";
        djNode += "<div id='cs_ultimatelyCbcs' class='cs_ultimatelyContentItem' style='display: block'>";
        djNode += "<div class='cs_ultimatelyCbcs0'><p>成本测算</p><div class='cs_ultimatelyItem1'> <div> <div> <div>航线</div> <div title='"+resultName+"'>"+resultName+"</div></div><div><div>座位数</div><div>"+data.data.focastResult.sites+"个</div></div><div><div>开航时间</div><div>"+data.data.focastResult.startTime+"</div></div></div><div><div><div>航司</div><div>"+data.data.focastResult.airCompanyName+"</div></div><div><div>成本/时</div><div>"+data.data.focastResult.hourCost+"W</div></div><div><div>停航时间</div><div>"+data.data.focastResult.endTime+"</div></div></div><div><div><div>航距</div><div>"+data.data.focastResult.distance+"km</div></div><div><div>航速</div><div>"+data.data.focastResult.speed+"km/h</div></div><div><div>飞行时间</div> <div>"+data.data.focastResult.flyTime+"h</div></div></div><div><div><div>机型</div><div>"+data.data.focastResult.aircraftModel+"</div></div><div><div>成本预算</div><div>"+data.data.focastResult.cost+"W</div></div><div><div>轮档时间</div><div>"+data.data.focastResult.ldsj+"h</div></div></div></div></div>";
        djNode += "<div class='cs_ultimatelyCbcs0' id='cs_ultimatelyCbcsX'>";

        djNode +="</div></div>";
        // 拼接机场数据节点
        djNode +="<div id='cs_ultimatelyJcxx' class='cs_ultimatelyContentItem'><div class='cs_ultimatelyJcxxH'>";
        for(var i = 0;i < data.data.airportList.length;i ++){
            djNode +="<div tag='"+i+"'>"+data.data.airportList[i].airPortName+"</div>";
        };
        djNode +="</div><div class='cs_ultimatelyItem1 cs_ultimatelyItem1x' id='cs_ultimatelyJcxxBox'></div>";
        djNode +="<div class='cs_zbox'><div><div>旅客吞吐量</div><div id='cs_lkttl'></div></div><div><div>货物吞吐量</div><div id='cs_hwttl'></div></div><div><div>起降架次</div><div id='cs_qjjc'></div></div></div>";
        djNode +="<div class='cs_ultimatelyHsxxBox' id='cs_ultimatelyJcxxBoxPd'></div></div>";

        // 拼接航司信息

        djNode +="<div id='cs_ultimatelyHsxx' class='cs_ultimatelyContentItem'><div class='cs_ultimatelyItem1'><div><div><div>航司名</div><div>"+data.data.airCompanyInfo.airlnCd+"</div></div><div><div>成立时间</div><div>"+data.data.airCompanyInfo.establishTime+"</div></div><div><div>基地分布</div><div title='"+data.data.airCompanyInfo.baseDistribution+"'>"+data.data.airCompanyInfo.baseDistribution+"</div></div><div><div>航空联盟</div><div>"+data.data.airCompanyInfo.airlineAlliance+"</div></div></div><div><div><div>三字码</div><div>"+data.data.airCompanyInfo.icao+"</div></div><div><div>总部地点</div><div>"+data.data.airCompanyInfo.headquartersLocation+"</div></div><div><div>通航国家</div><div title='"+data.data.airCompanyInfo.shippingCountry+"'>"+data.data.airCompanyInfo.shippingCountry+"</div></div></div><div><div><div>二字码</div><div>"+data.data.airCompanyInfo.iata+"</div></div><div><div>所属航系</div><div>"+data.data.airCompanyInfo.systemAirpot+"</div></div><div><div>通航机场</div><div title='"+data.data.airCompanyInfo.navigationAirport+"'>"+data.data.airCompanyInfo.navigationAirport+"</div></div></div></div><div class='cs_ultimatelyHsxxBox'> <div class='cs_ultimatelyHsxxHe'> <div>机型及数量</div> <div>共"+data.data.airCompanyInfo.planeList.length+"条</div></div><div class='cs_ultimatelyItem2'><div>";
        for(var i = 0;i<data.data.airCompanyInfo.planeList.length;i ++){
            djNode +="<div><div>"+data.data.airCompanyInfo.planeList[i].airportType+"</div><div>数量</div> <div>"+data.data.airCompanyInfo.planeList[i].number+"</div></div>";
        };
        djNode +="</div></div></div></div>";
        $("#cs_ultimatelyBody").html(djNode);

        if(data.data.focastResult.map == "-"){
            var ndjNode ="<p>收益预估</p><span style='float: left'>如需了解更多未来收益情况，点击申请收益预估</span><div class='cs_ultimatelySqsy cs_button' onclick='ultimatelySqsy()'>申请收益预估</div>";
            $("#cs_ultimatelyCbcsX").html(ndjNode);
        }else {
            afreshNode(data.data.focastResult.map);
        };
        $("#reckonTipR_close").click(function(){
            $("#reckonTip,#reckonTipO").toggle();
        });


        // 切换一级标题
        var cs_ultimatelyChangeK = function(num){
            var sasNode =" <table class='cs_ultimatelyTable1' id='cs_ultimatelyTable1' cellpadding='0' cellspacing='0'> <thead> <tr> <th>航段</th> <th>班次/月</th> <th>均班座位</th> <th>均班客量/（团/散）</th> <th>客座率</th> <th>均班收入（团/散）</th> <th>座公里收入</th> <th>均班折扣（团/散）</th></tr></thead>";
            if(data.data.focastResult.map != "-"){
                if(data.data.focastResult.map[num].data.length > 0){
                    sasNode +="<tbody>";
                    for(var i = 0;i < data.data.focastResult.map[num].data.length;i ++){
                        var code = "";
                        var name = "";
                        for(var y = 0;y < data.data.focastResult.map[num].data[i].leg.split("-").length;y ++){
                            var lsXX = cs_codeZname(data.data.focastResult.map[num].data[i].leg.split("-")[y]);
                            if(y == (data.data.focastResult.map[num].data[i].leg.split("-").length-1)){
                                code += lsXX.iata;
                                name += lsXX.airInfoName;
                            }else{
                                code += lsXX.iata +"-";
                                name += lsXX.airInfoName +"-";
                            }
                        }
                        sasNode +="<tr><td>"+name+"</td><td>"+data.data.focastResult.map[num].data[i].flightPerMonth+"</td><td>"+data.data.focastResult.map[num].data[i].avgSites+"</td><td>"+data.data.focastResult.map[num].data[i].avgVol+"</td><td>"+data.data.focastResult.map[num].data[i].plf+"</td><td>"+data.data.focastResult.map[num].data[i].avgIncome+"</td><td>"+data.data.focastResult.map[num].data[i].kmIncome+"</td><td>"+data.data.focastResult.map[num].data[i].avgDiscount+"</td></tr>";
                    };
                    sasNode +="</tbody>";
                }else{
                    sasNode +="<tbody><tr><td colspan='7'>暂无数据</td></tr></tbody>"
                };
            };
            sasNode +="</table>";
            $("#cs_ultimatelyTable1").replaceWith(sasNode);
        };
        $(".cs_ultimatelyYgTI").click(function(){
            cs_ultimatelyChangeK($(this).attr("tag"));
            $(this).addClass("cs_ultimatelyYgTSet").siblings().removeClass("cs_ultimatelyYgTSet");
        });
        $(".cs_ultimatelyYgTI").eq(0).click();
        // 切换机场信息二级标题
        var cs_ultimatelyJcxxF = function(num){
            num = Number(num);
            var sckNode = " <div class='cs_ultimatelyItem1 cs_ultimatelyItem1x' id='cs_ultimatelyJcxxBox'><div><div><div>机场名字</div><div>"+data.data.airportList[num].airPortName+"</div></div><div><div>四字码</div><div>"+data.data.airportList[num].icao+"</div></div><div><div>所在城市</div><div>"+data.data.airportList[num].city+"</div></div><div><div>区域</div><div>"+data.data.airportList[num].areaManager+"</div></div><div><div>战区</div><div>"+data.data.airportList[num].warZone+"</div></div><div><div>标高</div><div>"+data.data.airportList[num].airEle+"米</div></div><div><div>特殊机场</div><div>"+data.data.airportList[num].specialAirport+"</div></div><div><div>旅行准点率</div><div>"+data.data.airportList[num].releasePunctuality+"</div></div><div><div>灯光条件</div><div>"+data.data.airportList[num].lightingConditions+"</div></div><div><div>可起降机型</div><div title='"+data.data.airportList[num].modelCanHandle+"'>"+data.data.airportList[num].modelCanHandle+"</div></div><div><div>机位数量</div><div>"+data.data.airportList[num].planePositionNumber+"</div></div></div><div><div><div>三字码</div><div>"+data.data.airportList[num].iata+"</div></div><div><div>是否国际</div><div>"+(data.data.airportList[num].inter == 0 ? "否":"是")+"</div></div><div><div>飞行区等级</div><div>"+data.data.airportList[num].airfieldLvl+"</div></div><div><div>所在省份</div><div>"+data.data.airportList[num].province+"</div></div><div><div>机场类别</div><div>"+data.data.airportList[num].airportType+"</div></div><div><div>口岸</div><div>"+data.data.airportList[num].port+"</div></div><div><div>机场类型</div><div>"+data.data.airportList[num].airpotCls+"</div></div><div><div>特殊机场构成原因</div><div>"+data.data.airportList[num].specialAirportWhy+"</div></div><div><div>消防等级</div><div>"+data.data.airportList[num].fireLvl+"</div></div><div><div>允许起降时间</div><div>"+data.data.airportList[num].allowTheTakeoffAndLanding+"</div></div></div><div><div><div>所属机场集团</div><div title='"+data.data.airportList[num].membershipGroup+"'>"+data.data.airportList[num].membershipGroup+"</div></div><div><div>通航时间</div><div>"+data.data.airportList[num].departureTime+"</div></div><div><div>机场专线</div><div>"+data.data.airportList[num].airportShuttleMetro+"</div></div><div><div>机场巴士</div><div>"+data.data.airportList[num].airportBus+"</div></div><div><div>距市区距离</div><div>"+data.data.airportList[num].distanceFromDowntown+"</div></div><div><div>国际航点</div><div>"+data.data.airportList[num].international+"</div></div><div><div>国际航点录入时间</div><div>"+data.data.airportList[num].internationalTime+"</div></div><div><div>国内航点</div><div>"+data.data.airportList[num].domestic+"</div></div><div><div>国内在飞航班</div><div>"+data.data.airportList[num].inTheFlight+"</div></div><div><div>国内航点及航线统计时间</div><div>"+data.data.airportList[num].inTheFlightTime+"</div></div></div></div>";
            $("#cs_ultimatelyJcxxBox").replaceWith(sckNode);
            sckNode = " <div class='cs_ultimatelyHsxxBox' id='cs_ultimatelyJcxxBoxPd'><div class='cs_ultimatelyHsxxHe'><div>机场跑道数据</div><div>共"+(data.data.airportList[num].runwaythedetailList.length)+"条</div></div><div class='cs_ultimatelyItem3'><div>";
            if(data.data.airportList[num].runwaythedetailList != '-'){
                data.data.airportList[num].runwaythedetailList.forEach(function (dis) {
                    if(dis != null){
                        sckNode += "<div><div>跑道</div><div class='cs_ultimatelyItem_bh'><div><div>编号</div><div>"+dis.runwayNumber+"</div></div><div><div>等级</div><div>"+(dis.runwayLvl == null ? "" : dis.runwayLvl)+"</div></div></div><div class='cs_ultimatelyItem_bh'><div><div>长度</div><div>"+dis.runwayLENGTH+"</div></div><div><div>宽度</div><div>"+dis.runwayWidth+"</div></div></div></div>";
                    };
                });
            }else{
                sckNode += "<div><div>无数据</div><div class='cs_ultimatelyItem_bh'><div><div></div><div></div></div><div><div></div><div></div></div></div><div class='cs_ultimatelyItem_bh'><div><div></div><div></div></div><div><div></div><div></div></div></div></div>";
            }
            sckNode +="</div></div></div>";
            $("#cs_ultimatelyJcxxBoxPd").replaceWith(sckNode);


            var yearlist = [];
            var passengerThroughput = [];
            var goodsThroughput = [];
            var takeOffAndLandingFlights = [];
            data.data.airportList[num].flowofsubsidiaryList.forEach(function (q) {
                yearlist.push(q.year);
                passengerThroughput.push({value:q.passengerThroughput,symbol:"circle",symbolSize:8,itemStyle:{normal:{color:"#4455a1"}}});
                goodsThroughput.push({value:q.goodsThroughput,symbol:"circle",symbolSize:8,itemStyle:{normal:{color:"#4455a1"}}});
                takeOffAndLandingFlights.push({value:q.takeOffAndLandingFlights,symbol:"circle",symbolSize:8,itemStyle:{normal:{color:"#4455a1"}}});
            });
            var tbImgOption = {
                tooltip: {
                    trigger: 'false'
                },
                grid: {
                    left: '3%',
                    right: '15%',
                    bottom: '3%',
                    top:'7%',
                    containLabel: true
                },
                toolbox: {
                    show:false
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    axisLine:{
                        show:false
                    },
                    axisLabel:{
                        color:"#a7a7a7"
                    },
                    axisTick:{
                      show:false
                    },
                    data: yearlist.reverse()
                },
                yAxis: {
                    type: 'value',
                    axisTick:{
                        show:false
                    },
                    axisLabel:{
                        color:"#a7a7a7",
                        formatter: '{value} km'
                    },
                    splitLine:{
                        show:true,
                        lineStyle:{
                            type:"dashed"
                        }
                    },
                },
                series: [
                    {
                        name:'',
                        type:'line',
                        data:'',
                        lineStyle:{
                            normal:{
                                color:"#4455a1",
                                width:3
                            }
                        }

                    }
                ]
            };
            var cs_lkttl = echarts.init(document.getElementById("cs_lkttl"));
            tbImgOption.series[0].data = passengerThroughput.reverse();
            tbImgOption.series[0].name = "旅客吞吐量";
            tbImgOption.yAxis.axisLabel.formatter = "{value} /人";
            cs_lkttl.setOption(tbImgOption);

            var cs_hwttl = echarts.init(document.getElementById("cs_hwttl"));
            tbImgOption.series[0].data = goodsThroughput.reverse();
            tbImgOption.series[0].name = "货物吞吐量";
            tbImgOption.yAxis.axisLabel.formatter = "{value} /吨";
            cs_hwttl.setOption(tbImgOption);

            var cs_qjjc = echarts.init(document.getElementById("cs_qjjc"));
            tbImgOption.series[0].data = takeOffAndLandingFlights.reverse();
            tbImgOption.series[0].name = "起降架次";
            tbImgOption.yAxis.axisLabel.formatter = "{value} /次";
            cs_qjjc.setOption(tbImgOption);
        };
        $(".cs_ultimatelyJcxxH>div").click(function(){
            $(this).addClass("cs_ultimatelyJcxxHSet").siblings().removeClass("cs_ultimatelyJcxxHSet");
            cs_ultimatelyJcxxF($(this).attr("tag"));
        });
    };
    // 测算结果信息绑定数据
    var cs_measureBind = function (num) {
        if(num != "sq"){
            $.ajax({
                type:"post",
                url:"/getMeasureResultDetail",
                data:{
                    id:num
                },
                success:function(data){
                    if(data.success){
                        cs_measureXX(data);
                    };
                }
            });
        };
        $("#cs_ultimatelyBody").html();
    };
    var allLines = [];
    var queryAllLines = function () {
        $.ajax({
            url:'/restful/getAllFlightAirlineData',
            type:"get",
            dataType:"jsonp",
            success:function (lines) {
                allLines = [];
                for(var yup in lines.success){
                    for(var x = 0;x < lines.success[yup].length;x ++){
                        var arr1 = GPS.wd_encrypt(Number(lines.success[yup][x].stratCityPoit2)/10000,Number(lines.success[yup][x].stratCityPoit1)/10000);
                        var arr2 = GPS.wd_encrypt(Number(lines.success[yup][x].endCityCityPoit2)/10000,Number(lines.success[yup][x].endCityCityPoit1)/10000);
                        lines.success[yup][x].stratCityPoit2 = arr1.lat;
                        lines.success[yup][x].stratCityPoit1 = arr1.lon;
                        lines.success[yup][x].endCityCityPoit2 = arr2.lat;
                        lines.success[yup][x].endCityCityPoit1 = arr2.lon;
                    };
                    allLines.push({
                        color:"#6ca1ce",
                        data:lines.success[yup]
                    })
                };
            }
        });
    };

    /**
     * @param  measure{boolean}
     * @param  type{Number} 0、代表甩飞，1、代表直飞，2、代表经停
     * @param  data{Object} s、起始点，z、终点，j、经停点。（均代表机场名称），maxjl、最大距离，minjl、最小距离，jl、距离
     * @param  stage{Number} 0、开航找点一级菜单，1、跳过机场对比耳机菜单
     * @author yj 9/11
     * */
    var measureData = {
        measure:false,
        type:0,
        data:{
            s:"",
            z:"",
            j:"",
            maxjl:"",
            minjl:"",
            jl:""
        },
        stage:0
    };

    /**
     * 储存数据
     * @param  data{Array}
     * @param  hj{Number} 航距
     * @param  open{Number} 表示是否处于绘制航路状态中
     * @param  ts{String}
     * @param  pointEnd{String} 最后的一个点
     * @author yj 9/11
     * */
    var glData = {
        data:[],
        hj:0,
        open:false,
        ts:"",
        pointEnd:""
    };

    /**
     * 储存数据
     * @param ins {object}  储存自定义航路数据
     * @param out {object}  储存补全航路数据
     * */
    var hlhc = {
        ins:{
            data:'',
            lslines:''
        },
        out:{
            data:{},
            lslines:0
        }
    };



    var cs_xinx = {};
    var cslb = function () {
        $.post('/findMeasureHistoryList', function(data) {
            var cs_measureNode = "";
            if(data.data.length > 0){
                for(var i = 0;i < data.data.length; i++){
                    var dad = data.data[i].fltRteCd.split("");
                    var resultCode = "";
                    var resultName = "";
                    for(var j = 0,h = -3; j < dad.length/3 ;j ++){
                        h += 3;
                        resultCode += cs_codeZname(data.data[i].fltRteCd.substr(h,3)).iata + (dad.length/3 == (j+1) ? "":"-");
                        resultName += cs_codeZname(data.data[i].fltRteCd.substr(h,3)).airInfoName + (dad.length/3 == (j+1) ? "":"-");
                    };
                    if(data.data[i].state != "4"){
                        cs_measureNode +="<div class='cs_measureItem'><div class='"+(data.data[i].state == "3" ? "cs_measureItemUpdate" : "")+"'>"+(data.data[i].applyMeasureTime.replace(/-/g, '.'))+"</div> <div tag='"+resultName+"' title='"+resultName+"'>"+resultCode+"</div><div class='"+(data.data[i].state == "1" ? "cs_measureItemSnone":"cs_measureItemSq")+"' tag='"+data.data[i].id+"'>"+(data.data[i].state == "3" ? "查看结果" : data.data[i].state == "2" ? "详情" : data.data[i].state == "1" ? "测算中...":"" )+"</div><div class='cs_measureItemIcon' tag='"+data.data[i].id+"'><div>&#xe84b;</div></div></div>";
                    };
                };
                $("#cs_measureBody").html(cs_measureNode);
                $(".cs_measureItemIcon").mouseover(function(){
                    $(this).css({"background-color":"#e25656","color":"white"}).children("div").html("删除");
                });
                $(".cs_measureItemIcon").mouseout(function(){
                    $(this).css({"background-color":"transparent","color":"#959595"}).children("div").html("&#xe84b");
                });
                $(".cs_measureItemIcon").click(function(){
                    var cs_measureItemIconId = $(this).attr("tag");
                    var cs_measureItemIconIndex = $(".cs_measureItemIcon").index($(this));
                    $.ajax({
                        type: "POST",
                        url: "/updateState",
                        data: {
                            id:cs_measureItemIconId,
                            state:4
                        },
                        success: function (data) {
                            if(data.success){
                                $(".cs_measureItem").eq(cs_measureItemIconIndex).remove();
                                // $("#cs_measureBody").html("<div class='cs_measureItemN'>申请列表为空！</div>");
                            }else{
                                alert("删除失败！");
                            }
                        }
                    });
                });
                //  查看测算详情 事件
                $(".cs_measureItemSq").click(function () {
                    cs_measureBind($(this).attr("tag"));
                });
            }else{
                $("#cs_measureBody").html("<div class='cs_measureItemN'>申请列表为空！</div>");
            };
        });
    };
	var calculate = function(){
        $("#searchReset").click();
	    if(localStorage.getItem(airllist.airportCode + "hy") == null)localStorage.setItem(airllist.airportCode + "hy",codeScu(airllist.airportCode).airInfoName);
        $("#calculate-back,#cs_tag,#cs_tagItem1").show();
        $("#advanced-filter-container,#nationSeaerch").hide();
        $("#cs_tagItem2,.pla-zoom,#pla-data,#pla-btn-qhs,.pla-search,#line-kg,.pla-tool").hide();

        // 声明测算数据
        var hz = function(e){
            if(glData.open){
                EBmapExtend.drawOption(e.point,true);
            };
        };
        var rz = function(e){
            // $("#cs_calculate").removeClass('cs_calculate-left');
            // $(".pla-tool").removeClass('pla-tool-top');
            // $("#calculate-back").removeClass('calculate-back-right');
            $("*").removeClass("cs-ing");
            csmap.map.removeEventListener("click", hz);
            EBmapExtend.tagLine();
            csmap.map.removeEventListener("mousemove", mom);
            if(glData.open){
                EBmapExtend.drawOption("",false);
            };
        }
        var mom = function(e){
            if(glData.open){
                EBmapExtend.tagLine(e.point);
            };
        };
        // 生成实例
        var csmap = "";
        if(csmap == ""){
            csmap = constructionSeaway();  //获取实例
            EBmapExtend = new EBmapExtendConstruction(csmap.map)
            var wkt_arr = [];
            for(var j = 0;j < airllist.airInfoDataList.length;j ++){
                wkt_arr.push(airllist.airInfoDataList[j].iata);
            };
            var cs_opData = {series:[],center:[]};
            cs_opData.series.push({
                type:"dots",  // 图标点图形
                data:wkt_arr, //  点的三字码
                style:{
                    color: HLCS_COLOR.wkt,
                    size: BMAP_POINT_SIZE_SMALLER,
                    shape: BMAP_POINT_SHAPE_CIRCLE
                }
            });
            EBmapExtend.setOption(cs_opData);
        };
        // 申请列表数据
        cslb();

        queryAllLines();   // 获取全国航路图

	    var addSx = function (cs_pString) {
            var cs_snode = "";
            for(var i = 0; i < airllist.airInfoDataList.length; i++ ){
                if(airllist.airInfoDataList[i].airInfoName.toString().toUpperCase().indexOf(cs_pString) != -1
                    || airllist.airInfoDataList[i].iata.toString().toUpperCase().indexOf(cs_pString) != -1
                    || airllist.airInfoDataList[i].icao.toString().toUpperCase().indexOf(cs_pString) != -1
                ){
                    cs_snode += "<div class='cs_calculate_jccqItem' tag='"+airllist.airInfoDataList[i].airInfoName.toString()+"'>"+airllist.airInfoDataList[i].airInfoName.toString()+"</div>";
                }
            }
            return cs_snode;
        }
        //  筛选历史记录
        var addSxH = function (cs_pString) {
            var cs_snode = "";
            var _ls = localStorage.getItem(airllist.airportCode + "hy");
            var _hsy = _ls.split(",");

            var _airName = codeScu(airllist.airportCode).airInfoName;

            cs_snode += "<div class='cs_calculate_jccqItem cs_calculate_jccqItem7' tag='"+_airName+"'>"+_airName+"</div>";
            _hsy.forEach(function(val){
                if(val != _airName){
                    cs_snode += "<div class='cs_calculate_jccqItem cs_calculate_jccqItem8' tag='"+val+"'>"+val+"</div>";
                };
            });
            return cs_snode;
        }
		$("#cs_calculate").html(nodeList);
        // 预绑定事件
        var bindE = function(){
            $(".cs_calculate_jccq").on("blur",function(){ // 失去焦点
                var cs_setTiST = $(this);
                var cs_setTiStCs = true;
                for(var i = 0; i < airllist.airInfoDataList.length; i++ ){
                    if(airllist.airInfoDataList[i].airInfoName.toString() == cs_setTiST.val().toString()){
                        air_space(measureData.type,cs_setTiST.val(),cs_setTiST,measureData.stage);
                        cs_setTiStCs = false;
                        break;
                    };
                };
                if(cs_setTiStCs){
                    cs_setTiST.val("");
                };
                setTimeout(function () {
                    cs_renoveNode(cs_setTiST.next("div"));
                },200)
            })
            $(".cs_calculate_jccq").on("focus",function(){   //获取焦点
                $(".cs-calculate-tp").removeClass("cs-calculate-tp");
                var cs_setTiST = $(this);
                var cs_pString = cs_setTiST.val().toString().toUpperCase();
                var cs_renxx = '';
                cs_renxx = addSxH();
                if(cs_renxx != ''){
                    cs_setTiST.next("div").addClass("cs_calculate_tipSet").addClass("over_tip_box_shadow").html(cs_renxx);
                };
                $(".cs_calculate_jccqItem").click(function(){
                    cs_setTiST.val($(this).attr("tag"));
                    cs_renoveNode(cs_setTiST.next("div"));
                    air_space( measureData.type,$(this),cs_setTiST,measureData.stage);
                });
            });
            $(".cs_calculate_jccq").on("input",function(){  //内容改变
                var cs_setTiST = $(this);
                var cs_pString = cs_setTiST.val().toString().toUpperCase();
                if(cs_pString != ""){
                    var cs_renxx = addSx(cs_pString);
                    if(cs_renxx != ""){
                        cs_setTiST.next("div").addClass("cs_calculate_tipSet").html(cs_renxx);
                    }else{
                        cs_setTiST.next("div").removeClass("cs_calculate_tipSet").html("");
                    }
                }else{
                    cs_setTiST.next("div").removeClass("cs_calculate_tipSet").html("");
                    switch (Number(cs_setTiST.attr('tys'))){
                        case 0:
                            measureData.data.s = '' ;
                            break;
                        case 1:
                            measureData.data.j = '' ;
                            break;
                        case 2:
                            measureData.data.z = '' ;
                            break;
                    }
                }
                $(".cs_calculate_jccqItem").click(function(){
                    if(cs_setTiST.hasClass("cs_in")){
                        air_space( measureData.type,$(this),cs_setTiST,measureData.stage);
                    };
                    var _hs = localStorage.getItem(airllist.airportCode + "hy");
                    if(_hs != null){
                        _hs = _hs.split(",");
                        var _val = codeScu($(this).attr("tag")).airInfoName;
                        if(_hs.indexOf(_val) == -1 ){
                            _hs.unshift(_val);
                        }
                    };
                    localStorage.setItem(airllist.airportCode + "hy",_hs)
                    cs_renoveNode(cs_setTiST.next("div"));
                });
            });
        };
        bindE();
	    // 打开关闭开关
        $("#cs_ultimatelyClearX").click(function(){
            $("#cs_contrastC").hide();

        });
        $("#cs_ultimatelyClearS").click(function(){
            $("#cs_ultimatelyBody").toggle();
        });

	    // 绑定机场信息切换事件
        $(".cs_ultimatelyHead>div").click(function () {
            $(this).addClass("cs_ultimatelyHeadS").siblings().removeClass("cs_ultimatelyHeadS");
            var tNum =Number($(".cs_ultimatelyHead>div").index($(this)));
            switch(tNum)
            {
                case 0:
                    $("#cs_ultimatelyCbcs").css("display","block");
                    $("#cs_ultimatelyJcxx").css("display","none");
                    $("#cs_ultimatelyHsxx").css("display","none");
                    break;
                case 1:
                    $("#cs_ultimatelyCbcs").css("display","none");
                    $("#cs_ultimatelyJcxx").css("display","block");
                    $("#cs_ultimatelyHsxx").css("display","none");
                    $(".cs_ultimatelyJcxxH>div").eq(0).click();
                    break;
                case 2:
                    $("#cs_ultimatelyCbcs").css("display","none");
                    $("#cs_ultimatelyJcxx").css("display","none");
                    $("#cs_ultimatelyHsxx").css("display","block");
                    break;
            }
        });

        /**
         *  清空已测自定义航路数据
         *  @author yj 2017/9/6
         * */
        $("#delLines").click(function(){
            hlhc.ins = {data:'', lslines:''};
            $("#cs_selfdbtn").hide();
            EBmapExtend.clearSelfLine();
            $('.cs_informationIcon').eq(1).removeClass('cs_informationIconS');
            EBmapExtend.drawLast();
        });

        /**
         * 开航找点清空数据按钮-绑定事件
         * @author yj 2017/9/6
         * */
        $("#cs_calculate_itemZ").click(function(){
            isTgJd = true;
            EBmapExtend.cleraOption();
            EBmapExtend.removeCompare();
            EBmapExtend.setOption(cs_opData);
            measureData.data = {
                s:"",
                z:"",
                j:"",
                maxjl:"",
                minjl:"",
                jl:""
            };
            $("#cs-throughput-min").val("");
            $("#cs-throughput-max").val("");
            $("#cs_hjVal").val("");
            $("#cs_hsList").html("");
            $("#cs_hsSet").val("").removeAttr("tag");
            $("#cs_jxSet").val("").removeAttr("tag");
            $("#cs_jxList").html("");
            $("#cs_cbSet").val("");
            $("#cs_zwsSet").val("");
            $("#cs_sdSet").val("");
            $("#cs_khsjSet").val("");
            $("#cs_thsjSet").val("");
            $("#cs_ifxsj").val("");
            $("#cs_ildsj").val("");
            $("#delLines").click();
            $('.cs_informationIcon').removeClass('cs_informationIconS');
            $('#cs_informationItemWyyxBox').html('');
            $('#cs_informationItemWyyxCt').html('');
            $('#cs_contrastBox,#cs_ultimately').hide();
            assemblyData();
        });
        $("#cs_calculate_itemZ").mouseover(function () {
            $(this).html("清空");
        });
        $("#cs_calculate_itemZ").mouseout(function () {
            $(this).html("&#xe611");
        });


        /**
         * 输入框最大距离绑定事件
         * */
        var tipMax = "";
        $('#cs_calculate_khzd').on('input','input',function(){
            clearTimeout(tipMin);
            if($(this).attr('id') == 'cs_maxVal'){
                $(".cs_Inputs").remove();
                var val = Number($(this).val());
                var reg = /^\d+(?=\.{0,1}\d+$|$)/;
                var _this = $(this);
                if(reg.test(val)){
                    if(val > 5000){
                        _this.after("<span class='cs_Inputs'>最大距离不能大于5000km！</span>");
                    }else{
                        if(measureData.data.minjl != ""){
                            if(val < measureData.data.minjl){
                                _this.after("<span class='cs_Inputs'>最大距离应大于最小距离</span>");
                            }else{
                                measureData.data.maxjl = val;
                            };
                        }else{
                            measureData.data.maxjl = val;
                        };
                    };
                }else{
                    _this.after("<span class='cs_Inputs'>输入格式错误！</span>");
                };
            }else if($(this).attr('id') == 'cs_minVal'){
                $(".cs_Inputs").remove();
                var val = Number($(this).val());
                var reg = /^\d+(?=\.{0,1}\d+$|$)/;
                var _this = $(this);
                if(reg.test(val)){
                    if(val < 5000){
                        if(measureData.data.maxjl != ""){
                            if(val > measureData.data.maxjl){
                                _this.after("<span class='cs_Inputs'>最小距离不能大于最大距离！</span>");
                            }else{
                                measureData.data.minjl = val;
                            };
                        }else{
                            measureData.data.minjl = val;
                        };
                    }else{
                        _this.after("<span class='cs_Inputs'>最大距离应小于5000km！</span>");
                    };
                }else{
                    _this.after("<span class='cs_Inputs'>输入格式错误！</span>");
                };
            }else if($(this).attr('id') == 'cs_calculate_jl'){
                var val = Number($(this).val());
                var reg = /^\d+(?=\.{0,1}\d+$|$)/;
                var _this = $(this);
                if(reg.test(val)){
                    if(val < 5000){
                        measureData.data.jl = val;
                    }else{
                        _this.after("<span class='cs_Inputs'>最大距离应小于5000km！</span>");
                    };
                }else{
                    _this.after("<span class='cs_Inputs'>输入格式错误！</span>");
                };
            };
            tipMax = setTimeout(function(){
                $(".cs_Inputs").remove();
            },1500);
        });
        $('#cs_calculate_khzd').on('blur','input',function(){
            var reg = /^\d+(?=\.{0,1}\d+$|$)/;
            var _this = $(this);
            if($(this).attr('id') == 'cs_maxVal'){
                var val = Number($(this).val());
                if(!reg.test(val) || val > 5000){
                    measureData.data.maxjl = "";
                    _this.val("");
                }else{
                    measureData.data.maxjl = _this.val();
                };
            }else if($(this).attr('id') == 'cs_minVal'){
                var val = Number($(this).val());
                if(!reg.test(val)){
                    measureData.data.minjl = "";
                    _this.val("");
                }else{
                    measureData.data.minjl = _this.val();
                };
            }else if($(this).attr('id') == 'cs_calculate_jl'){
                var val = Number($(this).val());
                if(!reg.test(val) || val > 5000){
                    measureData.data.jl = "";
                    _this.val("");
                }else{
                    measureData.data.jl = _this.val();
                };
            }
        })


        /**
         * 输入框最小距离绑定事件
         * */
        var tipMin = "";
        $("#cs_minVal").on("input",function(){

        });

        /**
         * 跳过机场 && 过滤测算面板的数据
         * @return null 无返回值
         * @author yj 2017/9/6
         * */
        var filAbly = function () {
            if(measureData.type == 0){
                measureData.data.z = "";
            }else if(measureData.type == 1){
                measureData.data.j = "";
                measureData.data.z = "";
            }else if(measureData.type == 2){
                measureData.data.j = "";
            };
        };

         /**
          * 更新输入框存储的数据
          * @param  无
          * @return 无
          * @author yj 2017/9/6
          * */
         var assemblyData = function () {
            $("#cs_calculate_qsd").val(measureData.data.s);
            $("#cs_calculate_jtd").val(measureData.data.j);
            $("#cs_calculate_zd").val(measureData.data.z);
            $("#cs_information_q").val(measureData.data.s);
            $("#cs_information_j").val(measureData.data.j);
            $("#cs_information_z").val(measureData.data.z);
            $("#cs_maxVal").val(measureData.data.maxjl);
            $("#cs_minVal").val(measureData.data.minjl);
            $("#cs_calculate_jl").val(measureData.data.jl);
            if(measureData.stage == 1){
                if(measureData.type == 1){
                    if(measureData.data.s != "" && measureData.data.z != ""){
                        var getFlightAirlineDatas ={
                            stratCity:cs_thereCode(measureData.data.s),
                            endCity:cs_thereCode(measureData.data.z)
                        };
                        queryGetFlightAirlineDatas(getFlightAirlineDatas);
                    };
                }else{
                    if(measureData.data.s != "" && measureData.data.j != "" && measureData.data.z != ""){
                        var getFlightAirlineDatas ={
                            stratCity:cs_thereCode(measureData.data.s),
                            pasCity1:cs_thereCode(measureData.data.j),
                            endCity:cs_thereCode(measureData.data.z)
                        };
                        queryGetFlightAirlineDatas(getFlightAirlineDatas);
                    };
                }
            };
         };

         /**
          * 开航时间选择 - 屏蔽事件
          * @author yj 9/11
          */
         $("#cs_khsjSet,#cs_thsjSet").bind('keydown', function(event) {
            return false;
         });

         /**
          * 开航时间选择 - 判断时间范围是否合理
          * @author yj 9/11
          * */

         $("#cs_khsjSet,#cs_thsjSet").on('blur',function(){
             var sThis = $(this);
             var elc = $(this).offset();
             setTimeout(function(){
                 if($("#cs_khsjSet").val() != "" && $("#cs_thsjSet").val() != ""){
                     if( new Date($("#cs_thsjSet").val()).getTime() < new Date($("#cs_khsjSet").val()).getTime()){
                         sThis.val("");
                         $("#csTip").css({"top":elc.top+ 22 + "px","left":elc.left + "px"}).html("开航时间不能大于停航时间");
                     };

                     setTimeout(function(){
                         $("#csTip").html('');
                     },1000)
                 };
             },200);
         });

         var men = true;
         // 上一年度吞吐量
        $('#cs-throughput-min,#cs-throughput-max').on('input',function(){
        	men =true;
            clearTimeout(setT);
            $("#csTip").html("");
            if($(this).val() != ''){
                var val = Number($(this).val());
                var _this = $(this);
                var min,max;
                var elc = $(this).offset();
                if($('#cs-throughput-min').val() != '')min = Number($('#cs-throughput-min').val());
                if($('#cs-throughput-max').val() != '')max = Number($('#cs-throughput-max').val());
                var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
                if(!reg.test(val)){
                    $("#csTip").css({"top":elc.top+ 28 + "px","left":elc.left + "px"}).html("输入格式不正确！");
                }else{
                    if(min != '' && max != ''){
                        if(min > max){
                            $("#csTip").css({"top":elc.top+ 28 + "px","left":elc.left + "px"}).html("最小值不能比最大值大！");
                            men = false;
                        }
                    }
                };
                setT = setTimeout(function(){
                    $("#csTip").html("");
                },1500);
            };
        });
        $('#cs-throughput-max,#cs-throughput-min').on('blur',function(){
            var _this = $(this);
            var val = Number($(this).val());
            var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
            var max =  $('#cs-throughput-max').val();
            var min =  $('#cs-throughput-in').val();
            var elc = $(this).offset();
            if(!reg.test(val) || men == false){
                _this.val('');
            };
            setT = setTimeout(function(){
                $("#csTip").html("");
            },1500);
        });


        /**
         * 机场选择信息判断提示
         * @param _type {String || Number} 用于区分是经停，起始点，到达点
         * @param _this {Object || String} 传递的是点击选择对象 || 机场名称
         * @param _thisI {Object} 传递的是输入框对象节点
         * @param _stage {Number} 区分的是查询机场（0）|| 手动输入 （1）
         * @return 无返回值
         * @author yj 2017/9/6
         */
        var air_space = function (_type,_this,_thisI,_stage) {
            _type = Number(_type);
            var _thisName = typeof _this == "object" ? _this.attr("tag") : _this;
            var _thiII= Number(_thisI.attr("tys"));  // 区分操作当前input的序列
            var _thisIP = _thisI.parent();
            var _thisTpS = function () {
                _thisIP.addClass("cs-calculate-tp");
                setTimeout(function(){
                    _thisIP.removeClass("cs-calculate-tp");
                },1500);
            };
            switch (_type)
            {
                case 0:
                    if(_stage == 0){
                        if(_thiII == 0){
                            if(measureData.data.j != _thisName){
                                measureData.data.s = _thisName;
                            }else{
                                _thisTpS();
                            };
                        }else if(_thiII == 1){
                            if(measureData.data.s != _thisName){
                                measureData.data.j = _thisName;
                            }else{
                                _thisTpS();
                            };
                        };
                    }else if(_stage == 1){
                        if(_thiII == 0){
                            if(measureData.data.j != _thisName && measureData.data.z != _thisName){
                                measureData.data.s = _thisName;
                            }else{
                                _thisTpS();
                            };
                        }else if(_thiII == 1){
                            if(measureData.data.s != _thisName && measureData.data.z != _thisName){
                                measureData.data.j = _thisName;
                            }else{
                                _thisTpS();
                            };
                        }else if(_thiII == 2){
                            if(measureData.data.s != _thisName && measureData.data.j != _thisName){
                                measureData.data.z = _thisName;
                            }else{
                                _thisTpS();
                            };
                        };
                    }
                    break;
                case 1:
                    if(_stage == 0){
                        measureData.data.s = _thisName;
                        measureData.data.j = "";
                        measureData.data.z = "";
                    }else{
                        if(_thiII == 0){
                            if(measureData.data.z != _thisName){
                                measureData.data.s = _thisName;
                            }else{
                                _thisTpS();
                            };
                        }else if(_thiII == 2){
                            if(measureData.data.s != _thisName){
                                measureData.data.z = _thisName;
                            }else{
                                _thisTpS();
                            };
                        };
                    };
                    break;
                case 2:
                    if(_stage == 0){
                        if(_thiII == 0){
                            if(measureData.data.z != _thisName){
                                measureData.data.s = _thisName;
                            }else{
                                _thisTpS();
                            };
                        }else if(_thiII == 2){
                            if(measureData.data.s != _thisName){
                                measureData.data.z = _thisName;
                            }else{
                                _thisTpS();
                            };
                        };
                    }else if(_stage == 1){
                        if(_thiII == 0){
                            if(measureData.data.j != _thisName && measureData.data.z != _thisName){
                                measureData.data.s = _thisName;
                            }else{
                                _thisTpS();
                            };
                        }else if(_thiII == 1){
                            if(measureData.data.s != _thisName && measureData.data.z != _thisName){
                                measureData.data.j = _thisName;
                            }else{
                                _thisTpS();
                            };
                        }else if(_thiII == 2){
                            if(measureData.data.s != _thisName && measureData.data.j != _thisName){
                                measureData.data.z = _thisName;
                            }else{
                                _thisTpS();
                            };
                        };
                    }
                    break;
            };
            assemblyData();
        };





        /* 绑定高级筛选事件 */
        $(".cs_calculate_gjsxDetailedIcon,.cs_calculate_gjsxIconR,.cs_informationSetMinIcon").click(function () {
            if($(this).hasClass("cs_calculateJcdjOpen")){
                $(this).removeClass("cs_calculateJcdjOpen");
            }else{
                $(this).addClass("cs_calculateJcdjOpen");
            }
        });

        /**
         * 机场对比与绑定事件
         * */
        $("#cs_jcdb").on("input","input",function(){
            var cs_setTiST = $(this);
            var cs_pString = cs_setTiST.val().toString().toUpperCase();
            if(cs_pString != ""){
                var cs_renxx = addSx(cs_pString);
                if(cs_renxx != ""){
                    cs_setTiST.next("div").addClass("cs_calculate_tipSet").html(cs_renxx);
                }else{
                    cs_setTiST.next("div").removeClass("cs_calculate_tipSet").html("");
                }
            };
            cs_setTiST.next("div").on('click','div',function(){
                var airName = $(this).attr('tag');
                cs_setTiST.next("div").removeClass("cs_calculate_tipSet").html("");
                EBmapExtend.compare[EBmapExtend.compare.indexOf("")] = codeScu(airName).iata;
                EBmapExtend.resetCompare();
            })
        });

        /* 关闭机场对比 */
        $("#cs_contrastBoxC").click(function(){
            $("#cs_contrastBox").css("display","none");
            $("#cs_ultimately").css("display","none");
        });
        /* 开始对比 */
        $("#cs_calculate_bgdb").click(function () {
            if(EBmapExtend.compare.length > 0){
                if(EBmapExtend.compare.toString() == ''){
                    alert('请先添加机场！');
                }else{
                    $("#cs_contrastC,#cs_contrastBox").show();
                    $("#cs_ultimately").hide();
                    EBmapExtend.resetCompare();
                    $.ajax({
                        url:"/airportComparison",
                        dataType:"json",
                        data:{
                            iTATList:EBmapExtend.compare.toString()
                        },
                        type:"post",
                        success:function (data) {
                            var dbNode = "";
                            if(data.success){
                                dbNode +="<table id='cs_contrastTable' cellspacing='0'><thead class='cs_tableHead'><tr><th colspan='2'>点击机场选为目标机场</th>";
                                var fxdj = "";
                                var pad = "";
                                var city = "";
                                var csrk = "";
                                var lywj = "";
                                var jdhsyl = "";
                                var skb = "";
                                var zzls_item1 = [];  // 最近几年机场吞吐
                                var zzls_item2 = [];  // 最近几年GDP

                                data.data.forEach(function (das,index) {
                                    for(var t = 0;t < das.throughputs.length;t ++){
                                        if(index == 0){
                                            if(t == 0){
                                                zzls_item1.push({name:das.throughputs[t].year,node:"<tr class='cs_table_item3 cs_table_item'><td rowspan='"+(data.data[0].throughputs.length)+"' valign='' width='45px' style='padding: 0 10px 0 10px'>最近"+(data.data[0].throughputs.length)+"年机场吞吐量及增长率</td><td>"+das.throughputs[t].year+"</td>"});
                                            }else if((t+1) == das.throughputs.length){
                                                zzls_item1.push({name:das.throughputs[t].year,node:"<tr class='cs_table_item2 cs_table_item'><td>"+das.throughputs[t].year+"（预计）</td>"});
                                            }else{
                                                zzls_item1.push({name:das.throughputs[t].year,node:"<tr class='cs_table_item2 cs_table_item'><td>"+das.throughputs[t].year+"</td>"});
                                            };
                                        }
                                    };
                                    for(var t = 0;t < das.gdps.length;t ++){
                                        if(index == 0){
                                            if(t == 0){
                                                zzls_item2.push({name:das.gdps[t].year,node:"<tr class='cs_table_item3 cs_table_item'><td rowspan='"+(data.data[0].gdps.length)+"' valign='' width='45px' style='padding: 0 10px 0 10px'>最近"+(data.data[0].gdps.length)+"年城市gdp及增长率</td><td>"+das.gdps[t].year+"</td>"});
                                            }else if((t+1) == das.gdps.length){
                                                zzls_item2.push({name:das.gdps[t].year,node:"<tr class='cs_table_item2 cs_table_item'><td>"+das.gdps[t].year+"（预计）</td>"});
                                            }else{
                                                zzls_item2.push({name:das.gdps[t].year,node:"<tr class='cs_table_item2 cs_table_item'><td>"+das.gdps[t].year+"</td>"});
                                            };
                                        }
                                    };
                                });
                                data.data.forEach(function (das,index) {
                                    var jcName = cs_codeZname(das.iata);
                                    if(index == 0){
                                        if(data.data.length == 1){
                                            dbNode +="<th class='cs_tableHeadSet cs_tableHeadSetItem' tag='"+jcName.airInfoName+"'><span></span>"+jcName.airInfoName+"</th></tr></thead><tbody class='cs_tableBody'>";
                                        }else{
                                            dbNode +="<th class='cs_tableHeadSet cs_tableHeadSetItem' tag='"+jcName.airInfoName+"'><span></span>"+jcName.airInfoName+"</th>";
                                        }
                                    }else if((index+1) == data.data.length){
                                        dbNode +="<th class='cs_tableHeadSetItem' tag='"+jcName.airInfoName+"'><span></span>"+jcName.airInfoName+"</th></tr></thead><tbody class='cs_tableBody'>";
                                    }else{
                                        dbNode +="<th class='cs_tableHeadSetItem' tag='"+jcName.airInfoName+"'><span></span>"+jcName.airInfoName+"</th>";
                                    };
                                    if(index == 0){
                                        fxdj = "<tr class='cs_table_item'><td colspan='2'>飞行等级</td>";
                                        pad = "<tr class='cs_table_item'><td colspan='2'>跑道（条）</td>";
                                        city = "<tr class='cs_table_item'><td colspan='2'>城市</td>";
                                        csrk = "<tr class='cs_table_item' style='height: 50px'><td colspan='2'>城市人口</td>";
                                        lywj = "<tr class='cs_table_item'><td colspan='2'>旅游资源（旺季）</td>";
                                        jdhsyl = "<tr class='cs_table_item'><td colspan='2'>基地航司运力</td>";
                                        skb = "<tr class='cs_table_item'><td colspan='2'>时刻</td>";
                                    };
                                    fxdj += "<td>"+das.airfieldLvl+"</td>";
                                    pad += "<td>"+das.runwayArticleNumber+"</td>";
                                    city += "<td>"+das.city+"</td>";
                                    csrk += "<td>"+das.cityPgeNumber+"</td>";
                                    lywj += "<td title='"+das.touristResources+"' class='cs_table_itemHid'>"+das.touristResources+"</td>";
                                    jdhsyl += "<td>"+das.baseNavigationDep+"</td>";
                                    skb += "<td tag='"+das.iata+"' class='cs_table_itemCksk' onclick='appys(this)'>点击查看时刻表</td>";

                                    if((index+1) == data.data.length){
                                        fxdj += "</tr>";
                                        pad += "</tr>";
                                        city += "</tr>";
                                        csrk += "</tr>";
                                        lywj += "</tr>";
                                        jdhsyl += "</tr>";
                                        skb += "</tr>";
                                    };
                                    for(var t = 0;t < das.throughputs.length;t ++){
                                        zzls_item1[t].node +="<td>"+das.throughputs[t].data+"("+das.throughputs[t].growthRate+")"+"</td>";
                                    };
                                    for(var t = 0;t < das.gdps.length;t ++){
                                        zzls_item2[t].node +="<td>"+das.gdps[t].data+"("+das.gdps[t].growthRate+")"+"</td>";
                                    };
                                    if(index+1 ==  data.data.length){
                                        for(var f = 0 ;f < zzls_item1.length; f++){
                                            zzls_item1[f].node +="</tr>";
                                        };
                                        for(var f = 0 ;f < zzls_item2.length; f++){
                                            zzls_item2[f].node +="</tr>";
                                        };
                                    };
                                });

                                dbNode = dbNode + fxdj + pad + city;
                                zzls_item1.forEach(function (das) {
                                    dbNode +=das.node;
                                });
                                zzls_item2.forEach(function (das) {
                                    dbNode +=das.node;
                                });
                                dbNode = dbNode + csrk + lywj + jdhsyl + skb;
                                dbNode +="</tbody></table>";
                                $("#cs_contrastW").html(dbNode);

                                $(".cs_tableHeadSetItem").click(function () {
                                    $(".cs_tableHeadSetItem").removeClass("cs_tableHeadSet");
                                    $(this).addClass("cs_tableHeadSet");
                                });
                                $(".cs_tableHeadSetItem").mouseover(function () {
                                    $(this).text("点击设为目标机场");
                                });
                                $(".cs_tableHeadSetItem").mouseout(function () {
                                    $(this).text($(this).attr('tag'));
                                });
                            }
                        }
                    })
                }
            }else{
                alert('请先添加机场！');
            }

        });
        // 绑定跳过机场
        $("#cs_calculate_jump").click(function () {
            // filAbly();
            $("#cs_jcContrast,#cs_contrastBox,#cs_jcdb").hide();
            $("#cs_information").show();
            $(".cs_calculate_itemN_set").click();
            measureData.stage = 1;  // 切换添加输入框的状态
        });


        // 已有航路切换
        $(".cs_informationIcon").click(function(){
            $("#cs_tagItem1").hide();
            $("#cs_tagItem2").show();
            $('#cs_ultimatelyClearX').click();
            var changeLine = true;
            if(measureData.type == 1){
                if(measureData.data.s == "" || measureData.data.z == ""){
                    changeLine = false;
                    alert("请先完成机场选择");
                }else{
                    if(measureData.data.s == measureData.data.z){
                        alert("机场不能重复！");
                        changeLine = false;
                    };
                };
            }else{
                if(measureData.data.s == "" || measureData.data.z == "" || measureData.data.j == ""){
                    changeLine = false;
                    alert("请先完成机场选择");
                }else{
                    if(measureData.data.s == measureData.data.z || measureData.data.z == measureData.data.j || measureData.data.j == measureData.data.s){
                        alert("机场不能重复！");
                        changeLine = false;
                    };
                };
            };
           if(changeLine){
               var seleLine = [];
               if($(this).hasClass('cs_informationIconS')){
                   $(this).removeClass("cs_informationIconS");
               }else{
                   $(".cs_informationIcon").removeClass("cs_informationIconS");
                   $(this).addClass("cs_informationIconS");
               };
               var neArry = [];
               var setNode = "";
               EBmapExtend.drawLast();
               if($(".cs_informationIcon").index($(this)) == 0){  //  已有航路
                   if(measureData.type == 1)$('.wsetHelectA').click();
                   EBmapExtend.clearSelfLine();
                   var hjs = 0;
                   if(hlhc.out.lslines != ''){
                        if($('.wsetHelectA').length == 2){
                            EBmapExtend.drawLast(hlhc.out);
                        };
                   }else{
                       for (var i = 0;i < $(".wsetHelectA").length;i ++){
                           if($(".wsetHelectA").eq(i).attr("datas") != undefined){
                               setNode += $(".wsetHelectA").eq(i).attr("tag");
                               var dsta = JSON.parse( $(".wsetHelectA").eq(i).attr("datas"));
                               hjs += Number(dsta.jl);
                               neArry.push(dsta);
                           };
                       };
                       if(neArry.length == 2){
                           $("#cs_hjVal").val(parseInt(hjs*2));
                       }else{
                           $("#cs_informationItemWyyxC").click();
                           if($(".cs_informationItemWsetH").length == 2 && $(".wsetHelectA").length != 2 && $(this).hasClass("cs_informationIconS")){
                               var offset = $("#cs_informationItemWyyxCt").offset();
                               $('body').append("<div id='complete-show'><span class='cs-gstp'>请勾选&nbsp;<span style='color:#ecdd0c; '>手动添加航段</span>&nbsp;完成航路补全</span></div>");
                               $('#complete-show').css({'left' : offset.left + 'px',top : offset.top - 8 + 'px'});
                               setTimeout(function(){
                                   $("#complete-show").remove();
                               },2000);
                           };
                       };
                   };
                   if($(this).hasClass('cs_informationIconS')){
                       neArry.forEach(function (vs) {
                           seleLine.push({
                               color:"#d96334",
                               data:vs.dhd
                           })
                       });
                   }else{
                       $("#cs_informationItemWyyxBox").hide();
                   };
               }else{  //  自定义航路
                   $('#cs_informationItemWyyxBox').hide();
                   $('#cs_informationItemWyyxC').removeClass('cs_calculateJcdjOpen');
                    if(hlhc.ins.lslines == ''){
                        if($(this).hasClass("cs_informationIconS")){
                            $("*").addClass("cs-ing");
                            if(!glData.open){
                                EBmapExtend.clearSelfLine();
                                if(glData.ts != ""){
                                    // glData.ts.removeClass("wsetHelectA");
                                };
                                var pointsd = cs_cgair(measureData.data.z);
                                glData.pointEnd = new BMap.Point(pointsd.city_coordinate_w,pointsd.city_coordinate_j);
                                glData.open = true;
                                csmap.map.addEventListener('click', hz);
                                csmap.map.addEventListener('rightclick',rz);
                                csmap.map.addEventListener('mousemove',mom);
                                var cityData = cs_cgair(measureData.data.s);
                                EBmapExtend.drawOption(new BMap.Point(cityData.city_coordinate_w,cityData.city_coordinate_j),true);
                            };
                           setTimeout(function(){
                               $("#cs_calculate").addClass('cs_calculate-left');
                               $(".pla-tool").addClass('pla-tool-top');
                               $("#calculate-back").addClass('calculate-back-right');
                           },500);
                        }else{
                            $("*").removeClass("cs-ing");
                        };
                    }else{
                        EBmapExtend.drawLast(hlhc.ins);
                    };
                   seleLine = seleLine.concat(allLines);
               };
               drawlap(seleLine);
           }
        });

        $("#cs_informationItemWyyxC").click(function () {
            $('#cs_hsListIcon,#cs_jxListIcon').removeClass('cs_calculateJcdjOpen');
            $('#cs_hsList,#cs_jxList').hide();
            if($(".cs_informationIcon").eq(0).hasClass('cs_informationIconS')){
                $(this).toggleClass("cs_calculateJcdjOpen");
                $("#cs_informationItemWyyxBox").toggle();
                if($("#cs_informationItemWyyxBox").is(':hidden') && $('.cs_informationItemWsetH').length == 2 && $('.wsetHelectA').length != 2) $('.cs_informationIconS').removeClass('cs_informationIconS');
            };
        });
        $("#cs_informationItemWyyxBox").on("click","li",function () {
            if($(this).attr("datas") == ""){
                if(glData.open){
                    if($(this).hasClass("wsetHelectA")){
                        $(this).toggleClass("wsetHelectA");
                        EBmapExtend.clearSelfLine();
                        csmap.map.removeEventListener('click', hz);
                        csmap.map.removeEventListener('rightclick',rz);
                    }else{
                        alert("请先完成为完成的航路绘制或者取消选择！");
                    };
                }else{
                    $(this).toggleClass("wsetHelectA");
                    if($(this).hasClass("wsetHelectA")){
                        if(hlhc.out.lslines == ''){
                            var  yydata = [];
                            $("*").addClass("cs-ing");
                            glData.open = true;
                            var _airCode = $(this).attr('tscode').split('=');
                            if(_airCode[0] != codeScu(measureData.data.s).iata){  // 前段
                                if($('.wsetHelectA').eq(0).attr('datas') != ''){
                                    yydata = JSON.parse($('.wsetHelectA').eq(0).attr('datas')).road;
                                    glData.pointEnd = new BMap.Point(codeScu(measureData.data.z).city_coordinate_w,codeScu(measureData.data.z).city_coordinate_j);
                                    if(JSON.parse($('.wsetHelectA').eq(0).attr('datas')).name != $('.wsetHelectA').eq(0).attr('kv')){
                                        yydata = yydata.reverse();
                                    };
                                };
                            }else{
                                if($('.wsetHelectA').eq(1).attr('datas') != ''){
                                    yydata = JSON.parse($('.wsetHelectA').eq(1).attr('datas')).road;
                                    glData.pointEnd = new BMap.Point(codeScu(measureData.data.s).city_coordinate_w,codeScu(measureData.data.s).city_coordinate_j);
                                    if(JSON.parse($('.wsetHelectA').eq(1).attr('datas')).name == $('.wsetHelectA').eq(1).attr('kv')){
                                        yydata = yydata.reverse();
                                    };
                                };
                            };

                            csmap.map.addEventListener('click', hz);
                            csmap.map.addEventListener('rightclick', rz);
                            glData.ts = $(this);
                            csmap.map.addEventListener('mousemove',mom);
                            drawlap(allLines);
                            yydata.forEach(function (ws) {
                                EBmapExtend.drawOption(new BMap.Point(ws[0],ws[1]),true);
                            });
                            setTimeout(function(){
                                $("#cs_calculate").addClass('cs_calculate-left');
                                $(".pla-tool").addClass('pla-tool-top');
                                $("#calculate-back").addClass('calculate-back-right');
                            },500);
                        }else{
                            EBmapExtend.drawLast(hlhc.out);
                        };
                    }else{
                        if($(this).hasClass("wsetHelectA")){
                            EBmapExtend.drawLast(hlhc.out);
                        }else{
                            EBmapExtend.drawLast();
                            EBmapExtend.clearSelfLine();
                            csmap.map.removeEventListener('click', hz);
                            csmap.map.removeEventListener('rightclick',rz);
                        }
                    };
                };
            }else{
            	$("#cs_yhzbtnc").click();
                $(this).parent().find("li").removeClass("wsetHelectA");
                $(this).addClass("wsetHelectA");
                airbc[$(this).attr("kv")].set = $(this).attr("ind");
                var setNode = "";
                var neArry = [];
                var hjs = 0;
                for (var i = 0;i < $(".wsetHelectA").length;i ++){
                    var dsta = JSON.parse( $(".wsetHelectA").eq(i).attr("datas"));
                    hjs += Number(dsta.jl);
                    setNode += $(".wsetHelectA").eq(i).attr("tag");
                    neArry.push(dsta);
                };
                $("#cs_hjVal").val(parseInt(hjs*2));
                $("#cs_informationItemWyyxCt").html(setNode);
                var seleLine = [];
                neArry.forEach(function (vs) {
                    seleLine.push({
                        color:"#d96334",
                        data:vs.dhd
                    })
                });
                // seleLine = seleLine.concat(allLines);
                drawlap(seleLine);
            };
        });


        var isTgJd = true;
        // 返回上一步
        $("#cs_informationBack").click(function () {
            if(isTgJd){
                measureData.stage = 0;  // 切换添加输入框的状态
                $("#cs_jcContrast,#cs_jcdb").show();
                $("#cs_jcdb").addClass("cs_jcdb_none");
                EBmapExtend.setOption(cs_opData);
            }else{
                $("#cs_jcContrast,#cs_contrastBox,#cs_jcdb").show();
                isTgJd = true;
            };
            $("#cs_information,#cs_ultimately").hide();
            $(".cs_calculate_itemN_set").click();
        });
        /* 成本测算 */
        $("#cs_table_jump").click(function () {
            setTimeout(function(){ isTgJd = false;},30);
            $("#cs_jcContrast,#cs_jcContrast,#cs_contrastBox,#cs_jcdb").hide();
            $("#cs_information").show();
            if(measureData.type == 0){
                measureData.data.z = $(".cs_tableHeadSet").attr("tag");
            }else if(measureData.type == 1){
                measureData.data.z = $(".cs_tableHeadSet").attr("tag");
            }else if(measureData.type == 2){
                measureData.data.j = $(".cs_tableHeadSet").attr("tag");
            };
            measureData.stage = 0;
            assemblyData();
            measureData.stage = 1;
            $(".cs_calculate_itemN_set").click();
        });
        $("#cs_hsList").on("click","div",function(){
            $("#cs_hsSet").val($(this).text()).attr("tag",$(this).attr("tag"));
            $("#cs_hsList").toggle();
            $("#cs_hsListIcon").toggleClass("cs_calculateJcdjOpen");
        });
        $("#cs_jxList").on("click","div",function(){
            $("#cs_jxSet").val($(this).text()).attr("tag",$(this).attr("tag"));
            $("#cs_jxList").toggle();
            $("#cs_jxListIcon").toggleClass("cs_calculateJcdjOpen");
        });

        $("#cs_hsListIcon").click(function(){
            $('#cs_informationItemWyyxC,#cs_jxListIcon').removeClass('cs_calculateJcdjOpen');
            $('#cs_informationItemWyyxBox,#cs_jxList').hide();
            $("#cs_hsList").toggle();
            var hsNode= "";
            var cs_jxSet = $("#cs_jxSet").val();
            if(cs_jxSet == ""){
                $.post('/getAllAircompany', function(das) {
                    das.forEach(function (val) {
                        if(val != null){
                            hsNode += "<div tag='"+val.id+"'>"+val.airlnCd+"</div>"
                        };
                    });
                    $("#cs_hsList").html(hsNode);
                });
            }else{
                $.ajax({
                    url:"/getAllAircompany",
                    type:"post",
                    data:{
                        airType:cs_jxSet
                    },
                    success:function(das){
                        das.forEach(function (val) {
                            if(val != null){
                                hsNode += "<div tag='"+val.id+"'>"+val.airlnCd+"</div>"
                            };
                        });
                        $("#cs_hsList").html(hsNode);
                    }
                });
            };
        });

        $('#detailedC,.cs_informationSetMinInput').mouseout(function () {
            $(this).find('span').hide();
        });
        $('#detailedC,.cs_informationSetMinInput').mouseover(function () {
            if($(this).find('input').val() != ''){
                $(this).find('span').show();
            };
        });
        $('.clear-content').click(function(){
            $(this).next('input').val('');
        });

        $("#cs_jxListIcon").click(function(){
            $('#cs_informationItemWyyxC,#cs_hsListIcon').removeClass('cs_calculateJcdjOpen');
            $('#cs_informationItemWyyxBox,#cs_hsList').hide();
            $("#cs_jxList").toggle();
            var jxNode= "";
            var cs_hsSet = $("#cs_hsSet");
            if(cs_hsSet.val() == ""){
                $.post('/getAllAirType', function(das) {
                    das.forEach(function (val) {
                        if(val != null){
                            jxNode += "<div tag='"+val+"'>"+val+"</div>"
                        };
                    });
                    $("#cs_jxList").html(jxNode);
                });
            }else{
                $.ajax({
                    url:"/getAllAirType",
                    type:"post",
                    data:{
                        aircompanyId:cs_hsSet.attr("tag")
                    },
                    success:function(das){
                        das.forEach(function (val) {
                            if(val != null){
                                jxNode += "<div tag='"+val+"'>"+val+"</div>"
                            };
                        });
                        $("#cs_jxList").html(jxNode);
                    }
                });

            };
        });
        var regExps = function(value){
            var reg = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
            var regExp = new RegExp(reg);
            if(!regExp.test(value)){
                return false;
            }else{
                return true;
            };
        };

        /* 开始测算 */
        $("#cs_informationBg").click(function(){
            if($("#cs_information_q").val() == ""){
                alert("起始点不能为空");
            }else if($("#cs_information_z").val() == ""){
                alert("终点不能为空");
            }else if($("#cs_information_z").val() == ""){
                alert("航距不能为空");
            }else if($("#cs_hsSet").val() == ""){
                alert("航司不能为空");
            }else if($("#cs_jxSet").val() == ""){
                alert("机型不能为空");
            }else if($("#cs_cbSet").val() == ""){
                alert("成本不能为空");
            }else if($("#cs_zwsSet").val() == ""){
                alert("座位数不能为空");
            }else if($("#cs_sdSet").val() == ""){
                alert("航速不能为空");
            }else if($("#cs_khsjSet").val() == ""){
                alert("开航时间不能为空");
            }else if(!regExps($("#cs_khsjSet").val())){
                alert("日期格式不正确，正确格式为：2014-01-01");
            }else if($("#cs_thsjSet").val() == ""){
                alert("停航时间不能为空");
            }else if(!regExps($("#cs_thsjSet").val())){
                alert("日期格式不正确，正确格式为：2014-01-01");
            }else if(new Date($("#cs_thsjSet").val()).getTime() < new Date($("#cs_khsjSet").val()).getTime()){
                alert("开航日期应该比停航日期小！");
            }else if($('#cs_ildsj').val() == ""){
                alert("轮档时间不能未空");
            }
            else {
                var isT = true;
                if($("#cs_information_j").val() == ""){
                    if(measureData.type != 1){
                        alert("经停点不能为空");
                        isT = false;
                    };
                };
                var fltRteCd = "";
                if(isT){
                    if(measureData.type != 1){
                        fltRteCd = cs_thereCode(measureData.data.s) + "-" +cs_thereCode(measureData.data.j) + "-" +cs_thereCode(measureData.data.z);
                    }else{
                        fltRteCd = cs_thereCode(measureData.data.s) + "-" +cs_thereCode(measureData.data.z);
                    };
                    cs_xinx={
                        distance:$("#cs_hjVal").val(),
                        fltRteCd:fltRteCd,
                        startTime:$("#cs_khsjSet").val(),
                        endTime:$("#cs_thsjSet").val(),
                        speed :$("#cs_sdSet").val(),
                        hourCost:$("#cs_cbSet").val(),
                        aircraftModel:$("#cs_jxSet").val(),
                        sites:$("#cs_zwsSet").val(),
                        aircompanyName:$("#cs_hsSet").val(),
                        aircompanyId:$("#cs_hsSet").attr("tag"),
                        ldsj:$('#cs_ildsj').val()
                    };
                    $.ajax({
                        url:"/costCalculation",
                        type:"post",
                        data:cs_xinx,
                        success:function (csData) {
                            cs_measureXX(csData);
                            $('.cs_ultimatelyHead>div:first-child').click();
                        }
                    });
                };
            };
        });


        var setT; // 标记延时器
        /**
         * 航距验证
         *
         * */
        $('#cs_hjVal').on('input',function () {
            var val = $(this).val();
            if(val == '')return;
            var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
            var regExp = new RegExp(reg);
            var elc = $(this).offset();
            if(regExp.test(val)){
                if(setT != undefined) clearTimeout(setT);
                if(val < 0){
                    $("#csTip").css({"top":elc.top+ 37 + "px","left":elc.left+ 15 + "px"}).html("航距不能为负数！");
                    setT = setTimeout(function(){
                        $("#csTip").html("");
                    },1500);
                }else if(val > 8000){
                    $("#csTip").css({"top":elc.top+ 37 + "px","left":elc.left+ 15 + "px"}).html("请不要输入异常的航距！");
                    setT = setTimeout(function(){
                        $("#csTip").html("");
                    },1500);
                }
            }else{
                if(setT != undefined) clearTimeout(setT);
                $("#csTip").css({"top":elc.top+ 37 + "px","left":elc.left+ 15 + "px"}).html("输入格式错误！");
                setT = setTimeout(function(){
                    $("#csTip").html("");
                },1500);
            }
        });
        $('#cs_hjVal').on('blur',function () {
            var val = $(this).val();
            if(val == '')return;
            var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
            var regExp = new RegExp(reg);
            if(!regExp.test(val) || Number(val) > 8000){
                $(this).val('');
            }
        })

        /**
         *成本 | 座位数 | 航速
         * */
        $('#cs_cbSet,#cs_zwsSet,#cs_sdSet').on('input',function () {
            var val = $(this).val();
            if(val == '')return;
            var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
            var regExp = new RegExp(reg);
            var elc = $(this).offset();
            if(regExp.test(val)){
                if(setT != undefined) clearTimeout(setT);
                if(val <= 0){
                    $("#csTip").css({"top":elc.top+ 22 + "px","left":elc.left + "px"}).html("请输入正确格式！");
                }else if($(this).attr('id') == 'cs_cbSet' && Number(val) > 50){
                    $("#csTip").css({"top":elc.top+ 22 + "px","left":elc.left + "px"}).html("请不要输入异常的成本！");
                }else if($(this).attr('id') == 'cs_zwsSet' && Number(val) > 1000){
                    $("#csTip").css({"top":elc.top+ 22 + "px","left":elc.left + "px"}).html("请不要输入异常的座位数！");
                }else if($(this).attr('id') == 'cs_sdSet' && Number(val) > 2000){
                    $("#csTip").css({"top":elc.top+ 22 + "px","left":elc.left + "px"}).html("请不要输入异常的航速！");
                }
                setT = setTimeout(function(){
                    $("#csTip").html("");
                },1500);
            }else{
                if(setT != undefined) clearTimeout(setT);
                $("#csTip").css({"top":elc.top+ 22 + "px","left":elc.left + "px"}).html("输入格式错误！");
                setT = setTimeout(function(){
                    $("#csTip").html("");
                },1500);
            }
        });
        $('#cs_ildsj').on('input',function(){
            var val = $(this).val();
            if(val == '')return;
            var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
            var regExp = new RegExp(reg);
            var elc = $(this).offset();
            if(regExp.test(val)){
                if(setT != undefined) clearTimeout(setT);
                if(Number(val) > 200){
                    $("#csTip").css({"top":elc.top+ 22 + "px","left":elc.left + "px"}).html("请不要输入异常的时间！");
                };
                setT = setTimeout(function(){
                    $("#csTip").html("");
                },1500);
            }            
        })
        $('#cs_cbSet,#cs_zwsSet,#cs_sdSet,#cs_ildsj').on('blur',function(){
            var val = $(this).val();
            if(val == '')return;
            var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
            var regExp = new RegExp(reg);
            if(!regExp.test(val)){
                $(this).val('');
            }else{
                if(Number(val) == 0){
                    $(this).val('');
                }else if($(this).attr('id') == 'cs_cbSet' && Number(val) > 50){
                    $(this).val('');
                }else if($(this).attr('id') == 'cs_zwsSet' && Number(val) > 1000){
                    $(this).val('');
                }else if($(this).attr('id') == 'cs_sdSet' && Number(val) > 2000){
                    $(this).val('');
                }else if($(this).attr('id') == 'cs_ildsj' && Number(val) > 200){
                    $(this).val('');
                }
            }
        })

        $('#cs_sdSet, #cs_hjVal').on('input',function(){
            var hs = Number($('#cs_sdSet').val()),
                hj = Number($('#cs_hjVal').val());
            if(hj&&hs&&(hj+hs>1)){
                $('#cs_ifxsj').val((hj/hs).toFixed(2));
            }
        })



        function downloadFile(fileName, content){
            var aLink = document.createElement('a');
            var blob = new Blob([content]);
            var evt = document.createEvent("HTMLEvents");
            evt.initEvent("click", false, false);
            aLink.download = fileName;
            aLink.href = URL.createObjectURL(blob);
            aLink.dispatchEvent(evt);
        }
        // 导出表格
        $("#cs_table_query").click(function () {
            $("body").append("<form id='airportComparisonExport' action='/airportComparisonExport' method='get'><input type='text' name='iTATList' value='"+(EBmapExtend.compare.toString())+"'><input type='submit' id='airportComparisonExport'></form>")
            $("#airportComparisonExport").submit();
            $("#airportComparisonExport").remove();
        });

        $("#cs_calculate_gjsxIconC").click(function(){
            $(".cs_calculate_gjsxBox").slideToggle("slow");;
        });



        //  ---
        var calThis;
        $('.gjsxDetailed').click(function(){
            calThis = $(this);
        });


        $(".cs_calculateJcdjJx").click(function () {
            setTimeout(function(){
                calThis.find('.cs_calculateJcdjJxBox').toggle();
                calThis.addClass('gjsxDetailed-set').siblings().removeClass('gjsxDetailed-set');
                for(var i = 0; i < $('.gjsxDetailed').length ; i ++){
                    if(i != $('.gjsxDetailed').index(calThis)){
                        $('.gjsxDetailed').eq(i).find('.cs_calculateJcdjJxBox').hide();
                        $('.gjsxDetailed').eq(i).find('.cs_calculateJcdjJx').removeClass('cs_calculateJcdjOpen');
                    };
                }
            },20);
        });

        var resetNodes = function(){
            var nodes = '',chlid = calThis.find('.cs_calculateJcdjJxAf'),item = calThis.find('.cs_calculateJcdjJxItem');
            for(var i = 0;i < chlid.length; i ++ ){
                if(i == chlid.length -1){
                    nodes += chlid.eq(i).attr('tag');
                }else{
                    nodes += chlid.eq(i).attr('tag') + "/";
                }
            };
            calThis.find('.disabledInp').val(nodes);

        }
        $(".gjsxDetailed").on('click','div',function(){
            if($(this).hasClass('cs_calculateJcdjJxBoxChan')){
                if(!$(this).hasClass('cs_calculateJcdjJxBoxSet')){
                    $(this).addClass('cs_calculateJcdjJxBoxSet');
                    calThis.find('.cs_calculateJcdjJxItem').addClass('cs_calculateJcdjJxAf')
                }
                // else{
                //     calThis.find('.cs_calculateJcdjJxItem').removeClass('cs_calculateJcdjJxAf');
                // };
                resetNodes();
            }
        });
        $(".gjsxDetailed").on('click','div',function(){
            if($(this).hasClass('cs_calculateJcdjJxItem')){
                var chlid = calThis.find('.cs_calculateJcdjJxAf'),item = calThis.find('.cs_calculateJcdjJxItem');
                if(chlid.length != 1){
                    $(this).toggleClass('cs_calculateJcdjJxAf');
                }else{
                    if($(this).hasClass('cs_calculateJcdjJxAf')){
                        var elc = $(this).offset();
                        $("#csTip").css({"top":elc.top+ 24 + "px","left":elc.left + 2 + "px"}).html("最少选择一个！");
                        setT = setTimeout(function(){
                            $("#csTip").html("");
                        },1500);
                    }else{
                        $(this).toggleClass('cs_calculateJcdjJxAf');
                    }
                }
                if(calThis.find('.cs_calculateJcdjJxAf').length == calThis.find('.cs_calculateJcdjJxItem').length){
                    calThis.find('.cs_calculateJcdjJxBoxChan').addClass('cs_calculateJcdjJxBoxSet');
                }else{
                    calThis.find('.cs_calculateJcdjJxBoxChan').removeClass('cs_calculateJcdjJxBoxSet');
                }
                resetNodes();
            };
        });


        /* 开航-收藏 切换 */
        $(".cs_calculate_headS").click(function(){
            $('#cs_contrastBox').hide();
            $(".cs_calculate_headS").removeClass("cs_calculate_head_set");
            $(this).addClass("cs_calculate_head_set");
            if($(".cs_calculate_headS").index($(this)) == 0){
                $(".cs_calculate_box").css({"padding-bottom":"24px"});
                $("#cs_calculate_khzd,#cs_jcdb").show();
                $("#cs_measure").hide();
            }else if($(".cs_calculate_headS").index($(this)) == 1){
                $("#cs_calculate_khzd,#cs_jcdb").hide();
                $("#cs_measure").show();
                $(".cs_measureItemIcon>div").html("&#xe84b");
                $(".cs_calculate_box").css({"padding-bottom":"0px"});
            }
        });

        // 重新绘制默认地图
        drawlap = function (dhd) {
            EBmapExtend.cleraOption();
            var cs_opData = {series:[],center:[]};

            var mr_arr = [];
            for(var j = 0;j < airllist.airInfoDataList.length;j ++){
                mr_arr.push(airllist.airInfoDataList[j].iata);
            };

            cs_opData.series.push({
                type:"dots",  // 图标点图形
                data:mr_arr, //  点的三字码
                style:{
                    color:"#74899a",
                    size: BMAP_POINT_SIZE_SMALLER,
                    shape: BMAP_POINT_SHAPE_CIRCLE
                }
            });
            var hl_arr = [];
            for(var i = 0;i < dhd.length;i ++){
                var neAr = {
                    data:[],
                    color:dhd[i].color
                };
                for(var j = 0;j < dhd[i].data.length;j ++){
                    if(dhd[i].data.length == 1){
                        neAr.data.push(
                            {
                                j:dhd[i].data[j].stratCityPoit2,
                                w:dhd[i].data[j].stratCityPoit1
                            },
                            {
                                j:dhd[i].data[j].endCityCityPoit2,
                                w:dhd[i].data[j].endCityCityPoit1
                            }
                        );
                    }else if(dhd[i].data.length > 1){
                       if(j == (dhd[i].data.length-1)){
                           neAr.data.push(
                                {
                                    j:dhd[i].data[j].endCityCityPoit2,
                                    w:dhd[i].data[j].endCityCityPoit1
                                }
                            );
                        }else{
                           neAr.data.push({
                                j:dhd[i].data[j].stratCityPoit2,
                                w:dhd[i].data[j].stratCityPoit1
                            });
                        };
                    };
                };
                hl_arr.push(neAr);
            };
            cs_opData.series.push({
                type:"lines",  // 图标点图形
                data:hl_arr, //  点的三字码
                style:{
                    color:"#3c78ff"
                }
            });
            EBmapExtend.setOption(cs_opData);
        };



        // 获取航路
        var airbc = {};
        var queryGetFlightAirlineDatas = function (nums) {
            $.ajax({
                url:"/restful/getFlightAirlineData",
                type:"get",
                dataType:"jsonp",
                data:nums,
                success:function (nus) {
                    for(var yup in nus.success){
                        for(var x = 0;x < nus.success[yup].length;x ++){
                            var arr1 = GPS.wd_encrypt(Number(nus.success[yup][x].stratCityPoit2)/10000,Number(nus.success[yup][x].stratCityPoit1)/10000);
                            var arr2 = GPS.wd_encrypt(Number(nus.success[yup][x].endCityCityPoit2)/10000,Number(nus.success[yup][x].endCityCityPoit1)/10000);
                            nus.success[yup][x].stratCityPoit2 = arr1.lat;
                            nus.success[yup][x].stratCityPoit1 = arr1.lon;
                            nus.success[yup][x].endCityCityPoit2 = arr2.lat;
                            nus.success[yup][x].endCityCityPoit1 = arr2.lon;
                        };
                    };
                    var hlNode = new Object();
                    var points = [];
                    var hjD = [];
                    for(var key in nus.success){
                        points.push(
                            new BMap.Point(Number(nus.success[key][0].stratCityPoit2)/10000,Number(nus.success[key][0].stratCityPoit1)/10000),
                            new BMap.Point(Number(nus.success[key][0].endCityCityPoit2)/10000,Number(nus.success[key][0].endCityCityPoit1)/10000),
                            new BMap.Point(Number(nus.success[key][nus.success[key].length - 1].endCityCityPoit2)/10000,Number(nus.success[key][nus.success[key].length - 1].endCityCityPoit1)/10000),
                            new BMap.Point(Number(nus.success[key][nus.success[key].length - 1].endCityCityPoit2)/10000,Number(nus.success[key][nus.success[key].length - 1].endCityCityPoit1)/10000)
                        );
                    };
                    for(var yup in nus.success){
                        hcjl =  csmap.map.getDistance(new BMap.Point(nus.success[yup][0].stratCityPoit2,nus.success[yup][0].stratCityPoit1),new BMap.Point(nus.success[yup][0].endCityCityPoit2,nus.success[yup][0].endCityCityPoit1));
                        hcjl =  csmap.map.getDistance(new BMap.Point(nus.success[yup][nus.success[yup].length-1].stratCityPoit2,nus.success[yup][nus.success[yup].length-1].stratCityPoit1),new BMap.Point(nus.success[yup][nus.success[yup].length-1].endCityCityPoit2,nus.success[yup][nus.success[yup].length-1].endCityCityPoit1));
                        hjD.push(hcjl/1000);
                    };
                    var e = 0;
                    for(var key in nus.success){
                        var kee = "";
                        var hcjl = 0;
                        if(key.indexOf("go") != -1){
                            kee = key.split("go");
                        }else if (key.indexOf("back") != -1){
                            kee = key.split("back");
                        };
                        var keyName2 = codeScu(nus.success[key][0].stratCity).iata + "=" + codeScu(nus.success[key][nus.success[key].length-1].endCity).iata;
                        var keyName1 = codeScu(nus.success[key][0].stratCity).city + "=" + codeScu(nus.success[key][nus.success[key].length-1].endCity).city;
                        if(hlNode[keyName2] == undefined){
                            hlNode[keyName2] = [];
                        };
                        var _datas = [];
                        nus.success[key].forEach(function(vs,ind){
                            if(measureData.type != 1){
                                if(ind != nus.success[key].length - 1){
                                    _datas.push([vs.stratCityPoit2,vs.stratCityPoit1]);
                                }else{
                                    _datas.push([vs.stratCityPoit2,vs.stratCityPoit1]);
                                    _datas.push([vs.endCityCityPoit2,vs.endCityCityPoit1]);
                                };
                            };
                        });
                        hlNode[keyName2].push({
                            name:keyName1,
                            jl:(Number(kee[1].substring(1,kee[0].length)) + hjD[e]),
                            dhd:nus.success[key],
                            road:_datas
                        });
                        e ++;
                    };
                    // 判断是不是经停 补数据
                    var cs_codeZname1 = "";
                    var cs_codeZname2 = "";
                    var cs_codeZname3 = "";
                    airbc = {};
                    if(measureData.type != 1){
                        cs_codeZname1 = cs_cgair(measureData.data.s);
                        cs_codeZname2 = cs_cgair(measureData.data.j);
                        cs_codeZname3 = cs_cgair(measureData.data.z);
                        airbc[cs_codeZname1.iata+"="+cs_codeZname2.iata] ={set:0,data:[],ce:(cs_codeZname2.iata+"="+cs_codeZname3.iata),reversal:false,code:cs_codeZname1.iata+"="+cs_codeZname2.iata};
                        airbc[cs_codeZname2.iata+"="+cs_codeZname3.iata] ={set:0,data:[],ce:(cs_codeZname1.iata+"="+cs_codeZname2.iata),reversal:true,code:cs_codeZname2.iata+"="+cs_codeZname3.iata};
                        airbc[cs_codeZname3.iata+"="+cs_codeZname2.iata] ={set:0,data:[],ce:(cs_codeZname2.iata+"="+cs_codeZname1.iata),reversal:true,code:cs_codeZname3.iata+"="+cs_codeZname2.iata};
                        airbc[cs_codeZname2.iata+"="+cs_codeZname1.iata] ={set:0,data:[],ce:(cs_codeZname3.iata+"="+cs_codeZname2.iata),reversal:true,code:cs_codeZname2.iata+"="+cs_codeZname1.iata};
                    }else{
                        cs_codeZname1 = cs_cgair(measureData.data.s);
                        cs_codeZname2 = cs_cgair(measureData.data.z);
                        airbc[cs_codeZname1.iata+"="+cs_codeZname2.iata] ={set:0,data:[],code:cs_codeZname1.iata+"="+cs_codeZname2.iata};
                        airbc[cs_codeZname2.iata+"="+cs_codeZname1.iata] ={set:0,data:[],code:cs_codeZname2.iata+"="+cs_codeZname1.iata};
                    };


                    for(var key in airbc){
                        if(hlNode[key] != undefined){
                            airbc[key].data = hlNode[key];
                        }else {
                            airbc[key].data.push({
                                name:"手动添加航段",
                                jl:"",
                                dhd:[],
                                road:""
                            });
                        };
                    };
                    var airbcZ = {};

                    if(measureData.type == 1){
                        airbcZ[codeScu(measureData.data.s).iata + "=" +codeScu(measureData.data.z).iata] = {set:0,data:[],code:codeScu(measureData.data.s).iata + "=" +codeScu(measureData.data.z).iata};
                    }else{
                        airbcZ[codeScu(measureData.data.s).iata + "=" +codeScu(measureData.data.j).iata] = {set:0,data:[],code:codeScu(measureData.data.s).iata + "=" +codeScu(measureData.data.j).iata};
                        airbcZ[codeScu(measureData.data.j).iata + "=" +codeScu(measureData.data.z).iata] = {set:0,data:[],code:codeScu(measureData.data.j).iata + "=" +codeScu(measureData.data.z).iata};
                    };

                    for(var key in airbc){
                        var codes = airbc[key].code.split("=");
                        if(airbcZ[codes[0] + "=" + codes[1]] != undefined){
                            var cot = codes[0] + "=" + codes[1];
                            airbcZ[cot].data = airbcZ[cot].data.concat(airbc[key].data);
                        }else if(airbcZ[codes[1] + "=" + codes[0]] != undefined){
                            var cot = codes[1] + "=" + codes[0];
                            var _tn = codeScu(measureData.data.z).city;

                            if(airbc[cot].data[0].name != "手动添加航段"){
                                airbcZ[cot].data = airbcZ[cot].data.concat(airbc[key].data);
                            };
                        };
                    };

                    hlNode = {};
                    for(var key in airbcZ){
                        for(var ou in airbc){
                            if(airbcZ[key].code == airbc[ou].code){
                                var con = ou.split("=");
                                hlNode[con[0] + "=" + con[1]] = airbcZ[key];
                            };
                        };
                    };

                    var setNode = "";
                    var setTitle = "";
                    var neArry = [];
                    for(var key in hlNode){
                        setNode += "<div class='cs_informationItemWsetH'><div>"+key+"</div><ul>";
                        for(var u = 0;u < hlNode[key].data.length;u ++){
                            if(u == 0){
                                if(hlNode[key].data[u].dhd.length == 0){
                                    setNode +="<li datas='' title='"+hlNode[key].data[u].name+"' tsCode='"+hlNode[key].code+"' tag='"+key+"' kv='"+key+"' class='wsetHelectB'>"+hlNode[key].data[u].name+"</li>";
                                }else{
                                    setNode +="<li title='航距："+parseInt(hlNode[key].data[u].jl)+"' tsCode='"+hlNode[key].code+"'  kv='"+key+"' datas='"+(JSON.stringify(hlNode[key].data[u]))+"' tag='"+hlNode[key].data[u].name+"-"+u+"' ind='"+u+"' class='wsetHelectB  wsetHelectA'>"+hlNode[key].data[u].name+"-"+u+"</li>";
                                    setTitle += hlNode[key].data[u].name+"-"+u;
                                    neArry.push(hlNode[key].data[u]);
                                };
                            }else{
                                setNode +="<li title='航距："+parseInt(hlNode[key].data[u].jl)+"' tsCode='"+hlNode[key].code+"' kv='"+key+"' datas='"+(JSON.stringify(hlNode[key].data[u]))+"' ind='"+u+"' tag='"+hlNode[key].data[u].name+"-"+u+"' class='wsetHelectB'>"+hlNode[key].data[u].name+"-"+u+"</li>";
                            };
                            if(u == (hlNode[key].length-1)){
                                setNode += "</ul>"
                            };
                        };
                        setNode += "</div>";
                    };
                    if(neArry.length == 0){
                        setTitle = "无航路";
                        setNode = "";
                    };
                    $("#cs_informationItemWyyxBox").html(setNode);
                    $("#cs_informationItemWyyxCt").html(setTitle);
                    $(".cs_informationIconS").removeClass('cs_informationIconS');
                    $('#delLines').click();
                }
            })
        };

		$(".cs_calculate_itemN").click(function () {
            $('#cs_ultimatelyClearX').click();
            isTgJd = true;
            $(this).addClass("cs_calculate_itemN_set").siblings().removeClass("cs_calculate_itemN_set");
            measureData.type = $(".cs_calculate_itemN").index($(this));
            EBmapExtend.cleraOption();
            EBmapExtend.removeCompare();
            EBmapExtend.setOption(cs_opData);
            $('#cs_hjVal').val("");
            $('#cs_informationItemWyyxBox').hide();
            if($("#cs_jcContrast").css("display") == "none"){
                assemblyData();
                if(measureData.type == 0){
                    $("#cs_informationIqs").css("display","flex");
                    $("#cs_information_q").val(measureData.data.s);
                    $("#cs_informationIjt").css("display","flex");
                    $("#cs_information_j").val(measureData.data.j);
                    $("#cs_informationIzd").css("display","flex");
                    $("#cs_information_z").val(measureData.data.z);
                }else if(measureData.type == 1){
                    $("#cs_informationIqs").css("display","flex");
                    $("#cs_information_q").val(measureData.data.s);
                    $("#cs_informationIjt").css("display","none");
                    $("#cs_information_j").val(measureData.data.j);
                    $("#cs_informationIzd").css("display","flex");
                    $("#cs_information_z").val(measureData.data.z);
                }else if(measureData.type == 2){
                    $("#cs_informationIqs").css("display","flex");
                    $("#cs_information_q").val(measureData.data.s);
                    $("#cs_informationIjt").css("display","flex");
                    $("#cs_information_j").val(measureData.data.j);
                    $("#cs_informationIzd").css("display","flex");
                    $("#cs_information_z").val(measureData.data.z);
                };
            }else{
                if(measureData.type == 0){
                    $(".cs_calculate_direct").replaceWith(nodeList_sf);
                    $("#cs_calculate_qsd").val(measureData.data.s);
                    $("#cs_calculate_jtd").val(measureData.data.j);
                    $("#cs_maxVal").val(measureData.data.maxjl);
                    $("#cs_minVal").val(measureData.data.minjl);
                }else if(measureData.type == 1){
                    $(".cs_calculate_direct").replaceWith(nodeList_zf);
                    $("#cs_calculate_qsd").val(measureData.data.s);
                    $("#cs_maxVal").val(measureData.data.maxjl);
                    $("#cs_minVal").val(measureData.data.minjl);
                }else if(measureData.type == 2){
                    $(".cs_calculate_direct").replaceWith(nodeList_jt);
                    $("#cs_calculate_qsd").val(measureData.data.s);
                    $("#cs_calculate_zd").val(measureData.data.z);
                    $("#cs_calculate_jl").val(measureData.data.jl);
                }
            };
            bindE();
        });
        $("#cs_jcdb_addDb").click(function(){
            if(EBmapExtend.compare.indexOf("") == -1 && EBmapExtend.compare.length < 5){
                EBmapExtend.compare.push("");
                EBmapExtend.resetCompare();
            }
        });
        $("#cs_jcdb_addDb").mouseover(function(){
            $(this).addClass("cs_jcdb_tj").html("添加");
        });
        $("#cs_jcdb_addDb").mouseout(function(){
            $(this).removeClass("cs_jcdb_tj").html("&#xe6b0");
        });
        var cs_renoveNode = function(cs_setTiST){  //移出节点
            cs_setTiST.removeClass("cs_calculate_tipSet").html("");
        };
        $(".cs_calculate_jccq").keyup(function(e){  //内容改变
            if(e.keyCode == 13){

            };
        });
        $("#cs_calculate_query").click(function(){  //查询机场 - 按钮
            var cs_opData = {series:[],center:[]};
            var cs_isTrue = true;
            var ykt_arr = [];  // 添加点 -- 已开通点
            var gld_arr = [];  // 添加点 -- 关联点
            var wkt_arr = [];  // 添加点 -- 未开通点
            var tjxd_arr = [];  // 添加点 -- 推荐选点范围
            var fxqdj = $('#cs_fxqdj').val().split('/');
            var jclx = $('#cs_jclx').val().replace(/非高原机场/g,'普通机场').replace(/高原机场/g,'高原机场/高高原机场').split('/');
            var btzc = $('#cs_zhbt').val().replace(/有补贴/g,'是').replace(/无补贴/g,'否').split('/');
            var ttl = [$('#cs-throughput-min').val() == '' ? 0 : Number($('#cs-throughput-min').val()),$('#cs-throughput-max').val() == '' ? 99999999999 : Number($('#cs-throughput-max').val())];

            /*
            * 默认装已开通的
            * */
            ykt_arr = [];
            allLineD.success.AriLineAirportDataList.forEach(function(val){

                var str0 = val.substring(0,3);
                var str1 = val.substring(3,6);

                if(str0 == codeScu(measureData.data.s).iata || str0 == codeScu(measureData.data.j).iata || str0 == codeScu(measureData.data.z).iata || str1 == codeScu(measureData.data.s).iata || str1 == codeScu(measureData.data.j).iata || str1 == codeScu(measureData.data.z).iata){
                    if(ykt_arr.indexOf(str1) == -1){
                        var flyPersonN = codeScu(str1);
                        if(flyPersonN == undefined){
                        	flyPersonN ={
                        			flyPerson:"0"
                        	}
                        }
                        var flyPerson;
                        if(flyPersonN.flyPerson != undefined){
                            flyPerson = Number(flyPersonN.flyPerson.replace(/\,/g, ""))
                        }else{
                            flyPerson = 0;
                        }
                        if(flyPerson >= ttl[0] && flyPerson <= ttl[1]){
                            ykt_arr.push(str1);
                        }
                    };
                    if(ykt_arr.indexOf(str0) == -1){
                    	
                        var flyPersonN = codeScu(str0);
                        if(flyPersonN == undefined){
                        	flyPersonN ={
                        			flyPerson:"0"
                        	}
                        }
                        var flyPerson;
                        if(flyPersonN.flyPerson != undefined){
                            flyPerson = Number(flyPersonN.flyPerson.replace(/\,/g, ""))
                        }else{
                            flyPerson = 0;
                        }
                        if(flyPerson >= ttl[0] && flyPerson <= ttl[1]){
                            ykt_arr.push(str0);
                        }
                    }
                }
            });

            if(measureData.type == 0){
                if($("#cs_calculate_qsd").val() == ""){
                    alert("起始点不能为空");
                    cs_isTrue = false;
                }else if($("#cs_calculate_jtd").val() == ""){
                    alert("经停点不能为空");
                    cs_isTrue = false;
                }else if($("#cs_calculate_jtd").val() == $("#cs_calculate_qsd").val()){
                    alert("起始点不能和经停点相同");
                    cs_isTrue = false;
                } else if($("#cs_maxVal").val() == ""){
                    alert("最大距离不能为空");
                    cs_isTrue = false;
                }else if($("#cs_minVal").val() == ""){
                    alert("最小距离不能为空");
                    cs_isTrue = false;
                }else if(Number($("#cs_maxVal").val()) <= Number($("#cs_minVal").val())){
                    alert("最大距离应比最小距离大");
                    cs_isTrue = false;
                }else if(cs_isTrue == true){
                    measureData.data = {
                        s:$("#cs_calculate_qsd").val().toString(),
                        z:"",
                        j:$("#cs_calculate_jtd").val().toString(),
                        maxjl:Number($("#cs_maxVal").val()),
                        minjl:Number($("#cs_minVal").val()),
                        jl:""
                    };
                    if(csmap == ""){
                        csmap = constructionSeaway();  //获取实例
                        EBmapExtend = new EBmapExtendConstruction(csmap.map);
                    };
                    var cs_calculate_qsd = measureData.data.s;
                    var cs_calculate_jtd = measureData.data.j;
                    var cs_point = "";
                    var cs_dzb = "";  // 获取到圆点中心
                    var cs_dzb1 = "";  // 获取到圆点中心
                    for(var i = 0; i < airllist.airInfoDataList.length; i++ ){
                        if(cs_calculate_jtd == ""){
                            if(airllist.airInfoDataList[i].airInfoName.toString() == cs_calculate_qsd){
                                cs_dzb = airllist.airInfoDataList[i].iata;
                                cs_point = new BMap.Point(airllist.airInfoDataList[i].city_coordinate_w,airllist.airInfoDataList[i].city_coordinate_j);
                                break;
                            }
                        }else{
                            if(airllist.airInfoDataList[i].airInfoName.toString() == cs_calculate_jtd){
                                cs_dzb = airllist.airInfoDataList[i].iata;
                                cs_point = new BMap.Point(airllist.airInfoDataList[i].city_coordinate_w,airllist.airInfoDataList[i].city_coordinate_j);
                            }else if(airllist.airInfoDataList[i].airInfoName.toString() == cs_calculate_qsd){
                                cs_dzb1 = airllist.airInfoDataList[i].iata;
                            }
                        }
                    };
                    if(cs_dzb1 != ""){
                        gld_arr.push(cs_dzb,cs_dzb1);
                    }else{
                        gld_arr.push(cs_dzb);
                    }
                    cs_opData.series.push({
                        type:"circle",  // 双范围圆图形
                        data:[cs_dzb,{d:$("#cs_maxVal").val()*1000,color: HLCS_COLOR.area0 },{d:$("#cs_minVal").val()*1000,color:HLCS_COLOR.area1 }],  //  三个数据，1、圆点三字码，2、最大范围，3、最小范围
                        style:{
                            color:"#d44c1f"
                        }
                    });
                    cs_opData.series.push({
                        type:"dots",  // 图标点图形
                        data:gld_arr, //  点的三字码
                        style:{
                            color: HLCS_COLOR.gljc,
                            size: BMAP_POINT_SIZE_SMALL,
                            shape: BMAP_POINT_SHAPE_CIRCLE
                        }
                    });
                    for(var j = 0;j < airllist.airInfoDataList.length;j ++){
                        if(
                            Number(csmap.map.getDistance(new BMap.Point(airllist.airInfoDataList[j].city_coordinate_w,airllist.airInfoDataList[j].city_coordinate_j),cs_point)) <= $("#cs_maxVal").val()*1000 &&
                            Number(csmap.map.getDistance(new BMap.Point(airllist.airInfoDataList[j].city_coordinate_w,airllist.airInfoDataList[j].city_coordinate_j),cs_point)) >= $("#cs_minVal").val()*1000){
                            var flyPerson = airllist.airInfoDataList[j].flyPerson != undefined ? Number(airllist.airInfoDataList[j].flyPerson.replace(/\,/g, "")) : 0;

                            if(
                                ykt_arr.indexOf(airllist.airInfoDataList[j].iata) == -1
                                && gld_arr.indexOf(airllist.airInfoDataList[j].iata) == -1
                                && (fxqdj.indexOf(airllist.airInfoDataList[j].flyLevel) != -1 ? true : false)
                                && (jclx.indexOf(airllist.airInfoDataList[j].airpotCls) != -1 ? true : false)
                                && (btzc.indexOf(airllist.airInfoDataList[j].isRewardPolicy) != -1 ? true : false)
                                && flyPerson >= ttl[0]
                                && flyPerson <= ttl[1]
                            ){
                                tjxd_arr.push(airllist.airInfoDataList[j].iata);
                            }
                        }
                    };
                    for(var j = 0;j < airllist.airInfoDataList.length;j ++){
                        var flyPerson = airllist.airInfoDataList[j].flyPerson != undefined ? Number(airllist.airInfoDataList[j].flyPerson.replace(/\,/g, "")) : 0;
                        if(
                            gld_arr.indexOf(airllist.airInfoDataList[j].iata) == -1
                            && ykt_arr.indexOf(airllist.airInfoDataList[j].iata) == -1
                            && tjxd_arr.indexOf(airllist.airInfoDataList[j].iata) == -1
                            && (fxqdj.indexOf(airllist.airInfoDataList[j].flyLevel) != -1 ? true : false)
                            && (jclx.indexOf(airllist.airInfoDataList[j].airpotCls) != -1 ? true : false)
                            && (btzc.indexOf(airllist.airInfoDataList[j].isRewardPolicy) != -1 ? true : false)
                            && flyPerson >= ttl[0]
                            && flyPerson <= ttl[1]
                        ){
                            wkt_arr.push(airllist.airInfoDataList[j].iata);
                        }
                    };

                    // 过滤已开通点的关联点
                    gld_arr.forEach(function(val){
                        var index = ykt_arr.indexOf(val);
                        if(index != -1) ykt_arr.splice(index,1)
                    });
                    cs_opData.series.push({ //已开通
                        type:"dots",  // 图标点图形
                        data:ykt_arr, //  点的三字码
                        style:{
                            color: HLCS_COLOR.ykt,
                            size: BMAP_POINT_SIZE_SMALL,
                            shape: BMAP_POINT_SHAPE_CIRCLE
                        }
                    });
                    cs_opData.series.push({ //推荐点
                        type:"dots",  // 图标点图形
                        data:tjxd_arr, //  点的三字码
                        style:{
                            color: HLCS_COLOR.tjd,
                            size: BMAP_POINT_SIZE_SMALL,
                            shape: BMAP_POINT_SHAPE_CIRCLE
                        }
                    });
                    cs_opData.series.push({ //未开通
                        type:"dots",  // 图标点图形
                        data:wkt_arr, //  点的三字码
                        style:{
                            color:HLCS_COLOR.wkt,
                            size: BMAP_POINT_SIZE_SMALLER,
                            shape: BMAP_POINT_SHAPE_CIRCLE
                        }
                    });
                    cs_opData.center = gld_arr;
                    if(cs_dzb1 != ""){
                        cs_opData.series.push({ //关联机场连线
                            type:"line",  // 图标点图形
                            data:[cs_dzb,cs_dzb1], //  点的三字码
                            style:{
                                color:HLCS_COLOR.line,
                                size: 3
                            }
                        });
                    }
                }
            }else if(measureData.type == 1){
                if($("#cs_calculate_qsd").val() == ""){
                    alert("起始点不能为空");
                    cs_isTrue = false;
                } else if($("#cs_maxVal").val() == ""){
                    alert("最大距离不能为空");
                    cs_isTrue = false;
                }else if($("#cs_minVal").val() == ""){
                    alert("最小距离不能为空");
                    cs_isTrue = false;
                }else if(Number($("#cs_maxVal").val()) <= Number($("#cs_minVal").val())){
                    alert("最大距离应比最小距离大");
                    cs_isTrue = false;
                }else {
                    measureData.data = {
                        s:$("#cs_calculate_qsd").val().toString(),
                        z:"",
                        j:"",
                        maxjl:Number($("#cs_maxVal").val()),
                        minjl:Number($("#cs_minVal").val()),
                        jl:""
                    };
                    var cs_calculate_qsd = measureData.data.s;
                    var cs_calculate_jtd = measureData.data.j;
                    var cs_point = "";
                    var cs_dzb = "";  // 获取到圆点中心
                    var cs_dzb1 = "";  // 获取到圆点中心
                    for(var i = 0; i < airllist.airInfoDataList.length; i++ ){
                        if(cs_calculate_jtd == ""){
                            if(airllist.airInfoDataList[i].airInfoName.toString() == cs_calculate_qsd){
                                cs_dzb = airllist.airInfoDataList[i].iata;
                                cs_point = new BMap.Point(airllist.airInfoDataList[i].city_coordinate_w,airllist.airInfoDataList[i].city_coordinate_j);
                                break;
                            }
                        }else{
                            if(airllist.airInfoDataList[i].airInfoName.toString() == cs_calculate_jtd){
                                cs_dzb = airllist.airInfoDataList[i].iata;
                                cs_point = new BMap.Point(airllist.airInfoDataList[i].city_coordinate_w,airllist.airInfoDataList[i].city_coordinate_j);
                            }else if(airllist.airInfoDataList[i].airInfoName.toString() == cs_calculate_qsd){
                                cs_dzb1 = airllist.airInfoDataList[i].iata;
                            }
                        }
                    };
                    if(cs_dzb1 != ""){
                        gld_arr.push(cs_dzb,cs_dzb1);
                    }else{
                        gld_arr.push(cs_dzb);
                    }
                    cs_opData.series.push({
                        type:"circle",  // 双范围圆图形
                        data:[cs_dzb,{d:$("#cs_maxVal").val()*1000,color:HLCS_COLOR.area0},{d:$("#cs_minVal").val()*1000,color:HLCS_COLOR.area1}],  //  三个数据，1、圆点三字码，2、最大范围，3、最小范围
                        style:{
                            color:"#d44c1f"
                        }
                    });
                    cs_opData.series.push({
                        type:"dots",  // 图标点图形
                        data:gld_arr, //  点的三字码
                        style:{
                            color: HLCS_COLOR.gljc,
                            size: BMAP_POINT_SIZE_SMALL,
                            shape: BMAP_POINT_SHAPE_CIRCLE
                        }
                    });
                    for(var j = 0;j < airllist.airInfoDataList.length;j ++){
                        var flyPerson = airllist.airInfoDataList[j].flyPerson != undefined ? Number(airllist.airInfoDataList[j].flyPerson.replace(/\,/g, "")) : 0;
                        if(
                            Number(csmap.map.getDistance(new BMap.Point(airllist.airInfoDataList[j].city_coordinate_w,airllist.airInfoDataList[j].city_coordinate_j),cs_point)) <= $("#cs_maxVal").val()*1000 &&
                            Number(csmap.map.getDistance(new BMap.Point(airllist.airInfoDataList[j].city_coordinate_w,airllist.airInfoDataList[j].city_coordinate_j),cs_point)) >= $("#cs_minVal").val()*1000){
                            if(
                                ykt_arr.indexOf(airllist.airInfoDataList[j].iata.toUpperCase()) == -1
                                && gld_arr.indexOf(airllist.airInfoDataList[j].iata.toUpperCase()) == -1
                                && (fxqdj.indexOf(airllist.airInfoDataList[j].flyLevel.toUpperCase()) != -1 ? true : false)
                                && (jclx.length==9 ? true : (jclx.indexOf(airllist.airInfoDataList[j].airpotCls.toUpperCase()) != -1))
                                && (btzc.indexOf(airllist.airInfoDataList[j].isRewardPolicy.toUpperCase()) != -1 ? true : false)
                                && flyPerson >= ttl[0]
                                && flyPerson <= ttl[1]
                            ){
                                tjxd_arr.push(airllist.airInfoDataList[j].iata);
                            }
                        }
                    };
                    for(var j = 0;j < airllist.airInfoDataList.length;j ++){
                    	var flyPerson = Number(airllist.airInfoDataList[j].flyPerson == undefined ? 0 : airllist.airInfoDataList[j].flyPerson.replace(/\,/,''));
                        if(
                            gld_arr.indexOf(airllist.airInfoDataList[j].iata) == -1
                            && ykt_arr.indexOf(airllist.airInfoDataList[j].iata) == -1
                            && tjxd_arr.indexOf(airllist.airInfoDataList[j].iata) == -1
                            && (fxqdj.indexOf(airllist.airInfoDataList[j].flyLevel) != -1 ? true : false)
                            && (jclx.indexOf(airllist.airInfoDataList[j].airpotCls) != -1 ? true : false)
                            && (btzc.indexOf(airllist.airInfoDataList[j].isRewardPolicy) != -1 ? true : false)
                            && flyPerson >= ttl[0]
                            && flyPerson <= ttl[1]
                        ){
                            wkt_arr.push(airllist.airInfoDataList[j].iata);
                        }
                    };
                    // 过滤已开通点的关联点
                    gld_arr.forEach(function(val){
                        var index = ykt_arr.indexOf(val);
                        if(index != -1) ykt_arr.splice(index,1)
                    });
                    cs_opData.series.push({//已开通
                        type:"dots",  // 图标点图形
                        data:ykt_arr, //  点的三字码
                        style:{
                            color: HLCS_COLOR.ykt,
                            size: BMAP_POINT_SIZE_SMALL,
                            shape: BMAP_POINT_SHAPE_CIRCLE
                        }
                    });
                    cs_opData.series.push({//推荐点
                        type:"dots",  // 图标点图形
                        data:tjxd_arr, //  点的三字码
                        style:{
                            color: HLCS_COLOR.tjd,
                            size: BMAP_POINT_SIZE_SMALL,
                            shape: BMAP_POINT_SHAPE_CIRCLE
                        }
                    });
                    cs_opData.series.push({//未开通
                        type:"dots",  // 图标点图形
                        data:wkt_arr, //  点的三字码
                        style:{
                            color: HLCS_COLOR.wkt,
                            size: BMAP_POINT_SIZE_SMALLER,
                            shape: BMAP_POINT_SHAPE_CIRCLE
                        }
                    });
                    cs_opData.center = gld_arr;
                }
            }else if(measureData.type == 2){
                var cs_dzb = "";  // 获取到圆点中心
                var cs_dzb1 = "";  // 获取到圆点中心
                if($("#cs_calculate_qsd").val() == ""){
                    alert("起始点不能为空");
                    cs_isTrue = false;
                }else if($("#cs_calculate_zd").val() == ""){
                    alert("终点不能为空");
                    cs_isTrue = false;
                }else if($("#cs_calculate_jtd").val() == $("#cs_calculate_qsd").val()){
                    alert("起始点不能和终点相同");
                    cs_isTrue = false;
                }else if($("#cs_calculate_jl").val() == ""){
                    alert("距离不能为空");
                    cs_isTrue = false;
                }else{
                        if(csmap == ""){
                            csmap = constructionSeaway();  //获取实例
                            EBmapExtend = EBmapExtendConstruction(csmap.map);
                        }
                        var cs_ariqd = cs_cgair(measureData.data.s);
                        var cs_arizd = cs_cgair(measureData.data.z);
                        var cs_airjl = Number(csmap.map.getDistance(new BMap.Point(cs_ariqd.city_coordinate_w,cs_ariqd.city_coordinate_j),new BMap.Point(cs_arizd.city_coordinate_w,cs_arizd.city_coordinate_j)));
                        if(cs_airjl >= Number($("#cs_calculate_jl").val())*1000){
                            alert("距离最小应大于"+parseInt((cs_airjl/1000) + 1)+"km");
                            cs_isTrue = false;
                        }else if(cs_isTrue == true){
                            /* 组装关联点 */
                            gld_arr.push(cs_thereCode(measureData.data.s),cs_thereCode(measureData.data.z));
                            cs_opData.series.push({
                                type:"dots",  // 图标点图形
                                data:gld_arr, //  点的三字码
                                style:{
                                    color:"rgba(60,120,255,1)",
                                    size: BMAP_POINT_SIZE_SMALL,
                                    shape: BMAP_POINT_SHAPE_CIRCLE
                                }
                            });


                            // 过滤已开通点的关联点
                            gld_arr.forEach(function(val){
                                var index = ykt_arr.indexOf(val);
                                if(index != -1) ykt_arr.splice(index,1)
                            });
                            cs_opData.series.push({
                                type:"dots",  // 图标点图形
                                data:ykt_arr, //  点的三字码
                                style:{
                                    color:"#74899a",
                                    size: BMAP_POINT_SIZE_SMALL,
                                    shape: BMAP_POINT_SHAPE_CIRCLE
                                }
                            });
                            /* 组装推荐点 */
                            for(var j = 0;j < airllist.airInfoDataList.length;j ++){
                                /* 计算总距离以对比 用以计算范围 */
                                var flyPerson = Number(airllist.airInfoDataList[j].flyPerson == undefined ? 0 : airllist.airInfoDataList[j].flyPerson.replace(/\,/,''));
                                var cs_shjl = Number(csmap.map.getDistance(new BMap.Point(cs_ariqd.city_coordinate_w,cs_ariqd.city_coordinate_j),new BMap.Point(airllist.airInfoDataList[j].city_coordinate_w,airllist.airInfoDataList[j].city_coordinate_j))) + Number(csmap.map.getDistance(new BMap.Point(cs_arizd.city_coordinate_w,cs_arizd.city_coordinate_j),new BMap.Point(airllist.airInfoDataList[j].city_coordinate_w,airllist.airInfoDataList[j].city_coordinate_j)));
                                if(cs_shjl < parseInt($("#cs_calculate_jl").val())*1000){
                                    if(
                                        ykt_arr.indexOf(airllist.airInfoDataList[j].iata.toUpperCase()) == -1
                                        && gld_arr.indexOf(airllist.airInfoDataList[j].iata.toUpperCase()) == -1
                                        && (fxqdj.indexOf(airllist.airInfoDataList[j].flyLevel.toUpperCase()) != -1 ? true : false)
                                        && (jclx.length==9 ? true : (jclx.indexOf(airllist.airInfoDataList[j].airpotCls.toUpperCase()) != -1))
                                        && (btzc.indexOf(airllist.airInfoDataList[j].isRewardPolicy.toUpperCase()) != -1 ? true : false)
                                        && flyPerson >= ttl[0]
                                        && flyPerson <= ttl[1]
                                    ){
                                        tjxd_arr.push(airllist.airInfoDataList[j].iata);
                                    }
                                }
                            }



                            cs_opData.series.push({ //推荐点
                                type:"dots",  // 图标点图形
                                data:tjxd_arr, //  点的三字码
                                style:{
                                    color: HLCS_COLOR.tjd,
                                    size: BMAP_POINT_SIZE_SMALL,
                                    shape: BMAP_POINT_SHAPE_CIRCLE
                                }
                            });
                            /* 组装未开通点 */
                            for(var j = 0;j < airllist.airInfoDataList.length;j ++){
                                if(
                                    gld_arr.indexOf(airllist.airInfoDataList[j].iata) == -1 &&
                                    ykt_arr.indexOf(airllist.airInfoDataList[j].iata) == -1 &&
                                    tjxd_arr.indexOf(airllist.airInfoDataList[j].iata) == -1
                                    && (fxqdj.indexOf(airllist.airInfoDataList[j].flyLevel) != -1 ? true : false)
                                    && (jclx.indexOf(airllist.airInfoDataList[j].airpotCls) != -1 ? true : false)
                                    && (btzc.indexOf(airllist.airInfoDataList[j].isRewardPolicy) != -1 ? true : false)
                                    && flyPerson >= ttl[0]
                                    && flyPerson <= ttl[1]
                                ){
                                    wkt_arr.push(airllist.airInfoDataList[j].iata);
                                }
                            };
                            cs_opData.series.push({ //未开通
                                type:"dots",  // 图标点图形
                                data:wkt_arr, //  点的三字码
                                style:{
                                    color: HLCS_COLOR.wkt,
                                    size: BMAP_POINT_SIZE_SMALLER,
                                    shape: BMAP_POINT_SHAPE_CIRCLE
                                }
                            });
                            /* 椭圆组装数据 */
                            var structureOval = function (ovalStart,ovalEnd,ovalDistance) {  // 1.起点三字码，2.终点三字码，3.距离范围
                                var point_s = [Number(ovalStart.city_coordinate_w),Number(ovalStart.city_coordinate_j)];
                                var point_e = [Number(ovalEnd.city_coordinate_w),Number(ovalEnd.city_coordinate_j)];
                                var dis = ovalDistance;
                                function cre_angle(start,end){  //夹角公式
                                    var diff_x = end.x - start.x,
                                        diff_y = end.y - start.y;
                                    return 360*Math.atan(diff_y/diff_x)/(2*Math.PI);
                                };
                                var cre_zc = dis ;  //总范围
                                var cre_jj= Number(csmap.map.getDistance(new BMap.Point(point_s[0],point_s[1]),new BMap.Point(point_e[0], point_e[1])));  //焦距
                                var cre_dz =Math.sqrt(Math.pow(cre_zc/2,2) -  Math.pow(cre_jj/2,2));  //短轴
                                var cre_cz = Math.sqrt(Math.pow(cre_jj/2,2)+ Math.pow(cre_dz,2)); //长轴
                                var projection =new BMap.MercatorProjection();
                                var pointx1 = projection.lngLatToPoint(new BMap.Point(point_s[0], point_s[1]));
                                var pointx2 = projection.lngLatToPoint(new BMap.Point(point_e[0], point_e[1]));
                                var cre_jaj = cre_angle({x:0,y:0},{x:Number(pointx1.x - pointx2.x),y:Number(pointx1.y - pointx2.y)});
                                var cer_pors = [(point_s[0] > point_e[0] ? (point_s[0] - (point_s[0] - point_e[0])/2) : (point_s[0] + (point_e[0] - point_s[0])/2)) ,(point_s[1] > point_e[1] ? (point_s[1] - (point_s[1] - point_e[1])/2) : (point_s[1] + (point_e[1] - point_s[1])/2)) ];  // 中心点

                                var centre = new BMap.Point(cer_pors[0],cer_pors[1]);
                                var long = cre_cz/1000;
                                var short = cre_dz/1000;
                                var angle = cre_jaj;
                                var c_long = long;
                                var d_lat = short;
                                var aPoint;
                                var aPointLng;
                                var a_uxilium;
                                for(var i = 0.0000; i<parseFloat(180.0000)-centre.lng;i=parseFloat(i)+0.00001){
                                    var aLng = centre.lng+i;
                                    aPoint = new BMap.Point(aLng,centre.lat);
                                    if( csmap.map.getDistance(centre,aPoint).toFixed(0) == c_long.toFixed(0)*1000 ){//在这个纬度下寻找距离为a的点
                                        aPointLng=aLng;
                                        break;
                                    }
                                };
                                a_uxilium=aPointLng-centre.lng;
                                var bPoint;
                                var bPointLat;
                                var b_uxilium;
                                for(var i = 0.0000; i<parseFloat(90.0000)-centre.lat;i=parseFloat(i)+0.00001){
                                    var bLat = centre.lat+i;
                                    bPoint = new BMap.Point(centre.lng,bLat);
                                    if(csmap.map.getDistance(centre,bPoint).toFixed(0) == d_lat.toFixed(0)*1000){
                                        bPointLat = bLat;
                                        break;
                                    };
                                };
                                b_uxilium=bPointLat - centre.lat;
                                if (a_uxilium>b_uxilium) {
                                    var c_uxilium = Math.sqrt(Math.pow(a_uxilium,2) - Math.pow(b_uxilium,2) );
                                }else{
                                    var c_uxilium = Math.sqrt(Math.pow(b_uxilium,2) - Math.pow(a_uxilium,2) );
                                }
                                var kVar= new Array();
                                for(var i = 0; i<360;i++){
                                    kVar.push( Math.tan( i*(2*Math.PI/36)) );
                                };
                                var assemble = new Array();
                                var onePoint;
                                var twoPoint;
                                var threePoint;
                                for(var i = 0; i<36;i++){
                                    if(i>=0 && i<=9){
                                        x= Math.sqrt( (Math.pow(a_uxilium,2)*Math.pow(b_uxilium,2)) / ( Math.pow(b_uxilium,2)+( Math.pow(a_uxilium,2)*Math.pow(kVar[i],2) )) );
                                        y= kVar[i]*x;
                                        onePoint = new BMap.Point(centre.lng + x,centre.lat+y);
                                        assemble.push(onePoint);
                                    }else if(i>=9 && i<=27){
                                        x=(-1)* Math.sqrt( (Math.pow(a_uxilium,2)*Math.pow(b_uxilium,2)) / ( Math.pow(b_uxilium,2)+( Math.pow(a_uxilium,2)*Math.pow(kVar[i],2) )) );
                                        y= kVar[i]*x;
                                        twoPoint = new BMap.Point(centre.lng + x,centre.lat+y);
                                        assemble.push(twoPoint);
                                    }else if(i>27 && i<=36){
                                        x= Math.sqrt( (Math.pow(a_uxilium,2)*Math.pow(b_uxilium,2)) / ( Math.pow(b_uxilium,2)+( Math.pow(a_uxilium,2)*Math.pow(kVar[i],2) )) );
                                        y= kVar[i]*x;
                                        threePoint = new BMap.Point(centre.lng + x,centre.lat+y);
                                        assemble.push(threePoint);
                                    }
                                };
                                var lng;
                                var lat;
                                for(var i=0 ; i<assemble.length ; i++){
                                    lng=assemble[i].lng-centre.lng;
                                    lat=assemble[i].lat-centre.lat;
                                    var x=Math.abs( lng * Math.cos(Math.PI/180*0));
                                    var y=Math.abs( lng * Math.sin(Math.PI/180*0));
                                    if(i>=0&&i<9) {
                                        assemble[i].lng = assemble[i].lng + x;
                                        assemble[i].lat = assemble[i].lat + y;
                                    }else if(i>=9&&i<18){
                                        assemble[i].lng=assemble[i].lng - x;
                                        assemble[i].lat=assemble[i].lat - y;
                                    }else if(i>=18&&i<27){
                                        assemble[i].lng=assemble[i].lng - x;
                                        assemble[i].lat=assemble[i].lat - y;
                                    }else if(i>=27&&i<36){
                                        assemble[i].lng=assemble[i].lng + x;
                                        assemble[i].lat=assemble[i].lat + y;
                                    }
                                };

                                var angle1=Math.PI /180*-angle;
                                var centrePix=csmap.map.pointToPixel(centre);
                                for(var i=0 ; i<assemble.length ; i++){
                                    var assemblePix=csmap.map.pointToPixel(assemble[i]);
                                    var pix0=(assemblePix.x-centrePix.x)*Math.cos(angle1)-(assemblePix.y-centrePix.y)* Math.sin(angle1)+centrePix.x;
                                    var piy0=(assemblePix.x-centrePix.x)* Math.sin(angle1)+(assemblePix.y-centrePix.y)* Math.cos(angle1)+centrePix.y;
                                    assemble[i]=csmap.map.pixelToPoint(new BMap.Pixel(pix0,piy0));
                                }
                                var arr=[];
                                var arr0=[];
                                for(var i=0;i<assemble.length;i++){
                                    arr.push([assemble[i].lng,assemble[i].lat]);
                                };
                                for(var i=0;i<arr.length;i++){
                                    arr0.push(new BMap.Point(arr[i][0],arr[i][1]));
                                };
                                return arr;
                            };
                            var d_assemble = new structureOval(cs_ariqd,cs_arizd,Number($("#cs_calculate_jl").val())*1000);
                            cs_opData.series.push({
                                type:"oval",
                                data:d_assemble,
                                style:{
                                    color:"rgba(68,85,161,0.2)"
                                }
                            });
                        };
                    };
                };
            if(cs_opData.series.length != 0){
                /*
                var x = ''
                cs_opData.series[2].data.map((item)=>{
                    x+=item+',';
                })
                console.log(x)
                x='';
                cs_opData.series[3].data.map((item)=>{
                    x+=item+',';
                })
                console.log(x)
                */
                EBmapExtend.setOption(cs_opData);
            };
        });
    }
    var appys = function(name){
	    window.bgName = $(name).attr('tag');
        opensk();
    }


setTimeout(function(){
    $(document).on('click',function(e){    
        var _con = $('.pla-promptBox');   // 航线菜单框
        var _conn = $('.their-own');   // 航点菜单框
        var type = $('.pla-btn-switch').attr('tag');
        if(type== 'line'){
            if(!_con.is(e.target) && _con.has(e.target).length === 0){
                var curnum=JSON.parse(JSON.stringify(myChart.getOption()));
                for(var i=0;i<curnum.series.length;i++){
                    if(curnum.series[i].name=="航线搜索新增" && haveRedraw){
                        curnum.series.pop();
                        myChart.clear();
                        curnum.tooltip = [];
                        myChart.setOption(curnum);
                        haveRedraw=false;
                    }
                }
            }
        }else{
            if(!_conn.is(e.target) && _conn.has(e.target).length === 0){
                var curnum=JSON.parse(JSON.stringify(myChart.getOption()));
                for(var i=0;i<curnum.series.length;i++){
                    if(curnum.series[i].name=="航线搜索新增" && haveRedraw){
                        curnum.series.pop();
                        myChart.clear();
                        curnum.tooltip = [];
                        myChart.setOption(curnum);
                        haveRedraw=false;
                    }
                }
            }
        }	
    })
},2000)