package net.realizeideas.walkingme.common

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

    @Override
    public String toString() {
        if(additional){
            return additional
        }
        return "${street?:''} ${postalCode?:''} ${city?:''}, ${countryCode}"
    }
}
