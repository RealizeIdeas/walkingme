package net.realizeideas.walkingme.common

import net.realizeideas.walkingme.places.Place
/**
 * @author Michael Astreiko
 */
class Photo {
    String name
    String absoluteURL

    static belongsTo = [place:Place]
    static constraints = {
        name(nullable: true)
    }
}
