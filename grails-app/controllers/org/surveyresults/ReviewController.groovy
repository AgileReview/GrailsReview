package org.surveyresults

class ReviewController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [reviewInstanceList: Review.list(params), reviewInstanceTotal: Review.count()]
    }

    def create = {
        def reviewInstance = new Review()
        reviewInstance.properties = params
        return [reviewInstance: reviewInstance]
    }

    def save = {
        def reviewInstance = new Review(params)
        if (reviewInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'review.label', default: 'Review'), reviewInstance.id])}"
            redirect(action: "show", id: reviewInstance.id)
        }
        else {
            render(view: "create", model: [reviewInstance: reviewInstance])
        }
    }

    def show = {
        def reviewInstance = Review.get(params.id)
        if (!reviewInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'review.label', default: 'Review'), params.id])}"
            redirect(action: "list")
        }
        else {
            [reviewInstance: reviewInstance]
        }
    }

    def edit = {
        def reviewInstance = Review.get(params.id)
        if (!reviewInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'review.label', default: 'Review'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [reviewInstance: reviewInstance]
        }
    }

    def update = {
        def reviewInstance = Review.get(params.id)
        if (reviewInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (reviewInstance.version > version) {
                    
                    reviewInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'review.label', default: 'Review')] as Object[], "Another user has updated this Review while you were editing")
                    render(view: "edit", model: [reviewInstance: reviewInstance])
                    return
                }
            }
            reviewInstance.properties = params
            if (!reviewInstance.hasErrors() && reviewInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'review.label', default: 'Review'), reviewInstance.id])}"
                redirect(action: "show", id: reviewInstance.id)
            }
            else {
                render(view: "edit", model: [reviewInstance: reviewInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'review.label', default: 'Review'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def reviewInstance = Review.get(params.id)
        if (reviewInstance) {
            try {
                reviewInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'review.label', default: 'Review'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'review.label', default: 'Review'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'review.label', default: 'Review'), params.id])}"
            redirect(action: "list")
        }
    }
}
