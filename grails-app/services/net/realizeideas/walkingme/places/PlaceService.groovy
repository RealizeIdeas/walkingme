package net.realizeideas.walkingme.places

import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.Method.GET
import groovyx.net.http.ContentType
import grails.converters.JSON
import net.realizeideas.walkingme.common.Location
import net.realizeideas.walkingme.common.Photo

class PlaceService {

    def getFourSquarePlaceDetails(venueId) {
        def venueJson
        def http = new HTTPBuilder("https://api.foursquare.com")
        http.request( GET, ContentType.TEXT ) {
            uri.path = "/v2/venues/${venueId}"
            uri.query = [client_id: grailsApplication.config.foursquare.clientId, client_secret: grailsApplication.config.foursquare.clientSecret]

            response.success = { resp, reader ->
                def searchResult = reader.text
                venueJson = JSON.parse(searchResult)
            }
        }
        if (log.isInfoEnabled()) {
            log.info("foursquareResult: ${venueJson?.toString()}")
        }

        def place = retrieveFoursquarePlace(venueJson)
    }

    private Place retrieveFoursquarePlace(venueJson) {
        Place place = new Place()
        place.title = venueJson.name
        place.publicId = venueJson.id
        place.service = "Foursquare"
        place.title = venueJson.name
        place.websiteURL = venueJson.url

        place.location = retrieveFoursquareLocation(venueJson.location)
        place.telephone = venueJson.contact?.formattedPhone

        place.distance = venueJson.location.distance
        place.ranking = venueJson.rating
        place.photos = retrieveFoursquarePhotos(venueJson.photos)

    }

    private Location retrieveFoursquareLocation(foresquareLocation) {
        Location location = new Location()
        location.latitude = new BigDecimal(foresquareLocation.lat)
        location.longitude = new BigDecimal(foresquareLocation.lng)
        location.city = foresquareLocation.city
        location.province = foresquareLocation.state
        location.street = foresquareLocation.address
        location.postalCode = foresquareLocation.postalCode
        location.countryCode = foresquareLocation.cc

        return location
    }

    private List retrieveFoursquarePhotos(photos) {
        def result = []
        photos?.groups?.find{it.type == "venue"}?.items?.each { item ->
            Photo photo = new Photo()
            photo.absoluteURL = "${item.prefix}/300x300/${item.suffix}"
            result << photo
        }
        return result

    }
}
