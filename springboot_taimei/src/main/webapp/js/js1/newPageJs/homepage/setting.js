
    var kpiData={};//首页航线视图排行与趋势数据
    //数据图
    function drawImg(summary){
        var dom = document.getElementById("pla-ranking-box");
        var myChart = echarts.init(dom);
        var xl = summary.time.length;

        var option = {
            grid :{
                x:70
            },
            tooltip : {
                trigger: 'axis'
            },
            color:['#8bcded','#bd5741'],
            calculable : true,
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: summary.time,
                silent:true,
                axisLine:{
                    show:true,
                    lineStyle:{
                        color:"#2e416c"
                    }
                },
                splitLine:{
                    show:true,
                    lineStyle:{
                        color:"#304b76",
                        opacity:0.6
                    }
                },
                axisLabel:{
                    interval: 0,//全部显示
                    formatter: function(p){
                        return summary.time.length>15?(p.split('.')[1]):p;
                    },
                    textStyle:{
                        color:"white"
                    }
                 }
            },
            yAxis : [
                {
                    type : 'value',
                    show:true,
                    axisLine:{
                        show:true,
                        lineStyle:{
                            color:"#2e416c"
                        }
                    },
                    splitLine:{
                        show:false
                    },
                    axisLabel:{
                           textStyle:{
                             color:"white"
                        }
                   }
                }
            ],
            series : [
                {
                    name: "收入/1000（元）",
                    type: 'line',
                    smooth:true,
                    label: {
                        normal: {
                            show: false,
                            position: 'top'
                        }
                    },
                    symbol:'circle',
                    symbolSize:'0',
                    lineStyle: {
                        normal: {
                            width: 4,
                        }
                    },
                    data: summary.separateM
                },
                {
                    name: "人数/天（人）",
                    type: 'line',
                    smooth:true,
                    label: {
                        normal: {
                            show: false,
                            position: 'top'
                        }
                    },
                    symbol:'circle',
                    symbolSize:'0',
                    lineStyle: {
                        normal: {
                            width: 4,
                        }
                    },
                    data: summary.separateR
                }
            ]
        };
        myChart.setOption(option,true);
    }
    $(function(){
        $(".unified").on("click",function(){
            if( $(this).css("overflow")=="hidden"){
                $(this).css({"overflow":"visible","height":"auto"}).siblings().css({"overflow":"hidden","height":"30px"});
            }else if($(this).css("overflow")=="visible"){
                $(this).css({"overflow":"hidden","height":"30px"});
            }
    
        });
        $(".unified>ul>li").on("click",function(event){
            if(!$(this).hasClass("li-mark1")){
                $(".unified>ul>li>span").css("background-color","transparent");
                $(this).children(0).css("background-color","#7dbce8");
                event.stopPropagation();
            }else {
    
            }
        });
    
        /**/
          //鼠标移入
          $(".pla-btn-switch").on("mouseover",function(){
              if($('.pla-btn-switch').attr('tag')=='air'){
                  $(".pla-btn-prompt").css({"display":"block",top:"0%"}).text('切换到航线视图');
              }else{
                  $(".pla-btn-prompt").css({"display":"block",top:"0%"}).text('切换到机场视图');
              }
          });
          $(".pla-btn-switch_line").on("mouseover",function(){
              if($('.pla-btn-switch_line').attr('tag')=='air'){
                  $(".pla-btn-prompt").css({"display":"block",top:"0%"}).text('切换到航线视图');
              }else{
                  $(".pla-btn-prompt").css({"display":"block",top:"33%"}).text('切换到航路视图');
              }
          });
          $(".pla-btn-flight").on("mouseover",function(){
              $(".pla-btn-prompt").css({"display":"block",top:"67%"}).html("航班排行");
          });
          $(".pla-btn-trend").on("mouseover",function(){
              $(".pla-btn-prompt").css({"display":"block",top:"100%"}).html("最近七天汇总数据");
          });
          $(".pla-btn-national").on("mouseover",function(){
              if($(".pla-btn-switch").attr("tag")=="line"){
                  $(".pla-btn-prompt").css({"display":"block",top:"0%"}).html("返回到航线视图");
              }else{
                  $(".pla-btn-prompt").css({"display":"block",top:"0%"}).html("返回到机场视图");
              }
          });
          //鼠标移出
          $(".pla-btn-trend,.pla-btn-flight,.pla-btn-switch,.pla-btn-switch_line,.pla-btn-national").on("mouseout",function(){
              $(".pla-btn-prompt").css({"display":"none"});
          });
          $(".pla-ranking").on("click","li",function(){
              var va=[];
              var fig=[];
              if($(this).attr("id")=="income"){
                  $(this).addClass("sets").siblings().removeClass("sets");
                  var clickPoint = $('.result-acts').text();
                  names="收入";
                  if(clickPoint.indexOf('最近1天')>-1){
                      if(clickPoint.indexOf('汇总')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[0][0];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[0][0];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('出港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[0][1];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[0][1];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('进港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[0][2];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[0][2];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }
                  }else if(clickPoint.indexOf('最近7天')>-1){
                      if(clickPoint.indexOf('汇总')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[1][0];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[1][0];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('出港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[1][1];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[1][1];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('进港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[1][2];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[1][2];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }
                  }else if(clickPoint.indexOf('最近30天')>-1){
                      if(clickPoint.indexOf('汇总')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[2][0];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[2][0];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('出港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[2][1];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[2][1];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('进港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[2][2];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[2][2];
                              for(var i=0;i<optionData.income.length;i++){
                                  va.push(optionData.income[i][0]);
                                  fig.push(optionData.income[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }
                  }
              }else if($(this).attr("id")=="traffic"){
                  $(this).addClass("sets").siblings().removeClass("sets");
                  var clickPoint = $('.result-acts').text();
                  names="客流量";
                  if(clickPoint.indexOf('最近1天')>-1){
                      if(clickPoint.indexOf('汇总')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[0][0];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[0][0];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('出港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[0][1];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[0][1];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('进港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[0][2];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[0][2];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }
                  }else if(clickPoint.indexOf('最近7天')>-1){
                      if(clickPoint.indexOf('汇总')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[1][0];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[1][0];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('出港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[1][1];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[1][1];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('进港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[1][2];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[1][2];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }
                  }else if(clickPoint.indexOf('最近30天')>-1){
                      if(clickPoint.indexOf('汇总')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[2][0];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[2][0];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('出港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[2][1];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[2][1];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('进港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[2][2];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[2][2];
                              for(var i=0;i<optionData.traffic.length;i++){
                                  va.push(optionData.traffic[i][0]);
                                  fig.push(optionData.traffic[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }
                  }
              }else if($(this).attr("id")=="proportion"){
                  $(this).addClass("sets").siblings().removeClass("sets");
                  var clickPoint = $('.result-acts').text();
                  names="客座率";
                  if(clickPoint.indexOf('最近1天')>-1){
                      if(clickPoint.indexOf('汇总')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[0][0];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[0][0];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('出港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[0][1];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[0][1];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('进港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[0][2];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[0][2];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }
                  }else if(clickPoint.indexOf('最近7天')>-1){
                      if(clickPoint.indexOf('汇总')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[1][0];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[1][0];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('出港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[1][1];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[1][1];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('进港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[1][2];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[1][2];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }
                  }else if(clickPoint.indexOf('最近30天')>-1){
                      if(clickPoint.indexOf('汇总')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[2][0];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[2][0];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('出港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[2][1];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[2][1];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('进港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[2][2];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[2][2];
                              for(var i=0;i<optionData.proportion.length;i++){
                                  va.push(optionData.proportion[i][0]);
                                  fig.push(optionData.proportion[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }
                  }
              }else if($(this).attr("id")=="quasi"){
                  $(this).addClass("sets").siblings().removeClass("sets");
                  var clickPoint = $('.result-acts').text();
                  names="准点率";
                  if(clickPoint.indexOf('最近1天')>-1){
                      if(clickPoint.indexOf('汇总')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[0][0];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[0][0];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('出港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[0][1];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[0][1];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('进港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[0][2];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[0][2];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }
                  }else if(clickPoint.indexOf('最近7天')>-1){
                      if(clickPoint.indexOf('汇总')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[1][0];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[1][0];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('出港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[1][1];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[1][1];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('进港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[1][2];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[1][2];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }
                  }else if(clickPoint.indexOf('最近30天')>-1){
                      if(clickPoint.indexOf('汇总')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[2][0];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[2][0];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('出港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[2][1];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[2][1];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }else if(clickPoint.indexOf('进港')>-1){
                          if($('.pla-ranking-tag').hasClass('top-ten')){
                              var optionData = kpiData.topTen[2][2];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }else{
                              var optionData = kpiData.afterTen[2][2];
                              for(var i=0;i<optionData.quasi.length;i++){
                                  va.push(optionData.quasi[i][0]);
                                  fig.push(optionData.quasi[i][1]);
                              }
                              drawing(fig,va);
                          }
                      }
                  }
              } else if($(this).hasClass("result-act")){
                    $(this).addClass("result-acts").siblings().removeClass("result-acts");
                    var clickPoint = $('.result-acts').text();
                    if(clickPoint.indexOf('最近1天')>-1){
                        if(clickPoint.indexOf('汇总')>-1){
                            if($('.pla-ranking-tag').hasClass('top-ten')){
                                var optionData = kpiData.topTen[0][0];
                                var typeId = $('.sets').attr('id');
                                if(typeId=='income'){
                                    for(var i=0;i<optionData.income.length;i++){
                                        va.push(optionData.income[i][0]);
                                        fig.push(optionData.income[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='traffic'){
                                    for(var i=0;i<optionData.traffic.length;i++){
                                        va.push(optionData.traffic[i][0]);
                                        fig.push(optionData.traffic[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='proportion'){
                                    for(var i=0;i<optionData.proportion.length;i++){
                                        va.push(optionData.proportion[i][0]);
                                        fig.push(optionData.proportion[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='quasi'){
                                    for(var i=0;i<optionData.quasi.length;i++){
                                        va.push(optionData.quasi[i][0]);
                                        fig.push(optionData.quasi[i][1]);
                                    }
                                    drawing(fig,va);
                                }
                            }else{
                                var optionData = kpiData.afterTen[0][0];
                                var typeId = $('.sets').attr('id');
                                if(typeId=='income'){
                                    for(var i=0;i<optionData.income.length;i++){
                                        va.push(optionData.income[i][0]);
                                        fig.push(optionData.income[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='traffic'){
                                    for(var i=0;i<optionData.traffic.length;i++){
                                        va.push(optionData.traffic[i][0]);
                                        fig.push(optionData.traffic[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='proportion'){
                                    for(var i=0;i<optionData.proportion.length;i++){
                                        va.push(optionData.proportion[i][0]);
                                        fig.push(optionData.proportion[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='quasi'){
                                    for(var i=0;i<optionData.quasi.length;i++){
                                        va.push(optionData.quasi[i][0]);
                                        fig.push(optionData.quasi[i][1]);
                                    }
                                    drawing(fig,va);
                                }
                            }
                        }else if(clickPoint.indexOf('出港')>-1){
                            if($('.pla-ranking-tag').hasClass('top-ten')){
                                var optionData = kpiData.topTen[0][1];
                                var typeId = $('.sets').attr('id');
                                if(typeId=='income'){
                                    for(var i=0;i<optionData.income.length;i++){
                                        va.push(optionData.income[i][0]);
                                        fig.push(optionData.income[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='traffic'){
                                    for(var i=0;i<optionData.traffic.length;i++){
                                        va.push(optionData.traffic[i][0]);
                                        fig.push(optionData.traffic[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='proportion'){
                                    for(var i=0;i<optionData.proportion.length;i++){
                                        va.push(optionData.proportion[i][0]);
                                        fig.push(optionData.proportion[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='quasi'){
                                    for(var i=0;i<optionData.quasi.length;i++){
                                        va.push(optionData.quasi[i][0]);
                                        fig.push(optionData.quasi[i][1]);
                                    }
                                    drawing(fig,va);
                                }
                            }else{
                                var optionData = kpiData.afterTen[0][1];
                                var typeId = $('.sets').attr('id');
                                if(typeId=='income'){
                                    for(var i=0;i<optionData.income.length;i++){
                                        va.push(optionData.income[i][0]);
                                        fig.push(optionData.income[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='traffic'){
                                    for(var i=0;i<optionData.traffic.length;i++){
                                        va.push(optionData.traffic[i][0]);
                                        fig.push(optionData.traffic[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='proportion'){
                                    for(var i=0;i<optionData.proportion.length;i++){
                                        va.push(optionData.proportion[i][0]);
                                        fig.push(optionData.proportion[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='quasi'){
                                    for(var i=0;i<optionData.quasi.length;i++){
                                        va.push(optionData.quasi[i][0]);
                                        fig.push(optionData.quasi[i][1]);
                                    }
                                    drawing(fig,va);
                                }
                            }
                        }else if(clickPoint.indexOf('进港')>-1){
                            if($('.pla-ranking-tag').hasClass('top-ten')){
                                var optionData = kpiData.topTen[0][2];
                                var typeId = $('.sets').attr('id');
                                if(typeId=='income'){
                                    for(var i=0;i<optionData.income.length;i++){
                                        va.push(optionData.income[i][0]);
                                        fig.push(optionData.income[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='traffic'){
                                    for(var i=0;i<optionData.traffic.length;i++){
                                        va.push(optionData.traffic[i][0]);
                                        fig.push(optionData.traffic[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='proportion'){
                                    for(var i=0;i<optionData.proportion.length;i++){
                                        va.push(optionData.proportion[i][0]);
                                        fig.push(optionData.proportion[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='quasi'){
                                    for(var i=0;i<optionData.quasi.length;i++){
                                        va.push(optionData.quasi[i][0]);
                                        fig.push(optionData.quasi[i][1]);
                                    }
                                    drawing(fig,va);
                                }
                            }else{
                                var optionData = kpiData.afterTen[0][2];
                                var typeId = $('.sets').attr('id');
                                if(typeId=='income'){
                                    for(var i=0;i<optionData.income.length;i++){
                                        va.push(optionData.income[i][0]);
                                        fig.push(optionData.income[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='traffic'){
                                    for(var i=0;i<optionData.traffic.length;i++){
                                        va.push(optionData.traffic[i][0]);
                                        fig.push(optionData.traffic[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='proportion'){
                                    for(var i=0;i<optionData.proportion.length;i++){
                                        va.push(optionData.proportion[i][0]);
                                        fig.push(optionData.proportion[i][1]);
                                    }
                                    drawing(fig,va);
                                }else if(typeId=='quasi'){
                                    for(var i=0;i<optionData.quasi.length;i++){
                                        va.push(optionData.quasi[i][0]);
                                        fig.push(optionData.quasi[i][1]);
                                    }
                                    drawing(fig,va);
                                }
                            }
                        }
                    }else if(clickPoint.indexOf("最近7天")>-1){
                        var titleText = $('.pla-ranking').find('h3').text();
                        if(titleText=='航班排行'){
                            if(clickPoint.indexOf('汇总')>-1){
                                if($('.pla-ranking-tag').hasClass('top-ten')){
                                    var optionData = kpiData.topTen[1][0];
                                    var typeId = $('.sets').attr('id');
                                    if(typeId=='income'){
                                        for(var i=0;i<optionData.income.length;i++){
                                            va.push(optionData.income[i][0]);
                                            fig.push(optionData.income[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='traffic'){
                                        for(var i=0;i<optionData.traffic.length;i++){
                                            va.push(optionData.traffic[i][0]);
                                            fig.push(optionData.traffic[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='proportion'){
                                        for(var i=0;i<optionData.proportion.length;i++){
                                            va.push(optionData.proportion[i][0]);
                                            fig.push(optionData.proportion[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='quasi'){
                                        for(var i=0;i<optionData.quasi.length;i++){
                                            va.push(optionData.quasi[i][0]);
                                            fig.push(optionData.quasi[i][1]);
                                        }
                                        drawing(fig,va);
                                    }
                                }else{
                                    var optionData = kpiData.afterTen[1][0];
                                    var typeId = $('.sets').attr('id');
                                    if(typeId=='income'){
                                        for(var i=0;i<optionData.income.length;i++){
                                            va.push(optionData.income[i][0]);
                                            fig.push(optionData.income[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='traffic'){
                                        for(var i=0;i<optionData.traffic.length;i++){
                                            va.push(optionData.traffic[i][0]);
                                            fig.push(optionData.traffic[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='proportion'){
                                        for(var i=0;i<optionData.proportion.length;i++){
                                            va.push(optionData.proportion[i][0]);
                                            fig.push(optionData.proportion[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='quasi'){
                                        for(var i=0;i<optionData.quasi.length;i++){
                                            va.push(optionData.quasi[i][0]);
                                            fig.push(optionData.quasi[i][1]);
                                        }
                                        drawing(fig,va);
                                    }
                                }
                            }else if(clickPoint.indexOf('出港')>-1){
                                if($('.pla-ranking-tag').hasClass('top-ten')){
                                    var optionData = kpiData.topTen[1][1];
                                    var typeId = $('.sets').attr('id');
                                    if(typeId=='income'){
                                        for(var i=0;i<optionData.income.length;i++){
                                            va.push(optionData.income[i][0]);
                                            fig.push(optionData.income[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='traffic'){
                                        for(var i=0;i<optionData.traffic.length;i++){
                                            va.push(optionData.traffic[i][0]);
                                            fig.push(optionData.traffic[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='proportion'){
                                        for(var i=0;i<optionData.proportion.length;i++){
                                            va.push(optionData.proportion[i][0]);
                                            fig.push(optionData.proportion[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='quasi'){
                                        for(var i=0;i<optionData.quasi.length;i++){
                                            va.push(optionData.quasi[i][0]);
                                            fig.push(optionData.quasi[i][1]);
                                        }
                                        drawing(fig,va);
                                    }
                                }else{
                                    var optionData = kpiData.afterTen[1][1];
                                    var typeId = $('.sets').attr('id');
                                    if(typeId=='income'){
                                        for(var i=0;i<optionData.income.length;i++){
                                            va.push(optionData.income[i][0]);
                                            fig.push(optionData.income[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='traffic'){
                                        for(var i=0;i<optionData.traffic.length;i++){
                                            va.push(optionData.traffic[i][0]);
                                            fig.push(optionData.traffic[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='proportion'){
                                        for(var i=0;i<optionData.proportion.length;i++){
                                            va.push(optionData.proportion[i][0]);
                                            fig.push(optionData.proportion[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='quasi'){
                                        for(var i=0;i<optionData.quasi.length;i++){
                                            va.push(optionData.quasi[i][0]);
                                            fig.push(optionData.quasi[i][1]);
                                        }
                                        drawing(fig,va);
                                    }
                                }
                            }else if(clickPoint.indexOf('进港')>-1){
                                if($('.pla-ranking-tag').hasClass('top-ten')){
                                    var optionData = kpiData.topTen[1][2];
                                    var typeId = $('.sets').attr('id');
                                    if(typeId=='income'){
                                        for(var i=0;i<optionData.income.length;i++){
                                            va.push(optionData.income[i][0]);
                                            fig.push(optionData.income[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='traffic'){
                                        for(var i=0;i<optionData.traffic.length;i++){
                                            va.push(optionData.traffic[i][0]);
                                            fig.push(optionData.traffic[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='proportion'){
                                        for(var i=0;i<optionData.proportion.length;i++){
                                            va.push(optionData.proportion[i][0]);
                                            fig.push(optionData.proportion[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='quasi'){
                                        for(var i=0;i<optionData.quasi.length;i++){
                                            va.push(optionData.quasi[i][0]);
                                            fig.push(optionData.quasi[i][1]);
                                        }
                                        drawing(fig,va);
                                    }
                                }else{
                                    var optionData = kpiData.afterTen[1][2];
                                    var typeId = $('.sets').attr('id');
                                    if(typeId=='income'){
                                        for(var i=0;i<optionData.income.length;i++){
                                            va.push(optionData.income[i][0]);
                                            fig.push(optionData.income[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='traffic'){
                                        for(var i=0;i<optionData.traffic.length;i++){
                                            va.push(optionData.traffic[i][0]);
                                            fig.push(optionData.traffic[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='proportion'){
                                        for(var i=0;i<optionData.proportion.length;i++){
                                            va.push(optionData.proportion[i][0]);
                                            fig.push(optionData.proportion[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='quasi'){
                                        for(var i=0;i<optionData.quasi.length;i++){
                                            va.push(optionData.quasi[i][0]);
                                            fig.push(optionData.quasi[i][1]);
                                        }
                                        drawing(fig,va);
                                    }
                                }
                            }
                        }else{
                            if(clickPoint.indexOf('汇总')>-1){
                                if($('.labels-set').next().text()=='关注航班'){
                                    var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.fsummary[0][0][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.fsummary[0][0][2];
                                        }else{
                                            optionData = kpiData.fsummary[0][0][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.fsummary[0][0][3];
                                    }
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);
                                }else{
                                    var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.summary[0][0][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.summary[0][0][2];
                                        }else{
                                            optionData = kpiData.summary[0][0][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.summary[0][0][3];
                                    }
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);
                                }
                            }else if(clickPoint.indexOf('出港')>-1){
                                if($('.labels-set').next().text()=='关注航班'){
                                    var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.fsummary[0][1][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.fsummary[0][1][2];
                                        }else{
                                            optionData = kpiData.fsummary[0][1][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.fsummary[0][1][3];
                                    }
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);
                                }else{
                                    var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.summary[0][1][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.summary[0][1][2];
                                        }else{
                                            optionData = kpiData.summary[0][1][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.summary[0][1][3];
                                    }
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);
                                }
                            }else if(clickPoint.indexOf('进港')>-1){
                                if($('.labels-set').next().text()=='关注航班'){
                                    var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.fsummary[0][2][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.fsummary[0][2][2];
                                        }else{
                                            optionData = kpiData.fsummary[0][2][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.fsummary[0][2][3];
                                    }
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);
                                }else{
                                    var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.summary[0][2][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.summary[0][2][2];
                                        }else{
                                            optionData = kpiData.summary[0][2][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.summary[0][2][3];
                                    }
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);
                                }
                            }
                        }
                    }else if(clickPoint.indexOf('最近30天')>-1){
                        var titleText = $('.pla-ranking').find('h3').text();
                        if(titleText=='航班排行'){
                            if(clickPoint.indexOf('汇总')>-1){
                                if($('.pla-ranking-tag').hasClass('top-ten')){
                                    var optionData = kpiData.topTen[2][0];
                                    var typeId = $('.sets').attr('id');
                                    if(typeId=='income'){
                                        for(var i=0;i<optionData.income.length;i++){
                                            va.push(optionData.income[i][0]);
                                            fig.push(optionData.income[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='traffic'){
                                        for(var i=0;i<optionData.traffic.length;i++){
                                            va.push(optionData.traffic[i][0]);
                                            fig.push(optionData.traffic[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='proportion'){
                                        for(var i=0;i<optionData.proportion.length;i++){
                                            va.push(optionData.proportion[i][0]);
                                            fig.push(optionData.proportion[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='quasi'){
                                        for(var i=0;i<optionData.quasi.length;i++){
                                            va.push(optionData.quasi[i][0]);
                                            fig.push(optionData.quasi[i][1]);
                                        }
                                        drawing(fig,va);
                                    }
                                }else{
                                    var optionData = kpiData.afterTen[2][0];
                                    var typeId = $('.sets').attr('id');
                                    if(typeId=='income'){
                                        for(var i=0;i<optionData.income.length;i++){
                                            va.push(optionData.income[i][0]);
                                            fig.push(optionData.income[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='traffic'){
                                        for(var i=0;i<optionData.traffic.length;i++){
                                            va.push(optionData.traffic[i][0]);
                                            fig.push(optionData.traffic[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='proportion'){
                                        for(var i=0;i<optionData.proportion.length;i++){
                                            va.push(optionData.proportion[i][0]);
                                            fig.push(optionData.proportion[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='quasi'){
                                        for(var i=0;i<optionData.quasi.length;i++){
                                            va.push(optionData.quasi[i][0]);
                                            fig.push(optionData.quasi[i][1]);
                                        }
                                        drawing(fig,va);
                                    }
                                }
                            }else if(clickPoint.indexOf('出港')>-1){
                                if($('.pla-ranking-tag').hasClass('top-ten')){
                                    var optionData = kpiData.topTen[2][1];
                                    var typeId = $('.sets').attr('id');
                                    if(typeId=='income'){
                                        for(var i=0;i<optionData.income.length;i++){
                                            va.push(optionData.income[i][0]);
                                            fig.push(optionData.income[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='traffic'){
                                        for(var i=0;i<optionData.traffic.length;i++){
                                            va.push(optionData.traffic[i][0]);
                                            fig.push(optionData.traffic[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='proportion'){
                                        for(var i=0;i<optionData.proportion.length;i++){
                                            va.push(optionData.proportion[i][0]);
                                            fig.push(optionData.proportion[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='quasi'){
                                        for(var i=0;i<optionData.quasi.length;i++){
                                            va.push(optionData.quasi[i][0]);
                                            fig.push(optionData.quasi[i][1]);
                                        }
                                        drawing(fig,va);
                                    }
                                }else{
                                    var optionData = kpiData.afterTen[2][1];
                                    var typeId = $('.sets').attr('id');
                                    if(typeId=='income'){
                                        for(var i=0;i<optionData.income.length;i++){
                                            va.push(optionData.income[i][0]);
                                            fig.push(optionData.income[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='traffic'){
                                        for(var i=0;i<optionData.traffic.length;i++){
                                            va.push(optionData.traffic[i][0]);
                                            fig.push(optionData.traffic[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='proportion'){
                                        for(var i=0;i<optionData.proportion.length;i++){
                                            va.push(optionData.proportion[i][0]);
                                            fig.push(optionData.proportion[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='quasi'){
                                        for(var i=0;i<optionData.quasi.length;i++){
                                            va.push(optionData.quasi[i][0]);
                                            fig.push(optionData.quasi[i][1]);
                                        }
                                        drawing(fig,va);
                                    }
                                }
                            }else if(clickPoint.indexOf('进港')>-1){
                                if($('.pla-ranking-tag').hasClass('top-ten')){
                                    var optionData = kpiData.topTen[2][2];
                                    var typeId = $('.sets').attr('id');
                                    if(typeId=='income'){
                                        for(var i=0;i<optionData.income.length;i++){
                                            va.push(optionData.income[i][0]);
                                            fig.push(optionData.income[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='traffic'){
                                        for(var i=0;i<optionData.traffic.length;i++){
                                            va.push(optionData.traffic[i][0]);
                                            fig.push(optionData.traffic[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='proportion'){
                                        for(var i=0;i<optionData.proportion.length;i++){
                                            va.push(optionData.proportion[i][0]);
                                            fig.push(optionData.proportion[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='quasi'){
                                        for(var i=0;i<optionData.quasi.length;i++){
                                            va.push(optionData.quasi[i][0]);
                                            fig.push(optionData.quasi[i][1]);
                                        }
                                        drawing(fig,va);
                                    }
                                }else{
                                    var optionData = kpiData.afterTen[2][2];
                                    var typeId = $('.sets').attr('id');
                                    if(typeId=='income'){
                                        for(var i=0;i<optionData.income.length;i++){
                                            va.push(optionData.income[i][0]);
                                            fig.push(optionData.income[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='traffic'){
                                        for(var i=0;i<optionData.traffic.length;i++){
                                            va.push(optionData.traffic[i][0]);
                                            fig.push(optionData.traffic[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='proportion'){
                                        for(var i=0;i<optionData.proportion.length;i++){
                                            va.push(optionData.proportion[i][0]);
                                            fig.push(optionData.proportion[i][1]);
                                        }
                                        drawing(fig,va);
                                    }else if(typeId=='quasi'){
                                        for(var i=0;i<optionData.quasi.length;i++){
                                            va.push(optionData.quasi[i][0]);
                                            fig.push(optionData.quasi[i][1]);
                                        }
                                        drawing(fig,va);
                                    }
                                }
                            }
                        }else{
                            if(clickPoint.indexOf('汇总')>-1){
                                if($('.labels-set').next().text()=='关注航班'){
                                    var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.fsummary[1][0][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.fsummary[1][0][2];
                                        }else{
                                            optionData = kpiData.fsummary[1][0][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.fsummary[1][0][3];
                                    }
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);
                                }else{
                                    var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.summary[1][0][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.summary[1][0][2];
                                        }else{
                                            optionData = kpiData.summary[1][0][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.summary[1][0][3];
                                    }
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);
                                }
                            }else if(clickPoint.indexOf('出港')>-1){
                                if($('.labels-set').next().text()=='关注航班'){
                                    var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.fsummary[1][1][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.fsummary[1][1][2];
                                        }else{
                                            optionData = kpiData.fsummary[1][1][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.fsummary[1][1][3];
                                    }
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);
                                }else{
                                    var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.summary[1][1][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.summary[1][1][2];
                                        }else{
                                            optionData = kpiData.summary[1][1][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.summary[1][1][3];
                                    }
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);
                                }
                            }else if(clickPoint.indexOf('进港')>-1){
                                if($('.labels-set').next().text()=='关注航班'){
                                    var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.fsummary[1][2][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.fsummary[1][2][2];
                                        }else{
                                            optionData = kpiData.fsummary[1][2][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.fsummary[1][2][3];
                                    }
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);
                                }else{
                                    var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.summary[1][2][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.summary[1][2][2];
                                        }else{
                                            optionData = kpiData.summary[1][2][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.summary[1][2][3];
                                    }
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);
                                }
                            }
                        }
                    }
              }
          });
          var clickFlag = true;
          var doubleFlag = true;
          $(".pla-ranking").on("click",'label',function(){
              if(clickFlag){
                  clickFlag = false;
              }else{
                  clickFlag = true;
              }
              if(doubleFlag==clickFlag){
                  if($(this).hasClass("labels")){
                      var typeText = $(this).next().text();
                      var checkedText = $('.result-acts').text();
                      if(typeText.indexOf('关注航班')>-1){
                          var focusFlts = kpiData.focusFlt;
                          if(focusFlts!=null&&focusFlts.length>0){
                              if(checkedText.indexOf('最近7天')>-1){
                                  if(checkedText.indexOf('汇总')>-1){
                                        var optionData;
                                          if($('.label-set').length>0){
                                              var summaryText = $('.label-set').next().text();
                                              if(summaryText=='包含过站'){
                                                  optionData = kpiData.fsummary[0][0][1];
                                              }else if(summaryText=='包含甩飞'){
                                                  optionData = kpiData.fsummary[0][0][2];
                                              }else{
                                                  optionData = kpiData.fsummary[0][0][0];
                                              }
                                          }else{//数据不含过站与甩飞
                                              optionData = kpiData.fsummary[0][0][3];
                                          }
                                          if(!optionData.zbc) return alert('暂无数据');
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                  }else if(checkedText.indexOf('出港')>-1){
                                          var optionData;
                                          if($('.label-set').length>0){
                                              var summaryText = $('.label-set').next().text();
                                              if(summaryText=='包含过站'){
                                                  optionData = kpiData.fsummary[0][1][1];
                                              }else if(summaryText=='包含甩飞'){
                                                  optionData = kpiData.fsummary[0][1][2];
                                              }else{
                                                  optionData = kpiData.fsummary[0][1][0];
                                              }
                                          }else{//数据不含过站与甩飞
                                              optionData = kpiData.fsummary[0][1][3];
                                          }
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                  }else if(checkedText.indexOf('进港')>-1){
                                          var optionData;
                                          if($('.label-set').length>0){
                                              var summaryText = $('.label-set').next().text();
                                              if(summaryText=='包含过站'){
                                                  optionData = kpiData.fsummary[0][2][1];
                                              }else if(summaryText=='包含甩飞'){
                                                  optionData = kpiData.fsummary[0][2][2];
                                              }else{
                                                  optionData = kpiData.fsummary[0][2][0];
                                              }
                                          }else{//数据不含过站与甩飞
                                              optionData = kpiData.fsummary[0][2][3];
                                          }
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                  }
                              }else if(checkedText.indexOf('最近30天')>-1){
                                  if(checkedText.indexOf('汇总')>-1){
                                      var optionData;
                                          if($('.label-set').length>0){
                                              var summaryText = $('.label-set').next().text();
                                              if(summaryText=='包含过站'){
                                                  optionData = kpiData.fsummary[1][0][1];
                                              }else if(summaryText=='包含甩飞'){
                                                  optionData = kpiData.fsummary[1][0][2];
                                              }else{
                                                  optionData = kpiData.fsummary[1][0][0];
                                              }
                                          }else{//数据不含过站与甩飞
                                              optionData = kpiData.fsummary[1][0][3];
                                          }
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                  }else if(checkedText.indexOf('出港')>-1){
                                      var optionData;
                                          if($('.label-set').length>0){
                                              var summaryText = $('.label-set').next().text();
                                              if(summaryText=='包含过站'){
                                                  optionData = kpiData.fsummary[1][1][1];
                                              }else if(summaryText=='包含甩飞'){
                                                  optionData = kpiData.fsummary[1][1][2];
                                              }else{
                                                  optionData = kpiData.fsummary[1][1][0];
                                              }
                                          }else{//数据不含过站与甩飞
                                              optionData = kpiData.fsummary[1][1][3];
                                          }
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                  }else if(checkedText.indexOf('进港')>-1){
                                      var optionData;
                                          if($('.label-set').length>0){
                                              var summaryText = $('.label-set').next().text();
                                              if(summaryText=='包含过站'){
                                                  optionData = kpiData.fsummary[1][2][1];
                                              }else if(summaryText=='包含甩飞'){
                                                  optionData = kpiData.fsummary[1][2][2];
                                              }else{
                                                  optionData = kpiData.fsummary[1][2][0];
                                              }
                                          }else{//数据不含过站与甩飞
                                              optionData = kpiData.fsummary[1][2][3];
                                          }
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                  }
                              }
                          }else{
                              var res = confirm('请勾选关注航班');
                              if(res){
                                  $('.sSettings').click();
                                  setTimeout(function(){
                                       $('.fltSet')[0].click();
                                    }, 300);
                              }else{
                                  $('.labels-set').removeClass('labels-set');
                                  $('.labels').each(function(){
                                      var content=$(this).next().text();
                                      if(content=='所有航班'){
                                          $(this).addClass('labels-set');
                                      }
                                  });
                                  return;
                              }
                          }
                      }else if(typeText.indexOf('所有航班')>-1){
                          if(checkedText.indexOf('最近7天')>-1){
                              if(checkedText.indexOf('汇总')>-1){
                                  var optionData;
                                    if($('.label-set').length>0){
                                        var summaryText = $('.label-set').next().text();
                                        if(summaryText=='包含过站'){
                                            optionData = kpiData.summary[0][0][1];
                                        }else if(summaryText=='包含甩飞'){
                                            optionData = kpiData.summary[0][0][2];
                                        }else{
                                            optionData = kpiData.summary[0][0][0];
                                        }
                                    }else{//数据不含过站与甩飞
                                        optionData = kpiData.summary[0][0][3];
                                    }
                                    $('.summary-head').find('h3').text('最近7天汇总数据');
                                    $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                    $('.income').text(optionData.mon);
                                    $('.performShift').text(optionData.zbc);
                                    drawImg(optionData);
                              }else if(checkedText.indexOf('出港')>-1){
                                  var optionData;
                                  if($('.label-set').length>0){
                                      var summaryText = $('.label-set').next().text();
                                      if(summaryText=='包含过站'){
                                          optionData = kpiData.summary[0][1][1];
                                      }else if(summaryText=='包含甩飞'){
                                          optionData = kpiData.summary[0][1][2];
                                      }else{
                                          optionData = kpiData.summary[0][1][0];
                                      }
                                  }else{//数据不含过站与甩飞
                                      optionData = kpiData.summary[0][1][3];
                                  }
                                  $('.summary-head').find('h3').text('最近7天汇总数据');
                                  $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                  $('.income').text(optionData.mon);
                                  $('.performShift').text(optionData.zbc);
                                  drawImg(optionData);
                              }else if(checkedText.indexOf('进港')>-1){
                                  var optionData;
                                  if($('.label-set').length>0){
                                      var summaryText = $('.label-set').next().text();
                                      if(summaryText=='包含过站'){
                                          optionData = kpiData.summary[0][2][1];
                                      }else if(summaryText=='包含甩飞'){
                                          optionData = kpiData.summary[0][2][2];
                                      }else{
                                          optionData = kpiData.summary[0][2][0];
                                      }
                                  }else{//数据不含过站与甩飞
                                      optionData = kpiData.summary[0][2][3];
                                  }
                                  $('.summary-head').find('h3').text('最近7天汇总数据');
                                  $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                  $('.income').text(optionData.mon);
                                  $('.performShift').text(optionData.zbc);
                                  drawImg(optionData);
                              }
                          }else if(checkedText.indexOf('最近30天')>-1){
                              if(checkedText.indexOf('汇总')>-1){
                                  var optionData;
                                  if($('.label-set').length>0){
                                      var summaryText = $('.label-set').next().text();
                                      if(summaryText=='包含过站'){
                                          optionData = kpiData.summary[1][0][1];
                                      }else if(summaryText=='包含甩飞'){
                                          optionData = kpiData.summary[1][0][2];
                                      }else{
                                          optionData = kpiData.summary[1][0][0];
                                      }
                                  }else{//数据不含过站与甩飞
                                      optionData = kpiData.summary[1][0][3];
                                  }
                                  $('.summary-head').find('h3').text('最近7天汇总数据');
                                  $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                  $('.income').text(optionData.mon);
                                  $('.performShift').text(optionData.zbc);
                                  drawImg(optionData);
                              }else if(checkedText.indexOf('出港')>-1){
                                  var optionData;
                                  if($('.label-set').length>0){
                                      var summaryText = $('.label-set').next().text();
                                      if(summaryText=='包含过站'){
                                          optionData = kpiData.summary[1][1][1];
                                      }else if(summaryText=='包含甩飞'){
                                          optionData = kpiData.summary[1][1][2];
                                      }else{
                                          optionData = kpiData.summary[1][1][0];
                                      }
                                  }else{//数据不含过站与甩飞
                                      optionData = kpiData.summary[1][1][3];
                                  }
                                  $('.summary-head').find('h3').text('最近7天汇总数据');
                                  $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                  $('.income').text(optionData.mon);
                                  $('.performShift').text(optionData.zbc);
                                  drawImg(optionData);
                              }else if(checkedText.indexOf('进港')>-1){
                                var optionData;
                                  if($('.label-set').length>0){
                                      var summaryText = $('.label-set').next().text();
                                      if(summaryText=='包含过站'){
                                          optionData = kpiData.summary[1][2][1];
                                      }else if(summaryText=='包含甩飞'){
                                          optionData = kpiData.summary[1][2][2];
                                      }else{
                                          optionData = kpiData.summary[1][2][0];
                                      }
                                  }else{//数据不含过站与甩飞
                                      optionData = kpiData.summary[1][2][3];
                                  }
                                  $('.summary-head').find('h3').text('最近7天汇总数据');
                                  $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                  $('.income').text(optionData.mon);
                                  $('.performShift').text(optionData.zbc);
                                  drawImg(optionData);
                              }
                          }
                      }
                      if($(this).hasClass("labels-set")){
                      }else{
                          $(".labels").removeClass("labels-set");
                          $(this).addClass("labels-set");
                      }
                  }else if($(this).hasClass('datarange')){
                      if($(this).hasClass("label-set")){
                          $(this).removeClass("label-set");
                      }else{
                          $(this).addClass("label-set");
                      }
                      var typeText = $('.labels-set').next().text();
                      var checkedText = $('.result-acts').text();
                      if($('.label-set').length>0){
                          var summaryText = $('.label-set').next().text();
                          if(summaryText=='包含过站'){
                              if(typeText =='所有航班'){
                                  if(checkedText.indexOf('最近7天')>-1){
                                      if(checkedText.indexOf('汇总')>-1){
                                        var optionData = kpiData.summary[0][0][1];
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                            $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                            $('.income').text(optionData.mon);
                                            $('.performShift').text(optionData.zbc);
                                            drawImg(optionData);
                                      }else if(checkedText.indexOf('出港')>-1){
                                        var optionData = kpiData.summary[0][1][1];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                      }else if(checkedText.indexOf('进港')>-1){
                                        var optionData = kpiData.summary[0][2][1];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                      }
                                  }else if(checkedText.indexOf('最近30天')>-1){
                                      if(checkedText.indexOf('汇总')>-1){
                                        var optionData = kpiData.summary[1][0][1];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                      }else if(checkedText.indexOf('出港')>-1){
                                        var optionData = kpiData.summary[1][1][1];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                      }else if(checkedText.indexOf('进港')>-1){
                                        var optionData = kpiData.summary[1][2][1];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                      } 
                                  }
                              }else if(typeText=='关注航班'){
                                  if(checkedText.indexOf('最近7天')>-1){
                                      if(checkedText.indexOf('汇总')>-1){
                                        var optionData = kpiData.fsummary[0][0][1];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                      }else if(checkedText.indexOf('出港')>-1){
                                        var optionData = kpiData.fsummary[0][1][1];
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                            $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                            $('.income').text(optionData.mon);
                                            $('.performShift').text(optionData.zbc);
                                            drawImg(optionData);
                                      }else if(checkedText.indexOf('进港')>-1){
                                        var optionData = kpiData.fsummary[0][2][1];
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                            $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                            $('.income').text(optionData.mon);
                                            $('.performShift').text(optionData.zbc);
                                            drawImg(optionData);
                                      }
                                  }else if(checkedText.indexOf('最近30天')>-1){
                                      if(checkedText.indexOf('汇总')>-1){
                                        var optionData = kpiData.fsummary[1][0][1];
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                            $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                            $('.income').text(optionData.mon);
                                            $('.performShift').text(optionData.zbc);
                                            drawImg(optionData);
                                      }else if(checkedText.indexOf('出港')>-1){
                                        var optionData = kpiData.fsummary[1][1][1];
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                            $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                            $('.income').text(optionData.mon);
                                            $('.performShift').text(optionData.zbc);
                                            drawImg(optionData);
                                      }else if(checkedText.indexOf('进港')>-1){
                                        var optionData = kpiData.fsummary[1][2][1];
                                        $('.summary-head').find('h3').text('最近7天汇总数据');
                                            $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                            $('.income').text(optionData.mon);
                                            $('.performShift').text(optionData.zbc);
                                            drawImg(optionData);
                                      } 
                                  }
                              }
                            }else if(summaryText=='包含甩飞'){
                                if(typeText =='所有航班'){
                                    if(checkedText.indexOf('最近7天')>-1){
                                        if(checkedText.indexOf('汇总')>-1){
                                          var optionData = kpiData.summary[0][0][2];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData); 
                                        }else if(checkedText.indexOf('出港')>-1){
                                          var optionData = kpiData.summary[0][1][2];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                        }else if(checkedText.indexOf('进港')>-1){
                                          var optionData = kpiData.summary[0][2][2];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }
                                    }else if(checkedText.indexOf('最近30天')>-1){
                                        if(checkedText.indexOf('汇总')>-1){
                                          var optionData = kpiData.summary[1][0][2];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);
                                        }else if(checkedText.indexOf('出港')>-1){
                                          var optionData = kpiData.summary[1][1][2];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }else if(checkedText.indexOf('进港')>-1){
                                          var optionData = kpiData.summary[1][2][2];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        } 
                                    }
                                }else if(typeText=='关注航班'){
                                    if(checkedText.indexOf('最近7天')>-1){
                                        if(checkedText.indexOf('汇总')>-1){
                                          var optionData = kpiData.fsummary[0][0][2];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }else if(checkedText.indexOf('出港')>-1){
                                          var optionData = kpiData.fsummary[0][1][2];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }else if(checkedText.indexOf('进港')>-1){
                                          var optionData = kpiData.fsummary[0][2][2];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }
                                    }else if(checkedText.indexOf('最近30天')>-1){
                                        if(checkedText.indexOf('汇总')>-1){
                                          var optionData = kpiData.fsummary[1][0][2];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }else if(checkedText.indexOf('出港')>-1){
                                          var optionData = kpiData.fsummary[1][1][2];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }else if(checkedText.indexOf('进港')>-1){
                                          var optionData = kpiData.fsummary[1][2][2];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        } 
                                    }
                                }
                            }else{
                                if(typeText =='所有航班'){
                                    if(checkedText.indexOf('最近7天')>-1){
                                        if(checkedText.indexOf('汇总')>-1){
                                          var optionData = kpiData.summary[0][0][0];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData); 
                                        }else if(checkedText.indexOf('出港')>-1){
                                          var optionData = kpiData.summary[0][1][0];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }else if(checkedText.indexOf('进港')>-1){
                                          var optionData = kpiData.summary[0][2][0];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }
                                    }else if(checkedText.indexOf('最近30天')>-1){
                                        if(checkedText.indexOf('汇总')>-1){
                                          var optionData = kpiData.summary[1][0][0];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }else if(checkedText.indexOf('出港')>-1){
                                          var optionData = kpiData.summary[1][1][0];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }else if(checkedText.indexOf('进港')>-1){
                                          var optionData = kpiData.summary[1][2][0];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        } 
                                    }
                                }else if(typeText=='关注航班'){
                                    if(checkedText.indexOf('最近7天')>-1){
                                        if(checkedText.indexOf('汇总')>-1){
                                          var optionData = kpiData.fsummary[0][0][0];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }else if(checkedText.indexOf('出港')>-1){
                                          var optionData = kpiData.fsummary[0][1][0];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }else if(checkedText.indexOf('进港')>-1){
                                          var optionData = kpiData.fsummary[0][2][0];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }
                                    }else if(checkedText.indexOf('最近30天')>-1){
                                        if(checkedText.indexOf('汇总')>-1){
                                          var optionData = kpiData.fsummary[1][0][0];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }else if(checkedText.indexOf('出港')>-1){
                                          var optionData = kpiData.fsummary[1][1][0];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        }else if(checkedText.indexOf('进港')>-1){
                                          var optionData = kpiData.fsummary[1][2][0];
                                          $('.summary-head').find('h3').text('最近7天汇总数据');
                                          $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                          $('.income').text(optionData.mon);
                                          $('.performShift').text(optionData.zbc);
                                          drawImg(optionData);  
                                        } 
                                    }
                                }
                            }
                      }else{
                          if(typeText =='所有航班'){
                              if(checkedText.indexOf('最近7天')>-1){
                                  if(checkedText.indexOf('汇总')>-1){
                                    var optionData = kpiData.summary[0][0][3];
                                    $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);  
                                  }else if(checkedText.indexOf('出港')>-1){
                                    var optionData = kpiData.summary[0][1][3];
                                    $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);  
                                  }else if(checkedText.indexOf('进港')>-1){
                                    var optionData = kpiData.summary[0][2][3];
                                      $('.summary-head').find('h3').text('最近7天汇总数据');
                                      $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                      $('.income').text(optionData.mon);
                                      $('.performShift').text(optionData.zbc);
                                      drawImg(optionData);  
                                  }
                              }else if(checkedText.indexOf('最近30天')>-1){
                                  if(checkedText.indexOf('汇总')>-1){
                                    var optionData = kpiData.summary[1][0][3];
                                      $('.summary-head').find('h3').text('最近7天汇总数据');
                                      $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                      $('.income').text(optionData.mon);
                                      $('.performShift').text(optionData.zbc);
                                      drawImg(optionData);  
                                  }else if(checkedText.indexOf('出港')>-1){
                                    var optionData = kpiData.summary[1][1][3];
                                      $('.summary-head').find('h3').text('最近7天汇总数据');
                                      $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                      $('.income').text(optionData.mon);
                                      $('.performShift').text(optionData.zbc);
                                      drawImg(optionData);  
                                  }else if(checkedText.indexOf('进港')>-1){
                                    var optionData = kpiData.summary[1][2][3];
                                      $('.summary-head').find('h3').text('最近7天汇总数据');
                                      $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                      $('.income').text(optionData.mon);
                                      $('.performShift').text(optionData.zbc);
                                      drawImg(optionData);  
                                  } 
                              }
                          }else if(typeText=='关注航班'){
                              if(checkedText.indexOf('最近7天')>-1){
                                  if(checkedText.indexOf('汇总')>-1){
                                    var optionData = kpiData.fsummary[0][0][3];
                                      $('.summary-head').find('h3').text('最近7天汇总数据');
                                      $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                      $('.income').text(optionData.mon);
                                      $('.performShift').text(optionData.zbc);
                                      drawImg(optionData);
                                  }else if(checkedText.indexOf('出港')>-1){
                                    var optionData = kpiData.fsummary[0][1][3];
                                    $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);  
                                  }else if(checkedText.indexOf('进港')>-1){
                                    var optionData = kpiData.fsummary[0][2][3];
                                    $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);  
                                  }
                              }else if(checkedText.indexOf('最近30天')>-1){
                                  if(checkedText.indexOf('汇总')>-1){
                                    var optionData = kpiData.fsummary[1][0][3];
                                    $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);  
                                  }else if(checkedText.indexOf('出港')>-1){
                                    var optionData = kpiData.fsummary[1][1][3];
                                    $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);  
                                  }else if(checkedText.indexOf('进港')>-1){
                                    var optionData = kpiData.fsummary[1][2][3];
                                    $('.summary-head').find('h3').text('最近7天汇总数据');
                                        $('.guestAmount').html(optionData.people[0]+"<span>（散："+optionData.people[2]+"/团："+optionData.people[1]+"）</span>");
                                        $('.income').text(optionData.mon);
                                        $('.performShift').text(optionData.zbc);
                                        drawImg(optionData);  
                                  } 
                              }
                          }
                      }
                  }
                  double = clickFlag;
              }else{
                  double = clickFlag;
                  return;
              }
              
         });
          $(".pla-ranking").on("click","div",function(){
              var checkedText = $('.result-acts').text();
              if($(this).hasClass("top-ten")){
                  $(this).addClass("pla-ranking-tag").siblings().removeClass("pla-ranking-tag");
                  if(checkedText.indexOf("最近1天")>-1){
                      if(checkedText.indexOf('汇总')>-1){
                          ultimately=kpiData.topTen[0][0];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }else if(checkedText.indexOf('出港')>-1){
                          ultimately=kpiData.topTen[0][1];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }else if(checkedText.indexOf('进港')>-1){
                          ultimately=kpiData.topTen[0][2];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }
                  }else if(checkedText.indexOf("最近7天")>-1){
                      if(checkedText.indexOf('汇总')>-1){
                          ultimately=kpiData.topTen[1][0];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }else if(checkedText.indexOf('出港')>-1){
                          ultimately=kpiData.topTen[1][1];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }else if(checkedText.indexOf('进港')>-1){
                          ultimately=kpiData.topTen[1][2];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }
                  }else if(checkedText.indexOf('最近30天')>-1){
                      if(checkedText.indexOf('汇总')>-1){
                          ultimately=kpiData.topTen[2][0];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }else if(checkedText.indexOf('出港')>-1){
                          ultimately=kpiData.topTen[2][1];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }else if(checkedText.indexOf('进港')>-1){
                          ultimately=kpiData.topTen[2][2];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }
                  }
              }else if($(this).hasClass("after-ten")){
                  $(this).addClass("pla-ranking-tag").siblings().removeClass("pla-ranking-tag");
                  if(checkedText.indexOf("最近1天")>-1){
                      if(checkedText.indexOf('汇总')>-1){
                          ultimately=kpiData.afterTen[0][0];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }else if(checkedText.indexOf('出港')>-1){
                          ultimately=kpiData.afterTen[0][1];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }else if(checkedText.indexOf('进港')>-1){
                          ultimately=kpiData.afterTen[0][2];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }
                  }else if(checkedText.indexOf("最近7天")>-1){
                      if(checkedText.indexOf('汇总')>-1){
                          ultimately=kpiData.afterTen[1][0];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }else if(checkedText.indexOf('出港')>-1){
                          ultimately=kpiData.afterTen[1][1];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }else if(checkedText.indexOf('进港')>-1){
                          ultimately=kpiData.afterTen[1][2];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }
                  }else if(checkedText.indexOf('最近30天')>-1){
                      if(checkedText.indexOf('汇总')>-1){
                          ultimately=kpiData.afterTen[2][0];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }else if(checkedText.indexOf('出港')>-1){
                          ultimately=kpiData.afterTen[2][1];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }else if(checkedText.indexOf('进港')>-1){
                          ultimately=kpiData.afterTen[2][2];
                          for(var i=0;i<$(".pla-ranking-title-nav>ul>li").length;i++){
                              if($(".pla-ranking-title-nav>ul>li").eq(i).hasClass("sets")){
                                  $(".pla-ranking-title-nav>ul>li").eq(i).click();
                                  break;
                              }
                          }
                      }
                  }
              }else if($(this).hasClass("pla-ranking-shut")){
                  $(".pla-ranking").css({"display":"none"});
              }
          });
          function drawing(fig,va){
              var dom = document.getElementById('pla-ranking-box');
              //用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
              var myChart = echarts.init(dom);
              var option = {
                  grid: {
                      left: '0%',
                      right: '10%',
                      bottom: '10%',
                      top:"15%",
                      containLabel: true
                  },
                  tooltip: {
                      trigger: 'axis',
                      axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                          type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                      }
                  },
                  toolbox: {
                      show: false
                  },
                  xAxis:  {
                      type: 'category',
                      boundaryGap: true,
                      data: fig,
                      axisTick: {
                          alignWithLabel: false
                      },
                      axisLine:{
                          show:true,
                          lineStyle:{
                              color:'#475367'
                          }
                      },
                      splitLine:{
                          show:true,
                          lineStyle:{
                              color:'#1a2b4b'
                          }
                      },
                      axisLabel:{
                          interval:0,
                          rotate:45,
                          margin:10,
                          textStyle:{
                                 color:"white"
                           }
                      }
                  },
                  yAxis: {
                      show:true,
                      type: 'value',
                      boundaryGap: ['0%', '20%'],
                      splitLine:{
                          show:false
                      },
                      axisLabel: {
                          formatter: '{value}',
                          textStyle:{
                                   color:"white"
                             }
                      },
                      axisLine:{
                          show:true,
                          lineStyle:{
                              color:'#475367'
                          }
                      },
                      axisTick:{
                          show:true
                      }
                  },
                  series: [
                      {
                          name:names,
                          type:'bar',
                          stack:'total',
                          label:{
                              normal:{
                                  show: false,
                                  position:'top'
                              }
                          },
                          barWidth: 10,
                          itemStyle:{
                              normal:{
                                  color: new echarts.graphic.LinearGradient(
                                          0, 0, 0, 1,
                                          [
                                              {offset: 0, color: '#62c5d8'},
                                              {offset: 0.5, color: '#4b5cb6'},
                                              {offset: 1, color: '#4b5cb6'}
                                          ]
                                      ),
                                  label : {show: true, position: 'top',textStyle: {color: '#fff'}}
                              }
                          },
                          data:va
                      }
                  ]
              };
              myChart.setOption(option,true);
          };
          $(".pla-btn-flight").on("click",function(event){ //summary.people["10000","8000","2000"]
                event.stopPropagation();
              $(".pla-ranking").html(" <div class='pla-ranking-title'><div class='pla-ranking-title-head'><h3>航班排行</h3>" +
                      "<div class='top-ten pla-ranking-tag'>前10</div> <div class='after-ten'>后10</div> </div>" +
                      "<div class='pla-ranking-title-nav'> <ul> <li id='income'>收入</li> <li id='traffic'>客流量</li>" +
                      "<li id='proportion'>客座率</li> <li id='quasi'>准点率</li> </ul> </div> </div><div id='flight-box'>" +
                      "<ul class='flight-farst'><li id='flight-yesterday' class='result-act result-acts'>最近1天</li>" +
                      "<li id='flight-sevenDay' class='result-act'>最近7天</li><li id='flight-thirtyDay' class='result-act'>最近30天</li>" +
                      "</ul><ul class='flight-last'><li id='flight-summary' class='result-act result-acts'>汇总</li>" +
                      "<li id='flight-clearance' class='result-act'>出港</li><li id='flight-putIn' class='result-act'>进港</li>" +
                      "</ul></div> <div class='pla-ranking-box' id='pla-ranking-box'></div><div class='pla-ranking-shut'>&#xe612;</div>").css("display","block");
              $(".pla-ranking #income").click();//默认加载前十的收入
          });
          
          $(".pla-btn-trend").on("click",function(event){
                event.stopPropagation();
              //关闭仪表盘弹出框
              $('.material-fact-installBox').css('display','none');
              var summary = kpiData.summary[0][0][0];
              $(".pla-ranking").html("<div class='pla-ranking-title'><div class='pla-ranking-title-head summary-head'>" +
                      "<h3>最近7天汇总数据</h3></div><div class='pla-ranking-title-nav'><div class='summary'> <p>客量（人）</p>" +
                      "<div class='guestAmount'>"+summary.people[0]+"<span>（散："+summary.people[2]+"/团："+summary.people[1]+"）</span>" +
                      "</div></div><div class='summary'><p>客票收入（元）</p><div class='income'>"+summary.mon+"</div>" +
                      "</div><div class='summary'><p>执行班次（班）</p><div class='performShift'>"+summary.zbc+"</div></div>" +
                      "</div></div><div id='collect-box'><ul class='flight-radios'><li><label class='labels labels-set'>" +
                      "<input class='labels-radios' type='radio' name='collect-radio' checked='checked' class='collect-select'>" +
                      "</label><span>所有航班</span></li><li><label class='labels'><input type='radio' name='collect-radio' checked='checked' class='collect-select'>" +
                      "</label><span>关注航班</span></li></ul><ul class='flight-datarange'><li><label class='datarange label-set'>" +
                      "<input class='labels-radios' type='radio' name='collect-radio' checked='checked' class='collect-select'>" +
                      "</label><span>包含过站</span></li><li><label class='datarange label-set'><input type='radio' name='collect-radio' checked='checked' class='collect-select'>" +
                      "</label><span>包含甩飞</span></li></ul><ul class='flight-farst'><li class='result-act result-acts'>最近7天</li>" +
                      "<li class='result-act'>最近30天</li></ul><ul class='flight-last'><li class='result-act result-acts'>汇总</li>" +
                      "<li class='result-act'>出港</li><li class='result-act'>进港</li></ul></div><div class='pla-ranking-box' id='pla-ranking-box'>" +
                      "</div><div class='pla-ranking-shut'>&#xe612;</div>").css("display","block");//数据区分甩飞和过站
              drawImg(summary);
          });
          //延时访问数据
          setTimeout('getAirportKPIData()', 200);
        //绑定上传背景图片方法
          $('body').on('change','#imgBg',function(){
              uploadBackground();
          });
          //绑定上传头像方法
          $('body').on('change','#phBg',function(){
              uploadHead();
          });
    });
    
    
    function getAirportKPIData(){
        $.ajax({
            type:'get',
            url : '/getAirportKPIData',
            dataType : 'jsonp',
            success : function(datas) {
               if(datas!=null&&datas.success!=null&&datas.success.length>0){
                   
                   var topTenList = new Array(), afterTenList = new Array(), summaryList = new Array(), fsummaryList = new Array();
                   for(var i=0;i<datas.success.length;i++){
                       var data=datas.success[i];
                       typeof(data)
                       if(!data)break;
                       var summaryArray = new Array(), fsummaryArray = new Array();
                       if(i!=0){
                           //所有航班数据
                           var summarys = new Array(), fsummarys = new Array(), summary = {};//重置对象
                           //汇总-所有
                           var person = new Array();
                           person[0] = data.zkl;
                           person[1] = data.ztd;
                           person[2] = data.zsk;
                           summary["people"]=person;
                           summary["mon"]=data.zsr;
                           summary["zbc"]=data.zbc;
                           //取最近7天数据集合
                           var zsdata = data.zsData;
                           var time = new Array();
                           var separateR = new Array();
                           var separateM = new Array();
                           for(var a=0; a<zsdata.length;a++){
                               time[a] = zsdata[a].date;
                               separateR[a] = zsdata[a].persons;
                               separateM[a] = zsdata[a].zsr;
                           }
                           summary["time"]=time;
                           summary["separateR"]=separateR;
                           summary["separateM"]=separateM;
                           summarys.push(summary);
                           summary = {};//重置对象
                           //汇总-含过站
                           var person = new Array();
                           person[0] = data.pzkl;
                           person[1] = data.pztd;
                           person[2] = data.pzsk;
                           summary["people"]=person;
                           summary["mon"]=data.pzsr;
                           summary["zbc"]=data.pzbc;
                           //取最近7天数据集合
                           var zsdata = data.pzsData;
                           var time = new Array();
                           var separateR = new Array();
                           var separateM = new Array();
                           for(var a=0; a<zsdata.length;a++){
                               time[a] = zsdata[a].date;
                               separateR[a] = zsdata[a].persons;
                               separateM[a] = zsdata[a].zsr;
                           }
                           summary["time"]=time;
                           summary["separateR"]=separateR;
                           summary["separateM"]=separateM;
                           summarys.push(summary);
                           summary = {};//重置对象
                           //汇总-含甩飞
                           var person = new Array();
                           person[0] = data.tzkl;
                           person[1] = data.tztd;
                           person[2] = data.tzsk;
                           summary["people"]=person;
                           summary["mon"]=data.tzsr;
                           summary["zbc"]=data.tzbc;
                           //取最近7天数据集合
                           var zsdata = data.tzsData;
                           var time = new Array();
                           var separateR = new Array();
                           var separateM = new Array();
                           for(var a=0; a<zsdata.length;a++){
                               time[a] = zsdata[a].date;
                               separateR[a] = zsdata[a].persons;
                               separateM[a] = zsdata[a].zsr;
                           }
                           summary["time"]=time;
                           summary["separateR"]=separateR;
                           summary["separateM"]=separateM;
                           summarys.push(summary);
                           summary = {};//重置对象
                           //汇总-不含过站与甩飞
                           var person = new Array();
                           person[0] = data.nzkl;
                           person[1] = data.nztd;
                           person[2] = data.nzsk;
                           summary["people"]=person;
                           summary["mon"]=data.nzsr;
                           summary["zbc"]=data.nzbc;
                           //取最近7天数据集合
                           var zsdata = data.nzsData;
                           var time = new Array();
                           var separateR = new Array();
                           var separateM = new Array();
                           for(var a=0; a<zsdata.length;a++){
                               time[a] = zsdata[a].date;
                               separateR[a] = zsdata[a].persons;
                               separateM[a] = zsdata[a].zsr;
                           }
                           summary["time"]=time;
                           summary["separateR"]=separateR;
                           summary["separateM"]=separateM;
                           summarys.push(summary);
                           summaryArray.push(summarys);
                           summarys = new Array();
                           summary = {};//重置对象
                           //出港-所有
                           person = new Array();
                           person[0] = data.czkl;
                           person[1] = data.cztd;
                           person[2] = data.czsk;
                           summary["people"]=person;
                           summary["mon"]=data.czsr;
                           summary["zbc"]=data.czbc;
                           //取最近7天数据集合
                           zsdata = data.czsData;
                           time = new Array();
                           separateR = new Array();
                           separateM = new Array();
                           for(var a=0; a<zsdata.length;a++){
                               time[a] = zsdata[a].date;
                               separateR[a] = zsdata[a].persons;
                               separateM[a] = zsdata[a].zsr;
                           }
                           summary["time"]=time;
                           summary["separateR"]=separateR;
                           summary["separateM"]=separateM;
                           summarys.push(summary);
                           summary = {};//重置对象
                           //出港-含过站
                           person = new Array();
                           person[0] = data.cpzkl;
                           person[1] = data.cpztd;
                           person[2] = data.cpzsk;
                           summary["people"]=person;
                           summary["mon"]=data.cpzsr;
                           summary["zbc"]=data.cpzbc;
                           //取最近7天数据集合
                           zsdata = data.cpzsData;
                           time = new Array();
                           separateR = new Array();
                           separateM = new Array();
                           for(var a=0; a<zsdata.length;a++){
                               time[a] = zsdata[a].date;
                               separateR[a] = zsdata[a].persons;
                               separateM[a] = zsdata[a].zsr;
                           }
                           summary["time"]=time;
                           summary["separateR"]=separateR;
                           summary["separateM"]=separateM;
                           summarys.push(summary);
                           summary = {};//重置对象
                           //出港-含甩飞
                           person = new Array();
                           person[0] = data.ctzkl;
                           person[1] = data.ctztd;
                           person[2] = data.ctzsk;
                           summary["people"]=person;
                           summary["mon"]=data.ctzsr;
                           summary["zbc"]=data.ctzbc;
                           //取最近7天数据集合
                           zsdata = data.ctzsData;
                           time = new Array();
                           separateR = new Array();
                           separateM = new Array();
                           for(var a=0; a<zsdata.length;a++){
                               time[a] = zsdata[a].date;
                               separateR[a] = zsdata[a].persons;
                               separateM[a] = zsdata[a].zsr;
                           }
                           summary["time"]=time;
                           summary["separateR"]=separateR;
                           summary["separateM"]=separateM;
                           summarys.push(summary);
                           summary = {};//重置对象
                           //出港-不含过站与甩飞
                           person = new Array();
                           person[0] = data.cnzkl;
                           person[1] = data.cnztd;
                           person[2] = data.cnzsk;
                           summary["people"]=person;
                           summary["mon"]=data.cnzsr;
                           summary["zbc"]=data.cnzbc;
                           //取最近7天数据集合
                           zsdata = data.cnzsData;
                           time = new Array();
                           separateR = new Array();
                           separateM = new Array();
                           for(var a=0; a<zsdata.length;a++){
                               time[a] = zsdata[a].date;
                               separateR[a] = zsdata[a].persons;
                               separateM[a] = zsdata[a].zsr;
                           }
                           summary["time"]=time;
                           summary["separateR"]=separateR;
                           summary["separateM"]=separateM;
                           summarys.push(summary);
                           summaryArray.push(summarys);
                           summarys = new Array();
                           summary = {};//重置对象
                           //进港-所有
                           person = new Array();
                           person[0] = data.jzkl;
                           person[1] = data.jztd;
                           person[2] = data.jzsk;
                           summary["people"]=person;
                           summary["mon"]=data.jzsr;
                           summary["zbc"]=data.jzbc;
                           //取最近7天数据集合
                           zsdata = data.jzsData;
                           time = new Array();
                           separateR = new Array();
                           separateM = new Array();
                           for(var a=0; a<zsdata.length;a++){
                               time[a] = zsdata[a].date;
                               separateR[a] = zsdata[a].persons;
                               separateM[a] = zsdata[a].zsr;
                           }
                           summary["time"]=time;
                           summary["separateR"]=separateR;
                           summary["separateM"]=separateM;
                           summarys.push(summary);
                           summary = {};//重置对象
                           //进港-含过站
                           person = new Array();
                           person[0] = data.jpzkl;
                           person[1] = data.jpztd;
                           person[2] = data.jpzsk;
                           summary["people"]=person;
                           summary["mon"]=data.jpzsr;
                           summary["zbc"]=data.jpzbc;
                           //取最近7天数据集合
                           zsdata = data.jpzsData;
                           time = new Array();
                           separateR = new Array();
                           separateM = new Array();
                           for(var a=0; a<zsdata.length;a++){
                               time[a] = zsdata[a].date;
                               separateR[a] = zsdata[a].persons;
                               separateM[a] = zsdata[a].zsr;
                           }
                           summary["time"]=time;
                           summary["separateR"]=separateR;
                           summary["separateM"]=separateM;
                           summarys.push(summary);
                           summary = {};//重置对象
                           //进港-含甩飞
                           person = new Array();
                           person[0] = data.jtzkl;
                           person[1] = data.jtztd;
                           person[2] = data.jtzsk;
                           summary["people"]=person;
                           summary["mon"]=data.jtzsr;
                           summary["zbc"]=data.jtzbc;
                           //取最近7天数据集合
                           zsdata = data.jtzsData;
                           time = new Array();
                           separateR = new Array();
                           separateM = new Array();
                           for(var a=0; a<zsdata.length;a++){
                               time[a] = zsdata[a].date;
                               separateR[a] = zsdata[a].persons;
                               separateM[a] = zsdata[a].zsr;
                           }
                           summary["time"]=time;
                           summary["separateR"]=separateR;
                           summary["separateM"]=separateM;
                           summarys.push(summary);
                           summary = {};//重置对象
                           //进港-不含过站与甩飞
                           person = new Array();
                           person[0] = data.jnzkl;
                           person[1] = data.jnztd;
                           person[2] = data.jnzsk;
                           summary["people"]=person;
                           summary["mon"]=data.jnzsr;
                           summary["zbc"]=data.jnzbc;
                           //取最近7天数据集合
                           zsdata = data.jnzsData;
                           time = new Array();
                           separateR = new Array();
                           separateM = new Array();
                           for(var a=0; a<zsdata.length;a++){
                               time[a] = zsdata[a].date;
                               separateR[a] = zsdata[a].persons;
                               separateM[a] = zsdata[a].zsr;
                           }
                           summary["time"]=time;
                           summary["separateR"]=separateR;
                           summary["separateM"]=separateM;
                           summarys.push(summary);
                           summaryArray.push(summarys);
                           summaryList.push(summaryArray);
                           //关注航班
                           var focusFlt = kpiData["focusFlt"]= data.focusFlt;//关注航班列表
                           if(focusFlt!=null&&focusFlt.length>0){
                               var fsummary = {};//重置对象
                              //汇总-所有
                              var fperson = new Array();
                              fperson[0] = data.fzkl;
                              fperson[1] = data.fztd;
                              fperson[2] = data.fzsk;
                              fsummary["people"]=fperson;
                              fsummary["mon"]=data.fzsr;
                              fsummary["zbc"]=data.fzbc;
                              //取汇总数据集合
                              var fzsdata = data.fzsData;
                              var ftime = new Array();
                              var fseparateR = new Array();
                              var fseparateM = new Array();
                              for(var a=0; a<fzsdata.length;a++){
                                  ftime[a] = fzsdata[a].date;
                                  fseparateR[a] = fzsdata[a].persons;
                                  fseparateM[a] = fzsdata[a].zsr;
                              }
                              fsummary["time"]=ftime;
                              fsummary["separateR"]=fseparateR;
                              fsummary["separateM"]=fseparateM;
                              fsummarys.push(fsummary);
                              fsummary = {};//重置对象
                              //汇总-含过站
                              var fperson = new Array();
                              fperson[0] = data.fpzkl;
                              fperson[1] = data.fpztd;
                              fperson[2] = data.fpzsk;
                              fsummary["people"]=fperson;
                              fsummary["mon"]=data.fpzsr;
                              fsummary["zbc"]=data.fpzbc;
                              //取汇总数据集合
                              var fzsdata = data.fpzsData;
                              var ftime = new Array();
                              var fseparateR = new Array();
                              var fseparateM = new Array();
                              for(var a=0; a<fzsdata.length;a++){
                                  ftime[a] = fzsdata[a].date;
                                  fseparateR[a] = fzsdata[a].persons;
                                  fseparateM[a] = fzsdata[a].zsr;
                              }
                              fsummary["time"]=ftime;
                              fsummary["separateR"]=fseparateR;
                              fsummary["separateM"]=fseparateM;
                              fsummarys.push(fsummary);
                              fsummary = {};//重置对象
                              //汇总-含甩飞
                              var fperson = new Array();
                              fperson[0] = data.ftzkl;
                              fperson[1] = data.ftztd;
                              fperson[2] = data.ftzsk;
                              fsummary["people"]=fperson;
                              fsummary["mon"]=data.ftzsr;
                              fsummary["zbc"]=data.ftzbc;
                              //取汇总数据集合
                              var fzsdata = data.ftzsData;
                              var ftime = new Array();
                              var fseparateR = new Array();
                              var fseparateM = new Array();
                              for(var a=0; a<fzsdata.length;a++){
                                  ftime[a] = fzsdata[a].date;
                                  fseparateR[a] = fzsdata[a].persons;
                                  fseparateM[a] = fzsdata[a].zsr;
                              }
                              fsummary["time"]=ftime;
                              fsummary["separateR"]=fseparateR;
                              fsummary["separateM"]=fseparateM;
                              fsummarys.push(fsummary);
                              fsummary = {};//重置对象
                              //汇总-不含过站与甩飞
                              var fperson = new Array();
                              fperson[0] = data.fnzkl;
                              fperson[1] = data.fnztd;
                              fperson[2] = data.fnzsk;
                              fsummary["people"]=fperson;
                              fsummary["mon"]=data.fnzsr;
                              fsummary["zbc"]=data.fnzbc;
                              //取汇总数据集合
                              var fzsdata = data.fnzsData;
                              var ftime = new Array();
                              var fseparateR = new Array();
                              var fseparateM = new Array();
                              for(var a=0; a<fzsdata.length;a++){
                                  ftime[a] = fzsdata[a].date;
                                  fseparateR[a] = fzsdata[a].persons;
                                  fseparateM[a] = fzsdata[a].zsr;
                              }
                              fsummary["time"]=ftime;
                              fsummary["separateR"]=fseparateR;
                              fsummary["separateM"]=fseparateM;
                              fsummarys.push(fsummary);
                              fsummaryArray.push(fsummarys);
                              fsummarys = new Array();
                              fsummary = {};//重置对象
                              //出港-所有
                              fperson = new Array();
                              fperson[0] = data.cfzkl;
                              fperson[1] = data.cfztd;
                              fperson[2] = data.cfzsk;
                              fsummary["people"]=fperson;
                              fsummary["mon"]=data.cfzsr;
                              fsummary["zbc"]=data.cfzbc;
                              //取出港数据集合
                              fzsdata = data.cfzsData;
                              ftime = new Array();
                              fseparateR = new Array();
                              fseparateM = new Array();
                              for(var a=0; a<fzsdata.length;a++){
                                  ftime[a] = fzsdata[a].date;
                                  fseparateR[a] = fzsdata[a].persons;
                                  fseparateM[a] = fzsdata[a].zsr;
                              }
                              fsummary["time"]=ftime;
                              fsummary["separateR"]=fseparateR;
                              fsummary["separateM"]=fseparateM;
                              fsummarys.push(fsummary);
                              fsummary = {};//重置对象
                              //出港-含过站
                              fperson = new Array();
                              fperson[0] = data.cfpzkl;
                              fperson[1] = data.cfpztd;
                              fperson[2] = data.cfpzsk;
                              fsummary["people"]=fperson;
                              fsummary["mon"]=data.cfpzsr;
                              fsummary["zbc"]=data.cfpzbc;
                              //取出港数据集合
                              fzsdata = data.cfpzsData;
                              ftime = new Array();
                              fseparateR = new Array();
                              fseparateM = new Array();
                              for(var a=0; a<fzsdata.length;a++){
                                  ftime[a] = fzsdata[a].date;
                                  fseparateR[a] = fzsdata[a].persons;
                                  fseparateM[a] = fzsdata[a].zsr;
                              }
                              fsummary["time"]=ftime;
                              fsummary["separateR"]=fseparateR;
                              fsummary["separateM"]=fseparateM;
                              fsummarys.push(fsummary);
                              fsummary = {};//重置对象
                              //出港-含甩飞
                              fperson = new Array();
                              fperson[0] = data.cftzkl;
                              fperson[1] = data.cftztd;
                              fperson[2] = data.cftzsk;
                              fsummary["people"]=fperson;
                              fsummary["mon"]=data.cftzsr;
                              fsummary["zbc"]=data.cftzbc;
                              //取出港数据集合
                              fzsdata = data.cftzsData;
                              ftime = new Array();
                              fseparateR = new Array();
                              fseparateM = new Array();
                              for(var a=0; a<fzsdata.length;a++){
                                  ftime[a] = fzsdata[a].date;
                                  fseparateR[a] = fzsdata[a].persons;
                                  fseparateM[a] = fzsdata[a].zsr;
                              }
                              fsummary["time"]=ftime;
                              fsummary["separateR"]=fseparateR;
                              fsummary["separateM"]=fseparateM;
                              fsummarys.push(fsummary);
                              fsummary = {};//重置对象
                              //出港-不含过站与甩飞
                              fperson = new Array();
                              fperson[0] = data.cfnzkl;
                              fperson[1] = data.cfnztd;
                              fperson[2] = data.cfnzsk;
                              fsummary["people"]=fperson;
                              fsummary["mon"]=data.cfnzsr;
                              fsummary["zbc"]=data.cfnzbc;
                              //取出港数据集合
                              fzsdata = data.cfnzsData;
                              ftime = new Array();
                              fseparateR = new Array();
                              fseparateM = new Array();
                              for(var a=0; a<fzsdata.length;a++){
                                  ftime[a] = fzsdata[a].date;
                                  fseparateR[a] = fzsdata[a].persons;
                                  fseparateM[a] = fzsdata[a].zsr;
                              }
                              fsummary["time"]=ftime;
                              fsummary["separateR"]=fseparateR;
                              fsummary["separateM"]=fseparateM;
                              fsummarys.push(fsummary);
                              fsummaryArray.push(fsummarys);
                              fsummarys = new Array();
                              fsummary = {};//重置对象
                              //进港-所有
                              fperson = new Array();
                              fperson[0] = data.jfzkl;
                              fperson[1] = data.jfztd;
                              fperson[2] = data.jfzsk;
                              fsummary["people"]=fperson;
                              fsummary["mon"]=data.jfzsr;
                              fsummary["zbc"]=data.jfzbc;
                              //取进港数据集合
                              fzsdata = data.jfzsData;
                              ftime = new Array();
                              fseparateR = new Array();
                              fseparateM = new Array();
                              for(var a=0; a<fzsdata.length;a++){
                                  ftime[a] = fzsdata[a].date;
                                  fseparateR[a] = fzsdata[a].persons;
                                  fseparateM[a] = fzsdata[a].zsr;
                              }
                              fsummary["time"]=ftime;
                              fsummary["separateR"]=fseparateR;
                              fsummary["separateM"]=fseparateM;
                              fsummarys.push(fsummary);
                              //进港-含过站
                              fperson = new Array();
                              fperson[0] = data.jfpzkl;
                              fperson[1] = data.jfpztd;
                              fperson[2] = data.jfpzsk;
                              fsummary["people"]=fperson;
                              fsummary["mon"]=data.jfpzsr;
                              fsummary["zbc"]=data.jfpzbc;
                              //取进港数据集合
                              fzsdata = data.jfpzsData;
                              ftime = new Array();
                              fseparateR = new Array();
                              fseparateM = new Array();
                              for(var a=0; a<fzsdata.length;a++){
                                  ftime[a] = fzsdata[a].date;
                                  fseparateR[a] = fzsdata[a].persons;
                                  fseparateM[a] = fzsdata[a].zsr;
                              }
                              fsummary["time"]=ftime;
                              fsummary["separateR"]=fseparateR;
                              fsummary["separateM"]=fseparateM;
                              fsummarys.push(fsummary);
                              //进港-含甩飞
                              fperson = new Array();
                              fperson[0] = data.jftzkl;
                              fperson[1] = data.jftztd;
                              fperson[2] = data.jftzsk;
                              fsummary["people"]=fperson;
                              fsummary["mon"]=data.jftzsr;
                              fsummary["zbc"]=data.jftzbc;
                              //取进港数据集合
                              fzsdata = data.jftzsData;
                              ftime = new Array();
                              fseparateR = new Array();
                              fseparateM = new Array();
                              for(var a=0; a<fzsdata.length;a++){
                                  ftime[a] = fzsdata[a].date;
                                  fseparateR[a] = fzsdata[a].persons;
                                  fseparateM[a] = fzsdata[a].zsr;
                              }
                              fsummary["time"]=ftime;
                              fsummary["separateR"]=fseparateR;
                              fsummary["separateM"]=fseparateM;
                              fsummarys.push(fsummary);
                              //进港-不含过站与甩飞
                              fperson = new Array();
                              fperson[0] = data.jfnzkl;
                              fperson[1] = data.jfnztd;
                              fperson[2] = data.jfnzsk;
                              fsummary["people"]=fperson;
                              fsummary["mon"]=data.jfnzsr;
                              fsummary["zbc"]=data.jfnzbc;
                              //取进港数据集合
                              fzsdata = data.jfnzsData;
                              ftime = new Array();
                              fseparateR = new Array();
                              fseparateM = new Array();
                              for(var a=0; a<fzsdata.length;a++){
                                  ftime[a] = fzsdata[a].date;
                                  fseparateR[a] = fzsdata[a].persons;
                                  fseparateM[a] = fzsdata[a].zsr;
                              }
                              fsummary["time"]=ftime;
                              fsummary["separateR"]=fseparateR;
                              fsummary["separateM"]=fseparateM;
                              fsummarys.push(fsummary);
                              fsummaryArray.push(fsummarys);
                              fsummaryList.push(fsummaryArray);
                           }
                       }
                       var toptenArray = new Array();
                       var aftertenArray = new Array();
                       var topTen = {};//重置对象
                       var afterTen = {};//重置对象
                       //汇总
                       var topten_zsr = data.topten_zsr;
                       var topten_kll = data.topten_kll;
                       var topten_kzl = data.topten_kzl;
                       var topten_zdl = data.topten_zdl;
                       var dataa1 = new Array();
                       for(var a=0; a<topten_zsr.length;a++){
                           var temArr = new Array();
                           temArr[0] = topten_zsr[a].zsr;
                           temArr[1] = topten_zsr[a].flyNum;
                           dataa1[a] = temArr;
                       }
                       topTen["income"] = dataa1;
                       var dataa2 = new Array();
                       for(var a=0; a<topten_kll.length;a++){
                           var temArr = new Array();
                           temArr[0] = topten_kll[a].persons;
                           temArr[1] = topten_kll[a].flyNum;
                           dataa2[a] = temArr;
                       }
                       topTen["traffic"] = dataa2;
                       var dataa3 = new Array();
                       for(var a=0; a<topten_kzl.length;a++){
                           var temArr = new Array();
                           temArr[0] = topten_kzl[a].kzl;
                           temArr[1] = topten_kzl[a].flyNum;
                           dataa3[a] = temArr;
                       }
                       topTen["proportion"] = dataa3;
                       var dataa33 = new Array();
                       for(var a=0; a<topten_zdl.length;a++){
                           var temArr = new Array();
                           temArr[0] = topten_zdl[a].zdl;
                           temArr[1] = topten_zdl[a].flyNum;
                           dataa33[a] = temArr;
                       }	 
                       topTen["quasi"] = dataa33;
                       var afterten_zsr = data.afterten_zsr;
                       var afterten_kll = data.afterten_kll;
                       var afterten_kzl = data.afterten_kzl;
                       var afterten_zdl = data.afterten_zdl;
                       var dataa4 = new Array();
                       for(var a=0; a<afterten_zsr.length;a++){
                           var temArr = new Array();
                           temArr[0] = afterten_zsr[a].zsr;
                           temArr[1] = afterten_zsr[a].flyNum;
                           dataa4[a] = temArr;
                       }
                       afterTen["income"] = dataa4;
                       var dataa5 = new Array();
                       for(var a=0; a<afterten_kll.length;a++){
                           var temArr = new Array();
                           temArr[0] = afterten_kll[a].persons;
                           temArr[1] = afterten_kll[a].flyNum;
                           dataa5[a] = temArr;
                       }
                       afterTen["traffic"] = dataa5;
                       var dataa6 = new Array();
                       for(var a=0; a<afterten_kzl.length;a++){
                           var temArr = new Array();
                           temArr[0] = afterten_kzl[a].kzl;
                           temArr[1] = afterten_kzl[a].flyNum;
                           dataa6[a] = temArr;
                       }
                       afterTen["proportion"] = dataa6;
                       var dataa7 = new Array();
                       for(var a=0; a<afterten_zdl.length;a++){
                           var temArr = new Array();
                           temArr[0] = afterten_zdl[a].zdl;
                           temArr[1] = afterten_zdl[a].flyNum;
                           dataa7[a] = temArr;
                       }
                       afterTen["quasi"] = dataa7;
                       toptenArray.push(topTen);
                       aftertenArray.push(afterTen);
                       topTen = {};//重置对象
                       afterTen = {};//重置对象
                       //出港
                       topten_zsr = data.ctopten_zsr;
                       topten_kll = data.ctopten_kll;
                       topten_kzl = data.ctopten_kzl;
                       topten_zdl = data.ctopten_zdl;
                       dataa1 = new Array();
                       for(var a=0; a<topten_zsr.length;a++){
                           var temArr = new Array();
                           temArr[0] = topten_zsr[a].zsr;
                           temArr[1] = topten_zsr[a].flyNum;
                           dataa1[a] = temArr;
                       }
                       topTen["income"] = dataa1;
                       dataa2 = new Array();
                       for(var a=0; a<topten_kll.length;a++){
                           var temArr = new Array();
                           temArr[0] = topten_kll[a].persons;
                           temArr[1] = topten_kll[a].flyNum;
                           dataa2[a] = temArr;
                       }
                       topTen["traffic"] = dataa2;
                       dataa3 = new Array();
                       for(var a=0; a<topten_kzl.length;a++){
                           var temArr = new Array();
                           temArr[0] = topten_kzl[a].kzl;
                           temArr[1] = topten_kzl[a].flyNum;
                           dataa3[a] = temArr;
                       }
                       topTen["proportion"] = dataa3;
                       dataa33 = new Array();
                       for(var a=0; a<topten_zdl.length;a++){
                           var temArr = new Array();
                           temArr[0] = topten_zdl[a].zdl;
                           temArr[1] = topten_zdl[a].flyNum;
                           dataa33[a] = temArr;
                       }	 
                       topTen["quasi"] = dataa33;
                       afterten_zsr = data.cafterten_zsr;
                       afterten_kll = data.cafterten_kll;
                       afterten_kzl = data.cafterten_kzl;
                       afterten_zdl = data.cafterten_zdl;
                       dataa4 = new Array();
                       for(var a=0; a<afterten_zsr.length;a++){
                           var temArr = new Array();
                           temArr[0] = afterten_zsr[a].zsr;
                           temArr[1] = afterten_zsr[a].flyNum;
                           dataa4[a] = temArr;
                       }
                       afterTen["income"] = dataa4;
                       dataa5 = new Array();
                       for(var a=0; a<afterten_kll.length;a++){
                           var temArr = new Array();
                           temArr[0] = afterten_kll[a].persons;
                           temArr[1] = afterten_kll[a].flyNum;
                           dataa5[a] = temArr;
                       }
                       afterTen["traffic"] = dataa5;
                       dataa6 = new Array();
                       for(var a=0; a<afterten_kzl.length;a++){
                           var temArr = new Array();
                           temArr[0] = afterten_kzl[a].kzl;
                           temArr[1] = afterten_kzl[a].flyNum;
                           dataa6[a] = temArr;
                       }
                       afterTen["proportion"] = dataa6;
                       dataa7 = new Array();
                       for(var a=0; a<afterten_zdl.length;a++){
                           var temArr = new Array();
                           temArr[0] = afterten_zdl[a].zdl;
                           temArr[1] = afterten_zdl[a].flyNum;
                           dataa7[a] = temArr;
                       }
                       afterTen["quasi"] = dataa7;
                       toptenArray.push(topTen);
                       aftertenArray.push(afterTen);
                       topTen = {};//重置对象
                       afterTen = {};//重置对象
                       //进港
                       topten_zsr = data.jtopten_zsr;
                       topten_kll = data.jtopten_kll;
                       topten_kzl = data.jtopten_kzl;
                       topten_zdl = data.jtopten_zdl;
                       dataa1 = new Array();
                       for(var a=0; a<topten_zsr.length;a++){
                           var temArr = new Array();
                           temArr[0] = topten_zsr[a].zsr;
                           temArr[1] = topten_zsr[a].flyNum;
                           dataa1[a] = temArr;
                       }
                       topTen["income"] = dataa1;
                       dataa2 = new Array();
                       for(var a=0; a<topten_kll.length;a++){
                           var temArr = new Array();
                           temArr[0] = topten_kll[a].persons;
                           temArr[1] = topten_kll[a].flyNum;
                           dataa2[a] = temArr;
                       }
                       topTen["traffic"] = dataa2;
                       dataa3 = new Array();
                       for(var a=0; a<topten_kzl.length;a++){
                           var temArr = new Array();
                           temArr[0] = topten_kzl[a].kzl;
                           temArr[1] = topten_kzl[a].flyNum;
                           dataa3[a] = temArr;
                       }
                       topTen["proportion"] = dataa3;
                       dataa33 = new Array();
                       for(var a=0; a<topten_zdl.length;a++){
                           var temArr = new Array();
                           temArr[0] = topten_zdl[a].zdl;
                           temArr[1] = topten_zdl[a].flyNum;
                           dataa33[a] = temArr;
                       }	 
                       topTen["quasi"] = dataa33;
                       
                       afterten_zsr = data.jafterten_zsr;
                       afterten_kll = data.jafterten_kll;
                       afterten_kzl = data.jafterten_kzl;
                       afterten_zdl = data.jafterten_zdl;
                       dataa4 = new Array();
                       for(var a=0; a<afterten_zsr.length;a++){
                           var temArr = new Array();
                           temArr[0] = afterten_zsr[a].zsr;
                           temArr[1] = afterten_zsr[a].flyNum;
                           dataa4[a] = temArr;
                       }
                       afterTen["income"] = dataa4;
                       dataa5 = new Array();
                       for(var a=0; a<afterten_kll.length;a++){
                           var temArr = new Array();
                           temArr[0] = afterten_kll[a].persons;
                           temArr[1] = afterten_kll[a].flyNum;
                           dataa5[a] = temArr;
                       }
                       afterTen["traffic"] = dataa5;
                       dataa6 = new Array();
                       for(var a=0; a<afterten_kzl.length;a++){
                           var temArr = new Array();
                           temArr[0] = afterten_kzl[a].kzl;
                           temArr[1] = afterten_kzl[a].flyNum;
                           dataa6[a] = temArr;
                       }
                       afterTen["proportion"] = dataa6;
                       dataa7 = new Array();
                       for(var a=0; a<afterten_zdl.length;a++){
                           var temArr = new Array();
                           temArr[0] = afterten_zdl[a].zdl;
                           temArr[1] = afterten_zdl[a].flyNum;
                           dataa7[a] = temArr;
                       }
                       afterTen["quasi"] = dataa7;
                       toptenArray.push(topTen);
                       aftertenArray.push(afterTen);
                       topTenList.push(toptenArray);
                       afterTenList.push(aftertenArray);
                   }
                   kpiData['topTen'] = topTenList;
                   kpiData['afterTen'] = afterTenList;
                   kpiData['summary'] = summaryList;
                   kpiData['fsummary'] = fsummaryList;//关注航班数据
                 ultimately=kpiData.topTen[0][0];
               }
            },
            error : function() {//FIXME  请求数据后此函数内容应放在success里面 -暂时做无求情数据测试
            }
        });
    }
    function uploadHead(){
        var imgPath = $('#phBg').val();
        if(imgPath==null||imgPath==''){
            alert('请选择上传图片');
            return;
        }
        $.ajaxFileUpload({
            url:'/employee_uhead',
            type:'post',
            secureuri : false,//是否启用安全提交，默认为false  
            fileElementId:'phBg',//文件选择框的id属性  
            data:{
                data:[$('#phBg').attr('name')],
                imagePath:imgPath
            },
            dataType: 'json',//服务器返回的格式
            async:false,
            success:function(data){
                if(data.opResult=='0'){
                    $('.ph-img').css('background-image','url("/uploadimage/'+data.id+'/'+data.fileName+'")');
                    $('.small-head').attr('src','/uploadimage/'+data.id+'/'+data.fileName);
                }else{
                    alert('上传失败');
                }
            }
        });
    }
    function uploadBackground(){
        var imgPath = $('#imgBg').val();
        if(imgPath==null||imgPath==''){
            alert('请选择上传图片');
            return;
        }
        $.ajaxFileUpload({
            url:'/employee_ubg',
            type:'post',
            secureuri : false,//是否启用安全提交，默认为false  
            fileElementId:'imgBg',//文件选择框的id属性  
            data:{
                data:[$('#imgBg').attr('name')],
                picParams:imgPath
            },
            dataType: 'json',//服务器返回的格式
            async:false,
            success:function(data){
                if(data.opResult=='0'){
                    $('.bg-img').css('background-image','url("/uploadimage/'+data.id+'/'+data.fileName+'")');
                }else{
                    alert('上传失败');
                }
            },
            error:function(data){
                console.log(data);
            }
        });
    }
