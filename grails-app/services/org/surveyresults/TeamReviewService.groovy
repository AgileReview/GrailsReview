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
	
	def resultsForTeamMember(def teamReview,def teamMember){
		def reviewForUser = teamReview.reviews.find { r->r.reviewee.id == teamMember.id}
		def results = reviewForUser.averageScores.collect { ques,av-> new ReviewResult(question:Question.get(ques),yourScore:av)}
		reviewForUser.maximumScores.each{qid,max-> results.find {res->res.question.id ==qid }.maxAnswer=max}
		reviewForUser.minimumScores.each{qid,min-> results.find {res->res.question.id ==qid }.minAnswer=min}
		teamReview.averageScores.each{qid,avg-> results.find {res->res.question.id ==qid }.teamAverage=avg}
		results
	}
}
