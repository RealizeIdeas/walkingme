package net.realizeideas.walkingme.authentication

import org.springframework.social.facebook.api.impl.FacebookTemplate
import grails.plugins.springsocial.UserConnection
import org.springframework.social.connect.ConnectionKey

import org.springframework.social.facebook.api.FqlResultMapper
import org.springframework.social.facebook.api.FqlResult
import org.apache.commons.logging.LogFactory
import org.apache.commons.logging.Log
import net.realizeideas.walkingme.keywords.Category
import org.springframework.social.facebook.api.Page
import net.realizeideas.walkingme.keywords.Keyword

/**
 * @author Michael Astreiko
 */
class OauthService {
    static transactional = true
    def imageService
    private static final Log log = LogFactory.getLog(OauthService.class)

    User createUserBasedOnProviderAndToken(ConnectionKey connectionKey) {
        def userConnection = UserConnection.findByProviderUserIdAndProviderId(connectionKey.providerUserId, connectionKey.providerId)
        FacebookTemplate facebookTemplate = new FacebookTemplate(userConnection.accessToken)
        User user = new User()
        def profile = facebookTemplate.userOperations().getUserProfile(connectionKey.providerUserId)
        log.error("facebook profile: ${profile.properties}")
        user.enabled = true
        user.username = "wm_" + connectionKey.providerId + "_" + connectionKey.providerUserId
        user.firstName = profile.firstName
        user.lastName = profile.lastName
        user.email = profile.email
        user.gender = profile.gender
        if (profile.hometown) {
            //user.location = new Location(city: profile.hometown)
        }
        if (profile.birthday) {
            user.birthday = Date.parse("MM/dd/yyyy", profile.birthday)
        }
        if (!user.email) {
            log.warn("Facebook did not return User email for some reasons, retirieveing it via FQL...")
            List users = facebookTemplate.fqlOperations().query('SELECT email FROM user WHERE uid=' + connectionKey.providerUserId,
                    new FqlResultMapper<User>() {
                        private static final Log internalLog = LogFactory.getLog(OauthService.class)

                        public User mapObject(FqlResult result) {
                            User fUser = new User()
                            fUser.email = result.getString('email')
                            internalLog.info("User's email: ${fUser.email}")
                            return fUser
                        }
                    })

            user.email = users ? users[0].email : null
        }


//        def profileImage = facebookTemplate.userOperations().getUserProfileImage()
//        if (profileImage) {
//            def imageName = "profile_photo.jpg"
//            imageName = imageService.uploadImageIS(imageName, new ByteArrayInputStream(profileImage), user.publicId, WorkareaConstants.USERS_PATH)
//            Image image = imageService.createImageDomainBasedOnUploadedFile(imageName, user.publicId, WorkareaConstants.USERS_PATH)
//            if (image) {
//                user.photo = image
//            }
//        }
        user.connection = userConnection
        return user
    }
}
