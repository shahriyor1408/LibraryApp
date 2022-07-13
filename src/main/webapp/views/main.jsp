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
    <title>Home Page</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>

<%--<%!--%>
<%--    String localname = "John";--%>
<%--%>--%>

<%--<%--%>
<%--    Date date = new Date();--%>
<%--    PrintWriter writer = response.getWriter();--%>
<%--    writer.println(date);--%>
<%--%>--%>

<%--<%=12 * 324%>--%>

<%--<h1>Hello--%>
<%--    <%--%>

<%--        String name = request.getParameter("name");--%>
<%--        if (Objects.isNull(name))--%>
<%--            writer.print(localname);--%>
<%--        else--%>
<%--            writer.print(name);--%>
<%--    %>--%>
<%--</h1>--%>

<a href="/logout" class="btn btn-success">
    Logout
</a>
${username}

<table class="table">
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Author</th>
    </tr>

    </thead>
    <tbody>
    <c:forEach items="${books}" var="book">
        <tr>
            <td>${book.id}</td>
            <td>${book.name}</td>
            <td>${book.author}</td>
        </tr>
    </c:forEach>

    </tbody>
</table>

</body>
</html>
