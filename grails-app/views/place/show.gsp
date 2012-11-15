<!doctype html>
<html>
<head>
  <meta name="layout" content="main">
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'place.css')}" type="text/css">
  <title>
      <g:message code="place.show.place"/>
  </title>


  <script src="http://maps.googleapis.com/maps/api/js?key=${grailsApplication.config.google.places.apiKey}&sensor=false"
          type="text/javascript"></script>
  <r:require modules="jquery-ui"/>
  <r:script>
      var map;
      var overlay = new google.maps.OverlayView();
      var bounds = new google.maps.LatLngBounds();
      var iconPath = '${resource(dir: 'images', file: 'map_marker4.png')}';
      var cookieLocation = jQuery.cookie("location");
      var latitude = cookieLocation ? parseFloat(cookieLocation.split(',')[0]) : 51.5073346;
      var longitude = cookieLocation ? parseFloat(cookieLocation.split(',')[1]) : 27.5611;
      var defaultLocation = new google.maps.LatLng(latitude,longitude);

    jQuery("#refineLocation").jqm(
      { modal:true,overlay: 70,
        height:450, width:650,
        onHide : function(hash) {
        hash.o.remove(); // remove overlay
        hash.w.hide(); // hide window
        location.reload(true);
      }
    });

  function placeMarker(location, elemId){
    var containerPixel = overlay.getProjection().fromLatLngToContainerPixel(location);
    jQuery(elemId).css({top:containerPixel.y, left:containerPixel.x, 'dislay':'block'});
  }

  </r:script>


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
