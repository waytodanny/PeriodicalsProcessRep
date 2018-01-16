<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Issues</title>
</head>
<body>
<h2>Welcome, ${sessionScope.user.login}</h2>
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <th>name</th>
        <th>no</th>
        <th>publishing date</th>
    </tr>
    <c:forEach var="issue" items="${issues}">
        <tr>
            <td>${issue.name}</td>
            <td>${issue.issueNo}</td>
            <td>${issue.publishDate}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
