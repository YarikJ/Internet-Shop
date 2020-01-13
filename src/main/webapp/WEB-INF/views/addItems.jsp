<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add items</title>
    <style>
        a {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 15px 25px;
            text-align: center;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
        }

        a:hover {
            background-color: green;
        }

        button {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 15px 25px;
            text-align: center;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
        }
    </style>
</head>
<body>
<a href="${pageContext.request.contextPath}/shop">Home</a>
<form action="${pageContext.request.contextPath}/servlet/item" method="post">
    <div class="container">
        <h1>Add new item</h1>
        <p>Please fill in this form to add an item</p>
        <hr>

        <label for="name"><b>Name</b></label>
        <input type="text" placeholder="Enter product name" name="name" id="name" required>

        <label for="price"><b>Price</b></label>
        <input type="text" placeholder="Enter price" name="price" id="price" required>
        <hr>

        <button type="submit" class="registerbtn">ADD</button>
    </div>
</form>
</body>
</html>
