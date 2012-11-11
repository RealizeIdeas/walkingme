package net.realizeideas.walkingme.authentication

import org.springframework.dao.DataIntegrityViolationException
import org.apache.commons.lang.math.RandomUtils
import net.realizeideas.walkingme.keywords.Category
import org.apache.commons.lang.StringUtils
import net.realizeideas.walkingme.keywords.Keyword

/**
 * @author Michael Astreiko
 */
class UserController {
    def springSecurityService
    def userService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def create() {
        buildModel(new User(params))
    }

    /**
     * Ajax
     */
    def loadUserMap(){
        render(template: "/user/currentLocation")
    }

    def save() {
        def userInstance = new User(params)
        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }
        addRoles(userInstance)
        addKeywords(userInstance)
        flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def edit(Long id) {
        def userInstance
        if(params.username){
            userInstance = User.findByUsername(params.username)
        }
        if(!userInstance){
            userInstance = User.get(id)
        }
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }
        String view = 'edit'
        withMobileDevice {
            view = 'm_edit'
        }
        render view: view, model: buildModel(userInstance)
    }



    def update(Long id, Long version) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'user.label', default: 'User')] as Object[],
                        "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }
        userInstance.properties = params

        if (userInstance.hasErrors() || !userInstance.save()) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

        UserRole.removeAll(userInstance)
        addRoles(userInstance)
        addKeywords(userInstance)

        flash.message = "Your Likes were updated"
        redirect(uri: "/")
    }



    def delete(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        try {
            UserRole.removeAll(userInstance)
            userInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "show", id: id)
        }
    }

    private void addKeywords(User userInstance) {
        def keywords = []
        Category.list()?.each{category ->
            params.entrySet().findAll{it.key.indexOf("${category.id}_keywords[") > -1 && it.key.indexOf("keywords[]") < 0}?.each {
                def keywordKey = StringUtils.substringBetween(it.key, "_keywords[", "]")
                keywords << Keyword.get(Long.valueOf(keywordKey))
            }
        }
        userInstance.keywords?.findAll{!keywords.contains(it)}?.each{
            it.delete()
            userInstance.keywords.remove(it)
        }
        params.entrySet().findAll{it.key.indexOf("keywords[]") > -1}?.each{
            def categoryKey = StringUtils.substringBefore(it.key, "_")
            def category = Category.get(Long.valueOf(categoryKey))
            def values = []
            values.add(it.value)
            values = values.flatten()
            values.each{value ->
                def keyword = new Keyword()
                keyword.title = value
                keyword.category = category
                keyword.user = userInstance
                keyword.save()
                userInstance.keywords << keyword
            }
        }
    }

    private void addRoles(User userInstance) {
        for (String key in params.keySet()) {
            if (key.contains('ROLE') && 'on' == params.get(key)) {
                UserRole.create(userInstance, Role.findByAuthority(key), true)
            }
        }
    }

    private Map buildModel(User userInstance) {
        List roles = Role.list().sort { it.authority }
        Set userRoleNames = userInstance.authorities?.collect { it?.authority }
        def granted = [:]
        def notGranted = [:]
        for (role in roles) {
            String authority = role.authority
            if (userRoleNames.contains(authority)) {
                granted[(role)] = userRoleNames.contains(authority)
            }
            else {
                notGranted[(role)] = userRoleNames.contains(authority)
            }
        }

        def roleMap = granted + notGranted

        def categories = Category.listOrderByTitle()
        [userInstance: userInstance, roleMap: roleMap, categories: categories]
    }
}
