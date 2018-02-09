<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<script type="text/javascript">
		window.onload=loadList();
		function loadList(){
		    $.ajax({
    	        type:'post',
    	        url:'${pageContext.request.contextPath}/dailyParametersList',
    	        data:null,
    	        success:function(data){
    	        	cretaeTable(data);
    	        }
    	    }); 
		}
		function cretaeTable(datasorce){
		        $('#parametersTable').bootstrapTable({
		            data: datasorce,  
		            method: 'get',                      //请求方式（*）
		            toolbar: '#toolbar',                //工具按钮用哪个容器
		            striped: true,                      //是否显示行间隔色
		            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		            pagination: false, 
		            dataType: "json",				//是否显示分页（*）
		            queryParams: null,					//传递参数（*）
		            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
		            pageNumber:1,                       //初始化加载第一页，默认第一页
		            pageSize: 10,                       //每页的记录行数（*）
		            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
		            showColumns: true,                  //是否显示所有的列
		            clickToSelect: true,                //是否启用点击选中行
		            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
		            columns:[
		                     {
			   		        	 field:'id',
					        	 visible:false,
					         },
		                     {
			   		        	 title:'选择',
					        	 align:'center',
					        	 width:'4%',
					        	 checkbox:true
					         },
		 					{
		 						field: 'start_time',
		 						title: '开始时间',
		 						width: '80',
		 						align: 'center',
		 						valign: 'middle'
		 					},
		 					{
		 						field: 'end_time',
		 						title: '结束时间',
		 						width: '80',
		 						align: 'center',
		 						valign: 'middle'
		 					},
		 					{
		 						field: 'agence_price',
		 						title: '代理费率(%)',
		 						width: '80',
		 						align: 'center',
		 						valign: 'middle'
		 					},
		 					{
		 						field: 'hour_price',
		 						title: '小时成本',
		 						width: '80',
		 						align: 'center',
		 						valign: 'middle'
		 					},
		 					{
		 						field: 'sitee',
		 						title: '座位数',
		 						width: '80',
		 						align: 'center',
		 						valign: 'middle'
		 					}
		 					]
		        });
		}
		function doSaveOrUpdate(){
				 var paramm = $('#dataform').serialize();
				/*paramm.id = $("#id").val();
				paramm.start_time = $("#start_time").val();
				paramm.end_time = $("#end_time").val();
				paramm.agence_price = $("#agence_price").val();
				paramm.hour_price = $("#hour_price").val();
				paramm.fly_site_min = $("#fly_site_min").val();
				paramm.fly_site = $("#fly_site").val();*/
				$.ajax({
	    	        type:'post',
	    	        url:'${pageContext.request.contextPath}/saveOrUpdatedailyParameters',
	    	        data:paramm,
	    	        success:function(data){
	    	        	alert(data.info);
	    	        	$("#parametersTable").bootstrapTable('destroy');
	    	        	$("#updateTable").css('display','none');
	    	        	loadList();
	    	        }
	    	    }); 
		}
		function updateData(){
			var row = $("#parametersTable").bootstrapTable('getSelections');
			if(row.length==0||row.length>1){
				alert("请选择1条记录");
			}else{
				$("#updateTable").css('display','block');
				$("#id").val(row[0].id);
				$("#start_time").val(row[0].start_time);
				$("#end_time").val(row[0].end_time);
				$("#agence_price").val(row[0].agence_price);
				$("#hour_price").val(row[0].hour_price);
				$("#fly_site_min").val(row[0].fly_site_min);
				$("#fly_site").val(row[0].fly_site);
			}
		}
		function addData(){
				$("#updateTable").css('display','block');
				$("#id").val("");
				$("#start_time").val("");
				$("#end_time").val("");
				$("#agence_price").val("");
				$("#hour_price").val("");
				$("#fly_site_min").val("");
				$("#fly_site").val("");
		}
		function deleteData(){
			var str="";
			var row = $("#parametersTable").bootstrapTable('getSelections');
			if(row.length==0){
				alert("请选择1条以上记录");
			}else{
				for(var i=0;i<row.length;i++){
					str =str + row[i].id+",";
					//$("#parametersTable").bootstrapTable('remove', {field: 'id', values: row[i].id}); 
				}
				$.ajax({
	    	        type:'post',
	    	        url:'${pageContext.request.contextPath}/deletedailyParameters',
	    	        data:{ids:str},
	    	        success:function(data){
	    	        	alert(data.info);
	    	        	$("#parametersTable").bootstrapTable('destroy');
	    	        	loadList();
	    	        }
	    	    }); 
			}
		}
	
	</script>
