<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orders" scope="request" type="java.util.List"/>
<html>
<head>
    <title>All Orders</title>
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
<p><h1>List of orders:</h1>
</p>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Product Price</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.orderId}"/>
            </td>
            <td>
                <table>
                    <c:forEach var="item" items="${order.items}">
                        <tr>
                            <td>
                                <c:out value="${item.name}"/>
                            </td>
                            <td>
                                <c:out value="${item.price}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
