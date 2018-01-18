<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>" type="text/css"/>
</head>
<body class="no-header">
<div id ="error" class="centered-flex-column">
    <img class="error-image" src="<c:url value='/img/error-icon.png'/>">
    <h1 class="error-title">Oops, error!</h1>
    <span>
        <%--<c:when test="${not empty errorMessage}">--%>
            <%--${errorMessage}--%>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
            <%--Something went wrong--%>
        <%--</c:otherwise>--%>
        Something went wrong
    </span>
</div>
</body>
</html>
