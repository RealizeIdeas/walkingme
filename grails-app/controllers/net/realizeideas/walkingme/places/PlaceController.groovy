package net.realizeideas.walkingme.places

class PlaceController {

    def placeService

    def show = {
        if (params.service.equals("Foursquare")) {
            def place = placeService.getFoursquarePlaceDetils(params.publicId)
        } else if (params.service.equals("Google")) {

        }
    }
}
