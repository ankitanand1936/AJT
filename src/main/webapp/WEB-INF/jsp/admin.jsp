<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Admin Panel"/>
<%@ include file="/WEB-INF/jsp/fragments/header.jsp" %>

<h1>Admin panel</h1>

<div class="grid grid-2">
    <!-- Add module -->
    <div class="card">
        <h3>Add a module</h3>
        <form action="${ctx}/admin/modules" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <label for="mTitle">Title</label>
            <input type="text" id="mTitle" name="title" maxlength="150" required>

            <label for="mDesc">Description</label>
            <textarea id="mDesc" name="description" rows="3" maxlength="500" required></textarea>

            <button type="submit" class="btn btn-primary">Add module</button>
        </form>
    </div>

    <!-- Add lesson -->
    <div class="card">
        <h3>Add a lesson to a module</h3>
        <c:choose>
            <c:when test="${empty modules}">
                <p class="muted">Add a module first.</p>
            </c:when>
            <c:otherwise>
                <form action="${ctx}/admin/lessons" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <label for="lModule">Module</label>
                    <select id="lModule" name="moduleId" required>
                        <c:forEach var="m" items="${modules}">
                            <option value="${m.id}"><c:out value="${m.title}"/></option>
                        </c:forEach>
                    </select>

                    <label for="lTitle">Lesson title</label>
                    <input type="text" id="lTitle" name="title" maxlength="150" required>

                    <label for="lOrder">Order in module (optional)</label>
                    <input type="number" id="lOrder" name="lessonOrder" min="1">

                    <label for="lContent">Lesson content</label>
                    <textarea id="lContent" name="content" rows="6" required></textarea>

                    <button type="submit" class="btn btn-primary">Add lesson</button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<h2>Existing modules and lessons</h2>

<c:if test="${empty modules}">
    <p class="muted">No modules yet.</p>
</c:if>

<c:forEach var="m" items="${modules}">
    <div class="card">
        <h3><c:out value="${m.title}"/></h3>
        <p class="muted"><c:out value="${m.description}"/></p>

        <c:choose>
            <c:when test="${empty m.lessons}">
                <p class="muted">No lessons in this module yet.</p>
            </c:when>
            <c:otherwise>
                <div class="table-wrap">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Order</th>
                            <th>Lesson</th>
                            <th>Questions</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="l" items="${m.lessons}">
                            <tr>
                                <td>${l.lessonOrder}</td>
                                <td><c:out value="${l.title}"/></td>
                                <td>${l.questions.size()}</td>
                                <td><a class="btn btn-small btn-outline" href="${ctx}/admin/lessons/${l.id}">View / add questions</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</c:forEach>

<%@ include file="/WEB-INF/jsp/fragments/footer.jsp" %>
