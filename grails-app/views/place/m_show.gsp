<%--
  Created by IntelliJ IDEA.
  User: eugene
  Date: 11.11.12
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='mobile'/>
</head>
<body>
    <div data-theme="a" data-role="header">

        <h3>
            ${placeInstance.title}
        </h3>

        <g:link controller="search" action="placesSearch" data-role="button"
                data-icon="arrow-l" data-iconpos="left" class="ui-btn-left" data-transition="slide">Back</g:link>

    </div>




</body>
</html>