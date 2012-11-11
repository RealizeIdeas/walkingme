<%--
  Created by IntelliJ IDEA.
  User: eugene
  Date: 10.11.12
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='mobile'/>
    <title>Settings</title>
</head>

<body>
<div data-theme="a" data-role="header">
    <g:link controller="search" action="placesSearch" params="[username:sec.username()]" data-role="button"
            class="ui-btn-left" data-transition="slide">OK</g:link>
    <h3 id="settings">
        Settings
    </h3>
</div>

<ul id="CategoriesList" data-role="listview" data-divider-theme="b" data-inset="true">
    <li data-role="list-divider" role="heading">
        Categories
    </li>

    <g:each in="${categories}" status="i" var="category">
    <li data-icon="arrow-r" data-iconpos="right">
        <g:link controller="category" action="show" id="${category.id}"  data-transition="slide">${category.title?.getValue("en")}</g:link>
    </li>
    </g:each>
</ul>

%{--<div data-role="content">
    <div data-role="fieldcontain">
        <fieldset data-role="controlgroup">
            <label for="publishToggle">
                Publish on wall
            </label>
            <select name="publishToggle" id="publishToggle" data-theme="b" data-role="slider">
                <option value="off">
                    Off
                </option>
                <option value="on" selected="selected">
                    On
                </option>
            </select>
        </fieldset>
    </div>
</div>--}%
</body>
</html>