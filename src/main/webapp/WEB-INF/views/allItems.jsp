<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="items" scope="request" type="java.util.List<internetshop.model.Item>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AllItems</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/shop">Home</a>
<br>Here is a list of users:</br>
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
                <a href="${pageContext.request.contextPath}/servlet/bucket?item_id=${item.idItem}">Buy</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/servlet/item">Add items</a>
</body>
</html>