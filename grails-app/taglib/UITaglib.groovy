/**
 * @author Michael Astreiko
 */
class UITagLib {
    def translate = {attrs ->
        def translatable = attrs.value
        def languageId = "en"

        out << translatable.getValue(languageId)
    }

}
