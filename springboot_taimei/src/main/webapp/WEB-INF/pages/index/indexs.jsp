<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>太美航空</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<%@include file="/WEB-INF/pages/common/css.jsp"%>
<%-- <%@include file="/WEB-INF/pages/common/common.jsp"%> --%>
<%@include file="/WEB-INF/pages/index/js/index.jsp"%>
</head>
<script type="text/javascript">
$(document).ready(function(){
	loadmenu();
	$('.sidebar-menu a').on('click',function(){
		if($(this).siblings('ul').css('display')=='none'){
			$(this).parent('li').siblings('li').removeClass('active');
			$(this).parent('li').addClass('active');
			$(this).children('i.fa.pull-right').removeClass('fa-angle-left');
			$(this).children('i.fa.pull-right').addClass('fa-angle-down');
			$(this).siblings('ul').slideDown(100).children('li');
			if($(this).parents('li').siblings('li').children('ul').css('display')=='block'){
				$(this).parents('li').siblings('li').children('ul').parent('li').children('a').removeClass('active');
				$(this).parents('li').siblings('li').children('ul').slideUp(100);
			}
		}else{
			//控制自身变成+号
			$(this).removeClass('active');
			//控制自身菜单下子菜单隐藏
			$(this).siblings('ul').slideUp(100);
			//控制自身子菜单变成+号
			$(this).siblings('ul').children('li').children('ul').parent('li').children('a').addClass('active');
			//控制自身菜单下子菜单隐藏
			$(this).siblings('ul').children('li').children('ul').slideUp(100);
			$(this).children('i.fa.pull-right').removeClass('fa-angle-down');
			$(this).children('i.fa.pull-right').addClass('fa-angle-left');
			//控制同级菜单只保持一个是展开的（-号显示）
			$(this).siblings('ul').children('li').children('a').removeClass('active');
		}
	});
});
</script>
<body class="skin-blue">
	<header class="header">
		<a href="index.html" class="logo"> <img src="/img/logo.png">
		</a>
		<nav class="navbar navbar-static-top" role="navigation">
			<a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas"
				role="button"> <span class="sr-only">Toggle navigation</span> <span
				class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
			</a> <select id="cc1" name="dta_Sce" class="btn btn-primary btn-lg"
				style="appearance: none; -moz-appearance: none; -webkit-appearance: none; padding: 10px 5px;">
				<option>数据源</option>
			</select> <select id="myCompanyList" class="btn btn-primary btn-lg"
				style="appearance: none; -moz-appearance: none; -webkit-appearance: none; padding: 10px 5px;"></select>
			<div class="navbar-right">
				<a href="#"> <img src="/img/yonghu.png" align="left">
				</a> <a href="#"> <img src="/img/shezhi.png" align="left">
				</a> <a href="#" id="time"
					style="color: white; font-size: 12px; line-height: 50px;"></a>
			</div>
		</nav>
	</header>
	<div class="wrapper row-offcanvas row-offcanvas-left">
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="left-side sidebar-offcanvas">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- /.search form -->
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu">
					<li class="treeview"><a href="#"> <i class="fa fa-laptop"></i>
							<span>权限控制</span> <i class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu">
							<li><a href="/datasupplier/show" target="rightName"><i
									class="fa fa-angle-double-right"></i> 角色关系管理 </a></li>
							<li><a href="/company/show" target="rightName"><i
									class="fa fa-angle-double-right"></i> 公司管理 </a></li>
							<li><a href="/department/show" target="rightName"><i
									class="fa fa-angle-double-right"></i> 部门管理 </a></li>
							<li><a href="/employee/show" target="rightName"><i
									class="fa fa-angle-double-right"></i> 成员管理 </a></li>
							<li><a href="/role/show" target="rightName"><i
									class="fa fa-angle-double-right"></i> 角色管理 </a></li>
						</ul></li>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>
		<aside class="right-side">
			<section class="content">
				<iframe name="rightName" src="/homePage" frameborder="0"
					scrolling="no" id="test" width="100%" onload="this.height=100">
				</iframe>
			</section>
		</aside>
	</div>
	<script type="text/javascript">
change(); 
getCompanyList();
function change()  
{  
        var today;  
        today = new Date(); 
        var now = "";
        if(today.getHours() < 10)  
        {  
                now = now +"0";  
        }  
        now = now + today.getHours()+":";  
          
        if(today.getMinutes() < 10)  
        {  
                now = now +"0";  
        }  
        now = now + today.getMinutes();  
        document.getElementById("time").innerHTML ="时间:"+now;  
        setTimeout("change();", 1000);  
}

function getCompanyList(){
	$('#myCompanyList').empty();
	var option = '<option value="0">公司列表</option>';
	$.ajax({
		url:'${pageContext.request.contextPath}/company/getList',
		data:[],
		type:'post',
		success:function(data){
			if(data.opResult=="0"){
				if(data!=null&&data.list!=null&&data.list.length>0){
					var list=data.list;
					option = '';//重置下拉列表
					for(var i=0;i<list.length;i++){
						option += '<option value="'+list[i].id+'">'+list[i].cpyNm+'</option>';
					}
				}
				$('#myCompanyList').append(option);
			}else{
				alert(data.message);
			}
		}
	});
}

function changeCompany(){
	var companyId = $('#myCompanyList').val();
	$.ajax({
		url:'${pageContext.request.contextPath}/menu/getmenulist',
		data:{
			companyId:companyId
		},
		type:'post',
		success:function(data){
			
		}
	});
}
</script>
	<script type="text/javascript">
function reinitIframe(){
	var iframe = document.getElementById("test");
	try{
	var bHeight = iframe.contentWindow.document.body.scrollHeight;
	var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
	var height = Math.max(bHeight, dHeight);
	iframe.height = height;
	}catch (ex){}
}
window.setInterval("reinitIframe()", 200);
var obj=document.getElementById('cc1');
$.ajax({
       type:'post',
       url:'${pageContext.request.contextPath}/DOW_company',//请求数据的地址	
       success:function(data){
           for(var index in data){
    		obj.options.add(new Option(data[index].dta_Sce)); //这个兼容IE与firefox  
           }
       }
   });
</script>
</body>
</html>