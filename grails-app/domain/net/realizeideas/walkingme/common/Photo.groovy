package net.realizeideas.walkingme.common

import net.realizeideas.walkingme.places.Place

/**
 * @author Michael Astreiko
 */
class Photo {
    String name
    String absoluteURL
    String prefix
    String suffix

    static belongsTo = [place:Place]
    static constraints = {
        name(nullable: true)
        absoluteURL(nullable: true)
        prefix(nullable: true)
        suffix(nullable: true)
    }
}
