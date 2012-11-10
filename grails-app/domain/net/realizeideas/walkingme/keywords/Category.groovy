package net.realizeideas.walkingme.keywords

import net.realizeideas.walkingme.common.translatable.Translatable

/**
 * @author Michael Astreiko
 */
class Category {

    Translatable title
    static hasMany = [keywords: Keyword]

    static namedQueries = {
        retrieveByTitle {titleToSearch, langId ->
            "title"{
                translations {
                    eq("languageId", langId)
                    ilike("%${titleToSearch}%")
                }
            }
            cache false
        }
    }

    static constraints = {
        keywords(nullable: true)
    }
}
