<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"--%>
<%--scope="session" />--%>
<%--<fmt:setLocale value="${language}" />--%>
<fmt:setBundle basename="resources" var="rb"/>
<html>
<head>
    <title>Subscriptions</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>" type="text/css"/>
</head>
<body>
<jsp:include page="additional/header.jsp"/>
<jsp:include page="additional/cart.jsp"/>
<c:if test="${not empty sessionScope.user}">
    <div id="products-wrapper" class="container">
        <h1><fmt:message key="page.subs.title" bundle="${rb}"/></h1>
        <div class="row">
            <c:forEach var="periodical" items="${periodicals}">
                <div class="item col-md-6 col-sm-12">
                    <h4 class="list-group-item-heading">
                        <a>${periodical.name}</a>
                    </h4>
                    <div class="row">
                        <div class="col-sm-6">
                            <label><fmt:message key="page.catalog.label.publisher" bundle="${rb}"/></label>
                            <span>${periodical.publisher.name}</span>
                        </div>
                        <div class="col-sm-6">
                            <label><fmt:message key="page.catalog.label.genre" bundle="${rb}"/></label>
                            <span>${periodical.genre.name}</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6">
                            <label><fmt:message key="page.catalog.label.iss_per_year" bundle="${rb}"/></label>
                            <span>${periodical.issuesPerYear}</span>
                        </div>
                        <div class="col-sm-6">
                            <label><fmt:message key="page.catalog.label.is_limited" bundle="${rb}"/></label>
                            <span>${periodical.isLimited}</span>
                        </div>
                    </div>
                    <p class="list-group-item-text">${periodical.description}</p>
                    <div class="row">
                        <div class="col-sm-12">
                            <a class="btn btn-warning" href="issues?periodical=${periodical.id}">
                                <fmt:message key="page.subs.to_issues" bundle="${rb}"/>
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <jsp:include page="additional/pagination.jsp"/>
    </div>
</c:if>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<c:url value='/js/catalog.js'/>"></script>
</body>
</html>