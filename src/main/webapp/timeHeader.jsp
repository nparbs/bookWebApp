<%-- 
    Document   : timeHeader
    Created on : Oct 12, 2016, 8:50:31 PM
    Author     : Nick
--%>

<div class="row">

    <div class="col-md-4">
        <div class="well" style="float:left;">
            <h3>Book Web App</h3>
        </div>
    </div>
    <div class="col-md-4">
        <div class="well" style="text-align: center;">
            <div style="margin:10px;">
                <a class="btn btn-info" href=<%= response.encodeURL("AuthorController?task=View")%> >View all authors</a>
            </div>
            <div style="margin:10px;">
                <a class="btn btn-info" href=<%= response.encodeURL("BookController")%> >View All Books</a>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="well" style="float:right;">
            <p style="text-align: center;font-size: 20px;">Date is ${time}</p>
        </div>
    </div>
</div>
