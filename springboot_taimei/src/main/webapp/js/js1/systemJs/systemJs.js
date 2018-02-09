/**********数据设置************/
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
var fltArray = {};
var atten="";
var changeAtten = "";
var focusFlag;//是否关注所有航班
var clickFocusFltFlag=false;//点击关注航班弹出设置界面标记
$(function(){
    var authCode="";
    /**二维码*/
    function randomNum(min,max){
        return Math.floor(Math.random()*(max-min)+min);
    }
    function randomColor(min,max){
        var _r = randomNum(min,max);
        var _g = randomNum(min,max);
        var _b = randomNum(min,max);
        return "rgb("+_r+","+_g+","+_b+")";
    }
    document.getElementById("verificatio").onclick = function(e){
        e.preventDefault();
        drawPic();
    };
    function drawPic(){
        var $canvas = document.getElementById("verificatio");
        var _str = "0123456789";
        var _picTxt = "";
        var _num = 4;
        var _width = $canvas.width;
        var _height = $canvas.height;
        var ctx = $canvas.getContext("2d");
        ctx.textBaseline = "bottom";
        ctx.fillStyle = randomColor(180,240);
        ctx.fillRect(0,0,_width,_height);
        for(var i=0; i<_num; i++){
            var x = (_width-10)/_num*i+10;
            var y = randomNum(_height/2,_height);
            var deg = randomNum(-45,45);
            var txt = _str[randomNum(0,_str.length)];
            _picTxt += txt;
            ctx.fillStyle = randomColor(10,100);
            ctx.font = randomNum(16,40)+"px SimHei";
            ctx.translate(x,y);
            ctx.rotate(deg*Math.PI/180);
            ctx.fillText(txt, 0,0);
            ctx.rotate(-deg*Math.PI/180);
            ctx.translate(-x,-y);
        }
        for(var i=0; i<_num; i++){
            ctx.strokeStyle = randomColor(90,180);
            ctx.beginPath();
            ctx.moveTo(randomNum(0,_width), randomNum(0,_height));
            ctx.lineTo(randomNum(0,_width), randomNum(0,_height));
            ctx.stroke();
        }
        for(var i=0; i<_num*10; i++){
            ctx.fillStyle = randomColor(0,255);
            ctx.beginPath();
            ctx.arc(randomNum(0,_width),randomNum(0,_height), 1, 0, 2*Math.PI);
            ctx.fill();
        }
        authCode=_picTxt.toUpperCase();
        return _picTxt;
    }
    drawPic();
    /***设置界面控制***/
    $(".sSettings").on("click",function(){
        $(".pla-set").css({"display":"block","background-color":"rgba(0,0,0,0.6)"});
        $(".pla-set-interface").animate({
            "margin-top":"5%"
        },300);
        setData();
    });
    $(".modification-list>li:nth-of-type(2)").on("click",function(){
        var name=$(this).text();
        $(this).text( $(".modification-list>li").eq(0).text());
        $(".modification-list>li").eq(0).text(name);
        $(".modification-list").css({"height":"25px","overflow":"hidden"});
    });
    $(".modification-pas").on("click",function(){
        if($(this).prev().attr("type")=="password"){
            $(this).prev()[0].type="text";
            $(this).html("&#xe673;");
        }else {
            $(this).prev()[0].type="password";
            $(this).html("&#xe667;");
        }
    });
    $(".modK,.conR,.epA,.cosb").on("mouseover",function(e){
        var le=e.clientX+50+"px";
        var to=e.clientY+"px";
        if($(this).hasClass("modK")){
            $(".modification-pro").css({"display":"block","left":le,"top":to}).text("指定系统自动执行数据采集任务的开始时间。因中航信数据归档会重置连接，请尽量避免凌晨三点左右执行采集任务。");
        }else if($(this).hasClass("conR")){
            $(".modification-pro").css({"display":"block","left":le,"top":to}).text("指定当系统自动采集任务进行中发现账号被人为登录造成账号使用冲突，系统自动暂停采集的时间间隔。");
        }else if($(this).hasClass("epA")){
            $(".modification-pro").css({"display":"block","left":le,"top":to}).text("深度采集会采集乘客票面级别数据，收入分析更精确，并且可提供客源组成分析。");
        }else if($(this).hasClass("cosb")){
            $(".modification-pro").css({"display":"block","left":le,"top":to}).text("在选择深度采集时，因指令量较大需指定待采航班号，为避免账号行为异常，建议每个账号指定不超过3个航班对。");
        }
    });
    $(".modK,.conR,.epA,.cosb").on("mouseout",function(){
        $(".modification-pro").css({"display":"none"});
    });
    /*关闭账号添加*/
    $(".modification-clear").on("click",function(){
        $(".modification").animate({
            "right":"-330px"
        },function(){
            $(".modification").css("display","none");
        });
    });
    $(".modification-clear").click();
    /*打开配置*/
    function condition(){
        $(".modification").css("display","block").animate({ //新增账号
            "right":"10px"
        });
    }
    /*配置页面*/
    function conPage(tag,num){
        if(num==0){
        	$("#id").val("");
            $(".p-user").val("");
            $(".p-pas").val("");
            $(".p-jobn").val("");
            $(".p-jobp").val("");
            $(".p-level").val("");
            $(".p-etermIp").val("");
            $(".p-etermPort").val("");
            $(".p-Secure")[0].checked="checked";
            $(".p-si").val("");
            for(var i=0;i<$(".acquisition-time>input").length;i++){
                $(".acquisition-time>input").eq(i).val("");
            }
            $(".p-conflict").val("");
            $(".p-conflictAvoid")[0].checked="checked";
            $(".epth-acquisition>input")[1].checked="checked";
            sd(tag,num);
        }else if(num==1){
        	$("#id").val(aggregate[tag].id);
            $(".p-user").val(aggregate[tag].user);
            $(".p-pas").val(aggregate[tag].pas);
            $(".p-jobn").val(aggregate[tag].jobn);
            $(".p-jobp").val(aggregate[tag].jobp);
            $(".p-level").val(aggregate[tag].level);
            $(".p-etermIp").val(aggregate[tag].etermIp);
            $(".p-etermPort").val(aggregate[tag].etermPort);
            if(aggregate[tag].Secure=="0"){//安全传输
                $(".p-Secure")[0].checked="";
            }else {
                $(".p-Secure")[0].checked="checked";
            }
            $(".p-si").val(aggregate[tag].si);
            for(var i=0;i<$(".acquisition-time>input").length;i++){
                $(".acquisition-time>input").eq(i).val(aggregate[tag].acquisitionT.broken[i]);
            }
            $(".p-conflict").val(aggregate[tag].conflict);
            if(aggregate[tag].conflictAvoid=="0"){ //冲突避让
                $(".p-conflictAvoid")[0].checked="";
            }else {
                $(".p-conflictAvoid")[0].checked="checked";
            }
            if(aggregate[tag].depth=="0"){  //是否深度采集
                $("#radio-jia").attr("checked","checked");
                $("#radio-state").removeAttr("checked");
            }else {
                $("#radio-state").attr("checked","checked");
            	$("#radio-jia").removeAttr("checked");
            }
            sd(tag,num);
        }
    }
    /*深度*/
    function sd(tag,num){
        /*深度范围*/
        if(num==1){
            var j=0;
            for(var i=0;i<3;i++){
            	if(aggregate[tag].depthScope.number.go[i]!=null && aggregate[tag].depthScope.number.go[i]!=""){
            		$(".speciaList-in1").eq(j).val(aggregate[tag].depthScope.number.go[i]);
            	}else{
            		$(".speciaList-in1").eq(j).val("");
            	}
            	if(aggregate[tag].depthScope.number.back[i]!=null && aggregate[tag].depthScope.number.back[i]!=""){
            		$(".speciaList-in1").eq(j+1).val(aggregate[tag].depthScope.number.back[i]);
            	}else{
            		$(".speciaList-in1").eq(j+1).val("");
            	}
            	if(aggregate[tag].depthScope.line.go[i]!=null &&aggregate[tag].depthScope.line.go[i]!=""){
            		$(".speciaList-in2").eq(j).val(aggregate[tag].depthScope.line.go[i]);
            	}else{
            		$(".speciaList-in2").eq(j).val("");
            	}
               
                if(aggregate[tag].depthScope.line.back[i]!=null &&aggregate[tag].depthScope.line.back[i]!=""){
                	 $(".speciaList-in2").eq(j+1).val(aggregate[tag].depthScope.line.back[i]);
                }else{
                	 $(".speciaList-in2").eq(j+1).val("");
                }
                j+=2;
            }
        }else if(num==0){
            for(var i=0;i< $(".speciaList-in1").length;i++){
                $(".speciaList-in1").eq(i).val("");
                $(".speciaList-in2").eq(i).val("");
            }
        }
    }
    $(".pla-set").on("click","div",function(){
        if($(this).hasClass("addBox")){
            conPage("",0);
            condition();
        }else if($(this).hasClass("collect-change")){//修改账号信息
            conPage($(this).attr("tag"),1);
            condition();
        }else if($(this).hasClass("collect-clear")){//删除账号
        	if (confirm("确认要删除？")) {
	            for(var i=0;i< $(".collect-configuration-area").length;i++){
	                if($(".collect-configuration-area").eq(i).attr("tag")==$(this).attr("tag")){
	                    del($(this).attr("id"));
	                    delete aggregate[$(this).attr("tag")];
	                    $(".collect-configuration-area").eq(i).animate({
	                        "height":"0px"
	                    },function(){
	                        $(".collect-configuration-area").eq(i).remove();
	                    });
	                    break;
	                }
	            }
        	}
        }else if($(this).hasClass("modification-sub")){//保存账号-上传信息
			var para = {};
        	para.id =  $("#id").val();
        	if($("[name='eTermName']").val()==""){
        		alert("用户名不能为空");
        		return;
        	}
        	para.eTermName = $("[name='eTermName']").val();
        	if($("[name='password']").val()==""){
        		alert("密码不能为空");
        		return;
        	}
        	para.password = addlock($("[name='password']").val());
        	if($("[name='wordName']").val()==""){
        		alert("工作号不能为空");
        		return;
        	}
        	para.wordName = $("[name='wordName']").val();
        	if($("[name='wordPassword']").val()==""){
        		alert("工作号密码不能为空");
        		return;
        	}
        	para.wordPassword = addlock($("[name='wordPassword']").val());
        	if($("[name='lvl']").val()==""){
        		alert("工作号级别不能为空");
        		return;
        	}
        	para.lvl = $("[name='lvl']").val();
        	if($("[name='ip']").val()==""){
        		alert("ip地址不能为空");
        		return;
        	}
        	para.ip = $("[name='ip']").val();
        	if($("[name='port']").val()==""){
        		alert("端口号不能为空");
        		return;
        	}
        	para.port = $("[name='port']").val();
        	para.security = $("[name='security']").val();
        	if($("[name='si']").val()==""){
        		alert("SI指令不能为空");
        		return;
        	}
        	para.si = $("[name='si']").val();
        	var autodate = $("[name='auto']").val()+$("[name='auto1']").val()+":"+$("[name='auto2']").val()+$("[name='auto3']").val();
        	if(autodate==":"){
        		alert("自动采集时间不能为空");
        		return;
        	}
        	var auto0 = $("[name='auto']").val();
        	var auto1 = $("[name='auto1']").val();
        	var auto2 = $("[name='auto2']").val();
        	var auto3 = $("[name='auto3']").val();
        	if(auto0 ==""){
        		auto0 = "0";
        	}
        	if(auto1==""){
        		auto1 = "0";
        	}
        	if(auto2==""){
        		auto2 = "0";
        	}
        	if(auto3==""){
        		auto3 = "0";
        	}
        	
        	para.auto = auto0+auto1+":"+auto2+auto3;
        	if($("[name='avoidTime']").val()==""){
        		alert("冲突等待时间不能为空");
        		return;
        	}
        	para.avoidTime = $("[name='avoidTime']").val();
        	para.che = $("[name='che'][checked]").val();
        	if(para.che==1){
        		para.flb_nbr1 = $("[name='flb_nbr1']").val();
        		para.flt_rte_cd1 = $("[name='flt_rte_cd1']").val();
        		para.flb_nbr2 = $("[name='flb_nbr2']").val();
        		para.flt_rte_cd2 = $("[name='flt_rte_cd2']").val();
        		para.flb_nbr3 = $("[name='flb_nbr3']").val();
        		para.flt_rte_cd3 = $("[name='flt_rte_cd3']").val();
        		para.flb_nbr4 = $("[name='flb_nbr4']").val();
        		para.flt_rte_cd4 = $("[name='flt_rte_cd4']").val();
        		para.flb_nbr5 = $("[name='flb_nbr5']").val();
        		para.flt_rte_cd5 = $("[name='flt_rte_cd5']").val();
        		para.flb_nbr6 = $("[name='flb_nbr6']").val();
        		para.flt_rte_cd6 = $("[name='flt_rte_cd6']").val();
        	}
        	$.ajax({
        		url:'/addeTerm',
        		type:"post",
        		data:para,
        		success:function(data){
        			if(data.success){
        				$(".modification").css("display","none");
        				obtain();
        			}
        		}
        	});
        }
    });
    $(".pla-set").on("click","p",function(){
        var sp="."+$(this).attr("tag");
        $(sp).remove();
    });
    $('#focusFlights').on('click',function(){
    	var map = atten;
    	var tag = true;
    	breaktag:
    	for(var i=0;i<map.length;i++){
			var obj = map[i].data;
			for(var j=0;j<obj.length;j++){
				if(obj[j].sle==1){
					tag=false;
					break breaktag;
				}
			}
		}
    	if(tag){
    		$('.sSettings').click();
    		$('.fltSet').click();
    		clickFocusFltFlag = true;
    	}
    });
//    $(".epth-acquisition>input").on("click",function(){
//        iszhen()
//    });
	/*删除的账号*/
    function addlock(pwd){
    	var b = "";
    	var e = pwd.split("");   
    	for(var i = 0;i<e.length;i++){
    		
    		b += e[i]+randomc();
    	}
    	return b;
    }
    function delock(pwd){
    	var b = "";
    	var e = pwd.split("");   
    	
    	for(var i = 0;i<e.length;i++){
    		if(i%2==0){
    			b += e[i];
    		}
    	}
    	return b;
    }
    function randomc(){
    	var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; 
    	return chars.charAt(Math.floor(Math.random() * chars.length));
    }
    
    function del(id){
    	$.ajax({
    		url:"/eterm_delete?id="+id,
    		success:function(data){
    			if(data.success){
    				
    			}else{
    				alert("删除失败");
    			}
    		}
    	});
    }
    /*初次加载获取数据*/
    function setData(name){
        $.ajax({
            type:'get',
            url : '/employee_load',
            dataType : 'jsonp',
            success : function(data) {
//                data = $.parseJSON(data);
                if(data.success.opResult=="0"){
                    $(".pla-set-interface-load").css({"display":"none"});//关闭加载动画
                    if(typeof(data.success.obj.usrNm)!="undefined"){
                        $("#usrNm").val(data.success.obj.usrNm);
                    }
                    if(typeof(data.success.obj.departmentId)!="undefined"){
                        $("#departmentId").val(data.success.obj.department.dptNm);
                    }
                    if(typeof(data.success.obj.phone)!="undefined"){
                        $("#phone").val(data.success.obj.phone);
                    }
//                    if(typeof(data.success.obj.weixin)!="undefined"){
//                        $("#weixin").val(data.success.obj.weixin);
//                    }
//                    if(typeof(data.success.obj.qqNbr)!="undefined"){
//                        $("#qqNbr").val(data.success.obj.qqNbr);
//                    }
                    if(typeof(data.success.obj.email)!="undefined"){
                        $("#email").val(data.success.obj.email);
                        if(data.success.obj.email!=''){
                        	$('.bindemail').text("解绑");
                        	
                        }else{
                        	$('.bindemail').text("绑定");
                        }
                    }
                    if(typeof(data.success.obj.compellation)!="undefined"){
                        $("#compellation").val(data.success.obj.compellation);
                    }
                    if(typeof(data.success.obj.headPath)!="undefined"&&data.success.obj.headPath!=''){
                    	$('.ph-img').css('background-image','url("'+data.success.obj.headPath+'")');
                    }
                    if(typeof(data.success.obj.bgPath)!="undefined"&&data.success.obj.bgPath!=''){
                    	$('.bg-img').css('background-image','url("'+data.success.obj.bgPath+'")');
                    }
                    $('._set-list-title').empty();
        			var list={
                	        data:data.success.list, //st节点集合
                	        name:"#dptList",  //添加list节点
                	        cleName:".pla-set-interface",   //取消绑定事件函数节点
                	        fun:function(){
                	        }
                	    };
        			chooseDpt(list,data.success.obj);
                    aggregate={
                        lzadmin:{
                            user:"lzadmin",
                            pas:"123456",
                            jobn:"564561",
                            jobp:"987654",
                            level:"2",
                            etermIp:"192.168.0.1",
                            Secure:"0",
                            etermPort:"8080",
                            si:"009",
                            acquisitionT:{time:"80.30",broken:[2,0,3,0]},
                            conflict:"60",
                            conflictAvoid:"0",
                            depth:"1",
                            depthScope:{"number":{go:["MF8430","MF8429","FD5200"],back:["MF9852","MF8765","FD5211"]},"line":{go:["AAABBBCCC","666555444","RRREEEIII"],back:["AAABBBCCC","AAABBBCCC","RRREEEIII"]}},
                            lastTime:"2016.10.20",
                            state:"正常"
                        },
                        syadmin:{
                            user:"syadmin",
                            pas:"123456",
                            jobn:"564561",
                            jobp:"987654",
                            level:"2",
                            etermIp:"192.168.0.1",
                            Secure:"0",
                            etermPort:"8080",
                            si:"007",
                            acquisitionT:{time:"80.30",broken:[2,0,3,0]},
                            conflict:"60",
                            conflictAvoid:"0",
                            depth:"0",
                            depthScope:{"number":{go:["MF8430","MF8429","FD5200"],back:["MF8430","MF8429","FD5200"]},"line":{go:["AAABBBCCC","AAABBBCCC","RRREEEIII"],back:["AAABBBCCC","AAABBBCCC","RRREEEIII"]}},
                            lastTime:"2013.8.20",
                            state:"正常"
                        }
                    };
//                    adjust();
                }
            },
            error : function() {//FIXME  请求数据后此函数内容应放在success里面 -暂时做无求情数据测试
                console.log("err");
            }
        });

    };
    /*获取数据*/
    function obtain(name){
        $.ajax({
            type:'get',
            url : '/getEtermAirportOnLineData',
            success : function(data) {
            	var datas = {};
                if(data.length>0){
                	for (var i in data) {
                		var number_go = [];
                		var number_back = [];
                		var line_go = [];
                		var line_back = [];
                		var info = data[i].info;
						for(var j in info.addFltNbrs){
							var nbrs = info.addFltNbrs[j];
							if(j%2==0){
								number_go.push(nbrs.flt_Nbr);
								line_go.push(nbrs.air_line);
							}else{
								number_back.push(nbrs.flt_Nbr);
								line_back.push(nbrs.air_line);
							}
						}
						
						info.pas = delock(info.pas);
						info.jobp = delock(info.jobp);
						info.acquisitionT = {time:info.acquisitionT,broken:info.broken};
						info.depthScope = {"number":{go:number_go,back:number_back},"line":{go:line_go,back:line_back}};
						datas[data[i].eTermUserName] = info;
					}
                	aggregate = datas;
                	adjust();
                }
            },
            error : function() {//FIXME  请求数据后此函数内容应放在success里面 -暂时做无求情数据测试
            }
        });

    }
    /*深度开关*/
    //一个空函数,用来做线程睡眠用的
    function textFinishs(i){
    	$(".tipBox-cont-other:eq("+i+")").removeAttr("hidden");
    	
    }
    function text(i,text){
    	$(".tipBox-cont-other img:eq("+i+")").removeAttr("src");
    	$(".tipBox-cont-other p:eq("+i+")").text(text);
    	$(".tipBox-cont-other span:eq("+i+")").removeAttr("style","inline");
    	
    }
    function errCode(i,text){
		$(".tipBox-cont-other:eq("+i+")").addClass("error");
		$(".tipBox-cont-other img:eq("+i+")").removeAttr("src");
		$(".tipBox-cont-other p:eq("+i+")").text(text);
		
		$(".tipBox-cont-other span:eq("+i+")").empty();
		$(".tipBox-cont-other span:eq("+i+")").append("&#xe64a;");
		$(".tipBox-cont-other span:eq("+i+")").removeAttr("style","inline");
	}
    function errCodeFinishs(text){
    	$(".tipBox-cont-result").addClass("error");
    	$(".tipBox-cont-result div").text(text);
    	$(".tipBox-cont-result").removeAttr("hidden");
    }
    function addHidden(){
    	$(".tipBox-cont").empty();
    	 var divs = "<div class=''  hidden=''><p>正在连接账号.测试si指令</p><img src='/images/icon/dt.gif' alt=''><span style='display: none;'>&#xe649;</span>	</div>";
    	  divs+= "<div class=''  hidden=''><p>正在连接账号.测试FDL指令</p><img src='/images/icon/dt.gif' alt=''><span style='display: none;'>&#xe649;</span>	</div>";
    	  divs+= "<div class=''  hidden=''><p>正在连接账号.测试MLB指令</p><img src='/images/icon/dt.gif' alt=''><span style='display: none;'>&#xe649;</span>	</div>";
    	  divs+= "<div class=''  hidden=''><p>正在连接账号.测试RT指令</p><img src='/images/icon/dt.gif' alt=''><span style='display: none;'>&#xe649;</span>	</div>";
    	  divs+= "<div class=''  hidden=''><p>正在连接账号.测试DETR指令</p><img src='/images/icon/dt.gif' alt=''><span style='display: none;'>&#xe649;</span>	</div>";
    	  divs+= "<div class=''  hidden=''><p>正在连接账号.测试FD指令</p><img src='/images/icon/dt.gif' alt=''><span style='display: none;'>&#xe649;</span>	</div>";
    	  divs+= "<div class=''  hidden=''><p>正在连接账号.测试FLK P指令</p><img src='/images/icon/dt.gif' alt=''><span style='display: none;'>&#xe649;</span>	</div>";
    	  divs+= "<div class=''  hidden=''><span>&#xe674;</span><div>测试成功</div>	</div>";
    	$(".tipBox-cont").append(divs);
    	for ( var int = 0; int < 7; int++) {
    		$(".tipBox-cont div:eq("+int+")").addClass("tipBox-cont-other");
		}
    	$(".tipBox-cont div:eq(7)").addClass("tipBox-cont-result");
    }
    $(".pla-set").on("click","span",function(){
        if($(this).hasClass("click-btn-test")){//开始测试
        	
        	//背景关闭按钮不可用
        	$(".pla-set-clear").attr("hidden","hidden");
            //给所有的连接测试都隐藏掉
        	addHidden();
        	$(".tipBox").css({"display":"block"}).animate({
        		"opacity":"1"
        	});
        	setTimeout(function(){textFinishs(0);},0);
        	$.ajax({
        		url:"/eterm_test?id="+$(this).attr("id"),
        		success:function(data){
        			if(data.success){
        				//采集通过后的动态效果
        				setTimeout(function(){text(0,"SI指令测试完成");},2000);
        				setTimeout(function(){textFinishs(1);},2500);
        				setTimeout(function(){text(1,"FDL指令测试完成");},4500);
        				setTimeout(function(){textFinishs(2);},5000);
        				setTimeout(function(){text(2,"MLB指令测试完成");},7000);
        				setTimeout(function(){textFinishs(3);},7500);
        				setTimeout(function(){text(3,"RT指令测试完成");},9500);
        				setTimeout(function(){textFinishs(4);},10000);
        				setTimeout(function(){text(4,"DETR指令测试完成");},12000);
        				setTimeout(function(){textFinishs(5);},12500);
        				setTimeout(function(){text(5,"FD指令测试完成");},14500);
        				setTimeout(function(){textFinishs(6);},15000);
        				setTimeout(function(){text(6,"FLK P指令测试完成");},17000);
        				setTimeout(function(){$(".tipBox-cont-result").removeAttr("hidden");},17000);
        			}else{
        				//采集失败后的动态效果;
//        				* 99:代表si登录异常
//        				 * 98:FDL指令无权限
//        				 * 97:MLB指令无权限
//        				 * 96:RT指令无权限
//        				 * 95:DETR指令无权限
//        				 * 94:当前航班号下fd 指令无权限
//        				 * 93:当前航班号下flk p指令无权限
//        				 * 92:当前采集所有航班,无PNR信息
//        				 * 91:当前采集所有航班,无DETR信息
        				var code = data.errorCode;
        				if(code==99){
        					setTimeout(function(){errCode(0,"SI指令测试完成");},2000);
        					setTimeout(function(){errCodeFinishs(data.message);},2000);
        				}else if(code==98){
        					setTimeout(function(){text(0,"SI指令测试完成");},2000);
        					setTimeout(function(){textFinishs(1);},2500);
        					
        					setTimeout(function(){errCode(1,"FDL指令测试完成");},4500);
        					setTimeout(function(){errCodeFinishs(data.message);},4500);
        				}else if(code==97){
        					setTimeout(function(){text(0,"SI指令测试完成");},2000);
        					setTimeout(function(){textFinishs(1);},2500);
        					setTimeout(function(){text(1,"FDL指令测试完成");},4500);
        					setTimeout(function(){textFinishs(2);},5000);
        					
        					setTimeout(function(){errCode(2,"MLB指令测试完成");},7000);
        					setTimeout(function(){errCodeFinishs(data.message);},7000);
        				}else if(code==96){
        					setTimeout(function(){text(0,"SI指令测试完成");},2000);
        					setTimeout(function(){textFinishs(1);},2500);
        					setTimeout(function(){text(1,"FDL指令测试完成");},4500);
        					setTimeout(function(){textFinishs(2);},5000);
        					setTimeout(function(){text(2,"MLB指令测试完成");},7000);
            				setTimeout(function(){textFinishs(3);},7500);
        					
        					setTimeout(function(){errCode(3,"RT指令测试完成");},9500);
        					setTimeout(function(){errCodeFinishs(data.message);},9500);
        				}else if(code==95){
        					setTimeout(function(){text(0,"SI指令测试完成");},2000);
        					setTimeout(function(){textFinishs(1);},2500);
        					setTimeout(function(){text(1,"FDL指令测试完成");},4500);
        					setTimeout(function(){textFinishs(2);},5000);
        					setTimeout(function(){text(2,"MLB指令测试完成");},7000);
            				setTimeout(function(){textFinishs(3);},7500);
            				setTimeout(function(){text(3,"RT指令测试完成");},9500);
            				setTimeout(function(){textFinishs(4);},10000);
        					
        					setTimeout(function(){errCode(4,"DETR指令测试完成");},12000);
        					setTimeout(function(){errCodeFinishs(data.message);},12000);
        				}else if(code==94){
        					setTimeout(function(){text(0,"SI指令测试完成");},2000);
        					setTimeout(function(){textFinishs(1);},2500);
        					setTimeout(function(){text(1,"FDL指令测试完成");},4500);
        					setTimeout(function(){textFinishs(2);},5000);
        					setTimeout(function(){text(2,"MLB指令测试完成");},7000);
            				setTimeout(function(){textFinishs(3);},7500);
            				setTimeout(function(){text(3,"RT指令测试完成");},9500);
            				setTimeout(function(){textFinishs(4);},10000);
            				setTimeout(function(){text(4,"DETR指令测试完成");},12000);
            				setTimeout(function(){textFinishs(5);},12500);
        					
        					setTimeout(function(){errCode(5,"FD指令测试完成");},12500);
        					setTimeout(function(){errCodeFinishs(data.message);},12500);
        				}else if(code==93){
        					setTimeout(function(){text(0,"SI指令测试完成");},2000);
        					setTimeout(function(){textFinishs(1);},2500);
        					setTimeout(function(){text(1,"FDL指令测试完成");},4500);
        					setTimeout(function(){textFinishs(2);},5000);
        					setTimeout(function(){text(2,"MLB指令测试完成");},7000);
            				setTimeout(function(){textFinishs(3);},7500);
            				setTimeout(function(){text(3,"RT指令测试完成");},9500);
            				setTimeout(function(){textFinishs(4);},10000);
            				setTimeout(function(){text(4,"DETR指令测试完成");},12000);
            				setTimeout(function(){textFinishs(5);},12500);
            				setTimeout(function(){text(5,"FD指令测试完成");},14500);
            				setTimeout(function(){textFinishs(6);},15000);
        					
        					setTimeout(function(){errCode(6,"FLK P指令测试完成");},15000);
        					setTimeout(function(){errCodeFinishs(data.message);},15000);
        				}else if(code==92){
        					setTimeout(function(){text(0,"SI指令测试完成");},2000);
        					setTimeout(function(){textFinishs(1);},2500);
        					setTimeout(function(){text(1,"FDL指令测试完成");},4500);
        					setTimeout(function(){textFinishs(2);},5000);
        					
        					setTimeout(function(){errCode(2,"MLB指令测试完成");},5000);
        					setTimeout(function(){errCodeFinishs(data.message);},5000);
        				}else if(code==91){
        					setTimeout(function(){text(0,"SI指令测试完成");},2000);
        					setTimeout(function(){textFinishs(1);},2500);
        					setTimeout(function(){text(1,"FDL指令测试完成");},4500);
        					setTimeout(function(){textFinishs(2);},5000);
        					setTimeout(function(){text(2,"MLB指令测试完成");},7000);
            				setTimeout(function(){textFinishs(3);},7500);
        					
        					setTimeout(function(){errCode(3,"RT指令测试完成");},9500);
        					setTimeout(function(){errCodeFinishs(data.message);},9500);
        				}else if(code==50){
        					setTimeout(function(){text(0,"SI指令测试完成");},2000);
        					setTimeout(function(){textFinishs(1);},2500);
        					setTimeout(function(){text(1,"FDL指令测试完成");},4500);
        					setTimeout(function(){$(".tipBox-cont-result").removeAttr("hidden");},5000);
        				}
        			}
        			obtain();
        		}
        	});
        }else if($(this).hasClass("tipBox-clear")){ //关闭关闭测试
            $(".tipBox").animate({
                "opacity":"0"
            },function(){
                $(".tipBox").css({"display":"none"});
            });
            //背景关闭按钮显示
            $(".pla-set-clear").removeAttr("hidden");
        }
    });
    $(".configuration").on("click",function(){//打开采集号配置信息栏目-并且获取数据
//    	addDiv();
        obtain();
    });
    obtain();
    function addDiv(){
//    	var div = "<div class=''><h3 class=''>采集号配置信息：</h3><div class=''>	<span></span><div>新增</div></div></div>"; 
//    	$(".pla-set-interface-box").empty();
//    	$(".pla-set-interface-box").append(div);
//    	$(".pla-set-interface-box div:eq(0)").addClass("collect-configuration");
    	$(".pla-set-interface-box h3").addClass("collect-configuration-title");
    	$(".pla-set-interface-box div:eq(1)").addClass("addBox");
    	$(".pla-set-interface-box span").append("&#xe60d;");
    }
    function adjust(){ //跟新数据-添加节点
        $(".collect-configuration-area").remove();
        var ele="";
        for(var key in aggregate){
            ele+="<div class='collect-configuration-area' tag='"+aggregate[key].user+"'><ul><li><div>配置类型</div><div>中航信eTerm</div><div>eTerm端口</div><div>"+aggregate[key].etermPort+"</div></li><li><div>用户名</div><div>"+aggregate[key].user+"</div><div>si指令</div><div>"+formatSi(aggregate[key].si)+"</div></li><li><div>密码</div><div>"+formatSi(aggregate[key].pas)+"</div><div>自动采集时间（h）</div><div>"+aggregate[key].acquisitionT.time+"</div></li><li><div>工作号</div><div>"+aggregate[key].jobn+"</div><div>冲突等待时间（m）</div><div>"+aggregate[key].conflict+"</div></li><li><div>工作号密码</div> <div>"+formatSi(aggregate[key].jobp)+"</div><div>最后更新时间</div><div>"+aggregate[key].lastTime+"</div></li><li><div>级别</div><div>"+aggregate[key].level+"</div><div>状态</div><div>"+format(aggregate[key].state)+"</div></li><li><div>eTermIP</div><div>"+aggregate[key].etermIp+"</div> <div class='click-btn'><span class='click-btn-test' id='"+aggregate[key].id+"'  >测试连接</span></div></li></ul><div><div class='collect-change'  tag='"+aggregate[key].user+"'>&#xe65a;</div><div class='collect-clear' id='"+aggregate[key].id+"' tag='"+aggregate[key].user+"'>&#xe64e;</div></div></div>";
        }
        $(".collect-configuration-title").after(ele);
    }
    function formatSi(si){
    	return "*********";
    }
	function format(state) {
		if (state == "0") {
			return "请测试";
		}
		if (state == "1") {
			return "正常";
		}
		if (state == "2") {
			return "密码错误";
		}
	}
    /*关闭设置*/
    $(".pla-set-clear").on("click",function(){
        $(".pla-set").css("display","none");
        var map = atten;
    	var tag = true;
    	breaktag:
		for(var i=0;i<map.length;i++){
			var obj = map[i].data;
			for(var j=0;j<obj.length;j++){
				if(obj[j].sle==1){
					tag=false;
					break breaktag;
				}
			}
		}
    	if(tag){
    		$('#allFlights').attr('checked',true);
    		$('.labels').each(function(){
    			var nextText = $(this).next().text();
    			if(nextText=="所有航班"){
    				$(this).addClass('labels-set');
    			}else{
    				$(this).removeClass('labels-set');
    			}
    		});
    	}
    });
    function infer(name){
        var infer=[];
        infer.push(parseFloat($(name).css("width").split("px")[0]));
        infer.push(parseFloat($(name).css("height").split("px")[0]));
        infer.push(parseFloat($(name).css("margin-top").split("px")[0]));
        infer.push(parseFloat($(name).css("left").split("px")[0]));
        infer.push(parseFloat($(name).css("top").split("px")[0]));
        return infer;
    }
    /*是否添加滚动条*/
    function add(idL,idE,speed,sheight){
        if($("#yj-scroll").length==0){
            $(idL).append("<div id='yj-scroll'></div>");
        }
        $("#yj-scroll").css({"border-radius":"2px","position":"absolute","width":"8px","right":"2px","z-index":"999","height":sheight+"px","background-color":"#79b2df"});
        $(idL).on("mousewheel",function(e,delta){
            if(delta=="1") {//上滑
                $(idE).css({"top":"+="+speed+"px"});
                if(infer(idE)[4]>=0){
                    $(idE).css({"top":"0px"});
                }
                var bar=Math.abs(infer(idE)[4])*(infer(idL)[1]-infer("#yj-scroll")[1])/(infer(idE)[1]-infer(idL)[1]);
                $("#yj-scroll").css("top",bar+"px");
            }else { //下滑
                $(idE).css({"top":"-="+speed+"px"});
                if(infer(idE)[4]<=-(infer(idE)[1]-infer(idL)[1])){
                    $(idE).css({"top":-(infer(idE)[1]-infer(idL)[1])+"px"});
                }
                var bar=Math.abs(infer(idE)[4])*(infer(idL)[1]-infer("#yj-scroll")[1])/(infer(idE)[1]-infer(idL)[1]);
                $("#yj-scroll").css("top",bar+"px");
            }
        });
        $("#yj-scroll").on("mousedown",function(b){
            var H=b.screenY;
            var ct=infer("#yj-scroll")[4];
            $(".pla-set").mousemove(function(e){
                var top= e.screenY;
                if(H>top){
                    var tops=ct-Math.abs(H-top);
                    if(tops<=0){
                        $("#yj-scroll").css("top","0px");
                    }else {
                        $("#yj-scroll").css("top",tops+"px");
                    }
                    var xt=infer("#yj-scroll")[4]*(infer(idE)[1]-infer(idL)[1])/(infer(idL)[1]-infer("#yj-scroll")[1]);
                    $(".pla-set-interface-box").css("top",-xt+"px");
                }else {
                    var tops=ct+Math.abs(H-top);
                    if(tops>=(infer(idL)[1]-infer("#yj-scroll")[1])){
                        $("#yj-scroll").css("top",(infer(idL)[1]-infer("#yj-scroll")[1])+"px");
                    }else {
                        $("#yj-scroll").css("top",tops+"px");
                    }
                    var xt=infer("#yj-scroll")[4]*(infer(idE)[1]-infer(idL)[1])/(infer(idL)[1]-infer("#yj-scroll")[1]);
                    $(".pla-set-interface-box").css("top",-xt+"px");
                }
            });

        });
        $(".pla-set").on("mouseup",function(){
            $(".pla-set").unbind("mousemove");
        });
    };
    add(".pla-set-interface",".pla-set-interface-box",50,100);
//    $(".sSettings").click();

    /******************个人中心*************************/
    /* 绑定手机号码 */
    $(".personal-information-itemBind3").on("click",function(){

    });

    $(".personal-center-setBth").on("click",function(){
        $(".personal-information-itemBind3").css("display","block");
        $(".personal-information-must").css("display","block");
        $(".information-qe").css("display","block");
        $(".information-of").css("display","block");
        $(".personal-information-item2Bind>input").removeAttr("disabled");
        $(".personal-information-item2Bind").css("background-color","#1b345c");
        $(".personal-center-setBth").css("display","none");
    });
    $(".information-of").on("click",function(){
    	setData();
        $(".personal-information-itemBind3").css("display","none");
        $(".personal-information-must").css("display","none");
        $(".information-qe").css("display","none");
        $(".information-of").css("display","none");
        $(".personal-information-item2Bind>input").attr("disabled","disabled");
        $("#phone").attr("disabled","disabled");
        $("#email").attr("disabled","disabled");
        $(".personal-information-item2Bind").css("background-color","transparent");
        $(".personal-center-setBth").css("display","block");
    });
    $(".information-of").click();
    $(".information-qe").on("click",function(){ // 上传个人信息数据
        if($("#usrNm").val()!=""&&$("#compellation").val()!=""){
//        	var user = {};
        	var usrNm = $('#usrNm').val();
        	var compellation = $('#compellation').val();
            var departmentId = $("._set-list-title").attr('abbr');
        	var phone = $("#phone").val();
//            var user.weixin = $("#weixin").val();
//            var user.qqNbr = $("#qqNbr").val();
        	var duty = $('#duty').val();
            var email = $("#email").val();
        	$.ajax({
        		url:'/employee_update',
//        		data:user,
        		data:{
        			usrNm:usrNm,
        			compellation:compellation,
        			departmentId:departmentId,
        			phone:phone,
        			duty:duty,
        			email:email
        		},
        		type:'post',
        		dataType:'jsonp',
        		success:function(data){
        			if(data.success.opResult=="0"){
        				$(".personal-information-itemBind3").css("display","none");
        		        $(".personal-information-must").css("display","none");
        		        $(".information-qe").css("display","none");
        		        $(".information-of").css("display","none");
        		        $(".personal-information-item2Bind>input").attr("disabled","disabled");
        		        $(".personal-information-item2Bind").css("background-color","transparent");
        		        $(".personal-center-setBth").css("display","block");
        			}else{
        				alert(data.success.message);
        			}
        		}
        	});
        }else {
            $(".determine-tip").css("display","block");
            setTimeout(function(){
                $(".determine-tip").css("display","none");
            },1000);
        }
    });
    function validation(pwd){
        $.ajax( {
            type:'post',
            url:'/employee_valid',
            dataType:'jsonp',
            data:{
                pwd:pwd
            },
            success:function(data) {
                if(data.success.opResult=="0"){
                    pwdState(1);
                }else if(data.success.opResult=="1"){
                    pwdState(0);
                }/*else if(data.success.opResult=="2"){
                	pwdState(2);
                }else if(data.success.opResult=="3"){
                	pwdState(3);
                }else if(data.success.opResult=="4"){
                	pwdState(4);
                }*/
                
            }
        });
    }
    var frequency=0;
    function pwdState(num){
        if(num==1){
            pasCounter();
            //$(".passwordSuccessful").css("display","block")
        }else if(num==0){
            $(".plales-warning-cont").text("密码错误");
            $(".plales-warning").css("display","inline-block");
            $("#verificatio").click();
            frequency+=1;
            setTimeout(function(){
                frequency=0;
            },10000);
        }/*else if(num==2){
        	$(".plales-warning-cont").text("服务器繁忙");
            $(".plales-warning").css("display","inline-block");
            $("#verificatio").click();
            frequency+=1;
            setTimeout(function(){
                frequency=0;
            },10000);
        }else if(num==3){
        	$(".plales-warning-cont").text("请输入密码");
            $(".plales-warning").css("display","inline-block");
            $("#verificatio").click();
            frequency+=1;
            setTimeout(function(){
                frequency=0;
            },10000);
        }else if(num==4){
        	$(".plales-warning-cont").text("新旧密码很相似");
            $(".plales-warning").css("display","inline-block");
            $("#verificatio").click();
            frequency+=1;
            setTimeout(function(){
                frequency=0;
            },10000);
        }*/
    };
    $(".cPBoxItm-pas").on("click",function(){
        $(".personal-center-cPBoxItm").css("display","block");
        $(".changePas").click();
    });
    $(".barMove").on("click",function(){
        if($(this).hasClass("userCenter")){
            $(".pla-set-interface-box").css("top","0px");
        }else if($(this).hasClass("changePas")){
            $(".pla-set-interface-box").css("top","-355px");
        }else if($(this).hasClass("fltSet")){
        	$(".pla-set-interface-box").css("top","-730px");
        }else if($(this).hasClass("etermInfo")){
        	$(".pla-set-interface-box").css("top","-1430px");
        }
    });
    /*还原样式*/
    function restore(){
        $(".plales-t-tip").css("display","inline");
        $(".plales-input").css("display","block");
        $(".newPassword").css("display","none");
        $(".cPBox-pas").attr("tag","").text("下一步");
        $(".cPBox-pas-input").val("");
        $(".Password-input").val("");
        $(".verificatio-input").val("");
        $(".passwordSuccessful").css("display","none");
        $(".plales-input-verificatio").css("display","none");
    }
    /*打开修改密码界面*/
    function pasCounter(){
        $(".cPBox-pas").attr("tag","determine");
        $(".plales-input").css("display","none");
        $(".plales-t-tip").css("display","none");
        $(".plales-input-verificatio").css("display","none");
        $(".newPassword").css("display","block");
        $(".cPBox-pas").text("确定");
    };
    /*请求数据修改密码*/
    function upPwd(){
        $.ajax( {
            type:'post',
            url:'employee_upwd',
            dataType:'jsonp',
            data:{
                usrPwd:$(".Password-input").val()
            },
            success:function(data) {
                if(data.success.opResult=="0"){
                    $(".passwordSuccessful").css("display","block");
                    var nu=2;
                    setInterval(function(){
                        if(nu<=0){
                            location.href = "/outLogin";
                        }else {
                            $("#insevr").text(nu);
                        };
                        nu--;
                    },1000);
                }else if(data.success.opResult=="2"){
                	$('.plales-warning-cont').text("服务器异常");
                    $(".plales-warning").css("display","block");
                    $("#verificatio").click();
                    frequency+=1;
                    setTimeout(function(){
                        frequency=0;
                    },10000);
                }else if(data.success.opResult=="3"){
                	$('.plales-warning-cont').text("请输入新密码");
                    $(".plales-warning").css("display","inline-block");
                    $("#verificatio").click();
                    frequency+=1;
                    setTimeout(function(){
                        frequency=0;
                    },10000);
                }else if(data.success.opResult=="4"){
                	$('.plales-warning-cont').text("新旧密码相似");
                    $(".plales-warning").css("display","inline-block");
                    $("#verificatio").click();
                    frequency+=1;
                    setTimeout(function(){
                        frequency=0;
                    },10000);
                }
            },
            error:function() {

            }
        });
    };
    $(".Password-input").on('input',function(){
        $(".Password-icon").css("display","none");
        var str=$(this).val();
        var patt=/^[0-9a-zA-Z]*$/g;
        if(patt.test(str)==false){
            $('.plales-warning-cont').text("密码格式错误");
            $(".plales-warning").css("display","inline-block");
            setTimeout(function(){
                $(".plales-warning").css("display","none");
            },1000);
        }else {
            if($(".Password-input").index(this)==1){
                if($(".Password-input").eq(0).val().split("").length<6){
                    $('.plales-warning-cont').text("密码长度不能小于6位");
                    $(".plales-warning").css("display","inline-block");
                    setTimeout(function(){
                        $(".plales-warning").css("display","none");
                    },1000);
                }else if($(".Password-input").eq(0).val().split("").length>20){
                    $('.plales-warning-cont').text("密码长度不能大于20位");
                    $(".plales-warning").css("display","inline-block");
                    setTimeout(function(){
                        $(".plales-warning").css("display","none");
                    },1000);
                }else {
                    if($(".Password-input").eq(0).val()==$(".Password-input").eq(1).val()){
                        $(".Password-icon").css("display","block");
                    }
                    //else {
                    //    $('.plales-warning-cont').text("两次密码输入不一致，请从新输入");
                    //    $(".plales-warning").css("display","inline-block");
                    //    setTimeout(function(){
                    //        $(".plales-warning").css("display","none")
                    //    },1000);
                    //}
                }
            };
        }
    });
    $(".cPBox-pas").on("click",function(){
        if( $(".cPBox-pas").attr("tag")=="determine"){
            if($(".Password-icon").css("display")=="block"){
                //上传-成功
                upPwd($(".Password-input").eq(1).val());
            }else {
                if($(".Password-input").eq(0).val()!=""&&$(".Password-input").eq(1).val()!=""){
                	var str=$(".Password-input").eq(0).val();
                	var str1=$(".Password-input").eq(1).val();
                    var patt=/^[0-9a-zA-Z]*$/g;
                    var patt1=/^[0-9a-zA-Z]*$/g;
                    if(str.split("").length>20||str1.split("").length>20){
                        $('.plales-warning-cont').text("密码长度不能大于20位");
                        $(".plales-warning").css("display","inline-block");
                        setTimeout(function(){
                            $(".plales-warning").css("display","none");
                        },1000);
                    } else if(str.split("").length<6||str1.split("").length<6){
                        $('.plales-warning-cont').text("密码长度不能小于6位");
                        $(".plales-warning").css("display","inline-block");
                        setTimeout(function(){
                            $(".plales-warning").css("display","none");
                        },1000);
                    }else if(patt.test(str)==false||patt1.test(str1)==false){
                        $('.plales-warning-cont').text("密码格式错误");
                        $(".plales-warning").css("display","inline-block");
                        setTimeout(function(){
                            $(".plales-warning").css("display","none");
                        },1000);
                    }else {
                        $('.plales-warning-cont').text("两次密码输入不一致，请从新输入");
                        $(".plales-warning").css("display","inline-block");
                        setTimeout(function(){
                            $(".plales-warning").css("display","none");
                        },1000);
                    }
                }else {
                    $('.plales-warning-cont').text("密码不能为空！");
                    $(".plales-warning").css("display","inline-block");
                    setTimeout(function(){
                        $(".plales-warning").css("display","none");
                    },1000);
                }
            }
        }else {
            if($(".cPBox-pas-input").val()!=""){
                if(frequency>=3){
                    $(".personal-center-cPBoxItm").css({"height":"255px"});
                    $(".plales-input-verificatio").css("display","block");
                    if($(".verificatio-input").val()==""){
                        $(".plales-warning-cont").text("请输入验证码");
                        $(".plales-warning").css("display","inline-block");
                    }else {
                        if(authCode==$(".verificatio-input").val()){
                            validation($(".cPBox-pas-input").val());
                        }else{
                            $(".plales-warning-cont").text("验证码错误");
                            $(".verificatio-input").val("");
                            $(".plales-warning").css("display","inline-block");

                            $("#verificatio").click();
                        }
                    }
                }else {
                    $(".personal-center-cPBoxItm").css({"height":"237px"});
                    validation($(".cPBox-pas-input").val());
                }
            }else {
                $(".plales-warning-cont").text("密码不能为空");
                $(".plales-warning").css("display","inline-block");
            }
        }
        setTimeout(function(){
            $(".plales-warning").css("display","none");
        },1000);
    });
    /*取消修改*/
    $('.c-recovery').on('click',function(){
        $('.personal-center-cPBoxItm').css("display","none");
        restore();
    });
    
    $("#flightC").on("mouseover","div",function(){
        if($(this).hasClass("data-set-mol")&&$("#setUp").css("display")=="none"){
        	if(!$(this).hasClass("data-set-green")){
        		$(this).addClass("data-set-gray");
                $(this).find("span").addClass("data-icon-gray");
        	}
        }
    });
    $("#flightC").on("mouseout","div",function(){
        if($(this).hasClass("data-set-mol")&&$("#setUp").css("display")=="none"){
        	if($(this).hasClass("data-set-gray")){
        		$(this).removeClass("data-set-gray");
                $(this).find("span").removeClass("data-icon-gray");
        	}
        }
    });
    //翻页按钮
    $("#paging").on("click",'.move-page-z',function(){
    	var curval = parseInt($(this).text());
//    	//当在关注航班页面进行翻页时，将原有页面中的内容叠加-start
//    	if(!$('#attentionAll').is(':checked')){
//    		//获取当前取消关注航班所在航段
//			var fltLeg = [];
//    		$('.flightC-line').each(function(){
//    			var fltAroute = $(this).find('.flightC-t').text();
//    			if(fltStr!=''&&fltStr.indexOf(fltAroute)==-1){
//    				fltLeg.push(fltAroute);
//            		fltStr += fltAroute+":";
//            		for(var i=0;i<$(this).find(".data-set-green").length;i++){
//            			if(i==0){
//            				fltStr += $($(this).find(".data-set-green")[i]).find(".data-value").text();
//            			}else{
//            				fltStr += ","+$($(this).find(".data-set-green")[i]).find(".data-value").text();
//            			}
//            		}
//            		fltStr +=";";
//    			}else if(fltStr==''){
//    				fltLeg.push(fltAroute);
//            		fltStr += fltAroute+":";
//            		for(var i=0;i<$(this).find(".data-set-green").length;i++){
//            			if(i==0){
//            				fltStr += $($(this).find(".data-set-green")[i]).find(".data-value").text();
//            			}else{
//            				fltStr += ","+$($(this).find(".data-set-green")[i]).find(".data-value").text();
//            			}
//            		}
//            		fltStr +=";";
//    			}
//        	});
//    	}
    	//当在关注航班页面进行翻页时，将原有页面中的内容叠加-end
        if(curval-parseInt($('#move-page-first').text())>1){
        	$('#move-page-t').css('display','block');
        	if(parseInt($('#move-page-last').text())-curval>2){
        		$(".move-page-z").removeClass("move-page-w");
        		$("#move-page-be").addClass("move-page-w");
        		$('#move-page-b').css('display','block');
        		$('#move-page-be').text(curval);
    			$('#move-page-md').text(curval+1);
        	}else{
        		$('#move-page-b').css('display','none');
        		if(parseInt($('#move-page-last').text())-curval==2){
        			$(".move-page-z").removeClass("move-page-w");
        	        $('#move-page-be').addClass("move-page-w");
        	        $('#move-page-be').text(curval);
        			$('#move-page-md').text(curval+1);
        		}else if(parseInt($('#move-page-last').text())-curval==1){
        			$(".move-page-z").removeClass("move-page-w");
        	        $(this).addClass("move-page-w");
        		}else if(parseInt($('#move-page-last').text())-curval==0){
        			$(".move-page-z").removeClass("move-page-w");
        	        $(this).addClass("move-page-w");
        		}
        	}
        }else{
          $(".move-page-z").removeClass("move-page-w");
          $(this).addClass("move-page-w");
        	$('#move-page-t').css('display','none');
        	if(parseInt($(this).text())==1){
        		if(parseInt($('#move-page-last').text())-curval>3){
        			$('#move-page-b').css('display','block');
        			$('#move-page-be').text(2);
        			$('#move-page-md').text(3);
            	}
        	}else{
        		if(parseInt($('#move-page-last').text())-curval>2){
        			$('#move-page-b').css('display','block');
        			$('#move-page-be').text(2);
        			$('#move-page-md').text(3);
            	}
        	}
        }
        attention(curval-1);
    });
    $("#paging").on("click",'#move-page-left',function(){
    	if(parseInt($('#move-page-last').text())-parseInt($('.move-page-w').text())==0){
    		$(".move-page-z").removeClass("move-page-w");
            $('#move-page-md').addClass("move-page-w");
            $('#move-page-md').click();
    	}else if(parseInt($('#move-page-last').text())-parseInt($('.move-page-w').text())==1){
    		$(".move-page-z").removeClass("move-page-w");
            $('#move-page-be').addClass("move-page-w");
            $('#move-page-be').click();
    	}else if(parseInt($('#move-page-last').text())-parseInt($('.move-page-w').text())==2){
    		$('#move-page-b').css('display','block');
    		$("#move-page-be").text(parseInt($("#move-page-be").text())-1);
            $("#move-page-md").text(parseInt($("#move-page-md").text())-1);
            $('#move-page-be').click();
    	}else if(parseInt($('#move-page-last').text())-parseInt($('.move-page-w').text())>2){
    		if(parseInt($('.move-page-w').text())>3){
    			$("#move-page-be").text(parseInt($("#move-page-be").text())-1);
                $("#move-page-md").text(parseInt($("#move-page-md").text())-1);
                $('#move-page-be').click();
    		}else if(parseInt($('.move-page-w').text())==3){
    			$('#move-page-t').css('display','none');
    			$("#move-page-be").text(parseInt($("#move-page-be").text())-1);
                $("#move-page-md").text(parseInt($("#move-page-md").text())-1);
                $('#move-page-be').click();
    		}else if(parseInt($('.move-page-w').text())==2){
    			$('.move-page-z').removeClass('move-page-w');
    			$('#move-page-first').addClass('move-page-w');
                $('#move-page-first').click();
    		}
    	}
    });
    $("#paging").on("click",'#move-page-right',function(){
    	if(parseInt($(".move-page-w").text())==1){
    		$(".move-page-z").removeClass("move-page-w");
            $('#move-page-be').addClass("move-page-w");
            $("#move-page-be").click();
    	}else if(parseInt($(".move-page-w").text())==2){
    		$("#move-page-t").css("display","block");
    		$("#move-page-be").text(parseInt($("#move-page-be").text())+1);
            $("#move-page-md").text(parseInt($("#move-page-md").text())+1);
            $("#move-page-be").click();
    	}else{
    		if(parseInt($('#move-page-last').text())-parseInt($(".move-page-w").text())>3){
    			$("#move-page-be").text(parseInt($("#move-page-be").text())+1);
                $("#move-page-md").text(parseInt($("#move-page-md").text())+1);
                $("#move-page-be").click();
    		}else if(parseInt($('#move-page-last').text())-parseInt($(".move-page-w").text())==3){
    			$("#move-page-b").css("display","none");
    			$("#move-page-be").text(parseInt($("#move-page-be").text())+1);
                $("#move-page-md").text(parseInt($("#move-page-md").text())+1);
                $("#move-page-be").click();
    		}else if(parseInt($('#move-page-last').text())-parseInt($(".move-page-w").text())==2){
    			$(".move-page-z").removeClass("move-page-w");
                $('#move-page-md').addClass("move-page-w");
                $("#move-page-md").click();
    		}else if(parseInt($('#move-page-last').text())-parseInt($(".move-page-w").text())==1){
    			$(".move-page-z").removeClass("move-page-w");
                $('#move-page-last').addClass("move-page-w");
                $("#move-page-last").click();
    		}
    	}
    });
    $('.data-set-r').on('click','.data-set-gray',function(){
    	var total = 0;
    	var totalchecked = 0;
    	var line = $(this).parent().prev().text();
    	if(changeAtten!=null&&changeAtten.length>0){
    		for(var i=0;i<changeAtten.length;i++){
    			var list = changeAtten[i].data;
    			total += list.length;
    			if(line==changeAtten[i].line){
    				for(var j=0;j<list.length;j++){
    					if(list[j].number==$(this).find('.data-value').text()){
    						list[j].sle=1;
    						totalchecked++;
    						if(!$('#attentionAll').is(":checked")){
    							if(!$('#attentionAll').prop('indeterminate')){
    								$('#attentionAll').prop('indeterminate',true);
    							}
    						}
    					}else{
    						if(list[j].sle==1){
    							totalchecked++;
    						}
    					}
    				}
    				changeAtten[i].data = list;
    			}else{
    				for(var k=0;k<list.length;k++){
    					if(list[k].sle==1){
    						totalchecked++;
    					}
    				}
    			}
    		}
    		//判断是否已经全部选中
    		if(total==totalchecked){
    			$('#attentionAll').removeAttr('indeterminate');
    			$('#attentionAll').attr('checked',true);
    		}
    	}
    	$(this).removeClass('data-set-gray');
    	$(this).find("span").removeClass('data-icon-gray');
        $(this).addClass("data-set-green");
        $(this).find("span").addClass("data-icon-green");
    });
    $('.data-set-r').on('click','.data-set-green',function(){
    	var total = 0;
    	var totalCheck = 0;
    	var line = $(this).parent().prev().text();
    	if(changeAtten!=null&&changeAtten.length>0){
    		for(var i=0;i<changeAtten.length;i++){
    			var list = changeAtten[i].data;
    			total+=list.length;
    			if(line==changeAtten[i].line){
    				for(var j=0;j<list.length;j++){
    					if(list[j].number==$(this).find('.data-value').text()){
    						list[j].sle=0;
    						if($('#attentionAll').attr('checked')){
    							$('#attentionAll').attr('checked',false).prop('indeterminate',true);
    						}
    					}else{
    						if(list[j].sle==1){
    							totalCheck++;
    						}
    					}
    				}
    				changeAtten[i].data = list;
    			}else{
    				for(var k=0;k<list.length;k++){
    					if(list[k].sle==1){
    						totalCheck++;
						}
    				}
    			}
    		}
    		if(totalCheck==0){
    			if(Odata.success.airportFocus.flightRange=="关注航班"){//在关注航班数据查看时，必须有一个航班备关注
    				alert('必须要关注一个航班号才满足要求');
    				for(var i=0;i<changeAtten.length;i++){
    	    			var list = changeAtten[i].data;
    	    			if(line==changeAtten[i].line){
    	    				for(var j=0;j<list.length;j++){
    	    					if(list[j].number==$(this).find('.data-value').text()){
    	    						list[j].sle=1;
    	    					}
    	    				}
    	    				changeAtten[i].data = list;
    	    				break;
    	    			}
    	    		}
    				return;
    			}else{
    				$('#attentionAll').removeProp('indeterminate').attr('checked',false);
    			}
    		}
    	}
    	$(this).removeClass("data-set-green");
        $(this).find("span").removeClass("data-icon-green");
        $(this).addClass("data-set-gray");
        $(this).find("span").addClass("data-icon-gray");
    });
    $(".rHandle-icon2").on("click",function(){  //打开设置关注航班
        $("#setUp").css({"display":"none"});
        $("#setClera").css({"display":"block"});
        $("#page-select").css({"display":"block"});
        $(".data-icon-green").parent().addClass("data-set-green");
    });
    $("#page-select-f").on("click",function(){
    	changeAtten = atten;//将变更的数据还原
    	var total = 0;
    	var totalChecked = 0;
    	for(var i=0;i<changeAtten.length;i++){
    		var list = changeAtten[i].data;
    		total += list.length;
    		for(var j=0;j<list.length;j++){
    			if(list[j].sle==1){
    				totalChecked++;
    			}
    		}
    	}
    	if(total==totalChecked){
    		$('#attentionAll').removeProp('indeterminate').attr('checked',true);
    	}else{
    		if(total==0){
    			$('#attentionAll').removeProp('indeterminate').attr('checked',false);
    		}else{
    			$('#attentionAll').prop('indeterminate',true);
    		}
    	}
        $("#setUp").css({"display":"block"});
        $("#setClera").css({"display":"none"});
        $("#page-select").css({"display":"none"});
        //以下代码可能会变化
//        $('.data-set-mol').each(function(){
//        	if($(this).attr('tag')==1){
//        		$(this).removeClass("data-set-green");
//        		if(!$(this).find('span').hasClass('data-icon-green')){
//        			$(this).find('span').addClass('data-icon-green');
//        		}
//        	}else{
//        		$(this).removeClass("data-set-green");
//        		$(this).find('span').removeClass('data-icon-green');
//        	}
//        });
        attention(parseInt($('.move-page-w').text()));
    });
    $("#page-select-t").on('click',function(){
    	var page = $('.move-page-w').text();
    	$('.pla-ranking-shut').click();//关闭右边弹出框
//    	var flag = $('#attentionAll').is(':checked');
    	var fltStr = '';
    	for(var j=0;j<atten.length;j++){
			var list = atten[j].data;
			fltStr += atten[j].line+":";
			for(var i=0;i<list.length;i++){
				if(list[i].sle==1){
					if(i==0){
						fltStr += list[i].number;
					}else{
						if(fltStr.endWith(':')){//该航线第一个关注航班号
							fltStr += list[i].number;
						}else{
							fltStr += ","+list[i].number;
						}
					}
				}
			}
			fltStr += ";";
		}
    	$.ajax({
    		url:'/updateFocusFltList',
    		type:'post',
    		data:{
    			fltStr:fltStr
    		},
    		dataType:'jsonp',
    		success:function(data){
    			if(data.success!=null&&data.success.opResult=='0'){
    				atten = changeAtten;//重置atten
//    				getCurrentSeasonFocusFlts(1);
    				$("#setUp").css({"display":"block"});
    		        $("#setClera").css({"display":"none"});
    		        $("#page-select").css({"display":"none"});
    		        attention(page-1);
    		        $('.data-set-mol').each(function(){
    		        	if($(this).attr('tag')==1){
    		        		$(this).removeClass("data-set-green");
    		        	}else{
    		        		$(this).removeClass("data-set-green");
    		        		$(this).find('span').removeClass('data-icon-green');
    		        	}
    		        });
    		        $('.indicators-bth-t').click();
    		        getAirportKPIData();
    			}
    		}
    	});
    });
    $('#setClera').on('click',function(){
    	var flag = $('#attentionAll').is(':checked')==true;
    	if(flag==true){
    		//将对象中的标志更改为1
    		for(var i=0;i<changeAtten.length;i++){
    			var list = changeAtten[i].data;
    			for(var j=0;j<list.length;j++){
    				list[j].sle=1;
    			}
    			changeAtten[i].data=list;//重新赋值
    		}
    		$('.data-set-mol').each(function(){
    			if(!$(this).hasClass("data-set-green")){
        			$(this).addClass('data-set-green');
        			$(this).find('.data-icon').addClass('data-icon-green');
        		}
    		});
    	}else{
    		//将对象中的标志更改为1
    		for(var i=0;i<changeAtten.length;i++){
    			var list = changeAtten[i].data;
    			for(var j=0;j<list.length;j++){
    				list[j].sle=0;
    			}
    			changeAtten[i].data=list;//重新赋值
    		}
    		$('.data-set-green').removeClass('data-set-green');
    		$('.data-icon-green').removeClass('data-icon-green');
    	}
    });
    $("#flightC").on("click","div",function(){
        if($(this).hasClass("data-set-mol")&&$("#setUp").css("display")=="none"){
            
        }
    });
    /*滚动到适应位置*/
    function barMove(){

    }
});
function chooseDpt(list,obj){// data-/list节点集合  summary-/是否包含往返 true包含false不包含 name-/添加list节点 cleName-/取消绑定事件函数节点
   var nums="";
   if(list.data.length>0){
	   nums+="<div class='_set-list-title' abbr="+obj.departmentId+">"+obj.department.dptNm+"</div><div class='_set-allList'>";
	   for(var i=0;i<list.data.length;i++){
		   nums+="<div abbr="+list.data[i].id+">"+list.data[i].dptNm+"</div>";
	   }
	   $(list.name).html(nums);
	   $("._set-list-title").on("click",function(event){
		   if($('.personal-center-setBth').css("display")=="none"){
			   $('.c-selectCityBox').remove();//关闭机场选择框
			   $('.opensright').css('display','none');
			   event.stopPropagation();
			   if( $("._set-allList").css("display")=="none"){
				   $("._set-allList").css({"display":"block"});
			   }else {
				   $("._set-allList").css({"display":"none"});
			   } 
		   }
	   });
	   $("body").on("click",'._set-allList>div',function(event){
		   event.stopPropagation();
		   $("._set-list-title").attr("abbr",$(this).attr("abbr"));
		   $("._set-list-title").text($(this).text());
		   $("._set-allList").css("display","none");
		   list.fun();
	   });
	   $(list.cleName).on("click",function(){
		   $("._set-allList").css({"display":"none"});
	   });
   }else{
	   nums+="<div class='_set-list-title'></div>";
	   $(list.name).html(nums); 
   }
}

function attention(nus){
    var dats="";
    if($('#attentionAll').is(":checked")){
    	if(atten.length>16){
            $("#fixed-page").css("display","none");
            $("#move-page").css("display","block");
            if((atten.length)%4==0){
                $("#move-page-last").text(atten.length/4);
            }else{
                $("#move-page-last").text(parseInt((atten.length/4))+1);
            }
            if((nus*4+4)<=atten.length){
                for(var i=nus*4;i<(nus*4+4);i++){
                    dats+="<div class='flightC-line'><p class='flightC-t'>"+atten[i].line+"</p><div class='data-set-box'>";
                    for(var j=0;j<atten[i].data.length;j++){
                        if(atten[i].data[j].sle==1){
                            dats+="<div class='data-set-mol' tag='1'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon data-icon-green'>&#xe661;</span></div>";
                        }else {
                            dats+="<div class='data-set-mol' tag='0'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon data-icon-green'>&#xe661;</span></div>";
                        }
                    }
                    dats+="</div></div>";
                }
            }else {
                for(var i=nus*4;i<(nus*4+(atten.length%4));i++){
                    dats+="<div class='flightC-line'><p class='flightC-t'>"+atten[i].line+"</p><div class='data-set-box'>";
                    for(var j=0;j<atten[i].data.length;j++){
                        if(atten[i].data[j].sle==1){
                            dats+="<div class='data-set-mol' tag='1'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon data-icon-green'>&#xe661;</span></div>";
                        }else {
                            dats+="<div class='data-set-mol' tag='0'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon data-icon-green'>&#xe661;</span></div>";
                        }
                    }
                    dats+="</div></div>";
                }
            }
        }else{
            $("#fixed-page").css("display","block");
            $("#move-page").css("display","none");
            if(nus*4+4<=atten.length){
            	for(var i=nus*4;i<(nus*4+4);i++){
                    dats+="<div class='flightC-line'><p class='flightC-t'>"+atten[i].line+"</p><div class='data-set-box'>";
                    for(var j=0;j<atten[i].data.length;j++){
                        if(atten[i].data[j].sle==1){
                            dats+="<div class='data-set-mol' tag='1'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon data-icon-green'>&#xe661;</span></div>";
                        }else {
                            dats+="<div class='data-set-mol' tag='0'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon data-icon-green'>&#xe661;</span></div>";
                        }
                    }
                    dats+="</div></div>";
                }
            }else{
            	for(var i=nus*4;i<(nus*4+atten.length%4);i++){
                    dats+="<div class='flightC-line'><p class='flightC-t'>"+atten[i].line+"</p><div class='data-set-box'>";
                    for(var j=0;j<atten[i].data.length;j++){
                        if(atten[i].data[j].sle==1){
                            dats+="<div class='data-set-mol' tag='1'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon data-icon-green'>&#xe661;</span></div>";
                        }else {
                            dats+="<div class='data-set-mol' tag='0'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon data-icon-green'>&#xe661;</span></div>";
                        }
                    }
                    dats+="</div></div>";
                }
            }
        }
    }else{
    	if(atten.length>16){
            $("#fixed-page").css("display","none");
            $("#move-page").css("display","block");
            if((atten.length)%4==0){
                $("#move-page-last").text(atten.length/4);
            }else{
                $("#move-page-last").text(parseInt((atten.length/4))+1);
            }
            if((nus*4+4)<=atten.length){
                for(var i=nus*4;i<(nus*4+4);i++){
                	var fltline = atten[i].line;
                	var fltss = fltline.split('=');
                	if(fltss.length==2){
                		dats+="<div class='flightC-line'>"+airportMap[fltss[0]].ctyChaNam+"="+airportMap[fltss[1]].ctyChaNam+"<p class='flightC-t'>"+fltline+"</p><div class='data-set-box'>";
                	}else if(fltss.length==3){
                		dats+="<div class='flightC-line'>"+airportMap[fltss[0]].ctyChaNam+"="+airportMap[fltss[1]].ctyChaNam+"="+airportMap[fltss[2]].ctyChaNam+"<p class='flightC-t'>"+fltline+"</p><div class='data-set-box'>";
                	}
                    for(var j=0;j<atten[i].data.length;j++){
                        if(atten[i].data[j].sle==1){
                            dats+="<div class='data-set-mol' tag='1'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon data-icon-green'>&#xe661;</span></div>";
                        }else {
                            dats+="<div class='data-set-mol' tag='0'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon'>&#xe661;</span></div>";
                        }
                    }
                    dats+="</div></div>";
                }
            }else {
                for(var i=nus*4;i<(nus*4+(atten.length%4));i++){
                	var fltline = atten[i].line;
                	var fltss = fltline.split('=');
                	if(fltss.length==2){
                		dats+="<div class='flightC-line'>"+airportMap[fltss[0]].ctyChaNam+"="+airportMap[fltss[1]].ctyChaNam+"<p class='flightC-t'>"+fltline+"</p><div class='data-set-box'>";
                	}else if(fltss.length==3){
                		dats+="<div class='flightC-line'>"+airportMap[fltss[0]].ctyChaNam+"="+airportMap[fltss[1]].ctyChaNam+"="+airportMap[fltss[2]].ctyChaNam+"<p class='flightC-t'>"+fltline+"</p><div class='data-set-box'>";
                	}
                    for(var j=0;j<atten[i].data.length;j++){
                        if(atten[i].data[j].sle==1){
                            dats+="<div class='data-set-mol' tag='1'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon data-icon-green'>&#xe661;</span></div>";
                        }else {
                            dats+="<div class='data-set-mol' tag='0'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon'>&#xe661;</span></div>";
                        }
                    }
                    dats+="</div></div>";
                }
            }
        }else{
            $("#fixed-page").css("display","block");
            $("#move-page").css("display","none");
            if(nus*4+4<=atten.length){
            	for(var i=nus*4;i<(nus*4+4);i++){
            		var fltline = atten[i].line;
                	var fltss = fltline.split('=');
                	if(fltss.length==2){
                		dats+="<div class='flightC-line'>"+airportMap[fltss[0]].ctyChaNam+"="+airportMap[fltss[1]].ctyChaNam+"<p class='flightC-t'>"+fltline+"</p><div class='data-set-box'>";
                	}else if(fltss.length==3){
                		dats+="<div class='flightC-line'>"+airportMap[fltss[0]].ctyChaNam+"="+airportMap[fltss[1]].ctyChaNam+"="+airportMap[fltss[2]].ctyChaNam+"<p class='flightC-t'>"+fltline+"</p><div class='data-set-box'>";
                	}
                    for(var j=0;j<atten[i].data.length;j++){
                        if(atten[i].data[j].sle==1){
                            dats+="<div class='data-set-mol' tag='1'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon data-icon-green'>&#xe661;</span></div>";
                        }else {
                            dats+="<div class='data-set-mol' tag='0'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon'>&#xe661;</span></div>";
                        }
                    }
                    dats+="</div></div>";
                }
            }else{
            	for(var i=nus*4;i<(nus*4+atten.length%4);i++){
            		var fltline = atten[i].line;
                	var fltss = fltline.split('=');
                	if(fltss.length==2){
                		dats+="<div class='flightC-line'>"+airportMap[fltss[0]].ctyChaNam+"="+airportMap[fltss[1]].ctyChaNam+"<p class='flightC-t'>"+fltline+"</p><div class='data-set-box'>";
                	}else if(fltss.length==3){
                		dats+="<div class='flightC-line'>"+airportMap[fltss[0]].ctyChaNam+"="+airportMap[fltss[1]].ctyChaNam+"="+airportMap[fltss[2]].ctyChaNam+"<p class='flightC-t'>"+fltline+"</p><div class='data-set-box'>";
                	}
                    for(var j=0;j<atten[i].data.length;j++){
                        if(atten[i].data[j].sle==1){
                            dats+="<div class='data-set-mol' tag='1'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon data-icon-green'>&#xe661;</span></div>";
                        }else {
                            dats+="<div class='data-set-mol' tag='0'><div class='data-value'>"+atten[i].data[j].number+"</div><span class='data-icon'>&#xe661;</span></div>";
                        }
                    }
                    dats+="</div></div>";
                }
            }
        }
    }
    $("#flightC").html(dats);
    if($("#setUp").css("display")=="none"){
    	$(".rHandle-icon2").click();
    }
}

function getCurrentSeasonFocusFlts(type){
//	var currentDate = new Date();
//	var currentTime = currentDate.getTime();
//	var year = currentDate.getFullYear();
//	var prevDate = new Date((year-1)+'-10-31').getDay();
//	var startDate = new Date(year+'-03-31').getDay();
//	var endDate = new Date(year+'-10-31').getDay();
//	//获取当前年份航季划分日期
//	prevDate = (year-1)+'-10-'+(31-prevDate);
//	startDate = year+'-03-'+(31-startDate);
//	endDate = year+'-10-'+(31-endDate-1);
//	if(currentTime<=new Date(startDate).getTime()){
//		startDate = prevDate;
//		endDate = currentDate.format("yyyy-MM-dd");
//	}else if(currentTime>new Date(startDate).getTime()&&currentTime<=new Date(endDate).getTime()){
//		endDate = currentDate.format("yyyy-MM-dd");
//	}else{
//		startDate = endDate;
//		endDate = currentDate.format("yyyy-MM-dd");
//	}
	//获取关注航班列表
    $.ajax({
        type:'post',
        url : '/getCurrentSeasonFocusFlts',
        dataType : 'json',
//        data:{
//        	startDate:startDate,
//        	endDate:endDate
//        },
        async:false,
        success : function(data) {
        	if(data!=null&&data.opResult=="0"){
        		//设置是否所有航班都被关注
        		var total = 0;
        		var totalChecked = 0;
        		var dataMap = data.map;
        		if(dataMap!=null){
        			var allData = [];
        			for(var key in dataMap){
            			var obj = dataMap[key];
            			var list = [];
            			total+=obj.length;
            			for(var i=0;i<obj.length;i++){
            				if(obj[i].state==1){
            					totalChecked++;
            				}
            				list.push({number:obj[i].flightNumber,sle:obj[i].state});
            			}
            			allData.push({line:key,data:list});
            		}
        			if(total==totalChecked){
        				$('#attentionAll').attr("checked",true);
        			}else{
        				if(totalChecked>0){
        					$('#attentionAll').prop('indeterminate',true);
        				}else{
        					$('#attentionAll').attr("checked",false);
        				}
        			}
        			if(allData){
                        atten=changeAtten=allData;//changeAtten为中间变量
                        if(type==0){
                        	var option = "";
                            if(atten.length>16){
                            	for(var i=1;i<=Math.ceil(parseFloat(atten.length)/parseFloat(4));i++){
                            		if(i<=3){
                            			if(i==1){
                            				option += '<div id="move-page-left">&lt;</div><div id="move-page-first" class="move-page-z move-page-w">'+i+'</div><div class="move-page-d" id="move-page-t">···</div><div class="move-page-box">"';
                            			}else if(i==2){
                            				option += '<div  class="move-page-z" id="move-page-be">'+i+'</div>';
                            			}else{
                            				option += '<div  class="move-page-z" id="move-page-md">'+i+'</div>';
                            			}
                            		}else if(i==Math.ceil(parseFloat(atten.length)/parseFloat(4))-1){
                            			option += '</div><div  class="move-page-d" id="move-page-b">···</div><div  class="move-page-z" id="move-page-last">16</div><div id="move-page-right">&gt;</div>';
                            		}
                            	}
                            	$('#move-page').html(option);
                            }else{
                                 for(var i=1;i<=Math.ceil(parseFloat(atten.length)/parseFloat(4));i++){
                                	 if(i==1){
                                		 option += "<div class='move-page-z move-page-w'>"+i+"</div>";
                                	 }else{
                                		 option += "<div class='move-page-z'>"+i+"</div>"; 
                                	 }
                                 }
                                 $('#fixed-page').html(option);
                            }
                            attention(0);
                        }else{
                        	attention(parseInt($('.move-page-w').text())-1);
                        }
                    }
        		}
        	}
        },
        error : function() {//FIXME  请求数据后此函数内容应放在success里面 -暂时做无求情数据测试
            console.log("err");
        }
    });
}

function iszhen(){
	$("#radio-state").attr("checked","checked");
	$("#radio-jia").removeAttr("checked");
	
    for(var j=0;j<$(".speciaList-in1").length;j++){
        $(".speciaList-in1").eq(j)[0].disabled="";
        $(".speciaList-in2").eq(j)[0].disabled="";
    }
}
function isjia(){
	$("#radio-jia").attr("checked","checked");
	$("#radio-state").removeAttr("checked");
	for(var j=0;j<$(".speciaList-in1").length;j++){
		$(".speciaList-in1").eq(j)[0].disabled="disabled";
		$(".speciaList-in2").eq(j)[0].disabled="disabled";
	}
}
function isIP(){
	/**
	 * 判断是否为IP
	 */
	var ip =  /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/;
	if(!ip.exec($("[name=ip]").val())){
		$("[name=ip]").val("");
	}
}
function keyflb(data){
	/**
	 * 验证航班号的长度
	 */
	if(data.value.length!=6){
		data.value = "";
		$("[name=flt_rte_cd"+data.name.split("")[data.name.length-1]+"]").val("");
	}
}
function keyflt(data){
	/**
	 * 验证航班号的长度
	 */
	if(data.value.length==6||data.value.length==9){
	}else{
		alert("航线输入不正确！");
		data.value = "";
		$("[name=flb_nbr"+data.name.split("")[data.name.length-1]+"]").val("");
	}
}
function key(){
	for(var i=0;i<$("[name=ip]").val().length;i++){
        var c = $("[name=ip]").val().substr(i,1);
        var ts = escape(c);
        if(ts.substring(0,2) == "%u"){
        	$("[name=ip]").val($("[name=ip]").val().substr(0,i));
        }
    }
	/**
	 * 判断级别表单值是否为数字
	 */
	if(isNaN($("[name=lvl]").val())){
		$("[name=lvl]").val("");
	}
	/**
	 * 验证端口号是否数字
	 */
	if(isNaN($("[name=port]").val())){
		$("[name=port]").val("");
	}
	/**
	 * 验证端口是否为大于65535
	 */
	if(parseInt($("[name=port]").val())>65535){
		$("[name=port]").val("");
	}
	/**
	 * 验证等待分钟是否为数字
	 */
	if(isNaN($("[name=avoidTime]").val())){
		$("[name=avoidTime]").val("");
	}
	/**
	 * 验证等待分组不能超过600分钟
	 */
	if(parseInt($("[name=avoidTime]").val())>600){
		$("[name=avoidTime]").val("");
	}
	/**
	 * 判断级别表单值的长度
	 */
	if($("[name=lvl]").val().length>4){
		$("[name=lvl]").val($("[name=lvl]").val().substr(0,3));
	}
	/**
	 * 判断时间表达值是否为数字
	 */
	if(isNaN($("[name=auto]").val())||parseInt($("[name=auto]").val())>2){
		$("[name=auto]").val("");
	}
	if(isNaN($("[name=auto1]").val())){
		$("[name=auto1]").val("");
	}
	if(parseInt($("[name=auto]").val())>1&&parseInt($("[name=auto1]").val())>3){
		$("[name=auto1]").val("");
	}
	if(isNaN($("[name=auto2]").val())||parseInt($("[name=auto2]").val())>5){
		$("[name=auto2]").val("");
	}
	if(isNaN($("[name=auto3]").val())){
		$("[name=auto3]").val("");
	}
	/**
	 * 判断时间表达值的长度
	 */
	if($("[name=auto]").val().length>1){
		$("[name=auto]").val($("[name=auto]").val().substr(0,1));
	}
	if($("[name=auto1]").val().length>1){
		$("[name=auto1]").val($("[name=auto1]").val().substr(0,1));
	}
	if($("[name=auto2]").val().length>1){
		$("[name=auto2]").val($("[name=auto2]").val().substr(0,1));
	}
	if($("[name=auto3]").val().length>1){
		$("[name=auto3]").val($("[name=auto3]").val().substr(0,1));
	}
}
String.prototype.endWith=function(s){
	  if(s==null||s==""||this.length==0||s.length>this.length)
	     return false;
	  if(this.substring(this.length-s.length)==s)
	     return true;
	  else
	     return false;
	  return true;
	 };

String.prototype.startWith=function(s){
	  if(s==null||s==""||this.length==0||s.length>this.length)
	   return false;
	  if(this.substr(0,s.length)==s)
	     return true;
	  else
	     return false;
	  return true;
	 };