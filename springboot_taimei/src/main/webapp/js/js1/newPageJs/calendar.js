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

if(/(y+)/.test(format)) { 
	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
} 

for(var k in o) { 
if(new RegExp("("+ k +")").test(format)) { 
	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
} 
} 
return format; 
};
function getMyDay(date){ //获取是星期几
	var num=0;
    date=new Date(date);
    if(parseInt(date.getDay())==0){
        num=7;
    }else {
        num=parseInt(date.getDay());
    }
    return num;
}
function addE(time){
    var year=parseInt(time.split("-")[0]);
    var month=parseInt(time.split("-")[1]);
    var day=parseInt(time.split("-")[2]);
    var node="";
    //闰年情况
    if((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)){
        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
            node="<div class='calendar-content'>";
            //计算上月剩余天数
            if(getMyDay(year+"-"+(month>9?month:('0'+month))+"-0"+1)!=1){
                if(month==3){
                    for(var t=getMyDay(year+"-"+('0'+month)+"-0"+1);t>1;t--){
                        node+="<div abbr='"+year+"-"+2+"-"+(31-t)+"' class='last-month'>"+(31-t)+"</div>";
                    }
                }else if(month==1){
                    for(var t=getMyDay(year+"-"+('0'+month)+"-0"+1);t>1;t--){
                        node+="<div abbr='"+(year-1)+"-"+12+"-"+(33-t)+"' class='last-month'>"+(33-t)+"</div>";
                    }
                }else if(month==8){
                    for(var t=getMyDay(year+"-"+('0'+month)+"-0"+1);t>1;t--){
                        node+="<div abbr='"+(year)+(month>10?"-":"-0")+(month-1)+"-"+(33-t)+"' class='last-month'>"+(33-t)+"</div>";
                    }
                }else {
                    for(var t=getMyDay(year+"-"+(month>9?month:('0'+month))+"-0"+1);t>1;t--){
                        node+="<div abbr='"+(year)+(month>10?"-":"-0")+(month-1)+"-"+(32-t)+"' class='last-month'>"+(32-t)+"</div>";
                    }
                }
            }
            //计算本月天数
            for(var i=1;i<=31;i++){
                if(getMyDay(year+"-"+(month>9?month:('0'+month))+'-'+(i>9?i:'0'+i))!=1){
//                	if(new Date(year+'-'+(month>9?month:('0'+month))+'-'+(i>9?i:'0'+i)).format('yyyy-MM-dd')==new Date().format('yyyy-MM-dd')){
//                		node+="<div style='color:red;' abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                	}else{
                		node+="<div abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
//                	}
                }else {
//                    if(new Date(year+'-'+(month>9?month:('0'+month))+'-'+(i>9?i:'0'+i)).format('yyyy-MM-dd')==new Date().format('yyyy-MM-dd')){
//                    	if(node=="<div class='calendar-content'>"){
//                            node+="<div style='color:red;' abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                        }else {
//                            node+="</div>";
//                            node+="<div class='calendar-content'>";
//                            node+="<div style='color:red;' abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                        }
//                    }else{
                    	if(node=="<div class='calendar-content'>"){
                            node+="<div abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
                        }else {
                            node+="</div>";
                            node+="<div class='calendar-content'>";
                            node+="<div abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
                        }
//                    }
                }
            };
            //计算结尾天数
            if(getMyDay(year+"-"+(month>9?month:('0'+month))+"-"+31)!=7){
                for(var i=1;i<=7-parseInt(getMyDay(year+"-"+(month>9?month:('0'+month))+"-"+31));i++){
                    if(month==12){
                        node+="<div abbr='"+(parseInt(year)+1)+"-0"+(1)+"-"+(i>9?i:'0'+i)+"' class='next-month'>"+(i)+"</div>";
                    }else {
                        node+="<div abbr='"+(year)+(month>8?"-":"-0")+(month+1)+"-"+(i>9?i:'0'+i)+"' class='next-month'>"+(i)+"</div>";
                    }
                }
            }
            node+="</div>";
            node="<div class='calendar-head'><div>周一</div><div>周二</div><div>周三</div><div>周四</div><div>周五</div><div>周六</div><div>周日</div></div>"+node;
            $(".calendar").html(node);
        }else if(month==4||month==6||month==9||month==11){
            node="<div class='calendar-content'>";
            //计算上月剩余天数
            if(getMyDay(year+"-"+(month>9?month:('0'+month))+"-0"+1)!=1){
                for(var t=getMyDay(year+"-"+(month>9?month:('0'+month))+"-0"+1);t>1;t--){
                    node+="<div abbr='"+year+(month>10?"-":"-0")+(month-1)+"-"+(33-t)+"' class='last-month'>"+(33-t)+"</div>";
                }
            }
            //计算本月天数
            for(var i=1;i<=30;i++){
                if(getMyDay(year+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i))!=1){
//                    if(new Date(year+'-'+(month>9?month:('0'+month))+'-'+(i>9?i:'0'+i)).format('yyyy-MM-dd')==new Date().format('yyyy-MM-dd')){
//                    	node+="<div style='color:red;' abbr='"+(year)+"-"+((month>9?month:('0'+month)))+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                    }else{
                    	node+="<div abbr='"+(year)+"-"+((month>9?month:('0'+month)))+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
//                    }
                }else {
//                    if(new Date(year+'-'+(month>9?month:('0'+month))+'-'+(i>9?i:'0'+i)).format('yyyy-MM-dd')==new Date().format('yyyy-MM-dd')){
//                    	if(node=="<div class='calendar-content'>"){
//                            node+="<div style='color:red;' abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                        }else {
//                            node+="</div>";
//                            node+="<div class='calendar-content'>";
//                            node+="<div style='color:red;' abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                        }
//                    }else{
                    	if(node=="<div class='calendar-content'>"){
                            node+="<div abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
                        }else {
                            node+="</div>";
                            node+="<div class='calendar-content'>";
                            node+="<div abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
                        }
//                    }
                }
            };
            //计算结尾天数
            if(getMyDay(year+"-"+(month>9?month:('0'+month))+"-"+30)!=7){
                for(var i=1;i<=7-parseInt(getMyDay(year+"-"+(month>9?month:('0'+month))+"-"+30));i++){
                    node+="<div abbr='"+(year)+(month==11?"-":"-0")+(month+1)+"-"+(i>9?i:'0'+i)+"' class='next-month'>"+(i)+"</div>";
                }
            }
            node+="</div>";
            node="<div class='calendar-head'><div>周一</div><div>周二</div><div>周三</div><div>周四</div><div>周五</div><div>周六</div><div>周日</div></div>"+node;
            $(".calendar").html(node);

        }else if(month==2){
            node="<div class='calendar-content'>";
            //计算上月剩余天数
            if(getMyDay(year+"-"+('0'+month)+"-0"+1)!=1){
                for(var t=getMyDay(year+"-"+('0'+month)+"-0"+1);t>1;t--){
                    node+="<div abbr='"+year+"-0"+(month-1)+"-"+(33-t)+"' class='last-month'>"+(33-t)+"</div>";
                }
            }
            //计算本月天数
            for(var i=1;i<=29;i++){
                if(getMyDay(year+"-"+('0'+month)+"-"+(i>9?i:'0'+i))!=1){
//                    if(new Date(year+'-'+('0'+month)+'-'+(i>9?i:'0'+i)).format('yyyy-MM-dd')==new Date().format('yyyy-MM-dd')){
//                    	node+="<div style='color:red;' abbr='"+(year)+"-"+('0'+month)+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                    }else{
                    	node+="<div abbr='"+(year)+"-"+('0'+month)+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
//                    }
                }else {
//                    if(new Date(year+'-'+('0'+month)+'-'+(i>9?i:'0'+i)).format('yyyy-MM-dd')==new Date().format('yyyy-MM-dd')){
//                    	if(node=="<div style='color:red;' class='calendar-content'>"){
//                            node+="<div abbr='"+(year)+"-"+('0'+month)+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                        }else {
//                            node+="</div>";
//                            node+="<div class='calendar-content'>";
//                            node+="<div style='color:red;' abbr='"+(year)+"-"+('0'+month)+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                        }
//                    }else{
                    	if(node=="<div class='calendar-content'>"){
                            node+="<div abbr='"+(year)+"-"+('0'+month)+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
                        }else {
                            node+="</div>";
                            node+="<div class='calendar-content'>";
                            node+="<div abbr='"+(year)+"-"+('0'+month)+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
                        }
//                    }
                }
            };
            //计算结尾天数
            if(getMyDay(year+"-"+('0'+month)+"-"+29)!=7){
                for(var i=1;i<=7-parseInt(getMyDay(year+"-"+('0'+month)+"-"+29));i++){
                    node+="<div abbr='"+(year)+"-0"+(month+1)+"-"+(i>9?i:'0'+i)+"' class='next-month'>"+(i)+"</div>";
                }
            }
            node+="</div>";
            node="<div class='calendar-head'><div>周一</div><div>周二</div><div>周三</div><div>周四</div><div>周五</div><div>周六</div><div>周日</div></div>"+node;
            $(".calendar").html(node);
        }
    }else {    //平年
        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
            node="<div class='calendar-content'>";
            //计算上月剩余天数
            if(getMyDay(year+"-"+(month>9?month:('0'+month))+"-0"+1)!=1){
                if(month==3){
                    for(var t=getMyDay(year+"-"+('0'+month)+"-0"+1);t>1;t--){
                        node+="<div abbr='"+year+"-0"+2+"-"+(30-t)+"' class='last-month'>"+(30-t)+"</div>";
                    }
                }else if(month==1){
                    for(var t=getMyDay(year+"-"+('0'+month)+"-0"+1);t>1;t--){
                        node+="<div abbr='"+(year-1)+"-"+12+"-"+(33-t)+"' class='last-month'>"+(33-t)+"</div>";
                    }
                }else if(month==8){
                    for(var t=getMyDay(year+"-"+('0'+month)+"-0"+1);t>1;t--){
                        node+="<div abbr='"+(year)+"-0"+(month-1)+"-"+(33-t)+"' class='last-month'>"+(33-t)+"</div>";
                    }
                }else {
                    for(var t=getMyDay(year+"-"+(month>9?month:('0'+month))+"-0"+1);t>1;t--){
                        node+="<div abbr='"+(year)+(month>10?"-":"-0")+(month-1)+"-"+(32-t)+"' class='last-month'>"+(32-t)+"</div>";
                    }
                }
            }
            //计算本月天数
            for(var i=1;i<=31;i++){
                if(getMyDay(year+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i))!=1){
//                    if(new Date(year+'-'+(month>9?month:('0'+month))+'-'+(i>9?i:'0'+i)).format('yyyy-MM-dd')==new Date().format('yyyy-MM-dd')){
//                    	node+="<div style='color:red;' abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                    }else{
                    	node+="<div abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
//                    }
                }else {
//                    if(new Date(year+'-'+(month>9?month:('0'+month))+'-'+(i>9?i:'0'+i)).format('yyyy-MM-dd')==new Date().format('yyyy-MM-dd')){
//                    	if(node=="<div class='calendar-content'>"){
//                            node+="<div style='color:red;' abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                        }else {
//                            node+="</div>";
//                            node+="<div class='calendar-content'>";
//                            node+="<div style='color:red;' abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                        }
//                    }else{
                    	if(node=="<div class='calendar-content'>"){
                            node+="<div abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
                        }else {
                            node+="</div>";
                            node+="<div class='calendar-content'>";
                            node+="<div abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
                        }
//                    }
                }
            };
            //计算结尾天数
            if(getMyDay(year+"-"+(month>9?month:('0'+month))+"-"+31)!=7){
                for(var i=1;i<=7-parseInt(getMyDay(year+"-"+(month>9?month:('0'+month))+"-"+31));i++){
                    if(month==12){
                        node+="<div abbr='"+(parseInt(year)+1)+"-0"+(1)+"-"+(i>9?i:'0'+i)+"' class='next-month'>"+(i)+"</div>";
                    }else {
                        node+="<div abbr='"+(year)+(month>10?"-":"-0")+(month+1)+"-"+(i>9?i:'0'+i)+"' class='next-month'>"+(i)+"</div>";
                    }
                }
            }
            node+="</div>";
            node="<div class='calendar-head'><div>周一</div><div>周二</div><div>周三</div><div>周四</div><div>周五</div><div>周六</div><div>周日</div></div>"+node;
            $(".calendar").html(node);
        }else if(month==4||month==6||month==9||month==11){
            node="<div class='calendar-content'>";
            //计算上月剩余天数
            if(getMyDay(year+"-"+(month>9?month:('0'+month))+"-0"+1)!=1){
                for(var t=getMyDay(year+"-"+(month>9?month:('0'+month))+"-0"+1);t>1;t--){
                    node+="<div abbr='"+year+(month>10?"-":"-0")+(month-1)+"-"+(33-t)+"' class='last-month'>"+(33-t)+"</div>";
                }
            }
            //计算本月天数
            for(var i=1;i<=30;i++){
                if(getMyDay(year+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i))!=1){
//                    if(new Date(year+'-'+(month>9?month:('0'+month))+'-'+(i>9?i:'0'+i)).format('yyyy-MM-dd')==new Date().format('yyyy-MM-dd')){
//                    	node+="<div style='color:red;' abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                    }else{
                    	node+="<div abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
//                    }
                }else {
//                    if(new Date(year+'-'+(month>9?month:('0'+month))+'-'+(i>9?i:'0'+i)).format('yyyy-MM-dd')==new Date().format('yyyy-MM-dd')){
//                    	if(node=="<div class='calendar-content'>"){
//                            node+="<div style='color:red;' abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                        }else {
//                            node+="</div>";
//                            node+="<div class='calendar-content'>";
//                            node+="<div style='color:red;' abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                        }
//                    }else{
                    	if(node=="<div class='calendar-content'>"){
                            node+="<div abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
                        }else {
                            node+="</div>";
                            node+="<div class='calendar-content'>";
                            node+="<div abbr='"+(year)+"-"+(month>9?month:('0'+month))+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
                        }
//                    }
                }
            };
            //计算结尾天数
            if(getMyDay(year+"-"+(month>9?month:('0'+month))+"-"+30)!=7){
                for(var i=1;i<=7-parseInt(getMyDay(year+"-"+(month>9?month:('0'+month))+"-"+30));i++){
                    node+="<div abbr='"+(year)+(month==11?"-":"-0")+(month+1)+"-"+(i>9?i:'0'+i)+"' class='next-month'>"+(i)+"</div>";
                }
            }
            node+="</div>";
            node="<div class='calendar-head'><div>周一</div><div>周二</div><div>周三</div><div>周四</div><div>周五</div><div>周六</div><div>周日</div></div>"+node;
            $(".calendar").html(node);

        }else if(month==2){
            node="<div class='calendar-content'>";
            //计算上月剩余天数
            if(getMyDay(year+"-"+('0'+month)+"-0"+1)!=1){
                for(var t=getMyDay(year+"-"+('0'+month)+"-0"+1);t>1;t--){
                    node+="<div abbr='"+year+"-0"+(month-1)+"-"+(33-t)+"' class='last-month'>"+(33-t)+"</div>";
                }
            }
            //计算本月天数
            for(var i=1;i<=28;i++){
                if(getMyDay(year+"-"+('0'+month)+"-"+(i>9?i:'0'+i))!=1){
//                    if(new Date(year+'-'+('0'+month)+'-'+(i>9?i:'0'+i)).format('yyyy-MM-dd')==new Date().format('yyyy-MM-dd')){
//                    	node+="<div style='color:red;' abbr='"+(year)+"-"+('0'+month)+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                    }else{
                    	node+="<div abbr='"+(year)+"-"+('0'+month)+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
//                    }
                }else {
//                    if(new Date(year+'-'+('0'+month)+'-'+(i>9?i:'0'+i)).format('yyyy-MM-dd')==new Date().format('yyyy-MM-dd')){
//                    	if(node=="<div class='calendar-content'>"){
//                            node+="<div style='color:red;' abbr='"+(year)+"-"+('0'+month)+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                        }else {
//                            node+="</div>";
//                            node+="<div class='calendar-content'>";
//                            node+="<div style='color:red;' abbr='"+(year)+"-"+('0'+month)+"-"+(i>9?i:'0'+i)+"' class='this-month list'>"+i+"</div>";
//                        }
//                    }else{
                    	if(node=="<div class='calendar-content'>"){
                            node+="<div abbr='"+(year)+"-"+('0'+month)+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
                        }else {
                            node+="</div>";
                            node+="<div class='calendar-content'>";
                            node+="<div abbr='"+(year)+"-"+('0'+month)+"-"+(i>9?i:'0'+i)+"' class='this-month'>"+i+"</div>";
                        }
//                    }
                }
            };
            //计算结尾天数
            if(getMyDay(year+"-"+('0'+month)+"-"+28)!=7){
                for(var i=1;i<=7-parseInt(getMyDay(year+"-"+('0'+month)+"-"+28));i++){
                    node+="<div abbr='"+(year)+"-0"+(month+1)+"-"+(i>9?i:'0'+i)+"' class='next-month'>"+(i)+"</div>";
                }
            }
            node+="</div>";
            node="<div class='calendar-head'><div>周一</div><div>周二</div><div>周三</div><div>周四</div><div>周五</div><div>周六</div><div>周日</div></div>"+node;
            $(".calendar").html(node);
        }
    }
}
$(function(){
	
//    var nums="2016-6-12";
    
//    addE(nums);
	/*减年份*/
    $("body").on("click",'.choose-year-top',function(){
    	var nums = '';
        if(parseInt($(".achieve-year").html())>0){
            $(".achieve-year").html(parseInt($(".achieve-year").html())-1);
//            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html();
        }else {
            $(".achieve-year").html(parseInt($(".achieve-year").html())-1);
//            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html();
        }
        addE(nums);
        if(typeof(getDates)=='function'){
        	getDates($('.checkFlt').text(),1);
        }
        if(typeof(getDateList)=='function'){
        	getDateList();
        }
    });
    /*加年份*/
    $("body").on("click",'.choose-year-bottom',function(){
        $(".achieve-year").html(parseInt($(".achieve-year").html())+1);
//        nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
        nums=$(".achieve-year").html()+"-"+$(".achieve-month").html();
        addE(nums);
        if(typeof(getDates)=='function'){
        	getDates($('.checkFlt').text(),1);
        }
        if(typeof(getDateList)=='function'){
        	getDateList();
        }
    });
    /*减月份*/
    $("body").on("click",'.choose-month-top',function(){
    	var nums = '';
        if(parseInt($(".achieve-month").html())==1){
            $(".achieve-year").html(parseInt($(".achieve-year").html())-1);
            $(".achieve-month").html(12);
//            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html();
        }else {
            $(".achieve-month").html(parseInt($(".achieve-month").html())-1);
//            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html();
        }
        addE(nums);
        if(typeof(getDates)=='function'){
        	getDates($('.checkFlt').text(),1);
        }
        if(typeof(getDateList)=='function'){
        	getDateList();
        }
    });
    /*加月份*/
    $("body").on("click",'.choose-month-bottom',function(){
    	var nums = '';
        if(parseInt($(".achieve-month").html())==12){
            $(".achieve-year").html(parseInt($(".achieve-year").html())+1);
            $(".achieve-month").html(1);
//            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html();
        }else {
            $(".achieve-month").html(parseInt($(".achieve-month").html())+1);
//            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html();
        }
        addE(nums);
        if(typeof(getDates)=='function'){
        	getDates($('.checkFlt').text(),1);
        }
        if(typeof(getDateList)=='function'){
        	getDateList();
        }
    });
});
