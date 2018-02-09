function stripscript(value) {  
    var pattern = new RegExp("[`~!@%#$^&*()=|{}';',　\\[\\]<>/? ；：%……+￥（）【】‘”“'。，、？]"); 
    var rs = "";   
    for (var i = 0; i < value.length; i++) {  
        rs += value.substr(i, 1).replace(pattern, '');   
    }   
    return rs;  
}
function changeSalesReport(type){
	switch(type){
	case 0:
		window.location.href = '/salesReport';
		break;
	case 1:
		window.location.href = '/salesReport_weeks';
		break;
	case 2:
		window.location.href = '/salesReport_month';
		break;
	case 3:
		window.location.href = '/salesReport_season';
		break;
	case 4:
		window.location.href = '/salesReport_years';
		break;
	}
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