package net.realizeideas.walkingme.common

import org.codehaus.groovy.grails.plugins.web.taglib.CountryTagLib

/**
 * @author Michael Astreiko
 */
class CountryService {
    static transactional = false
    static availableLocales = Locale.availableLocales
    static final ISO3166_2 = [:]
    static final COUNTRY_CODES_BY_NAME_ORDER = []

    static {

        CountryTagLib.ISO3166_3.each {key, value ->
            def twoLetterCountryCode = availableLocales.find {it.ISO3Country == key.toUpperCase()}?.country;
            if (twoLetterCountryCode) {
                ISO3166_2[twoLetterCountryCode] = value
            }
        }

        COUNTRY_CODES_BY_NAME_ORDER = ISO3166_2.entrySet().sort {
            a, b -> a.value.compareTo(b.value)
        }.collect() { it.key }
    }

    List getCountryCodes() {
        return COUNTRY_CODES_BY_NAME_ORDER
    }

    Map getISO3166_2() {
        return ISO3166_2
    }
}
