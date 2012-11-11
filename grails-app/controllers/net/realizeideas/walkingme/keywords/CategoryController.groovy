package net.realizeideas.walkingme.keywords

import org.springframework.dao.DataIntegrityViolationException
import net.realizeideas.walkingme.keywords.Category
import net.realizeideas.walkingme.keywords.Keyword
import net.realizeideas.walkingme.authentication.User

class CategoryController {
    def springSecurityService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [categoryInstanceList: Category.list(params), categoryInstanceTotal: Category.count()]
    }

    def create() {
        [categoryInstance: new Category(params)]
    }

    def save() {
        def categoryInstance = new Category(params)
        if (!categoryInstance.save(flush: true)) {
            render(view: "create", model: [categoryInstance: categoryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
        redirect(action: "show", id: categoryInstance.id)
    }

    def show(Long id) {
        def categoryInstance = Category.get(id)
        def user = User.read(springSecurityService.currentUser.id)
        def keywords = Keyword.findAllByCategoryAndUser(categoryInstance, user)

        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }
        String view = 'show'
        withMobileDevice {
            view = 'm_show'
        }
        render (view: view, model: [
                category: categoryInstance,
                keywords: keywords
        ])
    }

    def edit(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }

        [categoryInstance: categoryInstance]
    }

    def update(Long id, Long version) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (categoryInstance.version > version) {
                categoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'category.label', default: 'Category')] as Object[],
                          "Another user has updated this Category while you were editing")
                render(view: "edit", model: [categoryInstance: categoryInstance])
                return
            }
        }

        categoryInstance.properties = params

        if (!categoryInstance.save(flush: true)) {
            render(view: "edit", model: [categoryInstance: categoryInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
        redirect(action: "show", id: categoryInstance.id)
    }

    def deleteKeyword(Long id){
        def keyword = Keyword.get(id)
        def category = keyword.category

        try {
            category.removeFromKeywords(keyword)
            keyword.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'category.label', default: 'Keyword'), id])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
        }
        redirect(action: "show", id: category.id)
    }

    def addKeyword(String title, Long categoryId) {
        def category = Category.get(categoryId)
        def user = User.get(springSecurityService.currentUser?.id)

        Keyword keyword = new Keyword(user: user, category: category)
        keyword.title = title

        if (!keyword.save(flush: true)) {
            flash.error = "Cannot save keyword. Try again later."
        } else {
            flash.message = "Keyword '${title}' saved."
        }


        redirect(action: "show", id: categoryId)
    }

    def delete(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
            return
        }

        try {
            categoryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
            redirect(action: "show", id: id)
        }
    }
}
