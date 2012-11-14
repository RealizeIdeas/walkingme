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
  setCurrentLocation();

  jQuery(function () {
    jQuery("#link_profile").click(function () {
      jQuery(this).toggleClass('dropdown-open').find('a:first').toggleClass('link-active');
    });

    jQuery("#refineLocation").jqm(
        { modal:true, overlay:70,
          height:450, width:650,
          onHide:function (hash) {
            hash.o.remove(); // remove overlay
            hash.w.hide(); // hide window
            location.reload(true);
          }
        });

    jQuery("#precise_location").click(function () {
      jQuery(this).toggleClass('active');

      <g:remoteFunction controller="user" action="loadUserMap" update="refineLocation"/>
      jQuery("#refineLocation").jqmShow();
    });


  })
</script>