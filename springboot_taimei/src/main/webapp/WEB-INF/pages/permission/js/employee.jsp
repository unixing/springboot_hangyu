<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
function add(){
	if($("#ausrNm").val()==''){
		alert('请输入账户名称');
		return;
	}
	if($("#ausrPwd").val()==''){
		alert('请输入账户密码');
		return;
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/employee_add',
		data:$('#addform').serialize(),
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
	if($("#uusrNm").val()==''){
		alert('请输入账户名称');
		return;
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/employee_update',
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
			url:'${pageContext.request.contextPath}/employee_delete',
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
function search(){
	$.ajax({
		url:'${pageContext.request.contextPath}/employee_search',
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
			    		        	 field:'departmentId',
			    		        	 visible:false,
			    		         },{
			    		        	 title:'选择',
			    		        	 width:'3%',
			    		        	 checkbox:true,
			    		        	 align:'center'
			    		         },{
			    		        	 field:'usrNm',
			    		        	 title:'用户名',
			    		        	 width:'3%',
			    		        	 align:'center'
			    		         },{
			    		        	 field:'usrSts',
			    		        	 title:'账号状态',
			    		        	 width:'3%',
			    		        	 align:'center',
			    		        	 formatter:function(value,row,index){
			    		        		 if(1==value){
			    		        			 return '正常';
			    		        		 }
			    		        	 }
			    		         },{
			    		        	 field:'department',
			    		        	 title:'部门名称',
			    		        	 width:'3%',
			    		        	 align:'center',
			    		        	 formatter:function(value,row,index){
			    		        		 if(value!=null)
			    		        			 return value.dptNm;
			    		        	 }
			    		         },{
			    		        	 field:'department',
			    		        	 title:'公司名称',
			    		        	 width:'3%',
			    		        	 align:'center',
			    		        	 formatter:function(value,row,index){
			    		        		 if(value!=null)
			    		        			 return value.company.cpyNm;
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
	$.ajax({
		url:'${pageContext.request.contextPath}/department_list',
		data:{
			companyId:currentCompanyId
		},
		type:'post',
		success:function(data){
			var operation = '';
			$("#datalist").empty();
			operation += '<form id="addform">';
			operation += '<div class="addorupdate" style="border: 1px solid #dedede;">';
			operation += '<div class="title" style="background: #f2f0f1; width:100%;height:30px"><span style="line-height: 30px; font-weight: bold;color:#999798;">账户信息添加</span><span onclick="closePage(\'datalist\',\'search\');" style="font-weight:bold;float:right;line-height:30px;color:#999798;cursor:pointer;margin-right:5px;">关闭</span></div>';
			operation += '<div class="content"><ul>';
			operation += '<li><span>账户名：</span><input id="ausrNm" name="usrNm"/></li>';
			operation += '<li><span>密码:</span><input type="password" id="ausrPwd" name="usrPwd"/></li>';
			operation += '<li><span>部门:</span><select name="departmentId">';
			if(data!=null&&data.list!=null&&data.list.length>0){
				var list = data.list;
				for(var i=0;i<list.length;i++){
					operation += '<option value="'+list[i].id+'">'+list[i].dptNm+'</option>';
				}
			}
			operation += '</select></li></ul><ul>';
			operation += '<li style="width:100%; text-align: center;"><input class="btn btn-success" type="button" value="添加" onclick="add()"></li>';
			operation += '</li></ul></div></div></form>';
			$("#datalist").append(operation);
		}
	});
}
function updateshow(){
	$.ajax({
		url:'${pageContext.request.contextPath}/department_list',
		data:[],
		type:'post',
		success:function(data){
			var row = $("#datalist").bootstrapTable('getSelections');
			if(row.length==1){
				var operation = '';
				$("#datalist").empty();
				operation += '<form id="updateform">';
				operation += '<div class="addorupdate" style="border: 1px solid #dedede;">';
				operation += '<div class="title" style="background: #f2f0f1; width:100%;height:30px"><span style="line-height: 30px; font-weight: bold;color:#999798;">账户信息修改</span><span onclick="closePage(\'datalist\',\'search\');" style="font-weight:bold;float:right;line-height:30px;color:#999798;cursor:pointer;margin-right:5px;">关闭</span></div>';
				operation += '<div class="content"><ul>';
				operation += '<li><span>账户名：</span><input type="hidden" name="id" value="'+row[0].id+'"/><input id="uusrNm" name="usrNm" value="'+row[0].usrNm+'"/></li>';
				operation += '<li><span>部门:</span><select name="departmentId">';
				if(data!=null&&data.list!=null&&data.list.length>0){
					var list = data.list;
					for(var i=0;i<list.length;i++){
						if(list[i].id==row[0].departmentId){
							operation += '<option selected="selected" value="'+list[i].id+'">'+list[i].dptNm+'</option>';
						}else{
							operation += '<option value="'+list[i].id+'">'+list[i].dptNm+'</option>';
						}
					}
				}
				operation += '</select></li></ul><ul>';
				operation += '<li style="width:100%; text-align: center;"><input class="btn btn-success" type="button" value="修改" onclick="update()"></li>';
				operation += '</li></ul></div></div></form>';
				$("#datalist").append(operation);
			}else{
				alert('请选择一条数据');
			}
		}
	});
}
function routeAssignment(){
	var row = $("#datalist").bootstrapTable('getSelections');
	if(row.length==1){
		$.ajax({
			url:'${pageContext.request.contextPath}/flightinfo_routeAssignment',
			data:{
				employeeId:row[0].id
			},
			type:'post',
			success:function(data){
				if(data.opResult == '0'){
					showorhide(1);
					$(".show_content").empty();
					var operation = '<div class="show_title"><em class="hide_btn" onclick="showorhide(0)">关闭</em><b>航线航班列表</b></div>';
					operation += '<div id="routeList"><input type="hidden" id="employeeId" value="'+row[0].id+'"/>';
					if(data.list!=null&&data.list.length>0){
						var list = data.list;
						operation += '<ol style="margin-left:50px;width:693px;">';
						for(var i=0;i<list.length;i++){
							if(list[i].pas_stp!=null&&''!=list[i].pas_stp){
								if(list[i].flts!=null&&list[i].flts.length>0){
									var flts = list[i].flts;
									if(list[i].type==1){
										operation += '<li><input style="width:13px;" name="route" checked="checked" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].dpt_AirPt_Cd+'-'+list[i].pas_stp+'-'+list[i].arrv_Airpt_Cd+'</span>';
									}else{
										operation += '<li><input style="width:13px;" name="route" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].dpt_AirPt_Cd+'-'+list[i].pas_stp+'-'+list[i].arrv_Airpt_Cd+'</span>';
									}
									operation += '<ol>';
									for(var j = 0;j<flts.length;j++){
										if(flts[j].type==1){
											operation += '<li><input style="width:13px;" name="fltnbr" checked="checked" type="checkbox" value="'+list[i].id+':'+flts[j].fltNbr+'" /><span>'+flts[j].fltNbr+'</span></li>';
										}else{
											operation += '<li><input style="width:13px;" name="fltnbr" type="checkbox" value="'+list[i].id+':'+flts[j].fltNbr+'" /><span>'+flts[j].fltNbr+'</span></li>';
										}
									}
									operation += '</ol>';
									operation += '</li>';
								}else{
									if(list[i].type==1){
										operation += '<li><input style="width:13px;" name="route" checked="checked" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].dpt_AirPt_Cd+'-'+list[i].pas_stp+'-'+list[i].arrv_Airpt_Cd+'</span></li>';
									}else{
										operation += '<li><input style="width:13px;" name="route" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].dpt_AirPt_Cd+'-'+list[i].pas_stp+'-'+list[i].arrv_Airpt_Cd+'</span></li>';
									}
								}
							}else{
								if(list[i].flts!=null&&list[i].flts.length>0){
									var flts = list[i].flts;
									if(list[i].type==1){
										operation += '<li><input style="width:13px;" name="route" checked="checked" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].dpt_AirPt_Cd+'-'+list[i].arrv_Airpt_Cd+'</span>';
									}else{
										operation += '<li><input style="width:13px;" name="route" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].dpt_AirPt_Cd+'-'+list[i].arrv_Airpt_Cd+'</span>';
									}
									operation += '<ol>';
									for(var j = 0;j<flts.length;j++){
										if(flts[j].type==1){
											operation += '<li><input style="width:13px;" name="fltnbr" checked="checked" type="checkbox" value="'+list[i].id+':'+flts[j].fltNbr+'" /><span>'+flts[j].fltNbr+'</span></li>';
										}else{
											operation += '<li><input style="width:13px;" name="fltnbr" type="checkbox" value="'+list[i].id+':'+flts[j].fltNbr+'" /><span>'+flts[j].fltNbr+'</span></li>';
										}
									}
									operation += '</ol>';
								}else{
									if(list[i].type==1){
										operation += '<li><input style="width:13px;" name="route" checked="checked" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].dpt_AirPt_Cd+'-'+list[i].arrv_Airpt_Cd+'</span></li>';
									}else{
										operation += '<li><input style="width:13px;" name="route" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].dpt_AirPt_Cd+'-'+list[i].arrv_Airpt_Cd+'</span></li>';
									}
								}
							}
						}
						operation += '</ol>';
						operation += '<ol style="width:693px;margin-top:20px;"><li style="text-align: center;"><input class="btn btn-success" type="button" value="保存" onclick="saveRouteAssignment()"></li></ol></div>';
					}else{
						operation += '<ol style="width:693px;margin-top:20px;"><li>没有可以显示的航线</li></ol></div>';
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
function saveRouteAssignment(){
	var fltNbr = '';
	var obj= document.getElementById("routeList");
	var checkboxs = obj.getElementsByTagName("input");
	if(checkboxs.length>0){
		for(var i=0;i<checkboxs.length;i++){
			if(checkboxs[i].type=="checkbox"&&checkboxs[i].checked&&checkboxs[i].name=="fltnbr"){
				fltNbr += checkboxs[i].value+",";
			}
		}
	}
	/* if(fltNbr==''){
		alert("请选择一个菜单");
		return;
	} */
	var employeeId = $('#employeeId').val();
	$.ajax({
		url:'${pageContext.request.contextPath}/flightinfo_saveRouteAssignment',
		data:{
			employeeId:employeeId,
			fltNbr:fltNbr
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