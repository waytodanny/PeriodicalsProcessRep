<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"--%>
<%--scope="session" />--%>
<%--<fmt:setLocale value="${language}" />--%>
<fmt:setBundle basename="resources" var="rb"/>
<div id="overlay"></div>

<header id="header">
    <div id="hamburger-menu">
        <i class="fa fa-navicon"></i>
    </div>
    <div id="cart-button">
        <i class="fa fa-shopping-cart"></i>
    </div>
    <nav id="main-nav">
        <ul class="list-inline list-unstyled">
            <li class="list-inline-item">
                <a class="btn btn-success navbar-button" href="catalog">
                    <i class="fa fa-folder-open" aria-hidden="true"></i>
                    <fmt:message key="page.header.sidebar.btn.catalog" bundle="${rb}"/>
                </a>
            </li>
            <c:choose>
                <c:when test="${empty user}">
                    <li class="list-inline-item">
                        <a class="btn btn-success navbar-button" href="registration">
                            <i class="fa fa-user-circle-o" aria-hidden="true"></i>
                            <fmt:message key="page.header.sidebar.btn.register" bundle="${rb}"/>
                        </a>
                    </li>
                    <li class="list-inline-item">
                        <a class="btn btn-info navbar-button" href="login">
                            <i class="fa fa-sign-in" aria-hidden="true"></i>
                            <fmt:message key="page.header.sidebar.btn.login" bundle="${rb}"/>
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="list-inline-item">
                        <a class="btn btn-warning navbar-button" href="subscriptions">
                            <i class="fa fa-book" aria-hidden="true"></i>
                            <fmt:message key="page.header.sidebar.btn.subscribes" bundle="${rb}"/>
                        </a>
                    </li>
                    <li class="list-inline-item">
                        <a class="btn btn-secondary navbar-button" href="logout">
                            <i class="fa fa-sign-out" aria-hidden="true"></i>
                            <fmt:message key="page.header.sidebar.btn.log_out" bundle="${rb}"/>
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</header>

<nav id="sidebar" role="navigation">
    <h2>
        <fmt:message key="page.header.sidebar.genres" bundle="${rb}"/>
    </h2>
    <ul class="nav sidebar-nav">
        <c:forEach var="genre" items="${applicationScope.genres}">
            <li><a href="catalog?genre=${genre.name}">${genre.name}</a></li>
        </c:forEach>
    </ul>
</nav>