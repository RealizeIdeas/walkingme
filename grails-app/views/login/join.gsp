<%@ page import="net.realizeideas.walkingme.AppConstants" %>
<html>
<head>
  <meta name='layout' content='main'/>
  <title><g:message code="springSecurity.login.title"/></title>
  <r:script>
    $("#joinWithFacebook").click(function () {
      $("#facebookForm").submit();
    });
  </r:script>
</head>

<body>

<h2>Find places and events you are interested in</h2>

<div class="facebook_button_holder">
  <a id="joinWithFacebook" href="javascript: void(0)" class="btn-auth facebook_button">
    <span><g:message code="user.signup.facebook"/></span>
  </a>

</div>

<g:form name="facebookForm" mapping="springOAuthSocialSignIn"
        params="[providerId: 'facebook']" style="display: none">
  <input type="hidden" name="scope" value="${AppConstants.FACEBOOK_PERMISSIONS}"/>
</g:form>

</body>
</html>
