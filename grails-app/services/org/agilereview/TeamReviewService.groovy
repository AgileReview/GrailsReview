package org.agilereview

class TeamReviewService {

    static transactional = true
    ReviewService reviewService


	def reviewCompleted(def teamReview){
		def incomplete = teamReview.reviews.find { e-> e.complete == false}
		if(incomplete ==null){
			teamReview.complete = true
			teamReview.save()
		}
	}

    def createTeamReview(def name,def peopleToReview){
        def tr = new TeamReview(name:name)
        peopleToReview.each{tm->tr.addToReviews reviewService.createBlankReview(tm,tr)}
        tr
    }
	
	def resultsForTeamMember(def teamReview,def teamMember){
		def reviewForUser = teamReview.reviews.find { r->r.reviewee.id == teamMember.id}
		def results = reviewForUser.averageScores.collect { ques,av-> new ReviewResult(question:Question.get(ques),yourScore:av)}
		
		reviewForUser.maximumScores.each{qid,max-> results.find {res->res.question.id ==qid }.maxAnswer=max}
		reviewForUser.minimumScores.each{qid,min-> results.find {res->res.question.id ==qid }.minAnswer=min}
		teamReview.averageScores.each{qid,avg-> results.find {res->res.question.id ==qid }.teamAverage=avg}
		teamReview.getAverageScores(teamMember.role).each{qid,avg-> results.find {res->res.question.id ==qid }.roleAverage=avg}
		
		results
	}
	def commentsForTeamMember(def teamReview,def teamMember){
		teamReview.reviews.find { r->r.reviewee.id == teamMember.id}.comments
		
	}
	
}
