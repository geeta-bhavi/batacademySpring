<%@ include file="include.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="handleError.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bat Expert</title>
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

                            <c:forEach var="course" items="${courses}">
                                <li><span class="batLabel">Course Id:</span><span class="info courseId">${course.courseId}</span></li>
                                <li><span class="batLabel">Course Name:</span><span class="info">${course.courseName}</li>
                            </c:forEach>
                        </ul>
                    </div>   
                </div>
                
                <div class="searchCont">
                    <form id="searchById" class="large-6 medium-6 columns">
                        <div>
                            <input name="searchStudentId" type="number" id="searchStudentId" placeholder="Enter Student Id" max="9999" />
                            <select name="searchCourseId" id="searchCourseId">
                                <c:forEach var="course" items="${courses}">
                                    <option value="${course.courseId}">${course.courseName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div>
                            <input type="submit" value="Submit" class="button secondary">
                        </div>
                        <div id="searchError"></div>
                    </form>

                    <div id="searchResults" class="hide large-12 medium-12 columns">
                        <form id="updateActivity">
                            <table class="courseTable" id="selectedCousesTable">
                                <tr>
                                    <th>Student Id</th>
                                    <th>Course Id</th>
                                    <th>Activity 1</th>
                                    <th>Activity 2</th>
                                    <th>Activity 3</th>
                                </tr>
                                <tr>
                                    <td id="enterStudentId"></td>
                                    <td id="enterCourseId"></td>
                                    <td><input min="0" max="100" class="allowEdit" id="enterActivity1" type="number"></td>
                                    <td><input min="0" max="100" class="allowEdit" id="enterActivity2" type="number"></td>
                                    <td><input min="0" max="100" class="allowEdit" id="enterActivity3" type="number"></td>
                                </tr>
                            </table>

                            <input type="submit" class="button secondary" id="updateActivityBtn" value="Confirm changes">
                        </form>
                    </div>

                    <div id="updatedResults" class="large-12 medium-12 columns"></div>
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
