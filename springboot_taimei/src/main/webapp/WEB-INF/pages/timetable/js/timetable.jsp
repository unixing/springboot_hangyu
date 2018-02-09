<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<style type="text/css">
.wcolor {
	color: white;
	background-color: #2c3e5d;
}

.dcolor {
	color: white;
	background-color: #5987b8;
}
</style>
<script>
		$(document).on('blur','.selectCity',function(){
			$('#hzw_city_picker').attr('display','none');
		});
		var cityPicker = new HzwCityPicker({
			data: data,
			target: 'dpt_AirPt_Cd',
			valType: 'k-v',
			hideCityInput: {}
		});
		
		cityPicker.init();
		var cityPicker1 = new HzwCityPicker({
			data: data,
			target: 'arrv_Airpt_Cd',
			valType: 'k-v',
			hideCityInput: {}
		});
		
		cityPicker1.init();
	</script>
<script type="text/javascript">
		function getNewData(){
			$.get("${pageContext.request.contextPath}/processTask_list",function(data){
				if(data.success){
					alert("温馨提示: " +data.message);
			 	}
			});
		};
		function getData(){
			var queryParams = function() {
				return {
					arrv_Airpt_Cd : $("#arrv_Airpt_Cd").val(),
					dpt_AirPt_Cd : $("#dpt_AirPt_Cd").val(),
					get_tim : $("#get_tim").val(),
					goOrReturn : $("#flt_nbr_Count").val(),
					mu : $("#pas_stp").val(),
					goOrReturn : $("#flt_nbr_Count").val(),
				};
			};
			if(queryParams().arrv_Airpt_Cd!=""||queryParams().dpt_AirPt_Cd!=""){
				$.ajax({
					type:'post',
					url:'${pageContext.request.contextPath}/processTask_list2',
					data:queryParams(),
					success:function(data){
						dataTime(data);
					}
				})
			}else{
				alert("请至少输入一个城市");
			}
     };
     function dataTime(data1){
    	 	$('#MyTimeTable').bootstrapTable("destroy");
			$('#MyTimeTable').bootstrapTable({
				method: 'get',                      //请求方式（*）
	            toolbar: '#toolbar',                //工具按钮用哪个容器
// 	            striped: true,                      //是否显示行间隔色
// 	            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
// 	            pagination: true,                   //是否显示分页（*）
// 	            sortable: false,                     //是否启用排序
// 	            sortOrder: "asc",                   //排序方式
// 	            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
// 	            pageNumber:1,                       //初始化加载第一页，默认第一页
// 	            pageSize: 10,                       //每页的记录行数（*）
// 	            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
// 	            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
// 	            strictSearch: true,
// 	            showColumns: true,                  //是否显示所有的列
// 	            showRefresh: true,                  //是否显示刷新按钮
// 	            minimumCountColumns: 2,             //最少允许的列数
// 	            clickToSelect: true,                //是否启用点击选中行
	            height: 730,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
// 	            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
// 	            cardView: false,                    //是否显示详细视图
// 	            detailView: false,                   //是否显示父子表
				showExport: true, //是否显示导出
        		exportDataType: "basic",
				showColumns:true,
				classes:'table wcolor',
				data:data1.rows,
	            columns:[
					{
					field: 'dpt_AirPt_Cd',
					align: 'center',
					title: '始发地',
					},
					{
					field: 'arrv_Airpt_Cd',
					title: '到达地',
					},    
					{
					field: 'flt_nbr',
					title: '航班号',
					sortable: true,
					}, 
					{
					field: 'airCrft_Typ',
					title: '机型',
					}, {
					field: 'lcl_Dpt_Tm',
					title: '出发时间',
					sortable: true,
					}, {
					field: 'dpt_AirPt_pot',
					title: '出发机场',
					}, {
					field: 'lcl_Arrv_Tm',
					title: '到达时间',
					sortable: true,
					}, {
					field: 'arrv_Airpt_pot',
					title: '到达机场',
					}, {
					title: '班期',
					field: 'days',
					width:279,
					formatter:function(value,row,index){
						
						var svalue1 = "<input id='ban_一' value='一' class='wcolor' type='button' style='width: 39px;'>";
						var svalue2 = "<input id='ban_二' value='二' class='wcolor' type='button' style='width: 39px;'>";
						var svalue3 = "<input id='ban_三' value='三' class='wcolor' type='button' style='width: 39px;'>";
						var svalue4 = "<input id='ban_四' value='四' class='wcolor' type='button' style='width: 39px;'>";
						var svalue5 = "<input id='ban_五' value='五' class='wcolor' type='button' style='width: 39px;'>";
						var svalue6 = "<input id='ban_六' value='六' class='wcolor' type='button' style='width: 39px;'>";
						var svalue7 = "<input id='ban_日' value='日' class='wcolor' type='button' style='width: 39px;'>";
						for (var i=0;i<value.length;i++){
							if("一"==value.charAt(i)){
								svalue1 = "<input id='ban_一' value='一' class='dcolor' type='button' style='width: 39px;'>";
							}
							if("二"==value.charAt(i)){
								svalue2 = "<input id='ban_二' value='二' class='dcolor' type='button' style='width: 39px;'>";
							}
							if("三"==value.charAt(i)){
								svalue3 = "<input id='ban_三' value='三' class='dcolor' type='button' style='width: 39px;'>";
							}
							if("四"==value.charAt(i)){
								svalue4 = "<input id='ban_四' value='四' class='dcolor' type='button' style='width: 39px;'>";
							}
							if("五"==value.charAt(i)){
								svalue5 = "<input id='ban_五' value='五' class='dcolor' type='button' style='width: 39px;'>";
							}
							if("六"==value.charAt(i)){
								svalue6 = "<input id='ban_六' value='六' class='dcolor' type='button' style='width: 39px;'>";
							}
							if("日"==value.charAt(i)){
								svalue7 = "<input id='ban_日' value='日' class='dcolor' type='button' style='width: 39px;'>";
							}
						}
						var svalue = svalue1+svalue2+svalue3+svalue4+svalue5+svalue6+svalue7;
						return svalue;
					}
					}]
	        });
     }
	</script>
<script type="text/javascript">
	var obj2=document.getElementById('cc2');  
	$.ajax({
        type:'post',
        url:'${pageContext.request.contextPath}/processTask_date',//请求数据的地址	
        success:function(data){
        	var i = data.length;
            for(var index in data){
     			obj2.options.add(new Option(data[index].lcl_Dpt_Day)); //这个兼容IE与firefox  
            }
        }
    });
	</script>