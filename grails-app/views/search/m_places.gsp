<%--
  Created by IntelliJ IDEA.
  User: eugene
  Date: 11.11.12
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='mobile'/>
    <title></title>
    <style type="text/css">
    #popupPanel-popup {
        right: 0 !important;
        left: auto !important;
    }
    #popupPanel {
        width: 200px;
        border: 1px solid #000;
        border-right: none;
        background: rgba(0,0,0,.5);
        margin: -1px 0;
    }
    #popupPanel .ui-btn {
        margin: 2em 15px;
    }
    </style>
</head>

<body>

<div data-theme="a" data-role="header">

    %{--<g:link controller="user" action="edit" params="[username: sec.username()]" data-role="button"--}%
            %{--data-icon="gear" data-iconpos="left" class="ui-btn-left" data-transition="slide">Gear</g:link>--}%

    <a href="#popupPanel" data-rel="popup" data-transition="slide" data-position-to="window" data-role="button">Gear</a>

    <a data-role="button" data-transition="slide" href="#page1" data-icon="grid"
       data-iconpos="left" class="ui-btn-right">
        Map
    </a>

    <h3>
        WalkingMe
    </h3>
</div>

<div data-role="content">
    <ul data-role="listview" data-divider-theme="b" data-inset="true">
        <li data-role="list-divider" role="heading">
            Places
        </li>

        <g:each in="${places}" status="i" var="place">
        <li data-theme="c">
            <g:link controller="place" action="show" params="[publicId: place?.reference, service: place?.service]"  data-transition="slide">
                <h4 style="font-weight: bold;">
                    ${place.title}
                </h4>
                <label style="font-weight: normal;">
                     ${place.location}
                </label>
            </g:link>
            <span class="ui-li-count">
                <label style="color: #008b8b; font-style: italic; font-weight: normal;">
                    ${place.distance} m
                </label>
            </span>
        </li>
        </g:each>
    </ul>
</div>

<div data-role="popup" id="popupPanel" data-corners="true" data-theme="none" data-shadow="true" data-tolerance="0,0">

    <div data-role="content" data-corners="false">

    <g:each in="${categories}"
            status="i" var="category">
        <input type="checkbox" id="${category.title?.getValue("en")}" date-theme="a" data-mini="true" checked="checked"/>
        <label for="${category.title?.getValue("en")}" data-mini="true">${category.title?.getValue("en")}</label>
    </g:each>
    </div>

</div>

    <script type="text/javascript">
        $( document ).on( "pageinit", function() {

            $( "#popupPanel" ).on({
                popupbeforeposition: function() {
                    var h = $( window ).height();

                    $( "#popupPanel" )
                            .css( "height", h );
                }
            });

            $( "#popupPanel button" ).on( "click", function() {
                $( "#popupPanel" ).popup('close');
            });

        });
    </script>

</body>
</html>