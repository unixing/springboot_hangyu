<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
		window.onload=function(){
			getFlyTypeData();
		}
		function getFlyTypeData(){
			var custormersearchForm;
//	 		拿到form表单对象
			custormersearchForm = $("#systemLog-searchForm");
//	 		调用自定义方法, 内部处理好数据结构拿到json数据对象
			var searchJson = custormersearchForm.searchJson();
			var queryParams = function(params) {
				return {
					limit: params.limit,   //页面大小
		            offset: params.offset,  //页码
		            flyType : searchJson.flyType,
					order: params.order,
		            ordername: params.sort
				};

			};
			$('#flyTable').bootstrapTable("destroy");
			$('#flyTable').bootstrapTable({
	            url: '${pageContext.request.contextPath}/flyInfoList',
	            striped: true,
	            pagination: true,
	            pageList: [10,50,200],
	            pageSize:10,
	            pageNumber:1,
	            showExport: false, //是否显示导出
       			exportDataType: "basic",
	            queryParams:queryParams,
	            queryParamsType: "limit",
	            sidePagination:'server',//设置为服务器端分页
	            columns:[
					{
					field: 'fly_Tye_Nm',
					title: '飞机型号',
					width: '80',
					align: 'center',
					valign: 'middle'
					},
					{
					field: 'fly_Mfr',
					title: '生成厂家',
					width: '80',
					align: 'center',
					valign: 'middle'
					},    
					{
					field: 'fly_SAt',
					title: '座位数',
					width: '80',
					align: 'center',
					valign: 'middle'
					}, 
					{
					field: 'fly_Bdy_Lgt',
					title: '飞机<br/>长度',
					width: '80',
					align: 'center',
					valign: 'middle'
					}, {
					field: 'fly_Bdy_Wth',
					title: '飞机<br/>宽度',
					width: '80',
					align: 'center',
					valign: 'middle'
					}, {
					field: 'fly_Bdy_Hgt',
					title: '飞机<br/>高度',
					width: '80',
					align: 'center',
					valign: 'middle'
					}, {
					field: 'fly_Bdy_Wsn',
					title: '机翼展<br/>开宽度',
					width: '80',
					align: 'center',
					valign: 'middle'
					}, {
					field: 'fly_Cgo_Hdl',
					title: '货舱容积',
					width: '80',
					align: 'center',
					valign: 'middle'
					}, {
					title: '飞行速度',
					field: 'fly_Spd',
					width: '80',
					align: 'center',
					valign: 'middle'
					}, {
					title: '空机重',
					field: 'fly_Ept_Mce',
					width: '80',
					align: 'center',
					valign: 'middle'
					}, {
					title: '最大<br/>业载（吨）',
					field: 'fly_Max_Pld',
					width: '80',
					align: 'center',
					valign: 'middle'
					}, {
					title: '最大起飞<br/>总重（吨）',
					field: 'fly_Max_Tkf',
					width: '80',
					align: 'center',
					valign: 'middle'
					}, {
					title: '最大油箱<br/>容量（升）',
					field: 'fly_Tnk_Cpy',
					width: '80',
					align: 'center',
					valign: 'middle'
					}, {
					title: '航程 10,400<br/> 公里',
					field: 'fly_Vyg',
					width: '80',
					align: 'center',
					valign: 'middle'
					}, {
					title: '每小时<br/>成本(参考)',
					field: 'fly_Hos_Cot',
					width: '80',
					align: 'center',
					valign: 'middle'
					}
					]
	        });
     };
	</script>
