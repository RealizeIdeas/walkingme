


<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
  <label for="username">
    <g:message code="user.username.label" default="Username"/>
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="username" required="" value="${userInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
  <label for="password">
    <g:message code="user.password.label" default="Password"/>
    <span class="required-indicator">*</span>
  </label>
  <g:passwordField name="password" required="" value="${userInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'firstName', 'error')} ">
  <label for="firstName">
    <g:message code="user.firstName.label" default="First Name"/>

  </label>
  <g:textField name="firstName" value="${userInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'lastName', 'error')} ">
  <label for="lastName">
    <g:message code="user.lastName.label" default="Last Name"/>

  </label>
  <g:textField name="lastName" value="${userInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
  <label for="enabled">
    <g:message code="user.enabled.label" default="Enabled"/>

  </label>
  <g:checkBox name="enabled" value="${userInstance?.enabled}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'authorities', 'error')} roles_holder ">
  <div class="roles_label">
    <label for="authorities">
      <g:message code="user.authorities.label" default="Roles"/>
    </label>
  </div>

  <div class="roles">
    <g:each var="entry" in="${roleMap}">
      <div>
        <g:checkBox name="${entry.key.authority}" value="${entry.value}"/>
        <label>${entry.key.authority.encodeAsHTML()}</label>
      </div>
    </g:each>
  </div>
</div>


