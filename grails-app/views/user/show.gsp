<!doctype html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
  <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body tab="usersTab">
<a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                           default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
    <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
  </ul>
</div>

<div id="show-user" class="content scaffold-show" role="main">
  <h1><g:message code="default.show.label" args="[entityName]"/></h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <ol class="property-list user">

    <g:if test="${userInstance?.username}">
      <li class="fieldcontain">
        <span id="username-label" class="property-label"><g:message code="user.username.label"
                                                                    default="Username"/></span>

        <span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${userInstance}"
                                                                                    field="username"/></span>

      </li>
    </g:if>
    <g:if test="${userInstance?.firstName}">
      <li class="fieldcontain">
        <span id="firstName-label" class="property-label"><g:message code="user.firstName.label"
                                                                     default="First Name"/></span>

        <span class="property-value" aria-labelledby="firstName-label"><g:fieldValue bean="${userInstance}"
                                                                                     field="firstName"/></span>

      </li>
    </g:if>
    <g:if test="${userInstance?.lastName}">
      <li class="fieldcontain">
        <span id="firstName-label" class="property-label">
          <g:message code="user.lastName.label" default="Last Name"/>
        </span>

        <span class="property-value" aria-labelledby="lastName-label">
          <g:fieldValue bean="${userInstance}" field="lastName"/>
        </span>

      </li>
    </g:if>
    <g:if test="${userInstance?.enabled}">
      <li class="fieldcontain">
        <span id="enabled-label" class="property-label"><g:message code="user.enabled.label" default="Enabled"/></span>

        <span class="property-value" aria-labelledby="enabled-label"><g:formatBoolean
            boolean="${userInstance?.enabled}"/></span>

      </li>
    </g:if>



    <g:if test="${userInstance?.authorities}">
      <li class="fieldcontain">
        <span id="keywords-label" class="property-label"><g:message code="user.roles.label"
                                                                    default="Roles"/></span>

        <span class="property-value" aria-labelledby="keywords-label">
          ${userInstance.authorities*.authority.join(', ')}
        </span>

      </li>
    </g:if>

  </ol>
  <g:form>
    <fieldset class="buttons">
      <g:hiddenField name="id" value="${userInstance?.id}"/>
      <g:link class="edit" action="edit" id="${userInstance?.id}"><g:message code="default.button.edit.label"
                                                                             default="Edit"/></g:link>
      <g:actionSubmit class="delete" action="delete"
                      value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                      onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
    </fieldset>
  </g:form>
</div>
</body>
</html>
