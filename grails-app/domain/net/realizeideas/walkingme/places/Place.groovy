package net.realizeideas.walkingme.places

import net.realizeideas.walkingme.common.Location
import net.realizeideas.walkingme.common.Photo

/**
 * @author Michael Astreiko
 */
class Place {
    String title
    String description

    String telephone
    String websiteURL

    Location location
    Long ranking
    static hasMany = [reviews:Review, photos:Photo, checkins:Checkin]

    static constraints = {
        description(nullable: true, size: 1..5000)
        telephone(nullable: true)
        websiteURL(nullable: true)
        ranking(nullable: true)
        reviews(nullable: true)
        photos(nullable: true)
        checkins(nullable: true)
    }
}

