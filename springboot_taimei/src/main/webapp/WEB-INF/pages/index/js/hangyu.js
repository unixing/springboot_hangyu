$.ajax({
    type:'get',
    url : 'http://192.168.10.174/checkLogin',
    dataType : 'json',
    data:{
        wheres: 'hangyu'
    },
    success : function(data) {
        window.location.href="/hangyu"; 
    }
})
