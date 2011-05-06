package org.surveyresults

class EvaluationService {

    static transactional = true
	
	Person currentUser

    def createBlankEvaluation(def review) {
		currentUser = Person.findByName('Patrick Escarcega')
		def evaluation = new Evaluation(responder:currentUser)
		review.addToEvaluations(evaluation)
		Question.list().toArray().each(){q -> evaluation.addToResponses(new Response(question:q))}
		evaluation
    }
}
