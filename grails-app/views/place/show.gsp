<%@ page import="net.realizeideas.walkingme.places.Place" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'place.label', default: 'Place')}"/>
  <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-place" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                            default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
    <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
  </ul>
</div>

<div id="show-place" class="content scaffold-show" role="main">
  <h1><g:message code="default.show.label" args="[entityName]"/></h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <ol class="property-list place">

    <g:if test="${placeInstance?.description}">
      <li class="fieldcontain">
        <span id="description-label" class="property-label"><g:message code="place.description.label"
                                                                       default="Description"/></span>

        <span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${placeInstance}"
                                                                                       field="description"/></span>

      </li>
    </g:if>

    <g:if test="${placeInstance?.telephone}">
      <li class="fieldcontain">
        <span id="telephone-label" class="property-label"><g:message code="place.telephone.label"
                                                                     default="Telephone"/></span>

        <span class="property-value" aria-labelledby="telephone-label"><g:fieldValue bean="${placeInstance}"
                                                                                     field="telephone"/></span>

      </li>
    </g:if>

    <g:if test="${placeInstance?.websiteURL}">
      <li class="fieldcontain">
        <span id="websiteURL-label" class="property-label"><g:message code="place.websiteURL.label"
                                                                      default="Website URL"/></span>

        <span class="property-value" aria-labelledby="websiteURL-label"><g:fieldValue bean="${placeInstance}"
                                                                                      field="websiteURL"/></span>

      </li>
    </g:if>

    <g:if test="${placeInstance?.ranking}">
      <li class="fieldcontain">
        <span id="ranking-label" class="property-label"><g:message code="place.ranking.label" default="Ranking"/></span>

        <span class="property-value" aria-labelledby="ranking-label"><g:fieldValue bean="${placeInstance}"
                                                                                   field="ranking"/></span>

      </li>
    </g:if>

    <g:if test="${placeInstance?.reviews}">
      <li class="fieldcontain">
        <span id="reviews-label" class="property-label"><g:message code="place.reviews.label" default="Reviews"/></span>

        <g:each in="${placeInstance.reviews}" var="r">
          <span class="property-value" aria-labelledby="reviews-label"><g:link controller="review" action="show"
                                                                               id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
        </g:each>

      </li>
    </g:if>

    <g:if test="${placeInstance?.photos}">
      <li class="fieldcontain">
        <span id="photos-label" class="property-label"><g:message code="place.photos.label" default="Photos"/></span>

        <g:each in="${placeInstance.photos}" var="p">
          <span class="property-value" aria-labelledby="photos-label">${p.absoluteURL}<img src="${p.absoluteURL}" alt="${p.name}"/></span>
        </g:each>

      </li>
    </g:if>

    <g:if test="${placeInstance?.checkins}">
      <li class="fieldcontain">
        <span id="checkins-label" class="property-label"><g:message code="place.checkins.label"
                                                                    default="Checkins"/></span>

        <g:each in="${placeInstance.checkins}" var="c">
          <span class="property-value" aria-labelledby="checkins-label"><g:link controller="checkin" action="show"
                                                                                id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
        </g:each>

      </li>
    </g:if>

    <g:if test="${placeInstance?.publicId}">
      <li class="fieldcontain">
        <span id="publicId-label" class="property-label"><g:message code="place.publicId.label"
                                                                    default="Public Id"/></span>

        <span class="property-value" aria-labelledby="publicId-label"><g:fieldValue bean="${placeInstance}"
                                                                                    field="publicId"/></span>

      </li>
    </g:if>

    <g:if test="${placeInstance?.location}">
      <li class="fieldcontain">
        <span id="location-label" class="property-label"><g:message code="place.location.label"
                                                                    default="Location"/></span>

        <span class="property-value" aria-labelledby="location-label"><g:link controller="location" action="show"
                                                                              id="${placeInstance?.location?.id}">${placeInstance?.location?.encodeAsHTML()}</g:link></span>

      </li>
    </g:if>

    <g:if test="${placeInstance?.service}">
      <li class="fieldcontain">
        <span id="service-label" class="property-label"><g:message code="place.service.label" default="Service"/></span>

        <span class="property-value" aria-labelledby="service-label"><g:fieldValue bean="${placeInstance}"
                                                                                   field="service"/></span>

      </li>
    </g:if>

    <g:if test="${placeInstance?.title}">
      <li class="fieldcontain">
        <span id="title-label" class="property-label"><g:message code="place.title.label" default="Title"/></span>

        <span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${placeInstance}"
                                                                                 field="title"/></span>

      </li>
    </g:if>

  </ol>
  <g:form>
    <fieldset class="buttons">
      <g:hiddenField name="id" value="${placeInstance?.id}"/>
      <g:link class="edit" action="edit" id="${placeInstance?.id}"><g:message code="default.button.edit.label"
                                                                              default="Edit"/></g:link>
      <g:actionSubmit class="delete" action="delete"
                      value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                      onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
    </fieldset>
  </g:form>
</div>
</body>
</html>
