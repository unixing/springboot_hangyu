function getMyDay(date){ //获取是星期几
    date=new Date(date);
    return date.getDay();
}
$(function(){
    var nums="2016-6-12";
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
                if(getMyDay(year+"-"+month+"-"+1)!=0){
                    if(month==3){
                        for(var t=getMyDay(year+"-"+month+"-"+1);t>0;t--){
                            node+="<div abbr='"+year+"-"+2+"-"+(30-t)+"' class='last-month'>"+(30-t)+"</div>"
                        }
                    }else if(month==1){
                        for(var t=getMyDay(year+"-"+month+"-"+1);t>0;t--){
                            node+="<div abbr='"+(year-1)+"-"+12+"-"+(32-t)+"' class='last-month'>"+(32-t)+"</div>"
                        }
                    }else if(month==8){
                        for(var t=getMyDay(year+"-"+month+"-"+1);t>0;t--){
                            node+="<div abbr='"+(year)+"-"+(month-1)+"-"+(32-t)+"' class='last-month'>"+(32-t)+"</div>"
                        }
                    }else {
                        for(var t=getMyDay(year+"-"+month+"-"+1);t>0;t--){
                            node+="<div abbr='"+(year)+"-"+(month-1)+"-"+(31-t)+"' class='last-month'>"+(31-t)+"</div>"
                        }
                    }
                }
                //计算本月天数
                for(var i=1;i<=31;i++){
                    if(getMyDay(year+"-"+month+"-"+i)!=0){
                        node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                    }else {
                        if(node=="<div class='calendar-content'>"){
                            node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                        }else {
                            node+="</div>";
                            node+="<div class='calendar-content'>";
                            node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                        }
                    }
                };
                //计算结尾天数
                if(getMyDay(year+"-"+month+"-"+31)!=6){
                    for(var i=1;i<7-parseInt(getMyDay(year+"-"+month+"-"+31));i++){
                        if(month==12){
                            node+="<div abbr='"+(parseInt(year)+1)+"-"+(1)+"-"+(i)+"' class='next-month'>"+(i)+"</div>"
                        }else {
                            node+="<div abbr='"+(year)+"-"+(month+1)+"-"+(i)+"' class='next-month'>"+(i)+"</div>"
                        }
                    }
                }
                node+="</div>";
                node="<div class='calendar-head'><div>周日</div><div>周一</div><div>周二</div><div>周三</div><div>周四</div><div>周五</div> <div>周六</div></div>"+node;
                $(".calendar").html(node)
            }else if(month==4||month==6||month==9||month==11){
                node="<div class='calendar-content'>";
                //计算上月剩余天数
                if(getMyDay(year+"-"+month+"-"+1)!=0){
                    for(var t=getMyDay(year+"-"+month+"-"+1);t>0;t--){
                        node+="<div abbr='"+year+"-"+(month-1)+"-"+(32-t)+"' class='last-month'>"+(32-t)+"</div>"
                    }
                }
                //计算本月天数
                for(var i=1;i<=30;i++){
                    if(getMyDay(year+"-"+month+"-"+i)!=0){
                        node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                    }else {
                        if(node=="<div class='calendar-content'>"){
                            node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                        }else {
                            node+="</div>";
                            node+="<div class='calendar-content'>";
                            node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                        }
                    }
                };
                //计算结尾天数
                if(getMyDay(year+"-"+month+"-"+30)!=6){
                    for(var i=1;i<7-parseInt(getMyDay(year+"-"+month+"-"+30));i++){
                        node+="<div abbr='"+(year)+"-"+(month+1)+"-"+(i)+"' class='next-month'>"+(i)+"</div>"
                    }
                }
                node+="</div>";
                node="<div class='calendar-head'><div>周日</div><div>周一</div><div>周二</div><div>周三</div><div>周四</div><div>周五</div> <div>周六</div></div>"+node;
                $(".calendar").html(node)

            }else if(month==2){
                node="<div class='calendar-content'>";
                //计算上月剩余天数
                if(getMyDay(year+"-"+month+"-"+1)!=0){
                    for(var t=getMyDay(year+"-"+month+"-"+1);t>0;t--){
                        node+="<div abbr='"+year+"-"+(month-1)+"-"+(32-t)+"' class='last-month'>"+(32-t)+"</div>"
                    }
                }
                //计算本月天数
                for(var i=1;i<=29;i++){
                    if(getMyDay(year+"-"+month+"-"+i)!=0){
                        node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                    }else {
                        if(node=="<div class='calendar-content'>"){
                            node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                        }else {
                            node+="</div>";
                            node+="<div class='calendar-content'>";
                            node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                        }
                    }
                };
                //计算结尾天数
                if(getMyDay(year+"-"+month+"-"+29)!=6){
                    for(var i=1;i<7-parseInt(getMyDay(year+"-"+month+"-"+29));i++){
                        node+="<div abbr='"+(year)+"-"+(month+1)+"-"+(i)+"' class='next-month'>"+(i)+"</div>"
                    }
                }
                node+="</div>";
                node="<div class='calendar-head'><div>周日</div><div>周一</div><div>周二</div><div>周三</div><div>周四</div><div>周五</div> <div>周六</div></div>"+node;
                $(".calendar").html(node)
            }
        }else {    //平年
            if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
                node="<div class='calendar-content'>";
                //计算上月剩余天数
                if(getMyDay(year+"-"+month+"-"+1)!=0){
                    if(month==3){
                        for(var t=getMyDay(year+"-"+month+"-"+1);t>0;t--){
                            node+="<div abbr='"+year+"-"+2+"-"+(29-t)+"' class='last-month'>"+(29-t)+"</div>"
                        }
                    }else if(month==1){
                        for(var t=getMyDay(year+"-"+month+"-"+1);t>0;t--){
                            node+="<div abbr='"+(year-1)+"-"+12+"-"+(32-t)+"' class='last-month'>"+(32-t)+"</div>"
                        }
                    }else if(month==8){
                        for(var t=getMyDay(year+"-"+month+"-"+1);t>0;t--){
                            node+="<div abbr='"+(year)+"-"+(month-1)+"-"+(32-t)+"' class='last-month'>"+(32-t)+"</div>"
                        }
                    }else {
                        for(var t=getMyDay(year+"-"+month+"-"+1);t>0;t--){
                            node+="<div abbr='"+(year)+"-"+(month-1)+"-"+(31-t)+"' class='last-month'>"+(31-t)+"</div>"
                        }
                    }
                }
                //计算本月天数
                for(var i=1;i<=31;i++){
                    if(getMyDay(year+"-"+month+"-"+i)!=0){
                        node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                    }else {
                        if(node=="<div class='calendar-content'>"){
                            node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                        }else {
                            node+="</div>";
                            node+="<div class='calendar-content'>";
                            node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                        }
                    }
                };
                //计算结尾天数
                if(getMyDay(year+"-"+month+"-"+31)!=6){
                    for(var i=1;i<7-parseInt(getMyDay(year+"-"+month+"-"+31));i++){
                        if(month==12){
                            node+="<div abbr='"+(parseInt(year)+1)+"-"+(1)+"-"+(i)+"' class='next-month'>"+(i)+"</div>"
                        }else {
                            node+="<div abbr='"+(year)+"-"+(month+1)+"-"+(i)+"' class='next-month'>"+(i)+"</div>"
                        }
                    }
                }
                node+="</div>";
                node="<div class='calendar-head'><div>周日</div><div>周一</div><div>周二</div><div>周三</div><div>周四</div><div>周五</div> <div>周六</div></div>"+node;
                $(".calendar").html(node)
            }else if(month==4||month==6||month==9||month==11){
                node="<div class='calendar-content'>";
                //计算上月剩余天数
                if(getMyDay(year+"-"+month+"-"+1)!=0){
                    for(var t=getMyDay(year+"-"+month+"-"+1);t>0;t--){
                        node+="<div abbr='"+year+"-"+(month-1)+"-"+(32-t)+"' class='last-month'>"+(32-t)+"</div>"
                    }
                }
                //计算本月天数
                for(var i=1;i<=30;i++){
                    if(getMyDay(year+"-"+month+"-"+i)!=0){
                        node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                    }else {
                        if(node=="<div class='calendar-content'>"){
                            node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                        }else {
                            node+="</div>";
                            node+="<div class='calendar-content'>";
                            node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                        }
                    }
                };
                //计算结尾天数
                if(getMyDay(year+"-"+month+"-"+30)!=6){
                    for(var i=1;i<7-parseInt(getMyDay(year+"-"+month+"-"+30));i++){
                        node+="<div abbr='"+(year)+"-"+(month+1)+"-"+(i)+"' class='next-month'>"+(i)+"</div>"
                    }
                }
                node+="</div>";
                node="<div class='calendar-head'><div>周日</div><div>周一</div><div>周二</div><div>周三</div><div>周四</div><div>周五</div> <div>周六</div></div>"+node;
                $(".calendar").html(node)

            }else if(month==2){
                node="<div class='calendar-content'>";
                //计算上月剩余天数
                if(getMyDay(year+"-"+month+"-"+1)!=0){
                    for(var t=getMyDay(year+"-"+month+"-"+1);t>0;t--){
                        node+="<div abbr='"+year+"-"+(month-1)+"-"+(32-t)+"' class='last-month'>"+(32-t)+"</div>"
                    }
                }
                //计算本月天数
                for(var i=1;i<=28;i++){
                    if(getMyDay(year+"-"+month+"-"+i)!=0){
                        node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                    }else {
                        if(node=="<div class='calendar-content'>"){
                            node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                        }else {
                            node+="</div>";
                            node+="<div class='calendar-content'>";
                            node+="<div abbr='"+(year)+"-"+(month)+"-"+i+"' class='this-month'>"+i+"</div>"
                        }
                    }
                };
                //计算结尾天数
                if(getMyDay(year+"-"+month+"-"+28)!=6){
                    for(var i=1;i<7-parseInt(getMyDay(year+"-"+month+"-"+28));i++){
                        node+="<div abbr='"+(year)+"-"+(month+1)+"-"+(i)+"' class='next-month'>"+(i)+"</div>"
                    }
                }
                node+="</div>";
                node="<div class='calendar-head'><div>周日</div><div>周一</div><div>周二</div><div>周三</div><div>周四</div><div>周五</div> <div>周六</div></div>"+node;
                $(".calendar").html(node)
            }
        }
    }
    addE(nums);
    /*减年份*/
    $(".choose-year-top").on("click",function(){
        if(parseInt($(".achieve-year").html())>0){
            $(".achieve-year").html(parseInt($(".achieve-year").html())-1);
            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
        }else {
            $(".achieve-year").html(parseInt($(".achieve-year").html())-1);
            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
        }
        addE(nums);
    });
    /*加年份*/
    $(".choose-year-bottom").on("click",function(){
        $(".achieve-year").html(parseInt($(".achieve-year").html())+1);
        nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
        addE(nums);
    })
    /*减月份*/
    $(".choose-month-top").on("click",function(){
        if(parseInt($(".achieve-month").html())==1){
            $(".achieve-year").html(parseInt($(".achieve-year").html())-1);
            $(".achieve-month").html(12);
            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
        }else {
            $(".achieve-month").html(parseInt($(".achieve-month").html())-1);
            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
        }
        addE(nums);
    });
    /*加月份*/
    $(".choose-month-bottom").on("click",function(){
        if(parseInt($(".achieve-month").html())==12){
            $(".achieve-year").html(parseInt($(".achieve-year").html())+1);
            $(".achieve-month").html(1);
            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
        }else {
            $(".achieve-month").html(parseInt($(".achieve-month").html())+1);
            nums=$(".achieve-year").html()+"-"+$(".achieve-month").html()+"-"+1;
        }
        addE(nums);
    })
});
