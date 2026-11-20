<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Lesson"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<p class="breadcrumb">
    <a href="${ctx}/dashboard">Dashboard</a> &raquo;
    <a href="${ctx}/modules/${lesson.module.id}"><c:out value="${lesson.module.title}"/></a> &raquo;
    <c:out value="${lesson.title}"/>
</p>

<h1><c:out value="${lesson.title}"/></h1>

<c:if test="${completed}">
    <span class="badge badge-pass">You have passed this lesson's quiz</span>
</c:if>

<div class="card lesson-content"><c:out value="${lesson.content}"/></div>

<div class="actions">
    <a class="btn btn-outline" href="${ctx}/modules/${lesson.module.id}">Back to module</a>
    <c:choose>
        <c:when test="${empty lesson.questions}">
            <span class="muted">A quiz for this lesson has not been added yet.</span>
        </c:when>
        <c:otherwise>
            <a class="btn btn-primary" href="${ctx}/lessons/${lesson.id}/quiz">
                <c:out value="${completed ? 'Retake quiz' : 'Take quiz'}"/>
            </a>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
