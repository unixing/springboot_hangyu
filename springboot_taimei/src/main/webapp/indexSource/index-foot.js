$(function() {
	var tableBtn= $("#onTable");	//显示/隐藏公告的按钮
	var ver_list =$("#ver-list");   //公告列表
	var ver_table =$("#ver-table");	//公告表格	
	var ver_info=[];	//存放
	var btn_pDown =$("#pg_down");	//向下翻页
	var btn_pUp =$("#pg_up");	//向上翻页
	var isText=0;
	//单击外部隐藏表格
	function stopPropagation(e) {
		if (e.stopPropagation) 
			e.stopPropagation(); 
		else 
			e.cancelBubble = true; 
	}
	$(document).bind('click',function(){ 
		ver_table.css('display','none'); 
	}); 
	ver_table.bind('click',function(e){ 
		stopPropagation(e); 
	});
	
	//公告按钮单击后请求数据
	tableBtn.click(function(e){	//显示关闭公告表格,如果关闭就直接关闭，如果打开就请求数据
		btn_pDown.show();
		$(".ver-txt").text("");	//清空更新详情
		$(".ver-txt").css("display","none");//隐藏更新详情标签
		var totalSize;	//存放总条数
		stopPropagation(e);	//阻止按钮冒泡
		if(ver_table.css("display")==="none") {
			ver_list.empty();	//清空uL列表，每次显示都重新加载内容
			//ajax请求数据，并填充至li
			$.ajax({
					type:"post",
					async:true,
					url:"/versioninfo/getFirstPageData",
					dataType:"json",
					success:function(result){
						if(result){
							var tle_len = result.list.list.length;	//获取标题个数
							totalSize = tle_len;
							for(var i = 0 ; i < tle_len ; i++){	//循环取出标题
								$(ver_list).append("<li>"+ result.list.list[i].lclDptDay +"</li>"); //添加li
								ver_info[i]=result.list.list[i].logInfList.join("."); //将json数组转为字符串
							}
						}
						$.each($("#ver-list li"),function(index){
							if((index+1)% 3==0 ){
								$(this).css("border-bottom","0px")
							}
						})
						//列表分页
						btn_pUp.hide();		//每次显示先隐藏向上翻页按钮
						var pageSize=3;      //每页显示数据条数
						var currentPage=1;   //当前页数
						var totalPage=Math.ceil(totalSize/pageSize); //计算总页数，用ceil向上取整
						$("#ver-list li:gt(2)").hide(); //设置首页显示3条数据
						
						//下按钮
						btn_pDown.click(function(){
							//小于4条数据，按钮禁用
							if(totalSize < 4){
								return false;
							}
							else
							{
							    currentPage+=1;  //当前页数先+1
							    if(currentPage>=totalPage){currentPage=totalPage};	//不能大于总页数
								btn_pUp.show();	//显示上一页按钮
								//不是最后一页，显示应该显示的数据.
								var start=pageSize*(currentPage-1);
								var end=pageSize*currentPage;
								$.each($('#ver-list li'),function(index,item){
									if(index >=start && index < end){
										$(this).show();
									}
									else{
										$(this).hide();
									}
								});
								if(currentPage ==totalPage){ //当前页数==最后一页，禁止下一页
								   $(this).hide();
								}								
							}
						});
						//翻页按钮配置
						var ver_txt = $(".ver-txt");
						//上按钮
						btn_pUp.click(function(){
							//关闭更新详情栏目
							if(ver_txt.css("display")=="none"){
								if(currentPage ==1){	//当前页数是1，禁止上一页
									$(this).hide();
								}
								else{	//正常翻页功能
									btn_pDown.show();	//中间页显示下拉按钮
									currentPage-=1;  //当前页数先-1
									if(currentPage == 1){//当前页数==1，禁止上一页
										$(this).hide();
									}
									var start=pageSize*(currentPage-1);
									var end=pageSize*currentPage;
									$.each($('#ver-list li'),function(index,item){
										if(index >=start && index < end){
											$(this).show();
										}
										else{
											$(this).hide();
										}
									});
								}
							}
							else{
								
								ver_txt.css("display","none");//隐藏更新详情标签
								$(ver_list).find("li").show();
								$("#ver-list li:gt(2)").hide();								
							    	$(pg_down).show();
									$(this).hide();
								
							}
						});
						//点击li后下方显示内容
						var li_index = 0;
						var tleLi = $("#ver-list li");
						ver_list.on("click","li",function(){
							ver_txt.empty();
							var index = $(this).index();
							isText = $(this).index();
							li_index=index;
							$(this).siblings().hide();     //点击的li显示,其余兄弟元素隐藏
							$(pg_up).show();	//只显示上拉按钮
							$(pg_down).hide();	//隐藏下拉按钮
							ver_txt.text(ver_info[index].replace("（见截图）",""));	//填充文本
							ver_txt.css("bottom",30);
							ver_txt.css("display","block");	//显示文本
						})
						ver_table.css("display","block");	//显示表格
						ver_table.animate({width:"300px"});	//动画显示表格
					},
					error:function(){
						console.log("请求失败!");
						$(".ver-txt").text("加载失败，请刷新重试！");	
						$(".ver-txt").css("display","block");	//显示错误信息
					}
			})
		} 
		else{
			ver_table.css("display","none");	//关闭表格
		}
	});
})