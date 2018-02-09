 //  这个文件做vue事件触发中心，禁止添加其他项目代码

function setting(){//--------------------设置
	changeStyle();
	$(".useName-list").css("display","none");
	setTimeout(function(){
		document.getElementById('air').style.zIndex=999;
		document.getElementById('setting').click();
	},50)
}
function cityQuery(){//--------------------城市查询
	changeStyle();
	$(".useName-list").css("display","none");
	setTimeout(function(){
		document.getElementById('air').style.zIndex=999;
		document.getElementById('cityQuery').click();
	},50)
}
function airLine(){//--------------------航线查询
	changeStyle();
	$(".useName-list").css("display","none");
	setTimeout(function(){
		document.getElementById('air').style.zIndex=999;
		document.getElementById('airLineQuery').click();
	},50)
	
}
function airQuery(){//--------------------机场查询
	changeStyle();
	$(".useName-list").css("display","none");
	setTimeout(function(){
		document.getElementById('air').style.zIndex=999;
		document.getElementById('airQuery').click();
	},50)
}
function openSearch(){//--------------------搜索框
	$(".useName-list").css("display","none");
	$('#air').css({
		'display': 'block',
        'background-color': 'rgba(0,0,0,0)',
        'position': 'fixed',
        'top': '50px',
        'left': '60px',
        'width': '500px',
        'height': 'auto',
        'overflow':'visible'		
	})
	setTimeout(function(){
		document.getElementById('air').style.zIndex=999;
		document.getElementById('nationSearch').click();
	},50)
}
function changeStyle(){//--------------------搜索框更改wrap蒙版样式
	$('#air').css({
		'display': 'block',
        'background-color': 'rgba(0,0,0,.75)',
        'position': 'absolute',
        'top': '0',
        'left': '0',
        'width': '100%',
        'height': '100%',		
	})
}