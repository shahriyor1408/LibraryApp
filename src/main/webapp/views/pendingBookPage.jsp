<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Objects" %>
<%--
  Created by IntelliJ IDEA.
  User: shahriyor
  Date: 18/07/22
  Time: 07:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PendingBookPage</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div class="m-4">
    <div class="user-logOut">
        <a href="/logout" class="btn btn-warning user-logOut"> Logout </a> Welcome ${username}
    </div>
    <!-- Button trigger modal -->
    <nav class="navbar navbar-light bg-light justify-content-between"
         style="height: 60px;">
        <a type="button" class="btn btn-danger mb-4 text-white" href="/">
            Back
        </a>
    </nav>
</div>

<div class="row m-4">
    <c:forEach items="${books}" var="book">
        <div class="col-2">
            <div class="card p-2" style="background-color: #e5e3e3">
                <img class="card-img-top" src="<c:url value="/showCover?img=${book.cover.path}"/>" width="140"
                     height="250"
                     alt="${book.name}">
                <div class="card-body">
                    <h5 class="card-title" c:value="${book.name}">${book.name}</h5>
                    <i class="text">author : <i>${book.author}</i></i>
                    <i class="text">description : <i>${book.description}</i></i>
                    <i class="text">genre : <i>${book.genre.getKey()}</i></i>
                    <i class="text">language : <i>${book.language.getValue()}</i></i>
                    <i class="text">pageCount : <i>${book.pageCount}</i></i>
                    <i class="text">downloadCount : <i>${book.downloadCount}</i></i>
                    <br>
                    <form method="post" action="<c:url value="/adminPage?id=${book.id}"/>">
                        <button class="btn btn-success mb-4 text-white">Confirm book</button>
                    </form>
                    <form method="post" action="<c:url value="/pendingBooks?id=${book.id}"/>" style="margin-top: 1px">
                        <button class="btn btn-danger mb-4 text-white">Deny Book</button>
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>
