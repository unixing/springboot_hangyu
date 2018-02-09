if(parent.supData.linFlag != "0"){
    delete parent.supData.flight;
}
var airLine = parent.supData.lane;
var airports = parent.airportMap;
var airFltNbr = parent.supData.flight;
var oldTime = "";
var information={};
var searchJson = {};
var flts = '';
var cus_namemap="";
var isnonFlag = $('#isnon-stop').is(':checked');
var ishasFlag = $('#ishas-stop').is(':checked');
if(typeof(airFltNbr)!='undefined'){
    flts = airFltNbr.split('/');
}
var flight = '';
if(flts.length>=2){
    flight = flts[0]+"/"+flts[0].substring(0,4)+flts[1];
}else{
    flight = "汇总";
}

$(function(){
    parent.supData.linFlag = "1"; 
    var objj = parent.supData;
    if(typeof(objj.isIncloudPasstp)!="undefined"){
        if(objj.isIncloudPasstp=="on"){
            $('#ishas-stop').attr('checked',true);
        }
    }
    $("._set-query").click(function(e){
        $("._customers-box").show();
        $("._customers").css({width:'55%'});
        e.stopPropagation(); //屏蔽事件冒泡
        send();
    }) ;
    $("#ishas-stop").change(function(e){
        getFlt_Nbr();
    }) ;
    oldTime = $('#startEndDate').val();
    $("body").on("click","button",function(e){
        if($(this).hasClass("btn-success")){
            getFlt_Nbr();
        }
    }) ;
    setTimeout(function(){
        $('#startEndDate').daterangepicker(null, function(start, end, label) {});
    },1500);
    if(airLine!=null&&airLine!=""&&typeof(airLine)!='undefined'){
        var flt = parent.supData.flt;
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
                $('#divpas').addClass("_checkOp");
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
    var objs={
            back:function(){
            if($('#cityChoice3').val()==""){
                $('#divpas').removeClass("_checkOp");
                $('#ishas-stop').removeAttr("disabled");
                ishasFlag = true;
            }else{
//                $('#divpas').addClass("_checkOp");
//                $('#ishas-stop').attr("disabled","d");
//                ishasFlag = false;
            	$('#divpas').addClass("_checkOp");
				$('#isnon-stop').attr("disabled",false);
        		$('#ishas-stop').attr("disabled","disabled");
        		$('#ishas-stop').attr("checked", true)
				ishasFlag = false;
            }
            getFlt_Nbr();
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
    var dpt_AirPt_Cd = typeof(airports[$('#cityChoice').val()])=='undefined'?'':airports[$('#cityChoice').val()].iata;
    var pas_stp = typeof(airports[$('#cityChoice3').val()])=='undefined'?'':airports[$('#cityChoice3').val()].iata;
    var arrv_Airpt_Cd = typeof(airports[$('#cityChoice2').val()])=='undefined'?'':airports[$('#cityChoice2').val()].iata;
    //判断从哪里来（功能间的切换）
	var startTime = parent.supData.startTime;
	var endTime = parent.supData.endTime;
	if(typeof(startTime)!="undefined"){
		 $('#startEndDate').val(startTime+' - '+ endTime);
		 getFlt_Nbr();
         send();
	}else{
	    setTimeout(function () {
	    	//****start, longhong 2017-8-8
	    	var check1 = $('#isnon-stop').is(':checked')? 'on':'no';
	    	var check2 = $('#ishas-stop').is(':checked')? 'on':'no';
	        $.ajax({
	            type:'get',
	            url : '/restful/getCustomerNewDate',
	            data:{
	                dpt_AirPt_Cd :dpt_AirPt_Cd,
	                Arrv_Airpt_Cd: arrv_Airpt_Cd,
	                pas_stp:pas_stp,
	                isIncludeRdf:check1,
	                isIncludePas:check2,
	            },
	            dataType : 'jsonp',
	            success : function(data) {
	                var date = data.success.newDate;
	                changeDate('startEndDate',date);
	                getFlt_Nbr();
	                send();
	            }
	        });
	        //****end, longhong 2017-8-8
	    }, 500);
	}
    addBar(".sr-box","._customers-box","._customers-box-c");
});
function getFlt_Nbr(){
    var dpt_AirPt_Cd = typeof(airports[$('#cityChoice').val()])=='undefined'?'':airports[$('#cityChoice').val()].iata;
    var pas_stp = typeof(airports[$('#cityChoice3').val()])=='undefined'?'':airports[$('#cityChoice3').val()].iata;
    var arrv_Airpt_Cd = typeof(airports[$('#cityChoice2').val()])=='undefined'?'':airports[$('#cityChoice2').val()].iata;
    var startEndDate = $('#startEndDate').val();
    if(searchJson.dpt_AirPt_Cd!=dpt_AirPt_Cd||pas_stp!=searchJson.pas_stp||arrv_Airpt_Cd!=searchJson.arrv_Airpt_Cd||oldTime!=startEndDate){
        searchJson.dpt_AirPt_Cd = dpt_AirPt_Cd;
        searchJson.pas_stp = pas_stp;
        searchJson.arrv_Airpt_Cd = arrv_Airpt_Cd;
    }else{
        return;
    }
    var time = $('#startEndDate').val().split(' - ');
    if(time==null||time.length==0){
        alert('请选择起止日期');
        return;
    }
    var lcl_Dpt_Tm = time[0];
    var lcl_Arrv_Tm = time[1];
    if(time.length!=2||
            checkDate(lcl_Dpt_Tm)==false||
            checkDate(lcl_Arrv_Tm)==false||
            (new Date(lcl_Dpt_Tm).getTime()-new Date(lcl_Arrv_Tm).getTime()>0)||
            (new Date(lcl_Arrv_Tm).getTime()-new Date(new Date().format('yyyy-MM-dd')).getTime()>0)){
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
        url:'/getFltNbrList', 
        type:'post',
        data:{
            dpt_AirPt_Cd:dpt_AirPt_Cd,
            arrv_Airpt_Cd:arrv_Airpt_Cd,
            pas_stp:pas_stp,
            lcl_Dpt_Tm:lcl_Dpt_Tm,
            lcl_Arrv_Tm:lcl_Arrv_Tm,
            isIncludePas: ispas,
            isIncludeRdf: isnon
        },
        async:false,
	})
	.done(function(data) {
        var dats = new Array();
        if(data!=null&&data.length>0){
            for(var index in data){
                dats.push(data[index]);
            }
        }
        var list={
            data:dats, //st节点集合
            summary:"true", //是否包含往返 true包含false不包含
            name:"._set-list-set",  //添加list节点
            cleName:".sr-box",   //取消绑定事件函数节点
            flyNbr :flight,
            fun:function(){
            }
        };
        setChoose(list);
	})
	.fail(function(err) {
//	    console.log("error");
	})
	.always(function() {
		// $('#TM-sloading').hide('normal');
	    //console.log("complete");
	});
	
}
function getairCode(searchJson){
    if(searchJson.dpt_AirPt_Cd!=""){
        searchJson.dpt_AirPt_Cd = airports[searchJson.dpt_AirPt_Cd].iata;
    }
    if(searchJson.pas_stp!=""){
        searchJson.pas_stp = airports[searchJson.pas_stp].iata;
    }
    if(searchJson.arrv_Airpt_Cd!=""){
        searchJson.arrv_Airpt_Cd = airports[searchJson.arrv_Airpt_Cd].iata;
    }
    return  searchJson;
}
/*测各种块大小*/
function infer(name){
    var infer=[];
    infer.push(parseFloat($(name).css("width").split("px")[0]));
    infer.push(parseFloat($(name).css("height").split("px")[0]));
    infer.push(parseFloat($(name).css("margin-top").split("px")[0]));
    infer.push(parseFloat($(name).css("left").split("px")[0]));
    infer.push(parseFloat($(name).css("top").split("px")[0]));
    return infer;
}
function reset(){
    $("._customers").css({"height":(infer(".sr-box")[1]-infer(".sr-box-head")[1]+"px")});
    $("._customers-box").css({"width":(infer("._customers")[0]-infer("._customers-nav")[0]-5)});
    
    //修改高度  2017-4-13 long
    let a =100 / $("._customers-nav-a").length;
    $("._customers-nav-a").css("height",a+"%");    
}


/*ech*/
function draw(name){
    var dom = document.getElementById("sample1");
    var myChart = echarts.init(dom);
    /*改为前10*/
    var option = {
            title: {
                text: "国内客源分布",
                left:'5%',
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
                show : true,
                borderColor:'#7ebde8',
                borderWidth:1
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '7%',
                containLabel: true
            },
            xAxis : [
                     {
                         type : 'category',
                         data : information[name].cityName,
                         axisTick: {
                             alignWithLabel: true
                         },
                         axisLabel:{
                             interval:0,
                             rotate:45,
                             margin:10,
                             textStyle:{
                                 color:"white"
                             }, 
                             formatter:function(params){
                                 return params.substr(0,4);
                             }
                         },
                         splitLine: {
                             show:true,
                             lineStyle:{
                                 color:"#1b2c4c"
                             }
                         }
                     }
                     ],
                     yAxis : [
                              {
                                  type : 'value',
                                  splitLine: {
                                      show:false,
                                      lineStyle:{
                                          color:"#1b2c4c"
                                      }
                                  },
                                  axisLabel:{
                                      textStyle:{
                                          color:"white"
                                      }
                                  }
                              }
                              ],
                              series : [
                                        {
                                            name:'人数',
                                            type:'bar',
                                            barWidth: '30%',
                                            itemStyle: {
                                                normal: {
                                                    color: new echarts.graphic.LinearGradient(
                                                            0, 0, 0, 1,
                                                            [
                                                             {offset: 0, color: '#7ebce9'},
                                                             {offset: 0.5, color: '#7fbce9'},
                                                             {offset: 1, color: '#7fbce9'}
                                                             ]
                                                    )
                                                },
                                            /*  emphasis: {
                                                    color: new echarts.graphic.LinearGradient(
                                                            0, 0, 0, 1,
                                                            [
                                                             {offset: 0, color: '#4b5cb6'},
                                                             {offset: 0.5, color: '#5076bf'},
                                                             {offset: 1, color: '#5896c7'}
                                                             ]
                                                    )
                                                }*/
                                            },
                                            data:information[name].people.slice(0,10)
                                        }
                                        ]
    };
    myChart.setOption(option,true);

    
    drawlevel(name,information[name].cityName[0]);
    myChart.on("mouseover",function(params){
        drawlevel(name,params.name);
        drawheatmap(information[name],true,params.name);
    });
    myChart.on("mouseout",function(params){
        drawheatmap(information[name],false);
    });
}
function drawlevel(CityN,levelN){
    var data=[];
    var name=[];
    /*改为前10*/
    let biga = information[CityN].province[levelN].fr.length;
    biga=biga>10?10:biga;
    for(var i=0; i<biga;i++){
        data.push(information[CityN].province[levelN].fr[i][1]);
        name.push(information[CityN].province[levelN].fr[i][0]);
    }
    var dom = document.getElementById("sample2");
    var myChart = echarts.init(dom);
    var option = {
            title: {
                text: levelN+"客源分布",
                left:'5%',
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
                show : true,
                borderColor:'#7ebde8',
                borderWidth:1
                
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '7%',
                containLabel: true
            },
            xAxis : [
                     {
                         type : 'category',
                         data :name ,
                         axisTick: {
                             alignWithLabel: true
                         },
                         axisLabel:{
                             interval:0,
                             rotate:45,
                             margin:10,
                             textStyle:{
                                 color:"white"
                             },
                             formatter:function(params){
                                 return params.substr(0,4);
                             }
                         },
                         splitLine: {
                             show:true,
                             lineStyle:{
                                 color:"#1b2c4c"
                             }
                         }
                     }
                     ],
             yAxis : [
                      {
                          type : 'value',
                          splitLine: {
                              show:false,
                              lineStyle:{
                                  color:"#1b2c4c"
                              }
                          },
                          axisLabel:{
                              textStyle:{
                                  color:"white"
                              }
                          }
                      }
                      ],
              series : [
                        {
                            name:'人数',
                            type:'bar',
                            barWidth: '30%',
                            itemStyle: {
                                normal: {
                                    color: new echarts.graphic.LinearGradient(
                                            0, 0, 0, 1,
                                            [
                                             {offset: 0, color: '#7ebce9'},
                                             {offset: 0.5, color: '#7fbce9'},
                                             {offset: 1, color: '#7fbce9'}
                                             ]
                                    )
                                },
                            /*  emphasis: {
                                    color: new echarts.graphic.LinearGradient(
                                            0, 0, 0, 1,
                                            [
                                             {offset: 0, color: '#4b5cb6'},
                                             {offset: 0.5, color: '#5076bf'},
                                             {offset: 1, color: '#5896c7'}
                                             ]
                                    )
                                }*/
                            },
                            label:{
                                position:'top'
                            },
                            data:data
                        }
                        ]
    };
    myChart.setOption(option,true);
    myChart.on("mouseover",function(params){
        drawheatmap(information[CityN],true,levelN,params.name);
    });
    myChart.on("mouseout",function(params){
        drawheatmap(information[CityN],false);
    });
}

function bar1(cityN){
    var dom = document.getElementById("sample3");
    var myChart = echarts.init(dom);
    var option = {
            title: [{
                text: "男女比例",
                left:'10%',
                top:'8%',
                textStyle:{
                    color:"white",
                    fontWeight:"normal"
                }
            }
            ],
            tooltip: {
                trigger: 'item',
                borderColor:'#7ebde8',
                borderWidth:1,
                formatter: "{a} <br/>{b}: {d}% ({c}人)"
            },
            legend: {
                orient: 'vertical',
                x: '80%',
                y:"15%",
                data:['男','女'],
                textStyle:{
                    color:"white"
                }
            },
            series: [
                     {
                         name:'比例',
                         type:'pie',
                         selectedMode: 'single',
                         radius: [0, '50%'],
                         label: {
                             normal: {
                                 show:false,
                                 position: 'inner'
                             }
                         },
                         labelLine: {
                             normal: {
                                 show: false
                             }
                         },
                         data:[
                               {value:information[cityN].age.total.count[0], name:'男',itemStyle:{normal:{color:"#204e7f"}}},
                               {value:information[cityN].age.total.count[1], name:'女',itemStyle:{normal:{color:"#5479a3"}}}
                               ]
                     }
                     ]
    };
    myChart.setOption(option,true);
    myChart.on("click",function(params){
        if(params.name=="男"){
            if(params.data.selected==true){
                bar2(cityN,"man");
            }else {
                bar2(cityN,"total");
            }
        }else if(params.name=="女"){
            if(params.data.selected==true){
                bar2(cityN,"woman");
            }else {
                bar2(cityN,"total");
            }
        }
    });
}
function bar2(cityN,sex){
    var dataN=information[cityN].age.lineName;
    var dataV=[];
    for(var i=0;i<information[cityN].age[sex].line.length;i++){
        dataV.push({value:information[cityN].age[sex].line[i],name:information[cityN].age.lineName[i]});
    }
    var dom = document.getElementById("sample4");
    var myChart = echarts.init(dom);
    var option = {
            title: [{
                text: "年龄层次比例",
                left:'10%',
                top:'8%',
                textStyle:{
                    color:"white",
                    fontWeight:"normal"
                }
            }
            ],
            tooltip: {
                trigger: 'item',
                borderColor:'#7ebde8',
                borderWidth:1,
                formatter: "{a} <br/>{b}: {d}% ({c}人)"
            },
            legend: {
                orient: 'vertical',
                x: '78%',
                y:"15%",
                data:dataN,
                textStyle:{
                    color:"white"
                }
            },
            series: [
                     {
                         name:'年龄层次比例',
                         type:'pie',
                         selectedMode: 'single',
                         radius: [0, '50%'],
                         label: {
                             normal: {
                                 show:false,
                                 position: 'inner'
                             }
                         },
                         labelLine: {
                             normal: {
                                 show: false
                             }
                         },
                         data:dataV
                     }
                     ]
    };
    myChart.setOption(option,true);
};
//addNav();
function addNav(){
    $("._customers-nav").html('');
    var el="";
    for(var key in information){
        el+="<div class='_customers-nav-a'> <p> <span>"+key.split("-")[0]+"</span> <span>&#xe676;</span> <span>"+key.split("-")[1]+"</span> </p> </div>";
    }
    $("._customers-nav").html(el);
}
function controller(){
    //addNav();
    reset();//算大小    
/*  $("._customers-nav").on("click","div",function(){
    //2017-4-12 if可以加判断表格视窗关闭   && $("._customers-box").css('display')!='none'  
    if($(this).hasClass("_customers-nav-a")){
        $(this).addClass("_set").siblings().removeClass("_set");
        var a=$(this).children().children().eq(1).attr("flag");
        let name ="";
        if(a==true){
             name=$(this).children().children().eq(0).text()+ "=" +$(this).children().children().eq(2).text();              
        }
        else{
             name=$(this).children().children().eq(0).text()+ "-" +$(this).children().children().eq(2).text();     
        }
        draw(name);
        bar1(name);
        bar2(name,"total");
        drawheatmap(name);
    }
    else{
        return false;
    }

    
}); */  
    
    
    //航线导航事件
    $("._customers-nav-a").click(function(){
        if($(this).hasClass("_set")){
            return false;
        }
        else{
            $(this).attr("tag","true");
            $(this).addClass("_set").siblings().removeClass("_set").attr("tag","false");
            var a=$(this).children().children().eq(1).attr("flag");
            let hxname ="";
            if(a=="true"){
                 hxname=$(this).children().children().eq(0).text()+ "=" +$(this).children().children().eq(2).text();                
            }
            else{
                 hxname=$(this).children().children().eq(0).text()+ "-" +$(this).children().children().eq(2).text();     
            }            
            cus_namemap=hxname;
            draw(hxname);
            bar1(hxname);
            bar2(hxname,"total");
            drawheatmap(information[hxname]);
        }
    })
    $("._customers-nav-a").eq(0).click();
}
var DATE_FORMAT = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
function checkDate(date){
var result = false;
 if(DATE_FORMAT.test(date)){
   result = true;
  }
 return result;
}
function send(){
//    getFlt_Nbr();
    //关闭所有选择框
    $(".c-selectCity ").on("input",function(){
         getFlt_Nbr();
    }) ;
    $(".c-selectCity").nextAll().remove();
    $("._set-allList").css({"display":"none"});
    $('.opensright').css('display','none');
    var dpt_AirPt_Cd = $('#cityChoice').val();
    if(dpt_AirPt_Cd==null||dpt_AirPt_Cd==''){
        $("._customers").addClass("muhu");
        $(".err").css("display","block");
        alert('请选择起始机场');
        return;
    }
    var arrv_Airpt_Cd = $('#cityChoice2').val();
    if(arrv_Airpt_Cd==null||arrv_Airpt_Cd==''){
        $("._customers").addClass("muhu");
        $(".err").css("display","block");
        alert('请选择到达机场');
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
    }else{
        delete object.isIncloudPasstp;
    }
    var pas_stp = $('#cityChoice3').val();
    var time = $('#startEndDate').val().split(' - ');
    if($('#startEndDate').val()==""||time==null||time.length==0){
        $("._customers").addClass("muhu");
        $(".err").css("display","block");
        alert('请选择起止日期');
        return;
    }
    var lcl_Dpt_Tm = time[0];
    var lcl_Arrv_Tm = time[1];
    if(time.length!=2||
            checkDate(lcl_Dpt_Tm)==false||
            checkDate(lcl_Arrv_Tm)==false||
            (new Date(lcl_Dpt_Tm).getTime()-new Date(lcl_Arrv_Tm).getTime()>0)||
            (new Date(lcl_Arrv_Tm).getTime()-new Date(new Date().format('yyyy-MM-dd')).getTime()>0)){
        $("._customers").addClass("muhu");
        $(".err").css("display","block");
        alert('结束日期不能大于当前日期！');
        return;
    }
    if(pas_stp!=''){
        if(dpt_AirPt_Cd==arrv_Airpt_Cd||dpt_AirPt_Cd==pas_stp||arrv_Airpt_Cd==pas_stp){
            $("._customers").addClass("muhu");
            $(".err").css("display","block");
//          alert('该航线不存在');
            return;
        }
    }else{
        if(dpt_AirPt_Cd==arrv_Airpt_Cd){
            $("._customers").addClass("muhu");
            $(".err").css("display","block");
//          alert('该航线不存在');
            return;
        }
    }
    var flt_nbr =$('._set-list-title').html();
    if(flt_nbr==null||flt_nbr==''){
        $("._customers").addClass("muhu");
        $(".err").css("display","block");
//      alert('没有相关航班号，请重新选择');
        return;
    }
    
    //时间保存起来，用于其它功能
    object.startTime = lcl_Dpt_Tm;
    object.endTime = lcl_Arrv_Tm;
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
	$("#TM-sloading").show();
    $.ajax({        
        url: '/getAllSourceDistriData',
        type: 'post',
        dataType: 'json',
        async: true,
        data:{
            dpt_AirPt_Cd: airports[arrv_Airpt_Cd].iata,
            arrv_Airpt_Cd: airports[dpt_AirPt_Cd].iata,
            pas_stp: pas_stp==""?'':airports[pas_stp].iata,
            flt_nbr: flt_nbr,
            lcl_Dpt_Tm: lcl_Dpt_Tm,
            lcl_Arrv_Tm: lcl_Arrv_Tm,
            isIncludePas: ispas,
            isIncludeRdf: isnon
        }
    })
    .done(function(data) {
    	if(pas_stp != ''){
    		parent.supData.flt = 0;
    		if($('#ishas-stop').is(':checked')==false){
    			$('#ishas-stop').attr('checked',true);
    			$('#isnon-stop').attr('disabled', false);
    		}
    	}
    	$("#TM-sloading").hide('normal');
        $("._customers").removeClass("muhu");
        $(".err").css("display","none");
        information = {};
        var flage = false;
        if(data!=null&&data.length>0){
            information={};
            $("._customers-nav").empty();
            for(var i=0;i<data.length;i++){
                if(data[i].dataMap!=null){
                    flage = true;
                    var segment = new Map();
                    var obj = data[i];
                    
                    
                    //这里生成左侧导航  判断往返还是单段
                    if(obj.leg.substr(3,1)=="="){
                        $("._customers-nav").append("<div class='_customers-nav-a'> <div> <span>"+obj.leg.split("=")[0]+"</span> <span flag=true>&#xe676;</span> <span>"+obj.leg.split("=")[1]+"</span> </div> </div>");
                        
                    }
                    else{
                        $("._customers-nav").append("<div class='_customers-nav-a'> <div> <span>"+obj.leg.split("-")[0]+"</span> <span flag=false>&#xe648;</span> <span>"+obj.leg.split("-")[1]+"</span> </div> </div>");
                    }
                
                if(obj!=null){
                    var provinceMap = new Map();
                    var age = {};
                    var people = [];
                    var cityName = [];
                    var dataMap = obj.dataMap;
                    if(dataMap!=null){                      
                        for(var key in dataMap){
                            cityName.push(key);
                            var o= dataMap[key];
                            var provice = {};
                            if(o!=null&&o.length>0){
                                var zr=0;
                                var fr = [];
                                for(var j=0;j<o.length;j++){
                                    var frArray =[];
                                    zr+=o[j].number;
                                    frArray.push(o[j].city);
                                    frArray.push(o[j].number);
                                    frArray.push(o[j].city_x);
                                    frArray.push(o[j].city_y);                                  
                                    fr.push(frArray);
                                }
                                people.push(zr);
                                provice.zr = zr;
                                provice.fr = fr;
                            }
                            provinceMap[key] = provice;
                        }
                    }
                    var peopleStruct = obj.peopleStruct;
                    if(peopleStruct!=null){
                        var totalWomen = 0;
                        var totalMan = 0;
                        var totalline = [];
                        var total = {};
                        var totalcount = [];
                        var man = {};
                        var woman = {};
                        var manline = [];
                        var womanline = [];
                        var lineName = [];
                        for(var key in peopleStruct){
                            lineName.push(key);
                            var o = peopleStruct[key];
                            var mancount = o['男'];
                            var womancount = o['女'];
                            totalline.push(mancount+womancount);
                            manline.push(mancount);
                            womanline.push(womancount);
                            totalWomen+=womancount;
                            totalMan+=mancount;
                        }
                        totalcount.push(totalMan);
                        totalcount.push(totalWomen);
                        total.count=totalcount;
                        total.line=totalline;
                        man.line=manline;
                        woman.line=womanline;
                        age.man = man;
                        age.woman = woman;
                        age.total = total;
                        age.lineName = lineName;
                    }
                    segment.province = provinceMap;
                    segment.age = age;
                    segment.people =people;
                    segment.cityName =cityName;
                }
                
                //自己根据后台逻辑装载航线该显示的分段---后台逻辑变了，这里肯定要变（与顺序逻辑一一对应的）
                var airportName = parent.airportMap[parent.airportCode].aptChaNam;

                information[obj.leg]=segment;
            }
                else if(i==1){
                    if(airportName==dpt_AirPt_Cd){
                        information[dpt_AirPt_Cd+'-'+arrv_Airpt_Cd] = segment;
                    }
                    if(airportName==pas_stp){
                        information[pas_stp+'-'+arrv_Airpt_Cd] = segment;
                    }
                    if(airportName==arrv_Airpt_Cd){
                        information[arrv_Airpt_Cd+'-'+pas_stp] = segment;
                    }
                }

            }
            if(flage){
                controller();
            }
            }
        else{
//              alert('当前起止日期内没有相关数据，请重新选择起止日期');
                $("._customers").addClass("muhu");
                $(".err").css("display","block");
                flage = true;
            }

        if(!flage){
//          alert('当前起止日期内没有相关数据，请重新选择起止日期');
            $("._customers").addClass("muhu");
            $(".err").css("display","block");
        }
    })
    .fail(function() {
    	$("#TM-sloading").hide('normal');
        console.log("error");
    })
    
    
     addBar(".sr-box","._customers-box","._customers-box-c");
     $("._customers-box-c").css("top","0px");
     $("._bar-box").css("right","30px");
     
}
$(window).resize(function(){
    reset();//算大小
    $("._customers-nav-a").eq(0).click();
});
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





//伸缩栏

var cus_rotate_flag=0;
$(".cus-closechart").click(function(){
    cus_rotate_flag+=180;
    $('.cus-closechart span').css('transform','rotate('+ cus_rotate_flag +'deg)');
    if($("._customers-box").css('display')=='none'){
        $("._customers-box").show('fast');
        $("._customers").animate({width:'55%'});
        setTimeout(function(){
            draw(cus_namemap);
            bar1(cus_namemap);
            bar2(cus_namemap,"total");
        },500);
    }
    else{
        $("._customers").animate({width:'88px'});
        $("._customers-box").hide('fast');
    }
})


function l_getline(){
	let lfrom=$("._set").children().children().eq(0).text();
	let lto=$("._set").children().children().eq(2).text();
	let ld={};
    ld.line=[];
    ld.port=[];
    ld.port.push({name:parent.portinfo[lfrom].airName,value:[parent.portinfo[lfrom].airZB[0],parent.portinfo[lfrom].airZB[1]]});
    ld.port.push({name:parent.portinfo[lto].airName,value:[parent.portinfo[lto].airZB[0],parent.portinfo[lto].airZB[1]]});

    ld.line.push( {coords:[[parent.portinfo[lfrom].airZB[0],parent.portinfo[lfrom].airZB[1]],[parent.portinfo[lto].airZB[0],parent.portinfo[lto].airZB[1]]],name:parent.portinfo[lfrom].airName}); 
    return ld;
  }
//绘制背景热图
function drawheatmap(data,lhag,pss,pcc){
    var ldata=l_getline();
    var myChart = echarts.init(document.getElementById('cus_heatmap')); 
    var heylong=[];
    var hmax=0; //获取max
    if(lhag){   //市区分布
        $.each(data.province,function(i,el){
            if(i==pss){             
                $.each(el.fr,function(ii,ell){
                    if(pcc){
                        if(ell[0]==pcc){
                            heylong.push({name:ell[0],value:[ell[2],ell[3],ell[1]]});
                            if(ell[1]>hmax)hmax=ell[1];
                            
                        }                       
                    }
                    else{
                        heylong.push({name:ell[0],value:[ell[2],ell[3],ell[1]]});
                        if(ell[1]>hmax)hmax=ell[1];
                    }
                })              
            }   
        })
    }    
    else{
        $.each(data.province,function(i,el){
            $.each(el.fr,function(ii,ell){
                heylong.push({name:ell[0],value:[ell[2],ell[3],ell[1]]});
                if(ell[1]>hmax)hmax=ell[1];
            })      
        })
        
    }
    if(hmax< 50){hmax=50};
    //中心点偏移
    //heylong.split(0,2);
    //[heylong[0].value[0],heylong[0].value[1]]
    var mapcenter=[Number(heylong[0].value[0])-5,Number(heylong[0].value[1])+2];
    var option={
            backgroundColor:'rgb(7,19,39)',
            title:{
                show:false
            },
            tooltip:{
                trigger:'item',
                formatter: function (params) {
                	if(params.seriesName=="customer"){
                        return params.name + '客量 : '+ params.value[2];
                	}
                },
            },
            geo:{	//地图配置
                map:'world' , 
                label: {
                    normal: {
                        show: false,
                        textStyle: {
                            color: '#777'
                        }
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            color: '#fff'
                        }
                    }
                }, 
                itemStyle:{
                    normal: {
                        areaColor: '#22324f',
                        borderColor: '#666',
                        borderWidth: 1.1,
                        borderType: 'solid'
                    },
                    emphasis: {
                        areaColor: '#22324f'
                    }        
                },
                zoom:6,
                center:[104.06,30.67],
                roam: true
            },
            visualMap: {	//控制条
                show: true,
                min: 0,
                max: hmax/10,
                right:'5%',
                bottom:'5%',
                calculable: true,
                hoverLink:false,
                itemWidth:15,
                align:'right',
                color:['#d9631b','#fac106'],
                textStyle: {
                    color: '#fff'
                },
                align:'right',
                seriesIndex: 0                
            },
            series:[{	//客量图
                name:'customer',
                type:'heatmap',
                symbolSize:10,
                coordinateSystem: 'geo',                         
                itemStyle: {
                   emphasis: {
                       borderColor: '#fff',
                       borderWidth: 1
                   }
                },
                data:heylong
    		} ,
    		{	//机场线图
    	        name:'twoline',
    	        type:'lines',
    	        coordinateSystem: 'geo',
    	        lineStyle:{
    	            normal:{
    	                color:"#7fbce9",
    	                curveness:0.25,
    	                opacity:0.5,
    	                type:"solid",
    	                width:2
    	            }
    	        },
    	        effect:{
    	        	show:true,
    	        	color:'#7fbce9',
    	        	period:10,
    	        	symbolSize:15,
    	        	trailLength:0,
        	        symbol:'path://M1705.06,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705.06,1318.313z',

    	        },
    	        label:{
    	        	normal:{
    	        		show: false,
    	        	}
    	        },
    	        data: ldata.line,
    	        zlevel:2
    	    },
    		{	//机场点图
    	        name:'twoport',
    	        type:'scatter',
    	        symbolSize: 8,
    	        coordinateSystem: 'geo',
	            itemStyle: {
	                normal: {
	                     color: '#fff'
	                }
	            },
    	        label:{
    	        	normal:{
    	        		show: true,
    	        		formatter:'{b}',
    		            position:'left',
    		            textStyle:{
        	        		color:'#fff',
        	        		fontSize:13
    		            }
    	        	} ,
	   	            emphasis:{
		            	show:true,
    		            textStyle:{
        	        		color:'#fff',
        	        		fontSize:20
    		            }
		            },
    	        },
    	        data: ldata.port,
    	        zlevel:3
    	    }]
        }
    myChart.setOption(option);
	$('#TM-sloading').hide('normal');
    $('.cus-closechart').show();
    $('.cus_heatzoom').show();
    
    /*改变窗口大小*/
    /*放大*/
    $(".cus_heatzoom>div:nth-of-type(1)").click(function(){
        var num = myChart.getOption();//获取上次保留的实时数据
        if(num.geo[0].zoom<20){
            num.geo[0].zoom+=2;
            myChart.setOption(num,true);
        };
    });
    /*缩小*/
    $(".cus_heatzoom>div:nth-of-type(2)").click(function(){
        var num = myChart.getOption();//获取上次保留的实时数据
        if(num.geo[0].zoom>3){
            num.geo[0].zoom-=2;
            myChart.setOption(num, true);
        };
    });
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
