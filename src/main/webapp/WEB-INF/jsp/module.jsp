<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Module"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<p class="breadcrumb"><a href="${ctx}/dashboard">Dashboard</a> &raquo; <c:out value="${module.title}"/></p>

<h1><c:out value="${module.title}"/></h1>
<p><c:out value="${module.description}"/></p>

<h2>Lessons</h2>

<c:if test="${empty module.lessons}">
    <p class="muted">This module has no lessons yet.</p>
</c:if>

<c:forEach var="l" items="${module.lessons}" varStatus="status">
    <div class="card lesson-row">
        <div>
            <h3>${status.count}. <c:out value="${l.title}"/></h3>
            <c:choose>
                <c:when test="${passedIds.contains(l.id)}">
                    <span class="badge badge-pass">Completed</span>
                </c:when>
                <c:when test="${empty l.questions}">
                    <span class="badge badge-muted">No quiz yet</span>
                </c:when>
                <c:otherwise>
                    <span class="badge badge-warn">Not completed</span>
                </c:otherwise>
            </c:choose>
        </div>
        <a class="btn btn-primary" href="${ctx}/lessons/${l.id}">Read lesson</a>
    </div>
</c:forEach>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
