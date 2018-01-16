<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Catalog</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>" type="text/css"/>
</head>
<body>
<jsp:include page="additional/header.jsp"/>
<jsp:include page="additional/cart.jsp"/>
<div id="products-wrapper" class="container">
    <h1>Periodicals</h1>
    <div class="row">
        <c:forEach var="periodical" items="${periodicals}">
            <div class="item col-md-6 col-sm-12">
                <h4 class="list-group-item-heading">
                    <a>${periodical.name}</a>
                </h4>
                <div class="row">
                    <div class="col-sm-6">
                        <label>Publisher</label>
                        <span>${periodical.publisher.name}</span>
                    </div>
                    <div class="col-sm-6">
                        <label>Genre</label>
                        <span>${periodical.genre.name}</span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6">
                        <label>Issues per Year</label>
                        <span>${periodical.issuesPerYear}</span>
                    </div>
                    <div class="col-sm-6">
                        <label>Is Limited</label>
                        <span>${periodical.isLimited}</span>
                    </div>
                </div>
                <p class="list-group-item-text">${periodical.description}</p>
                <div class="row">
                    <div class="col-sm-6">
                        <span class="lead">${periodical.subscriptionCost}</span>
                    </div>
                    <div class="col-sm-6">
                        <form class="list-group-item-button" method="post" action="add_to_cart">
                            <button name="perId" value="${periodical.id}" type="submit" class="btn btn-success">
                                <i class="fa fa-shopping-cart">&nbsp;</i> Add to Cart
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <jsp:include page="additional/pagination.jsp"/>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<c:url value='/js/catalog.js'/>"></script>
</body>
</html>
