<%-- 
 * @Author: lonhon 
 * @Date: 2017-09-07 15:45:33 
 * @Last Modified by: github.lonhon
 * @Last Modified time: 2017-09-07 15:46:18
 * @Ps: 这是一个用vue2做的页面 
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String flagpage = request.getParameter("flagpage");
 %>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>客票监控</title>
<link rel="stylesheet" type="text/css" href="/indexSource/reset.css">
<link rel="stylesheet" type="text/css" media="all"	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/bootstrap.css">
<link rel="stylesheet" type="text/css" media="all"	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/daterangepicker-bs3.css" />
<link rel="stylesheet" href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/salesReports-new.css">
<link rel="stylesheet" href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/coalesce.css">
<link rel="stylesheet" href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/TMcommon.css">
<link rel="stylesheet" href="/err/ed.css">
<link rel="stylesheet" href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/ticketMonitor.css">
<link rel="stylesheet" href="https://at.alicdn.com/t/font_234130_nem7eskcrkpdgqfr.css"> 
</head>
<script type="text/javascript">
var flagpage = <%=flagpage%>;
</script>

<body>
    <div id="app" class="app sr-box" style="" @click="bodyClick($event)">
		<div class="TM-loading" id="TM-sloading" style="top:40px;display:none;"></div>	
        <!-- 头部查询控件 -->
		<div class="sr-box-head">
			<div class="sr-box-head-classify">
				<ul>
             	    <li class='tltle-sel' title='销售报表' id="_permissions_13"><a class='page-fun-change' href='/salesReport'>&#xe628;</a></li>
                    <li class='tltle-sel' title='航线历史收益统计' id="_permissions_1"><a class='page-fun-change' href='/airline'>&#xe629;</a></li>
                    <li class='tltle-sel' title='销售动态' id="_permissions_5"><a class='page-fun-change' href='/buyTicketReport'>&#xe624;</a></li>
                    <li class='tltle-sel' title='销售数据' id="_permissions_5"><a class='page-fun-change' href='/SalesData/accountCheck'>&#xe688;</a></li>
                    <li class='tltle-sel tltle-selI' id="_permissions_8">&#xe6a9;<span>客票监控</span></li>
				</ul>
			</div>
			<div class="sr-box-head-inquire" id='sr-box-head-inquire'>
				<div class="_set-flight">
					<div class="_set-name" id='flt_nbr_Count'>航班号</div>
                    <div class="_set-list-set"><input v-model="basicData.oflight" @input="flightToFullName(basicData.oflight)" id="SD-head-inquire" type="text" placeholder="如MU1234" maxlength="8" spellcheck=false readonly="true"></div>
				</div>
				<%-- <div class="_set-time">
					<div class="_set-name">起止时间</div>
					<div class="_set-name-b">
						<input type="text" placeholder="时间" id='startEndDate' maxlength="16">
					</div>
				</div> --%>
                <div class="_set-place-duplexing">
                    <div class="_set-name">航线</div>
                    <div class="SD-set-list-title" v-text="parentData.lane"></div>
                    <div class="SD-airportNam">
                    	<ul>                        
                    	</ul>
                    </div>
                </div>
				<%-- <div class="_set-choose">
					<input id='isnon-stop' type="checkbox" checked="checked">
					<div>直飞</div>
				</div>
				<div class="_set-choose" id="divpas">
					<input id='ishas-stop' type="checkbox" checked="checked">
					<div>经停</div>
				</div> --%>
				<%-- <div class="_set-query" id="pageQuery" @click="doSearch()">查询</div> --%>
			</div>
		</div>

        <!-- 左侧导航 -->
        <div class="lin-historical-body-navigation">        
            <div class="body_navig" v-for="(val, index) in basicData.flightList" :class="{current: val===currentFlight}" :key="val" @click="changeFlight(val)"><span v-text="val"></span></div>
        </div>
        
        <div class="src-wrap">        
            <div class="err" v-show="showControl.error" style="display:block;z-index: 50;">
                <div id='err-one'><img src="/err/errNodata.png"></div>
                <div id='err-two'><img src="/err/err2.png"></div>
            </div>
            <div id="main-container" class="src-container">
                <div class="main">
                    <div class="header" style="position:relative;z-index:52;">
                        <%-- 历史查询 --%>
                        <span class="tbtn tbtn-xs" :class="{'tbtn-checked':showControl.hisShow, 'tbtn-hover': showControl.error}" @click="showControl.hisShow = !showControl.hisShow">&#xe6be;&nbsp;&nbsp;&nbsp;历史查询</span>
                        <span v-if="showControl.error" style="display: inline-block;font-size:16px;border: 1px solid;padding: 10px 20px;"><i>&#xe697;</i> 可点击"历史查询"重新查询其它日期的数据</span>
                        <span class="tbtn tbtn-xs" v-show="!showControl.error" :class="{ 'hiddened': !historyOption.state}" @click="historyOption.state = false, exitHisSearch()">退出历史查询</span>
                        <div class="date-picker-container" v-show="showControl.hisShow">
                            <div class="date-picker-box-l" id="schedule-boxa"></div>
                            <div class="date-picker-box-l" style="pointer-events:none;" id="schedule-boxb"></div>
                            <div class="date-picker-box-s">
                                <p >
                                <span class="tbtn tbtn-xs" style="color: #fff;background-color: #4353a3;margin-right: 0;" @click="doHistorySearch">开始查询</span>
                                </p>
                            </div>
                        </div>
                        <%-- 航段 --%>
                        <ul id="perlane" v-show="!showControl.error">
                            <li 
                            class="tbtn tbtn-l" 
                            :class="{'tbtn-checked': index===hangduan.active }" 
                            v-for="(hd, index) in basicData.hdList[currentFlight]" :key="hd" 
                            v-text="hd"
                            @click="changeHd(currentFlight, hd, index)"></li>
                        </ul>
                    </div>
                    
                    <div class="barchar-box">
                        <h3>
                            {{basicData.stime + '-' + basicData.etime}} 客票价格及销售情况
                        </h3>
                        <div class="chart-radio">
                            <span @click="salesChart.isAddUp = true, changeHd(currentFlight, currentHd)"><span class="ticket-radio cur" :class="{'rd-checked':salesChart.isAddUp}"> </span>&nbsp;&nbsp;&nbsp;<label style="cursor:pointer;">累计销售数据</label></span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <span @click="salesChart.isAddUp = false, changeHd(currentFlight, currentHd)"><span class="ticket-radio cur" :class="{'rd-checked':!salesChart.isAddUp}"> </span>&nbsp;&nbsp;&nbsp;<label style="cursor:pointer;">昨日销售数据</label></span>
                        </div>
                        <div class="chart-box">
                            <div class="chart" id="salesChart"></div>
                            <div v-show="showControl.cwChart" class="chart chart-data-cover" style="position:absolute;top:0;font-size:30px;text-align:center;background-color:rgb(17,30,51);line-height: 500px;z-index:49;">暂无数据</div>
                        </div>
                        <p class="desctxt">*已售客票为昨日数据，销售价格为今日数据，仅供参考</p>
                    </div>

                    <div class="barchar-box">
                        <h3>
                            {{basicData.stime + '-' + basicData.etime}} 销售票价对比 
                            <div  v-show="true || (hbglOption.meta.length > 0 && hbglOption.changeFlag == -1)" 
                                class="tbtn tbtn-xs" :class="{'tbtn-checked':showControl.alarmShow}" 
                                style="display:inline-block;position:relative;">
                                    <span @click="showControl.alarmShow = !showControl.alarmShow, noChangeAlarm(), timePicker.show=false" style="display: inline-block;width:100%;">&#xe6be;&nbsp;&nbsp;&nbsp;涨跌提醒</span>
                                    <div v-show="showControl.alarmShow && hbglOption.changeFlag == -1"  class="caretip-box" :class="{'caretip-box-inh':alarmOption.changeFlag}">
                                        <div class="caretip-main">
                                            <div class="caretip-header">
                                                <p>提示闹钟设置</p>
                                                <p @click="alarmOption.changeFlag = !alarmOption.changeFlag">
                                                    <span v-show="!alarmOption.changeFlag" class="textbtn" style="font-size:20px;">&#xe69f;</span>
                                                    <span v-show="alarmOption.changeFlag" class="textbtn-on">编辑</span>
                                                </p>
                                            </div>                                
                                            <div class="caretip-body">
                                                <%-- 展示 --%>
                                                <table class="caretip-tbl" cellspacing="0" v-show="!alarmOption.changeFlag">
                                                    <tr> 
                                                    <td>是否开启</td>
                                                    <td>
                                                        <div class="lswitch" :class="{'lswitch-dark': !alarmOption.real.isopen}">
                                                            <div class="lswitch-circle" :class="{'lswitch-circle-c': alarmOption.real.isopen}"></div>
                                                        </div>
                                                    </td> 
                                                    </tr>
                                                    <tr> 
                                                    <td>短信提醒时段</td>
                                                    <td class="darkp">
                                                        {{alarmOption.real.stime + ' - ' + alarmOption.real.etime}}
                                                    </td> 
                                                    </tr>
                                                    <tr> 
                                                    <td>涨幅提醒金额</td>
                                                    <td class="darkp">{{alarmOption.real.upper}}</td> 
                                                    </tr>
                                                    <tr> 
                                                    <td>下跌提醒金额</td>
                                                    <td class="darkp">{{alarmOption.real.downer}}</td> 
                                                    </tr>
                                                </table>
                                                <%-- 编辑ing --%>
                                                <table class="caretip-tbl" cellspacing="0" v-show="alarmOption.changeFlag">
                                                    <tr> 
                                                    <td>是否开启</td>
                                                    <td>
                                                        <div class="lswitch cur" :class="{'lswitch-dark': !alarmOption.temp.isopen}" @click="alarmOption.temp.isopen = !alarmOption.temp.isopen">
                                                            <div class="lswitch-circle" :class="{'lswitch-circle-c': alarmOption.temp.isopen}"></div>
                                                        </div>
                                                    </td> 
                                                    </tr>
                                                    <tr> 
                                                    <td>短信提醒时段</td>
                                                    <td>
                                                        <input type="text" class="time-hm darkp" v-model="alarmOption.temp.stime" maxlength="5" readonly="readonly" @click="resetTimePicker(true)">
                                                         - 
                                                        <input type="text" class="time-hm darkp" v-model="alarmOption.temp.etime" maxlength="5" readonly="readonly" @click="resetTimePicker(false)">
                                                    </td> 
                                                    </tr>
                                                    <tr> 
                                                    <td>涨幅提醒金额</td>
                                                    <td>
                                                        <input type="text" class="je-txt darkp" v-model="alarmOption.temp.upper" @change="numberTest(true,alarmOption.temp.upper)" maxlength=4>
                                                    </td> 
                                                    </tr>
                                                    <tr> 
                                                    <td>下跌提醒金额</td>
                                                    <td>
                                                        <input type="text" class="je-txt darkp" v-model="alarmOption.temp.downer" @change="numberTest(false,alarmOption.temp.downer)" maxlength=4>
                                                    </td>
                                                    </tr>
                                                </table>                                                
                                                <ul class="time-box" v-if="timePicker.show">
                                                    <p class="time-title">时间选择</p>
                                                    <li>
                                                        <p>时</p>
                                                        <ol>
                                                            <li v-for="hour in timePicker.hourList" @click="timePicker.chour = hour" :class="{'time-checked':timePicker.chour == hour}">{{hour}}</li>
                                                        </ol>
                                                    </li>
                                                    <li>
                                                        <p>分</p>
                                                        <ol>
                                                            <li v-for="sec in timePicker.secList" @click="timePicker.csec = sec" :class="{'time-checked':timePicker.csec == sec}">{{sec}}</li>
                                                        </ol>
                                                    </li>
                                                    <div> 
                                                        <input class="btn btn-default btn-sm" type="button" value="取消" @click="timePicker.show=false">
                                                        <input class="btn btn-primary btn-sm" type="button" value="确定" @click="checkedTime">
                                                    </div>
                                                </ul>
                                            </div>
                                            <div class="caretip-footer" v-show="alarmOption.changeFlag">
                                                <div class="caretip-btn-area">
                                                    <button class="tbbtn tbbtn-cancel" @click="noChangeAlarm()">取消</button>
                                                    <button class="tbbtn tbbtn-enter" @click="doChangeAlarm()">确定</button>
                                                </div>
                                            </div>                                
                                        </div>
                                    </div>
                            </div>
                        </h3>
                        <div class="chart-box">
                            <div class="chart" id="compChart"></div>
                            <div v-show="true || showControl.pjChart" class="chart chart-data-cover" style="position:absolute;top:0;font-size:30px;text-align:center;background-color:rgb(17,30,51);line-height: 500px;z-index:49;">暂无数据</div>
                        </div>
                        <div class="chart-radio chart-radio-right">
                            <div v-show="historyOption.state" class="his-radio">
                                <span @click="hisChangLineChart(true)"><span class="ticket-radio cur" :class="{'rd-checked':historyOption.isLowest}"> </span>&nbsp;&nbsp;&nbsp;<label style="cursor:pointer;">最低票价</label></span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <span @click="hisChangLineChart(false)"><span class="ticket-radio cur" :class="{'rd-checked':!historyOption.isLowest}"> </span>&nbsp;&nbsp;&nbsp;<label style="cursor:pointer;">平均票价</label></span>
                            </div>
                            <span class="tbtn tbtn-xs wt" :class="{'tbtn-checked':showControl.hbglShow}" @click="showControl.hbglShow = !showControl.hbglShow, hbglCancel()" title="添加对比航线，最多3条">航班号管理</span>
                            <div class="hbgl-box chart-radio-right" :class="{onChange: hbglOption.changeFlag!== -1}" v-show="showControl.hbglShow && !alarmOption.changeFlag" spellcheck ="false">
                                    <table class="hbgl-tbl hbgl-head">
                                        <tr>
                                            <td>航班号</td>
                                            <td colspan="2">航段</td>
                                            <td class="head-last"><span title="添加" v-show="hbglOption.meta.length < 3 && hbglOption.delIndex == -1" @click="addCompFlight()">&#xe6b0;</span></td>
                                        </tr>
                                    </table>
                                    <table class="hbgl-tbl hbgl-item" v-for="(item,index) in hbglOption.meta">
                                        <tr>
                                            <td>
                                                <span v-show="hbglOption.changeFlag!==index" v-text="item.flight"></span>
                                                <input v-show="hbglOption.changeFlag===index" type="text" class="je-txt hbgl-txt" :value="item.flight" maxlength="6" @input="findFlight($event.target.value)" placeholder="如CA8888">
                                            </td>
                                            <td colspan="2" class="iop">
                                                <span v-show="hbglOption.changeFlag!==index" class="hbgl-drop">{{item.pre[item.check]}}</span>
                                                <select v-show="hbglOption.changeFlag===index" class="form-control input-sm hbgl-drop" @change="item.check = $event.target.options.selectedIndex">
                                                    <option v-for="per in item.pre">{{per}}</option>
                                                </select>
                                            </td>
                                            <td class="lasttd">
                                                <%-- <span class="txt-btn txt-btn-change" @click="editFlight(index)">&#xe6d1;</span> --%>
                                                <span class="txt-btn txt-btn-del" @click="showDelCompBtn(index)">&#xe84b;</span>
                                            </td>
                                        </tr>
                                    </table>
                                    <h4 v-show="hbglOption.meta.length==0" style="text-align:center;">暂无添加</h4>
                                    <p style="color:rgba(255,0,0,.7);font-size:10px;padding-left:30px;" v-show="hbglOption.errTip">{{ hbglOption.errText }}</p>
                                    <div class="hbgl-foot" v-show="hbglOption.changeFlag!== -1">
                                        <button type="button" class="btn btn-default" @click="hbglCancel()">取消</button>
                                        <button type="button" class="btn btn-primary" @click="hbglComplate()">完成</button>
                                    </div>
                                    <div class="hbgl-foot" v-show="hbglOption.delIndex!== -1">
                                        <button type="button" class="btn btn-group-xs btn-default" @click="hbglCancel()">取消</button>
                                        <button type="button" class="btn btn-group-xs btn-danger" @click="delCompFlight()">删除</button>
                                    </div>
                                    
                            </div>
                        </div>
                        <p class="desctxt">*计划采集，仅供参考</p>
                    </div>
                </div>
            </div>
        </div>
		<!-- 无数据提示 -->
        <div class="TM-loading" v-show="showControl.loading"></div>
	</div>
    </div>
    <script src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn")%>/jquery1.8.3.js"></script>   
    <script src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn")%>/vue-v2.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/ticketMonitor/echarts.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/ticketMonitor/schedule.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/moment.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/daterangepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/coalesce.js"></script>
    <script src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/ticketMonitor/ticketMonitor.js"></script>
    <script src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/ticketMonitor/ticketVue.js" defer></script>
</body>
</html>