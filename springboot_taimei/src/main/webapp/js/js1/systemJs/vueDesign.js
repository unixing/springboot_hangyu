/* 二维码 */
var authCode="";
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
    authCode=_picTxt.toUpperCase();
    return _picTxt;
}
var vm=new Vue({
    el:"#pla-set",
    directive:{},
    data:{
        templates:"verify",
        tim:0,
        mus:"",
        tem:true,
        process:""
    },
    methods:{
        open:function(par){
            this.templates="verify";
            if(par=="phone"){
                this.process="phone";
            }else if(par=="email"){
                this.process="email";
            }
            $(".pla-set-clear").css("display","none");
            document.getElementsByClassName("mobile-bind")[0].style.display="block";
        },
        shut:function(){
        	$(".pla-set-clear").css("display","block");
            document.getElementsByClassName("mobile-bind")[0].style.display="none";
            vm.$root.templates="verify";
        },
        getEmail:function(){
        	this.$http.post('/unbindMail',{
            },{
                emulateJSON:true
            }).then(function(res){
            	if(res.data.opResult=='0'){
            		$('#email').val('');
            		$('.bindemail').text('绑定');
            		this.templates="untieSuccessful";
            	}else{
            		this.templates="untieFailed";
            	}
            },function(){

            });
        },
        demand:function(pas){
            this.$http.jsonp('/employee_valid',{
                pwd:pas
            },{
                emulateJSON:true
            }).then(function(res){
                if(res.data.success.opResult=="0"){
                    if(this.process=="phone"){
                        this.templates="verifyPhon";
                    }else if(this.process=="email"){
                        if($(".bindemail").text()=="解绑"){
                        	this.getEmail();
                            
                        }else if($(".bindemail").text()=="绑定"){
                            this.templates="emailVerify"
                        }
                    }
                }else if(res.data.success.opResult=="1"){
                    $(".setPublic-tipErr").text("密码错误,请从新输入");
                    $(".setPublic-tipWarning").css("display","inline-block");
                    setTimeout(function(){
                        $(".setPublic-tipWarning").css("display","none");
                    },1000);
                    this.tim+=1;
                }
            },function(){

            });
        }
    },
    components:{
        "verify":{
            template:"#verifyPassword",
            data(){
              return{
                  pas:"",
                  open:false,
                  codes:"",
                  Tcodes:"aaaa",
                  title:"验证密码"
              }
            },
            methods:{
                shut:function(){
                    vm.$root.shut();
                },
                verifyPas:function(){
                    if(this.pas!=""){
                        if(vm.$root.tim>=3){
                            var othis=this;
                            this.open=true;
                            setTimeout(function(){
                                othis.Tcodes=drawPic("twoImg");
                            },100);
                            if(this.codes==this.Tcodes){
                                vm.$root.demand(this.pas);
                            }else {
                                $(".setPublic-tipErr").text("验证码错误");
                                $(".setPublic-tipWarning").css("display","inline-block");
                                setTimeout(function(){
                                    $(".setPublic-tipWarning").css("display","none");
                                },1000);
                                othis.Tcodes=drawPic("twoImg");
                            }
                        }else{
                            vm.$root.demand(this.pas);
                        }
                    }else{
                        $(".setPublic-tipErr").text("密码不能为空");
                        $(".setPublic-tipWarning").css("display","inline-block");
                        setTimeout(function(){
                            $(".setPublic-tipWarning").css("display","none");
                        },1000)
                    }
                },
                get(ge){   //接收验证码
                   this.codes=ge;
                }
            },
            components:{
                "there":{
                    template:"#thereErr",
                    data(){
                      return {
                          code:""
                      }
                    },
                    methods:{
                        changeImg:function(){
                            drawPic("twoImg");
                        },
                        send(){  //发送验证码
                            this.$emit('my-code',this.code);
                        }
                    }
                }

            }
        },
        "verifyPhon":{
            template:"#replace",
            data(){
                return{
                    pNumber:"",
                    title:"更换手机号",
                    pCode:"",
                    timing:"发送验证码"
                }
            },
            methods:{
                obtainCode:function(){
                	var oThis=this;
                	if(this.pNumber==$("#phone").val()){
                		 $(".setPublic-tipErr").text("手机号与原手机号不能重复！");
                         $(".setPublic-tipWarning").css("display","inline-block");
                         setTimeout(function(){
                             $(".setPublic-tipWarning").css("display","none");
                         },1000);
                	}else{
                		 if(oThis.timing=="发送验证码"){
                             if(oThis.pNumber!=""){
                                 oThis.timing="发送中...";
                                 $(".setPublic-inputSend").css({"box-shadow":"none","background-color":"gray"});
                                 var filter = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
                                 if(filter.test(oThis.pNumber)){
                                     this.$http.post('/getValidCode',{
                                         mobile:oThis.pNumber
                                     },{
                                         emulateJSON:true
                                     }).then(function(res){
                                         if(res.data.opResult=="0"){
                                             var nus=60;
                                             var seti1=setInterval(function(){
                                                 if(nus!=1){
                                                     nus--;
                                                     oThis.timing=nus+"s";
                                                 }else{
                                                     $(".setPublic-inputSend").css({"box-shadow":"2px 2px 2px #25354f inset","background-color":"#37527d"});
                                                     clearInterval(seti1);
                                                     oThis.timing="发送验证码"
                                                 }
                                             },1000);
                                         }else if(res.data.opResult=="1"){
                                             $(".setPublic-tipErr").text("发送验证码失败！");
                                             $(".setPublic-tipWarning").css("display","inline-block");
                                             setTimeout(function(){
                                                 $(".setPublic-tipWarning").css("display","none");
                                             },1000);
                                             $(".setPublic-inputSend").css({"box-shadow":"2px 2px 2px #25354f inset","background-color":"#37527d"});
                                             oThis.timing="发送验证码";
                                         }
                                     },function(){
                                         $(".setPublic-tipErr").text("发送验证码失败！");
                                         $(".setPublic-tipWarning").css("display","inline-block");
                                         setTimeout(function(){
                                             $(".setPublic-tipWarning").css("display","none");
                                         },1000);
                                         oThis.timing="发送验证码";
                                     });
                                 }else{
                                     $(".setPublic-tipErr").text("请输入正确的手机号码");
                                     $(".setPublic-tipWarning").css("display","inline-block");
                                     setTimeout(function(){
                                         $(".setPublic-tipWarning").css("display","none");
                                     },1000);
                                 }
                             }else {
                                 $(".setPublic-tipErr").text("手机号码不能为空");
                                 $(".setPublic-tipWarning").css("display","inline-block");
                                 setTimeout(function(){
                                     $(".setPublic-tipWarning").css("display","none");
                                 },1000);
                             }
                         }
                	}
                },
                uploadPone:function(){
                    if(this.pCode!=""){ // 上传短信验证码
                        this.$http.post('/switchPhone',{
                            mobile:this.pNumber,
                            validCode:this.pCode
                        },{
                            emulateJSON:true
                        }).then(function(res){
                            if(res.data.opResult=="0"){
                                vm.$root.templates="phoneSuccessful";
                                $("#phone").val(this.pNumber)
                            }else if(res.data.opResult=="3"){
                                $(".setPublic-tipErr").text("绑定手机号码相同");
                                $(".setPublic-tipWarning").css("display","inline-block");
                                setTimeout(function(){
                                    $(".setPublic-tipWarning").css("display","none");
                                },1000);
                            }else if(res.data.opResult=="1"){
                                $(".setPublic-tipErr").text("绑定失败");
                                $(".setPublic-tipWarning").css("display","inline-block");
                                setTimeout(function(){
                                    $(".setPublic-tipWarning").css("display","none");
                                },1000);
                            }
                        },function(){

                        });
                    }
                },
                shut:function(){
                    vm.$root.shut();
                },
                shield:function(){
                    alert("ddd");
                    return false;
                }
            }
        },
        "setOsucc":{
            template:"#setOsucc",
            methods:{
                shut:function(){
                    vm.$root.shut();
                }
            }
        },
        "untieSuccessful":{
            template:"#untieSuccessful",
            methods:{
                shut:function(){
                    vm.$root.shut();
                }
            }
        },
        "emailSuccessful":{
            template:"#emailSuccessful",
            methods:{
                shut:function(){
                    vm.$root.shut();
                }
            }
        },
        "emailVerify":{
            template:"#emailVerify",
            data(){
              return {
                  title:"绑定邮箱",
                  eNumber:""
              }
            },
            methods:{
                shut:function(){
                    vm.$root.shut();
                },
                emailRequest:function(){
                    if(this.eNumber!=""){  //邮箱发送
                        var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                        if(filter.test(this.eNumber)){   // 成功发送邮件
                            this.$http.post('/bindMail',{
                                email:this.eNumber
                            },{
                                emulateJSON:true
                            }).then(function(res){
                               if(res.data.opResult=="0"){
                                   vm.$root.templates="emailSuccessful";
                               }
                            },function(){

                            });

                        }else {
                            $(".setPublic-tipErr").text("邮箱格式错误！");
                            $(".setPublic-tipWarning").css("display","inline-block");
                            setTimeout(function(){
                                $(".setPublic-tipWarning").css("display","none");
                            },1000);
                        }
                    }else {
                        $(".setPublic-tipErr").text("邮箱不能为空！");
                        $(".setPublic-tipWarning").css("display","inline-block");
                        setTimeout(function(){
                            $(".setPublic-tipWarning").css("display","none");
                        },1000);
                    }
                }
            }
        },
        "phoneSuccessful":{
            template:"#phoneSuccessful",
            methods:{
                shut:function(){
                    vm.$root.shut();
                }
            }
        }

    }
});
