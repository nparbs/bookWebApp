<%-- 
    Document   : bookReport
    Created on : Nov 21, 2016, 8:10:50 PM
    Author     : Nick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%
    HttpSession sess = request.getSession();
    if (sess != null) {

    }

%>
<html>
    <head>
        <link rel="stylesheet" href="https://bootswatch.com/cyborg/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="main.css" rel="stylesheet" type="text/css"/>
        <title>Search Books</title>
    </head>
    <body>
        <div class="container">
            <jsp:include page="timeHeader.jsp"/>
            
            
            <div class="row">
                <div class="col-lg-10">
                    <div class="well bs-component">
                        <form class="form-horizontal" action="BookController?task=Search" method="post"><fieldset>
                                <legend></legend>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="title">Book Title: </label>
                                    <div class="col-md-4">
                                        <input type="text" name="title"/>
                                    </div>

                                    <label class="col-md-2 control-label" for="name">Author Name: </label>
                                    <div class="col-md-4">
                                        <input type="text" name="name"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group">
                                    <div class="col-lg-2 col-lg-offset-5">
                                        <button class="btn btn-success" type="submit">Search Books</button>
                                    </div>
                            </fieldset></form>
                    </div>
                </div>
            </div>
            
            
            <div class="row">
                <h1>Search Books ${auth}</h1>
                <table class="table table-striped table-hover">
                    <tr>
                        <th>Book ID</th>
                        <th>Book Title</th>
                        <th>ISBN</th>
                        <th>Author</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <c:forEach var="book" items="${bookList}">
                        <tr>
                            <td>${book.bookId}</td>
                            <td>${book.title}</td>
                            <td>${book.isbn}</td>
                            <td>${book.authorId.authorName} - ID: ${book.authorId.authorId}</td>
                            <td>
                                <form action="BookController?task=Edit" method="post">
                                    <button class="btn btn-info" name="id" type="submit" value="${book.bookId}">Edit</button>
                                </form>
                            </td>
                            <td>
                                <form action="BookController?task=Delete" method="post">
                                    <button class="btn btn-danger" name="id" type="submit" value="${book.bookId}">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <br>
            <br>
            
        </div>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
    
</html>
