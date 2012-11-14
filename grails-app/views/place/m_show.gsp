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
            data-icon="arrow-l" data-iconpos="left" class="ui-btn-left" data-transition="slide">
                <g:message code="walkingme.localized.back" />
            </g:link>

</div>

<div data-role="content">

  <g:staticMap latitude="${placeInstance.location.latitude}" sensor="true"
               longitude="${placeInstance.location.longitude}" mapSize="288x200"/>

  <div data-role="button" id="btnId" data-theme="c" corners="true">
    <label style="font-weight: normal;">
      ${placeInstance.location}
    </label>
  </br>
    <label style="color: #008b8b; font-style: italic; font-weight: normal;">
      ${placeInstance.distance}
    </label>
  </br>

  </div>

  %{-- <label style="font-weight: normal;">
      ${placeInstance.ranking}
  </label>--}%



</br>
  <label style="font-weight: normal;">
    ${placeInstance.telephone}
  </label>
</br>

  <a href="${placeInstance.websiteURL}">${placeInstance.websiteURL}</a>

</div>

</body>
</html>