<!doctype html>
<html>
<head>
  <meta name="layout" content="main">
  <title>
      <g:message code="search.places.places"/>
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

        var myOptions = {
    <g:if test="${places}">
      zoom: 15,
    </g:if>
    <g:else>
      zoom: 4,
    </g:else>
    center:defaultLocation ,
    streetViewControl:false,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("searchResultMap"),
        myOptions);

    var marker = new google.maps.Marker({ map: map, position: defaultLocation, draggable: false });
    overlay.draw = function() {};
    overlay.setMap(map);

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

</head>

<body tab="usersTab">
<div class="colDx" id="searchResultMapHolder">
  <div id="searchResultMap">
  </div>

</div>

<g:if test="${flash.message}">
  <div class="message" role="status">${flash.message}</div>
</g:if>

<div id="checkBoxFilter" class="filter-bar">
   <g:message code="search.places.filter" />
  <g:each in="${categories}" status="i" var="category">
    <input type="checkbox" id="${category.id}" ${params.filterByCategory ?
      (params.selectedCategories?.split(",").contains("${category.id}") ? "checked=\"checked\"" : "") : "checked=\"checked\""}/>
    <label for="${category.id}" data-mini="true">${category.title?.getValue("en")}</label>
  </g:each>
  <button id="updateLabel" class="updateButton">
    <g:message code="search.places.update"/>
  </button>
</div>

<div id="placesList" class="content scaffold-list" role="main">

  <g:if test="${places}">
    <div id="searchResultContent" class="searchResult">
      <g:each in="${places}" var="place" status="i">
        <div id="place${place.publicId}" class="place">
          <div class="description">
            <h5><g:link controller="place" action="show" params="[publicId: place?.reference, service: place?.service]">
              ${place.title?.encodeAsHTML()}
            </g:link></h5>

            <div class="distance">${place.distance?.encodeAsHTML()} meters</div>

            <div class="address">${place.location?.toString()?.encodeAsHTML()}</div>
            <g:if test="${place.ranking}">
              <div>${place.ranking}</div>
            </g:if>

          </div>
        </div>
      </g:each>
    </div>
    <r:script>
      jQuery(function () {
        <g:each in="${places}" var="place">

      var latLong${place.publicId} = new google.maps.LatLng(${place.location.latitude}, ${place.location.longitude});
        var marker${place.publicId} = new google.maps.Marker({
          position:latLong${place.publicId},
          map:map,
          icon:iconPath
        });
        bounds.extend(latLong${place.publicId});

        //On place hover change marker Pin
        jQuery("#place${place.publicId}").live('mouseover',function () {
          marker${place.publicId}.setIcon("");
        }).live('mouseout', function () {
              marker${place.publicId}.setIcon(iconPath);
            });
            google.maps.event.addListener(marker${place.publicId}, "click", function() {
              window.location = "${createLink(controller: 'place', action: 'show', params: [publicId: place?.reference, service: place?.service])}";
          });

        %{--//Draw tooltips
        var content${place.publicId} = "<strong>${place.title?.encodeAsHTML()}</strong>" +
            "<br/>${place.location?.toString()?.encodeAsHTML()}";
        var tooltipOptions = {
          marker:marker${place.publicId}, // required
          content:content${place.publicId}, // required
          cssClass:'mapTooltip' // name of a css class to apply to tooltip
        };
        var tooltip${place.publicId} = new Tooltip(tooltipOptions);--}%

    </g:each>
      //Resise/rezoom map after all markers pointed
        map.fitBounds(bounds);
      });
    </r:script>

  </g:if>

</div>
<script type="text/javascript">
  $("#updateLabel").click( function () {
    var categories = "";
    jQuery("#checkBoxFilter input:checked").each(function () {
      categories += jQuery(this).attr('id') + ",";
    });
    categories = categories.substring(0, categories.length - 1);
    window.location.href = "${createLink(controller: 'search', action: 'placesSearch')}?filterByCategory=true&selectedCategories=" + categories;
  })
</script>
</body>
</html>
