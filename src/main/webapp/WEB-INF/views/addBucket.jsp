<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bucket" scope="request" type="internetshop.model.Bucket"/>

<html>
<head>
    <title>Bucket</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/shop">Home</a>
<b>
    Bucket
</b>
<br>Here is a list of items:</br>
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

<form action="${pageContext.request.contextPath}/servlet/order?id=${bucket.idBucket}" method="post">
    <button type="submit">Complete order</button>
</form>
<a href="${pageContext.request.contextPath}/servlet/allItems">Continue shopping</a>
</body>
</html>
