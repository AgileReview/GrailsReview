package org.agilereview

class TeamMemberController {
	TeamMemberService teamMemberService
	ReviewService reviewService

	def index = {
		def currentUser = teamMemberService.getCurrentTeamMember(session)
		
		if(!currentUser){
			redirect(action:'login')
			return
		}
		def evaluationsToComplete = reviewService.evaluationsLeftToComplete(currentUser)
		def resultsToView = reviewService.completeReviewsForTeamMember(currentUser)
		def viewModel = new TeamMemberViewModel(resultsToView:resultsToView,evaluationsToComplete:evaluationsToComplete,teamMember:currentUser)
		['teamMemberViewModel':viewModel]
	}
	
	def login = {}
	
	def changePassword = {}
	
	def doChangePassword = {
		def tm = TeamMember.get(session.teamMember.id)
		tm.password = params.newPassword
		flash.message = 'Your password has been changed.'
		redirect(action:'index')
	}
	
	def logout = {
		session.teamMember = null
		redirect(action:'login')
	}
	
	def doLogin = {
		def teamMember = TeamMember.findByEmailAndPassword(params.email,params.password)
		if (teamMember){
			session.teamMember = teamMember
			redirect(action:'index',controller:'teamMember')
		}
		else{
			session.teamMember = null
			flash.message = 'Incorrect user name or password.'
			redirect(action:'login')
		}
	}
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]



    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [teamMemberInstanceList: TeamMember.list(params), teamMemberInstanceTotal: TeamMember.count()]
    }

    def create = {
		if(!enforceManager()){ return }

        def teamMemberInstance = new TeamMember()
        teamMemberInstance.properties = params
        return [teamMemberInstance: teamMemberInstance]
    }
	
	def enforceManager(){
		def tm = teamMemberService.getCurrentTeamMember(session)
		if((tm?.role?.name =='Manager') || params.id.toString() == tm.id.toString()){
            return true
		}
		response.sendError 401,'Only managers can manage other users profiles.'
		return false
	}

    def save = {
		if(!enforceManager()){ return }
        def teamMemberInstance = new TeamMember(params)
        if (teamMemberInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'teamMember.label', default: 'TeamMember'), teamMemberInstance.id])}"
            redirect(action: "show", id: teamMemberInstance.id)
        }
        else {
            render(view: "create", model: [teamMemberInstance: teamMemberInstance])
        }
    }

    def show = {
		if(!enforceManager()){ return }
        def teamMemberInstance = TeamMember.get(params.id)
        if (!teamMemberInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'teamMember.label', default: 'TeamMember'), params.id])}"
            redirect(action: "list")
        }
        else {
            [teamMemberInstance: teamMemberInstance]
        }
    }

    def edit = {

		if(!enforceManager()){ return }
        def teamMemberInstance = TeamMember.get(params.id)
        if (!teamMemberInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'teamMember.label', default: 'TeamMember'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [teamMemberInstance: teamMemberInstance]
        }
    }

    def update = {
		if(!enforceManager()){ return }
        def teamMemberInstance = TeamMember.get(params.id)
        if (teamMemberInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (teamMemberInstance.version > version) {
                    
                    teamMemberInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'teamMember.label', default: 'TeamMember')] as Object[], "Another user has updated this TeamMember while you were editing")
                    render(view: "edit", model: [teamMemberInstance: teamMemberInstance])
                    return
                }
            }
            teamMemberInstance.properties = params
            if (!teamMemberInstance.hasErrors() && teamMemberInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'teamMember.label', default: 'TeamMember'), teamMemberInstance.id])}"
                redirect(action: "show", id: teamMemberInstance.id)
            }
            else {
                render(view: "edit", model: [teamMemberInstance: teamMemberInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'teamMember.label', default: 'TeamMember'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
		if(!enforceManager()){ return }
        def teamMemberInstance = TeamMember.get(params.id)
        if (teamMemberInstance) {
            try {
                teamMemberInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'teamMember.label', default: 'TeamMember'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'teamMember.label', default: 'TeamMember'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'teamMember.label', default: 'TeamMember'), params.id])}"
            redirect(action: "list")
        }
    }
}
