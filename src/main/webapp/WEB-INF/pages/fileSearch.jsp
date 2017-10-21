<%@ page import="org.omg.CORBA.Environment" %>
<%@ page session="false" %>
<%@ include file="common/header.jspf" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>

    <%
        String message = (String) request.getAttribute("message");
    %>
    <title>Search File</title>
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
                        <li class="current"><a href="fileUpload"><i class="glyphicon glyphicon-plus"></i> Upload </a></li>
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
                                <a data-toggle="collapse" href="#collapse1">Find No of Records</a>
                            </h4>
                        </div>
                        <div id="collapse1" class="panel-collapse collapse in">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="fileName">Select File:</label>
                                    <select class="form-control" id="fileName" name="fileName" onchange="getRawCountViaAjax(this.value)">
                                        <option value="">Select</option>
                                        <c:forEach items="${fileList}" var="temp">
                                            <option value="${temp}">${temp}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

                </div>
                <div id="contant"></div>
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