<%@ include file="include.jsp"%>
<%@ page session="false" %>
<%@page errorPage="handleError.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section id="signup" class="details">
    <header class="row">
        <div class="large-12 columns large-text-center medium-text-center">
            <h1>Bat Register</h1>
        </div>
    </header>

    <div class="row">
        <div class="large-6 medium-6 columns">
            <form id="batSignUp">
                <div>
                    <input name="firstName" type="text" id="firstName" placeholder="Enter First Name" pattern="[a-zA-Z]+" title="Only a-z or A-Z characters"/>
                </div>
                <div>
                    <input name="lastName" type="text" id="lastName" placeholder="Enter Last Name" pattern="[a-zA-Z]+" title="Only a-z or A-Z characters" />
                </div>
                <div>
                    <input name="password" type="password" id="password" placeholder="Enter password" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$" title="Please enter 8 char pwd with one upper case letter and one lower case letter and one digit with one special char" />
                </div>
                <div>
                    <input name="cpwd" type="password" id="cpwd" placeholder="Confirm password" />
                </div>
                <div>
                    <input name="phno" type="text" id="phno" placeholder="Enter Phone number (000-000-0000)" pattern="^[0-9]{3}-[0-9]{3}-[0-9]{4}$" title="Enter in this format 000-000-0000"/>
                </div>
                <div>
                    <input type="radio" name="gender" value="M" checked="checked" id="male"><label for="male">Male</label>
                    <input type="radio" name="gender" value="F" id="female"><label for="female">Female</label>
                </div>
                <div id="errorSignUp"></div>
                <div class="signupBtns">
                    <input type="submit" value="Sign Up" class="button secondary">
                    <input type="reset" value="Clear" class="button secondary">
                </div> 
            </form>

            <div id="signUpStatus"></div>
        </div>
    </div>
    <div id="overlay"></div>
    <div class="loader hide"><div class="loadingAnime"></div></div>
</section>

<%@include file="footer.jsp" %>

<script src="${context}/resources/js/vendor/jquery.js"></script>
<script src="${context}/resources/js/vendor/foundation.min.js"></script>
<script src="${context}/resources/js/app.js"></script>
<script type="text/javascript">
setTimeout(function(){
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

}, 100);
</script>


