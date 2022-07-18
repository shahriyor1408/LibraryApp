<%@ page import="java.util.Date" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Objects" %>
<%--
  Created by IntelliJ IDEA.
  User: jl
  Date: 7/12/2022
  Time: 10:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Admin Page</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <style>
        .user-logOut {
            display: inline-block;
            margin-bottom: 5px;
            font-family: Arial, serif;
        }

        .text {
            display: block;
        }
    </style>

</head>
<body>

<%--<section th:include="fragments.html :: navbar"></section>--%>

<div class="m-4">
    <div class="user-logOut">
        <a href="/logout" class="btn btn-warning user-logOut"> Logout </a> Welcome Admin Page ${username}
    </div>
    <!-- Button trigger modal -->
    <nav class="navbar navbar-light bg-light justify-content-between"
         style="height: 60px;">
        <button type="button" class="btn btn-success mb-4 text-white" data-toggle="modal" data-target="#exampleModal">
            ➕ Add
        </button>
        <form class="form-inline">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="search"
                   value="${search}">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search
            </button>
        </form>
    </nav>
    <%--   --%>

    <!-- Modal -->
    <div class="modal fade bd-example-modal-lg" id="exampleModal" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Save book</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" enctype='multipart/form-data' action="/bookAdd">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group mb-3">
                                    <label>Name</label>
                                    <input type="text" name="name" class="form-control" c:value=""
                                           placeholder="Book name"/>
                                </div>

                                <div class="form-group mb-3">
                                    <label>Author</label>
                                    <input type="text" name="author" class="form-control" placeholder="Book author"/>
                                </div>

                                <div class="form-group mb-3">
                                    <label>Description</label>
                                    <input type="text" name="description" class="form-control"
                                           placeholder="Book description"/>
                                </div>

                                <div class="form-group mb-3">
                                    <label>Genre</label>
                                    <select class="form-control" name="genre" id="genre">
                                        <c:forEach items="${genres}" var="genre">
                                            <option c:out value="${genre.name()}">${genre.getKey()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group mb-3">
                                    <label>Language</label>
                                    <select class="form-control" name="language" id="language">
                                        <c:forEach items="${languages}" var="language">
                                            <option c:out value="${language.name()}">${language.getValue()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group mb-3">
                                    <label>Page Count</label>
                                    <input type="number" name="pageCount" class="form-control"
                                           placeholder="Book pageCount"/>
                                </div>
                                <div class="form-group mb-3">
                                    <label>Cover</label>
                                    <input type="file" name="cover" class="form-control" placeholder="Book Cover"/>
                                </div>

                                <div class="form-group mb-3">
                                    <label>Book</label>
                                    <input type="file" name="file" class="form-control" placeholder="Book"/>
                                </div>

                                <button type="submit" class="btn btn-primary">save</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
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
                    <a href="<c:url value="/downloadCover?cover=${book.cover.path}"/>">Download Cover</a>
                    <br/>
                    <a href="<c:url value="/downloadFile?file=${book.file.path}"/>">Download Book</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<div style="margin-top: 10px">
    <nav aria-label="Page navigation example">
        <ul class="pagination" style="margin-left: 20px">

            <c:if test="${currentPage != 1}">
                <td class="page-item"><a class="page-link" href="book?page=${currentPage - 1}">Previous</a>
                </td>
            </c:if>
            <table>
                <tr>
                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <td class="page-item active"><a class="page-link" href="#">${i}</a></td>
                            </c:when>
                            <c:otherwise>
                                <td class="page-item"><a class="page-link" href="book?page=${i}">${i}</a></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
            </table>

            <%--For displaying Next link --%>
            <c:if test="${currentPage lt noOfPages}">
                <td class="page-item"><a class="page-link" href="book?page=${currentPage + 1}">Next</a></td>
            </c:if>

        </ul>
    </nav>
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
