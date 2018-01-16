<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--<c:import url="/resources/dependencies.html"/>--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Edit issues</title>
</head>
<body>
Issues here
<form method="post">
    <input type="hidden" name="command" value="admin_periodical_issues?id=${id}"/>
    <%--hidden values that controller (command) will use to know parameteres of edited element--%>
    <input id="deletedIssueId" type="hidden" name="deletedIssueId" value=""/>
    <input id="updatedIssueId" type="hidden" name="updatedIssueId" value=""/>
    <input id="updatedIssueName" type="hidden" name="updatedIssueName" value=""/>
    <input id="updatedIssueNo" type="hidden" name="updatedIssueNo" value=""/>

    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <td>name</td>
            <td>no of issue</td>
            <td>publishing date</td>
        </tr>
        <c:forEach var="issue" items="${issues}">
            <tr id="${issue.id}">
                <td>
                    <input class="issue-name" type="text"  value="${issue.name}" />
                </td>name="up"
                <td>
                    <input class="issue_no" type="number" min="0" value="${issue.issueNo}"/>
                </td>
                <td>
                    <input class="issue_publish_date" type="text" value="${issue.publishDate}" readonly/>
                </td>
                <td>
                    <input class="del-btn" type="submit" value="delete"/>
                </td>
                <td>
                    <input class="upd-btn" type="submit" value="update"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>

<script>
    $(document).ready(function () {
        $(document).on("click", ".del-btn", function () {
            var row = $(this).parents()[1];
            var id = row.id;

            $("#deletedIssueId").val(id);
        });
    });

    $(document).ready(function () {
        $(document).on("click", ".upd-btn", function () {
            var row = $(this).parents()[1];
            var id = row.id;

            var name = $(row).find('.issue-name').val();
            var issueNo = $(row).find('.issue_no').val();

            $("#updatedIssueId").val(id);
            $("#updatedIssueName").val(name);
            $("#updatedIssueNo").val(issueNo);
        });
    });
</script>
</body>
</html>
