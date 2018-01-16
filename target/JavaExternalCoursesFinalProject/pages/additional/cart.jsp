<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="cart">
    <h2>Cart</h2>
    <form method="post" action="add_to_cart">
        <ul class="cart-items list-unstyled">
            <c:forEach var="periodical" items="${sessionScope.cart.periodicals}">
                <li>
                        ${periodical.name}
                        <div class="cart-price">$${periodical.subscriptionCost}</div>
                        <button name="remId" type="submit" class="fa fa-remove cart-item-remove"
                                value="${periodical.id}"></button>
                </li>
            </c:forEach>
        </ul>
    </form>

    <div class="cart-total">
        <p>Total <span>$
            <c:if test="${sessionScope.cart.quantity gt 0}">
                <fmt:formatNumber value="${sessionScope.cart.quantity}" minFractionDigits="0"/>
            </c:if>
        </span></p>
    </div>

    <c:if test="${fn:length(sessionScope.cart.periodicals) gt 0}">
        <form method="post" action="subscribe">
            <input type="submit" class="btn btn-success btn-lg btn-block cart-checkout" value="Checkout">
        </form>
    </c:if>
</div>