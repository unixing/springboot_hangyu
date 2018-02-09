
; (function (undefined) {
    var _global;
    //工具函数
    //配置合并
    function extend(def, opt, override) {
        for (var k in opt) {
            if (opt.hasOwnProperty(k) && (!def.hasOwnProperty(k) || override)) {
                def[k] = opt[k]
            }
        }
        return def;
    }
    //日期格式化
    function formartDate(y, m, d, symbol) {
        symbol = symbol || '-';
        m = (m.toString())[1] ? m : '0' + m;
        d = (d.toString())[1] ? d : '0' + d;
        return y + symbol + m + symbol + d
    }

    function Schedule(opt) {
        var def = {},
            opt = extend(def, opt, true),
            iscurrent = opt.iscurrent,  //true是第一个
            thisDate = new Date(),//本日日期
            curDate = opt.date ? new Date(opt.date) : new Date(),//初始化日期
            year = curDate.getFullYear(),
            month = curDate.getMonth(),
            day = curDate.getDate(),
            currentYear = curDate.getFullYear(),
            currentMonth = curDate.getMonth(),
            currentDay = curDate.getDate(),
            selectedDate = opt.date,
            el = document.querySelector(opt.el) || document.querySelector('body'),
            _this = this;
        var bindEvent = function () {
            el.addEventListener('click', function (e) {
                switch (e.target.id) {
                    case 'nextMonth':
                        _this.nextMonthFun();
                        break;
                    case 'nextYear':
                        _this.nextYearFun();
                        break;
                    case 'prevMonth':
                        _this.prevMonthFun();
                        break;
                    case 'prevYear':
                        _this.prevYearFun();
                        break;
                    default:
                        break;
                };
                if (e.target.className.indexOf('currentDate') > -1) {
                    if(new Date(e.target.title) > thisDate) return false;    //不能选择大于本日的日期
                    opt.clickCb && opt.clickCb(year, month + 1, e.target.innerHTML);
                    selectedDate = e.target.title;
                    day = e.target.innerHTML;
                    render();
                }
            }, false)
        }
        var getFL = function(date,flag){//获取指定日期的当月1日or最后一天
            var d = new Date(date),
                y = d.getFullYear(),
                m = d.getMonth();
            return (flag? new Date(y,m+1,1):new Date(y,m,1));
        }
        var init = function () {
            var scheduleHd = '<div class="schedule-hd">' +
                '<div>' +
                // '<span class="arrow icon iconfont icon-116leftarrowheads" id="prevYear" ></span>' +
                '<span class="arrow icon iconfont icon-112leftarrowhead" id="prevMonth"></span>' +
                '</div>' +
                '<div class="today">' + year + '-' + (month + 1) + '</div>' +
                '<div>' +
                '<span class="arrow icon iconfont icon-111arrowheadright" id="nextMonth"></span>' +
                // '<span class="arrow icon iconfont icon-115rightarrowheads" id="nextYear"></span>' +
                '</div>' +
                '</div>'
            var scheduleWeek = '<ul class="week-ul ul-box">' +
                '<li>SU</li>' +
                '<li>MO</li>' +
                '<li>TU</li>' +
                '<li>WE</li>' +
                '<li>TH</li>' +
                '<li>FR</li>' +
                '<li>SA</li>' +
                '</ul>'
            var scheduleBd = '<ul class="schedule-bd ul-box" ></ul>';
            el.innerHTML = scheduleHd + scheduleWeek + scheduleBd;
            bindEvent();
            render();
        }
        var render = function () {
            if (iscurrent && selectedDate !== '') {//只能选择本日之前。
                if (new Date(selectedDate) > new Date()) return console.log('b');
            }
            var fullDay = new Date(year, month + 1, 0).getDate(), //当月总天数
                startWeek = new Date(year, month, 1).getDay(), //当月第一天是周几
                total = (fullDay + startWeek) % 7 == 0 ? (fullDay + startWeek) : fullDay + startWeek + (7 - (fullDay + startWeek) % 7),//元素总个数
                lastMonthDay = new Date(year, month, 0).getDate(), //上月最后一天
                eleTemp = [],
                curLastDate = getFL(selectedDate,true), //选择月最后一天
                curFirstDate = getFL(selectedDate,false), //选择月第一天
                sday = (new Date(selectedDate)).getDate() || currentDay;
            for (var i = 0; i < total; i++) {
                if (i < startWeek) {
                    eleTemp.push('<li class="other-month"><span class="dayStyle" style="opacity:0;">' + (lastMonthDay - startWeek + 1 + i) + '</span></li>')
                    // eleTemp.push('<li class="other-month"><span class="dayStyle"></span></li>')
                } else if (i < (startWeek + fullDay)) {
                    var nowDate = formartDate(year, month + 1, (i + 1 - startWeek), '-');
                    var addClass = '';
                    if (selectedDate == nowDate) {    //选中日期
                        addClass = 'selected-style';
                    } else if (formartDate(currentYear, currentMonth + 1, currentDay, '-') == nowDate && selectedDate=="") {    //未选中时也标出今日
                        addClass = 'today-flag';
                    }
                    var nowDateFormal = new Date(nowDate);
                    if(iscurrent){//活动日历之后
                        if(nowDateFormal > new Date(selectedDate) && nowDateFormal<= curLastDate){
                            addClass += ' select-gray';
                        }
                    }else{//活动日历之前
                        if(nowDateFormal < new Date(selectedDate) && nowDateFormal >= curFirstDate){
                            addClass += ' select-gray';
                        }
                    }
                    eleTemp.push('<li class="current-month" ><span title=' + nowDate + ' class="currentDate dayStyle ' + addClass + '">' + (i + 1 - startWeek) + '</span></li>')
                } else {
                    eleTemp.push('<li class="other-month"><span class="dayStyle" style="opacity:1;">' + (i + 1 - (startWeek + fullDay)) + '</span></li>')
                }
            }
            el.querySelector('.schedule-bd').innerHTML = eleTemp.join('');
            el.querySelector('.today').innerHTML = year + '-' + (month + 1);
        };
        this.nextMonthFun = function () {
            if (month + 1 > 11) {
                year += 1;
                month = 0;
            } else {
                month += 1;
            }
            render();
            opt.nextMonthCb && opt.nextMonthCb(year, month + 1, day);
        },
        // this.nextYearFun = function () {
        //     year += 1;
        //     render();
        //     opt.nextYeayCb && opt.nextYeayCb(year, month + 1, day);
        // },
        this.prevMonthFun = function () {
            if (month - 1 < 0) {
                year -= 1;
                month = 11;
            } else {
                month -= 1;
            }
            render();
            opt.prevMonthCb && opt.prevMonthCb(year, month + 1, day);
        },
        // this.prevYearFun = function () {
        //     year -= 1;
        //     render();
        //     opt.prevYearCb && opt.prevYearCb(year, month + 1, day);
        // }
        init();
    }
    //将插件暴露给全局对象
    _global = (function () { return this || (0, eval)('this') }());
    if (typeof module !== 'undefined' && module.exports) {
        module.exports = Schedule;
    } else if (typeof define === "function" && define.amd) {
        define(function () {
            return Schedule;
        })
    } else {
        !('Schedule' in _global) && (_global.Schedule = Schedule);
    }

}());