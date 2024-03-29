<%@ page import="net.realizeideas.walkingme.AppConstants" %>
<html>
<head>
    <meta name='layout' content='mobile'/>
    <title>
        <g:message code="springSecurity.login.title"/>
    </title>
</head>

<body>
<div data-theme="a" data-role="header">
    <h3>
        <g:message code="default.walkingme"/>
    </h3>
</div>
<g:if test='${flash.message}'>
    <div data-role="popup" id="simplepopup">
        <p><g:message code="springSecurity.errors.login.fail"/><p>
    </div>
</g:if>

<h4><g:message code="login.join.findPlaces" /></h4>
<g:message code="login.join.description" />

  <div class="facebook_button_holder">
    <a id="joinWithFacebook" href="javascript: void(0)" class="btn-auth facebook_button">
      <span><g:message code="user.signup.facebook"/></span>
    </a>
  </div>

  <g:form name="facebookForm" mapping="springOAuthSocialSignIn"
          params="[providerId: 'facebook']" style="display: none">
    <input type="hidden" name="scope" value="${AppConstants.FACEBOOK_PERMISSIONS}"/>
  </g:form>
</div>

<script type="text/javascript">
    $.mobile.ajaxEnabled = false;
    $("#joinWithFacebook").click(function () {
            $("#facebookForm").submit();
        });
    <g:if test='${flash.message}'>
    setTimeout('$("#simplepopup").popup("open")', 500);
    </g:if>
</script>

</body>
</html>