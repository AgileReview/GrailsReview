package org.surveyresults

class EvaluationService {

    static transactional = false
	
	ReviewService reviewService

	def createBlankEvaluation(def responder) {
		
		def evaluation = new Evaluation(responder:responder,complete:false)
		Question.list().toArray().each(){q -> evaluation.addToResponses(new Response(question:q))}
		return evaluation
	}
	
	def complete(evaluation){
        evaluation.complete = true
		if(evaluation.validate()){
			evaluation.save(failOnError:true)
			reviewService.evaluationCompleted evaluation.review
			return true
		}
		return false
	}
	
	
}
