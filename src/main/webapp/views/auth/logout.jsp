<%--
  Created by IntelliJ IDEA.
  User: jl
  Date: 7/12/2022
  Time: 11:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Logout Page
    </title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

</head>
<body>
<div class="row">
    <div class="col-6 offset-3">
        <h2>Are you sure log out</h2>
        <form method="post">
            <button type="submit" class="btn btn-danger">Yes</button>
            <a href="/" class="btn btn-info">Back</a>
        </form>
    </div>
</div>
</body>
</html>
