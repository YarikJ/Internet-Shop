<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="order" scope="request" type="internetshop.model.Order"/>
<html>
<head>
    <title>Order</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/shop">Home</a>
<b>
    Thank you for your order! We will call you in a few minutes
</b>
<br>Ordered items</br>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Product name</th>
        <th>Price</th>
    </tr>
    <c:forEach var="item" items="${order.items}">
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
        </tr>
    </c:forEach>
</table>
</body>
</html>
