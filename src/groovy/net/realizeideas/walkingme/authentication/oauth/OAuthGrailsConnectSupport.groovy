package net.realizeideas.walkingme.authentication.oauth

import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionFactory
import org.springframework.social.connect.support.OAuth1ConnectionFactory
import org.springframework.social.connect.support.OAuth2ConnectionFactory
import org.springframework.social.connect.web.ConnectSupport
import org.springframework.social.oauth1.OAuth1Operations
import org.springframework.social.oauth1.OAuth1Parameters
import org.springframework.social.oauth1.OAuth1Version
import org.springframework.social.oauth1.OAuthToken
import org.springframework.social.oauth2.AccessGrant
import org.springframework.social.oauth2.GrantType
import org.springframework.social.oauth2.OAuth2Operations
import org.springframework.social.oauth2.OAuth2Parameters
import org.springframework.util.MultiValueMap
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestAttributes

/**
 * @author Michael Astreiko
 */
class OAuthGrailsConnectSupport extends ConnectSupport {
    String actionId = "ssconnect"
    private static final String OAUTH_TOKEN_ATTRIBUTE = "oauthToken";
    String home
    Boolean useAuthenticateUrl

    public String buildOAuthUrl(ConnectionFactory<?> connectionFactory, NativeWebRequest request, MultiValueMap<String, String> additionalParameters) {
        if (connectionFactory instanceof OAuth1ConnectionFactory) {
            return buildOAuth1Url((OAuth1ConnectionFactory<?>) connectionFactory, request, additionalParameters)
        } else if (connectionFactory instanceof OAuth2ConnectionFactory) {
            return buildOAuth2Url((OAuth2ConnectionFactory<?>) connectionFactory, request, additionalParameters)
        } else {
            throw new IllegalArgumentException("ConnectionFactory not supported")
        }
    }

    public Connection<?> completeConnection(OAuth2ConnectionFactory<?> connectionFactory, NativeWebRequest request) {
        String code = request.getParameter("code")
        def providerId = connectionFactory.getProviderId()
        def curl = callbackUrl(request, providerId)
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, curl, null)
        return connectionFactory.createConnection(accessGrant)
    }

    protected String callbackUrl(NativeWebRequest request, String providerId) {
        "${home}${actionId}/${providerId}".toString()
    }

    private String buildOAuth1Url(OAuth1ConnectionFactory<?> connectionFactory, NativeWebRequest request, MultiValueMap<String, String> additionalParameters) {
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth1Parameters parameters = new OAuth1Parameters(additionalParameters);
        if (oauthOperations.getVersion() == OAuth1Version.CORE_10) {
            parameters.setCallbackUrl(callbackUrl(request, connectionFactory.providerId));
        }
        OAuthToken requestToken = fetchRequestToken(request, oauthOperations, connectionFactory.providerId);
        request.setAttribute(OAUTH_TOKEN_ATTRIBUTE, requestToken, RequestAttributes.SCOPE_SESSION);
        return buildOAuth1Url(oauthOperations, requestToken.getValue(), parameters);
    }

    private OAuthToken fetchRequestToken(NativeWebRequest request, OAuth1Operations oauthOperations, String providerId) {
        if (oauthOperations.getVersion() == OAuth1Version.CORE_10_REVISION_A) {
            return oauthOperations.fetchRequestToken(callbackUrl(request, providerId), null);
        }
        return oauthOperations.fetchRequestToken(null, null);
    }

    private String buildOAuth2Url(OAuth2ConnectionFactory<?> connectionFactory, NativeWebRequest request, MultiValueMap<String, String> additionalParameters) {
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters parameters = getOAuth2Parameters(request, additionalParameters, connectionFactory.providerId);
        if (useAuthenticateUrl) {
            return oauthOperations.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, parameters);
        } else {
            return oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);
        }
    }

    private OAuth2Parameters getOAuth2Parameters(NativeWebRequest request, MultiValueMap<String, String> additionalParameters, String providerId) {
        OAuth2Parameters parameters = new OAuth2Parameters(additionalParameters);
        parameters.setRedirectUri(callbackUrl(request, providerId));
        String scope = request.getParameter("scope");
        if (scope != null) {
            parameters.setScope(scope);
        }
        return parameters;
    }

}