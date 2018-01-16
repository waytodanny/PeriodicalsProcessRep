<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--Created by IntelliJ IDEA.
  User: Daniel
  Date: 06.01.2018
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" session="true" isELIgnored="false" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<jsp:include page="../additional/header.jsp"/>
<h2>error</h2>
<span>${requestScope.errorMessage}</span>
</body>
</html>
