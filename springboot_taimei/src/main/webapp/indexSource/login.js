

/***登录****/
    /* 页面加载换图 */
    var yue=parseInt(Math.random()*5);
    $("body").css({"background-image":"url('/images/login/"+yue+".jpg')"});
    //根据背景图片改变foot文字颜色	龙洪附
    if(yue==3){	//如果背景图是3则改变样式
    	$("#foot").css("color","#000");
    	$("#foot a").css("color","#000");
    	$(".foot-link").css("border-bottom","1px solid #000");
    	$("#ver-table").css("color","#FFF");
    }
    /* 加载判断是否登录保存过密码 */
    if(getCookie("userName")!=null){
    	$($(".login-input-login-box-nus>input")[0]).val(getCookie("userName"));
        $($(".login-input-login-box-pas>input")[0]).val(getCookie("passWord"));
        $("#remember").html("&#xe643");
        $("#login-checkbox").prop("checked",true);
        $(".login-input-login-bth").removeClass("correct").addClass("login_down");
    }else{
        $("#remember").html("&#xe642");
        $("#login-checkbox").prop("checked",false)
    }
    /* 是否记住密码 */
    $("#remember").on("click",function(){
        if($("#login-checkbox").prop("checked")==true){
            $("#remember").html("&#xe642");
        }else {
            $("#remember").html("&#xe643");
        }
    });
    /*删除COOKIE*/
    function delCookie(name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval=getCookie(name);
        if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
    }
    /***清除cookie   已屏蔽***/
    (function(){
        var keys = document.cookie.match(/[^ =;]+(?=\=)/g);  
        if(keys) {  
            for(var i = keys.length; i--;){
                document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString();
            }
        }  
    });
    /* 设置保存密码 */
    function SetCookie(name,value){
        var Days = 10; //此 cookie 将被保存 30 天
        var exp = new Date();
        exp.setTime(exp.getTime() + Days*24*60*60*1000);
        document.cookie = name + "="+ decodeURI (value) + ";expires=" + exp.toGMTString();
    }
    /*获取保存密码*/
    function getCookie(name){
        var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
        if(arr != null)
            return decodeURI(arr[2]);
        return null;
    }
    $("#log-user").on("keydown",function(e){
        if(e.keyCode==13){
            login();
        }
    });
    $("#log-pas").on("keydown",function(e){
        if(e.keyCode==13){
        	login();
        }
    });
    $("#log-authImage").on("keydown",function(e){
        if(e.keyCode==13){
        	login();
        }
    });
    $(".login-input-login-bth").on("click",function(){
        login();
    });
    /* input改变 */
    $(".login-input-login-box-list>input").on("input",function(){
        $(".login-input-login-err").html("");
        if($(".login-input-login-box-nus>input").val()!=""&&$(".login-input-login-box-pas>input").val()!=""){
           $(".login-input-login-bth").removeClass("correct").addClass("login_down");
        }else {
            $(".login-input-login-bth").addClass("correct").removeClass("login_down");
        }
    });
    /* 切换登录方式 */
    $(".login-input-switch").toggle(function(){
        $(".login-input-switch").css({"background-position":"0px 0px"}).attr("title","返回账号登录");
    },function(){
        $(".login-input-switch").css({"background-position":"-61px -39px"}).attr("title","微信扫码登录")
    });
    function setCookie(name,value){
	    var Days = 30;
	    var exp = new Date();
	    exp.setTime(exp.getTime() + Days*24*60*60*1000);
	    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
	} 
	function getCookie(name){
	    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	    if(arr=document.cookie.match(reg))
	        return unescape(arr[2]);
	    else
	        return null;
    } 
    function login(){
    	var queryParams= {
    			username:$("#log-user").val(),
    			password:$("#log-pas").val()
    	};
    	var display = $("#log-authImageDiv").css('display');
    	if(display=="block"){
    		var authImage = $("#log-authImage").val();
    		if(authImage){
    			queryParams.authImage = authImage; 
    		}else{
    			$(".login-input-login-err").html("<span>&#xe64a;</span>请输入验证码")
    			return;
    		}
        }
        if(!$(".login-input-login-bth").hasClass("correct")){
            if(queryParams.username=="hangyu"){
                queryParams.wheres = "hangyu";
            }
            $.ajax({
                type:'get',
                url : '/checkLogin',
                dataType : 'json',
                data:queryParams,
                success : function(data) {
                	if(data){
                    	if(data["opResult"]=='4'){//没有绑定手机, 绑定手机
                    		$('#login_administrator').css('display','block');
                    		$('#login_input').css('display','none');
                    	}else  if(data["opResult"]=='3'){//有绑定手机, 直接手机登录
                			$('#login_input').css({'display':'none'});
                			$('#login_validation').css({'display':'block'});
                			$('#login_create').css({'display':'none'});
                		}else  if(data["opResult"]=='-3'){//有绑定手机, 直接手机登录
                			$(".login-input-login-err").html("<span>&#xe64a;</span>账户异常，请联系平台客服！")
                			setTimeout(function(){
                				$('.login-input-login-err').html('');
                			},1500);
                		}else if(data["opResult"]=='-1'){//账号停用
                			$(".login-input-login-err").html("<span>&#xe64a;</span>账户异常，请联系平台客服！")
                			setTimeout(function(){
                				$('.login-input-login-err').html('');
                			},1500);
                		}else if(data["success"]==false){//登录失败
                    		if(data["errorCode"]==-101){
                    			$(".login-input-login-err").html("<span>&#xe64a;</span>用户名或密码错误！")
                    			setTimeout(function(){
									$('.login-input-login-err').html('');
								},1500);
                    			$("#log-authImageDiv").css({'display':'block'});
                    		}else if(data["errorCode"]==-102){
                    			$(".login-input-login-err").html("<span>&#xe64a;</span>用户名或密码错误！")
                    			setTimeout(function(){
                    				$('.login-input-login-err').html('');
                    			},1500);
                    		}else if(data["errorCode"]==-103){//多次登录失败
                    			$('#login_input').css({'display':'none'});
                    			$('#login_validation').css({'display':'block'});
                    			$('#login_create').css({'display':'none'});
                    			$('#login_tip5').text('失败次数过多，请稍后再试！');
            					$('#login_tip5').css({'display':'inline-block'});
                    		}else if(data["errorCode"]==-104){//多次登录失败
                    			$('.login-input-login-err').text('失败次数过多，请稍后再试！');
                    		}
                    		
                        }else if(data["opResult"]=='-2'){
                        	$(".login-input-login-err").html("<span>&#xe64a;</span>用户名或密码为空！")
                        	setTimeout(function(){
                        		$('.login-input-login-err').html('');
                        	},1500);
                        }else if(data["opResult"]=='-8'){
                        	$(".login-input-login-err").html("<span>&#xe64a;</span>验证码错误")
                        	setTimeout(function(){
                        		$('.login-input-login-err').html('');
                        	},1500);
                        }else if(data["success"]==true){
                            if($("#login-checkbox").prop("checked")==true){
                                SetCookie("userName",$("#log-user").val());
                                SetCookie("passWord",$("#log-pas").val());
                            }else {
                                delCookie("userName");
                                delCookie("passWord");
                            }
                            //登录成功，验证邮箱绑定操作返回结果
                            if(data["opResult"]=='0'){
                            	location.href="/success";
                            	return;
                            }else if(data["opResult"]=='1'||data["opResult"]=='2'||data["opResult"]=='3'){
                            	location.href="/fail";
                            	return;
                            }
                            //登陆成功，判断版本是否改变，若改变则清除缓存
                    		var ver = $("#version").html();
                            var arr = getCookie("version");
                            if(queryParams.wheres){
                                location.href="/hangyu"; 
                            }else{
                                if(arr=="undefined"||arr==null){
                                    //第一次登陆没有设置cookie
                                    setCookie("version",ver);
                                    location.href="/indexd"; 
                                }else{
                                    if(arr!=ver){
                                        //版本号更新，重置cookie
                                        setCookie("version",ver);
                                        //发起ajax请求，改变session中的值
                                        $.ajax({
                                            type:'post',
                                            url : '/restful/versionExchange',
                                            data:null,
                                            dataType : 'json',
                                            success : function(data) {
                                                location.href="/indexd"; 
                                            }
                                        });
                                    }else{
                                        setCookie("version",ver);
                                        location.href="/indexd"; 
                                    }
                                } 
                            }
                        }
                    }
                    
                },
                error : function() {

                }
            })
        }
    };
  
    
	function changeImg(){
		var img = document.getElementById("img");  
		img.src = "/authImage?date=" + new Date();
    } 