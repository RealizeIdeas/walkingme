package net.realizeideas.walkingme.places

class PlaceController {

    def placeService

    def show() {
        Place place

        String view = 'show'
        withMobileDevice {
            view = 'm_show'
        }

        if (params.service.equals("Foursquare")) {
            place = placeService.getFourSquarePlaceDetails(params.publicId)
        } else if (params.service.equals("Google")) {
            place = placeService.getGooglePlaceDetails(params.publicId)
        }


        render(view: view, model: [placeInstance:place])
    }
}
