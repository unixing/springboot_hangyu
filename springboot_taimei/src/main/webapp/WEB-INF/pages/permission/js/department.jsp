<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">

function add(){
	if($("#asn").val()==''){
		alert('请输入部门简称');
		return;
	}
	if($("#aname").val()==''){
		alert('请输入部门名称');
		return;
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/department_add',
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
		alert('请输入部门简称');
		return;
	}
	if($("#uname").val()==''){
		alert('请输入部门名称');
		return;
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/department_update',
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

function getCurrentCompany(){
	var currentCompany = '<input id="companyId" value=""/>';
	$.ajax({
		url:'${pageContext.request.contextPath}/company_load',
		data:{},
		type:'post',
		success:function(data){
			if(data!=null &&data.obj!=null){
				currentCompany = '<input name="companyId.id" value="'+obj.id+'" type="hidden"/><input value="'+obj.cpyNm+'"/>';
			}
			return currentCompany;
		}
	});
}

/* function getEmployeeLsit(managerId){
	var employeeList = "<option value='0'>请选择</option>";
	$.ajax({
		url:'${pageContext.request.contextPath}/employee/getList',
		data:{
			companyId:currentCompanyId,
		},
		type:'post',
		success:function(data){
			if(data!=null&&data.list!=null&&data.list.length>0){
				var list = data.list;
				for(var i=0;i<list.length;i++){
					if(typeof(managerId) != "undefined"&&list[i].id==managerId)
						employeeList += '<option selected="selected" value="'+list[i].id+'">'+list[i].usrNm+'</option>';
					else
						employeeList += '<option value="'+list[i].id+'">'+list[i].usrNm+'</option>';
				}
			}
			return employeeList;
		}
	});
}

function getDepartmentLsit(departmentId){
	var departmentList = "<option value='0'>请选择</option>";
	$.ajax({
		url:'${pageContext.request.contextPath}/department/list',
		data:{
			companyId:currentCompanyId,
		},
		type:'post',
		success:function(data){
			if(data!=null&&data.list!=null&&data.list.length>0){
				var list = data.list;
				for(var i=0;i<list.length;i++){
					if(typeof(departmentId)!="undefined"&&list[i].id==departmentId)
						departmentList += '<option selected="selected" value="'+list[i].id+'">'+list[i].dptNm+'</option>';
					else
						departmentList += '<option value="'+list[i].id+'">'+list[i].dptNm+'</option>';	
				}
			}
			return departmentList;
		}
	});
} */

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
			url:'${pageContext.request.contextPath}/department_delete',
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
		url:'${pageContext.request.contextPath}/department_list',
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
		    		        	 field:'dptNm',
		    		        	 title:'部门名称',
		    		        	 width:'3%',
		    		        	 align:'center'
		    		         },{
		    		        	 field:'parent',
		    		        	 title:'上级部门',
		    		        	 width:'3%',
		    		        	 align:'center',
		    		        	 formatter:function(value,row,index){
		    		        		 if(value!=null){
		    		        			 return value.dptNm;
		    		        		 }
		    		        	 }
		    		         },{
		    		        	 field:'manager',
		    		        	 title:'部门管理者',
		    		        	 width:'3%',
		    		        	 align:'center',
		    		        	 formatter:function(value,row,index){
		    		        		 if(value!=null)
		    		        			 return value.usrNm;
		    		        	 }
		    		         },{
		    		        	 field:'company',
		    		        	 title:'所属公司',
		    		        	 width:'3%',
		    		        	 align:'center',
		    		        	 formatter:function(value,row,index){
		    		        		 if(value!=null)
		    		        			 return value.cpyNm;
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
	operation += '<div class="title" style="background: #f2f0f1; width:100%;height:30px"><span style="line-height: 30px; font-weight: bold;color:#999798;">部门信息添加</span><span onclick="closePage(\'datalist\',\'search\');" style="font-weight:bold;float:right;line-height:30px;color:#999798;cursor:pointer;margin-right:5px;">关闭</span></div>';
	operation += '<div class="content"><ul>';
	operation += '<li><span>部门名称：</span><input id="adptNm" name="dptNm"/></li>';
	operation += '<li><span>上级部门:</span><select id="departmentList" name="parentId">'+getDepartmentList()+'</select></li>';
	operation += '<li><span>部门管理者:</span><select id="employeeList" name="managerId">'+getEmployeeList()+'</select></li>';
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
		operation += '<div class="title" style="background: #f2f0f1; width:100%;height:30px"><span style="line-height: 30px; font-weight: bold;color:#999798;">部门信息修改</span><span onclick="closePage(\'datalist\',\'search\');" style="font-weight:bold;float:right;line-height:30px;color:#999798;cursor:pointer;margin-right:5px;">关闭</span></div>';
		operation += '<div class="content"><ul>';
		operation += '<li><span>部门名称：</span><input type="hidden" name="id" value="'+row[0].id+'"/><input id="udptNm" name="dptNm" value="'+row[0].dptNm+'"/></li>';
		operation += '<li><span>上级部门:</span><select id="departmentList" name="parentId">'+getDepartmentList(row[0].parentId==null?0:row[0].parentId)+'</select></li>';
		operation += '<li><span>部门管理:</span><select id="employeeList" name="managerId">'+getEmployeeList(row[0].managerId==null?0:row[0].managerId)+'</select></li>';
		operation += '</ul><ul>';
		operation += '<li style="width:100%; text-align: center;"><input class="btn btn-success" type="button" value="修改" onclick="update()"></li>';
		operation += '</li></ul></div></div></form>';
		$("#datalist").append(operation);
	}else{
		alert('请选择一条数据');
	}
}
</script>