<%--
  Created by IntelliJ IDEA.
  User: lengzq
  Date: 2018/2/23
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User detail</title>
</head>
<body>
    <table>
        <tr>
            <td>姓名：</td>
            <td>${user.username}</td>
        </tr>
        <tr>
            <td>手机：</td>
            <td>${user.phone}</td>
        </tr>
        <tr>
            <td>邮箱：</td>
            <td>${user.mail}</td>
        </tr>
    </table>
</body>
</html>
