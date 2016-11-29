<%-- 
    Document   : viewBooks
    Created on : Nov 2, 2016, 9:11:03 PM
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
        Object books = request.getAttribute("bookList");
        if (books == null) {
            response.sendRedirect("BookController");
        }

    }

//<jsp:include page="timeHeader.jsp"/>
%>
<html>
    <head>
        <link rel="stylesheet" href="https://bootswatch.com/cyborg/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="main.css" rel="stylesheet" type="text/css"/>
        <title>All Books</title>
    </head>
    <body>
        <div class="container">
            <jsp:include page="timeHeader.jsp"/>
            <div class="row">
                <h1>All Books</h1>
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

                <div class="col-lg-5">
                    <form action="BookController?task=Find" method="post">
                        <div class="form-group">
                            <label class="col-lg-3 control-label" for="id">Book Id:</label>
                            <div class="col-lg-5">
                                <input type="text" name="id"/>
                            </div>
                        </div>
                        <br>
                        <div style="margin: 25px;"><button type="submit" class="btn btn-primary">Find Book</button></div>
                    </form>
                </div>
            </div>
            <br>
            <br>
            <div class="row">
                <div class="col-lg-6">
                    <div class="well bs-component">
                        <form class="form-horizontal" action="BookController?task=Create" method="post">
                            <fieldset>
                                <legend>Create Book</legend>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="title">Title</label>
                                    <div class="col-lg-10">
                                        <input type="text" name="title"/>
                                    </div>
                                </div><br/>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="isbn">Isbn</label>
                                    <div class="col-lg-10">
                                        <input type="text" name="isbn"/>
                                    </div>
                                </div><br/>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="authorName">Author Name</label>
                                    <div class="col-lg-10">
                                        <input type="text" name="authorName"/>
                                    </div>
                                </div><br/>
                                <div class="form-group">
                                    <div class="col-lg-10 col-lg-offset-2">

                                        <button class="btn btn-success" type="submit">Add New Book</button>
                                    </div>
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

