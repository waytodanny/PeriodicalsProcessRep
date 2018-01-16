<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin_main</title>
</head>
<body>
ADMIN
<form action="/app/admin_catalog">
    <input type="submit" value="edit catalog"/>
</form>
<form action="/app/admin_add_periodical">
    <input type="submit" value="add periodical"/>
</form>
<form action="/app/admin_users">
    <input type="submit" value="see users"/>
</form>
</body>
</html>
