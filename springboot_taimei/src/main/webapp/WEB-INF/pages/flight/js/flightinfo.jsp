<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
function init(){
	var cityPicker = new HzwCityPicker({
		data: data,
		target: 'flight_fltRteCd',
		valType: 'k-v',
		hideCityInput: {
		},
		callback: function(){
			
		}
	});
	cityPicker.init();
	var cityPicker = new HzwCityPicker({
		data: data,
		target: 'dpt_AirPt_Cd',
		valType: 'k-v',
		hideCityInput: {
		},
		callback: function(){
			
		}
	});
	cityPicker.init();
	var cityPicker = new HzwCityPicker({
		data: data,
		target: 'arrv_Airpt_Cd',
		valType: 'k-v',
		hideCityInput: {
		},
		callback: function(){
			getFltNbr();
		}
	});
	cityPicker.init();
	var cityPicker = new HzwCityPicker({
		data: data,
		target: 'pas_stp',
		valType: 'k-v',
		hideCityInput: {
		},
		callback: function(){
			
		}
	});
	cityPicker.init();
}
function add(){
	if($('#dpt_AirPt_Cd').val()==''){
		alert('请选择起始机场');
		return;
	}
	if($('#arrv_Airpt_Cd').val()==''){
		alert('请选择到达机场');
		return;
	}
	if($('#flight_fltNbr').val()==''){
		alert('请填写航班号');
		return;
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/flightinfo_add',
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
	if($('#dpt_AirPt_Cd').val()==''){
		alert('请选择起始机场');
		return;
	}
	if($('#arrv_Airpt_Cd').val()==''){
		alert('请选择到达机场');
		return;
	}
	if($('#flight_fltNbr').val()==''){
		alert('请填写航班号');
		return;
	}
	$.ajax({
		url:'${pageContext.request.contextPath}/flightinfo_update',
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

function groundOrGoAround(type){
	var row = $("#datalist").bootstrapTable('getSelections');
	if(row.length==1){
		if(row[0].status==type){
			alert('操作无效');
			return;
		}
		$.ajax({
			url:'${pageContext.request.contextPath}/flightinfo_groundOrGoAround',
			data:{
				id:row[0].id,
				status:type
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

function batchdel(){
	var row = $("#datalist").bootstrapTable('getSelections');
	if(row.length>0){
		var ids = '';
		for(var i=0;i<row.length;i++){
			if(i==0){
				ids +=row[i].id;
			}else{
				ids +=','+row[i].id
			}
		}
		$.ajax({
			url:'${pageContext.request.contextPath}/flightinfo_batchdel',
			data:{
				ids:ids
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
		url:'${pageContext.request.contextPath}/flightinfo_search',
		data:$('#systemLog-searchForm').serialize(),
		type:'post',
		success:function(data){
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
		    		        	 field:'itia',
		    		        	 visible:false,
		    		         },{
		    		        	 field:'id',
		    		        	 visible:false,
		    		         },{
		    		        	 title:'选择',
		    		        	 checkbox:true,
		    		        	 width:'3%',
		    		         },{
		    		        	field:'airport',
		    		        	title:'所属机场',
		    		        	align:'center',
		    		        	width:'3%',
		    		         },{
		    		        	 field:'dpt_AirPt_Cd',
		    		        	 title:'始发地',
		    		        	 align:'center',
		    		        	 width:'3%',
		    		         },{
		    		        	 field:'pas_stp',
		    		        	 title:'经停地',
		    		        	 align:'center',
		    		        	 width:'3%',
		    		         },{
		    		        	 field:'arrv_Airpt_Cd',
		    		        	 title:'到达地',
		    		        	 align:'center',
		    		        	 width:'3%',
		    		         },{
		    		        	 field:'fltNbr',
		    		        	 title:'航班号',
		    		        	 align:'center',
		    		        	 width:'3%',
		    		         },{
		    		        	 field:'status',
		    		        	 title:'状态',
		    		        	 align:'center',
		    		        	 width:'3%',
		    		        	 formatter:function(value,row,index){
		    		        		 if(value==1){
		    		        			return '在飞';
		    		        		 }else if(value==2){
		    		        			return '停飞';
		    		        		 }
		    		        	 }
		    		         }
		    		         
   		         	]
				});
			}else{
				alert('后台操作异常');
			}
		}
	});
}
function addshow(){
	$.ajax({
		url:'${pageContext.request.contextPath}/company_list',
		data:[],
		type:'post',
		success:function(data){
			if(data!=null&&data.list!=null&&data.list.length>0){
				var list = data.list;
				var operation = '';
				$("#datalist").empty();
				operation += '<form id="addform">';
				operation += '<div class="addorupdate" style="border: 1px solid #dedede;">';
				operation += '<div class="title" style="background: #f2f0f1; width:100%;height:30px"><span style="line-height: 30px; font-weight: bold;color:#999798;">航线航班添加</span><span onclick="closePage(\'datalist\',\'search\');" style="font-weight:bold;float:right;line-height:30px;color:#999798;cursor:pointer;margin-right:5px;">关闭</span></div>';
				operation += '<div class="content"><ul style="border-bottom:1px solid #dedede;height:35px;">';
				operation += '<li style="text-indent: 10px;float: left;"><span>机场名：</span><select id="companyList" name="itia">';
				for(var i=0;i<list.length;i++){
					operation += '<option value="'+list[i].cpyItia+'">'+list[i].cpyNm+'</option>';
				}
				operation += '</select></li>';
				operation += '<li style="text-indent: 10px;float: left;"><span>始发地:</span><input class="flight" id="dpt_AirPt_Cd" name="dpt_AirPt_Cd"/></li>';
				operation += '<li style="text-indent: 10px;float: left;"><span>经停地:</span><input class="flight" id="pas_stp" name="pas_stp"/></li>';
				operation += '<li style="text-indent: 10px;float: left;"><span>到达地:</span><input class="flight" id="arrv_Airpt_Cd" name="arrv_Airpt_Cd"/></li>';
				operation += '<li style="text-indent: 10px;float: left;"><span>航&nbsp;&nbsp;&nbsp;班:</span><input class="flight" id="flight_fltNbr" name="fltNbr"/></li>';
				operation += '</ul><ul style="border-bottom:1px solid #dedede;height:35px;">';
				operation += '<li style="width:100%; text-align: center;"><input class="btn btn-success" type="button" value="添加" onclick="add()"></li>';
				operation += '</li></ul></div></div></form>';
				$("#datalist").append(operation);
				init();
			}else{
				alert('请先添加公司');
			}
		}
	});
}
function updateshow(){
	var row = $("#datalist").bootstrapTable('getSelections');
	if(row.length==1){
		$.ajax({
			url:'${pageContext.request.contextPath}/company_list',
			data:[],
			type:'post',
			success:function(data){
				if(data!=null&&data.list!=null&&data.list.length>0){
					var list = data.list;
					var operation = '';
					$("#datalist").empty();
					operation += '<form id="updateform">';
					operation += '<div class="addorupdate" style="border: 1px solid #dedede;">';
					operation += '<div class="title" style="background: #f2f0f1; width:100%;height:30px"><span style="line-height: 30px; font-weight: bold;color:#999798;">航线航班修改</span><span onclick="closePage(\'datalist\',\'search\');" style="font-weight:bold;float:right;line-height:30px;color:#999798;cursor:pointer;margin-right:5px;">关闭</span></div>';
					operation += '<div class="content"><ul style="border-bottom:1px solid #dedede;height:35px;">';
					operation += '<li style="text-indent: 10px;float: left;"><span>机场名：</span><input type="hidden" value="'+row[0].id+'" name="id"/><select id="companyList" name="itia">';
					for(var i=0;i<list.length;i++){
						if(row[0].itia==list[i].cpyItia){
							operation += '<option value="'+list[i].cpyItia+'" selected="selected">'+list[i].cpyNm+'</option>';
						}else{
							operation += '<option value="'+list[i].cpyItia+'">'+list[i].cpyNm+'</option>';
						}
					}
					operation += '</select></li>';
					operation += '<li style="text-indent: 10px;float: left;"><span>始发地:</span><input class="flight" id="dpt_AirPt_Cd" name="dpt_AirPt_Cd" value="'+row[0].dpt_AirPt_Cd+'"/></li>';
					operation += '<li style="text-indent: 10px;float: left;"><span>经停地:</span><input class="flight" onchange="getFltNbr()" id="pas_stp" name="pas_stp" value="'+(row[0].pas_stp==null?"":row[0].pas_stp)+'"/></li>';
					operation += '<li style="text-indent: 10px;float: left;"><span>到达地:</span><input class="flight" onchange="getFltNbr()" id="arrv_Airpt_Cd" name="arrv_Airpt_Cd" value="'+row[0].arrv_Airpt_Cd+'"/></li>';
					operation += '<li style="text-indent: 10px;float: left;"><span>航&nbsp;&nbsp;&nbsp;班:</span><input class="flight" name="fltNbr" id="flight_fltNbr" value="'+row[0].fltNbr+'"/></li>';
					operation += '</ul><ul style="border-bottom:1px solid #dedede;height:35px;">';
					operation += '<li style="width:100%; text-align: center;"><input class="btn btn-success" type="button" value="修改" onclick="update()"></li>';
					operation += '</li></ul></div></div></form>';
					$("#datalist").append(operation);
					init();
				}else{
					alert("请先添加公司");
				}
			}
		});
	}else{
		alert('请选择一条数据');
	}
}
function getFltNbr(){
	var dpt_AirPt_Cd = $('#dpt_AirPt_Cd').val();
	if(dpt_AirPt_Cd==null||dpt_AirPt_Cd==''){
		alert('请选择始发点');
		return;
	}
	var arrv_Airpt_Cd = $('#arrv_Airpt_Cd').val();
	if(arrv_Airpt_Cd==null||arrv_Airpt_Cd==''){
		alert('请选择到达地');
		return;
	}
	var pas_stp = $('#pas_stp').val();
	$.ajax({
		url:'${pageContext.request.contextPath}/getFltNum',
		data:{
			dpt_AirPt_Cd:dpt_AirPt_Cd,
			arrv_Airpt_Cd:arrv_Airpt_Cd,
			pas_stp:pas_stp
		},
		type:'post',
		success:function(data){
			if(data!=null){
				if(data.list!=null&&data.list.length>0){
					var list = data.list;
					var value = '';
					for(var i=0;i<list.length;i++){
						if(i==list.length-1){
							value += list[i];
						}else{
							value += list[i]+',';
						}
					}
					$('#flight_fltNbr').val(value);
				}else{
					$('#flight_fltNbr').val('');
				}
			}else{
				alert("服务器繁忙，请稍后再试。");
			}
		}
	});
}
</script>