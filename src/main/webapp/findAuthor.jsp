<%-- 
    Document   : findAuthor
    Created on : Sep 26, 2016, 7:37:23 PM
    Author     : Nick
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>



<html>
    <head>
        <link rel="stylesheet" href="https://bootswatch.com/cyborg/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="main.css" rel="stylesheet" type="text/css"/>
        <title>Find Author</title>
    </head>
    <body>
        <div class="container">
            <h1>Find Author by Id</h1>
            <form action="AuthorController?task=Find" method="post">
                Author Id: <input type="text" name="id"/>
                <button class="btn btn-success" type="submit">Find Author</button>
            </form>

            <% Object author = request.getAttribute("author");
            Object failed = request.getAttribute("failed");
                if (author != null) { %>

            <table class="table table-striped table-hover">
                <tr>

                    <th>Author ID</th>
                    <th>Author Name</th>
                    <th>Date Added</th>
                    <th></th>
                    <th></th>
                </tr>
                    <tr>
                        <form action="AuthorController?task=Edit" method="post">
                        <td name="id">${author.authorId}</td>
                        <td name="name">${author.authorName}</td>
                        <td name="date">${author.dateAdded}</td>
                        <td>
                            <button class="btn btn-danger" name="id"type="submit" value=${author.authorId}>Edit</button>
                        </td>
                        </form>
                        <form action="AuthorController?task=Delete" method="post">
                        <td>
                        <button class="btn btn-danger" name="id"type="submit" value=${author.authorId}>Delete</button>
                        </td>
                        </form>
                    </tr>
                   
            </table>
                    
            <%    }
                if(failed !=null){ %>
                
            <p class="well">Failed to find Author</p>
            
            <% } %>
            
            <h4>Add Author</h4>
            <form action="AuthorController?task=Create" method="post">
                Name: <input type="text" name="name"/>
                <button class="btn btn-success" type="submit">Add New Author</button>
            </form>

            <!--<div class="btn btn-success"><a href="index.html"></a>Back to home</div>-->

        </div>
    </body>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>
