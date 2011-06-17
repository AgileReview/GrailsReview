package org.surveyresults

class TeamReviewService {

    static transactional = true

	def reviewCompleted(def teamReview){
		def incomplete = teamReview.reviews.find { e-> e.complete == false}
		if(incomplete ==null){
			teamReview.complete = true
			teamReview.save()
		}
	}
}
