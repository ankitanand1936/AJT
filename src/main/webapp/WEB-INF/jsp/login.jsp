<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login - LearnForge</title>
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
            <div class="auth-code">&lt;/&gt;</div>
            <h2>Welcome back!</h2>
            <p>Pick up your Java journey right where you left off.</p>
            <ul class="auth-points">
                <li>Short, focused lessons</li>
                <li>Instant quiz marking</li>
                <li>Certificate on completion</li>
            </ul>
        </div>
    </aside>

    <!-- Form -->
    <section class="auth-form">
        <h1>Login</h1>
        <p class="auth-sub">Enter your details to access your dashboard.</p>

        <c:if test="${param.error != null}">
            <div class="alert alert-error">Invalid email or password.</div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-success">You have been logged out.</div>
        </c:if>

        <form action="${ctx}/login" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <label for="email">Email</label>
            <div class="input-group">
                <span class="input-icon">&#9993;</span>
                <input type="email" id="email" name="email" placeholder="you@example.com" required autofocus>
            </div>

            <label for="password">Password</label>
            <div class="input-group">
                <span class="input-icon">&#128274;</span>
                <input type="password" id="password" name="password" placeholder="Your password" required>
                <button type="button" class="toggle-pw" data-target="password">Show</button>
            </div>

            <button type="submit" class="btn btn-primary btn-block btn-lg">Login</button>
        </form>

        <p class="form-note">New student? <a href="${ctx}/register">Create an account</a></p>
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
