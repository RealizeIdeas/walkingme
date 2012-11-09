package net.realizeideas.walkingme.places

import net.realizeideas.walkingme.authentication.User
/**
 * @author Michael Astreiko
 */
class Checkin {
    static belongsTo = [user:User, place:Place]

}
