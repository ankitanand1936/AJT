<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Certificate"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<c:choose>
    <c:when test="${certificate != null}">
        <div class="certificate">
            <p class="cert-small">LearnForge</p>
            <h1>Certificate of Completion</h1>
            <p>This is to certify that</p>
            <p class="cert-name"><c:out value="${user.fullName}"/></p>
            <p>has successfully passed the quiz of every lesson in the<br>
                <strong>Java Programming</strong> course on LearnForge.</p>
            <div class="cert-footer">
                <span>Issued on: <strong>${certificate.issuedAtText}</strong></span>
                <span>Certificate ID: <strong><c:out value="${certificate.certificateCode}"/></strong></span>
            </div>
        </div>
        <div class="actions center">
            <button type="button" class="btn btn-primary" onclick="window.print()">Print certificate</button>
            <a class="btn btn-outline" href="${ctx}/dashboard">Back to dashboard</a>
        </div>
    </c:when>
    <c:otherwise>
        <h1>Certificate</h1>
        <fmt:formatNumber value="${totalQuizLessons == 0 ? 0 : passedLessons * 100 / totalQuizLessons}"
                          pattern="0" var="pct"/>
        <div class="card">
            <p>Your certificate is not available yet. You must pass the quiz of <strong>every</strong> lesson.</p>
            <p>Progress: <strong>${passedLessons}</strong> of <strong>${totalQuizLessons}</strong> lesson quizzes passed.</p>
            <div class="progress"><div class="progress-bar" style="width: ${pct}%"></div></div>
            <a class="btn btn-primary spaced" href="${ctx}/dashboard">Continue learning</a>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
