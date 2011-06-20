package org.surveyresults

import grails.test.*

class TeamReviewServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_completing_a_review_when_other_reviews_are_complete_completes_teamreview_and_saves() {
		mockDomain(TeamReview,[])
		def saved = false
		TeamReview.metaClass.save = {-> saved=true}
		def tr = new TeamReview(complete:false)
		tr.addToReviews(new Review(complete:true))
		tr.addToReviews(new Review(complete:true))
		def rs = new TeamReviewService()
		rs.reviewCompleted tr
		assertTrue tr.complete
		assertTrue saved
    }
	
	void test_completing_an_evaluation_when_other_evaluations_are_not_complete_does_not_complete_review_and_does_not_save() {
		mockDomain(TeamReview,[])
		def saved = false
		TeamReview.metaClass.save = {-> saved=true}
		def tr = new TeamReview(complete:false)
		tr.addToReviews(new Review(complete:false))
		tr.addToReviews(new Review(complete:true))
		def rs = new TeamReviewService()
		rs.reviewCompleted tr
		assertFalse tr.complete
		assertFalse saved
	}
	
	void getExampleTeamReview(){
		mockDomain(TeamReview,[])
		mockDomain(Review,[])
		mockDomain(Evaluation,[])
		def review = new Review(complete:true)
		def teamReview = new TeamReview(complete:true)
		teamReview.addToReviews(review)
		def p1 = new TeamMember(role:new Role(name:'qa'),name:'p1')
		def p2 = new TeamMember(role:new Role(name:'qa'),name:'p2')
		def p3 = new TeamMember(role:new Role(name:'dev'),name:'p3')
		
		
		def q1 = new Question(text:'q1')
		def q2 = new Question(text:'q2')
		def a1 = new Answer(value:2)
		def a2 = new Answer(value:3)
		
		def p1eval = new Evaluation(responder:p1)
		p1eval.addToResponses(new Response(question:q1,answer:a1))//2
		p1eval.addToResponses(new Response(question:q2,answer:a1))//2
		
		def p2eval = new Evaluation(responder:p2)
		p2eval.addToResponses(new Response(question:q1,answer:a1))//2
		p2eval.addToResponses(new Response(question:q2,answer:a2))//3

		def p3eval = new Evaluation(responder:p3)
		p3eval.addToResponses(new Response(question:q1,answer:a2))//3
		p3eval.addToResponses(new Response(question:q2,answer:a2))//3
		
		review.addToEvaluations(p1eval)
		review.addToEvaluations(p2eval)
		review.addToEvaluations(p3eval)
		teamReview
		
	}
	
	void test_example_team_review_has_results_calculated_correctly(){
		rs = new TeamReviewService()
		def rr = rs.calculateScoresForReviewee(getExampleTeamReview)
		assertEquals 2,rr.size()
		//get ReviewResult for q1
		def q1 = rr.find {r -> r.question.text == 'q1'}
		assertNotNull q1
		assertEquals 2.33,q1.yourScore
		//assertEquals 1,q1.teamAverage
		//assertEquals 1,q1.roleAverage
		//assertEquals 1,q1.minAnswer
		//assertEquals 1,q1.maxAnswer
	}
}
