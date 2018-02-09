<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
$(function(){
	$('');
});

function add(){
	if($("#acpyNm").val()==''){
		alert('请输入公司名称');
		return;
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/company_add',
		data:$('#addform').serialize(),
		type:'post',
		success:function(data){
			if(data!=null){
				alert(data.message);
				if(data.opResult=='0'){
					getCompanyList();
					search();
				}
			}else{
				alert('后台操作异常');
			}
		}
	});
}
function update(){
	if($("#ucpyNm").val()==''){
		alert('请输入公司名称');
		return;
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/company_update',
		data:$('#updateform').serialize(),
		type:'post',
		success:function(data){
			if(data!=null){
				alert(data.message);
				if(data.opResult=='0'){
					getCompanyList();
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
		var currentCompanyId = $(window.parent.document.body).find("#myCompanyList").val();
		if(currentCompanyId==row[0].id){
			alert('不能删除当前公司');
			return;
		}
		$.ajax({
			url:'${pageContext.request.contextPath}/company_delete',
			data:{
				id:row[0].id
			},
			type:'post',
			success:function(data){
				if(data!=null){
					alert(data.message);
					if(data.opResult=='0'){
						getCompanyList();
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
		url:'${pageContext.request.contextPath}/company_list',
		data:$('#systemLog-searchForm').serialize(),
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
			    		        	 field:'cpyNm',
			    		        	 title:'公司名称',
			    		        	 width:'3%',
			    		        	 align:'center'
			    		         },{
			    		        	 field:'cpyAds',
			    		        	 title:'公司地址',
			    		        	 width:'3%',
			    		        	 align:'center'
			    		         },{
			    		        	 field:'cpyPho',
			    		        	 title:'公司电话',
			    		        	 width:'3%',
			    		        	 align:'center'
			    		         },{
			    		        	 field:'cpyEml',
			    		        	 title:'公司邮箱',
			    		        	 width:'3%',
			    		        	 align:'center'
			    		         },{
			    		        	 field:'cpyIno',
			    		        	 title:'公司简介',
			    		        	 width:'3%',
			    		        	 align:'center'
			    		         },{
			    		        	 field:'cpyItia',
			    		        	 title:'公司三字码',
			    		        	 width:'3%',
			    		        	 align:'center'
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
	operation += '<div class="content"><ol class="parentCls">';
	operation += '<li><span>公司名称:</span><input id="acpyNm" name="cpyNm"/></li>';
	operation += '<li><span>公司地址:</span><input id="acpyAds" name="cpyAds"/></li>';
	operation += '<li><span>公司电话:</span><input id="acpyPho" name="cpyPho"/></li>';
	operation += '<li><span>公司邮箱:</span><input id="acpyEml" name="cpyEml"/></li>';
	operation += '<li><span>公司简介:</span><input id="acpyIno" name="cpyIno"/></li>';
	operation += '</ol><div class="clear"></div><ol><li><span>公司三字码:</span><input id="acpyItia" name="cpyItia"/></li>';
	operation += '</ol><ol>';
	operation += '<li style="width:100%; text-align: center;"><input class="btn btn-success" type="button" value="添加" onclick="add()"></li>';
	operation += '</ol></div></div></form>';
	$("#datalist").append(operation);
	var cityPicker = new HzwCityPicker({
		data: data,
		target: 'acpyAds',
		valType: 'k-v',
		hideCityInput: {
		},
		callback: function(){
		}
	});
	cityPicker.init();
}
function updateshow(){
	var row = $("#datalist").bootstrapTable('getSelections');
	if(row.length==1){
		var operation = '';
		$("#datalist").empty();
		operation += '<form id="updateform">';
		operation += '<div class="addorupdate" style="border: 1px solid #dedede;">';
		operation += '<div class="title" style="background: #f2f0f1; width:100%;height:30px"><span style="line-height: 30px; font-weight: bold;color:#999798;">公司信息修改</span><span onclick="closePage(\'datalist\',\'search\');" style="font-weight:bold;float:right;line-height:30px;color:#999798;cursor:pointer;margin-right:5px;">关闭</span></div>';
		operation += '<div class="content"><ol class="parentCls">';
		operation += '<li><span>公司名称：</span><input type="hidden" name="id" value="'+row[0].id+'"/><input id="ucpyNm" name="cpyNm" value="'+row[0].cpyNm+'"/></li>';
		operation += '<li><span>公司地址:</span><input id="ucpyAds" name="cpyAds" value="'+row[0].cpyAds+'"/></li>';
		operation += '<li><span>公司电话:</span><input id="ucpyPho" name="cpyPho" value="'+row[0].cpyPho+'"/></li>';
		operation += '<li><span>公司邮箱:</span><input id="ucpyEml" name="cpyEml" value="'+row[0].cpyEml+'"/></li>';
		operation += '<li><span>公司简介:</span><input id="ucpyIno" name="cpyIno" value="'+row[0].cpyIno+'"/></li>';
		operation += '</ol><div class="clear"></div><ol><li><span>公司三字码:</span><input id="ucpyItia" name="cpyItia" value="'+row[0].cpyItia+'"/></li>';
		operation += '</ol><ol>';
		operation += '<li style="width:100%; text-align: center;"><input class="btn btn-success" type="button" value="修改" onclick="update()"></li>';
		operation += '</ol></div></div></form>';
		$("#datalist").append(operation);
		var cityPicker1 = new HzwCityPicker({
			data: data,
			target: 'ucpyAds',
			valType: 'k-v',
			hideCityInput: {
			},
			callback: function(){
			}
		});
		cityPicker1.init();
	}else{
		alert('请选择一条数据');
	}
}
</script>