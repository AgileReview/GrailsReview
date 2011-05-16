package org.surveyresults

class EvaluationService {

    static transactional = false
	


	def createBlankEvaluation(def responder) {
		
		def evaluation = new Evaluation(responder:responder,complete:false)
		Question.list().toArray().each(){q -> evaluation.addToResponses(new Response(question:q))}
		return evaluation
	}
	
	
}
