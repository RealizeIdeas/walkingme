<script src="http://maps.googleapis.com/maps/api/js?key=${grailsApplication.config.google.places.apiKey}&sensor=false"
        type="text/javascript"></script>
<r:require modules="jquery-ui"/>
<r:script>
  var cookieLocation = jQuery.cookie("location");
  var latitude = cookieLocation ? parseFloat(cookieLocation.split(',')[0]) : 51.5073346;
  var longitude = cookieLocation ? parseFloat(cookieLocation.split(',')[1]) : 27.5611;
  var defaultLocation = new google.maps.LatLng(latitude,longitude);

</r:script>