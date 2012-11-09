<!doctype html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body tab="usersTab">
<a href="#list-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                           default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
  </ul>
</div>

<div id="list-user" class="content scaffold-list" role="main">
  <h1><g:message code="default.list.label" args="[entityName]"/></h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <table>
    <thead>
    <tr>

      <g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'Username')}"/>
      <g:sortableColumn property="firstName" title="${message(code: 'user.firstName.label', default: 'First Name')}"/>
      <g:sortableColumn property="lastName" title="${message(code: 'user.lastName.label', default: 'Last Name')}"/>
      <g:sortableColumn property="roles" title="${message(code: 'user.roles.label', default: 'Roles')}"/>
      <g:sortableColumn property="enabled" title="${message(code: 'user.enabled.label', default: 'Enabled')}"/>

    </tr>
    </thead>
    <tbody>
    <g:each in="${userInstanceList}" status="i" var="userInstance">
      <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

        <td><g:link action="show"
                    id="${userInstance.id}">${fieldValue(bean: userInstance, field: "username")}</g:link></td>

        <td>${fieldValue(bean: userInstance, field: "firstName")}</td>
        <td>${fieldValue(bean: userInstance, field: "lastName")}</td>
        <td>${userInstance.authorities*.authority.join(', ')}</td>

        <td><g:formatBoolean boolean="${userInstance.enabled}"/></td>

      </tr>
    </g:each>
    </tbody>
  </table>

  <div class="pagination">
    <g:paginate total="${userInstanceTotal}"/>
  </div>
</div>
</body>
</html>
