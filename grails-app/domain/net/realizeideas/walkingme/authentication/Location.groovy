package net.realizeideas.walkingme.authentication

/**
 * @author Michael Astreiko
 */
class Location {
    String countryCode
    String city
    String street
    String province
    String postalCode
    String additional

    BigDecimal latitude
    BigDecimal longitude


    static constraints = {
        street(nullable: true)
        province(nullable: true)
        postalCode(nullable: true)
        additional(nullable: true)
        latitude(nullable: true)
        longitude(nullable: true)
    }

}
