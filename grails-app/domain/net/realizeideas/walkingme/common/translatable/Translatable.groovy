package net.realizeideas.walkingme.common.translatable

import net.realizeideas.walkingme.keywords.Category

/**
 * @author Michael Astreiko
 */
class Translatable implements Serializable {

    static hasMany = [translations: TranslatableString]
    static belongsTo = [Category]

    String getValue(String languageId) {
        translations?.find {it.languageId == languageId}?.value
    }

    Translatable addValue(String languageId, String value) {
        def existedTranslation = translations?.find {it.languageId == languageId}
        if (existedTranslation) {
            existedTranslation.value = value
            return this
        } else {
            addToTranslations(new TranslatableString(languageId: languageId, value: value))
        }
    }
}
