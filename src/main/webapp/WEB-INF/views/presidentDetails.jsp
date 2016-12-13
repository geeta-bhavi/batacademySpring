<%@ include file="include.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="handleError.jsp" %>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>President Details</title>
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
                <%@include file="logout.jsp" %>
                <div class="large-12 columns large-text-center medium-text-center">
                    <h1>Bat Expert</h1>
                </div>
            </header>

            <div class="row detailsCont">
                <div class="infoCont">
                    <div class="large-2 medium-2 columns profilePic">
                        <img src="${context}/resources/images/${faculty.facultyId}.jpg">
                    </div>
                    <div class="large-4 medium-4 columns">
                        <ul class="facultyDetail">
                            <li><span class="batLabel">Faculty Id:</span><span id="facultyId" class="info">${faculty.facultyId}</span></li>
                            <li><span class="batLabel">First Name:</span><span class="info">${faculty.firstName}</span></li>
                            <li><span class="batLabel">Last Name:</span><span class="info">${faculty.lastName}</span></li>
                            <li><span class="batLabel">Designation:</span><span class="info">${faculty.designation}</span></li>
                            <li><span class="batLabel">Phone Number:</span><span class="info">${faculty.phone}</span></li>
                        </ul>
                    </div>
                    <div class="large-6 medium-6 columns">
                        <ul class="facultyDetail">
                            <li><span class="batLabel"><label class="enableRegLabel" for="enableReg">Enable Registration:</label></span><span class="info"><input id="enableReg" type="checkbox" ${faculty.enable ? 'checked = "checked"' : ''}></span></li>
                        </ul>
                    </div>
                </div>
                <div id="presidentUpdateStatus" class="large-12 medium-12 columns"></div>
                
                <div class="presSearchCont">
                    <form id="searchByStudentId" class="large-6 medium-6 columns">
                        <div>
                            <input name="presStudentId" type="number" id="presStudentId" placeholder="Enter Student Id" max="9999" />
                        </div>
                        <div>
                            <input type="submit" value="Submit" class="button secondary">
                        </div>
                        <div id="presEnSearchError"></div>
                    </form>

                    <div id="presEnsearchResults" class="hide large-12 medium-12 columns">
                        <table class="courseTable" id="selectedCousesTable">
                            <tr>
                                <th>Student Id</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>CGPA</th>
                            </tr>
                            <tr>
                                <td id="presEnStudentId"></td>
                                <td id="presEnFirstName"></td>
                                <td id="presEnLastName"></td>
                                <td id="presEnCGPA"></td>
                            </tr>
                        </table>
                        <a href="#" id="deleteStudent" class="button secondary">Delete Student</a>
                    </div>

                    <div id="presEnUpdatedResults" class="large-12 medium-12 columns"></div>
                </div>


                

            </div>
        </section>

        <div id="overlay"></div>
        <div class="loader hide"><div class="loadingAnime"></div></div>

        <%@include file="footer.jsp" %>

        <script src="${context}/resources/js/vendor/jquery.js"></script>
    	<script src="${context}/resources/js/vendor/foundation.min.js"></script>
    	<script src="${context}/resources/js/app.js"></script>
    </body>
</html>
