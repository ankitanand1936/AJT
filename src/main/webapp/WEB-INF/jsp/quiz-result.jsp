<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Quiz Result"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<h1>Quiz result: <c:out value="${lesson.title}"/></h1>

<div class="card result-card ${result.passed ? 'result-pass' : 'result-fail'}">
    <p class="result-score">${result.score} / ${result.totalQuestions}</p>
    <p>${result.percentage}% (pass mark: ${passPercentage}%)</p>
    <c:choose>
        <c:when test="${result.passed}">
            <span class="badge badge-pass badge-big">PASSED</span>
        </c:when>
        <c:otherwise>
            <span class="badge badge-fail badge-big">FAILED</span>
        </c:otherwise>
    </c:choose>
    <p class="muted">This attempt has been saved to your results.</p>

    <c:if test="${result.passed and certificate != null}">
        <p>You have passed every lesson quiz - your certificate is ready!</p>
        <a class="btn btn-success" href="${ctx}/certificate">View my certificate</a>
    </c:if>
</div>

<h2>Answer review</h2>
<c:forEach var="q" items="${questions}" varStatus="status">
    <c:set var="chosen" value="${answers[q.id]}"/>
    <div class="card question">
        <h3>Q${status.count}. <c:out value="${q.questionText}"/></h3>
        <p>Your answer:
            <c:choose>
                <c:when test="${chosen == q.correctOption}">
                    <span class="text-pass"><c:out value="${chosen}"/>. <c:out value="${q.optionText(chosen)}"/> (correct)</span>
                </c:when>
                <c:otherwise>
                    <span class="text-fail">
                        <c:choose>
                            <c:when test="${empty chosen}">not answered</c:when>
                            <c:otherwise><c:out value="${chosen}"/>. <c:out value="${q.optionText(chosen)}"/></c:otherwise>
                        </c:choose>
                    </span>
                </c:otherwise>
            </c:choose>
        </p>
        <c:if test="${chosen != q.correctOption}">
            <p>Correct answer: <span class="text-pass"><c:out value="${q.correctOption}"/>. <c:out value="${q.optionText(q.correctOption)}"/></span></p>
        </c:if>
    </div>
</c:forEach>

<div class="actions">
    <a class="btn btn-outline" href="${ctx}/modules/${lesson.module.id}">Back to module</a>
    <a class="btn btn-primary" href="${ctx}/lessons/${lesson.id}/quiz">Try again</a>
    <a class="btn btn-outline" href="${ctx}/results">All my results</a>
</div>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
