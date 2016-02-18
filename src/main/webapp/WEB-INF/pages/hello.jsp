<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="_csrf" scope="request" type="org.springframework.security.web.csrf.CsrfToken"/>

<html>
<body>
    <h1>${title}</h1>

    <sec:authorize access="hasRole('ROLE_USER')">

        <c:url value="/logout" var="logoutUrl" />
        <form action="${logoutUrl}" method="post" id="logoutForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
        <script>
            function formSubmit() {
                document.getElementById("logoutForm").submit();
            }
        </script>

        <c:if test="${pageContext.request.userPrincipal.name != null}">
            <h2>
                User : ${pageContext.request.userPrincipal.name} | <a href="javascript:formSubmit()">Logout</a>
            </h2>
        </c:if>
    </sec:authorize>
</body>
</html>
