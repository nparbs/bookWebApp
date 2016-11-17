<%-- 
    Document   : editBook
    Created on : Nov 9, 2016, 7:53:40 PM
    Author     : Nick
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%
    Object book = request.getAttribute("book");
    HttpSession sess = request.getSession();
    if(book==null){
        response.sendRedirect("BookController?task=view");
    }

%>
<html>
    <head>
        <link rel="stylesheet" href="https://bootswatch.com/cyborg/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="main.css" rel="stylesheet" type="text/css"/>
        <title>Edit Book</title>
    </head>
    <body>
        <div class="container">
            <jsp:include page="timeHeader.jsp"/>
            <h1>Edit Book</h1>

            <table class="table table-striped table-hover">
                <tr>
                    <th>Book ID</th>
                    <th>Book Title</th>
                    <th>ISBN</th>
                    <th>Author</th>
                    <th></th>
                    <th></th>
                    <th></th>                       
                </tr>               
                <tr>
                <form action="BookController?task=Edit" method="post">
                    <td>${book.bookId}</td>
                    <td><input type="text" name="title" value="${book.title}" /></td>
                    <td><input type="text" name="isbn" value="${book.isbn}" /></td>
                    <td><input type="text" name="authorId" value="${book.authorId.authorId}" />-${book.authorId.authorName}</td>
                    <td>
                        <button class="btn btn-success" name="id" value="${book.bookId}"  type="submit">Confirm Edit</button>
                    </td>
                </form> 
                <td>
                    <form action="BookController?task=Delete" method="post">
                        <button class="btn btn-warning" name="id" type="submit" value=${book.bookId}>Delete</button>
                    </form>
                </td>
                <td>
                    <a class="btn btn-danger" href="BookController?task=View">Cancel - Back to Books</a>
                </td>
                </tr>
            </table>
            </br>
        </div>
    </body>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>
