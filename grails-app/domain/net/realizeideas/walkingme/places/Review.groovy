package net.realizeideas.walkingme.places

import net.realizeideas.walkingme.authentication.User
/**
 * @author Michael Astreiko
 */
class Review {
    String title
    String message

    Long ranking
    Integer likes

    static belongsTo = [place:Place, user:User]
    static constraints = {
        message(size: 1..5000)
        user(nullable: true)
    }
}
