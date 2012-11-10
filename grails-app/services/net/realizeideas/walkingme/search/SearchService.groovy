package net.realizeideas.walkingme.search


import uk.ac.shef.wit.simmetrics.similaritymetrics.Soundex
import net.realizeideas.walkingme.places.Place

/**
 * @author Michael Astreiko
 */
class SearchService {
    static transactional = false
    def distanceService

    static final int LIMIT_OF_PLACES = 20
    static final int TWO_HUNDRED_METERS = 200
    static final long SIMILARITY_BOUNDARY = 0.98 as long

    /**
     * Merge results from different Search Services (Google, Foursquare,...)
     * by name and distance to remove duplicates.
     *
     * Sort results: first shown Google results, then sorting by distance
     *
     * @param searchResults from different search services
     *
     * @return merged and sorted Places
     */
    List<Place> mergeAndSortResults(List<SearchResult> searchResults) {
        List<Place> resultedPlaces = searchResults*.resultList?.flatten()

        mergePlaces(resultedPlaces)
        def sortedPlaces = sortPlaces(resultedPlaces)
        if(LIMIT_OF_PLACES < sortedPlaces.size()){
            sortedPlaces = sortedPlaces[0..(LIMIT_OF_PLACES-1)]
        }
        return sortedPlaces
    }

    /**
     *
     * previously it was first display Google Places then sort by distance.
     *
     * @param resultedPlaces
     * @return
     */
    private List<Place> sortPlaces(List<Place> resultedPlaces) {
        return resultedPlaces?.sort {Place p1, Place p2 ->
            p1.distance <=> p2.distance
        }
    }

    /**
     * Merge Places based on Soundex algorithm and distance between Places less then 200 meters
     *
     *
     * @param resultedPlaces
     */
    private void mergePlaces(List<Place> resultedPlaces) {
        Soundex soundexAlgorithm = new Soundex()
        resultedPlaces?.removeAll {Place place ->
            //Remove only non-NearMe results
            if (place.service != "Google") {
                def serviceName = place.service
                if (resultedPlaces.find {
                    it.service != serviceName &&
                            distanceBetweenPlaces(it, place) < TWO_HUNDRED_METERS &&
                            soundexAlgorithm.getSimilarity(it.title, place.title) > SIMILARITY_BOUNDARY
                }) {
                    return true
                }
            }
            false
        }
    }

    private BigDecimal distanceBetweenPlaces(Place place1, Place place2) {
        return distanceService.calculateDistanceInMeters(place1.location?.latitude, place1.location?.longitude,
                place2.location?.latitude, place2.location?.longitude)
    }
}
