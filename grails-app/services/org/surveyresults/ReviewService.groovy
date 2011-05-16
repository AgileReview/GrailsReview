package org.surveyresults


class ReviewService {

    static transactional = false

    def reviewsLeftToComplete(def teamMember) {
		def done = reviewsCompleted(teamMember)
		def all = reviewsForOtherTeamMembers(teamMember)
		all.findAll { r->done.find {d-> d.id==r.id } == null}
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
}
