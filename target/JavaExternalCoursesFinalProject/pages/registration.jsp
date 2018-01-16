<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"--%>
<%--scope="session" />--%>
<%--<fmt:setLocale value="${language}" />--%>
<fmt:setBundle basename="resources" var="rb"/>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>" type="text/css"/>
</head>
<body class="no-header">
<%--<jsp:include page="additional/header.jsp"/>--%>
<div class="login-wrapper">
    <form class="form-signin" method="post" action="/app/registration">
        <h2 class="form-signin-heading">
            <fmt:message key="page.registration.title" bundle="${rb}"/>
        </h2>
        <hr class="colorgraph">
        <br>
        <input type="text" class="form-control" name="login" required="" maxlength="50" autofocus=""
               placeholder="<fmt:message key="page.registration.placeholder.login" bundle="${rb}"/>"/>
        <input type="email" class="form-control" name="email" required="" maxlength="254" autofocus=""
               placeholder="<fmt:message key="page.registration.placeholder.email" bundle="${rb}"/>"/>
        <input type="password" class="form-control" name="password" required="" maxlength="50"
               placeholder="<fmt:message key="page.registration.placeholder.pass" bundle="${rb}"/>"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">
            <span>
                <fmt:message key="page.registration.button.enter" bundle="${rb}"/>
            </span>
        </button>
        <span>${registrationErrorMessage}</span>
        <span class="help-block">
            <a href="login">
                <fmt:message key="page.registration.link.login" bundle="${rb}"/>?
            </a>
        </span>
    </form>
</div>

<form action="login">
    <input type="submit" value="toLogin">
</form>
</body>
</html>
