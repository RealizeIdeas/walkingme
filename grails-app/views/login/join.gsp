<%@ page import="net.realizeideas.walkingme.AppConstants" %>
<html>
<head>
    <meta name='layout' content='login'/>
    <title><g:message code="springSecurity.login.title"/></title>
    <r:script>
        $("#joinWithFacebook").click(function () {
            $("#facebookForm").submit();
        });
    </r:script>
</head>

<body>

<div class="login_container">
    %{--<r:img dir="images" file="logo_text.png" class="logo_text"/>--}%
    <div class="logo_text">
        <g:message code="default.walkingme"/>
    </div>

    <h2>
        <g:message code="login.join.findPlaces"/>
    </h2>
    <r:img dir="images" file="logo.jpg" width="500" height="400"/>
    <div class="description">
        <g:message code="login.join.description"/>
    </div>


    <div class="facebook_button_holder">
        <a id="joinWithFacebook" href="javascript: void(0)" class="btn-auth facebook_button">
            <span><g:message code="user.signup.facebook"/></span>
        </a>

    </div>

    <div class="comments">
        <g:message code="main.question"/>
        <a href="mailto:info@realizeideas.net">
            <g:message code="main.email"/>
        </a>
    </div>

    <g:form name="facebookForm" mapping="springOAuthSocialSignIn"
            params="[providerId: 'facebook']" style="display: none">
        <input type="hidden" name="scope" value="${AppConstants.FACEBOOK_PERMISSIONS}"/>
    </g:form>

</div>

</body>
</html>
