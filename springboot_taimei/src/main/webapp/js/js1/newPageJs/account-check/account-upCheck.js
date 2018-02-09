$(document).ready(function() { 
	var loadStatus= false;
	var compare_over= true;
    // 核对按钮   显示面板
    $(".SD-check").click(function() {	//核对按钮

        if($("#SD-detail-table td").text()=="暂无数据"){
            alert("暂无数据可供对比。");
            return;
        }
        $(".check-container").show();
        $(".check-panel").animate({'right':'0px'});
        if(compare_over){
            pageManager(0);        	
        }
    })
    
    //上传图标  上传文件
    $("#check-upload-icon").change(function(){
    	var thisFile = $(this).prop("files")[0];
    	var fileType= thisFile.name.split('.')[thisFile.name.split('.').length - 1];
    	$(".check-uped p.uperror-msg").hide();
    	$("#doup").attr("disabled",false);
        pageManager(1);
    	$(".filelist .filelist-p").text(thisFile.name);
    	
    	if(fileType != "xls" && fileType != "xlsx") {	//文件类型
    		$(".check-uped p.uperror-msg").show();
    		$("#doup").attr("disabled","disabled");
    		return false;    		
    	}    	
    	if(thisFile.size / 1024 > 5000){
    		$(".check-uped p.uperror-msg").text('文件过大，请将文件保持在5M以内！');	//文件大小
    		$("#doup").attr("disabled","disabled");
    		return false;
    	}
    })    
    
    $("#check-panel-clearX").click(function(){	//删除文件按钮
    	if($("#check-upload-icon").prop("files")[0]){
    		$("#check-upload-icon").val(null);
    		$(".filelist .filelist-p").text("");
    		pageManager(0);		
    	}else{
    		return;
    	}    	
    })
    
    $("#check-main .docancel").click(function(event){	//取消按钮
    	event.stopPropagation();
    	pageManager();
		$("#check-upload-icon").val(null);
		$(".filelist .filelist-p").text("");
    })
    
    
    
    $(".check-uped .filelist-p").click(function(){	//重新选择文件
    	$("#check-upload-icon").click();
    })
    
    var open_price_tips = function(){	//票面价格错误提示信息
        $(".compare-Table .price-error,.price-errorB").bind('mouseover',function(e){
        	$(".price-error-info").show();
        	var tdtop= $(this).offset().top,
        	tdleft= $(this).offset().left - 170;
        	$(".price-error-info").css({'top':tdtop + 'px','left':tdleft + 'px'})

        	//判断是联程还是错误
        	if($(this).attr('flag')== 1 ){
        		$(".price-error-info").text("包含联程，数据可能有误差");
        	} else if($(this).attr('flag')== 0) {
        		$(".price-error-info").hide();
        		$(".price-error-info").text("票价存在误差");    		
        	} else {
        		$(".price-error-info").hide();
        	}
        })
        
        $(".compare-Table .price-error").bind('mouseout',function(e){
        	$(".price-error-info").hide()
        })    	
    }
    
    
    
    //上传按钮,都在uped模块
    $(".check-uped #doup").click(function(event){	
    	$(".check-uped-main").hide();
    	$(".check-container .check-loading").css('display', 'flex');
    	var oMyForm = new FormData();
    	var ofile = document.querySelector("#check-upload-icon").files[0];
    	
    	oMyForm.append('file', ofile);    	
    	oMyForm.append('flightNum', $(".current").text());
    	oMyForm.append('endTime', $("#reservation").val().split(" - ")[1]);
    	oMyForm.append('startTime', $("#reservation").val().split(" - ")[0]);
    	oMyForm.append('fltRteCd', $(".SD-set-list-title").attr('tag'));    	

    	fileUpLoad(oMyForm);
    	
    })
    
    //请求模块
    var fileUpLoad = function (formdata){
	    $("#check-complate-info").show();
	    $('#table-uesr-body').empty();
	    $('#table-system-body').empty();
    	$.ajax({
    	    url: '/SalesData/importExcel',
    	    type: 'POST',
    	    dataType: 'JSON',
    	    data: formdata,
    	    processData: false,
    	    contentType: false
    	})
    	.done(function(tabledata) {
    		if(tabledata.success == true) {  
    		    $("#check-complate-info").hide();    
    			fillTable(tabledata.data);
    			$("#check-file-out").attr('href','/SalesData/exportCompare');
    		} else {
        	    $("#check-complate-info span").text(tabledata.msg || '服务器异常，请稍后重试。');
    		}
    	})
    	.fail(function() {
    	    $("#check-complate-info span").text('上传操作错误');
    	})
    	.always(function() {
    		pageManager(2);
    	});
    }
    
    
    //填充数据
    var fillTable = function (data){    	
    	loadStatus= true;
    	var tableImport = $('#table-uesr-body'), tableSystem = $('#table-system-body');
    	var nodeI= '', nodeS= ''; 
    	var mark = true ;
    	for ( var i = 0 ; i < data.length ; i++){
    		nodeI= '', nodeS= '';
    		mark = true ;
    		if(data[i].ticketInfoImport != null){	//有数据
    			var val= data[i].ticketInfoImport;
				nodeI= '<tr><td>' + val.flightDate + '</td><td>' + val.flightNum + '</td><td>' + val.leg + '</td><td>' + val.ticketNum + '</td><td class="price-error" flag=' + val.flag + '>' + val.ticketPri + '</td></tr>'
 			
    		} else {
    			mark = false;
    			nodeI= '<tr><td colSpan="5">无对应票面数据</td></tr>';
    				
    		}
    		
    		if(data[i].ticketInfoSystem != null){	//有数据
    			var val= data[i].ticketInfoSystem;
    			if(val.flag =='0'){
    				nodeS= '<tr><td>' + val.flightDate + '</td><td>' + val.flightNum + '</td><td>' + val.leg + '</td><td>' + val.ticketNumDetail + '</td><td class="price-error" flag=' + val.flag + '>' + val.ticketPri + '</td></tr>'
    			}else {
    				nodeS= '<tr><td>' + val.flightDate + '</td><td>' + val.flightNum + '</td><td>' + val.leg + '</td><td>' + val.ticketNumDetail + '</td><td class="price-errorB" flag=' + val.flag + '>' + val.ticketPri + '</td></tr>'
    			}

    		} else { 
    			mark = false;
    			nodeS= '<tr><td colSpan="5">无对应票面数据</td></tr>'	;
    		}
    		if(!mark){
    			nodeI = nodeI.replace('class="price-error"',"");
    			nodeS = nodeS.replace('class="price-error"',"");
    			nodeS = nodeS.replace('class="price-errorB"',"");    			
    		}    		
			$(tableImport).append(nodeI);
			$(tableSystem).append(nodeS);
    	}
    	compare_over= false;
    	open_price_tips();
    }
    
    
    
    
    var pageManager = function(state){
    	var Pa = $("#check-uping"),
    	Pb = $(".check-uped"),
    	Pc = $("#check-compare");
    	if(state == 0) {
    		Pa.show();
    		Pb.hide();
    		Pc.hide();
    		$("#check-file-out").attr('href','javascript:void(0)');
    		$("#check-upload-icon").val('');
    	}else if(state == 1 ) {
    		Pa.hide();
    		Pb.show();
    		Pc.hide();
    		$(".check-uped-main").show();
    		
    	}else if(state == 2 ) {
    		Pa.hide();
    		Pb.hide();
    		Pc.show();
    		
    	}else {
    		pageManager(0);
    		$(".check-panel").css({'right':'-950px'});
    		$("#check-main").hide();
    	}
    }
    
    
    
    $("#check-file-put").bind('click',function(){	//重新上传按钮
    	compare_over= true;
    	pageManager(0);    	
    })
    
    

    //冒泡关闭
    $(".check-container").click(function(e){
        var _con = $(".check-panel");
        if(!_con.is(e.target) && _con.has(e.target).length === 0){
        	if(compare_over){
            	pageManager();        		
        	}else{
        		$('.check-container').hide();
        	}
//            $(".check-panel").animate({'right':'-950px'});
//            $(".check-container").hide();
//            $(".check-uped").hide();
//            $("#check-upload-icon").val(null);
//            $(".check-container .check-loading").hide();
//            $(".check-uped-main").show();
        }        
    });
})