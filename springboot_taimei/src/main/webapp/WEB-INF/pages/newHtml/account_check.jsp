<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String flagpage = request.getParameter("flagpage");
 %>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>销售数据</title>
<link rel="stylesheet" type="text/css" media="all"
    href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/bootstrap.css">
<link rel="stylesheet" type="text/css"
    href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/dateKJ.css" />
<link rel="stylesheet" type="text/css" media="all"
    href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/line_historical/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css"
    href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/lin-historical.css" />
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/line_historical/sy.css"
    rel="stylesheet/less">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/coalesce.css" rel="stylesheet">
<link href="http://cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet">
<link href="/err/ed.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/TMcommon.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/account-check/account-check.css" />
</head>
<script type="text/javascript">
var flagpage = <%=flagpage%>;
var calls = function(){
    return ;
}
</script>
<body>
    <div class="lin-historical">
        <div class="sr-box-head">
            <div class="sr-box-head-classify">
                <ul>
             	    <li class='tltle-sel' title='销售报表' id="_permissions_13"><a class='page-fun-change' href='/salesReport'>&#xe628;</a></li>
                    <li class='tltle-sel' title='航线历史收益统计' id="_permissions_1"><a class='page-fun-change' href='/airline'>&#xe629;</a></li>
                    <li class='tltle-sel' title='销售动态' id="_permissions_5"><a class='page-fun-change' href='/buyTicketReport'>&#xe624;</a></li>
                    <li class='tltle-sel tltle-selI' id="_permissions_8">&#xe688;<span>销售数据</span></li>
                    <li class='tltle-sel' title='客票监控' id="_permissions_5"><a class='page-fun-change' href='/ticketMonitor'>&#xe6a9;</a></li>
                </ul>
            </div>
            <div class="sr-box-head-inquire" id='sr-box-head-inquire'>
                
                <div class="_set-flight">
                    <div class="_set-name" id='flt_nbr_Count'>航班号</div>
                    <div class="_set-list-set"><input id="SD-head-inquire" type="text" placeholder="如MU1234" maxlength="8"></div>
                </div>
                <div class="_set-time">
                    <div class="_set-name">起止时间</div>
                    <div class="_set-name-b">
                        <input type="text" readonly="readonly"
                            id='reservation' placeholder="起止日期">
                    </div>
                </div>
                <div class="_set-place-duplexing">
                    <div class="_set-name">航线</div>
                    <div class="SD-set-list-title"></div>
                    <div class="SD-airportNam">
                    	<ul>
                    	</ul>
                    </div>
                </div>

                <div class="_set-query">查询</div>
            </div>
        </div>  
        
        <!-- 左侧导航栏目 -->
        <div class="lin-historical-body-navigation">
        </div>
        <div id="scroll-bar" style="top:0 px;"></div>
        
        
        
        
        <!-- 数据核对面板 -->
        <div class="check-container" id="check-main">
        	<div class="check-panel">
        		
        		<!-- 上传中界面 -->
        		<div id="check-uping">
	        		<div class="check-textarea">
	        			<p class="check-tips">
	        				<span class="icon">&#xe64a;</span>
	        				此功能便于航司核对数据与系统中数据是否一致，通过航班号、日期、航段和票号来匹配票面价格是否正确。<br>
	        				为了更好的进行数据对比，请下载excel模板： <a href="/SalesData/excelExample" title="点击下载">票面数据模板.xls</a>
	        			</p>
	        		</div>
	        		<div class="check-iconarea">
	        			<form id="check-upload-form" method="post" name="fileinfo" enctype="multipart/form-data">
		        			<div class="check-icon">
		        				<input type="file" name="myfile" id="check-upload-icon" class="check-upbutton" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" required="required">
		        				<p class="circle-p">
			        				<img style="display:inline-block;" src="${pageContext.request.contextPath}/images/platform/salesData/upload-big.png">
			        				<span>选择要上传的文件<br>仅支持Excel </span>
		        				</p>
		        			</div>		        			
		        			<div class="check-buttonArea">
		        				<button class="docancel" type="button">取消</button>
		        			</div>
	        			</form>
		        	<p class="normalTip">请上传查询条件范围内的数据。</p>
	        		</div>
        		</div>
        		
        		<!-- 上传后界面 -->
                <div class="check-uped">
                	<div class="check-uped-main">
	                    <p class="P-gray">已选择文件（暂仅支持单一文件上传）：</p>
	                    <div class="filelist">
	                        <div class="filelist-p" title="点击可重新选择文件"> </div>                    	
	                        <span class="icon check-close" id="check-panel-clearX" title="删除已选文件">&#xe84b;</span>
	                    <p class="uperror-msg">仅支持Excel文件</p>
	                    </div>	
	                    <div class="check-buttonArea">
		                    <button id="doup" class="doupbtn" type="button">上传</button>
		                    <button class="docancel" type="button">取消</button>
	                    </div>
                    </div>
	                <!-- 加载  -->
	                <div class="check-loading">   
	                	<div class="check-loading-main">        
		                	<img src="${pageContext.request.contextPath}/img/lon-loading.gif"><br>
		                	<p>文件上传中...</p>
	                	</div>     
	                </div>
                </div>
			        <!-- 上传完成数据查看页面 -->
			    <div class="check-complate" id="check-compare"> 
			        <p class="price-error-info">包含联程，数据可能有误差</p>
			        <div class="check-complate-main">
			            <div class="command">
			                <a id="check-file-put" href="#" title="重新上传文件"><span class="borderLink link-upload">重新上传对比</span></a>
			                <a id="check-file-out" href="javascript:void(0)" title="将对比结果导出到本地"><span class="borderLink link-export">导出对比结果</span></a>
			            </div>
			            <div class="tableArea" id="tableContainer">
			                <div class="tableCompare-left tableCompare">
			                    <div class="tableName">上传数据</div>
			                    <table class="compare-Table" id="table-user">
			                    	<thead>
			                    		<tr>
                                            <td>航班日期</td>
                                            <td>航班号</td>
                                            <td>航段</td>
                                            <td>票号</td>
                                            <td>票款</td>
			                    		</tr>
			                    	</thead>
			                    	<tbody id="table-uesr-body">
			                    	</tbody>
			                    </table>
			                </div>
			                <div class="tableCompare-right tableCompare">
			                    <div class="tableName">系统数据</div>    
			                    <table class="compare-Table" id="table-system">
			                    	<thead>
			                    		<tr>
                                            <td>航班日期</td>
                                            <td>航班号</td>
                                            <td>航段</td>
                                            <td>票号</td>
                                            <td>票款</td>
			                    		</tr>
			                    	</thead>
			                    	<tbody id="table-system-body">
			                    	</tbody>
			                    </table>                
			                </div>
			            </div>
		                <p id="check-complate-info" class="check-complate-info">
		                	上传错误： <span>请检查文件格式、是否满足当前页面查询条件后，重新上传对比。</span><br>
		                	
		                </p>
		                <p>&nbsp;</p>
			        </div>
			    </div>
        	</div>
        	
        </div>
        
        
        
        <!-- 主区域 -->
    <div class="SD-src-main">     

    <div id="dataLoading">    
		<div class="TM-loading" id="TM-sloading"></div>  
<%--    		<div class="loadblur"></div> 	
   		<img src="${pageContext.request.contextPath}/img/lon-loading.gif">
   		<p>数据加载中...</p> --%>
    </div>
	<div id="SD-cover"></div>
	<div class="lin-historical-body-box">
		<div class="lin-historical-body-bar"></div>
	</div>
    <a name="backtop"></a>
        <!-- 数据显示区域 -->
        <div class="SD-view">
            <div class="SD-view-header">
                <ul class="SD-ul-title" id="SD-head-title">
                    <li>
                        时间<br>
                        <span class="sumDetail">---</span>
                    </li>
                    <li>
                        航班号<br>
                        <span class="sumDetail">---</span>
                    </li>
                    <li>
                        平均折扣（%）<br>
                        <span class="sumDetail">---</span>
                    </li>
                    <li>
                        客量（人）<br>
                        <span class="sumDetail">---</span>
                    </li>
                    <li>
                        票面和（元）<br>
                        <span class="sumDetail">---</span>
                    </li>
                    <a class="SD-outer" title="导出所有航段数据">
                    	导出详情
                    	<div class="SD-print-link">
                    	</div>
                    </a>
                    <a class="SD-check" title="数据核对">
                    	上传对比
                    	<div class="SD-print-link">
                    	</div>
                    </a>
                </ul>
            </div>
            <div class="SD-view-table">
                <!-- 航段选择器 -->
                <div class="SD-view-t-label" id="SD-table-head">
                    <ul>
                    </ul>
                </div>              
                <!-- 表格区域 -->
                <div class="SD-view-body">
                    <ul class="SD-ul-title" id="SD-min-list">
                        <li>
                            平均折扣(%)<br>
                            <span>---</span>
                        </li>
                        <li>
                            票面和（元）<br>
                            <span>---</span>
                        </li>
                        <li id="personNum">
                            客量（人）<br>
                            <span>---</span>
                        </li>
                        <li>
                            平均票价（元）<br>
                            <span>---</span>
                        </li>
                        
                    </ul>

                    <div class="SD-table-sum">

                        <table class="sum-table" id="SD-sum-tableA">
                            <tr>                                
                                <th>舱位</th>
                            </tr>
                            <tr>                                
                                <th>人数</th>
                            </tr>
                            <tr>                                
                                <th>收入</th>
                            </tr>
                        </table>

                        <table class="sum-table" id="SD-sum-tableB">
                            <tr>                                
                                <th>舱位</th>
                            </tr>
                            <tr>                                
                                <th>人数</th>
                            </tr>
                            <tr>                                
                                <th>收入</th>
                            </tr>
                        </table>
                    </div>
                    <div class="SD-table-detail">
                        <!-- 详情表表头 -->
                        <div class="detail-table-head" id="detail-table-head">
                            <table class="sum-table">
                                <tr>                                
                                    <td>承运人</td>
                                    <td><span id="date_sort">航班日期    ↓</span></td>
                                    <td>航班号</td>
                                    <td>航段</td>
                                    <td>公司</td>
                                    <td>票号</td>
                                    <td>旅客类型</td>
                                    <td><span id="cw_sort">舱位    ↑↓</span></td>
                                    <td><span id="price_sort">票款    ↑↓</span></td>
                                </tr>
                            </table>
                        </div>
                        <!-- 详情表表体 -->
                        <table class="sum-table" id="SD-detail-table">
                        </table>
                        <!-- 表格底部导航 -->
                        <div class="table-nav">
                            <!-- 目录 -->
                            <p class="currpage-info">
                                当前显示第 <span class="nav-startTr"></span> - <span class="nav-endTr"></span> 条数据，总共 <span class="nav-totalTr"></span> 条数据
                            </p>
                            <!-- 分页 -->
                              <div class="table-nav-menu">                                
                                <div id="nav-long">
                                <span></span>
                                </div>
                            </div>
                        </div>
                        <!-- 回到顶部 -->
                        <div class="back-to-top" title="回到顶部"></div>
                    </div>
                </div>
                </div>
            </div>
        </div>
        <!-- 无数据时 -->        
		<div class="err">
			<div id='err-one'><img src="/err/errNodata.png" ></div>
        	<div id='err-two'><img src="/err/err2.png"></div>
		</div>
		<div class="SD-hd-null">
			<p>暂无该航段数据！<p>
		</div>
    </div>
    <script
        src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery1.8.3.js"></script>
    <script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/role.js"></script>    
    <script type="text/javascript"
        src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/coalesce.js"
        type="text/javascript"></script>
    <script type="text/javascript"
        src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/bootstrap.min.js"></script>
     <script type="text/javascript"
        src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/moment.js"></script>
     <script type="text/javascript"
        src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/line_historical/daterangepicker.js"></script>
    <script type="text/javascript"
        src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/line_historical/less.min.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/account-check/laypage.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/account-check/account-check.js"></script>
    <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/account-check/account-upCheck.js"></script>
</body>
</html>