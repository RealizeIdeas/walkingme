package net.realizeideas.walkingme

/**
 * @author Michael Astreiko
 */
class AppConstants {

    static final String JQUERY_DATE_FORMAT = "dd/mm/y"
    static final String BASE_DATE_FORMAT_WITH_MINUTES = "dd/MM/yy hh:mm"
    static final String BASE_DATE_FORMAT = "dd/MM/yy"
    static final String PROVIDER_FACEBOOK = "Facebook"
    static final String PROVIDER_TWITTER = "Twitter"
    static final String PROVIDER_GOOGLE = "Google"
    static final int MAX_RESULTS_IN_API_SEARCH = 20


    /**
     * Details see on https://developers.facebook.com/docs/authentication/permissions
     * @return list of defined FB user permissions
     */
    static String FACEBOOK_PERMISSIONS = ["user_hometown",
            "user_interests",
            "user_likes",
            "user_location",
            "email",
            "publish_stream",
            "user_birthday",
            "user_activities",
            "user_checkins",
            "user_events"
    ].join(',')

}