<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<jsp:useBean id="_csrf" scope="request" type="org.springframework.security.web.csrf.CsrfToken"/>

<html>
<head>
    <title>Login Page</title>
    <style>
        .error {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 1px;
            color: #a94442;
            background-color: #edccd1;
            border: 1px solid #edccd1;
        }

        .msg {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
            color: #31708f;
            background-color: #d9edf7;
            border: 1px solid #bce8f1;
        }

        #login-box {
            width: 350px;
            padding: 20px;
            margin: 100px auto;
            background: #fff;
            -webkit-border-radius: 22px;
            -moz-border-radius: 2px;
            border: 1px solid #000;
        }
    </style>
</head>
<body onLoad="document.loginForm.username.focus();">

    <h1>Money-Manager</h1>

    <div id="login-box">

        <h3>Login with username and password</h3>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="msg">${message}</div>
        </c:if>

        <c:url value="/login" var="loginUrl"/>
        <form name="loginForm" action="${loginUrl}" method="POST">

            <table>
                <tr>
                    <td>
                        <input type="text" name="username" title="User:"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="password" name="password" title="Password:"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" name="submit" value="submit"/></td>
                </tr>
            </table>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>

</body>
</html>
