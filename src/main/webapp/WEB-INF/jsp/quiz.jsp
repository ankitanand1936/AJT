<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Quiz"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<p class="breadcrumb">
    <a href="${ctx}/lessons/${lesson.id}"><c:out value="${lesson.title}"/></a> &raquo; Quiz
</p>

<h1>Quiz: <c:out value="${lesson.title}"/></h1>
<p class="muted">Choose one answer for every question. You need at least ${passPercentage}% to pass.</p>

<form action="${ctx}/lessons/${lesson.id}/quiz" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <c:forEach var="q" items="${questions}" varStatus="status">
        <div class="card question">
            <h3>Q${status.count}. <c:out value="${q.questionText}"/></h3>

            <label class="option"><input type="radio" name="q_${q.id}" value="A" required>
                <span><strong>A.</strong> <c:out value="${q.optionA}"/></span></label>
            <label class="option"><input type="radio" name="q_${q.id}" value="B" required>
                <span><strong>B.</strong> <c:out value="${q.optionB}"/></span></label>
            <label class="option"><input type="radio" name="q_${q.id}" value="C" required>
                <span><strong>C.</strong> <c:out value="${q.optionC}"/></span></label>
            <label class="option"><input type="radio" name="q_${q.id}" value="D" required>
                <span><strong>D.</strong> <c:out value="${q.optionD}"/></span></label>
        </div>
    </c:forEach>

    <div class="actions">
        <a class="btn btn-outline" href="${ctx}/lessons/${lesson.id}">Back to lesson</a>
        <button type="submit" class="btn btn-primary">Submit answers</button>
    </div>
</form>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
