<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="My Results"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<h1>My quiz results</h1>

<c:choose>
    <c:when test="${empty results}">
        <p class="muted">You have not attempted any quiz yet.
            <a href="${ctx}/dashboard">Go to the dashboard</a> and start learning.</p>
    </c:when>
    <c:otherwise>
        <div class="table-wrap">
            <table class="table">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Module</th>
                    <th>Lesson</th>
                    <th>Score</th>
                    <th>%</th>
                    <th>Result</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="r" items="${results}">
                    <tr>
                        <td>${r.attemptedAtText}</td>
                        <td><c:out value="${r.lesson.module.title}"/></td>
                        <td><a href="${ctx}/lessons/${r.lesson.id}"><c:out value="${r.lesson.title}"/></a></td>
                        <td>${r.score} / ${r.totalQuestions}</td>
                        <td>${r.percentage}%</td>
                        <td>
                            <c:choose>
                                <c:when test="${r.passed}"><span class="badge badge-pass">Passed</span></c:when>
                                <c:otherwise><span class="badge badge-fail">Failed</span></c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
