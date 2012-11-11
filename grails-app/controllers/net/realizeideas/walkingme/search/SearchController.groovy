package net.realizeideas.walkingme.search

import org.apache.commons.lang.time.StopWatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.Executors
import net.realizeideas.walkingme.authentication.User
import org.springframework.context.i18n.LocaleContextHolder
import net.realizeideas.walkingme.keywords.Category

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

        if(!springSecurityService.isLoggedIn()){
            redirect(controller: "login", action: "join")
            return
        }

        String view = 'places'
        withMobileDevice {
            view = 'm_places'
        }

        StopWatch stopWatch = new StopWatch()
        stopWatch.start()
        User user = User.read(springSecurityService.currentUser?.id)

        Query query = new Query()
        query.keywords = user.keywords?.collect{it.title}
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
        def categories = Category.listOrderByTitle()
        render view: view, model: [
                places: places,
                categories: categories
        ]
    }
}
