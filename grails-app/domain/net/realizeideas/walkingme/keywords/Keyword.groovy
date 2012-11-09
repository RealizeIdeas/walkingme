package net.realizeideas.walkingme.keywords

import net.realizeideas.walkingme.base.BasePersistentObject
import net.realizeideas.walkingme.authentication.User

/**
 * @author Michael Astreiko
 */
class Keyword extends BasePersistentObject {
    String title

    static belongsTo = [user:User, category:Category]

    static mapping = {
        table "keywords"
    }
}
