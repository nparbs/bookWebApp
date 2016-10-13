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
    Object author = request.getAttribute("author");
%>
<html>
    <head>
        <link rel="stylesheet" href="https://bootswatch.com/cyborg/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="main.css" rel="stylesheet" type="text/css"/>
        <title>All Authors</title>
    </head>
    <body>
        <div class="container">
            <h1>Edit Author</h1>

            <table class="table table-striped table-hover">
                <tr>
                    <th>Author ID</th>
                    <th>Author Name</th>
                    <th>Date Added</th>
                    <th></th>
                    <th></th>
                    <th></th>                       
                </tr>               
                <tr>
                <form action="AuthorController?task=Edit" method="post">
                    <td>${author.authorId}</td>
                    <td><input type="text" name="name" value="${author.authorName}" /></td>
                    <td name="date">${author.dateAdded}</td>
                    <td>
                        <button class="btn btn-warning" name="id" value="${author.authorId}"  type="submit">Confirm Edit</button>
                    </td>
                </form> 
                <td>
                    <form action="AuthorController?task=Delete" method="post">
                        <button class="btn btn-danger" name="id" type="submit" value=${author.authorId}>Delete</button>
                    </form>
                </td>
                <td>
                    <a class="btn btn-danger"href="AuthorController?task=View">Cancel</a>
                </td>
                </tr>
            </table>
            </br>
        </div>
    </body>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>
