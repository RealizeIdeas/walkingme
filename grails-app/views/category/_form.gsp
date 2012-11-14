<%@ page import="net.realizeideas.walkingme.keywords.Category" %>



<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'keywords', 'error')} ">
	<label for="keywords">
		<g:message code="category.keywords.label" default="Keywords" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${categoryInstance?.keywords?}" var="k">
    <li><g:link controller="keyword" action="show" id="${k.id}">${k?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="keyword" action="create" params="['category.id': categoryInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'keyword.label', default: 'Keyword')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="category.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="title" name="title.id" from="${net.realizeideas.walkingme.common.translatable.Translatable.list()}" optionKey="id" required="" value="${categoryInstance?.title?.id}" class="many-to-one"/>
</div>

