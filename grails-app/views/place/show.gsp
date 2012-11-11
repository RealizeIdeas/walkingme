<!doctype html>
<html>
<head>
  <meta name="layout" content="main">
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'place.css')}" type="text/css">
  <title>Place</title>
  <r:require modules="fancybox"/>
  <r:script>
    jQuery("a.photoList").fancybox({
      'overlayShow':false,
      'transitionIn':'elastic',
      'transitionOut':'elastic'
    });
  </r:script>
</head>

<body>
<div class="place_info">
  <div class="title">${placeInstance.title?.encodeAsHTML()}</div>

  <div class="address">${placeInstance.location?.toString()}</div>

%{--<div class="ranking">${placeInstance.ranking?.toString()}</div>--}%

  <g:if test="${placeInstance?.telephone || placeInstance?.websiteURL}">
    <div class="contact_info">
      <g:if test="${placeInstance?.telephone}">
        <div class="telephone">${placeInstance?.telephone}</div>
      </g:if>
      <g:if test="${placeInstance?.websiteURL}">
        <div class="websiteURL"><a href="${placeInstance?.websiteURL}">${placeInstance?.websiteURL}</a></div>
      </g:if>
    </div>
  </g:if>

  <g:if test="${placeInstance?.photos}">
      <div class="photo_gallery">
        <g:each in="${placeInstance.photos}" var="photo">

          <a rel="group" class="photoList" href="${photo.prefix + "500x500" + photo.suffix}">
            <img src="${photo.prefix + "100x100" + photo.suffix}"/>
          </a>

        </g:each>
      </div>
  </g:if>

</div>

<div class="location_info">
  <g:staticMap latitude="${placeInstance.location.latitude}" longitude="${placeInstance.location.longitude}"
               mapSize="300x214"/>

</div>
</body>
</html>
