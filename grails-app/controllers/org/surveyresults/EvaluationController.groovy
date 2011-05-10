package org.surveyresults

class EvaluationController {

	EvaluationService evaluationService
	TeamMemberService teamMemberService
	def create = {
		if(teamMemberService.getCurrentTeamMember(session) == null){
			redirect(action:'login',controller:'teamMember')
			return
		}
		
		def review = Review.get(params.reviewID)
		def currentUser = teamMemberService.getCurrentTeamMember(session)
		def viewModel = new EvaluationViewModel(evaluation: evaluationService.createBlankEvaluation(review,currentUser))
		viewModel.answers = Answer.findAll()
		[evaluationViewModel:viewModel]
	}
	
	def save = {
		def currentUser = teamMemberService.getCurrentTeamMember(session)
		if( currentUser == null){
			redirect(action:'login',controller:'teamMember')
			return
		}
		def eval = new Evaluation(params)
		eval.responder = currentUser
		eval.save(failOnError:true)
		redirect(controller:"review",action:"list")
	}
}
