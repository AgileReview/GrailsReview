package org.surveyresults

class EvaluationController {

	EvaluationService evaluationService
	def create = {
		def review = Review.get(params.reviewID)
		def viewModel = new EvaluationViewModel(evaluation: evaluationService.createBlankEvaluation(review))
		viewModel.answers = Answer.findAll()
		[evaluationViewModel:viewModel]
	}
	
	def save = {
		def eval = new Evaluation(params)
		eval.save(failOnError:true)
		redirect(controller:"review",action:"list")
	}
}
