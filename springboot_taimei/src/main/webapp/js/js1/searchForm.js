/*
 * @Author: lonhon 
 * @Date: 2018-01-09 11:02:16 
 * @Last Modified by:   github.lonhon 
 * @Last Modified time: 2018-01-09 11:02:16 
 */

var thisflag=true;
var zyshj=0,zxsys=0,zzgl=0,zavgkzl=0,zavgzk=0,zsumrs=0,zsumtd=0,zsumsk=0;     //标题数据
var ttdzrs= [], sskzrs= [],ttdzrss=0,sskzrss=0;
var Allarr={};
var naviChip=0;
var lonhbys = {},lonxssr = {},lonzgl = {};
var hasDataNavi= 0;
var fourChartA, fourChartB;
var bclist=[];
var allTitle= {};



$.fn.searchJson = function(){
	//定义一个json对象
	var paramObj = {};
	//获得from参数数组
	var paramArr = $(this).serializeArray();
	//遍历
	$.each(paramArr,function(i,data){
		//组装成json对象
		paramObj[data.name]=data.value;
		
	});
	//将组装好json对象返回给调用者
	return paramObj;
};
function searchData(data){
	return data += "&companyId="+$(window.parent.document.body).find("#myCompanyList").val();
};
//格式化数字函数，s为数字的字符串,n为保留的位数
function fomatDigit(s, n){  
	if(s!=null && typeof(s)!="undefined" && s!=0){
		if(n==0){
			   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")) + "";  
			   var l = s.split(".")[0].split("").reverse(),  
			   t = "";  
			   for(i = 0; i < l.length; i ++ )  
			   {  
			      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
			   }  
			   return t.split("").reverse().join("");  
		}else{
			   n = n > 0 && n <= 20 ? n : 2;  
			   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
			   var l = s.split(".")[0].split("").reverse(),  
			   r = s.split(".")[1];  
			   t = "";  
			   for(i = 0; i < l.length; i ++ )  
			   {  
			      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
			   }  
			   return t.split("").reverse().join("") + "." + r;  
		}
	}else{
		 return s;
	}
	
} 



$(".sales-check .lhead").on("click",function(){
		if($(this).attr("tag")==="off"){
			$(".sales-check").animate({"width": $(".sr-box-body-chart").width()+'px'});
			$(".sales-check-co").show();
			$(this).attr("tag","on");
			$(".sales-check-close").css({"right":"3%","width":"80px"});
			$(".sales-check-close").show();
			$(".sales-check-open").hide();
		}	
})
$(".sales-check-close").on("click",function(){			
	$(".sales-check").animate({"width":"200px"});
	$(".sales-check-co").hide();
	$(".sales-check .lhead").attr("tag","off");
	$(".sales-check-close").css({"right":"10%","width":"5px"});
	$(this).hide()
	$(".sales-check-open").css({"display":"inline-block"});
})

//航段无数据动画
function SD_hd_null(){
    $("div.SD-hd-null").show();
    setTimeout(function(){
        $(".SD-hd-null").hide();
    }
    ,2000)
}

//报表改版——longhong、2017-5

function afindData(data,leg,type){//重绘图表
	naviChip+=1;
	  $.each(data.everyList,function(i,el){
	      if(leg==el.flyCode){
	    	  //mad(el,type, data.everyList[0]);
	    	  dataCompare(el,type, data.everyList[0]);
	    	  
	      }
	  })
}



//头部航段导航
function create_hd(hdata){
	
	create_abnormal(hdata.success.newmap.everyList[0].dataMap.exceptionFlag);
	Allarr.yeary=[];
	clearAllarr();
	$('.bc').empty();
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
				resetTableData();
				//clearAllarr();
				naviChip=0,AyearArray=[],AoyearArray=[],first_a=[],first_b=[],first_c=[],ofirst_a=[],ofirst_b=[],ofirst_c=[],three_a=[],three_b=[],othree_a=[],othree_b=[],four_a=[],four_b=[],ofour_a=[],ofour_b=[];
				legentArray= [];
				if($("#income-set li.checkFlt").text()=="往返"){
					for(var i =0 ;i< $(".sales-check .leglist li").length;i++){
						$(".sales-check .leglist li").eq(i).removeClass("ck");
					}
					for(var i =0 ;i< $(".sales-check .leglist li").length;i++){
						if($(".sales-check .leglist li").eq(i).attr("group")==$(this).attr("group") && $(".sales-check .leglist li").eq(i).attr("isn") == "true"){//添加同组数据
							
							//这里填充数据true
							afindData(hddata,$(".sales-check .leglist li").eq(i).attr("tag"),true);
							$(".sales-check .leglist li").eq(i).addClass("ck");
						}
					}					
				}
				else{
					$(this).addClass("ck").siblings().removeClass("ck");
					//填充这次的数据false
					afindData(hddata,$(this).attr("tag"),false);
					curGroup=$(this).attr("group");				
				}
			}
			else{
				return;
			}
		})		
	},100)
}


function dataIsNull(d){//判断数据是否为空
	if(d=="" || d==null || d==undefined || d==0){
		return false
	}
	else{
		return true;
	}
}


function clearAllarr(){//重置数据数组
	for (var key in Allarr){
		Allarr[key]=[];
	} 
}


function reDrawChart(data,hd,type){		//重绘图表
	$.each(data.everyList,function(i,el){
		if(hd==el.flyCode){
			if(pageflag=="week" || pageflag=="month"){
				mad(el, type, data.everyList[0]);
			}
		}
	})
}

var lonresetmap = function(){
	$.each(lonhbys,function(i,el){
		if(lonhbys[i]==undefined){
			lonhbys[i]=0;
		}
	})
	$.each(lonxssr,function(i,el){
		if(lonxssr[i]==undefined){
			lonxssr[i]=0;
		}
	})
	$.each(lonzgl,function(i,el){
		if(lonzgl[i]==undefined){
			lonzgl[i]=0;
		}
	})
}

function totalTitle(num,type,n){
	if(n in allTitle){
	    if(type=="add"){
	        allTitle[n]+=toNum(num);
	    }
	    else if(type=="sub"){
	        allTitle[n]-=toNum(num);
	    }
	}
	else{
	    allTitle[n]=toNum(num);
	}
}

//补足数值月份
function fullMonthDate(y,m){
	m = ('000000' + m).substr(-2);
	m = y + '-' + m;
	
	return m;	
}

//求数组并集的长度
var concat = function(arr1,arr2,arr3){  
    if(arguments.length <= 1){  
        return false;  
    }
    var concat_ = function(arr1,arr2){  
        var arr = arr1.concat();  
        for(var i=0;i<arr2.length;i++){  
            arr.indexOf(arr2[i]) === -1 ? arr.push(arr2[i]) : 0;  
        }  
        return arr;  
    }  
    var result = concat_(arr1,arr2);  
    for(var i=2;i<arguments.length;i++){  
        result = concat_(result,arguments[i]);  
    }  
    return result.length;  
}  

function mapToArr(m){	//map值转数组
	var navi = naviChip ==0 ? 1 : naviChip;
	var arr=[];
	for( var key in m ){
		arr[Number(key)-1] = parseFloat((m[key] * 100 / navi) / 100).toFixed(2);
	}
	for(var i = arr.length-1 ; i > -1 ;i--){
		if(!arr[i]) arr.splice(i,1);
	}
	//console.log(m,arr)
	return arr
}

function testVal(val){	//验证数字
	if(typeof(val)=='string'){
		val = val.replace(/,/g,'');
		val = parseFloat(val).toFixed(2);
	}
    val = (val == undefined || val == null || val == '0.00' || val == NaN)? 0 : val;  
    return val
}


function resetTableData(){
	naviChip = 0,
	incomeArray = [], oincomeArray = [],
	legentArray =[], olegentArray= [], trueLegentArray=[],
	otruePieceKzl = [],otruePieceZk = [],otruePieceStrs = [],
	showContentArray = new Map(), oshowContentArray = new Map(),
	pieceIncome = new Map(), pieceZgl = new Map(), pieceXsys = new Map(),
	pieceKzl = new Map(),
	pieceZk = new Map(),
	pieceTdrs = new Map(), pieceSkrs = new Map(), pieceStrs = new Map(),
	pieArray=[0,0],
	weekDataArray= [], monthDataArray= [];	
	for(key in allTitle){
		allTitle[key]= 0;
	}
	
	//年报用
	oincomeArray = [],
	opieceXsys = new Map(),
	opieceZgl = new Map(),
	opieceKzl = new Map(),
	opieceStrs = new Map(),	//总人数
	opieceSkrs = new Map(), ombclist = new Map(), mbclist = new Map(),	//均班
	showoPieceStrs = new Map(),	showPieceStrs = new Map(), perbc = new Map(), operbc = new Map();
	showoPieceSkrs = new Map(),	showPieceSkrs = new Map(), perjb = new Map(), operjb = new Map();
}


var incomeArray = [], oincomeArray = [],
	legentArray = [], olegentArray = [], trueLegentArray = [],
	otruePieceKzl = [],otruePieceZk = [],otruePieceStrs = [],
	showContentArray = new Map(), oshowContentArray = new Map(),
	pieceIncome = new Map(), pieceZgl = new Map(), pieceXsys = new Map(),
	pieceKzl = new Map(),
	pieceZk = new Map(),
	pieceTdrs = new Map(), pieceSkrs = new Map(), pieceStrs = new Map();
	//年报用
	var	oincomeArray = [],
	opieceXsys = new Map(),
	opieceZgl = new Map(),
	opieceKzl = new Map(),
	opieceStrs = new Map(),	//总人数
	opieceSkrs = new Map(),	ombclist = new Map(), mbclist = new Map(),//均班
	showoPieceStrs = new Map(),	showPieceStrs = new Map(),	perbc = new Map(), operbc = new Map(),
	showoPieceSkrs = new Map(),	showPieceSkrs = new Map(),	perjb = new Map(), operjb = new Map();
	
	//季报用
	var showKzl = new Map(), showoKzl = new Map();
	
function dataCompare(data,type,total){
	if(naviChip == hasDataNavi){	//如果缺段时全选，则直接使用totalData数据
		resetTableData();
        naviChip = 0;
		return dataCompare(total, false, total);
	}
    
    var cyshj = testVal(data.dataMap.hbys);
    var cxsys = testVal(data.dataMap.xsys);
    var czgl = testVal(data.dataMap.zgl);
    var ckzl = testVal(data.dataMap.kzl);
    var cpjzk = testVal(data.dataMap.zk);
    var callPerson = Number(data.dataMap.skzrs) + Number(data.dataMap.stzrs);

    totalTitle(toNum(cyshj),"add","yshj");
    totalTitle(toNum(cxsys),"add","xsys");
    totalTitle(toNum(czgl),"add","zgl");
    totalTitle(toNum(ckzl),"add","kzl");
    totalTitle(toNum(cpjzk),"add","pjzk");
    totalTitle(toNum(callPerson),"add","allPerson");
    //汇总数据
    if(type){  //往返        
        $('#allIncome').text(formatNumber(allTitle.yshj,2,1) == '0.00' ? '--' : formatNumber(allTitle.yshj,2,1));
        $('#hourIncome').text(formatNumber(allTitle.xsys / naviChip,2,1) == '0.00' ? '--' : formatNumber(allTitle.xsys / naviChip,2,1));
        $('#raskIncome').text(formatNumber(allTitle.zgl / naviChip, 2, 1));
        $('#allAvgPlf').text((parseFloat(allTitle.kzl*100 / naviChip) / 100).toFixed(2) +'%');
        $('#allAvgDis').text((parseFloat(allTitle.pjzk*100 / naviChip) / 100).toFixed(2) +'%');
        $('#allTotalPC').text(formatNumber(allTitle.allPerson, 0, 1));
    }else{
        $('#allIncome').text(formatNumber(cyshj,2,1));
        $('#hourIncome').text(cxsys > 0 ? formatNumber(cxsys,2,1):'--');
        $('#raskIncome').text(czgl>0 ? formatNumber(czgl,2,1):'--');
        $('#allAvgPlf').text(parseFloat(ckzl).toFixed(2)+'%');
        $('#allAvgDis').text(parseFloat(cpjzk).toFixed(2) +'%');
        $('#allTotalPC').text(formatNumber(callPerson, 0, 1));
    }
    //本次时间轴
    $.each(data.dataMap.data, function(i, ell) {
        if($.inArray(i, legentArray) < 0) legentArray.push(i);        
    });
    //根据本次时间轴取出数据
    $.each(legentArray, function(ii, ell) {
    	incomeArray[ii] = incomeArray[ii] ? incomeArray[ii] : 0;
    	pieceZgl[ell] = pieceZgl[ell]? pieceZgl[ell] : 0;
    	pieceXsys[ell] = pieceXsys[ell]? pieceXsys[ell] : 0;
    	pieceKzl[ell] = pieceKzl[ell]? pieceKzl[ell] : 0;
    	pieceZk[ell] = pieceZk[ell]? pieceZk[ell] : 0;
    	pieceTdrs[ell] = pieceTdrs[ell]? pieceTdrs[ell] : 0;
    	pieceStrs[ell] = pieceStrs[ell]? pieceStrs[ell] : 0;
    	pieceSkrs[ell] = pieceSkrs[ell]? pieceSkrs[ell] : 0;
        if(data.dataMap.data[ell]){	//当前日期有航班营收        	统计各个指标
            incomeArray[ii] += toNum(data.dataMap.data[ell].hbys);
            pieceZgl[ell] += toNum(data.dataMap.data[ell].set_Ktr_Ine ? data.dataMap.data[ell].set_Ktr_Ine : 0);
            
            pieceXsys[ell] += toNum(data.dataMap.data[ell].xssr ? data.dataMap.data[ell].xssr : 0);
            pieceKzl[ell] += toNum(data.dataMap.data[ell].pjkzl ? data.dataMap.data[ell].pjkzl : 0);            
            pieceZk[ell] += toNum(data.dataMap.data[ell].avg_Dct ? data.dataMap.data[ell].avg_Dct : 0); 
            
            var zrsa = data.dataMap.data[ell].grp_Nbr ? toNum(data.dataMap.data[ell].grp_Nbr) : 0;
            pieceTdrs[ell] += zrsa;
            var zrsb = data.dataMap.data[ell].wak_tol_Nbr ? toNum(data.dataMap.data[ell].wak_tol_Nbr)-zrsa : 0;
            pieceSkrs[ell] += zrsb;
            var zrsc = toNum(zrsa + zrsb);
            pieceStrs[ell] += zrsc;
        }else{
        	incomeArray[ii] += 0;
            pieceZgl[ell] += 0;     
            
            pieceXsys[ell] += 0;
            pieceKzl[ell] += 0;            
            pieceZk[ell] += 0; 
            
            pieceTdrs[ell] += 0;
            pieceSkrs[ell] += 0;
            pieceStrs[ell] += 0;    
        }
        
        if(pageflag == 'week'){
        	trueLegentArray[ii] = ell.split('-')[1]+ '\n周' + parseInt(ell.split('-')[0]);
        	if(naviChip==0){
            	showContentArray[ell.split('-')[1]+ '\n周' + parseInt(ell.split('-')[0])] = 
            		'航班营收:' + (incomeArray[ii]>0 ? formatNumber(incomeArray[ii],2,1):'--') + 
            		'<br>小时营收:' + (pieceXsys[ell]>0 ? formatNumber(pieceXsys[ell],2,1):'--') + 
            		'<br>座公里收入:' + (pieceZgl[ell]>0 ? formatNumber(pieceZgl[ell],2,1):'--');
            	
        	}else{
                $('#raskIncome').text('--');
            	showContentArray[ell.split('-')[1]+ '\n周' + parseInt(ell.split('-')[0])] = 
            		'航班营收:' + formatNumber(incomeArray[ii],2,1) + 
            		'<br>小时营收:'+ (pieceXsys[ell]>0 ? formatNumber(pieceXsys[ell]/naviChip,2,1):'--') + 
            		'<br>座公里收入: --';
        	}
        }else if(pageflag == 'month'){
        	trueLegentArray[ii] = Number(ell.split('.')[1]);
        	if(naviChip==0){
        		showContentArray[parseInt(ell.split('.')[1])] = 
        			'航班营收：'+formatNumber(incomeArray[ii],2,1)+
        			'<br/>小时营收：'+ (pieceXsys[ell]>0 ? formatNumber(pieceXsys[ell],2,1):'--')+ 
        			'<br/>座公里收入：'+ (pieceZgl[ell]>0 ? formatNumber(pieceZgl[ell],2,1):'--');
        	}else{
                $('#raskIncome').text('--');
        		showContentArray[parseInt(ell.split('.')[1])] =
        			'航班营收：'+formatNumber(incomeArray[ii],2,1)+
        			'<br/>小时营收：'+ (pieceXsys[ell]>0 ? formatNumber(pieceXsys[ell]/naviChip,2,1):'--')+
        			'<br/>座公里收入：--'; 
        	}
        }        
    });//当前周、月数据遍历结束

    if(data.dataMap.olddata){
        $.each(data.dataMap.olddata, function(i, ell) {
        	if($.inArray(i, olegentArray) < 0) olegentArray.push(i);
        });
        //历史数据遍历
        $.each(olegentArray, function(oii, oell) {	//上周数据
        	var navi = naviChip>0 ? naviChip : 1;
        	oincomeArray[oii] = oincomeArray[oii] ? oincomeArray[oii] : 0; 
        	otruePieceKzl[oii] = otruePieceKzl[oii] ? otruePieceKzl[oii] : 0; 
        	otruePieceZk[oii] = otruePieceZk[oii] ? otruePieceZk[oii] : 0; 
        	otruePieceStrs[oii] = otruePieceStrs[oii] ? otruePieceStrs[oii] : 0;    	
        	if(data.dataMap.olddata[oell]){
        		var key = data.dataMap.olddata[oell];
        		oincomeArray[oii] += Number(key.hbys);
            	otruePieceKzl[oii]= ((Number(key.pjkzl)>0 ? Number(key.pjkzl): otruePieceKzl[oii]) + otruePieceKzl[oii])/navi;
            	otruePieceZk[oii]= ((Number(key.avg_Dct)>0 ? Number(key.avg_Dct): otruePieceZk[oii]) + otruePieceZk[oii])/navi;
            	otruePieceStrs[oii] = otruePieceStrs[oii] + Number(key.wak_tol_Nbr) + Number(key.grp_Nbr);
        	}else{
        		oincomeArray[oii] += 0;
            	otruePieceKzl[oii]= otruePieceKzl[oii];
            	otruePieceZk[oii]= otruePieceZk[oii];
            	otruePieceStrs[oii]= otruePieceStrs[oii];
        	}
        	

        })
    	
    }
    if(pageflag === "week"){
    	oincomeArray = [eval(oincomeArray.join("+")) / oincomeArray.length];
    }
    theNewCurve("income", trueLegentArray, incomeArray, oincomeArray, showContentArray);	//-----收入图
    var truePieceKzl = [], truePieceZk = [], truePieceStrs = [];
    $.each(pieceKzl,function(o, ol){
    	truePieceKzl.push((ol / (naviChip >0? naviChip:1)).toFixed(2));	//重新获得客座率数组
    })
    $.each(pieceZk,function(o, ol){
    	truePieceZk.push((ol / (naviChip >0? naviChip:1)).toFixed(2));	//重新获得折扣数组
    })
    pieArray= [0,0];
    $.each(pieceStrs,function(so, ol){	//重新获得散团总人数数组
    	truePieceStrs.push(ol);
        if(pageflag == 'week'){
        	weekDataArray[so.split('-')[1]] = [pieceTdrs[so], pieceSkrs[so]];
        }else if(pageflag == 'month'){
        	monthDataArray[parseInt(so.split('.')[1])] = [pieceTdrs[so], pieceSkrs[so]];
    	}
    	pieArray[0] += pieceTdrs[so]; 		//TD
    	pieArray[1] += pieceSkrs[so] ;    	//SK
    })
    theCurve("avg_set_ine", trueLegentArray, truePieceKzl, avgArrVal(otruePieceKzl,false));	//-----客座率图
    theCurve("person_dct",trueLegentArray,truePieceZk, avgArrVal(otruePieceZk,false));
    fourChartA= truePieceStrs;
    barThourthReport(pcname,trueLegentArray,truePieceStrs, avgArrVal(otruePieceStrs,false));		//-----柱状图
    pieFivthReport(piename,pieArray);	//-----饼图
}

function isErrorNum(num){
	if(!num) return '--';
	num = num.replace(/,/g,'');
	num = Number(num) > 0 ? num : '--';
	return num;
}

function avgArrVal(arr,t){	//将数组的值求和or平均后返回单值数组
	var val = 0;
	var mark = 0;
	for(var a = 0 ; a < arr.length ; a++){
		if(arr[a] > 0){
			val += arr[a];
			mark++;
		}
	}
	mark = mark>0? mark:1;
	return (t ? [val] : [Number(parseFloat(val/mark).toFixed(2))]);
}

function getYearAndMonth(start, end) {
    var result = [];
    var starts = start.split('-');
    var ends = end.split('-');
    var staYear = parseInt(starts[0]);
    var staMon = parseInt(starts[1]);
    var endYear = parseInt(ends[0]);
    var endMon = parseInt(ends[1]);
    var monResult = [];
    while (staYear <= endYear) {
        if (staYear === endYear) {
            while (staMon <= endMon) {
                result.push(staYear+'-'+staMon);
                monResult.push(fullMonthDate('',staMon).replace('-',''));
                staMon++;
            }
            staYear++;
        } else {
            if (staMon > 12) {
                staMon = 1;
                staYear++;
            }
            result.push(staYear+'-'+staMon);
            monResult.push(fullMonthDate('',staMon).replace('-',''));
            staMon++;
        }
    }
    return monResult;
    //return result;  //年+月
}


function aArrAdd(arr,type,name){	//相加减	
	var newArr=[];
	if(Allarr[name]==undefined || Allarr[name].length==0){
		Allarr[name]=arr;
		newArr=arrToNumArr(arr);
	}
	else if(type=="add"){
		for(var i=0;i<Allarr[name].length;i++){
			arr=arrToNumArr(arr);
			Allarr[name][i] += arr[i];
			newArr[i]=parseInt(Allarr[name][i]);
		}		
	}
	else{	//sub
		for(var i=0;i<Allarr[name].length;i++){
			Allarr[name][i] -=arr[i];
			newArr[i]=parseInt(Allarr[name][i]);
		}		
	}
	return newArr;
}

//不封装玩什么面向对象？？？
function oArrComp(arr,type,name){	//求和后平均
	var newArr=[];	
	if(Allarr[name]==undefined  ||  Allarr[name].length==0 ){
		Allarr[name]=arrToNumArr(arr);
		newArr=arrToNumArr(arr);
	}
	else if(type=="add"){		//add
		for(var i=0;i<Allarr[name].length;i++){
			arr=arrToNumArr(arr);
			Allarr[name][i] += arr[i];
			newArr[i]=parseFloat(Allarr[name][i]/naviChip).toFixed(2);
		}
	}
	else{		//sub
		for(var i=0;i<Allarr[name].length;i++){
			Allarr[name][i] -=arr[i];
			newArr[i]=parseFloat(Allarr[name][i]/naviChip).toFixed(2);
		}		
	}
	return newArr;
}



function oArrCompA(arr,type,name){	//求和后平均
	var newArr=[];	
	if(Allarr[name]==undefined  ||  Allarr[name].length==0 ){
		Allarr[name]=arrToNumArr(arr);
		newArr=arrToNumArr(arr);
	}
	else if(type=="add"){		//add
		arr=arrToNumArr(arr);
		for(var i=0;i<Allarr[name].length;i++){
			Allarr[name][i] += arr[i];
			newArr[i]=parseFloat(Allarr[name][i]/2).toFixed(2);
		}
	}
	return newArr;
}


function oArrCompSum(arr,type,name){	//求和后返回和
	var newArr=[];	
	if(Allarr[name]==undefined  ||  Allarr[name].length==0 ){
		Allarr[name]=arrToNumArr(arr);
		newArr=arrToNumArr(arr);
	}
	else if(type=="add"){		//add
		for(var i=0;i<Allarr[name].length;i++){
			arr=arrToNumArr(arr);
			Allarr[name][i] += arr[i];
			newArr[i]=parseFloat(Allarr[name][i]).toFixed(2);
		}
	}
	else{		//sub
		for(var i=0;i<Allarr[name].length;i++){
			Allarr[name][i] -=arr[i];
			newArr[i]=parseFloat(Allarr[name][i]).toFixed(2);
		}		
	}
	return newArr;
}

function arrToNumArr(arr){//数字化数组
	for(var i=0;i<arr.length;i++){
		arr[i]=Number(arr[i]);
	}
	return arr;
}


function tipToNumArr(arr){//格式化表1 tip
	for(var i=0;i<arr.length;i++){
		arr[i]=toNum(arr[i]);
	}
	return arr;
}

function toNum(nn){//数字化数字	
	var n=nn;
	nn=nn.toString();
	if(nn.indexOf(",")>-1){
		n = parseFloat(nn.replace(/,/g,""));
	}	
	else if(nn.indexOf("%")>-1){
		n = parseFloat(nn.replace(/%/g,""));
	}
	return Number(n);
}




function formatNumber(num,cent,isThousand) {
	num= num==undefined? 0:num;
    num = num.toString().replace(/\$|\,/g,'');
 
    // 检查传入数值为数值类型
     if(isNaN(num))
      num = "0";
 
    // 获取符号(正/负数)
    sign = (num == (num = Math.abs(num)));
 
    num = Math.floor(num*Math.pow(10,cent)+0.50000000001); // 把指定的小数位先转换成整数.多余的小数位四舍五入
    cents = num%Math.pow(10,cent);       // 求出小数位数值
    num = Math.floor(num/Math.pow(10,cent)).toString();  // 求出整数位数值
    cents = cents.toString();        // 把小数位转换成字符串,以便求小数位长度
 
    // 补足小数位到指定的位数
    while(cents.length<cent)
     cents = "0" + cents;
 
    if(isThousand) {
     // 对整数部分进行千分位格式化.
     for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
      num = num.substring(0,num.length-(4*i+3))+','+ num.substring(num.length-(4*i+3));
    }
 
    if (cent > 0)
     return (((sign)?'':'-') + num + '.' + cents);
    else
     return (((sign)?'':'-') + num);
}



//·····························表格图表转换
var createChanger= function(data, stime, etime, flyNum, type){
	setTimeout(function(){
		$('#TMtable').css('width',$(".sr-box-body-chart").width()+'px');		
	},200)
	//表格标题
	if(type== 1){	//周报/月报
		$('#TMtable .headArea h1').html(formatFlyNum(flyNum) +'&nbsp;&nbsp;&nbsp;'+ formatDate(stime, etime) + '销售' + $('.sr-box-body-report li.set-liset>div').text());	
		var tbhead= '<thead><tr><th>日期</th><th>航段</th><th>团队客量（人）</th><th>散团总客量（人）</th><th>综合客座率（%）</th><th>团队营收（元）</th><th>散团总营收（元）</th><th>座公里收入（元）</th><th>小时收入（元）</th><th>平均折扣（%）</th><th>平均团队折扣（%）</th></tr></thead>';
		drawtable(data.success.newmap.everyList, tbhead, formatFlyNum(flyNum));
		$('#TMtable .TMtable-wrap table').attr('id','lonhon');
		$('#TMtable .copyThead-area').remove();		
		$('#TMtable').append('<div class="copyThead-area"><table id="copyThead">'+ tbhead +'</table></div>');
		
	}else if(type == 2){ 	//季报
		$('#TMtable .copyThead-area').remove();
		$('#TMtable').append('<div class="copyThead-area"><table id="copyThead"></table></div>');
		$('#TMtable #copyThead').append($('#TMtable #lonhon thead').clone());
		$('#TMtable .headArea h1').html(formatFlyNum(flyNum) +'&nbsp;&nbsp;&nbsp;'+ stime.substring(0,4)+ '销售' + $('.sr-box-body-report li.set-liset>div').text());	
		yearDrawtable(data.success.newmap.everyList, type, formatFlyNum(flyNum));
		
	}else{	//年报
		$('#TMtable .copyThead-area').remove();
		$('#TMtable').append('<div class="copyThead-area"><table id="copyThead"></table></div>');
		$('#TMtable #copyThead').append($('#TMtable #lonhon thead').clone());
		$('#TMtable .headArea h1').html(formatFlyNum(flyNum) +'&nbsp;&nbsp;&nbsp;'+ stime.substring(0,4)+ '销售' + $('.sr-box-body-report li.set-liset>div').text());	
		yearDrawtable(data.success.newmap.everyList, type, formatFlyNum(flyNum));		
	}

	//添加回到顶部按钮
	$('#TMtable div.back-top').remove()
	$('#TMtable').append('<div class="back-top"></div>');
	$('.TMtable-wrap').animate({'scrollTop':'0px'}, 'fast');
	$('#TMtable .back-top').on('click', function(){
		$('.TMtable-wrap').animate({'scrollTop':'0px'}, 'fast');
	})
	
	
	$('.chart-changer').unbind('click');
	var clickDefer = true;
	$('.chart-changer').on('click', function(){		//切换按钮
			if($(this).attr('state') == 'chart'){
				$('.sales-check').hide();
				//$('.sr-box-body-chart').hide();
				$('.sr-box-body-chart').css('transform','translate(0,2000px)');
				$('#TMtable').css('transform','translate(0,0)');
				$(this).attr('state', 'table');
				$(this).children('span').html('&#xe6bc;');
				$('.TMtable-wrap').animate({'scrollTop':'0px'}, 'fast');
			}else{
				$('#TMtable').css('transform','translate(0,2000px)');
				$('.sales-check').show();
				$('.sr-box-body-chart').css('transform','translate(0,0)')
				$(this).attr('state', 'chart');
				$(this).children('span').html('&#xe750;');
			}
			if($(this).attr('isLack') == 'true'){
				$('.offsetContainer p.null-data-tips').show();
				setTimeout(function(){
					$('.offsetContainer p.null-data-tips').remove();
				}, 5000)
			}
	})
	
	
	$('#TMtable p.btn-export-out').unbind('click');
	$('#TMtable p.btn-export-out').on('click', function(){	//导出表格
		method1('lonhon');
	})
	
	
	$('#TMtable table tbody tr').hover(function(){	//tr移入变色
		if($(this).children('td:first-child').attr('rowspan') != undefined){
			$(this).children('td:first-child').siblings().addClass('hover-color');
		}else{
			$(this).addClass('hover-color');
		}
	},function(){
		if($(this).children('td:first-child').attr('rowspan') != undefined){
			$(this).children('td:first-child').siblings().removeClass('hover-color');
		}else{
			$(this).removeClass('hover-color');
		}
	})
	
}
var drawtable = function(data, head, fly){	//周报、月报填充数据
	var a = $('.sr-box-body-report .set-liset>div').text();
	var pageType = a == '周报'?1:0 ;
	if(pageType == 0) pageType = a == '月报'?2:0 ;
	$('#TMtable .tableArea thead').remove();
	$('#TMtable .tableArea tbody tr').remove();
	var lastcon= '<tr type="last"><td>上周</td><td>总计</td>', thiscon= '<tr type="this"><td>本周</td><td>总计</td>', jtcon= '';
	var thisD= data[0].dataMap, lastD= data[0].dataMap.oldexcelMap;
	if(pageType == 2) {
		lastcon= '<tr type="last"><td>上月</td><td>总计</td>', thiscon= '<tr type="this"><td>本月</td><td>总计</td>', jtcon= '';
	}
	thiscon += '<td>'+ formatNumber(thisD.stzrs, 0, 1) +'</td><td>'+ formatNumber(Number(thisD.skzrs) + Number(thisD.stzrs), 0, 1) +'</td><td>'+ formatNumber(thisD.kzl, 2, 1) +'</td><td>'+ formatNumber(thisD.tdsr, 2, 1) +'</td><td>'+ formatNumber(thisD.hbys, 2, 1) +'</td><td>' + formatNumber(thisD.zgl, 2, 1)  +'</td><td>' + formatNumber(thisD.xsys, 2, 1) +'</td><td>'+ formatNumber(thisD.zk, 2, 1) +'</td><td>'+ formatNumber(thisD.tdzk, 2, 1) +'</td></tr>';
	if(data[0].dataMap.oldexcelMap !=null){	//判断有无历史数据
		lastcon += '<td>'+ formatNumber(lastD.stzrs, 0, 1) +'</td><td>'+ formatNumber(Number(lastD.skzrs) + Number(lastD.stzrs), 0, 1) +'</td><td>'+ formatNumber(lastD.kzl, 2, 1) +'</td><td>'+ formatNumber(lastD.tdsr, 2, 1) +'</td><td>'+ formatNumber(lastD.hbys, 2, 1) +'</td><td>' + formatNumber(lastD.zgl, 2, 1)  +'</td><td>' + formatNumber(lastD.xsys, 2, 1) +'</td><td>'+ formatNumber(lastD.zk, 2, 1) +'</td><td>'+ formatNumber(lastD.tdzk, 2, 1) +'</td></tr>';
	}else{
		lastcon += '<td colspan= "9">暂无数据</td></tr>';
	}
	
	$('#TMtable .tableArea table').prepend(head);
	$('#TMtable .tableArea table tbody').append(formatInfo(lastcon));
	$('#TMtable .tableArea table tbody').append(formatInfo(thiscon));
	var dateArr= Object.keys(data[0].dataMap.data)
	for(var i= 0; i< dateArr.length; i++ ){
		jtcon = '';
		var la=0,lb=0,lc=0,ld=0,le=0,lf=0,lg=0,lh=0;
		var count = 0;
		for(var j = 1; j< data.length; j++ ){
			var key = data[j].dataMap.data[dateArr[i]];			
			var list= data[j].flyCode.split('-');
			
			if(data[j].dataMap.hasData == true && key){
				count ++;
				if( j ==1 ){
				    jtcon+= '<tr><td rowspan=' + data.length + '>'+ shapeDate(dateArr[i], pageType) +'</td><td>'
				    + search_getCityName(list[0]) +' - '+ search_getCityName(list[1]) + '</td><td>'
				    + formatNumber(key.grp_Nbr, 0, 1) +'</td><td>' 
				    + formatNumber(Number(key.wak_tol_Nbr) + Number(key.grp_Nbr), 0, 1) +'</td><td>' 
				    + formatNumber(key.pjkzl, 2, 1) + '</td><td>' 
				    + formatNumber(key.grp_Ine, 2,1) + '</td><td>' 
				    + formatNumber(key.hbys, 2, 1) + '</td><td>' 
				    + formatNumber(key.set_Ktr_Ine, 2, 1)+ '</td><td>' 
				    + (Number(key.xssr)>0?formatNumber(key.xssr, 2, 1):'--') + '</td><td>' 
				    + formatNumber(key.avg_Dct, 2, 1) + '</td><td>' 
				    + formatNumber(key.grp_Dct, 2, 1) + '</td></tr>';
				}else{
				    jtcon+= '<tr><td>'+ search_getCityName(list[0]) +' - '+ search_getCityName(list[1]) + '</td><td>'
				    + formatNumber(key.grp_Nbr, 0, 1) +'</td><td>' 
				    + formatNumber(Number(key.wak_tol_Nbr) + Number(key.grp_Nbr), 0, 1) +'</td><td>' 
				    + formatNumber(key.pjkzl, 2, 1) + '</td><td>' 
				    + formatNumber(key.grp_Ine, 2,1) + '</td><td>' 
				    + formatNumber(key.hbys, 2, 1) + '</td><td>' 
				    + formatNumber(key.set_Ktr_Ine, 2, 1)+ '</td><td>' 
				    + (Number(key.xssr)>0?formatNumber(key.xssr, 2, 1):'--') + '</td><td>' 
				    + formatNumber(key.avg_Dct, 2, 1) + '</td><td>' 
				    + formatNumber(key.grp_Dct, 2, 1) + '</td></tr>';
				}   
			}else{
				if( j ==1 ){
					jtcon+= '<tr><td rowspan=' + data.length + '>'+ shapeDate(dateArr[i], pageType) +'</td><td>'+ search_getCityName(list[0]) +' - '+ search_getCityName(list[1]) + '</td><td colspan= "8">暂无数据</td></tr>';
				}else{
					jtcon+= '<tr><td>'+ search_getCityName(list[0]) +' - '+ search_getCityName(list[1]) + '</td><td colspan= "9">暂无数据</td></tr>';
				}				
			}
		}
		if(count >0){
            var sumdata= data[0].dataMap.data[dateArr[i]];
			if(count == data.length-1){	//没缺段就直接取
				jtcon+='<tr class="sumtr"><td>'+ fly +'</td><td>'+ formatNumber(sumdata.grp_Nbr, 0, 1) + '</td><td>' + formatNumber(Number(sumdata.wak_tol_Nbr), 0, 1) + '</td><td>' + formatNumber(sumdata.pjkzl, 2, 0) + '</td><td>' + formatNumber(sumdata.grp_Ine, 2, 1) + '</td><td>' + formatNumber(sumdata.hbys, 2, 1) + '</td><td>' + formatNumber(sumdata.set_Ktr_Ine, 2, 1)+ '</td><td>' + formatNumber(sumdata.xssr, 2, 1)+ '</td><td>' + formatNumber(sumdata.avg_Dct, 2, 1)+ '</td><td>' + formatNumber(sumdata.grp_Dct, 2, 1)+ '</td></tr>';
			}else{	//缺段就求平均
				jtcon+='<tr class="sumtr"><td>'+ fly +'</td><td>'+ formatNumber(sumdata.grp_Nbr, 0, 1) + '</td><td>' + formatNumber(Number(sumdata.wak_tol_Nbr), 0, 1) + '</td><td>' + formatNumber(sumdata.pjkzl, 2, 0) + '</td><td>' + formatNumber(sumdata.grp_Ine, 2, 1) + '</td><td>' + formatNumber(sumdata.hbys, 2, 1) + '</td><td>' + formatNumber(sumdata.set_Ktr_Ine, 2, 1)+ '</td><td>' + formatNumber(sumdata.xssr, 2, 1)+ '</td><td>' + formatNumber(sumdata.avg_Dct, 2, 1)+ '</td><td>'+ formatNumber(sumdata.grp_Dct, 2, 1)+ '</td></tr>';
			}
		}else{
			jtcon+= '<tr class="sumtr"><td>'+ fly +'</td><td colspan= "9">暂无数据</td></tr>';

		}
		
		$('#TMtable .tableArea table tbody').append(formatInfo(jtcon));		
	}

}
var fomartYear= function(date){
	if(date.split('-').length>1){
		return (Number(date.split('-')[0])-1 +'-'+ date.split('-')[0]);		
	}else{
		return (Number(date)-1);
	}
	
}
var yearDrawtable = function(data, type, fly){	//季报、年报填充数据
	var thisdata= data[0].dataMap;
	var lastdata= data[0].dataMap.excelMapOld;
	var a = $('.sr-box-body-report .set-liset>div').text();
	var pageType = a == '季报'?3:0 ;
	var lackMark= false;
	if(pageType == 0) pageType = a == '年报'?4:0 ;
	$('#TMtable .tableArea tbody tr').remove();
	$('#TMtable .tableArea #h-table').hide();
	var mainTable= $('#TMtable .tableArea #lonhon tbody');
	//汇总表
	if(pageType == 3){	//季报
		var dateA = $('.sr-box-body-date ul.sr-box-body-date-body li.list h3').text();
		var dateB = $('.sr-box-body-date ul.sr-box-body-date-body li.list p').text();
		var olddata='';
		if(lastdata !=null){	//判断有无历史数据
			olddata= '<tr><td>'+ (fomartYear(dateA))+' '+dateB + '</td><td>总计</td><td>'+ formatNumber(lastdata.zrs, 0, 1) + '</td><td>'+ formatNumber(lastdata.jbrs, 2, 1) + '</td><td>'+ formatNumber(lastdata.kzl, 2, 1) + '</td><td>'+ formatNumber(lastdata.zys, 2, 1) + '</td><td>'+ formatNumber((parseFloat(lastdata.zys.replace(/,/g,''))/parseFloat(lastdata.zbc)),2,1) + '</td><td>'+ formatNumber(lastdata.zgl, 2, 1) + '</td><td>' + formatNumber(lastdata.xsys, 2, 1) + '</td><td>' + formatNumber(lastdata.pjzk, 2, 1) +'</td><td>'+ formatNumber(lastdata.pjtdzk, 2, 1) +'</td></tr>';
		}else{
			olddata += '<td colspan= "9">暂无数据</td></tr>';
		}
		//老数据
		$(mainTable).append(formatInfo(olddata));
		//本次数据
		var nowdata= '<tr><td>'+ dateA+' '+dateB + '</td><td>总计</td><td>'+ formatNumber(thisdata.zrs, 0, 1) + '</td><td>'+ formatNumber(thisdata.jbrs, 2, 1) + '</td><td>'+ formatNumber(thisdata.kzl, 2, 1) + '</td><td>'+ formatNumber(thisdata.zys, 2, 1) + '</td><td>'+ formatNumber((parseFloat(thisdata.zys.replace(/,/g,''))/parseFloat(thisdata.zbc)),2,1) + '</td><td>'+ formatNumber(thisdata.zgl, 2, 1) + '</td><td>' + formatNumber(thisdata.xsys, 2, 1) + '</td><td>' + formatNumber(thisdata.pjzk, 2, 1) +'</td><td>'+ formatNumber(thisdata.pjtdzk, 2, 1) +'</td></tr>';
		$(mainTable).append(formatInfo(nowdata));
	}else{	//年报
		//老数据
		var olddata='';
		if(lastdata !=null){
			olddata= '<tr><td>'+ (Number($('.sr-box-body-date-body div.list').text())-1) + '年</td><td>总计</td><td>'+ formatNumber(lastdata.zrs, 0, 1) + '</td><td>'+ formatNumber(lastdata.jbrs, 2, 1) + '</td><td>'+ formatNumber(lastdata.kzl, 2, 1) + '</td><td>'+ formatNumber(lastdata.zys, 2, 1) + '</td><td>'+ formatNumber((parseFloat(lastdata.zys.replace(/,/g,''))/parseFloat(lastdata.zbc)),2,1) + '</td><td>'+ formatNumber(lastdata.zgl, 2, 1) + '</td><td>' + formatNumber(lastdata.xsys, 2, 1) + '</td><td>' + formatNumber(lastdata.pjzk, 2, 1) +'</td><td>'+ formatNumber(lastdata.pjtdzk, 2, 1) +'</td></tr>';
		}else{
			olddata= '<tr><td>'+ (Number($('.sr-box-body-date-body div.list').text())-1) + '年</td><td>总计</td><td colspan="9">暂无数据</td></tr>' 
		}
		$(mainTable).append(formatInfo(olddata));
		//本次数据
		var nowdata= '<tr><td>'+ $('.sr-box-body-date-body div.list').text() + '年</td><td>总计</td><td>'+ formatNumber(thisdata.zrs, 0, 1) + '</td><td>'+ formatNumber(thisdata.jbrs, 2, 1) + '</td><td>'+ formatNumber(thisdata.kzl, 2, 1) + '</td><td>'+ formatNumber(thisdata.zys, 2, 1) + '</td><td>'+ formatNumber((parseFloat(thisdata.zys.replace(/,/g,''))/parseFloat(thisdata.zbc)),2,1) + '</td><td>'+ formatNumber(thisdata.zgl, 2, 1) + '</td><td>' + formatNumber(thisdata.xsys, 2, 1) + '</td><td>' + formatNumber(thisdata.pjzk, 2, 1) +'</td><td>'+ formatNumber(thisdata.pjtdzk, 2, 1) +'</td></tr>';
		$(mainTable).append(formatInfo(nowdata));
	}	
	//详情表
	var dateArr= Object.keys(thisdata.monthData);
	for(var i= 0; i< dateArr.length; i++ ){
		jtcon = '';
		var la=0,lb=0,lc=0,ld=0,le=0,lf=0,lg=0,lh=0;		
		var count = 0;
		for(var j = 1; j< data.length; j++ ){
			var key = data[j].dataMap.monthData[dateArr[i]];
			var list= data[j].flyCode.split('-');
			la += parseFloat(key? key.hbys : 0)> 0 ? parseFloat(key.xssr):0;
			var newtrarea= '';
			count ++;
			if(key){
				if( j ==1 ){
					jtcon+= '<tr><td rowspan= ' + data.length + '>' 
						+ yearformatDate(dateArr[i]) +'</td><td>'
						+ search_getCityName(list[0]) +' - '+ search_getCityName(list[1]) + '</td><td>'
						+ formatNumber(key.stzsr, 0, 1) +'</td><td>' 
						+ formatNumber(key.jbrs, 2, 1) +'</td><td>' 
						+ formatNumber(key.pjkzl, 2, 1) +'</td><td>' 
						+ formatNumber(key.hbys, 2, 1) +'</td><td>' 
						+ formatNumber(parseFloat(key.hbys)/parseFloat(key.banci), 2, 1) +'</td><td>' 
						+ formatNumber(key.set_Ktr_Ine, 2, 1)+'</td><td>' 
						+ (Number(key.xssr)>0?formatNumber(key.xssr, 2, 1):'--') + '</td><td>' 
						+ formatNumber(key.pjzk, 2, 1) + '</td><td>' 
						+ formatNumber(key.grp_Dct, 2, 1) + '</td></tr>';
				}else{
					jtcon+= '<tr><td>'
						+ search_getCityName(list[0]) +' - '+ search_getCityName(list[1]) + '</td><td>' 
						+ formatNumber(key.stzsr, 0, 1) +'</td><td>' 
						+ formatNumber(key.jbrs, 2, 1) +'</td><td>' 
						+ formatNumber(key.pjkzl, 2, 1) +'</td><td>' 
						+ formatNumber(key.hbys, 2, 1) +'</td><td>' 
						+ formatNumber(parseFloat(key.hbys)/parseFloat(key.banci), 2, 1) + '</td><td>' 
						+ formatNumber(key.set_Ktr_Ine, 2, 1) + '</td><td>' 
						+ (Number(key.xssr)>0?formatNumber(key.xssr, 2, 1):'--') + '</td><td>' 
						+ formatNumber(key.pjzk, 2, 1) + '</td><td>' 
						+ formatNumber(key.grp_Dct, 2, 1) + '</td></tr>';
				}	
			}else{
				if( j ==1 ){
					jtcon+= '<tr><td rowspan= ' + data.length + '>'+ yearformatDate(dateArr[i]) +'</td><td>' + search_getCityName(list[0]) +' - '+ search_getCityName(list[1]) + '</td><td colspan="8">暂无数据</td></tr>';
				}else{
					jtcon+= '<tr><td>'+ search_getCityName(list[0]) +' - '+ search_getCityName(list[1]) + '</td><td colspan="9">暂无数据</td></tr>';
				}				
			}
			
		}
		if(count > 0){
			var sumdata= data[0].dataMap.monthData[dateArr[i]];
			if(count == data.length-1){	//没缺段就直接取
				jtcon+='<tr class="sumtr"><td>'+ fly +'</td><td>'+ formatNumber(sumdata.stzsr, 0, 1) + '</td><td>' + formatNumber(sumdata.jbrs, 2, 1) + '</td><td>' + formatNumber(sumdata.pjkzl, 2, 0) + '</td><td>' + formatNumber(sumdata.hbys, 2, 1)+ '</td><td>' + formatNumber(parseFloat(sumdata.hbys)/parseFloat(sumdata.banci), 2, 1)+ '</td><td>' + formatNumber(sumdata.set_Ktr_Ine, 2, 1)+'</td><td>' + formatNumber(sumdata.xssr, 2, 1) + '</td><td>' + formatNumber(sumdata.pjzk, 2, 1) + '</td><td>'+ formatNumber(sumdata.grp_Dct, 2, 1) + '</td></tr>'; 
			}else{	//缺段就求平均
				jtcon+='<tr class="sumtr"><td>'+ fly +'</td><td>'+ formatNumber(sumdata.stzsr, 0, 1) + '</td><td>' + formatNumber(sumdata.jbrs, 2, 1) + '</td><td>' + formatNumber(sumdata.pjkzl, 2, 0) + '</td><td>' + formatNumber(sumdata.hbys, 2, 1)+ '</td><td>' + formatNumber(parseFloat(sumdata.hbys)/parseFloat(sumdata.banci), 2, 1)+ '</td><td>' + formatNumber(sumdata.set_Ktr_Ine, 2, 1)+'</td><td>' + formatNumber(la/count, 2, 1) + '</td><td>' + formatNumber(sumdata.pjzk, 2, 1) + '</td><td>'+ formatNumber(sumdata.grp_Dct, 2, 1) + '</td></tr>'; 
			}
			$(mainTable).append(formatInfo(jtcon));
		}else{
			lackMark= true;
		}
	}
	if(lackMark){
		$('.chart-changer').attr('isLack','true');
		$('.offsetContainer').prepend('<p class="null-data-tips">存在超过30天无数据情况。</p>');		
	}
}

$('.TMtable-wrap').on('scroll', function(e){	//监听滚动
	var sh = $(this).scrollTop();
	if(sh > 300){	//回到顶部
		$('.TMtable-container div.back-top').show();
	}else{
		$('.TMtable-container div.back-top').hide();
	}
	if($('#lonhon thead').offset().top < 0 ){
		//固定
		$('#TMtable .copyThead-area').css('width',$(".tableArea").width()+'px');	
		$('#TMtable .copyThead-area #copyThead').css('width',$(".tableArea").width()+'px');	
		$('#TMtable .copyThead-area').show();
	}else{
		//恢复
		$('#TMtable .copyThead-area').hide();
	}
})


var pieBarData= function(data,type){
	var list = [];
	if(type){
		for(var a in data){
			list.push(data[a][0]);
		}
	}else{
		for(var a in data){
			list.push(data[a][1]);
		}
	}
	return list;
}

var formatInfo= function(data){	//替换null和undefined
	data = data.replace(/undefined/g, '-');
	data = data.replace(/null/g, '-');
	return data;
}

var yearformatDate= function(date){
	var d = new Date(date);
	var d2 = d.getFullYear() + "年"+ (d.getMonth() + 1) +"月";
	return d2;
	
}
var formatDate= function(dateA,dateB){	//格式化日期——年季报
	if(dateB == undefined){
		var d = new Date(dateA);
		var d2 = d.getFullYear() + "年"+ (d.getMonth() + 1) +"月"+ d.getDate() +"日";
		return d2.substring(d2.length - 4, d2.length);
	}else{
		var d = new Date(dateA),dd= new Date(dateB);		
		var d2 = d.getFullYear() + "年"+ (d.getMonth() + 1) +"月"+ d.getDate() +"日";
		var dd2 = dd.getFullYear() + "年"+ (dd.getMonth() + 1) +"月"+ dd.getDate() +"日";
		return d2 + '-' + (dd.getMonth() + 1)+"月"+ dd.getDate() +"日" ;		
	}
}

var shapeDate= function(date, type){	//格式化日期——周月报
	if(type == 1){	//周
		date = date.split('-');
		var source= '周'+ date[0] + '('+ date[1] +')';
		if(date[0]=='7'){
			source= '周日('+ date[1] +')';
		}
		return  source;		
	} else{	//月		
		
		var dd = date.replace('.', '/');
		dd = '2000/' + dd;	//火狐兼容
		dd = new Date(dd);
		return (dd.getMonth() + 1)+"月"+ dd.getDate() +"日" ;			
	}
}

var search_getCityName = function (it){		//三字码转城市名
	if(parent.airportMap[it]){
		var ct =parent.airportMap[it].ctyChaNam;		
		return ct;
	}
	else{
		
		return it;
	}
}

var formatFlyNum= function (num){	//缩写航班对
	if(num.split('/').length>1){
		return (num.split('/')[0] + '/' +  num.split('/')[1].substring(num.split('/')[1].length-2, num.split('/')[1].length));
	}else{
		return num;
	}
}




var idTmr;
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
        //获取workbook对象 6y77+
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
        tableToExcel(tableid);
    }
}
function Cleanup() {
    window.clearInterval(idTmr);
    CollectGarbage();
}
var tableToExcel = (function() {
    var uri = 'data:application/vnd.ms-excel;base64,',
        template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
        base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },
        format = function(s, c) {
            return s.replace(/{(\w+)}/g,
                function(m, p) { return c[p]; }) }
    return function(table, name) {
        if (!table.nodeType) table = document.getElementById(table);
        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML};
        window.location.href = uri + base64(format(template, ctx));
    }
})()


function countBc(arrA,arrB){	//对比日期后得出班次  
	var arrC = arrA.length > arrB.length ? arrA : arrB;
	var arrD = arrA.length > arrB.length ? arrB : arrA;
	var mark=0;
	arrD.forEach(function(val){
		if($.inArray(val, arrC) > -1) mark++ ;		
	})
	mark = mark == 0 ? (arrA.length + arrB.length) : mark;
	return mark;
}



//--------------------异常数据、航班提示
var create_abnormal = function(flag){
	if(flag){
		if(status == 1) return false;//没有异常
		$(".abnormalData_prompt").show();
		if(flag == '2') $(".abnormalData_prompt").html("<span>&#xe64a;</span> 当前结果包含异常数据");//只包含异常数据
		if(flag == '3') $(".abnormalData_prompt").html("<span>&#xe64a;</span> 当前结果包含异常航班");//只包含异常航班
		if(flag == '4') $(".abnormalData_prompt").html("<span>&#xe64a;</span> 当前结果包含异常数据和异常航班");//both
		setTimeout(function(){
			$(".abnormalData_prompt").empty();
			$(".abnormalData_prompt").hide();
		},3000)		
	}else{
		return console.log('error');
	}
}


//---------------------加载动画

showLoading = function(flag){
	var odom = $('#TM-sloading');
	if(flag){	//无背景色
		odom.css('background-color','rgba(29,44,72,0)');
	}
	odom.show();
}
closeLoading = function(){
	var odom = $('#TM-sloading');
	odom.fadeOut('normal');
	odom.css('background-color','rgba(29,44,72,.6)');
}