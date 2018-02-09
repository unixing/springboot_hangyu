<%@page import="org.ldd.ssm.crm.utils.UserContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/wbox/wbox.js"></script>
<script type="text/javascript">
		function save(){
			showMask();
			var dta_Sce =$("#dta_Sce").val();
			$.get("${pageContext.request.contextPath}/externalSave?dta_Sce="+dta_Sce,function(data){
				if(data!=null){
					$("#cover").hide();
					$('#example2').bootstrapTable('destroy').bootstrapTable({
    	        	    pagination: true,                   //是否显示分页（*）
    	                sortable: true,                     //是否启用排序
    	                sortOrder: "asc",                   //排序方式
    	                title:'数据库已经存在当前航班数据',
    	                sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
    	                search: true,
    	                pageNumber: 1,                       //初始化加载第一页，默认第一页
    	                uniqueId: "flt_Nbr", 
    	                pageSize: 10,
    	                pageList: [10, 25, 50, 100,"ALL"],
    	                data:data
    	        	    
    	        	});
					$("#textData").text("以下为数据库已经存在当前航班数据");
			 	}
				
			});
		};
		function cancel(){
			$.get("${pageContext.request.contextPath}/externalCancel",function(data){
				$('#example2').bootstrapTable('destroy');
				alert("成功取消");
			});
		};
	</script>
<script type="text/javascript">
	function showMask(){
		$('body').css("overflow","hidden")
		$("#cover").show();
		};
	function checkData(){
		var data = new FormData();
		var cars = $("#cars").val();	
		var outAndOnAir = $("#outAndOnAir").val();	
		data.append('file', $("#file")[0].files[0]);
		showMask();
		$.ajax({
			data : data,
			url : "${pageContext.request.contextPath}/externalDataInsert?cars="+cars,
			type : 'post',
			contentType : false,
			processData : false,
			success : function(data) {
				alert(data.message);
				if(data.set.length>0){
					var add = $("#externalData_add");
					add.empty();
					for(var i in data.set){
						add.append("<p id='pid"+i+"'>航段:<input type='text' value="+data.set[i]+" id='line"+i+"' size='8'>城市中文名<input name='voyageName"+i+"' id='voyageName"+i+"' size='8'>航距:<input type='text' name='sailingDistance"+i+"' id='sailingDistance"+i+"' size='8'>YB运价:<input type='text' name='yBFare"+i+"' id='yBFare"+i+"' size='8'><input type='button' class='btn btn-success' value='保存' onclick='saveYbFare("+i+");'><br></p>");
					}
					$("#externalData_edit").modal("toggle");
					$("#cover").hide();
				}else if(data.success){
					$("#cover").hide();
					$.ajax({
		                type: "post",
		                url:"${pageContext.request.contextPath}/externalData",
		                dataType: "json",
		                success: function (Json) {
							$("#textData").text("验证完毕,以下为航班号异常可人为修正,修正后数据将重新保存,异常数据将被删除, 不被存储:");
		                	if(Json!=null&&Json.length>0){
			                	$('#example2').bootstrapTable('destroy').bootstrapTable({
			    	        	    data:Json,
			    	        	    pagination: true,                   //是否显示分页（*）
			    	                sortable: true,                     //是否启用排序
			    	                sortOrder: "asc",                   //排序方式
			    	                sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
			    	                search: true,
			    	                pageNumber: 1,                       //初始化加载第一页，默认第一页
			    	                uniqueId: "flt_Nbr", 
			    	                pageSize: 10,
			    	                pageList: [10, 25, 50, 100,"ALL"], 
			    	        	    onEditableSave: function (field, row, oldValue, $el) {
			    	                    $.ajax({
			    	                        type: "post",
			    	                        url: "${pageContext.request.contextPath}/addCheckData",
			    	                        data: row,
			    	                        success: function (data, status) {
			    	                           
			    	                        },
			    	                        error: function () {
			    	                        },
			    	                        complete: function () {
	
			    	                        }
	
			    	                    });
			    	                }
			    	        	    
			    	        	});
		                	}
		                }
		            });
				var file = $("#file") ;
				file.after(file.clone().val(""));      
				file.remove(); 
				}
			},
			error : function() {
				
			}
		});
	};
	function saveYbFare(data){
		var line = $("#line"+data);
		var yBFare =$("#yBFare"+data);
		var voyageName=$("#voyageName"+data);
		var sailingDistance=$("#sailingDistance"+data)
		if(line!=""&&yBFare!=""&&voyageName!=""&&sailingDistance!=""){
			var pid = $("#pid"+data);
			var Ybdata = {};
			Ybdata.voyageCode = line.val();
			Ybdata.yBFare = yBFare.val();
			Ybdata.voyageName = voyageName.val();
			Ybdata.sailingDistance = sailingDistance.val();
			$.ajax({
			       type:'get',
			       url:'${pageContext.request.contextPath}/externalYBsave',//请求数据的地址	
			       data:Ybdata,
			       success:function(data){
			    	   if(data.success){
						   pid.empty();
			    	   }
			       }
			   });
		}else{
			alert("请完整填写信息");
		}
	}
	var obj=document.getElementById('cars');
	$.ajax({
	       type:'get',
	       url:'${pageContext.request.contextPath}/upFile_company_list',//请求数据的地址	
	       success:function(data){
// 	           for(var index in data){
	    		obj.options.add(new Option(data[0])); //这个兼容IE与firefox  
// 	           }
	       }
	   });
	</script>