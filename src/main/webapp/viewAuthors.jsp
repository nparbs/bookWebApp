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
        response.sendRedirect("AuthorController?task=View");
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
        <div class="container">
            <div class="row">
                <h1>All Authors</h1>
                <table class="table table-striped table-hover">
                    <tr>
                        <th>Author ID</th>
                        <th>Author Name</th>
                        <th>Date Added</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <c:forEach var="author" items="${authorList}">
                        <tr>
                        <form name="${author.authorId}" action="AuthorController?task=Edit" method="post">
                            <td name="id">${author.authorId}</td>
                            <td name="name">${author.authorName}</td>
                            <td name="date">${author.dateAdded}</td>
                            <td>
                                <button class="btn btn-info" name="id" type="submit" value=${author.authorId}>Edit</button>
                            </td>
                        </form>
                        <td>
                            <form action="AuthorController?task=Delete" method="post">
                                <button class="btn btn-danger" name="id" type="submit" value=${author.authorId}>Delete</button>
                            </form>
                        </td>
                        </tr>
                    </c:forEach>
                </table>

                <div class="col-lg-5">
                    <div style="margin-bottom: 25px;" ><a href="AuthorController?task=Find" class="btn btn-primary">Find author</a></div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-6">
                    <div class="well bs-component">
                        <form class="form-horizontal" action="AuthorController?task=Create" method="post">
                            <fieldset>
                                <legend>Create Author</legend>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="name">Name: </label>
                                    <div class="col-lg-10">
                                        <input type="text" name="name"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-lg-10 col-lg-offset-2">
                                        </br>
                                        <button class="btn btn-success" type="submit">Add New Author</button>
                                    </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>
