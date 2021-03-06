package org.agilereview


class ReviewService {

    static transactional = false
	EvaluationService evaluationService
	TeamReviewService teamReviewService

    def evaluationsLeftToComplete(def teamMember) {
		def results = Review.findAll(" from Review as r join r.evaluations as e where e.complete=false and e.responder = ? and r.reviewee != ? ",[teamMember,teamMember])
		def evals = []
		results.each { r->r.each { e-> if(e.class==Evaluation) evals << e}}
		evals
    }
	
	def evaluationCompleted(def review){
		def incomplete = review.evaluations.find { e-> e.complete == false}
		if(incomplete ==null){
			review.complete = true
			review.save()
			teamReviewService.reviewCompleted review.teamReview
		}
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
	
	def completeReviewsForTeamMember(def teamMember){
		Review.withCriteria(){
			and{
				eq('reviewee',teamMember)
				teamReview{
					eq('complete',true)
				}
			}
		}
	}
	
    def createBlankReview(def reviewee,def team) {
		
		def review = new Review(reviewee:reviewee)
		team.findAll {x->x != reviewee}.each{ t-> review.addToEvaluations(evaluationService.createBlankEvaluation(t))}
		review
    }
	
	def calculateScoresForReview(Review review){
		def results = []
		def questions = []
		//get list of questions
		review.evaluations.responses.each { r-> questions.push(r.question)}
		//new up results for each question
		questions.each { q->results[q] = new ReviewResult(question:q)}
		
	}

}
