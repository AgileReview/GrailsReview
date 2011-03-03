package org.surveyresults

class ResponseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [responseInstanceList: Response.list(params), responseInstanceTotal: Response.count()]
    }

    def create = {
        def responseInstance = new Response()
        responseInstance.properties = params
        return [responseInstance: responseInstance]
    }

    def save = {
        def responseInstance = new Response(params)
        if (responseInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'response.label', default: 'Response'), responseInstance.id])}"
            redirect(action: "show", id: responseInstance.id)
        }
        else {
            render(view: "create", model: [responseInstance: responseInstance])
        }
    }

    def show = {
        def responseInstance = Response.get(params.id)
        if (!responseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'response.label', default: 'Response'), params.id])}"
            redirect(action: "list")
        }
        else {
            [responseInstance: responseInstance]
        }
    }

    def edit = {
        def responseInstance = Response.get(params.id)
        if (!responseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'response.label', default: 'Response'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [responseInstance: responseInstance]
        }
    }

    def update = {
        def responseInstance = Response.get(params.id)
        if (responseInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (responseInstance.version > version) {
                    
                    responseInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'response.label', default: 'Response')] as Object[], "Another user has updated this Response while you were editing")
                    render(view: "edit", model: [responseInstance: responseInstance])
                    return
                }
            }
            responseInstance.properties = params
            if (!responseInstance.hasErrors() && responseInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'response.label', default: 'Response'), responseInstance.id])}"
                redirect(action: "show", id: responseInstance.id)
            }
            else {
                render(view: "edit", model: [responseInstance: responseInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'response.label', default: 'Response'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def responseInstance = Response.get(params.id)
        if (responseInstance) {
            try {
                responseInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'response.label', default: 'Response'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'response.label', default: 'Response'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'response.label', default: 'Response'), params.id])}"
            redirect(action: "list")
        }
    }
}
