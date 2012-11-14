package net.realizeideas.walkingme.search

import net.realizeideas.walkingme.common.Location

import groovyx.net.http.HTTPBuilder
import grails.converters.JSON
import groovyx.net.http.ContentType
import static groovyx.net.http.Method.GET
import org.apache.commons.lang.time.StopWatch

import org.codehaus.groovy.grails.web.json.JSONObject
import net.realizeideas.walkingme.places.Place
import org.apache.commons.logging.LogFactory
import org.apache.commons.logging.Log
import org.apache.commons.lang.math.RandomUtils

/**
 * Ask Google Places API for Places
 *
 * @author Michael Astreiko
 */
class GooglePlacesSearchService implements PlacesSearchExecutor {
    private static final Log log = LogFactory.getLog(GooglePlacesSearchService.class)
    static transactional = false

    def distanceService
    def grailsApplication

    SearchResult search(Query query) {
        def googleResult
        //When location not defined we need to return empty result
        if (!query.location) {
            return new SearchResult()
        }
        //Used to calculate distance
        def userLatitude = new BigDecimal(query.location.split(',')[0])
        def userLongitude = new BigDecimal(query.location.split(',')[1])
        def keywords = query.keywords
        List places = []
        StopWatch stopWatch = new StopWatch()
        stopWatch.start()
        def keyword = keywords?.join("|")
        keywords = keywords.toList()
        //Google search not always return result for all keywords in one query -> then do separate search for each keyword until get 20 results
        while (keyword && places.size() < 20) {
            keyword = keyword.trim()
            def http = new HTTPBuilder("https://maps.googleapis.com")
            http.request(GET, ContentType.TEXT) {
                uri.path = '/maps/api/place/nearbysearch/json'
                uri.query = [location: query.location, sensor: false,
                        keyword: keyword,
                        rankby: "distance",
//                    language: query.language,
                        key: grailsApplication.config.google.places.apiKey]

                headers.'Referer' = grailsApplication.config.grails.serverURL

                response.success = { resp, reader ->
                    def searchResult = reader.text
                    googleResult = JSON.parse(searchResult)
                }
            }
            if (log.isInfoEnabled()) {
                log.info("googleResult: ${googleResult?.toString()}")
            }

            places += retrievePlaces(googleResult, userLatitude, userLongitude,
                    "Cannot parse specific Google Place with query ${keyword} and location ${query.location}")
            if(keywords.size() > 0) {
                keyword = keywords.remove(RandomUtils.nextInt(keywords.size()))
            } else {
                keyword = null
            }
        }
        stopWatch.stop()
        if (log.isInfoEnabled()) {
            log.info("Google search with query ${query.keywords} and location ${query.location} took ${stopWatch.toString()} and found ${places.size()} places")
        }
        SearchResult searchResult = new SearchResult()
        searchResult.resultList = places
        searchResult.searchTime = stopWatch.getTime()
        return searchResult
    }

    /**
     * Place example API Response:
     *
     *
     * @param googleResult
     * @param userLatitude
     * @param userLongitude
     * @param errorMessage
     * @return
     */
    private ArrayList retrievePlaces(googleResult, userLatitude, userLongitude, errorMessage) {
        def places = []
        if (googleResult.results != JSONObject.NULL) {
            googleResult.results.each {googlePlace ->
                try {
                    Place place = new Place()
                    place.publicId = googlePlace.id
                    place.reference = googlePlace.reference
                    place.service = "Google"
                    if (Place.findByPublicIdAndService(place.publicId, place.service)) {
                        place = Place.findByPublicIdAndService(place.publicId, place.service)
                    }
                    place.title = googlePlace.name

                    place.location = retrieveLocation(googlePlace)

                    place.distance = distanceService.calculateDistanceInMeters(userLatitude, userLongitude,
                            place.location.latitude, place.location.longitude)
//                    place.save()

                    places << place
                } catch (ex) {
                    log.error errorMessage + ": ${ex.message}", ex
                }
            }
        }
        return places
    }

    /**
     *
     * @param googlePlace
     * @return
     */
    private Location retrieveLocation(googlePlace) {
        Location location = new Location()
        location.latitude = new BigDecimal(googlePlace.geometry.location.lat)
        location.longitude = new BigDecimal(googlePlace.geometry.location.lng)

        location.additional = googlePlace.vicinity
        return location
    }


}
