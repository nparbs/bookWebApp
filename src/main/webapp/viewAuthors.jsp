<%-- 
    Document   : viewAuthors
    Created on : Sep 19, 2016, 4:07:38 PM
    Author     : Nick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%
    Object obj = request.getAttribute("authorList");
    if (obj == null) {
        response.sendRedirect("AuthorController");
    }
%>
<html>
    <head>
        <link rel="stylesheet" href="https://bootswatch.com/cyborg/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="main.css" rel="stylesheet" type="text/css"/>
        <title>All Authors</title>
    </head>
    <body>
        <h1>All Authors</h1>

        <table class="table">
            <tr>

                <th>Author ID</th>
                <th>Author Name</th>
                <th>Date Added</th>
            </tr>
            <c:forEach var="author" items="${authorList}">
                <tr>
                    <td>${author.authorId}</td>
                    <td>${author.authorName}</td>
                    <td>${author.dateAdded}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>
