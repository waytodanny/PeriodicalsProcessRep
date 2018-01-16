<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"--%>
<%--scope="session" />--%>
<%--<fmt:setLocale value="${language}" />--%>
<fmt:setBundle basename="resources" var="rb"/>
<div id="overlay"></div>

<header>
    <div id="hamburger-menu">
        <i class="fa fa-navicon"></i></div>
    <div id="cart-button">
        <i class="fa fa-shopping-cart"></i></div>
</header>

<nav id="sidebar" role="navigation">
    <a class="btn btn-success navbar-button" href="login">
        <i class="fa fa-user-circle-o" aria-hidden="true"></i>
        <fmt:message key="page.header.sidebar.btn.register" bundle="${rb}"/>
    </a>
    <a class="btn btn-info navbar-button" href="">
        <i class="fa fa-sign-in" aria-hidden="true"></i>
        <fmt:message key="page.header.sidebar.btn.login" bundle="${rb}"/>
    </a>

    <a class="btn btn-secondary navbar-button" href="">
        <i class="fa fa-sign-out" aria-hidden="true"></i>
        <fmt:message key="page.header.sidebar.btn.sign_out" bundle="${rb}"/>
    </a>
    <a class="btn btn-warning navbar-button" href="">
        <i class="fa fa-book" aria-hidden="true"></i>
        <fmt:message key="page.header.sidebar.btn.subscribes" bundle="${rb}"/>
    </a>

    <h2>
        <fmt:message key="page.header.sidebar.genres" bundle="${rb}"/>
    </h2>
    <ul class="nav sidebar-nav">
        <c:forEach var="genre" items="${genres}">
            <li><a href="catalog?genre=${genre.name}">${genre.name}</a></li>
        </c:forEach>
    </ul>
</nav>