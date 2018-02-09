<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
function add(){
	if($("#companyList").val()=='0'){
		alert('请选择公司');
		return;
	}
	if($("#employeeList").val()=='0'){
		alert('请选择成员');
		return;
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/datasupplier_add',
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
	if($("#companyList").val()=='0'){
		alert('请选择公司');
		return;
	}
	if($("#employeeList").val()=='0'){
		alert('请选择成员');
		return;
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/datasupplier_update',
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
			url:'${pageContext.request.contextPath}/datasupplier_delete',
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
		url:'${pageContext.request.contextPath}/datasupplier_list',
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
			    		        	 field:'company',
			    		        	 title:'数据公司名称',
			    		        	 width:'3%',
			    		        	 align:'center',
			    		        	 formatter:function(value,row,index){
			    		        		 if(value!=null)
			    		        			 return value.cpyNm;
			    		        	 }
			    		         },{
			    		        	 field:'datSreWay',
			    		        	 title:'数据渠道',
			    		        	 width:'3%',
			    		        	 align:'center'
			    		         },{
			    		        	 field:'datSrePhe',
			    		        	 title:'数据公司电话',
			    		        	 width:'3%',
			    		        	 align:'center'
			    		         },{
			    		        	 field:'employee',
			    		        	 title:'成员姓名',
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
	operation += '<div class="title" style="background: #f2f0f1; width:100%;height:30px"><span style="line-height: 30px; font-weight: bold;color:#999798;">公司信息添加</span><span onclick="closePage(\'datalist\',\'search\');" style="font-weight:bold;float:right;line-height:30px;color:#999798;cursor:pointer;margin-right:5px;">关闭</span></div>';
	operation += '<div class="content"><ul>';
	operation += '<li><span>数据公司名称：</span><select onchange="getDataSource()" id="companyList" name="datSreCpyId"></select></li>';
	operation += '<li><span>数据渠道:<input name="datSreWay" id="datSrcWay"/></span></li>';
	operation += '<li><span>数据公司电话:</span><input id="datSrePhe" name="datSrePhe"/></li>';
	operation += '<li><span>成员姓名:</span><select id="employeeList" name="employeeId"></select></li>';
	operation += '</ul><ul>';
	operation += '<li style="width:100%; text-align: center;"><input class="btn btn-success" type="button" value="添加" onclick="add()"></li>';
	operation += '</li></ul></div></div></form>';
	$("#datalist").append(operation);
	getEmployeeList();
	getCompanyList();
}
function updateshow(){
	var row = $("#datalist").bootstrapTable('getSelections');
	if(row.length==1){
		var operation = '';
		$("#datalist").empty();
		operation += '<form id="updateform">';
		operation += '<div class="addorupdate" style="border: 1px solid #dedede;">';
		operation += '<div class="title" style="background: #f2f0f1; width:100%;height:30px"><span style="line-height: 30px; font-weight: bold;color:#999798;">公司信息修改</span><span onclick="closePage(\'datalist\',\'search\');" style="font-weight:bold;float:right;line-height:30px;color:#999798;cursor:pointer;margin-right:5px;">关闭</span></div>';
		operation += '<div class="content"><ul>';
		operation += '<li><span>数据公司名称：</span><input type="hidden" name="id" value="'+row[0].id+'"/><select onchange="getDataSource()" id="companyList" name="datSreCpyId"></select></li>';
		operation += '<li><span>数据渠道:</span><input id="datSrcWay" name="datSreWay" value="'+row[0].datSreWay+'"/></li>';
		operation += '<li><span>数据公司电话:</span><input id="datSrePhe" name="datSrePhe" value="'+row[0].datSrePhe+'"/></li>';
		operation += '<li><span>成员姓名:</span><select id="employeeList" name="employeeId"></select></li>';
		operation += '</ul><ul>';
		operation += '<li style="width:100%; text-align: center;"><input class="btn btn-success" type="button" value="修改" onclick="update()"></li>';
		operation += '</li></ul></div></div></form>';
		$("#datalist").append(operation);
		getEmployeeList(row[0].employeeId.id);
		getCompanyList(row[0].datSreCpyId.id);
	}else{
		alert('请选择一条数据');
	}
}
function showroles(){
	var row = $("#datalist").bootstrapTable('getSelections');
	if(row.length==1){
		$.ajax({
			url:'${pageContext.request.contextPath}/role_getList',
			data:{
				companyId:row[0].company.id,
				relateId:row[0].id
			},
			type:'post',
			success:function(data){
				if(data.opResult == '0'){
					showorhide(1);
					$(".show_content").empty();
					var operation = '<div class="show_title"><em class="hide_btn" onclick="showorhide(0)">关闭</em><b>角色列表</b></div>';
					operation += '<form id="rolelist"><ul><input type="hidden" value="'+row[0].id+'" id="datasupplierId"/>';
					if(data.list!=null&&data.list.length>0){
						var list = data.list;
						for(var i=0;i<list.length;i++){
							if(i%4==0&&i!=0){
								if(list[i].type==1){
									operation += '</ul><ul><li><input style="width:13px;" type="checkbox" checked="checked" value="'+list[i].id+'"/><span>'+list[i].sn+'</span></li>';
								}else{
									operation += '</ul><ul><li><input style="width:13px;" type="checkbox" value="'+list[i].id+'"/><span>'+list[i].sn+'</span></li>';
								}
							}else{
								if(list[i].type==1){
									operation += '<li><input style="width:13px;" type="checkbox" checked="checked" value="'+list[i].id+'" /><span>'+list[i].sn+'</span></li>';
								}else{
									operation += '<li><input style="width:13px;" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].sn+'</span></li>';
								}
							}
							
						}
						if(data.list%4!=0){
							operation += '</ul><ul>';
						}
						operation += '<li style="width:100%; text-align: center;"><input class="btn btn-success" type="button" value="保存" onclick="assroles()"></li>';
						operation += '</li></ul></div></div></form>';
					}else{
						operation += '<li>没有可以显示的角色</li>';
						operation += '</ul><ul>';
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
function assroles(){
	var flag=false;
	var roleString = '';
	var obj= document.getElementById("show_content");
	var checkboxs = obj.getElementsByTagName("input");
	if(checkboxs.length>0){
		for(var i=0;i<checkboxs.length;i++){
			if(checkboxs[i].type=="checkbox"&&checkboxs[i].checked){
				if(!flag){
					flag=true;
				}
				if(i!=checkboxs.length-1){
					roleString += checkboxs[i].value+',';
				}else{
					roleString += checkboxs[i].value;
				}
			}
		}
	}
	if(!flag){
		alert("请选择一个角色");
	}
	var datasupplierId = $('#datasupplierId').val();
	$.ajax({
		url:'${pageContext.request.contextPath}/datasupplierrole_add',
		data:{
			roleString:roleString,
			datasupplierId:datasupplierId
		},
		type:'post',
		success:function(data){
			if(data!=null){
				if(data.opResult=='0'){
					showorhide(0);
					search();
				}
			}else{
				alert('添加失败');
			}
		}
	});
}
</script>