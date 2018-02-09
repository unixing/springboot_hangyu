
    login();
    function login(){
        var queryParams = {
            wheres: 'hangyu'
        }
        $.ajax({
            type:'get',
            url : '/checkLogin',
            dataType : 'json',
            data:queryParams,
            success : function(data) {
                if(data){
                    location.href="/hangyu";
                }else{
                    alert("登录失败!");
                    location.href="localhost";
                }        
            },
            error : function() {
            }
        })
    };
  
    