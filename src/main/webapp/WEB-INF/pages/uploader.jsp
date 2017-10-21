<%@ page import="org.omg.CORBA.Environment" %>
<%@ page session="false" %>
<%@ include file="common/header.jspf" %>
<!DOCTYPE html>
<html>
<head>

    <%
        String message = (String) request.getAttribute("message");
    %>
    <title>File Uploader</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- jQuery UI -->
    <link href="<c:url value="/resources/libs/vendors/jquery/css/jquery-ui.css" />" rel="stylesheet" media="screen">

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/core/css/bootstrap.min.css" />" rel="stylesheet"/>

    <!-- styles -->
    <link href="<c:url value="/resources/core/css/main.css" />" rel="stylesheet"/>

</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="row">

            <div class="col-lg-6">
                <a class="navbar-brand" href="#">File Uploader</a>
            </div>
            <div class="col-lg-6"></div>
        </div>
    </div>
</nav>
<div class="container-full"></div>
<div class="jumbotron">
    <div class="container-full">
        <div class="row">
            <div class="col-md-2">
                <div class="panel-body">
                    <div class="panel panel-default">
                        <div class="sidebar content-box" style="display: block;">
                            <ul class="nav">
                                <!-- Main menu -->
                                <li class="current"><a href="fileUpload"><i class="glyphicon glyphicon-plus"></i> Upload
                                </a></li>
                                <li><a href="searchFile"><i class="glyphicon glyphicon-search"></i> Search </a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="panel-body">
                    <div class="panel-group" id="accordion">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" href="#collapse1">Upload File</a>
                                </h4>
                            </div>
                            <div id="collapse1" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    <form method="post" action="doUpload" enctype="multipart/form-data">
                                        <fieldset>
                                            <div class="form-group">
                                                <label>Select File</label>
                                                <input class="form-control" type="file" name="file" size="50">
                                            </div>
                                        </fieldset>
                                        <div>
                                            <input class="btn btn-default" type="submit" value="Upload"/>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>

                <div class="alert alert-info">

                    <span>${message}</span>
                </div>
            </div>
            <div class="col-md-4"></div>
        </div>
    </div>
</div>


<%@ include file="common/footer.jspf" %>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<c:url value="/resources/libs/vendors/jquery/js/jquery.js" />"></script>
<!-- jQuery UI -->
<script src="<c:url value="/resources/libs/vendors/jquery/js/jquery-ui.js" />"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/resources/core/js/bootstrap.min.js" />"></script>

<script src="<c:url value="/resources/core/js/main.js" />"></script>

</body>
</html>

<!-- -->