package org.surveyresults

class EvaluationController {

	EvaluationService evaluationService
	TeamMemberService teamMemberService
	def create = {
		def currentUser = teamMemberService.getCurrentTeamMember(session)
		if(currentUser == null){
			redirect(action:'login',controller:'teamMember')
			return
		}
		def review = Review.get(params.reviewID)
		def evalInstance = evaluationService.createBlankEvaluation(review,currentUser)
		def viewModel = new EvaluationViewModel(evaluationInstance: evalInstance)
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
		
		if(eval.validate()){
			def p = params
			eval.save(failOnError:true)
			redirect(controller:"review",action:"list")
		}
		else{
			def viewModel = new EvaluationViewModel(evaluationInstance: eval)
			viewModel.answers = Answer.findAll()
			render(view:"create",model:[evaluationViewModel:viewModel])
		}
		
		
	}
}
