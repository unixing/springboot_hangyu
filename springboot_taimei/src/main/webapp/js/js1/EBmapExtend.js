// EBmapExtend  -扩展百度地图图形算法

var EBmapExtend = "";
var EBmapExtendConstruction = function (cs_map) {
    var EBmapExtendObj = new Object();
    EBmapExtendObj.map = cs_map;  //百度地图实例
    EBmapExtendObj.data = [];  //实例数据
    EBmapExtendObj.compare = [];  //实例数据
    EBmapExtendObj.removeCompare = function (num) { //删除数据
        if(num != undefined){
            EBmapExtendObj.compare.splice(num,1);
        }else{
            EBmapExtendObj.compare = []
        };
        EBmapExtendObj.resetCompare();
    };
    EBmapExtendObj.resetCompare = function (num) {
        var arr0 = [];
        var arr1 = [];
        switch(measureData.type){
            case 0 :
                if(measureData.data.s != "")arr1.push(codeScu(measureData.data.s).iata);
                if(measureData.data.j != "")arr1.push(codeScu(measureData.data.j).iata);
                if(measureData.data.z != "")arr1.push(codeScu(measureData.data.z).iata);
                break;
            case 1:
                if(measureData.data.s != "")arr1.push(codeScu(measureData.data.s).iata);
                if(measureData.data.z != "")arr1.push(codeScu(measureData.data.z).iata);
                break;
            case 2:
                if(measureData.data.s != "")arr1.push(codeScu(measureData.data.s).iata);
                if(measureData.data.j != "")arr1.push(codeScu(measureData.data.j).iata);
                if(measureData.data.z != "")arr1.push(codeScu(measureData.data.z).iata);
                break;
        }
        EBmapExtendObj.compare.forEach(function(val){
            if(arr0.indexOf(val) == -1 && arr1.indexOf(val) == -1){
                arr0.push(val);
            };
        });
        EBmapExtendObj.compare = arr0;

        if(EBmapExtendObj.compare.length == 0){
            // $("#cs_jcdb").addClass("cs_jcdb_none");
        };
        if($('#cs_information').css('display') == 'none'){
            $("#cs_jcdb").show();
        };
        var EBmapExtendObj_compare = "";
        for(var j = 0; j <  EBmapExtendObj.compare.length; j++){
            var EBmapExtendObj_inf;
            if(EBmapExtendObj.compare[j] == ""){
                EBmapExtendObj_inf = {airInfoName:""};
            }else{
                EBmapExtendObj_inf = EBmapExtendObj.conversion(EBmapExtendObj.compare[j]);
            };
            EBmapExtendObj_compare += "<div class='cs_jcdb_jc'> "+
                "<span>机场"+(j+1)+"</span> " +
                "<div> " +
                "<input  placeholder='输入机场名或选地图上选点'  class='cs_calculate_jccC cs_jcdb_r' value='"+EBmapExtendObj_inf.airInfoName+"'/> " +
                   " <div class='cs_calculate_tip'></div>"+
                "</div> " +
                "<span class='cs_calculate_icon cs_jcdb_icon'>&#xe84b;</span> " +
                "</div> ";
        };
        $("#cs_jcdb_b").html(EBmapExtendObj_compare);
        $(".cs_jcdb_icon").click(function () {
            EBmapExtendObj.removeCompare($(".cs_jcdb_icon").index($(this)));
        });
        $(".cs_jcdb_icon").mouseover(function () {
            $(this).html("删除").addClass("cs_calculate_iconH");
        });
        $(".cs_jcdb_icon").mouseout(function () {
            $(this).html("&#xe84b").removeClass("cs_calculate_iconH");
        });
    };

    /*
    * 点击缩放
    *
    * */
    EBmapExtendObj.zoom = function (mapZoom) {
        EBmapExtendObj.map = mapZoom;
    };
    /*
    * 三字码转换成机场 - 信息
    *
    * */
    EBmapExtendObj.conversion = function (cs_code) {
        for(var j = 0; j < airllist.airInfoDataList.length; j++ ){
            if(cs_code == airllist.airInfoDataList[j].iata){
                return airllist.airInfoDataList[j];
            }
        }
    };
    /**
     * 保存机场名字数组
     * */
    EBmapExtendObj.textTag = [];
    /*
    * 添加覆盖物事件
    *
    * */
    EBmapExtendObj.setOption = function (d) {
        EBmapExtendObj.cleraOption();
        var textTag = "";
        for(var i=0;i < d.series.length; i++){
            if(d.series[i].type == "circle"){
                var EBmapExtendObj_point = EBmapExtendObj.conversion(d.series[i].data[0]);
                EBmapExtendObj.data.push({d:new BMap.Circle(new BMap.Point(EBmapExtendObj_point.city_coordinate_w,EBmapExtendObj_point.city_coordinate_j),d.series[i].data[1].d,{fillColor:d.series[i].data[1].color,strokeColor:"rgba(68,85,161,0.2)", strokeWeight:2, strokeOpacity:0.28}),t:"circle"});
                EBmapExtendObj.data.push({d:new BMap.Circle(new BMap.Point(EBmapExtendObj_point.city_coordinate_w,EBmapExtendObj_point.city_coordinate_j),d.series[i].data[2].d,{fillColor:d.series[i].data[2].color,strokeColor:"rgba(68,85,161,0.2)", strokeWeight:2, strokeOpacity:0.28}),t:"circle"});
            }else if(d.series[i].type == "dots"){
                var EBmapExtendObj_dotsData = [];
                var options = {
                    size: d.series[i].style.size,
                    shape: BMAP_POINT_SHAPE_CIRCLE,
                    color: d.series[i].style.color
                }
                for(var j = 0;j < d.series[i].data.length; j++){
                    var EBmapExtendObj_point = EBmapExtendObj.conversion(d.series[i].data[j]);
                    if(EBmapExtendObj_point != undefined){
                        var EBmapExtendObj_das = new BMap.Point(EBmapExtendObj_point.city_coordinate_w,EBmapExtendObj_point.city_coordinate_j);
                        EBmapExtendObj_das.airData =  EBmapExtendObj_point;
                        EBmapExtendObj_dotsData.push(EBmapExtendObj_das);
                    }
                }
                EBmapExtendObj.data.push({d:new BMap.PointCollection(EBmapExtendObj_dotsData, options),t:"dots"});
            }else if(d.series[i].type == "line"){
                var liAyy = [];
                for(var l = 0;l < d.series[i].data.length;l ++){
                    var circzb_d = EBmapExtendObj.conversion(d.series[i].data[l]);
                    liAyy.push(new BMap.Point(circzb_d.city_coordinate_w,circzb_d.city_coordinate_j))
                };
                EBmapExtendObj.data.push({
                    d:new BMap.Polyline(liAyy,{strokeColor:d.series[i].style.color||'white', strokeWeight:1, strokeOpacity:1}),
                t:"line"});
            }else if(d.series[i].type == "lines"){
                for(var l = 0;l < d.series[i].data.length;l ++){
                    var liAyy = [];
                    // for (var y = 0;y < d.series[i].data[l].length; y ++){
                    for (var y = 0;y < d.series[i].data[l].data.length; y ++){
                        liAyy.push(new BMap.Point(d.series[i].data[l].data[y].j,d.series[i].data[l].data[y].w));
                    };
                    EBmapExtendObj.data.push({
                        d:new BMap.Polyline(liAyy,{strokeColor:d.series[i].data[l].color, strokeWeight:1, strokeOpacity:1}),
                        t:"line"});
                };
            } else if(d.series[i].type == "oval"){
                var cs_assemble = [];
                for(var u = 0;u < d.series[i].data.length; u ++){
                    cs_assemble.push(new BMap.Point(d.series[i].data[u][0],d.series[i].data[u][1]));
                }
                EBmapExtendObj.data.push({
                    d:new BMap.Polygon(cs_assemble,{strokeColor:"#283A65", strokeWeight:0.1, strokeOpacity:0.5,fillColor:d.series[i].style.color}),
                    t:"oval"
                })
            }
        };
        for(var i = 0;i < EBmapExtendObj.data.length; i ++){
            EBmapExtendObj.map.addOverlay(EBmapExtendObj.data[i].d);
            EBmapExtendObj.map.removeOverlay();
            if(EBmapExtendObj.data[i].t == "dots"){
                EBmapExtendObj.data[i].d.addEventListener('click', function (tags) {
                    if($('#cs_measure').css('display') == 'none'){
                    	if($("#cs_calculate_gjsxIconC").hasClass("cs_calculateJcdjOpen")){
                    		$("#cs_calculate_gjsxIconC").click();
                    	}
                    	 $("#cs_jcdb").removeClass("cs_jcdb_none");
                    	 if($('#cs_jcdb').css('display') == 'block' && EBmapExtendObj.compare.length < 5 && EBmapExtendObj.compare.indexOf(tags.point.airData.iata) == -1 && d.center.indexOf(tags.point.airData.iata) == -1 && $('#cs_contrastBox').css('display') == 'none'){
                             if(EBmapExtendObj.compare[EBmapExtendObj.compare.length-1] == ""){
                                 EBmapExtendObj.compare.splice(EBmapExtendObj.compare.length-1,1);
                             };
                             var EBmapExtendObjIsT= EBmapExtendObj.compare.indexOf("");
                             if(EBmapExtendObjIsT == -1){
                                 EBmapExtendObj.compare.push(tags.point.airData.iata);
                             }else{
                                 EBmapExtendObj.compare.splice(EBmapExtendObjIsT,1);
                             }
                         }
                    	 EBmapExtendObj.resetCompare();
                    }
                    
                });
                EBmapExtendObj.data[i].d.addEventListener("mouseout",function(tags){
                    EBmapExtendObj.textTag.forEach(function(val){
                        EBmapExtendObj.map.removeOverlay(val);
                    });
                    $('*').removeClass('cs-ing1');
                });
                EBmapExtendObj.data[i].d.addEventListener("mouseover",function(tags){
                    var label = new BMap.Label(tags.point.airData.airInfoName, {position:tags.point,offset:new BMap.Size(15,0)});
                    label.setStyle({
                        color : "#d6552e",
                        fontSize : "12px",
                        height : "20px",
                        padddingLeft:"5px",
                        lineHeight : "20px",
                        fontFamily:"微软雅黑",
                        border:"none",
                        width:"auto",
                        backgroundColor:"rgba(23, 21, 21, 0)"
                    });
                    EBmapExtendObj.map.addOverlay(label);
                    EBmapExtendObj.textTag.push(label);
                    $('*').addClass('cs-ing1');
                });
            }
        };
    };
    /*
    * 移出覆盖物事件
    *
    * */
    EBmapExtendObj.cleraOption = function (nu) {
        if(nu != undefined){
            EBmapExtendObj.map.removeOverlay(nu);
        }else{
            for(var i = 0;i < EBmapExtendObj.data.length; i ++){
                EBmapExtendObj.map.removeOverlay(EBmapExtendObj.data[i].d);
            };
            EBmapExtendObj.data = [];
        };
    };
    /*
    * 改变覆盖物事件
    *
    * */
    EBmapExtendObj.changeOption = function () {

    };
    /*
    * 自定义航路的数据
    *
    * */
    EBmapExtendObj.drawData = {
        lines:"",
        lineData:[],
        textLable:[],
        dianLable:[],
        mk:[],
        hj:{},
        lslines:0,
        tagLine:null
    };
    /*
    * 清除自绘制的航路
    *
    * */
    EBmapExtendObj.clearSelfLine = function(){
        EBmapExtendObj.cleraOption(EBmapExtendObj.drawData.lines);
        EBmapExtendObj.drawData.lineData = [];
        EBmapExtendObj.drawData.textLable.forEach(function (val) {
            EBmapExtendObj.cleraOption(val);
        });
        EBmapExtendObj.drawData.dianLable.forEach(function (val) {
            EBmapExtendObj.cleraOption(val);
        });
        EBmapExtendObj.drawData.mk.forEach(function (val) {
            EBmapExtendObj.cleraOption(val);
        });
        glData.open = false;
    };
    /**
     * 添加tag航线
     *
     * */
    EBmapExtendObj.tagLine = function (data) {
        if(EBmapExtendObj.drawData.tagLine != null)EBmapExtendObj.map.removeOverlay(EBmapExtendObj.drawData.tagLine);

        if(data != undefined){
            var arr = [EBmapExtendObj.drawData.lineData[EBmapExtendObj.drawData.lineData.length - 1],data];
            var lines = new BMap.Polyline(arr,{strokeColor:"#b54a30", strokeWeight:3, strokeOpacity:1});
            EBmapExtendObj.drawData.tagLine = lines;
            EBmapExtendObj.map.addOverlay(lines);
        };
    };



    /*
    * 自定义航路绘制方法
    *
    * */
    EBmapExtendObj.drawOption = function (data,state) {
        if(state){
            //  绘制线功能
            if(EBmapExtendObj.drawData.lines != ""){
                EBmapExtendObj.cleraOption(EBmapExtendObj.drawData.lines);
            };
            EBmapExtendObj.drawData.lineData.push(data);
            var lines = new BMap.Polyline(EBmapExtendObj.drawData.lineData,{strokeColor:"#b54a30", strokeWeight:3, strokeOpacity:1});
            EBmapExtendObj.drawData.lines = lines;
            EBmapExtendObj.map.addOverlay(lines);
            // 计算航距
            var line_hj = 0;
            for(var i = 0;i < EBmapExtendObj.drawData.lineData.length;i ++){
                if(EBmapExtendObj.drawData.lineData.length > 1){
                    if(i > 0){
                        line_hj +=  Number(EBmapExtendObj.map.getDistance(EBmapExtendObj.drawData.lineData[i-1],EBmapExtendObj.drawData.lineData[i]));
                    };
                }
            };
            EBmapExtendObj.drawData.lslines = line_hj;
            // 绘制点的dianLable
            var dianLable = new BMap.Circle(data,10,{fillColor:"white"});
            EBmapExtendObj.map.addOverlay(dianLable);
            EBmapExtendObj.drawData.textLable.push(dianLable);
            // 绘制点的textLable
            if(EBmapExtendObj.drawData.lineData.length > 1){
                var label = new BMap.Label(parseInt(line_hj/1000)+"公里", {position:data});
                label.setStyle({
                    color : "white",
                    fontSize : "12px",
                    height : "20px",
                    padddingLeft:"5px",
                    lineHeight : "20px",
                    fontFamily:"微软雅黑",
                    border:"none",
                    width:"auto",
                    backgroundColor:"rgba(23, 21, 21, 0.65)"
                });
                EBmapExtendObj.drawData.textLable.push(label);
                EBmapExtendObj.map.addOverlay(label);
            }else{
                var label = new BMap.Label("起始点",{position:data});
                label.setStyle({
                    color : "white",
                    fontSize : "12px",
                    height : "20px",
                    padddingLeft:"5px",
                    lineHeight : "20px",
                    fontFamily:"微软雅黑",
                    border:"none",
                    width:"auto",
                    backgroundColor:"rgba(23, 21, 21, 0.65)"
                });
                EBmapExtendObj.drawData.textLable.push(label);
                EBmapExtendObj.map.addOverlay(label);
            };
        }else{
            // 初始化数据
            EBmapExtendObj.drawData.mk.forEach(function (val,i) {
                EBmapExtendObj.cleraOption(val);
            });
            var myIcon1 = new BMap.Icon("./images/platform/errc.png", new BMap.Size(15,15),{
                imageSize:new BMap.Size(15, 15),
            });
            var marker1 = new BMap.Marker(EBmapExtendObj.drawData.lineData[EBmapExtendObj.drawData.lineData.length - 1],{icon:myIcon1,offset:new BMap.Size(15, 30)});  // 创建标注
            EBmapExtendObj.map.addOverlay(marker1);
            marker1.addEventListener("click",function(){   // 取消绘制的线
                drawlap([]);
                EBmapExtendObj.clearSelfLine();
                if(glData.ts != ''){
                    glData.ts.removeClass("wsetHelectA");
                }else{
                    $(".pla-tool").removeClass('pla-tool-top');
                    $(".cs_informationIcon").removeClass('cs_informationIconS');
                };
                $(".pla-tool").removeClass('pla-tool-top');
                $("#calculate-back").removeClass('calculate-back-right');
                $("#cs_calculate").removeClass('cs_calculate-left');
                glData.ts = '';

            });
            var myIcon2 = new BMap.Icon("./images/platform/correct.png", new BMap.Size(15,15),{
                imageSize:new BMap.Size(15, 15),
            });
            var marker2 = new BMap.Marker(EBmapExtendObj.drawData.lineData[EBmapExtendObj.drawData.lineData.length - 1],{icon:myIcon2,offset:new BMap.Size(35, 30)});  // 创建标注
            EBmapExtendObj.map.addOverlay(marker2);
            marker2.addEventListener("click",function(){  // 确定绘制的线
                // var cityData = cs_cgair(measureData.data.z);
                // EBmapExtendObj.drawOption(new BMap.Point(cityData.city_coordinate_w,cityData.city_coordinate_j),true);
                drawlap([]);
                EBmapExtendObj.drawData.mk.forEach(function (val) {
                    EBmapExtendObj.cleraOption(val);
                });
                glData.open = false;
                EBmapExtendObj.drawOption(glData.pointEnd,true);
                if($(".cs_informationIcon").index($(".cs_informationIconS")) == 0){
                    EBmapExtendObj.drawData.hj[glData.ts.attr("tscode")] = EBmapExtendObj.drawData.lslines/1000;
                    if(Object.keys(EBmapExtendObj.drawData.hj).length > 0){
                        EBmapExtendObj.drawData.lslines = 0;
                        for(var key in EBmapExtendObj.drawData.hj){
                            EBmapExtendObj.drawData.lslines += EBmapExtendObj.drawData.hj[key];
                        };
                        hlhc.out.lslines = EBmapExtendObj.drawData.lslines;
                        hlhc.out.data = EBmapExtendObj.drawData.lineData;
                        $("#cs_hjVal").val(parseInt(EBmapExtendObj.drawData.lslines) * 2);
                        glData.ts.html('<span class=\'cs_yhzbtn\' id="cs_yhzbtn">已绘制<span title=\'删除绘制\' id=\'cs_yhzbtnc\'>&#xe69e;</span><span>');
                        $('#cs_yhzbtnc').click(function(e){
                            e.stopPropagation();
                            $('#cs_yhzbtn').parent().removeClass('wsetHelectA');
                            $('#cs_yhzbtn').replaceWith('手动添加航段');
                            EBmapExtendObj.clearSelfLine();
                            EBmapExtendObj.drawLast();
                            hlhc.out = {data:'', lslines:''};
                        });
                    };
                }else{
                    hlhc.ins.data = EBmapExtendObj.drawData.lineData;
                    hlhc.ins.lslines = EBmapExtendObj.drawData.lslines/1000;
                    $("#cs_selfdbtn").show();
                    $("#cs_hjVal").val(parseInt((Number(EBmapExtendObj.drawData.lslines)/1000)*2));
                };
                $("#tipShow").html("已保存数据").toggle();
                setTimeout(function(){
                    $("#tipShow").toggle();
                    $("#cs_calculate").removeClass('cs_calculate-left');
                    $(".pla-tool").removeClass('pla-tool-top');
                    $("#calculate-back").removeClass('calculate-back-right');
                },1500);

            });
            EBmapExtendObj.drawData.mk.push(marker1,marker2); // 储存确定&取消按钮
        };
    };

    /**
     * 已存在已绘制航路
     * 
     * */
    EBmapExtendObj.drawLastData = '';
    EBmapExtendObj.drawLast = function(data){
        if(data != undefined){
            EBmapExtendObj.map.removeOverlay(EBmapExtendObj.drawLastData);
            var lines = new BMap.Polyline(data.data,{strokeColor:"#b54a30", strokeWeight:3, strokeOpacity:1});
            EBmapExtendObj.map.addOverlay(lines);
            EBmapExtendObj.drawLastData = lines;
            $("#cs_hjVal").val(parseInt(data.lslines) * 2);
        }else{
            EBmapExtendObj.map.removeOverlay(EBmapExtendObj.drawLastData);
        };
    };

    return EBmapExtendObj;
};
var init_EBmapExtend = function () {
    EBmapExtend = new EBmapExtendConstruction();
};

/**
 * 飞行区等级|机场类型|补贴政策  控件方法
 * @param data
 *
 * */
var controls = function(data){
    var node;
    data.forEach(function(val){
        // node + =
    });
}
