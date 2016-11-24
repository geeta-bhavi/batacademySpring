<%@ include file="include.jsp"%>
<%@ page session="false" %>
<!doctype html>
<html class="no-js" lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Batman Academy</title>
        <link rel="stylesheet" href="${context}/resources/css/vendor/foundation.min.css">
        <link rel="stylesheet" href="${context}/resources/css/vendor/foundation-icon.css">
        <link rel="stylesheet" href="${context}/resources/css/app.css">
        <link rel="icon" type="image/png" sizes="16x16" href="${context}/resources/images/favicon-16x16.png">
        <link rel="icon" type="image/png" sizes="32x32" href="${context}/resources/images/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="96x96" href="${context}/resources/images/favicon-96x96.png">
    </head>
    <body>

        <section class="landing">
            <header class="row">
                <div class="large-12 columns large-text-center medium-text-center">
                    <h1 class="headingBatAcademy">Bat Acade@y</h1>
                </div>
            </header>

            <div class="row landCont">
                <div class="large-6 medium-6 columns brderRight">
                    <div class="primary signupBtn">
                        <a data-open="batQuiz" class="secondary button">Sign Up</a>
                    </div>
                </div>
                <div class="large-4 medium-4 columns">
                    <div class="primary signIn">
                        <form id="batSignIn">
                            <div>
                                <input name="id" type="number" id="userId" placeholder="Enter Id" max="9999" />
                            </div>
                            <div>
                                <input name="password" type="password" id="userPasssword" placeholder="Enter password" />
                            </div>
                            <div>
                                <input type="radio" name="userType" value="student" checked="checked" id="student"><label for="student">Student</label>
                                <input type="radio" name="userType" value="faculty" id="faculty"><label for="faculty">Faculty</label>
                            </div>
                            <div id="error"></div>
                            <div class="signinBtns">
                                <input type="submit" value="Sign In" class="button secondary">
                                <input type="reset" value="Clear" class="button secondary">
                            </div> 
                        </form>
                    </div>
                </div>

                <div class="reveal modals"  id="batQuiz" data-reveal>
                    <h5>Please answer the below question in 20 seconds.</h5>
                    <div id="bat1" class="batmods hide">
                        <p class="lead">It's dark. You have ten grey socks and ten blue socks you want to put into pairs.
                            All socks are exactly the same except for their color. 
                            How many socks would you need to take with you to ensure you had at least a pair?</p>
                        <input class="batQ" type="radio" name="bat1" id="ans3" value="3"><label>3</label>
                        <input class="batQ" type="radio" name="bat1" id="ans16" value="16"><label>16</label>
                        <input class="batQ" type="radio" name="bat1" id="ans8" value="8"><label>8</label>
                    </div>
                    <div id="bat2" class="batmods hide">
                        <p class="lead">The sum of <img src="resources/images/quiz.png"></p>
                        <input class="batQ" type="radio" name="bat2" id="ans43" value="43"><label>43</label>
                        <input class="batQ" type="radio" name="bat2" id="ans41" value="41"><label>41</label>
                        <input class="batQ" type="radio" name="bat2" id="ans42" value="42"><label>42</label>
                    </div>
                    <button class="close-button" data-close aria-label="Close modal" type="button">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
            <div id="overlay"></div>
            <div class="loader hide"><div class="loadingAnime"></div></div>
            
            <%@include file="footer.jsp" %>
        </section>

        <script src="${context}/resources/js/vendor/jquery.js"></script>
        <script src="${context}/resources/js/vendor/foundation.min.js"></script>
        <script src="${context}/resources/js/app.js"></script>
    </body>
</html>
