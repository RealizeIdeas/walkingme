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
}
