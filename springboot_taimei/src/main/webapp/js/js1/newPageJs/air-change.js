$().ready(function(){
	
	var oChangeA= $("#their-ownA");
	var oChangeB=$("#their-ownB");
	$(".air-change-btn").click(function(){
		setTimeout(function(){
			var asd = $("#their-icao").text(); //获取机场ioca
			var tagg="";
			loadWeatherA(asd,tagg);	//根据ioca获取天气报文
		},10)
		function loadWeatherA(ioca,tagg){
			tagg="";
			long=[];
			$.ajax({
				url:'/getAirportWeather',
				type:'get',
				data:{
					ioca:ioca
				},
				success:function(data){
					if(data!=null&&data!=''){
						var weatherInfo =  data.weatherInfo;
						//console.log(weatherInfo);
						if(weatherInfo){
						//var ss = weatherInfo.split(" ");						
						metar_decode(weatherInfo);
						//符号的转义
//						op_text.replace(/摄氏度/,"°C");
//						op_text.replace(/度/,"°");
//						op_text.replace(/米每秒/,"m/s");
//						op_text.replace(/米/,"m");
//						op_text.replace(/百帕/,"°hPa");
//						op_text.replace(/英寸汞柱/,"inHg");
						//$("#their-txt-pull").html(long[1]);
						//$("#their-txt-pull").html(op_text);
						
						for(var i = 2 ; i<long.length ; i++){
							tagg = tagg + long[i];
						}
						$("#their-txt-pull").empty();
						$("#their-txt-pull").html(tagg);
						//$("#their-txt-pull").html("<span class=\"no_data\">当前无数据返回！</span>");
						}
						else{
							$("#their-txt-pull").html("<h4>暂无数据！</h4>");					
						}
					}
					else{
						//$("#their-txt-pull").html("<span class=\"no_data\">当前无数据返回！</span>");
					}
				}
			})
		}
		if(oChangeA.css("z-index")!=1){
			oChangeA.css("transform","rotateY(180deg)");
			oChangeA.css("z-index","1");
			oChangeB.css("transform","rotateY(0deg)");
			oChangeB.css("z-index","2");
		}
		else
		{
			oChangeA.css("transform","rotateY(0deg)");
			oChangeA.css("z-index","2");
			oChangeB.css("transform","rotateY(-180deg)");
			oChangeB.css("z-index","1");
		}
	})
})