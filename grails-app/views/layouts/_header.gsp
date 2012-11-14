<sec:ifLoggedIn>
<g:render template="/layouts/geolocation"/>

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