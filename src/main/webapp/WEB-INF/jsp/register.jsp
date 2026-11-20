<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Register - LearnForge</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<nav class="navbar">
    <div class="nav-inner">
        <a class="brand" href="${ctx}/">Learn<span>Forge</span></a>
        <ul class="nav-links">
            <li><a href="${ctx}/">Home</a></li>
            <li><a href="${ctx}/login">Login</a></li>
            <li><a href="${ctx}/register" class="nav-button">Register</a></li>
        </ul>
    </div>
</nav>

<main class="container">
<div class="auth-wrap">

    <!-- Decorative side panel -->
    <aside class="auth-panel">
        <div class="auth-bubble b1"></div>
        <div class="auth-bubble b2"></div>
        <div class="auth-bubble b3"></div>
        <div class="auth-panel-content">
            <div class="auth-code">{ java }</div>
            <h2>Start learning Java</h2>
            <p>Create a free student account and begin with your first lesson in minutes.</p>
            <ul class="auth-points">
                <li>Read lessons module by module</li>
                <li>Test yourself with MCQ quizzes</li>
                <li>Track your progress and results</li>
                <li>Earn your LearnForge certificate</li>
            </ul>
        </div>
    </aside>

    <!-- Form -->
    <section class="auth-form">
        <h1>Create account</h1>
        <p class="auth-sub">It only takes a minute.</p>

        <form action="${ctx}/register" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <label for="fullName">Full name</label>
            <div class="input-group">
                <span class="input-icon">&#128100;</span>
                <input type="text" id="fullName" name="fullName" maxlength="100" placeholder="Your full name" required
                       value="<c:out value='${fullName}'/>">
            </div>

            <label for="email">Email</label>
            <div class="input-group">
                <span class="input-icon">&#9993;</span>
                <input type="email" id="email" name="email" maxlength="150" placeholder="you@example.com" required
                       value="<c:out value='${email}'/>">
            </div>

            <label for="password">Password <span class="label-hint">(min. 6 characters)</span></label>
            <div class="input-group">
                <span class="input-icon">&#128274;</span>
                <input type="password" id="password" name="password" minlength="6" placeholder="Create a password" required>
                <button type="button" class="toggle-pw" data-target="password">Show</button>
            </div>

            <label for="confirmPassword">Confirm password</label>
            <div class="input-group">
                <span class="input-icon">&#128274;</span>
                <input type="password" id="confirmPassword" name="confirmPassword" minlength="6" placeholder="Repeat your password" required>
                <button type="button" class="toggle-pw" data-target="confirmPassword">Show</button>
            </div>

            <button type="submit" class="btn btn-primary btn-block btn-lg">Create account</button>
        </form>

        <p class="form-note">Already registered? <a href="${ctx}/login">Login</a></p>
    </section>
</div>

<script>
    document.querySelectorAll('.toggle-pw').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var input = document.getElementById(btn.dataset.target);
            var show = input.type === 'password';
            input.type = show ? 'text' : 'password';
            btn.textContent = show ? 'Hide' : 'Show';
        });
    });
</script>

</main>

<footer class="footer">
    <p>LearnForge - Interactive Java Programming Learning &amp; Assessment Platform</p>
</footer>

</body>
</html>
