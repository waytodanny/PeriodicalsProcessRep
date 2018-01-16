
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<jsp:include page="additional/header.jsp"/>
<form name="registrationForm" method="POST" action="registration">
    <input type="hidden" name="command" value="register"/>
    Login:<br/>
    <input type="text" name="login" value=""/>
    <br/>
    Password:
    <br/>
    <input type="password" name="password" value=""/>
    <br>
    Email:
    <br>
    <input type="email" name="email" value=""/>
    <input type="submit" value="register"/>
    <h4>${registrErrorMessage}</h4>
</form>
<form action="login">
    <input type="submit" value="toLogin">
</form>
</body>
</html>
