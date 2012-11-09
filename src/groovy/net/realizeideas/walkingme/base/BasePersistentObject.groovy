package net.realizeideas.walkingme.base

/**
 * Base object which contains common properties
 *
 * @author Michael Astreiko
 */
abstract class BasePersistentObject implements Serializable {

    transient springSecurityService

    static mapping = {
        tablePerHierarchy false
    }

    Date dateCreated // autoupdated by GORM
    Date lastUpdated // autoupdated by GORM
    String createdBy

    static constraints = {
        createdBy(nullable: true)
    }

    def beforeInsert() {
        if (!createdBy) {
            //Need to determine user who created object

            // use '?' after springSecurityService because there is a bug in Grails:
            //when populating collection of object like user.addCommission or venue.addVideo related objects
            //(commission, video) has no springSecurityService inserted
            //@todo: re-check in new version (current is 1.3.7)
            def user = springSecurityService?.getCurrentUser()
            createdBy = user?.username ?: 'system'
        }
    }
}
