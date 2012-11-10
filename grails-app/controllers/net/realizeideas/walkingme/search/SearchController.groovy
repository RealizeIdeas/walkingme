package net.realizeideas.walkingme.search

import org.apache.commons.lang.time.StopWatch
import grails.converters.JSON
import java.util.concurrent.TimeUnit
import java.util.concurrent.Executors

/**
 * @author Michael Astreiko
 */
class SearchController {
    def searchService
    def placesSearchExecutors

    /**
     * Action to process search requests:
     * Main paras are 'mode', 'categoryCode' and 'search'.
     */
    def venueSearch = {
        StopWatch stopWatch = new StopWatch()
        stopWatch.start()
        Integer searchMode = params.mode ? params.mode as Integer : 0
        Query query = searchService.retrieveQuery(searchMode, params.categoryCode, params.search)
        query.initSearchParams(params)

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
        def venues = searchService.mergeAndSortResults(searchResults)

        stopWatch.stop()
        if (log.isInfoEnabled()) {
            log.info "Time executing search for keyword ${query.freeText} is ${stopWatch.toString()}"
        }
        def result = [meta: [code: 200], response: [:]]
        result.response['venues'] = venues ?: null
        if (venues) {
            result.response['count'] = venues.size()
        }
        def converter = result as JSON
        render text: converter.toString(), contentType: 'application/json'
    }

}
