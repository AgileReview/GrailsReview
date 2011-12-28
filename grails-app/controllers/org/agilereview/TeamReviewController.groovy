package org.agilereview

class TeamReviewController {
	TeamReviewService teamReviewService
	TeamMemberService teamMemberService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }



    def results = {
		def teamReview = TeamReview.get(params.id)
		def currentUser = teamMemberService.getCurrentTeamMember(session)
		currentUser = TeamMember.get(currentUser.id) //rehydrate
		[
			'reviewResults':teamReviewService.resultsForTeamMember(teamReview,currentUser),
			'answers':Answer.list(),
			'comments':teamReviewService.commentsForTeamMember(teamReview,currentUser)
		]

	}

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [teamReviewInstanceList: TeamReview.list(params), teamReviewInstanceTotal: TeamReview.count()]
    }

    def create = {
        def teamReviewInstance = new TeamReview()
        teamReviewInstance.properties = params
        return [teamReviewInstance: teamReviewInstance]
    }

    def save = {
        def teamReviewInstance = teamReviewService.createTeamReview(params.name)
        if (!teamReviewInstance.save(flush: true)) {
            render(view: "create", model: [teamReviewInstance: teamReviewInstance])
        }
        else {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'teamReview.label', default: 'TeamReview'), teamReviewInstance.id])}"
            redirect(action: "show", id: teamReviewInstance.id)
        }
    }

    def show = {
        def teamReviewInstance = TeamReview.get(params.id)
        if (!teamReviewInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'teamReview.label', default: 'TeamReview'), params.id])}"
            redirect(action: "list")
        }
        else {
            [teamReviewInstance: teamReviewInstance]
        }
    }

    def edit = {
        def teamReviewInstance = TeamReview.get(params.id)
        if (!teamReviewInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'teamReview.label', default: 'TeamReview'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [teamReviewInstance: teamReviewInstance]
        }
    }

    def update = {
        def teamReviewInstance = TeamReview.get(params.id)
        if (teamReviewInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (teamReviewInstance.version > version) {
                    
                    teamReviewInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'teamReview.label', default: 'TeamReview')] as Object[], "Another user has updated this TeamReview while you were editing")
                    render(view: "edit", model: [teamReviewInstance: teamReviewInstance])
                    return
                }
            }
            teamReviewInstance.properties = params
            if (!teamReviewInstance.hasErrors() && teamReviewInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'teamReview.label', default: 'TeamReview'), teamReviewInstance.id])}"
                redirect(action: "show", id: teamReviewInstance.id)
            }
            else {
                render(view: "edit", model: [teamReviewInstance: teamReviewInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'teamReview.label', default: 'TeamReview'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def teamReviewInstance = TeamReview.get(params.id)
        if (teamReviewInstance) {
            try {
                teamReviewInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'teamReview.label', default: 'TeamReview'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'teamReview.label', default: 'TeamReview'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'teamReview.label', default: 'TeamReview'), params.id])}"
            redirect(action: "list")
        }
    }
}
