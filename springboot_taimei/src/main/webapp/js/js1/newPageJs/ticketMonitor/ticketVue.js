/*
 * @Author: long
 * @Date: 2017-09-07 15:18:53 
 * @Last Modified by: github.lonhon
 * @Last Modified time: 2018-01-18 11:04:51
 */

var app = new Vue({
    el: '#app',
    data: function () {
        return {
            today: '',
            sentCurrentFlight: '',
            currentFlight: '',
            currentHd: '',
            parentData: {},
            dataCounter: 0,
            currentLane: {
            },
            basicData: {
                oflight: '',
                flightList: '',
                hdList: {},
                stime: '',
                etime: '',
                hbglDelIndex: -1
            },
            hangduan: {
                active: 0,
                current: ''
            },
            showControl: {
                hisShow: false,
                cwChart: false,
                pjChart: false,
                alarmShow: false,
                hbglShow: false,
                loading: true,
                error: false
            },
            historyOption: {
                flight: '', //查询flight
                date: '',    //历史日期
                state: false,    //true在历史，false不在
                isLowest: true  //  最低/平均票价
            },
            salesChart: {
                isAddUp: true,
                option: ''
            },
            compChart: {
                option: '',
            },
            echartObj: {
                sales: null,
                comp: null
            },
            alarmOption: {//提醒设置
                changeFlag: false,
                oldStatus: 1,
                temp: {//temp用于展示和修改   点击完成之后，real变为temp，取消则不变
                    isopen: false,
                    stime: '09:00',
                    etime: '21:00',
                    upper: 200,
                    downer: 200
                },
                real: {
                    isopen: false,
                    stime: '09:00',
                    etime: '21:00',
                    upper: 200,
                    downer: 200
                }

            },
            hbglOption: {
                changeFlag: -1,//指向正在更改的项
                delIndex: -1,//正在删除的项
                meta: [],//对比航班航线---前台---暂存当前航段的监控
                changeObj: {//
                    // flight: '',
                    // pre: [],
                    // check: 0
                },
                postData: [
                    // flight: '',
                    // pre: 'a-b',
                    // type: 1增加 2删除 3修改
                ],
                errTip: false,
                errText: '*输入错误，请检查航班号是否正确'
            },
            hbbglOption: {
            },
            watchSetting: {
            },
            salesData: {
            },
            priceData: {
            },
            strSalesData: {},
            strPriceData: {},
            timePicker: {
                show: false,
                hourList: [],
                secList: [],
                chour: "00",
                csec: "00",
            }
        }
    },
    methods: {
        //-----------------------------------------------------------------------------------------------通用工具 start
        shortFlyNum: function (num) {   // HU7305/HU7306 => HU7305/06
            num = num.join('/');
            if (num.split('/').length > 1 && num.split('/')[0].length == num.split('/')[1].length) {// HU7305/HU7306
                return (num.split('/')[0] + '/' + num.split('/')[1].substring(num.split('/')[1].length - 2, num.split('/')[1].length));
            } else {// HU7305/06
                return num;
            }
        },
        flightToArr: function (fli) {  //HU7305, HU7305/06 ,HU7305/HU7306 => [HU7305,HU7306]
            var list = [];
            if (fli.split('/').length > 1 && fli.split('/')[0].length === fli.split('/')[1].length) {//HU7305/HU7306
                list = fli.split('/');
            } else if (fli.split('/').length > 1 && fli.split('/')[0].length !== fli.split('/')[1].length) {//HU7305/06
                list = [fli.split('/')[0], fli.split('/')[0].replace(fli.split('/')[0].substr(-2, 2), '') + fli.split('/')[1]];
            } else {//HU7305
                var a = Number(fli.substring(3)),
                    b = a % 2 === 0 ? a - 1 : a + 1;
                c = fli.substring(0, fli.length - 3) + b;
                list = [fli, c];
            }
            return list;
        },
        arrUnique: function (arr) {//一维数组去重
            var tmpArr = [];
            for (var i = 0; i < arr.length; i++) {
                if (tmpArr.indexOf(arr[i]) == -1) {
                    tmpArr.push(arr[i]);
                }
            }
            return tmpArr;
        },
        iataToStr: function (iata) {  // CAN => "广州"
            var res = parent.airportMap[iata].ctyChaNam;
            return res;
        },
        getNowDate: function () {   //今日日期yyyy-mm-dd
            var now = new Date;
            var yy = now.getFullYear(),
                mm = now.getMonth() + 1,
                dd = now.getDate();
            var nd = this.today = yy + '-' + (mm < 10 ? '0' + mm : mm) + '-' + (dd < 10 ? '0' + dd : dd);
            return nd;
        },
        compList: function (a, b) { //航班管理对比操作状态
            var result = [];
            if (a.length === 0) {
                b.map(function (i) {
                    result.push({
                        fltNbr: i.flight,
                        dptAirptCd: i.from,
                        arrvAirptCd: i.to,
                        state: '1'
                    })
                })
                return result;
            };
            var arra = [], arraa = [], arrb = [];
            //new
            a.map(function (q) {//遍历a
                arra.push([[q.flight, q.from, q.to].join('-'), q.id]);
                arraa.push([q.flight, q.from, q.to].join('-'));
            })
            b.map(function (w) {//遍历b
                arrb.push([w.flight, w.from, w.to].join('-'));
            })
            for (i of arra) {// a有b没有   删除
                if (arrb.indexOf(i[0]) < 0) {
                    result.push({
                        fltNbr: i[0].split('-')[0],
                        dptAirptCd: i[0].split('-')[1],
                        arrvAirptCd: i[0].split('-')[2],
                        state: '2',
                        id: i[1]
                    })
                }
            }
            for (ii of arrb) {//  a没有b有   添加
                if (arraa.indexOf(ii) < 0) {
                    result.push({
                        fltNbr: ii.split('-')[0],
                        dptAirptCd: ii.split('-')[1],
                        arrvAirptCd: ii.split('-')[2],
                        state: '1'
                    })
                }
            }
            return result;
        },
        showHbglErrTip: function (tips) {   //航班管理提示
            var _this = this;
            _this.hbglOption.errText = tips || "*输入错误，请检查航班号是否正确";
            _this.hbglOption.errTip = true;
            return setTimeout(function () {
                _this.hbglOption.errTip = false;
            }, 2000);
        },
        date_getPointDate: function (currDate, num) {   //获取某日期n天后的日期
            num = Math.floor(num);
            var myDate = new Date(currDate);
            var lw = new Date(Number(myDate) + 1000 * 60 * 60 * 24 * num); //num表示天数
            var lastY = lw.getFullYear();
            var lastM = lw.getMonth() + 1;
            var lastD = lw.getDate();
            var startdate = lastY + "-" + (lastM < 10 ? "0" + lastM : lastM) + "-" + (lastD < 10 ? "0" + lastD : lastD);
            return startdate;
        },
        bodyClick: function (e) {   //点击关闭div
            //做div隐藏
        },
        initDatePicker: function () {   //初始化日历控件
            var _this = this,
                nd = _this.today;
            _this.historyOption.date = nd;
            var mySchedulea = new Schedule({
                el: '#schedule-boxa', //指定包裹元素（可选）
                date: nd,
                iscurrent: true,
                clickCb: function (y, m, d) {
                    if (new Date(y, m - 1, d) > new Date()) { return false; };
                    var myScheduleb = new Schedule({
                        el: '#schedule-boxb', //指定包裹元素（可选）
                        date: _this.date_getPointDate((y + '-' + m + '-' + d), 32),
                        iscurrent: false
                    });
                    return _this.historyOption.date = [y, m < 10 ? "0" + m : m, Number(d) < 10 ? "0" + d : d].join('-');
                },
                nextMonthCb: function (y, m, d) {
                },
                prevMonthCb: function (y, m, d) {
                    //点击上个月回调（可选）
                }
            });
            var myScheduleb = new Schedule({
                el: '#schedule-boxb', //指定包裹元素（可选）
                date: _this.date_getPointDate(nd, 32),
                iscurrent: false
            });
        },
        formatterDate: function (d, s) {//格式化日期，默认分隔符为-
            s = s || '-';
            var arr = d.split(s);
            var y = arr[0],
                m = Number(arr[1]) > 9 ? arr[1] : ('0' + arr[1]),
                d = Number(arr[2]) > 9 ? arr[2] : ('0' + arr[2]);
            return (y + s + m + s + d);
        },
        numberTest: function (flag, val) { //正整数验证
            if (flag) {
                this.alarmOption.temp.upper = parseInt(this.alarmOption.temp.upper.replace(/\D/g, ''));
            } else {
                this.alarmOption.temp.downer = parseInt(this.alarmOption.temp.downer.replace(/\D/g, ''));
            }
        },
        showPageError: function () {
            var _this = this;
            _this.showControl.error = true;
            if (_this.echartObj.sales || _this.echartObj.comp) {
                _this.echartObj.sales.clear();
                _this.echartObj.comp.clear();
            }
        },
        //-----------------------------------------------------------------------------------------------数据切换 start
        doHistorySearch: function () {//查询历史
            var _this = this;
            if (_this.historyOption.date === _this.today && _this.historyOption.state) { return _this.exitHisSearch(); };
            _this.salesData[_this.currentFlight].pre = [];
            _this.basicData.hdList[_this.currentFlight] = [];
            _this.showControl.hisShow = false;
            if (_this.historyOption.date === _this.today) {
                return _this.showControl.hisShow = false;
            } else {
                _this.historyOption.state = true;//改变为历史状态
                return _this.changeFlight(false, _this.historyOption.date);
            }
        },
        exitHisSearch: function () {//退出历史查询
            var _this = this, flight = _this.currentFlight;
            _this.historyOption.date = _this.today;
            _this.initDatePicker();
            _this.historyOption.state = false;
            if (!$.isEmptyObject(_this.strSalesData) || !$.isEmptyObject(_this.strPriceData)) {
                _this.salesData[flight] = JSON.parse(_this.strSalesData[flight]);
                _this.priceData[flight] = JSON.parse(_this.strPriceData[flight]);
            } else {
                _this.salesData[flight] = {};
                _this.priceData[flight] = {};
            }
            return _this.changeFlight(flight, _this.today);
        },
        changeFlight: function (flight, date) {//改变航班号
            var _this = this;
            var flag = flight;
            flight = flight || _this.currentFlight;
            var nowdate = date || _this.getNowDate();
            _this.basicData.stime = nowdate.split('-')[1] + '.' + nowdate.split('-')[2];
            var lastDate = _this.date_getPointDate(nowdate, 32);
            _this.basicData.etime = lastDate.split('-')[1] + '.' + lastDate.split('-')[2];
            _this.changeReset();
            _this.historyOption.state = flight === _this.currentFlight ? _this.historyOption.state : false;
            _this.currentFlight = flight;
            _this.dataCounter = 0;
            $('#main-container').scrollTop(0);//回滚到顶部
            if ($.isEmptyObject(_this.salesData[flight]) || _this.salesData[flight].pre.length === 0) {//判断是否已有数据
                _this.getAllData(flight, nowdate).then(function (res) {
                    if (_this.dataCounter < 3) {
                        _this.showControl.error = false;
                        _this.changeHd(flight, _this.basicData.hdList[flight][0], 0);//初始化选中第一个leg
                        if (date === undefined) {//暂存今日数据
                            _this.strSalesData[flight] = JSON.stringify(_this.salesData[flight]);
                            _this.strPriceData[flight] = JSON.stringify(_this.priceData[flight]);
                        }
                    } else {
                        _this.showPageError();
                    }
                }, function (rej) {
                    console.log('warning:', rej);
                    _this.showPageError();
                }).catch(function (err) {
                    console.log('warning:', err);
                    _this.showControl.error = false;
                })
            } else {
                if (date === undefined) {
                    return _this.exitHisSearch();//
                }
                return _this.changeHd(flight, _this.basicData.hdList[flight][0], 0);//初始化选中第一个leg
            }
        },
        changeHd: function (flight, hd, index) {//改变航段
            var _this = this;
            _this.changeReset();
            _this.hangduan.current = hd;
            _this.currentHd = hd;
            _this.hbglOption.meta = _this.salesData[flight].watchFlight[hd];
            _this.hbglOption.postData = _this.hbglOption.meta.length > 0 ? _this.hbglOption.meta : [];
            if (_this.watchSetting[flight][hd]) {
                _this.alarmOption.temp = {
                    isopen: _this.watchSetting[flight][hd].isopen,
                    stime: _this.watchSetting[flight][hd].stime,
                    etime: _this.watchSetting[flight][hd].etime,
                    upper: _this.watchSetting[flight][hd].upper,
                    downer: _this.watchSetting[flight][hd].downer,
                    id: _this.watchSetting[flight][hd].id,
                    ostate: _this.watchSetting[flight][hd].state,
                }
            } else {
                _this.alarmOption.temp = {
                    isopen: false,
                    stime: '09:00',
                    etime: '21:00',
                    upper: 200,
                    downer: 200,
                    ostate: 0,
                    id: null
                }
            }
            _this.alarmOption.real = JSON.parse(JSON.stringify(_this.alarmOption.temp));
            if (index !== undefined) { _this.salesChart.isAddUp = true };
            if (_this.salesChart.isAddUp) {//是否为今日数据
                _this.hangduan.active = index !== undefined ? index : _this.hangduan.active;
                _this.createLineChart(_this.priceData[flight]);
                return _this.changeBarChart(_this.salesData[flight].data[hd]);
            } else {
                // _this.createLineChart(0, _this.priceData[flight]);   //只改变舱位图表，票价图表不变，故注释之
                return _this.changeBarChart(_this.salesData[flight].lastData[hd], false);
            }
        },
        changeReset: function () {//重置页面数据
            var _this = this;
            _this.showControl.hisShow = false; //历史查询
            //_this.salesChart.isAddUp = true; //昨日销售
            _this.showControl.alarmShow = false; //涨跌提醒
            _this.showControl.changeFlag = false;  //同上
            _this.showControl.hbglShow = false;  //航班管理
            _this.hbglOption.meta = [];
            _this.hbglOption.postData = [];
            _this.hbglCancel();  //同时取消
        },
        getAllData: function (flight, time) {//请求客票数据
            var _this = this;
            _this.showControl.loading = true;
            return new Promise(function (resolve, reject) {
                $.ajax({
                    url: "/restful/findTicketMonitor",// 生产
                    type: "post",
                    data: {
                        fltNbr: flight,
                        onGutDte: time,
                        dptAirptCd: _this.currentLane.dptAirptCd,
                        pasStp: _this.currentLane.pasStp,
                        arrvAirptCd: _this.currentLane.arrvAirptCd
                    },
                    dataType: "json",
                }).done(function (res) {
                    if (res.data && res.data.length > 0) {
                        _this.formatterData(flight, res.data);
                        if (time === _this.today) {
                            _this.initDatePicker();
                        }
                        resolve(res);
                    } else {
                        reject("无数据");
                    }
                }).fail(function (err) {
                    reject("无返回");
                }).always(function () {
                    _this.showControl.loading = false;
                })


            });
        },
        //-----------------------------------------------------------------------------------------------数据格式化 start
        formatterData: function (flight, data) {    //组装数据
            var _this = this, _counter = 0;
            var singFlightData = _this.salesData[flight];
            singFlightData.pre = [];//航段列表
            singFlightData.data = {};//今日舱位数据
            singFlightData.lastData = {};//昨日舱位数据
            singFlightData.watchFlight = {};

            var lines = _this.priceData[flight];//票价对比数据
            var hbgl = _this.hbbglOption[flight];//监控航班
            var watchSetting = _this.watchSetting[flight];//

            var legend = {},
                allSeries = {},
                olegend = {},
                oallSeries = {};
            data.map(function (item) {//遍历航段 CAN-IQN
                if (item.cabinDataList.length === 0 && item.currerntFlightPriceList === null) {
                    _this.dataCounter++;
                }
                _this.basicData.hdList[flight].push(item.leg);//当前航段列表
                singFlightData.pre.push(item.leg);
                //***********************************************************************************************************销售情况表    start
                singFlightData.data[item.leg] = {};
                singFlightData.lastData[item.leg] = {};

                lines[item.leg] = [];
                var sthisTime = [], snowData = [], sarrr = [];

                legend[item.leg] = [];
                allSeries[item.leg] = [];
                olegend[item.leg] = [];
                oallSeries[item.leg] = [];

                for (var val of item.cabinDataList) {//遍历舱位列表
                    var cw = val.dctChr;
                    for (var bydate of val.cabinDataList) {//获取日期legend
                        legend[item.leg].push(bydate.onGutDte);
                    }
                    legend[item.leg] = (_this.arrUnique(legend[item.leg])).sort();//
                }
                for (var val of item.cabinDataYesterdayList) {//遍历old舱位列表
                    var cw = val.dctChr;
                    for (var bydate of val.cabinDataList) {//获取legend
                        olegend[item.leg].push(bydate.onGutDte);
                    }
                    olegend[item.leg] = (_this.arrUnique(olegend[item.leg])).sort();//
                }
                var series = allSeries[item.leg], cwList = [];
                for (var iitem of item.cabinDataList) {//遍历今日舱位                
                    var darr = [], cwTime = [];
                    var name = iitem.dctChr;
                    cwList.push(name);
                    for (var iiitem of iitem.cabinDataList) {//遍历今日舱位数据
                        cwTime.push(iiitem.onGutDte)
                        for (var marki = 0; marki < legend[item.leg].length; marki++) {
                            var nowdate = legend[item.leg][marki];
                            if (nowdate == iiitem.onGutDte) {
                                darr[marki] = (iiitem.dctPgs);
                            } else {
                                darr[marki] = darr[marki] || null;
                            }
                        }
                    }
                    series.push({
                        name: name,
                        data: darr,
                        type: 'bar',
                        stack: 'sum',
                        barWidth: '10%',
                        barMaxWidth: 30,
                    })
                }

                var oseries = oallSeries[item.leg], ocwList = [];
                for (var oitem of item.cabinDataYesterdayList) {//遍历昨日舱位                
                    var darr = [];
                    var name = oitem.dctChr;
                    ocwList.push(name);
                    for (var oiitem of oitem.cabinDataList) {//遍历昨日舱位下的每天数据
                        for (var marki = 0; marki < olegend[item.leg].length; marki++) {
                            var nowdate = olegend[item.leg][marki];
                            if (nowdate == oiitem.onGutDte) {
                                darr[marki] = (oiitem.dctPgs);
                            } else {
                                darr[marki] = darr[marki] || null;
                            }
                        }
                    }
                    oseries.push({
                        name: name,
                        data: darr,
                        type: 'bar',
                        stack: 'sum',
                        barWidth: '10%',
                        barMaxWidth: 30,
                    })
                }
                singFlightData.data[item.leg] = {
                    allLegend: legend[item.leg],
                    seriesData: series,
                    cwList: cwList,
                };
                singFlightData.lastData[item.leg] = {
                    allLegend: olegend[item.leg],
                    seriesData: oseries,
                    cwList: ocwList
                };
                //***********************************************************************************************************销售情况表    end
                //获取涨跌提醒配置
                singFlightData.watchFlight[item.leg] = [];
                if (item.flightManageList.length > 0) {
                    for (var watch of item.flightManageList) {
                        singFlightData.watchFlight[item.leg].push({
                            flight: watch.fltNbr,
                            pre: [watch.dptAirptCd + '-' + watch.arrvAirptCd],
                            check: 0,
                            id: watch.id
                        })
                    }
                }
                //***********************************************************************************************************票价对比表    start
                lines[item.leg] = [];
                var thisTime = [], lowData = [], avgData = [];
                var timea = {}, timeb = {}, arrr = [];

                if (!$.isEmptyObject(item.currerntFlightPriceMap)) {    //遍历本场票价数据
                    for (itemm in item.currerntFlightPriceMap) {
                        var selfdata = item.currerntFlightPriceMap[itemm], longtime = [], pjmeta = [];
                        for (onePrice in selfdata) {
                            if (Number(selfdata[onePrice].lowest_price) > 0) {
                                lowData.push(selfdata[onePrice].lowest_price);
                                avgData.push(selfdata[onePrice].avg_price);
                                thisTime.push(selfdata[onePrice].search_date);
                                longtime.push(selfdata[onePrice].search_date);
                                pjmeta.push({
                                    price: selfdata[onePrice].changePrice,
                                    rate: selfdata[onePrice].changeRate
                                });
                            }
                        }
                        arrr.push({
                            flight: '本场航班',
                            from: itemm.split('/')[1].split('-')[0],
                            to: itemm.split('/')[1].split('-')[1],
                            data: lowData,
                            avgData: avgData,
                            meta: pjmeta,
                            timeline: longtime
                        })
                        //添加票价to舱位图表
                        singFlightData.data[item.leg].cwList.unshift('票价');
                        singFlightData.data[item.leg].seriesData.unshift({
                            name: '票价',
                            data: lowData,
                            type: 'line',
                            yAxisIndex: 1,
                            connectNulls: true
                        });
                    }
                    singFlightData.data[item.leg] = _this.overBarChart(singFlightData.data[item.leg], longtime);//重新格式化舱位数据
                }
                if (item.flightPriceTrendList && item.flightPriceTrendList.length > 0) {    //其它航线票价数据
                    for (price of item.flightPriceTrendList) {//遍历其它航线
                        for (flightName in price) {
                            var watchFlight = price[flightName];
                            var lowData = [], avgData = [], longtime = [], pjmeta = [];
                            for (onePrice in watchFlight) {
                                thisTime.push(watchFlight[onePrice].search_date);
                                lowData.push(watchFlight[onePrice].lowest_price);
                                avgData.push(watchFlight[onePrice].avg_price)
                                longtime.push(watchFlight[onePrice].search_date);
                                pjmeta.push({
                                    price: watchFlight[onePrice].changePrice,
                                    rate: watchFlight[onePrice].changeRate
                                });
                            }
                            var trendName = flightName.split('/');
                            //票价监控表中的一个series
                            arrr.push({
                                flight: trendName[0] + '/' + trendName[1],
                                from: trendName[1].split('-')[0],
                                to: trendName[1].split('-')[1],
                                data: lowData,
                                avgData: avgData,
                                meta: pjmeta,
                                timeline: longtime
                            })
                        }
                    }
                }
                lines[item.leg] = _this.overLineChart(arrr, thisTime);

                //***********************************************************************************************************票价对比表    end
                //***********************************************************************************************************航班管理      start
                hbgl[item.leg] = [];
                if (item.flightManageList && item.flightManageList.length > 0) {
                    for (hbh of item.flightManageList) {
                        hbgl[item.leg].push({
                            flight: hbh.fltNbr,
                            from: hbh.dptAirptCd,
                            to: hbh.arrvAirptCd,
                            id: hbh.id
                        })
                    }
                }
                //***********************************************************************************************************航班管理    end
                //***********************************************************************************************************涨跌提醒    start
                if (!$.isEmptyObject(item.watchFlight) && item.watchFlight != null) {
                    watchSetting[item.leg] = {
                        isopen: item.watchFlight.state == "1" ? true : false,
                        stime: item.watchFlight.startWatchTime,
                        etime: item.watchFlight.endWatchTime,
                        upper: item.watchFlight.upPrice,
                        downer: item.watchFlight.downPrice,
                        id: item.watchFlight.id,
                        state: item.watchFlight.state
                    }
                }
                //***********************************************************************************************************涨跌提醒    end
            });
            singFlightData.postData = singFlightData.watchFlight;
        },
        overLineChart: function (data, legtime) {   //格式化票价对比图表数据
            if (data.length === 0) { return [] };
            legtime = Array.from(new Set(legtime)).sort();
            data.map(function (item) {
                var newlData = [], newaData = [], extendaData = [], extendbData = [];
                legtime.map(function (t) {
                    newlData.push((item.timeline.indexOf(t) > -1) ? item.data[item.timeline.indexOf(t)] : null);
                    newaData.push((item.timeline.indexOf(t) > -1) ? item.avgData[item.timeline.indexOf(t)] : null);
                    // meta.push((item.timeline.indexOf(t) > -1) ? item.avgData[item.timeline.indexOf(t)] : '-');
                    extendaData.push({
                        value: (item.timeline.indexOf(t) > -1) ? item.data[item.timeline.indexOf(t)] : null,
                        meta: (item.timeline.indexOf(t) > -1) ? item.meta[item.timeline.indexOf(t)] : '-'
                    })
                    extendbData.push({
                        value: (item.timeline.indexOf(t) > -1) ? item.avgData[item.timeline.indexOf(t)] : null,
                        meta: (item.timeline.indexOf(t) > -1) ? item.meta[item.timeline.indexOf(t)] : '-'
                    })
                })
                // item.data = newlData;
                item.data = extendaData;
                // item.avgData = newaData;
                item.avgData = extendbData;
                item.timeline = legtime;
            })
            return data;
        },
        overBarChart: function (data, atime) { //格式化舱位图表数据,atime为票价的日期
            var _this = this;
            if (data.allLegend.length === 0) {  //   0 1情况直接覆盖X轴
                data.allLegend = atime;
                return data;
            }
            //  1 1情况正常重组
            var ot = data.allLegend.concat([]);//舱位日期备份
            var nt = Array.from(new Set(data.allLegend.concat(atime))).sort();  //合并去重排序日期
            for (var item of data.seriesData) {
                if (item.name === "票价") {
                    var adata = [];
                    for (var pi in nt) {
                        adata.push(atime.indexOf(nt[pi]) > -1 ? item.data[atime.indexOf(nt[pi])] : null)
                    }
                    item.data = adata;
                } else {
                    var bdata = [];
                    for (var pi in nt) {
                        bdata.push(ot.indexOf(nt[pi]) > -1 ? item.data[ot.indexOf(nt[pi])] : null)
                    }
                    item.data = bdata;
                }
            }
            data.allLegend = nt;
            return data;
        },
        //-----------------------------------------------------------------------------------------------图表控制 start
        createBarChart: function (legendList, series, datelist) {//初始化销售图
            if (series.length === 0) {
                _this.showControl.cwChart = true;
                setTimeout(function () {
                    _this.showControl.cwChart = false;
                }, 1700)
            }
            var _this = this;
            var _color = series[0].name === "票价" ? ['#fff', '#7ebbe9', '#2e5689', '#d3a156', '#efc495', '#92a1b3', '#c9877e', '#d85330', '#b4afe3', '#7466f3', '#52b777', '#005721', '#c5b423'] : ['#7ebbe9', '#2e5689', '#d3a156', '#efc495', '#92a1b3', '#c9877e', '#d85330', '#b4afe3', '#7466f3', '#52b777', '#005721', '#c5b423'];
            var option = {
                color: _color,
                textStyle: {
                    color: '#fff'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    },
                    textStyle: {
                        color: '#000'
                    },
                    backgroundColor: 'rgba(255,255,255,1)',
                    extraCssText: 'width: 150px;padding:0 16px;',
                    formatter: function (params) {
                        var result = _this.salesChart.isAddUp ? ('<h4 style="color:#999;border-bottom:1px solid #eee;padding-bottom:10px;"><span style="float:right;color:#34334b;">' + ((!!params[0].value && params[0].seriesName == "票价") ? params[0].value : '-') + '</span>票价（元）</h4>') : '';
                        result += '<h4 style="color:#999;">客量（人）</h4>';
                        (params.sort(function (a, b) {
                            return b.seriesName < a.seriesName;
                        })).forEach(function (item) {
                            if (parseInt(item.value) > 0 && item.seriesName !== "票价") {
                                result += '<p style="line-height:15px;"><span style="float:right;color:#34334b;font-size:18px;">' + item.value + '</span><span style="display:inline-block;margin-right:10px;width:9px;height:9px;background-color:' + item.color + '"></span>' + item.seriesName + '</p>';
                            }
                        });
                        return result;
                    },
                    borderColor: '#000',
                    borderWidth: 1
                },
                legend: {
                    textStyle: {
                        color: '#fff'
                    },
                    right: '4%',
                    type: 'scroll',
                    itemHeight: 10,
                    radius: 0,
                    data: legendList
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: datelist.map(function (val) {
                            return val.substr(5).replace('-', '.');
                        }),
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        splitLine: {
                            lineStyle: {
                                color: 'rgba(255,255,255,.2)',
                                type: 'dashed'
                            }
                        },
                        axisLabel: {
                            formatter: '{value} 人'
                        }
                    },
                    {
                        type: 'value',
                        splitLine: {
                            lineStyle: {
                                show: false,
                                color: 'rgba(255,255,255,.2)',
                                type: 'dashed'
                            }
                        },
                        axisLabel: {
                            formatter: '{value} 元'
                        }
                    }
                ],
                series: series
            };
            this.echartObj.sales.setOption(option, true);
        },
        linesTooltipCreator: function (params) {//票价tooltip生成器
            var result = '<h4 style="color:#999;">当日票价（元）</h4>', content = '';
            params.forEach(function (item) {
                var span = '';
                if (Number(item.data.meta.price)) {//涨跌幅度块
                    span = parseFloat(item.data.meta.price) > 0 ?
                        '<p style="color:#CF0015;margin: 0;padding: 0;float: right;"><i> &#xe6da; &nbsp;&nbsp;' + (item.data.meta.price ? Math.abs(item.data.meta.price) : '-') + '&nbsp;&nbsp;&nbsp;&nbsp; &#xe6da; &nbsp;&nbsp;' + (item.data.meta.price ? Math.abs(parseFloat(item.data.meta.rate).toFixed(2)) + '%' : '-') + '</i></p>' :
                        '<p style="color:#5EB201;margin: 0;padding: 0;float: right;"><i> &#xe6d9; &nbsp;&nbsp;' + (item.data.meta.price ? Math.abs(item.data.meta.price) : '-') + '&nbsp;&nbsp;&nbsp;&nbsp; &#xe6d9; &nbsp;&nbsp;' + (item.data.meta.price ? Math.abs(parseFloat(item.data.meta.rate).toFixed(2)) + '%' : '-') + '</i></p>';
                }
                content += item.seriesName !== '本场航班' ? (
                    '<li style="border-bottom:1px solid #eee;padding: 10px 0 3px 0;overflow: hidden;color: #666;"><span style="float:right;color:#34334b;font-size:18px;">'
                    + (parseInt(item.value) > 0 ? parseFloat(item.value).toFixed(2) : '-') + '</span><span style="display:inline-block;margin-right:10px;width:9px;height:9px;background-color:'
                    + item.color + ';"></span>'
                    + item.seriesName.substring(0, 6) + '<br>' + span + '</li>'
                ) : (
                        '<li style="border-bottom:1px solid #eee;padding: 10px 0 3px 0;overflow: hidden;color: #666;"><span style="float:right;color:#34334b;font-size:18px;">'
                        + (parseInt(item.value) > 0 ? parseFloat(item.value).toFixed(2) : '-') + '</span><span style="display:inline-block;margin-right:10px;width:9px;height:9px;background-color:'
                        + item.color + ';border:1px solid #000;"></span>'
                        + item.seriesName.substring(0, 6) + '<br>' + span + '</li>'
                    );
            });
            content = '<ul style="overflow:hidden;">' + content + '</ul>';
            return result + content;
        },
        createLineChart: function (ldata) {//初始化对比图
            var _this = this, _flag = _this.historyOption.isLowest,
                seriesData = [], legendList = [],
                linedata = ldata[_this.currentHd][0];
            _this.showControl.pjChart = false;
            
            if (ldata[_this.currentHd].length === 0) {//无数据提示
                _this.showControl.pjChart = true;
                return _this.echartObj.comp.clear();
            }
            
            ldata[_this.currentHd].map(function (item) {
                seriesData.push({
                    name: item.flight,
                    type: 'line',
                    connectNulls: true,
                    // areaStyle: item.flight==="本场航班" ? {} : {
                    //     normal: {
                    //         color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    //             offset: 0,
                    //             color: 'rgba(18,42,70,1)'
                    //         }, {
                    //             offset: 1,
                    //             color: 'rgba(19,35,61,.4)'
                    //         }])
                    //     }
                    // },
                    data: _flag ? item.data : item.avgData,
                    // lineStyle: item.flight==="本场航班" ? {} : {
                    //     normal: {
                    //         type: 'dashed'
                    //     }
                    // }
                })
                legendList.push(item.flight);
            })
            var option = {
                color: ['#fff', '#7ebbe9', '#efc495', '#b4afe3', '#7466f3', '#52b777', '#005721', '#c5b423'],
                textStyle: {
                    color: '#fff'
                },
                tooltip: {
                    trigger: 'axis',
                    textStyle: {
                        color: '#000'
                    },
                    alwaysShowContent: false,
                    backgroundColor: 'rgba(255,255,255,1)',
                    extraCssText: 'width: 150px;padding:0 16px;',
                    formatter: function (params) {
                        return _this.linesTooltipCreator(params);
                    },
                    borderColor: '#000',
                    borderWidth: 1
                },
                legend: {
                    textStyle: {
                        color: '#fff'
                    },
                    y: 'bottom',
                    type: 'scroll',
                    itemHeight: 10,
                    radius: 0,
                    data: legendList
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '8%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: linedata.timeline.map(function (item) {
                            return item.split('-')[1] + '.' + item.split('-')[2]
                        }),
                        axisTick: {
                            alignWithLabel: true,
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        splitLine: {
                            lineStyle: {
                                color: 'rgba(255,255,255,.2)',
                                type: 'dashed'
                            }
                        }
                    }
                ],
                series: seriesData
            };
            _this.echartObj.comp.setOption(option, true);
        },
        changeBarChart: function (ldata, flag) {//舱位图渲染预判断
            var _this = this;
            if (ldata.seriesData && ldata.seriesData.length > 0) {
                return _this.createBarChart(ldata.cwList, ldata.seriesData, ldata.allLegend);
            } else {
                //"暂无销售数据"
                if (!flag) {
                    _this.salesChart.isAddUp = true;
                }
                _this.showControl.cwChart = true;
                setTimeout(function () {
                    _this.showControl.cwChart = false;
                }, 3000)
            }
        },
        hisChangLineChart: function (flag) {//在历史查询状态下，切换最低和平均
            var _this = this;
            _this.historyOption.isLowest = flag;
            return _this.createLineChart(_this.priceData[_this.currentFlight]);
            // _this.createLineChart(_this.priceData[flight]);
        },
        //-----------------------------------------------------------------------------------------------涨跌提醒 start
        doChangeAlarm: function (type) {//改变涨跌提醒
            var _this = this;
            //用解构赋值就方便了
            if ((Number(_this.alarmOption.temp.upper) + Number(_this.alarmOption.temp.downer)) < 1) {
                return alert("金额错误，请重新输入。")
            }
            _this.alarmOption.real.isopen = _this.alarmOption.temp.isopen;
            _this.alarmOption.real.stime = _this.alarmOption.temp.stime;
            _this.alarmOption.real.etime = _this.alarmOption.temp.etime;
            _this.alarmOption.real.upper = _this.alarmOption.temp.upper;
            _this.alarmOption.real.downer = _this.alarmOption.temp.downer;
            _this.alarmOption.real.id = _this.alarmOption.temp.id;

            _this.alarmOption.changeFlag = false;
            _this.sendChangeAlarm()
        },
        noChangeAlarm: function (type) {//还原涨跌提醒
            var _this = this;
            _this.alarmOption.temp.isopen = _this.alarmOption.real.isopen;
            _this.alarmOption.temp.stime = _this.alarmOption.real.stime;
            _this.alarmOption.temp.etime = _this.alarmOption.real.etime;
            _this.alarmOption.temp.upper = _this.alarmOption.real.upper;
            _this.alarmOption.temp.downer = _this.alarmOption.real.downer;
            _this.alarmOption.real.id = _this.alarmOption.temp.id;

            _this.alarmOption.changeFlag = false;
        },
        sendChangeAlarm: function () {//涨跌提醒接口option封装
            var _this = this,
                _hd = _this.hangduan.current.split('-'), _flight = _this.currentFlight, _alarmOption = _this.alarmOption.real,
                _hbglList = _this.hbglOption.postData,
                postdata = {
                    currentFlight: { "fltNbr": _flight, "dptAirptCd": _hd[0], "arrvAirptCd": _hd[1] },
                    watchFlight: { "id": _alarmOption.id, "startWatchTime": _alarmOption.stime, "endWatchTime": _alarmOption.etime, "upPrice": _alarmOption.upper, "downPrice": _alarmOption.downer, "state": (_alarmOption.isopen ? 1 : 0), "oldState": _alarmOption.ostate },
                    list: []
                };
            _hbglList.map(function (item) {
                postdata.list.push({
                    fltNbr: item.flight,
                    dptAirptCd: item.pre[item.check].split("-")[0],
                    arrvAirptCd: item.pre[item.check].split("-")[1],
                    id: item.id
                })
            })
            $.ajax({
                url: "/restful/upDownRemind",
                type: "post",
                data: {
                    str: JSON.stringify(postdata)
                },
                dataType: "json",
            })
                .done(function (res) {
                    if (res.succ !== true) {
                        alert(res.msg);
                    } else {
                        var fli = _this.currentFlight, hd = _this.currentHd;
                        //修改本地option                    
                        _this.watchSetting[fli][hd].isopen = _alarmOption.isopen;
                        _this.watchSetting[fli][hd].stime = _alarmOption.stime;
                        _this.watchSetting[fli][hd].etime = _alarmOption.etime;
                        _this.watchSetting[fli][hd].upper = _alarmOption.upper;
                        _this.watchSetting[fli][hd].downer = _alarmOption.downer;
                        _this.watchSetting[fli][hd].id = _alarmOption.id;
                        _this.alarmOption.temp = {
                            isopen: _alarmOption.isopen,
                            stime: _alarmOption.stime,
                            etime: _alarmOption.etime,
                            upper: _alarmOption.upper,
                            downer: _alarmOption.downer,
                            id: _alarmOption.id
                        }
                    }
                })
                .fail(function (err) {
                    alert("修改失败，请稍后重试。");
                })
            return;
        },
        //-----------------------------------------------------------------------------------------------票价对比 start
        getNewHbgl: function () {//取最新监控航班列表
            var _this = this;
            var f = _this.currentFlight, hd = _this.currentHd.split('-'), d = hd[0], a = hd[1];
            $.ajax({
                url: "/restful/getflightManage",// 生产环境
                type: "get",
                data: {
                    fltNbr: f,
                    dptAirptCd: d,
                    arrvAirptCd: a,
                },
                dataType: "json",
            })
                .done(function (res) {
                    _this.hbbglOption[_this.currentFlight][_this.currentHd] = [];
                    _this.hbglOption.meta = [];
                    _this.salesData[_this.currentFlight].watchFlight[_this.currentHd] = [];
                    if (res.data.length > 0) {
                        for (var watch of res.data) {
                            _this.hbbglOption[_this.currentFlight][_this.currentHd].push({
                                flight: watch.fltNbr,
                                from: watch.dptAirptCd,
                                to: watch.arrvAirptCd,
                                id: watch.id
                            })
                            _this.hbglOption.meta.push({
                                flight: watch.fltNbr,
                                pre: [watch.dptAirptCd + '-' + watch.arrvAirptCd],
                                id: watch.id,
                                check: 0
                            })
                        }
                    }
                    _this.salesData[_this.currentFlight].watchFlight[_this.currentHd] = _this.hbglOption.meta;
                    _this.hbglOption.postData = _this.hbglOption.meta;
                })
                .fail(function (err) {
                    _this.showHbglErrTip('× 获取列表失败，请刷新页面重试。');
                    console.log(err);
                })
        },
        addCompFlight: function () {//添加票价比对项
            if (this.hbglOption.changeFlag !== -1) { return; }
            this.hbglOption.meta.push(
                {
                    flight: '',
                    pre: [],
                    check: 0
                }
            )
            this.hbglOption.changeFlag = this.hbglOption.meta.length - 1;
        },
        showDelCompBtn: function (i) {
            if (this.hbglOption.changeFlag !== -1) return this.hbglCancel();
            return this.hbglOption.delIndex = i;
        },
        delCompFlight: function () {//删除票价比对项            
            this.hbglOption.meta.splice(this.hbglOption.delIndex, 1);
            this.hbglOption.changeFlag = -1;
            this.hbglOption.delIndex = -1;
            this.showHbglErrTip('× 删除成功。');
            this.sentHbglData();
        },
        getFlight: function (flight) {//通过航班获取 最新航线
            var _this = this;
            return new Promise(function (resolve, reject) {
                $.ajax({
                    url: "/restful/getflightLeg",// /getflightLeg
                    type: "get",
                    data: {
                        fltNbr: flight,//    fltNbr:HU7393
                    },
                    dataType: "json",
                })
                    .done(function (res) {
                        if (res.data.length === 0) {
                            reject('res is null');
                        }
                        resolve(res.data);
                    })
                    .fail(function (err) {
                        console.log("服务器异常，请稍后重试！");
                        reject(err);
                    })
                    .always(function () {
                        //
                    })
            })
        },
        findFlight: function (word) {//查找航班号和航段
            if (word.length < 6) {
                return;
            }
            var i = this.hbglOption.changeFlag;
            var cur = this.hbglOption.meta[i];
            this.getFlight(word).then((arr) => {
                cur.flight = word;
                cur.pre = arr;
                cur.check = 0;
            }).catch((err) => {
                console.log(err)
            })

        },
        hbglCancel: function () {//用户更改取消
            var cur = this.hbglOption.meta;
            var i = this.hbglOption.changeFlag;
            this.hbbglOption.delIndex = -1;
            if (this.hbglOption.changeFlag !== -1) {//添加状态
                cur.pop();
                return this.hbglOption.changeFlag = -1;
            } else {//删除状态
                return this.hbglOption.delIndex = -1;
            }
        },
        hbglComplate: function () {//用户更改完成
            var _this = this;
            var cur = _this.hbglOption.meta;
            var newitem = cur[cur.length - 1];//最后一个即为新增数据项
            var i = _this.hbglOption.changeFlag;
            if (cur[i].flight === '' || cur[i].check === -1) {
                return _this.showHbglErrTip();
            }
            if (cur[i].flight.toUpperCase() === _this.currentFlight && cur[i].pre[cur[i].check] === _this.currentHd) {
                return _this.showHbglErrTip("*请选择 " + _this.currentFlight + '/' + _this.currentHd + ' 之外的航段。');
            }
            for (var ii = 0; ii < cur.length - 1; ii++) {
                if (ii !== i) {//跳过比较本条
                    if (cur[ii].flight.toUpperCase() == cur[i].flight.toUpperCase() && cur[ii].pre[cur[ii].check].toUpperCase() === cur[i].pre[cur[i].check].toUpperCase()) {
                        return _this.showHbglErrTip("*已有相同记录,请添加其它航线");
                    }
                }
            }
            _this.hbglOption.changeFlag = -1;
            _this.hbglOption.postData = [];
            cur.map(function (item) {
                var _hd = item.pre[item.check].split('-');
                _this.hbglOption.postData.push({
                    fltNbr: item.flight,
                    dptAirptCd: _hd[0],
                    arrvAirptCd: _hd[1],
                    state: 1,
                })
            })
            _this.showHbglErrTip("*添加成功，采集后将会显示该航段数据。");
            _this.sentHbglData();
        },
        sentHbglData: function () {//修改航班管理后立即发送
            var _this = this;
            var _hd = _this.hangduan.current.split('-'), _flight = _this.currentFlight, _alarmOption = _this.alarmOption.real;
            var _hbglList = _this.hbglOption.meta.map(function (item) {
                var from = item.pre[item.check].split('-')[0], to = item.pre[item.check].split('-')[1];
                return {
                    flight: item.flight,
                    from: from,
                    to: to
                };
            });
            if (_hbglList.length == 0) {//全部删除则重置涨跌配置
                _this.alarmOption = {
                    changeFlag: false,   //是否处于编辑状态
                    oldStatus: 1,
                    temp: {
                        isopen: false,
                        stime: '09:00',
                        etime: '21:00',
                        upper: 200,
                        downer: 200
                    },//temp用于展示和修改   点击完成之后，real变为temp，取消则不变
                    real: {
                        isopen: false,
                        stime: '09:00',
                        etime: '21:00',
                        upper: 200,
                        downer: 200
                    }
                }
            }
            var source = _this.hbbglOption[_this.currentFlight][_this.currentHd];
            var newHbglList = _this.compList(source, _hbglList);
            var postdata = {
                currentFlight: { "fltNbr": _flight, "dptAirptCd": _hd[0], "arrvAirptCd": _hd[1] },
                watchFlight: { "startWatchTime": _alarmOption.stime, "endWatchTime": _alarmOption.etime, "upPrice": _alarmOption.upper, "downPrice": _alarmOption.downer, "state": _alarmOption.isopen ? 1 : 0 },
                list: newHbglList
            };
            $.ajax({
                url: "/restful/flightManage",// 生产环境
                type: "post",
                data: {
                    str: JSON.stringify(postdata)
                },
                dataType: "json",
            })
                .done(function (res) {
                    if (res.succ !== true) {
                        console.log('err');
                    } else {
                        console.log(res.msg);
                        _this.getNewHbgl();
                    }
                })
                .fail(function (err) {
                    alert('修改失败，请稍后重试');
                })
        },
        createFlight: function (FlightCouple) {//初始化航班对
            var _this = this;
            _this.salesData = null || {};
            FlightCouple.map(function (item) {
                _this.salesData[item] = {
                    pre: [],//航段
                    preIndex: 0,
                    data: {
                    }
                }
                _this.priceData[item] = {};
                _this.basicData.hdList[item] = [];
                _this.hbbglOption[item] = {};
                _this.watchSetting[item] = {};
            })
        },
        getLaneList: function (lane) {
            var _this = this, arr = lane.split('=');
            if (arr.length === 3) {
                _this.currentLane = {
                    dptAirptCd: parent.airportMap[arr[0]].iata,
                    pasStp: parent.airportMap[arr[1]].iata,
                    arrvAirptCd: parent.airportMap[arr[2]].iata,
                }
            } else {
                _this.currentLane = {
                    dptAirptCd: parent.airportMap[arr[0]].iata,
                    pasStp: '',
                    arrvAirptCd: parent.airportMap[arr[1]].iata,
                }
            }
        },
        initTimePicker: function () {//初始化时间选择器
            var _this = this;
            for (let i = 0, j = 60; i < j; i++) {
                if (i < 24) {
                    _this.timePicker.hourList.push(i > 9 ? i : ('0' + i));
                }
                _this.timePicker.secList.push(i > 9 ? i : ('0' + i));
            }
        },
        resetTimePicker: function (flag) {
            this.timePicker.chour = '00';
            this.timePicker.csec = '00';
            this.timePicker.flag = flag;
            this.timePicker.show = true;
        },
        checkedTime: function () {
            let that = this;
            that.timePicker.show = false;
            if (that.timePicker.flag) {
                that.alarmOption.temp.stime = that.timePicker.chour + ':' + that.timePicker.csec;
            } else {
                that.alarmOption.temp.etime = that.timePicker.chour + ':' + that.timePicker.csec;
            }
        }
    },
    watch: {    //數據監聽
        alarmOption: {
            handler: function (val, oval) {
                if (val.changeFlag === false) {
                    this.noChangeAlarm();
                }
            },
            deep: true
        }
    },
    created: function () {  //創建echart dom，取出parent.supData
        this.initTimePicker();
    },
    beforeMount: function () {
        var _this = this;
        _this.basicData.flightList = _this.flightToArr(parent.supData.flight);
        _this.basicData.oflight = _this.shortFlyNum(_this.basicData.flightList);
        _this.parentData.lane = parent.supData.lane;
        _this.createFlight(_this.basicData.flightList);
        _this.getLaneList(parent.supData.lane)
        _this.getNowDate();
    },
    mounted: function () {//裝載數據
        var _this = this;
        _this.echartObj.sales = echarts.init(document.getElementById('salesChart'));
        _this.echartObj.comp = echarts.init(document.getElementById('compChart'));
        _this.changeFlight(_this.basicData.flightList[0]);
        _this.initDatePicker();
    },
    destroyed: function () {
        this.echartObj.sales.destroyed();
        this.echartObj.comp.destroyed();
    }
})