<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bucket" scope="request" type="internetshop.model.Bucket"/>

<html>
<head>
    <title>Bucket</title>
    <style>

        body {
            margin: 0 auto;
            padding: 20px;
            width: 800px;
        }

        a.selected {
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
    </style>
</head>
<body>
<a class="selected" href="${pageContext.request.contextPath}/shop">Home</a>

<p><b> Bucket </b></p>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Product name</th>
        <th>Price</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="item" items="${bucket.items}">
        <tr>
            <td>
                <c:out value="${item.idItem}"/>
            </td>
            <td>
                <c:out value="${item.name}"/>
            </td>
            <td>
                <c:out value="${item.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/deleteBucket?item_id=${item.idItem}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>

<form action="${pageContext.request.contextPath}/servlet/order?id=${bucket.user.userId}" method="post">
    <button type="submit">Complete order</button>
</form>
<a class="selected" href="${pageContext.request.contextPath}/servlet/allItems">Continue shopping</a>
</body>
</html>
