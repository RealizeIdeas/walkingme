package net.realizeideas.walkingme.common.translatable

/**
 * @author Michael Astreiko
 */
class TranslatableString implements Serializable {
    String languageId
    String value

    static belongsTo = [translatable: Translatable]
}
