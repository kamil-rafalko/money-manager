<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
    <tr>
        <th>

        </th>
    </tr>
<c:forEach var="budget" items="${budgets}">
    <tr>
        <td>
            <a href="<c:url value="/budget/${budget.id}" />">${budget.name}</a>
        </td>
    </tr>
</c:forEach>
</table>
