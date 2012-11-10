<script type="text/javascript">
  var ip = "${request.remoteAddr}";

  function checkGeolocation() {
    if (navigator.geolocation) {
      navigator.geolocation.watchPosition(
          function (position) {
            loadLocation(position.coords.latitude, position.coords.longitude, position.coords.accuracy);
          },
          function (error) {
            if (!$.cookie("errorLocationMessageWasShown" + ip)) {
              alert("Can't define your location!");
              $.cookie("errorLocationMessageWasShown" + ip, true, {expires:365, path:"/"});
            }
          }
      );
    }
  }

  function loadLocation(latitude, longitude, accuracy) {
    $.cookie("ip", ip, {expires:365, path:"/"});
    $.cookie("location", latitude + "," + longitude, {expires:365, path:"/"});
  }

  function setCurrentLocation() {
    if ($.cookie("ip") != ip || ($.cookie("ip") == ip && !$.cookie("location"))) {
      checkGeolocation();
    }
  }
  setCurrentLocation()
</script>