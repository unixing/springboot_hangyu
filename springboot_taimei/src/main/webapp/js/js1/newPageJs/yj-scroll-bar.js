//获取宽高等等
function infer(name){
    var infer=[];
    infer.push(parseFloat($(name).css("width").indexOf('px')>-1?$(name).css("width").split("px")[0]:0));
    infer.push(parseFloat($(name).css("height").indexOf('px')>-1?$(name).css("height").split("px")[0]:0));
    infer.push(parseFloat($(name).css("margin-top").indexOf('px')>-1?$(name).css("margin-top").split("px")[0]:0));
    infer.push(parseFloat($(name).css("left").indexOf('px')>-1?$(name).css("left").split("px")[0]:0));
    infer.push(parseFloat($(name).css("top").indexOf('px')>-1?$(name).css("top").split("px")[0]:0));
    return infer;
}
//滚动条
function addBar(cNode,zNode,gNode){ // 最大区域事件绑定块/-/区域固定盒子/-/滚动不固定盒子（滚动盒子）
    if(infer(gNode)[1]>infer(zNode)[1]){
        if($(zNode).css("position")=="static"){ //初始化盒子
            $(zNode).css("position","relative");
        };
        if($(gNode).css("position")=="static"){ //初始化滚动盒子
            $(gNode).css("position","relative");
        };
        if(parseInt($("_bar").length)==0){  //添加滚动条
            $(zNode).append("<div class='_bar-box'><div class='_bar'></div></div>");
            $("._bar-box").css({"position": "absolute","right": "0px","background-color": "rgba(153,153,153,0.1)","height": "100%","width": "15px",'top':'0px'});
            $("._bar").css({'width':'8px','height':'100px','background-color':'rgb(121, 178, 223)',"cursor":"pointer",'position':'absolute','left':'3.5px','top':'0px','border-radius':'2px'});
        };
        var Scrollable=(infer("._bar-box")[1]-infer("._bar")[1])/(infer(gNode)[1]-infer(zNode)[1]);
        $(cNode).on("mousewheel",function(e,delta){
            if(delta=="1"){
                $(gNode).css({"top":"+=15px"});
                if(infer(gNode)[4]>0){
                    $(gNode).css({"top":"0px"});
                };
                $("._bar").css({"top":-(infer(gNode)[4]*Scrollable)+"px"});
            }else {
                $(gNode).css({"top":"-=15px"});
                if(infer(gNode)[4]<-(infer(gNode)[1]-infer(zNode)[1])){
                    $(gNode).css({"top":-(infer(gNode)[1]-infer(zNode)[1])+"px"});
                }
                $("._bar").css({"top":-(infer(gNode)[4]*Scrollable)+"px"});
            }
        });
        $("._bar").on("mousedown",function(e){
            e.stopPropagation();
            var oTop= e.screenY;
            var top=infer("._bar")[4];
            $(cNode).on("mousemove",function(b){
                var nTop= b.screenY;
                var tTop=top+(nTop-oTop);
                $("._bar").css({"top":tTop+"px"});
                if(infer("._bar")[4]<0){
                    $("._bar").css({"top":"0px"});
                }else if(infer("._bar")[4]>(infer("._bar-box")[1]-infer("._bar")[1])){
                    $("._bar").css({"top":(infer("._bar-box")[1]-infer("._bar")[1])+"px"});
                }
                $(gNode).css({"top":-(infer("._bar")[4]/Scrollable)+"px"});
            });
        });
        $("._bar").on("click",function(e){  //阻止事件冒泡
            e.stopPropagation();
        });
        $(cNode).on("mouseup",function(){
            $(cNode).unbind("mousemove");
        });
        $("._bar-box").on("click",function(e){
            var et=e.offsetY;
            if(et<infer("._bar")[1]){
                $("._bar").css("top","0px");
            }else if(et>(infer("._bar-box")[1]-infer("._bar")[1])){
                $("._bar").css("top",(infer("._bar-box")[1]-infer("._bar")[1])+"px");
            }else {
                $("._bar").css("top",(et-(infer("._bar")[1]/2))+"px");
            }
            $(gNode).css({"top":-(infer("._bar")[4]/Scrollable)+"px"});
        });
    }
};
//模拟选择框
function setChoose(list){
    var nums="";
    if(list.summary=="true"){
        nums+="<div class='_set-list-title'>汇总</div><div class='_set-allList' style='display: none'>";
    }else {
        nums+="<div class='_set-list-title'>"+list.data[0]+"</div><div class='_set-allList' style='display: none'>";
    }
    nums+="<div>汇总</div>";
    for(var i=0;i<list.data.length;i++){
        nums+="<div>"+list.data[i]+"</div>";
    }
    $(list.name).html(nums);
    $("._set-list-title").on("click",function(){
        event.stopPropagation();
        if( $("._set-allList").css("display")=="none"){
            $("._set-allList").css({"display":"block"});
        }else {
            $("._set-allList").css({"display":"none"});
        }
    });
    $("._set-allList>div").on("click",function(){
        event.stopPropagation();
        $("._set-list-title").text($(this).text());
        $("._set-allList").css("display","none");
    });
    $("body").on("click",function(){
        console.log("ddd");
        $("._set-allList").css({"display":"none"});
    });
};
