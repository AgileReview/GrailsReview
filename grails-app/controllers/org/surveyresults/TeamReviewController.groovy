package org.surveyresults

class TeamReviewController {

	TeamReviewService teamReviewService
	TeamMemberService teamMemberService
	
    def results = {
		def teamReview = TeamReview.get(params.id)
		def currentUser = teamMemberService.getCurrentTeamMember(session)
		['reviewResults':teamReviewService.resultsForTeamMember(teamReview,currentUser)]
		
	}
}
