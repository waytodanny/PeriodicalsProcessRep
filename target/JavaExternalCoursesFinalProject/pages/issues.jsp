<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Issues</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>" type="text/css"/>
</head>
<body>
<jsp:include page="/pages/additional/header.jsp"/>
<jsp:include page="/pages/additional/cart.jsp"/>

<div id="issues" class="container">
    <h2>${periodical.name}</h2>
    <br/>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Name</th>
            <th>No.</th>
            <th>Publishing date</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="issue" items="${issues}">
            <tr>
                <td>${issue.name}</td>
                <td>${issue.issueNo}</td>
                <td>${issue.publishDate}</td>
                <td><a class="btn btn-info" href="">Read more</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <jsp:include page="/pages/additional/pagination.jsp"/>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<c:url value='/js/catalog.js'/>"></script>
</body>
</html>
