
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add issue</title>
</head>
<body>
Here we add issues
<form method="post">
    <input type="hidden" name="command" value="admin_add_periodical"/>

    Name:
    <br/>
    <input type="text" name="add_name" value=""/>
    <br/>
    Issue no
    <br/>
    <input type="text" name="add_no" value=""/>
    <br/>
    Periodical:
    <br/>
    <input type="text" name="add_name" value="${periodical_name}" readonly/>
    <br/>

    <input id="add-btn" type="submit" value="add periodical"/>

    <h4>${addingResultMessage}</h4>
</form>

</body>
</html>
