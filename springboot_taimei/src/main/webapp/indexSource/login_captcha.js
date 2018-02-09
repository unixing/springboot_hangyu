
var filter = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   //手机格式
var re=/^([\u4E00-\u9FA5]|\w)*$/;    //特殊字符
var rep=/^(?!\D+$)(?![^a-zA-Z]+$)\S{6,20}$/;      //6-20位数字加字母
var ti=1;
//验证码图片
function randomNum(min,max){
        return Math.floor(Math.random()*(max-min)+min);
    }
    function randomColor(min,max){
        var _r = randomNum(min,max);
        var _g = randomNum(min,max);
        var _b = randomNum(min,max);
        return "rgb("+_r+","+_g+","+_b+")";
    }
    function drawPic(id){
        var $canvas = document.getElementById(id);
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
        return _picTxt;
    }
$('.login_del').on('click',function(){
	$('#login_input').css({'display':'block'});
	$('#login_validation').css({'display':'none'});
	$('#login_create').css({'display':'none'});
	$('#login_administrator').css({'display':'none'});
	$('#login_administrator_note').css({'display':'none'});
	$(".login-input-login-err").html("")
	$("input").val('');
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
})
$('#login_captcha').on('click',function(){
	$('#login_input').css({'display':'none'});
	$('#login_validation').css({'display':'block'});
	$('#login_create').css({'display':'none'});
})
var login_phone='';
$("#login-pasBack-val").on('click',function(){
	login_phone=$('#login_phone').val();
	if(login_phone==''){
		$('#login_tip5').text('手机号码不能为空');
		setTimeout(function(){
			$('#login_tip5').text('');
		},1500)
	}else if(filter.test(login_phone)){
		if(ti<=1){
			$.ajax({
				url:'/getLoginSmCode',
				type:'post',
				dataType:'json',
				data:{
					mobile:login_phone
				},
				success:function(data){
					if(data.opResult==0){
						ti=60;
						var time=function(){
							if(ti>1){
								ti--;
								$("#login-pasBack-val").text(ti+'s'+'后发送');
								if(ti!=0){
									setTimeout(function(){time();},1000);
								}
							}else{
								$("#login-pasBack-val").text('发送验证码');
							};
						};
						time();
					}else if(data.opResult==5){
						$('#login_tip5').text('你的手机号没有绑定,请联系平台');
					}else{
						$('#login_tip5').text('请稍后再试');
						setTimeout(function(){
							$('#login_tip5').text('');
						},1500);
					}
				}
			});
		}
	}else{
		$('#login_tip5').text('手机号码格式有错');
		setTimeout(function(){
			$('#login_tip5').text('');
		},1500)
	}
})

$("#login_next").on('click',function(){userCheck();})
$('#login-pasBack-import-val').on('keydown',function(e){
	if(e.keyCode==13){
		userCheck();
	}
})
function userCheck(){
	login_phone=$('#login_phone').val();
	var importVla = $("#login-pasBack-import-val").val();
	if(login_phone!=''&&importVla!=''){
		$.ajax({
			url:'/bindPhoneOrValidCode_Login',
			type:'post',
			dataType:'json',
			data:{
				mobile:login_phone,
				validCode:$("#login-pasBack-import-val").val()
			},
			success:function(data){
				if(data.opResult=='0'){
					$('#login_validation').css({'display':'none'});
					$('#login_create').css({'display':'block'});
				}else if(data.opResult=='4'){
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
            		if(arr=="undefined"||arr==null){
            			//第一次登陆没有设置cookie
            			setCookie("version",ver);
            			location.href="/indexd"; 
            		}else{
            			if(arr!=ver){
            				//版本号更新，重置cookie
            				setCookie("version",ver);
            				//清楚所有缓存???
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
				}else if(data.opResult=='-1'){
					$('#login_tip5').text('账号停用需联系平台');
					setTimeout(function(){
						$('#login_tip5').text('');
					},1500)
				}else if(data.opResult=='6'){
					$('#login_tip5').text('账号不存在');
					setTimeout(function(){
						$('#login_tip5').text('');
					},1500)
				}else if(data.opResult=='2'){
					$('#login_tip5').text('请先获取验证码');
					setTimeout(function(){
						$('#login_tip5').text('');
					},1500)
				}else if(data.opResult=='3'){
					$('#login_tip5').text('手机或验证码不能为空');
					setTimeout(function(){
						$('#login_tip5').text('');
					},1500)
				}else if(data.opResult=='-3'){
					$('#login_tip5').text('账号试用期过期');
					setTimeout(function(){
						$('#login_tip5').text('');
					},1500)
				}else{
					$('#login_tip5').text('验证码错误');
					$('#login-pasBack-import-val').val('');
					setTimeout(function(){
						$('#login_tip5').text('');
					},1500)
				}
			}
		});
	}else if(login_phone==''){
		$('#login_tip5').text('手机号码不能为空');
		setTimeout(function(){
			$('#login_tip5').text('');
		},1500)	
	}else if(importVla==''){
		$('#login_tip5').text('验证码不能为空');
		setTimeout(function(){
			$('#login_tip5').text('');
		},1500)	
	}

}
$('#login_careat_next').on('click',function(){createPas();})
$('#login_careat_nextP').on('keydown',function(e){
	if(e.keyCode==13){
		createPas();
	}
})

function createPas(){

	var uesr=$('#login_careat_nextU').val();
	var pas=$('#login_careat_nextP').val();
	if(!re.test(uesr)){
		$('#login_createN0t').text('账号格式错误');
		$('#login_createN0').css({'display':'inline-block'});
		setTimeout(function(){
			
		},1500)
	}else if(pas==''){
		$('#login_createN1t').text('密码不能为空');
		$('#login_createN1').css({'display':'inline-block'});
		setTimeout(function(){
			$('#login_createN1').css({'display':'inline-block'});
		},1500)
	}else if(!rep.test(pas)){
		$('#login_createN1t').text('密码格式错误');
		$('#login_createN1').css({'display':'inline-block'});
	}else{
		$.ajax({
			url:'/createNamdAndPwd',
			type:'post',
			dataType:'json',
			data:{
				userName:uesr,
				password:pas,
			},
			success:function(data){
				if(data.success){
					$('#login_create').css({'display':'none'});
					$('#mobile-bind').css({'display':'block'});
					setTimeout(function(){
						window.location.href="/indexd"; 
					},1500)
				}else if(data.opResult=='1'){
					$('#login_createN1t').text('无手机用户');
					$('#login_createN1').css({'display':'inline-block'});
				}else if(data.opResult=='2'){
					$('#login_createN1t').text('绑定用户名失败');
					$('#login_createN1').css({'display':'inline-block'});
				}else if(data.opResult=='3'){
					$('#login_createN1t').text('密码为空');
					$('#login_createN1').css({'display':'inline-block'});
				}else if(data.opResult=='4'){
					$('#login_createN1t').text('用户名已经存在');
					$('#login_createN1').css({'display':'inline-block'});
				}
			}
		});
	} 
	setTimeout(function(){
		$('#login_createN0').css({'display':'none'});
		$('#login_createN1').css({'display':'none'});
		$('#login_createN0t').text('');
		$('#login_createN1t').text('');
	},1500)

}
/* 管理员验证  */

$("#login_administrator_next").on("click",function(){
	addPhone();
})
$("#login_administrator_phone").on("keydown",function(e){
    if(e.keyCode==13){
    	addPhone();
    }
});
function addPhone(){
	var adminPhone=$("#login_administrator_phone").val();
	if(adminPhone==""||adminPhone==undefined){
		$("#login_tip6").html("<span style='font-family:icon'>&#xe64a;</span>手机号码不能为空");
		setTimeout(function(){
			$('#login_tip6').text('');
		},1500)	
	}else if(!filter.test(adminPhone)){
		$("#login_tip6").html("<span style='font-family:icon'>&#xe64a;</span>手机号码格式错误");
		setTimeout(function(){
			$('#login_tip6').text('');
		},1500)	
	}else{
		$.ajax({
			url:'/validPhone',
			type:'post',
			dataType:'json',
			data:{
				mobile:adminPhone
			},
			success:function(data){
				login_phone=adminPhone;
				if(data.opResult=='0'){
					$('#login_administrator').css({'display':'none'});
					$('#login_administrator_note').css({'display':'block'});
				}else{
					alert('手机号已经存在')
				}
			}
		});
	}

}
/* 管理员获取验证码  */
$('#login_administrator_pasBackval').on('click',function(){
	if(ti<=1){
		$.ajax({
			url:'/getValidCode',
			type:'post',
			dataType:'json',
			data:{
				mobile:login_phone
			},
			success:function(data){
				if(data.opResult==0){
					ti=60;
					var time=function(){
						if(ti>1){
							ti--;
							$("#login_administrator_pasBackval").text(ti+'s'+'后发送');
							if(ti!=0){
								setTimeout(function(){time();},1000);
							}
						}else{
							$("#login_administrator_pasBackval").text('发送验证码');
						};
					};
					time();
				}else if(data.opResult==5){
					$('#login_tip7').text('获取验证码错误，请重新获取');
					setTimeout(function(){
						$('#login_tip7').text('');
					},1500);
				}else if(data.opResult==1){
					$('#login_tip7').text('验证获取频率过快,请联系平台');
					setTimeout(function(){
						$('#login_tip7').text('');
					},1500);
				}
			}
		});
	}
})

$("#login_administrator_note_next").on("click",function(){authImage();})
$("#login_administrator_note_val").on("keydown",function(e){
	if(e.keyCode==13){
		authImage();
	}
	
})
function authImage(){
	var code =$("#login_administrator_note_val").val();
	if($(code!=""&&code!=undefined)){
		$.ajax({
			url:'/bindPhoneOrValidCode_Admin',
			type:'post',
			dataType:'json',
			data:{
				mobile:login_phone,
				validCode:code
			},
			success:function(data){
				if(data["opResult"]=='0'){
					$('#login_administrator_note').css({'display':'none'});
					$('#login_validation').css({'display':'none'});
					$('#login_create').css({'display':'block'});
				}else if(data["opResult"]=='2'){
                	$("#login_tip7").html("请先获取验证码");
                	$("#login_administrator_note_val").val('');
                	setTimeout(function(){
						$('#login_tip7').text('');
					},1500);
                }else{
                	$("#login_tip7").html("你输入的验证码错误");
                	$("#login_administrator_note_val").val('');
                	setTimeout(function(){
						$('#login_tip7').text('');
					},1500);
                }
			}
		})
	}
}
/*** 忘记密码 ***/
$(".login-operation-forgot").on("click",function(){
	$(".login-collection-box").addClass("login_forgot_none");
    $("#login_forgot_pas").removeClass("login_forgot_none");
    
});
$("#login_forgot_pwd2Tip").on("click",function(){
	if(phones!=""&&ti==0){
		reqPhone(phones);
	}
})
var reqPhone=function(phone){
	$.ajax({
		url:'/getLoginSmCode',
		type:'post',
		dataType:'json',
		data:{
			mobile:phone
		},
		success:function(data){
			if(data.opResult==0){
				$("#login_forgot_phone").addClass("login_forgot_none");
				$("#login_forgot_yzm").addClass("login_forgot_none");
				$("#login_forgot_yzyzm").removeClass("login_forgot_none");
				if(data.opResult==0){
					ti=60;
					var time=function(){
						if(ti>1){
							ti--;
							$("#login_forgot_pwd2Tip").html(ti+'s'+'后发送');
							if(ti!=0){
								setTimeout(function(){time();},1000);
							}
						}else{
							$("#login_forgot_pwd2Tip").html('发送验证码');
							ti=0;
						};
					};
					time();
				}else if(data.opResult==5){
					$('#login_tip14').html('你的手机号没有绑定,请联系平台');
				}else{
					$('#login_tip14').html('&nbsp;请稍后再试');
					setTimeout(function(){
						$('#login_tip14').html('');
					},1500);
				}
			}else if(data.opResult==5){
				$('#login_tip12').html('<span class="login_forgot_icon">&#xe64a;</span>'+"你输入的手机号码有误");
				setTimeout(function(){$('#login_tip12').html("");},1500);
				cs++;
				if(cs>3){
					$("#login_forgot_yzm").removeClass("login_forgot_none");
					yzm=drawPic("twoImg");
				}
			}else{
				$('#login_tip12').html("&nbsp;<span style='font-family:icon'>&#xe64a;</span>请稍后再试");
				setTimeout(function(){
					$('#login_tip12').text('');
				},1500);
			}
		}
	});
}
//验证验证码
var yzCode = function(mob,code){
	var os = new Object();
	os.ti=0;
	os.req=function(){
		$.ajax({
			url:'/validChangePwdCode',
			type:'post',
			dataType:'json',
			data:{
				mobile:mob,
				code:code
			},
			success:function(data){
				if(data.opResult==0){
					$("#login_forgot_pas").addClass("login_forgot_none");
					$("#login_forgot_pas2").removeClass("login_forgot_none");	
					
				}else if(data.opResult==5){
					$('#login_tip14').html('你的手机号没有绑定,请联系平台');
				}else{
					$('#login_tip14').html("&nbsp;<span style='font-family:icon'>&#xe64a;</span>请稍后再试");
					setTimeout(function(){
						$('#login_tip14').html('');
					},1500);
				}
			}
		})
	};
	return os;
}
var yzm="";
var cs=0;
var yzyzm=0;
var phones="";
 
$("#twoImg").on("click",function(){yzm=drawPic("twoImg")});
$("#login_forgot_pas_n").on("click",function(){
	phones= $("#login_forgot_pwd").val();
	if($("#login_forgot_yzyzm").hasClass("login_forgot_none")){ //15208258814
		if(phones==""){
			$("#login_tip12").html("<span>&#xe64a;</span>你输入的手机号码不能为空！");
			setTimeout(function(){$("#login_tip10").html("");},1500);
		}else if(!filter.test(phones)){
			$("#login_tip12").html("<span>&#xe64a;</span>手机号码格式有误！");
			setTimeout(function(){$("#login_tip10").html("");},1500);
		}else{
			if(cs>3){
				if($("#login_forgot_pwd1").val()!=""){
					if($("#login_forgot_pwd1").val()!=yzm){
						$('#login_tip13').html('<span class="login_forgot_icon">&#xe64a;</span>'+"验证码错误");
						setTimeout(function(){$('#login_tip13').html("");},1500);
						yzm=drawPic("twoImg");
					}else{
						reqPhone(phones);
					}
				}else{
					$('#login_tip13').html('<span class="login_forgot_icon">&#xe64a;</span>'+"请输入验证码");
					setTimeout(function(){$('#login_tip13').html("");},1500);
				}
			}else{
				reqPhone(phones);
			}		
		}
	}else{
		var codess = $("#login_forgot_pwd2").val();
		
		if(codess!=""&&codess!=undefined){
			var jzyz = new yzCode(phones,codess);
			jzyz.req();
			console.log(jzyz);
		}else{
			$("#login_tip14").html("请输入短信验证码");
			setTimeout(function(){$('#login_tip14').html("");},1500);
		}
	}	
});

$(".login_delx").on("click",function(e){
	$(".login-collection-box").removeClass("login_forgot_none");	
	$("#login_input").removeClass("login_forgot_none");
	$("#login_forgot_pas").addClass("login_forgot_none");
	$("#login_forgot_yzm").addClass("login_forgot_none");
	$("#login_forgot_yzyzm").addClass("login_forgot_none");
	$("#mobile-bind").addClass("login_forgot_none");
	$("#mobile-bind1").addClass("login_forgot_none");
	$("#login_forgot_pas2").addClass("login_forgot_none");
	$("#login_forgot_phone").removeClass("login_forgot_none");
	$(".login_forgot_resul").val("");	
});
$("#login_forgot_pas_n2").on("click",function(){
	var patt=/^[0-9a-zA-Z]*$/g;
	var patt1=/^[0-9a-zA-Z]*$/g;

    if($("#login_forgot_pwd3").val()==""){
        $("#login_tip15").html("<span class='login_forgot_icon'>&#xe64a;</span>密码不能为空");
        setTimeout(function(){ $("#login_tip15").html("");},1500);
        $("#login_tip15").html("");
    }else if($("#login_forgot_pwd4").val()==""){
        $("#login_tip16").html("<span class='login_forgot_icon'>&#xe64a;</span>密码不能为空");
        setTimeout(function(){ $("#login_tip16").html("");},1500);
    }else if(!patt.test($("#login_forgot_pwd3").val())){
        $("#login_tip15").html("<span class='login_forgot_icon'>&#xe64a;</span>密码格式错误");
        setTimeout(function(){ $("#login_tip15").html("");},1500);
    }else if(!patt1.test($("#login_forgot_pwd4").val())){
        $("#login_tip16").html("<span class='login_forgot_icon'>&#xe64a;</span>密码格式错误");
        setTimeout(function(){ $("#login_tip16").html("");},1500);
    }else if($("#login_forgot_pwd4").val()!=$("#login_forgot_pwd3").val()){
        $("#login_tip16").html("<span class='login_forgot_icon'>&#xe64a;</span>两次密码不一样");
        setTimeout(function(){ $("#login_tip16").html("");},1500);
    }else {
        $.ajax({
            url:"/updatePwd",
            type:"post",
            data:{
                password:$("#login_forgot_pwd4").val(),
                mobile:phones
            },
            dataType:"json",
            success:function(data){
            	 if(data.opResult==0){
            		 $("#mobile-bind1").removeClass("login_forgot_none");
        	     	setTimeout(function(){
                		$(".login-collection-box").removeClass("login_forgot_none");	
                		$("#login_input").removeClass("login_forgot_none");
                		$("#login_forgot_pas").addClass("login_forgot_none");
                		$("#login_forgot_yzm").addClass("login_forgot_none");
                		$("#login_forgot_yzyzm").addClass("login_forgot_none");
                		$("#mobile-bind").addClass("login_forgot_none");
                		$("#mobile-bind1").addClass("login_forgot_none");
                		$("#login_forgot_pas2").addClass("login_forgot_none");
                		$(".login_forgot_resul").val("");	
                	},1500);
                 }else if(data.opResult==2){
                	 alert("服务器异常")
                 }else if(data.opResult==3){
                    
                 }else if(data.opResult==4){
                    alert("新旧密码不能相同")
                 }
            }
        })
    }
})