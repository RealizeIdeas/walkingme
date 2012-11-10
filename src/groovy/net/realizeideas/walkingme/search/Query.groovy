package net.realizeideas.walkingme.search


import org.springframework.context.i18n.LocaleContextHolder
import net.realizeideas.walkingme.AppConstants

/**
 * @author Michael Astreiko
 */
class Query {
    String freeText

    //User location
    String location

    String language
    //Paging params
    int max
    int page
    int offset

    void initSearchParams(params) {
        max = params.maxCount ? params.maxCount.toInteger() : AppConstants.MAX_RESULTS_IN_API_SEARCH
        page = params.page ? params.page as Integer : 0
        offset = params.offset ? params.offset.toInteger() : page * max


        language = params.hl ?: LocaleContextHolder.locale.language

        location = params.location

        freeText = params.search ?: null
    }


}
