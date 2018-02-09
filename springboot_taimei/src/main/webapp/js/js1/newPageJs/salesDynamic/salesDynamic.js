//组装查询参数
 function getSearchJosn(){
	  var searchJson = new Object();
		var object = parent.supData;
		var flight = object.flight;
		var lane = object.lane;
		var dpt = "";
		if(typeof(flight)!="undefined"){
			dpt = flight.split("/"); 
		}
		if($("#inputNum").val()!=""){
			searchJson.flt_nbr_Count = $("#inputNum").val();
		}else{
			searchJson.flt_nbr_Count = dpt[0];
		}
		if($("._set-list-title").html()!=""){
			searchJson.airLine = $("._set-list-title").html();
		}else{
			searchJson.airLine = lane;
		}
		var aa = $("#startEndDate").val().split(" - ");
		var nowdate=new Date();
		searchJson.startTime = aa[0];
		searchJson.endTime = aa[1];
		return searchJson;
 }
 //两个日期相减
 function DateDiff(d1,d2){
 	var day = 24 * 60 * 60 *1000;
 	try{  
 		  var dateArr = d1.split("-");
 		  var checkDate = new Date();
 		  checkDate.setFullYear(dateArr[0], dateArr[1]-1, dateArr[2]);
 		  var checkTime = checkDate.getTime();
 		  var dateArr2 = d2.split("-");
 		  var checkDate2 = new Date();
 		  checkDate2.setFullYear(dateArr2[0], dateArr2[1]-1, dateArr2[2]);
 		  var checkTime2 = checkDate2.getTime();
 		  var cha = parseInt((checkTime - checkTime2)/day); 
 		  return cha;
 	  }catch(e){
 	 	  return false;
 	  }
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
 var airports = parent.airportMap;
$(function(){
	var flag = 1;
	//设置航线，还是航班标志
	parent.supData.linFlag = "0"; 
//	changeDate('startEndDate');
	setTimeout(function(){
		$('#startEndDate').daterangepicker(null, function(start, end, label) {});
	},1500);
	//为查询按钮绑定事件
	$(".sr-box-head-btn").click(function(){
		send();
	}) ;
	$("#inputNum").on("input",function(){
		flag ++;
		getAirLine();
	}) ;
	$("body").on("click","button",function(e){
		if($(this).hasClass("btn-success")){
			flag ++;
			getAirLine();
		}
	}) ;
	box();
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
    	$(".lin-historical-body").css({"height":infer(".lin-historical")[1]-infer(".sr-box-head")[1]+"px"});
        $(".lin-historical-body-box").css({"width":(infer(".lin-historical-body")[0]-infer(".sr-box-body-date")[0]-infer(".lin-historical-body-navigation")[0]+"px")});
        $(".body-navigation-element").css({"height":function(){
            return 100/ $(".body-navigation-element").length+"%";
        }});
    }
    $(".lin-historical-body-navigation").on("click","div",function(){
        if($(this).hasClass("body-navigation-element")){
            $(this).addClass("set-liset").siblings().removeClass("set-liset");
            change(ultimately,$(this).attr("abbr"),$(this).attr("city"))
            //改变右边的航班号
            var a = hangxian[$(this).attr("abbr")];
            $(".time-line-fli").html(a);
            $("#inputNum").val(a);	
            getAirLine();
        }
    });
    $(".lin-historical-body-box").on("mouseover","div",function(){
        if($(this).hasClass("selected-date")){
            var tem=$(this).attr("days");
            if($(this).find("section")){
                $(this).append("<div class='remove'>距离起飞"+ tem+"天"+$(this).attr("abbr").split("-")[0]+"%/"+$(this).attr("abbr").split("-")[1]+"人</div>")
            }else{
            	 if($(this).children().length==1){
                     $(this).append("<div class='remove'>距离起飞"+ tem+"天"+$(this).attr("abbr").split("-")[0]+"%/"+$(this).attr("abbr").split("-")[1]+"人</div>")
                 }
            }
        }
    });
    $(".lin-historical-body-box").on("mouseout","div",function(){
        if($(this).hasClass("selected-date")){
            if($(this).children().length>1){
                $(".remove").remove();
            }
        }
    });
    $(".lin-historical-body-box").on("mouseover","section",function(e){
    	e.stopPropagation();
        if($(this).hasClass("more")){
        	var days=$(this).attr("moreday").split(",");
        	var datas=$(this).attr("moredata").split(",");
        	var peos=$(this).attr("morepeo").split(",");
        	var ter="";
        	for(var i=0;i<days.length;i++){
        		ter+=days[i]+"天"+datas[i]+"%/"+peos[i]+"人";
        	}
        	if($(".removeR").length==0){
        		$(this).append("<div class='remove removeR'>距离起飞"+ter+"</div>")
        	}
        }
    });
   $(".lin-historical-body-box").on("mouseout","section",function(){
        if($(this).hasClass("more")){
        	$(".remove").remove();
        }
    });
    /*请求数据*/
    var ultimately=new Object();
    var fisrt = "";
    var hangxian = {};
    //判断是否从菜单进入功能页面--start
    if(parent.supData.flag==1){
    	parent.supData.flag = 0;
    	return;
    }  
    //判断是否从菜单进入功能页面--end
    function getAirLine(){
    	$.ajax(
    	    	{
    	            type:'get',
    	            url:'/restful/getAirLine',
    	            data:getSearchJosn(),
    	            dataType : 'jsonp',
    	            success : function(data) {
                        if(data.success.list.length == 0){
                            $(".lin-historical-body").addClass("muhu");
                            $(".err").css("display","block");
                            $("#TM-sloading").hide();
                            return false;
                        }
    	            	var object = parent.supData;
    	        		var lane = object.lane;
    	        		var dats = data.success.list;
    	        		var sts = lane.split("=");
	                      var temp11 ;
	                      var temp22 ;
	                      var temp1 ;
	                      var temp2 ;
	                      if(sts.length>2){
	                          temp11 = sts[0]+"="+sts[1]+"="+sts[2];
	                          temp22 = sts[2]+"="+sts[1]+"="+sts[0];
	                          for ( var int = 0; int < dats.length; int++) {
	                        	  if(dats[int] == temp11){
		                              lane = temp11;
		                          }
							  }
	                          for ( var int = 0; int < dats.length; int++) {
	                        	  if(dats[int] == temp22){
			                          lane = temp22;
	                        	  }
	                          }
	                      }else{
	                          temp1 = sts[0]+"="+sts[1];
	                          temp2 = sts[1]+"="+sts[0];
	                          for ( var int = 0; int < dats.length; int++) {
	                        	  if(dats[int] == temp1){
		                              lane = temp1;
		                          }
							  }
	                          for ( var int = 0; int < dats.length; int++) {
	                        	  if(dats[int] == temp2){
			                          lane = temp2;
	                        	  }
	                          }
	                      }
    	            	var list={
    	            	        data:dats, //st节点集合
    	            	        summary:"false", //是否包含往返 true包含false不包含
    	            	        name:"._set-list-set",  //添加list节点
    	            	        cleName:".sr-box",   //取消绑定事件函数节点
    	            	        flyNbr :lane,
    	            	        fun:function(){
    	            	        }
    	            	    };
    	            	    setChoose(list);
    	            	    if(flag==1&&object.flag!=1){
    	            	    	var dpt = "";
    	            			if(typeof(flight)!="undefined"){
    	            				dpt = flight.split("/"); 
    	            			}
    	            			$("#inputNum").val(dpt[0]);
    	            	    	send();
    	            	    }
    	            	    flag ++;
    	            	    var lls =lane.split("=");
    	            	    //改变右边航线
        	        		$(".change-line-f1").find("p").html(airports[lls[0]].iata);
        	            	$(".change-line-f1").find("div").html(airports[lls[0]].aptChaNam);
        	            	
        	            	$(".change-line-o1").find("p").html(airports[lls[1]].iata);
        	            	$(".change-line-o1").find("div").html(airports[lls[1]].aptChaNam);
        	            	
        	            	$(".change-line-l1").find("p").html(airports[lls[2]].iata);
        	            	$(".change-line-l1").find("div").html(airports[lls[2]].aptChaNam);
    	            },
    	            error : function() {}
    	        }
    	    );
    }
    function send(){
    	$('#TM-sloading').show();
    	ultimately = null;
	   	ultimately = new Object();
        fisrt = "";
        if($("#inputNum").val()==""){
			alert("请输入航班号");
			$(".lin-historical-body").addClass("muhu");
    		$(".err").css("display","block");
    		$("#TM-sloading").hide();
			return;
        }
        //查询条件联动
    	var object = parent.supData;
		object.lane =$("._set-list-title").html() ;
		var flt_nbr_Counttemp = $('#inputNum').val();
		if(flt_nbr_Counttemp == ""){
			flt_nbr_Counttemp = object.flight.split("/")[0];
		}
		var lastNum = parseInt(flt_nbr_Counttemp.substring(flt_nbr_Counttemp.length-4,flt_nbr_Counttemp.length));
		var temp = 0;
		if(lastNum%2==0){
			temp = flt_nbr_Counttemp.substring(0,flt_nbr_Counttemp.length-4)+(lastNum-1);
		}else{
			temp = flt_nbr_Counttemp.substring(0,flt_nbr_Counttemp.length-4)+(lastNum+1);
		}
		var tempFly = flt_nbr_Counttemp + "/"+temp.substring(flt_nbr_Counttemp.length-2,flt_nbr_Counttemp.length);
		object.flight = tempFly;
        if($("._set-list-title").html()==""){
            alert("没有相关航线");
            $(".lin-historical-body").addClass("muhu");
            $(".err").css("display","block");
            $("#TM-sloading").hide();
            return;
        }
    	var date = $('#startEndDate').val();
    	var startTime = '';
    	var endTime = '';
		if((typeof(date)=='undefined'||date==null||date=='')){
	    	alert('请选择起止日期');
	    	$(".lin-historical-body").addClass("muhu");
    		$(".err").css("display","block");
    		$("#TM-sloading").hide();
	    	return;
        }
        var reqOpt = getSearchJosn();
		if(daysBetween(reqOpt.startTime,reqOpt.endTime)>366){
	    	$(".lin-historical-body").addClass("muhu");
    		$(".err").css("display","block");
    		$("#TM-sloading").hide();
	    	return alert('请将范围控制在 366天 以内！');
        }
	    $.ajax(
	    	{
	            type:'post',
	            url:'/restful/getBuyTicketReportLineDataNew',
	            data: reqOpt,
	            dataType : 'jsonp',
	            success : function(data) {
	            	$('#TM-sloading').hide('normal');
	            	var bz = false;
	           		var hangData = data.success.dataMap;
	           		if(hangData!=null&&hangData!=""){
	           			for(var a in hangData){
	           				var aa = a.split("-");
	           				var aptChaNams = "";
	           				var iatas = new Array();
	           				if(aa.length>2){
	           					b = airports[aa[0]].aptChaNam+"-"+airports[aa[1]].aptChaNam+"-"+airports[aa[2]].aptChaNam;
	           					iatas = [aa[0],aa[1],aa[2]];
	           				}else{
	           					b = airports[aa[0]].aptChaNam+"-"+airports[aa[1]].aptChaNam;
	           					iatas = [aa[0],aa[1]];
	           				}
	           				var data1 = hangData[a].list;
	           				if(data1.length>0){
	           					bz = true;
	           				}
	               			hangxian[a]= hangData[a].flyNum;
	               			var temp1 = new Object();
	               			temp1["code"] = iatas;
	               			var everydatas = new Object();
	               			for(var c in data1){
	               				var tempobj = new Array();
	               				var tempdata = new Object();
	               				var moreThan = new Object();
		               			var moreThan_day = new Array();
		               			var moreThan_jl = new Array();
		               			var moreThan_rs = new Array();
	               				var datea = data1[c].fltNumDate;
	               				var str = datea.split("-");
	               				if("undefined"!=typeof(data1[c].progressf1)){
	               					var tempobjchild = new Object(); 
	               					var numf1 = data1[c].progressf1.split(",");
	               					tempobjchild.jl = parseInt(numf1[0].replace("%",""));
	               					tempobjchild.rs = parseInt(numf1[1]);
	               					tempobjchild.indexx = -1;
	               					tempobj[-1] = tempobjchild;
	               					if(parseInt(numf1[0].replace("%",""))>100){
	               						moreThan_day.push(-1);
	               						moreThan_jl.push(parseInt(numf1[0].replace("%","")));
	               						moreThan_rs.push(tempobjchild.rs);
	               					}
	               				}
	               				if("undefined"!=typeof(data1[c].progress0)){
	               					var tempobjchild = new Object(); 
	               					var numf1 = data1[c].progress0.split(",");
	               					tempobjchild.jl = parseInt(numf1[0].replace("%",""));
	               					tempobjchild.rs = parseInt(numf1[1]);
	               					tempobjchild.indexx = 0;
	               					tempobj[0] = tempobjchild;
	               					if(parseInt(numf1[0].replace("%",""))>100){
	               						moreThan_day.push(0);
	               						moreThan_jl.push(parseInt(numf1[0].replace("%","")));
	               						moreThan_rs.push(tempobjchild.rs);
	               					}
	               				}
	               				if("undefined"!=typeof(data1[c].progress1)){
	               					var tempobjchild = new Object(); 
	               					var numf1 = data1[c].progress1.split(",");
	               					tempobjchild.jl = parseInt(numf1[0].replace("%",""));
	               					tempobjchild.rs = parseInt(numf1[1]);
	               					tempobjchild.indexx = 1;
	               					tempobj[1] = tempobjchild;
	               					if(parseInt(numf1[0].replace("%",""))>100){
	               						moreThan_day.push(1);
	               						moreThan_jl.push(parseInt(numf1[0].replace("%","")));
	               						moreThan_rs.push(tempobjchild.rs);
	               					}
	               				}
	               				if("undefined"!=typeof(data1[c].progress2)){
	               					var tempobjchild = new Object(); 
	               					var numf1 = data1[c].progress2.split(",");
	               					tempobjchild.jl = parseInt(numf1[0].replace("%",""));
	               					tempobjchild.rs = parseInt(numf1[1]);
	               					tempobjchild.indexx = 2;
	               					tempobj[2] = tempobjchild;
	               					if(parseInt(numf1[0].replace("%",""))>100){
	               						moreThan_day.push(2);
	               						moreThan_jl.push(parseInt(numf1[0].replace("%","")));
	               						moreThan_rs.push(tempobjchild.rs);
	               					}
	               				}
	               				if("undefined"!=typeof(data1[c].progress3)){
	               					var tempobjchild = new Object(); 
	               					var numf1 = data1[c].progress3.split(",");
	               					tempobjchild.jl = parseInt(numf1[0].replace("%",""));
	               					tempobjchild.rs = parseInt(numf1[1]);
	               					tempobjchild.indexx = 3;
	               					tempobj[3] = tempobjchild;
	               					if(parseInt(numf1[0].replace("%",""))>100){
	               						moreThan_day.push(3);
	               						moreThan_jl.push(parseInt(numf1[0].replace("%","")));
	               						moreThan_rs.push(tempobjchild.rs);
	               					}
	               				}
	               				if("undefined"!=typeof(data1[c].progress7)){
	               					var tempobjchild = new Object(); 
	               					var numf1 = data1[c].progress7.split(",");
	               					tempobjchild.jl = parseInt(numf1[0].replace("%",""));
	               					tempobjchild.rs = parseInt(numf1[1]);
	               					tempobjchild.indexx = 7;
	               					tempobj[7] = tempobjchild;
	               					if(parseInt(numf1[0].replace("%",""))>100){
	               						moreThan_day.push(7);
	               						moreThan_jl.push(parseInt(numf1[0].replace("%","")));
	               						moreThan_rs.push(tempobjchild.rs);
	               					}
	               				}
	               				if("undefined"!=typeof(data1[c].progress15)){
	               					var tempobjchild = new Object(); 
	               					var numf1 = data1[c].progress15.split(",");
	               					tempobjchild.jl = parseInt(numf1[0].replace("%",""));
	               					tempobjchild.rs = parseInt(numf1[1]);
	               					tempobjchild.indexx = 15;
	               					tempobj[15] = tempobjchild;
	               					if(parseInt(numf1[0].replace("%",""))>100){
	               						moreThan_day.push(15);
	               						moreThan_jl.push(parseInt(numf1[0].replace("%","")));
	               						moreThan_rs.push(tempobjchild.rs);
	               					}
	               				}
	               				if("undefined"!=typeof(data1[c].progress30)){
	               					var tempobjchild = new Object(); 
	               					var numf1 = data1[c].progress30.split(",");
	               					tempobjchild.jl = parseInt(numf1[0].replace("%",""));
	               					tempobjchild.rs = parseInt(numf1[1]);
	               					tempobjchild.indexx = 30;
	               					tempobj[30] = tempobjchild;
	               					if(parseInt(numf1[0].replace("%",""))>100){
	               						moreThan_day.push(30);
	               						moreThan_jl.push(parseInt(numf1[0].replace("%","")));
	               						moreThan_rs.push(tempobjchild.rs);
	               					}
	               				}
	               				//循环tempobj 抖出想要的数据
	               				var times = new Array();
	               				var datases = new Array();
	               				var pros = new Array();
	               				var prods = new Array();
	               				var maxTs = new Array();
	               				var nowTs = new Array();
	               				//当前天
	               				if("undefined"==typeof(data1[c].todaydate)){
	               					if("undefined"!=typeof(data1[c].progressf1)){
	               						nowTs.push(-1);
			               				var numf1 = data1[c].progressf1.split(",");
			               				nowTs.push(numf1[0].replace("%",""));
			               				nowTs.push(parseInt(numf1[1]));
	               					}
	               				}else{
	               					var todaydatee = data1[c].todaydate.split(",")[0];
		               				var todaychazhi = DateDiff(datea,todaydatee);
		               				var todayflag = true;
		               				for(var a in tempobj){
		               					if(a==todaychazhi){
		               						todayflag = false;
			               				}
		               				}
		               				if(todayflag&&todaychazhi>=-1){
		               					var tempobjchild = new Object(); 
		               					tempobjchild.jl = parseInt(data1[c].todaydate.split(",")[1]);
		               					tempobjchild.rs = parseInt(data1[c].todaydate.split(",")[2]);
//		               					if(tempobjchild.jl>100){
//		               						tempobjchild.jl = 100;
//		               					}
		               					tempobjchild.indexx = todaychazhi;
		               					tempobj[todaychazhi] = tempobjchild;
		               				}
		               				//放入今天的
		               				nowTs.push(todaychazhi);
		               				nowTs.push(data1[c].todaydate.split(",")[1]);
		               				nowTs.push(data1[c].todaydate.split(",")[2]);
	               				}
	               				if(nowTs[1]>100){
               						if(moreThan_day.indexOf(nowTs[0])<0){
	               						moreThan_day.push(nowTs[0]);
	               						moreThan_jl.push(nowTs[1]);
	               						moreThan_rs.push(nowTs[2]);
	               					}
               						nowTs[1] = 100;
               					}
	               				//最大天 datea
	               				var maxday = data1[c].maxDayandDate.split(",")[0];
	               				var chazhi = DateDiff(datea,maxday);
	               				var flag = true;
	               				for(var a in tempobj){
	               					if(a==chazhi){
	               						flag = false;
		               				}
	               				}
	               				if(flag&&chazhi>=-1){
	               					var tempobjchild = new Object(); 
	               					tempobjchild.jl = parseInt(data1[c].maxDayandDate.split(",")[1]);
	               					tempobjchild.rs = parseInt(data1[c].maxDayandDate.split(",")[2]);
	               					tempobjchild.indexx = chazhi;
//	               					if(tempobjchild.jl>100){
//	               						tempobjchild.jl = 100;
//	               					}
	               					tempobj[chazhi] = tempobjchild;
	               				}
	               				//放入最大的
	               				maxTs.push(chazhi);
	               				maxTs.push(parseInt(data1[c].maxDayandDate.split(",")[1]));
	               				maxTs.push(parseInt(data1[c].maxDayandDate.split(",")[2]));
	               				if(maxTs[1]>100){
	               					if(moreThan_day.indexOf(maxTs[0])<0){
	               						moreThan_day.push(maxTs[0]);
	               						moreThan_jl.push(maxTs[1]);
	               						moreThan_rs.push(maxTs[2]);
	               					}
	               					maxTs[1] = 100;
               					}
	               				for(var a in tempobj){
	               					times.push(a);
	               					var tema = new Array();
	               					tema.push(tempobj[a].jl);
	               					tema.push(tempobj[a].rs);
	               					datases.push(tema);
	               					pros.push(tempobj[a].jl);
	               					prods.push(tempobj[a].rs);
	               				}
	               				tempdata["time"] = times;
	               				tempdata["data"] = datases;
	               				tempdata["pro"] = pros;
	               				tempdata["proD"] = prods;
	               				tempdata["maxT"] = maxTs;
	               				tempdata["nowT"] = nowTs;
	               				if(moreThan_jl.length>0){
	               					moreThan["day"] = moreThan_day;
	               					moreThan["data"] = moreThan_jl;
	               					moreThan["peo"] = moreThan_rs;
	               				}
	               				if(typeof(moreThan["day"])!="undefined"){
	               					tempdata["moreThan"] = moreThan;
	               				}
	               				everydatas[str[1]+"."+str[2]] = tempdata;
//	               				data[city].daTime[x].moreThan={day:[20,30],data:[108,120],peo:[500,600]};
	               			}
	               			temp1["daTime"] = everydatas;
	               			ultimately[b] = temp1;
	           			};
	           			creatNav(ultimately);
		                box();
		                $(".body-navigation-element").eq(0).click();
		                initrightData(data);
		                if(!bz){
		                	$(".lin-historical-body").addClass("muhu");
		            		$(".err").css("display","block");
		                }else{
		                	$(".lin-historical-body").removeClass("muhu");
		            		$(".err").css("display","none");
		                }
	           		}else{
	           			$(".lin-historical-body").addClass("muhu");
	            		$(".err").css("display","block");
	           		}
	            },
	            error : function() {}
	        }
	    );
    }
    var object = parent.supData;
	var flight = object.flight;
	var lane = object.lane;
	var dpt_AirPt_Cd = "";
	var Arrv_Airpt_Cd = "";
	var pas_stp = "";
	var flt_nbr_Count2 = "";
    if("undefined"!=typeof(flight)){
    	if("undefined"!=typeof(lane)&&lane!=""){
    		lane = lane.split("=");
    		if(lane.length>2){
    			dpt_AirPt_Cd = lane[0];
    			pas_stp = airports[lane[1]].iata;
    			Arrv_Airpt_Cd = lane[2];
    		}else{
    			dpt_AirPt_Cd = lane[0];
    			Arrv_Airpt_Cd = lane[1];
    		}
    	}
    	var flighttemp = flight.split("/");
    	flt_nbr_Count2 = flighttemp[0]+"/"+flighttemp[0].substring(0,4)+flighttemp[1];
    	$.ajax({
    	    type:'get',
    	    url : '/restful/getBuyTicketReportLineDataNewDate',
    	    data:{
    	    	dpt_AirPt_Cd :airports[dpt_AirPt_Cd].iata,
            	Arrv_Airpt_Cd: airports[Arrv_Airpt_Cd].iata,
            	pst_cd:pas_stp,
            	flt_nbr_Count:flt_nbr_Count2,
    	    },
    	    dataType : 'jsonp',
    	    success : function(data) {
    	    	var date = data.success.newDate;
    	    	changeDate('startEndDate',date);
    	    	getAirLine();
    	    }
    	});
    }else{
    	getAirLine();
    }
    function initrightData(data){
		$(".time-line").css("display","block");
		if(airports[data.success.pstName].iata!==undefined&&airports[data.success.pstName].iata!==""){
			$(".change-line-o1").removeClass("change-line-o2");
			$(".change-line").removeClass("change-line2");
		}else{
			$(".change-line-o1").addClass("change-line-o2");
			$(".change-line").addClass("change-line2");
		}
    	$("#inputNum").val(data.success.fltNumq);
    	$(".change-line-f1").find("p").html(airports[data.success.dptName].iata);
    	$(".change-line-f1").find("div").html(airports[data.success.dptName].aptChaNam);
    	
    	$(".change-line-o1").find("p").html(airports[data.success.pstName].iata);
    	$(".change-line-o1").find("div").html(airports[data.success.pstName].aptChaNam);
    	
    	$(".change-line-l1").find("p").html(airports[data.success.arrName].iata);
    	$(".change-line-l1").find("div").html(airports[data.success.arrName].aptChaNam);
    }
    function creatNav(data){
//    	alert(data);
        /*创建左侧导航栏节点*/
        var ele="";
        for(var key in data){
            var nums=data[key].code;
            if(nums.length==3){
                ele+="<div class='body-navigation-element' city='"+key+"' abbr='"+nums[0]+"-"+nums[1]+"-"+nums[2]+"'><div class='tag-box'><div>"+nums[0]+"</div><div class='element-tag'>&#xe648;</div><div>"+nums[1]+"</div><div class='element-tag'>&#xe648;</div><div>"+nums[2]+"</div></div></div>"
            }else {
                ele+="<div class='body-navigation-element' city='"+key+"' abbr='"+nums[0]+"-"+nums[1]+"'><div class='tag-box'><div>"+nums[0]+"</div><div class='element-tag'>&#xe648;</div><div>"+nums[1]+"</div></div></div>";
            }
        }
        $(".lin-historical-body-navigation").html(ele);
    }
    function change(data,path,city){
        var eleBox="";
        var len=path.split("-");
        var citys=city.split("-");
        if(len.length==3){
            eleBox+="<div class='lin-historical-body-box-titles'><div>起飞日期</div> <div class='flight-name max-flight'><ul class='flight-name-box'><li class='flight-name-place'><h3>"+len[0]+"</h3><div>"+citys[0]+"</div></li><li class='flight-name-tag'><img src='/images/platform/dynamic/air-go.png'></li> <li class='flight-name-place'> <h3>"+len[1]+"</h3> <div>"+citys[1]+"</div> </li> <li class='flight-name-tag'> <img src='/images/platform/dynamic/air-go.png'> </li> <li class='flight-name-place'> <h3>"+len[2]+"</h3><div>"+citys[2]+"</div></li></ul></div><div>客座率/客量</div></div><div class='element-lines-box'>";
        }else if(len.length==2){
            eleBox+="<div class='lin-historical-body-box-titles'><div>起飞日期</div> <div class='flight-name min-flight'><ul class='flight-name-box'><li class='flight-name-place'><h3>"+len[0]+"</h3><div>"+citys[0]+"</div></li><li class='flight-name-tag'><img src='/images/platform/dynamic/air-go.png'></li> <li class='flight-name-place'> <h3>"+len[1]+"</h3> <div>"+citys[1]+"</div> </li></div><div>客座率/客量</div></div><div class='element-lines-box'>";
        }
        /*创建内容节点*/
        for(var x in data[city].daTime){
            eleBox+="<div class='element-lines'><p class='lines-title'>"+x+"</p><div class='lines-box'><div class='calibration'>";
            for(var t=0;t<=100;t++){
            	if(t!=100){
        		if(data[city].daTime[x].pro.indexOf(t)!="-1"){
        			var many=data[city].daTime[x].pro.indexOf(t);
        			var ms="";
        			for(var i=0;i<data[city].daTime[x].pro.length;i++){
        				if(t==data[city].daTime[x].pro[i]){
        					many=i;
        					ms+=data[city].daTime[x].time[i]+",";
        				}
        			}
        			ms = ms.substring(0,ms.length-1);
        			var sp = ms.split(",");
        			var mina = 200;
        			for(var k =0;k<sp.length;k++){
        				if(mina>sp[k]){
        					mina = sp[k];
        				}
        			}
        			eleBox+="<div class='selected-date' dddd='ddd' days='"+ms+"' abbr='"+data[city].daTime[x].data[many][0]+"-"+data[city].daTime[x].data[many][1]+"' tag='"+x+"' line='"+city+"'><p>"+mina+"</p></div>"
        		} else {
        			eleBox+="<div></div>"
        		}
        	}else{
        		if(data[city].daTime[x].moreThan){
        			if(data[city].daTime[x].pro.indexOf(t)!="-1"){
            			var many=data[city].daTime[x].pro.indexOf(t);
            			var ms="";
            			for(var i=0;i<data[city].daTime[x].pro.length;i++){
            				if(t==data[city].daTime[x].pro[i]){
            					many=i;
            					ms+=data[city].daTime[x].time[i]+",";
            				}
            			}
            			eleBox+="<div class='selected-date'  dddd='ddd' days='"+ms+"' abbr='"+data[city].daTime[x].data[many][0]+"-"+data[city].daTime[x].data[many][1]+"' tag='"+x+"' line='"+city+"'><p>"+data[city].daTime[x].time[many]+"</p><section class='more' moreDay='"+data[city].daTime[x].moreThan.day+"' moreData='"+data[city].daTime[x].moreThan.data+"' morePeo='"+data[city].daTime[x].moreThan.peo+"'>&#xe67d;</section></div>"
            		} else {
            			eleBox+="<div><section class='more' morepeo='"+data[city].daTime[x].moreThan.peo+"' moreDay='"+data[city].daTime[x].moreThan.day+"' moreData='"+data[city].daTime[x].moreThan.data+"'>&#xe67d;</section></div>"
            		}
        		}else{
        			if(data[city].daTime[x].pro.indexOf(t)!="-1"){
            			var many=data[city].daTime[x].pro.indexOf(t);
            			var ms="";
            			for(var i=0;i<data[city].daTime[x].pro.length;i++){
            				if(t==data[city].daTime[x].pro[i]){
            					many=i;
            					ms+=data[city].daTime[x].time[i]+",";
            				}
            			}
            			eleBox+="<div class='selected-date' dddd='ddd' days='"+ms+"' abbr='"+data[city].daTime[x].data[many][0]+"-"+data[city].daTime[x].data[many][1]+"' tag='"+x+"' line='"+city+"'><p>"+data[city].daTime[x].time[many]+"</p></div>"
            		} else {
            			eleBox+="<div></div>"
            		}
        		}
        	}}
            eleBox+="</div><div class='gradient'><div></div></div><div class='air-taimei' abbr='"+x+"' le='"+data[city].daTime[x].nowT+"'>&#xe637;</div><div class='difference'></div></div><div class='describe'><p>100%</p><div>距起飞天数</div></div></div>";
        }
        $(".lin-historical-body-bar").html(eleBox);
        /*上刻度线*/
        for(var i=0;i< $(".calibration").length;i++){
            for(var j=0;j<=100;j++){
                $(".calibration").eq(i).children().eq(j).css({"left":j+"%"});
            }
        }
        /*修改渐变长度和飞机位置*/
        for(var i=0;i< $(".air-taimei").length;i++){
            var nu= $(".air-taimei").eq(i).attr("le").split(",")[1];
            $(".air-taimei").eq(i).css({"left":nu+"%"});
            $(".gradient").eq(i).css({"width":nu+"%"});
        }
        /*已经起飞-1*/
        for(var i=0;i<$(".lines-title").length;i++){
            var nus=$(".lines-title").eq(i).text();
            if(data[city].daTime[nus].maxT[1]>data[city].daTime[nus].nowT[1]){
                var nol=parseInt(data[city].daTime[nus].nowT[1]);
                var now=parseInt(data[city].daTime[nus].maxT[1]-data[city].daTime[nus].nowT[1]);
                if((nol+now)>100){
                	$(".air-taimei").eq(i).next().css({"left":nol+"%","width":(100-nol)+"%"});
                }else{
                	$(".air-taimei").eq(i).next().css({"left":nol+"%","width":now+"%"});
                }
            }
        };
        box();
        $("._bar-box").remove();
        $(".lin-historical").unbind("mousewheel");
        $(".lin-historical-body-bar").css("top","0px");
        addBar(".lin-historical",".lin-historical-body-box",".lin-historical-body-bar");
    }
   
    window.onresize=function(){
        box();
    }
    function changeDate(id,date){
    	// 参数表示在当前日期下要增加的天数  
    	var d = new Date(date); 
     	d.setDate(d.getDate()-35); 
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
});

var daysBetween = function(sDate1,sDate2){
    //Date.parse() 解析一个日期时间字符串，并返回1970/1/1 午夜距离该日期时间的毫秒数
    var time1 = Date.parse(new Date(sDate1));
    var time2 = Date.parse(new Date(sDate2));
    var nDays = Math.abs(parseInt((time2 - time1)/1000/3600/24));
    return nDays;
};







