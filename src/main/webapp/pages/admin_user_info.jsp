<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User payments info</title>
</head>
<body>
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <th>time</th>
        <th>sum</th>
        <th>periodicals</th>
    </tr>
    <tr>
        <c:forEach var="payment" items="${payments}">
            <td>
                <span>${payment.paymentTime}</span>
            <td>
                <span>${payment.paymentSum}</span>
            </td>
            <td>
            <c:forEach var="periodical" items="${payment.periodicals}">
            <div><a href="periodical?id=${periodical.id}">${periodical.name}</a></div>
            </c:forEach>
            </td>
            </td>
        </c:forEach>
    </tr>
</table>

<%--For displaying Previous link except for the 1st page --%>
<c:if test="${currentPage != 1}">
    <td><a href="admin_user_info?id=${user.uuid}&page=${currentPage - 1}">Previous</a></td>
</c:if>

<%--For displaying Page numbers.
The when condition does not display a link for the current page--%>
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" end="${pagesCount}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="admin_user_info?page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<%--For displaying Next link --%>
<c:if test="${currentPage lt pagesCount}">
    <td><a href="admin_user_info?page=${currentPage + 1}">Next</a></td>
</c:if>
</body>
</html>
