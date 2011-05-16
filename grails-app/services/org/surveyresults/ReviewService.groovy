package org.surveyresults


class ReviewService {

    static transactional = false
	EvaluationService evaluationService

    def evaluationsLeftToComplete(def teamMember) {
		def results = Review.findAll(" from Review as r join r.evaluations as e where e.complete=false and e.responder = ? and r.reviewee != ? ",[teamMember,teamMember])
		def evals = []
		results.each { r->r.each { e-> if(e.class==Evaluation) evals << e}}
		evals
    }
	
	def reviewsCompleted(def teamMember){
		def reviewsCompleted = Review.withCriteria(){
			evaluations{
				eq("responder.id",teamMember.id)
			}
		}
	}
	
	def reviewsForOtherTeamMembers(def teamMember){
		Review.findAllByRevieweeNotEqual(teamMember)
	}
	
    def createBlankReview(def reviewee,def quarter) {
		def review = new Review(reviewee:reviewee,quarter:quarter)
		def team = TeamMember.findAllByIdNotEqual(reviewee.id)
		team.each{ t-> review.addToEvaluations(evaluationService.createBlankEvaluation(t))}
		review
    }
		

}
