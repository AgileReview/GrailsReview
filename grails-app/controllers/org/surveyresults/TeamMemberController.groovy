package org.surveyresults

class TeamMemberController {
	
	TeamMemberService teamMemberService
	ReviewService reviewService

    def index = { 
		def currentUser = teamMemberService.getCurrentTeamMember(session)
		def viewModel = new TeamMemberViewModel(evaluationsToComplete:reviewService.evaluationsLeftToComplete(currentUser),teamMember:currentUser)
		['teamMemberViewModel':viewModel]
	}
	
	def login = {}
	
	def doLogin = {
		def teamMember = TeamMember.findByEmailAndPassword(params.email,params.password)
		if (teamMember){
			session.teamMember = teamMember
			redirect(action:'list',controller:'review')
		}
		else{
			session.teamMember = null
			redirect(action:'login')
		}
    }

}
