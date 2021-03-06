<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="items" scope="request" type="java.util.List<internetshop.model.Item>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AllItems</title>
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
<p><h1>List of items:</h1>
</p>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Product name</th>
        <th>Price</th>
        <th>Buy</th>
    </tr>
    <c:forEach var="item" items="${items}">
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
                <a href="${pageContext.request.contextPath}/servlet/addToBucket?item_id=${item.idItem}">Buy</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p>
<a class="selected" href="${pageContext.request.contextPath}/servlet/item">Add items</a>
</p>
</body>
</html>
