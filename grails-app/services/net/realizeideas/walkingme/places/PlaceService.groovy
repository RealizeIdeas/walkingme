package net.realizeideas.walkingme.places

import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.Method.GET
import groovyx.net.http.ContentType
import grails.converters.JSON
import net.realizeideas.walkingme.common.Location
import net.realizeideas.walkingme.common.Photo

class PlaceService {
    def grailsApplication

    Place getGooglePlaceDetails(venueId) {
        def venueJson
        def http = new HTTPBuilder("https://maps.googleapis.com")
        http.request(GET, ContentType.TEXT) {
            uri.path = "/maps/api/place/details/json"
            uri.query = [reference: venueId, sensor: false,
                    key: grailsApplication.config.google.places.apiKey]

            response.success = { resp, reader ->
                def searchResult = reader.text
                venueJson = JSON.parse(searchResult)?.result
            }
        }
        if (log.isInfoEnabled()) {
            log.info("Google Result: ${venueJson?.toString()}")
        }

        return retrieveGooglePlace(venueJson)
    }

    Place getFourSquarePlaceDetails(venueId) {
        def venueJson
        def http = new HTTPBuilder("https://api.foursquare.com")
        http.request(GET, ContentType.TEXT) {
            uri.path = "/v2/venues/${venueId}"
            uri.query = [client_id: grailsApplication.config.foursquare.clientId,v:'20121111',
                    client_secret: grailsApplication.config.foursquare.clientSecret]

            response.success = { resp, reader ->
                def searchResult = reader.text
                venueJson = JSON.parse(searchResult)?.response?.venue
            }
        }
//        if (log.isInfoEnabled()) {
            log.error("foursquareResult: ${venueJson?.toString()}")
//        }

        return retrieveFoursquarePlace(venueJson)
    }

    private Place retrieveGooglePlace(venueJson) {
        Place place = new Place()
        place.title = venueJson.name
        place.publicId = venueJson.id
        place.service = "Google"
        place.title = venueJson.name
        place.websiteURL = venueJson.website

        place.location = retrieveGoogleLocation(venueJson)
        place.telephone = venueJson.international_phone_number

        place.ranking = venueJson.rating
        return place
    }

    private Location retrieveGoogleLocation(venueJson) {
        println "venueJson: $venueJson"
        def addresses = venueJson.address_components
        addresses.each{println it}
        Location location = new Location()
        location.latitude = new BigDecimal(venueJson.geometry.location.lat)
        location.longitude = new BigDecimal(venueJson.geometry.location.lng)
        location.city = addresses.find{it.types.contains('locality')}?.long_name
        location.street = addresses.find{it.types.contains('route')}?.long_name + " " + addresses.find{it.types.contains('route')}?.street_number
        location.postalCode = addresses.find{it.types.contains('postal_code')}?.long_name
        location.countryCode = addresses.find{it.types.contains('country')}?.long_name
        location.additional = venueJson.formatted_address

        return location
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
        return place
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
