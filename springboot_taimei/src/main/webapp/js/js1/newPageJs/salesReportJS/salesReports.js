$(function(){
    /*********************************************请求数据********************************************/
    var saveData="";
    var data={ "success": {"Arrv_Airpt_Cd_code":"LHW", "goNum":"HU7305","Dpt_AirPt_Cd":"广州", "backNum":"HU7306", "Dpt_AirPt_Cd_code":"CAN", "Arrv_Airpt_Cd":"兰州", "Pas_stp":"十堰", "Pas_stp_code":"WDS", "dateList":["2016-08-04","2016-08-06","2016-08-09","2016-08-11","2016-08-13","2016-08-16","2016-08-18","2016-08-20","2016-08-23","2016-08-25","2016-08-27","2016-08-30","2016-09-01","2016-09-03","2016-09-06","2016-09-08","2016-09-10"], "map":{"data_person":{"广州-十堰":{"yPrice":"1400","two_Tak_Ppt":"3","ful_Pce_Ppt":"2","nne_Dnt_Ppt":"0","eht_Five_Dnt_Ppt":"0","eht_Dnt_Ppt":"7","sen_Five_Dnt_Ppt":"16","sen_Dnt_Ppt":"21","six_Dnt_Ppt":"24","fve_Dnt_Ppt":"24","fur_Fve_Dnt_Ppt":"0","fur_Dnt_Ppt":"0","thr_Dnt_Ppt":"0","two_Dnt_Ppt":"0","sal_Tak_Ppt":"0","grp_Nbr":"0","pgs_Per_Cls":"97","avg_Dct":"73.89","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"dpt_AirPt_Cd":"CAN","arrv_Airpt_Cd":"WDS","flt_Nbr":"HU7305","section_ket_ine":"97.00"},"十堰-兰州":{"yPrice":"960","two_Tak_Ppt":"0","ful_Pce_Ppt":"0","nne_Dnt_Ppt":"0","eht_Five_Dnt_Ppt":"0","eht_Dnt_Ppt":"1","sen_Five_Dnt_Ppt":"0","sen_Dnt_Ppt":"0","six_Dnt_Ppt":"6","fve_Dnt_Ppt":"6","fur_Fve_Dnt_Ppt":"4","fur_Dnt_Ppt":"26","thr_Dnt_Ppt":"0","two_Dnt_Ppt":"0","sal_Tak_Ppt":"0","grp_Nbr":"48","pgs_Per_Cls":"91","avg_Dct":"36.87","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"dpt_AirPt_Cd":"WDS","arrv_Airpt_Cd":"LHW","flt_Nbr":"HU7305","section_ket_ine":"91.00"},"十堰-广州":{"yPrice":"1400","two_Tak_Ppt":"3","ful_Pce_Ppt":"4","nne_Dnt_Ppt":"0","eht_Five_Dnt_Ppt":"0","eht_Dnt_Ppt":"0","sen_Five_Dnt_Ppt":"5","sen_Dnt_Ppt":"22","six_Dnt_Ppt":"19","fve_Dnt_Ppt":"27","fur_Fve_Dnt_Ppt":"0","fur_Dnt_Ppt":"0","thr_Dnt_Ppt":"0","two_Dnt_Ppt":"0","sal_Tak_Ppt":"0","grp_Nbr":"0","pgs_Per_Cls":"80","avg_Dct":"66.20","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"dpt_AirPt_Cd":"WDS","arrv_Airpt_Cd":"CAN","flt_Nbr":"HU7306","section_ket_ine":"94.00"},"广州-兰州":{"yPrice":"2010","two_Tak_Ppt":"0","ful_Pce_Ppt":"0","nne_Dnt_Ppt":"0","eht_Five_Dnt_Ppt":"0","eht_Dnt_Ppt":"0","sen_Five_Dnt_Ppt":"0","sen_Dnt_Ppt":"0","six_Dnt_Ppt":"0","fve_Dnt_Ppt":"4","fur_Fve_Dnt_Ppt":"0","fur_Dnt_Ppt":"31","thr_Dnt_Ppt":"0","two_Dnt_Ppt":"0","sal_Tak_Ppt":"25","grp_Nbr":"0","pgs_Per_Cls":"60","avg_Dct":"38.58","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"dpt_AirPt_Cd":"CAN","arrv_Airpt_Cd":"LHW","flt_Nbr":"HU7305","section_ket_ine":"95.00"},"兰州-十堰":{"yPrice":"960","two_Tak_Ppt":"0","ful_Pce_Ppt":"0","nne_Dnt_Ppt":"0","eht_Five_Dnt_Ppt":"0","eht_Dnt_Ppt":"0","sen_Five_Dnt_Ppt":"1","sen_Dnt_Ppt":"0","six_Dnt_Ppt":"2","fve_Dnt_Ppt":"6","fur_Fve_Dnt_Ppt":"5","fur_Dnt_Ppt":"21","thr_Dnt_Ppt":"0","two_Dnt_Ppt":"0","sal_Tak_Ppt":"0","grp_Nbr":"23","pgs_Per_Cls":"58","avg_Dct":"35.85","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"dpt_AirPt_Cd":"LHW","arrv_Airpt_Cd":"WDS","flt_Nbr":"HU7306","section_ket_ine":"68.00"},"兰州-广州":{"yPrice":"2010","two_Tak_Ppt":"0","ful_Pce_Ppt":"0","nne_Dnt_Ppt":"0","eht_Five_Dnt_Ppt":"0","eht_Dnt_Ppt":"0","sen_Five_Dnt_Ppt":"0","sen_Dnt_Ppt":"0","six_Dnt_Ppt":"0","fve_Dnt_Ppt":"22","fur_Fve_Dnt_Ppt":"27","fur_Dnt_Ppt":"24","thr_Dnt_Ppt":"0","two_Dnt_Ppt":"0","sal_Tak_Ppt":"0","grp_Nbr":"0","pgs_Per_Cls":"73","avg_Dct":"46.78","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"dpt_AirPt_Cd":"LHW","arrv_Airpt_Cd":"CAN","flt_Nbr":"HU7306","section_ket_ine":"94.00"}},"date":{"8.27":{"goAndBack":{"set_Ktr_Ine":"1.19","stzsr":"433570.00","xssr":"120996.28","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"84.67"},"go":{"set_Ktr_Ine":"0.49","stzsr":"177990.0","xssr":"49671.63","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"79.67"},"back":{"set_Ktr_Ine":"0.70","stzsr":"255580.0","xssr":"71324.65","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"89.67"}},"8.30":{"goAndBack":{"set_Ktr_Ine":"0.91","stzsr":"334520.00","xssr":"93354.41","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"76.50"},"go":{"set_Ktr_Ine":"0.35","stzsr":"128060.0","xssr":"35737.67","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"60.67"},"back":{"set_Ktr_Ine":"0.56","stzsr":"206460.0","xssr":"57616.74","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"92.33"}},"9.01":{"goAndBack":{"set_Ktr_Ine":"0.69","stzsr":"243750.00","xssr":"68023.26","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"74.33"},"go":{"set_Ktr_Ine":"0.28","stzsr":"99000.0","xssr":"27627.91","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"61.33"},"back":{"set_Ktr_Ine":"0.41","stzsr":"144750.0","xssr":"40395.35","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"87.33"}},"9.03":{"goAndBack":{"set_Ktr_Ine":"0.97","stzsr":"353080.00","xssr":"98533.95","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"88.50"},"go":{"set_Ktr_Ine":"0.40","stzsr":"146380.0","xssr":"40850.23","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"85.67"},"back":{"set_Ktr_Ine":"0.57","stzsr":"206700.0","xssr":"57683.72","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"91.33"}},"9.06":{"goAndBack":{"set_Ktr_Ine":"0.96","stzsr":"352050.00","xssr":"98246.51","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"81.34"},"go":{"set_Ktr_Ine":"0.36","stzsr":"130870.0","xssr":"36521.86","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"77.67"},"back":{"set_Ktr_Ine":"0.60","stzsr":"221180.0","xssr":"61724.65","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"85.00"}},"9.08":{"goAndBack":{"set_Ktr_Ine":"1.02","stzsr":"372920.00","xssr":"60293.37","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"92.00"},"go":{"set_Ktr_Ine":"0.42","stzsr":"152655.0","xssr":"42601.40","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"92.33"},"back":{"set_Ktr_Ine":"0.60","stzsr":"220265.0","xssr":"17691.97","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"91.67"}},"9.10":{"goAndBack":{"set_Ktr_Ine":"1.18","stzsr":"432330.00","xssr":"120650.23","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"89.83"},"go":{"set_Ktr_Ine":"0.49","stzsr":"179090.0","xssr":"49978.60","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"94.33"},"back":{"set_Ktr_Ine":"0.69","stzsr":"253240.0","xssr":"70671.63","lcl_Dpt_Tm":0.0,"lcl_Arrv_Tm":0.0,"egs_Lod_Fts":"85.33"}}}}}};    //        url : 'http://192.168.18.4/restful/getDailyReportDataNew',
    //        dataType : 'jsonp',
    //        success : function(data) {
    //            if(data){
    //                mid(data)
    //            }
    //        },
    //        error : function() {
    //
    //        }
    //    }
    //);
    function mid(data){
        changew();
        deductive(data,0);//默认加载全部航线
        saveData=data;
        nodeData();
    }
    function nodeData(){
        $(".Flight-num").eq(0).html(saveData.success.goNum);
        $(".Flight-num").eq(1).html(saveData.success.backNum);
    }
/***********************************************canvas区域************************************/
    /***各种封装****/
    /*测各种块大小*/
    function infer(name){
        var infer=[];
        infer.push(parseFloat($(name).css("width").split("px")[0]));
        infer.push(parseFloat($(name).css("height").split("px")[0]));
        infer.push(parseFloat($(name).css("margin-top").split("px")[0]));
        infer.push(parseFloat($(name).css("left").split("px")[0]));
        return infer;
    }
    function changew(){
        /*计算中间块大小*/
        var Lwidth=infer(".sr-box-body-report")[0];
        var Zwidth=infer(".sr-box")[0];
        var Rwidth=infer(".sr-box-body-date")[0];
        var Swidth=Zwidth-Lwidth-Rwidth-2;
        $(".sr-box-body-chart").css("width",Swidth+"px");
        /*计算绘图区域大小*/
        var Cheight=infer(".sr-box-body-chart-income")[1]-infer(".p-height")[1]-infer(".p-height")[2]-infer(".d-height")[1];
        var Cwidth=infer(".graph-table")[0];
        $(".graph-table").css("height",Cheight);
        $("#income").attr({"width":Cwidth,"height":Cheight});
        $("#canvas").attr({"width":Cwidth,"height":Cheight});
        $("#graph-line").attr({"width":Cwidth,"height":Cheight});
    }
    $(window).resize(function(){
        mid(saveData);
    });
    changew();
    /*************************************************客量，折扣图********************************************/
    $("body").mouseup(function(){//鼠标松开取消事件绑定
        $("#income-set>li:nth-of-type(3)").unbind("mousemove");
    });
    var set=0;
    $("#income-set>li:nth-of-type(3)").on("mousedown",function(e){
        var left=infer("#income-set>li:nth-of-type(3)")[3];
        $("#income-set>li:nth-of-type(3)").mousemove(function(event){
            var b = event || window.event;
            $("#income-set>li:nth-of-type(3)").css({"left":left-e.screenX+b.screenX});
            if(infer("#income-set>li:nth-of-type(3)")[3]<50){
                if(infer("#income-set>li:nth-of-type(3)")[3]<left-2){
                    $("#income-set>li:nth-of-type(3)").unbind("mousemove");
                    $("#income-set>li:nth-of-type(3)").animate({
                        "left":"20"
                    });
                    set=data.success.goNum;
                    deductive(saveData,set);
                }else if(infer("#income-set>li:nth-of-type(3)")[3]>left+2){
                    $("#income-set>li:nth-of-type(3)").unbind("mousemove");
                    $("#income-set>li:nth-of-type(3)").animate({
                        "left":"92px"
                    });
                    set=0;
                    deductive(saveData,set);
                }
            }else if(infer("#income-set>li:nth-of-type(3)")[3]>50&&infer("#income-set>li:nth-of-type(3)")[3]<120){
                if(infer("#income-set>li:nth-of-type(3)")[3]<left-2){
                    $("#income-set>li:nth-of-type(3)").unbind("mousemove");
                    $("#income-set>li:nth-of-type(3)").animate({
                        "left":"17px"
                    });
                    set=data.success.goNum;
                    deductive(saveData,set);
                }else if(infer("#income-set>li:nth-of-type(3)")[3]>left+2){
                    $("#income-set>li:nth-of-type(3)").unbind("mousemove");
                    $("#income-set>li:nth-of-type(3)").animate({
                        "left":"165px"
                    });
                    set=data.success.backNum;
                    deductive(saveData,set);
                }
            }else {
                if(infer("#income-set>li:nth-of-type(3)")[3]<left-2){
                    $("#income-set>li:nth-of-type(3)").unbind("mousemove");
                    $("#income-set>li:nth-of-type(3)").animate({
                        "left":"92px"
                    });
                    set=0;
                    deductive(saveData,set);
                }else if(infer("#income-set>li:nth-of-type(3)")[3]>left+2){
                    $("#income-set>li:nth-of-type(3)").unbind("mousemove");
                    $("#income-set>li:nth-of-type(3)").animate({
                        "left":"165px"
                    });
                    set=data.success.backNum;
                    deductive(saveData,set);
                }
            }
        })
    });
    /*小标签*/
    function icon(ctx,come){
        var gBox=infer("#graph-line");
        ctx.save();//保存环境
        if(come=="go"){
            var data=[0.4,0.63];
            for(var i=0;i<data.length;i++){
                ctx.beginPath();
                ctx.fillStyle="#2a416b";
                ctx.fillRect(gBox[0]*data[i]+5,gBox[1]*0.5-3,3,6);
                ctx.translate(5,0);
                ctx.fillRect(gBox[0]*data[i]+4,gBox[1]*0.5-3,4,6);
                ctx.translate(10,0);
                ctx.moveTo(gBox[0]*data[i],gBox[1]*0.5);
                ctx.lineTo(gBox[0]*data[i],gBox[1]*0.5-3);
                ctx.lineTo(gBox[0]*data[i]+6,gBox[1]*0.5-3);
                ctx.lineTo(gBox[0]*data[i]+6,gBox[1]*0.5-6);
                ctx.lineTo(gBox[0]*data[i]+12,gBox[1]*0.5);
                ctx.lineTo(gBox[0]*data[i]+6,gBox[1]*0.5+6);
                ctx.lineTo(gBox[0]*data[i]+6,gBox[1]*0.5+3);
                ctx.lineTo(gBox[0]*data[i],gBox[1]*0.5+3);
                ctx.lineTo(gBox[0]*data[i],gBox[1]*0.5);
                ctx.fill();
            }
        }else if(come=="goandback"){
            ctx.beginPath();
            ctx.fillStyle="#2a416b";
            ctx.fillRect(gBox[0]*0.4+4,gBox[1]*0.5-3,3,6);
            ctx.translate(5,0);
            ctx.fillRect(gBox[0]*0.4+4,gBox[1]*0.5-3,4,6);
            ctx.translate(10,0);
            ctx.moveTo(gBox[0]*0.4,gBox[1]*0.5);
            ctx.lineTo(gBox[0]*0.4,gBox[1]*0.5-3);
            ctx.lineTo(gBox[0]*0.4+6,gBox[1]*0.5-3);
            ctx.lineTo(gBox[0]*0.4+6,gBox[1]*0.5-6);
            ctx.lineTo(gBox[0]*0.4+12,gBox[1]*0.5);
            ctx.lineTo(gBox[0]*0.4+6,gBox[1]*0.5+6);
            ctx.lineTo(gBox[0]*0.4+6,gBox[1]*0.5+3);
            ctx.lineTo(gBox[0]*0.4,gBox[1]*0.5+3);
            ctx.lineTo(gBox[0]*0.4,gBox[1]*0.5);
            ctx.fill();
            ctx.beginPath();
            ctx.translate(-5,0);
            ctx.moveTo(gBox[0]*0.65,gBox[1]*0.5);
            ctx.lineTo(gBox[0]*0.65+6,gBox[1]*0.5-6);
            ctx.lineTo(gBox[0]*0.65+6,gBox[1]*0.5-3);
            ctx.lineTo(gBox[0]*0.65+12,gBox[1]*0.5-3);
            ctx.lineTo(gBox[0]*0.65+12,gBox[1]*0.5+3);
            ctx.lineTo(gBox[0]*0.65+6,gBox[1]*0.5+3);
            ctx.lineTo(gBox[0]*0.65+6,gBox[1]*0.5+6);
            ctx.lineTo(gBox[0]*0.65,gBox[1]*0.5);
            ctx.fillStyle="#2a416b";
            ctx.fillRect(gBox[0]*0.65+14,gBox[1]*0.5-3,3,6);
            ctx.fillRect(gBox[0]*0.65+19,gBox[1]*0.5-3,4,6);
            ctx.fill();
        }else if(come=="back"){
            var data=[0.4,0.65];
            for(var i=0;i<data.length;i++){
                ctx.beginPath();
                ctx.translate(3,0);
                ctx.moveTo(gBox[0]*data[i],gBox[1]*0.5);
                ctx.lineTo(gBox[0]*data[i]+6,gBox[1]*0.5-6);
                ctx.lineTo(gBox[0]*data[i]+6,gBox[1]*0.5-3);
                ctx.lineTo(gBox[0]*data[i]+12,gBox[1]*0.5-3);
                ctx.lineTo(gBox[0]*data[i]+12,gBox[1]*0.5+3);
                ctx.lineTo(gBox[0]*data[i]+6,gBox[1]*0.5+3);
                ctx.lineTo(gBox[0]*data[i]+6,gBox[1]*0.5+6);
                ctx.lineTo(gBox[0]*data[i],gBox[1]*0.5);
                ctx.fillStyle="#2a416b";
                ctx.fillRect(gBox[0]*data[i]+14,gBox[1]*0.5-3,3,6);
                ctx.fillRect(gBox[0]*data[i]+20,gBox[1]*0.5-3,4,6);
                ctx.fill();
            }
        }
        ctx.restore();//恢复环境

    }
    /*画图*/
    function deductive(data,exp){
        var gBox=infer("#graph-line");
        var ctx=document.getElementById('graph-line').getContext('2d');//获取对象\
        ctx.clearRect(0,0,gBox[0],gBox[1]);
        ctx.beginPath();
        //X轴
        //1段
        ctx.moveTo(gBox[0]*0.07,gBox[1]*0.5);
        ctx.lineTo(gBox[0]*0.4,gBox[1]*0.5);
        //2段
        ctx.moveTo(gBox[0]*0.45,gBox[1]*0.5);
        ctx.lineTo(gBox[0]*0.65,gBox[1]*0.5);
        //3段
        ctx.moveTo(gBox[0]*0.7,gBox[1]*0.5);
        ctx.lineTo(gBox[0]*0.9,gBox[1]*0.5);
        /************无经停***************/
        //Y轴
        ctx.moveTo(gBox[0]*0.20,0);
        ctx.lineTo(gBox[0]*0.20,gBox[1]);
        ctx.strokeStyle="#2c4170";
        ctx.lineWidth="1";
        //城市文字
        ctx.fillStyle="white";
        ctx.font="12px 微软雅黑";
        ctx.fillText("客量",gBox[0]*0.08,gBox[1]*0.25);
        ctx.fillText("平均折扣",gBox[0]*0.08,gBox[1]*0.75);
        ctx.fillText(data.success.Dpt_AirPt_Cd,gBox[0]*0.3-10,gBox[1]*0.56);
        if(data.success.Pas_stp){
            ctx.fillText(data.success.Pas_stp,gBox[0]*0.55-10,gBox[1]*0.56);
        }
        ctx.fillText(data.success.Arrv_Airpt_Cd,gBox[0]*0.8-10,gBox[1]*0.56);
        ctx.stroke();
        //三字码
        ctx.beginPath();
        ctx.fillStyle="white";
        ctx.font="20px 微软雅黑";
        ctx.fillText(data.success.Dpt_AirPt_Cd_code,gBox[0]*0.3-20,gBox[1]*0.47);
        if(data.success.Pas_stp){
            ctx.fillText(data.success.Pas_stp_code,gBox[0]*0.55-20,gBox[1]*0.47);
        }
        ctx.fillText(data.success.Arrv_Airpt_Cd_code,gBox[0]*0.8-20,gBox[1]*0.47);
        /*调用函数*/
        icon(ctx,"goandback");//调用icon函数
        lines(data,exp);
    }
    /**********************************图线***************************************/
    /*绘制函数*/
    var point={};//数据
    function draw(pro,tag,num,p){
        var gBox=infer("#graph-line");
        var ctx=document.getElementById('graph-line').getContext('2d');//获取对象
        ctx.beginPath();
        ctx.moveTo(tag.Bl[0],gBox[1]*tag.Yt);
        ctx.lineTo(tag.Bl[1],pro);
        ctx.lineTo(tag.Bl[2],gBox[1]*tag.Yt);
        ctx.strokeStyle=tag.color;
        ctx.lineWidth="3";
        ctx.lineCap="round";
        ctx.stroke();
        if(num==1){
            ctx.beginPath();
            ctx.fillStyle=tag.color;
            ctx.arc(tag.Bl[1],pro,5,0,2*Math.PI);
            ctx.fill();
        }else if(num==0){
            ctx.beginPath();
            ctx.fillStyle=tag.color;
            ctx.rect(tag.Bl[1]-5,pro-5,10,10);
            ctx.fill();
        }
        /*数据*/
        if(!point[p]){
            point[p]=[];
            point[p].push(tag.Bl[1]);
            point[p].push(pro);
        }else {
            point[p].push(tag.Bl[1]);
            point[p].push(pro);
        }
    }
    /*控制函数*/
    function lines(data,swit){
        var gBox=infer("#graph-line");
        /*去最大值做Y轴参照*/
        var nums=data.success.map.data_person;
        var maxp=0;
        var maxt=0;
        for(var p in nums){
            if(nums[p].pgs_Per_Cls>maxp){
                maxp=nums[p].pgs_Per_Cls;
            }
            if(nums[p].avg_Dct>maxt){
                maxt=nums[p].avg_Dct;
            }
        }
        /*经停航线*/
        if(data.success.Pas_stp){
            /*客座率*/
            if(swit==0){
                var tData={color:"",Bl:[],Yt:0.38};
                for(var p in nums){
                    /*直段线*/
                    var Ycoor=(gBox[1]*0.38*nums[p].pgs_Per_Cls)/maxp;
                    Ycoor=gBox[1]*0.38-Ycoor+8;
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].dpt_AirPt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#582218";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,1,p);
                    }
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].arrv_Airpt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#d6552e";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,0,p);
                    }
                    /*短段1*/
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].dpt_AirPt_Cd&&data.success.Pas_stp_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#1c4b81";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.3+(gBox[0]*0.55-gBox[0]*0.3)/2,gBox[0]*0.55];
                        draw(Ycoor,tData,1,p);
                    }
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].arrv_Airpt_Cd&&data.success.Pas_stp_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#1270c7";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.3+(gBox[0]*0.55-gBox[0]*0.3)/2,gBox[0]*0.55];
                        draw(Ycoor,tData,0,p);
                    }
                    /*短2*/
                    if(data.success.Pas_stp_code==nums[p].dpt_AirPt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#276c73";
                        tData.Bl=[gBox[0]*0.55,gBox[0]*0.55+(gBox[0]*0.8-gBox[0]*0.55)/2,gBox[0]*0.8];
                        draw(Ycoor,tData,1,p);
                    }
                    if(data.success.Pas_stp_code==nums[p].arrv_Airpt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#64c8d8";
                        tData.Bl=[gBox[0]*0.55,gBox[0]*0.55+(gBox[0]*0.8-gBox[0]*0.55)/2,gBox[0]*0.8];
                        draw(Ycoor,tData,0,p);
                    }
                };
                /*折扣*/
                var tData={color:"",Bl:[],Yt:0.62};
                for(var p in nums){
                    /*直段线*/
                    var Ycoor=(gBox[1]*0.38*nums[p].avg_Dct)/maxt;
                    Ycoor=gBox[1]*0.62+Ycoor-13;
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].dpt_AirPt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#582218";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,1,p);
                    }
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].arrv_Airpt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#d6552e";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,0,p);
                    }
                    /*短段1*/
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].arrv_Airpt_Cd&&data.success.Pas_stp_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#1270c7";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.3+(gBox[0]*0.55-gBox[0]*0.3)/2,gBox[0]*0.55];
                        draw(Ycoor,tData,0,p);
                    }
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].dpt_AirPt_Cd&&data.success.Pas_stp_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#1c4b81";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.3+(gBox[0]*0.55-gBox[0]*0.3)/2,gBox[0]*0.55];
                        draw(Ycoor,tData,1,p);
                    }
                    /*短2*/
                    if(data.success.Pas_stp_code==nums[p].dpt_AirPt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#276c73";
                        tData.Bl=[gBox[0]*0.55,gBox[0]*0.55+(gBox[0]*0.8-gBox[0]*0.55)/2,gBox[0]*0.8];
                        draw(Ycoor,tData,1,p);
                    }
                    if(data.success.Pas_stp_code==nums[p].arrv_Airpt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#64c8d8";
                        tData.Bl=[gBox[0]*0.55,gBox[0]*0.55+(gBox[0]*0.8-gBox[0]*0.55)/2,gBox[0]*0.8];
                        draw(Ycoor,tData,0,p);
                    }
                };
            }else if(swit==data.success.goNum){
                var tData={color:"",Bl:[],Yt:0.38};
                for(var p in nums){
                    /*直段线*/
                    var Ycoor=(gBox[1]*0.38*nums[p].pgs_Per_Cls)/maxp;
                    Ycoor=gBox[1]*0.38-Ycoor+8;
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].dpt_AirPt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#582218";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,1,p);
                    }
                    /*短段1*/
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].dpt_AirPt_Cd&&data.success.Pas_stp_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#1c4b81";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.3+(gBox[0]*0.55-gBox[0]*0.3)/2,gBox[0]*0.55];
                        draw(Ycoor,tData,1,p);
                    }
                    /*短2*/
                    if(data.success.Pas_stp_code==nums[p].dpt_AirPt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#276c73";
                        tData.Bl=[gBox[0]*0.55,gBox[0]*0.55+(gBox[0]*0.8-gBox[0]*0.55)/2,gBox[0]*0.8];
                        draw(Ycoor,tData,1,p);
                    }
                };
                /*折扣*/
                var tData={color:"",Bl:[],Yt:0.62};//////////////////////////////////////////////////
                for(var p in nums){
                    /*直段线*/
                    var Ycoor=(gBox[1]*0.38*nums[p].avg_Dct)/maxt;
                    Ycoor=gBox[1]*0.62+Ycoor-13;
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].dpt_AirPt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#582218";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,1,p);
                    }
                    /*短段1*/
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].dpt_AirPt_Cd&&data.success.Pas_stp_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#1c4b81";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.3+(gBox[0]*0.55-gBox[0]*0.3)/2,gBox[0]*0.55];
                        draw(Ycoor,tData,1,p);
                    }
                    /*短2*/
                    if(data.success.Pas_stp_code==nums[p].dpt_AirPt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#276c73";
                        tData.Bl=[gBox[0]*0.55,gBox[0]*0.55+(gBox[0]*0.8-gBox[0]*0.55)/2,gBox[0]*0.8];
                        draw(Ycoor,tData,1,p);
                    }
                };
            }else if(swit==data.success.backNum){
                var tData={color:"",Bl:[],Yt:0.38};
                for(var p in nums){
                    /*直段线*/
                    var Ycoor=(gBox[1]*0.38*nums[p].pgs_Per_Cls)/maxp;
                    Ycoor=gBox[1]*0.38-Ycoor+8;
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].arrv_Airpt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#d6552e";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,0,p);
                    }
                    /*短段1*/
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].arrv_Airpt_Cd&&data.success.Pas_stp_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#1270c7";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.3+(gBox[0]*0.55-gBox[0]*0.3)/2,gBox[0]*0.55];
                        draw(Ycoor,tData,0,p);
                    }
                    /*短2*/
                    if(data.success.Pas_stp_code==nums[p].arrv_Airpt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#64c8d8";
                        tData.Bl=[gBox[0]*0.55,gBox[0]*0.55+(gBox[0]*0.8-gBox[0]*0.55)/2,gBox[0]*0.8];
                        draw(Ycoor,tData,0,p);
                    }
                };
                /*折扣*/
                var tData={color:"",Bl:[],Yt:0.62};//////////////////////////////////////////////////
                for(var p in nums){
                    /*直段线*/
                    var Ycoor=(gBox[1]*0.38*nums[p].avg_Dct)/maxt;
                    Ycoor=gBox[1]*0.62+Ycoor-13;
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].arrv_Airpt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#d6552e";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,0,p);
                    }
                    /*短段1*/
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].arrv_Airpt_Cd&&data.success.Pas_stp_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#1270c7";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.3+(gBox[0]*0.55-gBox[0]*0.3)/2,gBox[0]*0.55];
                        draw(Ycoor,tData,0,p);
                    }
                    /*短2*/
                    if(data.success.Pas_stp_code==nums[p].arrv_Airpt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#64c8d8";
                        tData.Bl=[gBox[0]*0.55,gBox[0]*0.55+(gBox[0]*0.8-gBox[0]*0.55)/2,gBox[0]*0.8];
                        draw(Ycoor,tData,0,p);
                    }
                };
            }
        }else {//非经停航线
            if(swit==0){
                var tData={color:"",Bl:[],Yt:0.38};
                for(var p in nums){
                    /*直段线*/
                    var Ycoor=(gBox[1]*0.38*nums[p].pgs_Per_Cls)/maxp;
                    Ycoor=gBox[1]*0.38-Ycoor+8;
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].dpt_AirPt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#582218";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,1,p);
                    }
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].arrv_Airpt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#d6552e";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,0,p);
                    }
                }
                var tData={color:"",Bl:[],Yt:0.62};
                for(var p in nums){
                    /*折扣*/
                    var Ycoor=(gBox[1]*0.38*nums[p].avg_Dct)/maxt;
                    Ycoor=gBox[1]*0.62+Ycoor-13;
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].dpt_AirPt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#582218";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,1,p);
                    }
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].arrv_Airpt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#d6552e";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,0,p);
                    }
                };
            }else if(swit==data.success.goNum){
                var tData={color:"",Bl:[],Yt:0.38};
                for(var p in nums){
                    /*直段线*/
                    var Ycoor=(gBox[1]*0.38*nums[p].pgs_Per_Cls)/maxp;
                    Ycoor=gBox[1]*0.38-Ycoor+8;
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].dpt_AirPt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#582218";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,1,p);
                    }
                }
                var tData={color:"",Bl:[],Yt:0.62};
                for(var p in nums){
                    /*折扣*/
                    var Ycoor=(gBox[1]*0.38*nums[p].avg_Dct)/maxt;
                    Ycoor=gBox[1]*0.62+Ycoor-13;
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].dpt_AirPt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].arrv_Airpt_Cd){
                        tData.color="#582218";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,1,p);
                    }
                };
            }else if(swit==data.success.backNum){}
                var tData={color:"",Bl:[],Yt:0.38};
                for(var p in nums){
                    /*直段线*/
                    var Ycoor=(gBox[1]*0.38*nums[p].pgs_Per_Cls)/maxp;
                    Ycoor=gBox[1]*0.38-Ycoor+8;
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].arrv_Airpt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#d6552e";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,0,p);
                    }
                }
                var tData={color:"",Bl:[],Yt:0.62};
                for(var p in nums){
                    /*折扣*/
                    var Ycoor=(gBox[1]*0.38*nums[p].avg_Dct)/maxt;
                    Ycoor=gBox[1]*0.62+Ycoor-13;
                    if(data.success.Dpt_AirPt_Cd_code==nums[p].arrv_Airpt_Cd&&data.success.Arrv_Airpt_Cd_code==nums[p].dpt_AirPt_Cd){
                        tData.color="#d6552e";
                        tData.Bl=[gBox[0]*0.3,gBox[0]*0.55,gBox[0]*0.8];
                        draw(Ycoor,tData,0,p);
                    }
                };
        }
    };
    mid(data);
    /*点线*/
    function drawDashedLine(context, x1, y1, x2, y2, dashLength) {
        dashLength = dashLength === undefined ? 5 : dashLength;
        var deltaX = x2 - x1;
        var deltaY = y2 - y1;
        var numDashes = Math.floor(
            Math.sqrt(deltaX * deltaX + deltaY * deltaY) / dashLength);
        for (var i=0; i < numDashes; ++i) {
            context[ i % 2 === 0 ? 'moveTo' : 'lineTo' ]
            (x1 + (deltaX / numDashes) * i, y1 + (deltaY / numDashes) * i);
        }
        context.stroke();
    };
    /*获取鼠标点击事件*/
    var nusname="";//暂存当前移入的航线
    $("#graph-line").on("mousemove",function(e){
        var ctx=document.getElementById('graph-line').getContext('2d');//获取对象
        var cleX=e.offsetX;
        var cleY=e.offsetY;
        for(var key in point){
            if(point[key][0]-5<cleX&&point[key][0]+5>cleX&&point[key][1]-5<cleY&&point[key][1]+5>cleY){
                for( var e in saveData.success.map.data_person){
                    if(e==key){
                        if(set==0){
                            var people=saveData.success.map.data_person[key].pgs_Per_Cls;
                            $("body").css({cursor:"pointer"});
                            ctx.beginPath();
                            ctx.lineWidth="0.3";
                            ctx.strokeStyle="#2c416e";
                            drawDashedLine(ctx,infer("#graph-line")[0]*0.20,point[key][1],point[key][0],point[key][1],6);
                            $("#matter").html(people+"人").css({"opacity":"1",left:point[key][0],top:point[key][1],width:"60px",height:"24px"});
                            nusname=key;
                        }else if(saveData.success.map.data_person[e].flt_Nbr==set){
                            var people=saveData.success.map.data_person[key].pgs_Per_Cls;
                            $("body").css({cursor:"pointer"});
                            ctx.beginPath();
                            ctx.lineWidth="0.3";
                            ctx.strokeStyle="#2c416e";
                            drawDashedLine(ctx,infer("#graph-line")[0]*0.20,point[key][1],point[key][0],point[key][1],6);
                            $("#matter").html(people+"人").css({"opacity":"1",left:point[key][0],top:point[key][1],width:"60px",height:"24px"});
                            nusname=key;
                        }
                    }
                }
            }else if(point[key][2]-5<cleX&&point[key][2]+5>cleX&&point[key][3]-5<cleY&&point[key][3]+5>cleY){
                for( var e in saveData.success.map.data_person){
                    if(e==key){
                        if(set==0){
                            var dis=saveData.success.map.data_person[key].avg_Dct;
                            $("body").css({cursor:"pointer"});
                            ctx.beginPath();
                            ctx.lineWidth="0.3";
                            ctx.strokeStyle="#2c416e";
                            drawDashedLine(ctx,infer("#graph-line")[0]*0.20,point[key][3],point[key][2],point[key][3],6);
                            $("#matter").html(dis+"%").css({"opacity":"1",left:point[key][2],top:point[key][3],width:"60px",height:"24px"});
                            nusname=key;
                        }else if(saveData.success.map.data_person[e].flt_Nbr==set){
                            var dis=saveData.success.map.data_person[key].avg_Dct;
                            $("body").css({cursor:"pointer"});
                            ctx.beginPath();
                            ctx.lineWidth="0.3";
                            ctx.strokeStyle="#2c416e";
                            drawDashedLine(ctx,infer("#graph-line")[0]*0.20,point[key][3],point[key][2],point[key][3],6);
                            $("#matter").html(dis+"%").css({"opacity":"1",left:point[key][2],top:point[key][3],width:"60px",height:"24px"});
                            nusname=key;
                        }
                    }
                }
            } else {
                if(nusname!=""){//关闭信息框
                    if(nusname==key){
                        $("body").css({cursor:""});
                        $("#matter").html("").css({"opacity":"0",left:point[key][0],top:point[key][1],width:"0px",height:"0px"});
                        var gBox=infer("#graph-line");
                        ctx.clearRect(0,0,gBox[0],gBox[1]);
                        deductive(saveData,set);
                    }
                }
            }
        }
    });
    /**********************绘图函数*********echarts******************/
    function theCurve(name){
        var dom = document.getElementById(name);
        var myChart = echarts.init(dom);
        option = {
            grid: {
                top:'20%',
                left: '3%',
                right: '20%',
                bottom: '7%',
                containLabel: true
            },
            toolbox: {
                show:false,
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: ['9.8','9.9','9.10','9.11','9.12','9.13','9.14'],
                silent:true,
                axisLine:{
                    show:true,
                    lineStyle:{
                        color:"#2e416c"
                    }
                },
                splitLine:{
                    show:true,
                    lineStyle:{
                        color:"#304b76",
                        opacity:0.6
                    }
                }
            },
            yAxis: {
                type: 'value',
                axisLine:{
                    show:false,
                    onZero:false
                },
                axisLabel:{
                    show:false
                },
                splitLine:{
                    show:false
                },
                axisTick:{
                    show:false
                }
            },
            series: [
                {
                    name:'合计',
                    smooth: true,
                    type:'line',
                    symbolSize:0,
                    showSymbol:false,
                    markPoint:{
                        symbol:"circle",
                        silent:true,
                        symbolSize:15,
                        data:[ {
                            yAxis: 320,
                            x: '80%'
                        }],
                        label:{
                            normal:{
                                show:false
                            }
                        },
                        itemStyle:{
                            normal:{
                                color:"#bd5741"
                            }
                        }
                    },
                    lineStyle: {
                        normal: {
                            width: 4,
                            color: '#bd5741'
                        }
                    },
                    data:[320, 332, 301, 334, 390, 330, 320]
                },
                {
                    name:'去程',
                    type:'line',
                    smooth: true,
                    symbolSize:0,
                    showSymbol:false,
                    markPoint:{
                        symbol:"circle",
                        symbolSize:15,
                        silent:true,
                        data:[{
                            name: '固定 x 像素位置',
                            yAxis: 210,
                            x: '80%',
                            symbolSize:15,
                            label:{
                                normal:{
                                    show:false
                                }
                            },
                            itemStyle:{
                                normal:{
                                    color:"#63c7d7"
                                }
                            }
                        }]

                    },
                    lineStyle: {
                        normal: {
                            width: 4,
                            color: '#63c7d7'
                        }
                    },
                    data:[120, 132, 101, 134, 90, 230, 210]
                },
                {
                    name:'返程',
                    type:'line',
                    smooth: true,
                    symbolSize:0,
                    showSymbol:false,
                    markPoint:{
                        symbol:"circle",
                        symbolSize:15,
                        silent:true,
                        data:[{
                            name: '固定 x 像素位置',
                            yAxis: 410,
                            x: '80%'
                        }],
                        label:{
                            normal:{
                                show:false
                            }
                        },
                        itemStyle:{
                            normal:{
                                color:"#1b72bf"
                            }
                        }
                    },
                    lineStyle: {
                        normal: {
                            width: 4,
                            color: '#1b72bf'
                        }
                    },
                    data:[150, 232, 201, 154, 190, 330, 410]
                }
            ]
        };
        myChart.setOption(option,true);
    }
    /*4图*/
    function dailyReport(id,dayData){
        var xData = [];
        var yPrice = [];
        var totalData = [];
        var colorArray = ['#1470c5','#d85430','#64c7d9','#1c4b81','#662210','#246b71'];
        var iter = 0;
        var datainfo = new Array(15);
        var salesreportmap = dayData.data_person;
        for(var key in salesreportmap ){
            xData.push({name:key,textStyle:{color:'#fff'},icon:'line'});
            var obj = salesreportmap[key];
            yPrice.push('\n￥:'+parseFloat(obj.yPrice).toFixed(0)+'元');
            datainfo[0] = parseInt(obj.two_Tak_Ppt);
            datainfo[1] = parseInt(obj.ful_Pce_Ppt);
            datainfo[2] = parseInt(obj.nne_Dnt_Ppt);
            datainfo[3] = parseInt(obj.eht_Five_Dnt_Ppt);
            datainfo[4] = parseInt(obj.eht_Dnt_Ppt);
            datainfo[5] = parseInt(obj.sen_Five_Dnt_Ppt);
            datainfo[6] = parseInt(obj.sen_Dnt_Ppt);
            datainfo[7] = parseInt(obj.six_Dnt_Ppt);
            datainfo[8] = parseInt(obj.fve_Dnt_Ppt);
            datainfo[9] = parseInt(obj.fur_Fve_Dnt_Ppt);
            datainfo[10] = parseInt(obj.fur_Dnt_Ppt);
            datainfo[11] = parseInt(obj.thr_Dnt_Ppt);
            datainfo[12] = parseInt(obj.two_Dnt_Ppt);
            datainfo[13] = parseInt(obj.grp_Nbr);
            datainfo[14] = parseInt(obj.sal_Tak_Ppt);
            totalData.push({name:key,type:'bar',stack: 'sum',barWidth : 10,itemStyle : { normal: {label : {show: false, position: 'insideRight'},color: colorArray[iter]}},data:datainfo});
            datainfo = new Array(15);
            iter++;
        }
        var lastOne = totalData[totalData.length-1];
        totalData[totalData.length-1] = {name:lastOne.name,type:'bar',stack: 'sum',barWidth :10,itemStyle : {normal: {label : {show: true,position: 'top',color:lastOne.itemStyle.color,formatter: function (params) {for (var i = 0, l = option.xAxis[0].data.length; i < l; i++) {if (option.xAxis[0].data[i] == params.name) {var sum = params.value;for(var j=0;j<option.series.length-1;j++){sum += option.series[j].data[i];}return sum>0?'合计\n'+sum+'人':'';}}},textStyle: {color: '#fff'}}}},data:lastOne.data}
        var dom = document.getElementById(id);
        //用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
        var myChart = echarts.init(dom);
        option = {
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            backgroundColor:'#13233d',
            legend: {
                x:'6%',
                y:'top',
                height: dom.style.height,
                width: dom.style.width,
                data:xData,
                formatter: function (data){
                    for(var i=0;i<xData.length;i++){
                        if(data==xData[i].name){
                            return data+yPrice[i];
                        }
                    }
                }
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    axisLabel:{
                        interval: 0
                    },
                    data : ['F\n200%','Y\n100%','B\n90%','H\n85%','K\n80%','L\n75%','M\n70%','Q\n60%','X\n50%','U\n45%','E\n40%','30%','T\n20%','G','特殊'],
                    axisLine:{
                        show:true,
                        lineStyle:{
                            color:"#fff"
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    show : false
                }
            ],
            series : totalData
        };
        myChart.setOption(option,true);
    }
    function coll(){
        var name="canvas";
        theCurve(name);
        var name="income";
        theCurve(name);
        dailyReport('dayliReport',data.success.map);
    }
    /*窗口变化自适应*/
    window.onresize=function(){
        coll();
    };
//    coll();//页面加载初始化
});