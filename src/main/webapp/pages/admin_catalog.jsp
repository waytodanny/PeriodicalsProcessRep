<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Catalog editing</title>
</head>
<body>
<jsp:include page="additional/header.jsp"/>

<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <th>name</th>
        <th>description</th>
        <th>cost</th>
        <th>is limited?</th>
        <th>issues per year</th>
        <th>genre</th>
        <th>publisher</th>
        <th>delete</th>
        <th>update</th>
        <th>view issues</th>
        <th>add issue</th>
    </tr>
    <form method="post">
        <%--hidden values that controller (command) will use to know parameteres of edited element--%>
        <input id="deleteId" type="hidden" name="deleteId" value=""/>
        <input id="updateId" type="hidden" name="updateId" value=""/>
        <input id="updatedName" type="hidden" name="updatedName" value=""/>
        <input id="updatedDesc" type="hidden" name="updatedDesc" value=""/>
        <input id="updatedSubscrCost" type="hidden" name="updatedSubscrCost" value=""/>
        <input id="updatedIssuesPerYear" type="hidden" name="updatedIssuesPerYear" value=""/>
        <input id="updatedIsLimited" type="hidden" name="updatedIsLimited" value=""/>
        <input id="genreId" type="hidden" name="genreId" value=""/>
        <input id="publisherId" type="hidden" name="publisherId" value=""/>

        <input type="hidden" name="command" value="admin_catalog"/>

        <c:forEach var="periodical" items="${periodicals}">
            <tr id="${periodical.id}">
                <td>
                    <input class="per-name" type="text" value="${periodical.name}"/>
                </td>
                <td>
                    <input class="per_desc" type="text" value="${periodical.description}"/>
                </td>
                <td>
                    <input class="per_sub_cost" type="text" value="${periodical.subscriptionCost}"/>
                </td>
                <td>
                    <select class="is_limited_dd_list">
                        <option value="${periodical.isLimited}">${periodical.isLimited}</option>
                        <option value="${not periodical.isLimited}">${not periodical.isLimited}</option>
                    </select>
                </td>
                <td>
                    <input class="per_iss_per_year" type="number" value="${periodical.issuesPerYear}"/>
                </td>
                <td>
                    <select class="genres_dd_list">
                        <c:forEach items="${genres}" var="genre">
                            <c:choose>
                                <c:when test="${genre.id eq periodical.genre.id}">
                                    <option value="${genre.id}" selected="selected">${genre.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${genre.id}">${genre.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>

                <td>
                    <select class="publishers_dd_list">
                        <c:forEach items="${publishers}" var="publ">
                            <c:choose>
                                <c:when test="${publ.id eq periodical.publisher.id}">
                                    <option value="${publ.id}" selected="selected">${publ.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${publ.id}">${publ.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <input class="del-btn" type="submit" value="delete"/>
                </td>
                <td>
                    <input class="upd-btn" type="submit" value="update"/>
                </td>
                <td>
                    <div>
                        <a href="admin_periodical_issues?id=${periodical.id}">issues</a>
                    </div>
                </td>
                <td>
                    <div>
                        <a href="admin_add_issue?id=${periodical.id}">+</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </form>
</table>

<%--For displaying Previous link except for the 1st page --%>
<c:if test="${currentPage != 1}">
    <td><a href="admin_catalog?page=${currentPage - 1}">Previous</a></td>
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
                    <td><a href="admin_catalog?page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<%--For displaying Next link --%>
<c:if test="${currentPage lt pagesCount}">
    <td><a href="admin_catalog?page=${currentPage + 1}">Next</a></td>
</c:if>

<script>
    $(document).ready(function () {
        $(document).on("click", ".del-btn", function () {
            var row = $(this).parents()[1];
            var id = row.id;

            $("#deleteId").val(id);
        });
    });

    $(document).ready(function () {
        $(document).on("click", ".upd-btn", function () {
            var row = $(this).parents()[1];
            var id = row.id;

            var name = $(row).find('.per-name').val();
            var desc = $(row).find('.per_desc').val();
            var subCost = $(row).find('.per_sub_cost').val();
            var issPerYear = $(row).find('.per_iss_per_year').val();
            var selectLimit = $(parent).find('.is_limited_dd_list').val();

            var selectGenre = $(row).find('.genres_dd_list:first').val();
            var selectPublisher = $(row).find('.publishers_dd_list:first').val();

            $("#updateId").val(id);

            $("#updatedName").val(name);
            $("#updatedDesc").val(desc);
            $("#updatedSubscrCost").val(subCost);
            $("#updatedIsLimited").val(selectLimit);
            $("#updatedIssuesPerYear").val(issPerYear);

            $("#genreId").val(selectGenre);
            $("#publisherId").val(selectPublisher);
        });
    });
</script>

</body>
</html>
