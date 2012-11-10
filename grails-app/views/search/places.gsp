<!doctype html>
<html>
<head>
  <meta name="layout" content="main">
  <title>Places</title>
</head>

<body tab="usersTab">

<div id="placesList" class="content scaffold-list" role="main">
  <h1>Places</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
    <table id="searchResultContent" class="searchResult">
    <g:each in="${places}" var="place" status="i">
      <tr id="place${place.publicId?.replaceAll(":", "_")}">
        <td class="description">
          <h5><g:link controller="place" action="show" params="[publicId:place?.publicId, service:place?.service]">
            ${place.title?.encodeAsHTML()}
          </g:link></h5>

          <div class="distance">${place.distance?.encodeAsHTML()}</div>
          <div class="address">${place.location?.toString()?.encodeAsHTML()}</div>
          <g:if test="${place.ranking}">
            <div>${place.ranking}</div>
          </g:if>

        </td>
      </tr>
    </g:each>
  </table>

</div>
</body>
</html>
