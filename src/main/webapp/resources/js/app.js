$(document).foundation();


function logOut(event) {
    event.preventDefault();

    $(".loader").removeClass("hide").addClass("show");
    $("section.details").addClass("loading");
    $.ajax({
        method: "POST",
        url: "../batacademy/signout",
        data: {task: "signout"}
    })
    .done(function (data) {
        $(".loader").removeClass("show").addClass("hide");
        $("section.details").removeClass("loading");
        if (data === "success") {
            window.location = '../batacademy/';
        }
    });
}

$(function () {

    var quiz;

    /* register functions */
    $(window).on("load resize", batHandleLoad);
    $(window).on("open.zf.reveal", batshowQuiz);
    $("#batSignIn").on("submit", batHandleSignIn); /* on sign-in submit of index */
    $(".batQ").on("click", batSubmitQuiz); /*quiz submission on click of radio buttons */
    $("#userId").on("keypress", removeErrorClass); /*remove error on sign-in page when user starts typing*/
    $("#userPasssword").on("keypress", removeErrorClass); /*remove error on sign-in page when user starts typing*/
    $(".addCourse").on("click", createCourseList);
    $(".confirm").on("click", showCourseList);
    $("#searchById").on("submit", batSearchById); /* searching student by id and the faculty courseId to update/view activity score */
    $("#searchStudentId").on("keypress", removeErrorClass); /* remove error when faculty starts typing again */
    $("#searchCourseId").on("keypress", removeErrorClass);/* remove error when faculty starts typing again */
    $("#updateActivity").on("submit", updateActivity); /* update activity scores after faculty confirms scores */
    $("#enableReg").on("change", enableRegistrationForNewSem);/* president has checked enable button */
    $("#searchByStudentId").on("submit", searchStudent); /* president search to delete student */
    $("#deleteStudent").on("click", deleteStudent);
    $("#batSignUp").on("submit", signUp); /* sign up*/

    /* functions */
    function batHandleLoad() {
        var footer = $("#footer");
        var pos = footer.position();
        var height = $(window).height();
        height = height - pos.top;
        height = height - footer.height();
        if (height > 0) {
            footer.css({
                'margin-top': height + 'px'
            });
        }
        $("#userId").focus();
    }

    function batHandleSignIn(event) {
        event.preventDefault();
        /* clear errors */
        clearLandingError();


        var id = $("#userId").val();
        var pwd = $("#userPasssword").val();
        var type = $('input[name=userType]:checked').val();

        if (id.length !== 0 && pwd.length !== 0) {
            showLoadingScreen();

            $.ajax({
                method: "POST",
                url: "../batacademy/signin",
                data: {id: id, password: pwd, userType: type}
            })
                    .done(function (data) {
                        hideLoadingScreen();
                        if (data === "error") {
                            clearLandingError();
                            showError("You have either entered wrong Id or Password", "error");
                        } else if (data === "student") {
                            window.location = '../batacademy/studentDetailsController';
                        } else if (data === "faculty") {
                            window.location = '../batacademy/facultyDetailsController';
                        }
                    });
        } else {

            if (id.length === 0) {
                showError("Id field is empty", "error");
                $("#userId").addClass('error');
            }
            if (pwd.length == 0) {
                showError("Password field is empty", "error");
                $("#userPasssword").addClass('error');
            }

        }
    }

    function showError(errorTxt, errorDiv) {
        var ulEle = $("#errorCont");
        if (ulEle.length === 0) {
            ulEle = $("<ul id='errorCont'></ul>");
        }
        ulEle.append("<li>" + errorTxt + "</li>");
        $("#" + errorDiv).html(ulEle);
    }

    function batshowQuiz() {
        var num = getRandomArbitrary(1, 3);
        var id = "bat" + num;
        $(".batmods").removeClass("show").addClass("hide");
        $("#" + id).removeClass("hide").addClass("show");
        $("#" + id + " input[type='radio']").removeAttr("checked");
        quiz = setTimeout(function () {
            window.location = '../BatAcademy/HandleError';
        }, 20000);

    }

    function batSubmitQuiz() {
        var id = $(".batmods.show").attr("id");
        var radioValue = $("input[name='" + id + "']:checked").val();
        if (id === "bat1" && radioValue === "3") {
            clearTimeout(quiz);
            $("body").load("../BatAcademy/signup");
            $("body").removeClass("is-reveal-open");
        } else if (id === "bat2" && radioValue === "42") {
            clearTimeout(quiz);
            $("body").load("../BatAcademy/signup");
            $("body").removeClass("is-reveal-open");
        } else {
            window.location = '../BatAcademy/HandleError';
        }
    }

    function createCourseList(event) {
        event.preventDefault();
        $(this).find('img').toggle();
        var parent = $(this).parent();
        var parentParent = $(this).parent().parent();
        var list = $("#courseList");


        $("#error").removeClass("show").addClass("hide");
        parentParent.toggleClass("selected");

        if (list.find(".selected").length > 2) {
            $("#error").removeClass("hide").addClass("show");
        }

    }

    function showCourseList(event) {
        event.preventDefault();
        var tr = $("#courseList").find("tr.selected");

        if (tr.length > 0 && tr.length <= 2) {

            showLoadingScreen();

            var list = [];
            var courses = {
                "studentId": $("#studentId").html(),
                "courseList": []
            };

            $.each(tr, function (key, value) {
                var cid = value.attributes.getNamedItem("data-cid").value;
                var cname = value.attributes.getNamedItem("data-cname").value;
                var faculty = value.attributes.getNamedItem("data-faculty").value;
                var tr = $("<tr></tr>");
                tr.append("<td>" + cid + "</td>");
                tr.append("<td>" + cname + "</td>");
                tr.append("<td>" + faculty + "</td>");
                tr.append("<td></td>");
                tr.append("<td></td>");
                tr.append("<td></td>");

                $("#selectedCousesTable").append(tr);

                var course = {
                    "courseId": cid,
                    "coursename": cname
                }

                list.push(course);
            });


            courses.courseList = list;


            $.ajax({
                method: "POST",
                url: "../BatAcademy/StudentDetailsController",
                data: {courses: JSON.stringify(courses)}
            }).done(function (data) {
                hideLoadingScreen();
                if (data === "success") {
                    $("#courseList").removeClass("show").addClass("hide");
                    $("#selectedCouses").removeClass("hide").addClass("show");
                    $("#confirmation").hide();
                } else {
                    $("#servererror").removeClass("hide").addClass("show");
                    $("#selectedCousesTable").empty();

                    var th = $("<tr></tr>");
                    th.append("<th>Course Id</th>");
                    th.append("<th>Course Name</th>");
                    th.append("<th>Faculty Name</th>");
                    th.append("<th>Activity 1</th>");
                    th.append("<th>Activity 2</th>");
                    th.append("<th>Activity 3</th>");

                    $("#selectedCousesTable").append(th);
                }

            });

        } else {
            $("#error").removeClass("hide").addClass("show");
        }
    }


    function batSearchById(event) {
        event.preventDefault();

        clearSearchError();
        clearSearchdata();

        var sid = $("#searchStudentId").val();
        var cid = $("#searchCourseId option:selected").val();

        if (sid.length !== 0) {

            showLoadingScreen();

            $.ajax({
                method: "POST",
                url: "../BatAcademy/FacultyDetailsController",
                data: {task: "search", sid: sid, cid: cid}
            }).done(function (data) {
                hideLoadingScreen();

                if (data.activities !== undefined) {
                    var value = data.activities;
                    /* if course is not completed then only allow faculty to edit */
                    var isCourseCompleted = value.courseCompleted;
                    if (isCourseCompleted) {
                        $("#updateActivityBtn").hide();
                    } else {
                        $("#updateActivityBtn").show();
                    }
                    var inputlist = $(".allowEdit");
                    $.each(inputlist, function (index, value) {
                        $(this).prop("disabled", isCourseCompleted);
                    });
                    $("#enterStudentId").html(sid);
                    $("#enterCourseId").html(cid);
                    $("#enterActivity1").val(value.Activity1);
                    $("#enterActivity2").val(value.Activity2);
                    $("#enterActivity3").val(value.Activity3);

                    $("#searchResults").removeClass("hide").addClass("show");


                } else {
                    showError("Either you have entered wrong id or the student has not taken any of your courses.", "searchError");
                    $("#searchResults").removeClass("show").addClass("hide");
                }

            });
        } else {
            showError("Id field is empty", "searchError");
            $("#searchStudentId").addClass('error');
        }
    }

    function clearSearchdata() {
        $("#enterStudentId").html("");
        $("#enterCourseId").html("");
        $("#enterActivity1").val("");
        $("#enterActivity2").val("");
        $("#enterActivity3").val("");
        $("#updatedResults").html("");
        $("#updatedResults").removeClass("success alert label");
    }

    function updateActivity(event) {
        event.preventDefault();
        var studentId = $("#enterStudentId").html();
        var courseId = $("#enterCourseId").html();
        var activity1 = $("#enterActivity1").val();
        var activity2 = $("#enterActivity2").val();
        var activity3 = $("#enterActivity3").val();

        showLoadingScreen();
        $.ajax({
            method: "POST",
            url: "../BatAcademy/FacultyDetailsController",
            data: {task: "update", sid: studentId, cid: courseId, activity1: activity1, activity2: activity2, activity3: activity3}
        }).done(function (data) {
            hideLoadingScreen();
            if (data === "success") {
                $("#searchResults").removeClass("show").addClass("hide");
                $("#updatedResults").html("Data has been updated");
                $("#updatedResults").addClass("success label");
            } else {
                $("#searchResults").removeClass("show").addClass("hide");
                $("#updatedResults").html("There was an error updating the data. Contact admin.");
                $("#updatedResults").addClass("alert label");
            }
        });
    }


    function enableRegistrationForNewSem() {

        var checked = $(this).is(":checked");

        /*claesr status div*/
        $("#presidentUpdateStatus").html("");
        $("#presidentUpdateStatus").removeClass("success alert label");

        showLoadingScreen();
        $.ajax({
            method: "POST",
            url: "../BatAcademy/FacultyDetailsController",
            data: {task: "enableRegistrationForNewSem", enable: checked}
        }).done(function (data) {
            hideLoadingScreen();
            if (data === "success") {
                if (checked) {
                    $("#presidentUpdateStatus").html("Regsitration enabled successfully");
                } else {
                    $("#presidentUpdateStatus").html("Regsitration disabled successfully");
                }

                $("#presidentUpdateStatus").addClass("success label");

            } else {
                $("#presidentUpdateStatus").html("There was an error updating the data. Contact admin.");
                $("#presidentUpdateStatus").addClass("alert label");
            }
        });
    }

    function searchStudent(event) {
        event.preventDefault();

        clearPresSearchError();
        clearPresSearchdata();

        var sid = $("#presStudentId").val();

        if (sid.length !== 0) {
            showLoadingScreen();
            $.ajax({
                method: "POST",
                url: "../BatAcademy/FacultyDetailsController",
                data: {task: "presidentEnSearch", sid: sid}
            }).done(function (data) {
                hideLoadingScreen();

                if (data.studentDetails !== undefined) {

                    var student = data.studentDetails;
                    $("#presEnStudentId").html(sid);
                    $("#presEnFirstName").html(student.StudentFirstName);
                    $("#presEnLastName").html(student.StudentLastName);
                    $("#presEnCGPA").html(student.CGPA);

                    $("#presEnsearchResults").removeClass("hide").addClass("show");


                } else {
                    showError("Student Id does not exist", "presEnSearchError");
                    $("#presEnsearchResults").removeClass("show").addClass("hide");
                }

            });

        } else {
            showError("Id field is empty", "presEnSearchError");
            $("#presStudentId").addClass('error');
        }
    }

    function deleteStudent(event) {

        event.preventDefault();
        var studentId = $("#presStudentId").val();


        showLoadingScreen();
        $.ajax({
            method: "POST",
            url: "../BatAcademy/FacultyDetailsController",
            data: {task: "deleteStudent", sid: studentId}
        }).done(function (data) {
            hideLoadingScreen();
            if (data === "success") {
                $("#presEnsearchResults").removeClass("show").addClass("hide");
                $("#presEnUpdatedResults").html("Student has been deleted from records");
                $("#presEnUpdatedResults").addClass("success label");
            } else {
                $("#presEnsearchResults").removeClass("show").addClass("hide");
                $("#presEnUpdatedResults").html("There was an error updating the data. Contact admin.");
                $("#presEnUpdatedResults").addClass("alert label");

            }
        });

    }

    function clearPresSearchdata() {
        $("#presEnStudentId").html("");
        $("#presEnFirstName").html("");
        $("#presEnLastName").html("");
        $("#presEnCGPA").html("");
        $("#presEnUpdatedResults").html("");
        $("#presEnUpdatedResults").removeClass("success label alert");
    }

    function getRandomArbitrary(min, max) {
        return Math.floor(Math.random() * (max - min) + min);
    }

    function clearSearchError() {
        $("#searchError").empty();
        $("#searchStudentId").removeClass('error');
    }

    function clearPresSearchError() {
        $("#presEnSearchError").empty();
        $("#presStudentId").removeClass('error');
    }

    function clearLandingError() {
        $("#error").empty();
        $("#userId").removeClass('error');
        $("#userPasssword").removeClass('error');
    }

    function removeErrorClass() {
        $(this).removeClass('error');
    }


    function signUp(event) {
        event.preventDefault();
        clearSignUpError();

        var firstName = $("#firstName").val();
        var lastName = $("#lastName").val();
        var password = $("#password").val();
        var cpwd = $("#cpwd").val();
        var phno = $("#phno").val();
        var gender = $("#batSignUp input[name=gender]:checked").val();

        if (firstName.length === 0) {
            showError("Enter First Name", "errorSignUp");
            $("#firstName").addClass("error");
        }

        if (password.length === 0) {
            showError("Enter password", "errorSignUp");
            $("#password").addClass("error");
        }

        if (cpwd.length === 0) {
            showError("Enter confirm password", "errorSignUp");
            $("#cpwd").addClass("error");
        }

        if (password.length !== 0 && cpwd !== password) {
            showError("Confirm password doesn't match with password", "errorSignUp");
            $("#cpwd").addClass("error");
        }

        if (phno.length === 0) {
            showError("Enter phone number", "errorSignUp");
            $("#phno").addClass("error");
        }

        if (firstName.length !== 0 && password.length !== 0 && cpwd === password && phno.length !== 0) {
            showLoadingScreen();
            $.ajax({
                method: "POST",
                url: "../BatAcademy/signin",
                data: {task: "signup", firstName: firstName, lastName: lastName, password: password, phno: phno, gender: gender}
            }).done(function (data) {
                hideLoadingScreen();
                if (data !== 0) {
                    $("#signUpStatus").html("Your student id is: " + data + ". Please use this id next time when you log in.");
                    $("#signUpStatus").addClass("success label");
                } else {
                    $("#signUpStatus").html("There was some error in the request. Please try again later.");
                    $("#signUpStatus").addClass("alert label");
                }
            });

        }

    }

    function clearSignUpError() {
        $("#errorSignUp").html("");
        $("#firstName").removeClass("error");
        $("#password").removeClass("error");
        $("#cpwd").removeClass("error");
        $("#phno").removeClass("error");
        $("#signUpStatus").removeClass("success label alert");
    }


    function showLoadingScreen() {
        $(".loader").removeClass("hide").addClass("show");
        $("#overlay").addClass("overlay");
    }

    function hideLoadingScreen() {
        $(".loader").removeClass("show").addClass("hide");
        $("#overlay").removeClass("overlay");
    }
    
    function b64EncodeUnicode(str) {
        return btoa(encodeURIComponent(str).replace(/%([0-9A-F]{2})/g, function(match, p1) {
            return String.fromCharCode('0x' + p1);
        }));
    }
    

}());