<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Users</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<form method="post">
    <input id="updateRoleId" type="hidden" name="updateRoleId" value=""/>
    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <th>login</th>
            <th>role</th>
            <th>delete</th>
            <th>update</th>
            <th>view info</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr id="${user.id}">
                <td>
                    <input type="text" value="${user.login}" readonly/>
                </td>

                <td>
                    <select class="roles_dd_list">
                        <c:forEach var="role" items="${roles}">
                            <c:choose>
                                <c:when test="${role.id eq user.role.id}">
                                    <option value="${role.id}" selected="selected">${role.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${role.id}">${role.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <button name="remId" type="submit" value=${user.id}>delete</button>
                </td>
                <td>
                    <button class="upd-btn" name="updId" type="submit" value=${user.id}>update</button>
                </td>
                <td>
                    <div>
                        <a href="admin_user_info?id=${user.id}"> user info</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>


<%--For displaying Previous link except for the 1st page --%>
<c:if test="${currentPage != 1}">
    <td><a href="admin_users?page=${currentPage - 1}">Previous</a></td>
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
                    <td><a href="admin_users?page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<%--For displaying Next link --%>
<c:if test="${currentPage lt pagesCount}">
    <td><a href="admin_users?page=${currentPage + 1}">Next</a></td>
</c:if>

<script>
    $(document).ready(function () {
        $(document).on("click", ".upd-btn", function () {
            var row = $(this).parents()[1];
            var id = row.id;

            var selectRole = $(row).find('.roles_dd_list').val();
            $("#updateRoleId").val(selectRole);
        });
    });
</script>
</body>
</html>
