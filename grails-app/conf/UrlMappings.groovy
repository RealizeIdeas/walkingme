class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "500"(view: '/error')

        name springOAuthSocialSignIn: "/ssoasignin/$providerId/$providerUserId?" {
            controller = 'oauthSpringSocialProviderSignIn'
            action = [GET: "oauthCallback", POST: 'signin']
        }

    }
}
