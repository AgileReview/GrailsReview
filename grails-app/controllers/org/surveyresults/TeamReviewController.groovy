package org.surveyresults

class TeamReviewController {

	TeamReviewService teamReviewService
	TeamMemberService teamMemberService
	
    def results = {
		def teamReview = TeamReview.get(params.id)
		def currentUser = teamMemberService.getCurrentTeamMember(session)
		currentUser = TeamMember.get(currentUser.id) //rehydrate
		['reviewResults':teamReviewService.resultsForTeamMember(teamReview,currentUser),'answers':Answer.list()]
		
	}
}
