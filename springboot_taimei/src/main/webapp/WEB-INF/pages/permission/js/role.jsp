<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
function add(){
	if($("#asn").val()==''){
		alert('请输入角色简称');
		return;
	}
	if($("#aname").val()==''){
		alert('请输入角色名称');
		return;
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/role_add',
		data:searchData($('#addform').serialize()),
		type:'post',
		success:function(data){
			if(data!=null){
				alert(data.message);
				if(data.opResult=='0'){
					search();
				}
			}else{
				alert('后台操作异常');
			}
		}
	});
}
function update(){
	if($("#usn").val()==''){
		alert('请输入角色简称');
		return;
	}
	if($("#uname").val()==''){
		alert('请输入角色名称');
		return;
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/role_update',
		data:$('#updateform').serialize(),
		type:'post',
		success:function(data){
			if(data!=null){
				alert(data.message);
				if(data.opResult=='0'){
					search();
				}
			}else{
				alert('后台操作异常');
			}
		}
	});
}
function del(){
	var row = $("#datalist").bootstrapTable('getSelections');
	if(row.length==1){
		/* var ids = '';
		for(var i=0;i<row.length;i++){
			if(i==0){
				ids +=row[i].id;
			}else{
				ids +=','+row[i].id
			}
		} */
		$.ajax({
			url:'${pageContext.request.contextPath}/role_delete',
			data:{
				id:row[0].id
			},
			type:'post',
			success:function(data){
				if(data!=null){
					alert(data.message);
					if(data.opResult=='0'){
						search();
					}
				}else{
					alert('后台操作异常');
				}
			}
		});
	}else{
		alert('请选择一条数据');
	}
}
/* function searchData(data){
	return data += "&companyId="+$("#myCompanyList").val();
}; */
function search(){
	$.ajax({
		url:'${pageContext.request.contextPath}/role_search',
		data:searchData($('#systemLog-searchForm').serialize()),
		type:'post',
		success:function(data){
			if(data.opResult=="0"){
				if(data!=null){
					var list = data.list;
					$("#datalist").bootstrapTable("destroy").bootstrapTable({
						data:list,
			    		sidePagination:'server',
			    		pageNumber:1,                       
			            pageSize: 10,                       
			            pageList: [10, 25, 50, 100],
			    		columns:[
			    		         {
			    		        	 field:'id',
			    		        	 visible:false,
			    		         },{
			    		        	 title:'选择',
			    		        	 width:'3%',
			    		        	 checkbox:true,
			    		        	 align:'center'
			    		         },{
			    		        	 field:'sn',
			    		        	 title:'简称',
			    		        	 width:'3%',
			    		        	 align:'center'
			    		         },{
			    		        	 field:'name',
			    		        	 title:'名称',
			    		        	 width:'3%',
			    		        	 align:'center'
			    		         },{
			    		        	 field:'company',
			    		        	 title:'所属公司',
			    		        	 width:'3%',
			    		        	 align:'center',
			    		        	 formatter:function(value,row,index){
			    		        		 if(value != null)
			    		        			 return value.cpyNm;
			    		        	 }
			    		         },{
			    		        	 field:'creator',
			    		        	 title:'创建者',
			    		        	 width:'3%',
			    		        	 align:'center',
			    		        	 formatter:function(value,row,index){
			    		        		 if(value!=null)
			    		        			 return value.usrNm;
			    		        	 }
			    		         }
			    		         
	   		         	]
					});
				}else{
					alert('后台操作异常');
				}
			}else{
				alert(data.message);
			}
		}
	});
}
function addshow(){
	var operation = '';
	$("#datalist").empty();
	operation += '<form id="addform">';
	operation += '<div class="addorupdate" style="border: 1px solid #dedede;">';
	operation += '<div class="title" style="background: #f2f0f1; width:100%;height:30px"><span style="line-height: 30px; font-weight: bold;color:#999798;">角色信息添加</span><span onclick="closePage(\'datalist\',\'search\');" style="font-weight:bold;float:right;line-height:30px;color:#999798;cursor:pointer;margin-right:5px;">关闭</span></div>';
	operation += '<div class="content"><ul>';
	operation += '<li><span>角色简称：</span><input id="asn" name="sn"/></li>';
	operation += '<li><span>角色名称:</span><input id="aname" name="name"/></li>';
	operation += '</ul><ul>';
	operation += '<li style="width:100%; text-align: center;"><input class="btn btn-success" type="button" value="添加" onclick="add()"></li>';
	operation += '</li></ul></div></div></form>';
	$("#datalist").append(operation);
}
function updateshow(){
	var row = $("#datalist").bootstrapTable('getSelections');
	if(row.length==1){
		var operation = '';
		$("#datalist").empty();
		operation += '<form id="updateform">';
		operation += '<div class="addorupdate" style="border: 1px solid #dedede;">';
		operation += '<div class="title" style="background: #f2f0f1; width:100%;height:30px"><span style="line-height: 30px; font-weight: bold;color:#999798;">角色信息修改</span><span onclick="closePage(\'datalist\',\'search\');" style="font-weight:bold;float:right;line-height:30px;color:#999798;cursor:pointer;margin-right:5px;">关闭</span></div>';
		operation += '<div class="content"><ul>';
		operation += '<li><span>角色简称：</span><input type="hidden" name="id" value="'+row[0].id+'"/><input id="usn" name="sn" value="'+row[0].sn+'"/></li>';
		operation += '<li><span>角色名称:</span><input id="uname" name="name" value="'+row[0].name+'"/></li>';
		operation += '</ul><ul>';
		operation += '<li style="width:100%; text-align: center;"><input class="btn btn-success" type="button" value="修改" onclick="update()"></li>';
		operation += '</li></ul></div></div></form>';
		$("#datalist").append(operation);
	}else{
		alert('请选择一条数据');
	}
}
function setPermission(){
	var row = $("#datalist").bootstrapTable('getSelections');
	if(row.length==1){
		var companyId = $(window.parent.document.body).find("#myCompanyList").val();
		$.ajax({
			url:'${pageContext.request.contextPath}/role_showPermission',
			data:{
				companyId:companyId,
				roleId:row[0].id
			},
			type:'post',
			success:function(data){
				if(data.opResult == '0'){
					showorhide(1);
					$(".show_content").empty();
					var operation = '<div class="show_title"><em class="hide_btn" onclick="showorhide(0)">关闭</em><b>菜单资源列表</b></div>';
					operation += '<div id="menuList"><input type="hidden" id="roleId" value="'+row[0].id+'"/>';
					if(data.menuList!=null&&data.menuList.length>0){
						var list = data.menuList;
						for(var i=0;i<list.length;i++){
							operation += '<table style="margin-left:100px;width:693px;"><tr>';
							if(list[i].type==1){
								if(list[i].chiledren!=null&&list[i].chiledren.length>0){
									var chiledren = list[i].chiledren;
									operation += '<td><input style="width:13px;" name="menu" checked="checked" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].name+'</span>';
									for(var j = 0;j<chiledren.length;j++){
										operation += '<table style="margin-left:30px;"><tr>';
										if(chiledren[j].type==1){
											operation += '<td><input style="width:13px;" name="menu" checked="checked" type="checkbox" value="'+chiledren[j].id+'" /><span>'+chiledren[j].name+'</span>';
											var resources = chiledren[j].resources;
											if(resources!=null&&resources.length>0){
												operation += '<table style="margin-left:60px;"><tr>';
												for(var k=0;k<resources.length;k++){
													if(k%6==0&&k!=0){
														if(resources[k].type==1){
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}else{
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}else{
														if(resources[k].type==1){
															operation += '<td><input style="width:13px;" name="resource" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}else{
															operation += '<td><input style="width:13px;" name="resource" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}
												}
												operation += '</tr></table>';
											}
										}else{
											operation += '<td><input style="width:13px;" name="menu" type="checkbox" value="'+chiledren[j].id+'" /><span>'+chiledren[j].name+'</span>';
											var resources = chiledren[j].resources;
											if(resources!=null&&resources.length>0){
												operation += '<table style="margin-left:60px;"><tr>';
												for(var k=0;k<resources.length;k++){
													if(k%6==0&&k!=0){
														if(resources[k].type==1){
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span><td>';
														}else{
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}else{
														if(resources[k].type==1){
															operation += '<td><input style="width:13px;" name="resource" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span><td>';
														}else{
															operation += '<td><input style="width:13px;" name="resource" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}
												}
												operation += '</tr></table>';
											}
										}
										operation += '</td></tr></table>';
									}
									operation += '</td>';
								}else{
									operation += '<td><input style="width:13px;" name="menu" checked="checked" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].name+'</span></td>';
								}
							}else{
								if(list[i].chiledren!=null&&list[i].chiledren.length>0){
									var chiledren = list[i].chiledren;
									operation += '<td><input style="width:13px;" name="menu" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].name+'</span>';
									for(var j = 0;j<chiledren.length;j++){
										operation += '<table style="margin-left:30px;"><tr>';
										if(chiledren[j].type==1){
											operation += '<td><input style="width:13px;" name="menu" checked="checked" type="checkbox" value="'+chiledren[j].id+'" /><span>'+chiledren[j].name+'</span>';
											var resources = chiledren[j].resources;
											if(resources!=null&&resources.length>0){
												operation += '<table style="margin-left:60px;"><tr>';
												for(var k=0;k<resources.length;k++){
													if(k%6==0&&k!=0){
														if(resources[k].type==1){
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}else{
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}else{
														if(resources[k].type==1){
															operation += '<td><input style="width:13px;" name="resource" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}else{
															operation += '<td><input style="width:13px;" name="resource" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}
												}
												operation += '</tr></table>';
											}
										}else{
											operation += '<td><input style="width:13px;" name="menu" type="checkbox" value="'+chiledren[j].id+'" /><span>'+chiledren[j].name+'</span>';
											var resources = chiledren[j].resources;
											if(resources!=null&&resources.length>0){
												operation += '<table style="margin-left:60px;"><tr>';
												for(var k=0;k<resources.length;k++){
													if(k%6==0&&k!=0){
														if(resources[k].type==1){
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}else{
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}else{
														if(resources[k].type==1){
															operation += '<td><input style="width:13px;" name="resource" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}else{
															operation += '<td><input style="width:13px;" name="resource" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}
												}
												operation += '</tr></table>';
											}
										}
										operation += '</td></tr></table>';
									}
								}else{
									operation += '<td><input style="width:13px;" name="menu" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].name+'</span></td>';
								}
							}
							operation += '</tr></table>';
						}
						operation += '<table style="width:693px;"><tr text-algin="center"><td style="text-align: center;"><input class="btn btn-success" type="button" value="保存" onclick="savePermission()"></td></tr></table></div>';
					}else{
						operation += '<table style="width:693px;"><tr text-algin="center"><td>没有可以显示的菜单</td></tr></table></div>';
					}
					$(".show_content").append(operation);
				}else{
					alert(data.message);
				}
			}
		});
	}else{
		alert('请选择一条数据');
	}
}

function savePermission(){
	var flag=false;
	var menuIds = '';
	var resourceIds = '';
	var obj= document.getElementById("menuList");
	var checkboxs = obj.getElementsByTagName("input");
	if(checkboxs.length>0){
		for(var i=0;i<checkboxs.length;i++){
			if(checkboxs[i].type=="checkbox"&&checkboxs[i].checked){
				if(checkboxs[i].name=="menu"){
					if(!flag){
						flag=true;
					}
					menuIds += checkboxs[i].value+',';
				}else if(checkboxs[i].name=="resource"){
					resourceIds += checkboxs[i].value+',';
				}
			}
		}
	}
	if(!flag){
		alert("请选择一个菜单");
		return;
	}
	var roleId = $('#roleId').val();
	$.ajax({
		url:'${pageContext.request.contextPath}/role_savePermission',
		data:{
			menuIds:menuIds,
			resourceIds:resourceIds,
			roleId:roleId
		},
		type:'post',
		success:function(data){
			if(data!=null){
				if(data.opResult=="0"){
					showorhide(0);
					search();
				}else{
					alert(data.message);
				}
			}else{
				alert("操作异常");
			}
		}
	});
}
</script>