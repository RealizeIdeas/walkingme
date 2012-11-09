package net.realizeideas.walkingme.authentication

import grails.plugins.springsocial.UserConnection
import net.realizeideas.walkingme.common.Location
import net.realizeideas.walkingme.base.BasePersistentObject
import net.realizeideas.walkingme.keywords.Keyword
import net.realizeideas.walkingme.places.Checkin

class User extends BasePersistentObject {

    transient springSecurityService

    String username
    String email
    String gender
    String firstName
    String lastName
    String password
    Date birthday
    Location location
    UserConnection connection
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static hasMany = [keywords:Keyword, checkins:Checkin]

    static constraints = {
        username blank: false, unique: true
        password blank: false
        firstName(nullable: true)
        lastName(nullable: true)
        birthday(nullable: true)
        gender(nullable: true)
        location(nullable: true)
        connection(nullable: true)
        keywords(nullable: true)
        checkins(nullable: true)
    }

    static mapping = {
        password(column: '`password`')
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    def beforeInsert() {
        super.beforeInsert()
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    def afterDelete() {
        //Delete UserConnection with User
        UserConnection.withNewSession {
            if (connection) {
                UserConnection.findWhere(userId: connection.userId, providerId: connection.providerId,
                        providerUserId: connection.providerUserId).delete()
            }
        }
    }

    def beforeValidate() {
        if (!password) {
            password = username
        }

    }


    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}
