<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Budget</title>
</head>
<body>
    <h2>Budget: ${budget.name}</h2>
    <div>
        <p>Part Budgets</p>
        <ul>
            <c:forEach items="${budget.partBudgets}" var="partBudget">
                <li>${partBudget.name}: ${partBudget.startDate} - ${partBudget.endDate}</li>
            </c:forEach>
        </ul>
    </div>

    <div>
        <p>Expenses for Categories</p>
        <ul>
            <c:forEach items="${expensesForCategories}" var="expenseForCategory">
                <li>${expenseForCategory.key}: ${expenseForCategory.value}</li>
            </c:forEach>
        </ul>
    </div>
</body>
</html>