package net.realizeideas.walkingme.search

/**
 * @author Michael Astreiko
 */
class DistanceService {
    static transactional = false
    //average earth radius
    double EARTH_RADIUS = 6371
    BigDecimal KILOMETERS_IN_ONE_DEGREE = 111.319

    /**
     * Calculate distance based on Haversine algorithmus
     * (thanks to http://www.movable-type.co.uk/scripts/latlong.html)
     *
     * @param latitudeFrom
     * @param longitudeFrom
     * @param latitudeTo
     * @param longitudeTo
     * @return distance in Kilometers
     */
    BigDecimal calculateDistance(BigDecimal latitudeFrom, BigDecimal longitudeFrom,
                                 BigDecimal latitudeTo, BigDecimal longitudeTo) {

        def dLat = Math.toRadians(latitudeFrom - latitudeTo)
        def dLon = Math.toRadians(longitudeFrom - longitudeTo)

        //a = sin²(Δlat/2) + cos(lat1).cos(lat2).sin²(Δlong/2)
        //distance = 2.EARTH_RADIUS.atan2(√a, √(1−a))
        def a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(Math.toRadians(latitudeFrom)) *
                Math.cos(Math.toRadians(latitudeTo)) * Math.pow(Math.sin(dLon / 2), 2)
        return 2 * EARTH_RADIUS * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

    }

    BigDecimal calculateDistanceInMeters(BigDecimal latitudeFrom, BigDecimal longitudeFrom,
                                 BigDecimal latitudeTo, BigDecimal longitudeTo){
        (calculateDistance(latitudeFrom, longitudeFrom, latitudeTo, longitudeTo) * 1000).intValue()
    }

    /**
     *
     * @param coordinateInitial
     * @param distanceInKilometers
     * @return
     */
    BigDecimal coordinateOnDistance(BigDecimal coordinateInitial, BigDecimal distanceInKilometers) {
        return coordinateInitial + (distanceInKilometers / KILOMETERS_IN_ONE_DEGREE)
    }
}
