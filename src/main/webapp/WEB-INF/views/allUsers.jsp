<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="users" scope="request" type="java.util.List<internetshop.model.User>"/>
<jsp:useBean id="greeting" scope="request" type="java.lang.String"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
<b>Hello, ${greeting}, Welcome to the All Users page!!!</b>

<br>Here is a list of users:</br>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.idUser}" />
            </td>
            <td>
                <c:out value="${user.name}" />
            </td>
            <td>
                <a href="/internetshopyarik_war_exploded/servlet/deleteUser?user_id=${user.idUser}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
