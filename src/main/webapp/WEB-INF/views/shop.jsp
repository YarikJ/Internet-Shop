<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My shop</title>
    <style>
        body {
            margin: 0 auto;
            padding: 20px;
            width: 800px;
        }

        ul {
            list-style: none;
            padding: 0;
        }

        li {
            display: inline-block;
        }

        a {
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

        a:hover {
            background-color: green;
        }

        a.selected:hover {
            background-color: #1d6720;
        }
    </style>
</head>
<body>
<nav>
    <ul>
        <li>
            <a class="selected" href="${pageContext.request.contextPath}/registration">Registration</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/servlet/getAllUsers">View all users</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/servlet/item">Add item</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/servlet/allItems">View all products</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/servlet/bucket">Bucket</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/servlet/allOrders">All Orders</a>
        </li>
    </ul>
</nav>
<main>
    <h1>Welcome to our shop!</h1>
    <section>
        We have the best products and the best prices!
        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQX4pZWXiPM-wrw2dqDiCVB8YrKMlGbrAuvJc1SC29hAOvU97h9g&s"
             alt="Site main image">
    </section>
</main>
</body>
</html>
