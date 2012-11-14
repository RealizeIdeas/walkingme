<!doctype html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
  <title>
    <g:message code="user.edit.updateLikes"/>
  </title>
  <r:require modules="jquery-ui, tagedit"/>
  <r:script>
    <g:each var="category" in="${categories}">
      jQuery("#category_${category.id} input.tag").tagedit({animSpeed:100, breakKeyCodes:[ 9, 13 ], allowEdit:false});
    </g:each>
  </r:script>
</head>

<body tab="usersTab">

<div id="edit-user" class="content scaffold-edit" role="main">
  <h1>
      <g:message code="user.edit.updateLikes"/>
  </h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <g:hasErrors bean="${userInstance}">
    <ul class="errors" role="alert">
      <g:eachError bean="${userInstance}" var="error">
        <li<g:if
               test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
            error="${error}"/></li>
      </g:eachError>
    </ul>
  </g:hasErrors>
  <g:form method="post">
    <g:hiddenField name="id" value="${userInstance?.id}"/>
    <g:hiddenField name="version" value="${userInstance?.version}"/>
    <fieldset class="form">
      <g:render template="form"/>
    </fieldset>
    <fieldset class="buttons">
      <g:actionSubmit class="save" action="update"
                      value="${message(code: 'default.button.update.label', default: 'Update')}"/>
    </fieldset>
  </g:form>
</div>
</body>
</html>
