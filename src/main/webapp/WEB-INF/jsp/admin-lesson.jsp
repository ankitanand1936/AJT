<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Lesson Questions"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<p class="breadcrumb">
    <a href="${ctx}/admin">Admin panel</a> &raquo;
    <c:out value="${lesson.module.title}"/> &raquo;
    <c:out value="${lesson.title}"/>
</p>

<h1><c:out value="${lesson.title}"/></h1>
<div class="card lesson-content"><c:out value="${lesson.content}"/></div>

<h2>Questions (${questions.size()})</h2>

<c:if test="${empty questions}">
    <p class="muted">No questions yet. Students cannot take a quiz for this lesson until you add some.</p>
</c:if>

<c:forEach var="q" items="${questions}" varStatus="status">
    <div class="card question">
        <h3>Q${status.count}. <c:out value="${q.questionText}"/></h3>
        <ul class="option-list">
            <li class="${q.correctOption == 'A' ? 'text-pass' : ''}">A. <c:out value="${q.optionA}"/></li>
            <li class="${q.correctOption == 'B' ? 'text-pass' : ''}">B. <c:out value="${q.optionB}"/></li>
            <li class="${q.correctOption == 'C' ? 'text-pass' : ''}">C. <c:out value="${q.optionC}"/></li>
            <li class="${q.correctOption == 'D' ? 'text-pass' : ''}">D. <c:out value="${q.optionD}"/></li>
        </ul>
        <p class="muted">Correct answer: <strong><c:out value="${q.correctOption}"/></strong></p>
    </div>
</c:forEach>

<div class="card">
    <h3>Add an MCQ question</h3>
    <form action="${ctx}/admin/lessons/${lesson.id}/questions" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <label for="qText">Question</label>
        <textarea id="qText" name="questionText" rows="2" maxlength="500" required></textarea>

        <label for="oA">Option A</label>
        <input type="text" id="oA" name="optionA" maxlength="255" required>
        <label for="oB">Option B</label>
        <input type="text" id="oB" name="optionB" maxlength="255" required>
        <label for="oC">Option C</label>
        <input type="text" id="oC" name="optionC" maxlength="255" required>
        <label for="oD">Option D</label>
        <input type="text" id="oD" name="optionD" maxlength="255" required>

        <label for="correct">Correct option</label>
        <select id="correct" name="correctOption" required>
            <option value="A">A</option>
            <option value="B">B</option>
            <option value="C">C</option>
            <option value="D">D</option>
        </select>

        <button type="submit" class="btn btn-primary">Add question</button>
    </form>
</div>

<a class="btn btn-outline" href="${ctx}/admin">Back to admin panel</a>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
