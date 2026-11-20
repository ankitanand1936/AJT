<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Home"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<section class="hero">
    <h1>Learn Java. Take quizzes. Earn your certificate.</h1>
    <p>LearnForge is an interactive Java programming learning and assessment platform.
        Read short lessons, test yourself with multiple-choice quizzes and collect a
        certificate when you have passed every lesson.</p>

    <div class="hero-buttons">
        <c:if test="${not loggedIn}">
            <a class="btn btn-primary" href="${ctx}/register">Create a free account</a>
            <a class="btn btn-outline" href="${ctx}/login">Login</a>
        </c:if>
        <c:if test="${isStudent}">
            <a class="btn btn-primary" href="${ctx}/dashboard">Go to my dashboard</a>
        </c:if>
        <c:if test="${isAdmin}">
            <a class="btn btn-primary" href="${ctx}/admin">Open admin panel</a>
        </c:if>
    </div>
</section>

<section class="grid grid-3">
    <div class="card">
        <h3>1. Read lessons</h3>
        <p>Java is split into modules. Each module contains short, focused lessons.</p>
    </div>
    <div class="card">
        <h3>2. Take MCQ quizzes</h3>
        <p>Every lesson has a quiz. Your marks are calculated automatically and every attempt is saved.</p>
    </div>
    <div class="card">
        <h3>3. Earn a certificate</h3>
        <p>Pass the quiz of every lesson and your LearnForge certificate is issued automatically.</p>
    </div>
</section>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
