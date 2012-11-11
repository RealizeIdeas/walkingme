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

    transient countryService


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
        if (additional) {
            return additional
        }
        def result = ""
        if (street) {
            result += street
        }
        if (postalCode) {
            result += postalCode
        }
        if (city) {
            result += city
        }
        if (result) {
            result += ", "
        }
        if (countryCode) {
            result += countryService.getISO3166_2().find {it.value == countryCode}?.key ?: countryCode
        }
        if (!result) {
            result = "Undefined"
        }
        return result
    }
}
