<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Error - LearnForge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="nav-inner">
        <a class="brand" href="${pageContext.request.contextPath}/">Learn<span>Forge</span></a>
    </div>
</nav>
<main class="container">
    <div class="form-card center-text">
        <h2>
            <c:choose>
                <c:when test="${status == 403}">Access denied</c:when>
                <c:when test="${status == 404}">Page not found</c:when>
                <c:otherwise>Something went wrong</c:otherwise>
            </c:choose>
        </h2>
        <p class="muted">
            <c:choose>
                <c:when test="${status == 403}">You do not have permission to open this page.</c:when>
                <c:when test="${status == 404}">The page or item you asked for does not exist.</c:when>
                <c:otherwise>An unexpected error occurred (status <c:out value="${status}"/>).</c:otherwise>
            </c:choose>
        </p>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/">Back to home</a>
    </div>
</main>
</body>
</html>
