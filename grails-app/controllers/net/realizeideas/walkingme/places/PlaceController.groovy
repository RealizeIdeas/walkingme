package net.realizeideas.walkingme.places

class PlaceController {

    def placeService

    def show() {
        Place place
        if (params.service.equals("Foursquare")) {
            place = placeService.getFourSquarePlaceDetails(params.publicId)
        } else if (params.service.equals("Google")) {
            place = placeService.getGooglePlaceDetails(params.publicId)
        }


        render(view: "show", model: [placeInstance:place])
    }
}
