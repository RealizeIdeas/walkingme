package net.realizeideas.walkingme.authentication.springsocial

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.social.connect.Connection
import net.realizeideas.walkingme.authentication.oauth.OAuthGrailsConnectSupport
import grails.plugins.springsocial.UserConnection
import org.springframework.social.connect.DuplicateConnectionException
import org.springframework.social.connect.ConnectionKey
import net.realizeideas.walkingme.authentication.User
import net.realizeideas.walkingme.authentication.UserRole
import net.realizeideas.walkingme.authentication.Role
import net.realizeideas.walkingme.keywords.Keyword
import net.realizeideas.walkingme.keywords.Category
import org.springframework.social.facebook.api.Page
import org.springframework.social.facebook.api.impl.FacebookTemplate
import org.springframework.social.facebook.api.FacebookLink
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.RememberMeAuthenticationToken

/**
 * Base Controller to handle OAuth 2 register/sigh in
 *
 * @author Michael Astreiko
 */
class OauthSpringSocialProviderSignInController {
    def connectionFactoryLocator
    def connectionRepository
    def usersConnectionRepository
    def oauthService
    def springSecurityService
    def userCache
    def webSupport = new OAuthGrailsConnectSupport(actionId: "ssoasignin")

    def signin = {
        def providerId = params.providerId
        def connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId)
        def nativeWebRequest = new GrailsWebRequest(request, response, servletContext)
        if (!webSupport.home) {
            webSupport.home = createLink(uri: "/", absolute: true)
        }
        def url = webSupport.buildOAuthUrl(connectionFactory, nativeWebRequest)
        redirect url: url
    }

    def oauthCallback = {
        def providerId = params.providerId

        def nativeWebRequest = new GrailsWebRequest(request, response, servletContext)

        try {
            def connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId)
            if (!webSupport.home) {
                webSupport.home = createLink(uri: "/", absolute: true)
            }
            handleSignIn(webSupport.completeConnection(connectionFactory, nativeWebRequest))
        } catch (ex) {
            log.error "Error in communication with facebook: ${ex.message}", ex
            flash.error = message(code: 'errors.login.facebook.fail')
            redirect(controller: "login", action: "auth")
        }
    }

    private String handleSignIn(Connection connection) {
        List<String> userIds = usersConnectionRepository.findUserIdsWithConnection(connection);
        if (!userIds) {
            log.info "No user found in the repository, creating a new one..."
            addConnection(connection)
            def connectionKey = connection.getKey()
            User user = oauthService.createUserBasedOnProviderAndToken(connectionKey)
            if (user.save()) {
                UserRole.create(user, Role.findByAuthority(Role.ROLE_BASIC), true)

                //Add keywords to User
                def facebookTemplate = new FacebookTemplate(user.connection.accessToken)
                List<Page> pagesLiked = facebookTemplate.likeOperations().getPagesLiked()
                log.error"All person facebook categories: ${pagesLiked*.category}"
                def categories = Category.list()
                def fbCategoryToCategoryMap = [:]
                categories.each{Category category ->
                    category.facebookCategories?.each {fbCategoryToCategoryMap[it.toLowerCase()] = category}
                }
                def allNeededFacebookCategories = fbCategoryToCategoryMap.keySet()
                pagesLiked.findAll {allNeededFacebookCategories.contains(it.category.toLowerCase())}?.each {Page page ->
                    Keyword keyword = new Keyword(user: user)
                    keyword.title = page.name
                    keyword.category = fbCategoryToCategoryMap[page.category.toLowerCase()]
                    keyword.save()

                    user.addToKeywords(keyword)
                }
                try {

                    facebookTemplate.feedOperations().postLink("Start looking for places of interest based on my Facebook Likes",
                            new FacebookLink("http://www.walkingme.com", "WalkingMe",
                                    "App to Walk You around the city.",
                                    "This Web App designed to walk you in the city based on your Facebook likes. Also available via mobile devices"))
                } catch (ex) {
                    log.error "Cannot publish on User's board - not enough permissions: ${ex.message}"
                }
                reAuthenticate(user)
                redirect(controller: "user", action: "edit", id: user?.id)
            } else {
                log.error "Can't save User $user.username: ${user.errors.allErrors}"
                removeConnection(connectionKey)
                redirect(controller: "login", action: "auth")
            }
        } else {
            connectionRepository.updateConnection(connection)
            def connectionKey = connection.getKey()
            def userConnection = UserConnection.findByProviderUserIdAndProviderId(
                    connectionKey.providerUserId, connectionKey.providerId)
            def user = User.findByConnection(userConnection)
            if (user) {
                log.info "User $user.username found in the repository..."
                reAuthenticate(user)
            } else {
                log.error "Connection for User ${userConnection.displayName} exist but User is not - something went wrong"
                removeConnection(connectionKey)
            }

            if (session.SPRING_SECURITY_SAVED_REQUEST_KEY) {
                redirect(url: session.SPRING_SECURITY_SAVED_REQUEST_KEY.redirectUrl)
            } else {
                //TODO: Document this setting
                redirect(controller: "search", action: "placesSearch")
            }
        }
    }

    private void addConnection(connection) {
        try {
            connectionRepository.addConnection(connection)
        } catch (DuplicateConnectionException e) {
            log.error("Exception during adding Connection: ${e.message}", e)
        }
    }

    private void removeConnection(ConnectionKey connectionKey) {
        try {
            connectionRepository.removeConnection(connectionKey)
        } catch (e) {
            log.error("Exception during removing Connection ${connectionKey}: ${e.message}", e)
        }
    }

    private reAuthenticate(User user) {
        SecurityContextHolder.getContext().setAuthentication(new RememberMeAuthenticationToken(
                grailsApplication.config.grails.plugins.springsecurity.rememberMe.key,
                user, user.getAuthorities()))
        userCache.removeUserFromCache(user.username);
    }

}
