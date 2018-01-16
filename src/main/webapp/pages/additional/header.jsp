<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="overlay"></div>

<header>
    <div id="hamburger-menu">
        <i class="fa fa-navicon"></i></div>
    <div id="cart-button">
        <i class="fa fa-shopping-cart"></i></div>
</header>

<nav id="sidebar" role="navigation">


    <a class="btn btn-success navbar-button" href="">
        <i class="fa fa-user-circle-o" aria-hidden="true"></i> Register
    </a>
    <a class="btn btn-info navbar-button" href="">
        <i class="fa fa-sign-in" aria-hidden="true"></i> Login
    </a>

    <a class="btn btn-secondary navbar-button" href="">
        <i class="fa fa-sign-out" aria-hidden="true"></i> Sign out
    </a>
    <a class="btn btn-warning navbar-button" href="">
        <i class="fa fa-book" aria-hidden="true"></i> Subscribes
    </a>
    
    <h2>Genres</h2>
    <ul class="nav sidebar-nav">
        <c:forEach var="genre" items="${genres}">
            <li><a href="catalog?genre=${genre.name}">${genre.name}</a></li>
        </c:forEach>
    </ul>
</nav>