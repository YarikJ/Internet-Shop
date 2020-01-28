<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Something went wrong</title>
</head>
<body>
<h1>Sorry, some problems occurred during processing your request :(</h1>

<div> <b>${msg}</b></div>

<p>
    <a class="selected" href="${pageContext.request.contextPath}/shop">Go to the main page</a>
</p>

</body>
</html>
