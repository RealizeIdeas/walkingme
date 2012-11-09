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


    /**
     * Details see on https://developers.facebook.com/docs/authentication/permissions
     * @return list of defined FB user permissions
     */
    static String FACEBOOK_PERMISSIONS = ["user_hometown",
            "user_interests",
            "user_likes",
            "user_location",
            "email",
            "offline_access",
            "publish_stream",
            "user_birthday",
            "user_online_presence",
            "user_about_me",
            "user_activities",
            "user_checkins",
            "user_education_history",
            "user_events",
            "user_games_activity",
            "user_groups",
            "user_notes",
            "user_actions.music",
            "user_actions.news",
            "user_actions.video",
            "user_photos",
            "user_questions",
            "user_relationships",
            "user_status",
            "user_subscriptions",
            "user_videos",
            // user stream table
            "read_stream",
            "read_insights"

            // remained
/*            ,"friends_about_me",
            "friends_actions.music",
            "friends_actions.news",
            "friends_actions.video",
            "friends_activities",
            "friends_birthday",
            "friends_education_history",
            "friends_events",
            "friends_games_activity",
            "friends_groups",
            "friends_hometown",
            "friends_interests",
            "friends_likes",
            "friends_location",
            "friends_notes",
            "friends_photos",
            "friends_questions",
            "friends_relationship_details",
            "friends_relationships",
            "friends_religion_politics",
            "friends_status",
            "friends_subscriptions",
            "friends_videos",
            "friends_website",
            "friends_work_history",
            "create_note",
            "video_upload",
            "read_mailbox",
            "share_item",
            "photo_upload",
            "status_update",
            "manage_notifications"
            ,"ads_management",
            "create_event",
            "export_stream",
            "friends_online_presence",
            "manage_friendlists",
            "publish_checkins",
            "read_friendlists",
            "read_insights",
            "read_page_mailboxes",
            "read_requests",
            "rsvp_event",
            "sms",
            "xmpp_login"*/
    ].join(',')

  /**
   * Details see on https://developers.google.com/+/api/oauth
   * @return list of defined Google user permissions
   */
  static String GOOGLE_PERMISSIONS = [
          "https://www.googleapis.com/auth/userinfo.profile",
          "https://www.googleapis.com/auth/userinfo#email",
          "https://www.googleapis.com/auth/plus.me"
//          "https://www.googleapis.com/auth/tasks",
//          "https://www-opensocial.googleusercontent.com/api/people"
          ].join(' ')

}
