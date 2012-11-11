import net.realizeideas.walkingme.authentication.User

/**
 * @author Michael Astreiko
 */
class UITagLib {
    def springSecurityService

    def translate = {attrs ->
        def translatable = attrs.value
        def languageId = "en"

        out << translatable.getValue(languageId)
    }

    def usernameFormatted = {attrs ->
        def user = User.read(springSecurityService.currentUser?.id)

        out << user.firstName + " " + user.lastName
    }

    def staticMap = {attrs ->
        def googleStaticMapsUrl = new StringBuilder()
        googleStaticMapsUrl << "http://maps.googleapis.com/maps/api/staticmap?size=${attrs.mapSize ?: '300x214'}&sensor=false"
        def locations = attrs.locations
        if (locations) {
            googleStaticMapsUrl << "&markers=color:red"
            locations.each {
                googleStaticMapsUrl << "|${it.latitude},${it.longitude}"
            }
        } else {
            def longitude = attrs.longitude
            def latitude = attrs.latitude
            googleStaticMapsUrl << "&markers=color:red|${latitude},${longitude}&zoom=14"
        }

        out << """<img alt="Location Map" src="${googleStaticMapsUrl.toString()}"/>"""
    }

}
