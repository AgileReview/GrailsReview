package org.surveyresults

class EvaluationService {

    static transactional = true
	
	TeamMemberService teamMemberService

    def createBlankEvaluation(def review,def responder) {
		
		def evaluation = new Evaluation(responder:responder)
		review.addToEvaluations(evaluation)
		Question.list().toArray().each(){q -> evaluation.addToResponses(new Response(question:q))}
		evaluation
    }
}
