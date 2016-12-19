<%@ include file="include.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="handleError.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="x-ua-compatible" content="ie=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Bat Trainee</title>
		<link rel="stylesheet" href="${context}/resources/css/vendor/foundation.min.css">
        <link rel="stylesheet" href="${context}/resources/css/vendor/foundation-icon.css">
        <link rel="stylesheet" href="${context}/resources/css/app.css">
        <link rel="icon" type="image/png" sizes="16x16" href="${context}/resources/images/favicon-16x16.png">
        <link rel="icon" type="image/png" sizes="32x32" href="${context}/resources/images/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="96x96" href="${context}/resources/images/favicon-96x96.png">
</head>
<body>
	<section id="studentDetails" class="details">
		<header class="row">
			<%@include file="logout.jsp"%>
			<div class="large-12 columns large-text-center medium-text-center">
				<h1>Bat Trainee</h1>
			</div>
		</header>
		
		<div class="row detailsCont">
			<div class="infoCont">
				<div class="large-2 medium-2 columns profilePic">
					<img src="${context}/resources/images/${student.studentId}.jpg"
						onerror="this.onerror=null;this.src='${context}/resources/images/default.jpg';">
				</div>
				<div class="large-10 medium-10 columns">
					<ul class="studentDetail">
						<li><span class="batLabel">Student Id:</span><span
							id="studentId" class="info">${student.studentId}</span></li>
						<li><span class="batLabel">First Name:</span><span
							class="info">${student.firstName}</span></li>
						<li><span class="batLabel">Last Name:</span><span
							class="info">${student.lastName}</span></li>
						<li><span class="batLabel">Phone Number:</span><span
							class="info">${student.phone}</span></li>
					</ul>
				</div>
			</div>
		</div>


		<div class="row cgpa">
			<span class="gpaLabel">CGPA:</span> ${student.cgpa}
		</div>
	
		<c:choose>
                <c:when test="${isRegistrationEnabled && !isStudentRegistered}">
                    <c:set var="showCoursesToRegister" value="true"/></c:when>
                <c:otherwise>
                    <c:set var="showCoursesAlreadyRegister" value="false"/> </c:otherwise>
            </c:choose>


            <c:choose>
                <c:when test="${showCoursesToRegister==true && fn:length(courses) gt 0}">
                    <div class="row coursesCont">
                        <div>
                            <table id="courseList" class="courseTable">
                                <caption>Register for Courses</caption>
                                <tr>
                                    <th>Course Id</th>
                                    <th>Course Name</th>
                                    <th>Faculty Name</th>
                                    <th>Add</th>
                                </tr>
                                <c:forEach var="course" items="${courses}">
                                    <tr data-cid="${course.courseId}"
                                        data-cname="${course.courseName}"
                                        data-faculty="${faculty[course.facultyId]}">
                                        <td>${course.courseId}</td>
                                        <td>${course.courseName}</td>
                                        <td>${faculty[course.facultyId]}</td>
                                        <td>
                                            <a class="addCourse" href="#">
                                                <img src="${context}/resources/images/batman-unsel.png">
                                                <img style="display: none;" src="${context}/resources/images/batman-sel.png">
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div id="confirmation" class="large-text-center medium-text-center">
                                <div id="error" class="hide">Please select 2 courses. No more than 2 courses.</div>
                                <a href="#" class="confirm button secondary">Confirm Courses</a>
                            </div>
                            <div id="servererror" class="hide">Server Error.</div>
                            <div id="selectedCouses" class="hide">
                                <table class="courseTable" id="selectedCousesTable">
                                    <caption>Selected Courses</caption>
                                    <tr>
                                        <th>Course Id</th>
                                        <th>Course Name</th>
                                        <th>Faculty Name</th>
                                        <th>Activity 1</th>
                                        <th>Activity 2</th>
                                        <th>Activity 3</th>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </c:when>

                <c:when test="${showCoursesAlreadyRegister==false && fn:length(selectedCourses) gt 0}">
                    <div class="row coursesCont">
                        <div>
                            <table id="courseList" class="courseTable">
                                <caption>Selected Courses</caption>
                                <tr>
                                    <th>Course Id</th>
                                    <th>Course Name</th>
                                    <th>Faculty Name</th>
                                    <th>Activity 1</th>
                                    <th>Activity 2</th>
                                    <th>Activity 3</th>
                                    <th>Course Completed</th>
                                </tr>
                                <c:forEach var="details" items="${selectedCourses}">
                                    <tr>
                                        <td>${details.courseID}</td>
                                        <td>${details.courseName}</td>
                                        <td>${details.facultyName}</td>
                                        <td>${details.a1}</td>
                                        <td>${details.a2}</td>
                                        <td>${details.a3}</td>  
                                        <td>${details.getCourseCompletedStatus()}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </c:when>
            </c:choose>
            
            
            


	</section>

	<div id="overlay"></div>

	<div class="loader hide">
		<div class="loadingAnime"></div>
	</div>

	<%@include file="footer.jsp"%>

	<script src="${context}/resources/js/vendor/jquery.js"></script>
    <script src="${context}/resources/js/vendor/foundation.min.js"></script>
    <script src="${context}/resources/js/app.js"></script>
</body>
</html>