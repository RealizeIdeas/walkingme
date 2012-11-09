package net.realizeideas.walkingme.keywords

import net.realizeideas.walkingme.common.translatable.Translatable

/**
 * @author Michael Astreiko
 */
class Category {

    Translatable title
    static hasMany = [keywords:Keyword]

    static constraints = {
        keywords(nullable: true)
    }
}
