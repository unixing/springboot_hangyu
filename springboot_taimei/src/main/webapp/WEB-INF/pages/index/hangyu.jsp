<%@page import="org.ldd.ssm.crm.domain.Employee"%>
<%@page import="org.ldd.ssm.crm.utils.UserContext"%>
<%@page import="java.util.List"%>
<%@page import="org.ldd.ssm.crm.domain.Menu"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>航路测算-太美航空</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand" />
    <meta name="renderer" content="webkit"> 
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- <link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/systemCss/systemCss.css" rel="stylesheet"> --%>
    <link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/bootstrap.css" rel="stylesheet">
    <link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/animate.css" rel="stylesheet">
    <link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/platform.css" rel="stylesheet">
    <link href="/indexSource/hangyu/hangyu.css" rel="stylesheet">
    <link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/hDate.css" rel="stylesheet">
    <%-- <link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/cs_calculate.css" rel="stylesheet"> --%>
    <link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/coalesce.css" rel="stylesheet"/>
    <link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/drawSimulateAirRoute.css" rel="stylesheet"/>
	<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/TMcommon.css" rel="stylesheet">
    <%-- <link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/warehouse.css" rel="stylesheet"/> --%>
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link rel="shortcut icon" href="/img/icon/logo.png" />
    <script src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/jquery1.8.3.js"></script>
    <script src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/ajaxfileupload.js"></script>
    <script src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/bootstrap.js"></script>
    <script src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/jquery.myConfirm.js"></script>
    <%@include file="/WEB-INF/pages/index/js/index.jsp"%>
</head>

<script type="text/javascript">
	var useName_n = '<%=UserContext.getUsertext()%>';
    var userUuid = '<%=UserContext.getUseruuid()%>';
    window.HLCS_COLOR = {
        gljc: '#3c78ff',
        wkt: '#d44c1f',
        tjd: 'rgba(60,120,255,0.6)',
        ykt: '#74899a',
        line: '#3c78ff',
        area0: 'rgba(60,120,255,0.3)',
        area1: 'rgba(60,120,255,0.5)'
    }
	if(localStorage.getItem("_lisj"+useName_n)!=null){
		 setTimeout(function(){document.getElementById("_lisj").innerText=localStorage.getItem("_lisj"+useName_n);},350)
    }
	var cityName = "<%=session.getAttribute("companyName_session")%>";
	var userPhone = '<%=((Employee)session.getAttribute("user_in_session")).getPhone()%>';
    var cityIata = '<%=session.getAttribute("companyItia_session")%>';
	var menus = JSON.parse('<%=session.getAttribute("menuAll")%>');
	var menus1 =JSON.parse('<%=session.getAttribute("menu")%>');
	var ipness = <%=UserContext.getIpNess()%>;
	var menus2=[];
	$(function(){
		for(var e=0;e<menus.length;e++){
			var istag=false;
			for(var x=0;x<menus1.length;x++){
				if(menus[e].id==menus1[x].id){
					istag=true;
				}
			}
			if(istag==false){
				menus2.push({
					id:menus[e].id,
					name:menus[e].name,
					url:menus[e].url,
					type:0
				});
			}else{
				menus2.push({
					id:menus[e].id,
					name:menus[e].name,
					url:menus[e].url,
					type:1
				});
			}
		}
		var arts = JSON.parse(window.sessionStorage.getItem("nameList"));
		if(typeof(arts)!='undefined'&&arts!=null&&arts.length>0){
			var groupName = window.sessionStorage.getItem('groupName')+'';
			var option = '<div class="airportList" style="display:none"><div class="airports" itia="">'+groupName+'</div>';
			for(var i=0;i<arts.length;i++){
				var user = arts[i];
				if(user.code==cityIata){
					$('#currentAirPort').html('<span>'+user.oname+'市</span>');
				}else{
					option +='<div class="airports" itia="'+user.code+'" title="'+ user.name +'">'+ user.oname +'市</div>';
                }
			}
			option +='</div>';
			$('#currentAirPort').append(option);
			$('.airports').on('click',function(){
				location.href="indexd?itia="+$(this).attr('itia');
			});
		}else{
			$('#currentAirPort').html('<span>'+cityName+'市</span>');
		}
	});
</script>
<body onselectstart="return false" unselectable="return false">
<div class="pla-box container-fluid">
    <div id="container"></div>
    <div class="pla-head" style="display:none;">
        <div class="pla-title">
           <!--  <h3>空港智家&nbsp;(+)</h3>
            <p>Airport Big DataService</p> -->
        </div>
        <div class="pla-spread">
            <ul>
                <li>&#xe694;</li>
                <li>统计
                    <ul class="pla-spread-statistical">
                        <li class="menu" id='_permissions_1'><a class="flagClass" href="/airline" target="new_frame">航线历史收益统计</a></li>
                        <li class="menu" id='_permissions_2'><a href="/outPort" target="new_frame">机场历史运营</a></li>
                        <li class="menu" id='_permissions_3'><a class="flagClass" href="totalFlyAnalysis" target="new_frame">共飞运营对比</a></li>
                    </ul>
                    <!-- <div class="pla-spread-indicate">&#xe60a;</div> -->
                </li>
                <li>报表
                    <ul class="pla-spread-statistical">
                        <li class="menu" id='_permissions_4'><a class="flagClass" href="/SourceDistribution" target="new_frame">客源组成</a></li>
                        <li class="menu" id='_permissions_5'><a class="flagClass" href="/buyTicketReport" target="new_frame">销售动态</a></li>
                        <li class="menu" id='_permissions_13'><a class="flagClass" href="/salesReport" target="new_frame">销售报表</a></li>
                        <li class="menu" id='_permissions_8'><a class="flagClass" href="/SalesData/accountCheck" target="new_frame">销售数据</a></li>
                    </ul>
                    <!-- <div class="pla-spread-indicate">&#xe60a;</div> -->
                </li>
                <li>监控
                    <ul class="pla-spread-statistical">
                        <li class="menu" id='_permissions_6'><a href="/airline_dynamic" target="new_frame">航班动态</a></li>
                       <li class="menu" id='_permissions_7'><a href="/processTask" target="new_frame">时刻分布</a></li>
                    </ul>
                    <!-- <div class="pla-spread-indicate">&#xe60a;</div> -->
                </li>
               <!--  <li>客源分析</li> -->
            </ul>
        </div>
    </div>
    <div class="pla-tool">
        <%-- <ul>
            <li>
               ${companyName_session}市
            </li>
            <li>
                <span>&#xe670;</span>
                <span>${user_in_session.usrNm}</span>
                <div class='pla-tool-notice'>&#xe64b;</div>
                <ul class="pla-tool-account">
                    <li style="color: gray;">个人资料</li>
                    <li>系统设置</li>
                    <li style="color: gray;">手机APP</li>
                    <li style="color: gray;">消息</li>
                    <li>退出</li>
                </ul>
            </li>
            <li>
                <span>&#xe60b;</span>
                <span>工具</span>
                 <ul class="pla-tool-accounts">
                    <li>时刻查询</li>
                </ul>
            </li>
        </ul> --%>
        <div class="pla-tool-name" id="currentAirPort"><%-- <span>${companyName_session}市</span> --%></div>
        <div class="pla-tool-useName">
        <div class="pla-tool-iList">
            <div class="pla-tool-img">
	            <%
	            	if(!"".equals(UserContext.getUser().getHeadPath())){
	             %>
                	<img class="small-head" src="${user_in_session.headPath}"  onerror="this.onerror=null;this.src='/images/af4f778f04855b76242924b06f0d8360.jpg'">
                <%}else{ %>
                	<img class="small-head" src="/css/images/ph.jpg"   onerror="this.onerror=null;this.src='http://img1.3lian.com/2015/gif/w/74/43.jpg'">
                <%} %>
            </div>
            <div class="pla-tool-icon">&#xe64c;</div>
        </div>
            <div class="useName-n" id="useName_n"><%=UserContext.getUsertext()%></div>
            <ul class="se-list">
                <li class="sSettings" onclick="setting()">设置</li>
                <li id="userResponse">用户反馈</li>
                <li id="userGuide"><a href="/userCommit/exportDocument" target="_self">帮助手册</a></li>
                <li>退出登录</li>
            </ul>
        </div>
        <div class="pla-tool-set">
            <div class="set-gj">工具</div>
            <ul class="useName-list">
                <li onclick='airLine()'>航司信息查询</li>
                <li onclick='cityQuery()'>城市信息查询</li>
                <li onclick='airQuery()'>机场信息查询</li>
                <%--<li>航班号查询</li>--%>
                <li id='global-routes'>全国航线视图</li>
                <li onclick="opensk()">时刻分布</li>
                 <%--<%if(UserContext.getIpNess()){ %>--%>
                	<%--<li id="simulate-routes">模拟航线视图</li>--%>
                <%--<%} %>--%>
                 <li onclick="calculate()">航路测算</li>
            </ul>
        </div>
    </div>
    
    
    
    
<!--     【首页样式】增加右侧查询图标    2017-3-29 -->
    <div class="pla-search">
    	<div class="search-iconarea">
	    	<span class="pla-search-icon">&#xe691;</span>
    	</div>
    	<div class="pla-search-panel">
	    	<div class="pla-search-panel-head">
	    		<div class="for-search-select">
					<p> <span class="search-type-t" id="pla-search-tor" tag="fnum">航班号</span> <span class="search-icon">&#xe648;</span></p>
					<ul class="slt">
						<li tag="fnum">航班号</li>
						<li tag="fdot">航点</li>
					</ul>
				</div>
			</div>	    
		    <div class="pla-search-panel-body">
		    	<input id="pla-search-id" type="text" placeholder="输入航班号后敲击回车搜索" maxlength="22">
			</div>
		</div>
    </div>
    </div>
    <div class="pla-zoom">
        <div>&#xe60d;</div>
        <div>&#xe60e;</div>
    </div>
    <div id="cs_tag" class="cs_tag">
        <div id='cs_tagItem1'>
            <div class="cs_tagPoint cs_tagWay"><span>&#xe6c5;</span>关联机场</div>
            <div class="cs_tagPoint cs_tagWay"><span>&#xe6c5;</span>已开通航线的机场</div>
            <div class="cs_tagPoint cs_tagWay"><span>&#xe6c5;</span>推荐选点范围</div>
            <div class="cs_tagPoint cs_tagWay"><span>&#xe6c5;</span>未开通航线的机场</div>
        </div>
        <div id='cs_tagItem2'>
            <div class="cs_tagPoint1 cs_tagWay"><span>&#xe6c5;</span>关联机场</div>
            <div class="cs_tagPoint1 cs_tagWay"><span>&#xe6c5;</span>机场</div>
            <div class="cs_tagPoint1 cs_tagWay"><span>&#xe6c5;</span>导航点</div>
            <div class="cs_tagPoint1 cs_tagWay"><span>&#xe6cd;</span>航路</div>
        </div>
    </div>
    <div id='calculate-back'>&#xe68a;<span>退出航路测算</span></div>
    <div id="way_calculate"></div>
    <div class="pla-data" id="pla-data">
        <div class="material-fact-cityMaterial">
            <div class="material-fact-cityTime"></div>
            <p class="material-fact-cityName"><span id="cityName"></span>机场</p>
        </div>
        <div class="material-fact-cityBody">
        </div>
        <div class="material-fact-install">
            <div class="material-fact-installBox">
                <div class="indicators">
                    <div class="indicators-title">
                        <span>首页指标事件维度选择：</span>
                    </div>
                    <div>
                        <label><input name="Fruit" type="radio" value="" id="yesterday"/>最近1天</label>
                        <label><input name="Fruit" type="radio" value="" id="sevenDay"/>最近7天</label>
                        <label><input name="Fruit" type="radio" value=""  id="thirtyDay"/>最近30天</label>
                    </div>
                </div>
                <div class="indicators">
                    <div class="indicators-title">
                        <span>航班统计范围：</span>
                    </div>
                    <div>
                        <label><input name="range" type="radio" value="" id="allFlights"/>所有航班</label>
                        <label><input name="range" type="radio" value="" id="focusFlights"/>关注航班</label>
                    </div>
                </div>
                <div class="indicators">
                    <div class="indicators-title">
                        <span>数据统计范围：</span>
                    </div>
                    <div>
                        <label><input type="checkbox" value="" id="station"/>包含过站</label>
                        <label><input type="checkbox" value="" id="jiltFly"/>包含甩飞</label>
                    </div>
                </div>
                <div class="indicators">
                    <div class="indicators-title">
                        <span class="indicators-tip">关注指标（不超过4个）：</span>
                    </div>
                    <div class="indicators-byd">
                        <label><input class="jruit" name="Jruit" type="checkbox" value=""  id="guestAmount"/><span>客量</span></label>
                        <label><input class="jruit" name="Jruit" type="checkbox" value="" id="shift"/><span>班次</span></label>
                        <label><input class="jruit" name="Jruit" type="checkbox" value="" id="income"/><span>客票收入</span></label>
                        <label><input class="jruit" name="Jruit" type="checkbox" value="" id="passengerVolume"/><span>均班客量</span></label>
                        <label><input class="jruit" name="Jruit" type="checkbox" value="" id="classIncome"/><span>均班收入</span></label>
                        <label><input class="jruit" name="Jruit" type="checkbox" value="" id="loadFactors"/><span>综合客座率</span></label>
                        <label><input class="jruit" name="Jruit" type="checkbox" value="" id="airportPunctuality"/><span>机场准点率</span></label>
                    </div>
                </div>
                <div class="indicators-bth">
                    <div class="indicators-bth-t">确定</div>
                    <div class="indicators-bth-f">取消</div>
                </div>
            </div>
            <div class="material-fact-installS">&#xe67f;</div>
            <div class="material-fact-installF">&#xe67e;</div>
        </div>
    </div>
    <div class="pla-tog">
        <div>展开信息</div>
        <div>&#xe610;</div>
    </div>
   	<div id="air"></div>
    <div class="pla-supernatant" >
        <div class="pla-supernatant-cont">
            <iframe id="pages" name="new_frame"></iframe>
        </div>
        <span class="pla-supernatant-del">&#xe84b;</span>
    </div>
    <div class="pla-promptBox">
		<div class="pla-prompTip"></div>
		<div class="pla-prompRight">
			<div class="pla-prompRight-box"></div>
			<div class="pla-prompRight-sj">&#xe663;</div>
		</div>
 	</div>
 	<div id="change-container">
 	<!-- 增加详情页面 2017-3-13 -->
 	
 	<div class="their-own" id="their-ownA" > 		
		<div class="TM-loading" id="portPanelLoading" style="display:none;"></div>  
        <div class="their-own-title">
            <h2 class="their-own-airName"></h2>
            <div class="their-own-title-code">
                <div>ICAO代码:<span class="their-own-icao" id="their-icao"></span></div>
                <div>IATA代码:<span class="their-own-iata"></span></div>
            </div>
            <!--   切换按钮     -->
            <div class="air-change-btn">
        	    <span class="change-turnIcon"></span>
        	    <span class="change-turnIcon1"></span>
        	</div>
        </div>
        <div class="their-own-body">
            <div class="their-own-body-flight-level">
                <div>
                    <p>飞行区等级</p>
                    <div class="their-own-flyLevel"></div>
                </div>
                <div>
                    <p>跑道数量（条）</p>
                    <div class="their-own-trackN"></div>
                </div>
            </div>
            <div class="their-own-body-flight-level">
                <div id="their-own-body-flying" title="点击后可绘制该机场在飞国内航线">
                    <p>国内航线</p>
                    <div><div class="their-own-zLine"></div><span class = 'yearr1'></span></div>
                </div>
                <div>
                    <p>国际航线</p>
                    <div><div class="their-own-nLine"></div><span class = 'yearr2'></span></div>
                </div>
            </div>
            <div class="their-own-body-flight-level">
                <div style="width:100%;">
                    <p>机场标高（米）</p>
                    <div class="their-own-airLevel"></div>
                </div>
            </div>
            <div class="their-own-body-peoples">
                <p>旅客吞吐量(人)</p>
                <div><div class="their-own-peos"></div><span class = 'yearr2'></span></div>
            </div>
        </div>
    </div>
    <!-- 背部 -->
    <div class="their-own" id="their-ownB"> 
 	    <div class="their-own-title">
            <h2 class="their-own-airName"></h2>
            <div class="their-own-title-code">
                <div>ICAO代码:<span class="their-own-icao"></span></div>
                <div>IATA代码:<span class="their-own-iata"></span></div>
            </div>
            <div class="air-change-btn">
        	    <span class="change-turnIcon"></span>
        	    <span class="change-turnIcon1"></span>
        	</div>
        </div>
        <div>
       		<div class="their-own-body">
	            <div class="their-txt-area" >
	            	<p id="their-txt-pull">
	            	</p>
            	</div>
	        </div>
        </div>
    </div>
 	</div>
    </div>
 	
    <div class="pla-btn" id="pla-btn-qhs">
        <div class="pla-btn-switch" tag="line">&#xe668;</div>
        <%--<div class="pla-btn-switch_line" tag="line">&#xe800;</div>--%>
        <div class="pla-btn-flight">&#xe66c;</div>
        <div class="pla-btn-trend">&#xe669;</div>
        <div class="pla-btn-national">&#xe68a;</div>
        <div class="pla-btn-simulate">&#xe6b0;</div>
        <p class="pla-btn-prompt">切换到机场视图</p>
    </div>
    
    <div id="airFlightt" style="display:none; position:absolute;right:50px;bottom: 134px;">
    	<ul style="">
    		<li class="air_flight" style=" display: flex;">
    			<div style="color: white;padding: 0px 10px;display: flex;justify-content: center;align-items: center;font-size: 12px;">
    				起始地:
    			</div>
    			<div>
    				<input id='startcity' class="air_flight_input" style="border: none; background-color: white; padding: 0; font-size: 12px;border-radius: 4px;">
    			</div>
    		</li>
    		<li class="air_flight" style=" display: flex;">
    			<div  style="color: white;padding: 0px 10px;display: flex;justify-content: center;align-items: center;font-size: 12px;">
    				经停地:
    			</div>
    			<div>
    				<input id='pascity1' class="air_flight_input" style="border: none; background-color: white; padding: 0; font-size: 12px;border-radius: 4px;">
    			</div>
    		</li>
    		<li class="air_flight" style=" display: flex;">
    			<div style="color: white;padding: 0px 10px;display: flex;justify-content: center;align-items: center;font-size: 12px;">
    				经停地:
    			</div>
    			<div>
    				<input id='pascity2' class="air_flight_input" style="border: none; background-color: white; padding: 0; font-size: 12px;border-radius: 4px;">
    			</div>
    		</li>
    		<li class="air_flight" style=" display: flex;">
    			<div style="color: white;padding: 0px 10px;display: flex;justify-content: center;align-items: center;font-size: 12px;">
    				到达地:
    			</div>
    			<div>
    				<input id='endcity' class="air_flight_input" style="border: none; background-color: white; padding: 0; font-size: 12px;border-radius: 4px;">
    			</div>
    		</li>
    	</ul>
    	<div style='display:flex;flex-direction: row; justify-content:center'>
    		<div style="width: 60px;height: 26px;" id="airsubmit" class='personal-information-determine information-qe nice-btn-lBlue'>确定</div>
    	 	<div style="width: 60px;height: 26px;" id = "quexiao" class='personal-information-cancel information-of cancel-btn-ordinary'>取消</div> 
    	</div>
    </div>
    <div id='line-kg'>
    	<ul class='line-icon'>
    		<li><img alt="" src="./images/platform/61.png">在飞航线</li>
    		<li><img alt="" src="./images/platform/60.png">自定义航线<span id="_lisj" style="visibility:hidden;">自定义航线</span></li>
    		<li><div id='turnLine' class='iskg iskg0'><span class='turn-off'>&#xe695;</span></div>历史航线</li>
    	</ul>
    </div>
    <div id='line-kg2'>
    </div>
    <div class="pla-ranking"></div>
    <div class="simulateRoutes"></div>
</div>

<!-- 用户反馈 -->
	<div id="response-parent">
	    <div class="response-container">
	    	<span id="Rsp-closer">&#xe84b;</span>
	        <div class="response-side">
	            <ul class="response-navi">
	                <li id="R-naviNew" class="response-checked">问题反馈</li>
	                <li id="R-naviOld">历史反馈</li>
	            </ul>
	        </div>
	        <!-- 填写新反馈 -->
	        <div class="response-main" id="R-newRsp">	        
	            <div class="form-container">
	            	<div id="just-contain">
	                <h2>反馈及意见 <span class="QuiredIcon">*</span></h2>
	                <form id="response-form" name="response-form" enctype="multipart/form-data">
	                	<input style="display:none">
	                    <div class="response-formArea">
	                        <textarea name="userText" id="response-text" maxlength="300" required="required" placeholder="请描述您遇到的问题或反馈意见。若功能异常，上传页面截图会更快解决！"></textarea>
	                        <p class="counterP textDescript"><span id="wordnum">0</span>/300字 &nbsp;</p>
	                        <div class="imgAndTel">   
	                            <div class="imgArea">
	                                <h2>添加图片<span class="textDescript">（提供问题截图）</span></h2>
	                                <ul class="diyImgFile">
	                                    <li><span>+</span><input type="file" name="files" class="img-input"  accept="image/gif, image/jpeg, image/x-png"></li>
	                                    <li><span>+</span><input type="file" name="files" class="img-input"  accept="image/gif, image/jpeg, image/x-png"></li>
	                                    <li><span>+</span><input type="file" name="files" class="img-input"  accept="image/gif, image/jpeg, image/x-png"></li>
	                                    <li><span>+</span><input type="file" name="files" class="img-input"  accept="image/gif, image/jpeg, image/x-png"></li>
	                                    <li><span>+</span><input type="file" name="files" class="img-input"  accept="image/gif, image/jpeg, image/x-png"></li>
	                                </ul>
	                            </div>
	                            <div class="telArea">
	                                <h2>联系电话&nbsp;<span class="QuiredIcon">*</span><span class="textDescript">（请填写手机号，便于我们与您联系）</span></h2>
	                                <input class="telNumInput" id="userTelNum" name="phone" type="text" required="required" maxlength="16" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9]/g,'')">
	                            </div>                         
	                        </div>
	                        <div class="buttonArea">
	                            <button type="button" class="btn-blue" id="rp-submit" >提交</button>
	                            <button type="button" class="btn-gray" id="rp-cancel" >取消</button>
	                        </div>
	                    </div>
	                </form>
	                </div>
	            </div>
	        </div>
	        
	        <!-- 历史反馈 -->
	        <div class="response-main" id="R-oldRsp">
	        	<div class="form-container">
	        		<h2>我的历史反馈 </h2>
	        		<table id="rp-table">
	        			<thead>
	        				<tr>
	        					<th>提交时间</th>
	        					<th>内容</th>
	        					<th>当前状态</th>
	        				</tr>
	        			</thead>
	        			<tbody id="rp-tableBody">
	        			</tbody>
	        		</table>
        		</div>
	        </div>
	        
	    </div>
    </div>
<div class="SD-hd-null">
	<p>暂无数据！<p>
</div>
<div class="TM-loading" id="TM-loading"></div>
<div id="cs_calculate" class="cs_calculate"></div>
<div id="tipShow"></div>
<div id="csTip"></div>
<div id="allTip"></div>
<div class="homeWarning" id="homeWarning">
    <p>输入错误，请检查</p>
</div>
<div class="full-layer">
</div>
<div id="hlcs-wrap" style="position:fixed;top:0;right:0;bottom:0;left:0;background-color:#fff;z-index:999;"></div>
<script type="text/javascript">
	menus2.forEach(function(val){
		if(val.type==0){
			$('#_permissions_'+val.id).css({'display':'none'});
		}
	});
</script>
<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/echarts.js"></script>
<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/PointChange.js"></script>
<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/platform-map.js"></script>
<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/eCenter.js"></script>
<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/world.js"></script>
<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/EBmapExtend.js"></script>
<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/platform.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=TDmhTStuIIhX3LsAf3bNZV60SZoloqdC"></script>
<script type="text/javascript" src="/echarts/build/dist3/bmap.js"></script>
<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/jquery.mousewheel.js"></script>
<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/homepage/setting.js"></script>
<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/yj-scroll-bar.js"></script>
<script src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/./../../vue/vue_two/dist/index.build.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/AdminLTE/myWeather.js"></script>
<script src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/air-change.js"></script>
<script src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/coalesce.js"></script>
<script src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/drawSimulateAirRoute.js"></script>
<script src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/bootstrap-typeahead.js"></script>
<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/hDate.js"></script>
<script>
    var opensk=function(){
        $("#pages",parent.document.body).attr("src","/processTask");
        $(".pla-supernatant").css({"display":"block","background-color":"rgba(0,0,0,0.6)"});
        $(".pla-promptBox").css({"display":"none"});
        $(".pla-supernatant-cont").css({"display":"block"});
        $(".pla-supernatant-cont").animate({"opacity":"1","top":"5%"},500);
        $(".their-own").css({"display":"none"});
    }
</script>
</body>


</html>