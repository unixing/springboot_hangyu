/*
 * @Author: Lonhon
 * @Date: 2017-09-04 09:50:58
 * @Last Modified time: 2017-09-04 09:50:58
 */
"use strict";
var airportMap = parent.airportMap;
$(function () {
    return false;
    console.log(123)
    //------------------------------------------------------------------------------------- 工具函数 start

    //-------------------------------------- 生成start日期
    var SD_getPreDate = function (currDate, num) {
        num = Math.floor(num);
        var myDate = new Date(currDate);
        var lw = new Date(myDate - 1000 * 60 * 60 * 24 * num); //num表示天数
        var lastY = lw.getFullYear();
        var lastM = lw.getMonth() + 1;
        var lastD = lw.getDate();
        var startdate = lastY + "-" + (lastM < 10 ? "0" + lastM : lastM) + "-" + (lastD < 10 ? "0" + lastD : lastD);
        return startdate;
    }

    //-------------------------------------- 展开航班对
    var resolveFlight = function (num) {   // HU7305/06 => HU7305/HU7306
        if (num.split('/').length > 1 && num.split('/')[0].length != num.split('/')[1].length) {
            return num.split('/')[0] + '/' + num.split('/')[0].replace(num.split('/')[0].substr(-2, 2), '') + num.split('/')[1];
        } else {
            return num;
        }
    }

    //------------------------------------------------------------------------------------- 工具函数 end

    //初始化日期，这步放在请求最新有日期之后或者从parent.supData获取
    /****  顶部日期控件 屏蔽
    var mydate = getNewDate(123);
    $('#startEndDate').daterangepicker({
        startDate: mydate.startDate,
        endDate: mydate.endDate
    }, function (start, end, label) { });
    $('#startEndDate').val(mydate.startDate + ' - ' + mydate.endDate);
    */
    //$('.SD-set-list-title').text(parent.supData.lane);


    $('#SD-head-inquire').val(parent.supData.flight);

    createNaviLeft(parent.supData.flight, true);

    createNaviTop(['a-b', 'a-c', 'b-c']);

    setTimeout(function () {
        // createNaviTop(['aa-bb', 'aa-bc', 'ab-cb'])
    }, 5000)
})