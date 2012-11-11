package net.realizeideas.walkingme.places

import net.realizeideas.walkingme.common.Location
import net.realizeideas.walkingme.common.Photo

/**
 * @author Michael Astreiko
 */
class Place {
    static transients = ['distance']
    //Id from Service
    String publicId
    //Unique only for Google Service, for other same like publicId
    String reference

    String title
    String description

    String service

    BigDecimal distance

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
        reference(nullable: true)
        publicId(unique: 'service')
    }
}

