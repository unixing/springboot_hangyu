var ws = null;
var val;
console.log(name)
function startWebSocket() {
    if('WebSocket' in window){
    	ws = new WebSocket("ws://localhost:8081/socket?name="+val);
    }else if('MozWebSocket' in window){
        ws = new MozWebSocket("ws://localhost:8081/socket?name="+val);
    }else{
        alert("not support");
    }
    ws.onmessage = function(evt){
    	console.log(evt);
        var data = evt.data;
        var obj = eval ('(' + data + ')');//将字符串转换成JSON
        if(obj.type == 'message'){
        		console.log("收到消息"+data);
                setMessageInnerHTML("时间:"+obj.data.date);
    			setMessageInnerHTML(obj.data.fromNameId +" 对 "+obj.data.toNameId+"说 ：");
    			setMessageInnerHTML("内容:"+obj.data.text);
    			setMessageInnerHTML("");
        }else{
        	 setMessageInnerHTML(obj.data.text);
        }
    };
    ws.onclose = function(evt) {
        $('#denglu').html("离线");
    };
    ws.onopen = function(evt) {
    	$('#denglu').html("在线");
        $('#userName').html(val);
    };
}

function setMessageInnerHTML(innerHTML){
	var temp = $('#message').html();
	temp += innerHTML + '<br/>';
	$('#message').html(temp);
}
var toName;
function sendMsg() {
	var params = {
		toNameId : toName,
		fromNameId : val,	
		text : $("#writeMsg").val(),
		
	}
	$.ajax({  
        type: "get",
        url: "api/send",  
        data: params,  
        success: function(msg){  
        	$("#writeMsg").val("");
        }  
     });  
}

$(function() {
	$("#writeMsg").bind("keydown", function(e) {
		if (e.keyCode == 13) { //回车键
			sendMsg();
		}
	});
	$.ajax({
		url:'/employees',
		success:function(data){
			for(var i in data){
				var inp = "<button type='button' id='btn"+i+"' value='"+data[i]+"' class='' onclick=changeChat('"+data[i]+"')>"+data[i]+"</button>";
				$("#spanl").append(inp);
			}
		}
	})
});
function changeChat(toNM){
	$("button").removeClass();
	
	$("button").each(function(index,obj,data){
		if($(obj).val()==toNM){
			$(obj).addClass("green");
		}
	})
	toName = toNM;
	$('#message').html("");
	setMessageInnerHTML("当前聊天对象:"+toNM);
	$.ajax({
		url:'/employeesChat?fromNameId='+val+"&toNameId="+toName,
		success:function(data){
			for(var i in data){
				var date = data[i].date;
				var text = data[i].text;
				var fromNameId=data[i].fromNameId;
				var toNameId=data[i].toNameId;
				var text = data[i].text;
				setMessageInnerHTML("时间:"+date);
				setMessageInnerHTML(fromNameId +" 对 "+toNameId+"说 ：");
				setMessageInnerHTML("内容:"+text);
				setMessageInnerHTML("");
				
			}
		}
	})
}