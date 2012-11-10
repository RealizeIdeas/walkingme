<script src="http://maps.googleapis.com/maps/api/js?key=${grailsApplication.config.google.places.apiKey}&sensor=false"
        type="text/javascript"></script>
<script type="text/javascript">
  var userMap;
  var userOverlay = new google.maps.OverlayView();
  var cookieUserLocation = jQuery.cookie("location");
  var userLatitude = cookieUserLocation ? parseFloat(cookieUserLocation.split(',')[0]) : 51.5073346;
  var userLongitude = cookieUserLocation ? parseFloat(cookieUserLocation.split(',')[1]) : 27.5611;
  var defaultUserLocation = new google.maps.LatLng(userLatitude, userLongitude);
  jQuery("#userLat").val(userLatitude);
  jQuery("#userLong").val(userLongitude);

    var myOptions = {
      zoom:15,
      center:defaultUserLocation,
      streetViewControl:false,
      mapTypeId:google.maps.MapTypeId.ROADMAP
    };
    userMap = new google.maps.Map(document.getElementById("currentLocationMap"),
        myOptions);

    var marker = new google.maps.Marker({ map:userMap, position:defaultUserLocation, draggable:true });
    google.maps.event.addListener(marker, "dragend", function (event) {
      jQuery("#userLat").val(event.latLng.lat());
      jQuery("#userLong").val(event.latLng.lng());
    });
    userOverlay.draw = function () {
    };
    userOverlay.setMap(userMap);

    jQuery("#updateLocationLink").click(function(){
      jQuery.cookie("location", jQuery("#userLat").val() + "," + jQuery("#userLong").val(), {expires:365, path:"/"});
      jQuery("#refineLocation").jqmHide();
    });
</script>

<div id="currentLocationMap"></div>

<div class="control_buttons">
  <a href="javascript:void(0)" id="updateLocationLink">Update</a>
</div>
<g:hiddenField name="userLat" value=""/>
<g:hiddenField name="userLong" value=""/>