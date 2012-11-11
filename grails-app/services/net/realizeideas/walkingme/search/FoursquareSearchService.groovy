package net.realizeideas.walkingme.search

import org.apache.commons.lang.time.StopWatch
import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.Method.GET
import groovyx.net.http.ContentType
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject
import net.realizeideas.walkingme.places.Place
import net.realizeideas.walkingme.common.Location
import java.text.SimpleDateFormat
import org.apache.commons.logging.LogFactory
import org.apache.commons.logging.Log

class FoursquareSearchService implements PlacesSearchExecutor {
    private static final Log log = LogFactory.getLog(FoursquareSearchService.class)
    def grailsApplication

    SearchResult search(Query query) {
        def foursquareResult

        StopWatch stopWatch = new StopWatch()
        stopWatch.start()

        def now = new Date();
        def df = new SimpleDateFormat("yyyyMMdd");
        def http = new HTTPBuilder("https://api.foursquare.com")
        http.request( GET, ContentType.TEXT ) {
            uri.path = '/v2/venues/search'
            uri.query = [ll: query.location, intent: 'checkin', query: query.keywords.join(" "), v: df.format(now),
                         client_id: grailsApplication.config.foursquare.clientId, client_secret: grailsApplication.config.foursquare.clientSecret]

            response.success = { resp, reader ->
                def searchResult = reader.text
                foursquareResult = JSON.parse(searchResult)
            }
        }
        if (log.isInfoEnabled()) {
            log.info("foursquareResult: ${foursquareResult?.toString()}")
        }

        List places = retrievePlaces(foursquareResult)

        stopWatch.stop()

        if (log.isInfoEnabled()) {
            log.info("Foursquare search with query ${query.keywords.join(" ")} and location ${query.location} took ${stopWatch.toString()} and found ${places.size()} places")
        }

        SearchResult searchResult = new SearchResult()
        searchResult.resultList = places
        searchResult.searchTime = stopWatch.getTime()
        return searchResult
    }

    private List retrievePlaces(foresquareResult) {
        def places = []
        if (foresquareResult.response != JSONObject.NULL) {
            foresquareResult.response.venues.each {venue ->
               try {
                    Place place = new Place()
                    place.publicId = venue.id
                    place.reference = venue.id
                    place.service = "Foursquare"
                    place.title = venue.name
                    place.websiteURL = venue.url

                    place.location = retrieveLocation(venue.location)
                    place.telephone = venue.contact?.formattedPhone

                    place.distance = venue.location.distance

                    places << place
                } catch (ex) {
                    log.error " ${ex.message}", ex
                }
            }
        }

        return places
    }

    private Location retrieveLocation(foresquareLocation) {
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

}
