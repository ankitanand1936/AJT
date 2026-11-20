<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="pageTitle" value="Dashboard"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<h1>Welcome, <c:out value="${user.fullName}"/></h1>

<fmt:formatNumber value="${totalQuizLessons == 0 ? 0 : passedLessons * 100 / totalQuizLessons}"
                  pattern="0" var="overallPct"/>

<div class="card">
    <h3>Your overall progress</h3>
    <p>You have passed <strong>${passedLessons}</strong> of <strong>${totalQuizLessons}</strong> lesson quizzes.</p>
    <div class="progress"><div class="progress-bar" style="width: ${overallPct}%"></div></div>

    <c:choose>
        <c:when test="${certificate != null}">
            <p class="spaced">Congratulations! Your certificate has been issued.</p>
            <a class="btn btn-success" href="${ctx}/certificate">View my certificate</a>
        </c:when>
        <c:otherwise>
            <p class="muted spaced">Pass every lesson quiz to receive your certificate.</p>
        </c:otherwise>
    </c:choose>
</div>

<h2>Java modules</h2>

<c:if test="${empty modules}">
    <p class="muted">No modules have been added yet.</p>
</c:if>

<div class="grid grid-2">
    <c:forEach var="m" items="${modules}">
        <c:set var="done" value="${empty passedPerModule[m.id] ? 0 : passedPerModule[m.id]}"/>
        <c:set var="total" value="${fn:length(m.lessons)}"/>
        <fmt:formatNumber value="${total == 0 ? 0 : done * 100 / total}" pattern="0" var="modulePct"/>

        <div class="card">
            <h3><c:out value="${m.title}"/></h3>
            <p><c:out value="${m.description}"/></p>
            <p class="muted">${done} of ${total} lessons completed</p>
            <div class="progress"><div class="progress-bar" style="width: ${modulePct}%"></div></div>
            <a class="btn btn-primary spaced" href="${ctx}/modules/${m.id}">Open module</a>
        </div>
    </c:forEach>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
