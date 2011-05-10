package org.surveyresults

class TeamMemberController {

    def index = { }
	
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
