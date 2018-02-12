<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="/js/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.2.6/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.2.6/themes/icon.css" />
    <script type="text/javascript" src="/js/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/js/jquery-easyui-1.2.6/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/js/commons.js"></script>
    <script type="text/javascript">
        $(function(){

            //-----------------------------对于form表单的验证 ---------------------------------------------------------------------------
            $('#username').numberbox({
                min:0 , //允许的最小值
                max:150 , //允许的最大值
                required:true , //必填字段  定义是否字段应被输入
                missingMessage:'用户名必填!' ,   //当文本框是空时出现的提示文字
                precision:0    //显示在小数点后面的最大精度
            });
            //数值验证组件
            $('#age').numberbox({
                min:0 , //允许的最小值
                max:150 , //允许的最大值
                required:true , //必填字段  定义是否字段应被输入
                missingMessage:'年龄必填!' ,   //当文本框是空时出现的提示文字
                precision:0    //显示在小数点后面的最大精度
            });

            //日期组件
            $('#birthday').datebox({
                required:true ,   //必填字段  定义是否字段应被输入
                missingMessage:'生日必填!' ,  //当文本框是空时出现的提示文字
                editable:false  //定义是否用户可以往文本域中直接输入文字
            });

            $('#salary').numberbox({
                min:1000 ,
                max:20000 ,
                required:true ,
                missingMessage:'薪水必填!' ,
                precision:2
            });

            //日期时间组件
            $('#startTime,#endTime').datetimebox({
                required:true ,
                missingMessage:'时间必填!' ,
                editable:false   //定义是否用户可以往文本域中直接输入文字
            });


            var flag ;		//undefined 判断新增和修改方法

            ///---------------------datagrid部分----------------------------------------------------------------------------------
            $('#tt').datagrid({
                idField: 'id',    //只要创建数据表格 就必须要加 ifField
                url: 'user/queryUserByPage',
                title: '用户信息',
                //width: '1000',
                height:450 ,
                fitColumns: true,				//宽度自适应
                striped: true ,					//隔行变色特性
                rownumbers:true,				//显示行号
                //singleSelect:true ,				//单选模式
                loadMsg: '数据正在加载,请耐心的等待...' ,
                frozenColumns:[[				//冻结列特性 ,不要与fitColumns 特性一起使用
                    {							//如果需要多选，需要禁止单选模式
                        field:'ck' ,
                        width:50 ,
                        checkbox: true
                    }
                ]],
                columns:[[
                    {field:'id',title:'编号',width:120},
                    {
                        field:'username',
                        title:'用户名',
                        width:120,
                        align:'center' ,   //居中显示
                        styler:function(value , record){
                            if(value == 'admin'){
                                //return 'background:blue;';   //如果用户名为admin，变蓝色
                            }
                        }
                    },
                    {
                        field:'password',
                        title:'密码',
                        width:120,
                        hidden: true   //将密码隐藏
                    },
                    {
                        field:'sex',
                        title:'性别',
                        width:120,
                        formatter:function(value , record , index){
                            if(value == 1){
                                return '<span style=color:red; >男</span>' ;
                            } else if( value == 2){
                                return '<span style=color:green; >女</span>' ;
                            }
                            //console.info(value);
                            //console.info(record);
                            //console.info(index);
                        }
                    },
                    {field:'age',title:'年龄',width:120},
                    {field:'birthday',title:'生日',width:120},
                    {
                        field:'city',
                        title:'城市',
                        width:120,
                        formatter:function(value , record , index){
                            /*
                            if(value==1){
                                return '北京';
                            } else if(value == 2){
                                return '上海';
                            } else if(value == 3){
                                return '深圳';
                            } else if(value == 4){
                                return '长春';
                            }
                            */
                            var str = '';
                            $.ajax({
                                type:'post' ,
                                url : 'user/getCityName' ,
                                /* url : 'user/getCityName2' , */
                                cache:false ,
                                async: false ,		//同步请求
                                data:{id:value},
                                dataType:'json' ,
                                success:function(result){
                                    //str = result ;    //dataType:'text'
                                    str = result.name ; //dataType:'json'
                                }
                            });
                            return str ;
                        }

                    },
                    {field:'salary',title:'薪资',width:120},
                    {field:'starttime',title:'创建日期',width:120},
                    {field:'endtime',title:'结束日期',width:120},
                    {
                        field:'description',
                        title:'描述',
                        width:120,
                        formatter:function(value , record , index){
                            return '<span title='+value+'>'+value+'</span>';
                        }
                    },
                ]],
                pagination: true ,   //在底部显示分页栏
                pageSize: 10 ,		 //每页显示多少个
                pageList:[5,10,15,20,50], //初始化页面尺寸的选择列表
                toolbar:[
                    {
                        iconCls:"icon-add",//按钮上的图标
                        text:"添加用户", //按钮的文字
                        handler:function(){
                            flag = 'add';   //改变flag的值
                            //$('#myform').find('input[name!=sex]').val("");
                            $('#myform').get(0).reset();
                            //$('#myform').form('clear');
                            $("#mydialog").dialog("open");
                        }
                    },
                    {
                        iconCls:"icon-edit",//按钮上的图标
                        text:"编辑用户",//按钮的文字
                        handler:function(){
                            flag = 'edit';   //改变flag的值
                            var arr =$('#tt').datagrid('getSelections');    //获取被选中的行，返回的是数组
                            if(arr.length != 1){
                                $.messager.show({
                                    title:'提示信息!',
                                    msg:'只能选择一行记录进行修改!'
                                });
                            } else {
                                $('#mydialog').dialog({
                                    title:'修改用户'
                                });
                                $('#mydialog').dialog('open'); //打开窗口
                                $('#myform').get(0).reset();   //清空表单数据
                                $('#myform').form('load',{	   //调用load方法把所选中的数据load到表单中,非常方便
                                    id:arr[0].id ,
                                    username:arr[0].username ,
                                    password:arr[0].password ,
                                    sex:arr[0].sex ,
                                    age:arr[0].age ,
                                    birthday:arr[0].birthday ,
                                    city:arr[0].city ,
                                    salary:arr[0].salary ,
                                    starttime:arr[0].starttime,
                                    endtime:arr[0].endtime ,
                                    description:arr[0].description
                                });
                            }
                        }
                    },
                    {
                        iconCls:"icon-remove",//按钮上的图标
                        text:"删除用户",//按钮的文字
                        handler:function(){
                            //console.log('删除');   //在浏览器控制台打印日志
                            var arr =$('#tt').datagrid('getSelections');
                            if(arr.length <=0){
                                $.messager.show({
                                    title:'提示信息!',
                                    msg:'至少选择一行记录进行删除!'
                                });
                            } else {

                                $.messager.confirm('提示信息' , '确认删除?' , function(r){
                                    if(r){
                                        var ids = '';
                                        for(var i =0 ;i<arr.length;i++){
                                            ids += arr[i].id + ',' ;
                                        }
                                        ids = ids.substring(0 , ids.length-1);
                                        $.post('user/delete' , {ids:ids} , function(result){
                                            //1 刷新数据表格
                                            $('#tt').datagrid('reload');
                                            //2 清空idField
                                            $('#tt').datagrid('clearSelections');   //unselectAll取消选中当前页所有的行。   clearSelections清除所有的选择。
                                            //3 给提示信息
                                            $.messager.show({
                                                title:result.status ,
                                                msg:result.message
                                            });
                                        });
                                    } else {
                                        return ;
                                    }
                                });
                            }
                        }
                    },
                    {
                        iconCls:"icon-search",//按钮上的图标
                        text:"查询用户",//按钮的文字
                        handler:function(){
                            //console.log('查询');   //在浏览器控制台打印日志
                            $('#lay').layout('expand' , 'north');
                        }
                    }
                ]
            });




            //-----------提交表单方法-------------------------------------------------------------------------------------------------------------
            $('#btn1').click(function(){
                if($('#myform').form('validate')){
                    $.ajax({
                        type: 'post' ,
                        url: flag=='add'?'user/save':'user/update' ,
                        //url:'user/save',
                        cache:false ,
                        data:$('#myform').serialize() ,
                        dataType:'json' ,
                        success:function(result){
                            //1 关闭窗口
                            $('#mydialog').dialog('close');
                            //2刷新datagrid
                            $('#tt').datagrid('reload');
                            //3 提示信息
                            $.messager.show({
                                title:result.status ,
                                msg:result.message
                            });
                        } ,
                        error:function(result){
                            $.meesager.show({
                                title:result.status ,
                                msg:result.message
                            });
                        }
                    });
                } else {
                    $.messager.show({
                        title:'提示信息!' ,
                        msg:'数据验证不通过,不能保存!'
                    });
                }
            });

            /**
             * 关闭窗口方法
             */
            $('#btn2').click(function(){
                $('#mydialog').dialog('close');
            });


            $('#searchbtn').click(function(){
                $('#tt').datagrid('load' ,serializeForm($('#mysearch')));
            });

            //查询时清空按钮
            $('#clearbtn').click(function(){
                $('#mysearch').form('clear');
                $('#tt').datagrid('load' ,{});  //清空数据
            });
        });

        //js方法：序列化表单
        function serializeForm(form){
            var obj = {};
            $.each(form.serializeArray(),function(index){
                if(obj[this['name']]){
                    obj[this['name']] = obj[this['name']] + ','+this['value'];
                } else {
                    obj[this['name']] =this['value'];
                }
            });
            return obj;
        }
    </script>
</head>
<body>

<div id="lay" class="easyui-layout" fit=true style="width: 100%; height: 1000px">
    <!-- 用户搜索部分 -->
    <div region="north" title="用户查询" split="true" collapsed=true  style="height: 100px;">
        <div style="margin-left: 100px;margin-top: 20px;">
            <form id="mysearch" method="post">
                用户名:<input name="username" class="easyui-validatebox"  value="" />
                   
                开始时间:<input name="starttime"  class="easyui-datetimebox" editable="false" style="width:160px;"  value="" />
                结束时间:<input name="endtime"  class="easyui-datetimebox" editable="false" style="width:160px;"  value="" />
                   
                <a id="searchbtn" class="easyui-linkbutton">查询</a> <a id="clearbtn" class="easyui-linkbutton">清空</a>
            </form>
        </div>
    </div>
    <!-- 用户列表部分 -->
    <div region="center"  style="padding: 5px; background: #eee;">
        <table id="tt"></table>
    </div>
</div>




<!-- modal：模态窗口      draggable：窗口不可拖动    closed：默认关闭-->
<div id="mydialog" title="新增用户" modal=true  draggable=false class="easyui-dialog" closed=true style="width:300px;">
    <form id="myform" action="" method="post">
        <input type="hidden" name="id" value="" />
        <table>
            <tr>
                <td>用户名:</td>
                <td><input type="text" name="username" class="easyui-validatebox" required=true validType="midLength[2,5]" missingMessage="用户名必填!" invalidMessage="用户名必须在2到5个字符之间!"  value="" /></td>
            </tr>
            <tr>
                <td>密码:</td>
                <td><input type="password" name="password" class="easyui-validatebox" required=true validType="equalLength[4]" missingMessage="密码必填!" value="" /></td>
            </tr>
            <tr>
                <td>性别:</td>
                <td>
                    男<input type="radio" checked="checked" name="sex" value="1" />
                    女<input type="radio" name="sex" value="2" />
                </td>
            </tr>
            <tr>
                <td>年龄:</td>
                <td><input id="age" type="text"  name="age" value="" /></td>
            </tr>
            <tr>
                <td>出生日期:</td>
                <td><input id="birthday" style="width:160px;"  type="text" name="birthday" value="" /></td>
            </tr>
            <tr>
                <td>所属城市:</td>
                <td>
                    <input name="city" class="easyui-combobox" url="user/getCity" valueField="id" textField="name"  value="" />
                </td>
            </tr>
            <tr>
                <td>薪水:</td>
                <td><input id="salary" type="text" name="salary" value="" /></td>
            </tr>
            <tr>
                <td>开始时间:</td>
                <td><input id="startTime" style="width:160px;"  type="text" name="starttime"  value="" /></td>
            </tr>
            <tr>
                <td>结束时间:</td>
                <td><input id="endTime" style="width:160px;"   type="text" name="endtime"  value="" /></td>
            </tr>
            <tr>
                <td>个人描述:</td>
                <td><input type="text" name="description" class="easyui-validatebox" required=true validType="midLength[5,50]" missingMessage="个人描述必填!" invalidMessage="描述必须在5到50个字符之间!"  value="" /></td>
            </tr>
            <tr align="center">
                <td colspan="2">
                    <a id="btn1" class="easyui-linkbutton">确定</a>
                    <a id="btn2" class="easyui-linkbutton">关闭</a>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>