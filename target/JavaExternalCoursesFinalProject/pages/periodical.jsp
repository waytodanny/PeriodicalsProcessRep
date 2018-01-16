
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Periodical</title>
</head>
<body>
<jsp:include page="additional/header.jsp"/>
<h2>Welcome, ${login}</h2>
<jsp:include page="additional/cart.jsp"/>

<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <td>${periodical.name}</td>
        <td>${periodical.description}</td>
        <td>${periodical.subscriptionCost}</td>
    </tr>
</table>
</body>
</html>
