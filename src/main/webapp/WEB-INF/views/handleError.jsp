<%@ include file="include.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Error</title>
        <link rel="stylesheet" href="${context}/resources/css/vendor/foundation.min.css">
        <link rel="stylesheet" href="${context}/resources/css/vendor/foundation-icon.css">
        <link rel="stylesheet" href="${context}/resources/css/app.css">
        <link rel="icon" type="image/png" sizes="16x16" href="${context}/resources/images/favicon-16x16.png">
        <link rel="icon" type="image/png" sizes="32x32" href="${context}/resources/images/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="96x96" href="${context}/resources/images/favicon-96x96.png">
    </head>
    <body>
        <section id="facultyDetails" class="details">
            <header class="row">
                <div class="large-12 columns large-text-center medium-text-center">
                    <h1>Error</h1>
                </div>
            </header>

            <div class="large-text-center medium-text-center">
                <img src="${context}/resources/images/error.png">
            </div>
        </section>

        <%@include file="footer.jsp" %>

	    <script src="${context}/resources/js/vendor/jquery.js"></script>
	    <script src="${context}/resources/js/vendor/foundation.min.js"></script>
	    <script src="${context}/resources/js/app.js"></script>
    </body>
</html>
