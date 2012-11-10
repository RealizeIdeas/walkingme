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

        createRequestMap("/user/**", "IS_AUTHENTICATED_REMEMBERED")
        createRequestMap("/user/smallBoard", "IS_AUTHENTICATED_ANONYMOUSLY")
        createRequestMap("/user/board", "IS_AUTHENTICATED_ANONYMOUSLY")
        createRequestMap("/requestmap/**", "ROLE_ADMIN")

        createCategory("Foods", "en")
        createCategory("Sports", "en")
        createCategory("Intertainment", "en")
        createCategory("Music", "en")
        createCategory("Shopping", "en")

    }

    void createRequestMap(String url, String configAtt) {
        if (!Requestmap.findByUrl(url)) {
            new Requestmap(url: url, configAttribute: configAtt).save()
        }
    }

    void createCategory(String title, String language) {
        if (!Category.retrieveByTitle(title, language).list()) {
            new Category(title: createTranslations(language, title)).save(failOnError: true, flush: true)
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
