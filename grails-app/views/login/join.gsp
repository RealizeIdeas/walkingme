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
  <div class="logo_text">WalkingMe</div>
  <h2>Find places and events you are interested in</h2>
  <r:img dir="images" file="logo.jpg" width="500" height="400"/>
  <div class="description">This Web App designed to walk you in the city based on your Facebook likes.
  Also available via mobile devices.
  </div>


  <div class="facebook_button_holder">
    <a id="joinWithFacebook" href="javascript: void(0)" class="btn-auth facebook_button">
      <span><g:message code="user.signup.facebook"/></span>
    </a>

  </div>
  <div class="comments">For questions and comments, please send us email to <a href="mailto:info@realizeideas.net">info@realizeideas.net</a>
  </div>

  <g:form name="facebookForm" mapping="springOAuthSocialSignIn"
          params="[providerId: 'facebook']" style="display: none">
    <input type="hidden" name="scope" value="${AppConstants.FACEBOOK_PERMISSIONS}"/>
  </g:form>

</div>

</body>
</html>
