<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"--%>
<%--scope="session" />--%>
<%--<fmt:setLocale value="${language}" />--%>
<fmt:setBundle basename="resources" var="rb"/>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>" type="text/css"/>
</head>
<body class="no-header">
<div class="login-wrapper centered-flex-column">
    <form id="login" class="form-signin" method="post" action="/app/login">
        <h2 class="form-signin-heading">
            <fmt:message key="page.login.title" bundle="${rb}"/>
        </h2>
        <input type="text" class="form-control" name="login" required="" maxlength="50" autofocus=""
               placeholder="<fmt:message key="page.login.placeholder.login" bundle="${rb}"/>"/>
        <input type="password" class="form-control" name="password" required="" maxlength="50"
               placeholder="<fmt:message key="page.login.placeholder.pass" bundle="${rb}"/>"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">
            <span>
                <fmt:message key="page.login.button.enter" bundle="${rb}"/>
            </span>
        </button>
        <c:if test="${not empty loginErrorMessage}">
        <span class="help-block">${loginErrorMessage}, <fmt:message key="page.login.register.begin" bundle="${rb}"/>
            <a href="registration">
                <fmt:message key="page.login.register.end" bundle="${rb}"/>?
            </a>
        </span>
        </c:if>
    </form>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>
</body>
</html>