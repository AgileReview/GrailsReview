package org.surveyresults

class EvaluationController {

	EvaluationService evaluationService
	TeamMemberService teamMemberService
	def update = {
		def currentUser = teamMemberService.getCurrentTeamMember(session)
		if(currentUser == null){
			redirect(action:'login',controller:'teamMember')
			return
		}
		def evaluation = Evaluation.get(params.evaluationID)
		
		def viewModel = new EvaluationViewModel(evaluationInstance: evaluation)
		viewModel.answers = Answer.findAll()
		[evaluationViewModel:viewModel]
	}
	
	def save = {
		def currentUser = teamMemberService.getCurrentTeamMember(session)
		if( currentUser == null){
			redirect(action:'login',controller:'teamMember')
			return
		}
		println params
		def eval = Evaluation.get(params.id)
		params.complete = true
		eval.properties = params
		eval.responder = currentUser
		
		if(eval.validate()){
			def p = params
			eval.save(failOnError:true)
			redirect(controller:"teamMember",action:"index")
		}
		else{
			def viewModel = new EvaluationViewModel(evaluationInstance: eval)
			viewModel.answers = Answer.findAll()
			
			render(view:"update",model:[evaluationViewModel:viewModel])
		}

		
	}
}
