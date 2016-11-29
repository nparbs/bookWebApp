<%-- 
    Document   : authorReport
    Created on : Nov 21, 2016, 8:11:05 PM
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
        <title>Search Author</title>

    </head>
    <body>
        <%
            HttpSession sess = request.getSession();

            if (sess == null) {

            }

        %>
        <div class="container">
            <jsp:include page="timeHeader.jsp"/>
            <h1>Search Authors</h1>
            <div class="row">
                <div class="col-lg-10">
                    <div class="well bs-component">
                        <form class="form-horizontal" action="AuthorController?task=Search" method="post"><fieldset>
                                <legend></legend>

                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="id">Author Id: </label>
                                    <div class="col-md-4">
                                        <input type="text" name="id"/>
                                    </div>

                                    <label class="col-md-2 control-label" for="name">Author Name: </label>
                                    <div class="col-md-4">
                                        <input type="text" name="name"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label" for="dateStart">Date Added - Start Date: </label>
                                    <div class="col-md-4">
                                        <input type="date" name="dateStart"/>
                                    </div>

                                    <label class="col-md-2 control-label" for="dateEnd">End Date: </label>
                                    <div class="col-md-4">
                                        <input type="date" name="dateEnd"/>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group">
                                    <div class="col-lg-2 col-lg-offset-5">
                                        <button class="btn btn-success" type="submit">Search Authors</button>
                                    </div>
                            </fieldset></form>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <table class="table table-striped table-hover">
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
                </div>
            </div>


            </br>
            <div ><a class="btn btn-primary" href="AuthorController?task=View">View All Authors</a></div>
        </div>
    </body>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</html>
