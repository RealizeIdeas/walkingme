<%--
  User: eugene
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='mobile'/>
    <title>WalkingMe</title>
</head>

<body>

<div data-theme="a" data-role="header">
    <a id="addBtn" data-role="button" href="#addPopup" data-rel="popup" class="ui-btn-right" data-transition="pop">
        <g:message code="category.show.add" />
    </a>

    <h3>
        <g:message code="default.walkingme"/>
    </h3>

    <g:link controller="user" action="edit" params="[username:sec.username()]" data-role="button"
           data-icon="arrow-l" data-iconpos="left" class="ui-btn-left" data-transition="slide">
            <g:message code="category.show.back" />
           </g:link>
</div>

<div data-role="popup" id="addPopup" data-position-to="window">
    <div data-role="content">
        <g:form  url='[controller: "category", action: "addKeyword"]'
                 id="newKeywordPopup"
                 name="newKeywordPopup"
                 method="post">
            <div data-role="fieldcontain">
                <fieldset data-role="controlgroup">
                    <label for="title">
                        <g:message code="category.show.newKeyword" />
                    </label>
                  <g:textField name="title"/>
                  <g:hiddenField name="categoryId" value="${category?.id}"/>
                </fieldset>
            </div>
          <g:submitButton name="addKeyword" data-transition="slide" value = "${message(code:'category.show.addKeyword')}"
                          data-role="button" data-icon="plus" data-iconpos="right">

              </g:submitButton>


        </g:form>
    </div>
</div>

<div data-role="content">
    <label for="categoryList">
        <g:message code="category.show.delete" />
    </label>
    <ul id="categoryList" data-role="listview" data-divider-theme="b" data-inset="true">
        <li data-role="list-divider" role="heading">
            <g:translate value="${category.title}"/>
        </li>

        <g:each in="${keywords}" status="i" var="keyword">
                <g:link controller="category" action="deleteKeyword" id="${keyword.id}"
                        data-icon="delete" data-role="button"   data-iconpos="left">${keyword.title}</g:link>
        </g:each>
    </ul>
</div>

</body>
</html>