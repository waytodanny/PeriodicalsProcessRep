<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="pagination">
    <c:choose>
        <c:when test="${currentPage != 1}">
            <li class="page-item">
                <a class="page-link" href="catalog?page=${currentPage - 1}">Previous</a>
            </li>
        </c:when>
        <c:otherwise>
            <li class="page-item disabled">
                <span class="page-link">Previous</span>
            </li>
        </c:otherwise>
    </c:choose>

    <c:forEach begin="1" end="${pagesCount}" var="i">
        <c:choose>
            <c:when test="${currentPage eq i}">
                <li class="page-item active">
                    <span class="page-link">
                            ${i}
                        <span class="sr-only">(current)</span>
                    </span>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <a class="page-link" href="catalog?page=${i}">${i}</a>
                </li>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:choose>
        <c:when test="${currentPage lt pagesCount}">
            <li class="page-item">
                <a class="page-link" href="catalog?page=${currentPage + 1}">Next</a>
            </li>
        </c:when>
        <c:otherwise>
            <li class="page-item disabled">
                <span class="page-link">Next</span>
            </li>
        </c:otherwise>
    </c:choose>
</ul>