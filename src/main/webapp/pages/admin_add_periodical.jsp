<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Periodical adding</title>
</head>
<body>
<h2>Welcome to periodical adding, ${login}</h2>
<form method="post" action="/app/admin_add_periodical">
    <%--<input type="hidden" name="command" value="admin_add_periodical"/>--%>

    <input id="add_genre_id" type="hidden" name="add_genre_id" value=""/>
    <input id="add_publish_id" type="hidden" name="add_publish_id" value=""/>
    <input id="add_limited_options" type="hidden" name="add_limited_options" value=""/>

    Name:
    <br/>
    <input type="text" name="add_name" value=""/>
    <br/>
    Description:
    <br>
    <textarea name="add_desc" style="width: 100px; height: 50px;">

    </textarea>
    Subscription cost:
    <br/>
    <input type="number" step="0.01" min="0" name="add_sub_cost" value="">
    <br>
    Issues per year
    <br/>
    <input type="number" min="0" name="add_iss_per_year" value="">
    <br>
    Is limited?
    <br/>
    <select class="is_limited_options">
        <option value="true">Yes</option>
        <option value="false">No</option>
    </select>
    <br>
    <tr>
        <td>genre</td>
        <td>publisher</td>
    </tr>
    <tr>
        <td>
            <select class="genres_dd_list">
                <c:forEach items="${genres}" var="genre">
                    <option value="${genre.id}">${genre.name}</option>
                </c:forEach>
            </select>
        </td>
        <td>
            <select class="publishers_dd_list">
                <c:forEach items="${publishers}" var="publ">
                    <option value="${publ.id}">${publ.name}</option>
                </c:forEach>
            </select>
        </td>
    </tr>

    <input id="add-btn" type="submit" value="add periodical"/>

    <h4>${addingResultMessage}</h4>
</form>

<script>
    $(document).ready(function () {
        $(document).on("click", "#add-btn", function () {
            var parent = $("body");
            var selectGenre = $(parent).find('.genres_dd_list').val();
            var selectPublisher = $(parent).find('.publishers_dd_list').val();
            var selectLimit = $(parent).find('.is_limited_options').val();

            $("#add_limited_options").val(selectLimit);
            $("#add_genre_id").val(selectGenre);
            $("#add_publish_id").val(selectPublisher);
        });
    });
</script>
</body>
</html>
