<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<%-- Login state comes from the servlet API, which Spring Security fills in (ROLE_ADMIN -> isUserInRole('ADMIN')) --%>
<c:set var="loggedIn" value="${pageContext.request.userPrincipal != null}" scope="request"/>
<c:set var="isAdmin" value="${pageContext.request.isUserInRole('ADMIN')}" scope="request"/>
<c:set var="isStudent" value="${pageContext.request.isUserInRole('STUDENT')}" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${pageTitle}"/> - LearnForge</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<nav class="navbar">
    <div class="nav-inner">
        <a class="brand" href="${ctx}/">Learn<span>Forge</span></a>
        <ul class="nav-links">
            <c:if test="${not loggedIn}">
                <li><a href="${ctx}/">Home</a></li>
                <li><a href="${ctx}/login">Login</a></li>
                <li><a href="${ctx}/register" class="nav-button">Register</a></li>
            </c:if>

            <c:if test="${isStudent}">
                <li><a href="${ctx}/dashboard">Dashboard</a></li>
                <li><a href="${ctx}/results">My Results</a></li>
                <li><a href="${ctx}/certificate">Certificate</a></li>
            </c:if>

            <c:if test="${isAdmin}">
                <li><a href="${ctx}/admin">Admin Panel</a></li>
            </c:if>

            <c:if test="${loggedIn}">
                <li class="nav-user"><c:out value="${pageContext.request.remoteUser}"/></li>
                <li>
                    <form action="${ctx}/logout" method="post" class="logout-form">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="nav-button">Logout</button>
                    </form>
                </li>
            </c:if>
        </ul>
    </div>
</nav>

<main class="container">
    <c:if test="${not empty message}">
        <div class="alert alert-success"><c:out value="${message}"/></div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error"><c:out value="${error}"/></div>
    </c:if>
