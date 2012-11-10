package net.realizeideas.walkingme.search

import net.realizeideas.walkingme.common.Location

import groovyx.net.http.HTTPBuilder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.converters.JSON
import groovyx.net.http.ContentType
import static groovyx.net.http.Method.GET
import org.apache.commons.lang.time.StopWatch

import org.codehaus.groovy.grails.web.json.JSONObject
import net.realizeideas.walkingme.places.Place

/**
 * Ask Google Places API for Places
 *
 * Use Deprecated Local Search API: https://developers.google.com/maps/documentation/localsearch/jsondevguide?hl=en
 *
 * Places API not used because it does not return phone number and detailsUrl in result, has no paging and return
 * max 20 items per query.
 *
 * @author Michael Astreiko
 */

class GooglePlacesSearchService implements PlacesSearchExecutor{
        static transactional = false

    def countryService
    def distanceService

    SearchResult search(Query query) {
        def googleResult
        //When location not defined we need to return empty result
        if (!query.location) {
            return new SearchResult()
        }
        //Used to calculate distance
        def userLatitude = new BigDecimal(query.location.split(',')[0])
        def userLongitude = new BigDecimal(query.location.split(',')[1])

        def keyword = query.freeText
        keyword = keyword.trim()

        int max = query.max

        StopWatch stopWatch = new StopWatch()
        stopWatch.start()
        def http = new HTTPBuilder("https://ajax.googleapis.com")
        http.request( GET, ContentType.TEXT ) {
            uri.path = '/ajax/services/search/local'
            uri.query = [sll: query.location, v: "1.0", rsz: max, start: query.page * max,
                        q: keyword, hl:query.language]

            headers.'Referer' = ConfigurationHolder.config.grails.serverURL

            response.success = { resp, reader ->
              def searchResult = reader.text
              googleResult = JSON.parse(searchResult)
            }
        }
        if (log.isInfoEnabled()) {
            log.info("googleResult: ${googleResult?.toString()}")
        }

        List places = []
        if (googleResult?.responseData?.cursor?.currentPageIndex == query.page) {
            places = retrievePlaces(googleResult, userLatitude, userLongitude,
                    "Cannot parse specific Google Place with query ${keyword} and location ${query.location}")
        }
        stopWatch.stop()

        if (log.isInfoEnabled()) {
            log.info("Google search with query ${keyword} and location ${query.location} took ${stopWatch.toString()}")
        }
        SearchResult searchResult = new SearchResult()
        searchResult.resultList = places
        searchResult.pageSize = max
        searchResult.offset = query.page * max
        searchResult.searchTime = stopWatch.getTime()
        return searchResult
    }

    /**
     * Place example API Response:
     *
     * {"GsearchResultClass":"GlocalSearch",
     *   "viewportmode":"computed",
     *   "listingType":"local",
     *   "lat":"45.46337",
     *   "lng":"9.193619",
     *   "accuracy":"8",
     *   "title":"Il Rosa Al Caminetto - \u003cb\u003eRistorante\u003c/b\u003e",
     *   "titleNoFormatting":"Il Rosa Al Caminetto Ristorante",
     *   "ddUrl":"http://www.google.com/maps?source\u003duds\u0026daddr\u003dVia+Cesare+Beccaria,+4,+Milano,+Lombardia+(Il+Rosa+Al+Caminetto+-+Ristorante)+@45.46337,9.193619\u0026saddr\u003dmilano",
     *   "ddUrlToHere":"http://www.google.com/maps?source\u003duds\u0026daddr\u003dVia+Cesare+Beccaria,+4,+Milano,+Lombardia+(Il+Rosa+Al+Caminetto+-+Ristorante)+@45.46337,9.193619\u0026iwstate1\u003ddir:to",
     *   "ddUrlFromHere":"http://www.google.com/maps?source\u003duds\u0026saddr\u003dVia+Cesare+Beccaria,+4,+Milano,+Lombardia+(Il+Rosa+Al+Caminetto+-+Ristorante)+@45.46337,9.193619\u0026iwstate1\u003ddir:from",
     *   "streetAddress":"Via Cesare Beccaria, 4",
     *   "city":"Milano",
     *   "region":"Lombardia",
     *   "country":"Italy",
     *   "staticMapUrl":"http://maps.google.com/maps/api/staticmap?maptype\u003droadmap\u0026format\u003dgif\u0026sensor\u003dfalse\u0026size\u003d150x100\u0026zoom\u003d13\u0026markers\u003d45.46337,9.193619",
     *   "url":"http://www.google.com/maps/place?source\u003duds\u0026q\u003dristoranti\u0026cid\u003d4331285673588107158",
     *   "content":"",
     *   "maxAge":604800,
     *   "phoneNumbers":[{"type":"","number":"02 8812 7833"}],
     *   "addressLines":["Via Cesare Beccaria, 4","20122 Milano, Italia"]
     *   }
     *
     * @param googleResult
     * @param userLatitude
     * @param userLongitude
     * @param errorMessage
     * @return
     */
    private ArrayList retrievePlaces(googleResult, userLatitude, userLongitude, errorMessage) {
        def palces = []
        if (googleResult.responseData != JSONObject.NULL) {
            googleResult.responseData.results.each {googlePlace ->
                try {
                    Place place = new Place()
                    place.service = "Google"
                    place.title = googlePlace.titleNoFormatting

                    place.location = retrieveLocation(googlePlace)
                    place.telephone = getPhoneNumber(googlePlace.phoneNumbers)

                    place.distance = distanceService.calculateDistanceInMeters(userLatitude, userLongitude,
                            place.location.latitude, place.location.longitude)

                    palces << place
                } catch (ex) {
                    log.error errorMessage + ": ${ex.message}", ex
                }
            }
        }
        return palces
    }

    /**
     * API Response is:
     * {"GsearchResultClass":"GlocalSearch",
     *   "lat":"45.46337",
     *   "lng":"9.193619",
     *   "streetAddress":"Via Cesare Beccaria, 4",
     *   "city":"Milano",
     *   "region":"Lombardia",
     *   "country":"Italy",
     *   "addressLines":["Via Cesare Beccaria, 4","20122 Milano, Italia"]
     *        ...
     *   }
     *
     * @param googlePlace
     * @return
     */
    private Location retrieveLocation(googlePlace) {
        Location location = new Location()
        location.latitude = new BigDecimal(googlePlace.lat)
        location.longitude = new BigDecimal(googlePlace.lng)
        location.city = googlePlace.city
        location.province = googlePlace.region
        location.street = googlePlace.streetAddress
        location.countryCode = countryService.getISO3166_2().find {it.value == googlePlace.country}?.key ?: googlePlace.country

        location.additional = googlePlace.addressLines.collect {it.toString()}.join(', ')
        return location
    }

    /**
     * Phone number format: [{"type":"","number":"02 8812 7833"}, {"type":"fax", "number":"01 8458 4587"}]
     *
     * @param phoneNumbers
     * @return
     */
    private String getPhoneNumber(phoneNumbers) {

        phoneNumbers?.each {phoneNumber ->
            if ('fax'.equalsIgnoreCase(phoneNumber.type)) {
                return phoneNumber.number
            } else if ('mobile'.equalsIgnoreCase(phoneNumber.type)) {
                return phoneNumber.number
            } else {
                return phoneNumber.number
            }
        }
    }

}
