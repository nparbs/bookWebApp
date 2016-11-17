<%-- 
    Document   : index
    Created on : Oct 19, 2016, 7:38:48 PM
    Author     : Nick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Authors link</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-lg-8">
                    <div style="margin:15px;">
                        <a class="btn btn-info" href=<%= response.encodeURL("AuthorController?task=View")%> >View all authors</a>
                    </div>
                    <div style="margin:15px;">
                        <a class="btn btn-info" href=<%= response.encodeURL("AuthorController?task=Find")%> >Find author</a>
                    </div>
                    <div style="margin:15px;">
                        <a class="btn btn-info" href=<%= response.encodeURL("BookController")%> >View All Books</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>