function calls(){
    if($(".set-month").css("display")=="block"){
        setTimeout(function(){
            var das=$(".prevData").text()+"-"+(parseInt($(".Pset").text())<10?('0'+$(".Pset").text()):$(".Pset").text())+" - "+$(".nextData").text()+"-"+(parseInt($(".Nset").text())<10?('0'+$(".Nset").text()):$(".Nset").text());
            $("#reservation")[0].value=das;
        },50);
    }
}
$(function(){
    var myDate = new Date();
    var month=parseInt(myDate.getMonth());
    var data1="";
    var data2="";
    $("body").on("click","div",function(){
        if($(this).hasClass("go-day")){
            if(data1!=""){
                $(".input-mini").eq(0)[0].value=data1;
                $(".input-mini").eq(1)[0].value=data2;
            }
            $(".set-month").css("display","none");
            $(this).addClass("go-set").siblings().removeClass("go-set");
        }else if($(this).hasClass("go-month")){
            data1=$(".input-mini").eq(0).val();
            data2=$(".input-mini").eq(1).val();
            if($(".Pset").length==0){
                $(".list-Pdays").eq(0).click();
                $(".list-Ndays").eq(month).click();
            }else {
                $(".Pset").click();
                $(".Nset").click();
            }
            $(".set-month").css("display","block");
            $(this).addClass("go-set").siblings().removeClass("go-set");
        }else if($(this).hasClass("fa-arrow-left")){
            $(this).next().text(parseInt($(this).next().text())-1);
            if(parseInt($(".nextData").text())==parseInt($(".prevData").text())){
                $(".display-le").css("display","none");
                $(".display-ri").css("display","none");
                $(".nextData").css("margin-left","32px");
                var p=parseInt($(".Pset").text());
                var n=parseInt($(".Nset").text());
                $(".list-Pdays").eq(p-1).click();
                $(".list-Ndays").eq(11).click();
            }else {
                $(".nextData").css("margin-left","0px");
                $(".display-le").css("display","block");
                $(".display-ri").css("display","block");
                $(".middles").removeClass("middles");
                $(".Pset").click();
                $(".Nset").click();
            }
        }else if($(this).hasClass("fa-arrow-right")){
            $(this).prev().text(parseInt($(this).prev().text())+1);
            if(parseInt($(".nextData").text())==parseInt($(".prevData").text())){
                $(".display-le").css("display","none");
                $(".display-ri").css("display","none");
                $(".nextData").css("margin-left","32px");
                var p=parseInt($(".Pset").text());
                var n=parseInt($(".Nset").text());
                $(".list-Pdays").eq(p-1).click();
                $(".list-Ndays").eq(11).click();
            }else {
                $(".nextData").css("margin-left","0px");
                $(".display-le").css("display","block");
                $(".display-ri").css("display","block");
                $(".middles").removeClass("middles");
                $(".Pset").click();
                $(".Nset").click();
            }
        }else if($(this).hasClass("list-Pdays")){
            Initialize($(this));
            Initialize2($(".Nset"));
            var das=$(".prevData").text()+"-"+$(".Pset").text();
            $(".input-mini").eq(0)[0].value=das;

        }else if($(this).hasClass("list-Ndays")){
            Initialize2($(this));
            Initialize($(".Pset"));
            var das1=$(".nextData").text()+"-"+$(".Nset").text();
            $(".input-mini").eq(1)[0].value=das1;
        }
    });
    function Initialize2(th){
        if(parseInt($(".nextData").text())==parseInt($(".prevData").text())){
            if(parseInt(th.text())>=parseInt($(".Pset").text())){
                th.addClass("Nset").siblings().removeClass("Nset excessive middles");
                for(var i=(parseInt($(".Pset").text()));i<=(parseInt(th.text()));i++){
                    if(i==parseInt($(".Pset").text())){
                        $(".list-Ndays").eq(i-1).addClass("middles");
                    }else {
                        $(".list-Ndays").eq(i-1).addClass("excessive");
                    };
                };
            }
        }else {
            th.addClass("Nset").siblings().removeClass("Nset excessive");
            $(".Nset").prevAll().addClass("excessive");
        }
    };
    function Initialize(th){
        if(parseInt($(".nextData").text())==parseInt($(".prevData").text())){
            if(parseInt(th.text())<=parseInt($(".Nset").text())){
                th.addClass("Pset").siblings().removeClass("Pset excessive middles");
                for(var i=parseInt(th.text());i<parseInt($(".Nset").text());i++){
                    if(i==parseInt($(".Nset").text())-1){
                        $(".list-Pdays").eq(i).addClass("middles");
                    }else {
                        $(".list-Pdays").eq(i).addClass("excessive");
                    }
                };
            }
        }else {
            th.addClass("Pset").siblings().removeClass("Pset excessive");
            $(".Pset").nextAll().addClass("excessive");
        }
    }
});