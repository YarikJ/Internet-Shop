<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log In</title>
    <style>

        body {
            margin: 0 auto;
            padding: 20px;
            width: 800px;
        }

        a.selected, button {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 15px 25px;
            text-align: center;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
            user-select: none;
        }

        a.selected, button:hover {
            background-color: green;
        }
    </style>
</head>
<body>
<a class="selected" href="${pageContext.request.contextPath}/shop">Home</a>
<form action="${pageContext.request.contextPath}/login" method="post">
    <div class="container">
        <h1>Sign in</h1>
        <div>${errorMsg}</div>

        <label for="name"><b>Name</b></label>
        <input type="text" placeholder="Enter Name" name="name" id="name" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" id="psw" required>

        <button type="submit">Sign in</button>
    </div>
    <div>
        <p>Don't have an account? <a href="${pageContext.request.contextPath}/registration">Sign up</a></p>
    </div>
</form>
</body>
</html>
