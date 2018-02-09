/*
 * @Author: lonhon 
 * @Date: 2017-11-10 11:50:42 
 * @Last Modified by: github.lonhon
 * @Last Modified time: 2017-11-28 11:41:51
 */

//请求数据
if (parent.supData.linFlag != "0") {
    delete parent.supData.flight;
}
var findNeeds = {};
var allexceptionFlag = '';
var title = '';
var data_table;
var data1 = null;
var data2 = null;
var data3 = null;
var data4 = null;
var xArray = null;
var x_char_data = {};
var airLine = parent.supData.lane;
var airports = parent.airportMap;
var airFltNbr = parent.supData.flight;
var flts = "";
var flagMap = {};
var havingData = "1";
var isnonFlag = $('#isnon-stop').is(':checked');
var ishasFlag = $('#ishas-stop').is(':checked');
if (typeof (airFltNbr) != "undefined") {
    flts = airFltNbr.split('/');
}
if (typeof (airLine) == "undefined") {
    airLine = "";
}
var flight = '';
var searchJson = {};
if (flts.length >= 2) {
    var flttemp = parseInt(flts[0].substring(2, 6)) + 1;
    flight = flts[0] + "/" + flts[0].substring(0, 2) + flttemp;
} else {
    flight = "汇总";
}
$(function () {
    //	changeDate('reservation');

    var objj = parent.supData;
    if (typeof (objj.isIncloudPasstp) != "undefined") {
        if (objj.isIncloudPasstp == "on") {
            $('#ishas-stop').attr('checked', true);
        }
    }
    $("._set-query").click(function (e) {
        e.stopPropagation(); //屏蔽事件冒泡
        send();
    });

    setTimeout(function () {
        $('#reservation').daterangepicker(null, function (start, end, label) {
            var s = start.format('YYYY-MM-DD'),
                e = end.format('YYYY-MM-DD');
         });
    }, 1500);
    var objs = {
        back: function () {
            //判断是否有经停，并是否值灰含经停
            if ($('#cityChoice3').val() == "") {
                $('#divpas').removeClass("_checkOp");
                $('#ishas-stop').removeAttr("disabled");
                ishasFlag = true;
            } else {
                //        		$('#divpas').addClass("_checkOp");
                //				$('#ishas-stop').attr("disabled","disabled");
                //				ishasFlag = false;

                $('#divpas').addClass("_checkOp");
                $('#isnon-stop').attr("disabled", false);
                $('#ishas-stop').attr("disabled", "disabled");
                $('#ishas-stop').attr("checked", true)
                ishasFlag = false;
            }
            getFlt_Nbr();
        }
    };
    oub = objs;
    airControl(".selectCity", objs);
    if (parent.supData.linFlag == "0") {        
        // var divv = ("<ul><li class='tltle-sel' title='销售报表' id='_permissions_13'><a class='page-fun-change' href='/salesReport'>&#xe628;</a></li>" +
        // "<li class='tltle-sel tltle-selI' id='_permissions_1'>&#xe629;<span>航线历史收益统计</span></li>" +
        // "<li class='tltle-sel' title='销售动态' id='_permissions_5'><a class='page-fun-change' href='/buyTicketReport'>&#xe624;</a></li>" +
        // "</li><li class='tltle-sel' title='销售数据' id='_permissions_8'><a class='page-fun-change' href='/SalesData/accountCheck'>&#xe688;</a></li>");
        var divv = ("<ul><li class='tltle-sel' title='销售报表' id='_permissions_13'><a class='page-fun-change' href='/salesReport'>&#xe628;</a></li>" +
            "<li class='tltle-sel tltle-selI' id='_permissions_1'>&#xe629;<span>航线历史收益统计</span></li>" +
            "<li class='tltle-sel' title='销售动态' id='_permissions_5'><a class='page-fun-change' href='/buyTicketReport'>&#xe624;</a></li>" +
            "</li><li class='tltle-sel' title='销售数据' id='_permissions_8'><a class='page-fun-change' href='/SalesData/accountCheck'>&#xe688;</a></li>" +
            "<li class='tltle-sel' title='客票监控' id='_permissions_5'><a class='page-fun-change' href='/ticketMonitor'>&#xe6a9;</a></li></ul>");
        $(".sr-box-head-classify").empty();
        $(".sr-box-head-classify").append(divv);
        if (typeof (flts[0]) != "undefined") {
            $('#fltNbr').text(flts[0] + '/' + flts[0].substring(0, 4) + flts[1]);
        }
    }
    if (airLine != null && airLine != "" && typeof (airLine) != 'undefined' && airLine != "=" && airLine != "==") {
        var flt = parent.supData.flt;
        var cds = airLine.split("=");
        if ('1' == flt) {
            var dptabbr = cds[0] + "-" + airports[cds[0]].iata;
            var arrabbr = cds[1] + "-" + airports[cds[1]].iata;
            $('#cityChoice').attr("abbr", dptabbr);
            $('#cityChoice2').attr("abbr", arrabbr);
            $('#cityChoice').val(cds[0]);
            $('#cityChoice2').val(cds[1]);
        } else {
            if (cds.length == 3) {
                var dptabbr = cds[0] + "-" + airports[cds[0]].iata;
                var pstabbr = cds[1] + "-" + airports[cds[1]].iata;
                var arrabbr = cds[2] + "-" + airports[cds[2]].iata;
                $('#cityChoice').attr("abbr", dptabbr);
                $('#cityChoice3').attr("abbr", pstabbr);
                $('#cityChoice2').attr("abbr", arrabbr);
                $('#cityChoice').val(cds[0]);
                $('#cityChoice3').val(cds[1]);
                $('#cityChoice2').val(cds[2]);
                //变灰经停
                $('#divpas').addClass("_checkOp");
                $('#ishas-stop').attr("disabled", "disabled");
                ishasFlag = false;
            } else {
                var dptabbr = cds[0] + "-" + airports[cds[0]].iata;
                var arrabbr = cds[1] + "-" + airports[cds[1]].iata;
                $('#cityChoice').attr("abbr", dptabbr);
                $('#cityChoice2').attr("abbr", arrabbr);
                $('#cityChoice').val(cds[0]);
                $('#cityChoice2').val(cds[1]);
            }
        }
    }
    /*各航段切换*/
    $(".lin-historical-body-navigation").on("click", "div", function () {
        if ($(this).hasClass("body-navigation-element")) {
            if ($(this).hasClass("TMnodata-div")) {
                showTMnodata();
                return false;
            }
            if (havingData == "0") {
                havingData = "1";
                $(this).addClass("TMnodata-div");
                return;
            }
            if (!$(this).hasClass("set")) {
                $(this).addClass("set").siblings().removeClass("set");
                hasAbnormal($(this).attr('eflag'))
                if ($("#spae_sector").attr("tag") == "graph") {
                    if ($(this).children().children().eq(1).text().length == 1) {
                        var num = $(this).children().children().eq(0).text() + "-" + $(this).children().children().eq(2).text();
                        direct(data1, num);
                        direct(data2, num);
                        mdirect(data3, num);
                        newMdirect(data4, num);
                    } else {
                        IncomeFigure(data1);//初始化
                        newIncomeFigure(data2);//初始化
                        movements(data3);//初始化
                        newMovements(data4);//初始化
                    }
                }
                zxfun();
            }
        }
    });
    //判断是否从菜单进入功能页面--start
    if (parent.supData.flag == 1) {
        parent.supData.flag = 0;
        return;
    }
    //判断是否从菜单进入功能页面--end
    getFlt_Nbr();
    setTimeout(function () {
        var dpt_AirPt_Cd = $('#cityChoice').val();
        var Arrv_Airpt_Cd = $('#cityChoice2').val();
        var pas_stp = $('#cityChoice3').val();
        if (pas_stp != '') {
            pas_stp = airports[pas_stp].iata;
        }
        if (dpt_AirPt_Cd != '') {
            dpt_AirPt_Cd = airports[dpt_AirPt_Cd].iata;
        }
        if (Arrv_Airpt_Cd != '') {
            Arrv_Airpt_Cd = airports[Arrv_Airpt_Cd].iata;
        }
        //    	var isIncloudPasstp = '';
        //    	if($('#isIncloudPasstp').is(':checked')==true){
        //    		isIncloudPasstp = 'on';
        //    	}
        var exceptionData = '';
        //判断异常数据是否选中
        if ($('#exceptionData').is(':checked') == true) {
            exceptionData = 'on';
        }
        var exceptionFlyData = '';
        //判断异常航班是否选中
        if ($('#exceptionFlyData').is(':checked') == true) {
            exceptionData = 'on';
        }
        var flt_nbr_Count = $('._set-list-title').html();
        //判断从哪里来（功能间的切换）
        var startTime = parent.supData.startTime;
        var endTime = parent.supData.endTime;

        if (typeof (startTime) != "undefined") {
            $('#reservation').val(startTime + ' - ' + endTime);
            send();
        } else {
            $.ajax({
                url: '/restful/getAirportHistroyNewDate',
                type: 'GET',
                dataType: 'jsonp',
                data: {
                    dpt_AirPt_Cd: dpt_AirPt_Cd,
                    Arrv_Airpt_Cd: Arrv_Airpt_Cd,
                    pas_stp: pas_stp,
                    flt_nbr: flt_nbr_Count,
                    //	    				isIncludePasDpt:isIncloudPasstp,
                    isDayOrMonth: "day",
                    isIncludeExceptionData: exceptionData
                }
            })
                .done(function (data) {
                    var date = data.success.newDate;
                    changeDate('reservation', date);
                    send();
                })
                .fail(function () {
                    console.log("error:204");
                })
        }
    }, 500);
});
function changeFltNbr() {
    $('#fltNbr').text($('._set-list-title').html());
}
function getfly() {
    getFlt_Nbr();
}
function getFlt_Nbr() {
    var searchJson = new Object();
    var dpt_AirPt_Cd = typeof (airports[$('#cityChoice').val()]) == 'undefined' ? '' : airports[$('#cityChoice').val()].iata;
    var pas_stp = typeof (airports[$('#cityChoice3').val()]) == 'undefined' ? '' : airports[$('#cityChoice3').val()].iata;
    var arrv_Airpt_Cd = typeof (airports[$('#cityChoice2').val()]) == 'undefined' ? '' : airports[$('#cityChoice2').val()].iata;
    if (("" != dpt_AirPt_Cd && arrv_Airpt_Cd != "") || pas_stp != "") {
        searchJson.dpt_AirPt_Cd = dpt_AirPt_Cd;
        searchJson.pas_stp = pas_stp;
        searchJson.arrv_Airpt_Cd = arrv_Airpt_Cd;
    } else {
        return;
    }
    if ($('#ishas-stop').is(':checked') == true) {
        searchJson.isIncludePasDpt = 'on';
    }
    $.ajax({
        type: 'post',
        url: 'getHbh',//请求数据的地址	
        data: searchJson,
        async: false,
        success: function (data) {
            var dats = new Array();
            if (data != null && data.list != null && data.list.length > 0) {
                for (var index in data.list) {
                    dats.push(data.list[index]);
                }
            }
            var list = {
                data: dats, //st节点集合
                summary: "true", //是否包含往返 true包含false不包含
                name: "._set-list-set",  //添加list节点
                cleName: ".sr-box",   //取消绑定事件函数节点
                flyNbr: flight,
                fun: function () {
                }
            };
            setChoose(list);
            //        	    send();
        }
    });
}
window.onresize = function () {
    addElement();
};
function infer(name) {
    var infer = [];
    infer.push(parseFloat($(name).css("width").split("px")[0]));
    infer.push(parseFloat($(name).css("height").split("px")[0]));
    infer.push(parseFloat($(name).css("margin-top").split("px")[0]));
    infer.push(parseFloat($(name).css("left").split("px")[0]));
    return infer;
}
/*图区*/
/*********************************1-2图***************************/
/*总共数据*/
var zpjzks = function (name) {
    var zkvalue = 0;
    var zkcs = 0;
    var zsr = 0;
    var bc = 0;
    for (var key in data_table.success.map[name].dataDate) {
        zkvalue += Number(data_table.success.map[name].dataDate[key].zpjzk);
        zkcs++;
        bc += Number(data_table.success.map[name].dataDate[key].zbc) / 100
        zsr += Number(data_table.success.map[name].dataDate[key].zsr) * 1000
    }
    return { pjzk: zkvalue / zkcs, zsr: zsr / bc };
}
function IncomeFigure(gather) {
    var dom = document.getElementById(gather.name);
    var myChart = echarts.init(dom);
    var option = {
        title: [{
            text: gather.className[0],
            left: '4%',
            top: '8%',
            textStyle: {
                color: "white",
                fontWeight: "normal"
            }
        },
        {
            text: "均班营收：" + (zpjzks(gather.hasStopping).zsr.toFixed(2) == "NaN" ? "-" : zpjzks(gather.hasStopping).zsr.toFixed(2)),
            left: '20%',
            top: '85%',
            textStyle: {
                color: "white",
                fontWeight: "normal",
                fontSize: 14
            }
        },
        {
            text: "平均折扣：" + (zpjzks(gather.hasStopping).pjzk.toFixed(2) == "NaN" ? "-" : zpjzks(gather.hasStopping).pjzk.toFixed(2)),
            right: '20%',
            top: '85%',
            textStyle: {
                color: "white",
                fontWeight: "normal",
                fontSize: 14
            }
        }
        ],
        tooltip: {
            trigger: 'item',
            //            formatter: "{b}<br/>{c} ({d}%)",
            formatter: function (param) {

                return param.name.split("\n")[0] + "<br/>" + fmoney(param.value, 2) + "(" + param.percent + "%)";
            },
            borderColor: '#d85430',
            borderWidth: 1
        },
        legend: {
            orient: 'vertical',
            x: '70%',
            y: "15%",
            data: gather.selName,
            textStyle: {
                color: "white"
            }
        },
        series: [
            {
                name: 'section',
                type: 'pie',
                selectedMode: 'single',
                radius: [0, '30%'],
                label: {
                    normal: {
                        show: false,
                        position: 'center',
                        formatter: function (params) {
                            if (params.dataIndex == 0) {
                                return '总收入占比';
                            } else {
                                return '';
                            }
                        },
                        textStyle: {
                            fontSize: '22',
                            fontWeight: 'bold',
                            color: '#B22222'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: gather.segment
            },
            {
                name: 'people',
                type: 'pie',
                selectedMode: 'single',
                radius: ['40%', '55%'],
                data: [
                    { value: gather.peoples[0], name: "散客总收入\n" + parseInt(gather.peoples[0]), itemStyle: { normal: { color: "#5479a3" } }, label: { normal: { textStyle: { color: "white" } } } },
                    { value: gather.peoples[1], name: "团队总收入\n" + parseInt(gather.peoples[1]), itemStyle: { normal: { color: "#204e7f" } }, label: { normal: { textStyle: { color: "white" } } } }
                ]
            }
        ]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
    //    var num=100;
    //    var num1=100;
    myChart.on("mouseover", function (parmes) {
        if (parmes.seriesName == "section") {
            option.title[1].text = "均班营收：" + zpjzks(parmes.name).zsr.toFixed(2);
            option.title[2].text = "平均折扣：" + zpjzks(parmes.name).pjzk.toFixed(2);

            option.series[1].data[0].value = gather.pieceData[parmes.name][0];
            option.series[1].data[1].value = gather.pieceData[parmes.name][1];

            option.series[1].data[0].name = "散客总收入\n" + fmoney(gather.pieceData[parmes.name][0], 2);
            option.series[1].data[1].name = "团队总收入\n" + fmoney(gather.pieceData[parmes.name][1], 2);

        } else if (parmes.seriesName == 'people') {

        }
        myChart.setOption(option, true);
    });
    myChart.on("mouseout", function (parmes) {
        if (parmes.seriesName == "section") {

            option.title[1].text = "均班营收：" + zpjzks(gather.hasStopping).zsr.toFixed(2);
            option.title[2].text = "平均折扣：" + zpjzks(gather.hasStopping).pjzk.toFixed(2);

            option.series[1].data[0].value = gather.peoples[0];
            option.series[1].data[1].value = gather.peoples[1];

            option.series[1].data[0].name = "散客总收入\n" + Number(gather.peoples[0]).toFixed(2);
            option.series[1].data[1].name = "团队总收入\n" + Number(gather.peoples[1]).toFixed(2);

        } else if (parmes.seriesName == 'people') {
        }
        myChart.setOption(option, true);
    });
}


var zklrs = function (name) {
    var tdkl = 0;
    var skkl = 0;
    var rscs = 0;
    var jbkl = 0;

    for (var key in data_table.success.map[name].dataDate) {
        tdkl += Number(data_table.success.map[name].dataDate[key].ztd);
        skkl += (Number(data_table.success.map[name].dataDate[key].mbkl) - Number(data_table.success.map[name].dataDate[key].mbtd)) * (Number(data_table.success.map[name].dataDate[key].zbc) / 100);
        jbkl += Number(data_table.success.map[name].dataDate[key].mbkl);
        rscs++;
    }
    return { tdkl: tdkl, skkl: skkl, jbkl: jbkl / rscs };
}

function newIncomeFigure(gather) {
    var dom = document.getElementById(gather.name);
    var myChart = echarts.init(dom);
    var option = {
        title: [{
            text: gather.className[0],
            left: '4%',
            top: '8%',
            textStyle: {
                color: "white",
                fontWeight: "normal"
            }
        },
        {
            text: "均班客量：" + Number(zklrs(gather.hasStopping).jbkl).toFixed(0),
            left: '20%',
            top: '85%',
            textStyle: {
                color: "white",
                fontWeight: "normal",
                fontSize: 14
            }
        }
        ],
        tooltip: {
            trigger: 'item',
            formatter: function (param) {
                return param.name.split("\n")[0] + "<br/>" + fmoney(param.value, 0) + "(" + param.percent + "%)";
            },
            borderColor: '#d85430',
            borderWidth: 1
        },
        legend: {
            orient: 'vertical',
            x: '70%',
            y: "15%",
            data: gather.selName,
            textStyle: {
                color: "white"
            }
        },
        series: [
            {
                name: 'section',
                type: 'pie',
                selectedMode: 'single',
                radius: [0, '30%'],
                label: {
                    normal: {
                        show: false,
                        position: 'center',
                        formatter: function (params) {
                            if (params.dataIndex == 0) {
                                return '总人数占比';
                            } else {
                                return '';
                            }
                        },
                        textStyle: {
                            fontSize: '22',
                            fontWeight: 'bold',
                            color: '#B22222'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: gather.segment
            },
            {
                name: 'people',
                type: 'pie',
                selectedMode: 'single',
                radius: ['40%', '55%'],
                data: [
                    {
                        value: gather.peoples[0],
                        name: '散客客量\n' + parseInt(zklrs(gather.hasStopping).skkl),
                        itemStyle: { normal: { color: "#5479a3" } },
                        label: { normal: { textStyle: { color: "white" } } }
                    },
                    { value: gather.peoples[1], name: '团队客量\n' + parseInt(zklrs(gather.hasStopping).tdkl), itemStyle: { normal: { color: "#204e7f" } }, label: { normal: { textStyle: { color: "white" } } } }
                ]
            }
        ]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
    myChart.on("mouseout", function (parmes) {
        if (parmes.seriesName == "section") {
            option.title[1].text = "均班客量：" + Number(zklrs(gather.hasStopping).jbkl).toFixed(0);
            option.series[1].data[0].value = gather.peoples[0];
            option.series[1].data[1].value = gather.peoples[1];

            option.series[1].data[0].name = "散客客量\n" + Number(gather.peoples[0]).toFixed(0);
            option.series[1].data[1].name = "团队客量\n" + Number(gather.peoples[1]).toFixed(0);

        } else if (parmes.seriesName == 'people') {

        }
        myChart.setOption(option, true);
    });
    myChart.on("mouseover", function (parmes) {

        if (parmes.seriesName == "section") {
            option.title[1].text = "均班客量：" + Number(zklrs(parmes.name).jbkl).toFixed(0);


            option.series[1].data[0].name = "散客客量\n" + Number(zklrs(parmes.name).skkl).toFixed(0);
            option.series[1].data[1].name = "团队客量\n" + Number(zklrs(parmes.name).tdkl).toFixed(0);

            option.series[1].data[0].value = gather.pieceData[parmes.name][0];
            option.series[1].data[1].value = gather.pieceData[parmes.name][1];

        } else if (parmes.seriesName == 'people') {

        }
        myChart.setOption(option, true);
    });
}
/*单边数据*/
function direct(gather, name) {
    var dom = document.getElementById(gather.name);
    var myChart = echarts.init(dom);
    var option = {
        title: [{
            text: gather.className[0],
            left: '4%',
            top: '8%',
            textStyle: {
                color: "white",
                fontWeight: "normal"
            }
        },
        {
            text: gather.className[1] == "总收入" ? "均班营收：" + Number(zpjzks(name).zsr).toFixed(0) : "均班客量：" + Number(zklrs(name).jbkl).toFixed(0),
            left: '20%',
            top: '85%',
            textStyle: {
                color: "white",
                fontWeight: "normal",
                fontSize: 14
            }
        },
        {
            show: gather.className[1] == "总收入" ? true : false,
            text: gather.className[1] == "总收入" ? "平均折扣：" + Number(zpjzks(name).pjzk).toFixed(2) : "",
            right: '20%',
            top: '85%',
            textStyle: {
                color: "white",
                fontWeight: "normal",
                fontSize: 14
            }
        }
        ],
        tooltip: {
            trigger: 'item',
            formatter: function () {
                return false;
            }
        },
        legend: {

        },
        series: [
            {
                name: 'section',
                hoverAnimation: false,
                type: 'pie',
                selectedMode: 'single',
                radius: [0, '50%'],
                labelLine: {
                    normal: {
                        show: true
                    }
                },
                data: [
                    { label: { normal: { textStyle: { color: "white" } } }, value: gather.pieceData[name][0], name: (gather.className[1] == "总收入" ? '散客总收入\n' + gather.pieceData[name][0] : '散客客量\n' + gather.pieceData[name][0]), itemStyle: { normal: { color: "#204e7f" } } },
                    { label: { normal: { textStyle: { color: "white" } } }, value: gather.pieceData[name][1], name: (gather.className[1] == "总收入" ? '团队总收入\n' + gather.pieceData[name][1] : '团队客量\n' + gather.pieceData[name][1]), itemStyle: { normal: { color: "#5479a3" } } },

                ]
            }
        ]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}
/*****************************3-4图***********************/

/*总共数据*/
function movements(gather) {
    var dom = document.getElementById(gather.name);
    var myChart = echarts.init(dom);
    var option = {
        title: {
            text: gather.className,
            left: '4%',
            top: '5%',
            textStyle: {
                color: "white",
                fontWeight: "normal"
            }
        },
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            borderColor: '#d85430',
            borderWidth: 1
        },
        legend: {
            data: ['客量', '座位', '班次*100', '团队', '团队收入/100', '收入/1000'],
            align: 'left',/*
            right: "0%",*/
            top: "15%",
            textStyle: {
                color: "white"
            }
        },
        grid: {
            left: '5%',
            right: '4%',
            bottom: '3%',
            top: "30%",
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: x_char_data[gather.hasStopping],
                axisTick: {
                    alignWithLabel: true
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: "#1b2c4c"
                    }
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "white"
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: "#24324a"
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                splitLine: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "white"
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: "#24324a"
                    }
                }
            }
        ],
        series: [
            {
                name: '客量',
                smooth: true,
                type: 'bar',
                showSymbol: false,
                itemStyle: {
                    normal: {
                        color: "#1f4e7f"
                    }
                },
                barWidth: '40%',
                data: gather.peoples[0]
            },
            {
                name: '座位',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#84acda"
                    }
                },
                symbol: 'circle',
                data: gather.peoples[1]
            },
            {
                name: '班次*100',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#52b778"
                    }
                },
                symbol: "rect",
                data: gather.peoples[2]
            },
            {
                name: '团队',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#af9060"
                    }
                },
                symbol: "triangle",
                data: gather.peoples[3]
            },
            {
                name: '团队收入/100',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#8e672e"
                    }
                },
                symbol: "triangle",
                data: gather.peoples[4]
            },
            {
                name: '收入/1000',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#d35b4c"
                    }
                },
                symbol: "triangle",
                data: gather.peoples[5]
            }

        ]
    };
    myChart.setOption(option, true);
}
function newMovements(gather) {
    var dom = document.getElementById(gather.name);
    var myChart = echarts.init(dom);
    var option = {
        title: {
            text: gather.className,
            left: '4%',
            top: '5%',
            textStyle: {
                color: "white",
                fontWeight: "normal"
            }
        },
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            borderColor: '#d85430',
            borderWidth: 1
        },
        legend: {
            data: ['每班旅客', '每班座位', '每班团队', '客座率', '团队收入/100', '每班收入/1000'],
            align: 'left',
            /*right: "0%",*/
            top: "15%",
            textStyle: {
                color: "white"
            }
        },
        grid: {
            left: '5%',
            right: '4%',
            bottom: '3%',
            top: "30%",
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: x_char_data[gather.hasStopping],
                axisTick: {
                    alignWithLabel: true
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: "#1b2c4c"
                    }
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "white"
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: "#24324a"
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                splitLine: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "white"
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: "#24324a"
                    }
                }
            }
        ],
        series: [
            {
                name: '每班旅客',
                smooth: true,
                type: 'bar',
                showSymbol: false,
                itemStyle: {
                    normal: {
                        color: "#1f4e7f"
                    }
                },
                barWidth: '40%',
                data: gather.peoples[0]
            },
            {
                name: '每班座位',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#84acda"
                    }
                },
                data: gather.peoples[1]
            },
            {
                name: '每班团队',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#52b778"
                    }
                },
                symbol: "rect",
                data: gather.peoples[2]
            },
            {
                name: '客座率',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#af9060"
                    }
                },
                symbol: "triangle",
                data: gather.peoples[3]
            },
            {
                name: '团队收入/100',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#8e672e"
                    }
                },
                symbol: "triangle",
                data: gather.peoples[4]
            },
            {
                name: '每班收入/1000',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#d35b4c"
                    }
                },
                symbol: "triangle",
                data: gather.peoples[5]
            }

        ]
    };
    myChart.setOption(option, true);
}

/*单边数据*/
function mdirect(gather, name) {
    var dom = document.getElementById(gather.name);
    var myChart = echarts.init(dom);
    var option = {
        title: {
            text: gather.className,
            left: '4%',
            top: '5%',
            textStyle: {
                color: "white",
                fontWeight: "normal"
            }
        },
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            borderColor: '#d85430',
            borderWidth: 1
        },
        legend: {
            data: ['客量', '座位', '班次*100', '团队', '团队收入/100', '收入/1000'],
            align: 'left',/*
            right: "0%",*/
            top: "15%",
            textStyle: {
                color: "white"
            }
        },
        grid: {
            left: '5%',
            right: '4%',
            bottom: '3%',
            top: "30%",
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: x_char_data[gather.hasStopping],
                axisTick: {
                    alignWithLabel: true
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: "#1b2c4c"
                    }
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "white"
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: "#24324a"
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                splitLine: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "white"
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: "#24324a"
                    }
                }
            }
        ],
        series: [
            {
                name: '客量',
                smooth: true,
                type: 'bar',
                showSymbol: false,
                itemStyle: {
                    normal: {
                        color: "#1f4e7f"
                    }
                },
                barWidth: '40%',
                data: gather.movements[name][0]
            },
            {
                name: '座位',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#84acda"
                    }
                },
                data: gather.movements[name][1]
            },
            {
                name: '班次*100',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#52b778"
                    }
                },
                symbol: "rect",
                data: gather.movements[name][2]
            },
            {
                name: '团队',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#af9060"
                    }
                },
                symbol: "triangle",
                data: gather.movements[name][3]
            },
            {
                name: '团队收入/100',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#8e672e"
                    }
                },
                symbol: "triangle",
                data: gather.movements[name][4]
            },
            {
                name: '收入/1000',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#d35b4c"
                    }
                },
                symbol: "triangle",
                data: gather.movements[name][5]
            }

        ]
    };
    myChart.setOption(option, true);
}

/*单边数据*/
function newMdirect(gather, name) {
    var dom = document.getElementById(gather.name);
    var myChart = echarts.init(dom);
    var option = {
        title: {
            text: gather.className,
            left: '4%',
            top: '5%',
            textStyle: {
                color: "white",
                fontWeight: "normal"
            }
        },
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            borderColor: '#d85430',
            borderWidth: 1
        },
        legend: {
            data: ['每班旅客', '每班座位', '每班团队', '客座率', '团队收入/100', '每班收入/1000'],
            align: 'left',/*
            right: "0%",*/
            top: "15%",
            textStyle: {
                color: "white"
            }
        },
        grid: {
            left: '5%',
            right: '4%',
            bottom: '3%',
            top: "30%",
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: x_char_data[gather.hasStopping],
                axisTick: {
                    alignWithLabel: true
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: "#1b2c4c"
                    }
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "white"
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: "#24324a"
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                splitLine: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "white"
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: "#24324a"
                    }
                }
            }
        ],
        series: [
            {
                name: '每班旅客',
                smooth: true,
                type: 'bar',
                showSymbol: false,
                itemStyle: {
                    normal: {
                        color: "#1f4e7f"
                    }
                },
                barWidth: '40%',
                data: gather.movements[name][0]
            },
            {
                name: '每班座位',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#84acda"
                    }
                },
                data: gather.movements[name][1]
            },
            {
                name: '每班团队',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#52b778"
                    }
                },
                symbol: "rect",
                data: gather.movements[name][2]
            },
            {
                name: '客座率',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#af9060"
                    }
                },
                symbol: "triangle",
                data: gather.movements[name][3]
            },
            {
                name: '团队收入/100',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#8e672e"
                    }
                },
                symbol: "triangle",
                data: gather.movements[name][4]
            },
            {
                name: '每班收入/1000',
                type: 'line',
                itemStyle: {
                    normal: {
                        color: "#d35b4c"
                    }
                },
                symbol: "triangle",
                data: gather.movements[name][5]
            }

        ]
    };
    myChart.setOption(option, true);
}
function box() {
    $(".lin-historical-body").eq(0).css({ "height": infer(".lin-historical")[1] - infer(".sr-box-head")[1] + "px" });//计算内容区的高度
    $(".body-navigation-element").css({ "height": infer(".lin-historical-body")[1] / parseInt($(".body-navigation-element").size()) }); //计算每个导航块的大小
    for (var i = 0; i < $(".tag-box").length; i++) {//计算每个导航块的居中位置
        var tagH = (infer(".body-navigation-element")[1] - parseFloat($(".tag-box").eq(i).css("height").split("px")[0])) / 2;
        $(".tag-box").eq(i).css({ "margin-top": tagH + "px" });
    };
    //    var Lhbx=infer(".lin-historical-body")[0]-352;
    var Lhbx = infer(".lin-historical-body")[0] - 55;
    $(".lin-historical-body-box").css({ "width": Lhbx + "px", "height": parseInt(infer(".lin-historical-body")[1]) });//就按图区盒大小
}
function addElement() {
    var ele = "";
    if (data1.hasStopping) {
        var ndata = data1.hasStopping.split("-");
        if (ndata[1] == '') {
            if (flagMap[data1.hasStopping].flag == "0") {
                ele = "<div class='body-navigation-element set TMnodata-div' tag = '" + ndata[0] + '=' + ndata[2] + "'><div class='tag-box'><div>" + ndata[0] + "</div> <div class='element-tag'>&#xe648;&#xe647;</div><div>" + ndata[2] + "</div> </div> </div>";
            } else {
                ele = "<div class='body-navigation-element set' tag = " + ndata[0] + '=' + ndata[2] + " eflag= " + allexceptionFlag + "><div class='tag-box'><div>" + ndata[0] + "</div> <div class='element-tag'>&#xe648;&#xe647;</div><div>" + ndata[2] + "</div> </div> </div>";
            }
            for (var key in data1.pieceData) {
                ndata = key.split("-");
                if (flagMap[key].flag == "0") {
                    ele += "<div class='body-navigation-element TMnodata-div' tag = '" + key + "'><div class='tag-box'> <div>" + ndata[0] + "</div><div class='element-tag'>&#xe648;</div><div>" + ndata[1] + "</div></div></div>";
                } else {
                    ele += "<div class='body-navigation-element' tag = '" + key + "' eflag= " + flagMap[key].exceptionFlag + "><div class='tag-box'> <div>" + ndata[0] + "</div><div class='element-tag'>&#xe648;</div><div>" + ndata[1] + "</div></div></div>";
                }
            }

        } else {
            if (flagMap[data1.hasStopping].flag == "0") {
                ele = "<div class='body-navigation-element set TMnodata-div' tag = '" + ndata[0] + '=' + ndata[1] + '=' + ndata[2] + "'><div class='tag-box'><div>" + ndata[0] + "</div> <div class='element-tag'>&#xe648;&#xe647;</div><div>" + ndata[1] + "</div><div class='element-tag'>&#xe648;&#xe647;</div><div>" + ndata[2] + "</div> </div> </div>";
            } else {
                ele = "<div class='body-navigation-element set' tag = " + ndata[0] + '=' + ndata[1] + '=' + ndata[2] + " eflag= " + allexceptionFlag + "><div class='tag-box'><div>" + ndata[0] + "</div> <div class='element-tag'>&#xe648;&#xe647;</div><div>" + ndata[1] + "</div><div class='element-tag'>&#xe648;&#xe647;</div><div>" + ndata[2] + "</div> </div> </div>";
            }
            for (var key in data1.pieceData) {
                ndata = key.split("-");
                if (flagMap[key].flag == "0") {
                    ele += "<div class='body-navigation-element TMnodata-div' tag = '" + key + "'><div class='tag-box'> <div>" + ndata[0] + "</div><div class='element-tag'>&#xe648;</div><div>" + ndata[1] + "</div></div></div>";
                } else {
                    ele += "<div class='body-navigation-element' tag = '" + key + "' eflag= " + flagMap[key].exceptionFlag + "><div class='tag-box'> <div>" + ndata[0] + "</div><div class='element-tag'>&#xe648;</div><div>" + ndata[1] + "</div></div></div>";
                }
            }
        }
    }

    $(".lin-historical-body-navigation").html(ele);
    $(".body-navigation-element").eq(0).addClass("set");
    $('.body-navigation-element').eq(0).click();
    box();//计算区域大小;
    IncomeFigure(data1);//初始化
    newIncomeFigure(data2);//初始化
    movements(data3, xArray);//初始化
    newMovements(data4, xArray);//初始化
}
function send() {
    $('#TM-sloading').show();
    //关闭所有选择框
    $(".c-selectCity").nextAll().remove();
    $("._set-allList").css({ "display": "none" });
    $('.opensright').css('display', 'none');
    var dpt_AirPt_Cd = $('#cityChoice').val();
    if (dpt_AirPt_Cd != '') {
        dpt_AirPt_Cd = airports[dpt_AirPt_Cd].iata;
    } else {
        $('#TM-sloading').hide();
        alert('请选择起始机场');
        $(".lin-historical-body").addClass("muhu");
        $(".err").css("display", "block");
        return;
    }
    var Arrv_Airpt_Cd = $('#cityChoice2').val();
    if (Arrv_Airpt_Cd != '') {
        Arrv_Airpt_Cd = airports[Arrv_Airpt_Cd].iata;
    } else {
        $('#TM-sloading').hide();
        alert('请选择到达机场');
        $(".lin-historical-body").addClass("muhu");
        $(".err").css("display", "block");
        return;
    }
    //查询条件联动
    var dpt_AirPt_Cdtemp = $('#cityChoice').val();
    var pas_stptemp = $('#cityChoice3').val();
    var arrv_Airpt_Cdtemp = $('#cityChoice2').val();
    var flt_nbr_Counttemp = $('._set-list-title').text();
    var object = parent.supData;
    if (pas_stptemp != "") {
        object.lane = dpt_AirPt_Cdtemp + "=" + pas_stptemp + "=" + arrv_Airpt_Cdtemp;
    } else {
        object.lane = dpt_AirPt_Cdtemp + "=" + arrv_Airpt_Cdtemp;
    }
    if ($('#isIncloudPasstp').is(':checked') == true) {
        object.isIncloudPasstp = 'on';
    } else {
        delete object.isIncloudPasstp;
    }
    if (flt_nbr_Counttemp != "") {
        if (flt_nbr_Counttemp != "汇总") {
            var flttemp = flt_nbr_Counttemp.split("/");
            var ff = flttemp[0] + "/" + flttemp[1].substring(flttemp[1].length - 2, flttemp[1].length);
            object.flight = ff;
        } else {
            object.flight = "";
        }
    }
    var pas_stp = $('#cityChoice3').val();
    if (pas_stp != '') {
        pas_stp = airports[pas_stp].iata;
    }
    var date = $('#reservation').val();
    var startTime = '';
    var endTime = '';
    if (typeof (date) == 'undefined' || date == null || date == '') {
        $('#TM-sloading').hide();
        $(".lin-historical-body").addClass("muhu");
        $(".err").css("display", "block");
        alert('请选择起止日期');
        return;
    } else {
        var dateArray = date.split(' - ');
        startTime = dateArray[0];
        endTime = dateArray[1];
    }
    var exceptionData = 'no';
    //判断异常数据是否选中
    if ($('#exceptionData').is(':checked') == true) {
        exceptionData = 'on';
    }
    var exceptionFlyData = 'no';
    //判断异常航班是否选中
    if ($('#exceptionFlyData').is(':checked') == true) {
        exceptionFlyData = 'on';
    }
    var dayOrMonth = '';//暂时
    var flt_nbr_Count = $('._set-list-title').text();
    if (flt_nbr_Count == null || flt_nbr_Count == '') {
        $('#TM-sloading').hide();
        $(".lin-historical-body").addClass("muhu");
        $(".err").css("display", "block");
        console.log('error.1410')
        return;
    }
    var date = $('#reservation').val();
    var startTime = '';
    var endTime = '';
    if (typeof (date) == 'undefined' || date == null || date == '') {
        $('#TM-sloading').hide();
        alert('请选择起止日期');
        return;
    } else {
        if (date.length == 23) {
            dayOrMonth = 'day';
        } else if (date.length == 17) {
            dayOrMonth = 'month';
        }
        var dateArray = date.split(' - ');
        startTime = dateArray[0];
        endTime = dateArray[1];
        var dateArray = date.split(' - ');
        startTime = dateArray[0];
        endTime = dateArray[1];
    }
    function getLastDay(year, month) {
        var new_year = year;  //取当前的年份
        var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）
        if (month > 12)      //如果当前大于12月，则年份转到下一年
        {
            new_month -= 12;    //月份减
            new_year++;      //年份增
        }
        var new_date = new Date(new_year, new_month, 1);//取当年当月中的第一天
        return (new Date(new_date.getTime() - 1000 * 60 * 60 * 24)).getDate();//获取当月最后一天日期
    }
    //时间保存起来，用于其它功能
    if (startTime.length < 9) {
        object.startTime = startTime + "-01";
    } else {
        object.startTime = startTime;
    }
    if (endTime.length < 9) {
        object.endTime = endTime + "-" + getLastDay(endTime.split("-")[0], endTime.split("-")[1]);
    } else {
        object.endTime = endTime;
    }
    if (new Date(parseInt(object.endTime.split("-")[0]), parseInt(object.endTime.split("-")[1]), parseInt(object.endTime.split("-")[2])).getTime() > new Date().getTime()) {
        object.endTime = new Date().format('yyyy-MM-dd');
    }
    var fstart,fend;
    if(dayOrMonth=='month'){
        fstart = startTime + '-01';
        fend = getDateToLastDay(endTime);
    }else{
        fstart = startTime;
        fend = endTime;
    }
    if(daysBetween(fstart,fend)>366){
        $('#TM-sloading').hide('normal');
        $(".lin-historical-body").addClass("muhu");
        $(".err").css("display", "block");
        return alert("请将范围控制在 366天 以内！");
    }

    //直飞经停
    var isnon = 'no';
    if ($('#isnon-stop').is(':checked') == true) {
        isnon = 'on';
    }
    var ispas = 'no';
    if ($('#ishas-stop').is(':checked') == true) {
        ispas = 'on';
    }

    $.ajax({
        type: 'post',
        url: '/restful/getAirportHistroyData',
        dataType: 'jsonp',
        data: {
            dpt_AirPt_Cd: dpt_AirPt_Cd,
            Arrv_Airpt_Cd: Arrv_Airpt_Cd,
            pas_stp: pas_stp,
            flt_nbr: flt_nbr_Count,
            startTime: startTime,
            endTime: endTime,
            isDayOrMonth: dayOrMonth,
            isIncludeExceptionData: exceptionData,
            isIncludeExceptionHuangduan: exceptionFlyData,	//新增异常航段
            isIncludePas: ispas,
            isIncludeRdf: isnon
        },
        async: true,
        success: function (data) {
            $('#TM-sloading').hide('normal');
            if (pas_stp != '') {
                parent.supData.flt = 0;
                if ($('#ishas-stop').is(':checked') == false) {
                    $('#ishas-stop').attr('checked', true);
                    $('#isnon-stop').attr('disabled', false);
                }
            }
            var flt_ectq = [dpt_AirPt_Cd, pas_stp, Arrv_Airpt_Cd].join(''),
                flt_ecth = [Arrv_Airpt_Cd, pas_stp, dpt_AirPt_Cd].join('');
            findNeeds = {
                'dpt_AirPt_Cd': dpt_AirPt_Cd,
                'Arrv_Airpt_Cd': Arrv_Airpt_Cd,
                'pas_stp': pas_stp,
                'flt_ectq': flt_ectq,
                'flt_ecth': flt_ecth,
                'flt_nbr': flt_nbr_Count,
                'startTime': startTime,
                'endTime': endTime,
                'isIncludeExceptionData': exceptionData,
                'isIncludeExceptionHuangduan': exceptionFlyData,
                'isIncludePas': ispas,
                'isIncludeRdf': isnon
            }
            var longFly = formatToZN(dpt_AirPt_Cd + pas_stp + Arrv_Airpt_Cd, pas_stp);
            data_table = JSON.parse(JSON.stringify(data));
            var dataValue = {}, dataCishu = 0;
            var excepFlag;
            for (var key in data.success.map) {
                x_char_data[key] = [];
                if (longFly == key) {
                    excepFlag = data.success.map[key].exceptionFlag
                }
                if (key.split("--").length > 1) {

                } else {
                    dataCishu++;
                    for (var yus in data.success.map[key].dataDate) {
                        if (dataValue[yus] == undefined) {
                            dataValue[yus] = { zpjzk: 0, zskzk: 0, ztdzk: 0 };
                        }
                        dataValue[yus].zpjzk += Number(data.success.map[key].dataDate[yus].zpjzk);
                        dataValue[yus].zskzk += Number(data.success.map[key].dataDate[yus].zskzk);
                        dataValue[yus].ztdzk += Number(data.success.map[key].dataDate[yus].ztdzk);
                    }
                }
            }
            for (var key in dataValue) {
                dataValue[key].zpjzk = dataValue[key].zpjzk / dataCishu;
                dataValue[key].zskzk = dataValue[key].zskzk / dataCishu;
                dataValue[key].ztdzk = dataValue[key].ztdzk / dataCishu;
            }
            for (var key in data.success.map) {
                var kees = data_table.success.map[key];
                var vaauls = key.replace(/--/g, "--");
                if (key.split("--").length > 1) {
                    delete data_table.success.map[key];
                    data_table.success.map[vaauls] = kees;
                    data_table.success.map[vaauls];
                    for (var yus in data_table.success.map[vaauls].dataDate) {
                        data_table.success.map[vaauls].dataDate[yus].zpjzk = dataValue[yus].zpjzk;
                        data_table.success.map[vaauls].dataDate[yus].zskzk = dataValue[yus].zskzk;
                        data_table.success.map[vaauls].dataDate[yus].ztdzk = dataValue[yus].ztdzk;
                    }
                }
            }
            allexceptionFlag = data.success.map[longFly].exceptionFlag;
            var pieceData1 = new Map();
            var selName = [];
            var segment1 = [];
            var pieceData2 = new Map();
            var segment2 = [];
            var peoples3 = [];
            var movements3 = new Map();
            var peoples4 = [];
            var movements4 = new Map();
            var xText = [];
            data1 = {};
            data2 = {};
            data3 = {};
            data4 = {};
            xArray = [];
            if (data != null && data.success != null && data.success.map != null) {
                var bz = true;
                //判断有无数据
                for (var key in data.success.map) {
                    var obj = data.success.map[key];
                    flagMap[key] = {
                        'flag': obj.flag,
                        'exceptionFlag': obj.exceptionFlag
                    }
                    if (obj.flag == "1") {
                        bz = false;
                    }
                }
                data1.name = "container1";
                data1.className = ["收入占比", "总收入"];
                data2.name = 'container2';
                data2.className = ['人数占比', '总人数'];
                data3.name = 'container3';
                data3.className = "月流量走势分析";
                data4.name = 'container4';
                data4.className = '均班客流量走势分析';
                var reg = new RegExp(',', 'g');
                for (var key in data.success.map) {
                    var obj = data.success.map[key];
                    var peoples1 = [];
                    var peoples2 = [];
                    var movements1 = [];
                    var movements2 = [];
                    if (key.split('-').length == 3) {
                        data1.hasStopping = key;
                        data2.hasStopping = key;
                        data3.hasStopping = key;
                        data4.hasStopping = key;
                        peoples1.push(parseFloat((obj.sksrd + '').replace(reg, '')));
                        peoples1.push(parseFloat((obj.tdsrd + '').replace(reg, '')));
                        data1.peoples = peoples1;
                        peoples2.push(parseInt((obj.skrsd + '').replace(reg, '')));
                        peoples2.push(parseInt((obj.tdrsd + '').replace(reg, '')));
                        data2.peoples = peoples2;
                    } else {
                        selName.push(key);
                        segment1.push({ value: (parseFloat((obj.sksrd + '').replace(reg, '')) + parseFloat((obj.tdsrd + '').replace(reg, ''))), name: key });
                        peoples1.push(parseFloat((obj.sksrd + '').replace(reg, '')));
                        peoples1.push(parseFloat((obj.tdsrd + '').replace(reg, '')));
                        pieceData1[key] = peoples1;
                        segment2.push({ value: (parseInt((obj.skrsd + '').replace(reg, '')) + parseInt((obj.tdrsd + '').replace(reg, ''))), name: key });
                        peoples2.push(parseInt((obj.skrsd + '').replace(reg, '')));
                        peoples2.push(parseInt((obj.tdrsd + '').replace(reg, '')));
                        pieceData2[key + ''] = peoples2;
                    }
                    if (obj.dataDate != null) {
                        var zkl = [];
                        var zzws = [];
                        var zbc = [];
                        var ztd = [];
                        var ztdsr = [];
                        var zsr = [];
                        var mbkl = [];
                        var mbzws = [];
                        var mbkzl = [];
                        var mbtd = [];
                        var mbtdsr = [];
                        var mbsr = [];
                        for (var keys in obj.dataDate) {
                            var demo = obj.dataDate[keys];
                            if (dayOrMonth == 'day') {
                                x_char_data[key].push(parseInt(keys.split('-')[1]) + '.' + parseInt(keys.split('-')[2]));//日期查询
                            } else {
                                x_char_data[key].push(parseInt(keys.split('-')[1]) + '月\n' + parseInt(keys.split('-')[0]));//月份查询
                            }
                            zkl.push(parseInt((demo.zkl + '').replace(reg, '')));
                            zzws.push(parseInt((demo.zftzws + '').replace(reg, '')));
                            zbc.push(parseFloat((demo.zbc + '').replace(reg, '')).toFixed(2));
                            ztd.push(parseFloat((demo.ztd + '').replace(reg, '')).toFixed(2));
                            ztdsr.push(parseFloat((demo.ztdsr + '').replace(reg, '')).toFixed(2));
                            zsr.push(parseFloat((demo.zsr + '').replace(reg, '')).toFixed(2));
                            mbkl.push(parseFloat((demo.mbkl + '').replace(reg, '')).toFixed(2));
                            mbzws.push(parseFloat((demo.mbftzws + '').replace(reg, '')).toFixed(2));
                            mbkzl.push(parseFloat((demo.mbkzl + '').replace(reg, '')).toFixed(2));
                            mbtd.push(parseFloat((demo.mbtd + '').replace(reg, '')).toFixed(2));
                            mbtdsr.push(parseFloat((demo.mbtdsr + '').replace(reg, '')).toFixed(2));
                            mbsr.push(parseFloat((demo.mbsr + '').replace(reg, '')).toFixed(2));
                        }
                        if (key.split('-').length == 3) {
                            peoples3.push(zkl);
                            peoples3.push(zzws);
                            peoples3.push(zbc);
                            peoples3.push(ztd);
                            peoples3.push(ztdsr);
                            peoples3.push(zsr);
                            peoples4.push(mbkl);
                            peoples4.push(mbzws);
                            peoples4.push(mbtd);
                            peoples4.push(mbkzl);
                            peoples4.push(mbtdsr);
                            peoples4.push(mbsr);
                            data3.peoples = peoples3;
                            data4.peoples = peoples4;
                        } else {
                            movements1.push(zkl);
                            movements1.push(zzws);
                            movements1.push(zbc);
                            movements1.push(ztd);
                            movements1.push(ztdsr);
                            movements1.push(zsr);
                            movements2.push(mbkl);
                            movements2.push(mbzws);
                            movements2.push(mbtd);
                            movements2.push(mbkzl);
                            movements2.push(mbtdsr);
                            movements2.push(mbsr);
                        }
                    }
                    //修改总段数据
                    // movements3[key] = movements1;
                    movements3[key] = key.split('-').length===2? movements1: peoples3;
                    // movements4[key] = movements2;
                    movements4[key] = key.split('-').length===2? movements2: peoples4;
                }
                data3.movements = movements3;
                data4.movements = movements4;
                data1.pieceData = pieceData1;
                data1.selName = selName;
                data1.segment = segment1;
                data2.pieceData = pieceData2;
                data2.selName = selName;
                data2.segment = segment2;
            }
            if (bz) {
                $(".lin-historical-body").addClass("muhu");
                $(".err").css("display", "block");
                return false;
            } else {
                $(".lin-historical-body").removeClass("muhu");
                $(".err").css("display", "none");
            }
            hasAbnormal(excepFlag);
            addElement();
            spae_table2(gz($(".set").attr("tag")));
            spae_table3(hz_table($(".set").attr("tag").replace(/=/g, '-')));
            $("._set-gB").click();
            getTheirTimeData($(".body-navigation-element")[0]);
        }
    });

}


//航段无数据动画
function TM_hd_null() {
    $("div.SD-hd-null").show();
    setTimeout(function () {
        $(".SD-hd-null").hide();
    }
        , 2000)
}
//30天无数据缺失动画
function TM_hd_lack() {
    $("div.TMlackdata").css('top', '9%');
    $("div.TMlackdata").show();
    setTimeout(function () {
        $("div.TMlackdata").hide();
    }
        , 2000)
}

//无数据提示
function showTMnodata() {
    $('.SD-hd-null').show();
    setTimeout(function () {
        $('.SD-hd-null').hide();
    }, 1500)
}

function getTheirTimeData(mythis) {
    var date = $('#reservation').val();
    var dateArray = date.split(' - ');
    var startTime = dateArray[0];
    var endTime = dateArray[1];
    if (DateDiff(dateArray[0], dateArray[1]) < 32) return false;

    var tag = $(mythis).attr("tag");
    for (var key in flagMap) {
        if (tag == key) {
            if (flagMap[key].flag == "0") {
                havingData = "0";
                TM_hd_null();
            }
        }
    }

    var isGoAndBack = "0";
    var dpt_AirPt_Cdtemp = "";
    var pas_stptemp = "";
    var arrv_Airpt_Cdtemp = "";
    if (tag.indexOf("=") > -1) {
        isGoAndBack = "1"
        var tt = tag.split("=");
        if (tt.length > 2) {
            dpt_AirPt_Cdtemp = airports[tt[0]].iata;
            pas_stptemp = airports[tt[1]].iata;
            arrv_Airpt_Cdtemp = airports[tt[2]].iata;
        } else {
            dpt_AirPt_Cdtemp = airports[tt[0]].iata;
            arrv_Airpt_Cdtemp = airports[tt[1]].iata;
        }
    } else {
        isGoAndBack = "0"
        var tt = tag.split("-");
        if (tt.length > 2) {
            dpt_AirPt_Cdtemp = airports[tt[0]].iata;
            pas_stptemp = airports[tt[1]].iata;
            arrv_Airpt_Cdtemp = airports[tt[2]].iata;
        } else {
            dpt_AirPt_Cdtemp = airports[tt[0]].iata;
            arrv_Airpt_Cdtemp = airports[tt[1]].iata;
        }
    }

    //异常数据
    var iscludeExe = '';
    if ($('#exceptionData').is(':checked') == true) {
        iscludeExe = 'on';
    }
    //异常航班
    var iscludeFly = '';
    if ($('#exceptionFlyData').is(':checked') == true) {
        iscludeFly = 'on';
    }
    //直飞
    var isnonStop = '';
    if ($('#isnon-stop').is(':checked') == true) {
        isnonStop = 'on';
    }
    //经停
    var ishasStop = '';
    if ($('#ishas-stop').is(':checked') == true) {
        ishasStop = 'on';
    }
    //航线来回
    var dpt_AirPt_CdName = $('#cityChoice').val();
    var pas_stpName = $('#cityChoice3').val();
    var arrv_Airpt_CdName = $('#cityChoice2').val();
    var flt_ectq;
    var flt_ecth;
    if (pas_stpName == "") {
        flt_ectq = airports[dpt_AirPt_CdName].iata + airports[arrv_Airpt_CdName].iata;
        flt_ecth = airports[arrv_Airpt_CdName].iata + airports[dpt_AirPt_CdName].iata;
    } else {
        flt_ectq = airports[dpt_AirPt_CdName].iata + airports[pas_stpName].iata + airports[arrv_Airpt_CdName].iata;
        flt_ecth = airports[arrv_Airpt_CdName].iata + airports[pas_stpName].iata + airports[dpt_AirPt_CdName].iata;
    }
    var fltNbr = $('._set-list-title').html();
    findNeeds.isGoAndBack = isGoAndBack;
    findNeeds.dpt_AirPt_Cd = dpt_AirPt_Cdtemp;
    findNeeds.Arrv_Airpt_Cd = arrv_Airpt_Cdtemp;
    findNeeds.pas_stp = pas_stptemp;
    $.ajax({
        type: 'post',
        url: '/restful/getHavingDataByThire',
        data: {
            dpt_AirPt_Cd: dpt_AirPt_Cdtemp,	//根据点击的来
            Arrv_Airpt_Cd: arrv_Airpt_Cdtemp,	//根据点击的来
            pas_stp: pas_stptemp,	//根据点击的来
            isGoAndBack: isGoAndBack,
            startTime: startTime,
            endTime: endTime,
            flt_ectq: flt_ectq,
            flt_ecth: flt_ecth,
            isIncludeExceptionData: iscludeExe,//是否包含异常数据
            isIncludeExceptionHuangduan: iscludeFly,//是否包含异常航段
            isIncludePas: isnonStop,//是否包含经停
            isIncludeRdf: ishasStop,//包含直飞
            fltNbr: fltNbr
        },
        dataType: 'json',
        success: function (data) {
            if (data) {
                if (data.status == "1") {
                    TM_hd_lack();
                }
            }
        }
    });
}


//计算天数差的函数，通用  
function DateDiff(sDate1, sDate2) {    //sDate1和sDate2是2006-12-18格式  
    var aDate, oDate1, oDate2, iDays
    aDate = sDate1.split("-")
    oDate1 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0])    //转换为12-18-2006格式  
    aDate = sDate2.split("-")
    oDate2 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0])
    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24)    //把相差的毫秒数转换为天数  
    return iDays
}

function changeDate(id, date) {
    // 参数表示在当前日期下要增加的天数  
    var d = new Date(date);
    d.setDate(d.getDate() - 30);
    var month = d.getMonth() + 1;
    var day = d.getDate();
    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }
    $('#' + id).val(d.getFullYear() + '-' + month + '-' + day + ' - ' + date);
}
function fmoney(s, n) {
    n = n > 0 && n <= 20 ? n : 0;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(), r = s.split(".").length == 2 ? s.split(".")[1] : '';
    t = "";
    for (var i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + (r == '' ? r : "." + r);
}

$("#spae_sector").on("click", function () {
    if ($(this).attr("tag") == "graph") {
        $(this).attr("tag", "table");
        $(".spae_sector_text").html("&#xe6bc;");
        $("#spae_sector_cont").removeClass("spae_sector_cont_c");
        $(".lin-historical-body-box").css("display", "none");
    } else {
        $(this).attr("tag", "graph");
        $(".spae_sector_text").html("&#xe750;");
        $("#spae_sector_cont").addClass("spae_sector_cont_c");
        $(".lin-historical-body-box").css("display", "block");
        //		 var num=$(".set").attr("tag").replace(/=/g,"-");
        var num = $(".set").attr("tag").split("=");
        if (num.length > 1) {
            IncomeFigure(data1);//初始化
            newIncomeFigure(data2);//初始化
            movements(data3);//初始化
            newMovements(data4);//初始化
        } else {
            direct(data1, num);
            direct(data2, num);
            mdirect(data3, num);
            newMdirect(data4, num);
        }

    }
});
function gz(name) {
    if (name.split("=").length == 3) {
        name = name.replace(/=/g, '-')
    } else if (name.split("=").length == 2) {
        name = name.replace(/=/g, '--')
    }
    var arr = [];
    for (var key in data_table.success.map[name].dataDate) {
        arr.push(key);
    }
    arr.sort(function (a, b) {
        return parseInt(a.replace(/-/g, ''), 10) - parseInt(b.replace(/-/g, ''), 10);
    });
    var strss = "<span>" + arr[0].split("-")[0] + "年" + arr[0].split("-")[1] + "月" + (arr[0].split("-")[2] == undefined ? "" : arr[0].split("-")[2] + "日") + "-" + arr[arr.length - 1].split("-")[0] + "年" + arr[arr.length - 1].split("-")[1] + "月" + (arr[arr.length - 1].split("-")[2] == undefined ? "" : arr[arr.length - 1].split("-")[2] + "日") + "&nbsp;&nbsp;" + ($(".set").attr("tag")) + "&nbsp;&nbsp;航线历史收益统计</span>";
    $("#exportTitle_span").html(strss);
    var t_data0 = {
        common: {
            "日期": [],
            "班次（班）": [],
            "座位（人）": [],
            "散团人数（人）": [],
            "团队人数（人）": [],
            "团队营收（元）": [],
            "散团营收（元）": [],
            "平均折扣（%）": [],
            "散客折扣（%）": [],
            "团队折扣（%）": [],
            "平均客座率（%）": []
        },
        special: ''
    }
    var t_data1 = {
        common: {
            "月份": [],
            "班次（班）": [],
            "座位（人）": [],
            "散团人数（人）": [],
            "团队人数（人）": [],
            "团队营收（元）": [],
            "散团营收（元）": [],
            "平均折扣（%）": [],
            "散客折扣（%）": [],
            "团队折扣（%）": [],
            "平均客座率（%）": [],
        },
        special: ''
    }
    var tag = 1;

    var zsr = 0;
    var zbc = 0;
    var jbsr = 0;
    var zkl = 0;
    var jbkl = 0;
    for (var key in data_table.success.map[name].dataDate) {
        if (key.split("-").length > 2) {
            tag = 0;
            t_data0.common["日期"].push(key);
        } else {
            t_data1.common["月份"].push(key);
        }
        t_data0.common["班次（班）"].push(parseFloat(data_table.success.map[name].dataDate[key].zbc) / 100);
        t_data0.common["座位（人）"].push(Number(data_table.success.map[name].dataDate[key].zftzws).toFixed(0));
        t_data0.common["散团人数（人）"].push(Number(data_table.success.map[name].dataDate[key].zkl).toFixed(0));
        t_data0.common["团队人数（人）"].push(Number(data_table.success.map[name].dataDate[key].ztd).toFixed(0));
        //        t_data0.common["团队营收（元）"].push((Number(data_table.success.map[name].dataDate[key].mbtdsr)*100).toFixed(0));
        t_data0.common["团队营收（元）"].push((Number(data_table.success.map[name].dataDate[key].ztdsr) * 100).toFixed(0));
        t_data0.common["散团营收（元）"].push((parseFloat(data_table.success.map[name].dataDate[key].zsr) * 1000).toFixed(0));

        // t_data0.common["平均客座率（%）"].push(((Number(data_table.success.map[name].dataDate[key].zkl)/Number(data_table.success.map[name].dataDate[key].zftzws))*100).toFixed(2));
        t_data0.common["平均客座率（%）"].push(parseFloat(data_table.success.map[name].dataDate[key].mbkzl)>0?data_table.success.map[name].dataDate[key].mbkzl: '-');


        t_data1.common["班次（班）"].push(parseFloat(data_table.success.map[name].dataDate[key].zbc) / 100);
        t_data1.common["座位（人）"].push(Number(data_table.success.map[name].dataDate[key].zftzws).toFixed(0));
        t_data1.common["散团人数（人）"].push(Number(data_table.success.map[name].dataDate[key].zkl).toFixed(0));
        t_data1.common["团队人数（人）"].push(Number(data_table.success.map[name].dataDate[key].ztd).toFixed(0));
        t_data1.common["团队营收（元）"].push((Number(data_table.success.map[name].dataDate[key].ztdsr) * 100).toFixed(0));
        t_data1.common["散团营收（元）"].push((parseFloat(data_table.success.map[name].dataDate[key].zsr) * 1000).toFixed(0));

        //t_data1.common["平均客座率（%）"].push(((Number(data_table.success.map[name].dataDate[key].zkl)/Number(data_table.success.map[name].dataDate[key].zftzws))*100).toFixed(2));
        t_data1.common["平均客座率（%）"].push(checkData(Number(data_table.success.map[name].dataDate[key].mbkzl)));

        if (name.indexOf("--") != -1 || name.split("-").length == 3) {
            var lszpjzk = 0, lszskzk = 0, lsztdzk = 0, lslength = 0;
            for (var ous in data_table.success.map) {
                if (ous.indexOf("--") == -1 || ous.split("-").length != 3) {
                    if(data_table.success.map[ous].flag == 1 && data_table.success.map[ous].dataDate[key] != undefined){
                        lszpjzk += Number(data_table.success.map[ous].dataDate[key].zpjzk);
                        lszskzk += Number(data_table.success.map[ous].dataDate[key].zskzk);
                        lsztdzk += Number(data_table.success.map[ous].dataDate[key].ztdzk);
                        lslength++;
                    }

                }
            }
            /**团队折扣改为直接取接口对应数据 */
            t_data0.common["平均折扣（%）"].push(checkData(lszpjzk / lslength));
            t_data0.common["散客折扣（%）"].push(checkData(lszskzk / lslength));
            t_data0.common["团队折扣（%）"].push(checkData(data_table.success.map[name].dataDate[key].mbtdzk));

            t_data1.common["平均折扣（%）"].push(checkData(lszpjzk / lslength));
            t_data1.common["散客折扣（%）"].push(checkData(lszskzk / lslength));
            t_data1.common["团队折扣（%）"].push(checkData(data_table.success.map[name].dataDate[key].mbtdzk));
        } else {
            t_data0.common["平均折扣（%）"].push(checkData(data_table.success.map[name].dataDate[key].zpjzk));
            t_data0.common["散客折扣（%）"].push(checkData(data_table.success.map[name].dataDate[key].zskzk));
            t_data0.common["团队折扣（%）"].push(checkData(data_table.success.map[name].dataDate[key].ztdzk));

            t_data1.common["平均折扣（%）"].push(checkData(data_table.success.map[name].dataDate[key].zpjzk));
            t_data1.common["散客折扣（%）"].push(checkData(data_table.success.map[name].dataDate[key].zskzk));
            t_data1.common["团队折扣（%）"].push(checkData(data_table.success.map[name].dataDate[key].ztdzk));
        }

        zsr += parseFloat((data_table.success.map[name].dataDate[key].zsr) * 1000);
        zbc += parseFloat(data_table.success.map[name].dataDate[key].zbc) / 100;
        zkl += parseFloat((data_table.success.map[name].dataDate[key].zkl));
    }
    $("#_space_zsr").text(zsr.toFixed(2));
    $("#_space_zbc").text(zbc);
    $("#_space_jbsr").text((parseFloat(zsr) / parseFloat(zbc)).toFixed(2));
    $("#_space_zkl").text(zkl);
    $("#_space_jbkl").text((parseFloat(zkl) / parseFloat(zbc)).toFixed(2));

    if (tag == 0) {
        return t_data0;
    } else {
        return t_data1;
    }
}
function hz_table(name) {
    if (name.split("=").length == 3) {
        name = name.replace(/=/g, '-');
    } else if (name.split("=").length == 2) {
        name = name.replace(/=/g, '--');
    }
    var data_x = [];
    var arr = [];
    for (var key in data_table.success.map[name].dataDate) {
        arr.push(key);
    }
    if(arr.length>0){
        arr.sort(function (a, b) {
            return parseInt(a.replace(/-/g, ''), 10) - parseInt(b.replace(/-/g, ''), 10);
        });
        var strss = "<span>" + arr[0].split("-")[0] + "年" + arr[0].split("-")[1] + "月" + (arr[0].split("-")[2] == undefined ? "" : arr[0].split("-")[2] + "日") + "-" + arr[arr.length - 1].split("-")[0] + "年" + arr[arr.length - 1].split("-")[1] + "月" + (arr[arr.length - 1].split("-")[2] == undefined ? "" : arr[arr.length - 1].split("-")[2] + "日") + "&nbsp;&nbsp;" + ($(".set").attr("tag")) + "&nbsp;&nbsp;航线历史收益统计</span>";
        $("#exportTitle_span").html(strss);
        for (var i = 0; i < arr.length; i++) {
            var hd = [];
            var bc = [];
            var mbzw = [];
            var strs = [];
            var tdrs = [];
            var stzys = [];
            var tdzys = [];
            var pjzk = [];
            var skzk = [];
            var tdzk = [];
            var pjkzl = [];
    
            var bc_j = 0;
            var mbzw_j = 0;
            var strs_j = 0;
            var tdrs_j = 0;
            var stzys_j = 0;
            var tdzys_j = 0;
            var pjzk_j = 0;
            var skzk_j = 0;
            var tdzk_j = 0;
            var pjkzl_j = 0;
            var len = 0;
            if (name.indexOf("--") != -1 || name.split("-").length == 3) {
                for (var key in data_table.success.map) {
                    if (key.split("-").length < 3) {
                        if(data_table.success.map[key].flag == 1 && data_table.success.map[key].dataDate[arr[i]] != undefined){
                            hd.push(key);
                            bc.push(parseFloat(data_table.success.map[key].dataDate[arr[i]].zbc) / 100);
                            mbzw.push(data_table.success.map[key].dataDate[arr[i]].mbftzws);
                            strs.push(data_table.success.map[key].dataDate[arr[i]].mbkl);
                            tdrs.push(Number(data_table.success.map[key].dataDate[arr[i]].mbtd).toFixed(2));
                            stzys.push((parseFloat(data_table.success.map[key].dataDate[arr[i]].mbsr) * 1000).toFixed(0));
                            tdzys.push((parseFloat(data_table.success.map[key].dataDate[arr[i]].mbtdsr) * 100).toFixed(0));
                            pjzk.push(checkData(data_table.success.map[key].dataDate[arr[i]].mbpjzk));
                            skzk.push(checkData(data_table.success.map[key].dataDate[arr[i]].mbskzk));
                            tdzk.push(checkData(data_table.success.map[key].dataDate[arr[i]].mbtdzk));
                            pjkzl.push(data_table.success.map[key].dataDate[arr[i]].mbkzl);
                            len++;
                        }
                    }
                }
            } else {
                hd.push(name);
                bc.push(parseFloat(data_table.success.map[name].dataDate[arr[i]].zbc) / 100);
                mbzw.push(data_table.success.map[name].dataDate[arr[i]].mbftzws);
                strs.push(data_table.success.map[name].dataDate[arr[i]].mbkl);
                tdrs.push(Number(data_table.success.map[name].dataDate[arr[i]].mbtd).toFixed(2));
                stzys.push((parseFloat(data_table.success.map[name].dataDate[arr[i]].mbsr) * 1000).toFixed(0));
                tdzys.push((parseFloat(data_table.success.map[name].dataDate[arr[i]].mbtdsr) * 100).toFixed(0));
                pjzk.push(checkData(data_table.success.map[name].dataDate[arr[i]].mbpjzk));
                skzk.push(checkData(data_table.success.map[name].dataDate[arr[i]].mbskzk));
                tdzk.push(checkData(data_table.success.map[name].dataDate[arr[i]].mbtdzk));
                pjkzl.push(data_table.success.map[name].dataDate[arr[i]].mbkzl);
                len = 1;
            }
    
            for (var j = 0; j < hd.length; j++) {
                bc_j = parseFloat(bc[j]);
                mbzw_j += parseFloat(mbzw[j]) * bc_j;
                strs_j += parseFloat(strs[j]) * bc_j;
                tdrs_j += parseFloat(tdrs[j]) * bc_j;
                stzys_j += parseFloat(stzys[j]) * bc_j;
                tdzys_j += parseFloat(tdzys[j]) * bc_j;
                pjzk_j += parseFloat(pjzk[j]);
                skzk_j += parseFloat(skzk[j]);
                tdzk_j += parseFloat(tdzk[j]);
                //pjkzl_j += parseFloat(pjkzl[j]);
            }
            pjzk_j = pjzk_j / len;
            skzk_j = skzk_j / len;
            tdzk_j = tdzk_j / len;
            /*                                                                                 均班中合计数据改为汇总数据 start */
            // pjkzl_j = pjkzl_j / len;
            bc_j = parseFloat(data_table.success.map[name].dataDate[arr[i]].zbc) / 100;
            mbzw_j = (data_table.success.map[name].dataDate[arr[i]].mbftzws);
            strs_j = (data_table.success.map[name].dataDate[arr[i]].mbkl);
            tdrs_j = (Number(data_table.success.map[name].dataDate[arr[i]].mbtd).toFixed(2));
            stzys_j = ((parseFloat(data_table.success.map[name].dataDate[arr[i]].mbsr) * 1000).toFixed(0));
            tdzys_j = ((parseFloat(data_table.success.map[name].dataDate[arr[i]].mbtdsr) * 100).toFixed(0));
            pjzk_j = checkData(data_table.success.map[name].dataDate[arr[i]].mbpjzk);
            skzk_j = checkData(data_table.success.map[name].dataDate[arr[i]].mbskzk);
            tdzk_j = checkData(data_table.success.map[name].dataDate[arr[i]].mbtdzk);
            pjkzl_j = (data_table.success.map[name].dataDate[arr[i]].mbkzl);
    
            //pjkzl_j = data_table.success.map[name].dataDate[arr[i]].mbkzl;
            /*                                                                                 均班中合计数据改为汇总数据 end */
            mbzw_j = mbzw_j;
            data_x.push(
                {
                    time: arr[i],
                    hd: { data: hd, all: "", name: "航段" },
                    bc: { data: bc, all: bc_j, name: "班次" },
                    mbzw: { data: mbzw, all: mbzw_j, name: "每班座位" },
                    strs: { data: strs, all: strs_j, name: "散团人数（人）" },
                    tdrs: { data: tdrs, all: tdrs_j, name: "团队人数（人）" },
                    stzys: { data: stzys, all: stzys_j, name: "散团总营收（元）" },
                    tdzys: { data: tdzys, all: tdzys_j, name: "团队总营收（元）" },
                    pjzk: { data: pjzk, all: pjzk_j, name: "平均折扣（%）" },
                    skzk: { data: skzk, all: skzk_j, name: "散客折扣（%）" },
                    tdzk: { data: tdzk, all: tdzk_j, name: "团队折扣（%）" },
                    pjkzl: { data: pjkzl, all: pjkzl_j, name: "平均客座率（%）" }
                }
            )
        }
    }
    return data_x;
}

var hasAbnormal = function (statue) {
    if (statue == 1) {
        return false;
    }//没有异常
    $(".abnormalData_prompt").css({
        'top': '5%',
        'left': '44%'
    })
    $(".abnormalData_prompt").show();
    if (statue == '2') $(".abnormalData_prompt").html("<span>&#xe64a;</span> 当前查询条件下包含异常数据");//只包含异常数据
    if (statue == '3') $(".abnormalData_prompt").html("<span>&#xe64a;</span> 当前查询条件下包含异常航班");//只包含异常航班
    if (statue == '4') $(".abnormalData_prompt").html("<span>&#xe64a;</span> 当前查询条件下包含异常数据和异常航班");//both
    setTimeout(function () {
        $(".abnormalData_prompt").hide();
    }, 2000)
}
$("._export-table").on("click", function () {
    $(this).addClass("_set-gB").siblings().removeClass("_set-gB");
    if ($(this).index() == 0) {
        $(".lin_historical_er").css("opacity", 1);
    } else {
        $("#ta").html(spae_table3(hz_table($(".set").attr("tag").replace(/=/g, '-'))));
        $(".lin_historical_er").css("opacity", 0);
    }
    zxfun();
})

var zxfun = function () {
    if ($("._set-gB").text() == "汇总") {
        gz($(".set").attr("tag"));
        $("#ta").html(spae_table2(gz($(".set").attr("tag"))));
    } else {
        $("#ta").html(spae_table3(hz_table($(".set").attr("tag"))));
    }
}

function formatToZN(iataList, pas) {	//三字码转城市+机场名	
    if (iataList) {
        var newlist = [];
        for (var i = 0; i < iataList.length; i += 3) {
            var a = parent.airportMap[iataList.substr(i, 3)].aptChaNam;
            newlist.push(a);
        }
        if (pas == '') return newlist.join("--");
        return newlist.join("-");
    }
    else {
        return "---";
    }
}


//for 直飞经停最少选一个       by long   2017-8-22
$('#isnon-stop').on('change', function (e) {
    if (!$('#isnon-stop').is(':checked') == true) {
        $('#ishas-stop').attr('disabled', true);
        $('#ishas-stop').attr('checked', true);
    } else {
        if (ishasFlag) {
            $('#ishas-stop').attr('disabled', false);
        } else {
            $('#ishas-stop').attr('disabled', !ishasFlag);
        }
    }
})
$('#ishas-stop').on('change', function (e) {
    var mark = ishasFlag === mark ? mark : ishasFlag;
    if (!$('#ishas-stop').is(':checked') == true) {
        $('#isnon-stop').attr('disabled', true);
        $('#isnon-stop').attr('checked', true);
    } else {
        $('#isnon-stop').attr('disabled', !mark);
    }
    mark = !mark;
})


//for 验证数据，为零则变为-
function checkData(val){
    return parseFloat(val)>0 ? parseFloat(val).toFixed(2):'-';
}

var daysBetween = function(sDate1,sDate2){
    //Date.parse() 解析一个日期时间字符串，并返回1970/1/1 午夜距离该日期时间的毫秒数
    var time1 = Date.parse(new Date(sDate1));
    var time2 = Date.parse(new Date(sDate2));
    var nDays = Math.abs(parseInt((time2 - time1)/1000/3600/24));
    return nDays;
};

function getDateToLastDay(d){
    var fd = new Date(d),
        m = fd.getMonth()+1,
        y = fd.getFullYear(),
        nd = new Date(y,m,0),
        ds = nd.getDate();
        m = m<10? '0'+m: m;
    return y+'-'+m + '-' + ds;
}