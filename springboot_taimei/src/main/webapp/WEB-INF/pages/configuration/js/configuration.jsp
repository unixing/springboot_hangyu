<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	 window.operateEvents = {
		        'click .like': function (e, value, row, index) {
// 		            alert('You click like action, row: ' + JSON.stringify(row));
		            edit(row);
		        },
		        'click .remove': function (e, value, row, index) {
		            $.ajax({
		            	url:"${pageContext.request.contextPath}/configuration_delete?id="+row.id,
		            	success:function(data){
		            		console.debug(data);
		            		if(data.success){
// 		            			location.reload();
		            		}
		            	}
		            });
		        	$('#configurationTable').bootstrapTable('remove', {
		                field: 'id',
		                values: [row.id]
		            });
		        }
		    };
		$('#configurationTable').bootstrapTable({
			url: '${pageContext.request.contextPath}/configuration_list',
// 			onClickRow:edit,
			striped:true,
		    columns: [
		    {
		        field: 'isc_Tem',
		        title: '公司名称'
		    }, {
		        field: 'etm_Usr',
		        title: 'eterm配置号'
		    }, {
		        field: 'etm_Psw',
		        title: 'eterm配置号密码'
		    }, {
		        field: 'off_ID',
		        title: '工作号'
		    }, {
		        field: 'off_Pwd',
		        title: '工作号密码'
		    }, {
		        field: 'acc_Lvl',
		        title: '级别'
		    }, {
		        field: 'etm_IP',
		        title: 'etermIP'
		    }, {
		        field: 'etm_Pot',
		        title: 'eterm端口'
		    }, {
		        field: 'aic_Tie',
		        title: '自动采集时间(时)'
		    }, {
		        field: 'dte_Aic_Rrt',
		        title: '冲突等待时间(分)'
		    }, {
                field: 'id',
                title: '操&emsp;作',
                align: 'center',
                events: operateEvents,
                formatter: operateFormatter
            }
		    ]
		});
	 function operateFormatter(value, row, index) {
	        return [
	            '<a class="like" href="javascript:void(0)" title="Like">',
	            '<i class="glyphicon glyphicon-cog"></i>',
	            '</a>  &emsp;&emsp;',
	            '<a class="remove" href="javascript:void(0)" title="Remove">',
	            '<i class="glyphicon glyphicon-remove"></i>',
	            '</a>'
	        ].join('');
	    }
	function edit(row){
		var configuration_edit =  $("#configuration_edit");
		if(row==null){
			$("#id").val(0);
			$("#isc_Tem").val(null);
			$("#etm_Usr").val(null);
			$("#etm_Psw").val(null);
			$("#off_ID").val(null);
			$("#off_Pwd").val(null);
			$("#acc_Lvl").val(null);
			$("#etm_IP").val(null);
			$("#etm_Pot").val(null);
			$("#aic_Tie").val(null);
			$("#dte_Aic_Rrt").val(null);
			configuration_edit.modal("toggle");
		}else{
			
			$("#id").val(row.id);
			$("#isc_Tem").val(row.isc_Tem);
			$("#etm_Usr").val(row.etm_Usr);
			$("#etm_Psw").val(row.etm_Psw);
			$("#off_ID").val(row.off_ID);
			$("#off_Pwd").val(row.off_Pwd);
			$("#acc_Lvl").val(row.acc_Lvl);
			$("#etm_IP").val(row.etm_IP);
			$("#etm_Pot").val(row.etm_Pot);
			$("#aic_Tie").val(row.aic_Tie);
			$("#dte_Aic_Rrt").val(row.dte_Aic_Rrt);
			configuration_edit.modal("toggle");
		}
	}
	function submitConfig(){
		var config = $("#configuration_add").searchJson();
		$("#configuration_edit").modal("hide");
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/configuration_save",
			data:config,
			success:function(date){
				if(date.success){
					alert(date.message);
					location.reload();
				}
			}
		})
	}
	</script>
