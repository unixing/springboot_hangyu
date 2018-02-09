<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<script type="text/javascript">
function loadmenu(){
	$.ajax({
		url:'${pageContext.request.contextPath}/menu_list',
		data:{
			companyId:$(window.parent.document.body).find("#myCompanyList").val()
		},
		type:'post',
		async:false,
		success:function(data){
			if(data!=null){
				if(data.opResult=="0"){
					var option = '';
					$('.sidebar-menu').empty();
					if(data.list!=null&&data.list.length>0){
						var list = data.list;
						for(var i=0;i<list.length;i++){
							if(i==0){
								option += '<li class="treeview active"><a href="#"><i class="fa fa-dashboard"></i><span>'+list[i].name+'</span><i class="fa fa-angle-left pull-right"></i></a>';
							}else{
								option += '<li class="treeview"><a href="#"><i class="fa fa-dashboard"></i><span>'+list[i].name+'</span><i class="fa fa-angle-left pull-right"></i></a>';
							}
							option += '<ul class="treeview-menu">';
							if(list[i].chiledren!=null&&list[i].chiledren.length>0){
								var chiledren = list[i].chiledren;
								for(var j=0;j<chiledren.length;j++){
									option += '<li><a href="'+chiledren[j].url+'" target="rightName"><i class="fa fa-angle-double-right" ></i>'+chiledren[j].name+'</a></li>';
								}
							}
							option += '</ul></li>';
						}
					}else{
						option += '<li>没有可以显示的菜单</li>';
					}
					$('.sidebar-menu').append(option);
				}else{
					alert(data.message);
				}
			}else{
				alert('后台异常');
			}
		}
	});
}
function getCurrentPermission(){
	$.ajax({
		url:'${pageContext.request.contextPath}/role_getCurrentPermission',
		data:{
			companyId:$(window.parent.document.body).find("#myCompanyList").val()
		},
		async:false,
		type:'post',
		success:function(data){
			if(data!=null){
				if(data.menuList!=null&&data.menuList.length>0){
					$('.sidebar-menu').empty();
					var list = data.menuList;
					var option = '';
					for(var i=0;i<list.length;i++){
						if(i==0){
							option += '<li class="treeview active"><a href="#"><i class="fa fa-dashboard"></i><span>'+list[i].name+'</span><i class="fa fa-angle-left pull-right"></i></a>';
						}else{
							option += '<li class="treeview"><a href="#"><i class="fa fa-dashboard"></i><span>'+list[i].name+'</span><i class="fa fa-angle-left pull-right"></i></a>';
						}
						option += '<ul class="treeview-menu">';
						if(list[i].chiledren!=null&&list[i].chiledren.length>0){
							var chiledren = list[i].chiledren;
							for(var j=0;j<chiledren.length;j++){
								option += '<li><a href="'+chiledren[j].url+'" target="rightName"><i class="fa fa-angle-double-right" ></i>'+chiledren[j].name+'</a></li>';
							}
						}
						option += '</ul></li>';
					}
					$('.sidebar-menu').append(option);
				}else{
					alert("没有可以显示菜单");
				}
			}else{
				alert('操作异常！');
			}
		}
	});
}
</script>