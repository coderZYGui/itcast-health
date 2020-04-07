<%--
  Created by IntelliJ IDEA.
  User: seanyang
  Date: 2019/7/22
  Time: 8:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<form action="/user/login.do" method="post">
    username：<input type="text" name="username"><br>
    password：<input type="password" name="password"><br>
    <input type="submit" value="submit">
</form>
</body>
</html>
