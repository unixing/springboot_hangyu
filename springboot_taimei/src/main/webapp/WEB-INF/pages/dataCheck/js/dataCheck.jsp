<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script>
		var cityPicker = new HzwCityPicker({
			data: data,
			target: 'dpt_AirPt_Cd',
			valType: 'k-v',
			hideCityInput: {
			}
		});
		
		cityPicker.init();
		var cityPicker1 = new HzwCityPicker({
			data: data,
			target: 'arrv_Airpt_Cd',
			valType: 'k-v',
			hideCityInput: {
			}
		});
		
		cityPicker1.init();
		
		function getData(data){
			var custormersearchForm;
//	 		拿到form表单对象
			custormersearchForm = $("#dataCheck");
//	 		调用自定义方法, 内部处理好数据结构拿到json数据对象
			var searchJson = custormersearchForm.searchJson();
			searchJson.dta_Sce = $(window.parent.document.body).find("#cc1").val();
			if("数据源"==searchJson.dta_Sce){
				searchJson.dta_Sce="中航";
			}
			var queryParams = function(params) {
				return {
					limit: params.limit,   //页面大小
		            offset: params.offset,  //页码
					arrv_Airpt_Cd : searchJson.arrv_Airpt_Cd,
					dpt_AirPt_Cd : searchJson.dpt_AirPt_Cd,
					get_tim : searchJson.get_tim,
					goOrReturn : searchJson.goOrReturn,
					startDate : searchJson.startDate,
					endDate : searchJson.endDate,
					flt_Nbr : searchJson.flt_Nbr,
					cpy_Nm : searchJson.cpy_Nm , 
					flt_Rte_Cd : searchJson. flt_Rte_Cd ,                     
					tal_Nbr_Set : searchJson.tal_Nbr_Set ,                   
					pgs_Per_Cls : searchJson.pgs_Per_Cls ,                   
					grp_Nbr : searchJson.grp_Nbr ,
					ech_Cls_Grp : searchJson.ech_Cls_Grp ,                     
					tal_Nbr : searchJson.tal_Nbr ,
					set_Ktr_Ine : searchJson.set_Ktr_Ine ,                   
					two_Tak_Ppt : searchJson.two_Tak_Ppt ,                     
					ful_Pce_Ppt : searchJson.ful_Pce_Ppt ,                     
					nne_Dnt_Ppt : searchJson.nne_Dnt_Ppt ,                     
					eht_Five_Dnt_Ppt : searchJson.eht_Five_Dnt_Ppt,             
					eht_Dnt_Ppt : searchJson.eht_Dnt_Ppt , 
					sen_Five_Dnt_Ppt : searchJson.sen_Five_Dnt_Ppt,                   
					six_Five_Dnt_Ppt : searchJson.six_Five_Dnt_Ppt,                   
					fve_Fve_Dnt_Ppt : searchJson. fve_Fve_Dnt_Ppt ,                   
					fve_Dnt_Ppt : searchJson.fve_Dnt_Ppt ,    
					fur_Fve_Dnt_Ppt : searchJson.fur_Fve_Dnt_Ppt ,                    
					fur_Dnt_Ppt : searchJson.fur_Dnt_Ppt ,    
					sal_Tak_Ppt : searchJson.sal_Tak_Ppt ,  
					r_Tak_Ppt : searchJson.r_Tak_Ppt ,      
					u_Tak_Ppt : searchJson.u_Tak_Ppt ,      
					i_Tak_Ppt : searchJson.i_Tak_Ppt ,      
					z_Tak_Ppt : searchJson.z_Tak_Ppt ,      
					e_Tak_Ppt : searchJson.e_Tak_Ppt ,      
					a_Tak_Ppt : searchJson.a_Tak_Ppt ,      
					o_Tak_Ppt : searchJson.o_Tak_Ppt ,      
					s_Tak_Ppt : searchJson.s_Tak_Ppt ,      
					h_Tak_Ppt : searchJson.h_Tak_Ppt ,      
					x_Tak_Ppt : searchJson.x_Tak_Ppt ,
					children : searchJson.children ,      
					dta_Sce : searchJson.dta_Sce ,
					order: params.order,
		            ordername: params.sort
		            
				};

			};
				$('#MyTimeTable').bootstrapTable("destroy");
				$('#MyTimeTable').bootstrapTable({
		            url: '${pageContext.request.contextPath}/dataCheck_list',
		            striped: true,
		            pagination: true,
		            showColumns: true,
		            pageList: [10,50,200],
		            pageSize:10,
		            pageNumber:1,
		            showExport: true,                     //是否显示导出
        			exportDataType: "basic",
		            queryParams:queryParams,
		            queryParamsType: "limit",
		            sidePagination:'server',//设置为服务器端分页
		            columns:[
						{
						field: 'lcl_Dpt_Day',
						title: '执行日期',
						width:'80'
						},
						{
						field: 'dpt_AirPt_Cd',
						title: '始发地',
						width:'80'
						},
						{
						field: 'arrv_Airpt_Cd',
						title: '到达地',
						width:'80'
						},    
						{
						field: 'flt_Nbr',
						title: '航班号',
						width:'80'
						}, 
						{
						field: 'cpy_Nm',
						title: '公司',
						width:'80'
						},
						{
						field: 'flt_Rte_Cd',
						title: '航线',
						width:'80'
						},
						{
						field: 'tal_Nbr_Set',
						title: '座位总数',
						width:'80'
						},
						{
						field: 'pgs_Per_Cls',
						title: '每班旅客',
						width:'80'
						},
						{
						field: 'grp_Nbr',
						title: '团队人数',
						width:'80'
						},
						{
						field: 'ech_Cls_Grp',
						title: '每班团队',
						width:'80'
						}, 
						{
						field: 'grp_Fng_Rte',
						title: '团队成行率',
						width:'80'
						},
						{
						field: 'grp_Ine',
						title: '团队收入',
						width:'80'
						},
						{
						field: 'egs_Lod_Fts',
						title: '收益客座率',
						width:'80'
						},
						{
						field: 'totalNumber',
						title: '每班收入',
						width:'80'
						},
						{
						field: 'set_Ktr_Ine',
						title: '座公里收入',
						width:'80'
						},
						{
						field: 'avg_Dct',
						title: '平均折扣',
						width:'80'
						},
						{
						field: 'idd_Dct',
						title: '散客折扣',
						width:'80'
						},
						{
						field: 'grp_Dct',
						title: '团队折扣',
						width:'80'
						},
						{
						field: 'two_Tak_Ppt',
						title: '两舱比例',
						width:'80'
						},
						{
						field: 'ful_Pce_Ppt',
						title: '全价比例',
						width:'80'
						},
						{
						field: 'nne_Dnt_Ppt',
						title: '9折比例',
						width:'80'
						},
						{
						field: 'eht_Five_Dnt_Ppt',
						title: '8.5折比例',
						width:'80'
						},
						{
						field: 'eht_Dnt_Ppt',
						title: '8折比例',
						width:'80'
						},
						{
						field: 'sen_Five_Dnt_Ppt',
						title: '7.5折比例',
						width:'80'
						},
						{
						field: 'sen_Dnt_Ppt',
						title: '7折比例',
						width:'80'
						},
						{
						field: 'six_Five_Dnt_Ppt',
						title: '6.5折比例',
						width:'80'
						},
						{
						field: 'six_Dnt_Ppt',
						title: '6折比例',
						width:'80'
						},
						{
						field: 'fve_Fve_Dnt_Ppt',
						title: '5.5折比例',
						width:'80'
						},
						{
						field: 'fve_Dnt_Ppt',
						title: '5折比例',
						width:'80'
						},
						{
						field: 'fur_Fve_Dnt_Ppt',
						title: '4.5折比例',
						width:'80'
						},
						{
						field: 'fur_Dnt_Ppt',
						title: '4折比例',
						width:'80'
						},
						{
						field: 'sal_Tak_Ppt',
						title: '特殊舱比例',
						width:'80'
						},
						{
						field: 'r_Tak_Ppt',
						title: 'R舱比例',
						width:'80'
						},
						{
						field: 'u_Tak_Ppt',
						title: 'U舱比例',
						width:'80'
						},
						{
						field: 'i_Tak_Ppt',
						title: 'I舱比例',
						width:'80'
						},
						{
						field: 'z_Tak_Ppt',
						title: 'Z舱比例',
						width:'80'
						},
						{
						field: 'e_Tak_Ppt',
						title: 'E舱比例',
						width:'80'
						},
						{
						field: 'a_Tak_Ppt',
						title: 'A舱比例',
						width:'80'
						},
						{
						field: 'o_Tak_Ppt',
						title: 'O舱比例',
						width:'80'
						},
						{
						field: 's_Tak_Ppt',
						title: 'S舱比例',
						width:'80'
						},
						{
						field: 'h_Tak_Ppt',
						title: 'H舱比例',
						width:'80'
						},
						{
						field: 'x_Tak_Ppt',
						title: 'X舱比例',
						width:'80'
						},
						{
						field: 'children',
						title: '儿童',
						width:'80'
						},
						{
						field: 'dta_Sce',
						title: '数据来源',
						width:'80'
						}
						]
		        });
     };
	</script>