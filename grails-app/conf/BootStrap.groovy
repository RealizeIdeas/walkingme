import net.realizeideas.walkingme.authentication.UserRole
import net.realizeideas.walkingme.authentication.User
import net.realizeideas.walkingme.authentication.Role
import net.realizeideas.walkingme.authentication.Requestmap
import net.realizeideas.walkingme.common.translatable.Translatable
import net.realizeideas.walkingme.keywords.Category

class BootStrap {

    def init = { servletContext ->
        def adminRole = Role.findByAuthority(Role.ROLE_ADMIN)
        if (!adminRole) {
            adminRole = new Role(authority: Role.ROLE_ADMIN).save(failOnError: true);
            def advRole = new Role(authority: Role.ROLE_ADVANCED).save(failOnError: true);
            def basicRole = new Role(authority: Role.ROLE_BASIC).save(failOnError: true);
            def admUser = new User(username: "admin", password: "admin1", firstName: "Vasya", lastName: "Pupkin", email: "fake@gmaol.com",
                    enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false).save(failOnError: true)
            UserRole.create(admUser, adminRole, true)
        }

        createRequestMap("/search/**", "IS_AUTHENTICATED_REMEMBERED")
        createRequestMap("/login/**", "IS_AUTHENTICATED_REMEMBERED")
        createRequestMap("/category/**", "IS_AUTHENTICATED_REMEMBERED")
        createRequestMap("/place/**", "IS_AUTHENTICATED_REMEMBERED")
        createRequestMap("/search/**", "IS_AUTHENTICATED_REMEMBERED")
        createRequestMap("/login/join", "IS_AUTHENTICATED_ANONYMOUSLY")
        createRequestMap("/login/auth", "IS_AUTHENTICATED_ANONYMOUSLY")
        createRequestMap("/requestmap/**", "ROLE_ADMIN")

        createCategory("Foods", "en", ["Bar", "Food/Beverages", "Food/Grocery", "Kitchen/Cooking", "Restaurant/Cafe",
                "Local Business", "Small Business"])
        createCategory("Sports", "en", ["Athlete", "Outdoor Gear/Sporting Goods", "Sports League", "Sports Venue",
                "Sports/Recreation/Activities", "Amateur Sports Team", "School Sports Team"])
        createCategory("Intertainment", "en", ["Club", "Hotel", "Movie", "Movie Theater", "Movies/Music",
                "Museum/Art Gallery", "Book", "Book Store", "Company", "Small Business", "Local Business", "Organization"])
        createCategory("Music", "en", ["Musician/Band", "Musical Instrument", "Playlist", "Radio Station",
                "Song", "Concert Tour", "Concert Venue", "Music Award", "Music Chart", "Music Video"])
        createCategory("Shopping", "en", ["Magazine",  "Clothing", "Spas/Beauty/Personal Care",
                "Jewelry/Watches",  "Shopping/Retail", "Product/Service",
                "Business ServicesConsulting/Business Services"])
    }

    void createRequestMap(String url, String configAtt) {
        if (!Requestmap.findByUrl(url)) {
            new Requestmap(url: url, configAttribute: configAtt).save()
        }
    }

    void createCategory(String title, String language, List facebookCategories) {
        if (!Category.retrieveByTitle(title, language).list()) {
            new Category(title: createTranslations(language, title), facebookCategories: facebookCategories.toSet()).save(failOnError: true, flush: true)
        }
    }

    private Translatable createTranslations(String lang, String value) {
        Translatable translatable = new Translatable()
        translatable.addValue(lang, value)
        translatable.save()
        return translatable
    }



    def destroy = {
    }
}
