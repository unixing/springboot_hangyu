<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
var currentCompanyId = $(window.parent.document.body).find("#myCompanyList").val();
var currentCompanyName = $(window.parent.document.body).find("#myCompanyList").find("option:selected").text();
jQuery(function($){  
    // 备份jquery的ajax方法    
    var _ajax=$.ajax;  
    // 重写ajax方法，先判断登录在执行success函数   
    $.ajax=function(opt){  
        var _success = opt && opt.success || function(a, b){};
        var _opt = $.extend(opt, {  
            success:function(data, textStatus){  
                // 如果后台将请求重定向到了登录页，则data里面存放的就是登录页的源码，这里需要找到data是登录页的证据(标记)  
                if(data.indexOf('back') != -1) {  
                    window.location.href= Globals.ctx + "/login.jsp";  
                    return;  
                }  
                _success(data, textStatus);    
            }    
        });  
        _ajax(_opt);  
    };  
}); 
function getEmployeeList(managerId){
	var employeeList = "<option value=''>请选择</option>";
	$.ajax({
		url:'${pageContext.request.contextPath}/employee_search',
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
			$("#employeeList").append(employeeList);
		}
	});
}

function getDataSource(){
	var companyId = $('#companyList').val();
	if(companyId!="0"){
		$.ajax({
			url:'${pageContext.request.contextPath}/datasupplier_datasource',
			data:{
				companyId:companyId
			},
			type:'post',
			success:function(data){
				var option = '';
				if(data!=null&&data.list!=null&&data.list.length>0){
					var list = data.list;
					for(var i=0;i<list.length;i++){
						if(i<list.length-1){
							option += list[i]+',';
						}else{
							option += list[i];
						}
					}
					
				}
				$('#datSrcWay').val(option);
			}
		});
		$.ajax({
			url:'${pageContext.request.contextPath}/company_load',
			data:{
				id:companyId
			},
			type:'post',
			success:function(data){
				var option  = '';
				if(data!=null&&data.company!=null){
					var obj = data.company;
					option += obj.cpyPho;
				}
				$('#datSrePhe').val(option);
			}
			
		});
	}
}

function getDepartmentList(departmentId){
	var departmentList = "<option value=''>请选择</option>";
	$.ajax({
		url:'${pageContext.request.contextPath}/department_list',
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
			$("#departmentList").append(departmentList);
		}
	});
}

function getCompanyList(company){
	var companyList = "<option value =''>请选择</option>";
	$.ajax({
		url:'${pageContext.request.contextPath}/company_selectList',
		data:[],
		type:'post',
		success:function(data){
			if(data!=null && data.opResult=='0'){
				if(data.list!=null&&data.list.length>0){
					var list = data.list;
					for(var i=0;i<list.length;i++){
						if(typeof(company)!="undefined"&&list[i].id==company){
							companyList += '<option selected="selected" value="'+list[i].id+'">'+list[i].cpyNm+'</option>';
						}else{
							companyList += '<option value="'+list[i].id+'">'+list[i].cpyNm+'</option>';
						}
					}
				}
			}else{
				alert('操作异常');
			}
			$("#companyList").append(companyList);
		}
	});
}
function showorhide(type){
	if(type==1){
		$(".mask").show();
		$(".warp").show();
	}else{
		$(".mask").hide();
		$(".warp").hide();
	}
}
</script>