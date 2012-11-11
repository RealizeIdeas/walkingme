<sec:ifLoggedIn>
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

  jQuery(function(){
    jQuery("#link_profile").click(function(){
      jQuery(this).toggleClass('dropdown-open').find('a:first').toggleClass('link-active');
    });

    jQuery("#refineLocation").jqm(
      { modal:true,overlay: 70,
        height:450, width:650,
        onHide : function(hash) {
        hash.o.remove(); // remove overlay
        hash.w.hide(); // hide window
        location.reload(true);
      }
    });

    jQuery("#precise_location").click(function(){
      jQuery(this).toggleClass('active');

      <g:remoteFunction controller="user" action="loadUserMap" update="refineLocation"/>
        jQuery("#refineLocation").jqmShow();
    });


  })
</script>

  <div id="refineLocation" style="display: none" class="jqmWindow"></div>
  <header class="top-bar">
    <div class="wrapper">
      <hgroup>
        <h1 class="logo"><a href="${request.contextPath ?: '/'}">WalkingMe</a></h1>
        <div id="precise_location"><a href="javascript:void(0)" title="Precise location"><r:img dir="images" file="location.png" alt="Precise location"/></a></div>
      </hgroup>


      <div class="account-state">
        <ul class="actions">

          <li id="link_profile" class="link-profile has-dropdown">
            <a href="javascript:void(0);">

              <strong><g:usernameFormatted/></strong>
            </a>

            <div class="dropdown">
              <i></i>

              <ul>
                <li><g:link controller="user" action="edit" id="${sec.loggedInUserInfo(field:'id')}">Edit My Likes</g:link></li>
                <li><g:link controller="logout">Log Out</g:link></li>
              </ul>
            </div>
          </li>

        </ul>
      </div>
    </div>
  </header>
  <div class="fix_float spacer_b_60"></div>
</sec:ifLoggedIn>