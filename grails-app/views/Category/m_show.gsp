<%--
  User: eugene
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='mobile'/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>

<div data-theme="a" data-role="header">
    <a id="addBtn" data-role="button" href="#addPopup" data-rel="popup" class="ui-btn-right" data-transition="slide">
        Add
    </a>

    <h3>
        WalkingMe
    </h3>

    <g:link controller="user" action="edit" params="[username:sec.username()]" data-role="backBtn"
           data-icon="arrow-l" data-iconpos="left" class="ui-btn-left" data-transition="pop">Back</g:link>
</div>

<div data-role="popup" id="addPopup" data-position-to="window">
    <div data-role="content">
        <g:form  url='[controller: "keyword", action: "create"]'
                 id="newKeyWordPopup"
                 name="newKeyWordPopup"
                 method="post">
            <div data-role="fieldcontain">
                <fieldset data-role="controlgroup">
                    <label for="newKewWord">
                        New keyword
                    </label>
                    <textarea name="" id="newKewWord" placeholder="" value="" type="text"
                           style="width: 400px;">

                    </textarea>
                </fieldset>
            </div>
            <input type="submit" value="Add this">
        </g:form>
    </div>
</div>

<div data-role="content">
    <ul id="categoryList" data-role="listview" data-divider-theme="b" data-inset="true">
        <li data-role="list-divider" role="heading">
            ${category.title?.getValue("en")}
        </li>

        <g:each in="${keywords}"
                status="i" var="keyword">
            <li data-transition="slide" data-icon="delete" data-role="button" data-iconpos="left">
                <g:link controller="keyword" action="delete" id="${keyword.id}">${keyword.title?.getValue("en")}</g:link>
            </li>
        </g:each>
    </ul>
</div>

</body>
</html>