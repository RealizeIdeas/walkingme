package net.realizeideas.walkingme.search

import org.apache.commons.lang.time.StopWatch
import grails.converters.JSON
import java.util.concurrent.TimeUnit
import java.util.concurrent.Executors
import net.realizeideas.walkingme.places.Place
import net.realizeideas.walkingme.common.Location
import net.realizeideas.walkingme.authentication.User
import org.springframework.context.i18n.LocaleContextHolder

/**
 * @author Michael Astreiko
 */
class SearchController {
    def searchService
    def placesSearchExecutors
    def springSecurityService

    /**
     * Action to process search requests:
     * Main paras are 'mode', 'categoryCode' and 'search'.
     */
    def placesSearch = {
        StopWatch stopWatch = new StopWatch()
        stopWatch.start()
        User user = User.read(springSecurityService.currentUser?.id)

        Query query = new Query()
        query.keywords = ["pizza"] as Set//user.keywords?.collect{it.title}
        query.location = request.cookies.find {it.name == "location"}?.value?.decodeURL()
        query.language = LocaleContextHolder.locale.language

        List<SearchResult> searchResults = []
        //For all other searches ask google, nearme in parallel
        def threadPool = Executors.newFixedThreadPool(placesSearchExecutors.size() + 1)
        placesSearchExecutors.each {PlacesSearchExecutor searchExecutor ->
            threadPool.execute({
                try {
                    searchResults << searchExecutor.search(query)
                } catch (ex) {
                    log.error "Error occurred during execution of Search for searchExecutor ${searchExecutor.getClass()}: ${ex.message}", ex
                }
            })
        }
        threadPool.shutdown()
        if (!threadPool.awaitTermination(1, TimeUnit.MINUTES)) {
            log.warn "Executors making search for more than 1 min, so they will be shut down..."
        }

        def places = searchService.mergeAndSortResults(searchResults)
        stopWatch.stop()
        if (log.isInfoEnabled()) {
            log.info "Time executing search for keyword ${query.keywords?.join(";")} is ${stopWatch.toString()}"
        }
        render view: "places", model: [places:places]
    }

    def places = {
        [places:[
                new Place(publicId: "154643", title: "Titlewwwww", location:new Location(additional: "Minsk city")),
                new Place(publicId: "4352354", title: "gsdgsdgfsdfg", location:new Location(additional: "Minsk city")),
                new Place(publicId: "363546", title: "Titlsdfgsdfgewwwww", location:new Location(additional: "Minsk city")),
                new Place(publicId: "25445", title: "sdfgsdfgsd", location:new Location(additional: "Minsk ddddcity")),
        ]]
    }

}
