package org.surveyresults

class EvaluationService {

    static transactional = false
	
	TeamMemberService teamMemberService

    def createBlankEvaluation(def review,def responder) {
		
		def evaluation = new Evaluation(responder:responder)
		evaluation.review = review
		Question.list().toArray().each(){q -> evaluation.addToResponses(new Response(question:q))}
		return evaluation
    }
}
